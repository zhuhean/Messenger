package com.zhuhean.library;

import android.app.Application;

import com.zhuhean.library.component.Remember;

/**
 * @author zhuhean
 * @date 25/11/2017.
 */

public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Remember.init(this, getPackageName());
    }

}
