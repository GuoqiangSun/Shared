package cn.com.startai.sharedlib.app.js.Utils;

import cn.com.swain.baselib.jsInterface.response.BaseCommonJsUtils;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/27 0027
 * desc :
 */
public class JsMsgType extends BaseCommonJsUtils {


    /**
     * 捕获错误到后台请求
     */
    public static final String TYPE_REQUEST_JS_ERROR = "0x5011";


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

    /**
     * MQTT状态返回
     */
    public static final String TYPE_RESPONSE_MQTT = "0x5032";

    /**
     * 网络状态返回
     */
    public static final String TYPE_RESPONSE_NETWORK = "0x5033";


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
     * 阿里登录请求
     */
    public static final String TYPE_REQUEST_ALI_LOGIN = "0x5109";

    /**
     * 阿里登录返回
     */
    public static final String TYPE_RESPONSE_ALI_LOGIN = "0x510A";

    /**
     * 第三方登录请求
     */
    public static final String TYPE_REQUEST_THIRD_LOGIN = "0x510B";

    /**
     * 第三方登录返回
     */
    public static final String TYPE_RESPONSE_THIRD_LOGIN = "0x510C";

    public static final String THIRD_LOGIN_TYPE_GOOGLE = "Google";
    public static final String THIRD_LOGIN_TYPE_TWITTER = "Twitter";
    public static final String THIRD_LOGIN_TYPE_FACEBOOK = "Facebook";

    /**
     * 第三方绑定请求
     */
    public static final String TYPE_REQUEST_THIRD_BIND = "0x510D";

    /**
     * 第三方绑定返回
     */
    public static final String TYPE_RESPONSE_THIRD_BIND = "0x510E";


    /**
     * 用户登出请求
     */
    public static final String TYPE_REQUEST_LOGIN_OUT = "0x5121";

    /**
     * 用户登出返回
     */
    public static final String TYPE_RESPONSE_LOGIN_OUT = "0x5122";


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
    public static final String TYPE_RESPONSE_MODIFY_HEADPIC_DATA = "0x5155";
    /**
     * 修改用户名请求
     */
    public static final String TYPE_REQUEST_MODIFY_USERNAME = "0x5156";

    /**
     * 修改昵称请求
     */
    public static final String TYPE_REQUEST_MODIFY_NICKNAME = "0x5157";

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


    /**
     * 绑定手机号请求
     */
    public static final String TYPE_REQUEST_BIND_PHONE = "0x5162";

    /**
     * 绑定手机号返回
     */
    public static final String TYPE_RESPONSE_BIND_PHONE = "0x5163";

    /**
     * wx bind请求
     */
    public static final String TYPE_REQUEST_WX_BIND = "0x5164";

    /**
     * wx bind返回
     */
    public static final String TYPE_RESPONSE_WX_BIND = "0x5165";

    /**
     * 阿里bind请求
     */
    public static final String TYPE_REQUEST_ALI_BIND = "0x5166";

    /**
     * 阿里bind返回
     */
    public static final String TYPE_RESPONSE_ALI_BIND = "0x5167";


    /**
     * 微信解绑请求
     */
    public static final String TYPE_REQUEST_UNBIND_WX = "0x516A";

    /**
     * 微信解绑返回
     */
    public static final String TYPE_RESPONSE_UNBIND_WX = "0x516B";

    /**
     * 支付宝解绑请求
     */
    public static final String TYPE_REQUEST_UNBIND_ALI = "0x516C";

    /**
     * 支付宝解绑返回
     */
    public static final String TYPE_RESPONSE_UNBIND_ALI = "0x516D";

    /**
     * 第三方解绑请求
     */
    public static final String TYPE_REQUEST_THIRD_UNBIND = "0x5110";

    /**
     * 第三方解绑返回
     */
    public static final String TYPE_RESPONSE_THIRD_UNBIND = "0x5111";

    /**
     * 调用手机拨号功能
     */
    public static final String TYPE_REQUEST_CALL_PHONE = "0x5170";

    /**
     * 调用手机拨号返回
     */
    public static final String TYPE_RESPONSE_CALL_PHONE = "0x5171";

    /**
     * 调用手机拨号功能
     */
    public static final String TYPE_REQUEST_IS_INSTALL = "0x5172";

    /**
     * 调用手机拨号返回
     */
    public static final String TYPE_RESPONSE_IS_INSTALL = "0x5173";

    public static final int WECHAT_TYPE = 10;
    public static final int ALI_TYPE = 13;


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

    /**
     * 租借归还充电宝请求
     */
    public static final String TYPE_REQUEST_GIVEBACK_DEVICE_BORROW = "0x5213";

    /**
     * 租借归还充电宝返回
     */
    public static final String TYPE_RESPONSE_GIVEBACK_DEVICE_BORROW = "0x5214";


    /********订单*******/

    /**
     * 请求订单列表
     */
    public static final String TYPE_REQUEST_ORDER_LIST = "0x5221";
    /**
     * 请求订单列表返回
     */
    public static final String TYPE_RESPONSE_ORDER_LIST = "0x5222";


    /**
     * 订单详情请求
     */
    public static final String TYPE_REQUEST_ORDER_DETAIL = "0x5223";
    /**
     * 订单详情请求返回
     */
    public static final String TYPE_RESPONSE_ORDER_DETAIL = "0x5224";


    /**
     * 查询有未归还充电宝
     */
    public static final String TYPE_REQUEST_CHARGING_STATUS = "0x5225";

    /**
     * 查询有未归还充电宝返回
     */
    public static final String TYPE_RESPONSE_CHARGING_STATUS = "0x5226";


    /********支付*******/

    /**
     * 第三方支付付款单请求
     */
    public static final String TYPE_REQUEST_THIRD_PAY_ORDER = "0x5231";

    /**
     * 第三方支付付款单返回
     */
    public static final String TYPE_RESPONSE_THIRD_PAY = "0x5232";

    /**
     * 余额支付未付款单请求
     */
    public static final String TYPE_REQUEST_BALANCE_PAY = "0x5233";
    /**
     * 余额支付未付款单请求
     */
    public static final String TYPE_RESPONSE_BALANCE_PAY = "0x5234";

    /********支付*******/

    /**
     * 查询押金及余额请求
     */
    public static final String TYPE_REQUEST_BALANCE_DEPOSIT = "0x5241";
    /**
     * 查询押金及余额返回
     */
    public static final String TYPE_RESPONSE_BALANCE_DEPOSIT = "0x5242";


    /**
     * 充值押金及余额请求
     */
    public static final String TYPE_REQUEST_THIRD_PAY_BALANCE = "0x5243";
    /**
     * 充值押金及余额请求返回
     */
    public static final String TYPE_RESPONSE_THIRD_PAY_BALANCE = "0x5244";


    /**
     * 交易明细请求
     */
    public static final String TYPE_REQUEST_TRANSACTION_DETAIL = "0x5261";
    /**
     * 交易明细返回
     */
    public static final String TYPE_RESPONSE_TRANSACTION_DETAIL = "0x5262";

    /**
     * 查询押金收费标准请求
     */
    public static final String TYPE_REQUEST_DEPOSIT_FEE_RULE = "0x5245";
    /**
     * 查询押金收费标准返回
     */
    public static final String TYPE_RESPONSE_DEPOSIT_FEE_RULE = "0x5246";

    /**
     * 查询收费标准请求
     */
    public static final String TYPE_REQUEST_FEE_RULE = "0x5247";
    /**
     * 查询收费标准返回
     */
    public static final String TYPE_RESPONSE_FEE_RULE = "0x5248";


    /********店铺*******/

    /**
     * 获取指定坐标附近店铺信息（完整版本，适合列表显示时调用）
     */
    public static final String TYPE_REQUEST_NEAR_STORES_DETAIL = "0x5281";
    /**
     * 获取指定坐标附近店铺信息（完整版本，适合列表显示时调用）返回
     */
    public static final String TYPE_RESPONSE_NEAR_STORES_DETAIL = "0x5282";

    /**
     * 获取指定坐标附近店铺信息（完整版本，适合地图显示时调用）
     */
    public static final String TYPE_REQUEST_NEAR_STORES_MAP = "0x5283";
    /**
     * 获取指定坐标附近店铺信息（完整版本，适合地图显示时调用）返回
     */
    public static final String TYPE_RESPONSE_NEAR_STORES_MAP = "0x5284";

    /**
     * 门店详情
     */
    public static final String TYPE_REQUEST_STORES_INFO = "0x5285";
    /**
     * 门店详情返回
     */
    public static final String TYPE_RESPONSE_STORES_INFO = "0x5286";


    /******导航*********/

    /**
     * 调用第三方地图导航请求
     */
    public static final String TYPE_REQUEST_MAP_NAV = "0x5301";
    /**
     * 调用第三方地图导航返回
     */
    public static final String TYPE_RESPONSE_MAP_NAV = "0x5302";

    public static final int MAP_TYPE_GAODE = 1;
    public static final int MAP_TYPE_BAIDU = 2;
    public static final int MAP_TYPE_GOOGLE = 3;


    /******定位*********/

    /**
     * 判断手机是否开启定位服务请求
     */
    public static final String TYPE_REQUEST_LOCATION_ENABLED = "0x5401";
    /**
     * 判断手机是否开启定位服务返回
     */
    public static final String TYPE_RESPONSE_LOCATION_ENABLED = "0x5402";

    /**
     * 请求手机开启定位服务
     */
    public static final String TYPE_REQUEST_ENABLE_LOCATION = "0x5403";
    /**
     * 请求手机开启定位服务返回
     */
    public static final String TYPE_RESPONSE_ENABLE_LOCATION = "0x5404";

    /**
     * 请求手机定位数据
     */
    public static final String TYPE_REQUEST_LOCATION_DATA = "0x5405";
    /**
     * 请求手机定位数据返回
     */
    public static final String TYPE_RESPONSE_LOCATION_DATA = "0x5406";

    public static final int LOCATION_GPS_IP = 1;
    public static final int LOCATION_GPS = 2;
    public static final int LOCATION_IP = 3;
}
