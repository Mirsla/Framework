package com.alex.mq.annotation;

import com.alex.mq.core.MQConst;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author: Alex
 * @descripTion: RocketMQ消费者自动装配注解
 * @date: Created in  22:23 2019/5/6
 * @modified By:
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface MQConsumer {
    String consumerGroup();
    String topic();
    int batchMxSize() default 0;
    /**
     * 广播模式消费： BROADCASTING
     * 集群模式消费： CLUSTERING
     * @return 消息模式
     */
    String messageMode() default MQConst.MESSAGE_MODE_CLUSTERING;

    /**
     * 使用线程池并发消费: CONCURRENTLY("CONCURRENTLY"),
     * 单线程消费: ORDERLY("ORDERLY");
     * @return 消费模式
     */
    String consumeMode() default MQConst.CONSUME_MODE_CONCURRENTLY;
    String[] tag() default {"*"};
}
