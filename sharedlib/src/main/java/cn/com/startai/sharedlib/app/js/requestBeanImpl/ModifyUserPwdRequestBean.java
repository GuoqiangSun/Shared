package cn.com.startai.sharedlib.app.js.requestBeanImpl;

import cn.com.swain.baselib.jsInterface.request.bean.BaseCommonJsRequestBean;
import cn.com.swain.baselib.jsInterface.request.bean.BaseCommonJsRequestBeanWrapper;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/29 0029
 * desc :
 */
public class ModifyUserPwdRequestBean extends BaseCommonJsRequestBeanWrapper {

    public ModifyUserPwdRequestBean(BaseCommonJsRequestBean mBean) {
        super(mBean);
    }

    public String getNewPwd() {
        return getStringByRootJson("newPassword");
    }


    public String getOldPwd() {
        return getStringByRootJson("oldPassword");
    }


}
