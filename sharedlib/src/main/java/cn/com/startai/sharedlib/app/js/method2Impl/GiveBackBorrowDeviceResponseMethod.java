package cn.com.startai.sharedlib.app.js.method2Impl;

import org.json.JSONException;
import org.json.JSONObject;

import cn.com.startai.chargersdk.entity.C_0x8301;
import cn.com.startai.sharedlib.app.js.Utils.JsMsgType;
import cn.com.swain.baselib.jsInterface.method.BaseResponseMethod2;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/28 0028
 * desc :
 */
public class GiveBackBorrowDeviceResponseMethod extends BaseResponseMethod2 {

    public static GiveBackBorrowDeviceResponseMethod getGiveBackBorrowDeviceResponseMethod() {
        return new GiveBackBorrowDeviceResponseMethod();
    }


    public GiveBackBorrowDeviceResponseMethod() {
        super(JsMsgType.TYPE_RESPONSE_GIVEBACK_DEVICE_BORROW);
    }

    @Override
    public void releaseCache() {
        super.releaseCache();
        content = null;
    }

//    "imei": "1000000089",                 // 机柜imei
//            "no": "Lend849jfjs9e9gjrjskdoh",      // 借出单的单号
//            "lentTime": "2018-01-01 09:00:00",    // 借出开始时间
//            "lentMerchantName": "常德人家",       // 借出网点名称
//            "returnTime":"2018-01-01 11:00:00",   // 还入时间点
//            "returnMerchantName":"常德人家",      // 还入网点名称
//            "fee":"1.05"

    private C_0x8301.Resp.ContentBean content;

    public void setContent(C_0x8301.Resp.ContentBean content) {
        this.content = content;
    }

    @Override
    public String toMethod() {

        JSONObject contentObj = new JSONObject();
        if (getResult()) {
            if (content != null) {

                try {
                    contentObj.put("type", content.getType());
                    contentObj.put("imei", content.getImei());
                    contentObj.put("no", content.getNo());
                    contentObj.put("lentTime", content.getLentTime());
                    contentObj.put("lentMerchantName", content.getLentMerchantName());
                    contentObj.put("returnTime", content.getReturnTime());
                    contentObj.put("returnMerchantName", content.getReturnMerchantName());
                    contentObj.put("fee", content.getFee());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return toMethod(contentObj);

    }
}
