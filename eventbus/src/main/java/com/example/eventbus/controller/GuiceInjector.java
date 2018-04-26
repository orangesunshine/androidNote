package com.example.eventbus.controller;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

/**
 * Created by yhuang115 on 2018/4/26.
 */

public class GuiceInjector {
    private static volatile Injector sInjector = Guice.createInjector(new Module() {
        @Override
        public void configure(Binder binder) {

        }
    });

    public static <T> T getInstance(Class<T> clazz) {
        return sInjector.getInstance(clazz);
    }
}
