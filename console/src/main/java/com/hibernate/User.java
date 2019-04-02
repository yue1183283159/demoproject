package com.hibernate;

import java.util.Date;

public class User {
    private int id;
    private String name;
    private String gender;
    private Date addon;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getAddon() {
        return addon;
    }

    public User(int id, String name, String gender, Date addon) {
        super();
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.addon = addon;
    }

    public void setAddon(Date addon) {
        this.addon = addon;
    }

    public User() {
    }

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }


}
