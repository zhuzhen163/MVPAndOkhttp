package com.common.zhuz.activity;

import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.common.zhuz.R;
import com.common.zhuz.adapter.DoubanTopAdapter;
import com.common.zhuz.base.BaseActivity;
import com.common.zhuz.entity.HotMovieBean;
import com.common.zhuz.module.xrecyclerview.XRecyclerView;
import com.common.zhuz.presenter.DoubanTopActivityPresenter;
import com.common.zhuz.tools.SwitchActivityManager;
import com.common.zhuz.view.DoubanTopActivityView;

import butterknife.BindView;

/**
 * Created by zhuzhen
 */
public class DoubanTopActivity extends BaseActivity<DoubanTopActivityPresenter> implements DoubanTopActivityView{

    @BindView(R.id.xrv_top)
    XRecyclerView xrv_top;
    private DoubanTopAdapter mDouBanTopAdapter;
    private int mStart = 0;
    private int mCount = 21;

    @Override
    protected DoubanTopActivityPresenter loadPresenter() {
        return new DoubanTopActivityPresenter();
    }

    @Override
    protected void initData() {
        mPresenter.getMovieTop250(mStart,mCount);
    }

    @Override
    protected void initListener() {
        xrv_top.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                mStart += mCount;
                mPresenter.getMovieTop250(mStart,mCount);
            }
        });
        setBaseBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwitchActivityManager.exitActivity(DoubanTopActivity.this);
            }
        });
    }

    @Override
    protected void initView() {
        setTitleName("豆瓣电影Top250");
        mDouBanTopAdapter = new DoubanTopAdapter(DoubanTopActivity.this);
        //构造器中，第一个参数表示列数或者行数，第二个参数表示滑动方向,瀑布流
        xrv_top.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        xrv_top.setAdapter(mDouBanTopAdapter);
        xrv_top.setPullRefreshEnabled(false);
        xrv_top.setLoadingMoreEnabled(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_douban_top;
    }

    @Override
    protected void otherViewClick(View view) {

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
    public void successCallBack(HotMovieBean hotMovieBean) {
        if (mStart == 0) {
            if (hotMovieBean != null && hotMovieBean.getSubjects() != null && hotMovieBean.getSubjects().size() > 0) {
                mDouBanTopAdapter.clear();
                mDouBanTopAdapter.addAll(hotMovieBean.getSubjects());
                mDouBanTopAdapter.notifyDataSetChanged();
            } else {
                xrv_top.setVisibility(View.GONE);
            }
        } else {
            if (hotMovieBean != null && hotMovieBean.getSubjects() != null && hotMovieBean.getSubjects().size() > 0) {
                xrv_top.refreshComplete();
                mDouBanTopAdapter.addAll(hotMovieBean.getSubjects());
                mDouBanTopAdapter.notifyDataSetChanged();
            } else {
                xrv_top.noMoreLoading();
            }
        }
    }

    @Override
    public void failCallBack(String msg) {
        xrv_top.refreshComplete();
    }
}
