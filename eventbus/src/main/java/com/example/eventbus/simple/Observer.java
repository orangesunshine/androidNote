package com.example.eventbus.simple;

import com.google.inject.ImplementedBy;

/**
 * Created by yhuang115 on 2018/4/25.
 */
@ImplementedBy(ObserverImpl.class)
public interface Observer {
    void addEvent(Event event);

    void notice(Event event);

    void receiver(Event event);
}
