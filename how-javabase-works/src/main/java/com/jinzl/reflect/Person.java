package com.jinzl.reflect;

import lombok.Data;

import java.io.Serializable;

@Data
public class Person implements Serializable {

    private static final long serialVersionUID = 5772917160911675752L;

    public Person(String name, Integer age){
        this.name = name;
        this.age = age;
    }

    private Person(String name){
        this.name = name;
    }

    public Person(){

    }

    private String name;

    private Integer age;

    public String phone;

    public void toStringCustom(){
        System.out.println(name);
        System.out.println(age);
        System.out.println(phone);
    }

}
