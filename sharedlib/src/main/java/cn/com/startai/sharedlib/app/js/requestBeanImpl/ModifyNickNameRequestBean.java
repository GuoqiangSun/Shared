package cn.com.startai.sharedlib.app.js.requestBeanImpl;

import cn.com.swain.baselib.jsInterface.request.bean.BaseCommonJsRequestBean;
import cn.com.swain.baselib.jsInterface.request.bean.BaseCommonJsRequestBeanWrapper;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/29 0029
 * desc :
 */
public class ModifyNickNameRequestBean extends BaseCommonJsRequestBeanWrapper {

    public ModifyNickNameRequestBean(BaseCommonJsRequestBean mBean) {
        super(mBean);
    }

    public String getNickname() {
        return getStringByRootJson("nickName");
    }


}
