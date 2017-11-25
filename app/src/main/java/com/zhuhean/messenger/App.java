package com.zhuhean.messenger;

import android.app.Application;
import android.content.Context;

import com.zhuhean.messenger.component.Remember;
import com.zhuhean.messenger.model.DaoMaster;
import com.zhuhean.messenger.model.DaoSession;
import com.zhuhean.messenger.ui.CrashActivity;

import org.greenrobot.greendao.database.Database;

public class App extends Application {

    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        initDao(this);
        Remember.init(this, getPackageName());
        CrashActivity.handleCrash(this);
    }

    private static void initDao(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "sms-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public static DaoSession daoSession() {
        return daoSession;
    }

}
