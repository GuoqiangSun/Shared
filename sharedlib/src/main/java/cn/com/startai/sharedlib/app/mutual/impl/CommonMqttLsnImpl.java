package cn.com.startai.sharedlib.app.mutual.impl;

import android.Manifest;
import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;

import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.PayTask;
import com.blankj.utilcode.util.ActivityUtils;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelpay.PayReq;

import java.io.File;
import java.util.Map;

import cn.com.startai.mqttsdk.StartAI;
import cn.com.startai.mqttsdk.base.BaseMessage;
import cn.com.startai.mqttsdk.base.StartaiError;
import cn.com.startai.mqttsdk.busi.entity.C_0x8001;
import cn.com.startai.mqttsdk.busi.entity.C_0x8002;
import cn.com.startai.mqttsdk.busi.entity.C_0x8003;
import cn.com.startai.mqttsdk.busi.entity.C_0x8004;
import cn.com.startai.mqttsdk.busi.entity.C_0x8005;
import cn.com.startai.mqttsdk.busi.entity.C_0x8015;
import cn.com.startai.mqttsdk.busi.entity.C_0x8016;
import cn.com.startai.mqttsdk.busi.entity.C_0x8017;
import cn.com.startai.mqttsdk.busi.entity.C_0x8018;
import cn.com.startai.mqttsdk.busi.entity.C_0x8020;
import cn.com.startai.mqttsdk.busi.entity.C_0x8021;
import cn.com.startai.mqttsdk.busi.entity.C_0x8022;
import cn.com.startai.mqttsdk.busi.entity.C_0x8023;
import cn.com.startai.mqttsdk.busi.entity.C_0x8024;
import cn.com.startai.mqttsdk.busi.entity.C_0x8025;
import cn.com.startai.mqttsdk.busi.entity.C_0x8026;
import cn.com.startai.mqttsdk.busi.entity.C_0x8028;
import cn.com.startai.mqttsdk.busi.entity.C_0x8031;
import cn.com.startai.mqttsdk.busi.entity.C_0x8033;
import cn.com.startai.mqttsdk.busi.entity.C_0x8034;
import cn.com.startai.mqttsdk.busi.entity.C_0x8035;
import cn.com.startai.mqttsdk.busi.entity.C_0x8036;
import cn.com.startai.mqttsdk.busi.entity.C_0x8037;
import cn.com.startai.mqttsdk.busi.entity.C_0x8038;
import cn.com.startai.mqttsdk.busi.entity.C_0x8039;
import cn.com.startai.mqttsdk.busi.entity.C_0x8200;
import cn.com.startai.mqttsdk.busi.entity.type.Type;
import cn.com.startai.mqttsdk.event.IOnStartaiMsgArriveListener;
import cn.com.startai.mqttsdk.listener.IOnCallListener;
import cn.com.startai.mqttsdk.mqtt.request.MqttPublishRequest;
import cn.com.startai.sharedlib.app.global.Debuger;
import cn.com.startai.sharedlib.app.js.Utils.JSErrorCode;
import cn.com.startai.sharedlib.app.js.method2Impl.AliBindResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.AliLoginResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.AliUnBindResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.GetIdentityCodeResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.IsLeastVersionResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.LoginOutResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.MobileLoginByIDCodeResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.ModifyUserInfoResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.ModifyUserPwdResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.PhoneBindResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.ThirdBindResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.ThirdPayResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.ThirdUnbindResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.UserInfoResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.WxBindResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.WxLoginResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.WxUnBindResponseMethod;
import cn.com.startai.sharedlib.app.mutual.IMutualCallBack;
import cn.com.startai.sharedlib.app.mutual.IUserIDManager;
import cn.com.startai.sharedlib.app.mutual.utils.AuthResult;
import cn.com.startai.sharedlib.app.mutual.utils.DownloadTask;
import cn.com.startai.sharedlib.app.mutual.utils.PayResult;
import cn.com.startai.sharedlib.app.wxapi.WXApiHelper;
import cn.com.swain.baselib.log.Tlog;
import cn.com.swain.baselib.permission.PermissionHelper;

/**
 * author: Guoqiang_Sun
 * date: 2018/12/25 0025
 * Desc:
 */
public class CommonMqttLsnImpl extends MutualSharedWrapper implements IOnStartaiMsgArriveListener {

    private final Application app;

    public CommonMqttLsnImpl(Application app, IMutualCallBack mCallBack, IUserIDManager mUserID) {
        super(mCallBack, mUserID);
        this.app = app;
    }

    @Override
    public void onCommand(String s, String s1) {
//        if (Debuger.isLogDebug) {
//            Tlog.d(TAG, " onCommand :" + String.valueOf(s) + "--" + String.valueOf(s1));
//        }
    }

    @Override
    public void onLoginResult(C_0x8018.Resp resp) {
        if (Debuger.isLogDebug) {
            Tlog.d(TAG, " onLoginResult :" + String.valueOf(resp));
        }

        if (resp.getResult() == 1) {
            String userIDFromMq = getUserIDFromMq();
            Tlog.d(TAG, " login userIDFromMq:" + userIDFromMq);
            setUserID(userIDFromMq);
        }

        C_0x8018.Resp.ContentBean loginInfo = resp.getContent();

        switch (loginInfo.getType()) {
            case Type.Login.EMAIL_PWD: // email

                break;
            case Type.Login.MOBILE_PWD://mobile + code
            case Type.Login.MOBILE_CODE://mobile + pwd
            case Type.Login.MOBILE_CODE_PWD:// mobile + code + pwd

                MobileLoginByIDCodeResponseMethod mobileLoginByIDCodeResponseMethod =
                        MobileLoginByIDCodeResponseMethod.getMobileLoginByIDCodeResponseMethod();
                mobileLoginByIDCodeResponseMethod.setResult(resp.getResult() == 1);
                mobileLoginByIDCodeResponseMethod.setIsLogin(resp.getResult() == 1);
                mobileLoginByIDCodeResponseMethod.setErrorCode(loginInfo.getErrcode());
                callJs(mobileLoginByIDCodeResponseMethod);
                break;
            case Type.Login.UNAME_PWD:// user + pwd
                break;

            case Type.Login.THIRD_WECHAT: // wx

                WxLoginResponseMethod mWxResponseMethod =
                        WxLoginResponseMethod.getWxLoginResponseMethod();
                mWxResponseMethod.setResult(resp.getResult() == 1);
                mWxResponseMethod.setErrorCode(loginInfo.getErrcode());
                mWxResponseMethod.setName(loginInfo.getuName());
                callJs(mWxResponseMethod);

                break;
            case Type.Login.THIRD_ALIPAY://ali

                AliLoginResponseMethod mAliResponseMethod =
                        AliLoginResponseMethod.getAliLoginResponseMethod();
                mAliResponseMethod.setResult(resp.getResult() == 1);
                mAliResponseMethod.setErrorCode(loginInfo.getErrcode());
                mAliResponseMethod.setName(loginInfo.getuName());
                callJs(mAliResponseMethod);

                break;
        }

    }

    @Override
    public void onLogoutResult(int result, String errorCode, String errorMsg) {
        if (Debuger.isLogDebug) {
            Tlog.d(TAG, " onLogoutResult result:" + result
                    + " errorCode:" + String.valueOf(errorCode)
                    + " errorMsg:" + String.valueOf(errorMsg));
        }

        String userIDFromMq = getUserIDFromMq();
        Tlog.d(TAG, " logout getUserIDFromMq:" + userIDFromMq);
        setUserID(userIDFromMq);

        LoginOutResponseMethod loginOutResponseMethod =
                LoginOutResponseMethod.getLoginOutResponseMethod();
        loginOutResponseMethod.setResult(result == 1);
        loginOutResponseMethod.setIsLoginOut(result == 1);
        loginOutResponseMethod.setErrorCode(errorCode);
        callJs(loginOutResponseMethod);

    }

    @Override
    public void onGetRealOrderPayStatus(C_0x8031.Resp resp) {

        if (Debuger.isLogDebug) {
            Tlog.d(TAG, " onGetRealOrderPayStatus :" + String.valueOf(resp));
        }

        ThirdPayResponseMethod thirdPayResponseMethod =
                ThirdPayResponseMethod.getThirdPayResponseMethod();
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

            ThirdPayResponseMethod thirdPayResponseMethod =
                    ThirdPayResponseMethod.getThirdPayResponseMethod();
            thirdPayResponseMethod.setResult(false);
            thirdPayResponseMethod.setErrorCode(String.valueOf(startaiError.getErrorCode()));
            callJs(thirdPayResponseMethod);
        }

    };

    /**
     * if wx pay see {@link #onWxPayResult(BaseResp)}
     * <p>
     * if ali pay see {@link #onGetRealOrderPayStatus(C_0x8031.Resp)}
     */
    // 第三方支付订单
    @Override
    public void onThirdPaymentUnifiedOrderResult(C_0x8028.Resp resp) {

        if (Debuger.isLogDebug) {
            Tlog.d(TAG, " onThirdPaymentUnifiedOrderResult :" + String.valueOf(resp));
        }

        if (resp.getResult() == 1) {

            if (resp.getContent().getPlatform() == Type.ThirdPayment.PLATFOME_WECHAT) {

                PayReq payReq = miofResponseToWechatPayReq(resp.getContent());
                payReq.extData = resp.getContent().getOrder_num();
                WXApiHelper.getInstance().getWXApi(app).sendReq(payReq);

            } else if (resp.getContent().getPlatform() == Type.ThirdPayment.PLATFOME_ALIPAY) {
                PayResult payResult = requestAlipay(resp.getContent().getWX_Sign());

                String resultStatus = payResult.getResultStatus();

                if (TextUtils.equals(resultStatus, "9000")) {

                    StartAI.getInstance().getBaseBusiManager().getRealOrderPayStatus(payResult
                            .getResult()
                            .getAlipay_trade_app_pay_response()
                            .getOut_trade_no(), mThirdPayLsn);

                } else {

                    ThirdPayResponseMethod thirdPayResponseMethod =
                            ThirdPayResponseMethod.getThirdPayResponseMethod();
                    thirdPayResponseMethod.setResult(false);
                    thirdPayResponseMethod.setErrorCode(String.valueOf(resultStatus));
                    callJs(thirdPayResponseMethod);

                }


            }

        } else {
            ThirdPayResponseMethod thirdPayResponseMethod =
                    ThirdPayResponseMethod.getThirdPayResponseMethod();
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
    public void onCheckIdetifyResult(C_0x8022.Resp resp) {

        if (Debuger.isLogDebug) {
            Tlog.d(TAG, " onCheckIdetifyResult :" + String.valueOf(resp));
        }
        C_0x8022.Resp.ContentBean content = resp.getContent();
        if (content.getType() == Type.CheckIdentifyCode.BIND_MOBILE_NUM) {

            if (resp.getResult() == 1) {

                C_0x8034.Req.ContentBean req = new C_0x8034.Req.ContentBean(getUserID(), content.getMobile());
                //mobile 需要绑定的手机号
                StartAI.getInstance().getBaseBusiManager().bindMobileNum(req, new IOnCallListener() {
                    @Override
                    public void onSuccess(MqttPublishRequest request) {
                        if (Debuger.isLogDebug) {
                            Tlog.i(TAG, "bindMobileNum  msg send success ");
                        }
                    }

                    @Override
                    public void onFailed(MqttPublishRequest request, StartaiError startaiError) {

                        if (Debuger.isLogDebug) {
                            Tlog.e(TAG, "bindMobileNum msg send fail " + String.valueOf(startaiError));
                        }

                        PhoneBindResponseMethod phoneBindResponseMethod =
                                PhoneBindResponseMethod.getPhoneBindResponseMethod();
                        phoneBindResponseMethod.setBinding(false);
                        phoneBindResponseMethod.setResult(false);
                        phoneBindResponseMethod.setErrorCode(String.valueOf(startaiError.getErrorCode()));
                        callJs(phoneBindResponseMethod);

                    }

                });
            } else {
                PhoneBindResponseMethod phoneBindResponseMethod =
                        PhoneBindResponseMethod.getPhoneBindResponseMethod();
                phoneBindResponseMethod.setBinding(false);
                phoneBindResponseMethod.setResult(false);
                phoneBindResponseMethod.setErrorCode(content.getErrcode());
                callJs(phoneBindResponseMethod);
            }

        }
    }


    @Override
    public void onGetLatestVersionResult(C_0x8016.Resp resp) {
        if (Debuger.isLogDebug) {
            Tlog.d(TAG, " onGetLatestVersionResult :" + String.valueOf(resp));
        }

        IsLeastVersionResponseMethod isLeastVersionResponseMethod =
                IsLeastVersionResponseMethod.getIsLeastVersionResponseMethod();
        isLeastVersionResponseMethod.setResult(resp.getResult() == 1);
        isLeastVersionResponseMethod.setErrorCode(resp.getContent().getErrcode());

        boolean isLatestVersion = true;
        if (resp.getResult() == 1 && app != null) {
            try {
                PackageInfo packageInfo = app.getPackageManager()
                        .getPackageInfo(app.getPackageName(), 0);

                Tlog.v(TAG, " myVersionCode:" + packageInfo.getLongVersionCode()
                        + " sVersionCode:" + resp.getContent().getVersionCode());

                isLatestVersion = packageInfo.getLongVersionCode() < resp.getContent().getVersionCode();

            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                Tlog.e(TAG, " getVersion :NameNotFoundException ", e);
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

        if (Debuger.isLogDebug) {
            Tlog.v(TAG, " onGetUserInfoResult:" + String.valueOf(resp));
        }

        UserInfoResponseMethod userInfoResponseMethod =
                UserInfoResponseMethod.getUserInfoResponseMethod();
        userInfoResponseMethod.setResult(resp.getResult() == 1);

        C_0x8024.Resp.ContentBean contentBean = resp.getContent();

        if (resp.getResult() == 1 && contentBean != null) {
            // 有缓存头像,就用缓存头像,否则就去下载。

            String headPic = contentBean.getHeadPic();
            File cacheDownPath = DownloadTask.getCacheDownPath(headPic);

            if (headPic != null && !"".equalsIgnoreCase(headPic)) {
                //
                if (cacheDownPath.exists()) {
                    if (PermissionHelper.isGranted(app, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                        String absolutePath = cacheDownPath.getAbsolutePath();
                        Tlog.w(TAG, " onResultModifyUserInformation() use cache path:" + absolutePath);
                        // 替换成缓存头像

                        contentBean.setHeadPic("file://" + absolutePath);
                    }
                } else {
                    if (PermissionHelper.isGranted(app, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        new DownloadTask(headPic).execute();
                    }
                }
            }


        }

        userInfoResponseMethod.setContent(contentBean);

        if (contentBean != null) {
            userInfoResponseMethod.setErrorCode(contentBean.getErrcode());
        }
        callJs(userInfoResponseMethod);

    }

    @Override
    public void onUpdateUserInfoResult(C_0x8020.Resp resp) {

        if (Debuger.isLogDebug) {
            Tlog.v(TAG, " onUpdateUserInfoResult:" + String.valueOf(resp));
        }

        ModifyUserInfoResponseMethod userInfoResponseMethod =
                ModifyUserInfoResponseMethod.getModifyUserInfoResponseMethod();
        userInfoResponseMethod.setResult(resp.getResult() == 1);
        C_0x8020.Resp.ContentBean contentBean = resp.getContent();

        if (resp.getResult() == 1 && contentBean != null) {
            // 有缓存头像,就用缓存头像,否则就去下载。

            String headPic = contentBean.getHeadPic();
            File cacheDownPath = DownloadTask.getCacheDownPath(headPic);

            if (headPic != null && !"".equalsIgnoreCase(headPic)) {
                //
                if (cacheDownPath.exists()) {
                    if (PermissionHelper.isGranted(app, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                        String absolutePath = cacheDownPath.getAbsolutePath();
                        Tlog.w(TAG, " onResultModifyUserInformation() use cache path:" + absolutePath);
                        // 替换成缓存头像

                        contentBean.setHeadPic("file://" + absolutePath);
                    }
                } else {
                    if (PermissionHelper.isGranted(app, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        new DownloadTask(headPic).execute();
                    }
                }
            }


        }


        userInfoResponseMethod.setContent(contentBean);
        if (contentBean != null) {
            userInfoResponseMethod.setErrorCode(contentBean.getErrcode());
        }
        callJs(userInfoResponseMethod);
    }

    @Override
    public void onUpdateUserPwdResult(C_0x8025.Resp resp) {
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

    /**
     * result see {@link #onGetRealOrderPayStatus(C_0x8031.Resp)}
     */
    // wx pay result
    public void onWxPayResult(BaseResp baseResp) {
        //resp.errCode == 0 支付成功
        //resp.errCode == -2 用户取消支付
        //resp.errCode == -1 未正确配置开发环境，签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。

        if (baseResp.errCode == BaseResp.ErrCode.ERR_OK) {

            //[{_wxapi_baseresp_transaction=null, _wxapi_payresp_extdata=null, _wxapi_command_type=5,
            // _wxapi_baseresp_errcode=0, _wxapi_baseresp_errstr=null, _wxapi_baseresp_openId=null,
            // _wxapi_payresp_returnkey=, _wxapi_payresp_prepayid=wx19161021755341a72e45a0160131517529}]

            Bundle bundle = new Bundle();
            baseResp.toBundle(bundle);
            String no = bundle.getString("_wxapi_payresp_extdata");
            Tlog.v(TAG, "onWxPayResult no:" + no);

            StartAI.getInstance().getBaseBusiManager().getRealOrderPayStatus(no, mThirdPayLsn);

        } else {
            // error

            ThirdPayResponseMethod thirdPayResponseMethod =
                    ThirdPayResponseMethod.getThirdPayResponseMethod();
            thirdPayResponseMethod.setResult(false);
            thirdPayResponseMethod.setErrorCode(String.valueOf(baseResp.errCode));
            callJs(thirdPayResponseMethod);
        }

    }

    // wx login bind result
    public void onWxEntryResult(BaseResp baseResp) {
        Tlog.v(TAG, "WxEntryResult errorCode:" + baseResp.errCode);

        SendAuth.Resp resp = (SendAuth.Resp) baseResp;
        String state = resp.state;
        String code = resp.code;

        if (WXApiHelper.Constants.WX_LOGIN_TAG.equals(state)) { // wxlogin

            if (baseResp.errCode == BaseResp.ErrCode.ERR_OK) {

                onWxLoginSuccess(code);

            } else {

                onWxLoginFail(baseResp.errCode);

            }
        } else if (WXApiHelper.Constants.WX_BIND_TAG.equals(state)) { // wxbind

            if (baseResp.errCode == BaseResp.ErrCode.ERR_OK) {

                onWxBindSuccess(code);

            } else {

                onWxBindFail(baseResp.errCode);

            }
        }


    }

    private void onWxLoginFail(int errCode) {

        WxLoginResponseMethod mWxResponseMethod = WxLoginResponseMethod.getWxLoginResponseMethod();
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

        StartAI.getInstance().getBaseBusiManager().loginWithThirdAccount(Type.Login.THIRD_WECHAT, code,
                new IOnCallListener() {
                    @Override
                    public void onSuccess(MqttPublishRequest mqttPublishRequest) {
                        if (Debuger.isLogDebug) {
                            Tlog.i(TAG, "loginWithThirdAccount wx msg send success ");
                        }
                    }

                    @Override
                    public void onFailed(MqttPublishRequest mqttPublishRequest, StartaiError startaiError) {
                        if (Debuger.isLogDebug) {
                            Tlog.e(TAG, "loginWithThirdAccount wx msg send fail " + String.valueOf(startaiError));
                        }

                        WxLoginResponseMethod mWxResponseMethod = WxLoginResponseMethod.getWxLoginResponseMethod();
                        mWxResponseMethod.setResult(false);
                        mWxResponseMethod.setErrorCode(String.valueOf(startaiError.getErrorCode()));
                        callJs(mWxResponseMethod);

                    }
                });

    }


    private void onWxBindSuccess(String code) {

        Tlog.e(TAG, "onWxBindSuccess code: " + code);


        //发送请求 接口调用前需要调用 微信的第三方登录SDK 授权api 拿到 code

        C_0x8037.Req.ContentBean req = new C_0x8037.Req.ContentBean();
        req.setCode(code); //code 来自微信授权返回
        req.setType(C_0x8037.THIRD_WECHAT); //绑定微信账号

        StartAI.getInstance().getBaseBusiManager().bindThirdAccount(req, new IOnCallListener() {
            @Override
            public void onSuccess(MqttPublishRequest mqttPublishRequest) {
                if (Debuger.isLogDebug) {
                    Tlog.i(TAG, "bindThirdAccount wx msg send success ");
                }
            }

            @Override
            public void onFailed(MqttPublishRequest mqttPublishRequest, StartaiError startaiError) {
                if (Debuger.isLogDebug) {
                    Tlog.e(TAG, "bindThirdAccount wx msg send fail " + String.valueOf(startaiError));
                }

                WxBindResponseMethod mWxResponseMethod = WxBindResponseMethod.getWxBindResponseMethod();
                mWxResponseMethod.setResult(false);
                mWxResponseMethod.setErrorCode(String.valueOf(startaiError.getErrorCode()));
                callJs(mWxResponseMethod);

            }
        });

    }


    private void onWxBindFail(int errCode) {

        WxBindResponseMethod mWxResponseMethod = WxBindResponseMethod.getWxBindResponseMethod();
        mWxResponseMethod.setResult(false);

        switch (errCode) {
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                Tlog.e(TAG, " user rejection wx bind");
                mWxResponseMethod.setErrorCode(JSErrorCode.ERROR_CODE_WX_BIND_USER_REJECTION);
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                Tlog.e(TAG, " user cancel wx bind ");
                mWxResponseMethod.setErrorCode(JSErrorCode.ERROR_CODE_WX_BIND_USER_CANCEL);
                break;
            default:
                Tlog.e(TAG, " wx bind fail errorCode: " + errCode);
                mWxResponseMethod.setErrorCode(JSErrorCode.ERROR_CODE_WX_BIND_UNKNOWN);
                break;
        }

        callJs(mWxResponseMethod);
    }


    @Override
    public void onGetAlipayAuthInfoResult(C_0x8033.Resp resp) {

        if (Debuger.isLogDebug) {
            Tlog.d(TAG, " onGetAlipayPrivateKeyResult :" + String.valueOf(resp));
        }


        if (resp.getResult() == BaseMessage.RESULT_SUCCESS) {

            Tlog.d(TAG, "get alipay auth info success");

            String AUTH_INFO = resp.getContent().getAliPayAuthInfo();
            aliAuthInfo(AUTH_INFO, resp.getContent().getAuthTargetId());

        } else {

            String authTargetId = resp.getContent().getAuthTargetId(); // AUTH_1543265456453123 | LOGIN_15264564564
            if (authTargetId != null) {
                if (authTargetId.startsWith(C_0x8033.AUTH_TYPE_LOGIN)) {
                    AliLoginResponseMethod mAliResponseMethod =
                            AliLoginResponseMethod.getAliLoginResponseMethod();
                    mAliResponseMethod.setResult(false);
                    mAliResponseMethod.setErrorCode(String.valueOf(resp.getContent().getErrcode()));
                    callJs(mAliResponseMethod);
                } else if (authTargetId.startsWith(C_0x8033.AUTH_TYPE_AUTH)) {
                    AliBindResponseMethod aliBindResponseMethod =
                            AliBindResponseMethod.getAliBindResponseMethod();
                    aliBindResponseMethod.setResult(false);
                    aliBindResponseMethod.setErrorCode(String.valueOf(resp.getContent().getErrcode()));
                    callJs(aliBindResponseMethod);
                }
            } else {
                Tlog.e(TAG, " getAlipayAuthInfoResult unknown auth ");
            }
        }

    }


    // 支付宝登陆
    private void aliAuthInfo(String AUTH_INFO, String targetId1) {

        Tlog.d(TAG, " aliAuthInfo autoInfo = " + AUTH_INFO + " targetId1:" + targetId1);

        // 调用授权接口，获取授权结果
        AuthTask authTask = new AuthTask(getActivity());
        Map<String, String> result = authTask.authV2(AUTH_INFO, true);
        AuthResult authResult = new AuthResult(result, true);

        String targetId = authResult.getTargetId();

        if (Debuger.isLogDebug) {
            Tlog.v(TAG, " authTask map:" + String.valueOf(result));
            Tlog.v(TAG, " aliAuthInfo targetId: " + targetId);
        }

        if (targetId == null) {
            targetId = targetId1;
        }

        if (targetId.startsWith(C_0x8033.AUTH_TYPE_LOGIN)) {
            aliLogin(authResult);
        } else if (targetId.startsWith(C_0x8033.AUTH_TYPE_AUTH)) {
            // alibind
            aliBind(authResult);
        }

    }

    private void aliLogin(AuthResult authResult) {

        String resultStatus = authResult.getResultStatus();
        String resultCode = authResult.getResultCode();

        // 判断resultStatus 为“9000”且result_code为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
        Tlog.d(TAG, "aliLogin resultStatus:" + resultStatus + " resultCode:" + resultCode);

        if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(resultCode, "200")) {
            // 获取alipay_open_id，调支付时作为参数extern_token 的value
            // 传入，则支付账户为该授权账户

            StartAI.getInstance().getBaseBusiManager().loginWithThirdAccount(
                    Type.Login.THIRD_ALIPAY,
                    authResult.getAuthCode(),
                    new IOnCallListener() {
                        @Override
                        public void onSuccess(MqttPublishRequest mqttPublishRequest) {
                            if (Debuger.isLogDebug) {
                                Tlog.i(TAG, "loginWithThirdAccount ali msg send success ");
                            }
                        }

                        @Override
                        public void onFailed(MqttPublishRequest mqttPublishRequest, StartaiError startaiError) {
                            if (Debuger.isLogDebug) {
                                Tlog.e(TAG, "loginWithThirdAccount ali msg send fail " + String.valueOf(startaiError));
                            }

                            AliLoginResponseMethod mAliResponseMethod =
                                    AliLoginResponseMethod.getAliLoginResponseMethod();
                            mAliResponseMethod.setResult(false);
                            mAliResponseMethod.setErrorCode(String.valueOf(startaiError.getErrorCode()));
                            callJs(mAliResponseMethod);

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
            mAliResponseMethod.setResult(false);
            String errorCode = resultStatus == null ? resultCode : resultStatus;
            mAliResponseMethod.setErrorCode(String.valueOf(errorCode));
            callJs(mAliResponseMethod);

        }
    }


    private void aliBind(AuthResult authResult) {

        String resultStatus = authResult.getResultStatus();
        String resultCode = authResult.getResultCode();

        // 判断resultStatus 为“9000”且result_code为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
        Tlog.d(TAG, " resultStatus:" + resultStatus + " resultCode:" + resultCode);

        if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(resultCode, "200")) {
            // 获取alipay_open_id，调支付时作为参数extern_token 的value
            // 传入，则支付账户为该授权账户

            C_0x8037.Req.ContentBean req = new C_0x8037.Req.ContentBean();
            req.setCode(authResult.getAuthCode()); //code 来自微信授权返回
            req.setType(C_0x8037.THIRD_ALIPAY); //绑定微信账号

            StartAI.getInstance().getBaseBusiManager().bindThirdAccount(req, new IOnCallListener() {
                @Override
                public void onSuccess(MqttPublishRequest mqttPublishRequest) {
                    if (Debuger.isLogDebug) {
                        Tlog.i(TAG, "loginWithThirdAccount ali msg send success ");
                    }
                }

                @Override
                public void onFailed(MqttPublishRequest mqttPublishRequest, StartaiError startaiError) {
                    if (Debuger.isLogDebug) {
                        Tlog.e(TAG, "loginWithThirdAccount ali msg send fail " + String.valueOf(startaiError));
                    }

                    AliBindResponseMethod mAliResponseMethod = AliBindResponseMethod.getAliBindResponseMethod();
                    mAliResponseMethod.setResult(false);
                    mAliResponseMethod.setErrorCode(String.valueOf(startaiError.getErrorCode()));
                    callJs(mAliResponseMethod);

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


            AliBindResponseMethod mAliResponseMethod = AliBindResponseMethod.getAliBindResponseMethod();
            mAliResponseMethod.setResult(false);
            String errorCode = resultStatus == null ? resultCode : resultStatus;
            mAliResponseMethod.setErrorCode(String.valueOf(errorCode));
            callJs(mAliResponseMethod);

        }
    }

    @Override
    public void onBindThirdAccountResult(C_0x8037.Resp resp) {

        if (Debuger.isLogDebug) {
            Tlog.d(TAG, " onBindThirdAccountResult :" + String.valueOf(resp));
        }

        C_0x8037.Resp.ContentBean content = resp.getContent();

        if (content == null) {
            return;
        }

        int type = content.getType();

        switch (type) {
            case C_0x8037.THIRD_ALIPAY:

                AliBindResponseMethod mAliResponseMethod = AliBindResponseMethod.getAliBindResponseMethod();
                mAliResponseMethod.setResult(resp.getResult() == 1);
                mAliResponseMethod.setBinding(resp.getResult() == 1);
                mAliResponseMethod.setErrorCode(String.valueOf(content.getErrcode()));
                mAliResponseMethod.setContent(content);
                callJs(mAliResponseMethod);

                break;

            case C_0x8037.THIRD_WECHAT:

                WxBindResponseMethod mWxResponseMethod = WxBindResponseMethod.getWxBindResponseMethod();
                mWxResponseMethod.setResult(resp.getResult() == 1);
                mWxResponseMethod.setBinding(resp.getResult() == 1);
                mWxResponseMethod.setErrorCode(String.valueOf(content.getErrcode()));
                mWxResponseMethod.setContent(content);
                callJs(mWxResponseMethod);

                break;

            case C_0x8037.THIRD_FACEBOOK:

                ThirdBindResponseMethod thirdBindResponseMethod_FACEBOOK =
                        ThirdBindResponseMethod.getThirdBindResponseMethod();
                thirdBindResponseMethod_FACEBOOK.setResult(resp.getResult() == 1);
                thirdBindResponseMethod_FACEBOOK.setIsBind(resp.getResult() == 1);
                thirdBindResponseMethod_FACEBOOK.setFacebookType();
                thirdBindResponseMethod_FACEBOOK.setErrorCode(String.valueOf(content.getErrcode()));
                callJs(thirdBindResponseMethod_FACEBOOK);

                break;
            case C_0x8037.THIRD_GOOGLE:

                ThirdBindResponseMethod thirdBindResponseMethod_GOOGLE =
                        ThirdBindResponseMethod.getThirdBindResponseMethod();
                thirdBindResponseMethod_GOOGLE.setResult(resp.getResult() == 1);
                thirdBindResponseMethod_GOOGLE.setIsBind(resp.getResult() == 1);
                thirdBindResponseMethod_GOOGLE.setErrorCode(String.valueOf(content.getErrcode()));
                thirdBindResponseMethod_GOOGLE.setFacebookType();
                callJs(thirdBindResponseMethod_GOOGLE);

                break;
            case C_0x8037.THIRD_TWITTER:

                ThirdBindResponseMethod thirdBindResponseMethod_TWITTER =
                        ThirdBindResponseMethod.getThirdBindResponseMethod();
                thirdBindResponseMethod_TWITTER.setResult(resp.getResult() == 1);
                thirdBindResponseMethod_TWITTER.setIsBind(resp.getResult() == 1);
                thirdBindResponseMethod_TWITTER.setFacebookType();
                thirdBindResponseMethod_TWITTER.setErrorCode(String.valueOf(content.getErrcode()));
                callJs(thirdBindResponseMethod_TWITTER);

                break;
        }

    }

    @Override
    public void onBindMobileNumResult(C_0x8034.Resp resp) {

        if (Debuger.isLogDebug) {
            Tlog.d(TAG, " onBindMobileNumResult :" + String.valueOf(resp));
        }

        PhoneBindResponseMethod phoneBindResponseMethod =
                PhoneBindResponseMethod.getPhoneBindResponseMethod();
        phoneBindResponseMethod.setResult(resp.getResult() == 1);
        phoneBindResponseMethod.setBinding(resp.getResult() == 1);
        C_0x8034.Resp.ContentBean content = resp.getContent();
        phoneBindResponseMethod.setErrorCode(String.valueOf(content.getErrcode()));
        phoneBindResponseMethod.setContent(content);
        callJs(phoneBindResponseMethod);
    }

    @Override
    public void onUnBindThirdAccountResult(C_0x8036.Resp resp) {

        if (Debuger.isLogDebug) {
            Tlog.d(TAG, " onUnBindThirdAccountResult :" + String.valueOf(resp));
        }

        C_0x8036.Resp.ContentBean content = resp.getContent();
        switch (content.getType()) {
            case C_0x8036.THIRD_WECHAT:

                WxUnBindResponseMethod wxUnBindResponseMethod =
                        WxUnBindResponseMethod.getWXUnBindResponseMethod();
                wxUnBindResponseMethod.setResult(resp.getResult() == 1);
                wxUnBindResponseMethod.setUnBind(resp.getResult() == 1);
                wxUnBindResponseMethod.setErrorCode(content.getErrcode());
                callJs(wxUnBindResponseMethod);

                break;

            case C_0x8036.THIRD_ALIPAY:

                AliUnBindResponseMethod aliUnBindResponseMethod =
                        AliUnBindResponseMethod.getAliUnBindResponseMethod();
                aliUnBindResponseMethod.setResult(resp.getResult() == 1);
                aliUnBindResponseMethod.setUnBind(resp.getResult() == 1);
                aliUnBindResponseMethod.setErrorCode(content.getErrcode());
                callJs(aliUnBindResponseMethod);

                break;
            case C_0x8036.THIRD_FACEBOOK:
                ThirdUnbindResponseMethod thirdUnbindResponseMethodFACEBOOK
                        = ThirdUnbindResponseMethod.getThirdUnbindResponseMethod();
                thirdUnbindResponseMethodFACEBOOK.setResult(resp.getResult() == 1);
                thirdUnbindResponseMethodFACEBOOK.setUnbind(resp.getResult() == 1);
                thirdUnbindResponseMethodFACEBOOK.setErrorCode(content.getErrcode());
                thirdUnbindResponseMethodFACEBOOK.setFacebookType();
                callJs(thirdUnbindResponseMethodFACEBOOK);
                break;

            case C_0x8036.THIRD_GOOGLE:
                ThirdUnbindResponseMethod thirdUnbindResponseMethodGOOGLE
                        = ThirdUnbindResponseMethod.getThirdUnbindResponseMethod();
                thirdUnbindResponseMethodGOOGLE.setResult(resp.getResult() == 1);
                thirdUnbindResponseMethodGOOGLE.setUnbind(resp.getResult() == 1);
                thirdUnbindResponseMethodGOOGLE.setErrorCode(content.getErrcode());
                thirdUnbindResponseMethodGOOGLE.setGoogleType();
                callJs(thirdUnbindResponseMethodGOOGLE);
                break;

            case C_0x8036.THIRD_TWITTER:
                ThirdUnbindResponseMethod thirdUnbindResponseMethodTWITTER
                        = ThirdUnbindResponseMethod.getThirdUnbindResponseMethod();
                thirdUnbindResponseMethodTWITTER.setResult(resp.getResult() == 1);
                thirdUnbindResponseMethodTWITTER.setUnbind(resp.getResult() == 1);
                thirdUnbindResponseMethodTWITTER.setErrorCode(content.getErrcode());
                thirdUnbindResponseMethodTWITTER.setTwitterType();
                callJs(thirdUnbindResponseMethodTWITTER);
                break;
        }

    }


    @Override
    public void onResetLoginPwdResult(C_0x8026.Resp resp) {
        if (Debuger.isLogDebug) {
            Tlog.d(TAG, " onResetLoginPwdResult :" + String.valueOf(resp));
        }
    }

    @Override
    public void onGetBindListByPageResult(C_0x8038.Resp resp) {
        if (Debuger.isLogDebug) {
            Tlog.d(TAG, " onGetBindListByPageResult :" + String.valueOf(resp));
        }
    }

    @Override
    public void onBindEmailResult(C_0x8039.Resp resp) {
        if (Debuger.isLogDebug) {
            Tlog.d(TAG, " onBindEmailResult :" + String.valueOf(resp));
        }
    }

    @Override
    public void onPassthroughResult(C_0x8200.Resp resp, String dataString, byte[] dataByteArray) {
        if (Debuger.isLogDebug) {
            Tlog.d(TAG, " onPassthroughResult :" + String.valueOf(resp));
        }
    }


    @Override
    public void onGetWeatherInfoResult(C_0x8035.Resp resp) {
        if (Debuger.isLogDebug) {
            Tlog.d(TAG, " onGetWeatherInfoResult :" + String.valueOf(resp));
        }
    }

    @Override
    public void onDeviceConnectStatusChange(String userid, int status, String sn) {
        if (Debuger.isLogDebug) {
            Tlog.d(TAG, " onDeviceConnectStatusChange : userid:" + userid + " sn:" + sn + " status:" + status);
        }
    }

    @Override
    public void onSendEmailResult(C_0x8023.Resp resp) {
        if (Debuger.isLogDebug) {
            Tlog.d(TAG, " onSendEmailResult :" + String.valueOf(resp));
        }
    }

    @Override
    public void onActiviteResult(C_0x8001.Resp resp) {
        if (Debuger.isLogDebug) {
            Tlog.d(TAG, " onActiviteResult :" + String.valueOf(resp));
        }
    }

    @Override
    public void onHardwareActivateResult(C_0x8001.Resp resp) {
        if (Debuger.isLogDebug) {
            Tlog.d(TAG, " onHardwareActivateResult :" + String.valueOf(resp));
        }
    }

    @Override
    public void onBindResult(C_0x8002.Resp resp, String id, C_0x8002.Resp.ContentBean.BebindingBean bebinding) {
        if (Debuger.isLogDebug) {
            Tlog.d(TAG, " onBindResult :" + String.valueOf(resp));
        }
    }

    @Override
    public void onUnActiviteResult(C_0x8003.Resp resp) {
        if (Debuger.isLogDebug) {
            Tlog.d(TAG, " onUnActiviteResult :" + String.valueOf(resp));
        }
    }

    @Override
    public void onUnBindResult(C_0x8004.Resp resp, String id, String beUnbindid) {
        if (Debuger.isLogDebug) {
            Tlog.d(TAG, " onUnBindResult : id:" + id + " beUnibindid:" + beUnbindid + " resp:" + String.valueOf(resp));
        }
    }

    @Override
    public void onGetBindListResult(C_0x8005.Response response) {
        if (Debuger.isLogDebug) {
            Tlog.d(TAG, " onGetBindListResult :" + String.valueOf(response));
        }
    }

    @Override
    public void onUpdateRemarkResult(C_0x8015.Resp resp) {
        if (Debuger.isLogDebug) {
            Tlog.d(TAG, " onUpdateRemarkResult :" + String.valueOf(resp));
        }
    }


    @Override
    public void onRegisterResult(C_0x8017.Resp resp) {
        if (Debuger.isLogDebug) {
            Tlog.d(TAG, " onRegisterResult :" + String.valueOf(resp));
        }
    }


}
