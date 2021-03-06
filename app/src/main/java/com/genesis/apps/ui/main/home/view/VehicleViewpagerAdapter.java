package com.genesis.apps.ui.main.home.view;

import com.genesis.apps.ui.main.home.FragmentHome1;
import com.genesis.apps.ui.main.home.FragmentHome2;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;


public class VehicleViewpagerAdapter extends FragmentStateAdapter {

    public int mCount;

    public VehicleViewpagerAdapter(Fragment fa, int count) {
        super(fa);
        mCount = count;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        int index = getRealPosition(position);
        if(index==0) return new FragmentHome1();
        else return new FragmentHome2();
    }


    @Override
    public int getItemCount() {
        return mCount;
    }

    public int getRealPosition(int position) { return position % mCount; }



}