package com.genesis.apps.ui.main.service;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;


public class ServiceViewpagerAdapter extends FragmentStateAdapter {
    private static final int MAINTENANCE = 0;
    private static final int CAR_WASH = 1;
    private static final int SERVICE_DRIVE = 2;
    private static final int SERVICE_CHARGE = 3;
    private final int tabCount;
    public ServiceViewpagerAdapter(Fragment fa, int count) {
        super(fa);
        tabCount = count;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        int index = getRealPosition(position);

        switch (index) {
            case MAINTENANCE:
                return new FragmentMaintenance();
            case CAR_WASH:
                return new FragmentCarWash();
            case SERVICE_DRIVE:
                return new FragmentServiceDrive();
            case SERVICE_CHARGE:
            default:
                return new FragmentCharge();
        }
    }

    @Override
    public int getItemCount() {
        return tabCount;
    }

    public int getRealPosition(int position) {
        return position % tabCount;
    }
}