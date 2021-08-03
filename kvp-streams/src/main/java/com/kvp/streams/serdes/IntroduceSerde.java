package com.kvp.streams.serdes;

import com.kvp.domain.Introduce;
import org.apache.kafka.common.serialization.Serdes;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

public class IntroduceSerde extends Serdes.WrapperSerde<Introduce> {
    public IntroduceSerde() {
        super(new JsonSerializer<>(), new JsonDeserializer<>(Introduce.class));
    }
}
