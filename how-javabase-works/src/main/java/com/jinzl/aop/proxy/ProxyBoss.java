package com.jinzl.aop.proxy;

import java.lang.reflect.Proxy;

public class ProxyBoss {

    /**
     * 对接口方法进行代理
     */
    public static <T> T getProxy(final int discountCoupon,
                                 final Class<?> interfaceClass,
                                 final Class<?> implementsClass) throws Exception{
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(),
                new Class[]{interfaceClass},
                (proxy, method, args) -> {
                    Integer value = (Integer) method.invoke(implementsClass.newInstance(), args);
                    return value = discountCoupon;
                });
    }

}
