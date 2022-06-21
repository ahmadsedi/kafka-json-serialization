package com.ahmadsedighi.kafka.receiver.service;

/**
 * @author Ahmad R. Seddighi (ahmadseddighi@yahoo.com)
 * Date: 20/06/2022
 * Time: 12:53
 */
@FunctionalInterface
public interface EventListener {
    void onEvent(Event event);
}
