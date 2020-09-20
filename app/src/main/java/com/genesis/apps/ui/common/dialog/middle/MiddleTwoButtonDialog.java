package com.genesis.apps.ui.common.dialog.middle;

import android.app.Activity;
import android.view.LayoutInflater;

import com.genesis.apps.R;
import com.genesis.apps.databinding.DialogMiddleTwoButtonBinding;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

public class MiddleTwoButtonDialog {

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

}
