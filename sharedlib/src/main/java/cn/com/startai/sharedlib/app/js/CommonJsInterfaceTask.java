package cn.com.startai.sharedlib.app.js;

import android.os.Looper;

import org.json.JSONException;

import cn.com.shared.weblib.js.XWalkCommonJsRequest;
import cn.com.startai.sharedlib.app.Debuger;
import cn.com.startai.sharedlib.app.js.Utils.JsMsgType;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.BalancePayRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.BindPhoneJsRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.BorrowDeviceRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.DeviceInfoJsRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.FeeRuleRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.GetIdentityCodeRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.GiveBackBorrowDeviceRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.GiveBackDeviceRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.LanguageSetRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.MobileLoginByIDCodeRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.ModifyNickNameRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.ModifyUserNameRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.ModifyUserPwdRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.OrderDetailRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.OrderListRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.StoresDetailLstRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.StoresInfoRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.StoresMapLstRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.ThirdPayBalanceRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.ThirdPayOrderRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.TransactionDetailsRequestBean;
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

    private final IJsRequestInterface mCallBack;

    public CommonJsInterfaceTask(Looper mLooper, IJsRequestInterface mCallBack) {
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

            case JsMsgType.TYPE_REQUEST_WX_BIND:

                mCallBack.onJsWXBind();

                break;

            case JsMsgType.TYPE_REQUEST_ALI_BIND:

                mCallBack.onJsAliBind();

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

            case JsMsgType.TYPE_REQUEST_QUERY_DEVICE_INFO:

                DeviceInfoJsRequestBean mQueryBean = new DeviceInfoJsRequestBean(mData);
                mCallBack.onJsRequestDeviceInfo(mQueryBean);

                break;

            case JsMsgType.TYPE_REQUEST_BORROW_DEVICE:

                BorrowDeviceRequestBean mBorrowBean = new BorrowDeviceRequestBean(mData);
                mCallBack.onJsBorrowDevice(mBorrowBean);

                break;

            case JsMsgType.TYPE_REQUEST_GIVEBACK_DEVICE:

                GiveBackDeviceRequestBean mGiveBackBean = new GiveBackDeviceRequestBean(mData);
                mCallBack.onJsGiveBackDevice(mGiveBackBean);

                break;

            case JsMsgType.TYPE_REQUEST_GIVEBACK_DEVICE_BORROW:

                GiveBackBorrowDeviceRequestBean mGiveBackBorrowBean = new GiveBackBorrowDeviceRequestBean(mData);
                mCallBack.onJsGiveBackBorrowDevice(mGiveBackBorrowBean);

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
                break;

            case JsMsgType.TYPE_REQUEST_BIND_PHONE:

                BindPhoneJsRequestBean mBindPhoneBean = new BindPhoneJsRequestBean(mData);
                mCallBack.onJsBindPhone(mBindPhoneBean);

                break;

            case JsMsgType.TYPE_REQUEST_ORDER_LIST:

                OrderListRequestBean mOrderLstBean = new OrderListRequestBean(mData);
                mCallBack.onJSRequestOrderLst(mOrderLstBean);

                break;


            case JsMsgType.TYPE_REQUEST_BALANCE_PAY:

                BalancePayRequestBean mPayBean = new BalancePayRequestBean(mData);
                mCallBack.onJSBalancePay(mPayBean);

                break;

            case JsMsgType.TYPE_REQUEST_THIRD_PAY_ORDER:

                ThirdPayOrderRequestBean mThirdPayBean = new ThirdPayOrderRequestBean(mData);
                mCallBack.onJSThirdPayOrder(mThirdPayBean);

                break;

            case JsMsgType.TYPE_REQUEST_ORDER_DETAIL:

                OrderDetailRequestBean mOrderDetailBean = new OrderDetailRequestBean(mData);
                mCallBack.onJSOrderDetail(mOrderDetailBean);

                break;

            case JsMsgType.TYPE_REQUEST_BALANCE_DEPOSIT:
                mCallBack.onJSRequestBalanceDeposit();
                break;

            case JsMsgType.TYPE_REQUEST_THIRD_PAY_BALANCE:

                ThirdPayBalanceRequestBean mRechargerBean = new ThirdPayBalanceRequestBean(mData);
                mCallBack.onJSThirdPayBalance(mRechargerBean);

                break;

            case JsMsgType.TYPE_REQUEST_TRANSACTION_DETAIL:
                TransactionDetailsRequestBean mTransactionDetailsBean = new TransactionDetailsRequestBean(mData);
                mCallBack.onJSRequestTransactionDetail(mTransactionDetailsBean);
                break;

            case JsMsgType.TYPE_REQUEST_DEPOSIT_FEE_RULE:
                mCallBack.onJSRequestDepositFeeRule();
                break;

            case JsMsgType.TYPE_REQUEST_FEE_RULE:
                FeeRuleRequestBean mFeeRuleBean = new FeeRuleRequestBean(mData);
                mCallBack.onJSRequestFeeRule(mFeeRuleBean);
                break;

            case JsMsgType.TYPE_REQUEST_NEAR_STORES_DETAIL:
                StoresDetailLstRequestBean mStoresLstBean = new StoresDetailLstRequestBean(mData);
                mCallBack.onJSRequestStoresDetailLst(mStoresLstBean);
                break;

            case JsMsgType.TYPE_REQUEST_NEAR_STORES_MAP:

                StoresMapLstRequestBean mStoresMapLstBean = new StoresMapLstRequestBean(mData);
                mCallBack.onJSRequestStoresMapLst(mStoresMapLstBean);

                break;

            case JsMsgType.TYPE_REQUEST_STORES_INFO:

                StoresInfoRequestBean mStoresInfoBean = new StoresInfoRequestBean(mData);
                mCallBack.onJSRequestStoresInfo(mStoresInfoBean);
                break;

            case JsMsgType.TYPE_REQUEST_CHARGING_STATUS:

                mCallBack.onJSRequestChargingStatus();

                break;

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


    public interface IJsRequestInterface  {

        void onJsBaseRequest(BaseCommonJsRequestBean mData);

        void onJsPressBackFinish();

        void onJsPressBackFinishBefore();

        void onJsPressBack();

        void onJsDataParseError(JSONException e, String jsonData);

        void onJsIsLogin();

        void onJsWXLogin();

        void onJsLogOut();

        void onJsCrashError(BaseCommonJsRequestBean mBaseData);

        void onJsRequestScanQR();

        void onJsRequestDeviceInfo(DeviceInfoJsRequestBean mBaseData);

        void onJsBorrowDevice(BorrowDeviceRequestBean mBaseData);

        void onJsGiveBackDevice(GiveBackDeviceRequestBean mGiveBackBean);

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

        void onJsAliLogin();

        void onJSRequestOrderLst(OrderListRequestBean mOrderLstBean);

        void onJSBalancePay(BalancePayRequestBean mPayBean);

        void onJSThirdPayOrder(ThirdPayOrderRequestBean mThirdPayBean);

        void onJSOrderDetail(OrderDetailRequestBean mOrderDetailBean);

        void onJSRequestBalanceDeposit();

        void onJSThirdPayBalance(ThirdPayBalanceRequestBean mRechargerBean);

        void onJSRequestTransactionDetail(TransactionDetailsRequestBean mTransactionDetailsBean);

        void onJSRequestFeeRule(FeeRuleRequestBean mFeeRuleBean);

        void onJSRequestDepositFeeRule();

        void onJSRequestStoresDetailLst(StoresDetailLstRequestBean mStoresLstBean);

        void onJSRequestStoresMapLst(StoresMapLstRequestBean mStoresMapLstBean);

        void onJSRequestStoresInfo(StoresInfoRequestBean mStoresInfoBean);

        void onJsGiveBackBorrowDevice(GiveBackBorrowDeviceRequestBean mGiveBackBorrowBean);

        void onJsWXBind();

        void onJsAliBind();

        void onJsBindPhone(BindPhoneJsRequestBean mBindPhoneBean);

        void onJSRequestChargingStatus();

    }


}
