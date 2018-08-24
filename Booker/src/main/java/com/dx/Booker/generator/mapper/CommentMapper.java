package com.dx.Booker.generator.mapper;

import com.dx.Booker.generator.extendPojo.commentAndSupport;
import com.dx.Booker.generator.po.Comment;
import com.dx.Booker.generator.po.CommentExample;

import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface CommentMapper {
    @Select("select * from comment where bookId=#{bookId} and userId = #{userId}")
    public Comment userCommentInBook(Comment comment);

    List<commentAndSupport> commentsOfBook(HashMap hashMap);


    long countByExample(CommentExample example);

    int deleteByExample(CommentExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Comment record);

    int insertSelective(Comment record);

    List<Comment> selectByExample(CommentExample example);

    Comment selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Comment record, @Param("example") CommentExample example);

    int updateByExample(@Param("record") Comment record, @Param("example") CommentExample example);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);
}