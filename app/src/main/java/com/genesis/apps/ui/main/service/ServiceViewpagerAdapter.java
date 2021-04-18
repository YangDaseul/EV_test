package com.genesis.apps.ui.main.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import static java.util.stream.Collectors.toList;


public class ServiceViewpagerAdapter extends FragmentStateAdapter {
    private List<Fragment> fragments = new ArrayList<>(Arrays.asList(FragmentCharge.newInstance(0), new FragmentMaintenance(), new FragmentCarWash(), new FragmentServiceDrive()));
    private List<Integer> fragmentIds = fragments.stream().map(Fragment::hashCode).collect(toList());
    public ServiceViewpagerAdapter(Fragment fa) {
        super(fa);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        int index = getRealPosition(position);
        return fragments.get(index);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }

    public int getRealPosition(int position) {
        return position % fragments.size();
    }

    public void removeFragmentCharge(){
        if(fragments.size()>3) {
            fragments.remove(0);
            fragmentIds.remove(0);
            notifyDataSetChanged();
        }
    }

    public void addFragmentCharge(){
        if(fragments.size()<4) {
            fragments.add(0, FragmentCharge.newInstance(0));
            fragmentIds.add(0, fragments.get(0).hashCode());
            notifyDataSetChanged();
        }
    }

    @Override
    public long getItemId(int position) {
        return fragmentIds.get(position);
    }

    @Override
    public boolean containsItem(long itemId) {
        return fragmentIds.contains((int)itemId);
    }
}