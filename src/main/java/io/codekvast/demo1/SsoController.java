package io.codekvast.demo1;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

import static javax.xml.bind.DatatypeConverter.printHexBinary;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * A controller that receives a request to to a Single Sign-On to Codekvast Dashboard, simulating
 * that the user performs `heroku addons:open codekvast` or clicks on Codekvast in the Heroku Dashboard.
 *
 * @author olle.hallin@crisp.se
 */
@Controller
@Slf4j
public class SsoController {

    @Value("${codekvast.herokuApiSsoSalt}")
    private String herokuApiSsoSalt;

    @Value("${codekvast.sso.url}")
    private String codekvastSsoUrl;

    @Value("${app.herokuId}")
    private String herokuId;

    @Value("${app.herokuAppName}")
    private String herokuAppName;

    private MessageDigest sha1;

    public SsoController() throws NoSuchAlgorithmException {
        this.sha1 = MessageDigest.getInstance("SHA-1");
    }

    @PostConstruct
    public void postConstruct() {
        logger.info("Will use '{}' as herokuId", herokuId);
        logger.info("Will use '{}' as codekvastSsoUrl", codekvastSsoUrl);
    }

    @RequestMapping(path = "/openCodekvastDashboard", method = {GET, POST})
    public String openCodekvastDashboard(@RequestParam(value = "email", required = false, defaultValue = "john.doe@acme.org") String email,
                                         HttpServletResponse httpServletResponse) throws IOException {
        logger.info("Simulating 'heroku addons:open codekvast' for user '{}'", email);
        Request request = simulateHerokuAddonsOpen(email);
        return "redirect:" + performSsoRequestCopyCookiesAndGetNewLocation(request, httpServletResponse);
    }

    private String performSsoRequestCopyCookiesAndGetNewLocation(Request request, HttpServletResponse finalResponse) throws IOException {
        OkHttpClient httpClient = new OkHttpClient.Builder().followRedirects(false).build();

        try (Response response = httpClient.newCall(request).execute()) {
            logger.info("Response={}", response);
            logger.info("Headers={}", response.headers());

            // Copy cookies...
            List<String> cookies = response.headers("Set-Cookie");
            for (String cookie : cookies) {
                logger.info("Received cookie={}", cookie);
                finalResponse.addHeader("Set-Cookie", cookie);
            }

            String location = response.header("Location");
            logger.info("Redirecting to {}", location);
            return location;
        }
    }

    private Request simulateHerokuAddonsOpen(@RequestParam(value = "email", required = false, defaultValue = "foo.bar@acme.com") String email) {
        long timestampSeconds = System.currentTimeMillis() / 1000L;

        RequestBody body = new FormBody.Builder()
                .add("id", herokuId)
                .add("timestamp", String.format("%d", timestampSeconds))
                .add("token", makeHerokuSsoToken(timestampSeconds))
                .add("nav-data", makeHerokuNavData(herokuAppName))
                .add("email", email)
                .build();

        return new Request.Builder()
                .url(String.format("%s/heroku/sso/", codekvastSsoUrl))
                .post(body)
                .build();
    }

    @SneakyThrows(UnsupportedEncodingException.class)
    private String makeHerokuNavData(String appName) {
        // A realistic navData JSON object contains a lot more fields. Codekvast is however only using the appname field.
        return Base64.getEncoder().withoutPadding().encodeToString(String.format("{\"appname\": \"%s\"}", appName).getBytes("UTF-8"));
    }

    private String makeHerokuSsoToken(long timestampSeconds) {
        return printHexBinary(
                sha1.digest(String.format("%s:%s:%d", herokuId, herokuApiSsoSalt, timestampSeconds).getBytes())).toLowerCase();
    }

}
