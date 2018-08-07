package com.dx.Booker.component;

import com.dx.Booker.generator.po.User;
import com.dx.Booker.serviceinterface.LoginService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;
/**
 * @description shiroRealm配置
 * @author xiangXX
 * @date 2018/6/17 0017 10:10
  * @param null
 *
 */
public class ShiroRealm extends AuthorizingRealm {
    @Autowired
    private LoginService loginservice;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Object primaryPrincipal = principalCollection.getPrimaryPrincipal();
        String email = String.valueOf(primaryPrincipal);
        User user = loginservice.isRegister(email);
        String role = user.getRole();
        Set<String> roles = new HashSet<String>();
        roles.add("user");
        if (role.equals("admin"))
            roles.add("admin");
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo(roles);
        return simpleAuthorizationInfo;


    }


    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String email = token.getUsername();
        User user = loginservice.isRegister(email);
        if (user == null) {
            throw new UnknownAccountException("用户不存在");
        }
        Object username = email;
        Object password = user.getPassword();
        ByteSource salt = ByteSource.Util.bytes(email);
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username, password, salt, getName());
        return info;
    }

    public static void main(String[] args) {
        String hashAlgorithmName = "MD5";
        Object credentials = "zx123456";
        Object salt = ByteSource.Util.bytes("745402208@qq.com");
        Object result = new SimpleHash(hashAlgorithmName, credentials, salt);
        System.out.println(result);
    }
}
