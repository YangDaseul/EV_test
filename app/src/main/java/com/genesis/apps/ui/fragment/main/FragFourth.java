package com.genesis.apps.ui.fragment.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.RequestCodes;
import com.genesis.apps.databinding.Frame4pBinding;
import com.genesis.apps.ui.activity.LoginActivity;
import com.genesis.apps.ui.activity.WebviewActivity;
import com.genesis.apps.ui.fragment.SubFragment;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.theartofdev.edmodo.cropper.CropImage;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ListAdapter;

import static android.app.Activity.RESULT_OK;


public class FragFourth extends SubFragment<Frame4pBinding> {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.setContentView(inflater, R.layout.frame_4p);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        me.tvName4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                baseActivity.startActivitySingleTop(new Intent(getActivity(), WebviewActivity.class).putExtra("url","https://www.genesis.com/kr/ko/genesis-membership/life-service.html"), RequestCodes.REQ_CODE_LOGIN.getCode());
            }
        });
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}