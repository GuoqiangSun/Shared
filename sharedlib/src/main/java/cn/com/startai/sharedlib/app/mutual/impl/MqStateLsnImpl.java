package cn.com.startai.sharedlib.app.mutual.impl;

import cn.com.startai.mqttsdk.busi.entity.C_0x8018;
import cn.com.startai.mqttsdk.event.ICommonStateListener;
import cn.com.startai.sharedlib.app.global.Debuger;
import cn.com.startai.sharedlib.app.js.method2Impl.MqttStatusResponseMethod;
import cn.com.startai.sharedlib.app.mutual.IMutualCallBack;
import cn.com.swain.baselib.log.Tlog;

/**
 * author: Guoqiang_Sun
 * date: 2019/1/23 0023
 * Desc:
 */
public class MqStateLsnImpl extends MutualCallBackWrapper implements ICommonStateListener {

    public MqStateLsnImpl(IMutualCallBack mCallBack) {
        super(mCallBack);
    }

    @Override
    public boolean needUISafety() {
        return false;
    }

    @Override
    public void onTokenExpire(C_0x8018.Resp.ContentBean resp) {
        if (Debuger.isLogDebug) {
            Tlog.e(TAG, "MQTT onConnectExpire :" + String.valueOf(resp));
        }
    }

    private MqttStatusResponseMethod networkDisconnectStatusResponseMethod = MqttStatusResponseMethod.getMqttStatusResponseMethod();

    @Override
    public void onDisconnect(int errorCode, String errorMsg) {
        Tlog.e(TAG, " mqtt onDisconnect -->" + errorCode + " :" + errorMsg);
        networkDisconnectStatusResponseMethod.setResult(false);
        networkDisconnectStatusResponseMethod.setConnect(false);
        networkDisconnectStatusResponseMethod.setCode(String.valueOf(errorCode));
        networkDisconnectStatusResponseMethod.setErrorCode(String.valueOf(errorCode));
        callJs(networkDisconnectStatusResponseMethod);
    }

    private MqttStatusResponseMethod networkConFailStatusResponseMethod = MqttStatusResponseMethod.getMqttStatusResponseMethod();

    @Override
    public void onConnectFail(int errorCode, String errorMsg) {
        Tlog.e(TAG, " mqtt onConnectFail -->" + errorCode + " :" + errorMsg);

        networkConFailStatusResponseMethod.setResult(true);
        networkConFailStatusResponseMethod.setConnect(true);
        networkConFailStatusResponseMethod.setCode(String.valueOf(errorCode));
        networkConFailStatusResponseMethod.setTypeIsWideNetwork();
        callJs(networkConFailStatusResponseMethod);
    }

    private MqttStatusResponseMethod networkConnectStatusResponseMethod = MqttStatusResponseMethod.getMqttStatusResponseMethod();

    @Override
    public void onConnected() {
        Tlog.e(TAG, " mqtt onConnected ");

        networkConnectStatusResponseMethod.setResult(true);
        networkConnectStatusResponseMethod.setConnect(true);
        networkConnectStatusResponseMethod.setTypeIsWideNetwork();
        callJs(networkConnectStatusResponseMethod);

    }


}
