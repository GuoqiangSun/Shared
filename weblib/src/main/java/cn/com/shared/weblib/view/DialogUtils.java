package cn.com.shared.weblib.view;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import org.xwalk.core.XWalkJavascriptResult;

/**
 * author: Guoqiang_Sun
 * date : 2018/4/8 0008
 * desc :
 */

public class DialogUtils {

    public static void alert(Activity mAct, String message, final XWalkJavascriptResult result) {

        AlertDialog.Builder b = new AlertDialog.Builder(mAct);
        b.setTitle("Alert");
        b.setMessage(message);
        b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                result.confirm();
            }
        });
        b.setCancelable(false);
        b.create().show();

    }

}
