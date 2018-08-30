package com.leeyeah.ua;

import java.io.Serializable;

public class Student implements Serializable {
    private String School;
    private String id;

    public String getSchool() {
        return School;
    }

    public void setSchool(String school) {
        School = school;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
