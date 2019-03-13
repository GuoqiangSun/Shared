package cn.com.startai.sharedlib.app.js.method2Impl;

import org.json.JSONException;
import org.json.JSONObject;

import cn.com.startai.fssdk.db.entity.DownloadBean;
import cn.com.startai.sharedlib.app.js.Utils.JsMsgType;
import cn.com.swain.baselib.jsInterface.response.BaseResponseMethod2;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/28 0028
 * desc :
 */
public class UpdateProgressResponseMethod extends BaseResponseMethod2 {

    public static UpdateProgressResponseMethod getUpdateProgressResponseMethod() {
        return new UpdateProgressResponseMethod();
    }

    private UpdateProgressResponseMethod() {
        super(JsMsgType.TYPE_RESPONSE_APP_UPGRADE);
    }


    @Override
    public void releaseCache() {
        super.releaseCache();
        this.url = this.extName = this.localPath = this.fileName = this.fileId = this.protocol = null;
        this.status = this.progress = 0;
        this.addedSize = this.totalSize = this.updateTime = 0L;
    }

    public void setUpdateProgress(DownloadBean downloadBean) {
        this.addedSize = downloadBean.getAddedSize();
        this.extName = downloadBean.getExtName();
        this.fileId = downloadBean.getFileId();
        this.fileName = downloadBean.getFileName();
        this.localPath = downloadBean.getLocalPath();
        this.progress = downloadBean.getProgress();
        this.protocol = downloadBean.getProtocol();
        this.status = downloadBean.getStatus();
        this.totalSize = downloadBean.getTotalSize();
        this.updateTime = downloadBean.getUpdateTime();
        this.url = downloadBean.getUrl();
    }

    private String url;        // 下载链接
    private int status;                      // 下载状态 0   0暂停 1下载中 2下载成功 3下载等待 4下载错误
    private String extName;              // 后缀名
    private String localPath;  // 文件本地保存路径
    private long addedSize;                        // 已下载的文件大小
    private long totalSize;                        // 文件总大小
    private int progress;                         // 进度（整数）
    private long updateTime;                      // 更新时间
    private String fileName;                       // 文件名
    private String fileId;                           // 文件fileId
    private String protocol;                  // 下载类型


    @Override
    public String toMethod() {

        JSONObject contentObj = new JSONObject();
        if (getResult()) {

            try {
                contentObj.put("url", url);
                contentObj.put("status", status);
                contentObj.put("extName", extName);
                contentObj.put("localPath", localPath);
                contentObj.put("addedSize", addedSize);
                contentObj.put("totalSize", totalSize);
                contentObj.put("progress", progress);
                contentObj.put("updateTime", updateTime);
                contentObj.put("fileName", fileName);
                contentObj.put("fileId", fileId);
                contentObj.put("protocol", protocol);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return toMethod(contentObj);

    }
}
