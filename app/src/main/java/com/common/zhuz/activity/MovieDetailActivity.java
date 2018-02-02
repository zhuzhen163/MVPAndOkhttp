package com.common.zhuz.activity;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.common.zhuz.R;
import com.common.zhuz.adapter.MovieDetailAdapter;
import com.common.zhuz.base.BaseActivity;
import com.common.zhuz.entity.MovieDetailBean;
import com.common.zhuz.entity.MoviesBean;
import com.common.zhuz.module.xrecyclerview.XRecyclerView;
import com.common.zhuz.presenter.MovieDetailActivityPresenter;
import com.common.zhuz.tools.ImgLoadUtil;
import com.common.zhuz.tools.StringUtils;
import com.common.zhuz.tools.SwitchActivityManager;
import com.common.zhuz.tools.ThreadUtil;
import com.common.zhuz.view.MovieDetailActivityView;

import butterknife.BindView;
import jp.wasabeef.glide.transformations.BlurTransformation;

public class MovieDetailActivity extends BaseActivity<MovieDetailActivityPresenter> implements MovieDetailActivityView {

    @BindView(R.id.xrv_cast)
    XRecyclerView xrv_cast;
    @BindView(R.id.img_item_bg)
    ImageView img_item_bg;
    @BindView(R.id.iv_one_photo)
    ImageView iv_one_photo;
    @BindView(R.id.tv_one_rating_rate)
    TextView tv_one_rating_rate;
    @BindView(R.id.tv_one_rating_number)
    TextView tv_one_rating_number;
    @BindView(R.id.tv_one_directors)
    TextView tv_one_directors;
    @BindView(R.id.tv_one_casts)
    TextView tv_one_casts;
    @BindView(R.id.tv_one_genres)
    TextView tv_one_genres;
    @BindView(R.id.tv_one_day)
    TextView tv_one_day;
    @BindView(R.id.tv_one_city)
    TextView tv_one_city;
    @BindView(R.id.tv_one_title)
    TextView tv_one_title;
    @BindView(R.id.tv_summary)
    TextView tv_summary;

    private MoviesBean moviesBean;
    private String mMoreUrl;
    private String mMovieName;
    private String detailId;


    @Override
    protected MovieDetailActivityPresenter loadPresenter() {
        return new MovieDetailActivityPresenter();
    }

    @Override
    protected void initData() {
        mPresenter.getMovieDetail(detailId);
    }

    @Override
    protected void initListener() {
        setBaseBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwitchActivityManager.exitActivity(MovieDetailActivity.this);
            }
        });
    }

    @Override
    protected void initView() {
        moviesBean =(MoviesBean) getIntent().getSerializableExtra("moviesBean");
        detailId = moviesBean.getId();
        setImgHeaderBg(moviesBean.getImages().getLarge());
//        ImgLoadUtil.displayEspImage(moviesBean.getImages().getLarge(), img_item_bg, 0);
        ImgLoadUtil.displayEspImage(moviesBean.getImages().getLarge(), iv_one_photo, 0);
        tv_one_rating_rate.setText(moviesBean.getRating().getAverage()+"");
        tv_one_rating_number.setText(moviesBean.getCollect_count()+"");
        tv_one_directors.setText(StringUtils.formatName(moviesBean.getDirectors()));
        tv_one_casts.setText(StringUtils.formatName(moviesBean.getCasts()));
        tv_one_genres.setText(StringUtils.formatGenres(moviesBean.getGenres()));
        setTitleName(moviesBean.getTitle());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_movie_detail;
    }

    @Override
    protected void otherViewClick(View view) {

    }

    /**
     * 异步线程转换数据
     */
    private void transformData(final MovieDetailBean movieDetailBean) {
        ThreadUtil.post(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < movieDetailBean.getDirectors().size(); i++) {
                    movieDetailBean.getDirectors().get(i).setType("导演");
                }
                for (int i = 0; i < movieDetailBean.getCasts().size(); i++) {
                    movieDetailBean.getCasts().get(i).setType("演员");
                }
            }
        });
        setAdapter(movieDetailBean);
    }

    /**
     * 设置导演&演员adapter
     */
    private void setAdapter(MovieDetailBean movieDetailBean) {
        tv_one_title.setText(StringUtils.formatGenres(movieDetailBean.getAka()));
        xrv_cast.setVisibility(View.VISIBLE);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(MovieDetailActivity.this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xrv_cast.setLayoutManager(mLayoutManager);
        xrv_cast.setPullRefreshEnabled(false);
        xrv_cast.setLoadingMoreEnabled(false);
        // 需加，不然滑动不流畅
        xrv_cast.setNestedScrollingEnabled(false);
        xrv_cast.setHasFixedSize(false);

        MovieDetailAdapter mAdapter = new MovieDetailAdapter(mContext);
        mAdapter.addAll(movieDetailBean.getDirectors());
        mAdapter.addAll(movieDetailBean.getCasts());
        xrv_cast.setAdapter(mAdapter);
    }

    @Override
    public void successCallBack(MovieDetailBean movieDetailBean) {
        // 上映日期
        tv_one_day.setText(String.format("上映日期：%s", movieDetailBean.getYear()));
        // 制片国家
        tv_one_city.setText(String.format("制片国家/地区：%s", StringUtils.formatGenres(movieDetailBean.getCountries())));

        mMoreUrl = movieDetailBean.getAlt();
        mMovieName = movieDetailBean.getTitle();

        transformData(movieDetailBean);
    }

    @Override
    public void failCallBack(String msg) {

    }

    @Override
    public void showLoading() {
        setShowLoading(true);
    }

    @Override
    public void hideLoading() {
        setShowLoading(false);
    }

    private void setImgHeaderBg(String imgUrl) {
        if (!TextUtils.isEmpty(imgUrl)) {

            // 高斯模糊背景 原来 参数：12,5  23,4
            Glide.with(this).load(imgUrl)
                    .error(R.drawable.stackblur_default)
                    .bitmapTransform(new BlurTransformation(this, 23, 4)).listener(new RequestListener<String, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                    return false;
                }

                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                    img_item_bg.setBackgroundColor(Color.TRANSPARENT);
//                    img_item_bg.setImageAlpha(0);
//                    img_item_bg.setVisibility(View.VISIBLE);
                    return false;
                }
            }).into(img_item_bg);
        }
    }
}
