package com.dx.Booker.generator.mapper;

import com.dx.Booker.generator.extendPojo.couponDetail;
import com.dx.Booker.generator.po.Coupon;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserCouponMapper {

    public List<couponDetail> userCoupons(Integer userId);

    @Delete("delete from coupon where id = #{couponId}")
    public void deleteCoupon(Integer couponId);
    @Insert("insert into coupon (userId,typeId,dateofacquisition) values(#{userId},#{typeId},#{dateofacquisition})")
    public void insertCoupon(Coupon coupon);




}
