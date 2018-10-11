package cn.com.startai.sharedlib.app.js.method2Impl;

import org.json.JSONException;
import org.json.JSONObject;

import cn.com.swain.baselib.jsInterface.method.BaseResponseMethod2;
import cn.com.startai.sharedlib.app.js.Utils.SharedCommonJsUtils;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/28 0028
 * desc :
 */
public class LoginOutResponseMethod extends BaseResponseMethod2 {

    public static LoginOutResponseMethod getLoginOutResponseMethod() {
        return new LoginOutResponseMethod();
    }

    public LoginOutResponseMethod() {
        super(SharedCommonJsUtils.TYPE_RESPONSE_LOGIN_OUT);
    }

    @Override
    public void releaseCache() {
        super.releaseCache();
        isLoginOut = false;
    }

    private boolean isLoginOut;

    public void setIsLoginOut(boolean isLoginOut) {
        this.isLoginOut = isLoginOut;
    }

    @Override
    public String toMethod() {

        JSONObject contentObj = new JSONObject();
        if (getResult()) {
            try {
                contentObj.put("logout", isLoginOut);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return toMethod(contentObj);

    }
}
