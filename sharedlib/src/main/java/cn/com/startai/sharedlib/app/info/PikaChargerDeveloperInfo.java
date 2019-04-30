package cn.com.startai.sharedlib.app.info;

import cn.com.startai.mqttsdk.mqtt.MqttInitParam;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/27 0027
 * desc :
 */
public class PikaChargerDeveloperInfo extends MqttInitParam {

    public PikaChargerDeveloperInfo() {
        super.domain = "synermax";
        super.apptype = "sharedPowerBank/controll/android";
        super.appid = "qxee6c7574f20f38a4";
        super.m_ver = "Json_9.2.5_1.3.4";
    }

}
