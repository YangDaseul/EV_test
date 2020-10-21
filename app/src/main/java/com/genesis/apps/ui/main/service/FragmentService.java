package com.genesis.apps.ui.main.service;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.genesis.apps.R;
import com.genesis.apps.databinding.FragmentServiceBinding;
import com.genesis.apps.ui.common.fragment.SubFragment;
import com.genesis.apps.ui.main.MainActivity;
import com.google.android.material.tabs.TabLayoutMediator;


public class FragmentService extends SubFragment<FragmentServiceBinding> {
    private static final String TAG = FragmentService.class.getSimpleName();

    private static final int PAGE_NUM = 3;//정비 세차 대리
    private static final int[] TAB_ID_LIST = {R.string.sm01_header_1, R.string.sm01_header_2, R.string.sm01_header_3};

    public FragmentStateAdapter serviceTabAdapter;


    //TODO ViewModel
//    private ViewModel ViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView(): start");
        View view = super.setContentView(inflater, R.layout.fragment_service);
        me.setFragment(this);

        initView();
        return view;
    }

    private void initView() {
        serviceTabAdapter = new ServiceViewpagerAdapter(getActivity(), PAGE_NUM);
        me.vpServiceContentsViewPager.setAdapter(serviceTabAdapter);
        me.vpServiceContentsViewPager.setUserInputEnabled(false);
        setTabView();

        //ViewPager Setting
        me.vpServiceContentsViewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        me.vpServiceContentsViewPager.setCurrentItem(0);
        me.vpServiceContentsViewPager.setOffscreenPageLimit(PAGE_NUM);

        //TODO : MAinActivity에서 복붙. 스와이프 리스너인데 필요한가?
//        me.vpServiceContentsViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
//                if (positionOffsetPixels == 0) {
//                    me.vpServiceContentsViewPager.setCurrentItem(position);
//                }
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                super.onPageSelected(position);
//
////                ui.indicator.animatePageSelected(position%num_page);
//            }
//
//        });

        //TODO : MAinActivity에서 복붙. 페이지 넘기는 이펙트 그럼 메인 탭이 넘어가야되나, 서비스 내부 탭이 넘어가야되나???
//        final float pageMargin = getResources().getDimensionPixelOffset(R.dimen.pageMargin);
//        final float pageOffset = getResources().getDimensionPixelOffset(R.dimen.offset);
//        me.vpServiceContentsViewPager.setPageTransformer((page, position) -> {
//            float myOffset = position * -(2 * pageOffset + pageMargin);
//            if (me.vpServiceContentsViewPager.getOrientation() == ViewPager2.ORIENTATION_HORIZONTAL) {
//                if (ViewCompat.getLayoutDirection(me.vpServiceContentsViewPager) == ViewCompat.LAYOUT_DIRECTION_RTL) {
//                    page.setTranslationX(-myOffset);
//                } else {
//                    page.setTranslationX(myOffset);
//                }
//            } else {
//                page.setTranslationY(myOffset);
//            }
//        });
    }

    //탭 헤더 세팅
    private void setTabView() {
        new TabLayoutMediator(me.tlServiceTabs, me.vpServiceContentsViewPager, (tab, position) -> {
        }).attach();

        for (int i = 0; i < PAGE_NUM; i++) {
            TextView tabTitle = new TextView(getActivity());
            tabTitle.setText(TAB_ID_LIST[i]);
            tabTitle.setTextAppearance(R.style.ServiceMainTabs);
            me.tlServiceTabs.getTabAt(i).setCustomView(tabTitle);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onActivityCreated(): start");

        super.onActivityCreated(savedInstanceState);
        //TODO ViewModel
//        ViewModel = new ViewModelProvider(getActivity()).get(ViewModel.class);

        me.setLifecycleOwner(getViewLifecycleOwner());
        me.setFragment(this);
        initViewPager();
    }

    private void initViewPager() {
        //ViewPager Setting

    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onClickCommon(View v) {

        switch (v.getId()) {

        }
    }

    @Override
    public void onRefresh() {

        Log.e("onResume", "onReusme contents");


        ((MainActivity) getActivity()).setGNB(false, true, 0, View.VISIBLE);
    }


}
