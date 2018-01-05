package com.common.zhuz.model;

import com.common.zhuz.application.ZApplication;
import com.common.zhuz.entity.BookBean;
import com.common.zhuz.exception.ApiException;
import com.common.zhuz.http.Http;
import com.common.zhuz.http.UrlHelper;
import com.common.zhuz.subscriber.CommonSubscriber;
import com.common.zhuz.transformer.ZcommonTransformer;

/**
 * Created by zhuzhen on 2017/11/2.
 */

public class BookCustomFragmentModel {

    public void getBook(String tag, int start, int count, final CallBackCustomFragment callBackCustomFragment){
        Http.getAPI_DOUBAN(UrlHelper.API_DOUBAN).getBook(tag,start,count)
                .compose(new ZcommonTransformer<BookBean>())
                .subscribe(new CommonSubscriber<BookBean>(ZApplication.getAppContext()) {
                    @Override
                    public void onNext(BookBean entity) {
                        if (entity!=null){
                            callBackCustomFragment.successCallBack(entity);
                        }
                    }
                    @Override
                    public void onError(ApiException e) {
                        super.onError(e);
                        callBackCustomFragment.failCallBack(e.message);
                    }
                })
        ;

    }
    public interface CallBackCustomFragment{
        void successCallBack(BookBean entity);
        void failCallBack(String msg);
    }
}
