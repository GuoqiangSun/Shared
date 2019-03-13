package cn.com.startai.sharedlib.app.js.requestBeanImpl.charger;

import cn.com.swain.baselib.jsInterface.request.bean.BaseCommonJsRequestBean;
import cn.com.swain.baselib.jsInterface.request.bean.BaseCommonJsRequestBeanWrapper;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/29 0029
 * desc :
 */
public class StoresInfoRequestBean extends BaseCommonJsRequestBeanWrapper {

    public StoresInfoRequestBean(BaseCommonJsRequestBean mBean) {
        super(mBean);
    }


    public int getMerchantId(){
        return getIntByRootJson("merchantId");
    }

    public String getLan(){
        return getStringByRootJson("lng");
    }

    public String getLat(){
        return getStringByRootJson("lat");
    }


}

