package cn.com.startai.sharedlib.app.mutual;

/**
 * author: Guoqiang_Sun
 * date: 2019/2/15 0015
 * Desc:
 */
public interface IUserIDManager {

    /**
     * 从mqtt获取userid
     */
    String getUserIDFromMq();

    /**
     * 从缓存获取userid
     */
    String getUserID();

    /**
     * 缓存userid
     */
    void setUserID(String userID);

}
