package com.example.eventbus.controller;

import com.google.inject.ImplementedBy;

/**
 * Created by yhuang115 on 2018/4/26.
 */
@ImplementedBy(DefaultEventBus.class)
public interface EventBus {
    void post(Event event);

    void register(Object object);

    void unregister(Object object);
}
