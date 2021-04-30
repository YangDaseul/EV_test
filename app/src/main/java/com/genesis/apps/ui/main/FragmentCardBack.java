package com.genesis.apps.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.genesis.apps.R;
import com.genesis.apps.databinding.FragmentCardBackBinding;
import com.genesis.apps.ui.common.fragment.SubFragment;

public class FragmentCardBack extends SubFragment<FragmentCardBackBinding> {

    public static FragmentCardBack newInstance() {
        return new FragmentCardBack();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.setContentView(inflater, R.layout.fragment_card_back);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onClickCommon(View v) {

    }


    @Override
    public void onRefresh() {

    }

}
