package cn.com.startai.sharedlib.app.view;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import cn.com.shared.weblib.activity.WebHomeActivity;
import cn.com.shared.weblib.fragment.GuideFragment;
import cn.com.startai.scansdk.ChargerScanActivity;
import cn.com.startai.sharedcharger.wxapi.WXLoginHelper;
import cn.com.startai.sharedlib.app.Debuger;
import cn.com.startai.sharedlib.app.FileManager;
import cn.com.startai.sharedlib.app.controller.Controller;
import cn.com.startai.sharedlib.app.js.Utils.Language;
import cn.com.startai.sharedlib.app.js.method2Impl.LanguageResponseMethod;
import cn.com.startai.sharedlib.app.mutual.IMutualCallBack;
import cn.com.startai.sharedlib.app.mutual.MutualManager;
import cn.com.swain.baselib.jsInterface.AbsJsInterface;
import cn.com.swain.baselib.util.PermissionRequest;
import cn.com.swain169.log.Tlog;

public class SharedMainActivity extends WebHomeActivity
        implements PermissionRequest.OnPermissionResult, IMutualCallBack {


    private void showStatusBar() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();


            window.clearFlags(
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS |
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION |
                    WindowManager.LayoutParams.FLAG_FULLSCREEN//显示状态栏
            );

            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION // 隐藏导航图标
            );

            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //api21新增接口
            window.setStatusBarColor(Color.TRANSPARENT);
//            window.setNavigationBarColor(Color.TRANSPARENT);
        } else {
            Window window = getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        showStatusBar();
        Tlog.v("HomeActivity  onWindowFocusChanged() ");
    }

    private final class LocaleChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Tlog.v("LocaleChangeReceiver onReceive :" + intent.getAction());
            if (Intent.ACTION_LOCALE_CHANGED.equals(intent.getAction())) {
                String type = Language.changeSystemLangToH5Lang(context);
                LanguageResponseMethod languageResponseMethod = LanguageResponseMethod.getLanguageResponseMethod();
                languageResponseMethod.setResult(true);
                languageResponseMethod.setLan(type);
                loadJs(languageResponseMethod.toMethod());
            }
        }
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Tlog.v("HomeActivity onConfigurationChanged() " + newConfig.locale.getLanguage());
        String type = Language.changeSystemLangToH5Lang(newConfig.locale.getLanguage());
        LanguageResponseMethod languageResponseMethod = LanguageResponseMethod.getLanguageResponseMethod();
        languageResponseMethod.setResult(true);
        languageResponseMethod.setLan(type);
        loadJs(languageResponseMethod.toMethod());
    }

    private PermissionRequest mPermissionRequest;
    private LocaleChangeReceiver mLocaleChangeReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        setTheme(android.R.style.Theme_Black_NoTitleBar_Fullscreen);

        Controller.getInstance().init(getApplication(), this);
        Controller.getInstance().onSCreate();

        super.onCreate(savedInstanceState);

        Tlog.v("WebHomeActivity  requestPermission() ");
        mPermissionRequest = new PermissionRequest(this,
                this);
        mPermissionRequest.requestAllPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION);

        IntentFilter filter = new IntentFilter(Intent.ACTION_LOCALE_CHANGED);
        mLocaleChangeReceiver = new LocaleChangeReceiver();
        registerReceiver(mLocaleChangeReceiver, filter);

    }

    @Override
    protected GuideFragment newGuideFragment() {
        return new SharedGuideFragment();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        MutualManager mutualManager = Controller.getInstance().getMutualManager();
        if (mutualManager != null) {
            mutualManager.onActivityResult(requestCode, resultCode, data);
        } else {
            Tlog.e("onActivityResult mutualManager=null ");
        }
    }

    @Override
    protected void onResume() {

        Controller.getInstance().onSResume();

        super.onResume();
    }

    @Override
    protected void onPause() {

        Controller.getInstance().onSPause();

        super.onPause();
    }


    @Override
    protected void onDestroy() {
        if (mPermissionRequest != null) {
            mPermissionRequest.release();
        }

        if (mLocaleChangeReceiver != null) {
            unregisterReceiver(mLocaleChangeReceiver);
        }

        Controller.getInstance().onSDestroy();
        WXLoginHelper.getInstance().releaseWxApi();

        super.onDestroy();

    }


    @Override
    public void jFinish() {

        Controller.getInstance().onSFinish();

        destroyMyself();

    }

    @Override
    public void callJs(String method) {
        loadJs(method);
    }

    @Override
    public void scanQR(int code) {
        ChargerScanActivity.showActivityForResult(this, code);
    }

    @Override
    public void jsStartActivityForResult(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
    }

    @Override
    public String getLoadUrl() {

        File localH5Resource = Debuger.getInstance().getLocalH5Resource();

        if (localH5Resource != null && localH5Resource.exists()) {
            Toast.makeText(getApplicationContext(),
                    "load from SDCard",
                    Toast.LENGTH_LONG).show();
            return "file://" + localH5Resource.getAbsolutePath();
        }

        return super.getLoadUrl();
    }

    @Override
    public ArrayList<AbsJsInterface> getJsInterfaces() {
        MutualManager mutualManager = Controller.getInstance().getMutualManager();
        if (mutualManager != null) {
            AbsJsInterface jsInterface = mutualManager.getJsInterface();
            if (jsInterface != null) {
                ArrayList<AbsJsInterface> mJsInterfaces = new ArrayList<>();
                mJsInterfaces.add(jsInterface);
                return mJsInterfaces;
            } else {
                Tlog.e("  jsManager.getJsInterface()==null ");
            }
        }

        return null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (mPermissionRequest != null) {
            mPermissionRequest.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }


    @Override
    public void onAllPermissionRequestFinish() {
        Tlog.v("WebHomeActivity onPermissionRequestFinish() ");

        FileManager.getInstance().recreate(getApplication());
        Debuger.getInstance().reCheckLogRecord(this);
    }

    @Override
    public void onPermissionRequestResult(String permission, boolean granted) {

    }
}
