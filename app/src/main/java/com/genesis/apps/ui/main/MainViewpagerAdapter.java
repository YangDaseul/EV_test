package com.genesis.apps.ui.main;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.genesis.apps.ui.common.fragment.main.FragFourth;
import com.genesis.apps.ui.common.fragment.main.FragSecond;
import com.genesis.apps.ui.common.activity.test.FragThird;
import com.genesis.apps.ui.common.fragment.main.FragmentMainContents;


public class MainViewpagerAdapter extends FragmentStateAdapter {

    public int mCount;

    public MainViewpagerAdapter(FragmentActivity fa, int count) {
        super(fa);
        mCount = count;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        int index = getRealPosition(position);

        switch (index) {
            case 0:
                return new FragmentHome();
            case 1:
                return new FragSecond();
            case 2:
                return new FragThird();
            case 3:
                return new FragFourth();
            default:
                return new FragmentMainContents();
        }

    }


    @Override
    public int getItemCount() {
        return mCount;
    }

    public int getRealPosition(int position) {
        return position % mCount;
    }


}