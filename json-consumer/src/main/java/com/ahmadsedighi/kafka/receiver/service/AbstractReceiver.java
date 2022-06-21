package com.ahmadsedighi.kafka.receiver.service;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Ahmad R. Seddighi (ahmadseddighi@yahoo.com)
 * Date: 20/06/2022
 * Time: 14:10
 */

public abstract class AbstractReceiver implements EventReceiver {
    private final Set<EventListener> listeners = new HashSet<>();

    public final void addListener(EventListener listener) {
        listeners.add(listener);
    }

    protected final void fire(Event event) {
        for (var listener : listeners) {
            listener.onEvent(event);
        }
    }
}
