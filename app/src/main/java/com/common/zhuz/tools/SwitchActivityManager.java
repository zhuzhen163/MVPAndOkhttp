package com.common.zhuz.tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.common.zhuz.R;
import com.common.zhuz.activity.DoubanTopActivity;
import com.common.zhuz.activity.webview.WebViewActivity;

/**
 * Created by zhuzhen on 2017/11/2.
 * 管理activity
 */

public class SwitchActivityManager {

    /**
     * 打开网页:
     *
     * @param mContext 上下文
     * @param mUrl     要加载的网页url
     * @param mTitle   title
     */
    public static void loadUrl(Context mContext, String mUrl, String mTitle) {
        Intent intent = new Intent(mContext, WebViewActivity.class);
        intent.putExtra("mUrl", mUrl);
        intent.putExtra("mTitle", mTitle);
        mContext.startActivity(intent);
    }

    public static void startDoubanTopActivity(Context mContext){
        Intent intent = new Intent(mContext, DoubanTopActivity.class);
        mContext.startActivity(intent);
    }

    public static void exitActivity(Activity activity){
        activity.finish();
        activity.overridePendingTransition(R.anim.left_out_two, R.anim.left_in_two);
    }

}
