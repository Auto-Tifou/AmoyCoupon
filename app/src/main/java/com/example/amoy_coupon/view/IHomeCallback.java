package com.example.amoy_coupon.view;

import com.example.amoy_coupon.base.IBaseCallback;
import com.example.amoy_coupon.model.domain.Categories;

public interface IHomeCallback extends IBaseCallback {


    void onCategoriesLoaded(Categories category);

}
