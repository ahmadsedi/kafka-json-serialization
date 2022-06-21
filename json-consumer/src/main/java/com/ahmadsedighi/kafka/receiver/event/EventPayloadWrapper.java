package com.ahmadsedighi.kafka.receiver.event;

import com.ahmadsedighi.kafka.receiver.event.EventPayload;

/**
 * @author Ahmad R. Seddighi (ahmadseddighi@yahoo.com)
 * Date: 20/06/2022
 * Time: 14:49
 */

public class EventPayloadWrapper<T extends EventPayload> {
    private final T payload;

    private final Throwable error;

    private String encodedValue;

    public EventPayloadWrapper(T payload, Throwable error, String encodedValue) {
        this.payload = payload;
        this.error = error;
        this.encodedValue = encodedValue;
    }

    public T getPayload() {
        return payload;
    }

    public Throwable getError() {
        return error;
    }

    public String getEncodedValue() {
        return encodedValue;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " [payload=" + payload +
                ", error=" + error + ", encodedValue=" + encodedValue + "]";
    }
}
