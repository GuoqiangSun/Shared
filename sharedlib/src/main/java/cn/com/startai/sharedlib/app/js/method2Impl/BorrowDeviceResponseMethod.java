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
public class BorrowDeviceResponseMethod extends BaseResponseMethod2 {

    public static BorrowDeviceResponseMethod getBorrowDeviceResponseMethod() {
        return new BorrowDeviceResponseMethod();
    }


    public BorrowDeviceResponseMethod() {
        super(SharedCommonJsUtils.TYPE_RESPONSE_BORROW_DEVICE);
    }

    @Override
    public void releaseCache() {
        super.releaseCache();
        imei = no = lentMerchantName = lentTime = null;


    }

//        "imei": "1000000089",                 // 机柜imei
//                "no": "Lend849jfjs9e9gjrjskdoh",      // 借出单的单号
//                "lentTime": "2018-01-01 09:00:00",    // 借出开始时间
//                "lentMerchantName": "常德人家"        // 借出网点名称

    private String imei;

    public void setIMEI(String imei) {
        this.imei = imei;
    }


    private String no;

    public void setNO(String NO) {
        this.no = NO;
    }


    private String lentTime;

    public void setLentTime(String lentTime) {
        this.lentTime = lentTime;
    }

    private String lentMerchantName;

    public void setLentMerchantName(String lentMerchantName) {
        this.lentMerchantName = lentMerchantName;
    }

    @Override
    public String toMethod() {

        JSONObject contentObj = new JSONObject();
        if (getResult()) {
            try {
                contentObj.put("imei", imei);
                contentObj.put("no", no);
                contentObj.put("lentTime", lentTime);
                contentObj.put("lentMerchantName", lentMerchantName);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return toMethod(contentObj);

    }
}
