package com.example.amoy_coupon.view;

import com.example.amoy_coupon.model.domain.Categories;

public interface IHomeCallback {


    void onCategoriesLoaded(Categories category);

    void onNetworkError();

    void onLoading();

    void onEmpty();
}
