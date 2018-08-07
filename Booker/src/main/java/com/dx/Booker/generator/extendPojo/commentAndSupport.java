package com.dx.Booker.generator.extendPojo;

import com.dx.Booker.generator.po.Comment;
import com.dx.Booker.generator.po.User;
import org.apache.ibatis.annotations.Insert;

public class commentAndSupport extends Comment {
    private User user;                  //评论user信息
    private Integer commentSupports;    //评论点赞数
    private boolean ifUserSupport;      //浏览评论的user是否点赞
    private Integer userBrowseId;       //浏览者userId
    private String userBrowseName;      //浏览者userName


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
