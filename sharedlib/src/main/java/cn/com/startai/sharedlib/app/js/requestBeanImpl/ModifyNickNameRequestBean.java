package cn.com.startai.sharedlib.app.js.requestBeanImpl;

import cn.com.swain.baselib.jsInterface.bean.BaseCommonJsRequestBean;
import cn.com.swain.baselib.jsInterface.bean.BaseCommonJsRequestBeanWrapper;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/29 0029
 * desc :
 */
public class ModifyNickNameRequestBean extends BaseCommonJsRequestBeanWrapper {

    public ModifyNickNameRequestBean() {
        this(new BaseCommonJsRequestBean());
    }

    public ModifyNickNameRequestBean(BaseCommonJsRequestBean mBean) {
        super(mBean);
        setNickname(getStringByRootJson("nickName"));
    }

    private String value;

    private void setNickname(String value) {
        this.value = value;
    }

    public String getNickname() {
        return value;
    }


}
