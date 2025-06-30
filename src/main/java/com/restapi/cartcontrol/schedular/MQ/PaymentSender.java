package com.restapi.cartcontrol.schedular.MQ;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class PaymentSender {
    
    private final static Logger log = LoggerFactory.getLogger(PaymentSender.class);

    private final RabbitTemplate rabbitTemplate;

    public PaymentSender(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendQueue(String waitCode, int price){
        PaymentPackage queuePackage = new PaymentPackage();
        queuePackage.setWaitCode(waitCode);
        queuePackage.setServicePrice(price);
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_NAME, queuePackage);
        log.info("Queue Send");
    }
}
