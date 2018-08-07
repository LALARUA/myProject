package com.dx.Booker.generator.mapper;

import com.dx.Booker.generator.po.Support;
import com.dx.Booker.generator.po.SupportExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SupportMapper {
    long countByExample(SupportExample example);

    int deleteByExample(SupportExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Support record);

    int insertSelective(Support record);

    List<Support> selectByExample(SupportExample example);

    Support selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Support record, @Param("example") SupportExample example);

    int updateByExample(@Param("record") Support record, @Param("example") SupportExample example);

    int updateByPrimaryKeySelective(Support record);

    int updateByPrimaryKey(Support record);
}