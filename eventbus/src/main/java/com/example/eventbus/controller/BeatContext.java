package com.example.eventbus.controller;

import com.google.inject.ImplementedBy;

/**
 * Created by yhuang115 on 2018/4/26.
 */
@ImplementedBy(BeatContextImpl.class)
public interface BeatContext {
    void setEvent(Event event);
    Event getEvent();
    void clean();
}
