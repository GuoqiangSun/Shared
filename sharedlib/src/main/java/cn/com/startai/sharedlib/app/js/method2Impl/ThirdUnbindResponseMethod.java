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
public class ThirdUnbindResponseMethod extends BaseResponseMethod2 {

    public static ThirdUnbindResponseMethod getThirdUnbindResponseMethod() {
        return new ThirdUnbindResponseMethod();
    }

    private ThirdUnbindResponseMethod() {
        super(JsMsgType.TYPE_RESPONSE_THIRD_UNBIND);
    }

    @Override
    public void releaseCache() {
        super.releaseCache();
        isUnbind = false;
        type = null;
    }

    private boolean isUnbind;

    public void setUnbind(boolean isLogin) {
        this.isUnbind = isLogin;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String type;

    public void setGoogleType() {
        this.type = JsMsgType.THIRD_LOGIN_TYPE_GOOGLE;
    }

    public void setTwitterType() {
        this.type = JsMsgType.THIRD_LOGIN_TYPE_TWITTER;
    }

    public void setFacebookType() {
        this.type = JsMsgType.THIRD_LOGIN_TYPE_FACEBOOK;
    }

    @Override
    public String toMethod() {

        JSONObject contentObj = new JSONObject();
        try {
            contentObj.put("type", type);
            contentObj.put("unbind", isUnbind);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return toMethod(contentObj);

    }
}
