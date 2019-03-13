package cn.com.startai.synermax;

import cn.com.startai.sharedlib.app.global.CustomManager;
import cn.com.startai.sharedlib.app.view.app.SharedApplication;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/23 0023
 * desc :
 */
public class SynerMaxChargerApplication extends SharedApplication {

    @Override
    public void onCreate() {
        CustomManager.getInstance().initSynerMaxProject();
        super.onCreate();
    }

}
