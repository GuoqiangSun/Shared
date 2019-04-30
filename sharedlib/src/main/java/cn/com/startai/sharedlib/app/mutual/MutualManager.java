package cn.com.startai.sharedlib.app.mutual;

import android.app.Application;
import android.content.Intent;

import com.tencent.mm.opensdk.modelbase.BaseResp;

import cn.com.startai.chargersdk.ChargerBusiHandler;
import cn.com.startai.chargersdk.PersistentEventChargerDispatcher;
import cn.com.startai.mqttsdk.StartAI;
import cn.com.startai.mqttsdk.base.DistributeParam;
import cn.com.startai.mqttsdk.localbusi.SUserManager;
import cn.com.startai.mqttsdk.mqtt.MqttInitParam;
import cn.com.startai.sharedlib.BuildConfig;
import cn.com.startai.sharedlib.app.global.LooperManager;
import cn.com.startai.sharedlib.app.info.DeveloperInfoFactory;
import cn.com.startai.sharedlib.app.js.ChargerJsInterfaceTask;
import cn.com.startai.sharedlib.app.mutual.impl.MqStateLsnImpl;
import cn.com.startai.sharedlib.app.mutual.impl.charger.ChargerJsRequestInterfaceImpl;
import cn.com.startai.sharedlib.app.mutual.impl.charger.ChargerMqttLsnImpl;
import cn.com.swain.baselib.app.IApp.IService;
import cn.com.swain.baselib.jsInterface.AbsJsInterface;
import cn.com.swain.baselib.log.Tlog;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/27 0027
 * desc :
 */
public class MutualManager implements IUserIDManager, IService {

    public static final String TAG = AbsJsInterface.TAG;

    private final Application app;
    private final IMutualCallBack mCallBack;

    public MutualManager(Application app, IMutualCallBack mCallBack) {
        this.app = app;
        this.mCallBack = mCallBack;
    }

    private String userID;

    @Override
    public void setUserID(String userID) {
        this.userID = userID;
    }

    @Override
    public String getUserID() {
//        if (Debuger.isDebug) {
//            return "3a5f4bafe52943e286c7ee7240d52a42";
//        }
        return userID;
    }

    @Override
    public String getUserIDFromMq() {
//        UserBusi userBusi = new UserBusi();
//        C_0x8018.Resp.ContentBean currUser = userBusi.getCurrUser();
//        return currUser != null ? currUser.getUserid() : null;

        return SUserManager.getInstance().getUserId();
    }

    private ChargerMqttLsnImpl mChargerMqttLsnImpl;
    private MqStateLsnImpl mMqStateLsnImpl;

    private AbsJsInterface mJsInterface;

    public AbsJsInterface getJsInterface() {
        return mJsInterface;
    }

    private ChargerJsRequestInterfaceImpl mChargerJsRequestInterfaceImpl;

    @Override
    public void onSCreate() {

        Tlog.v(TAG, "mutualManager onSCreate() debug:" + BuildConfig.DEBUG);

        // mqtt
        DistributeParam.THIRDPAYMENTUNIFIEDORDER_DISTRIBUTE = true;
        MqttInitParam mqttInitParam = DeveloperInfoFactory.produceMqttInitParam();
        if (mqttInitParam == null) {
            throw new NullPointerException(" not custom ");
        }
        StartAI.getInstance().initialization(app, mqttInitParam);
        StartAI.getInstance().getPersisitnet().setBusiHandler(new ChargerBusiHandler());
        StartAI.getInstance().getPersisitnet().setEventDispatcher(PersistentEventChargerDispatcher.getInstance());

        // mqtt 连接状态
        mMqStateLsnImpl = new MqStateLsnImpl(mCallBack);
        StartAI.getInstance().getPersisitnet().getEventDispatcher().registerOnTunnelStateListener(mMqStateLsnImpl);

        // 共享充电宝接口
        mChargerMqttLsnImpl = new ChargerMqttLsnImpl(app,
                mCallBack,
                this);
        StartAI.getInstance().getPersisitnet().getEventDispatcher().registerOnPushListener(mChargerMqttLsnImpl);

        // js
        mChargerJsRequestInterfaceImpl = new ChargerJsRequestInterfaceImpl(app,
                mCallBack,
                this);

        mJsInterface = new ChargerJsInterfaceTask(LooperManager.getInstance().getWorkLooper(),
                mChargerJsRequestInterfaceImpl);

        mMqStateLsnImpl.registerNetworkReceiver(app);

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
    }

    @Override
    public void onSDestroy() {
        Tlog.v(TAG, "mutualManager onSDestroy() ");

        if (mMqStateLsnImpl != null) {
            mMqStateLsnImpl.unregNetworkReceiver(app);
        }

        if (mJsInterface != null) {
            mJsInterface.release();
            mJsInterface = null;
        }

        StartAI.getInstance().unInit();
        StartAI.getInstance().getPersisitnet().getEventDispatcher().unregisterOnTunnelStateListener(mMqStateLsnImpl);
        StartAI.getInstance().getPersisitnet().getEventDispatcher().unregisterOnPushListener(mChargerMqttLsnImpl);

        mChargerJsRequestInterfaceImpl = null;
        mChargerMqttLsnImpl = null;
        mMqStateLsnImpl = null;
    }

    public void onWxEntryResult(BaseResp baseResp) {
        if (mChargerMqttLsnImpl != null) {
            mChargerMqttLsnImpl.onWxEntryResult(baseResp);
        }
    }

    public void onWxPayResult(BaseResp resp) {
        if (mChargerMqttLsnImpl != null) {
            mChargerMqttLsnImpl.onWxPayResult(resp);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mChargerJsRequestInterfaceImpl != null) {
            mChargerJsRequestInterfaceImpl.onActivityResult(requestCode, resultCode, data);
        }
    }

}
