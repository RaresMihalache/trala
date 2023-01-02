package com.example.raresm.sdproject1.services;

import com.example.raresm.sdproject1.dtos.MeasurementMQ;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@RabbitListener(queues = "${mq.queue}")
public class RabbitMQConsumer {
    @RabbitHandler
    public void receiver(String message){

    }
}
