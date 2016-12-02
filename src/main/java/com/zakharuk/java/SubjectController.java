package com.zakharuk.java;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by citizenzer0 on 12/2/16.
 */
@Controller
public class SubjectController {

    @Autowired
    private SubjectDao subjectDao;

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
            return "Error creating the subject: " + ex.toString();
        }
        return "Subject succesfully created with id = " + subjectId;
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
            return "Error deleting the subject:" + ex.toString();
        }
        return "Subject succesfully deleted!";
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
            return "Subject not found";
        }
        return "The subject id is: " + subjectId;
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
            return "Error updating the subject: " + ex.toString();
        }
        return "Subject succesfully updated!";
    }

}
