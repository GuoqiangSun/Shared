package cn.com.startai.sharedlib.app.js.Utils;

import android.content.Context;

import java.util.Locale;

import cn.com.swain.baselib.sp.BaseSpTool;
import cn.com.swain.baselib.util.ChangeLanguageHelper;
import cn.com.swain.baselib.log.Tlog;

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


    public static final String ZH = "zh";
    public static final String EN = "en";

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
            type = EN;
        } else if (language.startsWith("zh")) {
            type = ZH;
        } else {
            type = EN;
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
        Tlog.v(TAG, " system language :" + sysLanguage + "; H5 language :" + h5Language);

        boolean change = true;
        if (sysLanguage.startsWith(h5Language)) {
            change = false;
        } else if (sysLanguage.startsWith(h5Language.toUpperCase())) {
            change = false;
        } else if (sysLanguage.startsWith(h5Language.toLowerCase())) {
            change = false;
        }

        if (change) {
            int appLanguage = ChangeLanguageHelper.getAppLanguage(h5Language);
            ChangeLanguageHelper.init(mCtx, appLanguage);

            String curLanguage = mCtx.getResources().getConfiguration().locale.getLanguage();
            Tlog.v(TAG, " change app Language , cur language:" + curLanguage);
        }
    }


}
