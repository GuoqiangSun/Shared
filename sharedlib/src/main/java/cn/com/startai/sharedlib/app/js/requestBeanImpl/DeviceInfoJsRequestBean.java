package cn.com.startai.sharedlib.app.js.requestBeanImpl;

import cn.com.swain.baselib.jsInterface.bean.BaseCommonJsRequestBean;
import cn.com.swain.baselib.jsInterface.bean.BaseCommonJsRequestBeanWrapper;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/29 0029
 * desc :
 */
public class DeviceInfoJsRequestBean extends BaseCommonJsRequestBeanWrapper {

    public DeviceInfoJsRequestBean() {
        this(new BaseCommonJsRequestBean());
    }

    public DeviceInfoJsRequestBean(BaseCommonJsRequestBean mBean) {
        super(mBean);
        setImei(getStringByRootJson("imei"));
    }

    private String imei;

    private void setImei(String imei) {
        this.imei = imei;
    }

    public String getIMEI() {
        return imei;
    }


}
