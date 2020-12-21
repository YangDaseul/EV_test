package com.genesis.apps.ui.main.service;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;


public class ServiceViewpagerAdapter extends FragmentStateAdapter {
    private static final int MAINTENANCE = 0;
    private static final int CAR_WASH = 1;
    private static final int SERVICE_DRIVE = 2;

//    private final Class[] fragmentList;
    private final int tabCount;


    //TODO 클래스 정보를 가져와서 갖고있다가 그 클래스의 인스턴스를 만들어주는 식으로 안 되나? 되면 공용으로 쓸 수 있을텐데
//    public ServiceViewpagerAdapter(FragmentActivity fa, Class[] list) {
//        super(fa);
//        fragmentList = list;
//        mCount = list.length;
//    }

    public ServiceViewpagerAdapter(Fragment fa, int count) {
        super(fa);
        tabCount = count;
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
                return new FragmentServiceDrive();
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