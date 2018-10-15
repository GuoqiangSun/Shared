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
public class GetAppVersionResponseMethod extends BaseResponseMethod2 {

    public static GetAppVersionResponseMethod getAppVersionResponseMethod() {
        return new GetAppVersionResponseMethod();
    }

    public GetAppVersionResponseMethod() {
        super(JsMsgType.TYPE_RESPONSE_APP_VERSION);
    }

    @Override
    public void releaseCache() {
        super.releaseCache();

        version = null;

    }

    private String version;

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toMethod() {

        JSONObject contentObj = new JSONObject();
        if (getResult()) {
            try {
                contentObj.put("version", version);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return toMethod(contentObj);

    }
}
