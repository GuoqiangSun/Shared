package cn.com.startai.sharedlib.app.js;

import android.os.Looper;

import org.json.JSONException;

import cn.com.shared.weblib.js.XWalkCommonJsRequest;
import cn.com.startai.sharedlib.app.js.Utils.JsMsgType;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.DeviceInfoJsRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.GetIdentityCodeRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.LanguageSetRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.MobileLoginByIDCodeRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.ModifyNickNameRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.ModifyUserNameRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.ModifyUserPwdRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.UpgradeAppRequestBean;
import cn.com.swain.baselib.jsInterface.base.AbsCommonJsInterfaceProxy;
import cn.com.swain.baselib.jsInterface.base.IBaseJsRequestInterface;
import cn.com.swain.baselib.jsInterface.bean.BaseCommonJsRequestBean;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/27 0027
 * desc :
 */
public class CommonJsInterfaceTask extends AbsCommonJsInterfaceProxy {

    public interface IJsRequestInterface extends IBaseJsRequestInterface {

        void onJsPressBackFinish();

        void onJsPressBackFinishBefore();

        void onJsPressBack();

        void onJsDataParseError(JSONException e, String jsonData);

        void onJsIsLogin();

        void onJsWXLogin(BaseCommonJsRequestBean mBaseData);

        void onJsLoginOut();


        void onJsCrashError(BaseCommonJsRequestBean mBaseData);

        void onJsRequestScanQR();

        void onJsRequestDeviceInfo(DeviceInfoJsRequestBean mBaseData);

        void onJsBorrowDevice(DeviceInfoJsRequestBean mBaseData);

        void onJsGiveBackDevice(DeviceInfoJsRequestBean mGiveBackBean);

        void onJsGetIdentityCode(GetIdentityCodeRequestBean mGetIDCodeBean);

        void onJsMobileLoginByIdentityCode(MobileLoginByIDCodeRequestBean mMobileLoginByIDBean);

        void onJSQuerySystemLanguage();

        void onJSSetSystemLanguage(LanguageSetRequestBean mLanguageSetRequestBean);

        void onJSCheckAppIsNew();

        void onJSRequestUpgradeApp(UpgradeAppRequestBean mUpgradeAppRequestBean);

        void onJSCancelUpgradeApp(UpgradeAppRequestBean mUpgradeAppRequestBean);

        void onJSRequestAppVersion();

        void onJSRequestUserInfo();

        void onJSRequestTakePhoto();

        void onJSRequestLocalPhoto();

        void onJSModifyUserName(ModifyUserNameRequestBean mModifyNameBean);

        void onJSModifyUserPwd(ModifyUserPwdRequestBean mModifyPwdBean);

        void onJSModifyNickName(ModifyNickNameRequestBean mModifyNicknameBean);

        void onJsAliLogin(BaseCommonJsRequestBean mData);
    }

    private final IJsRequestInterface mCallBack;

    public CommonJsInterfaceTask(Looper mLooper, IJsRequestInterface mCallBack) {
        super("XWalkCommonJsInterfaceProxy");
        final XWalkCommonJsRequest commonJsRequest = new XWalkCommonJsRequest(mLooper, this);
        attachAbsJsInterface(commonJsRequest);
        this.mCallBack = mCallBack;
    }

    @Override
    protected void onJsRequest(BaseCommonJsRequestBean mData) {

        switch (mData.getMsgType()) {
            case JsMsgType.TYPE_REQUEST_BACK:

                mCallBack.onJsPressBack();

                break;

            case JsMsgType.TYPE_REQUEST_IS_LOGIN:

                mCallBack.onJsIsLogin();

                break;

            case JsMsgType.TYPE_REQUEST_WX_LOGIN:

                mCallBack.onJsWXLogin(mData);

                break;

            case JsMsgType.TYPE_REQUEST_ALI_LOGIN:

                mCallBack.onJsAliLogin(mData);

                break;

            case JsMsgType.TYPE_REQUEST_LOGIN_OUT:

                mCallBack.onJsLoginOut();

                break;

            case JsMsgType.TYPE_REQUEST_JS_ERROR:

                mCallBack.onJsCrashError(mData);

                break;

            case JsMsgType.TYPE_REQUEST_SCAN_OR:

                mCallBack.onJsRequestScanQR();

                break;

            case JsMsgType.TYPE_REQUEST_QUERY_DEVICE_INFO:

                DeviceInfoJsRequestBean mQueryBean = new DeviceInfoJsRequestBean(mData);
                mCallBack.onJsRequestDeviceInfo(mQueryBean);

                break;

            case JsMsgType.TYPE_REQUEST_BORROW_DEVICE:

                DeviceInfoJsRequestBean mBorrowBean = new DeviceInfoJsRequestBean(mData);
                mCallBack.onJsBorrowDevice(mBorrowBean);

                break;

            case JsMsgType.TYPE_REQUEST_GIVEBACK_DEVICE:

                DeviceInfoJsRequestBean mGiveBackBean = new DeviceInfoJsRequestBean(mData);
                mCallBack.onJsGiveBackDevice(mGiveBackBean);

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

                mCallBack.onJSQuerySystemLanguage();

                break;

            case JsMsgType.TYPE_REQUEST_SET_SYSTEM_LANGUAGE:

                LanguageSetRequestBean mLanguageSetRequestBean = new LanguageSetRequestBean(mData);
                mCallBack.onJSSetSystemLanguage(mLanguageSetRequestBean);

                break;

            case JsMsgType.TYPE_REQUEST_IS_NEWVERSION:

                mCallBack.onJSCheckAppIsNew();

                break;

            case JsMsgType.TYPE_REQUEST_APP_UPGRADE:

                UpgradeAppRequestBean mUpgradeAppRequestBean = new UpgradeAppRequestBean(mData);
                mCallBack.onJSRequestUpgradeApp(mUpgradeAppRequestBean);

                break;

            case JsMsgType.TYPE_REQUEST_CANCEL_APP_UPGRADE:

                UpgradeAppRequestBean mCancelUpgradeAppRequestBean = new UpgradeAppRequestBean(mData);
                mCallBack.onJSCancelUpgradeApp(mCancelUpgradeAppRequestBean);

                break;
            case JsMsgType.TYPE_REQUEST_APP_VERSION:

                mCallBack.onJSRequestAppVersion();

                break;

            case JsMsgType.TYPE_REQUEST_USER_INFO:

                mCallBack.onJSRequestUserInfo();

                break;
            case JsMsgType.TYPE_REQUEST_TAKE_PHOTO:

                mCallBack.onJSRequestTakePhoto();

                break;
            case JsMsgType.TYPE_REQUEST_LOCAL_PHOTO:

                mCallBack.onJSRequestLocalPhoto();

                break;
            case JsMsgType.TYPE_REQUEST_MODIFY_USERNAME:

                ModifyUserNameRequestBean mModifyNameBean = new ModifyUserNameRequestBean(mData);
                mCallBack.onJSModifyUserName(mModifyNameBean);

                break;
            case JsMsgType.TYPE_REQUEST_MODIFY_NICKNAME:
                ModifyNickNameRequestBean mModifyNicknameBean = new ModifyNickNameRequestBean(mData);
                mCallBack.onJSModifyNickName(mModifyNicknameBean);

                break;
            case JsMsgType.TYPE_REQUEST_MODIFY_USERPWD:

                ModifyUserPwdRequestBean mModifyPwdBean = new ModifyUserPwdRequestBean(mData);
                mCallBack.onJSModifyUserPwd(mModifyPwdBean);

            default:

                mCallBack.onJsBaseRequest(mData);

                break;
        }

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

}
