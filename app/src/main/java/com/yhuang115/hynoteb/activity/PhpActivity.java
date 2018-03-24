package com.yhuang115.hynoteb.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yhuang115.baidulbs.activity.LocActivity;
import com.yhuang115.hynoteb.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Administrator on 2018/2/22 0022.
 */

public class PhpActivity extends Activity {
    private ListView lvPhp;
    private TextView tvGoLoc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_php);
        lvPhp = findViewById(R.id.lv_php);
        tvGoLoc = findViewById(R.id.tv_go_loc);
        tvGoLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PhpActivity.this, LocActivity.class));
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                getData();
            }
        }).start();
    }

    private void getData() {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = null;
        InputStreamReader isr = null;
        URLConnection conn;
        try {
            URL url = new URL("http://10.10.10.21/phpstudy/mvc/index.php");
            conn = url.openConnection();
            conn.connect();//get连接
            isr = new InputStreamReader(conn.getInputStream());//输入流
            br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);//获取输入流数据
            }
            String result = sb.toString();
            System.out.println(result);
            Log.e("hynoteb",result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {//执行流的关闭
            if (br != null) {
                try {
                    if (br != null) {
                        br.close();
                    }
                    if (isr != null) {
                        isr.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }
}
