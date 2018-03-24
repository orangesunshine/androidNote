package com.yhuang115.hynoteb.utils;

import android.content.Context;
import android.util.Log;

import com.yhuang115.hynoteb.constant.Const;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Created by Administrator on 2018/2/12 0012.
 */

public class PropertiesUtil {
    private static PropertiesUtil mInstance;
    private Properties mProperties;
    private String mPropertiesName;

    public static PropertiesUtil getInstance() {
        if (null == mInstance) {
            mInstance = new PropertiesUtil();
        }
        return mInstance;
    }

    private PropertiesUtil() {
        mProperties = new Properties();
    }

    public void load(Context context, String propertiesName) {
        try {
            mProperties.load(context.openFileInput(propertiesName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load(String propertiesName) {
        try {
            mProperties.load(PropertiesUtil.class.getClassLoader().getResourceAsStream(propertiesName));
            Log.e(Const.TAG, mProperties.stringPropertyNames().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadAssert(Context context, String propertiesName) {
        try {
            mProperties.load(context.getAssets().open(propertiesName));
            Log.e(Const.TAG, mProperties.stringPropertyNames().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String key) {
        return mProperties.getProperty(key);
    }

    public Enumeration getPropertys() {
        return mProperties.propertyNames();
    }
}
