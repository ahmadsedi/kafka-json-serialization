package com.ahmadsedighi.kafka.receiver.service;

import com.ahmadsedighi.kafka.receiver.event.EventPayload;
import org.apache.kafka.clients.consumer.ConsumerRecord;

/**
 * @author Ahmad R. Seddighi (ahmadseddighi@yahoo.com)
 * Date: 20/06/2022
 * Time: 14:06
 */

public class Event<E extends EventPayload> {
    private final E payload;
    private final Throwable error;
    private final ConsumerRecord<String, ?> record;
    private final String encodedValue;

    public Event(E payload, Throwable error, ConsumerRecord<String, ?> record, String encodedValue) {
        this.payload = payload;
        this.error = error;
        this.record = record;
        this.encodedValue = encodedValue;
    }

    public boolean isError() {
        return error != null;
    }

    public E getPayload() {
        return payload;
    }

    public Throwable getError() {
        return error;
    }

    public ConsumerRecord<String, ?> getRecord() {
        return record;
    }

    public String getEncodedValue() {
        return encodedValue;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " [payload=" + payload + ", error=" + error +
                ", record=" + record + ", encodedValue=" + encodedValue + "]";
    }
}
