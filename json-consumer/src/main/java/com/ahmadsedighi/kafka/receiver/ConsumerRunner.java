package com.ahmadsedighi.kafka.receiver;

import com.ahmadsedighi.kafka.receiver.service.OrderPayloadDeserializer;
import com.ahmadsedighi.kafka.receiver.service.OrderService;
import com.ahmadsedighi.kafka.receiver.service.PipelinedReceiver;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Map;
import java.util.Set;

/**
 * @author Ahmad R. Seddighi (ahmadseddighi@yahoo.com)
 * Date: 20/06/2022
 * Time: 15:30
 */

public class ConsumerRunner {
    public static void main_(String[] args) throws InterruptedException {
        final var consumerConfig =
                Map.<String, Object>of(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092",
                        ConsumerConfig.GROUP_ID_CONFIG, "order-consumer",
                        ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false
//                        ,ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"
                );

        try (var receiver = new PipelinedReceiver(consumerConfig, "order-topic", Duration.ofMillis(100), 10)) {
            new OrderService(receiver);
            receiver.start();
            Thread.sleep(10_000);
        }
    }
    public static void main(String[] args) {
        final var topic = "order-topic";
        final Map<String, Object> config = Map.of(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092",
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName(),
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, OrderPayloadDeserializer.class.getName(),
                ConsumerConfig.GROUP_ID_CONFIG, "basic-consumer-sample",
//                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest",
                ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        try (var consumer = new KafkaConsumer<String, String>(config)) {
            consumer.subscribe(Set.of(topic));
            while (true) {
                final var records = consumer.poll(Duration.ofMillis(127000));
                for (var record : records) {
                    System.out.format("Got record with value %s%n", record.value());
                }
                consumer.commitAsync();
            }
        }
    }
}
