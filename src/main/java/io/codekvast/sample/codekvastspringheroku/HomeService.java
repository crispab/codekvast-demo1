package io.codekvast.sample.codekvastspringheroku;

import org.springframework.stereotype.Component;

@Component
public class HomeService {
    public String home() {
        return "Hello World, Enterprise Version FTW!";
    }
}
