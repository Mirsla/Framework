package com.alex.controller;

import com.alex.mq.core.MQProducer;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

/**
 * @author: Alex
 * @descripTion:
 * @date: Created in  20:04 2019/5/7
 * @modified By:
 */
@RestController
@RequestMapping("test")
public class TestController {

    @Autowired
    private MQProducer mqProducer;

    @RequestMapping("")
    public String test() {
        return "This is MSG webent";
    }

    /**
     * 发送一个消息 topic: msg-test
     * @return
     */
    @RequestMapping("/msg")
    public String msgTest() throws UnsupportedEncodingException {
        Message message = new Message("msg-test", "test1", "msg-test".getBytes("UTF-8"));
        mqProducer.send(message);
        return "Send msg-test: SUCCESS";
    }


    /**
     * 发送一个消息 topic: msg-test-2
     * @return
     */
    @RequestMapping("/msg1")
    public String msgTest1() throws UnsupportedEncodingException{
        Message message = new Message("msg-test-1", "test1", "msg-test".getBytes("UTF-8"));
        mqProducer.send(message);
        return "Send msg-test-1: SUCCESS";
    }

    /**
     * 发送一个消息 topic: msg-test-2
     * @return
     */
    @RequestMapping("/msg2")
    public String msgTest2() throws UnsupportedEncodingException{
        Message message = new Message("msg-test-2", "test1", "msg-test".getBytes("UTF-8"));
        mqProducer.send(message);
        return "Send msg-test-2: SUCCESS";
    }
}
