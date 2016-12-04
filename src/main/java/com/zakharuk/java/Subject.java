package com.zakharuk.java;

import javax.jws.soap.SOAPBinding;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

/**
 * Created by citizenzer0 on 12/2/16.
 */
@XmlRootElement(name = "subject")
@XmlType(propOrder = {"id", "name", "credits", "prof", "annotation"})
@Entity
@Table(name = "subjects")
public class Subject {

    @XmlID
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private String name;

    @NotNull
    private double credits;

    @XmlAttribute
    private String prof;

    @XmlAttribute
    private String annotation;

    @ManyToMany(mappedBy = "subjects")
    public List<User> students;

    public Subject () {}

    public Subject(String name, double credits) {
        this.name = name;
        this.credits = credits;
    }

    public Subject(String name, double credits, String prof, String annotation) {
        this.name = name;
        this.credits = credits;
        this.prof = prof;
        this.annotation = annotation;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", credits=" + credits +
                ", prof='" + prof + '\'' +
                ", annotation='" + annotation + '\'' +
                '}';
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

    public double getCredits() {
        return credits;
    }

    public void setCredits(double credits) {
        this.credits = credits;
    }

    public String getProf() {
        return prof;
    }

    public void setProf(String prof) {
        this.prof = prof;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }
}
