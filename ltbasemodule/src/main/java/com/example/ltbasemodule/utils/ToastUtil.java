package com.example.ltbasemodule.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

/**
 * 一个线程安全的Toast
 */
public class ToastUtil {
    private static Toast toast;

    /**
     * 强大的吐司，可以连续弹的吐司
     *
     * @param context 上下文
     * @param msg     消息
     */
    @SuppressLint("ShowToast")
    public static void showToastLong(Context context, String msg) {
        if (toast == null) {
            //创建toast
            synchronized (ToastUtil.class) {
                if (toast == null) {
                    toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
                }
            }
        } else {
            //更改当前吐司的内容
            toast.setText(msg);
        }
        //最后再show
        toast.show();
    }

    /**
     * 强大的吐司，可以连续弹的吐司
     *
     * @param context 上下文
     * @param msg     消息
     */
    @SuppressLint("ShowToast")
    public static void showToastShort(Context context, String msg) {
        if (toast == null) {
            synchronized (ToastUtil.class) {
                if (toast == null) {
                    //创建toast
                    toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
                }
            }
        } else {
            //更改当前吐司的内容
            toast.setText(msg);
        }
        //最后再show
        toast.show();
    }

    public static void detachToast() {
        if (toast != null)
            toast = null;
    }
}
