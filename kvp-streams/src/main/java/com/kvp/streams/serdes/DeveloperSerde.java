package com.kvp.streams.serdes;

import com.kvp.domain.Developer;
import org.apache.kafka.common.serialization.Serdes;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

public class DeveloperSerde extends Serdes.WrapperSerde<Developer> {
    public DeveloperSerde() {
        super(new JsonSerializer<>(), new JsonDeserializer<>(Developer.class));
    }
}
