package com.common.zhuz.model;

import com.common.zhuz.application.ZApplication;
import com.common.zhuz.entity.RaidersDataEntity;
import com.common.zhuz.entity.VersionEntity;
import com.common.zhuz.exception.ApiException;
import com.common.zhuz.http.Http;
import com.common.zhuz.http.UrlHelper;
import com.common.zhuz.subscriber.CommonSubscriber;
import com.common.zhuz.transformer.CommonTransformer;

/**
 * Created by zhuzhen on 2017/11/2.
 */

public class MainModel {
    /**
     * get请求测试
     * @param fla_ff
     * @param versition
     * @param code
     * @param callBackMainActivity
     * @param platform
     */
    public void checkVersitionUpdate(String fla_ff, String versition, String code, final CallBackMainActivity callBackMainActivity, String platform){
        Http.getHttpService(UrlHelper.BASE_URL).checkVersition(code,fla_ff,versition,platform)
                .compose(new CommonTransformer<VersionEntity>())
                .subscribe(new CommonSubscriber<VersionEntity>(ZApplication.getAppContext()) {
                    @Override
                    public void onNext(VersionEntity entity) {
                        if (entity!=null){
                            callBackMainActivity.getVersitionEntity(entity);
                        }
                    }
                    @Override
                    public void onError(ApiException e) {
                        super.onError(e);
                        callBackMainActivity.failMainCallBack(e.message);
                    }
                })
        ;

    }
    public interface CallBackMainActivity{
        void getVersitionEntity(VersionEntity entity);
        void failMainCallBack(String msg);
    }

    /**
     * post请求测试
     */
    public void postRequestDemo(int page,int num,final PostInterface postInterface ){
        Http.getHttpService(UrlHelper.BASE_URL).postDemo(page,num)
                .compose(new CommonTransformer<RaidersDataEntity>())
                .subscribe(new CommonSubscriber<RaidersDataEntity>(ZApplication.getAppContext()) {
                    @Override
                    public void onNext(RaidersDataEntity raidersDataEntity) {
                        if (raidersDataEntity!=null){
                            postInterface.sucesspost(raidersDataEntity);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        super.onError(e);
                        postInterface.failpost(e.message);
                    }
                });
    }
    public interface PostInterface{
        void sucesspost(RaidersDataEntity entity);
        void failpost(String msg);
    }
}
