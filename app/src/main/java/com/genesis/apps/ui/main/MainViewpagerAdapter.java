package com.genesis.apps.ui.main;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.genesis.apps.ui.common.fragment.main.FragFourth;
import com.genesis.apps.ui.common.fragment.main.FragSecond;
import com.genesis.apps.ui.common.fragment.main.FragThird;
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

        if(index==0) return new FragmentHome();
        else if(index==1) return new FragSecond();
        else if(index==2) return new FragThird();
        else if(index==3) return new FragFourth();
        else return new FragmentMainContents();

    }


    @Override
    public int getItemCount() {
        return mCount;
    }

    public int getRealPosition(int position) { return position % mCount; }




}