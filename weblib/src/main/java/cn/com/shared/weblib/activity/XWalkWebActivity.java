package cn.com.shared.weblib.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import org.xwalk.core.XWalkActivityDelegate;
import org.xwalk.core.XWalkDialogManager;

import cn.com.swain.baselib.log.Tlog;

/**
 * author: Guoqiang_Sun
 * date: 2019/2/22 0022
 * Desc:
 */
public class XWalkWebActivity extends AppCompatActivity {

    public static final String TAG = "XWalkWebActivity";

    private XWalkActivityDelegate mActivityDelegate;

    public XWalkWebActivity() {
    }

    protected void onXWalkReady(Bundle savedInstanceState) {
        Tlog.v(TAG, " XWalkWebActivity onXWalkReady ");
    }

    protected void onXWalkFailed() {
        Tlog.v(TAG, " XWalkWebActivity onXWalkFailed ");
        this.finish();
    }

    protected XWalkDialogManager getDialogManager() {
        return this.mActivityDelegate.getDialogManager();
    }

    public boolean isXWalkReady() {
        boolean xWalkReady = this.mActivityDelegate.isXWalkReady();
        Tlog.v(TAG, " XWalkWebActivity isXWalkReady " + xWalkReady);
        return xWalkReady;
    }

    public boolean isSharedMode() {
        boolean sharedMode = this.mActivityDelegate.isSharedMode();
        Tlog.v(TAG, " XWalkWebActivity isSharedMode " + sharedMode);
        return sharedMode;
    }

    public boolean isDownloadMode() {
        boolean downloadMode = this.mActivityDelegate.isDownloadMode();
        Tlog.v(TAG, " XWalkWebActivity isDownloadMode " + downloadMode);
        return downloadMode;
    }

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Runnable cancelCommand = new Runnable() {
            public void run() {
                XWalkWebActivity.this.onXWalkFailed();
            }
        };
        Runnable completeCommand = new Runnable() {
            public void run() {
                XWalkWebActivity.this.onXWalkReady(savedInstanceState);
            }
        };
        this.mActivityDelegate = new XWalkActivityDelegate(this, cancelCommand, completeCommand);
    }

    protected void onResume() {
        super.onResume();
        this.mActivityDelegate.onResume();
    }
}
