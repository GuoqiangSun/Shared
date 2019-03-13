package cn.com.startai.sharedlib.app.js.requestBeanImpl.charger;

import cn.com.swain.baselib.jsInterface.request.bean.BaseCommonJsRequestBean;
import cn.com.swain.baselib.jsInterface.request.bean.BaseCommonJsRequestBeanWrapper;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/29 0029
 * desc :
 */
public class OrderListRequestBean extends BaseCommonJsRequestBeanWrapper {

    public OrderListRequestBean(BaseCommonJsRequestBean mBean) {
        super(mBean);
    }

    public int getType() {
        return getIntByRootJson("type");
    }


    public int getPage() {
        return getIntByRootJson("page");
    }


    public int getCount() {
        return getIntByRootJson("count");
    }

}
