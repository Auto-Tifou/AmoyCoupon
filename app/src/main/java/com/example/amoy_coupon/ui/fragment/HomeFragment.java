package com.example.amoy_coupon.ui.fragment;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.example.amoy_coupon.R;
import com.example.amoy_coupon.base.BaseFragment;
import com.example.amoy_coupon.model.domain.Categories;
import com.example.amoy_coupon.presenter.IHomePresenter;
import com.example.amoy_coupon.presenter.impl.HomePresenterImpl;
import com.example.amoy_coupon.ui.adapter.HomePagerAdapter;
import com.example.amoy_coupon.view.IHomeCallback;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;

public class HomeFragment extends BaseFragment implements IHomeCallback {

    private static final String TAG = "HomeFragment";

    @BindView(R.id.home_indicator)
    TabLayout mTabLayout;

    @BindView(R.id.home_pager)
    ViewPager mHomePager;

    private IHomePresenter mHomePresenter;
    private HomePagerAdapter mHomePagerAdapter;

    @Override
    protected int getRootViewResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View rootView) {
        mTabLayout.setupWithViewPager(mHomePager);
        //给mHomePager设置适配器
        mHomePagerAdapter = new HomePagerAdapter(getChildFragmentManager());
        mHomePager.setAdapter(mHomePagerAdapter);
    }

    @Override
    protected void initPresenter() {
        //创建Presenter
        mHomePresenter = new HomePresenterImpl();
        mHomePresenter.registerViewCallback(this);
    }

    @Override
    protected void loadData() {
        setUpState(State.LOADING);
        //加载数据
        mHomePresenter.getCategories();
    }

    @Override
    public void onCategoriesLoaded(Categories category) {
        setUpState(State.SUCCESS);
        //加载数据回调回来
        if (mHomePagerAdapter != null){
//            mHomePager.setOffscreenPageLimit(category.getData().size());//最大加载页面数据
            mHomePagerAdapter.setCategories(category);
        }
    }

    @Override
    public void onError() {
        setUpState(State.ERROR);
    }

    @Override
    public void onLoading() {
        setUpState(State.LOADING);
    }

    @Override
    public void onEmpty() {
        setUpState(State.EMPTY);
    }

    @Override
    protected void release() {
        if (mHomePresenter != null){
            mHomePresenter.unregisterViewCallback(this);
        }
    }

    @Override
    protected void onRetryClick() {
        //网络出错点击重试，重新加载分类
        if (mHomePresenter!= null){
            mHomePresenter.getCategories();
        }
    }
}
