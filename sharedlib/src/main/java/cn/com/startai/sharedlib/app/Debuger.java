package cn.com.startai.sharedlib.app;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.pm.PackageManager;
import android.os.StrictMode;
import android.support.v4.content.ContextCompat;

import java.io.File;

import cn.com.startai.sharedlib.BuildConfig;
import cn.com.swain.baselib.app.IApp.IApp;
import cn.com.swain.baselib.app.IApp.IService;
import cn.com.swain.baselib.log.Tlog;
import cn.com.swain.baselib.log.logRecord.impl.LogRecordManager;
import cn.com.swain.baselib.util.AppUtils;

/**
 * author: Guoqiang_Sun
 * date : 2018/5/4 0004
 * desc :
 */
public class Debuger implements IApp, IService {

    private Debuger() {
    }

    @Override
    public void onSCreate() {
        Tlog.startRecord();
    }

    @Override
    public void onSResume() {

    }

    @Override
    public void onSPause() {

    }

    @Override
    public void onSDestroy() {
        Tlog.stopRecord();
    }

    @Override
    public void onSFinish() {
        Tlog.syncRecordData();
    }

    private static final class ClassHolder {
        private static final Debuger DEBUGER = new Debuger();
    }

    public static Debuger getInstance() {
        return ClassHolder.DEBUGER;
    }

    /**
     * 全局的debug
     */
    public static final boolean isDebug = BuildConfig.DEBUG;

    /**
     * 是否toast
     */
    public static boolean isToastDebug = isDebug;

    /**
     * 是否打印log
     */
    public static boolean isLogDebug = isDebug;

    /**
     * 是否录制log
     */
    public static boolean isRecordLogDebug = false;

    /**
     * 是否打印栈log
     */
    public static boolean isPrintStackLogDebug = false;

    /**
     * 是否从本地加载H5页面
     */
    public static boolean isH5Debug = false;


    /**
     * 权限申请后再判断录制文件是否创建
     *
     * @param activity
     */
    public void reCheckLogRecord(Activity activity) {
        if (!Tlog.hasILogRecordImpl() && isRecordLogDebug) {

            boolean has = (ContextCompat.checkSelfPermission(activity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);

            Tlog.v(" reCheckLogRecord checkSelfPermission:" + has);

            if (has) {
                recordLog();
            }
        }
    }

    @Override
    public void init(Application app) {

        File appRootPath = FileManager.getInstance().getDebugPath();

        if (appRootPath != null && appRootPath.exists()) {
            File logFile = new File(appRootPath, "log.debug");
            if (logFile.exists()) {
                isLogDebug = true;
            }

            File recordLogFile = new File(appRootPath, "recordLog.debug");
            if (recordLogFile.exists()) {
                isRecordLogDebug = true;
            }

            File stackLogFile = new File(appRootPath, "stackLog.debug");
            if (stackLogFile.exists()) {
                isPrintStackLogDebug = true;
            }

            File toastFile = new File(appRootPath, "toast.debug");
            if (toastFile.exists()) {
                isToastDebug = true;
            }

            File h5File = new File(appRootPath, "h5.debug");
            if (h5File.exists()) {
                isH5Debug = true;
                mLocalH5 = getH5();
            }

            File strictFile = new File(appRootPath, "strict.debug");
            if (strictFile.exists()) {
                enableStrict();
            }
        }

        Tlog.setDebug(isLogDebug);
        Tlog.setLogRecordDebug(isRecordLogDebug);
        Tlog.setPrintStackDebug(isPrintStackLogDebug);

        if (isRecordLogDebug) {
            recordLog();
        }

        if (isLogDebug) {
            Tlog.i(AppUtils.generalSsl(app));
        }

        Tlog.i(" Debuger init success...");
    }

    private synchronized void recordLog() {

        final File logPath = FileManager.getInstance().getLogPath();

        if (logPath.exists()) {
            final String prefix = "00";
            if (!Tlog.hasILogRecordImpl()) {
                LogRecordManager mLogRecord = new LogRecordManager(logPath, prefix, 1024 * 1024 * 6);
                Tlog.v("TFlog init LogRecordManager");
                Tlog.set(mLogRecord);
            }
        } else {
            Tlog.e(" recordLog logPath not exit");
        }
    }

    /**
     * 激活严格模式
     */
    private void enableStrict() {
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectAll()
                .penaltyLog()
                .penaltyDeath()
//                .setClassInstanceLimit()
                .build());

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads() //监控磁盘读
                .detectDiskWrites() //监控磁盘写
                .detectNetwork()//监控网络访问
                .detectAll()//检测当前线程所有函数
                .penaltyLog()//将警告输出到LogCa
                .penaltyDeath() //一旦StrictMode消息被写到LogCat后应用就会崩溃
//                .penaltyDialog()
                .build());
    }


    private File getH5() {
        File resourcePath = FileManager.getInstance().getResourcePath();

        File h5Path = new File(resourcePath, "socketDist");

        if (h5Path.exists() && h5Path.isDirectory()) {

            File index = new File(h5Path, "index.html");

            if (index.exists() && index.length() > 0) {

                return index;

            }

        }
        return null;
    }

    private File mLocalH5;

    public File getLocalH5Resource() {
        if (isH5Debug) {
            return mLocalH5;
        }
        return null;
    }

}
