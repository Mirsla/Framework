package com.alex.mq.config;

import com.alex.mq.annotation.EnableMQConfiguration;
import com.alex.mq.core.AbstractMQProducer;
import com.alex.mq.core.AbstractMQPushConsumer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Alex
 * @descripTion: RocketMQ配置文件
 *
 *  xxxxAware 以Aware结尾的接口，用于注册spring底层的组件，比如现在需要 applicationContext 只需要实现ApplicationContextAware 接口，spring会自动的将applicationContext 注入进来
 *
 * @date: Created in  21:25 2019/5/6
 * @modified By:
 */
@Configuration
@ConditionalOnBean(annotation = EnableMQConfiguration.class)
@AutoConfigureAfter({AbstractMQProducer.class, AbstractMQPushConsumer.class})
@EnableConfigurationProperties(MQProperties.class)
public class MQBaseAutoConfiguration implements ApplicationContextAware {

    @Autowired
    protected MQProperties mqProperties;

    /**
     * 此接口结合了所有ApplicationContext需要实现的接口。因此大多数的ApplicationContext都要实现此接口。它在ApplicationContext的基础上增加了一系列配置应用上下文的功能。配置应用上下文和控制应用上下文生命周期的方法在此接口中被封装起来，以免客户端程序直接使用
     */
    protected ConfigurableApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = (ConfigurableApplicationContext) applicationContext;
    }
}
