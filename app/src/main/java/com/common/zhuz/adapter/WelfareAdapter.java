package com.common.zhuz.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.common.zhuz.R;
import com.common.zhuz.base.ListBaseAdapter;
import com.common.zhuz.base.SuperViewHolder;
import com.common.zhuz.entity.ResultBean;
import com.common.zhuz.tools.DensityUtil;
import com.common.zhuz.tools.ImgLoadUtil;


public class WelfareAdapter extends ListBaseAdapter<ResultBean> {

    ImageView iv_welfare;

    public WelfareAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_welfare;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        iv_welfare = holder.getView(R.id.iv_welfare);
        /**
         * 注意：DensityUtil.setViewMargin(itemView,true,5,3,5,0);
         * 如果这样使用，则每个item的左右边距是不一样的，
         * 这样item不能复用，所以下拉刷新成功后显示会闪一下
         * 换成每个item设置上下左右边距是一样的话，系统就会复用，就消除了图片不能复用 闪跳的情况
         */
        if (position % 2 == 0) {
            DensityUtil.setViewMargin(iv_welfare, false, 12, 6, 12, 0);
        } else {
            DensityUtil.setViewMargin(iv_welfare, false, 6, 12, 12, 0);
        }
        final ResultBean resultBean = mDataList.get(position);
        ImgLoadUtil.displayEspImage(resultBean.getUrl(), iv_welfare, 1);
        iv_welfare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    ArrayList<String> imageuri = new ArrayList<String>();
//                    imageuri.add(resultBean.getUrl());
//                    Bundle bundle = new Bundle();
//                    bundle.putInt("selet", 1);// 2,大图显示当前页数，1,头像，不显示页数
//                    bundle.putInt("code", 0);//第几张
//                    bundle.putStringArrayList("imageuri", imageuri);
//                    Intent intent = new Intent(v.getContext(), ViewBigImageActivity.class);
//                    intent.putExtras(bundle);
//                    v.getContext().startActivity(intent);
                }
            });
    }
}
