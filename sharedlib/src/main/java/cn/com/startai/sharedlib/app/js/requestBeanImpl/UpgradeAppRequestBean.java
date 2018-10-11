package cn.com.startai.sharedlib.app.js.requestBeanImpl;

import cn.com.swain.baselib.jsInterface.bean.BaseCommonJsRequestBean;
import cn.com.swain.baselib.jsInterface.bean.BaseCommonJsRequestBeanWrapper;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/29 0029
 * desc :
 */
public class UpgradeAppRequestBean extends BaseCommonJsRequestBeanWrapper {

    public UpgradeAppRequestBean() {
        this(new BaseCommonJsRequestBean());
    }

    public UpgradeAppRequestBean(BaseCommonJsRequestBean mBean) {
        super(mBean);
        setPath(getStringByRootJson("path"));
    }

    private String path;

    private void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

}
