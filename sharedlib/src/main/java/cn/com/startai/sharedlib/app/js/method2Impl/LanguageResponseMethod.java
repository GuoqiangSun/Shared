package cn.com.startai.sharedlib.app.js.method2Impl;

import org.json.JSONException;
import org.json.JSONObject;

import cn.com.startai.sharedlib.app.js.Utils.SharedCommonJsUtils;
import cn.com.swain.baselib.jsInterface.method.BaseResponseMethod2;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/28 0028
 * desc :
 */
public class LanguageResponseMethod extends BaseResponseMethod2 {

    public static LanguageResponseMethod getLanguageResponseMethod() {
        return new LanguageResponseMethod();
    }

    public LanguageResponseMethod() {
        super(SharedCommonJsUtils.TYPE_RESPONSE_SYSTEM_LANGUAGE);
    }

    @Override
    public void releaseCache() {
        super.releaseCache();

        lan = null;

    }

    private String lan;

    public void setLan(String lan) {
        this.lan = lan;
    }

    @Override
    public String toMethod() {

        JSONObject contentObj = new JSONObject();
        if (getResult()) {
            try {
                contentObj.put("language", lan);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return toMethod(contentObj);

    }
}
