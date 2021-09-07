package com.kvp.streams;

import com.kvp.domain.*;
import com.kvp.streams.joiner.WorkLogJoiner;
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

import java.time.Duration;
import java.util.Properties;

//https://coding-start.tistory.com/138
public class StreamsApplication {
    public static void main(String[] args) {
        Serde<String> stringSerde= Serdes.String();
        Serde<Long> longSerde = Serdes.Long();
        IntroduceSerde introduceSerde = new IntroduceSerde();
        DeveloperSerde developerSerde = new DeveloperSerde();
        SimpleDeveloperSerde simpleDeveloperSerde = new SimpleDeveloperSerde();
        PurchaseCustomerSerde purchaseCustomerSerde = new PurchaseCustomerSerde();
        CustomerSerde customerSerde = new CustomerSerde();
        WorkLogSerde workLogSerde = new WorkLogSerde();
        DailyWorkLogSerde dailyWorkLogKSerde = new DailyWorkLogSerde();
        OverWorkLogSerde overWorkLogKSerde = new OverWorkLogSerde();

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

        // step4
        KStream<Long, WorkLog> workLogKStream = streamsBuilder.stream("work-log", Consumed.with(longSerde, workLogSerde));

        Predicate<Long, WorkLog> isWork = (key, workLog) -> workLog.isWork();
        Predicate<Long, WorkLog> isOffWork = (key, workLog) -> workLog.isOffWork();
        KStream<Long, WorkLog>[] workLogStreamByType = workLogKStream
                .selectKey((key, value) -> value.getEmployee().getNo())
                .branch(isWork, isOffWork);
        KStream<Long, WorkLog> workStream = workLogStreamByType[0];
        KStream<Long, WorkLog> offWorkStream = workLogStreamByType[1];

        ValueJoiner<WorkLog, WorkLog, DailyWorkLog> workLogJoiner = new WorkLogJoiner();
        JoinWindows twentyFourHourWindow = JoinWindows.of(Duration.ofHours(24));

        KStream<Long, DailyWorkLog> dailyWorkLogKStream = workStream.join(offWorkStream,
                        workLogJoiner,
                        twentyFourHourWindow.after(Duration.ofHours(24)),
                        StreamJoined.with(longSerde, workLogSerde, workLogSerde));
        dailyWorkLogKStream.to("daily-work-log", Produced.with(longSerde, dailyWorkLogKSerde));

        KStream<Long, OverWorkLog> overWorkLogKStream = dailyWorkLogKStream.filter((key, value) -> value.isOverWork())
                .mapValues((dailyWorkLog) -> new OverWorkLog(dailyWorkLog.getEmployee(), dailyWorkLog.getWorkDate(), dailyWorkLog.getOverWorkTime()));
        overWorkLogKStream.to("over-work-log", Produced.with(longSerde, overWorkLogKSerde));

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
