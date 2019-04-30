package cn.com.startai.sharedlib.app.mutual.impl;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

import cn.com.startai.fssdk.FSDownloadCallback;
import cn.com.startai.fssdk.FSUploadCallback;
import cn.com.startai.fssdk.StartaiDownloaderManager;
import cn.com.startai.fssdk.StartaiUploaderManager;
import cn.com.startai.fssdk.db.entity.DownloadBean;
import cn.com.startai.fssdk.db.entity.UploadBean;
import cn.com.startai.mqttsdk.StartAI;
import cn.com.startai.mqttsdk.base.StartaiError;
import cn.com.startai.mqttsdk.busi.entity.C_0x8018;
import cn.com.startai.mqttsdk.busi.entity.C_0x8020;
import cn.com.startai.mqttsdk.busi.entity.C_0x8021;
import cn.com.startai.mqttsdk.busi.entity.C_0x8022;
import cn.com.startai.mqttsdk.busi.entity.C_0x8024;
import cn.com.startai.mqttsdk.busi.entity.C_0x8025;
import cn.com.startai.mqttsdk.busi.entity.C_0x8027;
import cn.com.startai.mqttsdk.busi.entity.C_0x8028;
import cn.com.startai.mqttsdk.busi.entity.C_0x8033;
import cn.com.startai.mqttsdk.busi.entity.C_0x8036;
import cn.com.startai.mqttsdk.busi.entity.C_0x8037;
import cn.com.startai.mqttsdk.busi.entity.type.Type;
import cn.com.startai.mqttsdk.listener.IOnCallListener;
import cn.com.startai.mqttsdk.mqtt.request.MqttPublishRequest;
import cn.com.startai.scansdk.ChargerScanActivity;
import cn.com.startai.sharedlib.R;
import cn.com.startai.sharedlib.app.global.Debuger;
import cn.com.startai.sharedlib.app.global.FileManager;
import cn.com.startai.sharedlib.app.global.LooperManager;
import cn.com.startai.sharedlib.app.js.CommonJsInterfaceTask;
import cn.com.startai.sharedlib.app.js.Utils.JSErrorCode;
import cn.com.startai.sharedlib.app.js.Utils.Language;
import cn.com.startai.sharedlib.app.js.method2Impl.AliBindResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.AliLoginResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.AliUnBindResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.AppInstallResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.CallPhoneResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.EnableLocationResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.GetAppVersionResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.GetIdentityCodeResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.IsLeastVersionResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.IsLoginResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.LanguageResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.LocationDataResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.LocationEnabledResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.MapNavResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.MobileLoginByIDCodeResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.ModifyHeadpicResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.ModifyUserInfoResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.ModifyUserPwdResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.PhoneBindResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.ScanORResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.ThirdBindResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.ThirdLoginResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.ThirdPayResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.ThirdUnbindResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.UpdateProgressResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.UserInfoResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.WxLoginResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.WxUnBindResponseMethod;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.AppInstallRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.BindPhoneRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.CallPhoneRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.GetIdentityCodeRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.LanguageSetRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.MapNavRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.MobileLoginByIDCodeRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.ModifyNickNameRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.ModifyUserNameRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.ModifyUserPwdRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.ThirdBindRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.ThirdLoginRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.ThirdPayOrderRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.ThirdUnBindRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.UpgradeAppRequestBean;
import cn.com.startai.sharedlib.app.mutual.IMutualCallBack;
import cn.com.startai.sharedlib.app.mutual.IUserIDManager;
import cn.com.startai.sharedlib.app.mutual.utils.MapUtils;
import cn.com.startai.sharedlib.app.mutual.utils.RuiooORCodeUtils;
import cn.com.startai.sharedlib.app.view.app.SharedApplication;
import cn.com.startai.sharedlib.app.wxapi.WXApiHelper;
import cn.com.swain.baselib.app.utils.AppUtils;
import cn.com.swain.baselib.jsInterface.request.bean.BaseCommonJsRequestBean;
import cn.com.swain.baselib.log.Tlog;
import cn.com.swain.baselib.permission.PermissionGroup;
import cn.com.swain.baselib.permission.PermissionHelper;
import cn.com.swain.baselib.permission.PermissionRequest;
import cn.com.swain.baselib.util.PhotoUtils;

/**
 * author: Guoqiang_Sun
 * date: 2018/12/25 0025
 * Desc:
 */
public class CommonJsRequestInterfaceImpl extends MutualSharedWrapper
        implements CommonJsInterfaceTask.ICommonJsRequestInterface {

    private final Application app;

    public CommonJsRequestInterfaceImpl(Application app, IMutualCallBack mCallBack, IUserIDManager mUserID) {
        super(mCallBack, mUserID);
        this.app = app;
    }

    @Override
    public void onJsCrashError(BaseCommonJsRequestBean mData) {
        if (app != null) {
            ((SharedApplication) app).onJsCrashError(mData.getRootJsonStr());
        }
    }

    @Override
    public void onJsPressBack() {

    }

    @Override
    public void onJsPressBackFinish() {
        jFinish();
    }

    @Override
    public void onJsPressBackFinishBefore() {
        if (app != null) {
            Toast.makeText(app, R.string.goBack_again, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onJsDataParseError(JSONException e, String jsonData) {

        if (Debuger.isLogDebug) {
            Tlog.e(TAG, " handleJsRequest parse jsonData: " + jsonData, e);
        }

    }

    @Override
    public void onJsIsLogin() {
        String userIDFromMq = getUserIDFromMq();
        setUserID(userIDFromMq);
        Tlog.d(TAG, "onJsIsLogin getUserIDFromMq:" + userIDFromMq);
        IsLoginResponseMethod mIsLoginResponseMethod =
                IsLoginResponseMethod.getIsLoginResponseMethod();
        mIsLoginResponseMethod.setResult(true);
        mIsLoginResponseMethod.setIsLogin(userIDFromMq != null);
        callJs(mIsLoginResponseMethod);

    }

    /**
     * result at {@link CommonMqttLsnImpl#onWxEntryResult(BaseResp)}
     */
    @Override
    public void onJsWXLogin() {
        IWXAPI wxApi = WXApiHelper.getInstance().getWXApi(app);

        if (wxApi == null) {
            if (Debuger.isLogDebug) {
                Tlog.e(TAG, " onJsWXLogin IWXAPI = null ");
            }

            WxLoginResponseMethod mWxResponseMethod =
                    WxLoginResponseMethod.getWxLoginResponseMethod();
            mWxResponseMethod.setResult(false);
            mWxResponseMethod.setErrorCode(JSErrorCode.ERROR_CODE_WX_LOGIN_NO_REGISTER);
            callJs(mWxResponseMethod);

            return;
        }

        //先判断是否安装微信APP,按照微信的说法，目前移动应用上微信登录只提供原生的登录方式，需要用户安装微信客户端才能配合使用。
        if (!wxApi.isWXAppInstalled()) {
            if (Debuger.isLogDebug) {
                Tlog.e(TAG, " onJsWXLogin error, wx client is not install");
            }

            WxLoginResponseMethod mWxResponseMethod =
                    WxLoginResponseMethod.getWxLoginResponseMethod();
            mWxResponseMethod.setResult(false);
            mWxResponseMethod.setErrorCode(JSErrorCode.ERROR_CODE_WX_LOGIN_NO_CLIENT);
            callJs(mWxResponseMethod);

            return;
        }

        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = WXApiHelper.Constants.WX_LOGIN_TAG;
        //向微信发送请求
        wxApi.sendReq(req);

    }


    /**
     * result at {@link CommonMqttLsnImpl#onWxEntryResult(BaseResp)}
     */
    @Override
    public void onJsWXBind() {

        //先判断是否安装微信APP,按照微信的说法，目前移动应用上微信登录只提供原生的登录方式，需要用户安装微信客户端才能配合使用。
        IWXAPI wxApi = WXApiHelper.getInstance().getWXApi(app);

        if (wxApi == null) {
            if (Debuger.isLogDebug) {
                Tlog.e(TAG, " onJsWXBind IWXAPI=null ");
            }

            WxLoginResponseMethod mWxResponseMethod =
                    WxLoginResponseMethod.getWxLoginResponseMethod();
            mWxResponseMethod.setResult(false);
            mWxResponseMethod.setErrorCode(JSErrorCode.ERROR_CODE_WX_BIND_NO_REGISTER);
            callJs(mWxResponseMethod);

            return;
        }

        if (!wxApi.isWXAppInstalled()) {
            if (Debuger.isLogDebug) {
                Tlog.e(TAG, " onJsWXBind error, wx client is not install");
            }

            WxLoginResponseMethod mWxResponseMethod =
                    WxLoginResponseMethod.getWxLoginResponseMethod();
            mWxResponseMethod.setResult(false);
            mWxResponseMethod.setErrorCode(JSErrorCode.ERROR_CODE_WX_BIND_NO_CLIENT);
            callJs(mWxResponseMethod);

            return;
        }

        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = WXApiHelper.Constants.WX_BIND_TAG;
        //向微信发送请求
        wxApi.sendReq(req);

    }

    /**
     * see {@link CommonMqttLsnImpl#onGetAlipayAuthInfoResult(C_0x8033.Resp)}
     */
    @Override
    public void onJsAliLogin() {

        StartAI.getInstance().getBaseBusiManager().getAlipayAuthInfo(C_0x8033.AUTH_TYPE_LOGIN, new IOnCallListener() {
            @Override
            public void onSuccess(MqttPublishRequest request) {
                if (Debuger.isLogDebug) {
                    Tlog.v(TAG, "getAlipayAuthInfo login msg send success ");
                }
            }

            @Override
            public void onFailed(MqttPublishRequest request, StartaiError startaiError) {

                if (Debuger.isLogDebug) {
                    Tlog.e(TAG, "getAlipayAuthInfo login msg send fail " + String.valueOf(startaiError));
                }

                AliLoginResponseMethod mAliResponseMethod =
                        AliLoginResponseMethod.getAliLoginResponseMethod();
                mAliResponseMethod.setResult(false);
                mAliResponseMethod.setErrorCode(String.valueOf(startaiError.getErrorCode()));
                callJs(mAliResponseMethod);

            }

        });
    }

    /**
     * see {@link CommonMqttLsnImpl#onGetAlipayAuthInfoResult(C_0x8033.Resp)}
     */
    @Override
    public void onJsAliBind() {
        StartAI.getInstance().getBaseBusiManager().getAlipayAuthInfo(C_0x8033.AUTH_TYPE_AUTH, new IOnCallListener() {
            @Override
            public void onSuccess(MqttPublishRequest request) {
                if (Debuger.isLogDebug) {
                    Tlog.v(TAG, "getAlipayAuthInfo bind msg send success ");
                }
            }

            @Override
            public void onFailed(MqttPublishRequest request, StartaiError startaiError) {

                if (Debuger.isLogDebug) {
                    Tlog.e(TAG, "getAlipayAuthInfo bind msg send fail " + String.valueOf(startaiError));
                }

                AliBindResponseMethod mAliResponseMethod =
                        AliBindResponseMethod.getAliBindResponseMethod();
                mAliResponseMethod.setResult(false);
                mAliResponseMethod.setErrorCode(String.valueOf(startaiError.getErrorCode()));
                callJs(mAliResponseMethod);

            }

        });
    }

    /**
     * see {@link CommonMqttLsnImpl#onCheckIdetifyResult(C_0x8022.Resp)}
     */
    @Override
    public void onJsBindPhone(BindPhoneRequestBean mBindPhoneBean) {

        //发送请求 接口调用前需要先 调用 获取验证码，检验验证码
        StartAI.getInstance().getBaseBusiManager().checkIdentifyCode(mBindPhoneBean.getPhone(), mBindPhoneBean.getCode(),
                Type.CheckIdentifyCode.BIND_MOBILE_NUM, new IOnCallListener() {
                    @Override
                    public void onSuccess(MqttPublishRequest request) {
                        if (Debuger.isLogDebug) {
                            Tlog.v(TAG, "bind phone checkIdentifyCode msg send success ");
                        }
                    }

                    @Override
                    public void onFailed(MqttPublishRequest request, StartaiError startaiError) {

                        if (Debuger.isLogDebug) {
                            Tlog.e(TAG, "bind phone checkIdentifyCode msg send fail " + String.valueOf(startaiError));
                        }

                        PhoneBindResponseMethod phoneBindResponseMethod = PhoneBindResponseMethod.getPhoneBindResponseMethod();
                        phoneBindResponseMethod.setBinding(false);
                        phoneBindResponseMethod.setResult(false);
                        phoneBindResponseMethod.setErrorCode(String.valueOf(startaiError.getErrorCode()));
                        callJs(phoneBindResponseMethod);
                    }

                });
    }

    /**
     * see {@link CommonMqttLsnImpl#onUnBindThirdAccountResult(C_0x8036.Resp)}
     */
    @Override
    public void onJsWXUnBind() {
        //发送请求
        C_0x8036.Req.ContentBean req = new C_0x8036.Req.ContentBean(getUserID(), C_0x8036.THIRD_WECHAT);
        //解绑 微信
        StartAI.getInstance().getBaseBusiManager().unBindThirdAccount(req, new IOnCallListener() {
            @Override
            public void onSuccess(MqttPublishRequest request) {
                if (Debuger.isLogDebug) {
                    Tlog.v(TAG, " unbindWx msg send success ");
                }
            }

            @Override
            public void onFailed(MqttPublishRequest request, StartaiError startaiError) {
                if (Debuger.isLogDebug) {
                    Tlog.e(TAG, " unbindWx msg send fail " + startaiError.getErrorCode());
                }
                WxUnBindResponseMethod wxUnBindResponseMethod = WxUnBindResponseMethod.getWXUnBindResponseMethod();
                wxUnBindResponseMethod.setResult(false);
                wxUnBindResponseMethod.setUnBind(false);
                wxUnBindResponseMethod.setErrorCode(String.valueOf(startaiError.getErrorCode()));
                callJs(wxUnBindResponseMethod);
            }
        });
    }

    /**
     * see {@link CommonMqttLsnImpl#onUnBindThirdAccountResult(C_0x8036.Resp)}
     */
    @Override
    public void onJsAliUnBind() {
        //发送请求
        C_0x8036.Req.ContentBean req = new C_0x8036.Req.ContentBean(getUserID(), C_0x8036.THIRD_ALIPAY);
        //解绑 微信
        StartAI.getInstance().getBaseBusiManager().unBindThirdAccount(req, new IOnCallListener() {
            @Override
            public void onSuccess(MqttPublishRequest request) {
                if (Debuger.isLogDebug) {
                    Tlog.v(TAG, " unbindAli msg send success ");
                }
            }

            @Override
            public void onFailed(MqttPublishRequest request, StartaiError startaiError) {
                if (Debuger.isLogDebug) {
                    Tlog.e(TAG, " unbindAli msg send fail " + startaiError.getErrorCode());
                }
                AliUnBindResponseMethod aliUnBindResponseMethod = AliUnBindResponseMethod.getAliUnBindResponseMethod();
                aliUnBindResponseMethod.setResult(false);
                aliUnBindResponseMethod.setUnBind(false);
                aliUnBindResponseMethod.setErrorCode(String.valueOf(startaiError.getErrorCode()));
                callJs(aliUnBindResponseMethod);
            }

        });
    }


    @Override
    public void onJsThirdUnbind(final ThirdUnBindRequestBean mThirdUnBindRequestBean) {
        C_0x8036.Req.ContentBean req = new C_0x8036.Req.ContentBean();
        req.setUserid(getUserID());
        if (mThirdUnBindRequestBean.isFacebookType()) {
            req.setType(C_0x8036.THIRD_FACEBOOK);
        } else if (mThirdUnBindRequestBean.isGoogleType()) {
            req.setType(C_0x8036.THIRD_GOOGLE);
        } else if (mThirdUnBindRequestBean.isTwitterType()) {
            req.setType(C_0x8036.THIRD_TWITTER);
        }

        StartAI.getInstance().getBaseBusiManager().unBindThirdAccount(req, new IOnCallListener() {
            @Override
            public void onSuccess(MqttPublishRequest request) {
                if (Debuger.isLogDebug) {
                    Tlog.v(TAG, " unBindThirdAccount msg send success ");
                }
            }

            @Override
            public void onFailed(MqttPublishRequest request, StartaiError startaiError) {
                if (Debuger.isLogDebug) {
                    Tlog.e(TAG, " unbindAli msg send fail " + startaiError.getErrorCode());
                }
                ThirdUnbindResponseMethod thirdUnbindResponseMethod
                        = ThirdUnbindResponseMethod.getThirdUnbindResponseMethod();
                thirdUnbindResponseMethod.setResult(false);
                thirdUnbindResponseMethod.setType(mThirdUnBindRequestBean.getType());
                thirdUnbindResponseMethod.setUnbind(false);
                callJs(thirdUnbindResponseMethod);
            }
        });
    }


    private int mFacebookStatus = 0;
    private static final int FACEBOOK_LOGIN = 0x01;
    private static final int FACEBOOK_BIND = 0x02;

    /**
     * see at {@link #onActivityResult(int, int, Intent)}
     */
    @Override
    public void onJsThirdLogin(ThirdLoginRequestBean mThirdLoginRequestBean) {
        if (mThirdLoginRequestBean.isFacebookType()) {
            mFacebookStatus = FACEBOOK_LOGIN;
            loginFacebook();
        } else if (mThirdLoginRequestBean.isGoogleType()) {
            loginGoogle(RC_SIGN_IN);
        }

    }

    @Override
    public void onJsThirdBind(ThirdBindRequestBean mThirdBindRequestBean) {
        if (mThirdBindRequestBean.isFacebookType()) {
            mFacebookStatus = FACEBOOK_BIND;
            bindFacebook();
        } else if (mThirdBindRequestBean.isGoogleType()) {
            loginGoogle(RC_BIND_IN);
        }
    }

    @Override
    public void onJsCallPhone(CallPhoneRequestBean callPhoneRequestBean) {
        String phone = callPhoneRequestBean.getPhone();

        CallPhoneResponseMethod callPhoneResponseMethod
                = CallPhoneResponseMethod.getCallPhoneResponseMethod();
        callPhoneResponseMethod.setPhone(phone);

        try {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            Uri data = Uri.parse("tel:" + phone);
            intent.setData(data);
            getActivity().startActivity(intent);
            callPhoneResponseMethod.setResult(true);
            callPhoneResponseMethod.setState(true);
        } catch (Exception e) {
            callPhoneResponseMethod.setResult(false);
            callPhoneResponseMethod.setState(false);
        }

        callJs(callPhoneResponseMethod);
    }

    @Override
    public void onJsAppIsInstall(AppInstallRequestBean appInstallRequestBean) {

        AppInstallResponseMethod appInstallResponseMethod
                = AppInstallResponseMethod.getAppInstallResponseMethod();

        boolean appInstalled;
        if (appInstallRequestBean.isWechat()) {

            appInstalled = AppUtils.isAppInstalled(app, "com.tencent.mm");
            appInstallResponseMethod.setResult(true);
        } else if (appInstallRequestBean.isAli()) {

            appInstalled = AppUtils.isAppInstalled(app, "com.eg.android.AlipayGphone");
            appInstallResponseMethod.setResult(true);
        } else {
            appInstalled = false;
            appInstallResponseMethod.setResult(false);
        }

        appInstallResponseMethod.setType(appInstallRequestBean.getType());
        appInstallResponseMethod.setState(appInstalled);

    }

    @Override
    public void onJsMapNav(MapNavRequestBean mapNavRequestBean) {

        MapNavResponseMethod mapNavResponseMethod
                = MapNavResponseMethod.getMapNavResponseMethod();

        Intent intent = null;

        if (mapNavRequestBean.isBaidu()) {

            if (MapUtils.isBaiduMapInstall(app)) {
                intent = MapUtils.getBaiduMap(mapNavRequestBean.getLat(),
                        mapNavRequestBean.getLng(),
                        mapNavRequestBean.getAddress(),
                        app.getPackageName());
            }

        } else if (mapNavRequestBean.isGaode()) {
            if (MapUtils.isGaodeMapInstall(app)) {
                intent = MapUtils.getGaodeMap(mapNavRequestBean.getLat(),
                        mapNavRequestBean.getLng(),
                        mapNavRequestBean.getAddress());
            }
        } else if (mapNavRequestBean.isGoogle()) {
            if (MapUtils.isGoogleMapInstall(app)) {
                intent = MapUtils.getGoogleMap(mapNavRequestBean.getLat(),
                        mapNavRequestBean.getLng(),
                        mapNavRequestBean.getAddress());
            }
        }

        if (intent != null) {
            try {
                getActivity().startActivity(intent);
            } catch (Exception e) {
                mapNavResponseMethod.setErrorCode(JSErrorCode.MAP_NAV_ERROR);
            }
        } else {
            mapNavResponseMethod.setErrorCode(JSErrorCode.MAP_NAV_ERROR_NO_CLIENT);
        }
        mapNavResponseMethod.setType(mapNavRequestBean.getType());
        mapNavResponseMethod.setSuccess(intent != null);
        mapNavResponseMethod.setResult(intent != null);
        callJs(mapNavResponseMethod);
    }


    @Override
    public void onJsLocationEnabled() {
        LocationEnabledResponseMethod locationEnabledResponseMethod
                = LocationEnabledResponseMethod.getLocationEnabledResponseMethod();
        locationEnabledResponseMethod.setResult(true);
        locationEnabledResponseMethod.setEnabled(locationEnabled());
        callJs(locationEnabledResponseMethod);
    }

    private static final int REQUEST_LOCATION = 0x6598;

    @Override
    public void onJsEnableLocation() {
        try {
            Intent i = new Intent();
            i.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            getActivity().startActivityForResult(i, REQUEST_LOCATION);
        } catch (Exception e) {
            Tlog.w(TAG, " enable location", e);
        }
    }

    @Override
    public void onJsGetLocationData() {

        if (!locationEnabled()) {
            LooperManager.getInstance().execute(new Runnable() {
                @Override
                public void run() {
                    MapUtils.LatLng locationData = MapUtils.getLocationData();
                    LocationDataResponseMethod locationDataResponseMethod
                            = LocationDataResponseMethod.getLocationDataResponseMethod();
                    locationDataResponseMethod.setResult(locationData != null);
                    locationDataResponseMethod.setEnabled(locationData != null);
                    if (locationData != null) {
                        locationDataResponseMethod.setLat(locationData.mLatitude);
                        locationDataResponseMethod.setLng(locationData.mLongitude);
                    }
                    callJs(locationDataResponseMethod);
                }
            });
        } else {

            final String bestProvider = getBestProvider();
            Location lastKnownLocation = getLastKnownLocation(bestProvider);
            if (lastKnownLocation != null) {
                callJsLocationData(lastKnownLocation);
            } else {
                LooperManager.getInstance().execute(new Runnable() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void run() {
                        String locationProvider = bestProvider;
                        int request = 0;

                        Location lastKnownLocationInFor = null;
                        while (lastKnownLocationInFor == null && ++request < 3) {
                            locationProvider = changeProvider(locationProvider);
                            lastKnownLocationInFor = getLastKnownLocation(locationProvider);
                        }
                        if (lastKnownLocationInFor != null) {
                            callJsLocationData(lastKnownLocationInFor);
                        } else {
                            locationProvider = changeProvider(locationProvider);
                            Tlog.w(TAG, " requestLocationUpdates " + locationProvider);
                            final LocationManager locationManager = getLocationManager();
                            locationManager.requestLocationUpdates(locationProvider,
                                    1000,
                                    1,
                                    locationListener);
                            LooperManager.getInstance().execute(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.sleep(3000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }

                                    String locationProvider = getBestProvider();
                                    Location lastKnownLocation = getLastKnownLocation(locationProvider);
                                    if (lastKnownLocation != null) {
                                        callJsLocationData(lastKnownLocation);
                                    } else {
                                        MapUtils.LatLng locationData = MapUtils.getLocationData();
                                        LocationDataResponseMethod locationDataResponseMethod
                                                = LocationDataResponseMethod.getLocationDataResponseMethod();
                                        locationDataResponseMethod.setResult(locationData != null);
                                        locationDataResponseMethod.setEnabled(locationData != null);
                                        if (locationData != null) {
                                            locationDataResponseMethod.setLat(locationData.mLatitude);
                                            locationDataResponseMethod.setLng(locationData.mLongitude);
                                        }
                                        callJs(locationDataResponseMethod);
                                    }

                                    locationManager.removeUpdates(locationListener);

                                }
                            });
                        }

                    }
                });
            }
        }

    }

    private void callJsLocationData(Location lastKnownLocation) {
        LocationDataResponseMethod locationDataResponseMethod
                = LocationDataResponseMethod.getLocationDataResponseMethod();
        locationDataResponseMethod.setResult(lastKnownLocation != null);
        locationDataResponseMethod.setEnabled(lastKnownLocation != null);
        if (lastKnownLocation != null) {
            locationDataResponseMethod.setLat(String.valueOf(lastKnownLocation.getLatitude()));
            locationDataResponseMethod.setLng(String.valueOf(lastKnownLocation.getLongitude()));
        }
        callJs(locationDataResponseMethod);
    }


    private final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            Tlog.e(TAG, " onLocationChanged " + String.valueOf(location));
            callJsLocationData(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Tlog.e(TAG, " onStatusChanged " + provider);
        }

        @Override
        public void onProviderEnabled(String provider) {
            Tlog.e(TAG, " onProviderEnabled " + provider);
        }

        @Override
        public void onProviderDisabled(String provider) {
            Tlog.e(TAG, " onProviderDisabled " + provider);

        }
    };

    private static final int RC_SIGN_IN = 9001;
    private static final int RC_BIND_IN = 9002;

    private void loginGoogle(int rc) {

        Tlog.v(TAG, " loginGoogle() ");

        Activity mAct = getActivity();
        Context applicationContext = mAct.getApplicationContext();
        GoogleApiClient mGoogleApiClient = getGoogleApiClient((FragmentActivity) mAct);

//        GoogleSignInClient mGoogleSignInClient = getGoogleSignInClient(mAct);
//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(applicationContext);
//        getGoogleAccount(account);

        int googlePlayServicesAvailable = GoogleApiAvailability.getInstance()
                .isGooglePlayServicesAvailable(applicationContext);
        Tlog.v(TAG, " isGooglePlayServicesAvailable : " + googlePlayServicesAvailable);

        if (googlePlayServicesAvailable != ConnectionResult.SUCCESS) {
            Dialog errorDialog = GoogleApiAvailability.getInstance().getErrorDialog(mAct, googlePlayServicesAvailable, 0);
            errorDialog.show();
        } else {
//            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
//            mAct.startActivityForResult(signInIntent, RC_SIGN_IN);

            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
            mAct.startActivityForResult(signInIntent, rc);

        }

    }


    private GoogleApiClient mGoogleApiClient;

    private GoogleApiClient getGoogleApiClient(final FragmentActivity mContext) {

        if (mGoogleApiClient == null) {
            GoogleSignInOptions.Builder builder = new GoogleSignInOptions.Builder(
                    GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .requestProfile()
                    .requestId();

//            String serverClientId = mContext.getResources().getString(R.string.server_client_id);
//            if (serverClientId != null) builder.requestIdToken(serverClientId);
            GoogleSignInOptions gso = builder.build();

            Context applicationContext = mContext.getApplicationContext();
            GoogleApiClient.Builder builder1 = new GoogleApiClient.Builder(applicationContext).enableAutoManage(mContext, new GoogleApiClient.OnConnectionFailedListener() {
                @Override
                public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                }
            }).addApi(Auth.GOOGLE_SIGN_IN_API, gso);
            mGoogleApiClient = builder1.build();

        }

        return mGoogleApiClient;
    }


    private CallbackManager facebookBindCallbackManager;

    private CallbackManager getFacebookBindCallbackManager() {

        return facebookBindCallbackManager;
    }

    private FacebookCallback<LoginResult> facebookBindCallback;

    private void bindFacebook() {

        if (facebookBindCallbackManager == null) {
            facebookBindCallbackManager = CallbackManager.Factory.create();
        }

        if (facebookBindCallback == null) {
            facebookBindCallback = new FacebookCallback<LoginResult>() {

                @Override
                public void onSuccess(LoginResult loginResult) {
                    Tlog.v(TAG, " facebook bindResult onSuccess() ");

                    AccessToken accessToken = loginResult.getAccessToken();
                    GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            Tlog.v(TAG, " getFacebookBindInfo：" + String.valueOf(object));

                            C_0x8037.Req.ContentBean mBean = new C_0x8037.Req.ContentBean();
                            mBean.fromFacebookJSONObject(object);
                            StartAI.getInstance().getBaseBusiManager().bindThirdAccount(mBean,
                                    new IOnCallListener() {
                                        @Override
                                        public void onSuccess(MqttPublishRequest request) {
                                            Tlog.w(TAG, " bind facebook msg send success  ");
                                        }

                                        @Override
                                        public void onFailed(MqttPublishRequest request, StartaiError startaiError) {
                                            Tlog.e(TAG, " bind facebook msg send fail ");
                                            ThirdBindResponseMethod thirdBindResponseMethodd =
                                                    ThirdBindResponseMethod.getThirdBindResponseMethod();
                                            thirdBindResponseMethodd.setResult(false);
                                            thirdBindResponseMethodd.setIsBind(false);
                                            thirdBindResponseMethodd.setFacebookType();
                                            thirdBindResponseMethodd.setErrorCode(String.valueOf(startaiError.getErrorCode()));
                                            callJs(thirdBindResponseMethodd);
                                        }

                                    });

                        }
                    });
                    Bundle parameters = new Bundle();
                    parameters.putString("fields",
                            "id,name,link,gender,birthday,email,picture,locale,updated_time,timezone,age_range,first_name,last_name");
                    request.setParameters(parameters);
                    request.executeAsync();

                }

                @Override
                public void onCancel() {
                    Tlog.i(TAG, " facebook loginResult onCancel() ");
                    ThirdBindResponseMethod thirdBindResponseMethodd =
                            ThirdBindResponseMethod.getThirdBindResponseMethod();
                    thirdBindResponseMethodd.setResult(false);
                    thirdBindResponseMethodd.setIsBind(false);
                    thirdBindResponseMethodd.setFacebookType();
                    thirdBindResponseMethodd.setErrorCode(JSErrorCode.THIRD_BIND_USER_CANCEL);
                    callJs(thirdBindResponseMethodd);
                }

                @Override
                public void onError(FacebookException error) {
                    Tlog.e(TAG, " facebook loginResult onError() ", error);
                    ThirdBindResponseMethod thirdBindResponseMethodd =
                            ThirdBindResponseMethod.getThirdBindResponseMethod();
                    thirdBindResponseMethodd.setResult(false);
                    thirdBindResponseMethodd.setIsBind(false);
                    thirdBindResponseMethodd.setFacebookType();
                    thirdBindResponseMethodd.setErrorCode(JSErrorCode.THIRD_BIND_INTER_ERROR);
                    callJs(thirdBindResponseMethodd);
                }
            };
        }

        loginFacebook(getActivity(), facebookBindCallbackManager, facebookBindCallback);
    }


    private CallbackManager facebookLoginCallbackManager;

    private CallbackManager getFacebookLoginCallbackManager() {

        return facebookLoginCallbackManager;
    }

    private FacebookCallback<LoginResult> facebookLoginCallback;

    private void loginFacebook() {

        if (facebookLoginCallbackManager == null) {
            facebookLoginCallbackManager = CallbackManager.Factory.create();
        }

        if (facebookLoginCallback == null) {
            facebookLoginCallback = new FacebookCallback<LoginResult>() {

                @Override
                public void onSuccess(LoginResult loginResult) {
                    Tlog.v(TAG, " facebook loginResult onSuccess() ");

                    AccessToken accessToken = loginResult.getAccessToken();
                    GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            Tlog.v(TAG, " getFacebookLoginInfo：" + String.valueOf(object));

                            C_0x8027.Req.ContentBean mBean = new C_0x8027.Req.ContentBean();
                            mBean.fromFacebookJSONObject(object);
                            StartAI.getInstance().getBaseBusiManager().loginWithThirdAccount(mBean,
                                    new IOnCallListener() {
                                        @Override
                                        public void onSuccess(MqttPublishRequest request) {
                                            Tlog.w(TAG, " login facebook msg send success  ");
                                        }

                                        @Override
                                        public void onFailed(MqttPublishRequest request, StartaiError startaiError) {
                                            Tlog.e(TAG, " login facebook msg send fail ");
                                            ThirdLoginResponseMethod thirdLoginResponseMethod
                                                    = ThirdLoginResponseMethod.getThirdLoginResponseMethod();
                                            thirdLoginResponseMethod.setResult(false);
                                            thirdLoginResponseMethod.setIsLogin(false);
                                            thirdLoginResponseMethod.setFacebookType();
                                            thirdLoginResponseMethod.setErrorCode(String.valueOf(startaiError.getErrorCode()));
                                            callJs(thirdLoginResponseMethod);
                                        }

                                    });

                        }
                    });
                    Bundle parameters = new Bundle();
                    parameters.putString("fields",
                            "id,name,link,gender,birthday,email,picture,locale,updated_time,timezone,age_range,first_name,last_name");
                    request.setParameters(parameters);
                    request.executeAsync();

                }

                @Override
                public void onCancel() {
                    Tlog.i(TAG, " facebook loginResult onCancel() ");
                    ThirdLoginResponseMethod thirdLoginResponseMethod
                            = ThirdLoginResponseMethod.getThirdLoginResponseMethod();
                    thirdLoginResponseMethod.setResult(false);
                    thirdLoginResponseMethod.setIsLogin(false);
                    thirdLoginResponseMethod.setFacebookType();
                    thirdLoginResponseMethod.setErrorCode(JSErrorCode.THIRD_LOGIN_USER_CANCEL);
                    callJs(thirdLoginResponseMethod);
                }

                @Override
                public void onError(FacebookException error) {
                    Tlog.e(TAG, " facebook loginResult onError() ", error);
                    ThirdLoginResponseMethod thirdLoginResponseMethod
                            = ThirdLoginResponseMethod.getThirdLoginResponseMethod();
                    thirdLoginResponseMethod.setResult(false);
                    thirdLoginResponseMethod.setIsLogin(false);
                    thirdLoginResponseMethod.setFacebookType();
                    thirdLoginResponseMethod.setErrorCode(JSErrorCode.THIRD_LOGIN_INTER_ERROR);
                    callJs(thirdLoginResponseMethod);
                }
            };
        }

        loginFacebook(getActivity(), facebookLoginCallbackManager, facebookLoginCallback);
    }

    private void loginFacebook(Activity mAct,
                               CallbackManager callbackManager,
                               FacebookCallback<LoginResult> callback) {
        Tlog.v(TAG, " loginFacebook() ");

        if (!FacebookSdk.isInitialized()) {
            FacebookSdk.sdkInitialize(mAct.getApplication());
        }
        AppEventsLogger.activateApp(mAct.getApplication());

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        Tlog.v(TAG, " Facebook isLoggedIn : " + isLoggedIn);

        // Callback registration
        LoginManager.getInstance().registerCallback(callbackManager, callback);

        LoginManager.getInstance().logInWithReadPermissions(mAct,
                Collections.singletonList("public_profile"
//                        Arrays.asList("public_profile"
//                , "user_friends", "email"
                ));

    }


    private IOnCallListener mThirdPayOrderLsn = new IOnCallListener() {
        @Override
        public void onSuccess(MqttPublishRequest request) {
            if (Debuger.isLogDebug) {
                Tlog.v(TAG, "thirdPay msg send success ");
            }
        }

        @Override
        public void onFailed(MqttPublishRequest request, StartaiError startaiError) {
            if (Debuger.isLogDebug) {
                Tlog.e(TAG, "thirdPay msg send fail " + String.valueOf(startaiError));
            }

            ThirdPayResponseMethod thirdPayResponseMethod = ThirdPayResponseMethod.getThirdPayResponseMethod();
            thirdPayResponseMethod.setResult(false);
            thirdPayResponseMethod.setErrorCode(String.valueOf(startaiError.getErrorCode()));
            callJs(thirdPayResponseMethod);
        }

    };

    /**
     * see {@link CommonMqttLsnImpl#onThirdPaymentUnifiedOrderResult(C_0x8028.Resp)}
     */
    @Override
    public void onJsThirdPayOrder(ThirdPayOrderRequestBean mPayBean) {

        C_0x8028.Req.ContentBean req = new C_0x8028.Req.ContentBean(
                Type.ThirdPayment.TYPE_ORDER,
                mPayBean.getPlatform(),
                mPayBean.getNo(),
                mPayBean.getDescription(),
                mPayBean.getCurrency(),
                String.valueOf(mPayBean.getFee()));
        StartAI.getInstance().getBaseBusiManager().thirdPaymentUnifiedOrder(req, mThirdPayOrderLsn);
    }

    @Override
    public void onJsLogOut() {
        StartAI.getInstance().getBaseBusiManager().logout();
    }

    @Override
    public void onJsRequestScanQR() {

        Activity activity = getActivity();
        if (activity == null) {
            ScanORResponseMethod scanORResponseMethod = ScanORResponseMethod.getScanORResponseMethod();
            scanORResponseMethod.setResult(false);
            scanORResponseMethod.setErrorCode(JSErrorCode.ERROR_CODE_SCAN_UNKNOWN);
            callJs(scanORResponseMethod);
        } else {
            ChargerScanActivity.showActivityForResult(activity, REQUEST_SCAN_QR_RESULT);
        }

    }

    private IOnCallListener mGetIdentityCodeLsn = new IOnCallListener() {
        @Override
        public void onSuccess(MqttPublishRequest request) {
            if (Debuger.isLogDebug) {
                Tlog.v(TAG, " get identity code msg send success");
            }
        }

        @Override
        public void onFailed(MqttPublishRequest request, StartaiError startaiError) {
            if (Debuger.isLogDebug) {
                Tlog.e(TAG, " get identity code msg send fail "
                        + String.valueOf(startaiError.getErrorCode()));
            }

            GetIdentityCodeResponseMethod getIdentityCodeResponseMethod =
                    GetIdentityCodeResponseMethod.getGetIdentityCodeResponseMethod();
            getIdentityCodeResponseMethod.setResult(false);
            getIdentityCodeResponseMethod.setErrorCode(String.valueOf(startaiError.getErrorCode()));
            callJs(getIdentityCodeResponseMethod);

        }

    };

    /**
     * see {@link CommonMqttLsnImpl#onGetIdentifyCodeResult(C_0x8021.Resp)}
     */
    @Override
    public void onJsGetIdentityCode(GetIdentityCodeRequestBean mGetIDCodeBean) {
        StartAI.getInstance().getBaseBusiManager().getIdentifyCode(mGetIDCodeBean.getPhone(),
                mGetIDCodeBean.getType(), mGetIdentityCodeLsn);
    }

    private IOnCallListener mMobileLoginByIDLsn = new IOnCallListener() {
        @Override
        public void onSuccess(MqttPublishRequest request) {
            if (Debuger.isLogDebug) {
                Tlog.v(TAG, " login msg send success");
            }
        }

        @Override
        public void onFailed(MqttPublishRequest request, StartaiError startaiError) {
            if (Debuger.isLogDebug) {
                Tlog.e(TAG, " login msg send fail "
                        + String.valueOf(startaiError.getErrorCode()));
            }

            MobileLoginByIDCodeResponseMethod mobileLoginByIDCodeResponseMethod =
                    MobileLoginByIDCodeResponseMethod.getMobileLoginByIDCodeResponseMethod();
            mobileLoginByIDCodeResponseMethod.setResult(false);
            mobileLoginByIDCodeResponseMethod.setIsLogin(false);
            mobileLoginByIDCodeResponseMethod.setErrorCode(String.valueOf(startaiError.getErrorCode()));
            callJs(mobileLoginByIDCodeResponseMethod);

        }

    };

    /**
     * see {@link CommonMqttLsnImpl#onLoginResult(C_0x8018.Resp)}
     */
    @Override
    public void onJsMobileLoginByIdentityCode(MobileLoginByIDCodeRequestBean mMobileLoginByIDBean) {
        StartAI.getInstance().getBaseBusiManager().login(mMobileLoginByIDBean.getPhone(), "",
                mMobileLoginByIDBean.getCode(), mMobileLoginByIDLsn);

    }

    @Override
    public void onJsQuerySystemLanguage() {
        String type = Language.changeSystemLangToH5Lang(app);
        LanguageResponseMethod languageResponseMethod =
                LanguageResponseMethod.getLanguageResponseMethod();
        languageResponseMethod.setResult(true);
        languageResponseMethod.setLan(type);
        callJs(languageResponseMethod);
    }

    @Override
    public void onJsSetSystemLanguage(LanguageSetRequestBean mLanguageSetRequestBean) {

        Language.saveLanguage(app, mLanguageSetRequestBean.getLan());
        Language.changeLanguage(app);

        String type = Language.changeSystemLangToH5Lang(app);
        LanguageResponseMethod languageResponseMethod =
                LanguageResponseMethod.getLanguageResponseMethod();
        languageResponseMethod.setResult(true);
        languageResponseMethod.setLan(type);
        callJs(languageResponseMethod);
    }

    private IOnCallListener mGetVersionLsn = new IOnCallListener() {
        @Override
        public void onSuccess(MqttPublishRequest mqttPublishRequest) {
            if (Debuger.isLogDebug) {
                Tlog.v(TAG, "mGetVersionLsn msg send success ");
            }
        }

        @Override
        public void onFailed(MqttPublishRequest mqttPublishRequest, StartaiError startaiError) {
            if (Debuger.isLogDebug) {
                Tlog.e(TAG, "mGetVersionLsn msg send fail "
                        + startaiError.getErrorCode());
            }
            IsLeastVersionResponseMethod isLeastVersionResponseMethod =
                    IsLeastVersionResponseMethod.getIsLeastVersionResponseMethod();
            isLeastVersionResponseMethod.setResult(false);
            isLeastVersionResponseMethod.setNeedUpgrade(false);
            isLeastVersionResponseMethod.setErrorCode(String.valueOf(startaiError.getErrorCode()));
            callJs(isLeastVersionResponseMethod);
        }

    };


    @Override
    public void onJsCheckAppIsNew() {

        final String os = "android";
        String packageName = app.getApplicationContext().getPackageName();
        Tlog.v(TAG, "checkIsLatestVersion() :" + packageName);
        StartAI.getInstance().getBaseBusiManager().getLatestVersion(os, packageName, mGetVersionLsn);

    }


    private FSDownloadCallback mDownloadAppListener = new FSDownloadCallback() {
        @Override
        public void onStart(DownloadBean downloadBean) {
            if (Debuger.isLogDebug) {
                Tlog.v(TAG, "FSDownloadCallback  onStart() " + downloadBean.toString());
            }
            UpdateProgressResponseMethod updateProgressResponseMethod =
                    UpdateProgressResponseMethod.getUpdateProgressResponseMethod();
            updateProgressResponseMethod.setResult(true);
            updateProgressResponseMethod.setUpdateProgress(downloadBean);
            callJs(updateProgressResponseMethod);
        }

        @Override
        public void onSuccess(DownloadBean downloadBean) {
            if (Debuger.isLogDebug) {
                Tlog.v(TAG, "FSDownloadCallback  onSuccess() " + String.valueOf(downloadBean));
            }

            UpdateProgressResponseMethod updateProgressResponseMethod =
                    UpdateProgressResponseMethod.getUpdateProgressResponseMethod();
            updateProgressResponseMethod.setResult(true);
            updateProgressResponseMethod.setUpdateProgress(downloadBean);
            callJs(updateProgressResponseMethod);

            com.blankj.utilcode.util.AppUtils.installApp(downloadBean.getLocalPath());

        }

        @Override
        public void onFailure(DownloadBean downloadBean, int i) {
            if (Debuger.isLogDebug) {
                Tlog.e(TAG, "FSDownloadCallback  onFailure() " + i + String.valueOf(downloadBean));
            }
            UpdateProgressResponseMethod updateProgressResponseMethod =
                    UpdateProgressResponseMethod.getUpdateProgressResponseMethod();
            updateProgressResponseMethod.setResult(false);
            updateProgressResponseMethod.setErrorCode(String.valueOf(i));
            updateProgressResponseMethod.setUpdateProgress(downloadBean);
            callJs(updateProgressResponseMethod);
        }

        @Override
        public void onProgress(DownloadBean downloadBean) {
            if (Debuger.isLogDebug) {
                Tlog.v(TAG, "FSDownloadCallback  onProgress() " + String.valueOf(downloadBean));
            }
            UpdateProgressResponseMethod updateProgressResponseMethod =
                    UpdateProgressResponseMethod.getUpdateProgressResponseMethod();
            updateProgressResponseMethod.setResult(true);
            updateProgressResponseMethod.setUpdateProgress(downloadBean);
            callJs(updateProgressResponseMethod);
        }

        @Override
        public void onWaiting(DownloadBean downloadBean) {
            if (Debuger.isLogDebug) {
                Tlog.v(TAG, "FSDownloadCallback  onWaiting() " + String.valueOf(downloadBean));
            }
            UpdateProgressResponseMethod updateProgressResponseMethod =
                    UpdateProgressResponseMethod.getUpdateProgressResponseMethod();
            updateProgressResponseMethod.setResult(true);
            updateProgressResponseMethod.setUpdateProgress(downloadBean);
            callJs(updateProgressResponseMethod);
        }

        @Override
        public void onPause(DownloadBean downloadBean) {
            if (Debuger.isLogDebug) {
                Tlog.v(TAG, "FSDownloadCallback  onPause() " + String.valueOf(downloadBean));
            }
            UpdateProgressResponseMethod updateProgressResponseMethod =
                    UpdateProgressResponseMethod.getUpdateProgressResponseMethod();
            updateProgressResponseMethod.setResult(true);
            updateProgressResponseMethod.setUpdateProgress(downloadBean);
            callJs(updateProgressResponseMethod);
        }
    };


    @Override
    public void onJsUpgradeApp(UpgradeAppRequestBean mUpgradeAppRequestBean) {
        if (Debuger.isLogDebug) {
            Tlog.v(TAG, " upgrade app downloadUrl:" + mUpgradeAppRequestBean.getPath());
        }

        //示例代码
        DownloadBean downloadBean = new DownloadBean.Builder()
                .url(mUpgradeAppRequestBean.getPath()) //需要下载的文件
//                .fileName(fileName) //文件保存名，选填
                .build();

        getStartaiDownloaderManager().startDownload(downloadBean, mDownloadAppListener);

    }

    private volatile boolean downloadInit = false;

    private StartaiDownloaderManager getStartaiDownloaderManager() {
        if (!downloadInit) {
            downloadInit = true;
            StartaiDownloaderManager.getInstance().init(app, null);
        }
        return StartaiDownloaderManager.getInstance();
    }

    @Override
    public void onJsCancelUpgradeApp(UpgradeAppRequestBean mUpgradeAppRequestBean) {
        if (Debuger.isLogDebug) {
            Tlog.v(TAG, " cancel upgrade app downloadUrl:" + mUpgradeAppRequestBean.getPath());
        }

        DownloadBean downloadBeanByUrl = StartaiDownloaderManager.getInstance().getFDBManager()
                .getDownloadBeanByUrl(mUpgradeAppRequestBean.getPath());

        if (downloadBeanByUrl != null && downloadBeanByUrl.getStatus() == 2) {
            // 已经下载成功 ，按了取消
            if (Debuger.isLogDebug) {
                Tlog.e(TAG, " user cancelUpdate but already download success ");
            }
            UpdateProgressResponseMethod updateProgressResponseMethod =
                    UpdateProgressResponseMethod.getUpdateProgressResponseMethod();
            updateProgressResponseMethod.setResult(true);
            updateProgressResponseMethod.setUpdateProgress(downloadBeanByUrl);
            callJs(updateProgressResponseMethod);
            return;
        }

        if (mUpgradeAppRequestBean.getPath() != null) {
            StartaiDownloaderManager.getInstance().stopDownloader(mUpgradeAppRequestBean.getPath());
        }

    }

    @Override
    public void onJsAppVersion() {
        GetAppVersionResponseMethod appVersionResponseMethod =
                GetAppVersionResponseMethod.getAppVersionResponseMethod();
        appVersionResponseMethod.setResult(true);
        appVersionResponseMethod.setVersion(AppUtils.getAppVersionStr(app));
        callJs(appVersionResponseMethod);
    }


    private IOnCallListener mGetUserInfoLsn = new IOnCallListener() {

        @Override
        public void onSuccess(MqttPublishRequest mqttPublishRequest) {
            if (Debuger.isLogDebug) {
                Tlog.v(TAG, " getUserInfo msg send success ");
            }
        }

        @Override
        public void onFailed(MqttPublishRequest mqttPublishRequest, StartaiError startaiError) {
            if (Debuger.isLogDebug) {
                Tlog.e(TAG, " mGetUserInfoLsn msg send fail " + startaiError.getErrorCode());
            }

            UserInfoResponseMethod userInfoResponseMethod =
                    UserInfoResponseMethod.getUserInfoResponseMethod();
            userInfoResponseMethod.setResult(false);
            userInfoResponseMethod.setErrorCode(String.valueOf(startaiError.getErrorCode()));
            callJs(userInfoResponseMethod);

        }

    };

    /**
     * see {@link CommonMqttLsnImpl#onGetUserInfoResult(C_0x8024.Resp)}
     */
    @Override
    public void onJsGetUserInfo() {
        StartAI.getInstance().getBaseBusiManager().getUserInfo(mGetUserInfoLsn);
    }

    /**
     * see {@link #onActivityResult(int, int, Intent)}
     */
    @Override
    public void onJsTakePhoto() {

        PermissionHelper.requestPermission(app, new PermissionRequest.OnPermissionResult() {
            @Override
            public boolean onPermissionRequestResult(String permission, boolean granted) {

                if (!granted) {
                    ModifyHeadpicResponseMethod modifyHeadpicSendResponseMethod =
                            ModifyHeadpicResponseMethod.getModifyHeadpicResponseMethod();
                    modifyHeadpicSendResponseMethod.setResult(false);
                    modifyHeadpicSendResponseMethod.setIsSend(false);

                    if (Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(permission)) {
                        modifyHeadpicSendResponseMethod.setErrorCode(JSErrorCode.UPDATE_HEAD_PIC_ERROR_NO_LOCAL_PERMISSION);
                    } else if (Manifest.permission.CAMERA.equals(permission)) {
                        modifyHeadpicSendResponseMethod.setErrorCode(JSErrorCode.UPDATE_HEAD_PIC_ERROR_NO_CAMERA_PERMISSION);
                    }
                    callJs(modifyHeadpicSendResponseMethod);
                }

                return granted;
            }
        }, new PermissionRequest.OnPermissionFinish() {
            @Override
            public void onAllPermissionRequestFinish() {
                final File savePhotoFile = FileManager.getPhotoFileNoCheckPermission();
                Tlog.d(TAG, "takePhoto() " + (savePhotoFile != null ? savePhotoFile.getAbsolutePath() : " null "));
                Uri imageUri = PhotoUtils.getTakePhotoURI(app, savePhotoFile);
                Intent intent = PhotoUtils.requestTakePhoto(imageUri);
                takePhotoUri = imageUri;
                jsStartActivityForResult(intent, TAKE_PHOTO_CODE);
            }
        }, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA);

    }

    /**
     * see {@link #onActivityResult(int, int, Intent)}
     */
    @Override
    public void onJsLocalPhoto() {
        PermissionHelper.requestSinglePermission(app, new PermissionRequest.OnPermissionResult() {
            @Override
            public boolean onPermissionRequestResult(String permission, boolean granted) {
                if (granted) {
                    Intent intent = PhotoUtils.requestLocalPhoto();
                    jsStartActivityForResult(intent, LOCAL_PHOTO_CODE);
                } else {
                    ModifyHeadpicResponseMethod modifyHeadpicSendResponseMethod =
                            ModifyHeadpicResponseMethod.getModifyHeadpicResponseMethod();
                    modifyHeadpicSendResponseMethod.setResult(false);
                    modifyHeadpicSendResponseMethod.setIsSend(false);
                    modifyHeadpicSendResponseMethod.setErrorCode(JSErrorCode.UPDATE_HEAD_PIC_ERROR_NO_LOCAL_PERMISSION);
                    callJs(modifyHeadpicSendResponseMethod);
                }
                return true;
            }
        }, PermissionGroup.STORAGE);

    }

    /**
     * see {@link CommonMqttLsnImpl#onUpdateUserInfoResult(C_0x8020.Resp)}
     */
    @Override
    public void onJsModifyUserName(ModifyUserNameRequestBean mModifyNameBean) {

        Tlog.v(TAG, "updateUserName() :" + mModifyNameBean.getType() + " " + mModifyNameBean.getValue());

        C_0x8020.Req.ContentBean contentBean = new C_0x8020.Req.ContentBean();

        if (mModifyNameBean.typeIsSurname()) {
            contentBean.setLastName(mModifyNameBean.getValue());
        } else if (mModifyNameBean.typeIsName()) {
            contentBean.setFirstName(mModifyNameBean.getValue());
        } else {

            Tlog.e(TAG, "updateUserName() UserUpdateInfo surname invalid " + mModifyNameBean.getType());

            ModifyUserInfoResponseMethod modifyUserInfoResponseMethod =
                    ModifyUserInfoResponseMethod.getModifyUserInfoResponseMethod();
            modifyUserInfoResponseMethod.setResult(false);
            modifyUserInfoResponseMethod.setErrorCode("000000");
            callJs(modifyUserInfoResponseMethod);

            return;
        }

        contentBean.setUserid(getUserID());
        StartAI.getInstance().getBaseBusiManager().updateUserInfo(contentBean, new IOnCallListener() {
            @Override
            public void onSuccess(MqttPublishRequest request) {
                if (Debuger.isLogDebug) {
                    Tlog.v(TAG, " updateUserName msg send success ");
                }
            }

            @Override
            public void onFailed(MqttPublishRequest request, StartaiError startaiError) {
                if (Debuger.isLogDebug) {
                    Tlog.e(TAG, " updateUserName msg send fail " + String.valueOf(startaiError.getErrorCode()));
                }

                ModifyUserInfoResponseMethod modifyUserInfoResponseMethod =
                        ModifyUserInfoResponseMethod.getModifyUserInfoResponseMethod();
                modifyUserInfoResponseMethod.setResult(false);
                modifyUserInfoResponseMethod.setErrorCode(String.valueOf(startaiError.getErrorCode()));
                callJs(modifyUserInfoResponseMethod);

            }

        });

    }

    /**
     * see {@link CommonMqttLsnImpl#onUpdateUserPwdResult(C_0x8025.Resp)}
     */
    @Override
    public void onJsModifyUserPwd(ModifyUserPwdRequestBean mModifyPwdBean) {
        Tlog.v(TAG, "updateUserPwd() old:" + mModifyPwdBean.getOldPwd() + " new:" + mModifyPwdBean.getNewPwd());
        StartAI.getInstance().getBaseBusiManager()
                .updateUserPwd(mModifyPwdBean.getOldPwd(), mModifyPwdBean.getNewPwd(), new IOnCallListener() {
                    @Override
                    public void onSuccess(MqttPublishRequest request) {
                        if (Debuger.isLogDebug) {
                            Tlog.v(TAG, " updateUserPwd msg send success ");
                        }
                    }

                    @Override
                    public void onFailed(MqttPublishRequest request, StartaiError startaiError) {
                        if (Debuger.isLogDebug) {
                            Tlog.e(TAG, " updateUserPwd msg send fail " + String.valueOf(startaiError.getErrorCode()));
                        }
                        ModifyUserPwdResponseMethod modifyUserPwdResponseMethod =
                                ModifyUserPwdResponseMethod.getModifyUserPwdResponseMethod();
                        modifyUserPwdResponseMethod.setIsSuccessfully(false);
                        modifyUserPwdResponseMethod.setResult(false);
                        modifyUserPwdResponseMethod.setErrorCode(String.valueOf(startaiError.getErrorCode()));
                    }

                });
    }

    /**
     * see {@link CommonMqttLsnImpl#onUpdateUserInfoResult(C_0x8020.Resp)}
     */
    @Override
    public void onJsModifyNickName(ModifyNickNameRequestBean mModifyNicknameBean) {
        C_0x8020.Req.ContentBean contentBean = new C_0x8020.Req.ContentBean();
        contentBean.setUserid(getUserID());
        contentBean.setNickName(mModifyNicknameBean.getNickname());
        StartAI.getInstance().getBaseBusiManager().updateUserInfo(contentBean, new IOnCallListener() {
            @Override
            public void onSuccess(MqttPublishRequest request) {
                if (Debuger.isLogDebug) {
                    Tlog.v(TAG, " updateNickName msg send success ");
                }
            }

            @Override
            public void onFailed(MqttPublishRequest request, StartaiError startaiError) {
                if (Debuger.isLogDebug) {
                    Tlog.e(TAG, " updateNickName msg send fail " + String.valueOf(startaiError.getErrorCode()));
                }

                ModifyUserInfoResponseMethod modifyUserInfoResponseMethod =
                        ModifyUserInfoResponseMethod.getModifyUserInfoResponseMethod();
                modifyUserInfoResponseMethod.setResult(false);
                modifyUserInfoResponseMethod.setErrorCode(String.valueOf(startaiError.getErrorCode()));
                callJs(modifyUserInfoResponseMethod);

            }

        });

    }

    private static final int LOCAL_PHOTO_CODE = 0x0367;
    private static final int CROP_LOCAL_PHOTO = 0x0368;

    private static final int TAKE_PHOTO_CODE = 0x0369;
    private static final int CROP_TAKE_PHOTO = 0x036A;

    // 扫描二维码
    private static final int REQUEST_SCAN_QR_RESULT = 0x6532;


    private File localPhotoFile;

    private Uri takePhotoUri;
    private File takePhotoFile;

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SCAN_QR_RESULT) {
            onScanQRCodeResult(resultCode, data);
        } else if (requestCode >= LOCAL_PHOTO_CODE && requestCode <= CROP_TAKE_PHOTO) {
            onPhotoResult(requestCode, resultCode, data);
        } else if (requestCode == RC_SIGN_IN) {
            callGoogleLoginResult(data);
        } else if (requestCode == RC_BIND_IN) {
            callGoogleBindResult(data);
        } else if (requestCode == REQUEST_LOCATION) {
            LooperManager.getInstance().getWorkHandler().post(new Runnable() {
                @Override
                public void run() {
                    EnableLocationResponseMethod enableLocationResponseMethod
                            = EnableLocationResponseMethod.getEnableLocationResponseMethod();
                    enableLocationResponseMethod.setResult(true);
                    boolean gps = gpsEnabled();
                    boolean ip = ipEnabled();
                    enableLocationResponseMethod.setEnabled(gps || ip);
                    if (gps && ip) {
                        enableLocationResponseMethod.setGpsIpType();
                    } else if (gps) {
                        enableLocationResponseMethod.setGpsType();
                    } else if (ip) {
                        enableLocationResponseMethod.setIpType();
                    }
                    callJs(enableLocationResponseMethod);
                }
            });

        }

        if (mFacebookStatus == FACEBOOK_LOGIN) {
            try {
                Tlog.d(TAG, " onActivityResult is facebook login status");
                CallbackManager facebookCallbackManager = getFacebookLoginCallbackManager();
                if (facebookCallbackManager != null) {
                    facebookCallbackManager.onActivityResult(requestCode, resultCode, data);
                }
            } catch (Exception e) {
                Tlog.e(TAG, " facebook login Error", e);
                ThirdLoginResponseMethod thirdLoginResponseMethod
                        = ThirdLoginResponseMethod.getThirdLoginResponseMethod();
                thirdLoginResponseMethod.setResult(false);
                thirdLoginResponseMethod.setIsLogin(false);
                thirdLoginResponseMethod.setFacebookType();
                thirdLoginResponseMethod.setErrorCode(JSErrorCode.THIRD_LOGIN_INTER_ERROR);
                callJs(thirdLoginResponseMethod);
            }

        } else if (mFacebookStatus == FACEBOOK_BIND) {
            try {
                Tlog.d(TAG, " onActivityResult is facebook bind status");
                CallbackManager facebookCallbackManager = getFacebookBindCallbackManager();
                if (facebookCallbackManager != null) {
                    facebookCallbackManager.onActivityResult(requestCode, resultCode, data);
                }

            } catch (Exception e) {
                Tlog.e(TAG, " facebook bind Error", e);
                ThirdBindResponseMethod thirdBindResponseMethod_GOOGLE =
                        ThirdBindResponseMethod.getThirdBindResponseMethod();
                thirdBindResponseMethod_GOOGLE.setResult(false);
                thirdBindResponseMethod_GOOGLE.setIsBind(false);
                thirdBindResponseMethod_GOOGLE.setFacebookType();
                thirdBindResponseMethod_GOOGLE.setErrorCode(JSErrorCode.THIRD_BIND_INTER_ERROR);
                callJs(thirdBindResponseMethod_GOOGLE);
            }

        }


    }


    private void callGoogleBindResult(Intent data) {
        // The Task returned from this call is always completed, no need to attach
        // a listener.

//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            callGoogleLoginIn(task);

        GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

        GoogleSignInAccount account = null;

        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            account = result.getSignInAccount();
        }

        if (account == null) {
            ThirdBindResponseMethod thirdBindResponseMethod_GOOGLE =
                    ThirdBindResponseMethod.getThirdBindResponseMethod();
            thirdBindResponseMethod_GOOGLE.setResult(false);
            thirdBindResponseMethod_GOOGLE.setIsBind(false);
            thirdBindResponseMethod_GOOGLE.setGoogleType();
            thirdBindResponseMethod_GOOGLE.setErrorCode(JSErrorCode.THIRD_BIND_INTER_ERROR);
            callJs(thirdBindResponseMethod_GOOGLE);
            return;
        }

        C_0x8037.Req.ContentBean mBean = new C_0x8037.Req.ContentBean();
        C_0x8037.Req.ContentBean.UserinfoBean userinfo = new C_0x8037.Req.ContentBean.UserinfoBean();
        userinfo.setUnionid(account.getId());
        userinfo.setOpenid(account.getId());
        userinfo.setNickname(account.getDisplayName());
        userinfo.setLastName(account.getFamilyName());
        userinfo.setFirstName(account.getGivenName());
        Uri photoUrl = account.getPhotoUrl();
        userinfo.setHeadimgurl(photoUrl != null ? photoUrl.toString() : null);
        mBean.setUserinfo(userinfo);
        mBean.setType(C_0x8027.THIRD_GOOGLE);

        StartAI.getInstance().getBaseBusiManager().bindThirdAccount(mBean, new IOnCallListener() {
            @Override
            public void onSuccess(MqttPublishRequest request) {
                Tlog.w(TAG, " bind google msg send success  ");
            }

            @Override
            public void onFailed(MqttPublishRequest request, StartaiError startaiError) {
                Tlog.e(TAG, " bind google msg send fail ");

                ThirdBindResponseMethod thirdBindResponseMethod_GOOGLE =
                        ThirdBindResponseMethod.getThirdBindResponseMethod();
                thirdBindResponseMethod_GOOGLE.setResult(false);
                thirdBindResponseMethod_GOOGLE.setIsBind(false);
                thirdBindResponseMethod_GOOGLE.setGoogleType();
                thirdBindResponseMethod_GOOGLE.setErrorCode(String.valueOf(startaiError.getErrorCode()));
                callJs(thirdBindResponseMethod_GOOGLE);

            }

        });

    }


    private void callGoogleLoginResult(Intent data) {
        // The Task returned from this call is always completed, no need to attach
        // a listener.

//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            callGoogleLoginIn(task);

        GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

        GoogleSignInAccount account = null;

        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            account = result.getSignInAccount();
        }

        if (account == null) {
            ThirdLoginResponseMethod thirdLoginResponseMethod
                    = ThirdLoginResponseMethod.getThirdLoginResponseMethod();
            thirdLoginResponseMethod.setResult(false);
            thirdLoginResponseMethod.setIsLogin(false);
            thirdLoginResponseMethod.setGoogleType();
            thirdLoginResponseMethod.setErrorCode(JSErrorCode.THIRD_LOGIN_INTER_ERROR);
            callJs(thirdLoginResponseMethod);
            return;
        }

        C_0x8027.Req.ContentBean mBean = new C_0x8027.Req.ContentBean();
        C_0x8027.Req.ContentBean.UserinfoBean userinfo = new C_0x8027.Req.ContentBean.UserinfoBean();
        userinfo.setUnionid(account.getId());
        userinfo.setOpenid(account.getId());
        userinfo.setNickname(account.getDisplayName());
        userinfo.setLastName(account.getFamilyName());
        userinfo.setFirstName(account.getGivenName());
        Uri photoUrl = account.getPhotoUrl();
        userinfo.setHeadimgurl(photoUrl != null ? photoUrl.toString() : null);
        mBean.setUserinfo(userinfo);
        mBean.setType(C_0x8027.THIRD_GOOGLE);

//                    StartAI.getInstance().getBaseBusiManager().bindThirdAccount();

        StartAI.getInstance().getBaseBusiManager().loginWithThirdAccount(mBean, new IOnCallListener() {
            @Override
            public void onSuccess(MqttPublishRequest request) {
                Tlog.w(TAG, " login google msg send success  ");
            }

            @Override
            public void onFailed(MqttPublishRequest request, StartaiError startaiError) {
                Tlog.e(TAG, " login google msg send fail ");

                ThirdLoginResponseMethod thirdLoginResponseMethod
                        = ThirdLoginResponseMethod.getThirdLoginResponseMethod();
                thirdLoginResponseMethod.setResult(false);
                thirdLoginResponseMethod.setIsLogin(false);
                thirdLoginResponseMethod.setGoogleType();
                thirdLoginResponseMethod.setErrorCode(String.valueOf(startaiError.getErrorCode()));
                callJs(thirdLoginResponseMethod);

            }

        });


    }

    private void onPhotoResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_CANCELED) {

            ModifyHeadpicResponseMethod modifyHeadpicSendResponseMethod =
                    ModifyHeadpicResponseMethod.getModifyHeadpicResponseMethod();
            modifyHeadpicSendResponseMethod.setResult(false);
            modifyHeadpicSendResponseMethod.setIsSend(false);
            modifyHeadpicSendResponseMethod.setErrorCode(JSErrorCode.UPDATE_HEAD_PIC_CANCEL);
            callJs(modifyHeadpicSendResponseMethod);

            return;
        } else if (resultCode == Activity.RESULT_FIRST_USER) {

            ModifyHeadpicResponseMethod modifyHeadpicSendResponseMethod =
                    ModifyHeadpicResponseMethod.getModifyHeadpicResponseMethod();
            modifyHeadpicSendResponseMethod.setResult(false);
            modifyHeadpicSendResponseMethod.setIsSend(false);
            modifyHeadpicSendResponseMethod.setErrorCode(JSErrorCode.UPDATE_HEAD_PIC_ERROR);
            callJs(modifyHeadpicSendResponseMethod);

            return;
        }

        if (requestCode == LOCAL_PHOTO_CODE) {

            final Uri imageUri = data.getData();
            Tlog.d(TAG, " onActivityResult LOCAL_PHOTO_CODE success " + String.valueOf(imageUri));
            localPhotoFile = crop(imageUri, CROP_LOCAL_PHOTO);

        } else if (requestCode == TAKE_PHOTO_CODE) {
            Tlog.d(TAG, " onActivityResult TAKE_PHOTO_CODE success " + String.valueOf(takePhotoUri));
            takePhotoFile = crop(takePhotoUri, CROP_TAKE_PHOTO);

        } else if (requestCode == CROP_TAKE_PHOTO) {

            cropSuccess(takePhotoFile);

        } else if (requestCode == CROP_LOCAL_PHOTO) {

            cropSuccess(localPhotoFile);
        }
    }

    private volatile boolean uploadInit;

    private StartaiUploaderManager getStartaiUploaderManager() {
        if (!uploadInit) {
            uploadInit = true;
            //初始文件上传模块
            StartaiUploaderManager.getInstance().init(app, null);
        }
        return StartaiUploaderManager.getInstance();
    }

    // 裁剪
    private File crop(Uri imageUri, int code) {

        File path = FileManager.getPhotoFile();

        if (path == null) {
            Tlog.e(TAG, " crop->" + (imageUri != null ? imageUri.getPath() : " null") + "; path == null");

            ModifyHeadpicResponseMethod modifyHeadpicSendResponseMethod =
                    ModifyHeadpicResponseMethod.getModifyHeadpicResponseMethod();
            modifyHeadpicSendResponseMethod.setResult(false);
            modifyHeadpicSendResponseMethod.setIsSend(false);
            modifyHeadpicSendResponseMethod.setErrorCode(JSErrorCode.UPDATE_HEAD_PIC_ERROR_NO_LOCAL_PERMISSION);
            callJs(modifyHeadpicSendResponseMethod);

            return null;
        }

        // 启动裁剪程序
        Uri outUri = Uri.fromFile(path);
        Intent intent = PhotoUtils.cropHeadpic(imageUri, outUri);
        jsStartActivityForResult(intent, code);
        return path;
    }


    // 裁剪成功
    private void cropSuccess(File path) {

        String filePath = "";

        if (path != null && path.exists()) {
            filePath = path.getAbsolutePath();
            try {
                PhotoUtils.compressImage(filePath);
            } catch (IOException e) {
                e.printStackTrace();
                filePath = "";
            }
        }

        Tlog.d(TAG, " onActivityResult CROP_PHOTO_SUCCESS:" + filePath);

        if (!"".equalsIgnoreCase(filePath)) {
            //示例代码
            UploadBean uploadentity = new UploadBean.Builder()
                    .localPath(String.valueOf(filePath)) //本地文件路径
                    .build();

            getStartaiUploaderManager().startUpload(uploadentity, mLogoUploadCallBack);

        } else {
            ModifyHeadpicResponseMethod modifyHeadpicSendResponseMethod =
                    ModifyHeadpicResponseMethod.getModifyHeadpicResponseMethod();
            modifyHeadpicSendResponseMethod.setResult(false);
            modifyHeadpicSendResponseMethod.setIsSend(false);
            modifyHeadpicSendResponseMethod.setErrorCode(JSErrorCode.UPDATE_HEAD_PIC_COMPRESS_ERROR);
            callJs(modifyHeadpicSendResponseMethod);
        }

    }


    private FSUploadCallback mLogoUploadCallBack = new FSUploadCallback() {
        @Override
        public void onStart(UploadBean uploadBean) {
            if (Debuger.isLogDebug) {
                Tlog.v(TAG, " mLogoUploadCallBack onStart " + uploadBean.toString());
            }

        }

        @Override
        public void onSuccess(UploadBean uploadBean) {
            if (Debuger.isLogDebug) {
                Tlog.v(TAG, " mLogoUploadCallBack onSuccess " + uploadBean.toString());
            }

            ModifyHeadpicResponseMethod modifyHeadpicSendResponseMethod =
                    ModifyHeadpicResponseMethod.getModifyHeadpicResponseMethod();
            modifyHeadpicSendResponseMethod.setResult(true);
            modifyHeadpicSendResponseMethod.setIsSend(false);
            modifyHeadpicSendResponseMethod.setUploadBean(uploadBean);
            callJs(modifyHeadpicSendResponseMethod);


            C_0x8020.Req.ContentBean contentBean = new C_0x8020.Req.ContentBean();
            contentBean.setHeadPic(uploadBean.getHttpDownloadUrl());
            contentBean.setUserid(getUserID());
            StartAI.getInstance().getBaseBusiManager().updateUserInfo(contentBean, new IOnCallListener() {
                @Override
                public void onSuccess(MqttPublishRequest mqttPublishRequest) {
                    if (Debuger.isLogDebug) {
                        Tlog.v(TAG, " updateUserInfo msg send success ");
                    }

                }

                @Override
                public void onFailed(MqttPublishRequest mqttPublishRequest, StartaiError startaiError) {
                    if (Debuger.isLogDebug) {
                        Tlog.e(TAG, " updateUserInfo msg send fail " + startaiError.getErrorCode());
                    }
                    ModifyHeadpicResponseMethod modifyHeadpicSendResponseMethod =
                            ModifyHeadpicResponseMethod.getModifyHeadpicResponseMethod();
                    modifyHeadpicSendResponseMethod.setResult(false);
                    modifyHeadpicSendResponseMethod.setIsSend(false);
                    modifyHeadpicSendResponseMethod.setErrorCode(String.valueOf(startaiError.getErrorCode()));
                    callJs(modifyHeadpicSendResponseMethod);
                }

            });

        }

        @Override
        public void onFailure(UploadBean uploadBean, int i) {
            if (Debuger.isLogDebug) {
                Tlog.v(TAG, " mLogoUploadCallBack onFailure " + i + " " + String.valueOf(uploadBean));
            }

            ModifyHeadpicResponseMethod modifyHeadpicSendResponseMethod =
                    ModifyHeadpicResponseMethod.getModifyHeadpicResponseMethod();
            modifyHeadpicSendResponseMethod.setResult(false);
            modifyHeadpicSendResponseMethod.setIsSend(false);
            modifyHeadpicSendResponseMethod.setErrorCode(String.valueOf(i));
            callJs(modifyHeadpicSendResponseMethod);

        }

        @Override
        public void onProgress(UploadBean uploadBean) {
            if (Debuger.isLogDebug) {
                Tlog.v(TAG, " mLogoUploadCallBack onProgress " + uploadBean.getProgress());
            }

            ModifyHeadpicResponseMethod modifyHeadpicSendResponseMethod =
                    ModifyHeadpicResponseMethod.getModifyHeadpicResponseMethod();
            modifyHeadpicSendResponseMethod.setResult(true);
            modifyHeadpicSendResponseMethod.setIsSend(false);
            modifyHeadpicSendResponseMethod.setUploadBean(uploadBean);
            callJs(modifyHeadpicSendResponseMethod);
        }

        @Override
        public void onWaiting(UploadBean uploadBean) {
            if (Debuger.isLogDebug) {
                Tlog.v(TAG, " mLogoUploadCallBack onWaiting " + String.valueOf(uploadBean));
            }
            ModifyHeadpicResponseMethod modifyHeadpicSendResponseMethod =
                    ModifyHeadpicResponseMethod.getModifyHeadpicResponseMethod();
            modifyHeadpicSendResponseMethod.setResult(true);
            modifyHeadpicSendResponseMethod.setIsSend(false);
            modifyHeadpicSendResponseMethod.setUploadBean(uploadBean);
            callJs(modifyHeadpicSendResponseMethod);
        }

        @Override
        public void onPause(UploadBean uploadBean) {
            if (Debuger.isLogDebug) {
                Tlog.v(TAG, " mLogoUploadCallBack onPause " + String.valueOf(uploadBean));
            }
            ModifyHeadpicResponseMethod modifyHeadpicSendResponseMethod =
                    ModifyHeadpicResponseMethod.getModifyHeadpicResponseMethod();
            modifyHeadpicSendResponseMethod.setResult(true);
            modifyHeadpicSendResponseMethod.setIsSend(false);
            modifyHeadpicSendResponseMethod.setUploadBean(uploadBean);
            callJs(modifyHeadpicSendResponseMethod);
        }
    };


    private void onScanQRCodeResult(int resultCode, Intent data) {

        ScanORResponseMethod scanORResponseMethod = ScanORResponseMethod.getScanORResponseMethod();
        if (resultCode == Activity.RESULT_OK) {

            String scanResult = data.getStringExtra("result");
            Tlog.i(TAG, "scanResult = " + scanResult);

            String[] aar = RuiooORCodeUtils.getStoreIdAndChargerId(scanResult);
            if (aar == null) {

                Tlog.e(TAG, " QR parse fail");
                scanORResponseMethod.setResult(false);
                scanORResponseMethod.setErrorCode(JSErrorCode.ERROR_CODE_ERROR_URL);

            } else {
                if (aar.length == 2) {
                    String storeId = aar[0];
                    String chargerId = aar[1];
                    scanORResponseMethod.setResult(true);
                    scanORResponseMethod.setIMEI(chargerId);
                    scanORResponseMethod.setMerchantId(storeId);
                } else if (aar.length == 1) {
                    String chargerId = aar[0];
                    scanORResponseMethod.setResult(true);
                    scanORResponseMethod.setIMEI(chargerId);
                } else {
                    Tlog.e(TAG, " QR parse length fail");
                    scanORResponseMethod.setResult(false);
                    scanORResponseMethod.setErrorCode(JSErrorCode.ERROR_CODE_ERROR_URL);
                }
            }

        } else if (resultCode == Activity.RESULT_CANCELED) {
            Tlog.e(TAG, " QR scan fail user cancel ");
            scanORResponseMethod.setResult(false);
            scanORResponseMethod.setErrorCode(JSErrorCode.ERROR_CODE_SCAN_CANCEL);
        } else {
            Tlog.e(TAG, " QR scan fail unknown ");
            scanORResponseMethod.setResult(false);
            scanORResponseMethod.setErrorCode(JSErrorCode.ERROR_CODE_SCAN_UNKNOWN);
        }
        callJs(scanORResponseMethod);
    }


}
