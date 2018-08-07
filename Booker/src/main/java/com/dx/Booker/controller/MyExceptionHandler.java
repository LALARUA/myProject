package com.dx.Booker.controller;

import com.dx.Booker.exception.OrderException;
import com.sun.mail.smtp.SMTPAddressFailedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.SendFailedException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class MyExceptionHandler {
    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(SendFailedException.class)
    /**
     * @description 处理邮箱发送失败的异常
     * @author xiangXX
     * @date 2018/6/15 0015 13:03
      * @param err
     * 
     */  
    public Map SendFailedException(String err) {
        err = "请填写正确的邮箱地址";
        HashMap hashmap = new HashMap<>();
        hashmap.put("err", err);
        return hashmap;

    }

    @org.springframework.web.bind.annotation.ExceptionHandler({UnauthorizedException.class})
    /**
     * @description 处理认证失败的异常
     * @author xiangXX
     * @date 2018/6/15 0015 13:04
      * @param
     *
     */
    public String UnauthorizedException() {

        return "forward:/error";
    }
}
