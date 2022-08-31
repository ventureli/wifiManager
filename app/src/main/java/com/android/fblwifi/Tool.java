package com.android.fblwifi;

import android.os.Handler;
import android.os.Looper;
public class Tool {
    private static Handler mainHandler = new Handler(Looper.getMainLooper());

    /**
     * 在主线程中运行
     */
    public static  void  runOnUiThread(Runnable runnable) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            runnable.run();;
        } else {
            mainHandler.post(runnable);
        }

    }
}
