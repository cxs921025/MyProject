package com.cxs.core.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@AutoConfigureAfter(RabbitmqConfig.class)
public class QueueConfig {

    private static final String GET_USER_QUEUE = "getUser";
    private static final String QUEUE_DELAY = "_delay";

    //路由
    private final DirectExchange directExchange;

    @Autowired
    public QueueConfig(DirectExchange directExchange) {
        this.directExchange = directExchange;
    }

    @Bean
    public Queue getUser() {
        // durable 是否持久化到本地磁盘
        return new Queue(GET_USER_QUEUE, true);
    }

    @Bean
    public Binding signNotifyRcQueueDataBinding(Queue getUser) {
        return BindingBuilder.bind(getUser).to(directExchange).with(GET_USER_QUEUE);
    }

    @Bean
    public Queue getUserDelay() {
        Map<String, Object> arguments = new HashMap<>();
        // 消息生存时间(毫秒)
        arguments.put("x-message-ttl", 10000);
        // 绑定路由
        arguments.put("x-dead-letter-exchange", directExchange.getName());
        // 转发队列
        arguments.put("x-dead-letter-routing-key", GET_USER_QUEUE);
        return new Queue(GET_USER_QUEUE + QUEUE_DELAY, true, false, false, arguments);
    }

    @Bean
    public Binding getUserDelayBinding(Queue getUserDelay) {
        return BindingBuilder.bind(getUserDelay).to(directExchange).with("getUser_delay");
    }
}
