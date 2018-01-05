package com.common.zhuz.fragment;

import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.common.zhuz.R;
import com.common.zhuz.adapter.WelfareAdapter;
import com.common.zhuz.base.BaseFragment;
import com.common.zhuz.entity.GankIoDataBean;
import com.common.zhuz.http.Http;
import com.common.zhuz.module.xrecyclerview.XRecyclerView;
import com.common.zhuz.presenter.WelfareFragmentPresenter;
import com.common.zhuz.view.WelfareFragmentView;

import butterknife.BindView;

/**
 * 福利
 * @author zhuzhen
 */
public class WelfareFragment extends BaseFragment<WelfareFragmentPresenter> implements WelfareFragmentView{

    private static final String TAG = "WelfareFragment";
    @BindView(R.id.xrv_welfare)
    XRecyclerView xrv_welfare;
    private int mPage = 1;
    private WelfareAdapter mWelfareAdapter;


    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_welfare;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mFragmentPresenter.getCustom("福利", mPage, Http.per_page_more);
    }

    @Override
    protected void release() {

    }

    @Override
    protected WelfareFragmentPresenter loadMPresenter() {
        return new WelfareFragmentPresenter();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {
        initRecycleView();
        mWelfareAdapter = new WelfareAdapter(mContext);
        //构造器中，第一个参数表示列数或者行数，第二个参数表示滑动方向,瀑布流
        xrv_welfare.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        xrv_welfare.setAdapter(mWelfareAdapter);
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {

    }

    private void initRecycleView() {
        xrv_welfare.setPullRefreshEnabled(false);
        xrv_welfare.clearHeader();
        xrv_welfare.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                mPage++;
                mFragmentPresenter.getCustom("福利", mPage, Http.per_page_more);
            }
        });
    }

    private void setAdapter(GankIoDataBean gankIoDataBean) {
        mWelfareAdapter.addAll(gankIoDataBean.getResults());
        mWelfareAdapter.notifyDataSetChanged();
    }

    @Override
    public void successCallBack(GankIoDataBean entity) {
        if (mPage == 1) {
            if (entity != null && entity.getResults() != null && entity.getResults().size() > 0) {
                setAdapter(entity);
            }
        } else {
            if (entity != null && entity.getResults() != null && entity.getResults().size() > 0) {
                xrv_welfare.refreshComplete();
                setAdapter(entity);
            } else {
                xrv_welfare.noMoreLoading();
            }
        }
    }

    @Override
    public void failCallBack(String msg) {
        xrv_welfare.refreshComplete();
        if (mPage > 1) {
            mPage--;
        }
    }
}
