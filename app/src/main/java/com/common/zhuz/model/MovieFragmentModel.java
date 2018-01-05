package com.common.zhuz.model;

import com.common.zhuz.application.ZApplication;
import com.common.zhuz.entity.HotMovieBean;
import com.common.zhuz.exception.ApiException;
import com.common.zhuz.http.Http;
import com.common.zhuz.http.UrlHelper;
import com.common.zhuz.subscriber.CommonSubscriber;
import com.common.zhuz.transformer.ZcommonTransformer;

/**
 * Created by zhuzhen
 */

public class MovieFragmentModel {

    public void getMovie(final CallBackCustomFragment callBackCustomFragment){
        Http.getAPI_DOUBAN(UrlHelper.API_DOUBAN).getHotMovie()
                .compose(new ZcommonTransformer<HotMovieBean>())
                .subscribe(new CommonSubscriber<HotMovieBean>(ZApplication.getAppContext()) {
                    @Override
                    public void onNext(HotMovieBean entity) {
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
        void successCallBack(HotMovieBean entity);
        void failCallBack(String msg);
    }
}
