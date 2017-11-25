package com.zhuhean.messenger.ui.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.zhuhean.messenger.R;
import com.zhuhean.messenger.ui.adapter.ViewHelper;

import butterknife.BindView;

public abstract class TabViewPagerFragment extends BaseFragment {

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Override
    public int getContentView() {
        return R.layout.fragment_tab_view_pager;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager.setAdapter(getPagerAdapter());
        tabLayout.setupWithViewPager(viewPager);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // To make the shadow show, you have to set a background on your TabLayout.
            tabLayout.setElevation(ViewHelper.dpToPx(getContext(), 6));
        }
    }

    protected abstract PagerAdapter getPagerAdapter();

}
