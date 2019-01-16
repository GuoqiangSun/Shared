package cn.com.startai.sharedlib.app.js.method2Impl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.com.startai.chargersdk.entity.C_0x8309;
import cn.com.startai.sharedlib.app.js.Utils.JsMsgType;
import cn.com.swain.baselib.jsInterface.response.BaseResponseMethod2;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/28 0028
 * desc :
 */
public class OrderListResponseMethod extends BaseResponseMethod2 {

    public static OrderListResponseMethod getOrderListResponseMethod() {
        return new OrderListResponseMethod();
    }

    public OrderListResponseMethod() {
        super(JsMsgType.TYPE_RESPONSE_ORDER_LIST);
    }

    @Override
    public void releaseCache() {
        super.releaseCache();
        contentBean = null;
    }

    private C_0x8309.Resp.ContentBean contentBean;

    public void setOrderList(C_0x8309.Resp.ContentBean contentBean) {
        this.contentBean = contentBean;
    }

    @Override
    public String toMethod() {

        JSONObject contentObj = new JSONObject();

        if (getResult() && contentBean != null) {
            try {
                contentObj.put("type", contentBean.getType());
                contentObj.put("page", contentBean.getPage());
                contentObj.put("count", contentBean.getCount());
                contentObj.put("total_page", contentBean.getTotal_page());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JSONArray mArray = new JSONArray();
            JSONObject mJsonObj;
            ArrayList<C_0x8309.Resp.ContentBean.OrderBean> orders = contentBean.getOrders();
            if (orders != null && orders.size() > 0) {
                for (C_0x8309.Resp.ContentBean.OrderBean mOrderBean : orders) {
//                                         * no : LENT2018012821520607070620574F
//                                * fee : 100
//                                * lent_time : 2018-06-23 18：00：00
//                                * duration  : 3天2小时12分钟
//                                * lent_adress : 广东省广州市天河区建中路3号
//                                * pay_state : 1

                    try {
                        mJsonObj = new JSONObject();
                        mJsonObj.put("no", mOrderBean.getNo());
                        mJsonObj.put("fee", mOrderBean.getFee());
                        mJsonObj.put("lent_time", mOrderBean.getLent_time());
                        mJsonObj.put("duration", mOrderBean.getDuration());
                        mJsonObj.put("lent_adress", mOrderBean.getLent_adress());
                        mJsonObj.put("pay_state", mOrderBean.getPay_state());
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
