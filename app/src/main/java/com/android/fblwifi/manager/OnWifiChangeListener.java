package com.android.fblwifi.manager;

import java.util.List;

public interface OnWifiChangeListener {
    void onWifiChanged(List<IAppWifi> wifis);
}
