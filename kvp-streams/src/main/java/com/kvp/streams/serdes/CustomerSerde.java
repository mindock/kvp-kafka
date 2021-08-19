package com.kvp.streams.serdes;

import com.kvp.domain.Customer;
import org.apache.kafka.common.serialization.Serdes;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

public class CustomerSerde extends Serdes.WrapperSerde<Customer> {
    public CustomerSerde() {
        super(new JsonSerializer<>(), new JsonDeserializer<>(Customer.class));
    }
}
