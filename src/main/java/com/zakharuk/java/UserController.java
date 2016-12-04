package com.zakharuk.java;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.transaction.Transactional;

/**
 * Created by citizenzer0 on 12/4/16.
 */
@Controller
public class UserController {

    @Autowired
    private UserDao userDao;

    @RequestMapping("/createuser")
    @ResponseBody
    public String create(String name, String password, String role) {
        String userId = "";
        try {
            User user = new User(name, password, role);
            userDao.save(user);
            userId = String.valueOf(user.getId());
        }
        catch (Exception ex) {
            return "Error creating the user: " + ex.toString();
        }
        return "User succesfully created with id = " + userId;
    }

    @RequestMapping("/deleteuser")
    @ResponseBody
    public String delete(long id) {
        try {
            User user = new User();
            user.setId(id);
            userDao.delete(user);
        }
        catch (Exception ex) {
            return "Error deleting the user:" + ex.toString();
        }
        return "User succesfully deleted!";
    }

    @RequestMapping("/get-user-by-name")
    @ResponseBody
    public String getByName(String name) {
        String userId = "";
        try {
            User user = userDao.findByName(name);
            userId = String.valueOf(user.getId());
        }
        catch (Exception ex) {
            return "User not found";
        }
        return "The subject id is: " + userId;
    }

    @RequestMapping("/updateuser")
    @ResponseBody
    public String updateSubject(long id, String name, String password, String role) {
        try {
            User user = userDao.findOne(id);
            user.setName(name);
            user.setPassword(password);
            user.setRole(role);
            userDao.save(user);
        }
        catch (Exception ex) {
            return "Error updating the user: " + ex.toString();
        }
        return "User succesfully updated!";
    }
}
