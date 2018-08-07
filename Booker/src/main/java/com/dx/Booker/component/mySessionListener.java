package com.dx.Booker.component;

import com.dx.Booker.generator.po.User;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashMap;
/**
 * @description session监听器
 * @author xiangXX
 * @date 2018/6/17 0017 10:10
  * @param null
 *
 */
@WebListener
public class mySessionListener implements HttpSessionListener {
    public mySessionListener() {
        super();
    }

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();

    }
/**
 * @description 用户退出后servletContext监听器会删除该用户数据
 * @author xiangXX
 * @date 2018/6/17 0017 10:11
  * @param null
 *
 */
    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {

        User user = (User) httpSessionEvent.getSession().getAttribute("user");
        if (user != null) {
            HashMap<String, User> users = (HashMap<String, User>) httpSessionEvent.getSession().getServletContext().getAttribute("users");
            users.remove(user.getEmail());
        }

    }
}
