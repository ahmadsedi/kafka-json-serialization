package com.ahmadsedighi.kafka.sender.event;

import java.util.UUID;

/**
 * @author Ahmad R. Seddighi (ahmadseddighi@yahoo.com)
 * Date: 20/06/2022
 * Time: 12:13
 */

public class EventPayload {
    private final UUID id;

    public EventPayload(UUID id) {
        this.id=id;
    }

    public UUID getId() {
        return id;
    }
}
