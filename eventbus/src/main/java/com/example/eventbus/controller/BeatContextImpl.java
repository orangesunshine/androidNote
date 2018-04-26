package com.example.eventbus.controller;

import com.google.inject.Singleton;

/**
 * Created by yhuang115 on 2018/4/26.
 */
@Singleton
public class BeatContextImpl implements BeatContext {
    private ThreadLocal<Event> threadLocal = new ThreadLocal<>();

    @Override
    public void setEvent(Event event) {
        if (null == event) {
            throw new NullPointerException("event 不能为空！");
        }
        threadLocal.set(event);
    }

    @Override
    public Event getEvent() {
        return threadLocal.get();
    }

    @Override
    public void clean() {
        threadLocal.remove();
    }
}
