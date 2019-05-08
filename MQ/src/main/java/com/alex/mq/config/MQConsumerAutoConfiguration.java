package com.alex.mq.config;

import com.alex.mq.annotation.MQConsumer;
import com.alex.mq.core.AbstractMQPushConsumer;
import com.alex.mq.core.MQConst;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author: Alex
 * @descripTion: 消费者的自动配置
 * @date: Created in  21:14 2019/5/8
 * @modified By:
 */
@Slf4j
@Configuration // 标注这是一个配置类 可替换xml配置文件，被注解的类内部包含有一个或多个被@Bean注解的方法，这些方法将会被AnnotationConfigApplicationContext或AnnotationConfigWebApplicationContext类进行扫描，并用于构建bean定义，初始化Spring容器。
@ConditionalOnBean(MQBaseAutoConfiguration.class) // 只有在当前项目中注入的Bean中有MQBaseAutoConfiguration才注入当前Bean
public class MQConsumerAutoConfiguration extends MQBaseAutoConfiguration {

//    protected DefaultMQPushConsumer

    // 维护一份map用于检测是否用同样的consumerGroup订阅了不同的topic+tag
    private Map<String, String> validConsumerMap;

    /**
     * @PostConstruct Servlet生命周期的注解 被用来修饰一个非静态的void()方法
     * 修饰的方法会在服务器加载Servlet的时候运行，并且只会被服务器调用一次，类似于Serclet的inti()方法。被@PostConstruct修饰的方法会在构造函数之后，init()方法之前运行。在Bean创建完成并且属性赋值完毕，来执行的初始化方法
     * @throws Exception
     */
    @PostConstruct
    public void init() throws Exception {
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(MQConsumer.class);
        if(!CollectionUtils.isEmpty(beans) && mqProperties.getTraceEnabled()) {
            //initAsyncAppender();
        }
        // 断言，在有MQ的时候必须有消费者的注解
        Assert.notNull(mqProperties.getNameServerAddress(), "name server address must be defined");
        validConsumerMap = new HashMap<>();
        for (Map.Entry<String, Object> entry : beans.entrySet()) {
            publishConsumer(entry.getKey(), entry.getValue());
        }
        // 清空map，等待回收
        validConsumerMap = null;
    }

    /**
     * 对每一个消费者进行处理
     * @param beanName
     * @param bean
     * @throws Exception
     */
    private void publishConsumer(String beanName, Object bean) throws Exception {
        // 获取当前rocketMQ消费者的MQConsumer注解信息
        MQConsumer mqConsumer = applicationContext.findAnnotationOnBean(beanName, MQConsumer.class);

        Assert.notNull(mqConsumer.consumerGroup(), "consumer's consumerGroup must be defined");
        Assert.notNull(mqConsumer.topic(), "consumer's topic must be defined");

        // 判断该类是否为AbstractMQPushConsumer的子类
        if (!AbstractMQPushConsumer.class.isAssignableFrom(bean.getClass())) {
            throw new RuntimeException(bean.getClass().getName() + " - consumer未实现Consumer抽象类");
        }

        // 获取当前Module的上下文信息
        Environment environment = applicationContext.getEnvironment();

        /**
         * todo 这里其实有点疑问
         */
        String consumerGroup = environment.resolvePlaceholders(mqConsumer.consumerGroup());
        String topic = environment.resolvePlaceholders(mqConsumer.topic());

        String tags = "*";
        if(mqConsumer.tag().length == 1) {
            tags = environment.resolvePlaceholders(mqConsumer.tag()[0]);
        } else if(mqConsumer.tag().length > 1) {
//            tags = StringUtils.join(mqConsumer.tag(), "||");
        }

        // 检查consumerGroup，同一个消费者只能订阅一个topic
        // todo 这里留一个问题，如果同一个消费订阅了多个topic会发生什么，如果一个topic被多个消费者订阅会发生什么
        if(!StringUtils.isEmpty(validConsumerMap.get(consumerGroup))) {
            String exist = validConsumerMap.get(consumerGroup);
            throw new RuntimeException("同一消费者只能订阅一个topic...[" + consumerGroup + "已经订阅了" + exist+"]");
        } else {
            validConsumerMap.put(consumerGroup, topic + "-" + tags);
        }

        if (AbstractMQPushConsumer.class.isAssignableFrom(bean.getClass())) {
            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGroup);
            // 设置mq的地址
            consumer.setNamesrvAddr(mqProperties.getNameServerAddress());
            // 设置消息的传播方式，广播或者是CLUSTERING（聚类）
            consumer.setMessageModel(MessageModel.valueOf(mqConsumer.messageMode()));
            // 设置rocketmq订阅的topic
            consumer.subscribe(topic, tags);
            // 当前消费者的实例名
            consumer.setInstanceName(UUID.randomUUID().toString());
            // 是否采用vip通道
            consumer.setVipChannelEnabled(mqProperties.getVipChannelEnabled());
            // 设置消费者一次消费消息的多少
            if(mqConsumer.batchMxSize()>0){
                consumer.setConsumeMessageBatchMaxSize(mqConsumer.batchMxSize());
            }

            AbstractMQPushConsumer<?> abstractMQPushConsumer = (AbstractMQPushConsumer<?>) bean;
            // 如果消息设置的是单线程消费
            if (MQConst.CONSUME_MODE_CONCURRENTLY.equals(mqConsumer.consumeMode())) {

                consumer.registerMessageListener(new MessageListenerConcurrently(){
                    @Override
                    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                                    ConsumeConcurrentlyContext context) {
                        return abstractMQPushConsumer.dealMessage(msgs);
                    }

                });
            } else if (MQConst.CONSUME_MODE_ORDERLY.equals(mqConsumer.consumeMode())) {
                consumer.registerMessageListener(new MessageListenerOrderly(){
                    @Override
                    public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs,
                                                               ConsumeOrderlyContext context) {
                        return abstractMQPushConsumer.dealOrderlyMessage(msgs);
                    }
                });

            } else {
                throw new RuntimeException("unknown consume mode");
            }
            abstractMQPushConsumer.setConsumer(consumer);
            consumer.start();

            System.out.println(consumerGroup + "  " + topic +" Consumer Started.");
        }
    }
}
