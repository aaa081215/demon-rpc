package com.demon.demonrpc.medium;

import java.lang.reflect.Method;

/**
 * Created by wangpengpeng on 2018/12/12.
 */
public class BeamMethod {
    private Object bean;
    private Method m;

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public Method getM() {
        return m;
    }

    public void setM(Method m) {
        this.m = m;
    }
}
