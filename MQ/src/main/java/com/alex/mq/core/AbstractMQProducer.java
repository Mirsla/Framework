package com.alex.mq.core;

import com.alex.mq.exception.MQExceptionResolver;
import com.alex.mq.exception.RocketMqException;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: Alex
 * @descripTion:
 * @date: Created in  21:31 2019/5/6
 * @modified By:
 */
@Slf4j
public abstract class AbstractMQProducer extends MQExceptionResolver {

    @Autowired
    private DefaultMQProducer mqProducer;

//    public AbstractMQProducer() {
//    }

    @Override
    protected void resolverSendException(String key, Message message, Exception e) {
        super.resolverSendException(key, message, e);
    }

    /**
     *  同步发送消息
     *
     * @author Alex
     * @param message 消息体
     * @return 消息发送结果
     * @throws RocketMqException 消息异常
     */
    public SendResult send(Message message) throws RocketMqException {
        try {
            SendResult sendResult = mqProducer.send(message);
            log.debug("send rocketmq message ,message : {}", sendResult);
            this.doAfterSyncSend(message, sendResult);
            return sendResult;
        } catch (Exception e) {
            resolverSendException("普通消息",message,e);
            return null;
        }
    }

    /**
     *  从写此方法，处理消息发送后得逻辑
     *
     * @author Alex
     * @param message 消息体
     * @param sendResult 消息发送结果
     */
    public void doAfterSyncSend(Message message, SendResult sendResult) {

    }

    /**
     *  异步发送消息
     *
     * @param message
     * @param sendCallback
     * @throws RocketMqException
     */
    public void send(Message message, SendCallback sendCallback) throws RocketMqException {
        try {
            mqProducer.send(message, sendCallback);
            log.debug("send rocketmq message async");
        } catch (Exception e) {
            resolverSendException("异步消息",message,e);
        }
    }

}


