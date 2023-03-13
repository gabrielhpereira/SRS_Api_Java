package com.api.srs.vo.user;

import java.util.Date;

public class LogUserVo {

    private Integer id;
    private String description;
    private String date;
    private Integer userId;

    public LogUserVo(Integer id, String description, Date date, Integer userId){
        this.id = id;
        this.description = description.toUpperCase();
        this.date = date.toString();
        this.userId = userId;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}