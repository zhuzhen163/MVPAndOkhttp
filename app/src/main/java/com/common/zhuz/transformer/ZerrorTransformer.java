package com.common.zhuz.transformer;


import com.common.zhuz.exception.ErrorType;
import com.common.zhuz.exception.ExceptionEngine;
import com.common.zhuz.exception.ServerException;

import rx.Observable;
import rx.functions.Func1;

/**
 *zhuzhen
 * 转换器
 */

public class ZerrorTransformer<T> implements Observable.Transformer<Object, T>  {

    private static ZerrorTransformer errorTransformer = null;
    private static final String TAG = "ErrorTransformer";

    @Override
    public Observable<T> call(Observable<Object> responseObservable) {
        return responseObservable.map(new Func1<Object, T>() {
            @Override
            public T call(Object httpResult) {
                if (httpResult == null)
                    throw new ServerException(ErrorType.EMPTY_BEAN, "解析对象为空");
                return (T) httpResult;
            }
        }).onErrorResumeNext(new Func1<Throwable, Observable<? extends T>>() {
            @Override
            public Observable<? extends T> call(Throwable throwable) {
                //ExceptionEngine为处理异常的驱动器
                throwable.printStackTrace();
                return Observable.error(ExceptionEngine.handleException(throwable));
            }
        });
    }

    /**
     * @return 线程安全, 双层校验
     */
    public static <T> ZerrorTransformer<T> getInstance() {
        if (errorTransformer == null) {
            synchronized (ZerrorTransformer.class) {
                if (errorTransformer == null) {
                    errorTransformer = new ZerrorTransformer<>();
                }
            }
        }
        return errorTransformer;
    }
}
