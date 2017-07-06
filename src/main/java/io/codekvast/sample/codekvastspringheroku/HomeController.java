package io.codekvast.sample.codekvastspringheroku;

import io.codekvast.sample.codekvastspringheroku.button.button1.ButtonOneService;
import io.codekvast.sample.codekvastspringheroku.button.button2.ButtonTwoService;
import io.codekvast.sample.codekvastspringheroku.button.button3.ButtonThreeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping(method = GET)
@Slf4j
public class HomeController {
    private final ButtonOneService buttonOneService;
    private final ButtonTwoService buttonTwoService;
    private final ButtonThreeService buttonThreeService;

    @Autowired
    public HomeController(ButtonOneService buttonOneService,
                          ButtonTwoService buttonTwoService,
                          ButtonThreeService buttonThreeService) {
        this.buttonOneService = buttonOneService;
        this.buttonTwoService = buttonTwoService;
        this.buttonThreeService = buttonThreeService;
    }

    @RequestMapping(value = "/", method = GET)
    String home()  {
        logger.info("Welcome home.");
        return "/home.html";
    }

    @RequestMapping("/button1")
    String buttonOne() {
        buttonOneService.doSomething();
        return "/buttonOne.html";
    }

    @RequestMapping("/button2")
    String buttonTwo() {
        buttonTwoService.doSomething();
        return "/buttonTwo.html";
    }

    @RequestMapping("/button3")
    String buttonThree() {
        // This feature has been disabled: buttonThreeService.doSomething();
        return "/buttonThree.html";
    }
}

