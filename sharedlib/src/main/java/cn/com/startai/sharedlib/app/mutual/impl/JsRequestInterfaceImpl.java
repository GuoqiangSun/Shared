package cn.com.startai.sharedlib.app.mutual.impl;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelmsg.SendAuth;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;

import cn.com.startai.chargersdk.ChargerBusiManager;
import cn.com.startai.chargersdk.entity.C_0x8309;
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
import cn.com.startai.mqttsdk.busi.entity.type.Type;
import cn.com.startai.mqttsdk.listener.IOnCallListener;
import cn.com.startai.mqttsdk.mqtt.request.MqttPublishRequest;
import cn.com.startai.scansdk.permission.PermissionHelper;
import cn.com.startai.sharedcharger.wxapi.WXApiHelper;
import cn.com.startai.sharedlib.R;
import cn.com.startai.sharedlib.app.Debuger;
import cn.com.startai.sharedlib.app.FileManager;
import cn.com.startai.sharedlib.app.js.CommonJsInterfaceTask;
import cn.com.startai.sharedlib.app.js.Utils.JSErrorCode;
import cn.com.startai.sharedlib.app.js.Utils.Language;
import cn.com.startai.sharedlib.app.js.method2Impl.AliLoginResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.BalanceDepositResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.BalancePayResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.BorrowDeviceResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.DeviceInfoResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.GetAppVersionResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.GetIdentityCodeResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.GiveBackDeviceResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.IsLeastVersionResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.IsLoginResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.LanguageResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.MobileLoginByIDCodeResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.ModifyHeadpicResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.ModifyUserInfoResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.ModifyUserPwdResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.OrderDetailResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.OrderListResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.ScanORResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.ThirdPayResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.UpdateProgressResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.UserInfoResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.WxLoginResponseMethod;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.BalancePayRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.DeviceInfoJsRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.GetIdentityCodeRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.LanguageSetRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.MobileLoginByIDCodeRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.ModifyNickNameRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.ModifyUserNameRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.ModifyUserPwdRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.OrderDetailRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.OrderListRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.ThirdPayRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.UpgradeAppRequestBean;
import cn.com.startai.sharedlib.app.mutual.IMutualCallBack;
import cn.com.startai.sharedlib.app.mutual.MutualManager;
import cn.com.startai.sharedlib.app.mutual.utils.RuiooORCodeUtils;
import cn.com.startai.sharedlib.app.view.SharedApplication;
import cn.com.swain.baselib.jsInterface.bean.BaseCommonJsRequestBean;
import cn.com.swain.baselib.jsInterface.method.BaseResponseMethod;
import cn.com.swain.baselib.util.AppUtils;
import cn.com.swain.baselib.util.PhotoUtils;
import cn.com.swain169.log.Tlog;

/**
 * author: Guoqiang_Sun
 * date: 2018/12/25 0025
 * Desc:
 */
public class JsRequestInterfaceImpl implements CommonJsInterfaceTask.IJsRequestInterface {

    private String TAG = MutualManager.TAG;

    private final Application app;
    private final IMutualCallBack mCallBack;
    private final MutualManager mMutualManager;

    public JsRequestInterfaceImpl(Application app, IMutualCallBack mCallBack, MutualManager mMutualManager) {
        this.app = app;
        this.mCallBack = mCallBack;
        this.mMutualManager = mMutualManager;
    }


    private void callJs(BaseResponseMethod mBaseResponseMethod) {
        if (mCallBack != null) {
            mCallBack.callJs(mBaseResponseMethod.toMethod());
        } else {
            Tlog.e(TAG, " JsRequestInterfaceImpl callJs mCallBack=null " + mBaseResponseMethod.toMethod());
        }
    }

    @Override
    public void onJsBaseRequest(BaseCommonJsRequestBean mData) {

        if (Debuger.isLogDebug) {
            Tlog.v(TAG, " onJsBaseRequest " + String.valueOf(mData));
        }

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
        if (mCallBack != null) {
            mCallBack.jFinish();
        }
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
        String userIDFromMq = mMutualManager.getUserIDFromMq();
        mMutualManager.setUserID(userIDFromMq);
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
        req.state = "diandi_wx_login";
        //向微信发送请求
        WXApiHelper.getInstance().getWXApi(app).sendReq(req);

    }

    @Override
    public void onJsAliLogin() {

        ChargerBusiManager.getInstance().getAlipayAuthInfo(new IOnCallListener() {
            @Override
            public void onSuccess(MqttPublishRequest request) {
                if (Debuger.isLogDebug) {
                    Tlog.v(TAG, "getAlipayAuthInfo msg send success ");
                }
            }

            @Override
            public void onFailed(MqttPublishRequest request, StartaiError startaiError) {

                if (Debuger.isLogDebug) {
                    Tlog.e(TAG, "getAlipayAuthInfo msg send fail " + String.valueOf(startaiError));
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

    private final IOnCallListener mOrderLstLsn = new IOnCallListener() {
        @Override
        public void onSuccess(MqttPublishRequest request) {
            if (Debuger.isLogDebug) {
                Tlog.v(TAG, "getOrderList msg send success ");
            }
        }

        @Override
        public void onFailed(MqttPublishRequest request, StartaiError startaiError) {
            if (Debuger.isLogDebug) {
                Tlog.e(TAG, "getOrderList msg send fail " + String.valueOf(startaiError));
            }

            OrderListResponseMethod orderListResponseMethod = OrderListResponseMethod.getOrderListResponseMethod();
            orderListResponseMethod.setResult(false);
            orderListResponseMethod.setErrorCode(String.valueOf(startaiError.getErrorCode()));
            callJs(orderListResponseMethod);

        }

        @Override
        public boolean needUISafety() {
            return false;
        }
    };

    @Override
    public void onJSRequestOrderLst(OrderListRequestBean mOrderLstBean) {

        C_0x8309.Req.ContentBean req = new C_0x8309.Req.ContentBean(mOrderLstBean.getPage(),
                mOrderLstBean.getCount(), mOrderLstBean.getType());

        ChargerBusiManager.getInstance().getOrderList(req, mOrderLstLsn);
    }

    private final IOnCallListener mBalancePayLsn = new IOnCallListener() {
        @Override
        public void onSuccess(MqttPublishRequest request) {
            if (Debuger.isLogDebug) {
                Tlog.v(TAG, "BalancePay msg send success ");
            }
        }

        @Override
        public void onFailed(MqttPublishRequest request, StartaiError startaiError) {
            if (Debuger.isLogDebug) {
                Tlog.e(TAG, "BalancePay msg send fail " + String.valueOf(startaiError));
            }

            BalancePayResponseMethod balancePayResponseMethod = BalancePayResponseMethod.getBalancePayResponseMethod();
            balancePayResponseMethod.setResult(false);
            balancePayResponseMethod.setErrorCode(String.valueOf(startaiError.getErrorCode()));
            callJs(balancePayResponseMethod);

        }

        @Override
        public boolean needUISafety() {
            return false;
        }
    };

    @Override
    public void onJSBalancePay(BalancePayRequestBean mPayBean) {
        ChargerBusiManager.getInstance().payForOrderWithBalance(mPayBean.getNo(), mBalancePayLsn);
    }

    private final IOnCallListener mThirdPayLsn = new IOnCallListener() {
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
    public void onJSThirdPay(ThirdPayRequestBean mWXPayBean) {
        C_0x8028.Req.ContentBean req = new C_0x8028.Req.ContentBean(
                Type.ThirdPayment.TYPE_ORDER,
                mWXPayBean.getPlatform(),
                mWXPayBean.getNo(),
                mWXPayBean.getDescription(),
                mWXPayBean.getCurrency(),
                String.valueOf(mWXPayBean.getFee()));
        ChargerBusiManager.getInstance().thirdPaymentUnifiedOrder(req, mThirdPayLsn);
    }

    private final IOnCallListener mOrderDetailLsn = new IOnCallListener() {
        @Override
        public void onSuccess(MqttPublishRequest request) {
            if (Debuger.isLogDebug) {
                Tlog.v(TAG, "orderDetail msg send success");
            }
        }

        @Override
        public void onFailed(MqttPublishRequest request, StartaiError startaiError) {
            if (Debuger.isLogDebug) {
                Tlog.v(TAG, "orderDetail msg send fail " + String.valueOf(startaiError));
            }

            OrderDetailResponseMethod orderDetailResponseMethod = OrderDetailResponseMethod.getOrderDetailResponseMethod();
            orderDetailResponseMethod.setResult(false);
            orderDetailResponseMethod.setErrorCode(String.valueOf(startaiError.getErrorCode()));
            callJs(orderDetailResponseMethod);
        }

        @Override
        public boolean needUISafety() {
            return false;
        }
    };

    @Override
    public void onJSOrderDetail(OrderDetailRequestBean mOrderDetailBean) {
        ChargerBusiManager.getInstance().getOrderDetail(mOrderDetailBean.getNo(), mOrderDetailLsn);
    }

    private final IOnCallListener mBalanceDepositLsn = new IOnCallListener() {
        @Override
        public void onSuccess(MqttPublishRequest request) {
            if (Debuger.isLogDebug) {
                Tlog.v(TAG, "getBalanceAndDeposit msg send success");
            }
        }

        @Override
        public void onFailed(MqttPublishRequest request, StartaiError startaiError) {
            if (Debuger.isLogDebug) {
                Tlog.v(TAG, "getBalanceAndDeposit msg send fail " + String.valueOf(startaiError));
            }

            BalanceDepositResponseMethod mBalanceDepositResponseMethod =
                    BalanceDepositResponseMethod.getBalanceDepositResponseMethod();
            mBalanceDepositResponseMethod.setResult(false);
            mBalanceDepositResponseMethod.setErrorCode(String.valueOf(startaiError.getErrorCode()));
            callJs(mBalanceDepositResponseMethod);

        }

        @Override
        public boolean needUISafety() {
            return false;
        }
    };


    @Override
    public void onJSRequestBalanceDeposit() {
        ChargerBusiManager.getInstance().getBalanceAndDeposit(mMutualManager.getUserID(), mBalanceDepositLsn);
    }

    @Override
    public void onJsLoginOut() {
        ChargerBusiManager.getInstance().logout();
    }

    @Override
    public void onJsRequestScanQR() {
        if (mCallBack != null) {
            mCallBack.scanQR(REQUEST_SCAN_QR_RESULT);
        }

    }

    private final IOnCallListener mGetChargerInfoLsn = new IOnCallListener() {
        @Override
        public void onSuccess(MqttPublishRequest mqttPublishRequest) {
            if (Debuger.isLogDebug) {
                Tlog.v(TAG, "getChargerInfo msg send success");
            }
        }

        @Override
        public void onFailed(MqttPublishRequest mqttPublishRequest, StartaiError startaiError) {
            if (Debuger.isLogDebug) {
                Tlog.e(TAG, "getChargerInfo msg send fail " + String.valueOf(startaiError));
            }

            DeviceInfoResponseMethod deviceInfoResponseMethod =
                    DeviceInfoResponseMethod.getDeviceInfoResponseMethod();
            deviceInfoResponseMethod.setResult(false);
            deviceInfoResponseMethod.setErrorCode(String.valueOf(startaiError.getErrorCode()));
            callJs(deviceInfoResponseMethod);

        }

        @Override
        public boolean needUISafety() {
            return false;
        }
    };

    @Override
    public void onJsRequestDeviceInfo(DeviceInfoJsRequestBean mData) {

        ChargerBusiManager.getInstance().getChargerInfo(mData.getIMEI(), mGetChargerInfoLsn);

    }

    private final IOnCallListener mBorrowLsn = new IOnCallListener() {
        @Override
        public void onSuccess(MqttPublishRequest mqttPublishRequest) {
            if (Debuger.isLogDebug) {
                Tlog.v(TAG, " borrow msg send success");
            }
        }

        @Override
        public void onFailed(MqttPublishRequest mqttPublishRequest, StartaiError startaiError) {
            if (Debuger.isLogDebug) {
                Tlog.e(TAG, " borrow msg send fail " + String.valueOf(startaiError));
            }

            BorrowDeviceResponseMethod borrowDeviceResponseMethod =
                    BorrowDeviceResponseMethod.getBorrowDeviceResponseMethod();
            borrowDeviceResponseMethod.setResult(false);
            borrowDeviceResponseMethod.setErrorCode(String.valueOf(startaiError.getErrorCode()));
            callJs(borrowDeviceResponseMethod);

        }

        @Override
        public boolean needUISafety() {
            return false;
        }
    };

    @Override
    public void onJsBorrowDevice(DeviceInfoJsRequestBean mData) {

        ChargerBusiManager.getInstance().borrow(mMutualManager.getUserID(), mData.getIMEI(), mBorrowLsn);

    }

    private final IOnCallListener mGiveBackDeviceLsn = new IOnCallListener() {
        @Override
        public void onSuccess(MqttPublishRequest mqttPublishRequest) {
            if (Debuger.isLogDebug) {
                Tlog.v(TAG, " giveBack msg send success");
            }
        }

        @Override
        public void onFailed(MqttPublishRequest mqttPublishRequest, StartaiError startaiError) {
            if (Debuger.isLogDebug) {
                Tlog.e(TAG, " giveBack msg send fail " + String.valueOf(startaiError));
            }
            GiveBackDeviceResponseMethod giveBackDeviceResponseMethod =
                    GiveBackDeviceResponseMethod.getGiveBackDeviceResponseMethod();
            giveBackDeviceResponseMethod.setResult(false);
            giveBackDeviceResponseMethod.setErrorCode(String.valueOf(startaiError.getErrorCode()));
            callJs(giveBackDeviceResponseMethod);
        }

        @Override
        public boolean needUISafety() {
            return false;
        }
    };

    @Override
    public void onJsGiveBackDevice(DeviceInfoJsRequestBean mGiveBackBean) {
        ChargerBusiManager.getInstance().giveBack(mMutualManager.getUserID(), mGiveBackBean.getIMEI(), mGiveBackDeviceLsn);
    }

    private final IOnCallListener mGetIdentityCodeLsn = new IOnCallListener() {
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
        ChargerBusiManager.getInstance().getIdentifyCode(mGetIDCodeBean.getPhone(),
                Type.GetIdentifyCode.LOGIN, mGetIdentityCodeLsn);
    }

    private final IOnCallListener mMobileLoginByIDLsn = new IOnCallListener() {
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
        ChargerBusiManager.getInstance().login(mMobileLoginByIDBean.getPhone(), "",
                mMobileLoginByIDBean.getCode(), mMobileLoginByIDLsn);
    }

    @Override
    public void onJSQuerySystemLanguage() {
        String type = Language.changeSystemLangToH5Lang(app);
        LanguageResponseMethod languageResponseMethod =
                LanguageResponseMethod.getLanguageResponseMethod();
        languageResponseMethod.setResult(true);
        languageResponseMethod.setLan(type);
        callJs(languageResponseMethod);
    }

    @Override
    public void onJSSetSystemLanguage(LanguageSetRequestBean mLanguageSetRequestBean) {

        Language.saveLanguage(app, mLanguageSetRequestBean.getLan());
        Language.changeLanguage(app);

        String type = Language.changeSystemLangToH5Lang(app);
        LanguageResponseMethod languageResponseMethod =
                LanguageResponseMethod.getLanguageResponseMethod();
        languageResponseMethod.setResult(true);
        languageResponseMethod.setLan(type);
        callJs(languageResponseMethod);
    }

    private final IOnCallListener mGetVersionLsn = new IOnCallListener() {
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
    public void onJSCheckAppIsNew() {

        final String os = "android";
        String packageName = app.getApplicationContext().getPackageName();
        Tlog.v(TAG, "checkIsLatestVersion() :" + packageName);
        ChargerBusiManager.getInstance().getLatestVersion(os, packageName, mGetVersionLsn);

    }


    private final FSDownloadCallback mDownloadAppListener = new FSDownloadCallback() {
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
    public void onJSRequestUpgradeApp(UpgradeAppRequestBean mUpgradeAppRequestBean) {
        if (Debuger.isLogDebug) {
            Tlog.v(TAG, " onJSRequestUpgradeApp: downloadUrl:" + mUpgradeAppRequestBean.getPath());
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
    public void onJSCancelUpgradeApp(UpgradeAppRequestBean mUpgradeAppRequestBean) {
        if (Debuger.isLogDebug) {
            Tlog.v(TAG, " onJSRequestUpgradeApp:  downloadUrl:" + mUpgradeAppRequestBean.getPath());
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
    public void onJSRequestAppVersion() {
        GetAppVersionResponseMethod appVersionResponseMethod =
                GetAppVersionResponseMethod.getAppVersionResponseMethod();
        appVersionResponseMethod.setResult(true);
        appVersionResponseMethod.setVersion(com.blankj.utilcode.util.AppUtils.getAppVersionName());
        callJs(appVersionResponseMethod);
    }


    private final IOnCallListener mGetUserInfoLsn = new IOnCallListener() {

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
    public void onJSRequestUserInfo() {
        ChargerBusiManager.getInstance().getUserInfo(mGetUserInfoLsn);
    }

    @Override
    public void onJSRequestTakePhoto() {

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
                if (mCallBack != null) {
                    mCallBack.jsStartActivityForResult(intent, TAKE_PHOTO_CODE);
                }
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
    public void onJSRequestLocalPhoto() {
        if (mCallBack != null) {
            Intent intent = PhotoUtils.requestLocalPhoto();
            mCallBack.jsStartActivityForResult(intent, LOCAL_PHOTO_CODE);
        }

    }

    @Override
    public void onJSModifyUserName(ModifyUserNameRequestBean mModifyNameBean) {


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

        contentBean.setUserid(mMutualManager.getUserID());
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
    public void onJSModifyUserPwd(ModifyUserPwdRequestBean mModifyPwdBean) {
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
    public void onJSModifyNickName(ModifyNickNameRequestBean mModifyNicknameBean) {
        C_0x8020.Req.ContentBean contentBean = new C_0x8020.Req.ContentBean();
        contentBean.setUserid(mMutualManager.getUserID());
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


        if (mCallBack != null) {
            Uri outUri = Uri.fromFile(path);
            Intent intent = PhotoUtils.cropImg(imageUri, outUri);
            // 启动裁剪程序
            mCallBack.jsStartActivityForResult(intent, code);
            return path;
        }
        return null;
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


    private final FSUploadCallback mLogoUploadCallBack = new FSUploadCallback() {
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

            C_0x8020.Req.ContentBean contentBean = new C_0x8020.Req.ContentBean();
            contentBean.setHeadPic(uploadBean.getHttpDownloadUrl());
            contentBean.setUserid(mMutualManager.getUserID());
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
                Tlog.v(TAG, " mLogoUploadCallBack onFailure " + i + String.valueOf(uploadBean));
            }

        }

        @Override
        public void onProgress(UploadBean uploadBean) {
            if (Debuger.isLogDebug) {
                Tlog.v(TAG, " mLogoUploadCallBack onProgress " + uploadBean.getProgress());
            }
        }

        @Override
        public void onWaiting(UploadBean uploadBean) {
            if (Debuger.isLogDebug) {
                Tlog.v(TAG, " mLogoUploadCallBack onWaiting " + String.valueOf(uploadBean));
            }
        }

        @Override
        public void onPause(UploadBean uploadBean) {
            if (Debuger.isLogDebug) {
                Tlog.v(TAG, " mLogoUploadCallBack onPause " + String.valueOf(uploadBean));
            }
        }
    };


    private void onScanQRCodeResult(int resultCode, Intent data) {

        ScanORResponseMethod scanORResponseMethod = ScanORResponseMethod.getScanORResponseMethod();
        if (resultCode == Activity.RESULT_OK) {

            String scanResult = data.getStringExtra("result");
            Tlog.i(TAG, "scanResult = " + scanResult);

            String[] aar = RuiooORCodeUtils.getStoreIdAndChargerId(scanResult);
            if (aar == null || aar.length < 2) {

                Tlog.e(TAG, " QR parse fail");
                scanORResponseMethod.setResult(false);
                scanORResponseMethod.setErrorCode(JSErrorCode.ERROR_CODE_ERROR_URL);

            } else {
                String storeId = aar[0];
                String chargerId = aar[1];
                scanORResponseMethod.setResult(true);
                scanORResponseMethod.setIMEI(chargerId);
                scanORResponseMethod.setMerchantId(storeId);
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
