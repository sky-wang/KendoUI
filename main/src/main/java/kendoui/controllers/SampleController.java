package kendoui.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SampleController {

    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        return "hello";
    }

    @RequestMapping("/")
    public String home() {
        return "home";
    }

}
