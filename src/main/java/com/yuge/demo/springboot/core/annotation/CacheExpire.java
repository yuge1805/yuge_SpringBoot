package com.yuge.demo.springboot.core.annotation;

import java.lang.annotation.*;

/**
 * @author: yuge
 * @date: 2021-01-19
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface CacheExpire {

    /**
     * 过期时间，秒
     *
     * @return
     */
    long value() default 0L;

}
