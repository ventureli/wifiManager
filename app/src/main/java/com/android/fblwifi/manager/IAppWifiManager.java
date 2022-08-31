package com.android.fblwifi.manager;

import android.content.Context;
import android.net.wifi.WifiManager;

import java.util.List;

public interface IAppWifiManager {

    boolean isOpened();

    void openWifi();

    void closeWifi();

    void scanWifi();

    boolean disConnectWifi();

    int  addNetwork(Context context , String  networkName , String  networkPassword , int  keyMgmt/*= WifiConfiguration.KeyMgmt.NONE*/);

    boolean connectEncryptWifi(IAppWifi wifi, String password);

    boolean connectSavedWifi(IAppWifi wifi);

    boolean connectOpenWifi(IAppWifi wifi);

    boolean removeWifi(IAppWifi wifi);

    WifiManager getManager();
    List<IAppWifi> getWifi();

    void setOnWifiConnectListener(OnWifiConnectListener onWifiConnectListener);

    void setOnWifiStateChangeListener(OnWifiStateChangeListener onWifiStateChangeListener);

    void setOnWifiChangeListener(OnWifiChangeListener onWifiChangeListener);


    void destroy();
}
