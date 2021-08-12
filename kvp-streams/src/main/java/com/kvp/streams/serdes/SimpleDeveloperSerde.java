package com.kvp.streams.serdes;

import com.kvp.domain.SimpleDeveloper;
import org.apache.kafka.common.serialization.Serdes;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

public class SimpleDeveloperSerde extends Serdes.WrapperSerde<SimpleDeveloper> {
    public SimpleDeveloperSerde() {
        super(new JsonSerializer<>(), new JsonDeserializer<>(SimpleDeveloper.class));
    }
}
