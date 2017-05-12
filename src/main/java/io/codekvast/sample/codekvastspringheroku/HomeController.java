package io.codekvast.sample.codekvastspringheroku;

import io.codekvast.sample.codekvastspringheroku.button.one.ButtonOneService;
import io.codekvast.sample.codekvastspringheroku.button.two.ButtonTwoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

    private final ButtonOneService buttonOneService;
    private final ButtonTwoService buttonTwoService;

    @Autowired
    public HomeController(ButtonOneService buttonOneService,
                          ButtonTwoService buttonTwoService) {
        this.buttonOneService = buttonOneService;
        this.buttonTwoService = buttonTwoService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    String home()  {
        return "/home.html";
    }

    @RequestMapping("/button1")
    String buttonOne() {
        buttonTwoService.doSomething();
        return "/buttonOne.html";
    }

    @RequestMapping("/button2")
    String buttonTwo() {
        buttonTwoService.doSomething();
        return "/buttonTwo.html";
    }
}

