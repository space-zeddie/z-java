package com.zakharuk.java;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.POST;

/**
 * Created by citizenzer0 on 12/4/16.
 */
@Controller
public class AddController {

    @RequestMapping(value = "/new-user")
    public String addNewUser() {
        return "addstudent";
    }

    @RequestMapping(value = "/new-subject")
    public String addNewSubject() {
        return "addsubject";
    }
}
