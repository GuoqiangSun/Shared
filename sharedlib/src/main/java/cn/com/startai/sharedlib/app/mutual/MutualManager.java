package cn.com.startai.sharedlib.app.mutual;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Looper;
import android.provider.MediaStore;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;

import cn.com.startai.chargersdk.AOnChargerMessageArriveListener;
import cn.com.startai.chargersdk.ChargerBusiHandler;
import cn.com.startai.chargersdk.ChargerBusiManager;
import cn.com.startai.chargersdk.PersistentEventChargerDispatcher;
import cn.com.startai.chargersdk.entity.C_0x8301;
import cn.com.startai.chargersdk.entity.C_0x8302;
import cn.com.startai.chargersdk.entity.C_0x8304;
import cn.com.startai.chargersdk.entity.C_0x8305;
import cn.com.startai.chargersdk.entity.C_0x8306;
import cn.com.startai.fssdk.FSDownloadCallback;
import cn.com.startai.fssdk.FSUploadCallback;
import cn.com.startai.fssdk.StartaiDownloaderManager;
import cn.com.startai.fssdk.StartaiUploaderManager;
import cn.com.startai.fssdk.db.entity.DownloadBean;
import cn.com.startai.fssdk.db.entity.UploadBean;
import cn.com.startai.mqttsdk.StartAI;
import cn.com.startai.mqttsdk.base.StartaiError;
import cn.com.startai.mqttsdk.busi.entity.C_0x8016;
import cn.com.startai.mqttsdk.busi.entity.C_0x8018;
import cn.com.startai.mqttsdk.busi.entity.C_0x8020;
import cn.com.startai.mqttsdk.busi.entity.C_0x8021;
import cn.com.startai.mqttsdk.busi.entity.C_0x8024;
import cn.com.startai.mqttsdk.busi.entity.C_0x8025;
import cn.com.startai.mqttsdk.event.ICommonStateListener;
import cn.com.startai.mqttsdk.listener.IOnCallListener;
import cn.com.startai.mqttsdk.localbusi.UserBusi;
import cn.com.startai.mqttsdk.mqtt.MqttInitParam;
import cn.com.startai.mqttsdk.mqtt.request.MqttPublishRequest;
import cn.com.startai.scansdk.permission.PermissionHelper;
import cn.com.startai.sharedcharger.wxapi.WXLoginHelper;
import cn.com.startai.sharedlib.BuildConfig;
import cn.com.startai.sharedlib.R;
import cn.com.startai.sharedlib.app.Debuger;
import cn.com.startai.sharedlib.app.FileManager;
import cn.com.startai.sharedlib.app.LooperManager;
import cn.com.startai.sharedlib.app.info.ChargerDeveloperInfo;
import cn.com.startai.sharedlib.app.js.CommonJsInterfaceTask;
import cn.com.startai.sharedlib.app.js.Utils.JSErrorCode;
import cn.com.startai.sharedlib.app.js.Utils.Language;
import cn.com.startai.sharedlib.app.js.method2Impl.BorrowDeviceResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.DeviceInfoResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.GetAppVersionResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.GetIdentityCodeResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.GiveBackDeviceResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.IsLeastVersionResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.IsLoginResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.LanguageResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.LoginOutResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.MobileLoginByIDCodeResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.ModifyHeadpicSendResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.ModifyUserInfoResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.ModifyUserPwdResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.ScanORResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.UpdateProgressResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.UserInfoResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.WxLoginResponseMethod;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.DeviceInfoJsRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.GetIdentityCodeRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.LanguageSetRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.MobileLoginByIDCodeRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.ModifyNickNameRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.ModifyUserNameRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.ModifyUserPwdRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.UpgradeAppRequestBean;
import cn.com.startai.sharedlib.app.view.SharedApplication;
import cn.com.swain.baselib.app.IApp.IService;
import cn.com.swain.baselib.jsInterface.AbsJsInterface;
import cn.com.swain.baselib.jsInterface.bean.BaseCommonJsRequestBean;
import cn.com.swain.baselib.jsInterface.method.BaseResponseMethod;
import cn.com.swain.baselib.util.AppUtils;
import cn.com.swain.baselib.util.PhotoUtils;
import cn.com.swain169.log.Tlog;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/27 0027
 * desc :
 */
public class MutualManager implements IService {

    public static final String TAG = AbsJsInterface.TAG;

    private AbsJsInterface mJsInterface;

    public AbsJsInterface getJsInterface() {
        return mJsInterface;
    }

    private final Application app;
    private final IMutualCallBack mCallBack;

    public MutualManager(Application app, IMutualCallBack mCallBack) {
        this.app = app;
        this.mCallBack = mCallBack;
    }

    private String getUserID() {
//        if (Debuger.isDebug) {
//            return "3a5f4bafe52943e286c7ee7240d52a42";
//        }
        UserBusi userBusi = new UserBusi();
        C_0x8018.Resp.ContentBean currUser = userBusi.getCurrUser();
        if (currUser != null) {
            return currUser.getUserid();
        }
        return null;
    }

    @Override
    public void onSCreate() {

        Tlog.v(TAG, "mutualManager onSCreate() debug:" + BuildConfig.DEBUG);

        initMqtt();

        initJS();

    }

    @Override
    public void onSResume() {
        Tlog.v(TAG, "mutualManager onSResume() ");
    }

    @Override
    public void onSPause() {
        Tlog.v(TAG, "mutualManager onSPause() ");
    }

    @Override
    public void onSFinish() {
        Tlog.v(TAG, "mutualManager onSFinish() ");
        if (mJsInterface != null) {
            mJsInterface.release();
        }

    }

    @Override
    public void onSDestroy() {
        Tlog.v(TAG, "mutualManager onSDestroy() ");
        if (mJsInterface != null) {
            mJsInterface.release();
            mJsInterface = null;
        }
    }

    private void initJS() {

        Looper workLooper = LooperManager.getInstance().getWorkLooper();

        mJsInterface = new CommonJsInterfaceTask(workLooper, new CommonJsInterfaceTask.IJsRequestInterface() {

            @Override
            public void onJsBaseRequest(BaseCommonJsRequestBean mData) {

                if (Debuger.isLogDebug) {
                    Tlog.v(TAG, " onJsBaseRequest " + mData.toString());
                }

            }

            @Override
            public void onJsCrashError(BaseCommonJsRequestBean mData) {
                ((SharedApplication) app).onJsCrashError(mData.getRootJsonStr());
            }

            @Override
            public void onJsPressBack() {

            }

            @Override
            public void onJsPressBackFinish() {
                mCallBack.jFinish();
            }

            @Override
            public void onJsPressBackFinishBefore() {
                Toast.makeText(app, R.string.goBack_again, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onJsDataParseError(JSONException e, String jsonData) {

                if (Debuger.isLogDebug) {
                    Tlog.e(TAG, " jsonData: " + jsonData + "\n handleJsRequest parse json ", e);
                }

            }

            @Override
            public void onJsIsLogin() {

                Tlog.v(TAG, " loginUserID:" + getUserID());
                IsLoginResponseMethod mIsLoginResponseMethod =
                        IsLoginResponseMethod.getIsLoginResponseMethod();
                mIsLoginResponseMethod.setResult(true);
                mIsLoginResponseMethod.setIsLogin(getUserID() != null);
                callJs(mIsLoginResponseMethod);

            }

            @Override
            public void onJsWXLogin(BaseCommonJsRequestBean mData) {

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
                WXLoginHelper.getInstance().getWXApi(app).sendReq(req);

            }

            @Override
            public void onJsLoginOut() {
                ChargerBusiManager.getInstance().logout();
            }

            @Override
            public void onJsRequestScanQR() {

                mCallBack.scanQR(REQUEST_SCAN_QR_RESULT);

            }

            private final IOnCallListener mGetChargerInfoLsn = new IOnCallListener() {
                @Override
                public void onSuccess(MqttPublishRequest mqttPublishRequest) {
                    Tlog.i(TAG, "getChargerInfo msg send success");
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
                    Tlog.v(TAG, " borrow msg send success");
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

                ChargerBusiManager.getInstance().borrow(getUserID(), mData.getIMEI(), mBorrowLsn);
            }

            private final IOnCallListener mGiveBackDeviceLsn = new IOnCallListener() {
                @Override
                public void onSuccess(MqttPublishRequest mqttPublishRequest) {
                    Tlog.v(TAG, " giveBack msg send success");
                }

                @Override
                public void onFailed(MqttPublishRequest mqttPublishRequest, StartaiError startaiError) {
                    Tlog.e(TAG, " giveBack msg send fail " + String.valueOf(startaiError));
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
                ChargerBusiManager.getInstance().giveBack(getUserID(), mGiveBackBean.getIMEI(), mGiveBackDeviceLsn);
            }

            private final IOnCallListener mGetIdentityCodeLsn = new IOnCallListener() {
                @Override
                public void onSuccess(MqttPublishRequest request) {
                    Tlog.v(TAG, " get identity code msg send success");
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
                        1, mGetIdentityCodeLsn);
            }

            private final IOnCallListener mMobileLoginByIDLsn = new IOnCallListener() {
                @Override
                public void onSuccess(MqttPublishRequest request) {
                    Tlog.v(TAG, " login msg send success");
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
                    Tlog.v(TAG, "mGetVersionLsn msg send success ");
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

                Tlog.v(TAG, " onJSRequestUpgradeApp: downloadUrl:" + mUpgradeAppRequestBean.getPath());

                //示例代码
                DownloadBean downloadBean = new DownloadBean.Builder()
                        .url(mUpgradeAppRequestBean.getPath()) //需要下载的文件
//                .fileName(fileName) //文件保存名，选填
                        .build();

                getStartaiDownloaderManager().startDownload(downloadBean, mDownloadAppListener);

            }

            @Override
            public void onJSCancelUpgradeApp(UpgradeAppRequestBean mUpgradeAppRequestBean) {

                Tlog.v(TAG, " onJSRequestUpgradeApp:  downloadUrl:" + mUpgradeAppRequestBean.getPath());

                DownloadBean downloadBeanByUrl = StartaiDownloaderManager.getInstance().getFDBManager()
                        .getDownloadBeanByUrl(mUpgradeAppRequestBean.getPath());

                if (downloadBeanByUrl != null && downloadBeanByUrl.getStatus() == 2) {
                    // 已经下载成功 ，按了取消
                    Tlog.e(TAG, " user cancelUpdate but already download success ");
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
                    Tlog.v(TAG, " getUserInfo msg send success ");
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
                    ModifyHeadpicSendResponseMethod modifyHeadpicSendResponseMethod =
                            ModifyHeadpicSendResponseMethod.getModifyHeadpicSendResponseMethod();
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
                        mCallBack.jsStartActivityForResult(intent, TAKE_PHOTO_CODE);
                    }
                }, new PermissionHelper.OnPermissionDeniedListener() {
                    @Override
                    public void onPermissionDenied() {
                        ModifyHeadpicSendResponseMethod modifyHeadpicSendResponseMethod =
                                ModifyHeadpicSendResponseMethod.getModifyHeadpicSendResponseMethod();
                        modifyHeadpicSendResponseMethod.setResult(false);
                        modifyHeadpicSendResponseMethod.setIsSend(false);
                        modifyHeadpicSendResponseMethod.setErrorCode(JSErrorCode.UPDATE_HEAD_PIC_ERROR_NO_CAMERA_PERMISSION);
                        callJs(modifyHeadpicSendResponseMethod);
                    }
                });


            }


            @Override
            public void onJSRequestLocalPhoto() {

//                Intent intent = PhotoUtils.requestLocalPhoto();

                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        "image/*");

                mCallBack.jsStartActivityForResult(intent, LOCAL_PHOTO_CODE);

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

                contentBean.setUserid(getUserID());
                StartAI.getInstance().getBaseBusiManager().updateUserInfo(contentBean, new IOnCallListener() {
                    @Override
                    public void onSuccess(MqttPublishRequest request) {
                        Tlog.v(TAG, " updateUserName msg send success ");
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
                                Tlog.v(TAG, " updateUserPwd msg send success ");
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
                contentBean.setUserid(getUserID());
                contentBean.setNickName(mModifyNicknameBean.getNickname());
                StartAI.getInstance().getBaseBusiManager().updateUserInfo(contentBean, new IOnCallListener() {
                    @Override
                    public void onSuccess(MqttPublishRequest request) {
                        Tlog.v(TAG, " updateNickName msg send success ");
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
        });

    }

    private void initMqtt() {

        MqttInitParam initParam = new ChargerDeveloperInfo();

        StartAI.getInstance().initialization(app, initParam);
        StartAI.getInstance().getPersisitnet().setBusiHandler(new ChargerBusiHandler());
        StartAI.getInstance().getPersisitnet().setEventDispatcher(PersistentEventChargerDispatcher.getInstance());

        StartAI.getInstance().getPersisitnet().getEventDispatcher().registerOnTunnelStateListener(new ICommonStateListener() {
            @Override
            public void onTokenExpire(C_0x8018.Resp.ContentBean resp) {
                if (Debuger.isLogDebug) {
                    Tlog.e(TAG, "MQTT onConnectExpire :" + String.valueOf(resp));
                }
            }

            @Override
            public void onConnectFail(int errorCode, String errorMsg) {
                Tlog.e(TAG, " mqtt onConnectFail -->" + errorCode + " :" + errorMsg);
            }

            @Override
            public void onConnected() {
                Tlog.e(TAG, " mqtt onConnected ");
            }

            @Override
            public void onDisconnect(int errorCode, String errorMsg) {
                Tlog.e(TAG, " mqtt onDisconnect -->" + errorCode + " :" + errorMsg);
            }

            @Override
            public boolean needUISafety() {
                return false;
            }
        });

        StartAI.getInstance().getPersisitnet().getEventDispatcher().registerOnPushListener(new AOnChargerMessageArriveListener() {

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
            public void onBorrowResult(C_0x8301.Resp resp) {
                super.onBorrowResult(resp);
                if (Debuger.isLogDebug) {
                    Tlog.d(TAG, " onBorrowResult :" + String.valueOf(resp));
                }

                BorrowDeviceResponseMethod borrowDeviceResponseMethod =
                        BorrowDeviceResponseMethod.getBorrowDeviceResponseMethod();
                borrowDeviceResponseMethod.setResult(resp.getResult() == 1);
                C_0x8301.Resp.ContentBean content = resp.getContent();
                if (content != null) {
                    borrowDeviceResponseMethod.setErrorCode(content.getErrcode());
                    borrowDeviceResponseMethod.setIMEI(content.getImei());
                    borrowDeviceResponseMethod.setNO(content.getNo());
                    borrowDeviceResponseMethod.setLentTime(content.getLentTime());
                    borrowDeviceResponseMethod.setLentMerchantName(content.getLentMerchantName());
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

                if (content != null) {
                    giveBackDeviceResponseMethod.setErrorCode(content.getErrcode());
                    giveBackDeviceResponseMethod.setIMEI(content.getImei());
                    giveBackDeviceResponseMethod.setNO(content.getNo());
                    giveBackDeviceResponseMethod.setLentTime(content.getLentTime());
                    giveBackDeviceResponseMethod.setLentMerchantName(content.getLentMerchantName());

                    giveBackDeviceResponseMethod.setReturnTime(content.getReturnTime());
                    giveBackDeviceResponseMethod.setReturnMerchantName(content.getReturnMerchantName());
                    giveBackDeviceResponseMethod.setFee(content.getFee());

                }
                callJs(giveBackDeviceResponseMethod);

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

                    case 10:

                        WxLoginResponseMethod mWxResponseMethod =
                                WxLoginResponseMethod.getWxLoginResponseMethod();
                        mWxResponseMethod.releaseCache();
                        mWxResponseMethod.setResult(resp.getResult() == 1);
                        mWxResponseMethod.setErrorCode(loginInfo.getErrcode());
                        mWxResponseMethod.setName(loginInfo.getuName());
                        callJs(mWxResponseMethod);

                        break;
                }
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
            }

            @Override
            public void onGetStoreInfoResult(C_0x8304.Resp resp) {
                super.onGetStoreInfoResult(resp);
                if (Debuger.isLogDebug) {
                    Tlog.d(TAG, " onGetStoreInfoResult :" + String.valueOf(resp));
                }
            }

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
                        Context applicationContext = app.getApplicationContext();
                        PackageInfo packageInfo = applicationContext.getPackageManager()
                                .getPackageInfo(applicationContext.getPackageName(), 0);

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
                userInfoResponseMethod.setErrorCode(resp.getContent().getErrcode());

                C_0x8024.Resp.ContentBean contentBean = resp.getContent();
                if (resp.getResult() == 1) {
                    userInfoResponseMethod.setAddress(contentBean.getAddress());
                    userInfoResponseMethod.setBirthday(contentBean.getBirthday());
                    userInfoResponseMethod.setCity(contentBean.getCity());
                    userInfoResponseMethod.setFirstName(contentBean.getFirstName());
                    userInfoResponseMethod.setHeadPic(contentBean.getHeadPic());
                    userInfoResponseMethod.setLastName(contentBean.getLastName());
                    userInfoResponseMethod.setNickName(contentBean.getNickName());
                    userInfoResponseMethod.setProvince(contentBean.getProvince());
                    userInfoResponseMethod.setSex(contentBean.getSex());
                    userInfoResponseMethod.setTown(contentBean.getTown());
                    userInfoResponseMethod.setUserName(contentBean.getUserName());
                    userInfoResponseMethod.setIsHavePwd(contentBean.getIsHavePwd());
                    userInfoResponseMethod.setEmail(contentBean.getEmail());
                    userInfoResponseMethod.setMobile(contentBean.getMobile());
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
                userInfoResponseMethod.setErrorCode(resp.getContent().getErrcode());

                C_0x8020.Resp.ContentBean contentBean = resp.getContent();
                if (resp.getResult() == 1) {
                    userInfoResponseMethod.setAddress(contentBean.getAddress());
                    userInfoResponseMethod.setBirthday(contentBean.getBirthday());
                    userInfoResponseMethod.setCity(contentBean.getCity());
                    userInfoResponseMethod.setFirstName(contentBean.getFirstName());
                    userInfoResponseMethod.setHeadPic(contentBean.getHeadPic());
                    userInfoResponseMethod.setLastName(contentBean.getLastName());
                    userInfoResponseMethod.setNickName(contentBean.getNickName());
                    userInfoResponseMethod.setProvince(contentBean.getProvince());
                    userInfoResponseMethod.setSex(contentBean.getSex());
                    userInfoResponseMethod.setTown(contentBean.getTown());
                    userInfoResponseMethod.setUserName(contentBean.getUserName());
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
                modifyUserPwdResponseMethod.setErrorCode(resp.getContent().getErrcode());
                modifyUserPwdResponseMethod.setIsSuccessfully(resp.getResult() == 1);
                callJs(modifyUserPwdResponseMethod);

            }
        });

    }

    private void callJs(BaseResponseMethod mBaseResponseMethod) {
        mCallBack.callJs(mBaseResponseMethod.toMethod());
    }

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

        ChargerBusiManager.getInstance().loginWithThirdAccount(10, code, new IOnCallListener() {
            @Override
            public void onSuccess(MqttPublishRequest mqttPublishRequest) {
                Tlog.i(TAG, "loginWithThirdAccount msg send success ");
            }

            @Override
            public void onFailed(MqttPublishRequest mqttPublishRequest, StartaiError startaiError) {
                if (Debuger.isDebug) {
                    Tlog.e(TAG, "loginWithThirdAccount msg send fail " + String.valueOf(startaiError));
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

            ModifyHeadpicSendResponseMethod modifyHeadpicSendResponseMethod =
                    ModifyHeadpicSendResponseMethod.getModifyHeadpicSendResponseMethod();
            modifyHeadpicSendResponseMethod.setResult(false);
            modifyHeadpicSendResponseMethod.setIsSend(false);
            modifyHeadpicSendResponseMethod.setErrorCode(JSErrorCode.UPDATE_HEAD_PIC_CANCEL);
            callJs(modifyHeadpicSendResponseMethod);

            return;
        } else if (resultCode == Activity.RESULT_FIRST_USER) {

            ModifyHeadpicSendResponseMethod modifyHeadpicSendResponseMethod =
                    ModifyHeadpicSendResponseMethod.getModifyHeadpicSendResponseMethod();
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


    private volatile boolean downloadInit = false;

    private StartaiDownloaderManager getStartaiDownloaderManager() {
        if (!downloadInit) {
            downloadInit = true;
            StartaiDownloaderManager.getInstance().init(app, null);
        }
        return StartaiDownloaderManager.getInstance();
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

            ModifyHeadpicSendResponseMethod modifyHeadpicSendResponseMethod =
                    ModifyHeadpicSendResponseMethod.getModifyHeadpicSendResponseMethod();
            modifyHeadpicSendResponseMethod.setResult(false);
            modifyHeadpicSendResponseMethod.setIsSend(false);
            modifyHeadpicSendResponseMethod.setErrorCode(JSErrorCode.UPDATE_HEAD_PIC_ERROR_NO_LOCAL_PERMISSION);
            callJs(modifyHeadpicSendResponseMethod);

            return null;
        }

        Uri outUri = Uri.fromFile(path);
        Intent intent = PhotoUtils.cropImg(imageUri, outUri);
        // 启动裁剪程序
        mCallBack.jsStartActivityForResult(intent, code);

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


    private final FSUploadCallback mLogoUploadCallBack = new FSUploadCallback() {
        @Override
        public void onStart(UploadBean uploadBean) {
            Tlog.v(TAG, " mLogoUploadCallBack onStart " + uploadBean.toString());

        }

        @Override
        public void onSuccess(UploadBean uploadBean) {
            if (Debuger.isLogDebug) {
                Tlog.v(TAG, " mLogoUploadCallBack onSuccess " + uploadBean.toString());
            }

            C_0x8020.Req.ContentBean contentBean = new C_0x8020.Req.ContentBean();
            contentBean.setHeadPic(uploadBean.getHttpDownloadUrl());
            contentBean.setUserid(getUserID());
            StartAI.getInstance().getBaseBusiManager().updateUserInfo(contentBean, new IOnCallListener() {
                @Override
                public void onSuccess(MqttPublishRequest mqttPublishRequest) {
                    Tlog.v(TAG, " updateUserInfo msg send success ");

                }

                @Override
                public void onFailed(MqttPublishRequest mqttPublishRequest, StartaiError startaiError) {
                    Tlog.e(TAG, " updateUserInfo msg send fail " + startaiError.getErrorCode());

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

            String[] aar = getStoreIdAndChargerId(scanResult);
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

    private java.lang.String QR_CODE_INDEX = "http://www.ruioo.net/merchant/detail/";

    /**
     * 检查 是否是sn的二码维码
     */
    private String[] getStoreIdAndChargerId(String result) {

        if (result.startsWith(QR_CODE_INDEX)) {
            String[] split = result.replace(QR_CODE_INDEX, "").split("/");

            if (split.length == 2) {
                return split;
            }
        }
        return null;

    }

}
