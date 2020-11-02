package com.genesis.apps.ui.common.dialog.middle;

import android.app.Activity;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.ExpnVO;
import com.genesis.apps.comm.util.DateUtil;
import com.genesis.apps.comm.util.PackageUtil;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.databinding.DialogInsightExpnDeleteBinding;
import com.genesis.apps.databinding.DialogMiddleTwoButtonBinding;
import com.genesis.apps.databinding.DialogUpdateBinding;
import com.genesis.apps.databinding.DialogUsedCarInfoBinding;
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

                    if (versionType.equalsIgnoreCase(VERSION_TYPE_M)) {
                        //필수업데이트
                        binding.tvTitle.setText(String.format(activity.getString(R.string.pop01_1), activity.getString(R.string.pop01_3)));
                        binding.btnCancel.setVisibility(View.GONE);
                        binding.btnOk.setText(R.string.dialog_common_4);
                    } else {
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
     * @param activity
     * @param ok
     * @param title
     * @param msg
     * @brief 공지사항 팝업
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
     * @param activity
     * @param ok
     * @param cancel
     * @brief 서비스 신청 안내
     * 버틀러 서비스 신청 안내 팝업
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


    public static void dialogUsedCarInfo(@NonNull Activity activity, final Runnable ok, final Runnable cancel) {
        if (activity.isFinishing()) {
            return;
        }
        activity.runOnUiThread(() ->
                new CustomDialog(activity, dialog -> {
                    DialogUsedCarInfoBinding binding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.dialog_used_car_info, null, false);
                    dialog.setContentView(binding.getRoot());

                    binding.btnOk.setOnClickListener(v -> {
                        dialog.dismiss();
                        if (ok != null) ok.run();
                    });
                }).show()
        );
    }

    public static void dialogOilDisconnect(@NonNull Activity activity, final Runnable ok, final Runnable cancel) {
        if (activity.isFinishing()) {
            return;
        }
        activity.runOnUiThread(() ->
                new CustomDialog(activity, dialog -> {
                    DialogMiddleTwoButtonBinding binding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.dialog_middle_two_button, null, false);
                    dialog.setContentView(binding.getRoot());

                    binding.tvTitle.setText(R.string.mg_con01_p01_1);
                    binding.tvMsg.setText(R.string.mg_con01_p01_2);
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




    public static void dialogInsightExpnDelete(@NonNull Activity activity, final Runnable ok, final Runnable cancel, ExpnVO item) {
        if (activity.isFinishing()) {
            return;
        }
        activity.runOnUiThread(() ->
                new CustomDialog(activity, dialog -> {
                    DialogInsightExpnDeleteBinding binding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.dialog_insight_expn_delete, null, false);
                    dialog.setContentView(binding.getRoot());
                    binding.tvMsg.setMovementMethod(new ScrollingMovementMethod());


                    binding.lExpn.tvExpnDtm.setVisibility(View.GONE);

                    binding.lExpn.tvExpnPlc.setText(item.getExpnPlc());
                    binding.lExpn.tvAccmMilg.setText(StringUtil.getDigitGroupingString(item.getAccmMilg())+"km");
                    binding.lExpn.tvExpnAmt.setText(StringUtil.getDigitGroupingString(item.getExpnAmt())+"원");
                    binding.lExpn.btnDelete.setVisibility(View.GONE);
                    binding.lExpn.btnModify.setVisibility(View.GONE);

                    int iconId=R.drawable.ic_service_potentiometer; //기타 이미지 변경 필요
                    int expnDivNmId = R.string.tm_exps01_21;

                    switch (item.getExpnDivNm()){
                        case VariableType.INSIGHT_EXPN_DIV_CODE_1000:
                            expnDivNmId = R.string.tm_exps01_13;
                            iconId = R.drawable.ic_service_refueling;
                            break;
                        case VariableType.INSIGHT_EXPN_DIV_CODE_2000:
                            expnDivNmId = R.string.tm_exps01_14;
                            iconId = R.drawable.ic_service_repair;
                            break;
                        case VariableType.INSIGHT_EXPN_DIV_CODE_3000:
                            expnDivNmId = R.string.tm_exps01_15;
                            iconId = R.drawable.ic_service_wash;
                            break;
                        case VariableType.INSIGHT_EXPN_DIV_CODE_4000:
                            expnDivNmId = R.string.tm_exps01_16;
                            iconId = R.drawable.ic_service_hometohome; //주차 아이콘으로 변경 필요
                            break;
                        case VariableType.INSIGHT_EXPN_DIV_CODE_5000:
                            expnDivNmId = R.string.tm_exps01_17;
                            iconId = R.drawable.ic_service_hometohome; //통행 아이콘으로 변경 필요
                            break;
                        case VariableType.INSIGHT_EXPN_DIV_CODE_6000:
                            expnDivNmId = R.string.tm_exps01_18;
                            iconId = R.drawable.ic_service_hometohome; //보험 아이콘으로 변경 필요
                            break;
                        case VariableType.INSIGHT_EXPN_DIV_CODE_7000:
                            expnDivNmId = R.string.tm_exps01_19;
                            iconId = R.drawable.ic_service_hometohome; //세금 아이콘으로 변경 필요
                            break;
                        case VariableType.INSIGHT_EXPN_DIV_CODE_8000:
                            expnDivNmId = R.string.tm_exps01_20;
                            iconId = R.drawable.ic_service_hometohome; //용품 아이콘으로 변경 필요
                            break;
                        case VariableType.INSIGHT_EXPN_DIV_CODE_9000:
                        default:
                            expnDivNmId = R.string.tm_exps01_21;
                            iconId = R.drawable.ic_service_potentiometer;
                            break;


                    }
                    binding.lExpn.tvExpnDivNm.setText(expnDivNmId);
                    binding.lExpn.ivIcon.setVisibility(View.GONE);

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


    public static void dialogInsightInputCancel(@NonNull Activity activity, final Runnable ok, final Runnable cancel) {
        if (activity.isFinishing()) {
            return;
        }
        activity.runOnUiThread(() ->
                getTwoButtonDialog(activity,
                        ok,
                        cancel,
                        R.string.tm_exps01_p02_1,
                        R.string.tm_exps01_p02_2,
                        R.string.dialog_common_1,
                        R.string.dialog_common_2
                ).show()
        );
    }


    public static void dialogInsightModifyCancel(@NonNull Activity activity, final Runnable ok, final Runnable cancel) {
        if (activity.isFinishing()) {
            return;
        }
        activity.runOnUiThread(() ->
                getTwoButtonDialog(activity,
                        ok,
                        cancel,
                        R.string.tm_exps01_p03_1,
                        R.string.tm_exps01_p03_2,
                        R.string.dialog_common_1,
                        R.string.dialog_common_2
                ).show()
        );
    }

    public static void dialogCarWashCancel(@NonNull Activity activity, final Runnable ok, final Runnable cancel) {
        if (activity.isFinishing()) {
            return;
        }
        activity.runOnUiThread(() ->
                getTwoButtonDialog(activity,
                        ok,
                        cancel,
                        R.string.cw_reserve_cancel_title,
                        R.string.cw_reserve_cancel_msg,
                        R.string.dialog_common_1,
                        R.string.dialog_common_2
                ).show()
        );
    }

    //R.layout.dialog_middle_two_button 쓰는 거 상당수를 이걸로 통합 가능할 듯..
    private static CustomDialog getTwoButtonDialog(
            @NonNull Activity activity,
            final Runnable ok,
            final Runnable cancel,
            final int titleId,
            final int msgId,
            final int okBtnTextId,
            final int cancelBtnTextId) {

        return new CustomDialog(activity, dialog -> {
            DialogMiddleTwoButtonBinding binding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.dialog_middle_two_button, null, false);
            dialog.setContentView(binding.getRoot());

            binding.tvTitle.setText(titleId);
            binding.tvMsg.setText(msgId);
            binding.tvMsg.setMovementMethod(new ScrollingMovementMethod());

            binding.btnCancel.setText(okBtnTextId);
            binding.btnOk.setText(cancelBtnTextId);

            binding.btnCancel.setOnClickListener(v -> {
                dialog.dismiss();
                if (cancel != null) cancel.run();
            });
            binding.btnOk.setOnClickListener(v -> {
                dialog.dismiss();
                if (ok != null) ok.run();
            });
        });
    }


}
