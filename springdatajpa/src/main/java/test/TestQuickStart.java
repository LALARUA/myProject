package test;

import dao.userDao;
import entity.user;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.SimpleDateFormat;

public class TestQuickStart {
    private static ApplicationContext ctx;
    static {
        ctx = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
    }
    @Test
    public void testGetById(){
        userDao userDao = ctx.getBean(userDao.class);
        user user1 = userDao.getById(1);
        user zx = userDao.findByIdAndName(1, "zx");
        System.out.println(zx);
    }
}
