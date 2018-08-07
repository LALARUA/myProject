package com.dx.Booker.generator.extendPojo;

import com.dx.Booker.generator.po.Coupon;
import com.dx.Booker.generator.po.CouponType;

public class couponDetail extends Coupon {
        private CouponType couponType;
        private String overtime;
        private String receiveTime;

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getOvertime() {
        return overtime;
    }

    public void setOvertime(String overtime) {
        this.overtime = overtime;
    }

    public CouponType getCouponType() {
        return couponType;
    }

    public void setCouponType(CouponType couponType) {
        this.couponType = couponType;
    }
}
