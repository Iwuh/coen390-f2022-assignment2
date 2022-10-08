package com.mfaigan.assignment2.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Profile {

    @PrimaryKey(autoGenerate = true)
    private long uid;

    private String surname;
    private String name;
    private double gpa;
    private Date creationDate;

    public Profile(long uid, String surname, String name, double gpa, Date creationDate) {
        this.uid = uid;
        this.surname = surname;
        this.name = name;
        this.gpa = gpa;
        this.creationDate = creationDate;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
