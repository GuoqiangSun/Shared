package cn.com.startai.sharedlib.app.js.requestBeanImpl;

import cn.com.swain.baselib.jsInterface.bean.BaseCommonJsRequestBean;
import cn.com.swain.baselib.jsInterface.bean.BaseCommonJsRequestBeanWrapper;

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
        setType(getStringByRootJson("type"));

        setValue(getStringByRootJson("value"));
    }

    public static final String TYPE_NAME = "name";//name
    public static final String TYPE_SURNAME = "surnam";//姓氏

    private String type;

    private void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public boolean typeIsName() {
        return getType().equalsIgnoreCase(TYPE_NAME);
    }

    public boolean typeIsSurname() {
        return getType().equalsIgnoreCase(TYPE_SURNAME);
    }

    private String value;

    private void setValue(String code) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }


}
