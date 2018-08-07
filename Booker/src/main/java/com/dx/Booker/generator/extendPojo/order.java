package com.dx.Booker.generator.extendPojo;

import com.dx.Booker.generator.po.Order;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public class order extends Order {

    private List<orderDetail> orderDetails;

    private Integer couponId;

    private String datetime;
//   private Address address;
//
//    public Address getAddress() {
//        return address;
//    }
//
//    public void setAddress(Address address) {
//        this.address = address;
//    }

    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public List<orderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<orderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }
}
