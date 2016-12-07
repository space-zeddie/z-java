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

    @NotNull
    private boolean isRecommended;

    @XmlAttribute
    private String prof;

    @XmlAttribute
    private String annotation;

    @ManyToMany(mappedBy = "subjects")
    public List<User> students;

    public List<User> getStudents() {
        return students;
    }

    public void setStudents(List<User> students) {
        this.students = students;
    }

    public Subject () {}

    public Subject(String name, double credits) {
        this.name = name;
        this.credits = credits;
        isRecommended = false;
    }

    public Subject(String name, double credits, String prof, String annotation) {
        this.name = name;
        this.credits = credits;
        isRecommended = false;
        this.prof = prof;
        this.annotation = annotation;
    }

    public boolean isRecommended() {
        return isRecommended;
    }

    public void setRecommended(boolean recommended) {
        isRecommended = recommended;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("<b>");
        res.append(getName());
        res.append("</b>");
        res.append("(" + getId() + ")");
        res.append("<br>");
        res.append("Credits: ");
        res.append(getCredits());
        res.append("<br>");
        if (getProf() != null) {
            res.append("Professor: ");
            res.append(getProf());
            res.append("<br>");
        }
        if (getAnnotation() != null) {
            res.append("Annotation: ");
            res.append(getAnnotation());
            res.append("<br>");
        }
        res.append("Students: ");
        res.append(getStudents().size());
        res.append("<br>");
        return res.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Subject subject = (Subject) o;

        if (id != subject.id) return false;
        if (Double.compare(subject.credits, credits) != 0) return false;
        return name.equals(subject.name);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (id ^ (id >>> 32));
        result = 31 * result + name.hashCode();
        temp = Double.doubleToLongBits(credits);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
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
