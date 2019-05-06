package com.alex.mq.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author: Alex
 * @descripTion: RocketMQ生产者自动装配注解
 * @date: Created in  22:25 2019/5/6
 * @modified By:
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface MQProducer {
}
