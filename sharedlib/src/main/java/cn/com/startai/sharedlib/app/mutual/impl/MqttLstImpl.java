package cn.com.startai.sharedlib.app.mutual.impl;

import android.app.Activity;
import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.PayTask;
import com.blankj.utilcode.util.ActivityUtils;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelpay.PayReq;

import java.util.HashMap;
import java.util.Map;

import cn.com.startai.chargersdk.AOnChargerMessageArriveListener;
import cn.com.startai.chargersdk.ChargerBusiManager;
import cn.com.startai.chargersdk.entity.C_0x8301;
import cn.com.startai.chargersdk.entity.C_0x8302;
import cn.com.startai.chargersdk.entity.C_0x8304;
import cn.com.startai.chargersdk.entity.C_0x8305;
import cn.com.startai.chargersdk.entity.C_0x8306;
import cn.com.startai.chargersdk.entity.C_0x8307;
import cn.com.startai.chargersdk.entity.C_0x8308;
import cn.com.startai.chargersdk.entity.C_0x8309;
import cn.com.startai.chargersdk.entity.C_0x8310;
import cn.com.startai.chargersdk.entity.C_0x8311;
import cn.com.startai.mqttsdk.base.BaseMessage;
import cn.com.startai.mqttsdk.base.StartaiError;
import cn.com.startai.mqttsdk.busi.entity.C_0x8016;
import cn.com.startai.mqttsdk.busi.entity.C_0x8018;
import cn.com.startai.mqttsdk.busi.entity.C_0x8020;
import cn.com.startai.mqttsdk.busi.entity.C_0x8021;
import cn.com.startai.mqttsdk.busi.entity.C_0x8024;
import cn.com.startai.mqttsdk.busi.entity.C_0x8025;
import cn.com.startai.mqttsdk.busi.entity.C_0x8028;
import cn.com.startai.mqttsdk.busi.entity.C_0x8031;
import cn.com.startai.mqttsdk.busi.entity.C_0x8033;
import cn.com.startai.mqttsdk.busi.entity.type.Type;
import cn.com.startai.mqttsdk.listener.IOnCallListener;
import cn.com.startai.mqttsdk.mqtt.request.MqttPublishRequest;
import cn.com.startai.sharedcharger.wxapi.WXApiHelper;
import cn.com.startai.sharedlib.app.Debuger;
import cn.com.startai.sharedlib.app.js.Utils.JSErrorCode;
import cn.com.startai.sharedlib.app.js.method2Impl.AliLoginResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.BalanceDepositResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.BalancePayResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.BorrowDeviceResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.DeviceInfoResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.GetIdentityCodeResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.GiveBackDeviceResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.IsLeastVersionResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.LoginOutResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.MobileLoginByIDCodeResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.ModifyUserInfoResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.ModifyUserPwdResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.OrderDetailResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.OrderListResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.ThirdPayResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.UserInfoResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.WxLoginResponseMethod;
import cn.com.startai.sharedlib.app.mutual.IMutualCallBack;
import cn.com.startai.sharedlib.app.mutual.MutualManager;
import cn.com.startai.sharedlib.app.mutual.utils.AuthResult;
import cn.com.swain.baselib.jsInterface.method.BaseResponseMethod;
import cn.com.swain169.log.Tlog;

/**
 * author: Guoqiang_Sun
 * date: 2018/12/25 0025
 * Desc:
 */
public class MqttLstImpl extends AOnChargerMessageArriveListener {

    private String TAG = MutualManager.TAG;

    private final Application app;
    private final IMutualCallBack mCallBack;
    private final MutualManager mMutualManager;

    public MqttLstImpl(Application app, IMutualCallBack mCallBack, MutualManager mMutualManager) {
        this.app = app;
        this.mCallBack = mCallBack;
        this.mMutualManager = mMutualManager;
    }

    private void callJs(BaseResponseMethod mBaseResponseMethod) {
        if (mCallBack != null) {
            mCallBack.callJs(mBaseResponseMethod.toMethod());
        } else {
            Tlog.e(TAG, " MqttLstImpl callJs mCallBack=null " + mBaseResponseMethod.toMethod());
        }
    }

    @Override
    public void onCommand(String s, String s1) {
        if (Debuger.isLogDebug) {
            Tlog.d(TAG, " onCommand :" + String.valueOf(s) + "--" + String.valueOf(s1));
        }
    }

    @Override
    public boolean needUISafety() {
        return false;
    }

    @Override
    public void onGetAlipayAuthInfoResult(C_0x8033.Resp resp) {
        super.onGetAlipayAuthInfoResult(resp);

        if (Debuger.isLogDebug) {
            Tlog.d(TAG, " onGetAlipayPrivateKeyResult :" + String.valueOf(resp));
        }

        if (resp.getResult() == BaseMessage.RESULT_SUCCESS) {

            Tlog.d(TAG, "支付宝密钥获取成功，准备登录 ");

            String AUTH_INFO = resp.getContent().getAliPayAuthInfo();
            aliLogin(AUTH_INFO);

        } else {
            AliLoginResponseMethod mAliResponseMethod = AliLoginResponseMethod.getAliLoginResponseMethod();
            mAliResponseMethod.releaseCache();
            mAliResponseMethod.setResult(false);
            mAliResponseMethod.setErrorCode(String.valueOf(resp.getContent().getErrcode()));
            callJs(mAliResponseMethod);
        }

    }


    // 支付宝登陆
    private void aliLogin(String AUTH_INFO) {

        Tlog.d(TAG, " aliLogin autoInfo = " + AUTH_INFO);
        Activity activity = mCallBack != null ? mCallBack.getActivity() : null;

        // 调用授权接口，获取授权结果
        AuthTask authTask = new AuthTask(activity);
        Map<String, String> result = authTask.authV2(AUTH_INFO, true);
        AuthResult authResult = new AuthResult(result, true);

        String resultStatus = authResult.getResultStatus();
        String resultCode = authResult.getResultCode();

        // 判断resultStatus 为“9000”且result_code为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
        Tlog.d(TAG, " resultStatus:" + resultStatus + " resultCode:" + resultCode);

        if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(resultCode, "200")) {
            // 获取alipay_open_id，调支付时作为参数extern_token 的value
            // 传入，则支付账户为该授权账户

            ChargerBusiManager.getInstance().loginWithThirdAccount(Type.Login.THIRD_ALIPAY, authResult.getAuthCode(), new IOnCallListener() {
                @Override
                public void onSuccess(MqttPublishRequest mqttPublishRequest) {
                    Tlog.i(TAG, "loginWithThirdAccount ali msg send success ");
                }

                @Override
                public void onFailed(MqttPublishRequest mqttPublishRequest, StartaiError startaiError) {
                    if (Debuger.isDebug) {
                        Tlog.e(TAG, "loginWithThirdAccount ali msg send fail " + String.valueOf(startaiError));
                    }

                    AliLoginResponseMethod mAliResponseMethod = AliLoginResponseMethod.getAliLoginResponseMethod();
                    mAliResponseMethod.releaseCache();
                    mAliResponseMethod.setResult(false);
                    mAliResponseMethod.setErrorCode(String.valueOf(startaiError.getErrorCode()));
                    callJs(mAliResponseMethod);

                }

                @Override
                public boolean needUISafety() {
                    return false;
                }
            });


        } else {
            // 其他状态值则为授权失败
//                            result_status
//                            9000	请求处理成功
//                            4000	系统异常
//                            6001	用户中途取消
//                            6002	网络连接出错

//                            result_code
//                            200	业务处理成功，会返回authCode
//                            1005	账户已冻结，如有疑问，请联系支付宝技术支持
//                            202	系统异常，请稍后再试或联系支付宝技术支持


            AliLoginResponseMethod mAliResponseMethod = AliLoginResponseMethod.getAliLoginResponseMethod();
            mAliResponseMethod.releaseCache();
            mAliResponseMethod.setResult(false);
            mAliResponseMethod.setErrorCode(String.valueOf(resultStatus));
            callJs(mAliResponseMethod);


        }
    }


    @Override
    public void onLoginResult(C_0x8018.Resp resp) {
        super.onLoginResult(resp);
        if (Debuger.isLogDebug) {
            Tlog.d(TAG, " onLoginResult :" + String.valueOf(resp));
        }
        C_0x8018.Resp.ContentBean loginInfo = resp.getContent();

        switch (loginInfo.getType()) {
            case 1: // email

                break;
            case 2://mobile + code
            case 3://mobile + pwd
            case 5:// mobile + code + pwd

                MobileLoginByIDCodeResponseMethod mobileLoginByIDCodeResponseMethod =
                        MobileLoginByIDCodeResponseMethod.getMobileLoginByIDCodeResponseMethod();
                mobileLoginByIDCodeResponseMethod.setResult(resp.getResult() == 1);
                mobileLoginByIDCodeResponseMethod.setIsLogin(resp.getResult() == 1);
                mobileLoginByIDCodeResponseMethod.setErrorCode(loginInfo.getErrcode());
                callJs(mobileLoginByIDCodeResponseMethod);
                break;
            case 4:// user + pwd
                break;

            case 10: // wx

                WxLoginResponseMethod mWxResponseMethod =
                        WxLoginResponseMethod.getWxLoginResponseMethod();
                mWxResponseMethod.setResult(resp.getResult() == 1);
                mWxResponseMethod.setErrorCode(loginInfo.getErrcode());
                mWxResponseMethod.setName(loginInfo.getuName());
                callJs(mWxResponseMethod);

                break;
            case Type.Login.THIRD_ALIPAY:

                AliLoginResponseMethod mAliResponseMethod =
                        AliLoginResponseMethod.getAliLoginResponseMethod();
                mAliResponseMethod.setResult(resp.getResult() == 1);
                mAliResponseMethod.setErrorCode(loginInfo.getErrcode());
                mAliResponseMethod.setName(loginInfo.getuName());
                callJs(mAliResponseMethod);

                break;
        }

        String userIDFromMq = mMutualManager.getUserIDFromMq();
        Tlog.d(TAG, " login userIDFromMq:" + userIDFromMq);
        mMutualManager.setUserID(userIDFromMq);
    }

    @Override
    public void onLogoutResult(int result, String errorCode, String errorMsg) {
        super.onLogoutResult(result, errorCode, errorMsg);
        if (Debuger.isLogDebug) {
            Tlog.d(TAG, " onLogoutResult result:" + result + " " + errorCode + ":" + errorMsg);
        }
        LoginOutResponseMethod loginOutResponseMethod =
                LoginOutResponseMethod.getLoginOutResponseMethod();
        loginOutResponseMethod.setResult(result == 1);
        loginOutResponseMethod.setIsLoginOut(result == 1);
        loginOutResponseMethod.setErrorCode(errorCode);
        callJs(loginOutResponseMethod);

        String userIDFromMq = mMutualManager.getUserIDFromMq();
        Tlog.d(TAG, " logout userIDFromMq:" + userIDFromMq);
        mMutualManager.setUserID(userIDFromMq);
    }


    @Override
    public void onBorrowResult(C_0x8301.Resp resp) {
        super.onBorrowResult(resp);
        if (Debuger.isLogDebug) {
            Tlog.d(TAG, " onBorrowResult :" + String.valueOf(resp));
        }

        BorrowDeviceResponseMethod borrowDeviceResponseMethod =
                BorrowDeviceResponseMethod.getBorrowDeviceResponseMethod();
        borrowDeviceResponseMethod.setResult(resp.getResult() == 1);
        C_0x8301.Resp.ContentBean content = resp.getContent();
        borrowDeviceResponseMethod.setContent(content);
        if (content != null) {
            borrowDeviceResponseMethod.setErrorCode(content.getErrcode());
        }
        callJs(borrowDeviceResponseMethod);
    }

    @Override
    public void onGiveBackResult(C_0x8302.Resp resp) {
        super.onGiveBackResult(resp);
        if (Debuger.isLogDebug) {
            Tlog.d(TAG, " onGiveBackResult :" + String.valueOf(resp));
        }

        GiveBackDeviceResponseMethod giveBackDeviceResponseMethod =
                GiveBackDeviceResponseMethod.getGiveBackDeviceResponseMethod();
        giveBackDeviceResponseMethod.setResult(resp.getResult() == 1);

        C_0x8302.Resp.ContentBean content = resp.getContent();
        giveBackDeviceResponseMethod.setContent(content);
        if (content != null) {
            giveBackDeviceResponseMethod.setErrorCode(content.getErrcode());
        }
        callJs(giveBackDeviceResponseMethod);

    }

    //            查询网点信息
    @Override
    public void onGetStoreInfoResult(C_0x8304.Resp resp) {
        super.onGetStoreInfoResult(resp);
        if (Debuger.isLogDebug) {
            Tlog.d(TAG, " onGetStoreInfoResult :" + String.valueOf(resp));
        }
    }

    //            查询机柜信息
    @Override
    public void onGetChargerInfoResult(C_0x8305.Resp resp) {
        super.onGetChargerInfoResult(resp);
        if (Debuger.isLogDebug) {
            Tlog.d(TAG, " onGetChargerInfoResult :" + String.valueOf(resp));
        }
        DeviceInfoResponseMethod deviceInfoResponseMethod =
                DeviceInfoResponseMethod.getDeviceInfoResponseMethod();
        deviceInfoResponseMethod.setResult(resp.getResult() == 1);

        C_0x8305.Resp.ContentBean content = resp.getContent();
        if (content != null) {
            deviceInfoResponseMethod.setErrorCode(content.getErrcode());
            deviceInfoResponseMethod.setIsOnline(content.isIsOnline());
        }
        callJs(deviceInfoResponseMethod);
    }

    @Override
    public void onGetFeeRuleResult(C_0x8306.Resp resp) {
        super.onGetFeeRuleResult(resp);
        if (Debuger.isLogDebug) {
            Tlog.d(TAG, " onGetFeeRuleResult :" + String.valueOf(resp));
        }
    }

    // 请求充值
    @Override
    public void onRequestRechargeResult(C_0x8307.Resp resp) {
        super.onRequestRechargeResult(resp);

        if (Debuger.isLogDebug) {
            Tlog.d(TAG, " onRequestRechargeResult :" + String.valueOf(resp));
        }
    }

    //  请求查询余额
    @Override
    public void onGetBalanceAndDepositResult(C_0x8308.Resp resp) {
        super.onGetBalanceAndDepositResult(resp);

        if (Debuger.isLogDebug) {
            Tlog.d(TAG, " onGetBalanceAndDepositResult :" + String.valueOf(resp));
        }

        BalanceDepositResponseMethod mBalanceDepositResponseMethod = BalanceDepositResponseMethod.getBalanceDepositResponseMethod();
        mBalanceDepositResponseMethod.setResult(resp.getResult() == 1);
        C_0x8308.Resp.ContentBean content = resp.getContent();
        mBalanceDepositResponseMethod.setContent(content);
        if (content != null) {
            mBalanceDepositResponseMethod.setErrorCode(content.getErrcode());
        }
        callJs(mBalanceDepositResponseMethod);
    }

    // 查询订单列表
    @Override
    public void onGetOrderListResult(C_0x8309.Resp resp) {
        super.onGetOrderListResult(resp);

        if (Debuger.isLogDebug) {
            Tlog.d(TAG, " onGetOrderListResult :" + String.valueOf(resp));
        }

        OrderListResponseMethod orderListResponseMethod = OrderListResponseMethod.getOrderListResponseMethod();
        orderListResponseMethod.setResult(resp.getResult() == BaseMessage.RESULT_SUCCESS);
        C_0x8309.Resp.ContentBean content = resp.getContent();
        orderListResponseMethod.setOrderList(content);
        if (content != null) {
            orderListResponseMethod.setErrorCode(content.getErrcode());
        }
        callJs(orderListResponseMethod);
    }

    // 查询订单详情
    @Override
    public void onGetOrderDetailResult(C_0x8310.Resp resp) {
        super.onGetOrderDetailResult(resp);

        if (Debuger.isLogDebug) {
            Tlog.d(TAG, " onGetOrderDetailResult :" + String.valueOf(resp));
        }

        OrderDetailResponseMethod orderDetailResponseMethod = OrderDetailResponseMethod.getOrderDetailResponseMethod();
        orderDetailResponseMethod.setResult(resp.getResult() == 1);
        C_0x8310.Resp.ContentBean content = resp.getContent();
        orderDetailResponseMethod.setContent(content);
        if (content != null) {
            orderDetailResponseMethod.setErrorCode(content.getErrcode());
        }
        callJs(orderDetailResponseMethod);

    }

    // 请求用余额支付订单
    @Override
    public void onPayForOrderWithBalanceResult(C_0x8311.Resp resp) {
        super.onPayForOrderWithBalanceResult(resp);

        if (Debuger.isLogDebug) {
            Tlog.d(TAG, " onPayForOrderWithBalanceResult :" + String.valueOf(resp));
        }

        BalancePayResponseMethod balancePayResponseMethod = BalancePayResponseMethod.getBalancePayResponseMethod();
        balancePayResponseMethod.setResult(resp.getResult() == 1);
        C_0x8311.Resp.ContentBean content = resp.getContent();
        balancePayResponseMethod.setContent(content);
        if (content != null) {
            balancePayResponseMethod.setErrorCode(content.getErrcode());
        }
        callJs(balancePayResponseMethod);
    }

    @Override
    public void onGetRealOrderPayStatus(C_0x8031.Resp resp) {
        super.onGetRealOrderPayStatus(resp);

        if (Debuger.isLogDebug) {
            Tlog.d(TAG, " onGetRealOrderPayStatus :" + String.valueOf(resp));
        }

        ThirdPayResponseMethod thirdPayResponseMethod = ThirdPayResponseMethod.getThirdPayResponseMethod();
        thirdPayResponseMethod.setResult(resp.getResult() == 1);
        C_0x8031.Resp.ContentBean content = resp.getContent();
        if (content != null) {
            thirdPayResponseMethod.setErrorCode(content.getErrcode());
        }
        thirdPayResponseMethod.setContent(content);
        callJs(thirdPayResponseMethod);

    }

    private final IOnCallListener mThirdPayLsn = new IOnCallListener() {
        @Override
        public void onSuccess(MqttPublishRequest request) {
            if (Debuger.isLogDebug) {
                Tlog.v(TAG, " getRealOrderPayStatus msg send success");
            }
        }

        @Override
        public void onFailed(MqttPublishRequest request, StartaiError startaiError) {

            if (Debuger.isLogDebug) {
                Tlog.e(TAG, "getRealOrderPayStatus msg send fail "
                        + startaiError.getErrorCode());
            }

            ThirdPayResponseMethod thirdPayResponseMethod = ThirdPayResponseMethod.getThirdPayResponseMethod();
            thirdPayResponseMethod.setResult(false);
            thirdPayResponseMethod.setErrorCode(String.valueOf(startaiError.getErrorCode()));
            callJs(thirdPayResponseMethod);
        }

        @Override
        public boolean needUISafety() {
            return false;
        }
    };

    private final Map<String, String> payNoPrepayidMap = new HashMap<>();

    // 第三方支付订单
    @Override
    public void onThirdPaymentUnifiedOrderResult(C_0x8028.Resp resp) {
        super.onThirdPaymentUnifiedOrderResult(resp);

        if (Debuger.isLogDebug) {
            Tlog.d(TAG, " onThirdPaymentUnifiedOrderResult :" + String.valueOf(resp));
        }

        if (resp.getResult() == 1) {

            if (resp.getContent().getPlatform() == Type.ThirdPayment.PLATFOME_WECHAT) {

                C_0x8028.Resp.ContentBean content = resp.getContent();
                payNoPrepayidMap.put(content.getWX_Prepayid(), content.getOrder_num());

                PayReq payReq = miofResponseToWechatPayReq(resp.getContent());
                WXApiHelper.getInstance().getWXApi(app).sendReq(payReq);

            } else if (resp.getContent().getPlatform() == Type.ThirdPayment.PLATFOME_ALIPAY) {
                PayResult payResult = requestAlipay(resp.getContent().getWX_Sign());

                ChargerBusiManager.getInstance().getRealOrderPayStatus(payResult
                        .getResult()
                        .getAlipay_trade_app_pay_response()
                        .getOut_trade_no(), mThirdPayLsn);

            } else {
                ThirdPayResponseMethod thirdPayResponseMethod = ThirdPayResponseMethod.getThirdPayResponseMethod();
                thirdPayResponseMethod.setResult(false);
                thirdPayResponseMethod.setErrorCode(String.valueOf(resp.getContent().getErrcode()));
                callJs(thirdPayResponseMethod);
            }

        } else {
            ThirdPayResponseMethod thirdPayResponseMethod = ThirdPayResponseMethod.getThirdPayResponseMethod();
            thirdPayResponseMethod.setResult(false);
            thirdPayResponseMethod.setErrorCode(String.valueOf(resp.getContent().getErrcode()));
            callJs(thirdPayResponseMethod);
        }

    }

    private static PayResult requestAlipay(String payInfo) {
        PayTask alipay = new PayTask(ActivityUtils.getTopActivity());
        Map<String, String> result = alipay.payV2(payInfo, true);
        return new PayResult(result);
    }

    private static PayReq miofResponseToWechatPayReq(C_0x8028.Resp.ContentBean content) {
        PayReq req = new PayReq();
        req.appId = content.getWX_Appid();
        req.partnerId = content.getWX_Partnerid();
        req.prepayId = content.getWX_Prepayid();
        req.nonceStr = content.getWX_Noncestr();
        req.timeStamp = content.getWX_Timestamp();
        req.packageValue = content.getWX_Package_();
        req.sign = content.getWX_Sign();
        return req;
    }


    @Override
    public void onGetIdentifyCodeResult(C_0x8021.Resp resp) {
        super.onGetIdentifyCodeResult(resp);
        if (Debuger.isLogDebug) {
            Tlog.d(TAG, " onGetIdentifyCodeResult :" + String.valueOf(resp));
        }

        GetIdentityCodeResponseMethod getIdentityCodeResponseMethod =
                GetIdentityCodeResponseMethod.getGetIdentityCodeResponseMethod();
        getIdentityCodeResponseMethod.setResult(resp.getResult() == 1);
        getIdentityCodeResponseMethod.setErrorCode(resp.getContent().getErrcode());
        callJs(getIdentityCodeResponseMethod);

    }

    @Override
    public void onGetLatestVersionResult(C_0x8016.Resp resp) {
        super.onGetLatestVersionResult(resp);
        if (Debuger.isLogDebug) {
            Tlog.d(TAG, " onGetLatestVersionResult :" + String.valueOf(resp));
        }

        IsLeastVersionResponseMethod isLeastVersionResponseMethod =
                IsLeastVersionResponseMethod.getIsLeastVersionResponseMethod();
        isLeastVersionResponseMethod.setResult(resp.getResult() == 1);
        isLeastVersionResponseMethod.setErrorCode(resp.getContent().getErrcode());

        boolean isLatestVersion = true;
        if (resp.getResult() == 1) {
            try {
                PackageInfo packageInfo = app.getPackageManager()
                        .getPackageInfo(app.getPackageName(), 0);

                Tlog.v(TAG, " myVersionCode:" + packageInfo.getLongVersionCode()
                        + " sVersionCode:" + resp.getContent().getVersionCode());

                isLatestVersion = packageInfo.getLongVersionCode() < resp.getContent().getVersionCode();

            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            String tmpDownloadUrl = resp.getContent().getUpdateUrl();
            Tlog.v(TAG, " onGetLatestVersionResult:  downloadUrl" + tmpDownloadUrl);
            isLeastVersionResponseMethod.setDownloadPath(tmpDownloadUrl);
        }

        isLeastVersionResponseMethod.setNeedUpgrade(isLatestVersion);
        callJs(isLeastVersionResponseMethod);

    }

    @Override
    public void onGetUserInfoResult(C_0x8024.Resp resp) {
        super.onGetUserInfoResult(resp);

        if (Debuger.isLogDebug) {
            Tlog.v(TAG, " onGetUserInfoResult:" + String.valueOf(resp));
        }

        UserInfoResponseMethod userInfoResponseMethod = UserInfoResponseMethod.getUserInfoResponseMethod();
        userInfoResponseMethod.setResult(resp.getResult() == 1);

        C_0x8024.Resp.ContentBean contentBean = resp.getContent();
        userInfoResponseMethod.setContent(contentBean);

        if (contentBean != null) {
            userInfoResponseMethod.setErrorCode(contentBean.getErrcode());
        }
        callJs(userInfoResponseMethod);
    }

    @Override
    public void onUpdateUserInfoResult(C_0x8020.Resp resp) {
        super.onUpdateUserInfoResult(resp);

        if (Debuger.isLogDebug) {
            Tlog.v(TAG, " onUpdateUserInfoResult:" + String.valueOf(resp));
        }

        ModifyUserInfoResponseMethod userInfoResponseMethod =
                ModifyUserInfoResponseMethod.getModifyUserInfoResponseMethod();
        userInfoResponseMethod.setResult(resp.getResult() == 1);
        C_0x8020.Resp.ContentBean contentBean = resp.getContent();
        userInfoResponseMethod.setContent(contentBean);
        if (contentBean != null) {
            userInfoResponseMethod.setErrorCode(contentBean.getErrcode());
        }
        callJs(userInfoResponseMethod);
    }

    @Override
    public void onUpdateUserPwdResult(C_0x8025.Resp resp) {
        super.onUpdateUserPwdResult(resp);
        if (Debuger.isLogDebug) {
            Tlog.v(TAG, " onUpdateUserPwdResult:" + String.valueOf(resp));
        }

        ModifyUserPwdResponseMethod modifyUserPwdResponseMethod =
                ModifyUserPwdResponseMethod.getModifyUserPwdResponseMethod();
        modifyUserPwdResponseMethod.setResult(resp.getResult() == 1);
        C_0x8025.Resp.ContentBean content = resp.getContent();
        if (content != null) {
            modifyUserPwdResponseMethod.setErrorCode(content.getErrcode());
        }
        modifyUserPwdResponseMethod.setIsSuccessfully(resp.getResult() == 1);
        callJs(modifyUserPwdResponseMethod);

    }

    // wx pay result
    public void onWxPayResult(BaseResp baseResp) {
        //resp.errCode == 0 支付成功
        //resp.errCode == -2 用户取消支付
        //resp.errCode == -1 未正确配置开发环境，签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。

        if (baseResp.errCode == BaseResp.ErrCode.ERR_OK) {

            //[{_wxapi_baseresp_transaction=null, _wxapi_payresp_extdata=null, _wxapi_command_type=5,
            // _wxapi_baseresp_errcode=0, _wxapi_baseresp_errstr=null, _wxapi_baseresp_openId=null,
            // _wxapi_payresp_returnkey=, _wxapi_payresp_prepayid=wx19161021755341a72e45a0160131517529}]


            String openId = baseResp.openId;
            String no = payNoPrepayidMap.get(openId);
            Tlog.v(TAG, " no:" + no);
            ChargerBusiManager.getInstance().getRealOrderPayStatus(no, mThirdPayLsn);

        } else {
            // error

            ThirdPayResponseMethod thirdPayResponseMethod = ThirdPayResponseMethod.getThirdPayResponseMethod();
            thirdPayResponseMethod.setResult(false);
            thirdPayResponseMethod.setErrorCode(String.valueOf(baseResp.errCode));
            callJs(thirdPayResponseMethod);
        }

    }

    // wx login result
    public void onWxLoginResult(BaseResp baseResp) {
        Tlog.v(TAG, "onWxLoginResult errorCode:" + baseResp.errCode);

        if (baseResp.errCode == BaseResp.ErrCode.ERR_OK) {

            String code = ((SendAuth.Resp) baseResp).code;
            onWxLoginSuccess(code);

        } else {

            onWxLoginFail(baseResp.errCode);

        }

    }

    private void onWxLoginFail(int errCode) {

        WxLoginResponseMethod mWxResponseMethod = WxLoginResponseMethod.getWxLoginResponseMethod();
        mWxResponseMethod.releaseCache();
        mWxResponseMethod.setResult(false);

        switch (errCode) {
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                Tlog.e(TAG, " user rejection wx login");
                mWxResponseMethod.setErrorCode(JSErrorCode.ERROR_CODE_WX_LOGIN_USER_REJECTION);
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                Tlog.e(TAG, " user cancel wx login ");
                mWxResponseMethod.setErrorCode(JSErrorCode.ERROR_CODE_WX_LOGIN_USER_CANCEL);
                break;
            default:
                Tlog.e(TAG, " wx login fail errorCode: " + errCode);
                mWxResponseMethod.setErrorCode(JSErrorCode.ERROR_CODE_WX_LOGIN_UNKNOWN);
                break;
        }

        callJs(mWxResponseMethod);
    }

    private void onWxLoginSuccess(String code) {

        Tlog.e(TAG, "onWxLoginSuccess code: " + code);

        ChargerBusiManager.getInstance().loginWithThirdAccount(Type.Login.THIRD_WECHAT, code, new IOnCallListener() {
            @Override
            public void onSuccess(MqttPublishRequest mqttPublishRequest) {
                Tlog.i(TAG, "loginWithThirdAccount wx msg send success ");
            }

            @Override
            public void onFailed(MqttPublishRequest mqttPublishRequest, StartaiError startaiError) {
                if (Debuger.isDebug) {
                    Tlog.e(TAG, "loginWithThirdAccount wx msg send fail " + String.valueOf(startaiError));
                }

                WxLoginResponseMethod mWxResponseMethod = WxLoginResponseMethod.getWxLoginResponseMethod();
                mWxResponseMethod.releaseCache();
                mWxResponseMethod.setResult(false);
                mWxResponseMethod.setErrorCode(String.valueOf(startaiError.getErrorCode()));
                callJs(mWxResponseMethod);

            }

            @Override
            public boolean needUISafety() {
                return false;
            }
        });

    }

}
