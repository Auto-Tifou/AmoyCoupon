package com.example.amoy_coupon.presenter.impl;

import com.example.amoy_coupon.model.Api;
import com.example.amoy_coupon.model.domain.HomePagerContent;
import com.example.amoy_coupon.presenter.ICategoryPagerPresenter;
import com.example.amoy_coupon.utils.LLog;
import com.example.amoy_coupon.utils.RetrofitManager;
import com.example.amoy_coupon.utils.UrlUtils;
import com.example.amoy_coupon.view.ICategoryPagerCallback;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CategoryPagePresenterImpl implements ICategoryPagerPresenter {

    private static final String TAG = "CategoryPagePresenterImpl";

    private Map<Integer, Integer> pagerInfo = new HashMap<>();

    public static final int DEFAULT_PAGE = 1;

    private CategoryPagePresenterImpl() {

    }

    private static ICategoryPagerPresenter sInstance = null;

    public static ICategoryPagerPresenter getsIntance() {
        if (sInstance == null) {
            sInstance = new CategoryPagePresenterImpl();
        }
        return sInstance;
    }

    @Override
    public void getContentByCategoryId(int categoryId) {
        for (ICategoryPagerCallback callback : mCallbacks) {
            if (callback.getCategoryId()==categoryId) {
                callback.onLoading();
            }
        }
        //根据分类id加载内容
        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
        Api api = retrofit.create(Api.class);
        Integer targetPager = pagerInfo.get(categoryId);
        if (targetPager == null) {
            targetPager = DEFAULT_PAGE;
            pagerInfo.put(categoryId, DEFAULT_PAGE);
        }
        String homePagerUrl = UrlUtils.createHomePagerUrl(categoryId, targetPager);
        Call<HomePagerContent> task = api.getHomePageContent(homePagerUrl);
        task.enqueue(new Callback<HomePagerContent>() {
            @Override
            public void onResponse(Call<HomePagerContent> call, Response<HomePagerContent> response) {
                int code = response.code();
                LLog.d(TAG,"code ->"+code);
                if (code == HttpURLConnection.HTTP_OK){
                    HomePagerContent pagerContent = response.body();
                    handleHomePageContentResult(pagerContent,categoryId);
                }else {
                    handleNetworkError(categoryId);
                }
            }

            @Override
            public void onFailure(Call<HomePagerContent> call, Throwable t) {
                LLog.e(TAG,"error ->"+t.toString());
                handleNetworkError(categoryId);
            }
        });

    }

    private void handleNetworkError(int categoryId) {
        for (ICategoryPagerCallback callback : mCallbacks) {
            if (callback.getCategoryId()==categoryId) {
                callback.onError();
            }
        }
    }

    private void handleHomePageContentResult(HomePagerContent pagerContent, int categoryId) {
        //通知UI层更新数据
        for (ICategoryPagerCallback callback : mCallbacks){
            if (callback.getCategoryId()==categoryId) {
                if (pagerContent == null || pagerContent.getData().size() == 0){
                    callback.onEmpty();
                }else {
                    callback.onContentLoaded(pagerContent.getData());
                }
            }
        }
    }

    @Override
    public void loaderMore(int categoryId) {

    }

    @Override
    public void reload(int categoryId) {

    }

    private List<ICategoryPagerCallback> mCallbacks = new ArrayList<>();

    @Override
    public void registerViewCallback(ICategoryPagerCallback callback) {
        if (!mCallbacks.contains(callback)) {
            mCallbacks.add(callback);
        }
    }

    @Override
    public void unregisterViewCallback(ICategoryPagerCallback callback) {
        mCallbacks.remove(callback);
    }
}
