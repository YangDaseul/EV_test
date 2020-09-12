package com.genesis.apps.ui.common.fragment.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.main.contents.ContentsResVO;
import com.genesis.apps.databinding.FragmentMainContentsVpBinding;
import com.genesis.apps.ui.common.fragment.SubFragment;

public class FragmentMainContentsVp extends SubFragment<FragmentMainContentsVpBinding> {

    private ContentsResVO contentsResVO;

    public FragmentMainContentsVp(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.setContentView(inflater, R.layout.fragment_main_contents_vp);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Glide.with(this)
//                .load(contentsResVO.getImgUrl())
//                .override(200,600) // ex) override(600, 200)
//                .error(R.color.x_d4d4d4)
//                .into(me.ivImage);
//
//        me.tvCategory.setText(contentsResVO.getCategory());
//        me.tvDescription.setText(contentsResVO.getContents());
//        me.tvTitle.setText(contentsResVO.getTitle());
     }

    @Override
    public boolean onBackPressed() {
        return false;
    }


    @Override
    public void onRefresh() {

    }



}
