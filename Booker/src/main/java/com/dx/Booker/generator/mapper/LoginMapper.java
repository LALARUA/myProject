package com.dx.Booker.generator.mapper;

import com.dx.Booker.generator.po.User;

public interface LoginMapper {

    //通过邮箱和密码查询用户
    public User validate(User user);

    //通过邮箱查询用户
    public User isRegister(String email);

    public void updatePassword(User user);
}
