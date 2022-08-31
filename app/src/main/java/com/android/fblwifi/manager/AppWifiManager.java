package com.android.fblwifi.manager;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;


import java.util.List;

public class AppWifiManager extends BaseWifiManager {

    private AppWifiManager(Context context) {
        super(context);
    }

    public static IAppWifiManager create(Context context) {
        return new AppWifiManager(context);
    }

    @Override
    public boolean isOpened() {
        return manager.isWifiEnabled();
    }

    @Override
    public void openWifi() {
        if (!manager.isWifiEnabled())
            manager.setWifiEnabled(true);
    }

    @Override
    public void closeWifi() {
        if (manager.isWifiEnabled())
            manager.setWifiEnabled(false);
    }

    @Override
    public void scanWifi() {
        manager.startScan();
    }

    @Override
    public boolean disConnectWifi() {
        return manager.disconnect();
    }

    @Override
    public boolean connectEncryptWifi(IAppWifi wifi, String password) {
        if (manager.getConnectionInfo() != null && wifi.SSID().equals(manager.getConnectionInfo().getSSID()))
            return true;
        int networkId = AppWifiHelper.configOrCreateWifi(manager, wifi, password);
        boolean ret = manager.enableNetwork(networkId, true);
        modifyWifi(wifi.SSID(), "开始连接...");
        return ret;
    }



    @Override
    public boolean connectSavedWifi(IAppWifi wifi) {
        int networkId = AppWifiHelper.configOrCreateWifi(manager, wifi, null);
        boolean ret = manager.enableNetwork(networkId, true);
        modifyWifi(wifi.SSID(), "开始连接...");
        return ret;
    }

    @Override
    public boolean connectOpenWifi(IAppWifi wifi) {
        boolean ret = connectEncryptWifi(wifi, null);
        modifyWifi(wifi.SSID(), "开始连接...");
        return ret;
    }


    @Override
    public boolean removeWifi(IAppWifi wifi) {
        boolean ret = AppWifiHelper.deleteWifiConfiguration(manager, wifi);
        modifyWifi();
        return ret;
    }

    @Override
    public List<IAppWifi> getWifi() {
        return wifis;
    }


}
