package cn.com.startai.sharedlib.app.js.method2Impl.charger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.com.startai.chargersdk.entity.C_0x8304;
import cn.com.startai.sharedlib.app.js.Utils.JsMsgType;
import cn.com.swain.baselib.jsInterface.response.BaseResponseMethod2;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/28 0028
 * desc :
 */
public class StoreInfoResponseMethod extends BaseResponseMethod2 {

    public static StoreInfoResponseMethod getStoreInfoResponseMethod() {
        return new StoreInfoResponseMethod();
    }

    private StoreInfoResponseMethod() {
        super(JsMsgType.TYPE_RESPONSE_STORES_INFO);
    }

    @Override
    public void releaseCache() {
        super.releaseCache();
        contentBean2 = null;
    }


    private C_0x8304.Resp.ContentBean contentBean2;

    public void setContent(C_0x8304.Resp.ContentBean contentBean) {
        this.contentBean2 = contentBean;
    }

    /**
     * merchantId : 114
     * name : 亓行
     * address : 广东省广州市天河区建中路3号
     * telephone : 13332965499
     * openTime : 09:00-18:00
     * consumption : 888
     * fullyCount : 0
     * vacancyCount : 0
     * logo : img/002.jpg
     * banners : ["img/001.jpg","img/002.jpg","img/003.jpg"]
     * type : 1
     * lng : 113.367631
     * lat : 23.130691
     * distance : 1000
     */

    @Override
    public String toMethod() {

        JSONObject data = new JSONObject();
        if (getResult()) {
            if (contentBean2 != null) {
                try {
                    data.put("merchantId", contentBean2.getMerchantId());
                    data.put("name", contentBean2.getName());
                    data.put("address", contentBean2.getAddress());
                    data.put("telephone", contentBean2.getTelephone());
                    data.put("openTime", contentBean2.getOpenTime());
                    data.put("consumption", contentBean2.getConsumption());
                    data.put("fullyCount", contentBean2.getFullyCount());
                    data.put("vacancyCount", contentBean2.getVacancyCount());
                    data.put("logo", contentBean2.getLogo());
                    JSONArray array = new JSONArray();
                    List<String> banners = contentBean2.getBanners();
                    for (String banner : banners) {
                        array.put(banner);
                    }
                    data.put("logo", contentBean2.getLogo());
                    data.put("type", contentBean2.getType());
                    data.put("lng", contentBean2.getLng());
                    data.put("lat", contentBean2.getLat());
                    data.put("distance", contentBean2.getDistance());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return toMethod(data);

    }


}
