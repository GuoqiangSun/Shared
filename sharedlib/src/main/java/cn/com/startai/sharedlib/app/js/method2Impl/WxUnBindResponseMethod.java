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
public class WxUnBindResponseMethod extends BaseResponseMethod2 {

    public static WxUnBindResponseMethod getWXUnBindResponseMethod() {
        return new WxUnBindResponseMethod();
    }

    private WxUnBindResponseMethod() {
        super(JsMsgType.TYPE_RESPONSE_UNBIND_WX);
    }

    @Override
    public void releaseCache() {
        super.releaseCache();

        unbind = false;
    }

    private boolean unbind;

    public void setUnBind(boolean unbind) {
        this.unbind = unbind;
    }


    @Override
    public String toMethod() {

        JSONObject contentObj = new JSONObject();
        if (getResult()) {
            try {
                contentObj.put("unbind", unbind);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return toMethod(contentObj);

    }

}