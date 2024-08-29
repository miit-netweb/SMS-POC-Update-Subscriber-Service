package com.microservices.update_subscriber.publisher;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.microservices.update_subscriber.entity.EmailPending;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RabbitMQProducer {

    @Value("${rabbitmq.exchange.name}")
    private String EXCHANGE_NAME;

    @Value("${rabbitmq.routing.key}")
    private String ROUTING_KEY;

    private RabbitTemplate rabbitTemplate;

    public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    @Async
    public void sendMessage(EmailPending message){
        log.info(String.format("Message sent -> %s",message));
        try {
            rabbitTemplate.convertAndSend(EXCHANGE_NAME,ROUTING_KEY,message);
        } catch (Exception e){
            log.error(e.getMessage());
        }
    }
}