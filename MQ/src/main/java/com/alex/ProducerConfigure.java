package com.alex;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Mirsla
 * @descripTion:
 * @date: Created in  22:34 2019/4/24
 * @modified By:
 */
@Configuration
public class ProducerConfigure {
    @Autowired
    private ProducerConfig producerConfig;
}
