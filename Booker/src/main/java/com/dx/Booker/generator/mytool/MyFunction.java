package com.dx.Booker.generator.mytool;

import com.dx.Booker.generator.po.User;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import javax.servlet.http.Cookie;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class MyFunction {
public Cookie AboutUserCookie(Cookie[] cookies,User user){
    String jsonstring = null;
    JSONArray jsonArray = null;
    List<User> list = new ArrayList<>();
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
    //如果没有usercookie
    if (jsonstring != null) {
        jsonArray = JSONArray.fromObject(jsonstring);
        list = JSONArray.toList(jsonArray, new User(), new JsonConfig());
        for (int i = 0; i < list.size(); i++) {
            User isUserExist = list.get(i);
            if (isUserExist.getEmail().equals(user.getEmail())) {
                list.remove(i);
            }
        }
    }
    jsonArray = JSONArray.fromObject(list);
    Cookie cookie = null;
    try {
        cookie = new Cookie("users", URLEncoder.encode(jsonArray.toString(), "utf-8"));
    } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
    }
    cookie.setMaxAge(60 * 60 * 24 * 10);
    cookie.setPath("/");
    return cookie;
}
}
