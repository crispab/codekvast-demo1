package io.codekvast.sample.codekvastspringheroku;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

@SuppressWarnings("WeakerAccess")
@Component
public class HomeService {
    public String home() throws IOException {
        URL resource = getClass().getClassLoader().getResource("static/home.html");
        if (resource != null) {
            return new String(Files.readAllBytes(Paths.get(resource.getFile())));
        }
        return "Home page not found.";
    }
}
