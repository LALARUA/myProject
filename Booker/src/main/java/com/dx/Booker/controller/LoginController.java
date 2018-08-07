package com.dx.Booker.controller;


//import com.dx.Booker.email.LoginDao;

import com.dx.Booker.generator.extendPojo.allTags;
import com.dx.Booker.generator.extendPojo.order;
import com.dx.Booker.generator.po.*;
import com.dx.Booker.pojo.ErrorMessage;

import com.dx.Booker.serviceinterface.BookService;
import com.dx.Booker.serviceinterface.LoginService;
import com.dx.Booker.serviceinterface.UserService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;

    @RequestMapping("/")
    /**
     * @description 主页
     * @author xiangXX
     * @date 2018/6/15 0015 12:59
      * @param httpSession
     *
     */
    public String userInSession(HttpSession httpSession) {
        User userInSession = (User) httpSession.getAttribute("user");
        if (userInSession != null) {
            return "redirect:/homepage";
        } else return "login";
    }

    @RequestMapping("/homepage")
    /**
     * @description 主页 主要有销量最高和最新上架的图书信息
     * @author xiangXX
     * @date 2018/6/15 0015 12:59
      * @param model
     * @param httpSession
     *
     */
    public String homepage(Integer i,Model model,HttpSession httpSession,HttpServletRequest httpServletRequest){
        User user = (User) httpSession.getAttribute("user");
        Map bookMap = bookService.BooksInHomepage();
        List<Books> homepagePutaway = (List<Books>) bookMap.get("homepagePutaway");
        List<Books> homepageSales =(List<Books>) bookMap.get("homepageSales");
        List<allTags> labels = (List<allTags>)bookMap.get("labels");
List<tag> hotTags = (List<tag>)bookMap.get("hotTags");
        List<Collect> collects = new ArrayList<>();
        if (user!=null)
            collects = userService.myCollect(user.getId());
        model.addAttribute("collects",collects);
        model.addAttribute("homepagePutaway",homepagePutaway);
        model.addAttribute("homepageSales",homepageSales);
        httpSession.setAttribute("labels",labels);
        httpSession.setAttribute("hotTags",hotTags);
        return "homepage";
    }
    @PostMapping("/login")
    /**
     * @description 会员登录
     * @author xiangXX
     * @date 2018/6/15 0015 13:00
      * @param httpSession
     * @param httpServletRequest
     * @param httpServletResponse
     * @param errorMessage 错误信息
     * @param remember 是否记住我
     * @param user user登录信息
     *
     */
    public String Login(HttpSession httpSession, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                        ErrorMessage errorMessage, String remember,
                        User user, Model model) {


        User userInSession = (User) httpSession.getAttribute("user");
        if (userInSession != null && user.getEmail().equals(userInSession.getEmail()))
            return "redirect:/homepage";
        HashMap<String, User> users = (HashMap<String, User>) httpSession.getServletContext().getAttribute("users");
        httpSession = httpServletRequest.getSession();
        Cookie[] cookies = httpServletRequest.getCookies();
        return loginService.login(user, httpSession, users, remember, httpServletResponse, cookies,errorMessage);

    }



    @ResponseBody
    @RequestMapping("/getCookieUser")
    /**
     * @description 得到userCookie
     * @author xiangXX
     * @date 2018/6/15 0015 13:00
      * @param email 用户的email
     * @param httpServletRequest 
     * 
     */  
    public Map getCookieUser(String email, HttpServletRequest httpServletRequest) {
        Cookie[] cookies = httpServletRequest.getCookies();

        return loginService.getUserCookie(cookies,email);
        }

    @PostMapping("/register")
    /**
     * @description 游客注册
     * @author xiangXX
     * @date 2018/6/15 0015 13:01
      * @param user 游客的注册信息
     * @param httpServletResponse
     * @param httpSession
     * @param httpServletRequest
     * @param verification
     * @param errorMessage 
     * 
     */
    public String register(User user, HttpServletResponse httpServletResponse, HttpSession httpSession, HttpServletRequest httpServletRequest, String verification, ErrorMessage errorMessage) {


      return loginService.register(user,verification,httpSession,errorMessage,httpServletRequest,httpServletResponse);

    }

    @ResponseBody
    @RequestMapping("/editUserMessage")
    public Map editUserMessage(User user, int flag) {

        return null;
    }


    @ResponseBody
    @RequestMapping("/isRegister")
    /**
     * @description 判断用户是否注册
     * @author xiangXX
     * @date 2018/6/15 0015 13:02
      * @param email 输入的email信息
     * @param errmessage 
     * 
     */  
    public Map isRegister(String email, ErrorMessage errmessage) {
        HashMap hashMap = new HashMap();
        String err = "该邮箱已经注册";
        String color = "red";
        if (errmessage.getFlag().equals("2")) {
            err = "该邮箱已经注册";
            color = "red";
            if (loginService.isRegister(email) == null) {
                err = "该邮箱可以注册";
                color = "green";
            }
            hashMap.put("err", err);
            hashMap.put("color", color);
            return hashMap;
        } else if (errmessage.getFlag().equals("3")) {

            err = "该邮箱未被注册";
            color = "red";
            if (loginService.isRegister(email) != null) {
                err = "";
                color = "green";
            }
            hashMap.put("err", err);
            hashMap.put("color", color);
            return hashMap;
        } else {
            hashMap.put("err", "操作错误");
            hashMap.put("color", "red");
            return hashMap;
        }
    }


    @ResponseBody
    @RequestMapping("/confirmEmail")
    /**
     * @description 验证邮箱
     * @author xiangXX
     * @date 2018/6/15 0015 13:02
      * @param email 输入的邮箱
     * @param httpSession
     * @param flag 前台传来的标志码，用于前台判断显示什么信息
     * 
     */  
    public Map confirmEmail(String email, HttpSession httpSession, int flag) {
        HashMap hashMap = new HashMap();
        try {
            Integer CAPTCHA = new Random().nextInt(9000) + 1000;
            httpSession.setAttribute("verification", CAPTCHA);
            httpSession.setMaxInactiveInterval(60 * 5);
            loginService.sendEmail(email, CAPTCHA, flag);
        } catch (MailException e) {
            java.lang.String s = new java.lang.String("发送验证码失败");
            hashMap.put("err", s);
        }
        return hashMap;
    }

}
