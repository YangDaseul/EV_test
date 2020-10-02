package com.genesis.apps.ui.common.dialog.middle;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.comm.util.PackageUtil;
import com.genesis.apps.databinding.DialogMiddleTwoButtonBinding;
import com.genesis.apps.databinding.DialogUpdateBinding;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

public class MiddleDialog {

    public static void remoteExitPop(@NonNull Activity activity, final Runnable ok, final Runnable cancel) {
        if (activity.isFinishing()) {
            return;
        }

        activity.runOnUiThread(() ->
                new CustomDialog(activity, dialog -> {
                    DialogMiddleTwoButtonBinding binding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.dialog_middle_two_button, null, false);
                    dialog.setContentView(binding.getRoot());

                    binding.tvTitle.setText(R.string.sm_emg01_p03_title_1);
                    binding.tvMsg.setText(R.string.sm_emg01_p03_msg_1);

                    binding.btnCancel.setOnClickListener(v -> {
                        dialog.dismiss();
                        if (cancel != null) cancel.run();
                    });
                    binding.btnOk.setOnClickListener(v -> {
                        dialog.dismiss();
                        if (ok != null) ok.run();
                    });
                }).show()
        );
    }

    private static final String VERSION_TYPE_M = "M"; //필수업데이트)
    public static void updatePopUp(@NonNull Activity activity, final Runnable ok, final Runnable cancel, String newVersion, String versionType) {
        if (activity.isFinishing()) {
            return;
        }

        activity.runOnUiThread(() ->
                new CustomDialog(activity, dialog -> {
                    DialogUpdateBinding binding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.dialog_update, null, false);
                    dialog.setContentView(binding.getRoot());

                    if(versionType.equalsIgnoreCase(VERSION_TYPE_M)){
                        //필수업데이트
                        binding.tvTitle.setText(String.format(activity.getString(R.string.pop01_1), activity.getString(R.string.pop01_3)));
                        binding.btnCancel.setVisibility(View.GONE);
                        binding.btnOk.setText(R.string.dialog_common_4);
                    }else{
                        //선택업데이트
                        binding.tvTitle.setText(String.format(activity.getString(R.string.pop01_1), activity.getString(R.string.pop01_2)));
                    }

                    binding.tvMsg.setText(String.format(activity.getString(R.string.pop01_4), String.format(activity.getString(R.string.pop01_5), PackageUtil.getApplicationVersionName(activity, activity.getPackageName())), String.format(activity.getString(R.string.pop01_6), newVersion)));

                    binding.btnCancel.setOnClickListener(v -> {
                        dialog.dismiss();
                        if (cancel != null) cancel.run();
                    });
                    binding.btnOk.setOnClickListener(v -> {
                        dialog.dismiss();
                        if (ok != null) ok.run();
                    });
                }).show()
        );
    }


}
