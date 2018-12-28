package cn.com.startai.sharedlib.app.js.method2Impl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.com.startai.chargersdk.entity.C_0x8309;
import cn.com.startai.chargersdk.entity.C_0x8314;
import cn.com.startai.mqttsdk.busi.entity.C_0x8031;
import cn.com.startai.sharedlib.app.js.Utils.JsMsgType;
import cn.com.swain.baselib.jsInterface.method.BaseResponseMethod2;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/28 0028
 * desc :
 */
public class TransactionDetailResponseMethod extends BaseResponseMethod2 {

    public static TransactionDetailResponseMethod getTransactionDetailResponseMethod() {
        return new TransactionDetailResponseMethod();
    }

    public TransactionDetailResponseMethod() {
        super(JsMsgType.TYPE_RESPONSE_TRANSACTION_DETAIL);
    }

    @Override
    public void releaseCache() {
        super.releaseCache();
        contentBean = null;
    }


    private C_0x8314.Resp.ContentBean contentBean;

    public void setContent(C_0x8314.Resp.ContentBean contentBean) {
        this.contentBean = contentBean;
    }


    @Override
    public String toMethod() {

        JSONObject contentObj = new JSONObject();

        if (getResult() && contentBean != null) {
            try {
                contentObj.put("transactionType", contentBean.getTransactionType());
                contentObj.put("page", contentBean.getPage());
                contentObj.put("count", contentBean.getCount());
                contentObj.put("total_page", contentBean.getTotal_page());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JSONArray mArray = new JSONArray();
            JSONObject mJsonObj;

            List<C_0x8314.Resp.ContentBean.DataBean> data = contentBean.getData();
            if (data != null && data.size() > 0) {
                for (C_0x8314.Resp.ContentBean.DataBean mDataBean : data) {
                    /**
                     * fee : 1
                     * recharge_type : 1
                     * pay_type : 2
                     * time_end : 201812181149
                     * result : SUCCESS
                     */

                    try {
                        mJsonObj = new JSONObject();
                        mJsonObj.put("fee", mDataBean.getFee());
                        mJsonObj.put("recharge_type", mDataBean.getRecharge_type());
                        mJsonObj.put("pay_type", mDataBean.getPay_type());
                        mJsonObj.put("time_end", mDataBean.getTime_end());
                        mJsonObj.put("result", mDataBean.getResult());
                        mJsonObj.put("charging_type", mDataBean.getCharging_type());
                        mArray.put(mJsonObj);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            try {
                contentObj.put("data", mArray);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return toMethod(contentObj);

    }
}
