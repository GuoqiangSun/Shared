package cn.com.startai.sharedlib.app.mutual.impl;

import cn.com.startai.chargersdk.AOnChargerMessageArriveListener;
import cn.com.startai.chargersdk.ChargerBusiManager;
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
import cn.com.startai.mqttsdk.base.BaseMessage;
import cn.com.startai.mqttsdk.base.StartaiError;
import cn.com.startai.mqttsdk.busi.entity.C_0x8001;
import cn.com.startai.mqttsdk.busi.entity.C_0x8002;
import cn.com.startai.mqttsdk.busi.entity.C_0x8003;
import cn.com.startai.mqttsdk.busi.entity.C_0x8004;
import cn.com.startai.mqttsdk.busi.entity.C_0x8005;
import cn.com.startai.mqttsdk.busi.entity.C_0x8015;
import cn.com.startai.mqttsdk.busi.entity.C_0x8016;
import cn.com.startai.mqttsdk.busi.entity.C_0x8017;
import cn.com.startai.mqttsdk.busi.entity.C_0x8018;
import cn.com.startai.mqttsdk.busi.entity.C_0x8020;
import cn.com.startai.mqttsdk.busi.entity.C_0x8021;
import cn.com.startai.mqttsdk.busi.entity.C_0x8022;
import cn.com.startai.mqttsdk.busi.entity.C_0x8023;
import cn.com.startai.mqttsdk.busi.entity.C_0x8024;
import cn.com.startai.mqttsdk.busi.entity.C_0x8025;
import cn.com.startai.mqttsdk.busi.entity.C_0x8026;
import cn.com.startai.mqttsdk.busi.entity.C_0x8028;
import cn.com.startai.mqttsdk.busi.entity.C_0x8031;
import cn.com.startai.mqttsdk.busi.entity.C_0x8033;
import cn.com.startai.mqttsdk.busi.entity.C_0x8034;
import cn.com.startai.mqttsdk.busi.entity.C_0x8035;
import cn.com.startai.mqttsdk.busi.entity.C_0x8036;
import cn.com.startai.mqttsdk.busi.entity.C_0x8037;
import cn.com.startai.mqttsdk.busi.entity.C_0x8200;
import cn.com.startai.mqttsdk.listener.IOnCallListener;
import cn.com.startai.mqttsdk.mqtt.request.MqttPublishRequest;
import cn.com.startai.sharedlib.app.global.Debuger;
import cn.com.startai.sharedlib.app.js.method2Impl.BalanceDepositResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.BalancePayResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.ChargerFeeRuleResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.ChargingStatusResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.DepositFeeRuleResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.DeviceInfoResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.GiveBackBorrowDeviceResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.NearByStoreByAreaResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.NearByStoreByMapResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.OrderDetailResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.OrderListResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.StoreInfoResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.ThirdPayResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.TransactionDetailResponseMethod;
import cn.com.startai.sharedlib.app.mutual.MutualManager;
import cn.com.swain.baselib.jsInterface.response.BaseResponseMethod;
import cn.com.swain.baselib.log.Tlog;

/**
 * author: Guoqiang_Sun
 * date: 2019/1/24 0024
 * Desc:
 */
public class ChargerMqttLsnImpl extends AOnChargerMessageArriveListener {

    protected String TAG = MutualManager.TAG;
    private final CommonMqttLsnImpl mCommonMqttLsnImpl;
    private MutualCallBackWrapper mutualCallBackWrapper;

    public ChargerMqttLsnImpl(CommonMqttLsnImpl mCommonMqttLsnImpl) {
        this.mCommonMqttLsnImpl = mCommonMqttLsnImpl;
        mutualCallBackWrapper = mCommonMqttLsnImpl.getMutualCallBackWrapper();
    }

    private void callJs(BaseResponseMethod mBaseResponseMethod) {
        mutualCallBackWrapper.callJs(mBaseResponseMethod);
    }

    @Override
    public void onCommand(String s, String s1) {
//        if (Debuger.isLogDebug) {
//            Tlog.d(TAG, " onCommand :" + String.valueOf(s) + "--" + String.valueOf(s1));
//        }
    }

    @Override
    public boolean needUISafety() {
        return false;
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
        super.onBorrowOrGiveBackResult(resp);

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
        super.onGetTransactionDetailsResult(resp);

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
        super.onGetStoreInfoResult(resp);
        if (Debuger.isLogDebug) {
            Tlog.d(TAG, " onGetStoreInfoResult :" + String.valueOf(resp));
        }

        StoreInfoResponseMethod storeInfoResponseMethod = StoreInfoResponseMethod.getStoreInfoResponseMethod();
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

    // v收费标准
    @Override
    public void onGetChargerFeeResult(C_0x8316.Resp resp) {
        super.onGetChargerFeeResult(resp);

        if (Debuger.isLogDebug) {
            Tlog.d(TAG, " onGetChargerFeeResult :" + String.valueOf(resp));
        }

        ChargerFeeRuleResponseMethod feeRuleResponseMethod = ChargerFeeRuleResponseMethod.getFeeRuleResponseMethod();
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
        super.onGetDepositFeeRuleResult(resp);

        if (Debuger.isLogDebug) {
            Tlog.d(TAG, " onGetDepositFeeRuleResult :" + String.valueOf(resp));
        }

        DepositFeeRuleResponseMethod feeRuleResponseMethod = DepositFeeRuleResponseMethod.getDepositFeeRuleResponseMethod();
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
        super.onGetNearbyStoresResult(resp);

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
        super.onGetNearbyStoreByAreaResult(resp);

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

        @Override
        public boolean needUISafety() {
            return false;
        }
    };

    // 请求充值
    @Override
    public void onRequestRechargeResult(C_0x8307.Resp resp) {
        super.onRequestRechargeResult(resp);

        if (Debuger.isLogDebug) {
            Tlog.d(TAG, " onRequestRechargeResult :" + String.valueOf(resp));
        }

        C_0x8307.Resp.ContentBean content;
        if (resp.getResult() == 1 && (content = resp.getContent()) != null) {

            ChargerBusiManager.getInstance().thirdPaymentUnifiedOrder(content.toThirdPayRequestBean(), mThirdPayBalanceLsn);

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
        super.onGetBalanceAndDepositResult(resp);

        if (Debuger.isLogDebug) {
            Tlog.d(TAG, " onGetBalanceAndDepositResult :" + String.valueOf(resp));
        }

        BalanceDepositResponseMethod mBalanceDepositResponseMethod = BalanceDepositResponseMethod.getBalanceDepositResponseMethod();
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
        super.onGetOrderListResult(resp);

        if (Debuger.isLogDebug) {
            Tlog.d(TAG, " onGetOrderListResult :" + String.valueOf(resp));
        }

        OrderListResponseMethod orderListResponseMethod = OrderListResponseMethod.getOrderListResponseMethod();
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
        super.onGetOrderDetailResult(resp);

        if (Debuger.isLogDebug) {
            Tlog.d(TAG, " onGetOrderDetailResult :" + String.valueOf(resp));
        }

        OrderDetailResponseMethod orderDetailResponseMethod = OrderDetailResponseMethod.getOrderDetailResponseMethod();
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
        super.onPayForOrderWithBalanceResult(resp);

        if (Debuger.isLogDebug) {
            Tlog.d(TAG, " onPayForOrderWithBalanceResult :" + String.valueOf(resp));
        }

        BalancePayResponseMethod balancePayResponseMethod = BalancePayResponseMethod.getBalancePayResponseMethod();
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
        super.onGetUserChargingStatusResult(resp);

        if (Debuger.isLogDebug) {
            Tlog.d(TAG, " onGetUserChargingStatusResult :" + String.valueOf(resp));
        }

        ChargingStatusResponseMethod charginStatusResponseMethod =
                ChargingStatusResponseMethod.getChargingStatusResponseMethod();
        charginStatusResponseMethod.setResult(resp.getResult() == 1);
        C_0x8315.Resp.ContentBean content = resp.getContent();
        charginStatusResponseMethod.setContent(content);
        charginStatusResponseMethod.setErrorCode(content.getErrcode());
        callJs(charginStatusResponseMethod);

    }

    /**********base mqtt ************/

    /**
     * 注册结果回调
     */
    @Override
    public void onRegisterResult(C_0x8017.Resp resp) {
        mCommonMqttLsnImpl.onRegisterResult(resp);
    }


    /**
     * 添加好友回调
     *
     * @param id        自己的id
     * @param bebinding 被绑定者 开发者需要持久化，在向对端发送消息时需要携带此bebinding的id
     */
    @Override
    public void onBindResult(C_0x8002.Resp resp, String id, C_0x8002.Resp.ContentBean.BebindingBean bebinding) {
        mCommonMqttLsnImpl.onBindResult(resp, id, bebinding);
    }


    /**
     * 删除好友回调
     *
     * @param beUnbindid 解绑对端
     */
    @Override
    public void onUnBindResult(C_0x8004.Resp resp, String id, String beUnbindid) {
        mCommonMqttLsnImpl.onUnBindResult(resp, id, beUnbindid);
    }


    /**
     * 登录 回调
     */
    @Override
    public void onLoginResult(C_0x8018.Resp resp) {
        mCommonMqttLsnImpl.onLoginResult(resp);
    }

    /**
     * 获取绑定关系列表回调
     */
    @Override
    public void onGetBindListResult(C_0x8005.Response response) {
        mCommonMqttLsnImpl.onGetBindListResult(response);
    }


    /**
     * 设备激活回调，如果激活成功只会回调一次
     */
    @Override
    public void onActiviteResult(C_0x8001.Resp resp) {
        mCommonMqttLsnImpl.onActiviteResult(resp);
    }


    /**
     * 获取验证码结果
     */
    @Override
    public void onGetIdentifyCodeResult(C_0x8021.Resp resp) {
        mCommonMqttLsnImpl.onGetIdentifyCodeResult(resp);
    }

    /**
     * 检验验证码结果
     */
    @Override
    public void onCheckIdetifyResult(C_0x8022.Resp resp) {
        mCommonMqttLsnImpl.onCheckIdetifyResult(resp);
    }


    /**
     * 消息透传结果
     *
     * @param dataString    回调的strintg 内容
     * @param dataByteArray 回调的byte[] 内容
     */
    @Override
    public void onPassthroughResult(C_0x8200.Resp resp, String dataString, byte[] dataByteArray) {
        mCommonMqttLsnImpl.onPassthroughResult(resp, dataString, dataByteArray);
    }


    /**
     * 登出
     */
    @Override
    public void onLogoutResult(int result, String errorCode, String errorMsg) {
        mCommonMqttLsnImpl.onLogoutResult(result, errorCode, errorMsg);

    }

    /**
     * 注销激活
     */
    @Override
    public void onUnActiviteResult(C_0x8003.Resp resp) {
        mCommonMqttLsnImpl.onUnActiviteResult(resp);
    }

    /**
     * 第三方硬件激活结果
     */
    @Override
    public void onHardwareActivateResult(C_0x8001.Resp resp) {
        mCommonMqttLsnImpl.onHardwareActivateResult(resp);
    }

    /**
     * 更新用户信息结果
     */
    @Override
    public void onUpdateUserInfoResult(C_0x8020.Resp resp) {
        mCommonMqttLsnImpl.onUpdateUserInfoResult(resp);
    }


    /**
     * 查询用户信息结果
     */
    @Override
    public void onGetUserInfoResult(C_0x8024.Resp resp) {
        mCommonMqttLsnImpl.onGetUserInfoResult(resp);
    }

    /**
     * 智能设备的连接状态变更
     *
     * @param userid 接收消息的userid
     * @param status 1 上线 0下线
     * @param sn     状态变更的设备sn
     */
    @Override
    public void onDeviceConnectStatusChange(String userid, int status, String sn) {
        mCommonMqttLsnImpl.onDeviceConnectStatusChange(userid, status, sn);
    }


    /**
     * 查询最新软件版本结果
     *
     * @param resp 最新软件版本信息
     */
    @Override
    public void onGetLatestVersionResult(C_0x8016.Resp resp) {
        mCommonMqttLsnImpl.onGetLatestVersionResult(resp);
    }


    /**
     * 更新用户密码返回
     *
     * @param resp 用户密码信息
     */
    @Override
    public void onUpdateUserPwdResult(C_0x8025.Resp resp) {
        mCommonMqttLsnImpl.onUpdateUserPwdResult(resp);
    }

    /**
     * 发送邮件结果返回
     *
     * @param resp 成功的信息
     */
    @Override
    public void onSendEmailResult(C_0x8023.Resp resp) {
        mCommonMqttLsnImpl.onSendEmailResult(resp);
    }


    /**
     * 修改备注名结果
     *
     * @param resp 成功内容
     */
    @Override
    public void onUpdateRemarkResult(C_0x8015.Resp resp) {
        mCommonMqttLsnImpl.onUpdateRemarkResult(resp);
    }


    /**
     * 重置手机登录密码结果
     *
     * @param resp 成功内容
     */
    @Override
    public void onResetMobileLoginPwdResult(C_0x8026.Resp resp) {
        mCommonMqttLsnImpl.onResetMobileLoginPwdResult(resp);
    }

    /**
     * 第三方支付 统一下单结果
     */
    @Override
    public void onThirdPaymentUnifiedOrderResult(C_0x8028.Resp resp) {
        mCommonMqttLsnImpl.onThirdPaymentUnifiedOrderResult(resp);
    }

    /**
     * 查询真实订单支付结果
     */
    @Override
    public void onGetRealOrderPayStatus(C_0x8031.Resp resp) {
        mCommonMqttLsnImpl.onGetRealOrderPayStatus(resp);
    }

    /**
     * 查询支付宝认证信息
     */
    @Override
    public void onGetAlipayAuthInfoResult(C_0x8033.Resp resp) {
        mCommonMqttLsnImpl.onGetAlipayAuthInfoResult(resp);
    }

    /**
     * 绑定手机号返回
     */
    @Override
    public void onBindMobileNumResult(C_0x8034.Resp resp) {
        mCommonMqttLsnImpl.onBindMobileNumResult(resp);
    }

    /**
     * 解绑第三方账号返回
     */
    @Override
    public void onUnBindThirdAccountResult(C_0x8036.Resp resp) {
        mCommonMqttLsnImpl.onUnBindThirdAccountResult(resp);
    }

    /**
     * 绑定第三方账号返回
     */
    @Override
    public void onBindThirdAccountResult(C_0x8037.Resp resp) {
        mCommonMqttLsnImpl.onBindThirdAccountResult(resp);
    }

    /**
     * 查询天气信息返回
     */
    @Override
    public void onGetWeatherInfoResult(C_0x8035.Resp resp) {
        mCommonMqttLsnImpl.onGetWeatherInfoResult(resp);
    }


}
