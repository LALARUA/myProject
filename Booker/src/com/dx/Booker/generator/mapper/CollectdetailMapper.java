package com.dx.Booker.generator.mapper;

import com.dx.Booker.generator.po.Collectdetail;
import com.dx.Booker.generator.po.CollectdetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CollectdetailMapper {
    long countByExample(CollectdetailExample example);

    int deleteByExample(CollectdetailExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Collectdetail record);

    int insertSelective(Collectdetail record);

    List<Collectdetail> selectByExample(CollectdetailExample example);

    Collectdetail selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Collectdetail record, @Param("example") CollectdetailExample example);

    int updateByExample(@Param("record") Collectdetail record, @Param("example") CollectdetailExample example);

    int updateByPrimaryKeySelective(Collectdetail record);

    int updateByPrimaryKey(Collectdetail record);
}