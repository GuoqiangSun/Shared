package cn.com.startai.sharedlib.app;

import android.Manifest;
import android.app.Application;
import android.content.Intent;
import android.net.Uri;

import com.blankj.utilcode.util.PermissionUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cn.com.swain.baselib.file.FileTemplate;
import cn.com.swain.baselib.log.Tlog;

/**
 * author: Guoqiang_Sun
 * date : 2018/5/15 0015
 * desc :
 */
public class FileManager extends FileTemplate {

    private FileManager() {
    }

    private static final class ClassHolder {
        private static final FileManager FM = new FileManager();
    }

    public static FileManager getInstance() {
        return ClassHolder.FM;
    }

    @Override
    public void init(Application app) {
        super.init(app);
        Tlog.i(" FileManager init finish ; success:" + exit);
        String absolutePath = getProjectPath().getAbsolutePath();

        notifySystemToScan(app, absolutePath);
    }

    public static void notifySystemToScan(Application app, String filePath) {
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File file = new File(filePath);

        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        app.sendBroadcast(intent);
    }

    public void recreate(Application app) {
        super.init(app);
        Tlog.i(" FileManager recreate finish ; success:" + exit);
    }

    private static final String MY_PROJECT_PATH_NAME = "Ruioo";

    /**
     * 获取app缓存数据的目录
     *
     * @return
     */
    protected File initMyProjectPath() {
        return new File(getAppRootPath(), MY_PROJECT_PATH_NAME);
    }


    @Override
    protected String initMyAppRootPath() {
        return "startai";
    }

    /**
     * 文件保存路径
     */
    public static File getPhotoFile() {
        // 判断存储卡是否可以用，可用进行存储

        if (!PermissionUtils.isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            return null;
        }

        SimpleDateFormat timeStampFormat = new SimpleDateFormat(
                "yyyy_MM_dd_HH_mm_ss", Locale.getDefault());
        String filename = timeStampFormat.format(new Date());
        File cachePath = FileManager.getInstance().getCachePath();
        boolean mkdirs = FileManager.getInstance().mkdirs(cachePath);
        if (!mkdirs) {
            return null;
        }
        return new File(cachePath, filename + ".jpg");
    }



}
