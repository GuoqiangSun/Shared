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
public class IsLoginResponseMethod extends BaseResponseMethod2 {

    public static IsLoginResponseMethod getIsLoginResponseMethod() {
        return new IsLoginResponseMethod();
    }

    private IsLoginResponseMethod() {
        super(JsMsgType.TYPE_RESPONSE_IS_LOGIN);
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
                contentObj.put("state", isLogin ? 1 : 0);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return toMethod(contentObj);

    }
}
