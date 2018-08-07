package com.dx.Booker.generator.mapper;

import com.dx.Booker.generator.extendPojo.allTags;
import com.dx.Booker.generator.extendPojo.handleTag;
import com.dx.Booker.generator.po.label;
import com.dx.Booker.generator.po.tag;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author: xiangXX
 * @Description:
 * @Date Created in 14:31 2018/6/19 0019
 */
public interface LabelAndTagMapper {
    public List<allTags> selectAllTags();

    @Select("select * from tag")
    public List<tag> selectTags();

    @Insert("insert into tag (name,labelId) values(#{tagName},#{id})")
    public void insertNewTag(handleTag handleTag);

    @Update("update tag set status=#{status}")
    public void setStatus(Integer status);

    public void updateHotTag(List<Integer> tags);

    @Select("select * from tag where status=#{status}")
    public List<tag> selectHotTags(Integer status);

    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("insert into label (name) values(#{name})")
    public void insertNewLabel(handleTag handleTag);

    @Delete("delete from label where id = #{id}")
    public void deleteLabel(handleTag handleTag);

    @Delete("delete from tag where id = #{tagId}")
    public void deleteTag(handleTag handleTag);

    @Update("update label set name = #{name} where id =#{id}")
    public void updateLabel(handleTag handleTag);

    @Update("update tag set name = #{tagName},labelId=#{id} where id=#{tagId}")
    public void updateTag(handleTag handleTag);



}
