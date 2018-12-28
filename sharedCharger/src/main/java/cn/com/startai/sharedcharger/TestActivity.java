package cn.com.startai.sharedcharger;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import org.xwalk.core.XWalkUIClient;
import org.xwalk.core.XWalkView;

import cn.com.shared.weblib.view.CrossWebView;
import cn.com.swain.baselib.log.Tlog;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/21 0021
 * desc :
 */
public class TestActivity extends AppCompatActivity {

    public static final String URL_H5_BLE_SOCKET_INDEX = "file:///android_asset/UI/index.html";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        ChargerScanActivity.showActivityForResult(this, code);

        setContentView(cn.com.shared.weblib.R.layout.weblib_framgment_web);


        CrossWebView mWebView = findViewById(R.id.web_view);

        mWebView.setUIClient(new XWalkUIClient(mWebView) {
            @Override
            public void onPageLoadStarted(XWalkView view, String url) {
                super.onPageLoadStarted(view, url);
                Tlog.v(" onPageLoadStarted ");
            }

            @Override
            public void onPageLoadStopped(XWalkView view, String url, LoadStatus status) {
                super.onPageLoadStopped(view, url, status);
                Tlog.v(" onPageLoadStopped ");
            }
        });

        mWebView.loadUrl(URL_H5_BLE_SOCKET_INDEX);

    }
}
