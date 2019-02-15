package cn.com.startai.sharedlib.app.mutual.impl;

import android.app.Application;

import cn.com.startai.chargersdk.ChargerBusiManager;
import cn.com.startai.chargersdk.entity.C_0x8304;
import cn.com.startai.chargersdk.entity.C_0x8307;
import cn.com.startai.chargersdk.entity.C_0x8309;
import cn.com.startai.chargersdk.entity.C_0x8312;
import cn.com.startai.chargersdk.entity.C_0x8313;
import cn.com.startai.chargersdk.entity.C_0x8314;
import cn.com.startai.mqttsdk.base.StartaiError;
import cn.com.startai.mqttsdk.listener.IOnCallListener;
import cn.com.startai.mqttsdk.mqtt.request.MqttPublishRequest;
import cn.com.startai.sharedlib.app.global.Debuger;
import cn.com.startai.sharedlib.app.js.ChargerJsInterfaceTask;
import cn.com.startai.sharedlib.app.js.method2Impl.BalanceDepositResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.BalancePayResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.BorrowDeviceResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.ChargerFeeRuleResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.ChargingStatusResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.DepositFeeRuleResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.DeviceInfoResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.GiveBackBorrowDeviceResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.GiveBackDeviceResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.NearByStoreByAreaResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.NearByStoreByMapResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.OrderDetailResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.OrderListResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.StoreInfoResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.ThirdPayResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.TransactionDetailResponseMethod;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.BalancePayRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.BorrowDeviceRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.DeviceInfoJsRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.FeeRuleRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.GiveBackBorrowDeviceRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.GiveBackDeviceRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.OrderDetailRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.OrderListRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.StoresDetailLstRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.StoresInfoRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.StoresMapLstRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.ThirdPayBalanceRequestBean;
import cn.com.startai.sharedlib.app.js.requestBeanImpl.TransactionDetailsRequestBean;
import cn.com.startai.sharedlib.app.mutual.IMutualCallBack;
import cn.com.startai.sharedlib.app.mutual.IUserIDManager;
import cn.com.swain.baselib.log.Tlog;

/**
 * author: Guoqiang_Sun
 * date: 2018/12/25 0025
 * Desc:
 */
public class ChargerJsRequestInterfaceImpl extends CommonJsRequestInterfaceImpl
        implements ChargerJsInterfaceTask.IChargerJsRequestInterface {

    public ChargerJsRequestInterfaceImpl(Application app, IMutualCallBack mCallBack, IUserIDManager mUserID) {
        super(app, mCallBack, mUserID);
    }

    private IOnCallListener getUserChargingStatusLsn = new IOnCallListener() {
        @Override
        public void onSuccess(MqttPublishRequest request) {
            if (Debuger.isLogDebug) {
                Tlog.v(TAG, "getUserChargingStatus msg send success ");
            }
        }

        @Override
        public void onFailed(MqttPublishRequest request, StartaiError startaiError) {
            if (Debuger.isLogDebug) {
                Tlog.e(TAG, "getUserChargingStatus msg send fail " + String.valueOf(startaiError));
            }
            ChargingStatusResponseMethod chargingStatusResponseMethod = ChargingStatusResponseMethod.getChargingStatusResponseMethod();
            chargingStatusResponseMethod.setResult(false);
            chargingStatusResponseMethod.setErrorCode(String.valueOf(startaiError));
            callJs(chargingStatusResponseMethod);
        }

        @Override
        public boolean needUISafety() {
            return false;
        }
    };

    @Override
    public void onJSRequestChargingStatus() {
        ChargerBusiManager.getInstance().getUserChargingStatus(getUserID(), getUserChargingStatusLsn);
    }

    private IOnCallListener mOrderLstLsn = new IOnCallListener() {
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

    private IOnCallListener mBalancePayLsn = new IOnCallListener() {
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

    private IOnCallListener mOrderDetailLsn = new IOnCallListener() {
        @Override
        public void onSuccess(MqttPublishRequest request) {
            if (Debuger.isLogDebug) {
                Tlog.v(TAG, "orderDetail msg send success");
            }
        }

        @Override
        public void onFailed(MqttPublishRequest request, StartaiError startaiError) {
            if (Debuger.isLogDebug) {
                Tlog.e(TAG, "orderDetail msg send fail " + String.valueOf(startaiError));
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

    private IOnCallListener mBalanceDepositLsn = new IOnCallListener() {
        @Override
        public void onSuccess(MqttPublishRequest request) {
            if (Debuger.isLogDebug) {
                Tlog.v(TAG, "getBalanceAndDeposit msg send success");
            }
        }

        @Override
        public void onFailed(MqttPublishRequest request, StartaiError startaiError) {
            if (Debuger.isLogDebug) {
                Tlog.e(TAG, "getBalanceAndDeposit msg send fail " + String.valueOf(startaiError));
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
        ChargerBusiManager.getInstance().getBalanceAndDeposit(getUserID(), mBalanceDepositLsn);
    }

    private IOnCallListener mThirdPayBalanceLsn = new IOnCallListener() {
        @Override
        public void onSuccess(MqttPublishRequest request) {
            if (Debuger.isLogDebug) {
                Tlog.v(TAG, "mThirdPayBalance msg send success");
            }
        }

        @Override
        public void onFailed(MqttPublishRequest request, StartaiError startaiError) {
            if (Debuger.isLogDebug) {
                Tlog.e(TAG, "mThirdPayBalance msg send fail " + String.valueOf(startaiError));
            }
            ThirdPayResponseMethod rechargerResponseMethod =
                    ThirdPayResponseMethod.getThirdPayResponseMethod();
            rechargerResponseMethod.setResult(false);
            rechargerResponseMethod.setErrorCode(String.valueOf(startaiError.getErrorCode()));
            callJs(rechargerResponseMethod);
        }

        @Override
        public boolean needUISafety() {
            return false;
        }
    };

    @Override
    public void onJSThirdPayBalance(ThirdPayBalanceRequestBean mRechargerBean) {

        C_0x8307.Req.ContentBean req = new C_0x8307.Req.ContentBean(mRechargerBean.getFee(),
                mRechargerBean.getPlatform(),
                mRechargerBean.getType());
        ChargerBusiManager.getInstance().requestRecharge(req, mThirdPayBalanceLsn);
    }

    private IOnCallListener mTransactionDetailLsn = new IOnCallListener() {
        @Override
        public void onSuccess(MqttPublishRequest request) {
            if (Debuger.isLogDebug) {
                Tlog.v(TAG, "mTransactionDetail msg send success");
            }
        }

        @Override
        public void onFailed(MqttPublishRequest request, StartaiError startaiError) {
            if (Debuger.isLogDebug) {
                Tlog.e(TAG, "mTransactionDetail msg send fail " + String.valueOf(startaiError));
            }
            TransactionDetailResponseMethod transactionDetailResponseMethod =
                    TransactionDetailResponseMethod.getTransactionDetailResponseMethod();
            transactionDetailResponseMethod.setResult(false);
            transactionDetailResponseMethod.setErrorCode(String.valueOf(startaiError.getErrorCode()));
            callJs(transactionDetailResponseMethod);
        }

        @Override
        public boolean needUISafety() {
            return false;
        }
    };

    @Override
    public void onJSRequestTransactionDetail(TransactionDetailsRequestBean mTransactionDetailsBean) {

        // transactionType : 1 page : 1 count : 10
        C_0x8314.Req.ContentBean req = new C_0x8314.Req.ContentBean(mTransactionDetailsBean.getTransactionType(),
                getUserID(),
                mTransactionDetailsBean.getPage(),
                mTransactionDetailsBean.getCount());
        ChargerBusiManager.getInstance().getTransactionDetails(req, mTransactionDetailLsn);
    }

    private IOnCallListener mFeeRuleLsn = new IOnCallListener() {
        @Override
        public void onSuccess(MqttPublishRequest request) {
            if (Debuger.isLogDebug) {
                Tlog.v(TAG, "getChargerFee msg send success");
            }
        }

        @Override
        public void onFailed(MqttPublishRequest request, StartaiError startaiError) {
            if (Debuger.isLogDebug) {
                Tlog.e(TAG, "getChargerFee msg send fail " + String.valueOf(startaiError));
            }
            ChargerFeeRuleResponseMethod feeRuleResponseMethod = ChargerFeeRuleResponseMethod.getFeeRuleResponseMethod();
            feeRuleResponseMethod.setResult(false);
            feeRuleResponseMethod.setErrorCode(String.valueOf(startaiError.getErrorCode()));
            callJs(feeRuleResponseMethod);
        }

        @Override
        public boolean needUISafety() {
            return false;
        }
    };

    @Override
    public void onJSRequestFeeRule(FeeRuleRequestBean mFeeRuleBean) {
        ChargerBusiManager.getInstance().getChargerFee(mFeeRuleBean.getIMEI(), mFeeRuleLsn);
    }


    private IOnCallListener mDepositFeeRuleLsn = new IOnCallListener() {
        @Override
        public void onSuccess(MqttPublishRequest request) {
            if (Debuger.isLogDebug) {
                Tlog.v(TAG, "getDepositFeeRule msg send success");
            }
        }

        @Override
        public void onFailed(MqttPublishRequest request, StartaiError startaiError) {
            if (Debuger.isLogDebug) {
                Tlog.e(TAG, "getDepositFeeRule msg send fail " + String.valueOf(startaiError));
            }
            DepositFeeRuleResponseMethod feeRuleResponseMethod = DepositFeeRuleResponseMethod.getDepositFeeRuleResponseMethod();
            feeRuleResponseMethod.setResult(false);
            feeRuleResponseMethod.setErrorCode(String.valueOf(startaiError.getErrorCode()));
            callJs(feeRuleResponseMethod);
        }

        @Override
        public boolean needUISafety() {
            return false;
        }
    };


    @Override
    public void onJSRequestDepositFeeRule() {
        ChargerBusiManager.getInstance().getDepositFeeRule(getUserID(), mDepositFeeRuleLsn);
    }


    private IOnCallListener mStoresDetailLstLsn = new IOnCallListener() {
        @Override
        public void onSuccess(MqttPublishRequest request) {
            if (Debuger.isLogDebug) {
                Tlog.v(TAG, "getNearbyStorsByArea msg send success");
            }
        }

        @Override
        public void onFailed(MqttPublishRequest request, StartaiError startaiError) {
            if (Debuger.isLogDebug) {
                Tlog.e(TAG, "getNearbyStorsByArea msg send fail " + String.valueOf(startaiError));
            }

            NearByStoreByAreaResponseMethod nearByStoreByAreaResponseMethod =
                    NearByStoreByAreaResponseMethod.getNearByStoreByAreaResponseMethod();
            nearByStoreByAreaResponseMethod.setResult(false);
            nearByStoreByAreaResponseMethod.setErrorCode(String.valueOf(startaiError.getErrorCode()));
            callJs(nearByStoreByAreaResponseMethod);

        }

        @Override
        public boolean needUISafety() {
            return false;
        }
    };


    @Override
    public void onJSRequestStoresDetailLst(StoresDetailLstRequestBean mStoresLstBean) {

        C_0x8313.Req.ContentBean req = new C_0x8313.Req.ContentBean(mStoresLstBean.getDeviceType(),
                mStoresLstBean.getRegion(), mStoresLstBean.getLan(), mStoresLstBean.getLat(),
                mStoresLstBean.getPage(), mStoresLstBean.getCount());
        ChargerBusiManager.getInstance().getNearbyStorsByArea(req, mStoresDetailLstLsn);
    }


    private IOnCallListener mStoresMapLstLsn = new IOnCallListener() {
        @Override
        public void onSuccess(MqttPublishRequest request) {
            if (Debuger.isLogDebug) {
                Tlog.v(TAG, "getNearbyStores msg send success");
            }
        }

        @Override
        public void onFailed(MqttPublishRequest request, StartaiError startaiError) {
            if (Debuger.isLogDebug) {
                Tlog.e(TAG, "getNearbyStores msg send fail " + String.valueOf(startaiError));
            }

            NearByStoreByMapResponseMethod nearByStoreByMapResponseMethod =
                    NearByStoreByMapResponseMethod.getNearByStoreByMapResponseMethod();
            nearByStoreByMapResponseMethod.setResult(false);
            nearByStoreByMapResponseMethod.setErrorCode(String.valueOf(startaiError.getErrorCode()));
            callJs(nearByStoreByMapResponseMethod);

        }

        @Override
        public boolean needUISafety() {
            return false;
        }
    };


    @Override
    public void onJSRequestStoresMapLst(StoresMapLstRequestBean mStoresMapLstBean) {
        //经度，纬度
        C_0x8312.Req.ContentBean req = new C_0x8312.Req.ContentBean(mStoresMapLstBean.getLan(), mStoresMapLstBean.getLat());
        ChargerBusiManager.getInstance().getNearbyStores(req, mStoresMapLstLsn);
    }

    private IOnCallListener mStoreInfoLsn = new IOnCallListener() {
        @Override
        public void onSuccess(MqttPublishRequest request) {
            if (Debuger.isLogDebug) {
                Tlog.v(TAG, "getStoreInfo msg send success");
            }
        }

        @Override
        public void onFailed(MqttPublishRequest request, StartaiError startaiError) {
            if (Debuger.isLogDebug) {
                Tlog.e(TAG, "getStoreInfo msg send fail " + String.valueOf(startaiError));
            }
            StoreInfoResponseMethod storeInfoResponseMethod = StoreInfoResponseMethod.getStoreInfoResponseMethod();
            storeInfoResponseMethod.setResult(false);
            storeInfoResponseMethod.setErrorCode(String.valueOf(startaiError.getErrorCode()));
            callJs(storeInfoResponseMethod);
        }

        @Override
        public boolean needUISafety() {
            return false;
        }
    };

    @Override
    public void onJSRequestStoresInfo(StoresInfoRequestBean mStoresInfoBean) {

        C_0x8304.Req.ContentBean req = new C_0x8304.Req.ContentBean();
        req.setLat(mStoresInfoBean.getLat());
        req.setLng(mStoresInfoBean.getLan());
        req.setMerchantId(mStoresInfoBean.getMerchantId());
        ChargerBusiManager.getInstance().getStoreInfo(req, mStoreInfoLsn);

    }

    private IOnCallListener mGetChargerInfoLsn = new IOnCallListener() {
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
    public void onJSRequestDeviceInfo(DeviceInfoJsRequestBean mData) {

        ChargerBusiManager.getInstance().getChargerInfo(mData.getIMEI(), mGetChargerInfoLsn);

    }

    private IOnCallListener mBorrowLsn = new IOnCallListener() {
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
    public void onJSBorrowDevice(BorrowDeviceRequestBean mData) {

//        ChargerBusiManager.getInstance().borrow(mMutualManager.getUserID(), mData.getIMEI(), mBorrowLsn);

        ChargerBusiManager.getInstance().borrowOrGiveBack(getUserID(), mData.getIMEI(), mBorrowLsn);

    }

    private IOnCallListener mGiveBackDeviceLsn = new IOnCallListener() {
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
    public void onJSGiveBackDevice(GiveBackDeviceRequestBean mGiveBackBean) {

        ChargerBusiManager.getInstance().borrowOrGiveBack(getUserID(), mGiveBackBean.getIMEI(), mGiveBackDeviceLsn);

    }

    private IOnCallListener mGiveBackBorrowDeviceLsn = new IOnCallListener() {
        @Override
        public void onSuccess(MqttPublishRequest mqttPublishRequest) {
            if (Debuger.isLogDebug) {
                Tlog.v(TAG, " borrowOrGiveBack msg send success");
            }
        }

        @Override
        public void onFailed(MqttPublishRequest mqttPublishRequest, StartaiError startaiError) {
            if (Debuger.isLogDebug) {
                Tlog.e(TAG, " borrowOrGiveBack msg send fail " + String.valueOf(startaiError));
            }
            GiveBackBorrowDeviceResponseMethod giveBackBorrowDeviceResponseMethod =
                    GiveBackBorrowDeviceResponseMethod.getGiveBackBorrowDeviceResponseMethod();
            giveBackBorrowDeviceResponseMethod.setResult(false);
            giveBackBorrowDeviceResponseMethod.setErrorCode(String.valueOf(startaiError.getErrorCode()));
            callJs(giveBackBorrowDeviceResponseMethod);
        }

        @Override
        public boolean needUISafety() {
            return false;
        }
    };

    @Override
    public void onJSGiveBackBorrowDevice(GiveBackBorrowDeviceRequestBean mGiveBackBorrowBean) {
        ChargerBusiManager.getInstance().borrowOrGiveBack(getUserID(),
                mGiveBackBorrowBean.getIMEI(),
                mGiveBackBorrowDeviceLsn);
    }

}
