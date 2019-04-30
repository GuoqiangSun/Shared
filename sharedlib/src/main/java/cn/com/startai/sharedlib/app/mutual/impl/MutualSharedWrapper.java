package cn.com.startai.sharedlib.app.mutual.impl;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;

import cn.com.startai.sharedlib.app.mutual.IMutualCallBack;
import cn.com.startai.sharedlib.app.mutual.IUserIDManager;
import cn.com.startai.sharedlib.app.mutual.MutualManager;
import cn.com.swain.baselib.jsInterface.response.BaseResponseMethod;
import cn.com.swain.baselib.log.Tlog;

/**
 * author: Guoqiang_Sun
 * date: 2019/1/24 0024
 * Desc:
 */
public class MutualSharedWrapper implements IMutualCallBack, IUserIDManager {

    public String TAG = MutualManager.TAG;

    private final IMutualCallBack mCallBack;
    private final IUserIDManager mUserID;

    public MutualSharedWrapper(IMutualCallBack mCallBack) {
        this(mCallBack, null);
    }

    public MutualSharedWrapper(IUserIDManager mUserID) {
        this(null, mUserID);
    }

    public MutualSharedWrapper(IMutualCallBack mCallBack, IUserIDManager mUserID) {
        this.mCallBack = mCallBack;
        this.mUserID = mUserID;
    }

    @Override
    public void jFinish() {
        mCallBack.jFinish();
    }

    @Override
    public void callJs(BaseResponseMethod mBaseResponseMethod) {
        mCallBack.callJs(mBaseResponseMethod);
    }

    @Override
    public void callJs(String method) {
        mCallBack.callJs(method);
    }

    @Deprecated
    @Override
    public void scanQR(int requestCode) {
        mCallBack.scanQR(requestCode);
    }

    @Override
    public void jsStartActivityForResult(Intent intent, int requestCode) {
        mCallBack.jsStartActivityForResult(intent, requestCode);
    }

    @Override
    public Activity getActivity() {
        return mCallBack != null ? mCallBack.getActivity() : null;
    }

    @Override
    public String getUserIDFromMq() {
        return mUserID.getUserIDFromMq();
    }

    @Override
    public String getUserID() {
        return mUserID.getUserID();
    }

    @Override
    public void setUserID(String userID) {
        mUserID.setUserID(userID);
    }

    private LocationManager mLocationManager;

    protected LocationManager getLocationManager() {
        if (mLocationManager == null) {
            synchronized (this) {
                if (mLocationManager == null) {
                    Activity activity = getActivity();
                    if (activity != null) {
                        mLocationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
                    }
                }
            }
        }
        return mLocationManager;
    }

    protected boolean gpsEnabled() {
        LocationManager locationManager = getLocationManager();
        return locationManager != null
                && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    protected boolean ipEnabled() {
        LocationManager locationManager = getLocationManager();
        return locationManager != null
                && locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    protected boolean locationEnabled() {
        boolean providerEnabledGps = gpsEnabled();
        boolean providerEnabledNet = ipEnabled();
        Tlog.d(TAG, " queryLocationEnabled providerEnabledGps :" + providerEnabledGps
                + " providerEnabledNet:" + providerEnabledNet);
        return providerEnabledGps || providerEnabledNet;
    }

    private Criteria getCriteria() {
        Criteria criteria = new Criteria();
        // 设置定位精确度 Criteria.ACCURACY_COARSE比较粗略，Criteria.ACCURACY_FINE则比较精细
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        // 设置是否要求速度
        criteria.setSpeedRequired(true);
        // 设置是否允许运营商收费
        criteria.setCostAllowed(false);
        // 设置是否需要方位信息
        criteria.setBearingRequired(false);
        // 设置是否需要海拔信息
        criteria.setAltitudeRequired(false);
        // 设置对电源的需求
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        return criteria;
    }

    protected String getBestProvider() {
        LocationManager locationManager = getLocationManager();

        if (locationManager != null) {
            return locationManager.getBestProvider(getCriteria(), true);
        }
        return LocationManager.NETWORK_PROVIDER;
    }


    @SuppressLint("MissingPermission")
    protected Location getLastKnownLocation(String provider) {

        LocationManager locationManager = getLocationManager();
        if (locationManager == null) {
            return null;
        }
        return locationManager.getLastKnownLocation(provider);
    }

    // 获取不到location 切换下
    protected String changeProvider(String provider) {
        if (LocationManager.NETWORK_PROVIDER.equalsIgnoreCase(provider)) {
            return LocationManager.GPS_PROVIDER;
        } else if (LocationManager.GPS_PROVIDER.equalsIgnoreCase(provider)) {
            return LocationManager.NETWORK_PROVIDER;
        } else {
            return LocationManager.NETWORK_PROVIDER;
        }
    }


}
