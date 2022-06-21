package com.ahmadsedighi.kafka.sender.service;

import com.ahmadsedighi.kafka.sender.event.EventPayload;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.io.Closeable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author Ahmad R. Seddighi (ahmadseddighi@yahoo.com)
 * Date: 20/06/2022
 * Time: 11:30
 */

public interface EventSender<T extends EventPayload> extends Closeable {

    Future<RecordMetadata> send(T payload);

    final class SendException extends Exception{
        private static final long serialVersionUID = 1L;
        SendException(Throwable tr) {super(tr);}
    }

    default RecordMetadata blockingSend(T eventPayload) throws InterruptedException, SendException {
        try{
            return send(eventPayload).get();
        }  catch (ExecutionException e) {
            throw new SendException(e);
        }
    }
}
