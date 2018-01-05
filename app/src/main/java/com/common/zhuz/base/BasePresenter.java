package com.common.zhuz.base;

import java.lang.ref.WeakReference;


/**
 * Created by zhuzhen on 2017/11/2.
 */

public abstract class BasePresenter<M ,V extends BaseView>  implements IBasePresenter {
    //弱引用防止内存溢出
    private WeakReference actReference;
    protected V iView;
    protected M iModel;
    public M getModel() {
        //使用前先进行初始化
        iModel = loadModel();
        return iModel;
    }
    /**
     * 绑定View(即Activity)
     * @param iView
     */
    @Override
    public void attachView(BaseView iView) {
        actReference = new WeakReference(iView);
    }
    /**
     * 清除绑定
     */
    @Override
    public void detachView() {
        if (actReference != null) {
            actReference.clear();
            actReference = null;
        }
    }
    /**
     * 获取View
     * @return
     */
    @Override
    public V getView() {
        return (V) actReference.get();
    }
    public abstract M loadModel();
}
