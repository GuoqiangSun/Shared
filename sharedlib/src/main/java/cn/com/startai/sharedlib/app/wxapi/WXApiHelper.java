package cn.com.startai.sharedlib.app.wxapi;

import android.app.Application;

import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import cn.com.startai.sharedlib.app.global.CustomManager;
import cn.com.swain.baselib.app.IApp.IApp;
import cn.com.swain.baselib.log.Tlog;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/23 0023
 * desc :
 */
public class WXApiHelper implements IApp {

    private WXApiHelper() {
    }

    private static final class ClassHolder {
        private static final WXApiHelper WX = new WXApiHelper();
    }

    public static WXApiHelper getInstance() {
        return ClassHolder.WX;
    }

    private IWXAPI wxapi;

    @Override
    public void init(Application app) {
        //

        if (wxapi == null) {
            synchronized (this) {
                if (wxapi == null) {

                    String appid = null;

                    if (CustomManager.getInstance().isSharedCharger()) {
                        appid = Constants.APP_ID_RUIOO;
                    } else if(CustomManager.getInstance().isSynerMax()){
                        appid = Constants.APP_ID_SYNERMAX;
                    }else {
                        Tlog.e("WXApiHelper unknown custom ");
                    }

                    if (appid != null) {
                        //通过WXAPIFactory工厂获取IWXApI的示例
                        wxapi = WXAPIFactory.createWXAPI(app, appid, true);
                        //将应用的appid注册到微信
                        wxapi.registerApp(appid);
                        Tlog.d("WXApiHelper init success ");
                    }

                }
            }
        }

    }


    public IWXAPI getWXApi(Application app) {
        init(app);
        return wxapi;
    }

    public void releaseWxApi() {
        if (wxapi != null) {
            wxapi.unregisterApp();
            wxapi = null;
        }
    }


    /**
     * Created by Robin on 2018/8/21.
     * qq: 419109715 彬影
     */

    public static class Constants {

        public static final String APP_ID_RUIOO = "wx421dfb9ea0670250";
        public static final String APP_SECRET_RUIOO = "eb5b264c990ef5e0a8c2a6d7152bab09";

        public static final String APP_ID_SYNERMAX = "wx8754b00a740c1a20";
        public static final String APP_SECRET_SYNERMAX = "8b1fa11f15b771cd4411facd3bbf5339";

        //https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code

        public static final String URL_GET_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token?";
        //https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN
        public static final String URL_REFRESH_TOKEN = "https://api.weixin.qq.com/sns/oauth2/refresh_token?";
        //https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID
        public static final String URL_GET_SUSERINFO = "https://api.weixin.qq.com/sns/userinfo?";


        public static final String WX_LOGIN_TAG = "startai_wx_login";
        public static final String WX_BIND_TAG = "startai_wx_bind";


    }
}
