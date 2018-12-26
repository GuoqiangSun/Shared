package cn.com.startai.sharedlib.app.js.method2Impl;

import org.json.JSONException;
import org.json.JSONObject;

import cn.com.startai.sharedlib.app.js.Utils.JsMsgType;
import cn.com.swain.baselib.jsInterface.method.BaseResponseMethod2;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/28 0028
 * desc :
 */
public class ModifyHeadpicResponseMethod extends BaseResponseMethod2 {

    public static ModifyHeadpicResponseMethod getModifyHeadpicResponseMethod() {
        return new ModifyHeadpicResponseMethod();
    }

    public ModifyHeadpicResponseMethod() {
        super(JsMsgType.TYPE_RESPONSE_MODIFY_HEADPIC_DATA);
    }

    @Override
    public void releaseCache() {
        super.releaseCache();
        send = false;
    }

    private boolean send;

    public void setIsSend(boolean send) {
        this.send = send;
    }

    @Override
    public String toMethod() {

        JSONObject contentObj = new JSONObject();
        if (getResult()) {
            try {
                contentObj.put("sent", send);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return toMethod(contentObj);

    }
}
