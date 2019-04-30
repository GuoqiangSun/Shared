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
public class LocationDataResponseMethod extends BaseResponseMethod2 {

    public static LocationDataResponseMethod getLocationDataResponseMethod() {
        return new LocationDataResponseMethod();
    }

    private LocationDataResponseMethod() {
        super(JsMsgType.TYPE_RESPONSE_LOCATION_DATA);
    }

    @Override
    public void releaseCache() {
        super.releaseCache();

        enabled = false;
        lng = null;
        lat = null;
    }


    private boolean enabled;

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    private String lng;
    private String lat;

    public void setLng(String lng) {
        this.lng = lng;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    @Override
    public String toMethod() {

        JSONObject contentObj = new JSONObject();
        try {
            contentObj.put("success", enabled);
            contentObj.put("lng", lng);
            contentObj.put("lat", lat);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return toMethod(contentObj);

    }


}
