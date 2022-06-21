package com.ahmadsedighi.kafka.receiver.service;

import com.ahmadsedighi.kafka.receiver.event.EventPayloadWrapper;
import com.ahmadsedighi.kafka.receiver.event.OrderPayload;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

/**
 * @author Ahmad R. Seddighi (ahmadseddighi@yahoo.com)
 * Date: 20/06/2022
 * Time: 15:25
 */

public class OrderPayloadDeserializer implements Deserializer<EventPayloadWrapper<OrderPayload>> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public EventPayloadWrapper deserialize(String topic, byte[] data) {
        final var value = new String(data);
        try {
            final var payload = objectMapper.readValue(value, OrderPayload.class);
            return new EventPayloadWrapper(payload, null, value);
        } catch (Throwable e) {
            System.out.format("error in deserialization: 5s", e.getMessage());
            return new EventPayloadWrapper(null, e, value);
        }
    }
}

