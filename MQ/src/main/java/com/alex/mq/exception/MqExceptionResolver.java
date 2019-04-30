package com.alex.mq.exception;

import org.apache.rocketmq.common.message.Message;

/**
 * @author: Mirsla
 * @descripTion: MQ消息异常统一处理类
 * @date: Created in  16:27 2019/4/28
 * @modified By:
 */
public abstract class MqExceptionResolver {

    protected void resolverSendException(String key, Message message, Exception e) {
        String msg = e != null ? e.getMessage() : "";
        msg = key + ",消息发送失败，info :" + message + ",e:"+msg;
        throw new RocketMqException(msg);
    }
}
