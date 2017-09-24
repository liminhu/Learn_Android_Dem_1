package com.lmhu.mylearncoolweather.util;

import android.os.Process;
import android.util.Log;


import com.lmhu.mylearncoolweather.BuildConfig;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MyLog {

    public static String TAG = "hook_test_";

    private static final boolean LOG = BuildConfig.DEBUG;

    static {
        TAG = TAG + Process.myPid();
    }
    public static void w(String msg) {
        if (LOG)
            Log.w(TAG, commonTag() + msg);
    }

    public static void w(String format, Object... args) {
        w(String.format(format, args));
    }

    public static void e(String msg) {
        if (LOG ) {
           // Log.e(TAG, commonTag() + Thread.currentThread().getStackTrace()[1].getMethodName());
            String test="--"+Thread.currentThread().getStackTrace()[3].getMethodName();
            Log.e(TAG, commonTag() + test+"\t  \t "+msg);
        }
    }

    public static void e(String format, Object... args) {
        e(String.format(format, args));
    }

    public static void e(Exception e, String msg) {
        if (LOG ) {
            Log.e(TAG, commonTag() + msg);
        }
        e(e);
    }

    public static void e(Exception e, String format, Object... args) {
        e(e, String.format(format, args));
    }

    public static void e(Exception e) {
        if (LOG)
            e.printStackTrace();
    }

    public static void i(String msg) {
        if (LOG )
            Log.i(TAG, commonTag() + msg);
    }

    public static void i(String format, Object... args) {
        i(String.format(format, args));
    }

    public static void d(String msg) {
        if (LOG )
            Log.d(TAG, commonTag() + msg);
    }

    public static void d(String format, Object... args) {
        d(String.format(format, args));
    }

    public static void v(String msg) {
        if (LOG)
            Log.v(TAG, commonTag() + msg);
    }

    public static void v(String format, Object... args) {
        v(String.format(format, args));
    }

    public static String commonTag() {
        return String.format("(%s--%s)", formatShortTime(System.currentTimeMillis()), Process.myPid());
    }

    public static String formatTime(long time) {
        return new SimpleDateFormat("MM/dd HH:mm:ss", Locale.US).format(new Date(time));
    }

    public static String formatShortTime(long time) {
        return new SimpleDateFormat("HH:mm:ss", Locale.US).format(new Date(time));
    }

    public static String getFormatrerTime(long currenTime){
        Date date=new Date(currenTime);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年-MM月-dd日 HH:mm:ss:SSS");
        String dateString = formatter.format(date);
        return dateString;
    }



}
