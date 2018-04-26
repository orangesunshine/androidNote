package com.example.hyutils;

/**
 * Created by yhuang115 on 2018/4/26.
 */

public class CommonUtils {
    public <T> T defaultValue(T src, T defaultVaule) {
        return src == null ? defaultVaule : src;
    }
}
