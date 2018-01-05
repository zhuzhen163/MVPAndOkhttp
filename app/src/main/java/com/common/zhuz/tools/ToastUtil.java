package com.common.zhuz.tools;

import android.view.Gravity;
import android.widget.Toast;

import com.common.zhuz.application.ZApplication;

/**
 * Created by Administrator on 2017/3/6
 * toast基类
 */
public class ToastUtil {
    public static Toast toast;

    public static void showToast(String str) {
        if (StringUtils.isNotBlank(str)){
            if (toast == null) {
                toast = Toast.makeText(ZApplication.getAppContext(), str, Toast.LENGTH_SHORT);
            } else {
                toast.setText(str);
            }
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }
    }
    public static void showToast(int contentId) {
        if (toast == null) {
            toast = Toast.makeText(ZApplication.getAppContext(), contentId, Toast.LENGTH_SHORT);
        } else {
            toast.setText(contentId);
        }
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }
}
