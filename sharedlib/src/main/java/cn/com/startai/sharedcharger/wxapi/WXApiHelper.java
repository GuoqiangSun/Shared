package cn.com.startai.sharedcharger.wxapi;

import android.app.Application;

import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import cn.com.swain.baselib.app.IApp.IApp;

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
                    //通过WXAPIFactory工厂获取IWXApI的示例
                    wxapi = WXAPIFactory.createWXAPI(app, Consts.APP_ID, true);
                    //将应用的appid注册到微信
                    wxapi.registerApp(Consts.APP_ID);
                }
            }
        }

    }


    public IWXAPI getWXApi(Application app) {
        init(app);
        return wxapi;
    }

    public void releaseWxApi() {
        wxapi = null;
    }


}
