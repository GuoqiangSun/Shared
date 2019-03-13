package cn.com.startai.sharedlib.app.js.method2Impl;

import org.json.JSONException;
import org.json.JSONObject;

import cn.com.startai.mqttsdk.busi.entity.C_0x8034;
import cn.com.startai.sharedlib.app.js.Utils.JsMsgType;
import cn.com.swain.baselib.jsInterface.response.BaseResponseMethod2;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/28 0028
 * desc :
 */
public class PhoneBindResponseMethod extends BaseResponseMethod2 {

    public static PhoneBindResponseMethod getPhoneBindResponseMethod() {
        return new PhoneBindResponseMethod();
    }

    private PhoneBindResponseMethod() {
        super(JsMsgType.TYPE_RESPONSE_BIND_PHONE);
    }

    @Override
    public void releaseCache() {
        super.releaseCache();

        binding = false;
        content = null;
    }

    private boolean binding;

    public void setBinding(boolean binding) {
        this.binding = binding;
    }


    private C_0x8034.Resp.ContentBean content;

    public void setContent(C_0x8034.Resp.ContentBean content) {
        this.content = content;
    }

    @Override
    public String toMethod() {

        JSONObject contentObj = new JSONObject();
        if (getResult()) {
            try {
                contentObj.put("binding", binding);

                if (content != null) {
                    contentObj.put("mobile", content.getMobile());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return toMethod(contentObj);

    }

}