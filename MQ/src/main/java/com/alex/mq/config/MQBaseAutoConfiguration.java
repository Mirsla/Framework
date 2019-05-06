package com.alex.mq.config;

import com.alex.mq.annotation.EnableMQConfiguration;
import com.alex.mq.core.AbstractMQProducer;
import com.alex.mq.core.AbstractMQPushConsumer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Alex
 * @descripTion: RocketMQ配置文件
 * @date: Created in  21:25 2019/5/6
 * @modified By:
 */
@Configuration
@ConditionalOnBean(annotation = EnableMQConfiguration.class)
@AutoConfigureAfter({AbstractMQProducer.class, AbstractMQPushConsumer.class})
@EnableConfigurationProperties(MQProperties.class)
public class MQBaseAutoConfiguration {

}
