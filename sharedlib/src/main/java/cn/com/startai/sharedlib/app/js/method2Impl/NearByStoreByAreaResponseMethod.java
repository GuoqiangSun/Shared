package cn.com.startai.sharedlib.app.js.method2Impl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.com.startai.chargersdk.entity.C_0x8313;
import cn.com.startai.sharedlib.app.js.Utils.JsMsgType;
import cn.com.swain.baselib.jsInterface.method.BaseResponseMethod2;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/28 0028
 * desc :
 */
public class NearByStoreByAreaResponseMethod extends BaseResponseMethod2 {

    public static NearByStoreByAreaResponseMethod getNearByStoreByAreaResponseMethod() {
        return new NearByStoreByAreaResponseMethod();
    }

    public NearByStoreByAreaResponseMethod() {
        super(JsMsgType.TYPE_RESPONSE_NEAR_STORES_DETAIL);
    }

    @Override
    public void releaseCache() {
        super.releaseCache();
        contentBean = null;
    }


    /**
     * device_type : 1
     * region : 天河区
     * page : 1
     * count : 10
     * total_page : 10
     * data : [{"merchantId":110,"name":"尊宝比萨","address":"广东省深圳市福田区水围七街4号","openTime":"9:00-22:00","consumption":100,"fullyCount":0,"vacancyCount":3,"logo":"img/002.jpg","type":1,"distance":1000,"lng":"113.367631","lat":"23.130781"}]
     */
    private C_0x8313.Resp.ContentBean contentBean;

    public void setContent(C_0x8313.Resp.ContentBean contentBean) {
        this.contentBean = contentBean;
    }

    @Override
    public String toMethod() {

        JSONObject data = new JSONObject();
        if (getResult() && contentBean != null) {
            try {
                data.put("device_type", contentBean.getDevice_type());
                data.put("region", contentBean.getRegion());
                data.put("page", contentBean.getPage());
                data.put("count", contentBean.getCount());
                data.put("total_page", contentBean.getTotal_page());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            List<C_0x8313.Resp.ContentBean.DataBean> data1 = contentBean.getData();

            JSONArray mArray = new JSONArray();
            JSONObject mObj;

            if (data1 != null && data1.size() > 0) {

                /**
                 * merchantId : 110
                 * name : 尊宝比萨
                 * address : 广东省深圳市福田区水围七街4号
                 * openTime : 9:00-22:00
                 * consumption : 100
                 * fullyCount : 0
                 * vacancyCount : 3
                 * logo : img/002.jpg
                 * type : 1
                 * distance : 1000
                 * lng : 113.367631
                 * lat : 23.130781
                 */

                for (C_0x8313.Resp.ContentBean.DataBean mDataBean : data1) {
                    mObj = new JSONObject();

                    try {
                        mObj.put("merchantId", mDataBean.getMerchantId());
                        mObj.put("name", mDataBean.getName());
                        mObj.put("address", mDataBean.getAddress());
                        mObj.put("openTime", mDataBean.getOpenTime());
                        mObj.put("consumption", mDataBean.getConsumption());
                        mObj.put("fullyCount", mDataBean.getFullyCount());
                        mObj.put("vacancyCount", mDataBean.getVacancyCount());
                        mObj.put("logo", mDataBean.getLogo());
                        mObj.put("type", mDataBean.getType());
                        mObj.put("distance", mDataBean.getDistance());
                        mObj.put("lng", mDataBean.getLng());
                        mObj.put("lat", mDataBean.getLat());
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
