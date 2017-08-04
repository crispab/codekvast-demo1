package io.codekvast.sample1.button.button3;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ButtonThreeService {

    public void doSomething() {
        logger.info("Doing something 3");
    }
}
