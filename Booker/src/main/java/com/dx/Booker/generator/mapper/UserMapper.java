package com.dx.Booker.generator.mapper;

import com.dx.Booker.generator.extendPojo.UserGetOtherUserInformation;
import com.dx.Booker.generator.po.User;
import com.dx.Booker.generator.po.UserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import javax.jws.soap.SOAPBinding;

public interface UserMapper {
    @Update("update user set username = #{username},sex=#{sex},love=#{love} where id = #{id}")
    public void updateUser(User user);

    @Update("update user set icon = #{icon} where id = #{id}")
    public void updateUserIcon(User user);

    @Update("update user set role = 'admin' where id = #{userId}")
    public void registerAdmin(Integer userId);

    public UserGetOtherUserInformation UserGetOtherUserInformation(Integer userId);

    long countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}