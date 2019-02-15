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

    private boolean isRuiooProject = false;
    private boolean isSynerMaxProject = false;

    public boolean isRuioo() {
        return isRuiooProject;
    }

    public boolean isSynerMax() {
        return isSynerMaxProject;
    }

    public void initSynerMaxProject() {
        Tlog.i(" init synermax project ");
        this.isSynerMaxProject = true;
    }

    public void initRuiooProject() {
        Tlog.i(" init Ruioo project ");
        this.isRuiooProject = true;
    }


    @Override
    public void init(Application app) {
        Tlog.i("CustomManager init : ");

        if (isRuioo()) {

        } else if (isSynerMax()) {

        }
    }


}
