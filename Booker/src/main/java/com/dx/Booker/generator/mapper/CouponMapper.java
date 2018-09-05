package com.dx.Booker.generator.mapper;

import org.apache.ibatis.annotations.Select;

public interface CouponMapper {

    @Select("select count(*) from coupontype")
    public Integer couponTypeNum();
}
