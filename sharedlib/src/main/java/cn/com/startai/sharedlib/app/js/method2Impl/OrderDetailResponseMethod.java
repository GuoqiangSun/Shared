package cn.com.startai.sharedlib.app.js.method2Impl;

import org.json.JSONException;
import org.json.JSONObject;

import cn.com.startai.chargersdk.entity.C_0x8310;
import cn.com.startai.sharedlib.app.js.Utils.JsMsgType;
import cn.com.swain.baselib.jsInterface.response.BaseResponseMethod2;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/28 0028
 * desc :
 */
public class OrderDetailResponseMethod extends BaseResponseMethod2 {

    public static OrderDetailResponseMethod getOrderDetailResponseMethod() {
        return new OrderDetailResponseMethod();
    }

    public OrderDetailResponseMethod() {
        super(JsMsgType.TYPE_RESPONSE_ORDER_DETAIL);
    }

    @Override
    public void releaseCache() {
        super.releaseCache();
        contentBean = null;
    }


    private C_0x8310.Resp.ContentBean contentBean;

    public void setContent(C_0x8310.Resp.ContentBean contentBean) {
        this.contentBean = contentBean;
    }

    /**
     * no : LENT2018012821520607070620574F
     * lent_time : 2018-06-23 18：00：00
     * lent_adress : 广东省广州市天河区建中路3号
     * return_time : 2018-06-23 18：00：00
     * return_adress : 广东省广州市天河区建中路3号
     * duration : 3天2小时12分钟
     * fee : 100
     * pay_state : 1
     * discount_fee : 50
     * balance_deduction : 50
     * need_pay_fee : 0
     * total_page : 10
     */

    @Override
    public String toMethod() {

        JSONObject data = new JSONObject();
        if (getResult() && contentBean != null) {
            try {
                data.put("no", contentBean.getNo());
                data.put("lent_adress", contentBean.getLent_adress());
                data.put("lent_time", contentBean.getLent_time());
                data.put("return_time", contentBean.getReturn_time());
                data.put("return_adress", contentBean.getReturn_adress());
                data.put("duration", contentBean.getDuration());
                data.put("fee", contentBean.getFee());
                data.put("pay_state", contentBean.getPay_state());
                data.put("discount_fee", contentBean.getDiscount_fee());
                data.put("balance_deduction", contentBean.getBalance_deduction());
                data.put("need_pay_fee", contentBean.getNeed_pay_fee());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return toMethod(data);

    }
}
