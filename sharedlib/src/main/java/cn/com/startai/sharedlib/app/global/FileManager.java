package cn.com.startai.sharedlib.app.global;

import android.Manifest;
import android.app.Application;

import com.blankj.utilcode.util.PermissionUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cn.com.swain.baselib.file.FileTemplate;
import cn.com.swain.baselib.file.FileUtil;
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
        FileUtil.notifySystemToScan(app, getProjectPath());
    }


    public void recreate(Application app) {
        super.init(app);
        Tlog.i(" FileManager recreate finish ; success:" + exit);
        FileUtil.notifySystemToScan(app, getProjectPath());
    }


    @Override
    protected File initMyProjectPath() {
        if (CustomManager.getInstance().isSharedCharger()) {
            return new File(getAppRootPath(), "SharedCharger");
        } else if (CustomManager.getInstance().isSynerMax()) {
            return new File(getAppRootPath(), "Synermax");
        } else if (CustomManager.getInstance().isPikaPower()) {
            return new File(getAppRootPath(), "Pika");
        }
        return new File(getAppRootPath(), "SharedCharger");
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
        return getPhotoFileNoCheckPermission();
    }

    /**
     * 文件保存路径
     */
    public static File getPhotoFileNoCheckPermission() {

        File cachePath = FileManager.getInstance().getCachePath();
        boolean mkdirs = FileManager.getInstance().mkdirs(cachePath);
        if (!mkdirs) {
            return null;
        }
        SimpleDateFormat timeStampFormat = new SimpleDateFormat(
                "yyyy_MM_dd_HH_mm_ss", Locale.getDefault());
        String filename = timeStampFormat.format(new Date());
        return new File(cachePath, filename + ".jpg");
    }

}
