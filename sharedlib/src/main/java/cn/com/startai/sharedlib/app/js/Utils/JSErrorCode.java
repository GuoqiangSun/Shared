package cn.com.startai.sharedlib.app.js.Utils;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/29 0029
 * desc :
 */
public class JSErrorCode {

    /**
     * 配网失败,链接WiFi 5G频段
     */
    public static final String ERROR_CODE_CONFIG_WIFI5G = "0xAA0200";


    /**
     * 二维码错误 未知
     */
    public static final String ERROR_CODE_SCAN_UNKNOWN = "0xAA0100";
    /**
     * 用户取消扫描
     */
    public static final String ERROR_CODE_SCAN_CANCEL = "0xAA0101";
    /**
     * 二维码格式不对
     */
    public static final String ERROR_CODE_ERROR_URL = "0xAA0102";


    /**
     * wx登录错误 unknown
     */
    public static final String ERROR_CODE_WX_LOGIN_UNKNOWN = "0x830399";

    /**
     * wx登录没有客户端
     */
    public static final String ERROR_CODE_WX_LOGIN_NO_CLIENT = "0x830398";

    /**
     * wx登录用户取消
     */
    public static final String ERROR_CODE_WX_LOGIN_USER_CANCEL = "0x830397";

    /**
     * wx登录用户拒接
     */
    public static final String ERROR_CODE_WX_LOGIN_USER_REJECTION = "0x830396";

    /**
     * 更新头像取消
     */
    public static final String UPDATE_HEAD_PIC_CANCEL = "0x8025A0";
    /**
     * 更新头像失败
     */
    public static final String UPDATE_HEAD_PIC_ERROR = "0x802599";
    /**
     * 更新头像失败,没有文件存储权限
     */
    public static final String UPDATE_HEAD_PIC_ERROR_NO_LOCAL_PERMISSION = "0x802598";
    /**
     * 更新头像失败,没有相机权限
     */
    public static final String UPDATE_HEAD_PIC_ERROR_NO_CAMERA_PERMISSION = "0x802597";

}
