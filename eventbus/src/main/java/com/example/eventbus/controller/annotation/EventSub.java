package com.example.eventbus.controller.annotation;

import com.example.eventbus.controller.MatchEnum;
import com.example.eventbus.controller.ThreadEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by yhuang115 on 2018/4/26.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface EventSub {
    MatchEnum match() default MatchEnum.MATCH_NO;
    ThreadEnum thread() default ThreadEnum.NEW_THREAD;
}
