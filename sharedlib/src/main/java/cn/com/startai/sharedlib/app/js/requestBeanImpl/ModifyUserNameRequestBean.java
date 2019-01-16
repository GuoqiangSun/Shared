package cn.com.startai.sharedlib.app.js.requestBeanImpl;

import cn.com.swain.baselib.jsInterface.request.bean.BaseCommonJsRequestBean;
import cn.com.swain.baselib.jsInterface.request.bean.BaseCommonJsRequestBeanWrapper;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/29 0029
 * desc :
 */
public class ModifyUserNameRequestBean extends BaseCommonJsRequestBeanWrapper {

    public ModifyUserNameRequestBean() {
        this(new BaseCommonJsRequestBean());
    }

    public ModifyUserNameRequestBean(BaseCommonJsRequestBean mBean) {
        super(mBean);
    }

    public static final String TYPE_NAME = "name";//name
    public static final String TYPE_SURNAME = "surnam";//姓氏


    public String getType() {
        return getStringByRootJson("type");
    }

    public boolean typeIsName() {
        return TYPE_NAME.equalsIgnoreCase(getType());
    }

    public boolean typeIsSurname() {
        return TYPE_SURNAME.equalsIgnoreCase(getType());
    }

    public String getValue() {
        return getStringByRootJson("value");
    }


}
