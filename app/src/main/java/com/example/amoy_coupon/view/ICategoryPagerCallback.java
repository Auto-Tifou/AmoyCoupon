package com.example.amoy_coupon.view;

import com.example.amoy_coupon.base.IBaseCallback;
import com.example.amoy_coupon.model.domain.HomePagerContent;

import java.util.List;

public interface ICategoryPagerCallback extends IBaseCallback {

    /**
     * 数据加载回来
     *
     * @param content
     */
    void onContentLoaded(List<HomePagerContent.DataBean> content);

    int getCategoryId();

    /**
     * 加载更多网络错误
     */
    void onLoaderMoreError();

    /**
     * 加载更多没内容
     */
    void onLoaderMoreEmpty();

    /**
     * 加载到了更多内容
     * @param contents
     */
    void onLoaderMoreLoaded(List<HomePagerContent.DataBean> contents);

    /**
     * 轮播图内容加载到了
     * @param contents
     */
    void onLooperListLoaded(List<HomePagerContent.DataBean> contents);

}
