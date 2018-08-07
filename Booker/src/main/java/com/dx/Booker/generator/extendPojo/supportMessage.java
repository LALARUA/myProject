package com.dx.Booker.generator.extendPojo;

import com.dx.Booker.generator.po.Support;

public class supportMessage extends Support {

    Integer commentUserId;      //评论者userId
    String supportUserName;     //点赞者名字
    String bookTitle;           //书名
    public Integer getCommentUserId() {
        return commentUserId;
    }

    public void setCommentUserId(Integer commentUserId) {
        this.commentUserId = commentUserId;
    }

    public String getSupportUserName() {
        return supportUserName;
    }

    public void setSupportUserName(String supportUserName) {
        this.supportUserName = supportUserName;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }
}
