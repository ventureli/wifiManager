package com.android.fblwifi.manager;

public interface IAppWifi {

    String name();

    boolean isEncrypt();

    boolean isSaved();

    boolean isConnected();

    String encryption();

    int level();
    int speed();

    String description();

    String ip();

    String description2();

    void state(String state);

    @Deprecated
    String SSID();

    @Deprecated
    String capabilities();

    @Deprecated
    IAppWifi merge(IAppWifi merge);

    String state();
}
