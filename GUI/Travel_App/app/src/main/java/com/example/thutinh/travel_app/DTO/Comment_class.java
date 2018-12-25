package com.example.thutinh.travel_app.DTO;

import java.io.Serializable;

public class Comment_class implements Serializable {

    String name;
    String cmt;
    String timeCmt;
    public Comment_class(){

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCmt(String cmt) {
        this.cmt = cmt;
    }

    public void setTimeCmt(String timeCmt) {
        this.timeCmt = timeCmt;
    }

    public String getName() {
        return name;
    }

    public String getCmt() {
        return cmt;
    }

    public String getTimeCmt() {
        return timeCmt;
    }

    public Comment_class(String name, String cmt, String timeCmt) {
        this.name = name;
        this.cmt = cmt;
        this.timeCmt = timeCmt;
    }
}
