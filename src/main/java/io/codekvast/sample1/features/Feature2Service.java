package io.codekvast.sample1.features;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class Feature2Service {

    public void doSomething() {
        logger.info("Doing something 2");
    }
}
