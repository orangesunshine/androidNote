package com.example.eventbus.controller.container;

import android.content.Context;

import com.example.eventbus.controller.Subscriber;
import com.example.hyutils.AndroidUtils;
import com.google.inject.Singleton;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by yhuang115 on 2018/4/26.
 */
@Singleton
public class SubscriberContainerImpl implements SubscriberContainer {
    private List<Subscriber> subcribers = new CopyOnWriteArrayList<>();
    private Set<Class<?>> classFactory;

    @Override
    public void addSubscriber(Subscriber subscriber) {
        if (null == subscriber) {
            throw new NullPointerException(" subscriber 为空！");
        }

//        Method method = subscriber.getMethod();
//        if (null == method || method.getParameterTypes().length != 1) {
//            throw new IllegalArgumentException("method 错误!");
//        }

        if (subcribers.contains(subscriber)) {
            System.err.println(subscriber + " Already exist!");
            return;
        }
        subcribers.add(subscriber);
    }

    @Override
    public void removeSubcriber(Object object) {
        Iterator<Subscriber> iterator = subcribers.iterator();
        while (iterator.hasNext()) {
            Subscriber next = iterator.next();
            if (next.getReceiver() == object.getClass()) {
                iterator.remove();
            }
        }
    }

    @Override
    public List<Subscriber> getSubscribers() {
        return subcribers;
    }

    @Override
    public Set<Class<?>> getClassFactory(Context context, String pack) {
        if (null == classFactory) {
            classFactory = AndroidUtils.getClasses(pack, context);
        }
        return classFactory;
    }
}
