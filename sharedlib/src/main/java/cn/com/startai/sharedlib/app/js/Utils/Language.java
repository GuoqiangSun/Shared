package cn.com.startai.sharedlib.app.js.Utils;

import android.content.Context;

import java.util.Locale;

import cn.com.swain.baselib.app.utils.ChangeLanguageHelper;
import cn.com.swain.baselib.log.Tlog;
import cn.com.swain.baselib.sp.BaseSpTool;

/**
 * author: Guoqiang_Sun
 * date : 2018/10/9 0009
 * desc :
 */
public class Language extends BaseSpTool {

    private static final String NAME_LOCAL_DATA = "LocalData";

    private Language(Context mCtx) {
        super(mCtx, NAME_LOCAL_DATA);
    }

    private static Language mLocalData;

    public static Language getLocalData(Context mCtx) {
        if (mLocalData == null) {
            synchronized (NAME_LOCAL_DATA) {
                if (mLocalData == null) {
                    mLocalData = new Language(mCtx);
                }
            }
        }
        return mLocalData;
    }

    private static final String KEY_LANGUAGE = "language";

    public void setLanguage(String language) {
        putString(KEY_LANGUAGE, language);
    }

    public String getLanguage(String def) {
        return getString(KEY_LANGUAGE, def);
    }


    public static final String H5_ZH = "zh";
    public static final String H5_EN = "en";
    public static final String H5_GE = "ge";//德语

    private static String convertH5LanToSys(String h5Lan) {
        if (H5_GE.equals(h5Lan)) {
            return "de";
        }
        return h5Lan;
    }

    private static String TAG = "Language";

    /**
     * 系统语言转换成H5语言
     */
    public static String changeSystemLangToH5Lang(Context mContext) {
        Locale locale = mContext.getResources().getConfiguration().locale;
        return changeSystemLangToH5Lang(locale.getLanguage());

    }

    /**
     * 系统语言转换成H5语言
     */
    public static String changeSystemLangToH5Lang(String language) {
        Tlog.v(TAG, " system language " + language);
        String type;
        if (language.startsWith("en")) {
            type = H5_EN;
        } else if (language.startsWith("zh")) {
            type = H5_ZH;
        } else if(language.startsWith("de")){
            type = H5_GE;
        }else {
            type = H5_EN;
        }
        return type;
    }

    /**
     * 保存设置的语言
     */
    public static void saveLanguage(Context mCtx, String lan) {
        Language.getLocalData(mCtx).setLanguage(lan);
    }

    /**
     * 修改语言
     */
    public static void changeLanguage(Context mCtx) {
        Tlog.v(TAG, " execute changeLanguage() ");

        String sysLanguage = mCtx.getResources().getConfiguration().locale.getLanguage();
        String h5Language = Language.getLocalData(mCtx).getLanguage(sysLanguage);
        String language = convertH5LanToSys(h5Language);
        Tlog.v(TAG, " system language :" + sysLanguage + "; H5 language :" + h5Language);

        boolean change = true;
        if (sysLanguage.startsWith(language)) {
            change = false;
        } else if (sysLanguage.startsWith(language.toUpperCase())) {
            change = false;
        } else if (sysLanguage.startsWith(language.toLowerCase())) {
            change = false;
        }

        if (change) {
            Locale appLanguage = getAppLanguage(h5Language);
            ChangeLanguageHelper.changeLanguage(mCtx, appLanguage);

            String curLanguage = mCtx.getResources().getConfiguration().locale.getLanguage();
            Tlog.v(TAG, " change app Language , cur language:" + curLanguage);
        }
    }


    public static Locale getAppLanguage(String h5lang) {

        if (h5lang.equalsIgnoreCase(H5_ZH)) {
            return Locale.SIMPLIFIED_CHINESE;
        } else if (h5lang.equalsIgnoreCase(H5_EN)) {
            return Locale.ENGLISH;
        } else if (h5lang.equalsIgnoreCase(H5_GE)) {
            return Locale.GERMANY;
        }
        String country = Locale.getDefault().getCountry();

        if ("CN".equals(country)) {
            return Locale.SIMPLIFIED_CHINESE;
        } else {
            return Locale.ENGLISH;
        }
    }


}
