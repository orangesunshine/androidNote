package com.example.eventbus.controller.container;

import android.content.Context;

import com.example.eventbus.controller.Subscriber;
import com.google.inject.ImplementedBy;

import java.util.List;
import java.util.Set;

/**
 * Created by yhuang115 on 2018/4/26.
 */
@ImplementedBy(SubscriberContainerImpl.class)
public interface SubscriberContainer {
    void addSubscriber(Subscriber subscriber);

    void removeSubcriber(Object object);

    List<Subscriber> getSubscribers();

    Set<Class<?>> getClassFactory(Context context, String pack);
}
