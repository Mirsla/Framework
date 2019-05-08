package com.alex.mq.core;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * @author: Alex
 * @descripTion:
 * @date: Created in  22:15 2019/5/6
 * @modified By:
 */
@Slf4j
public abstract class AbstractMQPushConsumer<T> extends AbstractMQConsumer<T>{

    @Setter
    @Getter
    private DefaultMQPushConsumer consumer;


    /**
     * 原生dealMessage方法，可以重写此方法自定义序列化和返回消费成功的相关逻辑
     *
     * @param list 消息列表
     * @return 消费状态
     */
    public ConsumeConcurrentlyStatus dealMessage(List<MessageExt> list) {
        for (MessageExt messageExt : list) {
            log.debug("receive msgId: {}, tags : {}", messageExt.getMsgId(), messageExt.getTags());
            T t = parseMessage(messageExt);
            Map<String, Object> ext = parseExtParam(messageExt);
            if (null != t) {
                try {
                    onMessage(t, ext);
                } catch (Exception e) {
                    log.error("consume dealMessage fail , reconsume_later , msgId: {}", messageExt.getMsgId());
                    String msg = "普通消息处理失败，topic :" + messageExt.getTopic() + ",msgId:" + messageExt.getMsgId() + ",e:" + e.getMessage();
//                    mqNotify.notify(new NotifyMsg(msg, MsgLevel.ERROR));
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
            }
        }
        System.out.println("ConsumeConcurrentlyStatus...dealMessage");
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

    /**
     * 原生dealMessage方法，可以重写此方法自定义序列化和返回消费成功的相关逻辑
     *
     * @param list 消息列表
     * @return 处理结果
     */
    public ConsumeOrderlyStatus dealOrderlyMessage(List<MessageExt> list) {
//        for (MessageExt messageExt : list) {
//            log.debug("receive msgId: {}, tags : {}", messageExt.getMsgId(), messageExt.getTags());
//            T t = parseMessage(messageExt);
//            Map<String, Object> ext = parseExtParam(messageExt);
//            if (null != t) {
//                try {
//                    onMessage(t, ext);
//                } catch (Exception e) {
//                    log.error("consume dealOrderlyMessage fail , suspend_current_queue_a_moment , msgId: {}",
//                            messageExt.getMsgId());
//                    String msg = "顺序消息处理失败，topic :" + messageExt.getTopic() + ",msgId:" + messageExt.getMsgId() + ",e:" + e.getMessage();
//                    mqNotify.notify(new NotifyMsg(msg, MsgLevel.ERROR));
//                    return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
//                }
//            }
//        }
        System.out.println("ConsumeOrderlyStatus...dealOrderlyMessage");
        return ConsumeOrderlyStatus.SUCCESS;
    }

    /**
     * 继承这个方法处理消息
     *
     * @param message 消息范型
     * @param extMap  存放消息附加属性的map, map中的key存放在 @link MessageExtConst 中
     * @return 处理结果
     */
    public abstract void onMessage(T message, Map<String, Object> extMap) throws Exception;
}

