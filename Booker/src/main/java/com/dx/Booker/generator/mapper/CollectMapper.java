package com.dx.Booker.generator.mapper;

import com.dx.Booker.generator.po.Collect;
import com.dx.Booker.generator.po.CollectDetail;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: xiangXX
 * @Description:
 * @Date Created in 14:01 2018/6/8 0008
 */

public interface CollectMapper {

    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("insert into collect (userId,name) values(#{userId},#{name})")
    public int addNewFavorite(Collect collect);
    @Select("select * from collect where userId = #{userId}")
    public List<Collect> userCollect(Integer userId);

    @Delete("delete from collectdetail where id = #{collectDetailId}")
    public void deleteBookInCollect(Integer collectDetailId);

    @Delete("delete from collect where id = #{collectionId}")
    public void deleteCollection(Integer collectionId);

    @Insert("insert into collectdetail (bookId,collectId) values(#{bookId},#{collectId})")
    public void addBookInCollection(CollectDetail collectDetail);

    @Select("select * from collectdetail where collectId=#{collectId} and bookId = #{bookId}")
    public List<CollectDetail> selectBookInCollection(CollectDetail collectDetail);
}
