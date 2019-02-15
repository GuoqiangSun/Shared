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

        if (CustomManager.getInstance().isRuioo()) {
            return new RuiooChargerDeveloperInfo();
        } else if (CustomManager.getInstance().isSynerMax()) {
            return new SynermaxChargerDeveloperInfo();
        }
        return null;

    }

}
