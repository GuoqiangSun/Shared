package cn.com.startai.sharedlib.app.js.requestBeanImpl;

import cn.com.swain.baselib.jsInterface.bean.BaseCommonJsRequestBean;
import cn.com.swain.baselib.jsInterface.bean.BaseCommonJsRequestBeanWrapper;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/29 0029
 * desc :
 */
public class MobileLoginByIDCodeRequestBean extends BaseCommonJsRequestBeanWrapper {

    public MobileLoginByIDCodeRequestBean(BaseCommonJsRequestBean mBean) {
        super(mBean);
    }


    public String getPhone() {
        return getStringByRootJson("phone");
    }


    public String getCode() {
        return getStringByRootJson("code");
    }


}
