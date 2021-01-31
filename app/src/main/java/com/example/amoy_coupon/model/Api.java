package com.example.amoy_coupon.model;

import com.example.amoy_coupon.model.domain.Categories;
import com.example.amoy_coupon.model.domain.HomePagerContent;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface Api {

    @GET("discovery/categories")
    Call<Categories> getCategories();

    @GET
    Call<HomePagerContent> getHomePageContent(@Url String url);
}
