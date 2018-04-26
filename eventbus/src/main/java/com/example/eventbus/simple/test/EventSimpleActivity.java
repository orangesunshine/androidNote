package com.example.eventbus.simple.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.eventbus.R;
import com.example.eventbus.controller.BeatContext;
import com.example.eventbus.controller.GuiceInjector;
import com.example.eventbus.controller.MatchEnum;
import com.example.eventbus.controller.ThreadEnum;
import com.example.eventbus.controller.annotation.EventSub;
import com.example.eventbus.simple.DefaultEventBus;
import com.example.eventbus.simple.Event;
import com.example.eventbus.simple.EventBus;
import com.example.eventbus.simple.Observer;
import com.example.eventbus.simple.ObserverImpl;
import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Module;

/**
 * Created by yhuang115 on 2018/4/25.
 */

public class EventSimpleActivity extends Activity implements View.OnClickListener {
    private Button btnSend, btnRegister, btnUnregiter, btnNext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_simple);
        btnSend = findViewById(R.id.btn_send);
        btnNext = findViewById(R.id.btn_next);
        btnRegister = findViewById(R.id.btn_register);
        btnUnregiter = findViewById(R.id.btn_unregister);
        btnSend.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        btnUnregiter.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        GuiceInjector.getInstance(com.example.eventbus.controller.EventBus.class).register(this);
    }

    @EventSub(match = MatchEnum.MATCH_RECEIVER, thread = ThreadEnum.UI_THREAD)
    public void test() {
        btnNext.setText(GuiceInjector.getInstance(BeatContext.class).getEvent().getBody().toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GuiceInjector.getInstance(com.example.eventbus.controller.EventBus.class).unregister(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send:
                break;
            case R.id.btn_register:
                DefaultEventBus.getDefault().register(new Event("event1"), new ObserverImpl("ob1") {
                    @Override
                    public void receiver(Event event) {
                        runOnUiThread(() -> {
                            Toast.makeText(EventSimpleActivity.this, event.getTag(), Toast.LENGTH_LONG).show();
                        });
                    }
                });
                break;
            case R.id.btn_unregister:
                DefaultEventBus.getDefault().unregister(new Event("event1"), new ObserverImpl("ob1") {
                    @Override
                    public void receiver(Event event) {
                        runOnUiThread(() -> {
                            Toast.makeText(EventSimpleActivity.this, event.getTag(), Toast.LENGTH_LONG).show();
                        });
                    }
                });
                break;
            case R.id.btn_next:
                startActivity(new Intent(this, EventSendActivity.class));
                break;
            default:
                break;
        }
    }
}
