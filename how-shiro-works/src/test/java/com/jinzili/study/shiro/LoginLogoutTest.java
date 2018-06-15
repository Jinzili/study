package com.jinzili.study.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * FUNCTION:
 * author: jinzili
 * date: 2018/6/12
 */
public class LoginLogoutTest {

    /**
     * 2.1、首先通过new IniSecurityManagerFactory并指定一个ini配置文件来创建一个SecurityManager工厂；
     *
     * 2.2、接着获取SecurityManager并绑定到SecurityUtils，这是一个全局设置，设置一次即可；
     *
     * 2.3、通过SecurityUtils得到Subject，其会自动绑定到当前线程；如果在web环境在请求结束时需要解除绑定；然后获取身份验证的Token，如用户名/密码；
     *
     * 2.4、调用subject.login方法进行登录，其会自动委托给SecurityManager.login方法进行登录；
     *
     * 2.5、如果身份验证失败请捕获AuthenticationException或其子类，常见的如： DisabledAccountException（禁用的帐号）、
     * LockedAccountException（锁定的帐号）、UnknownAccountException（错误的帐号）、ExcessiveAttemptsException（登录失败次数过多）、
     * IncorrectCredentialsException （错误的凭证）、ExpiredCredentialsException（过期的凭证）等，具体请查看其继承关系；
     * 对于页面的错误消息展示，最好使用如“用户名/密码错误”而不是“用户名错误”/“密码错误”，防止一些恶意用户非法扫描帐号库；
     *
     * 2.6、最后可以调用subject.logout退出，其会自动委托给SecurityManager.logout方法退出。
     *
     * 身份认证流程:
     * 1、首先调用Subject.login(token)进行登录，其会自动委托给Security Manager，调用之前必须通过SecurityUtils. setSecurityManager()设置；
     *
     * 2、SecurityManager负责真正的身份验证逻辑；它会委托给Authenticator进行身份验证；
     *
     * 3、Authenticator才是真正的身份验证者，Shiro API中核心的身份认证入口点，此处可以自定义插入自己的实现；
     *
     * 4、Authenticator可能会委托给相应的AuthenticationStrategy进行多Realm身份验证，默认ModularRealmAuthenticator会调用AuthenticationStrategy进行多Realm身份验证；
     *
     * 5、Authenticator会把相应的token传入Realm，从Realm获取身份验证信息，如果没有返回/抛出异常表示身份验证失败了。此处可以配置多个Realm，将按照相应的顺序及策略进行访问。
     *
     * 授权流程:
     * 1、首先调用Subject.isPermitted*\/hasRole*接口，其会委托给SecurityManager，而SecurityManager接着会委托给Authorizer；
     *
     * 2、Authorizer是真正的授权者，如果我们调用如isPermitted(“user:view”)，其首先会通过PermissionResolver把字符串转换成相应的Permission实例；
     *
     * 3、在进行授权之前，其会调用相应的Realm获取Subject相应的角色/权限用于匹配传入的角色/权限；
     *
     * 4、Authorizer会判断Realm的角色/权限是否和传入的匹配，如果有多个Realm，会委托给ModularRealmAuthorizer进行循环判断，
     * 如果匹配如isPermitted*\/hasRole*会返回true，否则返回false表示授权失败。
     *
     * 如果Realm进行授权的话，应该继承AuthorizingRealm，其流程是：
     *
     * 1.1、如果调用hasRole*，则直接获取AuthorizationInfo.getRoles()与传入的角色比较即可；
     *
     * 1.2、首先如果调用如isPermitted(“user:view”)，首先通过PermissionResolver将权限字符串转换成相应的Permission实例，
     * 默认使用WildcardPermissionResolver，即转换为通配符的WildcardPermission；
     *
     * 2、通过AuthorizationInfo.getObjectPermissions()得到Permission实例集合；通过AuthorizationInfo. getStringPermissions()得到字符串集合并通过PermissionResolver解析为Permission实例；
     * 然后获取用户的角色，并通过RolePermissionResolver解析角色对应的权限集合（默认没有实现，可以自己提供）；
     *
     * 3、接着调用Permission. implies(Permission p)逐个与传入的权限比较，如果有匹配的则返回true，否则false
     *
     */
    @Test
    public void testHelloworld(){
        // 1、获取SecurityManager工厂, 此处使用ini配置文件初始化 SecurityManager
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-jdbc-realm.ini");
        // 2、得到SecurityManager实例 并绑定给SecurityUtils
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        // 3、得到subject及创建用户名/密码身份验证token(用户身份凭证)
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");
        try {
            // 4、登录, 即验证身份
            subject.login(token);
        }catch (AuthenticationException e){
            // 5、身份验证失败
        }
        Assert.assertTrue(subject.isAuthenticated());
        subject.logout();
    }

    private void login(String config, String username, String password){
        // 1、获取SecurityManager工厂, 此处使用ini配置文件初始化 SecurityManager
        Factory<SecurityManager> factory = new IniSecurityManagerFactory(config);
        // 2、得到SecurityManager实例 并绑定给SecurityUtils
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        // 3、得到subject及创建用户名/密码身份验证token(用户身份凭证)
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            // 4、登录, 即验证身份
            subject.login(token);
        }catch (AuthenticationException e){
            // 5、身份验证失败
        }
    }

    private Subject subject(){
        return SecurityUtils.getSubject();
    }

    /**
     * checkRole/checkRoles和hasRole/hasAllRoles 不同的地方是check*会抛出UnauthorizedException
     */
    @Test
    public void testHasRole(){
        login("classpath:shiro-role.ini", "zhang", "123");
        // 判断拥有角色
        Assert.assertTrue(subject().hasRole("role1"));
        // 判断拥有角色 role1 and role2
        Assert.assertTrue(subject().hasAllRoles(Arrays.asList("role1", "role2")));
        // 判断拥有角色 role 1 and role2 and !role3
        boolean[] result = subject().hasRoles(Arrays.asList("role1", "role2", "role3"));
        Assert.assertTrue(result[0]);
        Assert.assertTrue(result[1]);
        Assert.assertTrue(result[2]);
    }

}
