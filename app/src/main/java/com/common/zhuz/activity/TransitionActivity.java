package com.common.zhuz.activity;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.common.zhuz.R;
import com.common.zhuz.base.BaseActivity;
import com.common.zhuz.base.BasePresenter;
import com.common.zhuz.tools.CommonUtils;
import com.common.zhuz.tools.ConstantsImageUrl;

import java.util.Random;

import butterknife.BindView;

public class TransitionActivity extends BaseActivity {

    @BindView(R.id.iv_pic)
    ImageView iv_pic;
    @BindView(R.id.tv_jump)
    TextView tv_jump;
    private boolean animationEnd;
    private boolean isIn;

    @Override
    protected BasePresenter loadPresenter() {
        return null;
    }

    @Override
    protected void initData() {
        setBaseTitleState(View.GONE);
    }

    @Override
    protected void initListener() {
        tv_jump.setOnClickListener(this);
    }

    @Override
    protected void initView() {
        int i = new Random().nextInt(ConstantsImageUrl.TRANSITION_URLS.length);
        // 先显示默认图
        iv_pic.setImageDrawable(CommonUtils.getDrawable(R.drawable.img_transition_default));
        Glide.with(this)
                .load(ConstantsImageUrl.TRANSITION_URLS[i])
                .placeholder(R.drawable.img_transition_default)
                .error(R.drawable.img_transition_default)
                .into(iv_pic);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                toMainActivity();
            }
        }, 3500);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_transition;
    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()){
            case R.id.tv_jump:
                toMainActivity();
                break;
        }
    }

    /**
     * 实现监听跳转效果
     */
    private Animation.AnimationListener animationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationEnd(Animation animation) {
            animationEnd();
        }

        @Override
        public void onAnimationStart(Animation animation) {
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }
    };


    private void animationEnd() {
        synchronized (TransitionActivity.this) {
            if (!animationEnd) {
                animationEnd = true;
                iv_pic.clearAnimation();
                toMainActivity();
            }
        }
    }

    private void toMainActivity() {
        if (isIn) {
            return;
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
        finish();
        isIn = true;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
