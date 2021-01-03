package com.example.amoy_coupon.utils;

import android.util.Log;

/**
 * log工具类，不需要全部log的话直接把currentLev改成最低则全无log打印
 */
public class LogUtils {

    private static int current_lev = 4;
    private static final int DEBUG_LEV = 4;
    private static final int INFO_LEV = 3;
    private static final int WARNING_LEV = 2;
    private static final int ERROR_LEV = 1;

    public static void d(Object object , String log) {
        if (current_lev >= DEBUG_LEV){
            Log.d(object.getClass().getSimpleName(),log);
        }

    }

    public static void i(Object object , String log) {
        if (current_lev >= INFO_LEV){
            Log.i(object.getClass().getSimpleName(),log);
        }
    }

    public static void w(Object object , String log) {
        if (current_lev >= WARNING_LEV){
            Log.w(object.getClass().getSimpleName(),log);
        }
    }

    public static void e(Object object , String log) {
        if (current_lev >= ERROR_LEV){
            Log.e(object.getClass().getSimpleName(),log);
        }
    }
}
