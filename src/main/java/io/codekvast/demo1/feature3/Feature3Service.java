package io.codekvast.demo1.feature3;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class Feature3Service {

    public void doSomething() {
        logger.info("Doing something 3");
    }
}
