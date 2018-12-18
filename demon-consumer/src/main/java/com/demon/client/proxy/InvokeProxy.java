package com.demon.client.proxy;

import com.demon.client.annotation.RemoteInvoke;
import com.demon.client.core.TcpClient;
import com.demon.client.parame.ClientRequest;
import com.demon.client.parame.Response;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangpengpeng on 2018/12/14.
 */
@Component
public class InvokeProxy implements BeanPostProcessor {
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        Field[] fields = o.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(RemoteInvoke.class)) {
                field.setAccessible(true);

                final Map<Method, Class> methodClassMethod = new HashMap<Method, Class>();
                putMethodClass(methodClassMethod, field);
                Enhancer enhancer = new Enhancer();
                enhancer.setInterfaces(new Class[]{
                        field.getType()
                });
                enhancer.setCallback(new MethodInterceptor() {
                    public Object intercept(Object instance, Method method, Object[] args, MethodProxy proxy) throws Throwable {

                        ClientRequest request = new ClientRequest();

                        request.setCommand(methodClassMethod.get(method).getName() + "." + method.getName());

                        request.setContent(args[0]);

                        Response response = TcpClient.send(request);

                        return response;
                    }
                });

                try {
                    field.set(o, enhancer.create());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        }
        return o;
    }

    /*
     * 属性
     */
    private void putMethodClass(Map<Method, Class> methodClassMethod, Field field) {
        Method[] methods = field.getType().getDeclaredMethods();
        for (Method m : methods) {
            methodClassMethod.put(m, field.getType());
        }
    }

    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        return o;
    }
}
