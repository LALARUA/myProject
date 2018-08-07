package com.dx.Booker.service;

import com.dx.Booker.generator.extendPojo.*;
import com.dx.Booker.generator.mapper.*;
import com.dx.Booker.generator.mytool.MyFunction;
import com.dx.Booker.generator.po.*;

import com.dx.Booker.serviceinterface.UserService;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
/**
 * @description 用户操作
 * @author xiangXX
 * @date 2018/6/7 0012 10:40
  *
 *
 */
@Transactional
@Service
public class UserServiceImp implements UserService {
    @Autowired
    private LoginMapper loginMapper;
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderdetailMapper orderdetailMapper;
    @Autowired
    private UserAddressMapper userAddressMapper;
    @Autowired
    private UserCouponMapper userCouponMapper;
    @Autowired
    private BooksMapper booksMapper;
    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private CollectMapper collectMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private promotionMapper promotionMapper;
    @Autowired
    private LabelAndTagMapper labelAndTagMapper;
//
//
    @Override
    /**
     * @description 用户浏览购物车
     * @author xiangXX
     * @date 2018/6/12 0012 10:40
      * @param userId
     * @param model
     *
     */
    public void myCart(Integer userId,Model model) {
        UserAddress defaultAddress = userAddressMapper.findDefaultAddress(userId);
        List<UserAddress> userAddresses = userAddressMapper.userAddress(userId);
        List<BooksInCart> booksInCart = cartMapper.booksInCart(userId);
        List<couponDetail> couponDetails = userCouponMapper.userCoupons(userId);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (couponDetail coupon:couponDetails
             ) {
            String overtime = simpleDateFormat.format(coupon.getDateofacquisition());
            coupon.setOvertime(overtime);

        }
        Integer couponNumber = couponDetails.size();
        model.addAttribute("couponNumber",couponNumber);
        model.addAttribute("coupons",couponDetails);
        model.addAttribute("defaultAddress",defaultAddress);
        model.addAttribute("addresses",userAddresses);
        model.addAttribute("booksInCart",booksInCart);

        }
//
    @Override
    /**
     * @description 用户修改密码
     * @author xiangXX
     * @date 2018/6/12 0012 10:41
      * @param user
     * @param httpSession
     * @param cookies
     * @param httpServletResponse
     *
     */
    public void updatePassword(User user, HttpSession httpSession, Cookie[] cookies, HttpServletResponse httpServletResponse) {
        String hashAlgorithmName = "MD5";
        Object credentials = user.getPassword();
        Object salt = ByteSource.Util.bytes(user.getEmail());
        Object result = new SimpleHash(hashAlgorithmName, credentials, salt);
        user.setPassword(String.valueOf(result));
        loginMapper.updatePassword(user);
        httpSession.removeAttribute("verification");
        MyFunction myFunction = new MyFunction();
        httpServletResponse.addCookie( myFunction.AboutUserCookie(cookies,user));
        }
//
//
//
    @Override
    /**
     * @description 用户更新地址
     * @author xiangXX
     * @date 2018/6/12 0012 10:41
      * @param userAddress user地址信息
     *
     */
    public void updateAddress(UserAddress userAddress){
        if (userAddress.getStatus()==0){
            userAddressMapper.updateAddress(userAddress);
        }
        else {
            userAddressMapper.updateStatus(userAddress.getUserId());
            userAddressMapper.updateAddress(userAddress);
        }
    }


    @Override
    /**
     * @description 用户新增地址
     * @author xiangXX
     * @date 2018/6/12 0012 10:41
      * @param userAddress
     *
     */
    public void insertAddress(UserAddress userAddress) {
        if (userAddress.getStatus()==0){
            userAddressMapper.insertAddress(userAddress);
        }
        else {
            userAddressMapper.updateStatus(userAddress.getUserId());
            userAddressMapper.insertAddress(userAddress);
        }
    }
//
    @Override
    /**
     * @description 用户删除地址
     * @author xiangXX
     * @date 2018/6/12 0012 10:42
      * @param id
     *
     */
    public void deleteAddress(Integer id) {
        userAddressMapper.deleteAddress(id);
    }

    @Override
    /**
     * @description 用户查看消息
     * @author xiangXX
     * @date 2018/6/12 0012 10:42
      * @param userId
     *
     */
    public List<MessagePro> myMessage(Integer userId) {
        List<MessagePro> messages = messageMapper.messages(userId);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (MessagePro messagePro:messages
             ) {
            Date date = messagePro.getDate();
            String format = simpleDateFormat.format(date);
            messagePro.setShowDate(format);
        }
        return messages;
    }

    @Override
    /**
     * @description 用户处理订单
     * @author xiangXX
     * @date 2018/6/12 0012 10:42
      * @param order
     * @param way
     *
     */
    public void userHandleOrder(Order order, String way){
        if (way.equals("确认订单"))
            order.setCondition("订单完成");
        else if(way.equals("申请退款"))
              order.setCondition("退款审核中");
        else if (way.equals("申请退货"))
            order.setCondition("退货审核中");
        else if (way.equals("退货发送"))
            order.setCondition("退货中");
        orderMapper.userHandleOrder(order);
    }

    @Override
    /**
     * @description 用户优惠券
     * @author xiangXX
     * @date 2018/6/12 0012 10:43
      * @param id
     *
     */
    public List<couponDetail> myCoupon(Integer id) {
        List<couponDetail> couponDetails = userCouponMapper.userCoupons(id);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (couponDetail coupon:couponDetails
                ) {
            String receiveTime = simpleDateFormat.format(coupon.getDateofacquisition());
            coupon.setReceiveTime(receiveTime);
            }
        return couponDetails;
    }

    @Override
    /**
     * @description 用户添加收藏夹
     * @author xiangXX
     * @date 2018/6/12 0012 10:43
      * @param collect
     *
     */
    public void addNewFavorite(Collect collect) {
        collectMapper.addNewFavorite(collect);
    }

    @Override
    /**
     * @description 用户所有收藏夹
     * @author xiangXX
     * @date 2018/6/12 0012 10:43
      * @param userId
     *
     */
    public List<Collect> myCollect(Integer userId) {
        List<Collect> collects = collectMapper.userCollect(userId);
        return collects;
    }

    @Override
    /**
     * @description 用户浏览收藏夹下的收藏
     * @author xiangXX
     * @date 2018/6/12 0012 10:43
      * @param collectionId
     *
     */
    public List<BooksInCollection> selectBooksInCollect(Integer collectionId) {
       return booksMapper.selectBooksInCollect(collectionId);

    }

    @Override
    /**
     * @description 删除收藏夹里面的书
     * @author xiangXX
     * @date 2018/6/12 0012 10:44
      * @param collectDetailId
     *
     */
    public void deleteBookInCollect(Integer collectDetailId) {
        collectMapper.deleteBookInCollect(collectDetailId);

    }

    @Override
    /**
     * @description 删除收藏夹
     * @author xiangXX
     * @date 2018/6/12 0012 10:44
      * @param collectionId
     *
     */
    public void deleteCollection(Integer collectionId) {
        collectMapper.deleteCollection(collectionId);
    }

    @Override
    /**
     * @description 添加book进收藏夹
     * @author xiangXX
     * @date 2018/6/12 0012 10:44
      * @param collectDetail
     *
     */
    public void addBookInCollection(CollectDetail collectDetail) {
        List<CollectDetail> collectDetails = collectMapper.selectBookInCollection(collectDetail);
        if (collectDetails.size()==0)
        collectMapper.addBookInCollection(collectDetail);
    }

    @Override
    /**
     * @description 添加book到新收藏夹
     * @author xiangXX
     * @date 2018/6/12 0012 10:45
      * @param collect
     * @param collectDetail
     *
     */
    public void addNewCollectionAndBook(Collect collect, CollectDetail collectDetail) {
        collectMapper.addNewFavorite(collect);
        Integer collectId = collect.getId();
        collectDetail.setCollectId(collectId);
        collectMapper.addBookInCollection(collectDetail);

    }

    @Override
    /**
     * @description 更新用户信息
     * @author xiangXX
     * @date 2018/6/12 0012 10:45
      * @param updateUser
     *
     */
    public void updateUser(UpdateUser updateUser) {
        List<String> loves = updateUser.getLoves();
        String userLoves = "";
        if (loves!=null){
            for (String love:loves
                    ) {userLoves = userLoves+"/"+love;
            }
            updateUser.setLove(userLoves);
        }

        userMapper.updateUser(updateUser);
    }

    @Override
    /**
     * @description 更新用户头像
     * @author xiangXX
     * @date 2018/6/12 0012 10:45
      * @param user
     * @param image
     *
     */
    public void updateUserIcon(User user, MultipartFile image) {
        //存储图片的物理地址
        String pic_path="/opt/tomcat/";

        String oldIconImage = pic_path+user.getIcon();
        File oldIcon = new File(oldIconImage);
        if (oldIcon.exists())
            oldIcon.delete();
        //原始名称
        String originalFilename= image.getOriginalFilename();
        //新的文件名
        String newfilename= UUID.randomUUID()+originalFilename.substring(originalFilename.lastIndexOf("."));
        //新图片
        File file = new File(pic_path+"userIcon/"+newfilename);
        //将内存中的图片写入磁盘
        try {
            image.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
            e.printStackTrace();
        }
        //将新图片的名称写入book中
        user.setIcon("/userIcon/"+newfilename);
        userMapper.updateUserIcon(user);
    }

    @Override
    /**
     * @description 用户获得优惠券
     * @author xiangXX
     * @date 2018/6/12 0012 12:28
      * @param coupon
     *
     */
    public void userGetCoupon(Coupon coupon) {
        coupon.setDateofacquisition(new Date());
        userCouponMapper.insertCoupon(coupon);
        promotionMapper.insertPromotion(coupon);
    }

    @Override
    public void registerAdmin(Integer userId) {
        userMapper.registerAdmin(userId);

    }

    @Override
    public List<Boolean> isGetCoupon(Integer userId) {
        ArrayList<Boolean> booleans = new ArrayList<>();
        promotion promotion = new promotion();
        promotion.setUserId(userId);
        for (int i=1;i<4;i++){
            promotion.setTypeId(i);
            if (promotionMapper.selectPromotion(promotion)!=null)
                booleans.add(true);
            else booleans.add(false);

        }
        return booleans;
    }


    @Override
    public void test() {
        cartMapper.test();
        int a=0/0;

    }
//
//
    @Override
    /**
     * @description 用户提交订单
     * @author xiangXX
     * @date 2018/6/12 0012 10:46
      * @param order
     *
     */
    public String insertOrder(order order) {
            Date date = new Date();
            order.setCreatetime(date);

//			 插入订单
			 orderMapper.insertOrder(order);
//			 删除用掉的优惠券
			 cartMapper.deleteCartInOrder(order);
        Integer couponId = order.getCouponId();
        if (couponId!=0){
            userCouponMapper.deleteCoupon(couponId);
        }
        List<orderDetail> orderDetails = order.getOrderDetails();

//        循环插入订单明细
            for (orderDetail orderDetail: orderDetails
                    ) {orderDetail.setOrderId(order.getId());
                Books book = orderDetail.getBook();
                Integer booknum = orderDetail.getBooknum();
                Integer bookId = book.getId();
                book = booksMapper.selectByPrimaryKey(bookId);
                Integer stock = book.getStock();
                if (stock<booknum){
//                    如果库存不够则事务回滚
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return "库存不够";
                }
                Integer sales = book.getSales();
                book.setSales(sales+booknum);
                book.setStock(stock-booknum);
                booksMapper.updateBookSales(book);
                Double price = book.getPrice();
                double orderDetailSummoney = booknum * price;
                orderDetail.setSummoney(orderDetailSummoney);
                orderdetailMapper.insertOrderDetail(orderDetail);
                }
                return "下单成功";
        }
//
//
//
//
    @Override
    /**
     * @description 用户浏览订单
     * @author xiangXX
     * @date 2018/6/12 0012 10:47
      * @param userId
     *
     */
    public List<order> myOrder(Integer userId) {
        List<order> orders = orderMapper.order(userId);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (order order:orders
                ) {Date createtime = order.getCreatetime();
            order.setDatetime(simpleDateFormat.format(createtime));

        }
        return orders;
    }

    @Override
    /**
     * @description 用户个人中心
     * @author xiangXX
     * @date 2018/6/12 0012 10:47
      * @param model
     * @param userId
     *
     */
    public void myUser(Model model,Integer userId) {
        List<UserAddress> userAddresses = userAddressMapper.userAddress(userId);
        List<order> orders = orderMapper.orderLimit(userId);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (order order:orders
                ) {Date createtime = order.getCreatetime();
            order.setDatetime(simpleDateFormat.format(createtime));

        }
        List<tag> tags = labelAndTagMapper.selectTags();
        model.addAttribute("tags",tags);
        model.addAttribute("orders",orders);
        model.addAttribute("addresses",userAddresses);



    }
}
