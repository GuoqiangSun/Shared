package cn.com.startai.sharedlib.app.mutual.impl;

import android.app.Activity;
import android.content.Intent;

import cn.com.startai.sharedlib.app.mutual.IMutualCallBack;
import cn.com.startai.sharedlib.app.mutual.IUserIDManager;
import cn.com.startai.sharedlib.app.mutual.MutualManager;
import cn.com.swain.baselib.jsInterface.response.BaseResponseMethod;

/**
 * author: Guoqiang_Sun
 * date: 2019/1/24 0024
 * Desc:
 */
public class MutualSharedWrapper implements IMutualCallBack, IUserIDManager {

    public String TAG = MutualManager.TAG;

    private final IMutualCallBack mCallBack;
    private final IUserIDManager mUserID;

    public MutualSharedWrapper(IMutualCallBack mCallBack) {
        this(mCallBack, null);
    }

    public MutualSharedWrapper(IUserIDManager mUserID) {
        this(null, mUserID);
    }

    public MutualSharedWrapper(IMutualCallBack mCallBack, IUserIDManager mUserID) {
        this.mCallBack = mCallBack;
        this.mUserID = mUserID;
    }

    @Override
    public void jFinish() {
        mCallBack.jFinish();
    }

    @Override
    public void callJs(BaseResponseMethod mBaseResponseMethod) {
        mCallBack.callJs(mBaseResponseMethod);
    }

    @Override
    public void callJs(String method) {
        mCallBack.callJs(method);
    }

    @Deprecated
    @Override
    public void scanQR(int requestCode) {
        mCallBack.scanQR(requestCode);
    }

    @Override
    public void jsStartActivityForResult(Intent intent, int requestCode) {
        mCallBack.jsStartActivityForResult(intent, requestCode);
    }

    @Override
    public Activity getActivity() {
        return mCallBack != null ? mCallBack.getActivity() : null;
    }

    @Override
    public String getUserIDFromMq() {
        return mUserID.getUserIDFromMq();
    }

    @Override
    public String getUserID() {
        return mUserID.getUserID();
    }

    @Override
    public void setUserID(String userID) {
        mUserID.setUserID(userID);
    }
}
