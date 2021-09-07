package com.kvp.kafka.producer;

import com.kvp.domain.Developer;
import com.kvp.domain.Introduce;
import com.kvp.domain.PurchaseCustomer;
import com.kvp.domain.WorkLog;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

//https://docs.spring.io/spring-kafka/docs/current/reference/html/#kafka-template
@Configuration
public class KafkaProducerConfiguration {
    public ProducerFactory<String, Introduce> introduceProducerFactory() {
        return new DefaultKafkaProducerFactory<>(getPropsWithStringKeyAndJsonValue());
    }

    @Bean
    public KafkaTemplate<String, Introduce> introduceKafkaTemplateTemplate() {
        return new KafkaTemplate<>(introduceProducerFactory());
    }

    public ProducerFactory<String, Developer> developerProducerFactory() {
        return new DefaultKafkaProducerFactory<>(getPropsWithStringKeyAndJsonValue());
    }

    @Bean
    public KafkaTemplate<String, Developer> developerKafkaTemplate() {
        return new KafkaTemplate<>(developerProducerFactory());
    }

    public ProducerFactory<String, PurchaseCustomer> purchaseCustomerProducerFactory() {
        return new DefaultKafkaProducerFactory<>(getPropsWithStringKeyAndJsonValue());
    }

    @Bean
    public KafkaTemplate<String, PurchaseCustomer> purchaseCustomerKafkaTemplate() {
        return new KafkaTemplate<>(purchaseCustomerProducerFactory());
    }

    public ProducerFactory<Long, WorkLog> workLogProducerFactory() {
        return new DefaultKafkaProducerFactory<>(getPropsWithStringKeyAndJsonValue());
    }

    @Bean
    public KafkaTemplate<Long, WorkLog> workLogKafkaTemplate() {
        return new KafkaTemplate<>(workLogProducerFactory());
    }

    private static Map<String, Object> getPropsWithStringKeyAndJsonValue() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return props;
    }
}
