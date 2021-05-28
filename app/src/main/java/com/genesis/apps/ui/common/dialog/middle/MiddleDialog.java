package com.genesis.apps.ui.common.dialog.middle;

import android.app.Activity;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.databinding.DataBindingUtil;

import com.airbnb.paris.Paris;
import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.ExpnVO;
import com.genesis.apps.comm.util.DateUtil;
import com.genesis.apps.comm.util.PackageUtil;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.databinding.DialogCarwashApplyInfoBinding;
import com.genesis.apps.databinding.DialogInsightExpnDeleteBinding;
import com.genesis.apps.databinding.DialogMiddleTwoButtonBinding;
import com.genesis.apps.databinding.DialogOilReconnectInfoBinding;
import com.genesis.apps.databinding.DialogServiceCantReserveInfoBinding;
import com.genesis.apps.databinding.DialogServiceRemoteInfoBinding;
import com.genesis.apps.databinding.DialogServiceRemoteNotTargetBinding;
import com.genesis.apps.databinding.DialogServiceRemoteNotTimeBinding;
import com.genesis.apps.databinding.DialogServiceSimplePayInfoBinding;
import com.genesis.apps.databinding.DialogSimilarInfoBinding;
import com.genesis.apps.databinding.DialogUpdateBinding;
import com.genesis.apps.databinding.DialogUsedCarInfoBinding;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;

import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.RunnableFuture;

public class MiddleDialog {

    public static void dialogBtrExit(@NonNull Activity activity, final Runnable ok, final Runnable cancel) {
        if (activity.isFinishing()) {
            return;
        }

        activity.runOnUiThread(() ->
                new CustomDialog(activity, dialog -> {
                    DialogMiddleTwoButtonBinding binding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.dialog_middle_two_button, null, false);
                    dialog.setContentView(binding.getRoot());

                    binding.tvTitle.setText(R.string.gm_bt04_p01_1);
                    binding.tvMsg.setText(R.string.gm_bt04_p01_2);
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



    public static void dialogAbnormal(@NonNull Activity activity, String title, String msg, Runnable ok) {
        if (activity.isFinishing()) {
            return;
        }

        activity.runOnUiThread(() ->
                getOneButtonDialog(activity,
                        ok,
                        title,
                        msg,
                        R.string.dialog_common_4
                ).show()
        );
    }

    /**
     *
     * @param activity
     * @param ok
     * @brief 에스트레픽 서비스 가입 안내 팝업
     */
    public static void dialogNeedRegistSTC(@NonNull Activity activity, Runnable ok) {
        if (activity.isFinishing()) {
            return;
        }

        activity.runOnUiThread(() ->
                getOneButtonDialog(activity,
                        ok,
                        R.string.sm_evsb01_p03_1,
                        R.string.sm_evsb01_p03_2,
                        R.string.dialog_common_4
                ).show()
        );
    }

    public static void dialogEVServiceInfo(@NonNull Activity activity, Runnable ok) {
        if (activity.isFinishing()) {
            return;
        }

        activity.runOnUiThread(() ->
                getOneButtonDialog(activity,
                        ok,
                        R.string.sm_evsb01_p04_1,
                        R.string.sm_evsb01_p04_2,
                        R.string.dialog_common_4
                ).show()
        );
    }


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
                        binding.tvTitleSub.setText(R.string.pop01_3);
                        binding.btnCancel.setVisibility(View.GONE);
                        Paris.style(binding.btnOk).apply(R.style.BigBtn_Black);
                        binding.btnOk.setText(R.string.dialog_common_4);
                    } else {
                        //선택업데이트
                        binding.tvTitleSub.setText(R.string.pop01_2);
                    }

                    binding.tvVersionC.setText(PackageUtil.changeVersionToAppFormat(PackageUtil.getApplicationVersionName(activity, activity.getPackageName())));
                    binding.tvVersionN.setText(PackageUtil.changeVersionToAppFormat(newVersion));

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






    //MY 차고 차량 옵션
    public static void dialogCarOption(@NonNull Activity activity, String msg, final Runnable ok) {
        if (activity.isFinishing()) {
            return;
        }
        activity.runOnUiThread(() ->
                getOneButtonDialog(activity,
                        ok,
                        activity.getString(R.string.gm_carlst_p01_12),
                        msg,
                        R.string.comm_word_1
                ).show()
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

                    binding.btnCancel.setText(R.string.dialog_common_6);
                    binding.btnOk.setText(R.string.dialog_common_3);
//                    Paris.style(binding.btnCancel).apply(R.style.BigBtn_Black2);

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


    /**
     * @param activity
     * @param ok
     * @param cancel
     * @brief 주유포인트 재 연동 안내 팝업
     */
    public static void dialogOilReConnectInfo(@NonNull Activity activity, final Runnable ok, final Runnable cancel) {
        if (activity.isFinishing()) {
            return;
        }
        activity.runOnUiThread(() ->
                new CustomDialog(activity, dialog -> {
                    DialogOilReconnectInfoBinding binding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.dialog_oil_reconnect_info, null, false);
                    dialog.setContentView(binding.getRoot());

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
     * @brief 간편결제 서비스 이용 안내 팝업
     */
    public static void dialogServiceSimplePayInfo(@NonNull Activity activity, final OnSingleClickListener ok, final OnSingleClickListener cancel) {
        if (activity.isFinishing()) {
            return;
        }
        activity.runOnUiThread(() ->
                new CustomDialog(activity, dialog -> {
                    DialogServiceSimplePayInfoBinding binding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.dialog_service_simple_pay_info, null, false);
                    dialog.setContentView(binding.getRoot());

                    binding.btnCancel.setOnClickListener(v -> {
                        dialog.dismiss();
                        if (cancel != null) {
                            v.setTag(R.id.item, binding.cb.isChecked());
                            cancel.onSingleClick(v);
                        }
                    });
                    binding.btnOk.setOnClickListener(v -> {
                        dialog.dismiss();
                        if (ok != null) {
                            v.setTag(R.id.item, binding.cb.isChecked());
                            ok.onSingleClick(v);
                        }
                    });
                }).show()
        );
    }


    /**
     * @param activity
     * @param ok
     * @param cancel
     * @brief 원격진단안내팝업
     */
    public static void dialogSimilarInfo(@NonNull Activity activity, final Runnable ok, final Runnable cancel) {
        if (activity.isFinishing()) {
            return;
        }
        activity.runOnUiThread(() ->
                new CustomDialog(activity, dialog -> {
                    DialogSimilarInfoBinding binding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.dialog_similar_info, null, false);
                    dialog.setContentView(binding.getRoot());

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
     * @brief 원격진단안내팝업 EV 체크
     */
    public static void dialogServiceRemoteInfo(@NonNull Activity activity, boolean isEv, final Runnable ok, final Runnable cancel) {
        if (activity.isFinishing()) {
            return;
        }
        activity.runOnUiThread(() ->
                new CustomDialog(activity, dialog -> {
                    DialogServiceRemoteInfoBinding binding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.dialog_service_remote_info, null, false);
                    binding.setIsEv(isEv);
                    dialog.setContentView(binding.getRoot());

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
     * @brief 원격진단안내팝업
     */
    public static void dialogServiceRemoteInfo(@NonNull Activity activity, final Runnable ok, final Runnable cancel) {
        dialogServiceRemoteInfo(activity, false, ok, cancel);
    }

    /**
     * 원격 진단 신청 불가 팝업.
     *
     * @param activity
     * @param message
     * @param ok
     */
    public static void dialogServiceRemoteRegisterErr(@NonNull Activity activity, String message, Runnable ok) {
        if (activity.isFinishing()) {
            return;
        }

        activity.runOnUiThread(() ->
                getOneButtonDialog(activity,
                        ok,
                        activity.getString(R.string.sm_remote01_dialog_title_error),
                        message,
                        R.string.dialog_common_4
                ).show()
        );
    }

    /**
     * @biref 충전소 예약 취소 팝업
     * @param activity
     * @param ok
     * @param cancel
     */
    public static void dialogChargeReserveCancel(@NonNull Activity activity, final Runnable ok, final Runnable cancel) {
        if (activity.isFinishing()) {
            return;
        }
        activity.runOnUiThread(() ->
                getTwoButtonDialog(activity,
                        ok,
                        cancel,
                        R.string.sm_evsb02_p01_1,
                        R.string.sm_evsb02_p01_2,
                        R.string.dialog_common_1,
                        R.string.dialog_common_2
                ).show()
        );
    }

    /**
     * @biref 충전소 예약 취소 팝업(충전소 상세 화면)
     * @param activity
     * @param ok
     * @param cancel
     */
    public static void dialogChargeReserveCancelFromDetail(@NonNull Activity activity, final Runnable ok, final Runnable cancel) {
        if (activity.isFinishing()) {
            return;
        }
        activity.runOnUiThread(() ->
                getTwoButtonDialog(activity,
                        ok,
                        cancel,
                        R.string.sm_evss04_p01_1,
                        R.string.sm_evss04_p01_2,
                        R.string.dialog_common_1,
                        R.string.dialog_common_2
                ).show()
        );
    }

    /**
     * @biref 원격진단 예약 취소 팝업
     *
     *
     * @param activity
     * @param ok
     * @param cancel
     */
    public static void dialogServiceRemoteCancel(@NonNull Activity activity, final Runnable ok, final Runnable cancel) {
        if (activity.isFinishing()) {
            return;
        }
        activity.runOnUiThread(() ->
                getTwoButtonDialog(activity,
                        ok,
                        cancel,
                        R.string.sm_romte02_p01_1,
                        R.string.sm_romte02_p01_2,
                        R.string.dialog_common_1,
                        R.string.dialog_common_2
                ).show()
        );
    }

    /**
     * @biref 주유포인트 연동 종료 팝업
     *
     *
     * @param activity
     * @param ok
     * @param cancel
     */
    public static void dialogOilExit(@NonNull Activity activity, final Runnable ok, final Runnable cancel) {
        if (activity.isFinishing()) {
            return;
        }
        activity.runOnUiThread(() ->
                getTwoButtonDialog(activity,
                        ok,
                        cancel,
                        R.string.mg_con02_p01_1,
                        R.string.mg_con02_p01_2,
                        R.string.dialog_common_1,
                        R.string.dialog_common_2
                ).show()
        );
    }

    /**
     * @biref 주유포인트 연동 불가 팝업
     *
     *
     * @param activity
     * @param ok
     */
    public static void dialogOilReject(@NonNull Activity activity, String msg, Runnable ok) {
        if (activity.isFinishing()) {
            return;
        }

        activity.runOnUiThread(() ->
                getOneButtonDialog(activity,
                        ok,
                       "서비스 준비 중",
                        msg,
                        R.string.dialog_common_4
                ).show()
        );
    }


    /**
     * 원격 진단 신청 이용 시간이 아닌 안내 팝업.
     * @param activity
     * @param ok
     */
    public static void dialogServiceRemoteNotServiceTime(@NonNull Activity activity, Runnable ok) {
        if (activity.isFinishing()) {
            return;
        }

        activity.runOnUiThread(() ->
                new CustomDialog(activity, dialog -> {
                    DialogServiceRemoteNotTimeBinding binding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.dialog_service_remote_not_time, null, false);
                    dialog.setContentView(binding.getRoot());

                    binding.btnOk.setOnClickListener(v -> {
                        dialog.dismiss();
                        if (ok != null) ok.run();
                    });
                }).show()
        );
    }

    /**
     * 원격 진단 대상 차량 불가 팝업
     * @param activity
     * @param ok
     */
    public static void dialogServiceRemoteNotServiceNotTarget(@NonNull Activity activity, Runnable ok) {
        if (activity.isFinishing()) {
            return;
        }
        activity.runOnUiThread(() ->
                new CustomDialog(activity, dialog -> {
                    DialogServiceRemoteNotTargetBinding binding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.dialog_service_remote_not_target, null, false);
                    dialog.setContentView(binding.getRoot());

                    binding.btnOk.setOnClickListener(v -> {
                        dialog.dismiss();
                        if (ok != null) ok.run();
                    });
                }).show()
        );
    }


    public static void dialogCommonOneButton(@NonNull Activity activity, @StringRes int titleResId, String msg, Runnable ok) {
        if (activity.isFinishing()) {
            return;
        }

        activity.runOnUiThread(() ->
                getOneButtonDialog(activity,
                        ok,
                        activity.getString(titleResId),
                        msg,
                        R.string.dialog_common_4
                ).show()
        );
    }

    public static void dialogServiceRemoteOneButton(@NonNull Activity activity, @StringRes int titleResId, @StringRes int messageResId, Runnable ok) {
        if (activity.isFinishing()) {
            return;
        }

        activity.runOnUiThread(() ->
                getOneButtonDialog(activity,
                        ok,
                        activity.getString(titleResId),
                        activity.getString(messageResId),
                        R.string.dialog_common_4
                ).show()
        );
    }

    public static void dialogCommonTwoButton(@NonNull Activity activity, @StringRes int titleResId, String msg, Runnable ok, Runnable cancel) {
        if (activity.isFinishing()) {
            return;
        }

        activity.runOnUiThread(() ->
                getTwoButtonDialog(activity,
                        ok,
                        cancel,
                        activity.getString(titleResId),
                        msg,
                        R.string.dialog_common_7,
                        R.string.dialog_common_4
                ).show()
        );
    }

    public static void dialogServiceRemoteTwoButton(@NonNull Activity activity, @StringRes int titleResId, @StringRes int messageResId, Runnable ok, Runnable cancel) {
        if (activity.isFinishing()) {
            return;
        }

        activity.runOnUiThread(() ->
                getTwoButtonDialog(activity,
                        ok,
                        cancel,
                        titleResId,
                        messageResId,
                        R.string.dialog_common_1,
                        R.string.dialog_common_2
                ).show()
        );
    }
    public static void dialogServiceRemoteTwoButton(@NonNull Activity activity, String title, String message, Runnable ok, Runnable cancel) {
        if (activity.isFinishing()) {
            return;
        }

        activity.runOnUiThread(() ->
                getTwoButtonDialog(activity,
                        ok,
                        cancel,
                        title,
                        message,
                        R.string.dialog_common_1,
                        R.string.dialog_common_2
                ).show()
        );
    }
    public static void dialogLogout(@NonNull Activity activity, String title, String message, Runnable ok, Runnable cancel) {
        if (activity.isFinishing()) {
            return;
        }

        activity.runOnUiThread(() ->
                getTwoButtonDialog(activity,
                        ok,
                        cancel,
                        title,
                        message,
                        R.string.dialog_common_7,
                        R.string.dialog_common_9
                ).show()
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

    public static void dialogLeasingCarCancel(@NonNull Activity activity, final Runnable ok, final Runnable cancel) {
        if (activity.isFinishing()) {
            return;
        }
        activity.runOnUiThread(() ->
                new CustomDialog(activity, dialog -> {
                    DialogMiddleTwoButtonBinding binding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.dialog_middle_two_button, null, false);
                    dialog.setContentView(binding.getRoot());

                    binding.tvTitle.setText(R.string.gm_carlst_01_p04_1);
                    binding.tvMsg.setText(R.string.gm_carlst_01_p04_2);
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


    public static void dialogDriveApplyCancel(@NonNull Activity activity, final Runnable ok, final Runnable cancel) {
        if (activity.isFinishing()) {
            return;
        }
        activity.runOnUiThread(() ->
                new CustomDialog(activity, dialog -> {
                    DialogMiddleTwoButtonBinding binding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.dialog_middle_two_button, null, false);
                    dialog.setContentView(binding.getRoot());

                    binding.tvTitle.setText(R.string.sm_drv01_p04_1);
                    binding.tvMsg.setText(R.string.sm_drv01_p04_2);
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

//                    Paris.style(binding.btnCancel).apply(R.style.BigBtn_Black2);
                    binding.btnCancel.setText(R.string.dialog_common_6);
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

    public static void dialogServiceCantReserveInfo(@NonNull Activity activity, final Runnable ok) {
        if (activity.isFinishing()) {
            return;
        }
        activity.runOnUiThread(() ->
                new CustomDialog(activity, dialog -> {
                    DialogServiceCantReserveInfoBinding binding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.dialog_service_cant_reserve_info, null, false);
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


                    binding.lExpn.tvExpnDtm.setVisibility(View.VISIBLE);
                    binding.lExpn.tvExpnDtm.setText(DateUtil.getDate(DateUtil.getDefaultDateFormat(item.getExpnDtm(), DateUtil.DATE_FORMAT_yyyyMMddHHmmss), DateUtil.DATE_FORMAT_yyyy_mm_dd_dot));

                    binding.lExpn.tvExpnPlc.setText(item.getExpnPlc());
                    if(TextUtils.isEmpty(item.getAccmMilg())){
                        binding.lExpn.tvTitleAccmMilg.setVisibility(View.GONE);
                        binding.lExpn.tvAccmMilg.setVisibility(View.GONE);
                    }else{
                        binding.lExpn.tvTitleAccmMilg.setVisibility(View.VISIBLE);
                        binding.lExpn.tvAccmMilg.setVisibility(View.VISIBLE);
                        binding.lExpn.tvAccmMilg.setText(StringUtil.getDigitGroupingString(item.getAccmMilg())+"km");
                    }
                    binding.lExpn.tvExpnAmt.setText(StringUtil.getDigitGroupingString(item.getExpnAmt()) + "원");
                    binding.lExpn.btnDelete.lWhole.setVisibility(View.GONE);
                    binding.lExpn.btnModify.setVisibility(View.GONE);
                    binding.lExpn.tvExpnDivNm.setText(VariableType.getExpnDivNM(StringUtil.isValidString(item.getExpnDivCd())));

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
     * @brief 중복 로그인 안내 팝업
     */
    public static void dialogDuplicateLogin(@NonNull Activity activity, final Runnable ok, final Runnable cancel) {
        if (activity.isFinishing()) {
            return;
        }
        activity.runOnUiThread(() ->
                getTwoButtonDialog(activity,
                        ok,
                        cancel,
                        R.string.pop03_1,
                        R.string.pop03_2,
                        R.string.dialog_common_1,
                        R.string.dialog_common_2
                ).show()
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

    /**
     * @param activity
     * @param ok
     * @param cancel
     * @brief 하자재발 신청 종료 팝업
     */
    public static void dialogServiceRelapseApplyExit(@NonNull Activity activity, final Runnable ok, final Runnable cancel) {
        if (activity.isFinishing()) {
            return;
        }
        activity.runOnUiThread(() ->
                getTwoButtonDialog(activity,
                        ok,
                        cancel,
                        R.string.sm_flaw03_01_1,
                        R.string.sm_flaw03_01_2,
                        R.string.dialog_common_1,
                        R.string.dialog_common_2
                ).show()
        );
    }

    /**
     * @param activity
     * @param ok
     * @param cancel
     * @brief 긴급출동 신청 종료 팝업
     */
    public static void dialogServiceSOSApplyExit(@NonNull Activity activity, final Runnable ok, final Runnable cancel) {
        if (activity.isFinishing()) {
            return;
        }
        activity.runOnUiThread(() ->
                getTwoButtonDialog(activity,
                        ok,
                        cancel,
                        R.string.sm_emgc01_p03_4,
                        R.string.sm_emgc01_p03_5,
                        R.string.dialog_common_1,
                        R.string.dialog_common_2
                ).show()
        );
    }


    /**
     * @param activity
     * @param ok
     * @param cancel
     * @brief 찾아가는 충전 서비스 신청 종료 팝업
     */
    public static void dialogServiceChargeApplyExit(@NonNull Activity activity, final Runnable ok, final Runnable cancel) {
        if (activity.isFinishing()) {
            return;
        }
        activity.runOnUiThread(() ->
                getTwoButtonDialog(activity,
                        ok,
                        cancel,
                        R.string.sm_cggo_01_12,
                        R.string.sm_cggo_01_9,
                        R.string.dialog_common_1,
                        R.string.dialog_common_2
                ).show()
        );
    }

    /**
     * @param activity
     * @param ok
     * @param cancel
     * @brief 긴급출동 취소 안내 팝업
     */
    public static void dialogServiceSOSApplyCancel(@NonNull Activity activity, final Runnable ok, final Runnable cancel) {
        if (activity.isFinishing()) {
            return;
        }
        activity.runOnUiThread(() ->
                getTwoButtonDialog(activity,
                        ok,
                        cancel,
                        R.string.sm_emgc02_p01_1,
                        R.string.sm_emgc02_p01_2,
                        R.string.dialog_common_1,
                        R.string.dialog_common_2
                ).show()
        );
    }

    /**
     * @param activity
     * @param ok
     * @param cancel
     * @brief 찾아가는 출동 취소 안내 팝업
     */
    public static void dialogServiceChargeSOSApplyCancel(@NonNull Activity activity, final Runnable ok, final Runnable cancel) {
        if (activity.isFinishing()) {
            return;
        }
        activity.runOnUiThread(() ->
                getTwoButtonDialog(activity,
                        ok,
                        cancel,
                        R.string.sm_cggO02_p01,
                        R.string.sm_cggO02_p02,
                        R.string.dialog_common_1,
                        R.string.dialog_common_2
                ).show()
        );
    }

    /**
     * @param activity
     * @param ok
     * @param cancel
     * @brief 정비예약 뒤로가기 경고 팝업
     */
    public static void dialogServiceBack(@NonNull Activity activity, final Runnable ok, final Runnable cancel) {
        if (activity.isFinishing()) {
            return;
        }
        activity.runOnUiThread(() ->
                getTwoButtonDialog(activity,
                        ok,
                        cancel,
                        R.string.sm_r_rsv_p03_1,
                        R.string.sm_r_rsv_p03_2,
                        R.string.dialog_common_1,
                        R.string.dialog_common_2
                ).show()
        );
    }

    /**
     * @param activity
     * @param ok
     * @param cancel
     * @brief 정비예약 뒤로가기 경고 팝업
     */
    public static void dialogPrecheckBack(@NonNull Activity activity, final Runnable ok, final Runnable cancel) {
        if (activity.isFinishing()) {
            return;
        }
        activity.runOnUiThread(() ->
                getTwoButtonDialog(activity,
                        ok,
                        cancel,
                        R.string.service_01,
                        R.string.service_02,
                        R.string.dialog_common_1,
                        R.string.dialog_common_2
                ).show()
        );
    }

    /**
     * @param activity
     * @param ok
     * @param cancel
     * @brief 로그인 안내 팝업
     */
    public static void dialogLogin(@NonNull Activity activity, final Runnable ok, final Runnable cancel) {
        if (activity.isFinishing()) {
            return;
        }
        activity.runOnUiThread(() ->
                getTwoButtonDialog(activity,
                        ok,
                        cancel,
                        R.string.sm01_p01_1,
                        R.string.sm01_p01_2,
                        R.string.dialog_common_1,
                        R.string.dialog_common_2
                ).show()
        );
    }



    /**
     * @param activity
     * @param ok
     * @param cancel
     * @brief 세차 서비스 신청하기
     */
    public static void dialogCarWashReserve(@NonNull Activity activity, final Runnable ok, final Runnable cancel, String brnhNm, String mdlNm, String godsName) {
        if (activity.isFinishing()) {
            return;
        }
        activity.runOnUiThread(() ->
                new CustomDialog(activity, dialog -> {
                    DialogCarwashApplyInfoBinding binding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.dialog_carwash_apply_info, null, false);
                    dialog.setContentView(binding.getRoot());
                    binding.tvBrnhNm.setText(brnhNm);
                    binding.tvContetns.setText(mdlNm+" "+String.format(Locale.getDefault(),activity.getString(R.string.cw_reserve_msg), godsName));

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


    //세차 예약 취소
    public static void dialogCarWashCancel(@NonNull Activity activity, String msg, final Runnable ok) {
        if (activity.isFinishing()) {
            return;
        }
        activity.runOnUiThread(() ->
                getTwoButtonDialog(activity,
                        ok,
                        null,
                        activity.getString(R.string.cw_reserve_cancel_title),
                        msg,
                        R.string.dialog_common_1,
                        R.string.dialog_common_2
                ).show()
        );
    }

    //세차 서비스 평가
    public static void dialogCarWashReview(@NonNull Activity activity, final Runnable ok) {
        if (activity.isFinishing()) {
            return;
        }
        activity.runOnUiThread(() ->
                getTwoButtonDialog(activity,
                        ok,
                        null,
                        R.string.service_review_title,
                        R.string.service_review_msg,
                        R.string.dialog_common_1,
                        R.string.dialog_common_2
                ).show()
        );
    }

    //대리운전 취소
    public static void dialogServiceDriveCancel(@NonNull Activity activity, int msgId, final Runnable ok) {
        if (activity.isFinishing()) {
            return;
        }

        activity.runOnUiThread(() ->
                getTwoButtonDialog(activity,
                        ok,
                        null,
                        R.string.sd_cancel_title,
                        msgId,
                        R.string.dialog_common_1,
                        R.string.dialog_common_2
                ).show()
        );
    }


    //정비 서비스 예약 취소
    public static void dialogServiceReserveCancel(@NonNull Activity activity, String msg, final Runnable ok) {
        if (activity.isFinishing()) {
            return;
        }

        activity.runOnUiThread(() ->
                getTwoButtonDialog(activity,
                        ok,
                        null,
                        activity.getString(R.string.sm_r_rsv05_p02_1),
                        msg,
                        R.string.dialog_common_1,
                        R.string.dialog_common_2
                ).show()
        );
    }

    /**
     * @param activity
     * @param ok
     * @param cancel
     * @brief 픽업앤충전 서비스 신청 종료 팝업
     */
    public static void dialogServiceChargeBtrReqExit(@NonNull Activity activity, final Runnable ok, final Runnable cancel) {
        if (activity.isFinishing()) {
            return;
        }
        activity.runOnUiThread(() ->
                getTwoButtonDialog(activity,
                        ok,
                        cancel,
                        R.string.service_charge_btr_02,
                        R.string.service_charge_btr_popup_msg_00,
                        R.string.dialog_common_1,
                        R.string.dialog_common_2
                ).show()
        );
    }

    /**
     * @param activity
     * @param ok
     * @param cancel
     * @brief 픽업앤충전 서비스 예약 취소 팝업
     */
    public static void dialogServiceChargeBtrCancel(@NonNull Activity activity, final Runnable ok, final Runnable cancel) {
        if (activity.isFinishing()) {
            return;
        }
        activity.runOnUiThread(() ->
                getTwoButtonDialog(activity,
                        ok,
                        cancel,
                        R.string.service_charge_btr_06,
                        R.string.service_charge_btr_popup_msg_02,
                        R.string.dialog_common_1,
                        R.string.dialog_common_2
                ).show()
        );
    }

    /**
     * @param activity
     * @param ok
     * @param cancel
     * @brief 블루월넛 회원가입 안내 팝업
     */
    public static void dialogBlueWalnutSingIn(@NonNull Activity activity, final Runnable ok, final Runnable cancel) {
        if (activity.isFinishing()) {
            return;
        }
        activity.runOnUiThread(() ->
                getTwoButtonDialog(activity,
                        ok,
                        cancel,
                        R.string.pop03_1,
                        R.string.service_charge_btr_popup_msg_03,
                        R.string.dialog_common_7,
                        R.string.dialog_common_4
                ).show()
        );
    }

    /**
     * @param activity
     * @param ok
     * @param cancel
     * @brief 주 카드 삭제 팝업 1_주 카드 이외 카드가 존재 하는 경우
     */
    public static void dialogDeletePayCard01(@NonNull Activity activity, final Runnable ok, final Runnable cancel) {
        if (activity.isFinishing()) {
            return;
        }
        activity.runOnUiThread(() ->
                getTwoButtonDialog(activity,
                        ok,
                        cancel,
                        R.string.pop03_1,
                        R.string.pay03_p01_1,
                        R.string.dialog_common_7,
                        R.string.dialog_common_4
                ).show()
        );
    }
    /**
     * @param activity
     * @param ok
     * @param cancel
     * @brief 주 카드 삭제 팝업 2_주 카드 이외 카드가 없는 경우
     */
    public static void dialogDeletePayCard02(@NonNull Activity activity, final Runnable ok, final Runnable cancel) {
        if (activity.isFinishing()) {
            return;
        }
        activity.runOnUiThread(() ->
                getTwoButtonDialog(activity,
                        ok,
                        cancel,
                        R.string.pop03_1,
                        R.string.pay03_p02_1,
                        R.string.dialog_common_7,
                        R.string.dialog_common_4
                ).show()
        );
    }

    /**
     * @param activity
     * @param ok
     * @param cancel
     * @brief 디지털월렛 EV 충전 크레딧 부족 안내 팝업
     */
    public static void dialogEvCretPntLackInfo(@NonNull Activity activity, @StringRes int titleResId, String message, Runnable ok, Runnable cancel) {
        if (activity.isFinishing()) {
            return;
        }

        activity.runOnUiThread(() ->
                getTwoButtonDialog(activity,
                        ok,
                        cancel,
                        activity.getString(titleResId),
                        message,
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
            final int cancelTextId,
            final int okTextId) {

        return getTwoButtonDialog(
                activity, ok, cancel,
                activity.getString(titleId),
                activity.getString(msgId),
                cancelTextId, okTextId);
    }

    private static CustomDialog getTwoButtonDialog(
            @NonNull Activity activity,
            final Runnable ok,
            final Runnable cancel,
            final String title,
            final String msg,
            final int cancelTextId,
            final int okTextId) {

        return new CustomDialog(activity, dialog -> {
            DialogMiddleTwoButtonBinding binding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.dialog_middle_two_button, null, false);
            dialog.setContentView(binding.getRoot());

            binding.tvTitle.setText(title);
            binding.tvMsg.setText(msg);
            binding.tvMsg.setMovementMethod(new ScrollingMovementMethod());

            binding.btnCancel.setText(cancelTextId);
            binding.btnOk.setText(okTextId);

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


    private static CustomDialog getOneButtonDialog(
            @NonNull Activity activity,
            final Runnable ok,
            final String title,
            final String msg,
            final int okTextId) {

        return new CustomDialog(activity, dialog -> {
            DialogMiddleTwoButtonBinding binding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.dialog_middle_two_button, null, false);
            dialog.setContentView(binding.getRoot());

            binding.tvTitle.setText(title);
            binding.tvMsg.setText(msg);
            binding.tvMsg.setMovementMethod(new ScrollingMovementMethod());

            binding.btnCancel.setVisibility(View.GONE);
            binding.btnOk.setText(okTextId);

            binding.btnOk.setOnClickListener(v -> {
                dialog.dismiss();
                if (ok != null) ok.run();
            });
        });
    }

    private static CustomDialog getOneButtonDialog(
            @NonNull Activity activity,
            final Runnable ok,
            final int titleId,
            final int msgId,
            final int okTextId) {

        return getOneButtonDialog(
                activity, ok,
                activity.getString(titleId),
                activity.getString(msgId),
                okTextId);
    }


    /**
     * @brief 서비스 예약 중복 안내
     */
    public static void dialogServiceInfo(@NonNull Activity activity, final Runnable ok) {
        if (activity.isFinishing()) {
            return;
        }
        activity.runOnUiThread(() ->
                getOneButtonDialog(activity,
                        ok,
                        R.string.sm01_p02_1,
                        R.string.sm01_p02_2,
                        R.string.dialog_common_4
                ).show()
        );
    }

    /**
     * @brief 서비스 가입 성공 팝업
     */
    public static void dialogJoin(@NonNull Activity activity, final Runnable ok) {
        if (activity.isFinishing()) {
            return;
        }
        activity.runOnUiThread(() ->
                getOneButtonDialog(activity,
                        ok,
                        R.string.pop01_8,
                        R.string.pop01_9,
                        R.string.dialog_common_4
                ).show()
        );
    }


    /**
     * @brief 서비스 이용제한 안내
     */
    public static void dialogNetworkError(@NonNull Activity activity, final Runnable ok) {
        if (activity.isFinishing()) {
            return;
        }
        activity.runOnUiThread(() ->
                getOneButtonDialog(activity,
                        ok,
                        R.string.pop02_1,
                        R.string.pop02_2,
                        R.string.dialog_common_4
                ).show()
        );
    }

}
