package com.kvp.kafka.controller;

import com.kvp.domain.*;
import com.kvp.kafka.producer.DeveloperProducer;
import com.kvp.kafka.producer.KvpTestProducer;
import com.kvp.kafka.producer.PurchaseCustomerProducer;
import com.kvp.kafka.producer.WorkLogProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/kafka")
@RequiredArgsConstructor
public class KafkaController {
    private final KvpTestProducer kvpTestProducer;
    private final DeveloperProducer developerProducer;
    private final PurchaseCustomerProducer purchaseCustomerProducer;
    private final WorkLogProducer workLogProducer;

    @GetMapping
    public ResponseEntity send(String name, Long age) {
        Introduce introduce = new Introduce(name, age);
        kvpTestProducer.send(introduce);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/developer")
    public ResponseEntity send(String name, Long age, Language language, int year) {
        Developer developer = new Developer(name, age, language, year);
        developerProducer.send(developer);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/customer")
    public ResponseEntity sendCustomer(String name, Long purchaseAmount) {
        PurchaseCustomer purchaseCustomer = new PurchaseCustomer(name, purchaseAmount);
        purchaseCustomerProducer.send(purchaseCustomer);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/work-log")
    public ResponseEntity sendWorkLog(Long no, String name, WorkType workType,
                                      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime dateTime) {
        WorkLog workLog = new WorkLog(new Employee(no, name), workType, dateTime);
        workLogProducer.send(workLog);
        return ResponseEntity.ok().build();
    }
}
