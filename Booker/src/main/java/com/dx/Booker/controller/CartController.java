package com.dx.Booker.controller;

import com.dx.Booker.generator.po.Cart;
import com.dx.Booker.serviceinterface.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
   private CartService cartService;

    @ResponseBody
    @RequestMapping("/addBookInCart")
    /**
     * @description 添加Book到购物车
     * @author xiangXX
     * @date 2018/6/15 0015 12:56
      * @param cart 购物车明细信息 
     * 
     */  
    public Map addBookInCart(Cart cart){
        HashMap<Object, Object> data = new HashMap<>();
        String info=null;
        try {
            cartService.insertCart(cart);
            info = "已加入购物车";
        } catch (Exception e) {
            info = "操作失败";
            e.printStackTrace();
        }
        data.put("info",info);
        return data;
    }
    @ResponseBody
    @RequestMapping("/updateBookNumberInCart")
    /**
     * @description 更新购物车明细信息，比如增减单本书的数量
     * @author xiangXX
     * @date 2018/6/15 0015 12:57
      * @param cart 购物车明细信息
     *
     */
    public Map updateBookNumberInCart(Cart cart){
        HashMap<String, String> info = new HashMap<>();
        cartService.updateBookInCart(cart);
        return info;
    }
    @ResponseBody
    @RequestMapping("/deleteBookInCart")
    /**
     * @description 删除购物车明细(删除购物车里面的一种书)
     * @author xiangXX
     * @date 2018/6/15 0015 12:57
      * @param cart 购物车明细信息
     *
     */
    public Map deleteBookInCart(Cart cart){
        HashMap<Object, Object> data = new HashMap<>();
        String info=null;
        try {
            cartService.deleteCart(cart);
            info = "删除成功";
        } catch (Exception e) {
            info = "删除失败";
            e.printStackTrace();
        }
        data.put("info",info);
        return data;
    }
}
