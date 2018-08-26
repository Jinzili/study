package com.jinzl.reflect;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectTest {

    private String className = null;

    private Class personClass = null;

    @Before
    public void init() throws ClassNotFoundException {
        className = "com.jinzl.reflect.Person";
        personClass = Class.forName(className);
    }

    @Test
    public void getClassName(){
        System.out.println(personClass);
        // 获取某个class文件对象的另一种方式
        System.out.println(Person.class);
    }

    /**
     * 创建一个class文件表示的实例对象, 底层会调用无参构造方法
     */
    @Test
    public void getNewInstance() throws IllegalAccessException, InstantiationException {
        System.out.println(personClass.newInstance());
    }

    /**
     * 反射调用公有有参构造方法
     */
    @Test
    public void getPublicConstructorTest() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor constructor = personClass.getConstructor(String.class, Integer.class);
        Person person = (Person) constructor.newInstance("jinzili", 18);
        System.out.println(person);
    }

    /**
     * 反射调用私有有参构造方法
     */
    @Test
    public void getPrivateConstructorTest() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor constructor = personClass.getDeclaredConstructor(String.class);
        constructor.setAccessible(true);
        Person person = (Person) constructor.newInstance("jinzili");
        System.out.println(person);
    }

    /**
     * 访问非私有的成员变量
     */
    @Test
    public void getPublicField() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        Constructor constructor = personClass.getConstructor(String.class, Integer.class);
        Person person = (Person) constructor.newInstance("jinzili", 18);

        Field field = personClass.getField("phone");
        field.set(person, "18721317074");
        System.out.println(person);
    }

    /**
     * 访问私有的成员变量
     */
    @Test
    public void getPrivateField() throws Exception {
        Constructor constructor = personClass.getConstructor(String.class, Integer.class);
        Person person = (Person) constructor.newInstance("jinzili", 18);

        Field field = personClass.getDeclaredField("name");
        field.setAccessible(true);
        field.set(person, "jinzili");
        System.out.println(person);
    }

    /**
     * 获取非私有的成员函数
     */
    @Test
    public void getPublicMethod() throws Exception{
        Object obj = personClass.newInstance();
        Object object = personClass.getMethod("toStringCustom").invoke(obj);
        System.out.println(object);
    }

    /**
     * 获取私有的成员函数
     */
    @Test
    public void getPrivateMethod() throws Exception{
        Constructor constructor = personClass.getConstructor(String.class, Integer.class);
        Object obj = constructor.newInstance("jinzili", 18);
        Method method = personClass.getDeclaredMethod("getName");
        method.setAccessible(true);
        Object value = method.invoke(obj);
        System.out.println(value);
    }

    /**
     * 其他方法
     */
    @Test
    public void getOtherMethod() throws Exception{
        // 当前加载这个class文件的哪个类加载器对象
        System.out.println(personClass.getClassLoader());
        // 获取某个类实现的所有接口
        Class[] interfaces = personClass.getInterfaces();
        for(Class _interface : interfaces){
            System.out.println(_interface);
        }
        // 反射当前这个类的直接父类
        System.out.println(personClass.getGenericSuperclass());
        // 判断当前的Class对象表示是否是数组
        System.out.println(personClass.isArray());
        System.out.println(new String[3].getClass().isArray());
        // 判断当前的Class对象表示是否是枚举
        System.out.println(personClass.isEnum());
        // 判断当前的Class对象表示是否是接口
        System.out.println(personClass.isInterface());
    }

}
