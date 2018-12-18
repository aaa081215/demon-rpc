package com.demon.demonrpc.medium;


import com.demon.demonrpc.annotation.Remote;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * 中介模式 TcpServerHandler
 * Created by wangpengpeng on 2018/12/12.
 */
@Component
public class InitialMedium implements BeanPostProcessor {
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        return o;
    }

    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        if (o.getClass().isAnnotationPresent(Remote.class)) {
            Method[] methods = o.getClass().getDeclaredMethods();
            for (Method m : methods) {
                String key = o.getClass().getInterfaces()[0].getName() + "." + m.getName();
                Map<String, BeamMethod> beamMethodMap = Medium.beamMap;
                BeamMethod beamMethod = new BeamMethod();
                beamMethod.setBean(o);
                beamMethod.setM(m);
                beamMethodMap.put(key, beamMethod);
            }

        }
        return o;
    }
}
