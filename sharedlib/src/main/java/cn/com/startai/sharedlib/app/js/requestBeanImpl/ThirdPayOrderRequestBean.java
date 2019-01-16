package cn.com.startai.sharedlib.app.js.requestBeanImpl;

import cn.com.swain.baselib.jsInterface.request.bean.BaseCommonJsRequestBean;
import cn.com.swain.baselib.jsInterface.request.bean.BaseCommonJsRequestBeanWrapper;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/29 0029
 * desc :
 */
public class ThirdPayOrderRequestBean extends BaseCommonJsRequestBeanWrapper {

    public ThirdPayOrderRequestBean(BaseCommonJsRequestBean mBean) {
        super(mBean);
    }

    public int getPlatform(){
        return getIntByRootJson("platform");
    }

    public String getDescription() {
        return getStringByRootJson("description");
    }

    public String getCurrency() {
        return getStringByRootJson("currency");
    }

    public int getFee() {
        return getIntByRootJson("fee");
    }

    public String getNo() {
        return getStringByRootJson("no");
    }


}
