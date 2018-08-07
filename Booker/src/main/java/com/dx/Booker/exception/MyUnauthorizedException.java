package com.dx.Booker.exception;

import org.apache.shiro.authz.UnauthorizedException;

public class MyUnauthorizedException extends UnauthorizedException {
    public MyUnauthorizedException() {
        super("你没有权限访问此页面");
    }
}
