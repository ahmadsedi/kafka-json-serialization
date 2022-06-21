package com.ahmadsedighi.kafka.receiver.event;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.UUID;

/**
 * @author Ahmad R. Seddighi (ahmadseddighi@yahoo.com)
 * Date: 20/06/2022
 * Time: 11:20
 */

public final class OrderPayload extends EventPayload {
    private final Date orderDate;
    private final String merchandise;
    private final String user;

    public OrderPayload(@JsonProperty("id")UUID id, @JsonProperty("order_date")Date orderDate,
                        @JsonProperty("merchandise")String merchandise, @JsonProperty("user")String user) {
        super(id);
        this.orderDate = orderDate;
        this.merchandise = merchandise;
        this.user = user;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public String getMerchandise() {
        return merchandise;
    }

    public String getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "OrderPayload{" +
                "orderDate=" + orderDate +
                ", merchandise='" + merchandise + '\'' +
                ", user='" + user + '\'' +
                '}';
    }
}
