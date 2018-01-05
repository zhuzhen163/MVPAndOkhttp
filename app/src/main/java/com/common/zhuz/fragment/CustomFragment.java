package com.common.zhuz.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.cocosw.bottomsheet.BottomSheet;
import com.common.zhuz.R;
import com.common.zhuz.adapter.AndroidAdapter;
import com.common.zhuz.base.BaseFragment;
import com.common.zhuz.entity.GankIoDataBean;
import com.common.zhuz.http.Http;
import com.common.zhuz.module.xrecyclerview.XRecyclerView;
import com.common.zhuz.presenter.CustomFragmentPresenter;
import com.common.zhuz.tools.ToastUtil;
import com.common.zhuz.view.CustomFragmentView;

import butterknife.BindView;


/**
 * @author zhuzhen
 * 干货
 */
public class CustomFragment extends BaseFragment<CustomFragmentPresenter> implements CustomFragmentView{

    @BindView(R.id.xrv_custom)
    XRecyclerView xrv_custom;
    @BindView(R.id.fab_button)
    FloatingActionButton fab_button;
    private String mType = "all";
    private int mPage = 1;
    private AndroidAdapter mAndroidAdapter;
    private View mHeaderView;
    private TextView txName;

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_custom;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mFragmentPresenter.getCustom(mType, mPage, Http.per_page_more);
    }

    @Override
    protected void release() {

    }

    @Override
    protected CustomFragmentPresenter loadMPresenter() {
        return new CustomFragmentPresenter();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {
        initRecyclerView();
    }


    private void initRecyclerView() {
        // 禁止下拉刷新
        xrv_custom.setPullRefreshEnabled(false);
        // 去掉刷新头
        xrv_custom.clearHeader();
        mAndroidAdapter = new AndroidAdapter(mContext);
        xrv_custom.setLayoutManager(new LinearLayoutManager(getActivity()));
        xrv_custom.setAdapter(mAndroidAdapter);
        xrv_custom.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onLoadMore() {
                mPage++;
                mFragmentPresenter.getCustom(mType, mPage, Http.per_page_more);
            }
        });
    }

    /**
     * 设置adapter
     */
    private void setAdapter(GankIoDataBean mCustomBean) {
        if (mHeaderView == null) {
            mHeaderView = View.inflate(getContext(), R.layout.header_item_gank_custom, null);
            xrv_custom.addHeaderView(mHeaderView);
        }
        initHeader(mHeaderView);

        mAndroidAdapter.clear();
        mAndroidAdapter.setAllType(true);
        mAndroidAdapter.addAll(mCustomBean.getResults());
        mAndroidAdapter.notifyDataSetChanged();

    }

    private void initHeader(View mHeaderView) {
        txName = (TextView) mHeaderView.findViewById(R.id.tx_name);
        txName.setText("全部");
        View view = mHeaderView.findViewById(R.id.ll_choose_catalogue);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                new BottomSheet.Builder(mContext, R.style.BottomSheet_StyleDialog).title("选择分类").sheet(R.menu.gank_bottomsheet).listener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case R.id.gank_all:
                                if (isOtherType("全部")) {
                                    txName.setText("全部");
                                    mType = "all";// 全部传 all
                                    mPage = 1;
                                    mAndroidAdapter.clear();
                                    mFragmentPresenter.getCustom(mType, mPage, Http.per_page_more);
                                }
                                break;
                            case R.id.gank_ios:
                                if (isOtherType("IOS")) {
                                    txName.setText("IOS");
                                    mType = "iOS";// 这里有严格大小写
                                    mPage = 1;
                                    mAndroidAdapter.clear();
                                    showLoading();
                                    mFragmentPresenter.getCustom(mType, mPage, Http.per_page_more);
                                }
                                break;
                            case R.id.gank_qian:
                                if (isOtherType("前端")) {
                                    changeContent(txName, "前端");
                                }
                                break;
                            case R.id.gank_app:
                                if (isOtherType("App")) {
                                    changeContent(txName, "App");
                                }
                                break;
                            case R.id.gank_movie:
                                if (isOtherType("休息视频")) {
                                    changeContent(txName, "休息视频");
                                }
                                break;
                            case R.id.gank_resouce:
                                if (isOtherType("拓展资源")) {
                                    changeContent(txName, "拓展资源");
                                }
                                break;
                            default:
                                break;
                        }
                    }
                }).show();

            }
        });
    }

    private void changeContent(TextView textView, String content) {
        textView.setText(content);
        mType = content;
        mPage = 1;
        mAndroidAdapter.clear();
        showLoading();
        mFragmentPresenter.getCustom(mType, mPage, Http.per_page_more);
    }

    private boolean isOtherType(String selectType) {
        if (txName.getText().toString().equals(selectType)) {
            ToastUtil.showToast("当前已经是" + selectType + "分类");
            return false;
        } else {
            // 重置XRecyclerView状态，解决 如出现刷新到底无内容再切换其他类别后，无法上拉加载的情况
            xrv_custom.reset();
            return true;
        }
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
                xrv_custom.refreshComplete();
                mAndroidAdapter.addAll(entity.getResults());
                mAndroidAdapter.notifyDataSetChanged();
            } else {
                xrv_custom.noMoreLoading();
            }
        }
    }

    @Override
    public void failCallBack(String msg) {
        xrv_custom.refreshComplete();
        if (mPage > 1) {
            mPage--;
        }
    }
}
