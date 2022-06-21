package com.ahmadsedighi.kafka.sender.service;

import com.ahmadsedighi.kafka.sender.event.OrderPayload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;

/**
 * @author Ahmad R. Seddighi (ahmadseddighi@yahoo.com)
 * Date: 20/06/2022
 * Time: 12:34
 */

public class OrderPayloadSerializer implements Serializer<OrderPayload> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final class MarshallingException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        private MarshallingException(Throwable cause) { super(cause); }
    }

    @Override
    public byte[] serialize(String topic, OrderPayload data) {
        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (JsonProcessingException e) {
            throw new MarshallingException(e);
        }
    }
}
