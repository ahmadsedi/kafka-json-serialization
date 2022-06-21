package com.ahmadsedighi.kafka.sender.service;

import com.ahmadsedighi.kafka.sender.event.OrderPayload;

import java.util.Date;
import java.util.UUID;

/**
 * @author Ahmad R. Seddighi (ahmadseddighi@yahoo.com)
 * Date: 20/06/2022
 * Time: 11:36
 */

public class OrderService {
    private final EventSender eventSender;

    public OrderService(EventSender eventSender) {
        this.eventSender = eventSender;
    }

    public void send(String merchandise, String user) throws InterruptedException, EventSender.SendException {
        send(new OrderPayload(UUID.randomUUID(), new Date(), merchandise, user));
    }
    private void send(OrderPayload orderPayload) throws EventSender.SendException, InterruptedException {
        System.out.format("Publishing %s%n", orderPayload);
        eventSender.blockingSend(orderPayload);
    }
}
