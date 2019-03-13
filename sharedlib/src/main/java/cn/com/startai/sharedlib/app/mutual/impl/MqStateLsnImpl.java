package cn.com.startai.sharedlib.app.mutual.impl;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

import cn.com.startai.mqttsdk.busi.entity.C_0x8018;
import cn.com.startai.mqttsdk.event.ICommonStateListener;
import cn.com.startai.sharedlib.app.global.Debuger;
import cn.com.startai.sharedlib.app.js.method2Impl.MqttStatusResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.NetworkStatusResponseMethod;
import cn.com.startai.sharedlib.app.mutual.IMutualCallBack;
import cn.com.swain.baselib.log.Tlog;

/**
 * author: Guoqiang_Sun
 * date: 2019/1/23 0023
 * Desc: mqtt state
 */
public class MqStateLsnImpl extends MutualSharedWrapper implements ICommonStateListener {

    public MqStateLsnImpl(IMutualCallBack mCallBack) {
        super(mCallBack);
    }

    @Override
    public void onTokenExpire(C_0x8018.Resp.ContentBean resp) {
        if (Debuger.isLogDebug) {
            Tlog.e(TAG, "MQTT onConnectExpire :" + String.valueOf(resp));
        }
    }

    private MqttStatusResponseMethod mqttDisconnectStatusResponseMethod =
            MqttStatusResponseMethod.getMqttStatusResponseMethod();

    @Override
    public void onDisconnect(int errorCode, String errorMsg) {
        Tlog.e(TAG, " mqtt onDisconnect -->" + errorCode + " :" + errorMsg);
        mqttDisconnectStatusResponseMethod.setResult(false);
        mqttDisconnectStatusResponseMethod.setConnect(false);
        mqttDisconnectStatusResponseMethod.setCode(String.valueOf(errorCode));
        mqttDisconnectStatusResponseMethod.setErrorCode(String.valueOf(errorCode));
        callJs(mqttDisconnectStatusResponseMethod);
    }

    private MqttStatusResponseMethod mqttConFailStatusResponseMethod =
            MqttStatusResponseMethod.getMqttStatusResponseMethod();

    @Override
    public void onConnectFail(int errorCode, String errorMsg) {
        Tlog.e(TAG, " mqtt onConnectFail -->" + errorCode + " :" + errorMsg);

        mqttConFailStatusResponseMethod.setResult(false);
        mqttConFailStatusResponseMethod.setConnect(false);
        mqttConFailStatusResponseMethod.setCode(String.valueOf(errorCode));
        mqttConFailStatusResponseMethod.setErrorCode(String.valueOf(errorCode));
        callJs(mqttConFailStatusResponseMethod);
    }

    private MqttStatusResponseMethod mqttConnectStatusResponseMethod =
            MqttStatusResponseMethod.getMqttStatusResponseMethod();

    @Override
    public void onConnected() {
        Tlog.e(TAG, " mqtt onConnected ");

        mqttConnectStatusResponseMethod.setResult(true);
        mqttConnectStatusResponseMethod.setConnect(true);
        mqttConnectStatusResponseMethod.setNetworkTypeIsWAN();
        callJs(mqttConnectStatusResponseMethod);

    }


    public void registerNetworkReceiver(Application app) {

        // network
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        if (app != null) {
            app.registerReceiver(mNetWorkStateReceiver, filter);
        } else {
            getActivity().registerReceiver(mNetWorkStateReceiver, filter);
        }
    }

    public void unregNetworkReceiver(Application app) {
        if (app != null) {
            app.unregisterReceiver(mNetWorkStateReceiver);
        } else {
            getActivity().unregisterReceiver(mNetWorkStateReceiver);
        }
    }


    private NetworkStatusResponseMethod networkStatusResponseMethod =
            NetworkStatusResponseMethod.getNetworkStatusResponseMethod();

    private BroadcastReceiver mNetWorkStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Tlog.v(TAG, " android.net.conn.CONNECTIVITY_CHANGE " + intent.getAction());

            //获得ConnectivityManager对象
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            if (connMgr == null) {
                Tlog.e(TAG, "ConnectivityManager==null");
                return;
            }

            boolean wifiConnected = false;
            String type;
            NetworkInfo.State state;

            //获取WIFI连接的信息
            NetworkInfo wifiNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

            if (wifiNetworkInfo.isConnected()) {

                Tlog.v(TAG, "WIFI isConnected ");
                wifiConnected = true;
                type = wifiNetworkInfo.getTypeName();
                state = wifiNetworkInfo.getState();

            } else if (wifiNetworkInfo.isConnectedOrConnecting()) {

                Tlog.v(TAG, "WIFI isConnecting");
                type = wifiNetworkInfo.getTypeName();
                state = wifiNetworkInfo.getState();

            } else {

                //获取移动数据连接的信息
                NetworkInfo dataNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

                if (dataNetworkInfo.isConnected()) {

                    Tlog.v(TAG, "mobile isConnected ");
                    type = dataNetworkInfo.getTypeName();
                    state = dataNetworkInfo.getState();

                } else if (dataNetworkInfo.isConnectedOrConnecting()) {

                    Tlog.v(TAG, "mobile isConnecting");
                    type = dataNetworkInfo.getTypeName();
                    state = dataNetworkInfo.getState();

                } else {

                    Tlog.v(TAG, " unknown network ");
                    type = NetworkStatusResponseMethod.NETWORK_NONE;
                    state = NetworkInfo.State.UNKNOWN;

                }
            }

//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//            //获取所有网络连接的信息
//            Network[] networks = connMgr.getAllNetworks();
//
//            //通过循环将网络信息逐个取出来
//            for (Network network : networks) {
//                //获取ConnectivityManager对象对应的NetworkInfo对象
//                NetworkInfo networkInfo = connMgr.getNetworkInfo(network);
//                ｝
//        }

            networkStatusResponseMethod.setResult(true);
            networkStatusResponseMethod.setType(type);
            networkStatusResponseMethod.setState(NetworkStatusResponseMethod.changeState(state));
            callJs(networkStatusResponseMethod.toMethod());
        }
    };


}
