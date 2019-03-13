package cn.com.startai.sharedlib.app.info;

import cn.com.startai.mqttsdk.mqtt.MqttInitParam;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/27 0027
 * desc :
 */
public class SynermaxChargerDeveloperInfo extends MqttInitParam {

    public SynermaxChargerDeveloperInfo() {
        super.domain = "synermax";
        super.apptype = "sharedPowerBank/controll/android";
        super.appid = "qxb546488afc25b190";
        super.m_ver = "Json_9.2.5_1.3.4";
    }

}
