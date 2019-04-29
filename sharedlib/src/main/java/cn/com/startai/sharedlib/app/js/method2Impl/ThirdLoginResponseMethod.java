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
public class ThirdLoginResponseMethod extends BaseResponseMethod2 {

    public static ThirdLoginResponseMethod getThirdLoginResponseMethod() {
        return new ThirdLoginResponseMethod();
    }

    private ThirdLoginResponseMethod() {
        super(JsMsgType.TYPE_RESPONSE_THIRD_LOGIN);
    }

    @Override
    public void releaseCache() {
        super.releaseCache();
        isLogin = false;
        type = null;
    }

    private boolean isLogin;

    public void setIsLogin(boolean isLogin) {
        this.isLogin = isLogin;
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
            contentObj.put("state", isLogin);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return toMethod(contentObj);

    }
}
