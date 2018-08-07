package com.dx.Booker.generator.mapper;

import com.dx.Booker.generator.extendPojo.order;
import com.dx.Booker.generator.po.Order;
import com.dx.Booker.generator.po.OrderExample;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface OrderMapper {
    List<order> order(Integer userId);

    List<order> orderLimit(Integer userId);
    Date time();
    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("INSERT into `order` (user_id,createtime,summoney,addressDetail,userName,phone) VALUES (#{userId},#{createtime},#{summoney},#{addressDetail},#{userName},#{phone})")
    public int insertOrder(order order);

    @Update("UPDATE `order` SET adminId=#{adminId},`condition`=#{condition} WHERE id=#{id}")
    public void handleOrder(Order order);

    @Update("UPDATE `order` SET `condition`=#{condition} WHERE id=#{id}")
    public void userHandleOrder(Order order);

    public List<order> ordersByCondition(String condition);

    long countByExample(OrderExample example);

    int deleteByExample(OrderExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    List<Order> selectByExample(OrderExample example);

    Order selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Order record, @Param("example") OrderExample example);

    int updateByExample(@Param("record") Order record, @Param("example") OrderExample example);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
}