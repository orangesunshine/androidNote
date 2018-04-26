package com.example.eventbus;

import com.example.eventbus.controller.DefaultEventBus;
import com.example.eventbus.controller.EventBus;
import com.example.eventbus.controller.GuiceInjector;
import com.example.eventbus.controller.MatchEnum;
import com.example.eventbus.controller.Subscriber;
import com.example.eventbus.controller.container.Person;
import com.example.eventbus.controller.container.SubscriberContainer;
import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test() {
        Injector injector = Guice.createInjector(new Module() {
            @Override
            public void configure(Binder binder) {

            }
        });
        Subscriber subscriber1 = null;
        Person p1 = new Person();
        Method[] methods = p1.getClass().getMethods();
        for (Method method : methods) {
            if ("test".equals(method.getName())) {
                subscriber1 = new Subscriber(method, p1, MatchEnum.MATCH_NO);
            }
        }

        Subscriber subscriber2 = null;
        Person p2 = new Person();
        Method[] mds = p2.getClass().getMethods();
        for (Method method : mds) {
            if ("test".equals(method.getName())) {
                subscriber2 = new Subscriber(method, p2, MatchEnum.MATCH_NO);
            }
        }

        GuiceInjector.getInstance(EventBus.class).register(subscriber1);
        GuiceInjector.getInstance(EventBus.class).unregister(subscriber1);
    }
}