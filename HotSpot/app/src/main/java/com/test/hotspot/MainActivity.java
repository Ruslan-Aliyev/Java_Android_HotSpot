package com.test.hotspot;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import java.lang.reflect.Method;

public class MainActivity extends Activity {

    Button btnEnableHotspot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnEnableHotspot = (Button) findViewById(R.id.btnEnableHotspot);
        btnEnableHotspot.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                makeHotspot(getBaseContext(), "ssid", "password");
            }
        });
    }

    public static boolean makeHotspot(Context context, String ssid, String password) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);

        if(wifiManager.isWifiEnabled()){
            wifiManager.setWifiEnabled(false);
        }

        WifiConfiguration wifiCon = new WifiConfiguration();
        wifiCon.SSID = ssid;
        wifiCon.preSharedKey = password;
        wifiCon.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
        wifiCon.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
        wifiCon.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
        wifiCon.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);

        boolean result;

        try
        {
            Method setWifiApMethod = wifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);
            result = (Boolean) setWifiApMethod.invoke(wifiManager, wifiCon, true);
        }
        catch (Exception e)
        {
            Log.e("Hotspot_Error", e.getMessage());
            result = false;
        }
        return result;
    }
}
