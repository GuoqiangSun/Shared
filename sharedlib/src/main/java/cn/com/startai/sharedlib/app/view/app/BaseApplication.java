package cn.com.startai.sharedlib.app.view.app;

import android.support.multidex.MultiDexApplication;

/**
 * author: Guoqiang_Sun
 * date : 2018/3/28 0028
 * desc :
 */

public class BaseApplication extends MultiDexApplication implements Thread.UncaughtExceptionHandler {


    @Override
    public void onCreate() {
        super.onCreate();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {

    }
}
