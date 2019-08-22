package cn.com.startai.shared.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import cn.com.swain.baselib.util.MapUtils;
import cn.com.swain.baselib.util.StrUtil;

/**
 * author Guoqiang_Sun
 * date 2019/5/6
 * desc
 */
public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.test_activity_home);

    }


    double lat = 23.1307896;
    double lng = 113.36774690000001;
    String address = "guanghaidasha";

    public void goBaiduMap(View view) {
        boolean install;
        Intent mIntent;
        install = MapUtils.isBaiduMapInstall(this);
        mIntent = MapUtils.getBaiduMap(lat, lng, address, getPackageName());
        if (install) {
            startActivity(mIntent);
        } else {
            Toast.makeText(this, "not map", Toast.LENGTH_SHORT).show();
        }
    }

    public void goTencentMap(View view) {
        boolean install;
        Intent mIntent;
        install = MapUtils.isTencentMapInstall(this);
        mIntent = MapUtils.getTencentMap(lat, lng, address);
        if (install) {
            startActivity(mIntent);
        } else {
            Toast.makeText(this, "not map", Toast.LENGTH_SHORT).show();
        }
    }

    public void goGoogleMap(View view) {
        boolean install;
        Intent mIntent;
        install = MapUtils.isGoogleMapInstall(this);
        mIntent = MapUtils.getGoogleMap(lat, lng, address);
        if (install) {
            startActivity(mIntent);
        } else {
            Toast.makeText(this, "not map", Toast.LENGTH_SHORT).show();
        }
    }

    public void goGaodeMap(View view) {
        boolean install;
        Intent mIntent;
        install = MapUtils.isGaodeMapInstall(this);
        mIntent = MapUtils.getGaodeMap(lat, lng, address);
        if (install) {
            startActivity(mIntent);
        } else {
            Toast.makeText(this, "not map", Toast.LENGTH_SHORT).show();
        }
    }


    public void initHandler(View view) {
        long from = 0xFF0C0000123L;  // 17526687793443
        JSONObject obj = new JSONObject();
        try {
            obj.put("from", from);
            obj.put("fromS", String.valueOf(from));
            obj.put("s", "{");
            obj.put("e", "}");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String s = obj.toString();
        Log.v("abc", s);
        Log.v("abc", StrUtil.toHexString(s.getBytes()));

        byte[] b = new byte[]{0x7b,
                0x22, 0x66, 0x72, 0x6f, 0x6d, 0x22, 0x3a,
                0x31, 0x37, 0x35, 0x32, 0x36, 0x36, 0x38, 0x37, 0x37, 0x39, 0x33, 0x34, 0x34, 0x33,
                0x2c, 0x22, 0x66, 0x72, 0x6f, 0x6d, 0x53, 0x22, 0x3a,
                0x22, 0x31, 0x37, 0x35, 0x32, 0x36, 0x36, 0x38, 0x37, 0x37, 0x39, 0x33, 0x34, 0x34, 0x33, 0x22,
                0x2c, 0x22, 0x73, 0x22, 0x3a, 0x22,
                0x7b, 0x22, 0x2c, 0x22, 0x65, 0x22, 0x3a, 0x22, 0x7d, 0x22,
                0x7d};

        String jsonStrHex = new String(b);
        Log.v("abc", jsonStrHex);

        try {
            JSONObject objParse = new JSONObject(jsonStrHex);
            Log.v("abc", String.valueOf(obj.get("from")));
            Log.v("abc", String.valueOf(obj.get("fromS")));
            Log.v("abc", objParse.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void stopHandler(View view) {
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
