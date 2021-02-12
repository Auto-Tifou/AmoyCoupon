package com.example.amoy_coupon.ui.fragment;

import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.amoy_coupon.R;
import com.example.amoy_coupon.base.BaseFragment;
import com.example.amoy_coupon.model.domain.Categories;
import com.example.amoy_coupon.model.domain.HomePagerContent;
import com.example.amoy_coupon.presenter.ICategoryPagerPresenter;
import com.example.amoy_coupon.presenter.impl.CategoryPagePresenterImpl;
import com.example.amoy_coupon.ui.adapter.HomePagerContentAdapter;
import com.example.amoy_coupon.ui.adapter.LooperPagerAdapter;
import com.example.amoy_coupon.utils.SizeUtils;
import com.example.amoy_coupon.view.ICategoryPagerCallback;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.List;

import butterknife.BindView;

import static com.example.amoy_coupon.utils.Constants.KEY_HOME_PAGER_ID;
import static com.example.amoy_coupon.utils.Constants.KEY_HOME_PAGER_TITLE;

public class HomePagerFragment extends BaseFragment implements ICategoryPagerCallback {

    private static final String TAG = "HomePagerFragment";
    private ICategoryPagerPresenter mCategoryPagePresenter;
    private int mId;
    private HomePagerContentAdapter mContentAdapter;
    private LooperPagerAdapter mLooperPagerAdapter;

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
    @BindView(R.id.looper_pager)
    public ViewPager mLooperPager;
    @BindView(R.id.home_pager_title)
    public TextView mCurrentCategoryTitleTv;
    @BindView(R.id.looper_point_container)
    public LinearLayout mLooperPointContainer;
    @BindView(R.id.home_pager_refresh)
    public TwinklingRefreshLayout mTwinklingRefreshLayout;

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
        //给viewPager设置适配器
        mLooperPagerAdapter = new LooperPagerAdapter();
        mLooperPager.setAdapter(mLooperPagerAdapter);
        //设置TwinklingRefreshLayout相关
        mTwinklingRefreshLayout.setEnableRefresh(false);
        mTwinklingRefreshLayout.setEnableLoadmore(true);
//        mTwinklingRefreshLayout.setbo
    }

    @Override
    protected void initListener() {
        mLooperPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //切换指示器
                if (mLooperPagerAdapter.getDataSize() == 0) {
                    return;
                }
                int targetPosition = position % mLooperPagerAdapter.getDataSize();
                updateLooperIndicator(targetPosition);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mTwinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                mCategoryPagePresenter.loaderMore(mId);
            }
        });
    }

    /**
     * 切换指示器
     * @param targetPosition
     */
    private void updateLooperIndicator(int targetPosition) {
        for (int i = 0; i < mLooperPointContainer.getChildCount(); i++) {
            View point = mLooperPointContainer.getChildAt(i);
            if (i == targetPosition){
                point.setBackgroundResource(R.drawable.shape_indicator_point_select);
            }else {
                point.setBackgroundResource(R.drawable.shape_indicator_point_normal);
            }
        }
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
        if (mCurrentCategoryTitleTv != null) {
            mCurrentCategoryTitleTv.setText(title);
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
        showToast("没有更多数据");
    }

    @Override
    public void onLoaderMoreLoaded(List<HomePagerContent.DataBean> contents) {
        //添加到适配器数据底部
        mContentAdapter.addData(contents);
        if (mTwinklingRefreshLayout != null) {
            mTwinklingRefreshLayout.finishLoadmore();
        }
        showToast("加载了"+contents.size()+"条数据");
    }

    @Override
    public void onLooperListLoaded(List<HomePagerContent.DataBean> contents) {
        mLooperPagerAdapter.setData(contents);
        //设置中间点，中间点%数据的sizi不一定为0，显示的就不是第一个
        int dx = (Integer.MAX_VALUE / 2) % contents.size();
        int targetCenterPosition = (Integer.MAX_VALUE / 2) - dx;

        mLooperPager.setCurrentItem(targetCenterPosition);
        //添加点
        mLooperPointContainer.removeAllViews();

        for (int i = 0; i < contents.size(); i++) {
            View point = new View(getContext());
            int size = SizeUtils.dip2px(getContext(), 8);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(size, size);
            layoutParams.leftMargin = SizeUtils.dip2px(getContext(),5);
            layoutParams.rightMargin = SizeUtils.dip2px(getContext(),5);
            point.setLayoutParams(layoutParams);
            if (i == 0){
                point.setBackgroundResource(R.drawable.shape_indicator_point_select);
            }else {
                point.setBackgroundResource(R.drawable.shape_indicator_point_normal);
            }
            mLooperPointContainer.addView(point);
        }
    }

    @Override
    protected void release() {
        if (mCategoryPagePresenter!=null) {
            mCategoryPagePresenter.unregisterViewCallback(this);
        }
    }
}
