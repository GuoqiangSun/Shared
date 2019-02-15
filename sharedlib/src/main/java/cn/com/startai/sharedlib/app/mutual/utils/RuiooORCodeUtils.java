package cn.com.startai.sharedlib.app.mutual.utils;

/**
 * author: Guoqiang_Sun
 * date: 2018/12/17 0017
 * Desc:
 */
public class RuiooORCodeUtils {

    private static final java.lang.String QR_CODE_INDEX = "http://www.ruioo.net/merchant/detail/";

    private static final java.lang.String QR_CODE_INDEX_LINE = "http://hb.startai.net/scan/";

//    https://xiuse.xiudtech.com/scan/18A1P02895

    /**
     * 检查 是否是sn的二码维码
     */
    public static String[] getStoreIdAndChargerId(String result) {

        if (result != null) {
            if (result.startsWith(QR_CODE_INDEX)) {
                String[] split = result.replace(QR_CODE_INDEX, "").split("/");

                if (split.length == 2) {
                    return split;
                }

            } else if (result.startsWith(QR_CODE_INDEX_LINE)) {
                return result.replace(QR_CODE_INDEX_LINE, "").split("/");

            }
        }
        return null;

    }

}
