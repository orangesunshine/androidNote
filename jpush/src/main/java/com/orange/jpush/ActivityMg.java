package com.orange.jpush;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Debug;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.ContentValues.TAG;

public class ActivityMg {
    private static ActivityMg mInstance = new ActivityMg();

    private ActivityMg() {
    }

    public static ActivityMg getInstance() {
        return mInstance;
    }

    //1.获取ActivityManager服务：
    static ActivityManager mActivityManager;

    private void log(String log) {
        Log.e("ActivityMg", log);
    }

    public void perform(Context context) {
        mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        getMemoryInfo();
        getRunningTasks();
        log("isAppOnForeground: " + isAppOnForeground(context));
        getAllTheLauncher(context);
        isLauncherRunning(context);
        getRunningAppProcessInfo();
    }

    //2.获取内存信息：
    public void getMemoryInfo() {
        MemoryInfo memoryInfo = new MemoryInfo();
        mActivityManager.getMemoryInfo(memoryInfo);
        Object availMem = Reflections.getFieldValue(memoryInfo, "availMem");
        log("getMemoryInfo: ->availMem: " + availMem.toString());
    }

    //3.获取正在运行的task信息：
    public void getRunningTasks() {
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = mActivityManager.getRunningTasks(Integer.MAX_VALUE);
        String cmpNameTemp = null;
        if (null != runningTaskInfos) {
            cmpNameTemp = (runningTaskInfos.get(0).topActivity).toString();
        }
        log("getRunningTasks: " + cmpNameTemp);
    }

    //4.判断android应用是否在前台：
    public static boolean isAppOnForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = context.getPackageName();
        List<ActivityManager.RecentTaskInfo> appTask = activityManager.getRecentTasks(Integer.MAX_VALUE, 1);

        if (appTask == null) {
            return false;
        }

        if (appTask.get(0).baseIntent.toString().contains(packageName)) {
            return true;
        }
        return false;
    }

    //5.获取android手机内安装的所有桌面：
    public List<String> getAllTheLauncher(Context context) {
        List<String> names = null;
        PackageManager pkgMgt = context.getPackageManager();
        Intent it = new Intent(Intent.ACTION_MAIN);
        it.addCategory(Intent.CATEGORY_HOME);
        List<ResolveInfo> ra = pkgMgt.queryIntentActivities(it, 0);
        if (ra.size() != 0) {
            names = new ArrayList<String>();
        }
        for (int i = 0; i < ra.size(); i++) {
            String packageName = ra.get(i).activityInfo.packageName;
            names.add(packageName);
        }
        log("getAllTheLauncher: " + names.toString());
        return names;
    }
    //6.判断程序前后台状态：

    public boolean isLauncherRunning(Context context) {
        boolean result = false;
        List<String> names = getAllTheLauncher(context);
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appList = mActivityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo running : appList) {
            if (running.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                for (int i = 0; i < names.size(); i++) {
                    if (names.get(i).equals(running.processName)) {
                        result = true;
                        break;
                    }
                }
            }
        }
        log("isLauncherRunning: " + result);
        return result;
    }

    //7.获取系统中进程信息：
    public void getRunningAppProcessInfo() {
        List<ProcessInfo> processInfoList = new ArrayList<ProcessInfo>();

        List<ActivityManager.RunningAppProcessInfo> appProcessList = mActivityManager.getRunningAppProcesses();

        for (ActivityManager.RunningAppProcessInfo appProcessInfo : appProcessList) {

            int pid = appProcessInfo.pid;

            int uid = appProcessInfo.uid;

            String processName = appProcessInfo.processName;

            int[] myMempid = new int[]{pid};

            Debug.MemoryInfo[] memoryInfo = mActivityManager.getProcessMemoryInfo(myMempid);

            int memSize = memoryInfo[0].dalvikPrivateDirty;

            Log.i(TAG, "processName: " + processName + "  pid: " + pid

                    + " uid:" + uid + " memorySize is -->" + memSize + "kb");

            // 构造一个ProcessInfo对象

            ProcessInfo processInfo = new ProcessInfo();

            processInfo.setPid(pid);

            processInfo.setUid(uid);

            processInfo.setMemSize(memSize);

            processInfo.setProcessName(processName);

            processInfoList.add(processInfo);


            // 获得每个进程里运行的应用程序(包),即每个应用程序的包名

            String[] packageList = appProcessInfo.pkgList;

            Log.i(TAG, "process id is " + pid + "has " + packageList.length);

            for (String pkg : packageList) {

                Log.i(TAG, "packageName " + pkg + " in process id is -->" + pid);

            }

        }
        log("getRunningAppProcessInfo: " + processInfoList.toString());

    }

    public static class ProcessInfo {
        private int pid;

        private int uid;

        private String processName;

        private int[] myMempid;

        private int memSize;

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getProcessName() {
            return processName;
        }

        public void setProcessName(String processName) {
            this.processName = processName;
        }

        public int[] getMyMempid() {
            return myMempid;
        }

        public void setMyMempid(int[] myMempid) {
            this.myMempid = myMempid;
        }

        public int getMemSize() {
            return memSize;
        }

        public void setMemSize(int memSize) {
            this.memSize = memSize;
        }

        @Override
        public String toString() {
            return "ProcessInfo{" +
                    "pid=" + pid +
                    ", uid=" + uid +
                    ", processName='" + processName + '\'' +
                    ", myMempid=" + Arrays.toString(myMempid) +
                    ", memSize=" + memSize +
                    '}';
        }
    }
}
