package com.jinzl.aop.action;

import com.jinzl.aop.service.IBoss;
import com.jinzl.aop.service.impl.Boss;
import org.junit.Test;

public class SaleAction {

    /**
     * 不使用代理, 直接调用方法
     * 方法中规定什么业务, 就只能调用什么业务, 规定什么返回值, 就只能输出什么返回值
     */
    @Test
    public void saleByBossSelf() throws Exception{
        IBoss boss = new Boss();
        System.out.println("老板自营");
        int money = boss.cloth("xxl");
        System.out.println("衣服成交价:" + money);
    }

}
