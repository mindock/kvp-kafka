package com.kvp.kafka.consumer;

import com.kvp.domain.Customer;
import com.kvp.domain.Introduce;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomerConsumer {

    @KafkaListener(topics = "customer", groupId = "customer", containerFactory = "customerListener")
    public void consume(Customer message) {
        log.info("손님 : {}", message);
    }
}
