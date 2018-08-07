package com.dx.Booker.generator.mapper;

import com.dx.Booker.generator.extendPojo.orderDetail;
import com.dx.Booker.generator.po.Orderdetail;
import com.dx.Booker.generator.po.OrderdetailExample;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface OrderdetailMapper {


    @Insert("INSERT into `orderdetail` (order_id,book_id,booknum,summoney) VALUES (#{orderId},#{book.id},#{booknum},#{summoney})")
    public void insertOrderDetail(orderDetail orderDetail);
    long countByExample(OrderdetailExample example);

    int deleteByExample(OrderdetailExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Orderdetail record);

    int insertSelective(Orderdetail record);

    List<Orderdetail> selectByExample(OrderdetailExample example);

    Orderdetail selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Orderdetail record, @Param("example") OrderdetailExample example);

    int updateByExample(@Param("record") Orderdetail record, @Param("example") OrderdetailExample example);

    int updateByPrimaryKeySelective(Orderdetail record);

    int updateByPrimaryKey(Orderdetail record);
}