package com.yhuang115.hynoteb.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.yhuang115.hynoteb.R;
import com.yhuang115.hynoteb.utils.PropertiesUtil;
import com.yhuang115.hynoteb.widget.demo.CanvasView;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class MainActivity extends AppCompatActivity {
    private CanvasView cvDraw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        cvDraw = findViewById(R.id.cv_draw);
        TabLayout tab = findViewById(R.id.tab);
        setSupportActionBar(toolbar);
        PropertiesUtil.getInstance().loadAssert(this, "config/config.properties");
        String drawlist = PropertiesUtil.getInstance().getProperty("drawlist");
//        String drawlist = FileUtil.readAssertFile(this, "config/config.properties");
//        PropertiesUtil instance = PropertiesUtil.getInstance();
//        instance.load("config.properties");
//        Enumeration propertys = instance.getPropertys();
//        while (propertys.hasMoreElements()) {
//            Object o = propertys.nextElement();
//            System.out.println(String.valueOf(o));
//        }

        String[] split = drawlist.split(",");
        cvDraw.setDrawContent(split[0]);
        for (String content : split) {
            tab.addTab(tab.newTab().setText(content));
        }

        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                cvDraw.setDrawContent(tab.getText().toString());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
