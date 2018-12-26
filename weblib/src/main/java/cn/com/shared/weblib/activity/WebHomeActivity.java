package cn.com.shared.weblib.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Queue;

import cn.com.shared.weblib.R;
import cn.com.shared.weblib.fragment.BaseFragment;
import cn.com.shared.weblib.fragment.GuideFragment;
import cn.com.shared.weblib.fragment.WebFragment;
import cn.com.shared.weblib.js.ICallJS;
import cn.com.swain.baselib.Queue.SyncLimitQueue;
import cn.com.swain.baselib.jsInterface.AbsJsInterface;
import cn.com.swain.baselib.jsInterface.method.PressBackResponseMethod;
import cn.com.swain169.log.Tlog;

/**
 * author: Guoqiang_Sun
 * date : 2018/3/28 0028
 * desc :
 */
public abstract class WebHomeActivity extends AppCompatActivity
        implements WebFragment.IWebFragmentCallBack, ICallJS {

    private UiHandler mUiHandler;


    // activity onPause
    private volatile boolean mActPause = false;
    // webFragment show
    private volatile boolean mWebShowed = false;
    // webView loaded
    private volatile boolean mWebLoaded = false;
    // webView interface load finish
    private volatile boolean mCanCallJs = false;

    private int mCurFrame = ID_GUIDE;

    private final ArrayList<BaseFragment> mFragments = new ArrayList<>(2);
    private static final int ID_GUIDE = 0x00;
    private static final int ID_WEB = 0x01;
    private long createTs;


    // 第一种加载模式,guide web 一起加载
    public static final int LOAD_MODEL_TOGETHER = 0x01;

    // 第二种加载模式 采用分布加载方式,先加载guide ,再加载web
    public static final int LOAD_MODEL_DISTRIBUTED = 0x02;

    private int loadModel = LOAD_MODEL_DISTRIBUTED;

    // 设置一起加载模式
    protected void setLoadModelTogether() {
        setLoadModel(LOAD_MODEL_TOGETHER);
    }

    protected boolean isTogetherLoadModel() {
        return loadModel == LOAD_MODEL_TOGETHER;
    }

    // 设置分布加载模式
    protected void setLoadModelDistributed() {
        setLoadModel(LOAD_MODEL_DISTRIBUTED);
    }

    protected boolean isDistributedLoadModel() {
        return loadModel == LOAD_MODEL_DISTRIBUTED;
    }

    protected void setLoadModel(int model) {
        this.loadModel = model;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weblib_activity_webhome);

        createTs = System.currentTimeMillis();
        Tlog.v("WebHomeActivity  onCreate() ");

        if (mUiHandler == null) {
            mUiHandler = new UiHandler(this);
        }

        if (isTogetherLoadModel()) {
            restoreFragment(savedInstanceState);

        } else if (isDistributedLoadModel()) {

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            addGuideFragment(fragmentTransaction, savedInstanceState);
            if (mCurFrame == ID_GUIDE) {
                fragmentTransaction.show(mFragments.get(ID_GUIDE));
            }
            fragmentTransaction.commit();


            if (savedInstanceState != null) {
                Fragment fragmentWeb = getFragmentByCache(savedInstanceState, String.valueOf(ID_WEB));
                if (fragmentWeb != null) {
                    Tlog.e(" savedInstanceState!=null, add cache web fragment ");

                    mFragments.add(ID_WEB, (BaseFragment) fragmentWeb);
                }
            }

        } else {
            restoreFragment(savedInstanceState);
        }

    }

    protected void resLoadFinish() {

        if (mFragments.size() > ID_WEB && mFragments.get(ID_WEB) != null) {
            Tlog.e(" resLoadFinish but web fragment not null ");
            return;
        }

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        addWebFragment(fragmentTransaction, null);
        if (mCurFrame == ID_GUIDE) {
            fragmentTransaction.hide(mFragments.get(ID_WEB));
        }
        fragmentTransaction.commit();
    }

    private void addGuideFragment(FragmentTransaction fragmentTransaction, Bundle savedInstanceState) {
        Fragment fragmentGuide = getFragmentByCache(savedInstanceState, String.valueOf(ID_GUIDE));
        if (fragmentGuide == null) {
            Tlog.w(" mFragments add new guideFragment");
            mFragments.add(ID_GUIDE, newGuideFragment());
            fragmentTransaction.add(R.id.frame_content, mFragments.get(ID_GUIDE), String.valueOf(ID_GUIDE));
        } else {
            Tlog.w(" mFragments add cache guideFragment");
            if (mFragments.size() <= ID_GUIDE) {
                mFragments.add(ID_GUIDE, (BaseFragment) fragmentGuide);
            }
        }
    }

    private void addWebFragment(FragmentTransaction fragmentTransaction, Bundle savedInstanceState) {

        Fragment fragmentWeb = getFragmentByCache(savedInstanceState, String.valueOf(ID_WEB));
        if (fragmentWeb == null) {
            Tlog.w(" mFragments add new webFragment");
            mCanCallJs = false;
            mFragments.add(ID_WEB, new WebFragment());
            fragmentTransaction.add(R.id.frame_content, mFragments.get(ID_WEB), String.valueOf(ID_WEB));
        } else {
            Tlog.w(" mFragments add cache webFragment");
            if (mFragments.size() <= ID_WEB) {
                mFragments.add(ID_WEB, (BaseFragment) fragmentWeb);
            }
        }

    }

    protected abstract GuideFragment newGuideFragment();


    private Fragment getFragmentByCache(Bundle savedInstanceState, String tag) {
        Fragment fragmentByTag = null;
        if (savedInstanceState != null) {
            fragmentByTag = getSupportFragmentManager().getFragment(savedInstanceState, tag);
        }
        if (fragmentByTag == null) {
            fragmentByTag = getSupportFragmentManager().findFragmentByTag(tag);
        }
        return fragmentByTag;
    }


    @Override
    protected void onResume() {
        super.onResume();
        Tlog.v("WebHomeActivity onResume() ");
        mActPause = false;
        if (!mWebShowed && mWebLoaded) {
            showWeb(600);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        Tlog.v("WebHomeActivity onPause() ");
        mActPause = true;
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Tlog.d("WebHomeActivity onRestoreInstanceState() ");
        mCurFrame = ID_WEB;
        restoreFragment(savedInstanceState);
    }

    private synchronized void restoreFragment(Bundle savedInstanceState) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        addGuideFragment(fragmentTransaction, savedInstanceState);
        addWebFragment(fragmentTransaction, savedInstanceState);

        if (mCurFrame == ID_GUIDE) {
            fragmentTransaction.show(mFragments.get(ID_GUIDE));
            fragmentTransaction.hide(mFragments.get(ID_WEB));
        } else if (mCurFrame == ID_WEB) {
            fragmentTransaction.hide(mFragments.get(ID_GUIDE));
            fragmentTransaction.show(mFragments.get(ID_WEB));
        }

        fragmentTransaction.commit();

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        Tlog.d("WebHomeActivity onSaveInstanceState() ");

        if (mFragments.size() > ID_GUIDE) {
            Tlog.d("WebHomeActivity onSaveInstanceState() putFragment:" + String.valueOf(ID_GUIDE));
            getSupportFragmentManager().putFragment(outState, String.valueOf(ID_GUIDE), mFragments.get(ID_GUIDE));
        }

        if (mFragments.size() > ID_WEB) {
            Tlog.d("WebHomeActivity onSaveInstanceState() putFragment:" + String.valueOf(ID_WEB));
            getSupportFragmentManager().putFragment(outState, String.valueOf(ID_WEB), mFragments.get(ID_WEB));
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Tlog.v("WebHomeActivity onDestroy() ");
        if (mUiHandler != null) {
            mUiHandler.removeCallbacksAndMessages(null);
            mUiHandler.releaseWr();
            mUiHandler = null;
        }
        mFragments.clear();
        mMethodCache.clear();
    }

    private static final String URL_H5_INDEX = "file:///android_asset/UI/index.html";

    @Override
    public String getLoadUrl() {
        return URL_H5_INDEX;
    }

    /*******************/

    protected void setWebViewDebug(boolean debug) {
        BaseFragment baseFragment = mFragments.get(ID_WEB);
        if (baseFragment != null) {
            WebFragment mWebFragment = (WebFragment) baseFragment;
            mWebFragment.setWebViewDebugInChrome(debug);
        } else {
            Tlog.e(" setWebViewDebug baseFragment=null");
        }
    }

    private final Queue<String> mMethodCache = new SyncLimitQueue<>(12);

    @Override
    public void callJs(String method) {

        if (mWebShowed && mCanCallJs) {
            if (mUiHandler != null) {
                mUiHandler.obtainMessage(MSG_WHAT_LOAD_JS, method).sendToTarget();
            }
        } else {
            Tlog.e(" ajLoadJs() web not showed; add to cache: " + method);
            mMethodCache.offer(method);
        }

    }

    private final PressBackResponseMethod mBackMethod = new PressBackResponseMethod();

    @Override
    public void onBackPressed() {
//            super.onBackPressed();
        Tlog.v("WebHomeActivity onBackPressed() ");

        if (status) {
            String method = mBackMethod.toMethod();
            callJs(method);
        }

    }

    private boolean status = true;

    public void disableGoBack(boolean status) {
        this.status = status;
        BaseFragment baseFragment = mFragments.get(ID_WEB);
        if (baseFragment != null) {
            WebFragment mWebFragment = (WebFragment) baseFragment;
            mWebFragment.disableGoBack(status);
        } else {
            Tlog.e(" ajDisableGoBack baseFragment=null");
        }
    }

    /*******************/

    private static final long MAX_DELAY_SHOW_WEB = 1000 * 3;//s

    @Override
    public void onWebLoadFinish() {
        mWebLoaded = true;
        long loadDuration = (System.currentTimeMillis() - createTs);
        long delay;
        if (loadDuration >= MAX_DELAY_SHOW_WEB) {
            delay = 0L;
        } else if (loadDuration <= 0) {
            delay = MAX_DELAY_SHOW_WEB;
        } else {
            delay = MAX_DELAY_SHOW_WEB - loadDuration;
        }

        Tlog.d(" onWebLoadFinish delay " + delay + " show");

        showWeb(delay);
    }

    private void showWeb(long delay) {
        if (!mActPause) {
            if (mUiHandler != null) {
                mUiHandler.sendEmptyMessageDelayed(MSG_WHAT_WEB_VIEW_LOAD_FINISH, delay);
            }
        } else {
            Tlog.e(" onWebLoadFinish activity is pause . ");
        }
    }

    protected void destroyMyself() {

        if (mUiHandler != null) {
            mUiHandler.sendEmptyMessageDelayed(MSG_WHAT_FINISH, 500);
        } else {
            finish();
        }

    }

    /*******************/


    private static final int MSG_WHAT_LOAD_JS = 0x01;

    private static final int MSG_WHAT_WEB_VIEW_LOAD_FINISH = 0x02;

    private static final int MSG_WHAT_FINISH = 0x03;

    private static final int MSG_WHAT_LOADJS_CACHE_METHOD = 0x04;

    private void handleMessage(Message msg) {

        if (msg.what == MSG_WHAT_LOAD_JS) {

            BaseFragment baseFragment = mFragments.get(ID_WEB);
            if (baseFragment != null) {
                WebFragment mWebFragment = (WebFragment) baseFragment;
                String method = (String) msg.obj;
                mWebFragment.loadJs(method);
            } else {
                Tlog.e(" loadJs baseFragment=null");
            }

        } else if (msg.what == MSG_WHAT_WEB_VIEW_LOAD_FINISH) {

            if (!mActPause && !mWebShowed && mFragments.size() > ID_WEB) {
                mWebShowed = true;
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.hide(mFragments.get(ID_GUIDE));
                fragmentTransaction.show(mFragments.get(ID_WEB));
                fragmentTransaction.commit();
                mCurFrame = ID_WEB;
                Tlog.e("show mWebFragment ");

                if (mUiHandler != null) {
                    mUiHandler.sendEmptyMessage(MSG_WHAT_LOADJS_CACHE_METHOD);
                }

            } else {
                Tlog.e("WebView loaded ; pause:" + mActPause + " show:" + mWebShowed + " " + mFragments.size());
            }

        } else if (msg.what == MSG_WHAT_LOADJS_CACHE_METHOD) {
            mCanCallJs = true;
            new LoadCacheJsTask(WebHomeActivity.this).execute();

        } else if (msg.what == MSG_WHAT_FINISH) {

            Tlog.e("handleMessage finish ");

            this.finish();

        }

    }


    private static class LoadCacheJsTask extends AsyncTask<Void, Void, Void> {

        private WeakReference<WebHomeActivity> wr;

        private LoadCacheJsTask(WebHomeActivity mHomeActivity) {
            wr = new WeakReference<>(mHomeActivity);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            WebHomeActivity homeActivity = wr.get();
            if (homeActivity == null) {
                Tlog.e(AbsJsInterface.TAG, " LoadCacheJsTask homeActivity=null");
                return null;
            }

            Queue<String> mMethodCache = homeActivity.mMethodCache;

            if (mMethodCache.size() > 0) {
                String method = null;
                while ((method = mMethodCache.poll()) != null) {
                    Tlog.e(AbsJsInterface.TAG, " poll cache method: " + method);
                    homeActivity.callJs(method);
                }
            }
            return null;
        }
    }


    private static class UiHandler extends Handler {

        private WeakReference<WebHomeActivity> wr;

        void releaseWr() {
            if (wr != null) {
                wr.clear();
            }
            wr = null;
        }

        UiHandler(WebHomeActivity act) {
            super(Looper.getMainLooper());

            wr = new WeakReference<>(act);

        }


        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            WebHomeActivity act;

            if (wr != null && (act = wr.get()) != null) {
                act.handleMessage(msg);
            } else {
                Tlog.e("<UiHandler> WebHomeActivity == null ");
            }

        }
    }

}
