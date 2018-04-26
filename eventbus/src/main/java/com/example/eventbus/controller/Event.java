package com.example.eventbus.controller;

import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by yhuang115 on 2018/4/25.
 */

public class Event<T> {
    private Class receiver;
    private Object[] paramsTypes;
    private T body;
    private AtomicInteger seq = new AtomicInteger(0);

    public Event(Object... paramsTypes) {
        this(null, paramsTypes);
    }

    public Event(T body, Object... paramsTypes) {
        this(null, body, paramsTypes);
    }

    public Event(Class receiver, T body, Object... paramsTypes) {
        this.receiver = receiver;
        this.body = body;
        seq.incrementAndGet();
        this.paramsTypes = paramsTypes;
    }

    public void setReceiver(Class receiver) {
        this.receiver = receiver;
    }

    public void setParamsTypes(Class<?>[] paramsTypes) {
        this.paramsTypes = paramsTypes;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public Class getReceiver() {
        return receiver;
    }

    public int getSeq() {
        return seq.get();
    }

    public Object[] getParamsTypes() {
        return paramsTypes;
    }

    public T getBody() {
        return body;
    }
}
