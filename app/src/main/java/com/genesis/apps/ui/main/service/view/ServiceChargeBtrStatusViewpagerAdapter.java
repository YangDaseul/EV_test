package com.genesis.apps.ui.main.service.view;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.genesis.apps.comm.model.api.gra.CHB_1021;
import com.genesis.apps.ui.main.service.FragmentServiceChargeBtrApplyInfo;
import com.genesis.apps.ui.main.service.FragmentServiceChargeBtrMap;


public class ServiceChargeBtrStatusViewpagerAdapter extends FragmentStateAdapter {

    public CHB_1021.Response data;
    public int count;

    public ServiceChargeBtrStatusViewpagerAdapter(Fragment fa, int count, CHB_1021.Response data) {
        super(fa);
        this.count = count;
        this.data = data;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        int index = getRealPosition(position);

        switch (index) {
            case 0:
                return FragmentServiceChargeBtrApplyInfo.newInstance(data);
            default:
                return FragmentServiceChargeBtrMap.newInstance(data);
        }
    }


    @Override
    public int getItemCount() {
        return count;
    }

    public int getRealPosition(int position) {
        return position % count;
    }

}