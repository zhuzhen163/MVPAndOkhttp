package com.common.zhuz.subscriber;

import android.content.Context;

import com.common.zhuz.base.BaseSubscriber;
import com.common.zhuz.exception.ApiException;
import com.common.zhuz.tools.LogUtil;
import com.common.zhuz.tools.NetworkUtil;


/**
 * Created by zhuzhen
 */

public abstract  class CommonSubscriber<T> extends BaseSubscriber<T> {
    private Context context;

    public CommonSubscriber(Context mcontext) {
        this.context = mcontext;
    }

    private static final String TAG = "CommonSubscriber";
    @Override
    protected void onError(ApiException e) {
        LogUtil.i(TAG, "错误信息为 " + "code:" + e.code + "   message:" + e.message);
    }

    @Override
    public void onCompleted() {
        LogUtil.i(TAG, "成功了");
    }

    @Override
    public void onStart() {
        if (!NetworkUtil.isNetworkConnected(context)) {
            LogUtil.i(TAG, "网络不可用");
        } else {
            LogUtil.i(TAG, "网络可用");
        }
    }
}
