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
public class MapNavResponseMethod extends BaseResponseMethod2 {

    public static MapNavResponseMethod getMapNavResponseMethod() {
        return new MapNavResponseMethod();
    }

    private MapNavResponseMethod() {
        super(JsMsgType.TYPE_RESPONSE_MAP_NAV);
    }

    @Override
    public void releaseCache() {
        super.releaseCache();
        success = false;
        type = 0;
    }

    private boolean success;

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setType(int type) {
        this.type = type;
    }

    private int type;


    @Override
    public String toMethod() {

        JSONObject contentObj = new JSONObject();
        try {
            contentObj.put("success", success);
            contentObj.put("type", type);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return toMethod(contentObj);

    }
}
