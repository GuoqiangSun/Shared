package cn.com.startai.sharedlib.app.js.requestBeanImpl.charger;

import cn.com.swain.baselib.jsInterface.request.bean.BaseCommonJsRequestBean;
import cn.com.swain.baselib.jsInterface.request.bean.BaseCommonJsRequestBeanWrapper;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/29 0029
 * desc :
 */
public class OrderDetailRequestBean extends BaseCommonJsRequestBeanWrapper {

    public OrderDetailRequestBean(BaseCommonJsRequestBean mBean) {
        super(mBean);
    }

    public String getNo() {
        return getStringByRootJson("no");
    }



}
