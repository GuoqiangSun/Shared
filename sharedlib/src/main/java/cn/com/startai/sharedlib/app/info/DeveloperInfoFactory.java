package cn.com.startai.sharedlib.app.info;

import cn.com.startai.mqttsdk.mqtt.MqttInitParam;
import cn.com.startai.sharedlib.app.global.CustomManager;

/**
 * author: Guoqiang_Sun
 * date: 2019/1/18 0018
 * Desc:
 */
public class DeveloperInfoFactory {

    private DeveloperInfoFactory() {
    }

    public static MqttInitParam produceMqttInitParam() {

        if (CustomManager.getInstance().isSharedCharger()) {
            return new SharedChargerDeveloperInfo();
        } else if (CustomManager.getInstance().isSynerMax()) {
            return new SynermaxChargerDeveloperInfo();
        } else if (CustomManager.getInstance().isPikaPower()) {
            return new PikaChargerDeveloperInfo();
        }
        return null;

    }

}
