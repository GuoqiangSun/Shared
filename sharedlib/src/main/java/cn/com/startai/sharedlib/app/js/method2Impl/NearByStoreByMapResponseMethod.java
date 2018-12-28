package cn.com.startai.sharedlib.app.js.method2Impl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.com.startai.chargersdk.entity.C_0x8312;
import cn.com.startai.chargersdk.entity.C_0x8313;
import cn.com.startai.sharedlib.app.js.Utils.JsMsgType;
import cn.com.swain.baselib.jsInterface.method.BaseResponseMethod2;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/28 0028
 * desc :
 */
public class NearByStoreByMapResponseMethod extends BaseResponseMethod2 {

    public static NearByStoreByMapResponseMethod getNearByStoreByMapResponseMethod() {
        return new NearByStoreByMapResponseMethod();
    }

    public NearByStoreByMapResponseMethod() {
        super(JsMsgType.TYPE_RESPONSE_NEAR_STORES_MAP);
    }

    @Override
    public void releaseCache() {
        super.releaseCache();
        contentBean = null;
    }


    /**
     * lng : 113.367631
     * lat : 23.130781
     * resultSet : [{"merchant_id":114,"m_lng":"113.367631","m_lat":"23.130781","type":3},{"merchant_id":115,"m_lng":"113.367631","m_lat":"23.130781","type":2},{"merchant_id":112,"m_lng":"113.367631","m_lat":"23.130781","type":1}]
     */
    private C_0x8312.Resp.ContentBean contentBean;

    public void setContent(C_0x8312.Resp.ContentBean contentBean) {
        this.contentBean = contentBean;
    }

    @Override
    public String toMethod() {

        JSONObject data = new JSONObject();
        if (getResult() && contentBean != null) {
            try {
                data.put("lat", contentBean.getLat());
                data.put("lng", contentBean.getLng());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            List<C_0x8312.Resp.ContentBean.ResultSetBean> resultSet = contentBean.getResultSet();

            JSONArray mArray = new JSONArray();
            JSONObject mObj;

            if (resultSet != null && resultSet.size() > 0) {

                for (C_0x8312.Resp.ContentBean.ResultSetBean mDataBean : resultSet) {
                    mObj = new JSONObject();

//                    {"merchant_id":114,"m_lng":"113.367631","m_lat":"23.130781","type":3}

                    try {
                        mObj.put("merchant_id", mDataBean.getMerchant_id());
                        mObj.put("m_lng", mDataBean.getM_lng());
                        mObj.put("m_lat", mDataBean.getM_lat());
                        mObj.put("type", mDataBean.getType());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    mArray.put(mObj);
                }
            }

            try {
                data.put("data", mArray);
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
        return toMethod(data);

    }


}
