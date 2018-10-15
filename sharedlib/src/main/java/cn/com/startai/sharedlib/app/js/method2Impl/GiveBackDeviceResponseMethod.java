package cn.com.startai.sharedlib.app.js.method2Impl;

import org.json.JSONException;
import org.json.JSONObject;

import cn.com.swain.baselib.jsInterface.method.BaseResponseMethod2;
import cn.com.startai.sharedlib.app.js.Utils.JsMsgType;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/28 0028
 * desc :
 */
public class GiveBackDeviceResponseMethod extends BaseResponseMethod2 {

    public static GiveBackDeviceResponseMethod getGiveBackDeviceResponseMethod() {
        return new GiveBackDeviceResponseMethod();
    }


    public GiveBackDeviceResponseMethod() {
        super(JsMsgType.TYPE_RESPONSE_GIVEBACK_DEVICE);
    }

    @Override
    public void releaseCache() {
        super.releaseCache();
        imei = no = lentMerchantName = lentTime = returnTime = returnMerchantName = fee = null;
    }

//    "imei": "1000000089",                 // 机柜imei
//            "no": "Lend849jfjs9e9gjrjskdoh",      // 借出单的单号
//            "lentTime": "2018-01-01 09:00:00",    // 借出开始时间
//            "lentMerchantName": "常德人家",       // 借出网点名称
//            "returnTime":"2018-01-01 11:00:00",   // 还入时间点
//            "returnMerchantName":"常德人家",      // 还入网点名称
//            "fee":"1.05"

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

    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }

    public void setReturnMerchantName(String returnMerchantName) {
        this.returnMerchantName = returnMerchantName;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    private String returnTime;
    private String returnMerchantName;
    private String fee;

    @Override
    public String toMethod() {

        JSONObject contentObj = new JSONObject();
        if (getResult()) {
            try {
                contentObj.put("imei", imei);
                contentObj.put("no", no);
                contentObj.put("lentTime", lentTime);
                contentObj.put("lentMerchantName", lentMerchantName);
                contentObj.put("returnTime", returnTime);
                contentObj.put("returnMerchantName", returnMerchantName);
                contentObj.put("fee", fee);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return toMethod(contentObj);

    }
}
