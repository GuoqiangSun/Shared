package cn.com.startai.sharedlib.app.js.requestBeanImpl;

import cn.com.swain.baselib.jsInterface.bean.BaseCommonJsRequestBean;
import cn.com.swain.baselib.jsInterface.bean.BaseCommonJsRequestBeanWrapper;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/29 0029
 * desc : 请求充值
 */
public class TransactionDetailsRequestBean extends BaseCommonJsRequestBeanWrapper {

    public TransactionDetailsRequestBean(BaseCommonJsRequestBean mBean) {
        super(mBean);
    }

    public int getTransactionType() {
        return getIntByRootJson("transactionType");
    }

    public int getPage() {
        return getIntByRootJson("page");
    }

    public int getCount() {
        return getIntByRootJson("count");
    }
}
