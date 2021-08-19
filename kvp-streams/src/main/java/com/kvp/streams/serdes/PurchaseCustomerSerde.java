package com.kvp.streams.serdes;

import com.kvp.domain.PurchaseCustomer;
import org.apache.kafka.common.serialization.Serdes;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

public class PurchaseCustomerSerde extends Serdes.WrapperSerde<PurchaseCustomer> {
    public PurchaseCustomerSerde() {
        super(new JsonSerializer<>(), new JsonDeserializer<>(PurchaseCustomer.class));
    }
}
