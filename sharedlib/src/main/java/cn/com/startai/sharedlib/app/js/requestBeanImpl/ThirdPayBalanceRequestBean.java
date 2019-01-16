package cn.com.startai.sharedlib.app.js.requestBeanImpl;

import cn.com.swain.baselib.jsInterface.request.bean.BaseCommonJsRequestBean;
import cn.com.swain.baselib.jsInterface.request.bean.BaseCommonJsRequestBeanWrapper;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/29 0029
 * desc : 请求充值
 */
public class ThirdPayBalanceRequestBean extends BaseCommonJsRequestBeanWrapper {

    public ThirdPayBalanceRequestBean(BaseCommonJsRequestBean mBean) {
        super(mBean);
    }

    public int getFee() {
        return getIntByRootJson("fee");
    }

    public int getPlatform() {
        return getIntByRootJson("platform");
    }

    public int getType() {
        return getIntByRootJson("type");
    }
}
