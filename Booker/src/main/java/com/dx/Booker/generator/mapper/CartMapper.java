package com.dx.Booker.generator.mapper;


import com.dx.Booker.generator.extendPojo.BooksInCart;
import com.dx.Booker.generator.extendPojo.order;
import com.dx.Booker.generator.po.Cart;
import com.dx.Booker.generator.po.CartExample;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface CartMapper {

    @Update("update cart set number = #{number} where book_id = #{bookId} and user_id = #{userId}")
    public void updateBookNumberInCart(Cart cart);

    @Delete("delete from cart where book_id = #{bookId} and user_id=#{userId}")
    public void deleteCart(Cart cart);

    @Insert("insert into `cart` (user_id,book_id) values(#{userId},#{bookId})")
    public void insertCart(Cart cart);

    @Select("select * from `cart` where book_id=#{bookId} and user_id = #{userId}")
    public Cart selectCart(Cart cart);


    @Update("INSERT  INTO cart (user_id,book_id) VALUES (5,6)")
    public void test();

    public void deleteCartInOrder(order order);

    List<BooksInCart> booksInCart(Integer userId);
    long countByExample(CartExample example);

    int deleteByExample(CartExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Cart record);

    int insertSelective(Cart record);

    List<Cart> selectByExample(CartExample example);

    Cart selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Cart record, @Param("example") CartExample example);

    int updateByExample(@Param("record") Cart record, @Param("example") CartExample example);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);
}