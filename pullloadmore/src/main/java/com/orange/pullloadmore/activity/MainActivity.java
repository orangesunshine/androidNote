package com.orange.pullloadmore.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.orange.pullloadmore.R;
import com.orange.pullloadmorelib.PullLoadmoreListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private PullLoadmoreListView lvPullLoadmore;
    private List<String> texts;
    private int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        lvPullLoadmore = findViewById(R.id.lv_pull_loadmore);
        texts = new ArrayList<>();
        for (i = 0; i < 10; i++) {
            texts.add("你好" + i + "女士");
        }
        final ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, texts);
        lvPullLoadmore.setAdapter(arrayAdapter);
        lvPullLoadmore.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                texts.clear();
                arrayAdapter.notifyDataSetChanged();
            }
        });
        lvPullLoadmore.setRefreshListener(new PullLoadmoreListView.RefreshLoadmoreListener() {
            @Override
            public void onRefresh() {
                lvPullLoadmore.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        lvPullLoadmore.refreshComplete();
                    }
                }, 2000);
            }

            @Override
            public void onLoadmore() {
                lvPullLoadmore.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for (int j = 0; j < 5; j++) {
                            i++;
                            texts.add("你好" + i + "女士");
                        }
                        arrayAdapter.notifyDataSetChanged();
                        lvPullLoadmore.loadmoreComplete();
                    }
                }, 2000);
            }
        });
    }

}
