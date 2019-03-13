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
public class ModifyUserPwdResponseMethod extends BaseResponseMethod2 {

    public static ModifyUserPwdResponseMethod getModifyUserPwdResponseMethod() {
        return new ModifyUserPwdResponseMethod();
    }

    private ModifyUserPwdResponseMethod() {
        super(JsMsgType.TYPE_RESPONSE_MODIFY_USERPWD);
    }

    @Override
    public void releaseCache() {
        super.releaseCache();
        successfully = false;
    }

    private boolean successfully;

    public void setIsSuccessfully(boolean successfully) {
        this.successfully = successfully;
    }

    @Override
    public String toMethod() {

        JSONObject contentObj = new JSONObject();
        if (getResult()) {
            try {
                contentObj.put("successfully", successfully);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return toMethod(contentObj);

    }
}
