package com.orange.jpush;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import java.util.Timer;
import java.util.TimerTask;

public class AppListener extends Service {
    private boolean isAppStart = false;// 判断软件是否打开，过滤重复执行
    private String packageName_now = "";//记录当前所在应用的包名
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        timer.schedule(task, 0, 500); //开始监听应用，每500毫秒查询一次，用这种方式循环比while更节约资源，而且更好用，这个项目刚开始用了while，把我坑坏了
        super.onCreate();
    }
    Handler handler_listen = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
                ComponentName cn = am.getRunningTasks(1).get(0).topActivity;//获取到栈顶最顶层的activity所对应的应用
                String packageName = cn.getPackageName();//从ComponentName对象中获取到最顶层的应用包名
                if (!packageName_now.equals(packageName)) {//如果两个包名不相同，那么代表切换了应用
                    packageName_now=packageName;//更新当前的应用包名
                    isAppStart=false;//将是否是监听的应用包名的状态修改为false
                }
                if (packageName.equals("com.tencent.mobileqq")) {//这里举例监听QQ
                    if (!isAppStart) {
                        isAppStart=true;//因为一直在循环，所以需要加个isAppStart判断防止代码重复执行
                        //。。。。逻辑处理
                    }
                }
            }
            super.handleMessage(msg);
        };
    };
    Timer timer = new Timer();
    TimerTask task = new TimerTask() {

        @Override
        public void run() {
            Message message = new Message();
            message.what = 1;
            handler_listen.sendMessage(message);
        }
    };

    public void onDestroy() {
        timer.cancel();//销毁服务的时候同时关闭定时器timer
        super.onDestroy();
    }

}
