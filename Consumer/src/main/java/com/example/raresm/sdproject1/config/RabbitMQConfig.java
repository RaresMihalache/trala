package com.example.raresm.sdproject1.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class RabbitMQConfig {
    @Value("${mq.queue}")
    private String queueName;
    @Value("${spring.rabbitmq.addresses}")
    private String address;

    @Bean
    public Queue queue(){
        return new Queue(queueName, false);
    }
}
