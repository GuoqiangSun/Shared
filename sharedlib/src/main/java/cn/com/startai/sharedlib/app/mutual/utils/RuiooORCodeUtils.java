package cn.com.startai.sharedlib.app.mutual.utils;

/**
 * author: Guoqiang_Sun
 * date: 2018/12/17 0017
 * Desc:
 */
public class RuiooORCodeUtils {

    private static final java.lang.String QR_CODE_INDEX = "http://www.ruioo.net/merchant/detail/";

    /**
     * 检查 是否是sn的二码维码
     */
    public static String[] getStoreIdAndChargerId(String result) {

        if (result != null && result.startsWith(QR_CODE_INDEX)) {
            String[] split = result.replace(QR_CODE_INDEX, "").split("/");

            if (split.length == 2) {
                return split;
            }
        }
        return null;

    }

}
