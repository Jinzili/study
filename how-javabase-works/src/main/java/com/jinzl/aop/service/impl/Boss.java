package com.jinzl.aop.service.impl;

import com.jinzl.aop.service.IBoss;

public class Boss implements IBoss {

    @Override
    public int cloth(String size) {
        System.err.println("老板给客户发快递---衣服型号: " + size);
        return 50;
    }

    public void kuzi(){
        System.out.println("老板给客户发快递---裤子");
    }

}
