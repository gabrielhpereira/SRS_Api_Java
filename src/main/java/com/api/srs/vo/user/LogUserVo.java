package com.api.srs.vo.user;

import com.api.srs.entity.user.UserEntity;

import java.util.Date;

public class LogUserVo {

    private Integer id;
    private String description;
    private String date;
    private String user;

    public LogUserVo(Integer id, String description, Date date, UserEntity user){
        this.id = id;
        this.description = description.toUpperCase();
        this.date = date.toString();
        this.user = user.getId() + " - " + user.getName();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}