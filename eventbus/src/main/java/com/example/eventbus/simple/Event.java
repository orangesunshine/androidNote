package com.example.eventbus.simple;

/**
 * Created by yhuang115 on 2018/4/25.
 */

public class Event {
    private final String DEFAULT_TAG = "default_tag";
    private String tag = DEFAULT_TAG;

    public Event(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        return tag != null ? tag.equals(event.tag) : event.tag == null;
    }

    @Override
    public int hashCode() {
        return tag != null ? tag.hashCode() : 0;
    }
}
