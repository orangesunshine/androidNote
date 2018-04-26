package com.example.eventbus.simple;

import com.google.inject.ImplementedBy;

/**
 * Created by yhuang115 on 2018/4/25.
 */
public interface EventBus {
    void register(Event event, Observer observer);

    void unregister(Event event, Observer observer);

    void post(Event event);
}
