package com.example.amoy_coupon.presenter.impl;

import com.example.amoy_coupon.model.Api;
import com.example.amoy_coupon.model.domain.Categories;
import com.example.amoy_coupon.presenter.IHomePresenter;
import com.example.amoy_coupon.utils.LogUtils;
import com.example.amoy_coupon.utils.RetrofitManager;
import com.example.amoy_coupon.view.IHomeCallback;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomePresenterImpl implements IHomePresenter {

    private IHomeCallback mCallback;

    @Override
    public void getCategories() {
        //加载分类数据
        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
        Api api = retrofit.create(Api.class);
        Call<Categories> task = api.getCategories();
        task.enqueue(new Callback<Categories>() {
            @Override
            public void onResponse(Call<Categories> call, Response<Categories> response) {
                //成功-结果
                int code = response.code();
                LogUtils.d(HomePresenterImpl.this,"onResponse-->code"+code);
                if (code == HttpURLConnection.HTTP_OK){
                    //请求成功
                    Categories categories = response.body();
                    if (mCallback != null){
                        mCallback.onCategoriesLoaded(categories);
                    }
                }else {
                    //请求失败
                    LogUtils.e(HomePresenterImpl.this,"请求失败");
                }

            }
            @Override
            public void onFailure(Call<Categories> call, Throwable t) {
                //失败-结果
                LogUtils.e(HomePresenterImpl.this,"请求错误"+t);

            }
        });
    }

    @Override
    public void registerCallback(IHomeCallback callback) {
        this.mCallback = callback;
    }

    @Override
    public void unregisterCallback(IHomeCallback callback) {
        mCallback = null;
    }
}
