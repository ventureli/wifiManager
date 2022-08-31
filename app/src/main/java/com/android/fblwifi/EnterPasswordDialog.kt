package com.android.fblwifi

import android.content.Context
import android.os.Bundle
import com.android.fblwifi.base.BaseDialog
import com.android.fblwifi.manager.IAppWifi
import kotlinx.android.synthetic.main.dialog_enter_pass.*
import kotlinx.android.synthetic.main.dialog_enter_pass.tv_cancel
import kotlinx.android.synthetic.main.dialog_enter_pass.tv_level
import kotlinx.android.synthetic.main.dialog_enter_pass.tv_name
import kotlinx.android.synthetic.main.dialog_enter_pass.tv_security

class EnterPasswordDialog(context: Context, val wifi: IAppWifi, var listener:EnterPasswordDialogListener?) : BaseDialog(context) {

    override fun getContentResId(): Int {
        return R.layout.dialog_enter_pass;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tv_name.text = (wifi.name())
        tv_level.text = (wifi.level().toString())
        tv_security.text = (wifi.encryption().toString())
//        tv_speed.text = (wifi().toString())

    }

    override fun initEvent() {
        tv_cancel.setOnClickListener {
            listener?.onCancel()
            dismiss()
        }
        tv_ok.setOnClickListener {
            var text = et_pass.text.toString()
            listener?.onOk(text)
            dismiss()
        }

    }

    interface EnterPasswordDialogListener{
        fun onCancel()
        fun onOk(text:String);

    }
}