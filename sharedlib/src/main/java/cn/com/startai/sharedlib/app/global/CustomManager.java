package cn.com.startai.sharedlib.app.global;

import android.app.Application;

import cn.com.swain.baselib.app.IApp.IApp;
import cn.com.swain.baselib.log.Tlog;

/**
 * author: Guoqiang_Sun
 * date : 2018/6/4 0004
 * desc :
 */
public class CustomManager implements IApp {

    private CustomManager() {
    }

    private static final class ClassHolder {
        private static final CustomManager FLAVORS = new CustomManager();
    }

    public static CustomManager getInstance() {
        return ClassHolder.FLAVORS;
    }

    private boolean isTestProject = false;
    private boolean isSharedChargerProject = false;
    private boolean isSynerMaxProject = false;

    public boolean isSharedCharger() {
        return isSharedChargerProject;
    }

    public boolean isSynerMax() {
        return isSynerMaxProject;
    }

    public void initSynerMaxProject() {
        Tlog.i(" init synermax project ");
        this.isSynerMaxProject = true;
    }

    public void initSharedChargerProject() {
        Tlog.i(" init shared charger project ");
        this.isSharedChargerProject = true;
    }

    public void initTestProject() {
        Tlog.i(" init Test project ");
        this.isTestProject = true;
    }


    @Override
    public void init(Application app) {
        Tlog.i("CustomManager init : ");

        if (isSharedCharger()) {

        } else if (isSynerMax()) {

        }
    }


}
