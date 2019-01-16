package cn.com.startai.sharedlib.app.js.requestBeanImpl;

import cn.com.swain.baselib.jsInterface.request.bean.BaseCommonJsRequestBean;
import cn.com.swain.baselib.jsInterface.request.bean.BaseCommonJsRequestBeanWrapper;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/29 0029
 * desc :
 */
public class StoresDetailLstRequestBean extends BaseCommonJsRequestBeanWrapper {

    public StoresDetailLstRequestBean(BaseCommonJsRequestBean mBean) {
        super(mBean);
    }

//                 * device_type : 1
//            * region : 广州市天河区
//             * lng : 113.367631
//            * lat : 23.130781
//            * page : 1
//            * count : 10

    public String getRegion(){
        return getStringByRootJson("region");
    }

    public String getLan(){
        return getStringByRootJson("lng");
    }

    public String getLat(){
        return getStringByRootJson("lat");
    }

    public int getDeviceType(){
        return getIntByRootJson("device_type");
    }

    public int getPage(){
        return getIntByRootJson("page");
    }

    public int getCount(){
        return getIntByRootJson("count");
    }

}

