package com.dx.Booker.generator.po;

public class Address {
    private Integer id;

    private String addressdeatil;

    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddressdeatil() {
        return addressdeatil;
    }

    public void setAddressdeatil(String addressdeatil) {
        this.addressdeatil = addressdeatil == null ? null : addressdeatil.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}