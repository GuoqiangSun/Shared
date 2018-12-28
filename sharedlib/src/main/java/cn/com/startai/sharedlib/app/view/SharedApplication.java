package cn.com.startai.sharedlib.app.view;

import android.content.res.Configuration;
import android.os.Build;

import cn.com.startai.sharedlib.app.Debuger;
import cn.com.startai.sharedlib.app.FileManager;
import cn.com.startai.sharedlib.app.LooperManager;
import cn.com.startai.sharedlib.app.js.Utils.Language;
import cn.com.swain.baselib.log.Tlog;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/21 0021
 * desc :
 */
public class SharedApplication extends BaseApplication {

    public static final String TAG = "SharedApp";

    @Override
    public void onCreate() {
        super.onCreate();
        Tlog.setGlobalTag(TAG);

        Language.changeLanguage(getApplicationContext());

        FileManager.getInstance().init(this);
        Debuger.getInstance().init(this);
        LooperManager.getInstance().init(this);
        Tlog.i("SharedApplication onCreate(); pid:" + android.os.Process.myPid() + "; Build.VERSION.SDK_INT :" + Build.VERSION.SDK_INT);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        super.uncaughtException(t, e);
        Tlog.e(TAG, " SharedApplication caughtException ", e);
        FileManager.getInstance().saveAppException(t, e);
        catchException();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Tlog.v(TAG, "SocketApplication onConfigurationChanged() " + newConfig.toString());
    }

    private void catchException() {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    public void onJsCrashError(String jsonStr) {
        Tlog.e(TAG, "onJsCrashError:" + String.valueOf(jsonStr));
        FileManager.getInstance().saveH5Exception(jsonStr);
        catchException();
    }
}
