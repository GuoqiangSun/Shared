package cn.com.startai.sharedlib.app.mutual;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Looper;

import com.tencent.mm.opensdk.modelbase.BaseResp;

import cn.com.startai.chargersdk.ChargerBusiHandler;
import cn.com.startai.chargersdk.PersistentEventChargerDispatcher;
import cn.com.startai.mqttsdk.StartAI;
import cn.com.startai.mqttsdk.busi.entity.C_0x8018;
import cn.com.startai.mqttsdk.event.ICommonStateListener;
import cn.com.startai.mqttsdk.localbusi.UserBusi;
import cn.com.startai.mqttsdk.mqtt.MqttInitParam;
import cn.com.startai.sharedlib.BuildConfig;
import cn.com.startai.sharedlib.app.Debuger;
import cn.com.startai.sharedlib.app.LooperManager;
import cn.com.startai.sharedlib.app.info.RuiooChargerDeveloperInfo;
import cn.com.startai.sharedlib.app.js.CommonJsInterfaceTask;
import cn.com.startai.sharedlib.app.js.method2Impl.MqttStatusResponseMethod;
import cn.com.startai.sharedlib.app.js.method2Impl.NetworkStatusResponseMethod;
import cn.com.startai.sharedlib.app.mutual.impl.JsRequestInterfaceImpl;
import cn.com.startai.sharedlib.app.mutual.impl.MqttLsnImpl;
import cn.com.swain.baselib.app.IApp.IService;
import cn.com.swain.baselib.jsInterface.AbsJsInterface;
import cn.com.swain.baselib.log.Tlog;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/27 0027
 * desc :
 */
public class MutualManager implements IService, ICommonStateListener {

    public static final String TAG = AbsJsInterface.TAG;

    private AbsJsInterface mJsInterface;

    public AbsJsInterface getJsInterface() {
        return mJsInterface;
    }

    private final Application app;
    private final IMutualCallBack mCallBack;

    public MutualManager(Application app, IMutualCallBack mCallBack) {
        this.app = app;
        this.mCallBack = mCallBack;
    }

    private String userID;

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserID() {
//        if (Debuger.isDebug) {
//            return "3a5f4bafe52943e286c7ee7240d52a42";
//        }
        return userID;
    }

    public String getUserIDFromMq() {
        UserBusi userBusi = new UserBusi();
        C_0x8018.Resp.ContentBean currUser = userBusi.getCurrUser();
        if (currUser != null) {
            return currUser.getUserid();
        }
        return null;
    }

    private JsRequestInterfaceImpl jsRequestInterfaceImpl;
    private MqttLsnImpl mMqttLstImpl;

    @Override
    public void onSCreate() {

        Tlog.v(TAG, "mutualManager onSCreate() debug:" + BuildConfig.DEBUG);


        // mqtt
        MqttInitParam initParam = new RuiooChargerDeveloperInfo();

        StartAI.getInstance().initialization(app, initParam);
        StartAI.getInstance().getPersisitnet().setBusiHandler(new ChargerBusiHandler());
        StartAI.getInstance().getPersisitnet().setEventDispatcher(PersistentEventChargerDispatcher.getInstance());

        StartAI.getInstance().getPersisitnet().getEventDispatcher().registerOnTunnelStateListener(this);

        mMqttLstImpl = new MqttLsnImpl(app, mCallBack, this);
        StartAI.getInstance().getPersisitnet().getEventDispatcher().registerOnPushListener(mMqttLstImpl);


        // js
        Looper workLooper = LooperManager.getInstance().getWorkLooper();
        jsRequestInterfaceImpl = new JsRequestInterfaceImpl(app, mCallBack, this);
        mJsInterface = new CommonJsInterfaceTask(workLooper, jsRequestInterfaceImpl);

        // network
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        app.registerReceiver(mNetWorkStateReceiver, filter);

    }

    @Override
    public void onSResume() {
        Tlog.v(TAG, "mutualManager onSResume() ");
    }

    @Override
    public void onSPause() {
        Tlog.v(TAG, "mutualManager onSPause() ");
    }

    @Override
    public void onSFinish() {
        Tlog.v(TAG, "mutualManager onSFinish() ");
        if (mJsInterface != null) {
            mJsInterface.release();
        }

    }

    @Override
    public void onSDestroy() {
        Tlog.v(TAG, "mutualManager onSDestroy() ");

        jsRequestInterfaceImpl = null;
        mMqttLstImpl = null;

        if (app != null) {
            app.unregisterReceiver(mNetWorkStateReceiver);
        }

        if (mJsInterface != null) {
            mJsInterface.release();
            mJsInterface = null;
        }

        StartAI.getInstance().unInit();
    }

    public void onWxLoginResult(BaseResp baseResp) {
        if (mMqttLstImpl != null) {
            mMqttLstImpl.onWxLoginResult(baseResp);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (jsRequestInterfaceImpl != null) {
            jsRequestInterfaceImpl.onActivityResult(requestCode, resultCode, data);
        }
    }


    public void onWxPayResult(BaseResp resp) {
        if (mMqttLstImpl != null) {
            mMqttLstImpl.onWxPayResult(resp);
        }
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

    @Override
    public void onDisconnect(int errorCode, String errorMsg) {
        Tlog.e(TAG, " mqtt onDisconnect -->" + errorCode + " :" + errorMsg);
    }

    @Override
    public void onConnectFail(int errorCode, String errorMsg) {
        Tlog.e(TAG, " mqtt onConnectFail -->" + errorCode + " :" + errorMsg);

        MqttStatusResponseMethod networkStatusResponseMethod = MqttStatusResponseMethod.getNetworkStatusResponseMethod();
        networkStatusResponseMethod.setResult(true);
        networkStatusResponseMethod.setConnect(true);
        networkStatusResponseMethod.setCode(String.valueOf(errorCode));
        networkStatusResponseMethod.setTypeIsWideNetwork();
        if (mCallBack != null) {
            mCallBack.callJs(networkStatusResponseMethod.toMethod());
        }
    }

    @Override
    public void onConnected() {
        Tlog.e(TAG, " mqtt onConnected ");

        MqttStatusResponseMethod networkStatusResponseMethod = MqttStatusResponseMethod.getNetworkStatusResponseMethod();
        networkStatusResponseMethod.setResult(true);
        networkStatusResponseMethod.setConnect(true);
        networkStatusResponseMethod.setTypeIsWideNetwork();
        if (mCallBack != null) {
            mCallBack.callJs(networkStatusResponseMethod.toMethod());
        }

    }

    private final NetworkStatusResponseMethod networkStatusResponseMethod = NetworkStatusResponseMethod.getNetworkStatusResponseMethod();

    private final BroadcastReceiver mNetWorkStateReceiver = new BroadcastReceiver() {
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

            if (mCallBack != null) {
                mCallBack.callJs(networkStatusResponseMethod.toMethod());
            }
        }
    };


}
