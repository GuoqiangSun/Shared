package cn.com.startai.sharedlib.app.js.method2Impl;

import org.json.JSONException;
import org.json.JSONObject;

import cn.com.startai.chargersdk.entity.C_0x8308;
import cn.com.startai.sharedlib.app.js.Utils.JsMsgType;
import cn.com.swain.baselib.jsInterface.response.BaseResponseMethod2;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/28 0028
 * desc :
 */
public class BalanceDepositResponseMethod extends BaseResponseMethod2 {

    public static BalanceDepositResponseMethod getBalanceDepositResponseMethod() {
        return new BalanceDepositResponseMethod();
    }

    public BalanceDepositResponseMethod() {
        super(JsMsgType.TYPE_RESPONSE_BALANCE_DEPOSIT);
    }

    @Override
    public void releaseCache() {
        super.releaseCache();
        contentBean = null;
    }


    private C_0x8308.Resp.ContentBean contentBean;

    public void setContent(C_0x8308.Resp.ContentBean contentBean) {
        this.contentBean = contentBean;
    }

    /**
     * userId : userId
     * amount : 2000
     * deposit : 10000
     */

    @Override
    public String toMethod() {

        JSONObject data = new JSONObject();
        if (getResult() && contentBean != null) {
            try {
                data.put("amount", contentBean.getAmount());
                data.put("deposit", contentBean.getDeposit());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return toMethod(data);

    }
}
