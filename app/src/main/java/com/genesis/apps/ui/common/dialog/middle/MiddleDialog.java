package com.genesis.apps.ui.common.dialog.middle;

import android.app.Activity;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.comm.util.PackageUtil;
import com.genesis.apps.databinding.DialogMiddleTwoButtonBinding;
import com.genesis.apps.databinding.DialogUpdateBinding;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

public class MiddleDialog {

    public static void dialogRemoteExit(@NonNull Activity activity, final Runnable ok, final Runnable cancel) {
        if (activity.isFinishing()) {
            return;
        }

        activity.runOnUiThread(() ->
                new CustomDialog(activity, dialog -> {
                    DialogMiddleTwoButtonBinding binding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.dialog_middle_two_button, null, false);
                    dialog.setContentView(binding.getRoot());

                    binding.tvTitle.setText(R.string.sm_emg01_p03_title_1);
                    binding.tvMsg.setText(R.string.sm_emg01_p03_msg_1);
                    binding.tvMsg.setMovementMethod(new ScrollingMovementMethod());

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
    public static void dialogUpdate(@NonNull Activity activity, final Runnable ok, final Runnable cancel, String newVersion, String versionType) {
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


    /**
     * @brief 공지사항 팝업
     *
     * @param activity
     * @param ok
     * @param title
     * @param msg
     */
    public static void dialogNoti(@NonNull Activity activity, final Runnable ok, String title, String msg) {
        if (activity.isFinishing()) {
            return;
        }

        activity.runOnUiThread(() ->
                new CustomDialog(activity, dialog -> {
                    DialogMiddleTwoButtonBinding binding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.dialog_middle_two_button, null, false);
                    dialog.setContentView(binding.getRoot());

                    binding.tvTitle.setText(title);
                    binding.tvMsg.setText(msg);
                    binding.btnCancel.setVisibility(View.GONE);
                    binding.btnOk.setText(R.string.dialog_common_4);
                    binding.btnOk.setOnClickListener(v -> {
                        dialog.dismiss();
                        if (ok != null) ok.run();
                    });
                }).show()
        );
    }


    public static void dialogGPS(@NonNull Activity activity, final Runnable ok, final Runnable cancel) {
        if (activity.isFinishing()) {
            return;
        }
        activity.runOnUiThread(() ->
                new CustomDialog(activity, dialog -> {
                    DialogMiddleTwoButtonBinding binding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.dialog_middle_two_button, null, false);
                    dialog.setContentView(binding.getRoot());

                    binding.tvTitle.setText(R.string.r_bt06_p01_title_1);
                    binding.tvMsg.setText(R.string.r_bt06_p01_msg_1);
                    binding.tvMsg.setMovementMethod(new ScrollingMovementMethod());

                    binding.btnCancel.setText(R.string.dialog_common_4);
                    binding.btnOk.setText(R.string.dialog_common_3);

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


    /**
     * @brief 서비스 신청 안내
     * 버틀러 서비스 신청 안내 팝업
     * @param activity
     * @param ok
     * @param cancel
     */
    public static void dialogBtrApply(@NonNull Activity activity, final Runnable ok, final Runnable cancel) {
        if (activity.isFinishing()) {
            return;
        }
        activity.runOnUiThread(() ->
                new CustomDialog(activity, dialog -> {
                    DialogMiddleTwoButtonBinding binding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.dialog_middle_two_button, null, false);
                    dialog.setContentView(binding.getRoot());

                    binding.tvTitle.setText(R.string.gm_bt01_p02_1);
                    binding.tvMsg.setText(R.string.gm_bt01_p02_2);
                    binding.tvMsg.setMovementMethod(new ScrollingMovementMethod());

                    binding.btnCancel.setText(R.string.dialog_common_1);
                    binding.btnOk.setText(R.string.dialog_common_2);

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



    public static void dialogLeasingCarApplyCancel(@NonNull Activity activity, final Runnable ok, final Runnable cancel) {
        if (activity.isFinishing()) {
            return;
        }
        activity.runOnUiThread(() ->
                new CustomDialog(activity, dialog -> {
                    DialogMiddleTwoButtonBinding binding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.dialog_middle_two_button, null, false);
                    dialog.setContentView(binding.getRoot());

                    binding.tvTitle.setText(R.string.gm_carlst_01_p05_1);
                    binding.tvMsg.setText(R.string.gm_carlst_01_p05_2);
                    binding.tvMsg.setMovementMethod(new ScrollingMovementMethod());

                    binding.btnCancel.setText(R.string.dialog_common_1);
                    binding.btnOk.setText(R.string.dialog_common_2);

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

    public static void dialogPermission(@NonNull Activity activity, final Runnable ok, final Runnable cancel) {
        if (activity.isFinishing()) {
            return;
        }
        activity.runOnUiThread(() ->
                new CustomDialog(activity, dialog -> {
                    DialogMiddleTwoButtonBinding binding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.dialog_middle_two_button, null, false);
                    dialog.setContentView(binding.getRoot());

                    binding.tvTitle.setText(R.string.int02_17);
                    binding.tvMsg.setText(R.string.int02_18);
                    binding.tvMsg.setMovementMethod(new ScrollingMovementMethod());

                    binding.btnCancel.setText(R.string.dialog_common_4);
                    binding.btnOk.setText(R.string.dialog_common_3);

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
