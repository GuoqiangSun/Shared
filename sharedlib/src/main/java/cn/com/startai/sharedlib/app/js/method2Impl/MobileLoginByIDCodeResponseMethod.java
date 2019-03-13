package cn.com.startai.sharedlib.app.js.method2Impl;

import org.json.JSONException;
import org.json.JSONObject;

import cn.com.swain.baselib.jsInterface.response.BaseResponseMethod2;
import cn.com.startai.sharedlib.app.js.Utils.JsMsgType;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/28 0028
 * desc :
 */
public class MobileLoginByIDCodeResponseMethod extends BaseResponseMethod2 {

    public static MobileLoginByIDCodeResponseMethod getMobileLoginByIDCodeResponseMethod() {
        return new MobileLoginByIDCodeResponseMethod();
    }

    private MobileLoginByIDCodeResponseMethod() {
        super(JsMsgType.TYPE_RESPONSE_MOBILE_LOGIN_BY_IDCODE);
    }

    @Override
    public void releaseCache() {
        super.releaseCache();

        isLogin = false;

    }

    private boolean isLogin;

    public void setIsLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }

    @Override
    public String toMethod() {

        JSONObject contentObj = new JSONObject();
        if (getResult()) {
            try {
                contentObj.put("isLogin", isLogin);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return toMethod(contentObj);

    }
}
