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
public class GetIdentityCodeResponseMethod extends BaseResponseMethod2 {

    public static GetIdentityCodeResponseMethod getGetIdentityCodeResponseMethod() {
        return new GetIdentityCodeResponseMethod();
    }

    private GetIdentityCodeResponseMethod() {
        super(JsMsgType.TYPE_RESPONSE_GET_IDENTITY_CODE);
    }

    @Override
    public void releaseCache() {
        super.releaseCache();

        mIDCode = 0;

    }

    private int mIDCode;

    public void setIDCode(int mIDCode) {
        this.mIDCode = mIDCode;
    }

    @Override
    public String toMethod() {

        JSONObject contentObj = new JSONObject();
        if (getResult()) {
            try {
                contentObj.put("code", mIDCode);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return toMethod(contentObj);

    }
}
