package com.dx.Booker.service;

import com.dx.Booker.generator.mapper.CartMapper;
import com.dx.Booker.generator.po.Cart;
import com.dx.Booker.serviceinterface.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * @description 购物车操作
 * @author xiangXX
 * @date 2018/6/03 0012 10:06
  *
 *
 */
@Transactional
@Service
public class CartServiceImp implements CartService {
    @Autowired
    private CartMapper cartMapper;


    @Override
    /**
     * @description 更新购物车明细数据  比如Book数量
     * @author xiangXX
     * @date 2018/6/03 0012 10:08
      * @param cart 购物车明细信息
     *
     */
    public void updateBookInCart(Cart cart) {
        cartMapper.updateBookNumberInCart(cart);
    }

    @Override
    /**
     * @description 插入购物车明细数据
     * @author xiangXX
     * @date 2018/6/12 0012 10:08
      * @param cart 购物车明细数据
     *
     */
    public void insertCart(Cart cart) {

        if (cartMapper.selectCart(cart)==null)
            cartMapper.insertCart(cart);
    }

    @Override
    /**
     * @description 删除购物车明细
     * @author xiangXX
     * @date 2018/6/12 0012 10:08
      * @param cart 要删除的购物车明细数据
     *
     */
    public void deleteCart(Cart cart) {
        cartMapper.deleteCart(cart);
    }

    @Override
    public void test() {
        cartMapper.test();
        int a=0/0;
    }
}
