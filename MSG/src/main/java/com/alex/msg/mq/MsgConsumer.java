package com.alex.msg.mq;

import com.alex.mq.annotation.MQConsumer;
import com.alex.mq.core.AbstractMQConsumer;
import com.alex.mq.core.AbstractMQPushConsumer;
import com.alex.mq.core.MQConst;

import java.util.Map;

/**
 * @author: Alex
 * @descripTion: 这是一个rocketMQ 消费者类
 * @date: Created in  22:48 2019/5/6
 * @modified By:
 */
@MQConsumer(topic = "msg-test", messageMode = MQConst.MESSAGE_MODE_CLUSTERING, consumeMode = MQConst.CONSUME_MODE_CONCURRENTLY, batchMxSize = 10, consumerGroup = "mk-mid-msg-group")
public class MsgConsumer extends AbstractMQPushConsumer {

    @Override
    public void onMessage(Object message, Map extMap) throws Exception {
        System.out.println("-------------");
        System.out.println(message.toString());
        System.out.println("-------------");
        System.out.println("MsgConsumer....onMessage");
    }
}
