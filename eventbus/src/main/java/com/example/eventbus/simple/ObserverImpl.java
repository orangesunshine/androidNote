package com.example.eventbus.simple;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by yhuang115 on 2018/4/25.
 */

public abstract class ObserverImpl implements Observer {
    private String name;
    private final String TAG = ObserverImpl.class.getSimpleName();
    private List<Event> eventList = new ArrayList<>();

    public ObserverImpl(String name) {
        this.name = name;
    }

    @Override
    public void addEvent(Event event) {
        if (null == event) {
            throw new NullPointerException("event 不能为空");
        }
        if (eventList.contains(event)) {
            Log.d(TAG, event + " Already exist!");
            return;
        }
        eventList.add(event);
    }

    @Override
    public void notice(Event event) {
        if (null == event) {
            throw new NullPointerException("event 不能为空");
        }
        for (Event e : eventList) {
            if (e.equals(event)) {
                receiver(event);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ObserverImpl observer = (ObserverImpl) o;

        return name != null ? name.equals(observer.name) : observer.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
