package cn.com.startai.sharedlib.app.mutual.utils;

/**
 * Created by Robin on 2019/3/4.
 * qq: 419109715 彬影
 */
public class EGHLConsts {


    //开发者信息
    public static final String SERVICE_ID = "SSN";//服务号或商户号 由eghl给提供 固定值
    public static final String PASSWORD = "1InAN9kV";//密码，由eghl提供 固定值
    public static final String PASSWORD_TEST = "ssn12345";//测试密码

    public static final String PAYMENT_GATEWAY = "https://securepay.e-ghl.com/IPG/Payment.aspx";//支付请求网关 固定值
    public static final String PAYMENT_GATEWAY_TEST = "https://test2pay.ghl.com/IPGSG/Payment.aspx"; //测试网关

    public static final String CALLBACK_URL = "https://bs.startai.cn/pay/v1.0/eghl_pay/notify"; // 回调url 由startai 后台提供 用于接收 支付结果 固定值

    public static final String MERCHANT_NAME = "synermax";//商户名称 固定值
    public static final String PAYMENT_METHOD = "ANY";//支付方式 固定值
    public static final String TRANSACTION_TYPE = "SALE";//交易类型 固定值
    public static final String CURRENCY_CODE = "MYR"; //货币类型 固定值
    public static final String PAGE_TIMEOUT = "500"; //订单有效期 单位秒， 500表示订单必须在 500/60=8分20秒内完成支付 会以倒计时的方式显示在支付界面


//    public static final String cardHolder = "郑志锋";
//    public static final String cardHolder = "ZHENG ZHIFENG";
//    public static final String cardHolder = "ZHENGZHIFENG";
//
//    public static final String cardNumber = "4392260020454565";
//    public static final String cvv = "191";
//    public static final String expire_Y = "2021";
//    public static final String expire_M = "01";


//    public static final String carHolder1 = "Kok Siew Mun";
//    public static final String cardNumber = "5599 9800 0198 5893";
//    public static final String cvv = "588";
//    public static final String expir = "202103";

}
