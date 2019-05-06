package com.alex;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author: Mirsla
 * @descripTion: 消息发送配置
 * @date: Created in  22:31 2019/4/24
 * @modified By:
 */
@Data
@PropertySource("classpath:application.yml")
@ConfigurationProperties(prefix = "rocketmq.producer")
@Configuration
@Component
public class ProducerConfig {

    private String namesrvAddr;

    private String groupName;
}
