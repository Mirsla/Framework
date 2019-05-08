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


    /**
     * 来自 MessageExt
     */
    public static final String PROPERTY_EXT_QUEUE_ID = "QUEUE_ID";
    public static final String PROPERTY_EXT_STORE_SIZE = "STORE_SIZE";
    public static final String PROPERTY_EXT_QUEUE_OFFSET = "QUEUE_OFFSET";
    public static final String PROPERTY_EXT_SYS_FLAG = "SYS_FLAG";
    public static final String PROPERTY_EXT_BORN_TIMESTAMP = "BORN_TIMESTAMP";
    public static final String PROPERTY_EXT_BORN_HOST = "BORN_HOST";
    public static final String PROPERTY_EXT_STORE_TIMESTAMP = "STORE_TIMESTAMP";
    public static final String PROPERTY_EXT_STORE_HOST = "STORE_HOST";
    public static final String PROPERTY_EXT_MSG_ID = "MSG_ID";
    public static final String PROPERTY_EXT_COMMIT_LOG_OFFSET = "COMMIT_LOG_OFFSET";
    public static final String PROPERTY_EXT_RECONSUME_TIMES = "RECONSUME_TIMES";
    public static final String PROPERTY_EXT_PREPARED_TRANSACTION_OFFSET = "PREPARED_TRANSACTION_OFFSET";
    public static final String PROPERTY_EXT_BODY_CRC = "BODY_CRC";
}
