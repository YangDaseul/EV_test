package com.genesis.apps.ui.main.contents;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.genesis.apps.comm.model.vo.CatTypeVO;

import java.util.List;

public class ContentsViewpagerAdapter extends FragmentStateAdapter {

    private List<CatTypeVO> items;

    public ContentsViewpagerAdapter(Fragment fa, List<CatTypeVO> items) {
        super(fa);

        this.items = items;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        int index = getRealPosition(position);
        CatTypeVO item = items.get(index);

        return FragmentContentsList.newInstance(item.getCd());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public int getRealPosition(int position) {
        return position % items.size();
    }
}
