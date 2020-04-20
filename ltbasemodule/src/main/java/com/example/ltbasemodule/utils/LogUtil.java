package com.example.ltbasemodule.utils;

import android.util.Log;

/**
 * @author mzy
 * @description 为了统一管理Log日志
 */
public class LogUtil {
    private final static boolean isDebug = true;

    public static void d(Class clazz, String message) {
        if (isDebug)
            Log.d(clazz.getName(), message);
    }

    public static void e(Class clazz, String message) {
        if (isDebug)
            Log.e(clazz.getName(), message);
    }
}
