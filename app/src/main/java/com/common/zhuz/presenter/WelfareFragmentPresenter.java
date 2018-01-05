package com.common.zhuz.presenter;

import com.common.zhuz.base.BasePresenter;
import com.common.zhuz.entity.GankIoDataBean;
import com.common.zhuz.fragment.WelfareFragment;
import com.common.zhuz.model.WelfareFragmentModel;


/**
 * Created by zhuzhen on 2017/11/2.
 */

public class WelfareFragmentPresenter extends BasePresenter <WelfareFragmentModel,WelfareFragment>{

    public void getCustom(String id, int page, int per_page){
        if (getView() != null){
            getView().showLoading();
        }
        getModel().getCustom(id,page,per_page,new WelfareFragmentModel.CallBackCustomFragment(){

            @Override
            public void successCallBack(GankIoDataBean entity) {
                if (getView() != null){
                    getView().hideLoading();
                    getView().successCallBack(entity);
                }
            }

            @Override
            public void failCallBack(String msg) {
                if (getView() != null){
                    getView().hideLoading();
                    getView().failCallBack(msg);
                }
            }
        });
    }

    @Override
    public WelfareFragmentModel loadModel() {
        return new WelfareFragmentModel();
    }
}
