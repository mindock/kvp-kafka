package com.kvp.kafka.consumer;

import com.kvp.domain.DailyWorkLog;
import com.kvp.domain.OverWorkLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WorkLogConsumer {
    @KafkaListener(topics = "daily-work-log", groupId = "work-log", containerFactory = "dailyWorkLogListener")
    public void consumeWorkLog(DailyWorkLog dailyWorkLog) {
        log.info("날짜별 근무 기록 consumer message: {}", dailyWorkLog);
    }

    @KafkaListener(topics = "over-work-log", groupId = "work-log", containerFactory = "overWorkLogListener")
    public void consumeOverWorkLog(OverWorkLog overWorkLog) {
        log.info("초과 근무 기록 consumer message: {}", overWorkLog);
    }
}
