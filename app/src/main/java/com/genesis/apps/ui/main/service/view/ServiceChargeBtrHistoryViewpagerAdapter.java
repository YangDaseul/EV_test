package com.genesis.apps.ui.main.service.view;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.genesis.apps.ui.main.service.FragmentServiceChargeBtrHistory;
import com.genesis.apps.ui.main.service.FragmentServiceChargeBtrStatus;


public class ServiceChargeBtrHistoryViewpagerAdapter extends FragmentStateAdapter {

    public int mCount;

    public ServiceChargeBtrHistoryViewpagerAdapter(FragmentActivity fa, int count) {
        super(fa);
        mCount = count;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        int index = getRealPosition(position);

        switch (index) {
            case 0:
                return new FragmentServiceChargeBtrStatus();
            default:
                return new FragmentServiceChargeBtrHistory();
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