package com.example.eventbus.controller;

import com.example.eventbus.controller.annotation.EventSub;
import com.example.eventbus.controller.container.SubscriberContainer;
import com.google.inject.Singleton;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by yhuang115 on 2018/4/26.
 */
@Singleton
public class DefaultEventBus implements EventBus {
    private static AtomicBoolean once = new AtomicBoolean(true);

    public DefaultEventBus() {
        if (!once.compareAndSet(true, false)) {
            throw new IllegalArgumentException("DefaultEventBus only singleton");
        }
    }

    @Override
    public void post(Event event) {
        if (null == event) {
            throw new NullPointerException("event must not null");
        }
        List<Subscriber> subscribers = GuiceInjector.getInstance(SubscriberContainer.class).getSubscribers();
        for (Subscriber subscriber : subscribers) {
            Object receiver = subscriber.getReceiver();
            if (MatchEnum.MATCH_RECEIVER == subscriber.getMatchEnum() ? receiver.getClass() == event.getReceiver() : true) {

                if (matchMethod(subscriber, event)) {
                    EventSub annotation = subscriber.getMethod().getAnnotation(EventSub.class);
                    if (null == annotation) return;
                    if (ThreadEnum.UI_THREAD == annotation.thread()) {
                        GuiceInjector.getInstance(LocalThreadExecutor.class).uiexe(() -> {
                            exeNotice(event, subscriber);
                        });
                    } else {
                        GuiceInjector.getInstance(LocalThreadExecutor.class).execute(() -> {
                            exeNotice(event, subscriber);
                        });
                    }
                }
            }
        }
    }

    private void exeNotice(Event event, Subscriber subscriber) {
        try {
            GuiceInjector.getInstance(BeatContext.class).setEvent(event);
            subscriber.getMethod().invoke(subscriber.getReceiver(), event.getParamsTypes());
        } catch (Exception e) {
            GuiceInjector.getInstance(BeatContext.class).clean();
            e.printStackTrace();
        }
    }

    private boolean matchMethod(Subscriber subscriber, Event event) {
        Method method = subscriber.getMethod();
        Class<?>[] parameterTypes = method.getParameterTypes();
        Object[] eventParamsTypes = event.getParamsTypes();
        return Arrays.equals(parameterTypes, eventParamsTypes);
    }

    @Override
    public void register(Object object) {
        Method[] methods = object.getClass().getMethods();
        for (Method method : methods) {
            EventSub annotation = method.getAnnotation(EventSub.class);
            if (null != annotation) {
                MatchEnum match = annotation.match();
                GuiceInjector.getInstance(SubscriberContainer.class).addSubscriber(new Subscriber(method, object, match));
            }
        }
    }

    @Override
    public void unregister(Object object) {
        GuiceInjector.getInstance(SubscriberContainer.class).removeSubcriber(object);
    }
}
