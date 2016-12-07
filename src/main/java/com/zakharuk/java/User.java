package com.zakharuk.java;

import org.springframework.context.annotation.Role;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by citizenzer0 on 12/3/16.
 */
@Entity
@Table(name = "users")
public class User {

    static double CREDIT_LIMIT = 30;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private String name;

    @NotNull
    private String password;

    @NotNull
    private String role;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_subjects", joinColumns = @JoinColumn(name = "subject_id", referencedColumnName = "id"))
    private List<Subject> subjects;

    public User() {
        subjects = new ArrayList<Subject>();
    }

    public User(String name, String password, String role) {
        this.name = name;
        this.password = password;
        this.role = role;
        subjects = new ArrayList<Subject>();
    }

    public double getAllCredits() {
        double total = 0;
        for (Subject s : subjects) {
            total += s.getCredits();
        }
        return total;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("<i>");
        res.append(getName());
        res.append("</i>");
        res.append("(#" + getId() + ")");
        res.append("<br>");
        res.append("Role: ");
        res.append(getRole());
        res.append("<br>");
        res.append("Subjects: ");
        res.append(getSubjects().size());
        res.append("<br>");
        return res.toString();
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
