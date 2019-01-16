package cn.com.startai.sharedlib.app.js.method2Impl;

import org.json.JSONException;
import org.json.JSONObject;

import cn.com.startai.fssdk.db.entity.UploadBean;
import cn.com.startai.sharedlib.app.js.Utils.JsMsgType;
import cn.com.swain.baselib.jsInterface.response.BaseResponseMethod2;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/28 0028
 * desc :
 */
public class ModifyHeadpicResponseMethod extends BaseResponseMethod2 {

    public static ModifyHeadpicResponseMethod getModifyHeadpicResponseMethod() {
        return new ModifyHeadpicResponseMethod();
    }

    public ModifyHeadpicResponseMethod() {
        super(JsMsgType.TYPE_RESPONSE_MODIFY_HEADPIC_DATA);
    }

    @Override
    public void releaseCache() {
        super.releaseCache();
        send = false;
        uploadBean = null;
    }

    private boolean send;

    public void setIsSend(boolean send) {
        this.send = send;
    }


    private UploadBean uploadBean;

    public void setUploadBean(UploadBean uploadBean) {
        this.uploadBean = uploadBean;
    }

    @Override
    public String toMethod() {

        JSONObject contentObj = new JSONObject();
        if (getResult()) {
            try {
                contentObj.put("sent", send);

                if (uploadBean != null) {
                    contentObj.put("url", uploadBean.getHttpDownloadUrl());
                    contentObj.put("status", uploadBean.getStatus());
                    contentObj.put("extName", uploadBean.getExtName());
                    contentObj.put("localPath", uploadBean.getLocalPath());
                    contentObj.put("addedSize", uploadBean.getAddedSize());
                    contentObj.put("totalSize", uploadBean.getTotalSize());
                    contentObj.put("progress", uploadBean.getProgress());
                    contentObj.put("updateTime", uploadBean.getUpdateTime());
                    contentObj.put("fileName", uploadBean.getFileName());
                    contentObj.put("fileId", uploadBean.getFileId());
                    contentObj.put("protocol", uploadBean.getProtocol());
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return toMethod(contentObj);

    }
}
