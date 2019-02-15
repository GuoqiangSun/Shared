package cn.com.startai.sharedlib.app.mutual.impl;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;

import cn.com.startai.fssdk.FSDownloadCallback;
import cn.com.startai.fssdk.FSUploadCallback;
import cn.com.startai.fssdk.StartaiDownloaderManager;
import cn.com.startai.fssdk.StartaiUploaderManager;
import cn.com.startai.fssdk.db.entity.DownloadBean;
import cn.com.startai.fssdk.db.entity.UploadBean;
import cn.com.startai.mqttsdk.StartAI;
import cn.com.startai.mqttsdk.base.StartaiError;
import cn.com.startai.mqttsdk.busi.entity.C_0x8020;
import cn.com.startai.mqttsdk.busi.entity.C_0x8028;
import cn.com.startai.mqttsdk.busi.entity.C_0x8033;
import cn.com.startai.mqttsdk.busi.entity.C_0x8036;
import cn.com.startai.mqttsdk.busi.entity.type.Type;
import cn.com.startai.mqttsdk.listener.IOnCallListener;
import cn.com.startai.mqttsdk.mqtt.request.MqttPublishRequest;
import cn.com.startai.scansdk.ChargerScanActivity;
import cn.com.startai.scansdk.permission.PermissionHelper;
import cn.com.startai.sharedlib.R;
import cn.com.startai.sharedlib.app.global.Debuger;
import cn.com.startai.sharedlib.app.global.FileManager;
import cn.com.startai.sharedlib.app.js.CommonJsInterfaceTask;
import cn.com.startai.sharedlib.app.js.Utils.JSErrorCode;
import cn.com.startai.sharedlib.app.js.Utils.Language;
import cn.com.startai.sharedlib.app.js.method2Impl.AliBindResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.AliLoginResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.AliUnBindResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.GetAppVersionResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.GetIdentityCodeResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.IsLeastVersionResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.IsLoginResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.LanguageResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.MobileLoginByIDCodeResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.ModifyHeadpicResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.ModifyUserInfoResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.ModifyUserPwdResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.PhoneBindResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.ScanORResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.ThirdPayResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.UpdateProgressResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.UserInfoResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.WxLoginResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.WxUnBindResponseMethod;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.BindPhoneJsRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.GetIdentityCodeRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.LanguageSetRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.MobileLoginByIDCodeRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.ModifyNickNameRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.ModifyUserNameRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.ModifyUserPwdRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.ThirdPayOrderRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.UpgradeAppRequestBean;
import cn.com.startai.sharedlib.app.mutual.IMutualCallBack;
import cn.com.startai.sharedlib.app.mutual.IUserIDManager;
import cn.com.startai.sharedlib.app.mutual.utils.RuiooORCodeUtils;
import cn.com.startai.sharedlib.app.mutual.utils.WXApiHelper;
import cn.com.startai.sharedlib.app.view.SharedApplication;
import cn.com.swain.baselib.jsInterface.request.bean.BaseCommonJsRequestBean;
import cn.com.swain.baselib.log.Tlog;
import cn.com.swain.baselib.util.AppUtils;
import cn.com.swain.baselib.util.PhotoUtils;

/**
 * author: Guoqiang_Sun
 * date: 2018/12/25 0025
 * Desc:
 */
public class CommonJsRequestInterfaceImpl extends MutualCallBackWrapper implements CommonJsInterfaceTask.ICommonJsRequestInterface {

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
            Tlog.e(TAG, " jsonData: " + jsonData + "\n handleJsRequest parse json ", e);
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

    @Override
    public void onJsWXLogin() {

        //先判断是否安装微信APP,按照微信的说法，目前移动应用上微信登录只提供原生的登录方式，需要用户安装微信客户端才能配合使用。
        if (!AppUtils.isAppInstalled(app, "com.tencent.mm")) {
            Tlog.e(TAG, " com.tencent.mm is not install");

            WxLoginResponseMethod mWxResponseMethod =
                    WxLoginResponseMethod.getWxLoginResponseMethod();
            mWxResponseMethod.setResult(false);
            mWxResponseMethod.setErrorCode(JSErrorCode.ERROR_CODE_WX_LOGIN_NO_CLIENT);
            callJs(mWxResponseMethod);

            return;
        }

        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = WXApiHelper.Consts.WX_LOGIN_TAG;
        //向微信发送请求
        WXApiHelper.getInstance().getWXApi(app).sendReq(req);

    }

    @Override
    public void onJsWXBind() {

        //先判断是否安装微信APP,按照微信的说法，目前移动应用上微信登录只提供原生的登录方式，需要用户安装微信客户端才能配合使用。
        IWXAPI wxApi = WXApiHelper.getInstance().getWXApi(app);
//        if (!AppUtils.isAppInstalled(app, "com.tencent.mm")) {

        if (!wxApi.isWXAppInstalled()) {
            Tlog.e(TAG, " com.tencent.mm is not install");

            WxLoginResponseMethod mWxResponseMethod =
                    WxLoginResponseMethod.getWxLoginResponseMethod();
            mWxResponseMethod.setResult(false);
            mWxResponseMethod.setErrorCode(JSErrorCode.ERROR_CODE_WX_LOGIN_NO_CLIENT);
            callJs(mWxResponseMethod);

            return;
        }

        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = WXApiHelper.Consts.WX_BIND_TAG;
        //向微信发送请求
        wxApi.sendReq(req);

    }


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

            @Override
            public boolean needUISafety() {
                return false;
            }
        });
    }

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

            @Override
            public boolean needUISafety() {
                return false;
            }
        });
    }

    @Override
    public void onJsBindPhone(BindPhoneJsRequestBean mBindPhoneBean) {

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

                    @Override
                    public boolean needUISafety() {
                        return false;
                    }
                });
    }

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

            @Override
            public boolean needUISafety() {
                return false;
            }
        });
    }

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

            @Override
            public boolean needUISafety() {
                return false;
            }
        });
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

        @Override
        public boolean needUISafety() {
            return false;
        }
    };

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

        @Override
        public boolean needUISafety() {
            return false;
        }
    };

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

        @Override
        public boolean needUISafety() {
            return false;
        }
    };

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

        @Override
        public boolean needUISafety() {
            return false;
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
    public void onJsRequestUpgradeApp(UpgradeAppRequestBean mUpgradeAppRequestBean) {
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
    public void onJsRequestAppVersion() {
        GetAppVersionResponseMethod appVersionResponseMethod =
                GetAppVersionResponseMethod.getAppVersionResponseMethod();
        appVersionResponseMethod.setResult(true);
        appVersionResponseMethod.setVersion(com.blankj.utilcode.util.AppUtils.getAppVersionName());
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

        @Override
        public boolean needUISafety() {
            return false;
        }
    };


    @Override
    public void onJsRequestUserInfo() {
        StartAI.getInstance().getBaseBusiManager().getUserInfo(mGetUserInfoLsn);
    }

    @Override
    public void onJsRequestTakePhoto() {

        final File savePhotoFile = FileManager.getPhotoFile();

        Tlog.d(TAG, "takePhoto() " + (savePhotoFile != null ? savePhotoFile.getAbsolutePath() : " null "));

        if (savePhotoFile == null) {
            ModifyHeadpicResponseMethod modifyHeadpicSendResponseMethod =
                    ModifyHeadpicResponseMethod.getModifyHeadpicResponseMethod();
            modifyHeadpicSendResponseMethod.setResult(false);
            modifyHeadpicSendResponseMethod.setIsSend(false);
            modifyHeadpicSendResponseMethod.setErrorCode(JSErrorCode.UPDATE_HEAD_PIC_ERROR_NO_LOCAL_PERMISSION);
            callJs(modifyHeadpicSendResponseMethod);
            return;
        }

        PermissionHelper.requestCamara(new PermissionHelper.OnPermissionGrantedListener() {
            @Override
            public void onPermissionGranted() {
                Uri imageUri = PhotoUtils.getTakePhotoURI(app, savePhotoFile);
                Intent intent = PhotoUtils.requestTakePhoto(imageUri);
                takePhotoUri = imageUri;
                jsStartActivityForResult(intent, TAKE_PHOTO_CODE);
            }
        }, new PermissionHelper.OnPermissionDeniedListener() {
            @Override
            public void onPermissionDenied() {
                ModifyHeadpicResponseMethod modifyHeadpicSendResponseMethod =
                        ModifyHeadpicResponseMethod.getModifyHeadpicResponseMethod();
                modifyHeadpicSendResponseMethod.setResult(false);
                modifyHeadpicSendResponseMethod.setIsSend(false);
                modifyHeadpicSendResponseMethod.setErrorCode(JSErrorCode.UPDATE_HEAD_PIC_ERROR_NO_CAMERA_PERMISSION);
                callJs(modifyHeadpicSendResponseMethod);
            }
        });


    }


    @Override
    public void onJsRequestLocalPhoto() {
        Intent intent = PhotoUtils.requestLocalPhoto();
        jsStartActivityForResult(intent, LOCAL_PHOTO_CODE);
    }

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

            @Override
            public boolean needUISafety() {
                return false;
            }
        });

    }

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

                    @Override
                    public boolean needUISafety() {
                        return false;
                    }
                });
    }

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

            @Override
            public boolean needUISafety() {
                return false;
            }
        });

    }

    private static final int LOCAL_PHOTO_CODE = 0x0367;
    private static final int CROP_LOCAL_PHOTO = 0x0368;

    private static final int TAKE_PHOTO_CODE = 0x0369;
    private static final int CROP_TAKE_PHOTO = 0x036A;

    private static final int REQUEST_SCAN_QR_RESULT = 0x6532;

    private File localPhotoFile;

    private Uri takePhotoUri;
    private File takePhotoFile;

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SCAN_QR_RESULT) {
            onScanQRCodeResult(resultCode, data);
        } else if (requestCode >= LOCAL_PHOTO_CODE && requestCode <= CROP_TAKE_PHOTO) {
            onPhotoResult(requestCode, resultCode, data);
        }

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

            Uri imageUri = data.getData();
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

                @Override
                public boolean needUISafety() {
                    return false;
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
