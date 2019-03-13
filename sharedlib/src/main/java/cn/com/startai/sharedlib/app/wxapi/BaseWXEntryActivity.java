package cn.com.startai.sharedlib.app.wxapi;

import android.app.Activity;
import android.os.Bundle;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import cn.com.startai.sharedlib.app.controller.Controller;
import cn.com.startai.sharedlib.app.global.Debuger;
import cn.com.startai.sharedlib.app.mutual.MutualManager;
import cn.com.swain.baselib.log.Tlog;


/**
 * author: Guoqiang_Sun
 * date : 2019/2/18 0010
 * desc :
 */
public class BaseWXEntryActivity extends Activity implements IWXAPIEventHandler {

    private String TAG = MutualManager.TAG;

    private static final int RETURN_MSG_TYPE_SHARE = 2; //分享
    private static final int RETURN_MSG_TYPE_LOGIN = 1; //登录

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        setTheme(android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        super.onCreate(savedInstanceState);
        Tlog.v(TAG, "BaseWXEntryActivity.onCreate()");
        IWXAPI wxApi = WXApiHelper.getInstance().getWXApi(getApplication());
        wxApi.handleIntent(getIntent(), this);
    }

    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq baseReq) {
        if (Debuger.isLogDebug) {
            Tlog.v(TAG, "BaseWXEntryActivity onReq: " + baseReq.toString());
        }
    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    //app发送消息给微信，处理返回消息的回调
    @Override
    public void onResp(BaseResp baseResp) {

        int type = baseResp.getType(); //类型：分享还是登录
        if (Debuger.isLogDebug) {
            Tlog.v(TAG, "BaseWXEntryActivity type:" + type + "; errStr: " + baseResp.errStr + "; errCode:" + baseResp.errCode);
        }

        if (type == RETURN_MSG_TYPE_LOGIN) {
            MutualManager mutualManager = Controller.getInstance().getMutualManager();
            if (mutualManager != null) {
                mutualManager.onWxEntryResult(baseResp);
            }
        }

        finish();
    }


}
