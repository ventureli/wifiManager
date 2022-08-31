package com.android.fblwifi.manager;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.text.TextUtils;

import java.util.List;

public class AppWifi implements IAppWifi {
    protected String name;
    protected String SSID;
    protected boolean isEncrypt;
    protected boolean isSaved;
    protected boolean isEAP;
    protected boolean isConnected;
    protected String encryption;
    protected String description;
    protected String capabilities;
    protected String ip;
    protected String state;
    protected int level;
    protected int speed = 0;


    public static IAppWifi create(ScanResult result, List<WifiConfiguration> configurations, String connectedSSID, int ipAddress , WifiInfo connectInfo) {
        if (TextUtils.isEmpty(result.SSID))
            return null;
        AppWifi wifi = new AppWifi();
        wifi.isConnected = false;
        wifi.isSaved = false;
        wifi.name = result.SSID;
        wifi.SSID = "\"" + result.SSID + "\"";
//        wifi.isConnected = wifi.SSID.equals(connectedSSID) && ipAddress > 0;
        wifi.isConnected = wifi.SSID.equals(connectedSSID);

        if(wifi.isConnected && connectInfo != null)
        {
            wifi.speed = connectInfo.getLinkSpeed();
        }

        wifi.capabilities = result.capabilities;
        wifi.isEncrypt = true;
        wifi.encryption = "";
        wifi.isEAP = false;
        wifi.level = result.level;
        wifi.ip = wifi.isConnected ? String.format("%d.%d.%d.%d", (ipAddress & 0xff), (ipAddress >> 8 & 0xff), (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff)) : "";
        if (wifi.capabilities.toUpperCase().contains("WPA2-PSK") && wifi.capabilities.toUpperCase().contains("WPA-PSK")) {
            wifi.encryption = "WPA/WPA2";
        } else if (wifi.capabilities.toUpperCase().contains("WPA-PSK")) {
            wifi.encryption = "WPA";
        } else if (wifi.capabilities.toUpperCase().contains("WPA2-PSK")) {
            wifi.encryption = "WPA2";
        } else {
            if( (wifi.capabilities.toUpperCase().contains("WPA2-EAP-CCMP")) || (wifi.capabilities.toUpperCase().contains("RSN-EAP-CCMP")))
            {
                wifi.isEAP = true;
            }else{
                wifi.isEncrypt = false;
            }
        }



        wifi.description = wifi.encryption;
        for (WifiConfiguration configuration : configurations) {
            if (configuration.SSID.equals(wifi.SSID)) {
                wifi.isSaved = true;
                break;
            }
        }
        if (wifi.isSaved) {
            wifi.description = "已保存";
        }
        if (wifi.isConnected) {
            wifi.description = "已连接";
        }
        return wifi;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public boolean isEncrypt() {
        return isEncrypt;
    }

    @Override
    public boolean isSaved() {
        return isSaved;
    }

    @Override
    public boolean isConnected() {
        return isConnected;
    }

    @Override
    public String encryption() {
        return encryption;
    }

    @Override
    public int level() {
        return level;
    }

    @Override
    public int speed() {
        return this.speed;
    }

    @Override
    public String description() {
        return state == null ? description : state;
    }

    @Override
    public String ip() {
        return ip;
    }

    @Override
    public String description2() {
        return isConnected ? String.format("%s(%s)", description(), ip) : description();
    }

    @Override
    public void state(String state) {
        this.state = state;
    }

    @Override
    public String SSID() {
        return SSID;
    }

    @Override
    public String capabilities() {
        return capabilities;
    }

    @Override
    public IAppWifi merge(IAppWifi merge) {
        isSaved = merge.isSaved();
        isConnected = merge.isConnected();
        ip = merge.ip();
        state = merge.state();
        level = merge.level();
        description = ((AppWifi) merge).description;
        return this;
    }

    @Override
    public String state() {
        return state;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null || (!(obj instanceof AppWifi))) return false;
        return ((AppWifi) obj).SSID.equals(this.SSID);
    }

    @Override
    public String toString() {
        return "{" +
                "\"name\":\'" + name + "\'" +
                ", \"SSID\":\'" + SSID + "\'" +
                ", \"isEncrypt\":" + isEncrypt +
                ", \"isSaved\":" + isSaved +
                ", \"isConnected\":" + isConnected +
                ", \"encryption\":\'" + encryption + "\'" +
                ", \"description\":\'" + description + "\'" +
                ", \"capabilities\":\'" + capabilities + "\'" +
                ", \"ip\":\'" + ip + "\'" +
                ", \"state\":\'" + state + "\'" +
                ", \"level\":" + level +
                '}';
    }
}
