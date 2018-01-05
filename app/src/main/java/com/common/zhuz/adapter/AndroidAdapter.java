package com.common.zhuz.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.common.zhuz.R;
import com.common.zhuz.base.ListBaseAdapter;
import com.common.zhuz.base.SuperViewHolder;
import com.common.zhuz.entity.ResultBean;
import com.common.zhuz.tools.ImgLoadUtil;
import com.common.zhuz.tools.SwitchActivityManager;
import com.common.zhuz.tools.TimeUtil;


/**
 * Created by zhuzhen
 */

public class AndroidAdapter extends ListBaseAdapter<ResultBean> {

    private boolean isAll = false;
    ImageView iv_all_welfare;
    LinearLayout ll_welfare_other,ll_all;
    TextView tv_android_des,tv_android_who,tv_content_type,tv_android_time;
    ImageView iv_android_pic;

    public AndroidAdapter(Context context) {
        super(context);
    }

    public void setAllType(boolean isAll) {
        this.isAll = isAll;
    }


    @Override
    public int getLayoutId() {
        return R.layout.item_android;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        iv_all_welfare = holder.getView(R.id.iv_all_welfare);
        ll_welfare_other = holder.getView(R.id.ll_welfare_other);
        tv_android_des = holder.getView(R.id.tv_android_des);
        iv_android_pic = holder.getView(R.id.iv_android_pic);
        tv_android_who = holder.getView(R.id.tv_android_who);
        tv_content_type = holder.getView(R.id.tv_content_type);
        tv_android_time = holder.getView(R.id.tv_android_time);
        ll_all = holder.getView(R.id.ll_all);
        final ResultBean resultBean = mDataList.get(position);
        if (isAll && "福利".equals(resultBean.getType())) {
            iv_all_welfare.setVisibility(View.VISIBLE);
            ll_welfare_other.setVisibility(View.GONE);
            ImgLoadUtil.displayEspImage(resultBean.getUrl(), iv_all_welfare, 1);
        } else {
            iv_all_welfare.setVisibility(View.GONE);
            ll_welfare_other.setVisibility(View.VISIBLE);
        }
        tv_android_des.setText(resultBean.getDesc());
        tv_android_who.setText(resultBean.getWho());
        tv_android_time.setText(TimeUtil.getTranslateTime(resultBean.getPublishedAt()));
        if (isAll) {
            tv_content_type.setVisibility(View.VISIBLE);
            tv_content_type.setText(" · " + resultBean.getType());
        } else {
            tv_content_type.setVisibility(View.GONE);

        }

        // 显示gif图片会很耗内存
        if (resultBean.getImages() != null && resultBean.getImages().size() > 0  && !TextUtils.isEmpty(resultBean.getImages().get(0))) {
            iv_android_pic.setVisibility(View.VISIBLE);
            ImgLoadUtil.displayGif(resultBean.getImages().get(0), iv_android_pic);
        } else {
            iv_android_pic.setVisibility(View.GONE);
        }

        ll_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwitchActivityManager.loadUrl(mContext,resultBean.getUrl(),"加载中...");
            }
        });
    }
}
