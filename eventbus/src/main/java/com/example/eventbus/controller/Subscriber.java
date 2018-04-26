package com.example.eventbus.controller;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created by yhuang115 on 2018/4/26.
 */

public class Subscriber {
    private Method method;
    private WeakReference receiver;
    private MatchEnum matchEnum;

    public Subscriber(Method method, Object receiver, MatchEnum matchEnum) {
        this.method = method;
        this.receiver = new WeakReference(receiver);
        this.matchEnum = matchEnum;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public void setReceiver(Object receiver) {
        this.receiver = new WeakReference(receiver);
    }

    public void setMatchEnum(MatchEnum matchEnum) {
        this.matchEnum = matchEnum;
    }

    public Method getMethod() {
        return method;
    }

    public Object getReceiver() {
        return receiver.get();
    }

    public MatchEnum getMatchEnum() {
        return matchEnum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Subscriber that = (Subscriber) o;
        if (null == receiver || null == that.receiver ||
                null == method || method.getName().isEmpty() ||
                null == that.method || that.method.getName().isEmpty())
            return false;
        if (equalMethod(that.method) && receiver == that.receiver)
            return true;
        return false;
    }

    public boolean weakEquals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Subscriber that = (Subscriber) o;
        if (null == receiver || null == that.receiver ||
                null == method || method.getName().isEmpty() ||
                null == that.method || that.method.getName().isEmpty())
            return false;
        if (equalMethod(that.method))
            return true;
        return false;
    }

    @Override
    public int hashCode() {
        int result = method != null ? method.hashCode() : 0;
        result = 31 * result + (receiver != null ? receiver.hashCode() : 0);
        return result;
    }

    private boolean equalMethod(Method thatMethod) {
        Class<?>[] types = method.getParameterTypes();
        Class<?>[] thatTypes = thatMethod.getParameterTypes();
        return method.getName().equals(thatMethod.getName()) && Arrays.equals(types,thatTypes);
    }
}
