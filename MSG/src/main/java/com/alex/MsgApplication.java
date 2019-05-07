package com.alex;

import com.alex.mq.annotation.EnableMQConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author: Alex
 * @descripTion: 消息发送的简单实现
 * @date: Created in  22:34 2019/5/6
 * @modified By:
 */
@SpringBootApplication  //开启自动配置
@ComponentScan(value = {"com.alex.controller", "com.alex.mq.config","com.alex.mq.core"})
@EnableMQConfiguration
public class MsgApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsgApplication.class, args);
    }
}
