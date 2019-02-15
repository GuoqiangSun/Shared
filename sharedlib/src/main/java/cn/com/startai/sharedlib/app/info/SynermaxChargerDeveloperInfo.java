package cn.com.startai.sharedlib.app.info;

import cn.com.startai.mqttsdk.mqtt.MqttInitParam;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/27 0027
 * desc :
 */
public class SynermaxChargerDeveloperInfo extends MqttInitParam {

    public SynermaxChargerDeveloperInfo() {
        super.domain = "ruioo";
        super.apptype = "sharedPowerBank/controll/android";
        super.appid = "f7cc35b33c8f579fae3df9f3e5941608";
        super.m_ver = "Json_9.2.5_1.3.4";
    }

}
