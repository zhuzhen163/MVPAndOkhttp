package com.common.zhuz.model;

import com.common.zhuz.application.ZApplication;
import com.common.zhuz.entity.GankIoDataBean;
import com.common.zhuz.exception.ApiException;
import com.common.zhuz.http.Http;
import com.common.zhuz.http.UrlHelper;
import com.common.zhuz.subscriber.CommonSubscriber;
import com.common.zhuz.transformer.ZcommonTransformer;

/**
 * Created by zhuzhen on 2017/11/2.
 */

public class CustomFragmentModel {

    public void getCustom(String id, int page, int per_page, final CallBackCustomFragment callBackCustomFragment){
        Http.getHttpService(UrlHelper.API_GANKIO).getCustom(id,page,per_page)
                .compose(new ZcommonTransformer<GankIoDataBean>())
                .subscribe(new CommonSubscriber<GankIoDataBean>(ZApplication.getAppContext()) {
                    @Override
                    public void onNext(GankIoDataBean entity) {
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
        void successCallBack(GankIoDataBean entity);
        void failCallBack(String msg);
    }
}
