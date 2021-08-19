package com.kvp.kafka.producer;

import com.kvp.domain.Introduce;
import com.kvp.domain.PurchaseCustomer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PurchaseCustomerProducer {
    private static final String TOPIC = "purchase-customer";
    private final KafkaTemplate<String, PurchaseCustomer> kafkaTemplate;

    public PurchaseCustomerProducer(KafkaTemplate<String, PurchaseCustomer> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(PurchaseCustomer purchaseCustomer) {
        log.info("구매 고객 message : {}", purchaseCustomer);
        kafkaTemplate.send(TOPIC, purchaseCustomer);
    }
}
