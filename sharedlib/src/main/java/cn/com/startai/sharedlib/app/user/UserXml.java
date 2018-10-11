package cn.com.startai.sharedlib.app.user;

import android.content.Context;

import cn.com.swain.baselib.sp.BaseSpTool;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/28 0028
 * desc :
 */
public class UserXml extends BaseSpTool {

    private static final String NAME_USER = "loginUser";

    public UserXml(Context mCtx) {
        super(mCtx, NAME_USER);
    }

    private static UserXml mUser;

    public static UserXml getInstance(Context mContext) {
        checkInstance(mContext);
        return mUser;
    }

    private static void checkInstance(Context mContext) {
        if (mUser == null) {
            synchronized (NAME_USER) {
                if (mUser == null) {
                    mUser = new UserXml(mContext);
                }
            }
        }
    }

    private static final String KEY_LOGIN_TIME = "loginTime";

    public void setLastLogingTime(long timestamp) {
        putLong(KEY_LOGIN_TIME, timestamp);
    }

    public long getLastLoginTime() {
        return getLong(KEY_LOGIN_TIME, 0L);
    }

}
