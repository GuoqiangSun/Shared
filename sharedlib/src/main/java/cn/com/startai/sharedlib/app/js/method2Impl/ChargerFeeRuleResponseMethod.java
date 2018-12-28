package cn.com.startai.sharedlib.app.js.method2Impl;

import org.json.JSONException;
import org.json.JSONObject;

import cn.com.startai.chargersdk.entity.C_0x8316;
import cn.com.startai.sharedlib.app.js.Utils.JsMsgType;
import cn.com.swain.baselib.jsInterface.method.BaseResponseMethod2;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/28 0028
 * desc :
 */
public class ChargerFeeRuleResponseMethod extends BaseResponseMethod2 {

    public static ChargerFeeRuleResponseMethod getFeeRuleResponseMethod() {
        return new ChargerFeeRuleResponseMethod();
    }

    public ChargerFeeRuleResponseMethod() {
        super(JsMsgType.TYPE_RESPONSE_FEE_RULE);
    }

    @Override
    public void releaseCache() {
        super.releaseCache();
        contentBean2 = null;
    }

    /**
     * imei :
     * policyName : 1元/小时
     * money : 100
     * chargingType : 1
     * moneyMax : 500
     * duration : 0
     * count : 1
     */

    private C_0x8316.Resp.ContentBean contentBean2;

    public void setContent(C_0x8316.Resp.ContentBean contentBean) {
        this.contentBean2 = contentBean;
    }

    @Override
    public String toMethod() {

        JSONObject data = new JSONObject();
        if (getResult()) {
            if (contentBean2 != null) {
                try {
                    data.put("chargingType", contentBean2.getChargingType());
                    data.put("count", contentBean2.getCount());
                    data.put("duration", contentBean2.getDuration());
                    data.put("imei", contentBean2.getImei());
                    data.put("money", contentBean2.getMoney());
                    data.put("moneyMax", contentBean2.getMoneyMax());
                    data.put("policyName", contentBean2.getPolicyName());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return toMethod(data);

    }


}
