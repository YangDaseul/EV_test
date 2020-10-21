package com.genesis.apps.ui.main.service;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;


public class ServiceViewpagerAdapter extends FragmentStateAdapter {
    private final static int MAINTENANCE = 0;
    private final static int CAR_WASH = 1;
    private final static int SERVICE_DRIVE = 2;

    public int mCount;

    public ServiceViewpagerAdapter(FragmentActivity fa, int count) {
        super(fa);
        mCount = count;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        int index = getRealPosition(position);

        //TODO 정비 / 세차 / 대리 탭
        switch (index) {
            case MAINTENANCE:
                return new FragmentMaintenance();
            case CAR_WASH:
                return new FragmentCarWash();
            case SERVICE_DRIVE:
            default:
                return new FragServiceDrive();
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