//package com.yhuang115.baidulbs.loc;
//
//import android.content.Context;
//
//import com.baidu.location.BDAbstractLocationListener;
//import com.baidu.location.LocationClient;
//import com.baidu.location.LocationClientOption;
//
///**
// * Created by Administrator on 2018/3/3 0003.
// */
//
//public class LocHelper {
//    private static LocHelper mInstance;
//    public static int INTERVAL_LOC_MILLS = 3000;
//    public static String TYPE_COOR = "bd09ll";
//    private LocationClient client;
//    private LocationClientOption mDefOpt;
//
//    private LocHelper(Context context) {
//        client = new LocationClient(context);
//        client.setLocOption(getDefOpt());
//    }
//
//    public static LocHelper getInstance(Context context) {
//        synchronized (LocHelper.class) {
//            if (null == mInstance) {
//                synchronized (LocHelper.class) {
//                    mInstance = new LocHelper(context);
//                }
//            }
//        }
//        return mInstance;
//    }
//
//    /***
//     *
//     * @param listener
//     * @return
//     */
//
//    public boolean registerListener(BDAbstractLocationListener listener) {
//        boolean isSuccess = false;
//        if (listener != null) {
//            client.registerLocationListener(listener);
//            isSuccess = true;
//        }
//        return isSuccess;
//    }
//
//    public void unregisterListener(BDAbstractLocationListener listener) {
//        if (listener != null) {
//            client.unRegisterLocationListener(listener);
//        }
//    }
//
//    /***
//     *
//     * @param option
//     * @return isSuccessSetOption
//     */
//    public boolean setLocationOption(LocationClientOption option) {
//        boolean isSuccess = false;
//        if (option != null) {
//            if (client.isStarted()) {
//                client.stop();
//            }
//            client.setLocOption(option);
//        }
//        return isSuccess;
//    }
//
//    private LocationClientOption getDefOpt() {
//        if (null == mDefOpt) {
//            mDefOpt = new LocationClientOption();
//            mDefOpt.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
//            mDefOpt.setCoorType(TYPE_COOR);//可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
//            mDefOpt.setScanSpan(INTERVAL_LOC_MILLS);//可选，默认0，即仅定位一次，设置发起连续定位请求的间隔需要大于等于1000ms才是有效的
//            mDefOpt.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
//            mDefOpt.setIsNeedLocationDescribe(true);//可选，设置是否需要地址描述
//            mDefOpt.setNeedDeviceDirect(false);//可选，设置是否需要设备方向结果
//            mDefOpt.setLocationNotify(false);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
//            mDefOpt.setIgnoreKillProcess(true);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
//            mDefOpt.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
//            mDefOpt.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
//            mDefOpt.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
//            mDefOpt.setOpenGps(true);//可选，默认false，设置是否开启Gps定位
//            mDefOpt.setIsNeedAltitude(false);//可选，默认false，设置定位时是否需要海拔信息，默认不需要，除基础定位版本都可用
//        }
//        return mDefOpt;
//    }
//
//    public void start() {
//        synchronized (LocHelper.class) {
//            if (client != null && !client.isStarted()) {
//                client.start();
//            }
//        }
//    }
//
//    public void stop() {
//        synchronized (LocHelper.class) {
//            if (client != null && client.isStarted()) {
//                client.stop();
//            }
//        }
//    }
//
//    public boolean isStart() {
//        return client.isStarted();
//    }
//
//    public boolean requestHotSpotState() {
//        return client.requestHotSpotState();
//    }
//}
