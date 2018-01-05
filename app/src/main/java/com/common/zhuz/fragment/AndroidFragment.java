package com.common.zhuz.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.common.zhuz.R;
import com.common.zhuz.adapter.AndroidAdapter;
import com.common.zhuz.base.BaseFragment;
import com.common.zhuz.entity.GankIoDataBean;
import com.common.zhuz.http.Http;
import com.common.zhuz.module.xrecyclerview.XRecyclerView;
import com.common.zhuz.presenter.AndroidFragmentPresenter;
import com.common.zhuz.view.AndroidFragmentView;

import butterknife.BindView;

/**
 * 大安卓 fragment
 */
public class AndroidFragment extends BaseFragment<AndroidFragmentPresenter> implements AndroidFragmentView{

    private static final String TAG = "AndroidFragment";
    private static final String TYPE = "mType";
    private String mType = "Android";
    @BindView(R.id.xrv_android)
    XRecyclerView xrv_android;
    private int mPage = 1;
    private AndroidAdapter mAndroidAdapter;

    public static AndroidFragment newInstance(String type) {
        AndroidFragment fragment = new AndroidFragment();
        Bundle args = new Bundle();
        args.putString(TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_android;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mFragmentPresenter.getCustom(mType, mPage, Http.per_page_more);
    }

    @Override
    protected void release() {

    }

    @Override
    protected AndroidFragmentPresenter loadMPresenter() {
        return new AndroidFragmentPresenter();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {
        initRecyclerView();
    }

    private void initRecyclerView() {
        mAndroidAdapter = new AndroidAdapter(mContext);
        xrv_android.setLayoutManager(new LinearLayoutManager(getActivity()));
        xrv_android.setAdapter(mAndroidAdapter);
        xrv_android.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mPage = 1;
                mFragmentPresenter.getCustom(mType, mPage, Http.per_page_more);
            }

            @Override
            public void onLoadMore() {
                mPage++;
                mFragmentPresenter.getCustom(mType, mPage, Http.per_page_more);
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mType = getArguments().getString(TYPE);
        }
    }


    /**
     * 设置adapter
     */
    private void setAdapter(GankIoDataBean mAndroidBean) {
        mAndroidAdapter.clear();
        mAndroidAdapter.addAll(mAndroidBean.getResults());
        mAndroidAdapter.notifyDataSetChanged();
        xrv_android.refreshComplete();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void showLoading() {
        setShowLoading(true);
    }

    @Override
    public void hideLoading() {
        setShowLoading(false);
    }

    @Override
    public void successCallBack(GankIoDataBean entity) {
        if (mPage == 1) {
            if (entity != null && entity.getResults() != null && entity.getResults().size() > 0) {
                setAdapter(entity);
            }
        } else {
            if (entity != null && entity.getResults() != null && entity.getResults().size() > 0) {
                xrv_android.refreshComplete();
                mAndroidAdapter.addAll(entity.getResults());
                mAndroidAdapter.notifyDataSetChanged();
            } else {
                xrv_android.noMoreLoading();
            }
        }
    }

    @Override
    public void failCallBack(String msg) {
        xrv_android.refreshComplete();
        if (mPage > 1) {
            mPage--;
        }
    }
}
