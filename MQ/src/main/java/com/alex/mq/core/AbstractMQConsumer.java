package com.alex.mq.core;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.util.Assert;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author: Alex
 * @descripTion:
 * @date: Created in  21:28 2019/5/6
 * @modified By:
 */
@Slf4j
public abstract class AbstractMQConsumer<T> {
    
    /**
     *  反序列化解析消息
     *
     * @author Alex
     * @param message 消息体
     * @return 序列化结果
     */
    protected T parseMessage(MessageExt message) {
        if (message == null || message.getBody() == null) {
            return null;
        }
        final Type type = this.getMessageType();
        if (type instanceof Class) {
            T data = JSON.parseObject(new String(message.getBody()), type);
            return data;
        } else {
            log.warn("Parse msg error. {}", message);
        }
        return null;
    }

    /**
     *  解析消息类型
     *
     * @author Alex
     * @return 消息类型
     */
    protected Type getMessageType() {
        Type superType = this.getClass().getGenericSuperclass();
        if (superType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) superType;
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            Assert.isTrue(actualTypeArguments.length == 1, "Number of type arguments must be 1");
            return actualTypeArguments[0];
        } else {
            // 如果没有定义泛型，解析为Object
            return Object.class;
        }
    }
}
