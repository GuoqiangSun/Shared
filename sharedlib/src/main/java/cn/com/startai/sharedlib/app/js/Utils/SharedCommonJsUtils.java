package cn.com.startai.sharedlib.app.js.Utils;

import cn.com.swain.baselib.jsInterface.method.BaseCommonJsUtils;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/27 0027
 * desc :
 */
public class SharedCommonJsUtils extends BaseCommonJsUtils {


    /**
     * 捕获错误到后台请求
     */
    public static final String TYPE_REQUEST_JS_ERROR = "0x5011";

    /*******登录*****/

    /**
     * 检验是否登录请求
     */
    public static final String TYPE_REQUEST_IS_LOGIN = "0x5101";

    /**
     * 检验是否登录录返回
     */
    public static final String TYPE_RESPONSE_IS_LOGIN = "0x5102";


    /**
     * 微信登录请求
     */
    public static final String TYPE_REQUEST_WX_LOGIN = "0x5103";

    /**
     * 微信登录返回
     */
    public static final String TYPE_RESPONSE_WX_LOGIN = "0x5104";

    /**
     * 获取验证码请求
     */
    public static final String TYPE_REQUEST_GET_IDENTITY_CODE = "0x5105";
    /**
     * 获取验证码返回
     */
    public static final String TYPE_RESPONSE_GET_IDENTITY_CODE = "0x5106";

    /**
     * 手机号加验证码登录请求
     */
    public static final String TYPE_REQUEST_MOBILE_LOGIN_BY_IDCODE = "0x5107";

    /**
     * 手机号加验证码登录返回
     */
    public static final String TYPE_RESPONSE_MOBILE_LOGIN_BY_IDCODE = "0x5108";

    /**
     * 用户登出请求
     */
    public static final String TYPE_REQUEST_LOGIN_OUT = "0x5121";

    /**
     * 用户登出返回
     */
    public static final String TYPE_RESPONSE_LOGIN_OUT = "0x5122";


    /*****扫码充电*****/

    /**
     * 调用摄像头扫描二维码请求
     */
    public static final String TYPE_REQUEST_SCAN_OR = "0x5201";

    /**
     * 扫描二维码数据返回
     */
    public static final String TYPE_RESPONSE_SCAN_OR = "0x5202";

    /**
     * 查询设备是否在线请求
     */
    public static final String TYPE_REQUEST_QUERY_DEVICE_INFO = "0x5203";

    /**
     * 查询设备是否在线返回
     */
    public static final String TYPE_RESPONSE_QUERY_DEVICE_INFO = "0x5204";

    /**
     * 借出充电宝请求
     */
    public static final String TYPE_REQUEST_BORROW_DEVICE = "0x5205";

    /**
     * 借出充电宝返回
     */
    public static final String TYPE_RESPONSE_BORROW_DEVICE = "0x5206";

    /**
     * 归还充电宝请求
     */
    public static final String TYPE_REQUEST_GIVEBACK_DEVICE = "0x5211";

    /**
     * 归还充电宝返回
     */
    public static final String TYPE_RESPONSE_GIVEBACK_DEVICE = "0x5212";

    /*********国际化****/

    /**
     * 查询系统语言
     */
    public static final String TYPE_REQUEST_QUERY_SYSTEM_LANGUAGE = "0x5041";


    /**
     * 设置系统语言
     */
    public static final String TYPE_REQUEST_SET_SYSTEM_LANGUAGE = "0x5042";

    /**
     * 系统语言返回
     */
    public static final String TYPE_RESPONSE_SYSTEM_LANGUAGE = "0x5043";


    /********版本更新*******/

    /**
     * 检查是否有版本更新请求
     */
    public static final String TYPE_REQUEST_IS_NEWVERSION = "0x5021";

    /**
     * 检查是否有版本更新返回
     */
    public static final String TYPE_RESPONSE_IS_NEWVERSION = "0x5022";

    /**
     * 版本更新请求
     */
    public static final String TYPE_REQUEST_APP_UPGRADE = "0x5023";
    /**
     * 取消版本更新请求
     */
    public static final String TYPE_REQUEST_CANCEL_APP_UPGRADE = "0x5024";

    /**
     * 版本更新返回
     */
    public static final String TYPE_RESPONSE_APP_UPGRADE = "0x5025";

    /**
     * 版本号请求
     */
    public static final String TYPE_REQUEST_APP_VERSION = "0x5026";
    /**
     * 版本号返回
     */
    public static final String TYPE_RESPONSE_APP_VERSION = "0x5027";

    /********用户信息*******/

    /**
     * 查询用户信息请求
     */
    public static final String TYPE_REQUEST_USER_INFO = "0x5151";

    /**
     * 查询用户信息返回
     */
    public static final String TYPE_RESPONSE_USER_INFO = "0x5152";

    /**
     * 手机拍照请求
     */
    public static final String TYPE_REQUEST_TAKE_PHOTO = "0x5153";

    /**
     * 打开本地照片请求
     */
    public static final String TYPE_REQUEST_LOCAL_PHOTO = "0x5154";

    /**
     * 修改头像数据发送返回
     */
    public static final String TYPE_REQUEST_SEND_MODIFY_HEADPIC_DATA = "0x5155";
    /**
     * 修改用户名请求
     */
    public static final String TYPE_REQUEST_MODIFY_USERNAME = "0x5156";
    /**
     * 修改用户信息返回
     */
    public static final String TYPE_RESPONSE_MODIFY_USERINFO = "0x515F";
    /**
     * 修改密码请求
     */
    public static final String TYPE_REQUEST_MODIFY_USERPWD = "0x5160";
    /**
     * 修改密码返回
     */
    public static final String TYPE_RESPONSE_MODIFY_USERPWD = "0x5161";


}
