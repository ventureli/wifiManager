package com.android.fblwifi;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;

import com.android.fblwifi.manager.AppWifiManager;
import com.android.fblwifi.manager.IAppWifi;
import com.android.fblwifi.manager.IAppWifiManager;
import com.android.fblwifi.manager.OnWifiChangeListener;
import com.android.fblwifi.manager.OnWifiConnectListener;
import com.android.fblwifi.manager.OnWifiStateChangeListener;
import com.android.fblwifi.manager.State;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MainActivity";

    ScrollView mMainScrollView;
    LinearLayout llScrollContent;
    WifiItemView mCurrentItem;
    ArrayList<WifiItemView> mItemViews = new ArrayList<WifiItemView>();
    List<IAppWifi> mWifis = new ArrayList<IAppWifi>();
    IAppWifiManager appWifiManager;
    IAppWifi currentWifi = null;


    private void checkPermission() {
        String[] PERMS_INITIAL = {
                Manifest.permission.ACCESS_FINE_LOCATION,
        };
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int i = ContextCompat.checkSelfPermission(this, PERMS_INITIAL[0]);
            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝，判断需要的权限列表中是否有权限还没拥有
            if (i != PackageManager.PERMISSION_GRANTED
            ) {
                // 如果有权限没有授予，就去提示用户请求
                ActivityCompat.requestPermissions(this, PERMS_INITIAL, 127);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 321) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                //如果用户选择禁止，此时程序没有相应权限，执行相应操作
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    //如果没有获取权限，那么可以提示用户去设置界面--->应用权限开启权限
                    Toast.makeText(this, "获取定位权限", Toast.LENGTH_LONG).show();

                } else {
                }
            }
        }
    }
    void addWifi(){
        new AddNetDialog(MainActivity.this, new AddNetDialog.AddWifiDialogListener() {

            @Override
            public void onOk(@NonNull String name,String pass,int keyMgmt) {
                  appWifiManager.addNetwork(MainActivity.this,name,pass,keyMgmt);
            }

            @Override
            public void onCancel() {

            }
        }).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermission();
        mMainScrollView = (ScrollView) findViewById(R.id.sv_main_scroll_view);
        llScrollContent = (LinearLayout) findViewById(R.id.ll_scroll_content);

        appWifiManager = AppWifiManager.create(this);//) getSystemService(Context.WIFI_SERVICE);
        mCurrentItem = (WifiItemView)findViewById(R.id.wifi_current_item);
        Button btnScan = (Button) findViewById(R.id.btn_scan);
        Button btnOpen = (Button) findViewById(R.id.btn_open);
        Button btnClose = (Button) findViewById(R.id.btn_close);
        Button btnAdd = (Button) findViewById(R.id.btn_add);

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Start scan...");
                startScan();

            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(appWifiManager.isOpened())
                {
                    appWifiManager.closeWifi();
                    mWifis.clear();
                    updateListView();
                }
            }
        });

        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!appWifiManager.isOpened())
                {
                    appWifiManager.openWifi();
                    startScan();
                }
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addWifi();
            }
        });


        appWifiManager.setOnWifiChangeListener(new OnWifiChangeListener() {
            @Override
            public void onWifiChanged(List<IAppWifi> wifis) {
                mWifis = wifis;
                updateListView();
            }
        });
        appWifiManager.setOnWifiConnectListener(new OnWifiConnectListener() {
            @Override
            public void onConnectChanged(boolean status) {
                if(status)
                {
                    Toast.makeText(MainActivity.this,"已连接",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this,"未连接",Toast.LENGTH_SHORT).show();
                }
            }
        });

        appWifiManager.setOnWifiStateChangeListener(new OnWifiStateChangeListener() {
            @Override
            public void onStateChanged(State state) {

            }
        });

    }

    void startScan() {
        appWifiManager.scanWifi();
        Toast.makeText(this,"开始扫描",Toast.LENGTH_SHORT).show();
    }

    protected void onPause() {

        super.onPause();
    }

    protected void onResume() {
        super.onResume();
    }

    void onTapWifi(IAppWifi item){


            //这里开始做其他的区分

            if(item.isConnected())
            {
                new ConnectedWifiDialog(MainActivity.this, item, new ConnectedWifiDialog.ConnectDialogListener() {
                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onDelete() {
                        appWifiManager.removeWifi(item);
                    }
                }).show();
            }else if(item.isSaved())
            {
                new SaveWifiDialog(MainActivity.this, item, new SaveWifiDialog.SavedDialogListener() {
                    @Override
                    public void onCancel() {
                    }
                    @Override
                    public void onConnect() {
                        appWifiManager.connectSavedWifi(item);
                    }

                    @Override
                    public void onDelete() {
                        appWifiManager.removeWifi(item);
                    }
                }).show();
            }else{
                if(!item.isEncrypt()) //不需要密码
                {
                    appWifiManager.connectOpenWifi(item);
                }else{
                appWifiManager.removeWifi(item);
                new EnterPasswordDialog(MainActivity.this, item, new EnterPasswordDialog.EnterPasswordDialogListener() {
                    @Override
                    public void onCancel() {
                        ;//donothing
                    }
                    @Override
                    public void onOk(@NonNull String text) {
                        Toast.makeText(MainActivity.this,text,Toast.LENGTH_SHORT).show();
                        appWifiManager.connectEncryptWifi(item,text);
                    }
                }).show();
            }

        }
    }

    void createWifiItemUI(){
        for (int i = 0; i < mWifis.size() ; i++){
            IAppWifi item = mWifis.get(i);
            WifiItemView itemView = new WifiItemView(MainActivity.this);
            mItemViews.add(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onTapWifi(item);
                }
            });

            llScrollContent.addView(itemView);
            itemView.setWifi(item);
        }
    }

    void updateListView() {
        llScrollContent.removeAllViews();
        mItemViews.clear();
        createWifiItemUI();
        if(currentWifi != null)
        {
            mCurrentItem.setVisibility(View.GONE);
            mCurrentItem.setWifi(currentWifi);
            mCurrentItem.tvWifiHint.setText("已连接");
        }else{
            mCurrentItem.setVisibility(View.GONE);
        }
    }

}