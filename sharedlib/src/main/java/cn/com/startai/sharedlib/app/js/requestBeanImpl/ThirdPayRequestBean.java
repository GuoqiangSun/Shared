package cn.com.startai.sharedlib.app.js.requestBeanImpl;

import cn.com.swain.baselib.jsInterface.bean.BaseCommonJsRequestBean;
import cn.com.swain.baselib.jsInterface.bean.BaseCommonJsRequestBeanWrapper;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/29 0029
 * desc :
 * <p>
 * <p>
 * msgType	是	String	0x5231	消息指令
 * no	是	String	LENT2018120509545933126190284T	订单号
 * fee	是	Number	100	支付金额（分）
 * currency	是	String	CNY	货币种类
 * description	是	String	incharge-押金充值	描述
 */
public class ThirdPayRequestBean extends BaseCommonJsRequestBeanWrapper {

    public ThirdPayRequestBean(BaseCommonJsRequestBean mBean) {
        super(mBean);
    }

    public int getPlatform(){
        return getIntByRootJson("platform");
    }

    public String getDescription() {
        return getStringByRootJson("description");
    }

    public String getCurrency() {
        return getStringByRootJson("currency");
    }

    public int getFee() {
        return getIntByRootJson("fee");
    }

    public String getNo() {
        return getStringByRootJson("no");
    }


}
