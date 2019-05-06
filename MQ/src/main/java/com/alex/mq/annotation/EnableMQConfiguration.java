package com.alex.mq.annotation;

import java.lang.annotation.*;

/**
 * @author: Alex
 * @descripTion: 自动注册MQ配置注解
 * @date: Created in  21:27 2019/5/6
 * @modified By:
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface EnableMQConfiguration {
}
