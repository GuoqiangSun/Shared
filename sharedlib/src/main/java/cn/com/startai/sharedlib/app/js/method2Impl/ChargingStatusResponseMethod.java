package cn.com.startai.sharedlib.app.js.method2Impl;

import org.json.JSONException;
import org.json.JSONObject;

import cn.com.startai.chargersdk.entity.C_0x8315;
import cn.com.startai.sharedlib.app.js.Utils.JsMsgType;
import cn.com.swain.baselib.jsInterface.method.BaseResponseMethod2;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/28 0028
 * desc :
 */
public class ChargingStatusResponseMethod extends BaseResponseMethod2 {

    public static ChargingStatusResponseMethod getChargingStatusResponseMethod() {
        return new ChargingStatusResponseMethod();
    }

    public ChargingStatusResponseMethod() {
        super(JsMsgType.TYPE_RESPONSE_CHARGING_STATUS);
    }

    @Override
    public void releaseCache() {
        super.releaseCache();

        content = null;

    }

    public static final String TYPE_LENT = "LENT";
    public static final String TYPE_RETURN = "RETURN";

    private C_0x8315.Resp.ContentBean content;

    public void setContent(C_0x8315.Resp.ContentBean content) {
        this.content = content;
    }

    @Override
    public String toMethod() {

        JSONObject contentObj = new JSONObject();
        if (getResult()) {
            if (content != null) {
                try {
                    contentObj.put("type", content.getType());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return toMethod(contentObj);

    }

}
