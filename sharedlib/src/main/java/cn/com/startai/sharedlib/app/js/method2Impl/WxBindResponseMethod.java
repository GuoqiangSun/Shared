package cn.com.startai.sharedlib.app.js.method2Impl;

import org.json.JSONException;
import org.json.JSONObject;

import cn.com.startai.mqttsdk.busi.entity.C_0x8037;
import cn.com.startai.sharedlib.app.js.Utils.JsMsgType;
import cn.com.swain.baselib.jsInterface.response.BaseResponseMethod2;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/28 0028
 * desc :
 */
public class WxBindResponseMethod extends BaseResponseMethod2 {

    public static WxBindResponseMethod getWxBindResponseMethod() {
        return new WxBindResponseMethod();
    }

    public WxBindResponseMethod() {
        super(JsMsgType.TYPE_RESPONSE_WX_BIND);
    }

    @Override
    public void releaseCache() {
        super.releaseCache();

        content = null;
        binding = false;
    }

    private C_0x8037.Resp.ContentBean content;

    public void setContent(C_0x8037.Resp.ContentBean content) {
        this.content = content;
    }


    private boolean binding;

    public void setBinding(boolean binding) {
        this.binding = binding;
    }

    @Override
    public String toMethod() {

        JSONObject contentObj = new JSONObject();
        if (getResult()) {
            try {
                contentObj.put("binding", binding);

                if (content != null) {
                    C_0x8037.Req.ContentBean.UserinfoBean userinfo = content.getUserinfo();
                    if (userinfo != null) {

                        /**
                         * openid : OPENID
                         * nickname : NICKNAME
                         * email : email
                         * sex : 1
                         * province : PROVINCE
                         * city : CITY
                         * country : COUNTRY
                         * headimgurl : http://url
                         * username : username
                         * firstName : firstName
                         * lastName : lastName
                         * address : address
                         * unionid : unionid
                         */

                        contentObj.put("nickname", userinfo.getNickname());
                        contentObj.put("email", userinfo.getEmail());
                        contentObj.put("sex", userinfo.getSex());
                        contentObj.put("province", userinfo.getProvince());
                        contentObj.put("city", userinfo.getCity());
                        contentObj.put("country", userinfo.getCountry());
                        contentObj.put("headimgurl", userinfo.getHeadimgurl());
                        contentObj.put("username", userinfo.getUsername());
                        contentObj.put("firstName", userinfo.getFirstName());
                        contentObj.put("lastName", userinfo.getLastName());
                        contentObj.put("address", userinfo.getAddress());
                        contentObj.put("unionid", userinfo.getUnionid());
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return toMethod(contentObj);

    }


}
