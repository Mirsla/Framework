package com.alex.mq.core;

/**
 * @author: Alex
 * @descripTion:
 * @date: Created in  22:24 2019/5/6
 * @modified By:
 */
public abstract class MQConst {
    /**
     * 消息模式 集群或者广播
     */
    public static final String MESSAGE_MODE_CLUSTERING = "CLUSTERING";
    public static final String MESSAGE_MODE_BROADCASTING = "BROADCASTING";

    /**
     * 消费模式 有序（单线程）或者无序（多线程）
     */
    public static final String CONSUME_MODE_CONCURRENTLY = "CONCURRENTLY";
    public static final String CONSUME_MODE_ORDERLY = "ORDERLY";

    public static final String PROPERTY_TOPIC = "TOPIC";
}
