package cn.com.startai.sharedlib.app.js.requestBeanImpl;

import cn.com.startai.sharedlib.app.js.Utils.JsMsgType;
import cn.com.swain.baselib.jsInterface.request.bean.BaseCommonJsRequestBean;
import cn.com.swain.baselib.jsInterface.request.bean.BaseCommonJsRequestBeanWrapper;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/29 0029
 * desc :
 */
public class AppInstallRequestBean extends BaseCommonJsRequestBeanWrapper {

    public AppInstallRequestBean(BaseCommonJsRequestBean mBean) {
        super(mBean);
    }

    public int getType() {
        return getIntByRootJson("type");
    }

    public boolean isWechat() {
        return getType() == JsMsgType.WECHAT_TYPE;
    }

    public boolean isAli() {
        return getType() == JsMsgType.ALI_TYPE;
    }
}
