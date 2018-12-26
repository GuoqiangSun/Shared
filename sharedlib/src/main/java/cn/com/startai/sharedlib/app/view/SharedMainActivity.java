package cn.com.startai.sharedlib.app.view;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import cn.com.shared.weblib.activity.WebHomeActivity;
import cn.com.shared.weblib.fragment.GuideFragment;
import cn.com.startai.scansdk.ChargerScanActivity;
import cn.com.startai.sharedcharger.wxapi.WXApiHelper;
import cn.com.startai.sharedlib.app.Debuger;
import cn.com.startai.sharedlib.app.FileManager;
import cn.com.startai.sharedlib.app.controller.Controller;
import cn.com.startai.sharedlib.app.js.Utils.Language;
import cn.com.startai.sharedlib.app.js.method2Impl.LanguageResponseMethod;
import cn.com.startai.sharedlib.app.mutual.IMutualCallBack;
import cn.com.startai.sharedlib.app.mutual.MutualManager;
import cn.com.swain.baselib.app.IApp.IService;
import cn.com.swain.baselib.jsInterface.AbsJsInterface;
import cn.com.swain.baselib.util.PermissionRequest;
import cn.com.swain.baselib.util.StatusBarUtil;
import cn.com.swain169.log.Tlog;

public class SharedMainActivity extends WebHomeActivity
        implements IMutualCallBack {

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Tlog.v("HomeActivity  onWindowFocusChanged() ");
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
                callJs(languageResponseMethod.toMethod());
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
        callJs(languageResponseMethod.toMethod());
    }

    private PermissionRequest mPermissionRequest;
    private LocaleChangeReceiver mLocaleChangeReceiver;
    private IService IService;
    private ServiceConnection serviceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        setTheme(android.R.style.Theme_Black_NoTitleBar_Fullscreen);

//        setLoadModelDistributed();

        setLoadModelTogether();

        super.onCreate(savedInstanceState);
        Tlog.v("WebHomeActivity  onCreate() ");

        mPermissionRequest = new PermissionRequest(this);
        mPermissionRequest.requestPermissions(new PermissionRequest.OnAllPermissionFinish() {
                                                  @Override
                                                  public void onAllPermissionRequestFinish() {
                                                      Tlog.v("WebHomeActivity onPermissionRequestFinish() ");

                                                      FileManager.getInstance().recreate(getApplication());
                                                      Debuger.getInstance().reCheckLogRecord(SharedMainActivity.this);
                                                  }
                                              }, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION);

        IntentFilter filter = new IntentFilter(Intent.ACTION_LOCALE_CHANGED);
        mLocaleChangeReceiver = new LocaleChangeReceiver();
        registerReceiver(mLocaleChangeReceiver, filter);

        Controller.getInstance().init(getApplication(), this);

        if (isTogetherLoadModel()) {
            IService = Controller.getInstance();
            IService.onSCreate();
        } else if (isDistributedLoadModel()) {
            Intent MutualService = new Intent(this, MutualService.class);
            serviceConnection = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {

                    Tlog.d(" onServiceConnected success :" + name);

                    MutualService.MBinder service1 = (MutualService.MBinder) service;
                    IService = service1.getIService();
                    IService.onSCreate();
                    SharedMainActivity.this.resLoadFinish();
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {
                    Tlog.d(" onServiceDisconnected success :" + name);
                }
            };
            bindService(MutualService, serviceConnection, Context.BIND_AUTO_CREATE);

        } else {
            IService = Controller.getInstance();
            IService.onSCreate();
        }

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
        WXApiHelper.getInstance().releaseWxApi();

        if (null != serviceConnection) {
            unbindService(serviceConnection);
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
    public void scanQR(int code) {
        ChargerScanActivity.showActivityForResult(this, code);
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

        Tlog.d("getJsInterfaces mutualManager=null? " + (mutualManager == null));

        if (mutualManager != null) {
            AbsJsInterface jsInterface = mutualManager.getJsInterface();
            if (jsInterface != null) {
                ArrayList<AbsJsInterface> mJsInterfaces = new ArrayList<>();
                mJsInterfaces.add(jsInterface);
                return mJsInterfaces;
            } else {
                Tlog.e(" jsManager.getJsInterface()==null ");
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
