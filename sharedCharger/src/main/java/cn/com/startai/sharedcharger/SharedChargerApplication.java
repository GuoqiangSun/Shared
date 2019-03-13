package cn.com.startai.sharedcharger;

import cn.com.startai.sharedlib.app.global.CustomManager;
import cn.com.startai.sharedlib.app.view.app.SharedApplication;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/23 0023
 * desc :
 */
public class SharedChargerApplication extends SharedApplication {

    @Override
    public void onCreate() {
        CustomManager.getInstance().initSharedChargerProject();
        super.onCreate();
    }

}
