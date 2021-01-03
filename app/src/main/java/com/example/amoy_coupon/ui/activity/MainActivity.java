package com.example.amoy_coupon.ui.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.amoy_coupon.R;
import com.example.amoy_coupon.base.BaseFragment;
import com.example.amoy_coupon.ui.fragment.HomeFragment;
import com.example.amoy_coupon.ui.fragment.RedPacketFragment;
import com.example.amoy_coupon.ui.fragment.SearchFragment;
import com.example.amoy_coupon.ui.fragment.SelectedFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_navigation_bar)
    public BottomNavigationView mNavigationView;
    private HomeFragment mHomeFragment;
    private RedPacketFragment mRedPacketFragment;
    private SelectedFragment mSelectedFragment;
    private SearchFragment mSearchFragment;
    private FragmentManager mFm;
    private Unbinder mBind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBind = ButterKnife.bind(this);

        initFragment();
        initListener();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBind != null) {
            mBind.unbind();
        }
    }

    private void initFragment() {
        mHomeFragment = new HomeFragment();
        mSelectedFragment = new SelectedFragment();
        mRedPacketFragment = new RedPacketFragment();
        mSearchFragment = new SearchFragment();
        mFm = getSupportFragmentManager();
        switchFragment(mHomeFragment);
    }

    private void initListener() {
        mNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home:
                    switchFragment(mHomeFragment);
                    break;
                case R.id.selected:
                    switchFragment(mSelectedFragment);
                    break;
                case R.id.red_packet:
                    switchFragment(mRedPacketFragment);
                    break;
                case R.id.search:
                    switchFragment(mSearchFragment);
                    break;
                    default:

            }
            return true;
        });
    }

    private void switchFragment(BaseFragment baseFragment) {
        FragmentTransaction transaction = mFm.beginTransaction();
        transaction.replace(R.id.main_page_container,baseFragment);
        transaction.commit();
    }
}