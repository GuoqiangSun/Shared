package cn.com.startai.sharedlib.app.mutual.impl.charger;

import android.app.Application;

import cn.com.startai.chargersdk.IOnChargerMessageArriveListener;
import cn.com.startai.chargersdk.entity.C_0x8301;
import cn.com.startai.chargersdk.entity.C_0x8304;
import cn.com.startai.chargersdk.entity.C_0x8305;
import cn.com.startai.chargersdk.entity.C_0x8307;
import cn.com.startai.chargersdk.entity.C_0x8308;
import cn.com.startai.chargersdk.entity.C_0x8309;
import cn.com.startai.chargersdk.entity.C_0x8310;
import cn.com.startai.chargersdk.entity.C_0x8311;
import cn.com.startai.chargersdk.entity.C_0x8312;
import cn.com.startai.chargersdk.entity.C_0x8313;
import cn.com.startai.chargersdk.entity.C_0x8314;
import cn.com.startai.chargersdk.entity.C_0x8315;
import cn.com.startai.chargersdk.entity.C_0x8316;
import cn.com.startai.chargersdk.entity.C_0x8317;
import cn.com.startai.mqttsdk.StartAI;
import cn.com.startai.mqttsdk.base.BaseMessage;
import cn.com.startai.mqttsdk.base.StartaiError;
import cn.com.startai.mqttsdk.busi.entity.C_0x8028;
import cn.com.startai.mqttsdk.listener.IOnCallListener;
import cn.com.startai.mqttsdk.mqtt.request.MqttPublishRequest;
import cn.com.startai.sharedlib.app.global.Debuger;
import cn.com.startai.sharedlib.app.js.method2Impl.ThirdPayResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.charger.BalanceDepositResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.charger.BalancePayResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.charger.ChargerFeeRuleResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.charger.ChargingStatusResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.charger.DepositFeeRuleResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.charger.DeviceInfoResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.charger.GiveBackBorrowDeviceResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.charger.NearByStoreByAreaResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.charger.NearByStoreByMapResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.charger.OrderDetailResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.charger.OrderListResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.charger.StoreInfoResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.charger.TransactionDetailResponseMethod;
import cn.com.startai.sharedlib.app.mutual.IMutualCallBack;
import cn.com.startai.sharedlib.app.mutual.IUserIDManager;
import cn.com.startai.sharedlib.app.mutual.MutualManager;
import cn.com.startai.sharedlib.app.mutual.impl.CommonMqttLsnImpl;
import cn.com.swain.baselib.log.Tlog;

/**
 * author: Guoqiang_Sun
 * date: 2019/1/24 0024
 * Desc:
 */
public class ChargerMqttLsnImpl extends CommonMqttLsnImpl
        implements IOnChargerMessageArriveListener {

    public ChargerMqttLsnImpl(Application app, IMutualCallBack mCallBack, IUserIDManager mUserID) {
        super(app, mCallBack, mUserID);
        // mqtt 基础库接口
    }

//    @Override
//    public void onBorrowResult(C_0x8301.Resp resp) {
//        super.onBorrowResult(resp);
//        if (Debuger.isLogDebug) {
//            Tlog.d(TAG, " onBorrowResult :" + String.valueOf(resp));
//        }
//
//        BorrowDeviceResponseMethod borrowDeviceResponseMethod =
//                BorrowDeviceResponseMethod.getBorrowDeviceResponseMethod();
//        borrowDeviceResponseMethod.setResult(resp.getResult() == 1);
//        C_0x8301.Resp.ContentBean content = resp.getContent();
//        borrowDeviceResponseMethod.setContent(content);
//        if (content != null) {
//            borrowDeviceResponseMethod.setErrorCode(content.getErrcode());
//        }
//        callJs(borrowDeviceResponseMethod);
//    }


//    @Override
//    public void onGiveBackResult(C_0x8302.Resp resp) {
//        super.onGiveBackResult(resp);
//        if (Debuger.isLogDebug) {
//            Tlog.d(TAG, " onGiveBackResult :" + String.valueOf(resp));
//        }
//
//        GiveBackDeviceResponseMethod giveBackDeviceResponseMethod =
//                GiveBackDeviceResponseMethod.getGiveBackDeviceResponseMethod();
//        giveBackDeviceResponseMethod.setResult(resp.getResult() == 1);
//
//        C_0x8302.Resp.ContentBean content = resp.getContent();
//        giveBackDeviceResponseMethod.setContent(content);
//        if (content != null) {
//            giveBackDeviceResponseMethod.setErrorCode(content.getErrcode());
//        }
//        callJs(giveBackDeviceResponseMethod);
//
//    }


    @Override
    public void onBorrowOrGiveBackResult(C_0x8301.Resp resp) {

        if (Debuger.isLogDebug) {
            Tlog.d(TAG, " onBorrowOrGiveBackResult :" + String.valueOf(resp));
        }

        GiveBackBorrowDeviceResponseMethod giveBackBorrowDeviceResponseMethod =
                GiveBackBorrowDeviceResponseMethod.getGiveBackBorrowDeviceResponseMethod();
        giveBackBorrowDeviceResponseMethod.setResult(resp.getResult() == 1);
        C_0x8301.Resp.ContentBean content = resp.getContent();
        giveBackBorrowDeviceResponseMethod.setContent(content);
        if (content != null) {
            giveBackBorrowDeviceResponseMethod.setErrorCode(content.getErrcode());
        }
        callJs(giveBackBorrowDeviceResponseMethod);


//        if(C_0x8301.TYPE_LENT.equals(resp.getContent().getType())){
//
//            if (Debuger.isLogDebug) {
//                Tlog.d(TAG, " onGiveBackResult :" + String.valueOf(resp));
//            }
//
//            GiveBackDeviceResponseMethod giveBackDeviceResponseMethod =
//                    GiveBackDeviceResponseMethod.getGiveBackDeviceResponseMethod();
//            giveBackDeviceResponseMethod.setResult(resp.getResult() == 1);
//
//            giveBackDeviceResponseMethod.setContent(content);
//            if (content != null) {
//                giveBackDeviceResponseMethod.setErrorCode(content.getErrcode());
//            }
//            callJs(giveBackDeviceResponseMethod);
//
//
//        }else if(C_0x8301.TYPE_RETURN.equals(resp.getContent().getType())){
//
//            if (Debuger.isLogDebug) {
//                Tlog.d(TAG, " onBorrowResult :" + String.valueOf(resp));
//            }
//
//            BorrowDeviceResponseMethod borrowDeviceResponseMethod =
//                    BorrowDeviceResponseMethod.getBorrowDeviceResponseMethod();
//            borrowDeviceResponseMethod.setResult(resp.getResult() == 1);
//            borrowDeviceResponseMethod.setContent(content);
//            if (content != null) {
//                borrowDeviceResponseMethod.setErrorCode(content.getErrcode());
//            }
//            callJs(borrowDeviceResponseMethod);
//        }


    }


    //    查询用户交易明细
    @Override
    public void onGetTransactionDetailsResult(C_0x8314.Resp resp) {

        if (Debuger.isLogDebug) {
            Tlog.d(TAG, " onGetTransactionDetailsResult :" + String.valueOf(resp));
        }

        TransactionDetailResponseMethod transactionDetailResponseMethod =
                TransactionDetailResponseMethod.getTransactionDetailResponseMethod();
        transactionDetailResponseMethod.setResult(resp.getResult() == 1);
        C_0x8314.Resp.ContentBean content = resp.getContent();
        transactionDetailResponseMethod.setContent(content);
        if (content != null) {
            transactionDetailResponseMethod.setErrorCode(content.getErrcode());
        }
        callJs(transactionDetailResponseMethod);
    }

    //            查询网点信息
    @Override
    public void onGetStoreInfoResult(C_0x8304.Resp resp) {
        if (Debuger.isLogDebug) {
            Tlog.d(TAG, " onGetStoreInfoResult :" + String.valueOf(resp));
        }

        StoreInfoResponseMethod storeInfoResponseMethod =
                StoreInfoResponseMethod.getStoreInfoResponseMethod();
        storeInfoResponseMethod.setResult(resp.getResult() == 1);
        C_0x8304.Resp.ContentBean content = resp.getContent();
        storeInfoResponseMethod.setContent(content);
        if (content != null) {
            storeInfoResponseMethod.setErrorCode(content.getErrcode());
        }
        callJs(storeInfoResponseMethod);

    }

    //            查询机柜信息
    @Override
    public void onGetChargerInfoResult(C_0x8305.Resp resp) {
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

    // v收费标准
    @Override
    public void onGetChargerFeeResult(C_0x8316.Resp resp) {

        if (Debuger.isLogDebug) {
            Tlog.d(TAG, " onGetChargerFeeResult :" + String.valueOf(resp));
        }

        ChargerFeeRuleResponseMethod feeRuleResponseMethod =
                ChargerFeeRuleResponseMethod.getFeeRuleResponseMethod();
        feeRuleResponseMethod.setResult(resp.getResult() == 1);
        C_0x8316.Resp.ContentBean content = resp.getContent();
        feeRuleResponseMethod.setContent(content);
        if (content != null) {
            feeRuleResponseMethod.setErrorCode(content.getErrcode());
        }
        callJs(feeRuleResponseMethod);

    }

    // 押金
    @Override
    public void onGetDepositFeeRuleResult(C_0x8317.Resp resp) {

        if (Debuger.isLogDebug) {
            Tlog.d(TAG, " onGetDepositFeeRuleResult :" + String.valueOf(resp));
        }

        DepositFeeRuleResponseMethod feeRuleResponseMethod =
                DepositFeeRuleResponseMethod.getDepositFeeRuleResponseMethod();
        feeRuleResponseMethod.setResult(resp.getResult() == 1);
        C_0x8317.Resp.ContentBean content = resp.getContent();
        feeRuleResponseMethod.setContent(content);
        if (content != null) {
            feeRuleResponseMethod.setErrorCode(content.getErrcode());
        }
        callJs(feeRuleResponseMethod);
    }

    //    获取指定坐标附近店铺信息（简略版，适合地图显示时调用）
    @Override
    public void onGetNearbyStoresResult(C_0x8312.Resp resp) {

        if (Debuger.isLogDebug) {
            Tlog.d(TAG, " onGetNearbyStoresResult :" + String.valueOf(resp));
        }

        NearByStoreByMapResponseMethod nearByStoreByMapResponseMethod =
                NearByStoreByMapResponseMethod.getNearByStoreByMapResponseMethod();
        nearByStoreByMapResponseMethod.setResult(resp.getResult() == 1);
        C_0x8312.Resp.ContentBean content = resp.getContent();
        nearByStoreByMapResponseMethod.setContent(content);
        if (content != null) {
            nearByStoreByMapResponseMethod.setErrorCode(content.getErrcode());
        }
        callJs(nearByStoreByMapResponseMethod);

    }

    @Override
    public void onGetNearbyStoreByAreaResult(C_0x8313.Resp resp) {

        if (Debuger.isLogDebug) {
            Tlog.d(TAG, " onGetNearbyStoreByAreaResult :" + String.valueOf(resp));
        }

        NearByStoreByAreaResponseMethod nearByStoreByAreaResponseMethod =
                NearByStoreByAreaResponseMethod.getNearByStoreByAreaResponseMethod();
        nearByStoreByAreaResponseMethod.setResult(resp.getResult() == 1);
        C_0x8313.Resp.ContentBean content = resp.getContent();
        nearByStoreByAreaResponseMethod.setContent(content);
        if (content != null) {
            nearByStoreByAreaResponseMethod.setErrorCode(content.getErrcode());
        }
        callJs(nearByStoreByAreaResponseMethod);

    }


    private final IOnCallListener mThirdPayBalanceLsn = new IOnCallListener() {
        @Override
        public void onSuccess(MqttPublishRequest request) {
            if (Debuger.isLogDebug) {
                Tlog.v(TAG, "mRechargeLsn msg send success");
            }
        }

        @Override
        public void onFailed(MqttPublishRequest request, StartaiError startaiError) {
            if (Debuger.isLogDebug) {
                Tlog.e(TAG, "mRechargeLsn msg send fail " + String.valueOf(startaiError));
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
     * see {@link CommonMqttLsnImpl#onThirdPaymentUnifiedOrderResult(C_0x8028.Resp)}
     */
    @Override
    public void onRequestRechargeResult(C_0x8307.Resp resp) {

        if (Debuger.isLogDebug) {
            Tlog.d(TAG, " onRequestRechargeResult :" + String.valueOf(resp));
        }

        C_0x8307.Resp.ContentBean content;
        if (resp.getResult() == 1 && (content = resp.getContent()) != null) {

            StartAI.getInstance().getBaseBusiManager().
                    thirdPaymentUnifiedOrder(content.toThirdPayRequestBean(), mThirdPayBalanceLsn);

        } else {

            ThirdPayResponseMethod rechargerResponseMethod =
                    ThirdPayResponseMethod.getThirdPayResponseMethod();
            rechargerResponseMethod.setResult(false);
            rechargerResponseMethod.setErrorCode(resp.getContent().getErrcode());
            callJs(rechargerResponseMethod);

        }

    }

    //  请求查询余额
    @Override
    public void onGetBalanceAndDepositResult(C_0x8308.Resp resp) {

        if (Debuger.isLogDebug) {
            Tlog.d(TAG, " onGetBalanceAndDepositResult :" + String.valueOf(resp));
        }

        BalanceDepositResponseMethod mBalanceDepositResponseMethod =
                BalanceDepositResponseMethod.getBalanceDepositResponseMethod();
        mBalanceDepositResponseMethod.setResult(resp.getResult() == 1);
        C_0x8308.Resp.ContentBean content = resp.getContent();
        mBalanceDepositResponseMethod.setContent(content);
        if (content != null) {
            mBalanceDepositResponseMethod.setErrorCode(content.getErrcode());
        }
        callJs(mBalanceDepositResponseMethod);
    }

    // 查询订单列表
    @Override
    public void onGetOrderListResult(C_0x8309.Resp resp) {

        if (Debuger.isLogDebug) {
            Tlog.d(TAG, " onGetOrderListResult :" + String.valueOf(resp));
        }

        OrderListResponseMethod orderListResponseMethod =
                OrderListResponseMethod.getOrderListResponseMethod();
        orderListResponseMethod.setResult(resp.getResult() == BaseMessage.RESULT_SUCCESS);
        C_0x8309.Resp.ContentBean content = resp.getContent();
        orderListResponseMethod.setOrderList(content);
        if (content != null) {
            orderListResponseMethod.setErrorCode(content.getErrcode());
        }
        callJs(orderListResponseMethod);
    }

    // 查询订单详情
    @Override
    public void onGetOrderDetailResult(C_0x8310.Resp resp) {

        if (Debuger.isLogDebug) {
            Tlog.d(TAG, " onGetOrderDetailResult :" + String.valueOf(resp));
        }

        OrderDetailResponseMethod orderDetailResponseMethod =
                OrderDetailResponseMethod.getOrderDetailResponseMethod();
        orderDetailResponseMethod.setResult(resp.getResult() == 1);
        C_0x8310.Resp.ContentBean content = resp.getContent();
        orderDetailResponseMethod.setContent(content);
        if (content != null) {
            orderDetailResponseMethod.setErrorCode(content.getErrcode());
        }
        callJs(orderDetailResponseMethod);

    }

    // 请求用余额支付订单
    @Override
    public void onPayForOrderWithBalanceResult(C_0x8311.Resp resp) {

        if (Debuger.isLogDebug) {
            Tlog.d(TAG, " onPayForOrderWithBalanceResult :" + String.valueOf(resp));
        }

        BalancePayResponseMethod balancePayResponseMethod =
                BalancePayResponseMethod.getBalancePayResponseMethod();
        balancePayResponseMethod.setResult(resp.getResult() == 1);
        C_0x8311.Resp.ContentBean content = resp.getContent();
        balancePayResponseMethod.setContent(content);
        if (content != null) {
            balancePayResponseMethod.setErrorCode(content.getErrcode());
        }
        callJs(balancePayResponseMethod);
    }

    @Override
    public void onGetUserChargingStatusResult(C_0x8315.Resp resp) {

        if (Debuger.isLogDebug) {
            Tlog.d(TAG, " onGetUserChargingStatusResult :" + String.valueOf(resp));
        }

        ChargingStatusResponseMethod chargingStatusResponseMethod =
                ChargingStatusResponseMethod.getChargingStatusResponseMethod();
        chargingStatusResponseMethod.setResult(resp.getResult() == 1);
        C_0x8315.Resp.ContentBean content = resp.getContent();
        chargingStatusResponseMethod.setContent(content);
        chargingStatusResponseMethod.setErrorCode(content.getErrcode());
        callJs(chargingStatusResponseMethod);

    }

}
