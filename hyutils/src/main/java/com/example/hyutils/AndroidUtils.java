package com.example.hyutils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;

import dalvik.system.DexFile;

/**
 * Created by yhuang115 on 2018/4/26.
 */

public class AndroidUtils {
    public static Set<Class<?>> getClasses(String pack, Context context) {
        Set<Class<?>> vmClasses = new LinkedHashSet<Class<?>>();
        try {
            DexFile df = new DexFile(context.getPackageCodePath());
            for (Enumeration<String> iter = df.entries(); iter.hasMoreElements(); ) {
                String s = iter.nextElement();
                if (TextUtils.isEmpty(s)) continue;
                if (s.startsWith(pack)) {
                    Class clazz = Class.forName(s);
                    vmClasses.add(clazz);
                }
            }
            Log.i("yst", "AndroidClassUtils vmClasses size:" + vmClasses.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vmClasses;
    }
}
