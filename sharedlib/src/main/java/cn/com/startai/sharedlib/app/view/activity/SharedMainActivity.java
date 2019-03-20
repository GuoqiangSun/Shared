package cn.com.startai.sharedlib.app.view.activity;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import cn.com.startai.scansdk.ChargerScanActivity;
import cn.com.startai.sharedlib.app.controller.Controller;
import cn.com.startai.sharedlib.app.global.Debuger;
import cn.com.startai.sharedlib.app.global.FileManager;
import cn.com.startai.sharedlib.app.js.Utils.Language;
import cn.com.startai.sharedlib.app.js.method2Impl.LanguageResponseMethod;
import cn.com.startai.sharedlib.app.mutual.IMutualCallBack;
import cn.com.startai.sharedlib.app.mutual.MutualManager;
import cn.com.startai.sharedlib.app.view.fragment.BaseFragment;
import cn.com.startai.sharedlib.app.view.fragment.SharedGuideFragment;
import cn.com.swain.baselib.app.IApp.IService;
import cn.com.swain.baselib.app.utils.StatusBarUtil;
import cn.com.swain.baselib.jsInterface.AbsJsInterface;
import cn.com.swain.baselib.jsInterface.response.BaseResponseMethod;
import cn.com.swain.baselib.log.Tlog;
import cn.com.swain.baselib.permission.PermissionGroup;
import cn.com.swain.baselib.permission.PermissionHelper;
import cn.com.swain.baselib.permission.PermissionRequest;


/**
 * author: Guoqiang_Sun
 * date : 2018/3/28 0029
 * desc :
 */
public class SharedMainActivity extends WebHomeActivity
        implements IMutualCallBack {

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Tlog.v("SharedMainActivity  onWindowFocusChanged() ");
        StatusBarUtil.fullscreenShowBarFontBlack(getWindow());
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
                callJs(languageResponseMethod);
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
        callJs(languageResponseMethod);
    }

    private PermissionRequest mPermissionRequest;
    private LocaleChangeReceiver mLocaleChangeReceiver;
    private IService IService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        setTheme(android.R.style.Theme_Black_NoTitleBar_Fullscreen);

        super.onCreate(savedInstanceState);
        Tlog.v("SharedMainActivity  onCreate() ");

        requestPermission();

        IntentFilter filter = new IntentFilter(Intent.ACTION_LOCALE_CHANGED);
        mLocaleChangeReceiver = new LocaleChangeReceiver();
        registerReceiver(mLocaleChangeReceiver, filter);

        Controller.getInstance().init(getApplication(), this);
        IService = Controller.getInstance();
        IService.onSCreate();

    }

    private void requestPermission() {
        Context applicationContext = getApplicationContext();

        ArrayList<String> permissions = new ArrayList<>(2);

        if (!PermissionHelper.isGranted(applicationContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                || !PermissionHelper.isGranted(applicationContext, Manifest.permission.READ_EXTERNAL_STORAGE)) {

            permissions.add(PermissionGroup.STORAGE);

        } else {
            FileManager.getInstance().recreate(getApplication());
            Debuger.getInstance().reCheckLogRecord(applicationContext);
        }

        if (!PermissionHelper.isGranted(applicationContext, Manifest.permission.ACCESS_COARSE_LOCATION)
                || !PermissionHelper.isGranted(applicationContext, Manifest.permission.ACCESS_FINE_LOCATION)) {
            permissions.add(PermissionGroup.LOCATION);
        }

        if (permissions.size() > 0) {

            String[] strings = permissions.toArray(new String[0]);

            mPermissionRequest = new PermissionRequest(this);
            mPermissionRequest.requestPermissions(new PermissionRequest.OnPermissionFinish() {
                @Override
                public void onAllPermissionRequestFinish() {
                    Tlog.v("SharedMainActivity onAllPermissionRequestFinish() ");

                }
            }, new PermissionRequest.OnPermissionResult() {
                @Override
                public boolean onPermissionRequestResult(String permission, boolean granted) {

                    Tlog.v("SharedMainActivity permission " + permission + " granted:" + granted);

                    if (granted && PermissionGroup.STORAGE.equalsIgnoreCase(permission)) {
                        FileManager.getInstance().recreate(getApplication());
                        Debuger.getInstance().reCheckLogRecord(getApplication());
                    }

                    return true;
                }
            }, strings);
        } else {
            Tlog.d(" no permission need request ");
        }

    }

    @Override
    protected BaseFragment newGuideFragment() {
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

        if (IService != null) {
            IService.onSResume();
        }

        super.onResume();
    }

    @Override
    protected void onPause() {
        if (IService != null) {
            IService.onSPause();
        }

        super.onPause();
    }


    @Override
    protected void onDestroy() {
        if (mPermissionRequest != null) {
            mPermissionRequest.release();
            mPermissionRequest = null;
        }

        if (mLocaleChangeReceiver != null) {
            unregisterReceiver(mLocaleChangeReceiver);
        }

        if (IService != null) {
            IService.onSDestroy();
            IService = null;
        }

        super.onDestroy();

    }


    @Override
    public void jFinish() {
        if (IService != null) {
            IService.onSFinish();
        }
        destroyMyself();

    }

    @Override
    public void callJs(BaseResponseMethod mBaseResponseMethod) {
        callJs(mBaseResponseMethod.toMethod());
    }

    @Override
    public void scanQR(int requestCode) {
        ChargerScanActivity.showActivityForResult(this, requestCode);
    }

    @Override
    public void jsStartActivityForResult(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public String getLoadUrl() {

        if (Debuger.isH5Debug && PermissionHelper.isGranted(getApplication(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
            File localH5Resource = Debuger.getInstance().getLocalH5Resource();
            if (localH5Resource != null && localH5Resource.exists()) {
                Toast.makeText(getApplicationContext(), "load from SDCard", Toast.LENGTH_LONG).show();
                return "file://" + localH5Resource.getAbsolutePath();
            }
        }

        return super.getLoadUrl();
    }

    @Override
    public ArrayList<AbsJsInterface> getJsInterfaces() {
        MutualManager mutualManager = Controller.getInstance().getMutualManager();

        Tlog.d("getJsInterfaces mutualManager=null? " + (mutualManager == null));

        if (mutualManager != null) {
            AbsJsInterface jsInterface = mutualManager.getJsInterface();
            if (jsInterface != null) {
                ArrayList<AbsJsInterface> mJsInterfaces = new ArrayList<>();
                mJsInterfaces.add(jsInterface);
                return mJsInterfaces;
            } else {
                Tlog.e(" SharedMainActivity mutualManager.getJsInterface()==null ");
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


}
