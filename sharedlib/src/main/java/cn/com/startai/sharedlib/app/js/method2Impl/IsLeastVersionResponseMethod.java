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
public class IsLeastVersionResponseMethod extends BaseResponseMethod2 {

    public static IsLeastVersionResponseMethod getIsLeastVersionResponseMethod() {
        return new IsLeastVersionResponseMethod();
    }

    public IsLeastVersionResponseMethod() {
        super(JsMsgType.TYPE_RESPONSE_IS_NEWVERSION);
    }

    @Override
    public void releaseCache() {
        super.releaseCache();
        upgrade = false;
    }

    private boolean upgrade;

    public void setNeedUpgrade(boolean upgrade) {
        this.upgrade = upgrade;
    }

    private String path;

    public void setDownloadPath(String path) {
        this.path = path;
    }

    @Override
    public String toMethod() {

        JSONObject contentObj = new JSONObject();
        if (getResult()) {
            try {
                contentObj.put("upgrade", upgrade);
                contentObj.put("path", path);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return toMethod(contentObj);

    }
}
