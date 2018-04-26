package com.example.eventbus.simple.test;

import android.app.Activity;
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
import com.example.eventbus.simple.ObserverImpl;

/**
 * Created by yhuang115 on 2018/4/25.
 */

public class EventSendActivity extends Activity implements View.OnClickListener {
    private Button btnSend, btnRegister, btnUnregiter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_simple);
        btnSend = findViewById(R.id.btn_send);
        btnRegister = findViewById(R.id.btn_register);
        btnUnregiter = findViewById(R.id.btn_unregister);
        btnSend.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        btnUnregiter.setOnClickListener(this);
        GuiceInjector.getInstance(com.example.eventbus.controller.EventBus.class).register(this);
    }

    @EventSub(match = MatchEnum.MATCH_RECEIVER, thread = ThreadEnum.UI_THREAD)
    public void test() {
        Toast.makeText(this, GuiceInjector.getInstance(BeatContext.class).getEvent().getBody().toString(), Toast.LENGTH_SHORT).show();
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
                GuiceInjector.getInstance(com.example.eventbus.controller.EventBus.class).post(new com.example.eventbus.controller.Event<String>(EventSimpleActivity.class, "大洋仔"));
                break;
            case R.id.btn_register:
                DefaultEventBus.getDefault().register(new Event("event1"), new ObserverImpl("ob1") {
                    @Override
                    public void receiver(Event event) {
                        runOnUiThread(() -> {
                            Toast.makeText(EventSendActivity.this, event.getTag(), Toast.LENGTH_LONG).show();
                        });
                    }
                });
                break;
            case R.id.btn_unregister:
                DefaultEventBus.getDefault().unregister(new Event("event1"), new ObserverImpl("ob1") {
                    @Override
                    public void receiver(Event event) {
                        runOnUiThread(() -> {
                            Toast.makeText(EventSendActivity.this, event.getTag(), Toast.LENGTH_LONG).show();
                        });
                    }
                });
                break;
            default:
                break;
        }
    }
}
