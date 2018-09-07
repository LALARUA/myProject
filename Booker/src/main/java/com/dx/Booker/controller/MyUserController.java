package com.dx.Booker.controller;

import com.dx.Booker.exception.OrderException;
import com.dx.Booker.generator.extendPojo.*;
import com.dx.Booker.generator.po.*;
import com.dx.Booker.serviceinterface.LoginService;
import com.dx.Booker.serviceinterface.UserService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class MyUserController {
    @Autowired
    private UserService userService;
    @Autowired
    private LoginService loginService;

    @RequestMapping("/myUser")
    /**
     * @description 个人中心
     * @author xiangXX
     * @date 2018/6/15 0015 13:05
      * @param model
     * @param httpSession
     *
     */
    public String myUser(Model model,HttpSession httpSession) {
        User user =(User) httpSession.getAttribute("user");
        String email = user.getEmail();
        user = loginService.isRegister(email);
        httpSession.setAttribute("user",user);
        userService.myUser(model,user.getId());
        return "myUser";
    }

    @RequestMapping("/myMessage")
    /**
     * @description 会员查看消息
     * @author xiangXX
     * @date 2018/6/15 0015 13:05
      * @param httpSession
     * @param model
     *
     */
    public String myMessage(HttpSession httpSession,Model model){
        User user = (User) httpSession.getAttribute("user");
        Integer userId = user.getId();
        List<MessagePro> messages = userService.myMessage(userId);
        model.addAttribute("messages",messages);
        return "myMessage";


    }
    @RequestMapping("/submitOrder")
    /**
     * @description 会员提交订单
     * @author xiangXX
     * @date 2018/6/15 0015 13:05
      * @param order
     * @param httpServletRequest
     *
     */
    public String submitOrder(order order,HttpServletRequest httpServletRequest){
        String referer = httpServletRequest.getHeader("referer");
        if (referer == null)
            return "XX";

        String info = userService.insertOrder(order);
        if (info.equals("下单成功")){

            return "forward:/user/myOrder";
        }
        else if (info.equals("库存不够")){
            throw new OrderException("订单提交失败，你手慢咯，被抢光了");
        }
        return "forward:/user/myOrder";

    }
    @RequestMapping("/myOrder")
    /**
     * @description 会员查看订单
     * @author xiangXX
     * @date 2018/6/15 0015 13:05
      * @param httpSession
     * @param model
     *
     */
    public String myOrder(HttpSession httpSession,Model model){
        User user = (User) httpSession.getAttribute("user");
        List<order> orders = userService.myOrder(user.getId());
        model.addAttribute("orders",orders);
        return "myOrder";
    }
    @ResponseBody
    @RequestMapping("/handleOrder")
    /**
     * @description 会员处理订单
     * @author xiangXX
     * @date 2018/6/15 0015 13:06
      * @param order
     * @param way
     *
     */
    public Map userHandleOrder(Order order,String way){
        HashMap<Object, Object> data = new HashMap<>();
        String info = null;

        try {
            userService.userHandleOrder(order,way);
            info = "操作成功";
        } catch (Exception e) {
            info = "操作失败";
            e.printStackTrace();
        }
        data.put("info",info);
        return data;
    }
    @ResponseBody
    @RequestMapping("/getCoupon")
    /**
     * @description 会员获得优惠券
     * @author xiangXX
     * @date 2018/6/15 0015 13:06
      * @param coupon
     *
     */
    public Map userGetCoupon(Coupon coupon){
        HashMap<Object, Object> data = new HashMap<>();
        String info = null;

        try {
            userService.userGetCoupon(coupon);
            info = "领取成功";
        } catch (Exception e) {
            info = "领取失败";
            e.printStackTrace();
        }
        data.put("info",info);
        return data;
    }
    @GetMapping("/myCoupon/{userId}")
    /**
     * @description 会员查看优惠券
     * @author xiangXX
     * @date 2018/6/15 0015 13:06
      * @param userId
     * @param model
     * @param httpServletRequest
     *
     */
    public String myCoupon(@PathVariable("userId") Integer userId,Model model,HttpServletRequest httpServletRequest){
        String referer = httpServletRequest.getHeader("referer");
        if (referer == null)
            return "XX";
        List<couponDetail> couponDetails = userService.myCoupon(userId);
        model.addAttribute("coupons",couponDetails);
        return "myCoupon";
    }
    @RequestMapping("/userGetCouponPage")
    /**
     * @description 领取优惠券页面
     * @author xiangXX
     * @date 2018/6/15 0015 13:06
      * @param httpSession
     * @param model
     *
     */
    public String userGetCouponPage(HttpSession httpSession,Model model){
        User user = (User)httpSession.getAttribute("user");
       model.addAttribute("isGetCoupon",userService.isGetCoupon(user.getId()));
        return "userGetCoupon";
    }
    @RequestMapping("/applyAdmin")
    /**
     * @description 申请管理员操作
     * @author xiangXX
     * @date 2018/6/15 0015 13:06
      * @param httpSession
     *
     */
    public String applyAdmin(HttpSession httpSession){
        User user = (User)httpSession.getAttribute("user");
        userService.registerAdmin(user.getId());
        user.setRole("admin");
        return "applyAdmin";

    }
    @GetMapping("/myCollect/{userId}/{collectionId}")
    /**
     * @description 个人收藏夹
     * @author xiangXX
     * @date 2018/6/15 0015 13:07
      * @param userId 会员Id
     * @param collectionId 收藏夹Id
     * @param model
     * @param httpServletRequest
     *
     */
    public String myCollect(@PathVariable("userId") Integer userId,@PathVariable("collectionId")Integer collectionId, Model model,HttpServletRequest httpServletRequest){
        String referer = httpServletRequest.getHeader("referer");
        if (referer == null)
            return "XX";
        List<BooksInCollection> books = new ArrayList<BooksInCollection>();


        if (!(collectionId==0)){
           books = userService.selectBooksInCollect(collectionId);
        }

        List<Collect> collects = userService.myCollect(userId);
        model.addAttribute("collects",collects);
        model.addAttribute("books",books);
        return "myCollection";
    }
    @RequestMapping("/editUser")
    /**
     * @description 编辑个人信息
     * @author xiangXX
     * @date 2018/6/15 0015 13:08
      * @param updateUser
     *
     */
    public String editUser(UpdateUser updateUser){
        userService.updateUser(updateUser);
        return "redirect:/user/myUser";
    }
    @RequestMapping("/editUserIcon")
    /**
     * @description 更换头像
     * @author xiangXX
     * @date 2018/6/15 0015 13:08
      * @param user
     * @param image
     *
     */
    public String editUserIcon(User user, MultipartFile image){
        userService.updateUserIcon(user,image);
        return "redirect:/user/myUser";
    }
    @ResponseBody
    @RequestMapping("/addBookInCollection")
    /**
     * @description 添加Book到收藏夹
     * @author xiangXX
     * @date 2018/6/15 0015 13:08
      * @param collectDetail
     *
     */
    public Map addBookInCollection(CollectDetail collectDetail){
        HashMap<Object, Object> data = new HashMap<>();
        String info = null;
        try {
            userService.addBookInCollection(collectDetail);
            info = "添加成功";
        } catch (Exception e) {
            info = "添加失败";
            e.printStackTrace();
        }
        data.put("info",info);
        return data;
        }
    @ResponseBody
    @RequestMapping("/addNewCollectionAndBook")
    /**
     * @description 添加Book到新添加的收藏夹下
     * @author xiangXX
     * @date 2018/6/15 0015 13:09
      * @param collect
     * @param collectDetail
     *
     */
    public Map addNewCollectionAndBook(Collect collect,CollectDetail collectDetail){
        HashMap<Object, Object> data = new HashMap<>();
        String info = null;
        try {
            userService.addNewCollectionAndBook(collect,collectDetail);
            info = "添加成功";
        } catch (Exception e) {
            info = "添加失败";
            e.printStackTrace();
        }
        data.put("info",info);
        return data;
    }
    @ResponseBody
    @RequestMapping("/addNewFavorite")
    /**
     * @description 添加收藏夹
     * @author xiangXX
     * @date 2018/6/15 0015 13:09
      * @param collect
     *
     */
    public Map addNewFavorite(Collect collect){
        HashMap<Object, Object> data = new HashMap<>();
        String info = null;
        try {
            userService.addNewFavorite(collect);
            info = "创建成功";
        } catch (Exception e) {
            info = "创建失败";
            e.printStackTrace();
        }
        data.put("info",info);
        return data;

    }

    @RequestMapping("/deleteCollection/{userId}/{collectionId}")
    /**
     * @description 删除收藏夹
     * @author xiangXX
     * @date 2018/6/15 0015 13:09
      * @param collectionId 收藏夹I
     * @param userId
     *
     */
    public String deleteCollection(@PathVariable("collectionId") Integer collectionId,@PathVariable("userId")Integer userId){


            userService.deleteCollection(collectionId);


        return "redirect:/user/myCollect/"+userId+"/"+0;

    }
    @ResponseBody
    @RequestMapping("/deleteBookInCollect")
    /**
     * @description 删除收藏夹里面的书
     * @author xiangXX
     * @date 2018/6/15 0015 13:10
      * @param collectDetailId
     *
     */
    public Map deleteBookInCollect(Integer collectDetailId){
        HashMap<Object, Object> data = new HashMap<>();
        String info = null;
        try {
            userService.deleteBookInCollect(collectDetailId);
            info = "删除成功";
        } catch (Exception e) {
            info = "删除失败";
            e.printStackTrace();
        }
        data.put("info",info);
        return data;

    }
    @GetMapping("/myCart")
    /**
     * @description 个人购物车
     * @author xiangXX
     * @date 2018/6/15 0015 13:10
      * @param id
     * @param httpServletRequest
     * @param httpSession
     * @param model
     *
     */
    public String myCart(HttpServletRequest httpServletRequest,HttpSession httpSession, Model model){
//        String referer = httpServletRequest.getHeader("referer");
//        if (referer == null)
//            return "XX";

        User user = (User)httpSession.getAttribute("user");
        Integer id = user.getId();
        try {
            userService.myCart(id,model);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "myCart";
    }


//    @ResponseBody
//    @PostMapping("/myCart")
//    public Map buyBooks(Integer bookId,Integer in){
//        Map map = new HashMap();
//        String info = "添加成功";
//        map.put("info",info);
//        return map;
//    }
//    @ResponseBody
//    @DeleteMapping("/myCart/{bookId}")
//    public void deleteCart(@PathVariable("bookId") Integer id){
//
//    }
    @ResponseBody
    @RequestMapping("/address")
    /**
     * @description 个人收货地址
     * @author xiangXX
     * @date 2018/6/15 0015 13:10
      * @param userAddress
     * @param flag
     * @param httpSession
     *
     */
    public Map editAddress(UserAddress userAddress,Integer flag,HttpSession httpSession){
        User user =(User) httpSession.getAttribute("user");
        userAddress.setUserId(user.getId());
        String info="操作失败";
        HashMap<Object, Object> HashMap = new HashMap<>();
        if (flag==0){
            //编辑
            userService.updateAddress(userAddress);
            info="修改成功";

        }
        else if (flag==1){
            //添加
            userService.insertAddress(userAddress);
            info="添加成功";
        }
        else if (flag==2){
            //删除
            userService.deleteAddress(userAddress.getId());
            info="删除成功";

        }
        HashMap.put("info",info);
return HashMap;
    }

    @ResponseBody
    @RequestMapping("/updatePassword")
    /**
     * @description 修改密码
     * @author xiangXX
     * @date 2018/6/15 0015 13:10
      * @param verification 验证码
     * @param user 新修改的user信息
     * @param httpSession
     * @param httpServletResponse
     * @param httpServletRequest
     *
     */
    public Map updatePassword(String verification,User user, HttpSession httpSession, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        String rel = String.valueOf(httpSession.getAttribute("verification"));
        String info = null;
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        if (verification.equals(rel)) {

            Cookie[] cookies = httpServletRequest.getCookies();

            try {
                userService.updatePassword(user, httpSession, cookies, httpServletResponse);
            } catch (Exception e) {
                info = "修改密码失败";
            }
            info = "修改密码成功";
            }
        else info="验证码错误";
        objectObjectHashMap.put("info",info);

        return objectObjectHashMap;
    }

    @GetMapping("/userBrowseOtherUserPage/{userId}")
    public String userBrowseOtherUserPage(@PathVariable("userId") String userId,Model model){

        return null;
    }

}
