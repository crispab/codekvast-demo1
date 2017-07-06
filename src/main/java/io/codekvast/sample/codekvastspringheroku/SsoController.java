package io.codekvast.sample.codekvastspringheroku;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static javax.xml.bind.DatatypeConverter.printHexBinary;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * A controller that receives a request to to a Single Sign-On to Codekvast Warehouse, simulating
 * that the user performs `heroku addons:open codekvast` or clicks on Codekvast in the Heroku Dashboard.
 *
 * @author olle.hallin@crisp.se
 */
@Controller
@Slf4j
public class SsoController {

    @Value("${codekvast.herokuApiSsoSalt}")
    private String herokuApiSsoSalt;

    @Value("${codekvast.url}")
    private String codekvastUrl;

    @Value("${app.herokuId}")
    private String herokuId;

    @Value("${app.herokuNavData}")
    private String herokuNavData;

    private MessageDigest sha1;

    public SsoController() throws NoSuchAlgorithmException {
        this.sha1 = MessageDigest.getInstance("SHA-1");
    }

    @RequestMapping(path = "/openCodekvastDashboard", method = {GET, POST})
    public String openCodekvastDashboard(@RequestParam(value = "email", required = false, defaultValue = "foo.bar@acme.com") String email) throws IOException {
        Request request = simulateHerokuAddonsOpen(email);
        return "redirect:" + performRequestAndFollowRedirect(request);
    }

    private String performRequestAndFollowRedirect(Request request) throws IOException {
        OkHttpClient httpClient = new OkHttpClient.Builder().followRedirects(false).build();

        try (Response response = httpClient.newCall(request).execute()) {
            logger.debug("Response={}", response);
            logger.debug("Headers={}", response.headers());
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
                .add("nav-data", herokuNavData)
                .add("email", email)
                .build();

        return new Request.Builder()
                .url(String.format("%s/heroku/sso/", codekvastUrl))
                .post(body)
                .build();
    }

    private String makeHerokuSsoToken(long timestampSeconds) {
        return printHexBinary(
                sha1.digest(String.format("%s:%s:%d", herokuId, herokuApiSsoSalt, timestampSeconds).getBytes())).toLowerCase();
    }

}
