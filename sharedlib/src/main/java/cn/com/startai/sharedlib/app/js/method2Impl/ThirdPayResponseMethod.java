package cn.com.startai.sharedlib.app.js.method2Impl;

import org.json.JSONException;
import org.json.JSONObject;

import cn.com.startai.mqttsdk.busi.entity.C_0x8031;
import cn.com.startai.sharedlib.app.js.Utils.JsMsgType;
import cn.com.swain.baselib.jsInterface.response.BaseResponseMethod2;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/28 0028
 * desc :
 */
public class ThirdPayResponseMethod extends BaseResponseMethod2 {

    public static ThirdPayResponseMethod getThirdPayResponseMethod() {
        return new ThirdPayResponseMethod();
    }

    public ThirdPayResponseMethod() {
        super(JsMsgType.TYPE_RESPONSE_THIRD_PAY);
    }

    @Override
    public void releaseCache() {
        super.releaseCache();
        contentBean = null;
    }


    private C_0x8031.Resp.ContentBean contentBean;

    public void setContent(C_0x8031.Resp.ContentBean contentBean) {
        this.contentBean = contentBean;
    }

    /**
     * platform(Y):交易平台
     * 1.微信 2 支付宝 3 微信小程序
     * userid(Y):用户id
     * openid(Y): 用户在商户appid下的唯一id
     * is_subscribe(N):是否订阅
     * bank_type(Y):付款银行
     * total_fee(Y): 交易金额,单位为分
     * out_trade_no(Y):商户订单号
     * transaction_id(Y): 微信支付订单号
     * trade_type(Y):交易类型
     * time_end:支付完成时间yyyy-MM-dd HH:mm:ss
     * trade_state(Y):交易状态
     * SUCCESS—支付成功
     * REFUND—转入退款
     * NOTPAY—未支付
     * CLOSED—已关闭
     * REVOKED—已撤销（刷卡支付）
     * USERPAYING--用户支付中
     * PAYERROR--支付失败(其他原因，如银行返回失败)
     * ERROR:错误
     * cash_fee(Y):现金支付金额
     * coupon_fee:代金券金额
     * coupon_count:代金券数量
     * trade_state_desc:交易状态描述
     * 当trade_state为ERROR时,
     * err_code, err_code_des有值,但其他必有字段可能为空
     */


    @Override
    public String toMethod() {

        JSONObject data = new JSONObject();
        if (getResult() && contentBean != null) {
            try {
                data.put("platform", contentBean.getPlatform());
                data.put("openid", contentBean.getOpenid());
                data.put("is_subscribe", contentBean.getIs_subscribe());
                data.put("bank_type", contentBean.getBank_type());
                data.put("total_fee", contentBean.getTotal_fee());
                data.put("out_trade_no", contentBean.getOut_trade_no());
                data.put("transaction_id", contentBean.getTransaction_id());
                data.put("trade_type", contentBean.getTrade_type());
                data.put("time_end", contentBean.getTime_end());
                data.put("trade_state", contentBean.getTrade_state());
                data.put("cash_fee", contentBean.getCash_fee());
                data.put("coupon_fee", contentBean.getCoupon_fee());
                data.put("coupon_count", contentBean.getCoupon_count());
                data.put("trade_state_desc", contentBean.getTrade_state_desc());
                data.put("err_code", contentBean.getErr_code());
                data.put("err_code_des", contentBean.getErr_code_des());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return toMethod(data);

    }
}
