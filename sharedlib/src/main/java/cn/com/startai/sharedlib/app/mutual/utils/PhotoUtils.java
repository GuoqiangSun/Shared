package cn.com.startai.sharedlib.app.mutual.utils;

import android.Manifest;
import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import com.blankj.utilcode.util.PermissionUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cn.com.startai.sharedlib.app.FileManager;
import cn.com.startai.sharedlib.app.mutual.MutualManager;
import cn.com.swain169.log.Tlog;

/**
 * author: Guoqiang_Sun
 * date : 2018/10/12 0012
 * desc :
 */
public class PhotoUtils {

    private static String TAG = MutualManager.TAG;

    public static Uri getTakePhotoURI(Application app, File savePhotoFile) {
        Uri imageUri;
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            // 从文件中创建uri
            imageUri = Uri.fromFile(savePhotoFile);
        } else {
            String authority = app.getPackageName() + ".photo.provider";
            imageUri = FileProvider.getUriForFile(app, authority, savePhotoFile);
        }
        return imageUri;
    }

    /**
     * 请求照相
     */
    public static Intent requestTakePhoto(Uri imageUri) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        return intent;
    }

    /**
     * 请求本地图片
     */
    public static Intent requestLocalPhoto() {
        Intent intent;
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
        } else {
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
        }
        intent.setType("image/*");
        return intent;
    }

    /**
     * 裁剪
     */
    public static Intent cropImg(Uri inputUri, Uri outUri) {

        Intent intent = new Intent("com.android.camera.action.CROP");

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }

        Tlog.d(TAG, " crop. output:" + (outUri != null ? outUri.getPath() : " null ")
                + " input:" + (inputUri != null ? inputUri.toString() : "null"));

        intent.setDataAndType(inputUri, "image/*");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outUri);

        intent.putExtra("crop", "true");

//        intent.putExtra("aspectX", aspectX);
//        intent.putExtra("aspectY", aspectX);
//        intent.putExtra("outputX", outputX);
//        intent.putExtra("outputY", outputY);

        intent.putExtra("return-data", false);
        //黑边
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);

        return intent;
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


    /**
     * 压缩
     */
    public static void compressImage(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(srcPath, newOpts);//此时返回bm为空
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        newOpts.inJustDecodeBounds = false;
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int options = 100;
        bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        while (baos.toByteArray().length > 100 * 1024) { //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
            if (options < 0) {
                break;
            }
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(srcPath);
            //不断把stream的数据写文件输出流中去
            fileOutputStream.write(baos.toByteArray());
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
