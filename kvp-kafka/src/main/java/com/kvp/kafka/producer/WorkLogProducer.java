package com.kvp.kafka.producer;

import com.kvp.domain.PurchaseCustomer;
import com.kvp.domain.WorkLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WorkLogProducer {
    private static final String TOPIC = "work-log";
    private final KafkaTemplate<Long, WorkLog> kafkaTemplate;

    public WorkLogProducer(KafkaTemplate<Long, WorkLog> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(WorkLog workLog) {
        log.info("출/퇴근 기록 message : {}", workLog);
        kafkaTemplate.send(TOPIC, workLog);
    }
}
