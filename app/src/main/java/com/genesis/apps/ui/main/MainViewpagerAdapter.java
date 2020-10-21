package com.genesis.apps.ui.main;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.genesis.apps.ui.main.service.FragmentService;
import com.genesis.apps.ui.main.contents.FragmentContents;
import com.genesis.apps.ui.main.insight.FragmentInsight;


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
                return new FragmentInsight();
            case 2:
                return new FragmentService();
            default:
                return new FragmentContents();
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