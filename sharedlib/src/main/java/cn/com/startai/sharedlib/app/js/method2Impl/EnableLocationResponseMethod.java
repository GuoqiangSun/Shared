package cn.com.startai.sharedlib.app.js.method2Impl;

import org.json.JSONException;
import org.json.JSONObject;

import cn.com.startai.sharedlib.app.js.Utils.JsMsgType;
import cn.com.swain.baselib.jsInterface.response.BaseResponseMethod2;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/28 0028
 * desc :
 */
public class EnableLocationResponseMethod extends BaseResponseMethod2 {

    public static EnableLocationResponseMethod getEnableLocationResponseMethod() {
        return new EnableLocationResponseMethod();
    }

    private EnableLocationResponseMethod() {
        super(JsMsgType.TYPE_RESPONSE_ENABLE_LOCATION);
    }

    @Override
    public void releaseCache() {
        super.releaseCache();

        enabled = false;
    }


    private boolean enabled;

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    private int type;

    public void setType(int type) {
        this.type = type;
    }

    public void setGpsIpType() {
        this.type = JsMsgType.LOCATION_GPS_IP;
    }

    public void setGpsType() {
        this.type = JsMsgType.LOCATION_GPS;
    }

    public void setIpType() {
        this.type = JsMsgType.LOCATION_IP;
    }

    @Override
    public String toMethod() {

        JSONObject contentObj = new JSONObject();
        try {
            contentObj.put("success", enabled);
            contentObj.put("type", type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return toMethod(contentObj);

    }


}
