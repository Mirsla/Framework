package com.alex.mq.core;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: Alex
 * @descripTion:
 * @date: Created in  22:15 2019/5/6
 * @modified By:
 */
public abstract class AbstractMQPushConsumer<T> extends AbstractMQConsumer<T>{

    @Autowired
    private DefaultMQPushConsumer consumer;


}

