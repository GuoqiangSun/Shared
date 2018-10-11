package cn.com.startai.sharedlib.app.js.Utils;

import android.content.Context;

import java.util.Locale;

import cn.com.startai.sharedlib.app.mutual.MutualManager;
import cn.com.swain.baselib.util.ChangeLanguageHelper;
import cn.com.swain169.log.Tlog;

/**
 * author: Guoqiang_Sun
 * date : 2018/10/9 0009
 * desc :
 */
public class Language {

    public static final String ZH = "zh";
    public static final String EN = "en";

    private static String TAG = MutualManager.TAG;


    public static String changeSystemLangToH5Lang(Context mContext) {
        Locale locale = mContext.getResources().getConfiguration().locale;
        return changeSystemLangToH5Lang(locale.getLanguage());

    }

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

    public static void changeLanguage(Context mCtx) {
        Tlog.v(TAG, " execute changeLanguage() ");

        String sysLanguage = mCtx.getResources().getConfiguration().locale.getLanguage();
        String h5Language = LocalData.getLocalData(mCtx).getLanguage(sysLanguage);
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
