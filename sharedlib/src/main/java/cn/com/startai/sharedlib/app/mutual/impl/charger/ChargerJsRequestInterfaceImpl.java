package cn.com.startai.sharedlib.app.mutual.impl.charger;

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
import cn.com.startai.sharedlib.app.js.method2Impl.charger.BalanceDepositResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.charger.BalancePayResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.charger.BorrowDeviceResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.charger.ChargerFeeRuleResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.charger.ChargingStatusResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.charger.DepositFeeRuleResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.charger.DeviceInfoResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.charger.GiveBackBorrowDeviceResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.charger.GiveBackDeviceResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.charger.NearByStoreByAreaResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.charger.NearByStoreByMapResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.charger.OrderDetailResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.charger.OrderListResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.charger.StoreInfoResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.ThirdPayResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.charger.TransactionDetailResponseMethod;
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
import cn.com.startai.sharedlib.app.mutual.IMutualCallBack;
import cn.com.startai.sharedlib.app.mutual.IUserIDManager;
import cn.com.startai.sharedlib.app.mutual.impl.CommonJsRequestInterfaceImpl;
import cn.com.swain.baselib.log.Tlog;

/**
 * author: Guoqiang_Sun
 * date: 2018/12/25 0025
 * Desc:
 */
public class ChargerJsRequestInterfaceImpl extends CommonJsRequestInterfaceImpl
        implements ChargerJsInterfaceTask.IChargerJsRequestInterface {

    public ChargerJsRequestInterfaceImpl(Application app,
                                         IMutualCallBack mCallBack,
                                         IUserIDManager mUserID) {
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
            ChargingStatusResponseMethod chargingStatusResponseMethod =
                    ChargingStatusResponseMethod.getChargingStatusResponseMethod();
            chargingStatusResponseMethod.setResult(false);
            chargingStatusResponseMethod.setErrorCode(String.valueOf(startaiError));
            callJs(chargingStatusResponseMethod);
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

            OrderListResponseMethod orderListResponseMethod =
                    OrderListResponseMethod.getOrderListResponseMethod();
            orderListResponseMethod.setResult(false);
            orderListResponseMethod.setErrorCode(String.valueOf(startaiError.getErrorCode()));
            callJs(orderListResponseMethod);

        }

    };

    @Override
    public void onJSRequestOrderLst(OrderListRequestBean mOrderLstBean) {

        C_0x8309.Req.ContentBean req = new C_0x8309.Req.ContentBean(
                mOrderLstBean.getPage(),
                mOrderLstBean.getCount(),
                mOrderLstBean.getType());

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

            BalancePayResponseMethod balancePayResponseMethod =
                    BalancePayResponseMethod.getBalancePayResponseMethod();
            balancePayResponseMethod.setResult(false);
            balancePayResponseMethod.setErrorCode(String.valueOf(startaiError.getErrorCode()));
            callJs(balancePayResponseMethod);

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

            OrderDetailResponseMethod orderDetailResponseMethod =
                    OrderDetailResponseMethod.getOrderDetailResponseMethod();
            orderDetailResponseMethod.setResult(false);
            orderDetailResponseMethod.setErrorCode(String.valueOf(startaiError.getErrorCode()));
            callJs(orderDetailResponseMethod);
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

    };

    /**
     * 请求充值
     * see {@link ChargerMqttLsnImpl#onRequestRechargeResult(C_0x8307.Resp)}
     */
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

    };

    @Override
    public void onJSRequestTransactionDetail(TransactionDetailsRequestBean mTransactionDetailsBean) {

        // transactionType : 1 page : 1 count : 10
        C_0x8314.Req.ContentBean req = new C_0x8314.Req.ContentBean(
                mTransactionDetailsBean.getTransactionType(),
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
            ChargerFeeRuleResponseMethod feeRuleResponseMethod =
                    ChargerFeeRuleResponseMethod.getFeeRuleResponseMethod();
            feeRuleResponseMethod.setResult(false);
            feeRuleResponseMethod.setErrorCode(String.valueOf(startaiError.getErrorCode()));
            callJs(feeRuleResponseMethod);
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
            DepositFeeRuleResponseMethod feeRuleResponseMethod =
                    DepositFeeRuleResponseMethod.getDepositFeeRuleResponseMethod();
            feeRuleResponseMethod.setResult(false);
            feeRuleResponseMethod.setErrorCode(String.valueOf(startaiError.getErrorCode()));
            callJs(feeRuleResponseMethod);
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

    };


    @Override
    public void onJSRequestStoresDetailLst(StoresDetailLstRequestBean mStoresLstBean) {

        C_0x8313.Req.ContentBean req = new C_0x8313.Req.ContentBean(
                mStoresLstBean.getDeviceType(),
                mStoresLstBean.getRegion(),
                mStoresLstBean.getLan(),
                mStoresLstBean.getLat(),
                mStoresLstBean.getPage(),
                mStoresLstBean.getCount());
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

    };


    @Override
    public void onJSRequestStoresMapLst(StoresMapLstRequestBean mStoresMapLstBean) {
        //经度，纬度
        C_0x8312.Req.ContentBean req = new C_0x8312.Req.ContentBean(
                mStoresMapLstBean.getLan(),
                mStoresMapLstBean.getLat());
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
            StoreInfoResponseMethod storeInfoResponseMethod =
                    StoreInfoResponseMethod.getStoreInfoResponseMethod();
            storeInfoResponseMethod.setResult(false);
            storeInfoResponseMethod.setErrorCode(String.valueOf(startaiError.getErrorCode()));
            callJs(storeInfoResponseMethod);
        }

    };

    @Override
    public void onJSRequestStoresInfo(StoresInfoRequestBean mStoresInfoBean) {

        C_0x8304.Req.ContentBean req = new C_0x8304.Req.ContentBean();
        req.setLat(mStoresInfoBean.getLat());
        req.setLng(mStoresInfoBean.getLan());
        req.setMerchantId(String.valueOf(mStoresInfoBean.getMerchantId()));
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

    };

    @Override
    public void onJSGiveBackBorrowDevice(GiveBackBorrowDeviceRequestBean mGiveBackBorrowBean) {
        ChargerBusiManager.getInstance().borrowOrGiveBack(getUserID(),
                mGiveBackBorrowBean.getIMEI(),
                mGiveBackBorrowDeviceLsn);
    }

}
