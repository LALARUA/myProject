package com.dx.Booker.generator.po;

import java.util.Date;

public class Message {
    private Integer id;

    private Integer userId;

    private Integer userSend;

    private Date date;

    private String detail;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserSend() {
        return userSend;
    }

    public void setUserSend(Integer userSend) {
        this.userSend = userSend;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }
}