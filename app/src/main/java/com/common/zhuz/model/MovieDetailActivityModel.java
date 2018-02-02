package com.common.zhuz.model;

import com.common.zhuz.application.ZApplication;
import com.common.zhuz.entity.MovieDetailBean;
import com.common.zhuz.exception.ApiException;
import com.common.zhuz.http.Http;
import com.common.zhuz.http.UrlHelper;
import com.common.zhuz.subscriber.CommonSubscriber;
import com.common.zhuz.transformer.ZcommonTransformer;

/**
 * Created by zhuzhen
 */

public class MovieDetailActivityModel {

    public void getMovieDetail(String id, final CallBackCustomFragment callBackCustomFragment){
        Http.getAPI_DOUBAN(UrlHelper.API_DOUBAN).getMovieDetail(id)
                .compose(new ZcommonTransformer<MovieDetailBean>())
                .subscribe(new CommonSubscriber<MovieDetailBean>(ZApplication.getAppContext()) {
                    @Override
                    public void onNext(MovieDetailBean entity) {
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
        void successCallBack(MovieDetailBean entity);
        void failCallBack(String msg);
    }
}
