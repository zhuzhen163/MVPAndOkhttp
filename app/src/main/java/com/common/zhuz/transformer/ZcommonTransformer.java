package com.common.zhuz.transformer;


import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * zhuzhen
 * 普通转换器
 * 切换线程  请求在io线程 主线程处理
 */

public class ZcommonTransformer<T> implements Observable.Transformer<Object,T> {
    @Override
    public Observable<T> call(Observable<Object> tansFormerObservable) {
        return tansFormerObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(ZerrorTransformer.<T>getInstance());
    }
}
