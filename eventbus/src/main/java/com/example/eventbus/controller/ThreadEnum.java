package com.example.eventbus.controller;

/**
 * Created by yhuang115 on 2018/4/26.
 */

public enum ThreadEnum {
    UI_THREAD(0), NEW_THREAD(1);
    private int value;

    ThreadEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
