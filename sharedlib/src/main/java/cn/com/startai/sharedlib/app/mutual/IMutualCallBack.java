package cn.com.startai.sharedlib.app.mutual;

import android.app.Activity;
import android.content.Intent;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/28 0028
 * desc :
 */
public interface IMutualCallBack {

    void jFinish();

    void callJs(String method);

    void scanQR(int code);

    void jsStartActivityForResult(Intent intent, int takePhotoCode);

    Activity getActivity();
}
