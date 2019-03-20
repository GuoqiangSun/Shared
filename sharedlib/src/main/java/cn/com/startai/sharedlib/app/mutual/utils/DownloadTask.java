package cn.com.startai.sharedlib.app.mutual.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.com.startai.sharedlib.app.global.FileManager;
import cn.com.startai.sharedlib.app.mutual.MutualManager;
import cn.com.swain.baselib.file.FileUtil;
import cn.com.swain.baselib.log.Tlog;

/**
 * author: Guoqiang_Sun
 * date : 2018/9/6 0006
 * desc :
 */
public class DownloadTask extends AsyncTask<Void, Void, Void> {

    // 下载的缓存梗根路径
    public static File getSaveDownRootPath() {
        return FileManager.getInstance().getCachePath();
    }

    // 名称
    private static String getDownName(String url) {
        if (url == null) {
            return UUID.randomUUID().toString();
        }
        int i = url.lastIndexOf("/");
        if (i < 0) {
            return UUID.randomUUID().toString();
        }
        String substring = url.substring(i + 1);

        int i1 = substring.indexOf(".");
        if (i1 < 0) {
            substring = releaseName(substring); // 过滤特殊字符
            substring += ".jpg";
        } else {
            String name = substring.substring(0, i1);// 过滤特殊字符，不过滤后缀
            String suffix = substring.substring(i1);
            substring = releaseName(name) + suffix;
        }

        return substring;
    }

    // 保存路径
    public static File getCacheDownPath(String url) {
        return new File(getSaveDownRootPath(), getDownName(url));
    }

    private static String releaseName(String str) {
        String regEx = "[`~!@#$%^&*()\\-+={}':;,\\[\\].<>/?￥%…（）_+|【】‘；：”“’。，、？\\s]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("");
    }

    private String TAG = MutualManager.TAG;

    private String url;
    private File outputFile;

    public DownloadTask(String url) {
        this.url = url;
        this.outputFile = getCacheDownPath(url);
    }

    public DownloadTask(String url, File outputFile) {
        this.url = url;
        this.outputFile = outputFile;
    }

    public DownloadTask(String url, String outputPath) {
        this.url = url;
        this.outputFile = new File(outputPath);
    }

    @Override
    protected Void doInBackground(Void... voids) {

        Tlog.w(TAG, " start down headPic " + String.valueOf(url));

//        formBitmap();

        boolean hasSuffix = true;

        int i = url.lastIndexOf("/");
        if (i > 0) {
            String substring = url.substring(i + 1);

            int i1 = substring.indexOf(".");
            if (i1 < 0) {
                hasSuffix = false;
            }
        }
        if (hasSuffix) {
            fromHttp();
        } else {
            formBitmap(); // wx用 fromHttp有问题。
        }

        return null;
    }

    private void formBitmap() {

        try {

            URL downURL = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection) downURL.openConnection();

            int responseCode = urlConnection.getResponseCode();

            Tlog.v(TAG, "DownloadTask formBitmap responseCode " + responseCode);

            if (responseCode == 200) {

                long contentLength = urlConnection.getContentLength();
                Tlog.v(TAG, "DownloadTask formBitmap contentLength " + contentLength);

                InputStream inputStream = urlConnection.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(inputStream);

                Bitmap bitmap = BitmapFactory.decodeStream(bis);

                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outputFile));

                Tlog.v(TAG, "DownloadTask formBitmap start compress length:" + outputFile.length());

//                bmp,jpg,png,tif,gif,pcx,tga,exif,fpx,svg,psd,cdr,pcd,dxf,ufo,eps,ai,raw,WMF,webp等。

                if (url.endsWith(".jpg") || url.endsWith(".jpeg")) {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                } else if (url.endsWith(".png")) {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                } else if (url.endsWith(".webp")) {
                    bitmap.compress(Bitmap.CompressFormat.WEBP, 100, bos);
                } else {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                }

                Tlog.v(TAG, "DownloadTask formBitmap compress finish  length:" + outputFile.length());

                bis.close();

                bos.flush();
                bos.close();

            }

        } catch (Exception e) {
            e.printStackTrace();
            Tlog.e(TAG, " DownloadTask IOException ", e);
        }


    }


    private void fromHttp() {

        File cacheFile = null;
        long allLength = 0;

        try {

            URL downURL = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection) downURL.openConnection();

            int responseCode = urlConnection.getResponseCode();

            Tlog.v(TAG, "DownloadTask fromHttp responseCode " + responseCode);

            if (responseCode == 200) {

                long contentLength = urlConnection.getContentLength();
                Tlog.v(TAG, "DownloadTask fromHttp contentLength " + contentLength);

                InputStream inputStream = urlConnection.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(inputStream);

                String randomName = UUID.randomUUID().toString();
                cacheFile = new File(getSaveDownRootPath(), randomName);

                FileOutputStream fos = new FileOutputStream(cacheFile);
                BufferedOutputStream bos = new BufferedOutputStream(fos);

                int read;
                byte[] buffer = new byte[1024 * 8];

                int progress;
                int lastProgress = 0;
                while ((read = bis.read(buffer)) != -1) {
                    allLength += read;
                    bos.write(buffer, 0, read);

//                    Tlog.v(TAG, "DownloadTask down " + allLength);

                    progress = (int) (allLength * 100 / contentLength);
                    if (progress > lastProgress) {
                        lastProgress = progress;
                        Tlog.v(TAG, "DownloadTask fromHttp progress " + progress);
                    }

                }

                bos.flush();

                FileUtil.syncFile(fos.getFD());
                FileUtil.copyFileUsingFileChannels(cacheFile, outputFile);

                Tlog.v(TAG, "DownloadTask success syncFile ; allLength:" + allLength
                        + "  outputFile.length():" + outputFile.length());

                bis.close();

                bos.close();

            }

        } catch (java.io.IOException e) {
            e.printStackTrace();
            Tlog.e(TAG, " DownloadTask IOException ", e);
        } finally {

            if (cacheFile != null && cacheFile.exists()) {
                boolean delete1 = cacheFile.delete();// delete cache
                Tlog.v(TAG, "DownloadTask finish ;  delete cache:" + delete1);
            }

            if (outputFile != null && outputFile.exists() && outputFile.length() < allLength) {
                boolean delete = outputFile.delete();
                Tlog.v(TAG, "DownloadTask finish ; but outputFile.length()< allLength: delete :" + delete);
            }

        }

    }

}
