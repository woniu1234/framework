package com.common.frame.utils;

import android.content.Context;
import android.util.Log;

public class LogUtils {
    private static boolean sEnabled = false;
    private static final String sDefaultTag = "logger---------logger";

    public static void setEnabled(boolean enabled) {
        sEnabled = enabled;
    }

    public static void v(String msg) {
        if (sEnabled) {
            Log.v(sDefaultTag, "" + msg);
        }
    }

    public static void v(String tag, String msg) {
        if (sEnabled) {
            Log.v(tag, "" + msg);
        }
    }

    public static void d(String msg) {
        if (sEnabled) {
            Log.d(sDefaultTag, "" + msg);
        }
    }

    public static void d(String tag, String msg) {
        if (sEnabled) {
            Log.d(tag, "" + msg);
        }
    }

    public static void e(String tag, Throwable t) {
        if (sEnabled) {
            Log.e(tag, "exception", t);
        }
    }

    public static void e(String tag, String msg, Throwable t) {
        if (sEnabled) {
            Log.e(tag, "" + msg, t);
        }
    }

    public static void e(String tag, String msg) {
        if (sEnabled) {
            Log.e(tag, "" + msg);
        }
    }

    public static void e(String msg) {
        if (sEnabled) {
            Log.e(sDefaultTag, "" + msg);
        }
    }

    public static void i(String tag, String msg) {
        if (sEnabled) {
            Log.i(tag, "" + msg);
        }
    }

    public static void i(String msg) {
        if (sEnabled) {
            Log.i(sDefaultTag, "" + msg);
        }
    }

    public static void w(String tag, String msg) {
        if (sEnabled) {
            Log.w(tag, "" + msg);
        }
    }

    public static void init(Context context, boolean isDebug) {
        if (context != null) {
            setEnabled(isDebug);
        }
    }

}
