package com.example.amoy_coupon.ui.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.amoy_coupon.R;
import com.example.amoy_coupon.model.domain.HomePagerContent;
import com.example.amoy_coupon.utils.UrlUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomePagerContentAdapter extends RecyclerView.Adapter<HomePagerContentAdapter.InnerHolder> {

    List<HomePagerContent.DataBean> mData = new ArrayList<>();

    @NonNull
    @Override
    public HomePagerContentAdapter.InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_pager_content, parent, false);
        ButterKnife.bind(this,view);
        return new InnerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomePagerContentAdapter.InnerHolder holder, int position) {
        HomePagerContent.DataBean dataBean = mData.get(position);
        //设置数据
        holder.setData(dataBean);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(List<HomePagerContent.DataBean> content) {
        mData.clear();
        mData.addAll(content);
        notifyDataSetChanged();
    }

    public void addData(List<HomePagerContent.DataBean> contents) {
        int oldSize = mData.size();
        mData.addAll(contents);
        notifyItemRangeChanged(oldSize,contents.size());

    }

    public class InnerHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.goods_cover)
        public ImageView cover;
        @BindView(R.id.goods_title)
        public TextView title;
        @BindView(R.id.goods_prise)
        public TextView prise;
        @BindView(R.id.goods_after_off_prise)
        public TextView finalPriseTv;
        @BindView(R.id.goods_original_prise)
        public TextView originalPrise;
        @BindView(R.id.goods_sell_count)
        public TextView sellCount;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void setData(HomePagerContent.DataBean dataBean) {
            Context context = itemView.getContext();
            title.setText(dataBean.getTitle());
            String finalPrise = dataBean.getZk_final_price();
            long couponAmount = dataBean.getCoupon_amount();
            float resultPrise = Float.parseFloat(finalPrise)-couponAmount;
            prise.setText(String.format(itemView.getContext().getString(R.string.item_goods_prise),couponAmount));
            Glide.with(context).load(UrlUtils.getCoverPath(dataBean.getPict_url())).into(cover);
            finalPriseTv.setText(String.format("%.2f",resultPrise));
            originalPrise.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            originalPrise.setText(String.format(context.getString(R.string.item_goods_original_prise),finalPrise));
            sellCount.setText(String.format(context.getString(R.string.item_goods_sell_count),dataBean.getVolume()));
        }
    }
}
