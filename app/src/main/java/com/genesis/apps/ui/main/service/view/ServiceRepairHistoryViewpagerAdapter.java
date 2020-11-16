package com.genesis.apps.ui.main.service.view;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.genesis.apps.ui.main.service.FragmentServiceRepairHistory;
import com.genesis.apps.ui.main.service.FragmentServiceRepairStatus;


public class ServiceRepairHistoryViewpagerAdapter extends FragmentStateAdapter {

    public int mCount;

    public ServiceRepairHistoryViewpagerAdapter(FragmentActivity fa, int count) {
        super(fa);
        mCount = count;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        int index = getRealPosition(position);

        switch (index) {
            case 0:
                return new FragmentServiceRepairStatus();
            default:
                return new FragmentServiceRepairHistory();
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