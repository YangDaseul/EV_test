package com.genesis.apps.ui.main.service.view;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.genesis.apps.comm.model.api.gra.CHB_1021;
import com.genesis.apps.ui.main.service.FragmentServiceChargeBtrApplyInfo;
import com.genesis.apps.ui.main.service.FragmentServiceChargeBtrMap;


public class ServiceChargeBtrStatusViewpagerAdapter extends FragmentStateAdapter {

    public CHB_1021.Response mData;
    public int mCount;

    public ServiceChargeBtrStatusViewpagerAdapter(Fragment fa, int count, CHB_1021.Response data) {
        super(fa);
        mCount = count;
        mData = data;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        int index = getRealPosition(position);

        switch (index) {
            case 0:
                return FragmentServiceChargeBtrApplyInfo.newInstance(mData);
            default:
                return FragmentServiceChargeBtrMap.newInstance(mData);
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