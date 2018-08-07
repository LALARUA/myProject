package com.dx.Booker.service;

import com.dx.Booker.component.MailUtil;

import com.dx.Booker.generator.mapper.LabelAndTagMapper;
import com.dx.Booker.generator.mapper.LoginMapper;
import com.dx.Booker.generator.mapper.UserMapper;
import com.dx.Booker.generator.mytool.MyFunction;
import com.dx.Booker.generator.po.User;
import com.dx.Booker.pojo.ErrorMessage;
import com.dx.Booker.serviceinterface.LoginService;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * @author xiangXX
 * @description 关于登录的操作
 * @date 2018/6/12 0012 10:11
 */
@ConfigurationProperties(prefix = "myemail")
@Service
public class LoginServiceImp implements LoginService {

    private String registerMessage;
//@Autowired
//UserService userService;

    @Autowired
    private  LoginMapper loginMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private LabelAndTagMapper labelAndTagMapper;

    private String deliver;
    @Autowired
    private MailUtil mailUtil;

    public String getRegisterMessage() {
        return registerMessage;
    }

    public void setRegisterMessage(String message) {
        this.registerMessage = message;
    }

    public String getDeliver() {
        return deliver;
    }

    public void setDeliver(String deliver) {
        this.deliver = deliver;
    }

    @Override
    /**
     * @description 查出用户是否注册过
     * @author xiangXX
     * @date 2018/6/12 0012 10:12
     * @param email 用户email信息
     *
     */
    public User isRegister(String email) {
        return loginMapper.isRegister(email);


    }

    @Override
    /**
     * @description 给用户发送邮件确认信息
     * @author xiangXX
     * @date 2018/6/12 0012 10:12
     * @param email    用户邮件信息
     * @param CAPTCHA  验证码
     * @param flag     发送哪种邮件
     *
     */
    public void sendEmail(String email, Integer CAPTCHA, int flag) {
        String[] receiver = {email};
        String subject = "Booker 布客";
        String content = null;
        if (flag == 2)
            content = CAPTCHA + " " + registerMessage;
        else content = "修改密码验证码 " + CAPTCHA;
        try {
            mailUtil.sendSimpleEmail(deliver, receiver, null, subject, content);

        } catch (MailException e) {
            System.out.println(2);
            throw e;
        }
    }

    @Override
    /**
     * @description 保存用户Cookie信息
     * @author xiangXX
     * @date 2018/6/12 0012 10:14
     * @param cookies  用户的Cookies
     * @param user     用户信息
     *
     */
    public Cookie saveUserCookie(Cookie[] cookies, User user) throws Exception {
        String jsonstring = null;
        List<User> list = new ArrayList<User>();
        JSONArray jsonArray = null;
//          循环遍历找出key为users的cookie
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("users")) {
                    try {
                        jsonstring = URLDecoder.decode(cookie.getValue(), "utf-8");
                        break;
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        //如果没有userCookie
        if (jsonstring == null) {
            list.add(user);
        }
//        如果有userCookie
        else {
            jsonArray = JSONArray.fromObject(jsonstring);
            list = JSONArray.toList(jsonArray, new User(), new JsonConfig());
            Iterator iterator = list.iterator();
            boolean b = false; //判断json串中是否有user用户
            for (int i = 0; i < list.size(); i++) {
                User isUserExist = list.get(i);

//                如果cookie中保存了用户信息
                if (isUserExist.getEmail().equals(user.getEmail())) {

//                    如果用户信息的password不等于输入的password 意味着用户已经修改密码，所以需要更新cookie用户信息
                    if (!isUserExist.getPassword().equals(user.getPassword())) {
                        isUserExist.setPassword(user.getPassword());
                    }
                    b = true;
                    break;
                }
            }

//            如果cookie中没有用户信息，则需要保存用户信息
            if (b == false && list.size() == 10) {
                for (int i = 0; i < 9; i++) {
                    list.set(i, list.get(i + 1));
                }
                list.set(9, user);
            } else if (b == false) list.add(user);
        }

//        将list转为jsonArray
        jsonArray = JSONArray.fromObject(list);

//        将jsonArray.toString 即json串存入cookie中
        Cookie cookie = new Cookie("users", URLEncoder.encode(jsonArray.toString(), "utf-8"));
        cookie.setMaxAge(60 * 60 * 24 * 10);
        cookie.setPath("/");
        return cookie;
    }

    @Override
/**
 * @description 得到usersCookie用于自动获取用户保存的密码
 * @author xiangXX
 * @date 2018/6/12 0012 10:26
 * @param cookies  用户cookies
 * @param email    用户email
 *
 */
    public HashMap getUserCookie(Cookie[] cookies, String email) {
        String jsonstring = null;
//        循环遍历获取usersCookie
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("users")) {
                    try {
                        jsonstring = URLDecoder.decode(cookie.getValue(), "utf-8");
                        break;
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

//        如果没有key为users的cookie则返回null
        if (jsonstring == null) return null;


        JSONArray jsonArray;

//        将cookie中的json串转为jsonArray
        jsonArray = JSONArray.fromObject(jsonstring);
//        将jsonArray转为list
        List<User> list = new ArrayList<User>();
        list = JSONArray.toList(jsonArray, new User(), new JsonConfig());
        Iterator iterator = list.iterator();
        boolean b = false;  //用于判断usersCookie中是否有该用户信息
        User userisexist = null;

//        循环遍历list找出该user信息
        while (iterator.hasNext()) {
            userisexist = (User) iterator.next();
            if (userisexist.getEmail().equals(email)) {
                b = true;
                break;
            }
        }
        if (b == false) return null;

        HashMap hashMap = new HashMap();
        hashMap.put("email", email);
        hashMap.put("password", userisexist.getPassword());
        return hashMap;
    }

    @Override
    /**
     * @description 注册user或者找回密码
     * @author xiangXX
     * @date 2018/6/12 0012 10:30
     * @param user
     * @param verification 验证码
     * @param httpSession
     * @param errMessage   存储错误信息
     * @param httpServletRequest
     * @param httpServletResponse
     *
     */
    public String register(User user, String verification, HttpSession httpSession, ErrorMessage errMessage, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String rel = String.valueOf(httpSession.getAttribute("verification"));
        String email = user.getEmail();
        String view = null;
        if (verification.equals(rel)) {
//            注册用户
            if (errMessage.getFlag().equals("2")) {
//                密码加密方式为MD5
                String hashAlgorithmName = "MD5";
                Object credentials = user.getPassword();
//                密码盐为用户email
                Object salt = ByteSource.Util.bytes(user.getEmail());
                Object result = new SimpleHash(hashAlgorithmName, credentials, salt);
                user.setPassword(String.valueOf(result));
                user.setUsername(email);

//                数据库新增user
                userMapper.insertSelective(user);

//                将验证码从session中删除
                httpSession.removeAttribute("verification");

//                将user保存进session
//                httpSession.setAttribute("user", user);

//                将sessionId存入cookie30分钟，以便于用户没有退出用户就关闭浏览器后访问网页能直接进入
//                Cookie cookie = new Cookie("JSESSIONID", httpSession.getId());
//                cookie.setMaxAge(30 * 60);
//                cookie.setPath("/");
//                httpServletResponse.addCookie(cookie);
//                httpSession.setMaxInactiveInterval(30 * 60);
                return "forward:/login";
            }
//            找回密码
            if (errMessage.getFlag().equals("3")) {
                Cookie[] cookies = httpServletRequest.getCookies();
                try {
                    String hashAlgorithmName = "MD5";
                    Object credentials = user.getPassword();
                    Object salt = ByteSource.Util.bytes(user.getEmail());
                    Object result = new SimpleHash(hashAlgorithmName, credentials, salt);
                    user.setPassword(String.valueOf(result));
                    loginMapper.updatePassword(user);
                    httpSession.removeAttribute("verification");
                    MyFunction myFunction = new MyFunction();
                    httpServletResponse.addCookie(myFunction.AboutUserCookie(cookies, user));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                errMessage.setFlag("1");
                return "login";
            } else {
                errMessage.setErr("操作错误");
                return "login";
            }
        } else {
            errMessage.setErr("验证码错误");
            return "login";
        }

    }

    @Override
    /**
     * @description 登录操作
     * @author xiangXX
     * @date 2018/6/12 0012 10:36
     * @param user
     * @param httpSession
     * @param users    登录中的所有user信息，防止重复登录
     * @param remember 是否记住我
     * @param httpServletResponse
     * @param cookies
     * @param errorMessage     错误信息
     *
     */
    public String login(User user, HttpSession httpSession, HashMap users, String remember, HttpServletResponse httpServletResponse, Cookie[] cookies, ErrorMessage errorMessage) {

        String view = null;
        org.apache.shiro.subject.Subject currentUser = SecurityUtils.getSubject();
        if (!currentUser.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken(user.getEmail(), user.getPassword());
            try {
//                shiro处理登录
                currentUser.login(token);

//                判断该用户是否在登录状态
                if (users.containsKey(user.getEmail())) {
                    errorMessage.setErr("该用户在登录状态");
                    currentUser.logout();
                    return "login";
                }
                User userInDB = isRegister(user.getEmail());
                httpSession.setAttribute("user", userInDB);
                httpSession.setMaxInactiveInterval(30 * 60);
                Cookie cookie1 = new Cookie("JSESSIONID", httpSession.getId());
                cookie1.setMaxAge(30 * 60);
                cookie1.setPath("/");
                httpServletResponse.addCookie(cookie1);

//                将user放入登录中状态，即存入users
                users.put(user.getEmail(), user);
                if (remember != null && remember.equals("rem")) {
                    try {
                        httpServletResponse.addCookie(saveUserCookie(cookies, user));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (UnknownAccountException e) {
                errorMessage.setErr("该邮箱未注册");
                view = "login";

            } catch (AuthenticationException e) {
                errorMessage.setErr("用户名或密码错误");
                view = "login";
            }
        }

        if (view == null)
            view = "redirect:/homepage";
        return view;
    }

}
