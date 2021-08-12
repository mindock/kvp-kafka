package com.kvp.kafka.controller;

import com.kvp.domain.Developer;
import com.kvp.domain.Introduce;
import com.kvp.domain.Language;
import com.kvp.kafka.producer.DeveloperProducer;
import com.kvp.kafka.producer.KvpTestProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class KafkaController {
    private final KvpTestProducer kvpTestProducer;
    private final DeveloperProducer developerProducer;

    public KafkaController(KvpTestProducer kvpTestProducer, DeveloperProducer developerProducer) {
        this.kvpTestProducer = kvpTestProducer;
        this.developerProducer = developerProducer;
    }

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
}
