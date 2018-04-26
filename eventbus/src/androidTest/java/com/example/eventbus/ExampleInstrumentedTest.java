package com.example.eventbus;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.eventbus.app.HyApplication;
import com.example.eventbus.controller.GuiceInjector;
import com.example.eventbus.controller.Subscriber;
import com.example.eventbus.controller.container.SubscriberContainer;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Method;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.eventbus", appContext.getPackageName());
    }

    @Test
    public void test() {
        SubscriberContainer instance = GuiceInjector.getInstance(SubscriberContainer.class);
        Set<Class<?>> classFactory = instance.getClassFactory(HyApplication.getGlobalContext(), "com.example.eventbus");
    }
}
