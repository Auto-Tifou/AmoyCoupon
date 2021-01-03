package com.example.amoy_coupon.model;

import com.example.amoy_coupon.model.domain.Categories;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

    @GET("discovery/categories")
    Call<Categories> getCategories();
}
