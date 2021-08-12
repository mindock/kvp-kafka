package com.kvp.kafka.producer;

import com.kvp.domain.Developer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DeveloperProducer {
    private static final String TOPIC = "developer";
    private final KafkaTemplate<String, Developer> kafkaTemplate;

    public DeveloperProducer(KafkaTemplate<String, Developer> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(Developer developer) {
        log.info("개발자 producer message: {}", developer);
        kafkaTemplate.send(TOPIC, developer);
    }
}
