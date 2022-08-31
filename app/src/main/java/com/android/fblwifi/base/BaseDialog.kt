package com.android.fblwifi.base


import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import androidx.annotation.LayoutRes
import com.android.fblwifi.R
abstract class BaseDialog(context: Context,val cancelable:Boolean = true) : Dialog(context, R.style.MyDialog) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getContentResId())
        setCancelable(true)
        setCanceledOnTouchOutside(cancelable)
        initEvent()
    }

    fun show(x: Int = -1, y: Int = -1) {
        val wl = window?.attributes
        if (x != -1) {
            wl?.x = x
            wl?.width = WindowManager.LayoutParams.WRAP_CONTENT
        }
        if (y != -1) {
            wl?.y = y
            wl?.height = WindowManager.LayoutParams.WRAP_CONTENT
        }

        window?.attributes = wl
        if (x != -1 && y != -1) {
            window?.setGravity(Gravity.TOP or Gravity.START)
        } else if (x != -1) {
            window?.setGravity(Gravity.START)
        } else {
            window?.setGravity(Gravity.TOP)
        }

        window?.setWindowAnimations(0)
        show()
    }
    @LayoutRes
    protected abstract fun getContentResId(): Int
    protected abstract fun initEvent()
}



