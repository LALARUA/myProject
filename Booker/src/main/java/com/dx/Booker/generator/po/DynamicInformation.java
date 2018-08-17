package com.dx.Booker.generator.po;

import java.util.Date;

public class DynamicInformation {
    private Integer id;
    private Date dateName;
    private String detail;
    private String title;
    private String image;
    private String url;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateName() {
        return dateName;
    }

    public void setDateName(Date dateName) {
        this.dateName = dateName;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
