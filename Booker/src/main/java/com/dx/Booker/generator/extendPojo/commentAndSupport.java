package com.dx.Booker.generator.extendPojo;

import com.dx.Booker.generator.po.Comment;
import com.dx.Booker.generator.po.User;
import com.dx.Booker.generator.po.reply;
import org.apache.ibatis.annotations.Insert;

import java.util.ArrayList;

public class commentAndSupport extends Comment {
    private User user;                  //评论user信息
    private Integer commentSupports;    //评论点赞数
    private boolean ifUserSupport;      //浏览评论的user是否点赞
    private Integer userBrowseId;       //浏览者userId
    private String userBrowseName;      //浏览者userName

    private ArrayList<reply> replies;   //该评论的回复

    public ArrayList<reply> getReplies() {
        return replies;
    }

    public void setReplies(ArrayList<reply> replies) {
        this.replies = replies;
    }

    public String getUserBrowseName() {
        return userBrowseName;
    }

    public void setUserBrowseName(String userBrowseName) {
        this.userBrowseName = userBrowseName;
    }

    public Integer getUserBrowseId() {
        return userBrowseId;
    }

    public void setUserBrowseId(Integer userBrowseId) {
        this.userBrowseId = userBrowseId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getCommentSupports() {
        return commentSupports;
    }

    public void setCommentSupports(Integer commentSupport) {
        this.commentSupports = commentSupport;
    }

    public boolean isIfUserSupport() {
        return ifUserSupport;
    }

    public void setIfUserSupport(boolean ifUserSupport) {
        this.ifUserSupport = ifUserSupport;
    }
}
