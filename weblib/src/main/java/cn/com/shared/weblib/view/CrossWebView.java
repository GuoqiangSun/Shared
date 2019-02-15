package cn.com.shared.weblib.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;

import org.xwalk.core.XWalkPreferences;
import org.xwalk.core.XWalkSettings;
import org.xwalk.core.XWalkView;

import java.util.Locale;

import cn.com.swain.baselib.log.Tlog;

/**
 * author: Guoqiang_Sun
 * date : 2018/3/28 0028
 * desc :
 */

public class CrossWebView extends XWalkView {

    public static boolean REMOTE_DEBUGGING = false;

    public CrossWebView(Context context) {
        super(context);
        init();
    }

    public CrossWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        // crossWalk 在浏览器调试
        XWalkPreferences.setValue(XWalkPreferences.REMOTE_DEBUGGING, REMOTE_DEBUGGING);

        setDrawingCacheEnabled(true);
        setChildrenDrawingCacheEnabled(true);

        XWalkSettings settings = getSettings();

        // 支持 JavaScript
//        settings.setJavaScriptEnabled(true);

        //设置自适应屏幕，两者合用
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);

        //缩放操作
        settings.setSupportZoom(false);


        //其他细节操作
        settings.setAllowFileAccess(true);
        settings.setAllowContentAccess(true);

        //js弹窗
//        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(false);

        settings.setSupportMultipleWindows(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
        settings.setLoadsImagesAutomatically(true);


        settings.setDomStorageEnabled(true);
        settings.setCacheMode(XWalkSettings.LOAD_DEFAULT);

    }

    @Deprecated
    public void loadJs(String url, Object... args) {
        try {

            String JAVA_SCRIPT = "javascript:";
            if (!url.startsWith(JAVA_SCRIPT)) {
                url = JAVA_SCRIPT + url;
            }

            String formatUrl = String.format(Locale.CHINA, url, args);
//            Tlog.d(TAG, getUrl() + "\n" + formatUrl);
            this.loadUrl(formatUrl);

        } catch (Exception e) {
            e.printStackTrace();

            Tlog.e(" loadJs Exception : ", e);

        }
    }

    private boolean status = true;

    public void disableGoBack(boolean status) {
        this.status = status;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (status) {
                return false;
            }
        }
        return super.dispatchKeyEvent(event);

    }

}
