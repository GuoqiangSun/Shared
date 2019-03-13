package cn.com.startai.sharedlib.app.js.method2Impl.charger;

import org.json.JSONException;
import org.json.JSONObject;

import cn.com.startai.chargersdk.entity.C_0x8317;
import cn.com.startai.sharedlib.app.js.Utils.JsMsgType;
import cn.com.swain.baselib.jsInterface.response.BaseResponseMethod2;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/28 0028
 * desc :
 */
public class DepositFeeRuleResponseMethod extends BaseResponseMethod2 {

    public static DepositFeeRuleResponseMethod getDepositFeeRuleResponseMethod() {
        return new DepositFeeRuleResponseMethod();
    }

    private DepositFeeRuleResponseMethod() {
        super(JsMsgType.TYPE_RESPONSE_DEPOSIT_FEE_RULE);
    }

    @Override
    public void releaseCache() {
        super.releaseCache();
        contentBean = null;
    }


    /**
     * userId :
     * id : 1元/小时
     * depFee : 100
     * isDeposit : 1
     * remark : 500
     */
    private C_0x8317.Resp.ContentBean contentBean;

    public void setContent(C_0x8317.Resp.ContentBean contentBean) {
        this.contentBean = contentBean;
    }


    @Override
    public String toMethod() {

        JSONObject data = new JSONObject();
        if (getResult()) {
            if (contentBean != null) {
                try {
                    data.put("id", contentBean.getId());
                    data.put("depFee", contentBean.getDepFee());
                    data.put("isDeposit", contentBean.getIsDeposit());
                    data.put("remark", contentBean.getRemark());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return toMethod(data);

    }


}
