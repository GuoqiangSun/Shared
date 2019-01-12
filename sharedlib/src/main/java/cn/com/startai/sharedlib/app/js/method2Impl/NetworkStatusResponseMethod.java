package cn.com.startai.sharedlib.app.js.method2Impl;

import android.net.NetworkInfo;

import org.json.JSONException;
import org.json.JSONObject;

import cn.com.startai.sharedlib.app.js.Utils.JsMsgType;
import cn.com.swain.baselib.jsInterface.method.BaseResponseMethod2;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/28 0028
 * desc :
 */
public class NetworkStatusResponseMethod extends BaseResponseMethod2 {

    public static NetworkStatusResponseMethod getNetworkStatusResponseMethod() {
        return new NetworkStatusResponseMethod();
    }

    public NetworkStatusResponseMethod() {
        super(JsMsgType.TYPE_RESPONSE_NETWORK);
    }

    @Override
    public void releaseCache() {
        super.releaseCache();

        type = null;
        connect = 0;
        code = null;
    }

//    WIFI、MOBILE、NONE

    public static final String NETWORK_WIFI = "WIFI";
    public static final String NETWORK_MOBILE = "MOBILE";
    public static final String NETWORK_NONE = "NONE";

    public void setTypeIsWiFi() {
        type = NETWORK_WIFI;
    }

    public void setTypeIsMobile() {
        type = NETWORK_MOBILE;
    }

    public void setTypeIsNone() {
        type = NETWORK_NONE;
    }

    private String type;

    public void setType(String type) {
        this.type = type;
    }


    public static final int CONNECTED = 1;
    public static final int CONNECTING = 2;
    public static final int DISCONNECTED = 3;
    public static final int DISCONNECTING = 4;
    public static final int UNKNOWN = 5;

    public static int changeState(NetworkInfo.State state) {

        int s;
        switch (state) {

            case CONNECTED:
                s = CONNECTED;
                break;
            case CONNECTING:
                s = CONNECTING;
                break;
            case DISCONNECTED:
                s = DISCONNECTED;
                break;
            case DISCONNECTING:
                s = DISCONNECTING;
                break;
            default:
                s = UNKNOWN;
                break;
        }
        return s;
    }


    // 1、连接 2、连接中 3、断开 4、断开中 5、UNKOWN

    private int connect;

    public void setState(int connect) {
        this.connect = connect;
    }

    private String code;

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toMethod() {

        JSONObject contentObj = new JSONObject();
        if (getResult()) {
            try {
                contentObj.put("type", type);
                contentObj.put("state", connect);
                contentObj.put("code", code);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return toMethod(contentObj);

    }
}
