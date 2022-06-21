package com.ahmadsedighi.kafka.receiver.service;

/**
 * @author Ahmad R. Seddighi (ahmadseddighi@yahoo.com)
 * Date: 20/06/2022
 * Time: 15:31
 */

public class OrderService {

    public OrderService(EventReceiver receiver) {
        receiver.addListener(this::onEvent);
    }

    private void onEvent(Event event) {
        if (! event.isError()) {
            System.out.format("Received %s%n", event.getPayload());
        } else {
            System.err.format("Error in record %s: %s%n", event.getRecord(), event.getError());
        }
    }
}
