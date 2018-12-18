package com.demon.user.bean;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by wangpengpeng on 2018/12/16.
 */
public class reflect {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
        User user1 = new User();
        Class cls = user1.getClass();
        User user = (User) cls.newInstance();
        user.setId(1);
        user.setName("你好");

        Field[] fields = cls.getDeclaredFields();
//        for (Field f:fields) {
//            System.out.println(f.getName());
//            System.out.println(f.getType());
//        }
        Method[] method = cls.getDeclaredMethods();
        for (Method m : method
                ) {
            Class[] c = m.getParameterTypes();
            for (Class cl : c
                    ) {
                //  System.out.println(cl.getName());
            }
        }
        method[0].invoke(user, new User());

    }
}
