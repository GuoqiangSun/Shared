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
public class AppInstallResponseMethod extends BaseResponseMethod2 {

    public static AppInstallResponseMethod getAppInstallResponseMethod() {
        return new AppInstallResponseMethod();
    }

    private AppInstallResponseMethod() {
        super(JsMsgType.TYPE_RESPONSE_IS_INSTALL);
    }

    @Override
    public void releaseCache() {
        super.releaseCache();
        state = false;
        type = 0;
    }

    private boolean state;

    public void setState(boolean state) {
        this.state = state;
    }

    public void setType(int type) {
        this.type = type;
    }

    private int type;


    @Override
    public String toMethod() {

        JSONObject contentObj = new JSONObject();
        try {
            contentObj.put("type", type);
            contentObj.put("state", state);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return toMethod(contentObj);

    }
}
