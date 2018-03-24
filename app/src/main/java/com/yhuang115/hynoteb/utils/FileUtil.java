package com.yhuang115.hynoteb.utils;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by Administrator on 2018/2/13 0013.
 */

public class FileUtil {
    public static final String PREFIX_PROJECT_FILE = "data/data/";

    public static String read(InputStream input) {
        StringBuilder sb = new StringBuilder();
        InputStreamReader reader = null;
        try {
            reader = new InputStreamReader(input, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        char[] buffer = new char[1024];
        int length = 0;
        try {
            while ((length = reader.read(buffer)) != -1) {
                sb.append(buffer, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

//    public static String readProjectFile(Context context, String fileName) {
//        try {
//            String codePath = context.getPackageCodePath();
//            System.out.print("codePath: " + codePath);
//            return read(new FileInputStream(codePath + Const.PACKAGENAME + File.separator + fileName));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    public static String readAssertFile(Context context, String fileName) {
        try {
            InputStream open = context.getAssets().open(fileName);
            return read(open);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String readFile(String fileName) {
        try {
            return read(new FileInputStream(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
