package com.common.zhuz.transformer;


import com.common.zhuz.base.BaseHttpResult;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * zhuzhen
 * 普通转换器
 * 切换线程  请求在io线程 主线程处理
 * 标准数据格式
 * {code：200，message："失败或者错误"，data：{具体数据}}
 */

public class CommonTransformer<T> implements Observable.Transformer<BaseHttpResult<T>,T> {
    @Override
    public Observable<T> call(Observable<BaseHttpResult<T>> tansFormerObservable) {
        return tansFormerObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(ErrorTransformer.<T>getInstance());
    }
}
