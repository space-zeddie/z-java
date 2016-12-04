package com.zakharuk.java;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.transaction.Synchronization;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;

/**
 * Created by citizenzer0 on 12/2/16.
 */
@Controller
public class SubjectController {

    @Autowired
    private SubjectDao subjectDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private UserRepository userRepository;

    /**
     * GET /create  --> Create a new subject and save it in the database.
     */
    @RequestMapping("/create")
    @ResponseBody
    public String create(String name, double credits) {
        String subjectId = "";
        try {
            Subject subject = new Subject(name, credits);
            subjectDao.save(subject);
            subjectId = String.valueOf(subject.getId());
        }
        catch (Exception ex) {
            return HEADER +  "Error creating the subject: " + ex.toString() + FOOTER;
        }
        return HEADER + "Subject succesfully created with id = " + subjectId + FOOTER;
    }

    /**
     * GET /delete  --> Delete the subject having the passed id.
     */
    @RequestMapping("/delete")
    @ResponseBody
    public String delete(long id) {
        try {
            Subject subject = new Subject();
            subject.setId(id);
            subjectDao.delete(subject);
        }
        catch (Exception ex) {
            return HEADER + "Error deleting the subject:" + ex.toString() + FOOTER;
        }
        return HEADER + "Subject succesfully deleted!" + FOOTER;
    }

    /**
     * GET /get-by-name  --> Return the id for the subject having the passed
     * name.
     */
    @RequestMapping("/get-by-name")
    @ResponseBody
    public String getByName(String name) {
        String subjectId = "";
        try {
            Subject subject = subjectDao.findByName(name);
            subjectId = String.valueOf(subject.getId());
        }
        catch (Exception ex) {
            return HEADER + "Subject not found" + FOOTER;
        }
        return HEADER + "The subject id is: " + subjectId + FOOTER;
    }

    /**
     * GET /update  --> Update the name and the credits for the subject in the
     * database having the passed id.
     */
    @RequestMapping("/update")
    @ResponseBody
    public String updateSubject(long id, String name, double credits) {
        try {
            Subject subject = subjectDao.findOne(id);
            subject.setName(name);
            subject.setCredits(credits);
            subjectDao.save(subject);
        }
        catch (Exception ex) {
            return HEADER + "Error updating the subject: " + ex.toString() + FOOTER;
        }
        return HEADER + "Subject succesfully updated!" + FOOTER;
    }

    @RequestMapping("/all")
    @ResponseBody
    public String findAll() {
        StringBuilder res = new StringBuilder();
        res.append(HEADER);
        try {
            Iterable<Subject> all = subjectDao.findAll();
            for (Subject s : all) {
                res.append("<div>");
                res.append(s.toString());
                res.append("<br>");
                res.append(listStudentsBtn(s.getId()));
                res.append("</div>");
                res.append("<br>");
            }
        }
        catch (Exception ex) {
            res.append("Error retrieving the subjects: " + ex.toString());
        }
        finally {
            res.append(FOOTER);
            return res.toString();
        }
    }

    @RequestMapping("/list-students")
    @ResponseBody
    public String listStudents(long id) {
        StringBuilder res = new StringBuilder();
        res.append(HEADER);
        try {
            Subject s = subjectDao.findOne(id);
            for (User u : s.getStudents()) {
                res.append(u.toString());
                res.append("<br>");
            }
        }
        catch (Exception ex) {
            res.append("Error retrieving the students: " + ex.toString());
        }
        finally {
            res.append(FOOTER);
            return res.toString();
        }
    }


    @RequestMapping("/add-user")
    @ResponseBody
    @Transactional
    public String addUser(Long id, Long userId) {
        try {
            Subject subject = subjectDao.findOne(id);
            User user = userDao.findOne(userId);
            subject.getStudents().add(user);
            user.getSubjects().add(subject);
            subjectRepository.save(new HashSet<Subject>(){{
                add(subject);
            }});
            userRepository.save(new HashSet<User>(){{
                add(user);
            }});
            //subjectDao.save(subject);
        }
        catch (Exception ex) {
            return HEADER + "Error adding the user: " + ex.toString() + FOOTER;
        }
        return HEADER + "User succesfully added!" + FOOTER;
    }

    @RequestMapping("/remove-user")
    @ResponseBody
    public String removeSubject(long subjectid, String userName) {
        try {
            Subject subject = subjectDao.findOne(subjectid);
            User user = userDao.findByName(userName);
            subject.getStudents().remove(user);
            subjectDao.save(subject);
        }
        catch (Exception ex) {
            return "Error removing the user: " + ex.toString();
        }
        return "User succesfully removed!";
    }

    private String listStudentsBtn(long id) {

        return "<a href=\"/list-students?id=" + id + "\" class=\"btn btn-info\">View Students</a>";
    }

    protected static String HEADER = "<!DOCTYPE html>\n" +
            "<html xmlns:th=\"http://www.thymeleaf.org\">\n" +
            "<head lang=\"en\">\n" +
            "\n" +
            "    <title>Subjects</title>\n" +
            "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/>\n" +
            "    <link href=\"css/bootstrap.min.css\"\n" +
            "          th:href=\"@{css/bootstrap.min.css}\"\n" +
            "          rel=\"stylesheet\" media=\"screen\" />\n" +
            "    <script src=\"css/js/bootstrap.js\"\n" +
            "            th:src=\"@{css/js/bootstrap.js}\"></script>\n" +
            "    <link href=\"../static/css/main.css\"\n" +
            "          th:href=\"@{css/main.css}\" rel=\"stylesheet\" media=\"screen\"/>\n" +
            "</head>\n" +
            "<body>\n" +
            "<div class=\"container\">";

    protected static String FOOTER = "<br><a href=\"/\" class=\"btn btn-info\"><< Back to Homepage</a>" + "\n" +
            "</div>\n" +
            "</body>\n" +
            "</html>";

}
