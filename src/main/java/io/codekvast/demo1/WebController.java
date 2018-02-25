package io.codekvast.demo1;

import io.codekvast.demo1.feature1.Feature1Service;
import io.codekvast.demo1.feature2.Feature2Service;
import io.codekvast.demo1.feature3.Feature3Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping(method = GET)
@Slf4j
public class WebController {
    private final Feature1Service feature1Service;
    private final Feature2Service feature2Service;
    private final Feature3Service feature3Service;

    @Autowired
    public WebController(Feature1Service feature1Service,
                         Feature2Service feature2Service,
                         Feature3Service feature3Service) {
        this.feature1Service = feature1Service;
        this.feature2Service = feature2Service;
        this.feature3Service = feature3Service;
    }

    @RequestMapping(value = "/", method = GET)
    String home(Model model) {
        logger.info("Welcome home.");
        String dashboardTarget = System.getenv("CODEKVAST_TARGET_FRAME");
        if (dashboardTarget == null) {
            dashboardTarget = "_blank";
        }
        model.addAttribute("dashboardTarget", dashboardTarget);
        return "home";
    }

    @RequestMapping("/feature1")
    String feature1() {
        feature1Service.doSomething();
        return "feature1";
    }

    @RequestMapping("/feature2")
    String feature2() {
        feature2Service.doSomething();
        return "feature2";
    }

    @RequestMapping("/feature3")
    String feature3() {
        // This feature has been disabled: button3Service.doSomething();
        return "feature3";
    }
}
