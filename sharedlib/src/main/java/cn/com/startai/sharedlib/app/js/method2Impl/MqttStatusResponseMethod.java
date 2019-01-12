package cn.com.startai.sharedlib.app.js.method2Impl;

import org.json.JSONException;
import org.json.JSONObject;

import cn.com.startai.sharedlib.app.js.Utils.JsMsgType;
import cn.com.swain.baselib.jsInterface.method.BaseResponseMethod2;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/28 0028
 * desc :
 */
public class MqttStatusResponseMethod extends BaseResponseMethod2 {

    public static MqttStatusResponseMethod getNetworkStatusResponseMethod() {
        return new MqttStatusResponseMethod();
    }

    public MqttStatusResponseMethod() {
        super(JsMsgType.TYPE_RESPONSE_MQTT);
    }

    @Override
    public void releaseCache() {
        super.releaseCache();

        type = "";
        connect = false;

    }

    public static final String NETWORK_WAN = "wideNetwork";
    public static final String NETWORK_LAN = "localNetwork ";

    public void setTypeIsWideNetwork() {
        type = NETWORK_WAN;
    }

    public void setTypeIsLocalNetwork() {
        type = NETWORK_WAN;
    }

    private String type;

    public void setType(String type) {
        this.type = type;
    }

    private boolean connect;

    public void setConnect(boolean connect) {
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
                contentObj.put("status", connect);
                contentObj.put("code", code);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return toMethod(contentObj);

    }
}
