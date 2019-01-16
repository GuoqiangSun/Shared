package cn.com.shared.weblib.js;

import android.os.Looper;

import org.xwalk.core.JavascriptInterface;

import cn.com.swain.baselib.jsInterface.AbsJsInterface;
import cn.com.swain.baselib.jsInterface.request.IJSRequest;
import cn.com.swain.baselib.jsInterface.request.impl.StartaiDataJsRequest;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/30 0030
 * desc :
 */
public final class XWalkCommonJsRequest extends StartaiDataJsRequest {

    public XWalkCommonJsRequest(Looper mLooper, IJSRequest mCallBack) {
        super(mLooper, mCallBack);
    }

    @JavascriptInterface
    public void dataInteractionRequest(String value) {

        intervalDataInteractionRequest(value);

    }

    @Override
    public AbsJsInterface getJsInterface() {
        return this;
    }
}
