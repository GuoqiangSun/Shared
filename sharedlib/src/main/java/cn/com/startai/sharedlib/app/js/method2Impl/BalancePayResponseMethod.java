package cn.com.startai.sharedlib.app.js.method2Impl;

import org.json.JSONException;
import org.json.JSONObject;

import cn.com.startai.chargersdk.entity.C_0x8311;
import cn.com.startai.sharedlib.app.js.Utils.JsMsgType;
import cn.com.swain.baselib.jsInterface.response.BaseResponseMethod2;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/28 0028
 * desc :
 */
public class BalancePayResponseMethod extends BaseResponseMethod2 {

    public static BalancePayResponseMethod getBalancePayResponseMethod() {
        return new BalancePayResponseMethod();
    }

    public BalancePayResponseMethod() {
        super(JsMsgType.TYPE_RESPONSE_BALANCE_PAY);
    }

    @Override
    public void releaseCache() {
        super.releaseCache();
        contentBean = null;
    }


    private C_0x8311.Resp.ContentBean contentBean;

    public void setContent(C_0x8311.Resp.ContentBean contentBean) {
        this.contentBean = contentBean;
    }

    @Override
    public String toMethod() {

        JSONObject data = new JSONObject();
        if (getResult() && contentBean != null) {
            try {

                /**
                 * no : LENT2018112817163224552973294Y
                 * balance_deduction : 0
                 * fee : 0
                 * need_pay_fee : 0
                 * discount_fee : 0
                 */

                data.put("no", contentBean.getNo());
                data.put("balance_deduction", contentBean.getBalance_deduction());
                data.put("fee", contentBean.getFee());
                data.put("need_pay_fee", contentBean.getNeed_pay_fee());
                data.put("discount_fee", contentBean.getDiscount_fee());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return toMethod(data);

    }
}
