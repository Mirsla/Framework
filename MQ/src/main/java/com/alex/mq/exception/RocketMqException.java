package com.alex.mq.exception;

/**
 * @author: Mirsla
 * @descripTion: RocketMqException 消息发送异常定义
 * @date: Created in  16:28 2019/4/28
 * @modified By:
 */
public class RocketMqException extends RuntimeException {

    public RocketMqException(String message) {
        super(message);
    }
}
