package cn.com.startai.sharedlib.app.js.requestBeanImpl;

import cn.com.startai.sharedlib.app.js.Utils.JsMsgType;
import cn.com.swain.baselib.jsInterface.request.bean.BaseCommonJsRequestBean;
import cn.com.swain.baselib.jsInterface.request.bean.BaseCommonJsRequestBeanWrapper;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/29 0029
 * desc :
 */
public class ThirdLoginRequestBean extends BaseCommonJsRequestBeanWrapper {

    public ThirdLoginRequestBean(BaseCommonJsRequestBean mBean) {
        super(mBean);
    }

    public String getType() {
        return getStringByRootJson("type");
    }

    public boolean isGoogleType() {
        return JsMsgType.THIRD_LOGIN_TYPE_GOOGLE.equalsIgnoreCase(getType());
    }

    public boolean isTwitterType() {
        return JsMsgType.THIRD_LOGIN_TYPE_TWITTER.equalsIgnoreCase(getType());
    }

    public boolean isFacebookType() {
        return JsMsgType.THIRD_LOGIN_TYPE_FACEBOOK.equalsIgnoreCase(getType());
    }
}
