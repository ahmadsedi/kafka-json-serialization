package com.ahmadsedighi.kafka.sender.service;

import com.ahmadsedighi.kafka.sender.event.OrderPayload;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * @author Ahmad R. Seddighi (ahmadseddighi@yahoo.com)
 * Date: 20/06/2022
 * Time: 12:30
 */

public class OrderEventSender implements EventSender<OrderPayload>{

    private final Producer<String, OrderPayload> producer;
    private final String topic;

    public OrderEventSender(String topic, Map<String, Object> producerConfig) {
        this.topic = topic;
        final var mergedConfig = new HashMap<String, Object>();
        mergedConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class.getName());
        mergedConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                OrderPayloadSerializer.class.getName());
        mergedConfig.putAll(producerConfig);
        producer = new KafkaProducer<>(mergedConfig);
    }

    @Override
    public Future<RecordMetadata> send(OrderPayload payload) {
        final var record =
                new ProducerRecord<>(topic, payload.getId().toString(),
                        payload);
        return producer.send(record);
    }

    @Override
    public void close() throws IOException {
        producer.close();
    }
}
