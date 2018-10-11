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
public class ScanORResponseMethod extends BaseResponseMethod2 {


    public static ScanORResponseMethod getScanORResponseMethod() {
        return new ScanORResponseMethod();
    }


    public ScanORResponseMethod() {
        super(SharedCommonJsUtils.TYPE_RESPONSE_SCAN_OR);
    }

    @Override
    public void releaseCache() {
        super.releaseCache();

        imei = null;
        merchantId = null;

    }

    private String imei;

    public void setIMEI(String imei) {
        this.imei = imei;
    }

    private String merchantId;

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    @Override
    public String toMethod() {

        JSONObject contentObj = new JSONObject();
        if (getResult()) {
            try {
                contentObj.put("imei", imei);
                contentObj.put("merchantId", merchantId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return toMethod(contentObj);


    }
}
