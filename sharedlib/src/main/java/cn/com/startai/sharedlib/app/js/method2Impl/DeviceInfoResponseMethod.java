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
public class DeviceInfoResponseMethod extends BaseResponseMethod2 {


    public static DeviceInfoResponseMethod getDeviceInfoResponseMethod() {
        return new DeviceInfoResponseMethod();
    }


    public DeviceInfoResponseMethod() {
        super(JsMsgType.TYPE_RESPONSE_QUERY_DEVICE_INFO);
    }

    @Override
    public void releaseCache() {
        super.releaseCache();

        isOnline = false;

    }

    private boolean isOnline;

    public void setIsOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

    @Override
    public String toMethod() {

        JSONObject contentObj = new JSONObject();
        if (getResult()) {
            try {
                contentObj.put("isOnline", isOnline);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return toMethod(contentObj);

    }
}
