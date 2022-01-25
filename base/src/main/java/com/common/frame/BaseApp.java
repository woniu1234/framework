package com.common.frame;

import android.app.Application;

/**
 * @author lst
 * @Description: BaseApplication.java(类描述)
 * @date 2021/6/18
 */
public class BaseApp extends Application {

    public static BaseApp sApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
    }
}
