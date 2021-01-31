package com.example.amoy_coupon.presenter;

import com.example.amoy_coupon.base.IBasePresenter;
import com.example.amoy_coupon.view.ICategoryPagerCallback;

public interface ICategoryPagerPresenter extends IBasePresenter<ICategoryPagerCallback> {

    /**
     * 根据分类id去获取内容
     * @param categoryId
     */
    void getContentByCategoryId(int categoryId);

    void loaderMore(int categoryId);

    void reload(int categoryId);

}
