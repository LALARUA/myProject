package com.dx.Booker.generator.mapper;

import com.dx.Booker.generator.po.Coupontype;
import com.dx.Booker.generator.po.CoupontypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CoupontypeMapper {
    long countByExample(CoupontypeExample example);

    int deleteByExample(CoupontypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Coupontype record);

    int insertSelective(Coupontype record);

    List<Coupontype> selectByExampleWithBLOBs(CoupontypeExample example);

    List<Coupontype> selectByExample(CoupontypeExample example);

    Coupontype selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Coupontype record, @Param("example") CoupontypeExample example);

    int updateByExampleWithBLOBs(@Param("record") Coupontype record, @Param("example") CoupontypeExample example);

    int updateByExample(@Param("record") Coupontype record, @Param("example") CoupontypeExample example);

    int updateByPrimaryKeySelective(Coupontype record);

    int updateByPrimaryKeyWithBLOBs(Coupontype record);

    int updateByPrimaryKey(Coupontype record);
}