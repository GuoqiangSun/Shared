package cn.com.startai.sharedlib.app.js.requestBeanImpl;

import cn.com.swain.baselib.jsInterface.bean.BaseCommonJsRequestBean;
import cn.com.swain.baselib.jsInterface.bean.BaseCommonJsRequestBeanWrapper;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/29 0029
 * desc :
 */
public class GiveBackBorrowDeviceRequestBean extends BaseCommonJsRequestBeanWrapper {

    public GiveBackBorrowDeviceRequestBean(BaseCommonJsRequestBean mBean) {
        super(mBean);
    }

    public String getIMEI() {
        return getStringByRootJson("imei");
    }

}
