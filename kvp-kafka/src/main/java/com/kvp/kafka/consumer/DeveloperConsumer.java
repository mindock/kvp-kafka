package com.kvp.kafka.consumer;

import com.kvp.domain.Developer;
import com.kvp.domain.SimpleDeveloper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DeveloperConsumer {
    @KafkaListener(topics = "junior-developer", groupId = "developer", containerFactory = "developerListener")
    public void consumeJunior(Developer developer) {
        log.info("주니어 개발자 consumer message: {}", developer);
    }

    @KafkaListener(topics = "senior-developer", groupId = "developer", containerFactory = "developerListener")
    public void consumeSenior(Developer developer) {
        log.info("시니어 개발자 consumer message: {}", developer);
    }

    @KafkaListener(topics = "senior-java-developer", groupId = "simple-developer", containerFactory = "simpleDeveloperListener")
    public void consumeSeniorJava(SimpleDeveloper developer) {
        log.info("시니어 자바 개발자 consumer message: {}", developer);
    }
}
