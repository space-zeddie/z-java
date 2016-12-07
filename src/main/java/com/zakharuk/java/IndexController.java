package com.zakharuk.java;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by citizenzer0 on 11/28/16.
 */
@Controller
public class IndexController {

    @RequestMapping("/")
    String index() {
        return "index";
    }
    @RequestMapping("/login")
    String loginPage() {
        return "login";
    }
    @RequestMapping("/logout")
    String logoutPage() {
        return "logout";
    }

    @RequestMapping("/switch-production")
    public String switchProductionStage(int id) {
        ZakharukApplication.moveProductionStage(id);
        return "index";
    }
}
