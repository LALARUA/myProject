package com.dx.Booker.generator.mapper;

import com.dx.Booker.generator.extendPojo.commentAndSupport;
import com.dx.Booker.generator.extendPojo.supportMessage;
import com.dx.Booker.generator.po.Support;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;


public interface SupportMapper {
    @Insert("insert into `support` (userId,commentId) values(#{userId},#{commentId})")
    public void addSupport(supportMessage supportMessage);

    @Select("SELECT COUNT(*) FROM support,`comment` WHERE `comment`.id = support.commentId AND support.userId=#{userBrowseId} AND `comment`.id=#{id}")
    public Integer isUserSupport(commentAndSupport commentAndSupport);

    @Select("SELECT COUNT(*) FROM support,`comment` WHERE `comment`.id = support.commentId AND `comment`.id=#{id}")
    public Integer commentSupports(Integer commentId);


}
