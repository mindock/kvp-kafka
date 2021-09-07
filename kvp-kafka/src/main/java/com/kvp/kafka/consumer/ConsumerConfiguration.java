package com.kvp.kafka.consumer;

import com.kvp.domain.*;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
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
        return new DefaultKafkaConsumerFactory<>(
                getConfigs("kvp"),
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
        return new DefaultKafkaConsumerFactory<>(
                getConfigs("developer"),
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
        return new DefaultKafkaConsumerFactory<>(
                getConfigs("simple-developer"),
                new StringDeserializer(),
                new JsonDeserializer<>(SimpleDeveloper.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, SimpleDeveloper> simpleDeveloperListener() {
        ConcurrentKafkaListenerContainerFactory<String, SimpleDeveloper> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(simpleDeveloperConsumerConfigs());
        return factory;
    }


    public ConsumerFactory<String, Customer> customerConsumerConfigs() {
        return new DefaultKafkaConsumerFactory<>(
                getConfigs("customer"),
                new StringDeserializer(),
                new JsonDeserializer<>(Customer.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Customer> customerListener() {
        ConcurrentKafkaListenerContainerFactory<String, Customer> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(customerConsumerConfigs());
        return factory;
    }

    public ConsumerFactory<Long, DailyWorkLog> dailyWorkLogConsumerConfigs() {
        return new DefaultKafkaConsumerFactory<>(
                getConfigs("work-log"),
                new LongDeserializer(),
                new JsonDeserializer<>(DailyWorkLog.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<Long, DailyWorkLog> dailyWorkLogListener() {
        ConcurrentKafkaListenerContainerFactory<Long, DailyWorkLog> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(dailyWorkLogConsumerConfigs());
        return factory;
    }

    public ConsumerFactory<Long, OverWorkLog> overWorkLogConsumerConfigs() {
        return new DefaultKafkaConsumerFactory<>(
                getConfigs("work-log"),
                new LongDeserializer(),
                new JsonDeserializer<>(OverWorkLog.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<Long, OverWorkLog> overWorkLogListener() {
        ConcurrentKafkaListenerContainerFactory<Long, OverWorkLog> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(overWorkLogConsumerConfigs());
        return factory;
    }

    private static Map<String, Object> getConfigs(String groupId) {
        Map<String, Object> configs = new HashMap<>();
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configs.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        configs.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        return configs;
    }
}
