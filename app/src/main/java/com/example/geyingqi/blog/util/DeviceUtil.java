package com.example.geyingqi.blog.util;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by geyingqi on 12/17/15.
 */
public class DeviceUtil {
    public static String getDeviceInfo(Context context){

        try{
            JSONObject json = new JSONObject();
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

            String device_id = tm.getDeviceId();
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            String mac = wifi.getConnectionInfo().getMacAddress();
            json.put("mac",mac);

            if (TextUtils.isEmpty(device_id)){
                device_id = mac;
            }

            if (TextUtils.isEmpty(device_id)){
                device_id = android.provider.Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            }

            json.put("device_id", device_id);
            return json.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }


}
