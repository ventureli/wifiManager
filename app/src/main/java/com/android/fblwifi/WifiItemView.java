package com.android.fblwifi;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.fblwifi.manager.IAppWifi;

public class WifiItemView extends ConstraintLayout {

    TextView tvWifiName;
    TextView tvWifiHint;
    TextView tvSecurity;
    private IAppWifi wifi;
    public WifiItemView(@NonNull Context context) {
        super(context);
        this.initView();
    }


    public WifiItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        this.initView();
    }

    public WifiItemView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.initView();
    }

    public WifiItemView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.initView();
    }
    private void initView(){
        LayoutInflater.from(getContext()).inflate(R.layout.view_wifi_item, this);
        tvWifiName = (TextView) findViewById(R.id.tv_wifi_name);
        tvWifiHint = (TextView) findViewById(R.id.tv_hint);
        tvSecurity = (TextView) findViewById(R.id.tv_security);

    }

    public void setWifi(IAppWifi wifi) {
        this.wifi = wifi;
        this.updateUI();
    }

    private void updateUI(){
        tvWifiName.setText(wifi.name());
        tvWifiHint.setText(wifi.description());
        if(wifi.isEncrypt())
        {
            tvSecurity.setText("ð");
        }else{

            tvSecurity.setText("");
        }
    }

}
