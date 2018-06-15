package com.jinzili.study.shiro;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;

/**
 * FUNCTION:
 * author: jinzili
 * date: 2018/6/12
 */
public class MyRealm1 implements Realm {
    public String getName() {
        return "myrealm1";
    }

    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 用户名
        String username = String.valueOf(token.getPrincipal());
        // 密码
        String password = new String((char[])token.getCredentials());

        if(!StringUtils.equals("zhang", username)){
            // 用户名错误
            throw new UnknownAccountException();
        }

        if(!StringUtils.equals("123", password)){
            // 密码错误
            throw new IncorrectCredentialsException();
        }
        // 如果身份认证验证成功, 返回一个AuthenticationInfo实现
        return new SimpleAuthenticationInfo(username, password, getName());
    }
}
