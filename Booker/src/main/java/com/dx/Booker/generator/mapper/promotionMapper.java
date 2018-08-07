package com.dx.Booker.generator.mapper;

import com.dx.Booker.generator.po.Coupon;
import com.dx.Booker.generator.po.promotion;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

/**
 * @Author: xiangXX
 * @Description:
 * @Date Created in 9:14 2018/6/13 0013
 */
public interface promotionMapper {
    @Select("select * from promotion where userId = #{userId} and typeId = #{typeId}")
    public promotion selectPromotion(promotion promotion);

    @Insert("insert into promotion (userId,typeId) values(#{userId},#{typeId})")
    public void insertPromotion(Coupon coupon);

}
