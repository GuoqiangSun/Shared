package cn.com.startai.sharedlib.app.js.requestBeanImpl;

import cn.com.swain.baselib.jsInterface.bean.BaseCommonJsRequestBean;
import cn.com.swain.baselib.jsInterface.bean.BaseCommonJsRequestBeanWrapper;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/29 0029
 * desc :
 */
public class ModifyUserPwdRequestBean extends BaseCommonJsRequestBeanWrapper {

    public ModifyUserPwdRequestBean() {
        this(new BaseCommonJsRequestBean());
    }

    public ModifyUserPwdRequestBean(BaseCommonJsRequestBean mBean) {
        super(mBean);
        setNewPwd(getStringByRootJson("newPassword"));

        setOldPwd(getStringByRootJson("oldPassword"));
    }

    private String newPwd;

    private void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }

    public String getNewPwd() {
        return newPwd;
    }

    private String oldPwd;

    private void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }

    public String getOldPwd() {
        return oldPwd;
    }


}
