package com.example.passsafe;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.example.passsafe.greendao.DaoMaster;
import com.example.passsafe.greendao.DaoSession;

import org.greenrobot.greendao.database.Database;

public class MyApplication extends Application {
    @SuppressLint("StaticFieldLeak")
    private static Context sContext = null;
    private static MyApplication sInstance = null;

    private DaoMaster.DevOpenHelper mHelper;
    private Database db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        sContext = getApplicationContext();

        sInstance = this;

        setDatabase();
    }

    public static Context getsContext(){
        return sContext;
    }

    public static MyApplication getInstance(){
        return sInstance;
    }

    /**
     * 设置greenDao
     */
    private void setDatabase() {
        mHelper = new DaoMaster.DevOpenHelper(this, "passsafe.db", null);
        db = mHelper.getEncryptedWritableDb("20231101");
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    /**
     * 得到DaoSession对象
     *
     * @return
     */
    public DaoSession getDaoSession() {
        return mDaoSession;
    }
}
