package com.kvp.streams;

import com.kvp.domain.Developer;
import com.kvp.domain.Introduce;
import com.kvp.domain.Language;
import com.kvp.domain.PurchaseCustomer;
import com.kvp.streams.serdes.*;
import com.kvp.streams.transformer.CustomerTransformer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueBytesStoreSupplier;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.StoreBuilder;
import org.apache.kafka.streams.state.Stores;

import java.util.Properties;

//https://coding-start.tistory.com/138
public class StreamsApplication {
    public static void main(String[] args) {
        Serde<String> stringSerde= Serdes.String();
        IntroduceSerde introduceSerde = new IntroduceSerde();
        DeveloperSerde developerSerde = new DeveloperSerde();
        SimpleDeveloperSerde simpleDeveloperSerde = new SimpleDeveloperSerde();
        PurchaseCustomerSerde purchaseCustomerSerde = new PurchaseCustomerSerde();
        CustomerSerde customerSerde = new CustomerSerde();

        StreamsBuilder streamsBuilder = new StreamsBuilder();

        // step1
        KStream<String, Introduce> changeIntroduceKStream = streamsBuilder.stream("kvp-input", Consumed.with(stringSerde, introduceSerde))
                .mapValues((introduce) -> {
                    Introduce copiedIntroduce = introduce.toBuilder().build();
                    copiedIntroduce.maskingName();
                    copiedIntroduce.addAge();
                    return copiedIntroduce;
                });

        changeIntroduceKStream.to("kvp-output", Produced.with(stringSerde, introduceSerde));

        // step2
        KStream<String, Developer> developerConsumeStream = streamsBuilder.stream("developer", Consumed.with(stringSerde, developerSerde));

        developerConsumeStream.print(Printed.<String, Developer>toSysOut().withLabel("[개발자다]"));

        Predicate<String, Developer> isJunior = (key, developer) -> developer.isJunior();
        Predicate<String, Developer> isSenior = (key, developer) -> developer.isSenior();
        KStream<String, Developer>[] developerStreamByYear = developerConsumeStream.branch(isJunior, isSenior);
        KStream<String, Developer> juniorDeveloperStream = developerStreamByYear[0];
        KStream<String, Developer> seniorDeveloperStream = developerStreamByYear[1];

        juniorDeveloperStream.to("junior-developer", Produced.with(stringSerde, developerSerde));


        seniorDeveloperStream.to("senior-developer", Produced.with(stringSerde, developerSerde));
        seniorDeveloperStream.filter((key, developer) -> developer.compare(Language.JAVA))
                .mapValues((developer) -> developer.toSimple())
                .to("senior-java-developer", Produced.with(stringSerde, simpleDeveloperSerde));

        // step3
        KStream<String, PurchaseCustomer> purchaseCustomerKStream = streamsBuilder.stream("purchase-customer", Consumed.with(stringSerde, purchaseCustomerSerde));

        String customerStateStoreName = "customerStateStore";
        KeyValueBytesStoreSupplier customerStoreSupplier = Stores.inMemoryKeyValueStore(customerStateStoreName);
        StoreBuilder<KeyValueStore<String, Long>> customerStoreBuilder = Stores.keyValueStoreBuilder(customerStoreSupplier, stringSerde, Serdes.Long())
                .withLoggingDisabled();
        streamsBuilder.addStateStore(customerStoreBuilder);
        purchaseCustomerKStream.print(Printed.<String, PurchaseCustomer>toSysOut().withLabel("[구매고객이다]"));

        purchaseCustomerKStream.transformValues(() -> new CustomerTransformer(customerStateStoreName), customerStateStoreName)
                .to("customer", Produced.with(stringSerde, customerSerde));

        KafkaStreams kafkaStreams = new KafkaStreams(streamsBuilder.build(), getProperties());
        kafkaStreams.start();
    }

    private static Properties getProperties() {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "kvp_streams_id");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        return props;
    }
}
