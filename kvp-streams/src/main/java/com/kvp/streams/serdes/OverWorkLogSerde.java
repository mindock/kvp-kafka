package com.kvp.streams.serdes;

import com.kvp.domain.OverWorkLog;
import org.apache.kafka.common.serialization.Serdes;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

public class OverWorkLogSerde extends Serdes.WrapperSerde<OverWorkLog> {
    public OverWorkLogSerde() {
        super(new JsonSerializer<>(), new JsonDeserializer<>(OverWorkLog.class));
    }
}