package com.genesis.apps.ui.fragment.main;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.genesis.apps.comm.model.main.contents.ContentsResVO;

import java.util.List;


public class ContentsViewpagerAdapter extends FragmentStateAdapter {

    private List<ContentsResVO> contentsResVOList;
    public int mCount;

    public ContentsViewpagerAdapter(FragmentActivity fa, int count, List<ContentsResVO> contentsResVOList) {
        super(fa);
        mCount = count;
        this.contentsResVOList = contentsResVOList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        int index = getRealPosition(position);
        return new FragmentMainContentsVp(contentsResVOList.get(index));
    }


    @Override
    public int getItemCount() {
        return mCount;
    }

    public int getRealPosition(int position) { return position % mCount; }



}