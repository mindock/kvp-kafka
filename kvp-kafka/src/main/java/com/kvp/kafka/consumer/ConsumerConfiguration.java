package com.kvp.kafka.consumer;

import com.kvp.domain.Developer;
import com.kvp.domain.Introduce;
import com.kvp.domain.SimpleDeveloper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ConsumerConfiguration {
    public ConsumerFactory<String, Introduce> introduceConsumerConfigs() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configs.put(ConsumerConfig.GROUP_ID_CONFIG, "kvp");
        configs.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        return new DefaultKafkaConsumerFactory<>(
                configs,
                new StringDeserializer(),
                new JsonDeserializer<>(Introduce.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Introduce> introduceListener() {
        ConcurrentKafkaListenerContainerFactory<String, Introduce> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(introduceConsumerConfigs());
        return factory;
    }

    public ConsumerFactory<String, Developer> developerConsumerConfigs() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configs.put(ConsumerConfig.GROUP_ID_CONFIG, "developer");
        configs.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        return new DefaultKafkaConsumerFactory<>(
                configs,
                new StringDeserializer(),
                new JsonDeserializer<>(Developer.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Developer> developerListener() {
        ConcurrentKafkaListenerContainerFactory<String, Developer> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(developerConsumerConfigs());
        return factory;
    }

    public ConsumerFactory<String, SimpleDeveloper> simpleDeveloperConsumerConfigs() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configs.put(ConsumerConfig.GROUP_ID_CONFIG, "simple-developer");
        configs.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        return new DefaultKafkaConsumerFactory<>(
                configs,
                new StringDeserializer(),
                new JsonDeserializer<>(SimpleDeveloper.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, SimpleDeveloper> simpleDeveloperListener() {
        ConcurrentKafkaListenerContainerFactory<String, SimpleDeveloper> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(simpleDeveloperConsumerConfigs());
        return factory;
    }
}
