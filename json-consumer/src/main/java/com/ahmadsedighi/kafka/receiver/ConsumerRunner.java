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
    public static void main(String[] args) throws InterruptedException {
        final var consumerConfig =
                Map.<String, Object>of(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092",
                        ConsumerConfig.GROUP_ID_CONFIG, "order-consumer-group",
                        ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false
//                        ,ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"
                );

        try (var receiver = new PipelinedReceiver(consumerConfig, "order-topic", Duration.ofMillis(100), 10)) {
            new OrderService(receiver);
            receiver.start();
            Thread.sleep(10_000);
        }
    }
}
