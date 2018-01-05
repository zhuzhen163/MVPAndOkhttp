package com.common.zhuz.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.zhuz.R;
import com.common.zhuz.activity.MainActivity;
import com.common.zhuz.entity.BooksBean;
import com.common.zhuz.tools.ImgLoadUtil;
import com.common.zhuz.tools.PerfectClickListener;

import java.util.ArrayList;
import java.util.List;


public class BookAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private MainActivity context;

    private int status = 1;
    public static final int LOAD_MORE = 0;
    public static final int LOAD_PULL_TO = 1;
    public static final int LOAD_NONE = 2;
    private static final int LOAD_END = 3;
    private static final int TYPE_TOP = -1;

    private static final int TYPE_FOOTER_BOOK = -2;
    private static final int TYPE_HEADER_BOOK = -3;
    private static final int TYPE_CONTENT_BOOK = -4;
    private List<BooksBean> list;
    private View viewHead,viewFooter,viewItem;

    public BookAdapter(Context context) {
        this.context = (MainActivity) context;
        list = new ArrayList<>();

    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0) {
            return TYPE_HEADER_BOOK;
        } else if (position + 1 == getItemCount()) {
            return TYPE_FOOTER_BOOK;
        } else {
            return TYPE_CONTENT_BOOK ;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_HEADER_BOOK:
                viewHead = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_item_book, parent, false);
                return new HeaderViewHolder(viewHead);
            case TYPE_FOOTER_BOOK:
                viewFooter = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_item_book, parent, false);
                return new FooterViewHolder(viewFooter);
            default:
                viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
                return new BookViewHolder(viewItem);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
            headerViewHolder.bindItem();
        } else if (holder instanceof FooterViewHolder) {
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            footerViewHolder.bindItem();
        } else if (holder instanceof BookViewHolder) {
            BookViewHolder bookViewHolder = (BookViewHolder) holder;
            if (list != null && list.size() > 0) {
                // 内容从"1"开始
//                DebugUtil.error("------position: "+position);
                bookViewHolder.bindItem(list.get(position - 1), position-1);
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size() + 2;
    }

    /**
     * 处理 GridLayoutManager 添加头尾布局占满屏幕宽的情况
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return (isHeader(position) || isFooter(position)) ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    /**
     * 处理 StaggeredGridLayoutManager 添加头尾布局占满屏幕宽的情况
     */
    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null
                && lp instanceof StaggeredGridLayoutManager.LayoutParams
                && (isHeader(holder.getLayoutPosition()) || isFooter(holder.getLayoutPosition()))) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            p.setFullSpan(true);
        }
    }

    /**
     * 这里规定 position = 0 时
     * 就为头布局，设置为占满整屏幕宽
     */
    private boolean isHeader(int position) {
        return position >= 0 && position < 1;
    }

    /**
     * 这里规定 position =  getItemCount() - 1时
     * 就为尾布局，设置为占满整屏幕宽
     * getItemCount() 改了 ，这里就不用改
     */
    private boolean isFooter(int position) {
        return position < getItemCount() && position >= getItemCount() - 1;
    }

    /**
     * footer view
     */
    private class FooterViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout rl_more;
        ProgressBar progress;
        TextView tv_load_prompt;
        FooterViewHolder(View itemView) {
            super(itemView);
            rl_more = (RelativeLayout) viewFooter.findViewById(R.id.rl_more);
            progress = (ProgressBar) viewFooter.findViewById(R.id.progress);
            tv_load_prompt = (TextView) viewFooter.findViewById(R.id.tv_load_prompt);
            rl_more.setGravity(Gravity.CENTER);
//            LinearLayoutCompat.LayoutParams params = new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtils.dipToPx(context, 40));
//            itemView.setLayoutParams(params);
        }

        private void bindItem() {
            switch (status) {
                case LOAD_MORE:
                    progress.setVisibility(View.VISIBLE);
                    tv_load_prompt.setText("正在加载...");
                    itemView.setVisibility(View.VISIBLE);
                    break;
                case LOAD_PULL_TO:
                    progress.setVisibility(View.GONE);
                    tv_load_prompt.setText("上拉加载更多");
                    itemView.setVisibility(View.VISIBLE);
                    break;
                case LOAD_NONE:
                    progress.setVisibility(View.GONE);
                    tv_load_prompt.setText("没有更多内容了");
                    break;
                case LOAD_END:
                    itemView.setVisibility(View.GONE);
                default:
                    break;
            }
        }
    }


    private class HeaderViewHolder extends RecyclerView.ViewHolder {


        HeaderViewHolder(View view) {
            super(view);
        }

        private void bindItem() {
        }
    }

    public void updateLoadStatus(int status) {
        this.status = status;
        notifyDataSetChanged();
    }

    public int getLoadStatus(){
        return this.status;
    }


    private class BookViewHolder extends RecyclerView.ViewHolder {

        LinearLayout ll_item_top;
        ImageView iv_top_photo;
        TextView tv_name,tv_rate;

        BookViewHolder(View view) {
            super(view);
            ll_item_top = (LinearLayout) itemView.findViewById(R.id.ll_item_top);
            iv_top_photo = (ImageView) itemView.findViewById(R.id.iv_top_photo);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_rate = (TextView) itemView.findViewById(R.id.tv_rate);
        }

        private void bindItem(final BooksBean book, int position) {
            ImgLoadUtil.displayEspImage(book.getImages().getLarge(), iv_top_photo, 2);
            tv_name.setText(book.getTitle());
            tv_rate.setText(context.getString(R.string.string_rating)+book.getRating().getAverage());
            ll_item_top.setOnClickListener(new PerfectClickListener() {
                @Override
                protected void onNoDoubleClick(View v) {
//                    BookDetailActivity.start(context,book,mBindBook.ivTopPhoto);
                }
            });

//            ViewGroup.LayoutParams params=iVFilm.getLayoutParams();
//            int width= ScreenUtils.getScreenWidthDp(context);
//            int ivWidth=(width-ScreenUtils.dipToPx(context,80))/3;
//            params.width=ivWidth;
//            double height=(420.0/300.0)*ivWidth;
//            params.height=(int)height;
//            iVFilm.setLayoutParams(params);
        }
    }

    public List<BooksBean> getList() {
        return list;
    }

    public void setList(List<BooksBean> list) {
        this.list.clear();
        this.list = list;
    }

    public void addAll(List<BooksBean> list) {
        this.list.addAll(list);
    }

}
