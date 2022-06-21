package com.ahmadsedighi.kafka.receiver.service;

import java.io.Closeable;

/**
 * @author Ahmad R. Seddighi (ahmadseddighi@yahoo.com)
 * Date: 20/06/2022
 * Time: 14:10
 */

public interface EventReceiver extends Closeable {
    void addListener(EventListener listener);

    void start();

    @Override
    void close();
}

