package com.demon.demonrpc.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Created by wangpengpeng on 2018/12/14.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Remote {
    String value() default "";
}
