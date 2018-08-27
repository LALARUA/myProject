package com.dx.Booker.generator.mapper;

import com.dx.Booker.generator.po.DynamicInformation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @program: Booker
 * @description: ${description}
 * @author: zhong.xiang
 * @create: 2018-08-27 14:57
 **/
@Mapper
public interface DynamicInformationMapper {

    @Select("select * from dynamicInformation where id = #{id} ")
    public DynamicInformation findDynamicIformationById(Integer id);
}
