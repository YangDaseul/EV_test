package com.genesis.apps.ui.fragment.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.genesis.apps.R;
import com.genesis.apps.databinding.Frame4pBinding;
import com.genesis.apps.ui.dialog.TestDialog;
import com.genesis.apps.ui.fragment.SubFragment;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import androidx.annotation.Nullable;


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
//                baseActivity.startActivitySingleTop(new Intent(getActivity(), WebviewActivity.class).putExtra("url","https://www.genesis.com/kr/ko/genesis-membership/life-service.html"), RequestCodes.REQ_CODE_LOGIN.getCode());

                TestDialog testDialog = new TestDialog(getContext(), R.style.BottomSheetDialogTheme);
                testDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        testDialog.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                testDialog.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);
////                                BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);
//                            }
//                        },2000);
                    }
                });
                testDialog.show();;
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