package com.common.zhuz.base;

import com.common.zhuz.exception.ApiException;

import rx.Subscriber;

/**
 * 基类观察者
 */

public abstract  class BaseSubscriber<T> extends Subscriber<T> {
    @Override
    public void onError(Throwable e) {
        if (e!=null){
            if (e instanceof ApiException){
                ApiException apiException = (ApiException) e;
                if (apiException!=null){
                    onError(apiException);
                }
            }
        }
    }
    /**
     * @param e 错误的一个回调
     */
    protected abstract void onError(ApiException e);
}
