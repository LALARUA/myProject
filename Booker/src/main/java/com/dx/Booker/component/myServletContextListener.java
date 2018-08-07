package com.dx.Booker.component;

import com.dx.Booker.generator.po.User;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.HashMap;
/**
 * @description servlet监听器
 * @author xiangXX
 * @date 2018/6/17 0017 10:11
  * @param null
 *
 */
@WebListener
public class myServletContextListener implements ServletContextListener {
    public myServletContextListener() {
        super();
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        HashMap<String, User> usersHashMap = new HashMap<String, User>();
        usersHashMap.put("0", null);
        servletContextEvent.getServletContext().setAttribute("users", usersHashMap);

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
