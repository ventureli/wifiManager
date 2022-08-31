package com.android.fblwifi

import android.content.Context
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager

import android.os.Bundle
import android.view.View
import com.android.fblwifi.base.BaseDialog
import kotlinx.android.synthetic.main.dialog_add_wifi.*
import kotlinx.android.synthetic.main.dialog_add_wifi.et_pass
import kotlinx.android.synthetic.main.dialog_add_wifi.tv_cancel
import kotlinx.android.synthetic.main.dialog_add_wifi.tv_ok


class AddNetDialog(context: Context,  var listener:AddWifiDialogListener?) : BaseDialog(context) {

    var needPas = false
    override fun getContentResId(): Int {
        return R.layout.dialog_add_wifi;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        et_pass.visibility = View.GONE
        tv_security_wap.setOnClickListener {
            tv_security_none.setBackgroundResource(R.drawable.bg_border_1_dp);
            tv_security_none.setTextColor(context.getColor( R.color.black))
            tv_security_wap.setBackgroundResource(R.color.black)
            tv_security_wap.setTextColor(context.getColor( R.color.white))
            et_pass.visibility = View.VISIBLE
            needPas = true
        }

        tv_security_none.setOnClickListener {
            tv_security_wap.setBackgroundResource(R.drawable.bg_border_1_dp);
            tv_security_none.setBackgroundResource(R.color.black)
            tv_security_none.setTextColor(context.getColor( R.color.white))
            tv_security_wap.setTextColor(context.getColor( R.color.black))
            et_pass.visibility = View.GONE
            needPas = false
        }
    }

    override fun initEvent() {
        tv_cancel.setOnClickListener {
            listener?.onCancel()
            dismiss()
        }
        tv_ok.setOnClickListener {
            var textPass:String? = et_pass.text.toString()
            if(!needPas)
            {
                textPass = null
            }
            var name = et_name.text.toString();
            var encry = WifiConfiguration.KeyMgmt.NONE;
            if(needPas)
            {
                encry = WifiConfiguration.KeyMgmt.WPA_PSK
            }
            listener?.onOk(name,textPass,encry)
            dismiss()
        }


    }

    interface AddWifiDialogListener{
        fun onCancel()
        fun onOk(name:String,pwd:String?,keyMgmt:Int);

    }
}