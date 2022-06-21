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
//    private final Thread pollingThread;
//
//    private final Thread processingThread;

    private final Consumer<String, EventPayloadWrapper> consumer;

    private final Duration pollTimeout;

    private final BlockingQueue<Event> receivedEvents;

    private final Queue<Map<TopicPartition, OffsetAndMetadata>> pendingOffsets = new LinkedBlockingQueue<>();

    private boolean active = true;
    private final String topic ;

    public PipelinedReceiver(Map<String, Object> consumerConfig,
                             String topic,
                             Duration pollTimeout,
                             int queueCapacity) {
        this.pollTimeout = pollTimeout;
        receivedEvents = new LinkedBlockingQueue<>(queueCapacity);

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
        try {
            consumer.subscribe(Set.of(topic));
            while (active) {
                this.onPollCycle();
//                this.onProcessCycle();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void onPollCycle() throws InterruptedException {
        final ConsumerRecords<String, EventPayloadWrapper> records;

        try {
            records = consumer.poll(pollTimeout);
        } catch (InterruptException e) {
            throw new InterruptedException("Interrupted during poll");
        }

//        if (!records.isEmpty()) {
            for (var record : records) {
                final var value = record.value();
                final var event = new Event(value.getPayload(), value.getError(), record, value.getEncodedValue());
                System.out.format("Received:%s", event);
                receivedEvents.put(event);
            }
//        }else{
//            System.out.println("Empty records!");
//        }

        for (Map<TopicPartition, OffsetAndMetadata> pendingOffset;
             (pendingOffset = pendingOffsets.poll()) != null; ) {
            System.out.format("Committing %d records", pendingOffset.size());
            consumer.commitAsync(pendingOffset, null);
        }
    }

    private void onProcessCycle() throws InterruptedException {
        final var event = receivedEvents.take();
        fire(event);
        final var record = event.getRecord();
        pendingOffsets.add(Map.of(new TopicPartition(record.topic(), record.partition()),
                new OffsetAndMetadata(record.offset() + 1)));
    }

    @Override
    public void close() {
        this.active = false;
        consumer.close();
    }
}

