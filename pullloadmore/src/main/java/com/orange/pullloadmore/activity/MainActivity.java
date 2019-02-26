package com.orange.pullloadmore.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.orange.pullloadmore.R;
import com.orange.pullloadmorelib.PullLoadmoreListView;

public class MainActivity extends AppCompatActivity {
    private PullLoadmoreListView lvPullLoadmore;
    private String strs[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        lvPullLoadmore = findViewById(R.id.lv_pull_loadmore);
        strs = new String[15];
        for (int i = 0; i < strs.length; i++) {
            strs[i] = "你好" + i + "先生";
        }
        final ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, strs);
        lvPullLoadmore.setAdapter(arrayAdapter);
        lvPullLoadmore.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                strs = new String[0];
                arrayAdapter.notifyDataSetChanged();
            }
        });
        lvPullLoadmore.setRefreshListener(new PullLoadmoreListView.RefreshListener() {
            @Override
            public void onRefresh() {
                lvPullLoadmore.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        lvPullLoadmore.refreshComplete();
                    }
                },2000);
            }
        });
    }

}
