package com.ahmadsedighi.kafka.receiver.service;

import com.ahmadsedighi.kafka.receiver.event.EventPayloadWrapper;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.InterruptException;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Ahmad R. Seddighi (ahmadseddighi@yahoo.com)
 * Date: 20/06/2022
 * Time: 14:11
 */

public class PipelinedReceiver extends AbstractReceiver {

    private final Consumer<String, EventPayloadWrapper> consumer;

    private final Duration pollTimeout;

    private final String topic;

    public PipelinedReceiver(Map<String, Object> consumerConfig,
                             String topic,
                             Duration pollTimeout) {
        this.pollTimeout = pollTimeout;

        final var mergedConfig = new HashMap<String, Object>();
        mergedConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        mergedConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, OrderPayloadDeserializer.class.getName());
        mergedConfig.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        mergedConfig.putAll(consumerConfig);
        consumer = new KafkaConsumer<>(mergedConfig);
        this.topic = topic;
    }

    @Override
    public void start() {
            consumer.subscribe(Set.of(topic));
            System.out.println("Start Processing");
            while (true) {
                final var records = consumer.poll(pollTimeout);
                for (var record : records) {
                    System.out.format("Got record with value %s%n", record.value());
                }
                consumer.commitAsync();
            }
    }

    @Override
    public void close() {
        consumer.close();
    }
}

