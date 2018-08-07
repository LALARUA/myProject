package com.dx.Booker.generator.mapper;

import com.dx.Booker.generator.extendPojo.UserAddress;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

public interface UserAddressMapper {

    @Select("select * from address where userId = #{userId} order by status desc")
    public List<UserAddress> userAddress(Integer userId);

    @Update("update address set addressDetail=#{addressDetail},status=#{status},phone=#{phone},userName=#{userName},addressType=#{addressType} where id=#{id}")
    public void updateAddress(UserAddress userAddress);

    @Update("update address set status=0 where userId = #{userId}")
    public void updateStatus(Integer userId);

    @Insert("INSERT into `address` (addressDetail,status,phone,userName,addressType,userId) VALUES (#{addressDetail},#{status},#{phone},#{userName},#{addressType},#{userId})")
    public void insertAddress(UserAddress userAddress);

    @Delete("delete from `address` where id=#{id}")
    public void deleteAddress(Integer id);

    @Select("select * from address where userId = #{userId} and status=1")
    public UserAddress findDefaultAddress(Integer userId);

    @Select("select * from address where userId = #{userId} and status=0")
    public List<UserAddress> findOtherAddress(Integer userId);
}
