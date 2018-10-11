package cn.com.startai.sharedlib.app.js.requestBeanImpl;

import cn.com.swain.baselib.jsInterface.bean.BaseCommonJsRequestBean;
import cn.com.swain.baselib.jsInterface.bean.BaseCommonJsRequestBeanWrapper;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/29 0029
 * desc :
 */
public class GetIdentityCodeRequestBean extends BaseCommonJsRequestBeanWrapper {

    public GetIdentityCodeRequestBean() {
        this(new BaseCommonJsRequestBean());
    }

    public GetIdentityCodeRequestBean(BaseCommonJsRequestBean mBean) {
        super(mBean);
        setPhone(getStringByRootJson("phone"));
    }

    private String phone;

    private void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

}
