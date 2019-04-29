package cn.com.startai.sharedlib.app.mutual.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import cn.com.swain.baselib.app.utils.AppUtils;

/**
 * author Guoqiang_Sun
 * date 2019/4/29
 * desc
 */
public class MapUtils {

    public static class LatLng {

        public LatLng(double latitude, double longitude) {
            this.longitude = longitude;
            this.latitude = latitude;
        }

        public double longitude;
        public double latitude;
    }

    /**
     * BD-09 坐标转换成 GCJ-02 坐标
     */
    public static LatLng BD2GCJ(LatLng bd) {
        double x = bd.longitude - 0.0065, y = bd.latitude - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * Math.PI);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * Math.PI);

        double lng = z * Math.cos(theta);//lng
        double lat = z * Math.sin(theta);//lat
        return new LatLng(lat, lng);
    }

    /**
     * GCJ-02 坐标转换成 BD-09 坐标
     */
    public static LatLng GCJ2BD(LatLng bd) {
        double x = bd.longitude, y = bd.latitude;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * Math.PI);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * Math.PI);
        double tempLon = z * Math.cos(theta) + 0.0065;
        double tempLat = z * Math.sin(theta) + 0.006;
        return new LatLng(tempLat, tempLon);
    }

    public static boolean isBaiduMapInstall(Context app) {
        return AppUtils.isAppInstalled(app, "com.baidu.BaiduMap");
    }

    /**
     * 跳转百度地图
     */
    public static Intent getBaiduMap(double mLat, double mLng, String mAddressStr, String pkgName) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("baidumap://map/direction?destination=latlng:"
                + mLat + ","
                + mLng + "|name:" + mAddressStr + // 终点
                "&mode=driving" + // 导航路线方式
                "&src=" + pkgName));
        return intent;
    }

    public static boolean isGaodeMapInstall(Context app) {
        return AppUtils.isAppInstalled(app, "com.autonavi.minimap");
    }

    /**
     * 跳转高德地图
     */
    public static Intent getGaodeMap(double mLat, double mLng, String mAddressStr) {

        LatLng endPoint = BD2GCJ(new LatLng(mLat, mLng));//坐标转换
        StringBuffer stringBuffer = new StringBuffer("androidamap://navi?sourceApplication=").append("amap");
        stringBuffer.append("&lat=").append(endPoint.latitude)
                .append("&lon=").append(endPoint.longitude).append("&keywords=").append(mAddressStr)
                .append("&dev=").append(0)
                .append("&style=").append(2);
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(stringBuffer.toString()));
        intent.setPackage("com.autonavi.minimap");
        return intent;
    }


    public static boolean isTencentMapInstall(Context app) {
        return AppUtils.isAppInstalled(app, "com.tencent.map");
    }

    /**
     * 跳转腾讯地图
     */
    public static Intent goToTencentMap(double mLat, double mLng, String mAddressStr) {
        LatLng endPoint = BD2GCJ(new LatLng(mLat, mLng));//坐标转换
        StringBuffer stringBuffer = new StringBuffer("qqmap://map/routeplan?type=drive")
                .append("&tocoord=").append(endPoint.latitude).append(",")
                .append(endPoint.longitude).append("&to=")
                .append(mAddressStr);
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(stringBuffer.toString()));
        return intent;
    }

    public static boolean isGoogleMapInstall(Context app) {
        return AppUtils.isAppInstalled(app, "com.google.android.apps.maps");
    }

    public static Intent getGoogleMap(double mLatitude, double mLongitude, String address) {
        Uri gmmIntentUri = Uri.parse("google.navigation:q="
                + mLatitude + "," + mLongitude
                + ",+ " + address);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW,
                gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");

//        Uri uri = Uri
//                .parse("market://details?id=com.google.android.apps.maps");
//        Intent intent = new Intent(Intent.ACTION_VIEW, uri);

        return mapIntent;
    }


}
