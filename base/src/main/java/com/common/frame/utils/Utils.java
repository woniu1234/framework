
package com.common.frame.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.WindowManager;

import com.common.frame.BaseApp;

import java.util.Collection;
import java.util.Map;

public class Utils {
    private static final String TAG = "Utils";
    private static final boolean DEBUG = false;

    public static boolean isEmpty(final String str) {
        return str == null || str.length() <= 0;
    }

    public static boolean isEmpty(final SparseArray sparseArray) {
        return sparseArray == null || sparseArray.size() <= 0;
    }

    public static boolean isEmpty(final Collection<? extends Object> collection) {
        return collection == null || collection.size() <= 0;
    }

    public static boolean isEmpty(final Map<? extends Object, ? extends Object> list) {
        return list == null || list.size() <= 0;
    }

    public static boolean isEmpty(final byte[] bytes) {
        return bytes == null || bytes.length <= 0;
    }

    public static boolean isEmpty(final String[] strArr) {
        return strArr == null || strArr.length <= 0;
    }

    public static int nullAs(final Integer obj, final int def) {
        return obj == null ? def : obj;
    }

    public static long nullAs(final Long obj, final long def) {
        return obj == null ? def : obj;
    }

    public static boolean nullAs(final Boolean obj, final boolean def) {
        return obj == null ? def : obj;
    }

    public static String nullAs(final String obj, final String def) {
        return obj == null ? def : obj;
    }

    public static String emptyAs(final String obj, final String def) {
        return isEmpty(obj) ? def : obj;
    }

    public static int nullAsNil(final Integer obj) {
        return obj == null ? 0 : obj;
    }

    public static long nullAsNil(final Long obj) {
        return obj == null ? 0L : obj;
    }

    public static String nullAsNil(final String obj) {
        return obj == null ? "" : obj;
    }

    public static boolean isEmpty(int[] si) {
        return si == null || si.length == 0;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(float dpValue, Context context) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int[] getScreenSize(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        int widthPixels = outMetrics.widthPixels;
        int heightPixels = outMetrics.heightPixels;
        return new int[]{widthPixels, heightPixels};
    }

    /**
     * 获取软件的版本号
     */
    public static String getVersionName() {
        String versionCode = "1.0.0";
        try {
            versionCode = BaseApp.sApplication.getPackageManager().getPackageInfo(
                    BaseApp.sApplication.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            LogUtils.e(e.getMessage());
        }
        return versionCode;
    }

    /**
     * 获取软件的版本号
     */
    public static int getVersionCode() {
        int versionCode = 1;
        try {
            versionCode = BaseApp.sApplication.getPackageManager().getPackageInfo(
                    BaseApp.sApplication.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            LogUtils.e(e.getMessage());
        }
        return versionCode;
    }

    public static String getPackageName() {
        return BaseApp.sApplication.getPackageName();
    }

}
