package io.codekvast.sample1.feature1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class Feature1Service {

    public void doSomething() {
        logger.info("Doing something 1");
    }
}
