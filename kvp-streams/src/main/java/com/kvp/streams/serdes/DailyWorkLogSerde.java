package com.kvp.streams.serdes;

import com.kvp.domain.DailyWorkLog;
import org.apache.kafka.common.serialization.Serdes;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

public class DailyWorkLogSerde extends Serdes.WrapperSerde<DailyWorkLog> {
    public DailyWorkLogSerde() {
        super(new JsonSerializer<>(), new JsonDeserializer<>(DailyWorkLog.class));
    }
}
