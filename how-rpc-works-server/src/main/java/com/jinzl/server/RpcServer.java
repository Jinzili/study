package com.jinzl.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.HashMap;
import java.util.Map;

public class RpcServer implements ApplicationContextAware, InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(RpcServer.class);

    private String serverAddress;

    // 用于存储业务接口和实现类的实例对象(由spring所构造)
    private Map<String, Object> handlerMap = new HashMap<>();

    public RpcServer(String serverAddress){
        this.serverAddress = serverAddress;
    }

    /**
     * 通过注解, 获取标注了rpc服务注解的业务类的接口及impl对象, 把它放到handlerMap中
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }

    /**
     * 在此启动netty服务, 绑定handler流水线
     * 1、接收
     */
    @Override
    public void afterPropertiesSet() throws Exception {

    }

}
