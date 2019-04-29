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


    ///////////微信

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
     * wx登录没有在微信平台注册（内部错误）
     */
    public static final String ERROR_CODE_WX_LOGIN_NO_REGISTER = "0x830395";


    /**
     * wx绑定错误 unknown
     */
    public static final String ERROR_CODE_WX_BIND_UNKNOWN = "0x830394";

    /**
     * wx绑定没有客户端
     */
    public static final String ERROR_CODE_WX_BIND_NO_CLIENT = "0x830393";

    /**
     * wx绑定用户取消
     */
    public static final String ERROR_CODE_WX_BIND_USER_CANCEL = "0x830392";

    /**
     * wx绑定用户拒接
     */
    public static final String ERROR_CODE_WX_BIND_USER_REJECTION = "0x830391";

    /**
     * wx绑定没有在微信平台注册（内部错误）
     */
    public static final String ERROR_CODE_WX_BIND_NO_REGISTER = "0x830390";

    /////头像

    /**
     * 更新头像失败，用户取消
     */
    public static final String UPDATE_HEAD_PIC_CANCEL = "0x8025A0";
    /**
     * 更新头像失败，内部错误
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
    /**
     * 更新头像失败,压缩出错
     */
    public static final String UPDATE_HEAD_PIC_COMPRESS_ERROR = "0x802596";


    /////支付

    /**
     * 支付失败
     */
    public static final String THIRD_PAR_FAIL = "0x524301";

    /**
     * 支付失败,内部错误
     */
    public static final String THIRD_PAR_INTER_ERROR = "0x524302";

    /**
     * 支付失败,用户取消
     */
    public static final String THIRD_PAR_USER_CANCLE = "0x524303";

    /**
     * 支付失败,商户未认证审核
     */
    public static final String THIRD_PAR_NO_AUTHORIZED = "0x524304";

    /**
     * 第三方登录用户取消
     */
    public static final String THIRD_LOGIN_USER_CANCEL = "0x510B01";

    /**
     * 第三方登录内部错误
     */
    public static final String THIRD_LOGIN_INTER_ERROR = "0x510B02";

    /**
     * 第三方绑定用户取消
     */
    public static final String THIRD_BIND_USER_CANCEL = "0x510C01";

    /**
     * 第三方绑定内部错误
     */
    public static final String THIRD_BIND_INTER_ERROR = "0x510C02";


    /**
     * 未安装相应地图
     */
    public static final String MAP_NAV_ERROR_NO_CLIENT = "0x530201";

    /**
     * 失败
     */
    public static final String MAP_NAV_ERROR = "0x530201";


}
