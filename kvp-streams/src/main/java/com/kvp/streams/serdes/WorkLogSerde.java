package com.kvp.streams.serdes;

import com.kvp.domain.WorkLog;
import org.apache.kafka.common.serialization.Serdes;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

public class WorkLogSerde extends Serdes.WrapperSerde<WorkLog> {
    public WorkLogSerde() {
        super(new JsonSerializer<>(), new JsonDeserializer<>(WorkLog.class));
    }
}
