package com.dx.Booker.serviceinterface;

import com.dx.Booker.generator.po.User;
import com.dx.Booker.pojo.ErrorMessage;
import net.sf.json.JSONArray;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

public interface LoginService {
    public void sendEmail(String email, Integer CAPTCHA, int flag);
    public User isRegister(String email);
    public Cookie saveUserCookie(Cookie[] cookies,User user) throws Exception;
    public HashMap getUserCookie(Cookie[] cookies,String email);
    public String login(User user, HttpSession httpSession, HashMap users, String remember, HttpServletResponse httpServletResponse, Cookie[] cookies, ErrorMessage errorMessage);
    public String register(User user, String verification, HttpSession httpSession, ErrorMessage errMessage, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse);
}
