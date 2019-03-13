package cn.com.startai.sharedlib.app.js;

import android.os.Looper;

import cn.com.startai.sharedlib.app.global.Debuger;
import cn.com.startai.sharedlib.app.js.Utils.JsMsgType;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.charger.BalancePayRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.charger.BorrowDeviceRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.charger.DeviceInfoJsRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.charger.FeeRuleRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.charger.GiveBackBorrowDeviceRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.charger.GiveBackDeviceRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.charger.OrderDetailRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.charger.OrderListRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.charger.StoresDetailLstRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.charger.StoresInfoRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.charger.StoresMapLstRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.ThirdPayBalanceRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.charger.TransactionDetailsRequestBean;
import cn.com.startai.sharedlib.app.mutual.impl.charger.ChargerJsRequestInterfaceImpl;
import cn.com.swain.baselib.jsInterface.request.bean.BaseCommonJsRequestBean;
import cn.com.swain.baselib.log.Tlog;

/**
 * author: Guoqiang_Sun
 * date: 2019/2/15 0015
 * Desc:
 */
public class ChargerJsInterfaceTask extends CommonJsInterfaceTask {

    private final IChargerJsRequestInterface mChargerCallBack;

    public ChargerJsInterfaceTask(Looper mLooper, ChargerJsRequestInterfaceImpl mChargerCallBack) {
        super(mLooper, mChargerCallBack);
        this.mChargerCallBack = mChargerCallBack;
    }

    @Override
    protected void onJsUnknownRequest(BaseCommonJsRequestBean mData) {
        super.onJsUnknownRequest(mData);

        switch (mData.getMsgType()) {

            //charger

            case JsMsgType.TYPE_REQUEST_GIVEBACK_DEVICE_BORROW:

                GiveBackBorrowDeviceRequestBean mGiveBackBorrowBean = new GiveBackBorrowDeviceRequestBean(mData);
                mChargerCallBack.onJSGiveBackBorrowDevice(mGiveBackBorrowBean);

                break;


            case JsMsgType.TYPE_REQUEST_GIVEBACK_DEVICE:

                GiveBackDeviceRequestBean mGiveBackBean = new GiveBackDeviceRequestBean(mData);
                mChargerCallBack.onJSGiveBackDevice(mGiveBackBean);

                break;

            case JsMsgType.TYPE_REQUEST_BORROW_DEVICE:

                BorrowDeviceRequestBean mBorrowBean = new BorrowDeviceRequestBean(mData);
                mChargerCallBack.onJSBorrowDevice(mBorrowBean);

                break;

            case JsMsgType.TYPE_REQUEST_QUERY_DEVICE_INFO:

                DeviceInfoJsRequestBean mQueryBean = new DeviceInfoJsRequestBean(mData);
                mChargerCallBack.onJSRequestDeviceInfo(mQueryBean);

                break;

            case JsMsgType.TYPE_REQUEST_STORES_INFO:

                StoresInfoRequestBean mStoresInfoBean = new StoresInfoRequestBean(mData);
                mChargerCallBack.onJSRequestStoresInfo(mStoresInfoBean);
                break;

            case JsMsgType.TYPE_REQUEST_NEAR_STORES_MAP:

                StoresMapLstRequestBean mStoresMapLstBean = new StoresMapLstRequestBean(mData);
                mChargerCallBack.onJSRequestStoresMapLst(mStoresMapLstBean);

                break;

            case JsMsgType.TYPE_REQUEST_NEAR_STORES_DETAIL:
                StoresDetailLstRequestBean mStoresLstBean = new StoresDetailLstRequestBean(mData);
                mChargerCallBack.onJSRequestStoresDetailLst(mStoresLstBean);
                break;


            case JsMsgType.TYPE_REQUEST_DEPOSIT_FEE_RULE:
                mChargerCallBack.onJSRequestDepositFeeRule();
                break;


            case JsMsgType.TYPE_REQUEST_FEE_RULE:
                FeeRuleRequestBean mFeeRuleBean = new FeeRuleRequestBean(mData);
                mChargerCallBack.onJSRequestFeeRule(mFeeRuleBean);
                break;

            case JsMsgType.TYPE_REQUEST_TRANSACTION_DETAIL:
                TransactionDetailsRequestBean mTransactionDetailsBean = new TransactionDetailsRequestBean(mData);
                mChargerCallBack.onJSRequestTransactionDetail(mTransactionDetailsBean);
                break;

            case JsMsgType.TYPE_REQUEST_THIRD_PAY_BALANCE:

                ThirdPayBalanceRequestBean mRechargerBean = new ThirdPayBalanceRequestBean(mData);
                mChargerCallBack.onJSThirdPayBalance(mRechargerBean);

                break;


            case JsMsgType.TYPE_REQUEST_BALANCE_DEPOSIT:
                mChargerCallBack.onJSRequestBalanceDeposit();
                break;

            case JsMsgType.TYPE_REQUEST_ORDER_DETAIL:

                OrderDetailRequestBean mOrderDetailBean = new OrderDetailRequestBean(mData);
                mChargerCallBack.onJSOrderDetail(mOrderDetailBean);

                break;

            case JsMsgType.TYPE_REQUEST_BALANCE_PAY:

                BalancePayRequestBean mPayBean = new BalancePayRequestBean(mData);
                mChargerCallBack.onJSBalancePay(mPayBean);

                break;

            case JsMsgType.TYPE_REQUEST_ORDER_LIST:

                OrderListRequestBean mOrderLstBean = new OrderListRequestBean(mData);
                mChargerCallBack.onJSRequestOrderLst(mOrderLstBean);

                break;

            case JsMsgType.TYPE_REQUEST_CHARGING_STATUS:

                mChargerCallBack.onJSRequestChargingStatus();

                break;
            default:

                if (Debuger.isLogDebug) {
                    Tlog.v(TAG, " ChargerJsInterfaceTask onJsUnknownRequest " + String.valueOf(mData));
                }

                break;

        }
    }


    public interface IChargerJsRequestInterface {

        /********/

        void onJSRequestStoresInfo(StoresInfoRequestBean mStoresInfoBean);

        void onJSRequestStoresMapLst(StoresMapLstRequestBean mStoresMapLstBean);

        void onJSRequestStoresDetailLst(StoresDetailLstRequestBean mStoresLstBean);

        void onJSRequestDepositFeeRule();

        void onJSRequestFeeRule(FeeRuleRequestBean mFeeRuleBean);

        void onJSRequestTransactionDetail(TransactionDetailsRequestBean mTransactionDetailsBean);

        void onJSThirdPayBalance(ThirdPayBalanceRequestBean mRechargerBean);

        void onJSRequestBalanceDeposit();

        void onJSOrderDetail(OrderDetailRequestBean mOrderDetailBean);

        void onJSBalancePay(BalancePayRequestBean mPayBean);

        void onJSRequestOrderLst(OrderListRequestBean mOrderLstBean);

        void onJSRequestChargingStatus();

        void onJSBorrowDevice(BorrowDeviceRequestBean mBaseData);

        void onJSGiveBackDevice(GiveBackDeviceRequestBean mGiveBackBean);

        void onJSRequestDeviceInfo(DeviceInfoJsRequestBean mBaseData);

        void onJSGiveBackBorrowDevice(GiveBackBorrowDeviceRequestBean mGiveBackBorrowBean);

    }


}
