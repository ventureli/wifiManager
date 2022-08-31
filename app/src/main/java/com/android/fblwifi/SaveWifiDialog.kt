package com.android.fblwifi

import android.content.Context
import android.os.Bundle
import com.android.fblwifi.base.BaseDialog
import com.android.fblwifi.manager.IAppWifi
import kotlinx.android.synthetic.main.dialog_saved_wifi.*

class SaveWifiDialog(context: Context, val wifi: IAppWifi, var listener:SavedDialogListener?) : BaseDialog(context) {

    override fun getContentResId(): Int {
        return R.layout.dialog_saved_wifi;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tv_name.text = (wifi.name())
        tv_level.text = (wifi.level().toString())


    }

    override fun initEvent() {
        tv_cancel.setOnClickListener {
            listener?.onCancel()
            dismiss()
        }
        tv_connect.setOnClickListener {
            listener?.onConnect()
            dismiss()
        }
        tv_delete.setOnClickListener {
            listener?.onDelete()
            dismiss()
        }

    }

    interface SavedDialogListener{
        fun onCancel()
        fun onConnect();
        fun onDelete();

    }
}