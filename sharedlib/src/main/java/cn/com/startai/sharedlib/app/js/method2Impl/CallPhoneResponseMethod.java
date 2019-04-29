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
public class CallPhoneResponseMethod extends BaseResponseMethod2 {

    public static CallPhoneResponseMethod getCallPhoneResponseMethod() {
        return new CallPhoneResponseMethod();
    }

    private CallPhoneResponseMethod() {
        super(JsMsgType.TYPE_RESPONSE_CALL_PHONE);
    }

    @Override
    public void releaseCache() {
        super.releaseCache();
        state = false;
        phone = null;
    }

    private boolean state;

    public void setState(boolean state) {
        this.state = state;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    private String phone;


    @Override
    public String toMethod() {

        JSONObject contentObj = new JSONObject();
        try {
            contentObj.put("phone", phone);
            contentObj.put("state", state);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return toMethod(contentObj);

    }
}
