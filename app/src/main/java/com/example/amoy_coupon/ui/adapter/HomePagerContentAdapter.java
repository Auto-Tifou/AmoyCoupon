package com.example.amoy_coupon.ui.adapter;

import android.media.Image;
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

    List<HomePagerContent.DataBean> data = new ArrayList<>();

    @NonNull
    @Override
    public HomePagerContentAdapter.InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_pager_content, parent, false);
        ButterKnife.bind(this,view);
        return new InnerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomePagerContentAdapter.InnerHolder holder, int position) {
        HomePagerContent.DataBean dataBean = data.get(position);
        //设置数据
        holder.setData(dataBean);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<HomePagerContent.DataBean> content) {
        data.clear();
        data.addAll(content);
        notifyDataSetChanged();
    }

    public class InnerHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.goods_cover)
        public ImageView cover;
        @BindView(R.id.goods_title)
        public TextView title;
        @BindView(R.id.goods_prise)
        public TextView prise;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void setData(HomePagerContent.DataBean dataBean) {
            title.setText(dataBean.getTitle());
            prise.setText(String.format(itemView.getContext().getString(R.string.text_goods_prise),dataBean.getCoupon_amount()));
            Glide.with(itemView.getContext()).load(UrlUtils.getCoverPath(dataBean.getPict_url())).into(cover);
        }
    }
}
