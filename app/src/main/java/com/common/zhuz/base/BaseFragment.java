package com.common.zhuz.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.common.zhuz.widget.LoadingDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zhuzhen on 2017/11/2.
 */

public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements BaseView {

    private View view;
    public Context mContext;
    public LayoutInflater mInflater;
    private boolean init;
    private Unbinder unbinder;
    private LoadingDialog loadingDialog;
    public P mFragmentPresenter;

    public BaseFragment() {
        super();
    }

    public BaseFragment(Bundle bd) {
        super();
        this.setArguments(bd);
    }
    protected abstract int getFragmentLayoutId();
    // 加载数据
    abstract protected void initData(Bundle savedInstanceState);
    // 释放资源
    abstract protected void release();
    //加载P
    protected abstract P loadMPresenter();

    abstract protected void initListener();
    abstract protected void initView();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentPresenter = loadMPresenter();
        if (mFragmentPresenter!=null){
            mFragmentPresenter.attachView(this);
        }
        if (loadingDialog==null){
            loadingDialog = new LoadingDialog(mContext);
            loadingDialog.setMessage("加载中");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mInflater = inflater;
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (null != parent) {
                parent.removeView(view);
            }
        } else {
            view = inflater.inflate(getFragmentLayoutId(),null);
        }
        if (view.getLayoutParams()==null){
            view.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        }
        unbinder=ButterKnife.bind(this,view);
        initView();
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData(savedInstanceState);
        initListener();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder!=null){
            unbinder.unbind();
        }
        if (mFragmentPresenter!=null){
            mFragmentPresenter.detachView();
        }
        release();
    }
    @Override
    public void onDetach() {
        super.onDetach();

    }

    /**
     * loading显示与隐藏
     *
     * @param is_show
     */
    public void setShowLoading(boolean is_show) {
        if (is_show) {
            if (loadingDialog!=null){
                loadingDialog.show();
            }
        } else {
            if (loadingDialog!=null){
                loadingDialog.dismiss();
            }
        }
    }
}
