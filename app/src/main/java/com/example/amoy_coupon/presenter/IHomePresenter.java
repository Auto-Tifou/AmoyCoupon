package com.example.amoy_coupon.presenter;

import com.example.amoy_coupon.base.IBasePresenter;
import com.example.amoy_coupon.view.IHomeCallback;

public interface IHomePresenter extends IBasePresenter<IHomeCallback> {

    /**
     * 获取商品分类
     */
    void getCategories();

}
