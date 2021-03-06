package com.zakharuk.java;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.transaction.Transactional;
import java.util.HashSet;
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
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/createuser")
    @ResponseBody
    public String create(String name, String password, String role) {
        //System.err.println("HERE");
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
    @Transactional
    public String addSubject(Long userid, Long subjectid) {
        try {
            User user = userDao.findOne(userid);
            Subject subject = subjectDao.findOne(subjectid);
            if (!user.getSubjects().contains(subject)) {
                if (user.getAllCredits() + subject.getCredits() <= User.CREDIT_LIMIT) {
                    user.getSubjects().add(subject);
                    subject.getStudents().add(user);
                    subjectRepository.save(new HashSet<Subject>(){{
                        add(subject);
                    }});
                    userRepository.save(new HashSet<User>(){{
                        add(user);
                    }});
                }
                else return SubjectController.HEADER + "No more available credits!" + SubjectController.FOOTER;
            }
            else return SubjectController.HEADER + "You've already signed up for this subject." + SubjectController.FOOTER;
        }
        catch (Exception ex) {
            return SubjectController.HEADER + "Error adding the subject: " + ex.toString() + SubjectController.FOOTER;
        }
        return SubjectController.HEADER + "Subject succesfully added!" + SubjectController.FOOTER;
    }

    @RequestMapping("/remove-subject")
    @ResponseBody
    public String removeSubject(Long userid, Long subjectid) {
        try {
            User user = userDao.findOne(userid);
            Subject subject = subjectDao.findOne(subjectid);
            if (user.getSubjects().contains(subject)) {
                user.getSubjects().remove(subject);
                subject.getStudents().remove(user);
                subjectRepository.save(new HashSet<Subject>(){{
                    add(subject);
                }});
                userRepository.save(new HashSet<User>(){{
                    add(user);
                }});
            }
        }
        catch (Exception ex) {
            return SubjectController.HEADER + "Error removing the subject: " + ex.toString() + SubjectController.FOOTER;
        }
        return SubjectController.HEADER + "Subject succesfully removed!" + SubjectController.FOOTER;
    }

    @RequestMapping("/my-console")
    @ResponseBody
    public String myConsole() {
        try {
            StringBuilder res = new StringBuilder();
            res.append(SubjectController.HEADER);
            String myName = SecurityConfiguration.findAuth().getName();
            User u = userDao.findByName(myName);
            res.append("<div class=\"jumbotron\">");
            if (SecurityConfiguration.isStudent()) {
                res.append("<div>Hello " + myName + "!</div>");
                res.append("<div>You currently have " + u.getAllCredits() + " credits.</div>");
                res.append("<div>You're signed up for ");
                for (Subject s : u.getSubjects())
                    res.append(s.getName() + " ");
                res.append("</div");
            }
            if (SecurityConfiguration.isMethodist()) {
                res.append("<div>Hello " + myName + "!</div>");
               // res.append("<a href=\"/new-subject\" class=\"btn btn-info\">Add a subject</a>");
            }
            if (SecurityConfiguration.isAdmin()) {
                res.append("<div>Hello " + myName + "!</div>");
                res.append("<a href=\"/new-subject\" class=\"btn btn-info\">Add a subject</a>");
                if (ZakharukApplication.getProductionStage() != 1)
                    res.append("<a href=\"/switch-production?id=1\" class=\"btn btn-info\">Switch to 1st production stage</a>");
                if (ZakharukApplication.getProductionStage() != 2)
                    res.append("<a href=\"/switch-production?id=2\" class=\"btn btn-info\">Switch to 2nd production stage</a>");
                if (ZakharukApplication.getProductionStage() != 3)
                    res.append("<a href=\"/switch-production?id=3\" class=\"btn btn-info\">Switch to 3rd production stage</a>");
            }
            res.append("</div>");
            res.append(SubjectController.FOOTER);
            return res.toString();
        }
        catch (Exception ex) {
            return SubjectController.HEADER + "Error finding a user in the database: " + ex.toString() + SubjectController.FOOTER;
        }
    }
}
