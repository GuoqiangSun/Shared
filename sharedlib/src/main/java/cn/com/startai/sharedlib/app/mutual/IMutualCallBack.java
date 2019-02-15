package cn.com.startai.sharedlib.app.mutual;

import android.app.Activity;
import android.content.Intent;

import cn.com.startai.sharedlib.app.view.ICallJS;
import cn.com.swain.baselib.jsInterface.response.BaseResponseMethod;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/28 0028
 * desc :
 */
public interface IMutualCallBack extends ICallJS {

    /**
     * js请求finish,activity finish
     */
    void jFinish();

    /**
     * response to js
     */
    void callJs(BaseResponseMethod mBaseResponseMethod);

    @Deprecated
    void scanQR(int requestCode);

    void jsStartActivityForResult(Intent intent, int requestCode);

    Activity getActivity();
}
