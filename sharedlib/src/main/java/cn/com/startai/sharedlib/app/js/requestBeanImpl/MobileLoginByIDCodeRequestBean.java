package cn.com.startai.sharedlib.app.js.requestBeanImpl;

import cn.com.swain.baselib.jsInterface.bean.BaseCommonJsRequestBean;
import cn.com.swain.baselib.jsInterface.bean.BaseCommonJsRequestBeanWrapper;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/29 0029
 * desc :
 */
public class MobileLoginByIDCodeRequestBean extends BaseCommonJsRequestBeanWrapper {

    public MobileLoginByIDCodeRequestBean() {
        this(new BaseCommonJsRequestBean());
    }

    public MobileLoginByIDCodeRequestBean(BaseCommonJsRequestBean mBean) {
        super(mBean);
        setPhone(getStringByRootJson("phone"));

        setCode(getStringByRootJson("code"));
    }

    private String phone;

    private void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    private String code;

    private void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }


}
