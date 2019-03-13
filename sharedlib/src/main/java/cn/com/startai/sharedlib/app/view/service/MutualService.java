package cn.com.startai.sharedlib.app.view.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import cn.com.startai.sharedlib.app.controller.Controller;
import cn.com.swain.baselib.app.IApp.IService;
import cn.com.swain.baselib.log.Tlog;

/**
 * author: Guoqiang_Sun
 * date: 2018/12/25 0025
 * Desc:
 */
public class MutualService extends Service implements IService {

    @Override
    public void onSCreate() {
        Controller.getInstance().onSCreate();
    }

    @Override
    public void onSResume() {
        Controller.getInstance().onSResume();
    }

    @Override
    public void onSPause() {
        Controller.getInstance().onSPause();
    }

    @Override
    public void onSDestroy() {
        Controller.getInstance().onSDestroy();
    }

    @Override
    public void onSFinish() {
        Controller.getInstance().onSFinish();
    }

    public final class MBinder extends Binder {

        public IService getIService() {
            return MutualService.this;
        }

    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Tlog.d(" MutualService onCreate() ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Tlog.d(" MutualService onDestroy() ");
    }
}
