package com.example.eventbus.simple.test;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eventbus.R;

public class DemoActivity extends Activity {
    private LinearLayout llParent;
    private TextView tvContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        llParent = findViewById(R.id.ll_parent);
        tvContent = findViewById(R.id.tv_content);
        tvContent.setText("fdjfjkdsjlfjl");

        llParent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                llParent.measure(0, 0);
                Toast.makeText(DemoActivity.this, "height: " + llParent.getHeight(), Toast.LENGTH_LONG).show();
            }
        });

        tvContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvContent.append("fdjfjkdsjlfjl\n");
            }
        });
    }
}
