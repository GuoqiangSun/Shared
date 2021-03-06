package cn.com.startai.sharedlib.app.js.requestBeanImpl.charger;

import cn.com.swain.baselib.jsInterface.request.bean.BaseCommonJsRequestBean;
import cn.com.swain.baselib.jsInterface.request.bean.BaseCommonJsRequestBeanWrapper;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/29 0029
 * desc :
 */
public class StoresMapLstRequestBean extends BaseCommonJsRequestBeanWrapper {

    public StoresMapLstRequestBean(BaseCommonJsRequestBean mBean) {
        super(mBean);
    }

    public String getLan() {
        return getStringByRootJson("lng");
    }

    public String getLat() {
        return getStringByRootJson("lat");
    }


}

