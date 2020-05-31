package com.bus.live.util;

import android.util.Log;

public final class Dog {

    private static final String LIB_NAME = "LiveBus";

    private static boolean isDebug = true;

    public static void i(String tag, String msg) {
        if (isDebug)
            Log.i(LIB_NAME, tag + " ->>> " + msg);
    }

    public static void d(String tag, String msg) {
        if (isDebug)
            Log.d(LIB_NAME, tag + " ->>> " + msg);
    }

    public static void v(String tag, String msg) {
        if (isDebug)
            Log.v(LIB_NAME, tag + " ->>> " + msg);
    }

    public static void w(String tag, String msg) {
        if (isDebug)
            Log.w(LIB_NAME, tag + " ->>> " + msg);
    }

    public static void e(String tag, String msg) {
        if (isDebug)
            Log.e(LIB_NAME, tag + " ->>> " + msg);
    }
}
