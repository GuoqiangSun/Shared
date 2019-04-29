package cn.com.startai.sharedlib.app.js.requestBeanImpl;

import cn.com.startai.sharedlib.app.js.Utils.JsMsgType;
import cn.com.swain.baselib.jsInterface.request.bean.BaseCommonJsRequestBean;
import cn.com.swain.baselib.jsInterface.request.bean.BaseCommonJsRequestBeanWrapper;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/29 0029
 * desc :
 */
public class MapNavRequestBean extends BaseCommonJsRequestBeanWrapper {

    public MapNavRequestBean(BaseCommonJsRequestBean mBean) {
        super(mBean);
    }

    public String getAddress() {
        return getStringByRootJson("address");
    }

    public String getName() {
        return getStringByRootJson("name");
    }

    public int getType() {
        return getIntByRootJson("type");
    }

    public double getLng() {
        return getDoubleByRootJson("lng");
    }

    public double getLat() {
        return getDoubleByRootJson("lat");
    }

    public boolean isGaode() {
        return JsMsgType.MAP_TYPE_GAODE == getType();
    }

    public boolean isBaidu() {
        return JsMsgType.MAP_TYPE_BAIDU == getType();
    }

    public boolean isGoogle() {
        return JsMsgType.MAP_TYPE_GOOGLE == getType();
    }

//    type	是	Number	1	调用地图类型： 1、高德地图 2、百度地图 3、谷歌地图
//    lng	是	Number	113.367631	经度
//    lat	是	Number	23.130691	纬度
//    name	是	String	"亓行"	店铺名称
//    address	是	String	"中山大道海天楼"	店铺地址

}
