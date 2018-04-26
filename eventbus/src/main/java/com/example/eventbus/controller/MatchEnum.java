package com.example.eventbus.controller;

/**
 * Created by yhuang115 on 2018/4/26.
 */

public enum MatchEnum {
    MATCH_NO(0), MATCH_RECEIVER(1);
    private int value;

    MatchEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
