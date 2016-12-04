package com.zakharuk.java;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by citizenzer0 on 12/4/16.
 */
@Controller
public class UserController {

    @Autowired
    private UserDao userDao;
    @Autowired
    private SubjectDao subjectDao;

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
            return SubjectController.HEADER + "Error creating the user: " + ex.toString() + SubjectController.FOOTER;
        }
        return SubjectController.HEADER + "User succesfully created with id = " + userId + SubjectController.FOOTER;
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
            return SubjectController.HEADER + "Error deleting the user:" + ex.toString() + SubjectController.FOOTER;
        }
        return SubjectController.HEADER + "User succesfully deleted!" + SubjectController.FOOTER;
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
            return SubjectController.HEADER + "User not found" + SubjectController.FOOTER;
        }
        return SubjectController.HEADER + "The subject id is: " + userId + SubjectController.FOOTER;
    }

    @RequestMapping("/updateuser")
    @ResponseBody
    public String updateUser(long id, String name, String password, String role) {
        try {
            User user = userDao.findOne(id);
            user.setName(name);
            user.setPassword(password);
            user.setRole(role);
            userDao.save(user);
        }
        catch (Exception ex) {
            return SubjectController.HEADER + "Error updating the user: " + ex.toString() + SubjectController.FOOTER;
        }
        return SubjectController.HEADER + "User succesfully updated!" + SubjectController.FOOTER;
    }

    @RequestMapping("/all-users")
    @ResponseBody
    public String findAll() {
        try {
            Iterable<User> users = userDao.findAll();
            StringBuilder res = new StringBuilder();
            res.append(SubjectController.HEADER);
            for (User s : users)
                res.append(s + "<br>");
            res.append(SubjectController.FOOTER);
            return res.toString();
        }
        catch (Exception ex) {
            return SubjectController.HEADER + "Error retrieving the subjects: " + ex.toString() + SubjectController.FOOTER;
        }
    }

    @RequestMapping("/add-subject")
    @ResponseBody
    public String addSubject(long userid, String subjectName) {
        try {
            User user = userDao.findOne(userid);
            Subject subject = subjectDao.findByName(subjectName);
            List<Subject> newlist = user.getSubjects();
            newlist.add(subject);
            user.setSubjects(newlist);
            userDao.save(user);
        }
        catch (Exception ex) {
            return SubjectController.HEADER + "Error adding the subject: " + ex.toString() + SubjectController.FOOTER;
        }
        return SubjectController.HEADER + "Subject succesfully added!" + SubjectController.FOOTER;
    }

    @RequestMapping("/remove-subject")
    @ResponseBody
    public String removeSubject(long userid, String subjectName) {
        try {
            User user = userDao.findOne(userid);
            Subject subject = subjectDao.findByName(subjectName);
            List<Subject> newlist = user.getSubjects();
            newlist.remove(subject);
            user.setSubjects(newlist);
            userDao.save(user);
        }
        catch (Exception ex) {
            return SubjectController.HEADER + "Error removing the subject: " + ex.toString() + SubjectController.FOOTER;
        }
        return SubjectController.HEADER + "Subject succesfully removed!" + SubjectController.FOOTER;
    }
}
