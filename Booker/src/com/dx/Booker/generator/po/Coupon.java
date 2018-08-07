package com.dx.Booker.generator.po;

import java.util.Date;

public class Coupon {
    private Integer id;

    private Integer userId;

    private Integer typeId;

    private Date valid;

    private Date dateofacquisition;

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

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Date getValid() {
        return valid;
    }

    public void setValid(Date valid) {
        this.valid = valid;
    }

    public Date getDateofacquisition() {
        return dateofacquisition;
    }

    public void setDateofacquisition(Date dateofacquisition) {
        this.dateofacquisition = dateofacquisition;
    }
}