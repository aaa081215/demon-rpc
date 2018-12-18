package com.demon.client.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 调用者注解
 * Created by wangpengpeng on 2018/12/14.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface RemoteInvoke {
}
