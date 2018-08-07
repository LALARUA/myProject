package com.dx.Booker.generator.extendPojo;

import com.dx.Booker.generator.po.Message;

public class MessagePro extends Message {
    private String userName;
    private String userIcon;
    private String showDate;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public String getShowDate() {
        return showDate;
    }

    public void setShowDate(String showDate) {
        this.showDate = showDate;
    }
}
