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
public class LocationEnabledResponseMethod extends BaseResponseMethod2 {

    public static LocationEnabledResponseMethod getLocationEnabledResponseMethod() {
        return new LocationEnabledResponseMethod();
    }

    private LocationEnabledResponseMethod() {
        super(JsMsgType.TYPE_RESPONSE_LOCATION_ENABLED);
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

    @Override
    public String toMethod() {

        JSONObject contentObj = new JSONObject();
        try {
            contentObj.put("success", enabled);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return toMethod(contentObj);

    }


}
