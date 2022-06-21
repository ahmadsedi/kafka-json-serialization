package com.ahmadsedighi.kafka.sender;

import com.ahmadsedighi.kafka.sender.service.EventSender;
import com.ahmadsedighi.kafka.sender.service.OrderEventSender;
import com.ahmadsedighi.kafka.sender.service.OrderService;
import org.apache.kafka.clients.producer.ProducerConfig;

import java.io.IOException;
import java.util.Map;

/**
 * @author Ahmad R. Seddighi (ahmadseddighi@yahoo.com)
 * Date: 20/06/2022
 * Time: 12:40
 */

public class OrderGenerator {
    private static String[] merchandises = {"Bag", "Computer", "Mobile", "Book", "Mouse", "Hands-Free", "Charger", "Watch"};
    private static String[] users = {"John", "Adam", "Jerald", "Mary", "Jennifer", "Thomas", "Lisa", "Nancy"};

    public static void main(String[] args) throws InterruptedException, EventSender.SendException, IOException {
        final Map<String, Object> config = Map.of(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"kafka:9092",
                ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true,
                ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 127000);
        final var topic = "order-topic";
        try (var sender = new OrderEventSender(topic, config)) {
            final var orderService = new OrderService(sender);
            for (int i = 0; ; i++) {
                orderService.send(merchandises[i % 8], users[i % 8]);
                Thread.sleep(500);
            }
        }
    }

}
