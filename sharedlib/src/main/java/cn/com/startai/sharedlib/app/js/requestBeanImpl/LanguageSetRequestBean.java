package cn.com.startai.sharedlib.app.js.requestBeanImpl;

import cn.com.swain.baselib.jsInterface.bean.BaseCommonJsRequestBean;
import cn.com.swain.baselib.jsInterface.bean.BaseCommonJsRequestBeanWrapper;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/29 0029
 * desc :
 */
public class LanguageSetRequestBean extends BaseCommonJsRequestBeanWrapper {

    public LanguageSetRequestBean() {
        this(new BaseCommonJsRequestBean());
    }

    public LanguageSetRequestBean(BaseCommonJsRequestBean mBean) {
        super(mBean);
        setLan(getStringByRootJson("language"));
    }

    private String lan;

    private void setLan(String lan) {
        this.lan = lan;
    }

    public String getLan() {
        return lan;
    }

}
