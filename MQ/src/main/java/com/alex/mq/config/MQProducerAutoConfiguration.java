package com.alex.mq.config;

import com.alex.mq.annotation.MQProducer;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * @author: Alex
 * @descripTion: rocketMQ 消息发送自动装配类
 * @date: Created in  21:32 2019/5/7
 * @modified By:
 */
@Slf4j
@Configuration  // 定义一个配置类，用于替换xml文件的配置，用于初始化spring 容器
@ConditionalOnBean(MQBaseAutoConfiguration.class)
public class MQProducerAutoConfiguration extends MQBaseAutoConfiguration{

    @Setter
    private static DefaultMQProducer producer;



    /**
     * 往容器中注入 DefaultMQProducer
     * @return DefaultMQProducer 消息默认发送者
     * @throws MQClientException 消息发送者初始化
     */
    @Bean
    public DefaultMQProducer exposeProducer() throws MQClientException {
        // 获取上下文中所有标注有MQProducer注解的消息发送类
        Map<String, Object> producers = applicationContext.getBeansWithAnnotation(MQProducer.class);

        // 如果没有标准有MQProducer,说明当前项目启动的时候不需要消息发送者
        if(CollectionUtils.isEmpty(producers)) {
            return null;
        }

        if(producer == null) {
            // 断言，在配置文件中的 producer group不能为空
            Assert.notNull(mqProperties.getProducerGroup(), "producer group must be defined");
            // 断言，配置文件中的RocketMQ的地址不能为空
            Assert.notNull(mqProperties.getNameServerAddress(), "name server address must be defined");

            // 创建消息发送者
            producer = new DefaultMQProducer(mqProperties.getProducerGroup());

            // 设置消息发送者的RocketMQ地址
            producer.setNamesrvAddr(mqProperties.getNameServerAddress());
            // 设置消息发送超时时间
            producer.setSendMsgTimeout(mqProperties.getSendMsgTimeout());

            // 是否启用vip netty通道以发送消息	-D com.rocketmq.sendMessageWithVIPChannel参数的值，若无则是true
            //broker的netty server会起两个通信服务。两个服务除了服务的端口号不一样，其他都一样。其中一个的端口（配置端口-2）作为vip通道，客户端可以启用本设置项把发送消息此vip通道
            producer.setSendMessageWithVIPChannel(mqProperties.getVipChannelEnabled());
            producer.start();
        }

        return producer;
    }
}
