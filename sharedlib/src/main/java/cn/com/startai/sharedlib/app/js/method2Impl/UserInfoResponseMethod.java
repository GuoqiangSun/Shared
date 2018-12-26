package cn.com.startai.sharedlib.app.js.method2Impl;

import org.json.JSONException;
import org.json.JSONObject;

import cn.com.startai.mqttsdk.busi.entity.C_0x8024;
import cn.com.startai.sharedlib.app.js.Utils.JsMsgType;
import cn.com.swain.baselib.jsInterface.method.BaseResponseMethod2;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/28 0028
 * desc :
 */
public class UserInfoResponseMethod extends BaseResponseMethod2 {

    public static UserInfoResponseMethod getUserInfoResponseMethod() {
        return new UserInfoResponseMethod();
    }

    public UserInfoResponseMethod() {
        super(JsMsgType.TYPE_RESPONSE_USER_INFO);
    }

    @Override
    public void releaseCache() {
        super.releaseCache();
        contentBean = null;
    }

    private C_0x8024.Resp.ContentBean contentBean;

    public void setContent(C_0x8024.Resp.ContentBean contentBean) {
        this.contentBean = contentBean;
    }

    @Override
    public String toMethod() {

        JSONObject data = new JSONObject();
        if (getResult()) {
            try {

                data.put("userName", contentBean.getUserName());
                data.put("birthday", contentBean.getBirthday());
                data.put("province", contentBean.getProvince());
                data.put("city", contentBean.getCity());
                data.put("town", contentBean.getTown());
                data.put("address", contentBean.getAddress());
                data.put("nickName", contentBean.getNickName());
                data.put("headPic", contentBean.getHeadPic());
                data.put("sex", contentBean.getSex());
                data.put("firstName", contentBean.getFirstName());
                data.put("lastName", contentBean.getLastName());
                data.put("email", contentBean.getEmail());
                data.put("mobile", contentBean.getMobile());
                data.put("isHavePwd", contentBean.getIsHavePwd() == 1);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return toMethod(data);

    }
}
