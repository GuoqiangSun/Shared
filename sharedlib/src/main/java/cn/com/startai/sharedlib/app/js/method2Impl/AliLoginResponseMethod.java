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
public class AliLoginResponseMethod extends BaseResponseMethod2 {

    public static AliLoginResponseMethod getAliLoginResponseMethod() {
        return new AliLoginResponseMethod();
    }

    private AliLoginResponseMethod() {
        super(JsMsgType.TYPE_RESPONSE_ALI_LOGIN);
    }

    @Override
    public void releaseCache() {
        super.releaseCache();

        name = null;

    }

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toMethod() {

        JSONObject contentObj = new JSONObject();
        if (getResult()) {
            try {
                contentObj.put("name", name);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return toMethod(contentObj);

    }
}
