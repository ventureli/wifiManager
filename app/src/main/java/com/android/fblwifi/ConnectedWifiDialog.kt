package com.android.fblwifi

import android.content.Context
import android.os.Bundle
import com.android.fblwifi.base.BaseDialog
import com.android.fblwifi.manager.IAppWifi
import kotlinx.android.synthetic.main.dialog_connected_wifi.*

class ConnectedWifiDialog(context: Context, val wifi: IAppWifi, var listener:ConnectDialogListener?) : BaseDialog(context) {

    override fun getContentResId(): Int {
        return R.layout.dialog_connected_wifi;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tv_name.text = (wifi.name())
        tv_level.text = (wifi.level().toString())
        tv_security.text = (wifi.encryption().toString())
        tv_ip.text = (wifi.ip().toString())
        tv_speed.text = (wifi.speed().toString())

    }

    override fun initEvent() {
        tv_cancel.setOnClickListener {
            listener?.onCancel()
            dismiss()
        }
        tv_delete.setOnClickListener {
            listener?.onDelete()
            dismiss()
        }

    }

    interface ConnectDialogListener{
        fun onCancel()
        fun onDelete();

    }
}