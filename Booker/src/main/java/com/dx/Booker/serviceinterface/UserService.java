package com.dx.Booker.serviceinterface;

import com.dx.Booker.generator.extendPojo.*;
import com.dx.Booker.generator.po.*;
import net.sf.ehcache.search.expression.Or;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface UserService {
    public void myCart(Integer userId,Model model);
    public void updatePassword(User user, HttpSession httpSession, Cookie[] cookies, HttpServletResponse httpServletResponse);
    public String insertOrder(order order);
    public List<order> myOrder(Integer userId);
    public void myUser(Model model,Integer userId);
    public void updateAddress(UserAddress userAddress);
    public void insertAddress(UserAddress userAddress);
    public void deleteAddress(Integer id);
    public List<MessagePro> myMessage(Integer userId);
    public void userHandleOrder(Order order, String way);
    public List<couponDetail> myCoupon(Integer id);
    public void addNewFavorite(Collect collect);
    public List<Collect> myCollect(Integer userId);
    public List<BooksInCollection> selectBooksInCollect(Integer collectionId);
    public void deleteBookInCollect(Integer collectDetailId);
    public void deleteCollection(Integer collectionId);
    public void addBookInCollection(CollectDetail collectDetail);
    public void addNewCollectionAndBook(Collect collect,CollectDetail collectDetail);
    public void updateUser(UpdateUser updateUser);
    public void updateUserIcon(User user, MultipartFile multipartFile);
    public void userGetCoupon(Coupon coupon);
    public void registerAdmin(Integer userId);
    public List<Boolean> isGetCoupon(Integer userId);
    public UserGetOtherUserInformation UserGetOtherUserInformation(Integer userId);
    public void test();

}
