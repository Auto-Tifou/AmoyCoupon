package com.example.amoy_coupon.ui.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amoy_coupon.R;
import com.example.amoy_coupon.base.BaseFragment;
import com.example.amoy_coupon.model.domain.Categories;
import com.example.amoy_coupon.model.domain.HomePagerContent;
import com.example.amoy_coupon.presenter.ICategoryPagerPresenter;
import com.example.amoy_coupon.presenter.impl.CategoryPagePresenterImpl;
import com.example.amoy_coupon.ui.adapter.HomePagerContentAdapter;
import com.example.amoy_coupon.view.ICategoryPagerCallback;

import java.util.List;

import butterknife.BindView;

import static com.example.amoy_coupon.utils.Constants.KEY_HOME_PAGER_ID;
import static com.example.amoy_coupon.utils.Constants.KEY_HOME_PAGER_TITLE;

public class HomePagerFragment extends BaseFragment implements ICategoryPagerCallback {

    private static final String TAG = "HomePagerFragment";
    private ICategoryPagerPresenter mCategoryPagePresenter;
    private int mId;
    private HomePagerContentAdapter mContentAdapter;

    public static HomePagerFragment nerInstance(Categories.DataBean category){
        HomePagerFragment homePagerFragment = new HomePagerFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_HOME_PAGER_TITLE,category.getTitle());
        bundle.putInt(KEY_HOME_PAGER_ID,category.getId());
        homePagerFragment.setArguments(bundle);
        return homePagerFragment;
    }

    @BindView(R.id.home_pager_content_list)
    public RecyclerView mContentList;

    @Override
    protected int getRootViewResId() {
        return R.layout.fragment_home_pager;
    }

    @Override
    protected void initView(View rootView) {
        mContentList.setLayoutManager(new LinearLayoutManager(getContext()));
        mContentList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, int itemPosition, @NonNull RecyclerView parent) {
                outRect.top = 2;
                outRect.bottom = 2;
            }
        });
        mContentAdapter = new HomePagerContentAdapter();
        //设置适配器
        mContentList.setAdapter(mContentAdapter);
    }

    @Override
    protected void initPresenter() {
        mCategoryPagePresenter = CategoryPagePresenterImpl.getsIntance();
        mCategoryPagePresenter.registerViewCallback(this);
    }

    @Override
    protected void loadData() {
        Bundle arguments = getArguments();
        String title = arguments.getString(KEY_HOME_PAGER_TITLE);
        mId = arguments.getInt(KEY_HOME_PAGER_ID);
        //加载数据
        if (mCategoryPagePresenter!=null) {
            mCategoryPagePresenter.getContentByCategoryId(mId);
        }
    }

    @Override
    public void onContentLoaded(List<HomePagerContent.DataBean> content) {
        //数据列表加载更新UI
        mContentAdapter.setData(content);
        setUpState(State.SUCCESS);
    }

    @Override
    public int getCategoryId() {
        return mId;
    }

    @Override
    public void onLoading() {
        //加载中
        setUpState(State.LOADING);
    }

    @Override
    public void onError() {
        //网络错误
        setUpState(State.ERROR);
    }

    @Override
    public void onEmpty() {
        //内容为空
        setUpState(State.EMPTY);
    }

    @Override
    public void onLoaderMoreError() {

    }

    @Override
    public void onLoaderMoreEmpty() {

    }

    @Override
    public void onLoaderMoreLoaded(List<HomePagerContent.DataBean> contents) {

    }

    @Override
    public void onLooperListLoaded(List<HomePagerContent.DataBean> contents) {

    }

    @Override
    protected void release() {
        if (mCategoryPagePresenter!=null) {
            mCategoryPagePresenter.unregisterViewCallback(this);
        }
    }
}
