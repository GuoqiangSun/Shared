package cn.com.shared.weblib.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.xwalk.core.XWalkJavascriptResult;
import org.xwalk.core.XWalkUIClient;
import org.xwalk.core.XWalkView;

import java.util.ArrayList;

import cn.com.shared.weblib.R;
import cn.com.shared.weblib.view.CrossWebView;
import cn.com.shared.weblib.view.DialogUtils;
import cn.com.swain.baselib.jsInterface.AbsJsInterface;
import cn.com.swain.baselib.log.Tlog;

/**
 * author: Guoqiang_Sun
 * date : 2018/4/13 0013
 * desc :
 */

public class WebFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tlog.v(" WebFragment onCreate() ");
    }

    private CrossWebView mWebView;

    private volatile boolean firstAdd = true;

    private IWebFragmentCallBack mCallBack;

    private View mRootView;

    private View inflateView() {
        Tlog.v(" WebFragment inflateView() ");

        View mRootView = View.inflate(getActivity(), R.layout.weblib_framgment_web,
                null);

        mWebView = mRootView.findViewById(R.id.web_view);
        return mRootView;
    }

    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Tlog.v(" WebFragment onCreateView() " + hashCode());
        if (mRootView == null) {
            mRootView = inflateView();
        }

        Activity activity = getActivity();
        if (activity instanceof IWebFragmentCallBack) {
            mCallBack = (IWebFragmentCallBack) activity;
        }

        if (mCallBack == null) {
            throw new NullPointerException(" webFragment IWebFragmentCallBack must not be null ");
        }

        ArrayList<AbsJsInterface> jsInterfaces = mCallBack.getJsInterfaces();

        if (jsInterfaces != null && jsInterfaces.size() > 0) {
            for (int i = 0; i < jsInterfaces.size(); i++) {
                AbsJsInterface absJsInterface = jsInterfaces.get(i);
                Tlog.e(" addJavascriptInterface :" + absJsInterface.getName());
                mWebView.addJavascriptInterface(absJsInterface.getJsInterface(), absJsInterface.getName());
            }
        }

//        AbsJsInterface jsInterfaces = new CommonJsRequest(Looper.getMainLooper(), null);
//        Tlog.e(" addJavascriptInterface :" + jsInterfaces.getName());
//        mWebView.addJavascriptInterface(jsInterfaces, jsInterfaces.getName());

        mWebView.setUIClient(new MXWalkUIClient(mWebView));
        String loadUrl = mCallBack.getLoadUrl();
        Tlog.v(" loadUrl:" + loadUrl);
        mWebView.loadUrl(loadUrl);

        return mRootView;
    }

    @Override
    public void onDestroyView() {
        Tlog.v(" WebFragment onDestroyView() ");
        if (mWebView != null) {
            mWebView.stopLoading();
            mWebView.onDestroy();
            mWebView = null;
        }
        if (mRootView != null) {
            ViewGroup vg = (ViewGroup) mRootView.getParent();
            if (vg != null) {
                vg.removeView(mRootView);
            }
            mRootView = null;
        }
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Tlog.v(" WebFragment onDestroy() ");
    }

    public void disableGoBack(boolean status) {
        if (mWebView != null) {
            mWebView.disableGoBack(status);
        }
    }

    public void loadJs(String method) {
        if (mWebView != null) {
            if (Tlog.isDebug()) {
                Tlog.e(AbsJsInterface.TAG, " loadJs:" + method);
            }
            mWebView.loadUrl(method);
        } else {
            Tlog.e("WebFragment loadJs mWebView=null ");
        }
    }

    private class MXWalkUIClient extends XWalkUIClient {

        MXWalkUIClient(XWalkView view) {
            super(view);
        }

        @Override
        public void onPageLoadStarted(XWalkView view, String url) {
            super.onPageLoadStarted(view, url);
            Tlog.v("MXWalkUIClient onPageLoadStarted() ");
        }

        @Override
        public void onPageLoadStopped(XWalkView view, String url, LoadStatus status) {
            super.onPageLoadStopped(view, url, status);
            Tlog.v("MXWalkUIClient onPageLoadStopped() ");
            if (firstAdd) {
                firstAdd = false;
                if (mCallBack != null) {
                    mCallBack.onWebLoadFinish();
                }
            }
        }

        @Override
        public boolean onJsAlert(XWalkView view, String url, String message, final XWalkJavascriptResult result) {
            Tlog.v("MXWalkUIClient onJsAlert() " + message);
            DialogUtils.alert(getActivity(), message, result);
            return true;
        }

        @Override
        public boolean onJsConfirm(XWalkView view, String url, String message, XWalkJavascriptResult result) {
            Tlog.v("MXWalkUIClient onJsConfirm() ");
            return super.onJsConfirm(view, url, message, result);
        }

        @Override
        public boolean onJsPrompt(XWalkView view, String url, String message, String defaultValue, XWalkJavascriptResult result) {
            Tlog.v("MXWalkUIClient onJsPrompt() ");
            return super.onJsPrompt(view, url, message, defaultValue, result);
        }

    }

    /**
     * author: Guoqiang_Sun
     * date : 2018/4/16 0016
     * desc :
     */
    public interface IWebFragmentCallBack {

        void onWebLoadFinish();

        String getLoadUrl();

        ArrayList<AbsJsInterface> getJsInterfaces();

    }
}
