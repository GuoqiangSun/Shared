package cn.com.startai.sharedlib.app.js;

import android.os.Looper;

import org.json.JSONException;

import cn.com.shared.weblib.js.XWalkCommonJsRequest;
import cn.com.startai.sharedlib.app.global.Debuger;
import cn.com.startai.sharedlib.app.js.Utils.JsMsgType;
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
import cn.com.swain.baselib.jsInterface.request.AbsCommonJsInterfaceProxy;
import cn.com.swain.baselib.jsInterface.request.bean.BaseCommonJsRequestBean;
import cn.com.swain.baselib.log.Tlog;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/27 0027
 * desc :
 */
public class CommonJsInterfaceTask extends AbsCommonJsInterfaceProxy {

    private final ICommonJsRequestInterface mCallBack;

    public CommonJsInterfaceTask(Looper mLooper, ICommonJsRequestInterface mCallBack) {
        super("XWalkCommonJsInterfaceProxy");
        final XWalkCommonJsRequest commonJsRequest = new XWalkCommonJsRequest(mLooper, this);
        attachAbsJsInterface(commonJsRequest);
        this.mCallBack = mCallBack;
    }

    @Override
    protected void onJsRequest(BaseCommonJsRequestBean mData) {

        if (Debuger.isLogDebug) {
            Tlog.i(TAG, " onJsRequest:" + String.valueOf(mData));
        }

        switch (mData.getMsgType()) {
            case JsMsgType.TYPE_REQUEST_BACK:

                mCallBack.onJsPressBack();

                break;

            case JsMsgType.TYPE_REQUEST_IS_LOGIN:

                mCallBack.onJsIsLogin();

                break;

            case JsMsgType.TYPE_REQUEST_WX_LOGIN:

                mCallBack.onJsWXLogin();

                break;

            case JsMsgType.TYPE_REQUEST_ALI_LOGIN:

                mCallBack.onJsAliLogin();

                break;
            case JsMsgType.TYPE_REQUEST_THIRD_LOGIN:

                ThirdLoginRequestBean mThirdLoginRequestBean = new ThirdLoginRequestBean(mData);
                mCallBack.onJsThirdLogin(mThirdLoginRequestBean);

                break;

            case JsMsgType.TYPE_REQUEST_THIRD_BIND:

                ThirdBindRequestBean mThirdBindRequestBean = new ThirdBindRequestBean(mData);
                mCallBack.onJsThirdBind(mThirdBindRequestBean);

                break;

            case JsMsgType.TYPE_REQUEST_WX_BIND:

                mCallBack.onJsWXBind();

                break;

            case JsMsgType.TYPE_REQUEST_ALI_BIND:

                mCallBack.onJsAliBind();

                break;

            case JsMsgType.TYPE_REQUEST_UNBIND_WX:

                mCallBack.onJsWXUnBind();

                break;

            case JsMsgType.TYPE_REQUEST_UNBIND_ALI:

                mCallBack.onJsAliUnBind();

                break;

            case JsMsgType.TYPE_REQUEST_THIRD_UNBIND:

                ThirdUnBindRequestBean mThirdUnBindRequestBean = new ThirdUnBindRequestBean(mData);
                mCallBack.onJsThirdUnbind(mThirdUnBindRequestBean);

                break;

            case JsMsgType.TYPE_REQUEST_CALL_PHONE:

                CallPhoneRequestBean callPhoneRequestBean = new CallPhoneRequestBean(mData);
                mCallBack.onJsCallPhone(callPhoneRequestBean);

                break;

            case JsMsgType.TYPE_REQUEST_IS_INSTALL:

                AppInstallRequestBean appInstallRequestBean = new AppInstallRequestBean(mData);
                mCallBack.onJsAppIsInstall(appInstallRequestBean);

                break;

            case JsMsgType.TYPE_REQUEST_LOGIN_OUT:

                mCallBack.onJsLogOut();

                break;

            case JsMsgType.TYPE_REQUEST_JS_ERROR:

                mCallBack.onJsCrashError(mData);

                break;

            case JsMsgType.TYPE_REQUEST_SCAN_OR:

                mCallBack.onJsRequestScanQR();

                break;


            case JsMsgType.TYPE_REQUEST_GET_IDENTITY_CODE:

                GetIdentityCodeRequestBean mGetIDCodeBean = new GetIdentityCodeRequestBean(mData);
                mCallBack.onJsGetIdentityCode(mGetIDCodeBean);

                break;

            case JsMsgType.TYPE_REQUEST_MOBILE_LOGIN_BY_IDCODE:

                MobileLoginByIDCodeRequestBean mMobileLoginByIDBean = new MobileLoginByIDCodeRequestBean(mData);
                mCallBack.onJsMobileLoginByIdentityCode(mMobileLoginByIDBean);

                break;

            case JsMsgType.TYPE_REQUEST_QUERY_SYSTEM_LANGUAGE:

                mCallBack.onJsQuerySystemLanguage();

                break;

            case JsMsgType.TYPE_REQUEST_SET_SYSTEM_LANGUAGE:

                LanguageSetRequestBean mLanguageSetRequestBean = new LanguageSetRequestBean(mData);
                mCallBack.onJsSetSystemLanguage(mLanguageSetRequestBean);

                break;

            case JsMsgType.TYPE_REQUEST_IS_NEWVERSION:

                mCallBack.onJsCheckAppIsNew();

                break;

            case JsMsgType.TYPE_REQUEST_APP_UPGRADE:

                UpgradeAppRequestBean mUpgradeAppRequestBean = new UpgradeAppRequestBean(mData);
                mCallBack.onJsRequestUpgradeApp(mUpgradeAppRequestBean);

                break;

            case JsMsgType.TYPE_REQUEST_CANCEL_APP_UPGRADE:

                UpgradeAppRequestBean mCancelUpgradeAppRequestBean = new UpgradeAppRequestBean(mData);
                mCallBack.onJsCancelUpgradeApp(mCancelUpgradeAppRequestBean);

                break;
            case JsMsgType.TYPE_REQUEST_APP_VERSION:

                mCallBack.onJsRequestAppVersion();

                break;

            case JsMsgType.TYPE_REQUEST_USER_INFO:

                mCallBack.onJsRequestUserInfo();

                break;
            case JsMsgType.TYPE_REQUEST_TAKE_PHOTO:

                mCallBack.onJsRequestTakePhoto();

                break;
            case JsMsgType.TYPE_REQUEST_LOCAL_PHOTO:

                mCallBack.onJsRequestLocalPhoto();

                break;
            case JsMsgType.TYPE_REQUEST_MODIFY_USERNAME:

                ModifyUserNameRequestBean mModifyNameBean = new ModifyUserNameRequestBean(mData);
                mCallBack.onJsModifyUserName(mModifyNameBean);

                break;
            case JsMsgType.TYPE_REQUEST_MODIFY_NICKNAME:
                ModifyNickNameRequestBean mModifyNicknameBean = new ModifyNickNameRequestBean(mData);
                mCallBack.onJsModifyNickName(mModifyNicknameBean);

                break;
            case JsMsgType.TYPE_REQUEST_MODIFY_USERPWD:

                ModifyUserPwdRequestBean mModifyPwdBean = new ModifyUserPwdRequestBean(mData);
                mCallBack.onJsModifyUserPwd(mModifyPwdBean);
                break;

            case JsMsgType.TYPE_REQUEST_BIND_PHONE:

                BindPhoneRequestBean mBindPhoneBean = new BindPhoneRequestBean(mData);
                mCallBack.onJsBindPhone(mBindPhoneBean);

                break;

            case JsMsgType.TYPE_REQUEST_THIRD_PAY_ORDER:

                ThirdPayOrderRequestBean mThirdPayBean = new ThirdPayOrderRequestBean(mData);
                mCallBack.onJsThirdPayOrder(mThirdPayBean);

                break;

            case JsMsgType.TYPE_REQUEST_MAP_NAV:

                MapNavRequestBean mapNavRequestBean = new MapNavRequestBean(mData);
                mCallBack.onJsMapNav(mapNavRequestBean);

                break;

            default:

                onJsUnknownRequest(mData);

                break;
        }

    }

    protected void onJsUnknownRequest(BaseCommonJsRequestBean mData) {
    }

    @Override
    protected void onJsDataParseError(JSONException e, String jsonData) {
        mCallBack.onJsDataParseError(e, jsonData);
    }


    @Override
    protected void onJsPressBackFinish() {

        mCallBack.onJsPressBackFinish();

    }

    @Override
    protected void onJsPressBackFinishBefore() {
        mCallBack.onJsPressBackFinishBefore();
    }


    public interface ICommonJsRequestInterface {

        void onJsPressBackFinish();

        void onJsPressBackFinishBefore();

        void onJsPressBack();

        void onJsDataParseError(JSONException e, String jsonData);

        void onJsIsLogin();

        void onJsWXLogin();

        void onJsLogOut();

        void onJsCrashError(BaseCommonJsRequestBean mBaseData);

        void onJsRequestScanQR();

        void onJsGetIdentityCode(GetIdentityCodeRequestBean mGetIDCodeBean);

        void onJsMobileLoginByIdentityCode(MobileLoginByIDCodeRequestBean mMobileLoginByIDBean);

        void onJsQuerySystemLanguage();

        void onJsSetSystemLanguage(LanguageSetRequestBean mLanguageSetRequestBean);

        void onJsCheckAppIsNew();

        void onJsRequestUpgradeApp(UpgradeAppRequestBean mUpgradeAppRequestBean);

        void onJsCancelUpgradeApp(UpgradeAppRequestBean mUpgradeAppRequestBean);

        void onJsRequestAppVersion();

        void onJsRequestUserInfo();

        void onJsRequestTakePhoto();

        void onJsRequestLocalPhoto();

        void onJsModifyUserName(ModifyUserNameRequestBean mModifyNameBean);

        void onJsModifyUserPwd(ModifyUserPwdRequestBean mModifyPwdBean);

        void onJsModifyNickName(ModifyNickNameRequestBean mModifyNicknameBean);

        void onJsAliLogin();

        void onJsThirdPayOrder(ThirdPayOrderRequestBean mThirdPayBean);

        void onJsWXBind();

        void onJsAliBind();

        void onJsBindPhone(BindPhoneRequestBean mBindPhoneBean);

        void onJsWXUnBind();

        void onJsAliUnBind();

        void onJsThirdLogin(ThirdLoginRequestBean mThirdLoginRequestBean);

        void onJsThirdUnbind(ThirdUnBindRequestBean mThirdUnBindRequestBean);

        void onJsThirdBind(ThirdBindRequestBean mThirdBindRequestBean);

        void onJsCallPhone(CallPhoneRequestBean callPhoneRequestBean);

        void onJsAppIsInstall(AppInstallRequestBean appInstallRequestBean);

        void onJsMapNav(MapNavRequestBean mapNavRequestBean);
    }


}
