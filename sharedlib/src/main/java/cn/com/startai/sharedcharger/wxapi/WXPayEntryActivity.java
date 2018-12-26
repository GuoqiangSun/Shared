package cn.com.startai.sharedcharger.wxapi;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import cn.com.startai.mqttsdk.utils.SLog;
import cn.com.startai.sharedlib.app.controller.Controller;
import cn.com.startai.sharedlib.app.mutual.MutualManager;
import cn.com.swain169.log.Tlog;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = WXPayEntryActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.pay_result);
        SLog.d(TAG, "onCreate");
        WXApiHelper.getInstance().getWXApi(getApplication()).handleIntent(getIntent(), this);
    }


    @Override
    public void onReq(BaseReq req) {
        SLog.d(TAG, "onReq");
    }

    @Override
    public void onResp(BaseResp resp) {
        Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);

        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            Tlog.v("resp.transaction = " + resp.transaction);
            Tlog.v("resp.errStr = " + resp.errStr);

            //[{_wxapi_baseresp_transaction=null, _wxapi_payresp_extdata=null, _wxapi_command_type=5, _wxapi_baseresp_errcode=0, _wxapi_baseresp_errstr=null, _wxapi_baseresp_openId=null, _wxapi_payresp_returnkey=, _wxapi_payresp_prepayid=wx19161021755341a72e45a0160131517529}]

//            Bundle bundle = new Bundle();
//            resp.toBundle(bundle);
//            String wxapi_payresp_prepayid = bundle.getString("_wxapi_payresp_prepayid");
//            Tlog.v("resp.bundle = " + bundle.toString());


            MutualManager mutualManager = Controller.getInstance().getMutualManager();
            if (mutualManager != null) {
                mutualManager.onWxPayResult(resp);
            }

            //resp.errCode == 0 支付成功
            //resp.errCode == -2 用户取消支付
            //resp.errCode == -1 未正确配置开发环境，签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。

            finish();

        }
    }


}