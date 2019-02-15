package cn.com.startai.sharedlib.app.controller;

import android.app.Application;

import java.util.ArrayList;

import cn.com.startai.sharedlib.app.global.Debuger;
import cn.com.startai.sharedlib.app.mutual.IMutualCallBack;
import cn.com.startai.sharedlib.app.mutual.MutualManager;
import cn.com.swain.baselib.app.IApp.IService;
import cn.com.swain.baselib.log.Tlog;

/**
 * author: Guoqiang_Sun
 * date : 2018/4/10 0010
 * desc :
 */

public class Controller implements IService {

    private Controller() {
        this.isInit = false;
    }

    private static final class ClassHolder {
        private static final Controller C = new Controller();
    }

    public static Controller getInstance() {
        return ClassHolder.C;
    }

    private final ArrayList<IService> mServices = new ArrayList<>();

    private volatile boolean isInit;

    public void init(Application app, IMutualCallBack mMutualCallBack) {

        if (isInit) {
            Tlog.e(" Controller is already init");
            return;
        }

        isInit = true;

        MutualManager mMutualManger = new MutualManager(app, mMutualCallBack);
        mServices.add(INDEX_MUTUAL_MANAGER, mMutualManger);

    }

    public MutualManager getMutualManager() {
        if (mServices.size() <= INDEX_MUTUAL_MANAGER) {
            return null;
        }
        return (MutualManager) mServices.get(INDEX_MUTUAL_MANAGER);
    }

    private static final int INDEX_MUTUAL_MANAGER = 0;

    private volatile boolean create = false;

    @Override
    public void onSCreate() {
        if (create) {
            Tlog.e(" Controller is already create");
            return;
        }
        create = true;
        if (mServices.size() > 0) {
            for (IService mIService : mServices) {
                mIService.onSCreate();
            }
        }
        Tlog.v(" Controller onSCreate");
        Debuger.getInstance().onSCreate();
    }

    @Override
    public void onSResume() {
        if (mServices.size() > 0) {
            for (IService mIService : mServices) {
                mIService.onSResume();
            }
        }
        Tlog.v(" Controller onSResume");
        Debuger.getInstance().onSResume();
    }

    @Override
    public void onSPause() {
        if (mServices.size() > 0) {
            for (IService mIService : mServices) {
                mIService.onSPause();
            }
        }
        Tlog.v(" Controller onSPause");
        Debuger.getInstance().onSPause();
    }

    @Override
    public void onSDestroy() {
        if (mServices.size() > 0) {
            for (IService mIService : mServices) {
                mIService.onSDestroy();
            }
        }

        mServices.clear();
        this.isInit = false;
        this.create = false;
        Tlog.v(" Controller onSDestroy");
        Debuger.getInstance().onSDestroy();
    }

    @Override
    public void onSFinish() {
        if (mServices.size() > 0) {
            for (IService mIService : mServices) {
                mIService.onSFinish();
            }
        }
        this.isInit = false;
        this.create = false;
        Tlog.v(" Controller onSFinish");
        Debuger.getInstance().onSFinish();
    }
}
