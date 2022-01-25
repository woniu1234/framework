package com.common.frame.utils;

import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;

import com.common.frame.BaseApp;


/**
 * @author lst
 * @Description: ToastUtil.java(类描述)
 * @date 2021/6/22
 */
public class ToastUtil {
    private static Toast mToast;

    public static void show(String msg) {
        try {
            if (!TextUtils.isEmpty(msg)) {
                if (mToast != null) {
                    mToast.cancel();
                }
                mToast = Toast.makeText(BaseApp.sApplication, "", Toast.LENGTH_LONG);
                mToast.setText(msg);
                mToast.show();
            }
        } catch (Exception e) {
            Looper.prepare();
            if (mToast != null) {
                mToast.cancel();
            }
            mToast = Toast.makeText(BaseApp.sApplication, "", Toast.LENGTH_LONG);
            mToast.setText(msg);
            mToast.show();
            Looper.loop();
        }
    }
}
