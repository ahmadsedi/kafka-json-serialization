package com.ahmadsedighi.kafka.sender.event;

import java.util.Date;
import java.util.UUID;

/**
 * @author Ahmad R. Seddighi (ahmadseddighi@yahoo.com)
 * Date: 20/06/2022
 * Time: 11:20
 */

public final class OrderPayload extends EventPayload{
    private Date orderDate;
    private String merchandise;
    private String user;

    public OrderPayload(UUID id, Date orderDate, String merchandise, String user) {
        super(id);
        this.orderDate = orderDate;
        this.merchandise = merchandise;
        this.user = user;
    }

    public OrderPayload(UUID id) {
        super(id);
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

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public void setMerchandise(String merchandise) {
        this.merchandise = merchandise;
    }

    public void setUser(String user) {
        this.user = user;
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
