package com.genesis.apps.ui.common.dialog.bottom;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate;
import com.appeaser.sublimepickerlibrary.helpers.SublimeOptions;
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;
import com.genesis.apps.R;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.util.CalenderUtil;
import com.genesis.apps.comm.util.DateUtil;
import com.genesis.apps.comm.util.InteractionUtil;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.databinding.DialogBottomNowOrReserveBinding;
import com.genesis.apps.ui.common.activity.SubActivity;

import java.util.Calendar;
import java.util.Locale;

public class BottomDialogReqNowOrReserve extends BaseBottomDialog<DialogBottomNowOrReserveBinding> {
    private static final String TAG = BottomDialogReqNowOrReserve.class.getSimpleName();
    private static final int MSG_MAX_LENGTH = 40;
    private static final int RESERVE_MIN_HOUR = 3;
    private static final int RESERVE_MAX_DAY = 7;

    private SubActivity activity;
    private VehicleVO mainVehicle;
    private String priceMaybe;

    private boolean inputConfirmed;
    private boolean isNow = true;
    private boolean isValidReserve;
    private Calendar pickedTime;
    private EditText msgInput;
    private String msg;

    private long reserveLimitStart;
    private long reserveLimitEnd;
    private CalenderUtil.Callback calendarCallback = new CalenderUtil.Callback() {
        @Override
        public void onCancelled() {
            Log.d(TAG, "onCancelled: ");

            //예약 날짜 선택을 취소하면 실시간 호출로 선택 변경
            ui.rbDiaBottomNowBtn.setChecked(true);
        }

        @Override
        public void onDateTimeRecurrenceSet(SelectedDate selectedDate,
                                            int hourOfDay, int minute,
                                            SublimeRecurrencePicker.RecurrenceOption recurrenceOption,
                                            String recurrenceRule) {
            Log.d(TAG, "onDateTimeRecurrenceSet: ");

            pickedTime = selectedDate.getFirstDate();
            pickedTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            pickedTime.set(Calendar.MINUTE, minute);

            //선택 결과를 예약 날짜 뷰에 출력
            showDatePickResult();
        }
    };

    public BottomDialogReqNowOrReserve(@NonNull SubActivity activity, VehicleVO vehicleVO, String price, int theme) {
        super(activity, theme);
        this.activity = activity;
        mainVehicle = vehicleVO;
        priceMaybe = price;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_bottom_now_or_reserve);
        setAllowOutTouch(true);

        ui.setMaxLength(MSG_MAX_LENGTH);
        //todo 예약날짜 펼친 상태로 요청사항 입력하려고 하면 키보드가 입력창을 가림 OTL

        initCarInfo();
        initPrice();
        initTextInputLayouts();
        initRadioBtn();
        initOkBtn();
    }

    private void initCarInfo() {
        ui.lDiaBottomNowOrReserveCarInfoPanel.tvServiceReqCarModel.setText(mainVehicle.getMdlNm());
        if(TextUtils.isEmpty(mainVehicle.getCarRgstNo())){
            ui.lDiaBottomNowOrReserveCarInfoPanel.tvServiceReqCarNumber.setVisibility(View.GONE);
        }else{
            ui.lDiaBottomNowOrReserveCarInfoPanel.tvServiceReqCarNumber.setVisibility(View.VISIBLE);
            ui.lDiaBottomNowOrReserveCarInfoPanel.tvServiceReqCarNumber.setText(mainVehicle.getCarRgstNo());
        }
    }

    private void initPrice() {
        ui.lDiaBottomNowOrReserveCarInfoPanel.lServiceDriveReqPrice.getRoot().setVisibility(View.VISIBLE);
        ui.lDiaBottomNowOrReserveCarInfoPanel.lServiceDriveReqPrice.tvServicePriceMaybe.setText(StringUtil.getPriceString(priceMaybe));
    }

    private void initTextInputLayouts() {
        msgInput = ui.tietDiaBottomNowOrReserveRequestInput;

        msgInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //do nothing
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //do nothing
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //글자수 넘치면 자른다
                if (editable.length() > MSG_MAX_LENGTH) {
                    msgInput.setText(TextUtils.substring(editable, 0, MSG_MAX_LENGTH));
                    msgInput.setSelection(MSG_MAX_LENGTH);
                }
            }
        });
    }

    private void initRadioBtn() {
        ui.rgDiaBottomNowOrReserveRadiogroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_dia_bottom_now_btn:
                    Log.d(TAG, "radio selected : now");
                    setNow(true);
                    break;

                case R.id.rb_dia_bottom_reserve_btn:
                    Log.d(TAG, "radio selected : reserve");
                    setNow(false);
                    break;

                default:
                    //do nothing
                    break;
            }
        });

        ui.tvDiaBottomNowOrReserveDatePickBtn.setOnClickListener(v -> {
            showDatePicker();
        });
    }

    //선택을 저장하고 예약 날짜 뷰 접기/펴기(펼 때 picker 호출)
    private void setNow(boolean now) {
        Log.d(TAG, "setNow: " + now);

        isNow = now;
        if (now) {
            InteractionUtil.collapse(ui.lDiaBottomNowOrReserveDate, null);
        } else {
            InteractionUtil.expand(ui.lDiaBottomNowOrReserveDate, null);
            showDatePicker();
        }
    }

    private void showDatePicker() {
        CalenderUtil datePicker = new CalenderUtil();
        datePicker.setCallback(calendarCallback);

        //예약 시간 최소 3시간 후부터
        Calendar limitStart = Calendar.getInstance(Locale.getDefault());
        limitStart.add(Calendar.HOUR_OF_DAY, RESERVE_MIN_HOUR);
        reserveLimitStart = limitStart.getTimeInMillis();

        //최대 7일후 까지
        Calendar limitEnd = Calendar.getInstance(Locale.getDefault());
        limitEnd.add(Calendar.DAY_OF_MONTH, RESERVE_MAX_DAY);
        reserveLimitEnd = limitEnd.getTimeInMillis();

        Pair<Boolean, SublimeOptions> optionsPair = datePicker.getOptions(
                SublimeOptions.ACTIVATE_DATE_PICKER | SublimeOptions.ACTIVATE_TIME_PICKER,
                false,
                reserveLimitStart,
                reserveLimitEnd,
                limitStart
        );

        // Options
        // Valid options
        Bundle bundle = new Bundle();
        bundle.putParcelable("SUBLIME_OPTIONS", optionsPair.second);
        datePicker.setArguments(bundle);

        datePicker.setStyle(CalenderUtil.STYLE_NO_TITLE, 0);
        datePicker.show(activity.getSupportFragmentManager(), "SUBLIME_PICKER");
    }

    //선택된 시간이 유효하면 그 시간을 표시, 아니면 안내문을 표시
    private void showDatePickResult() {

        //선택한 시간이 유효 범위(3시간 후 ~ 7일 후) 이내에 있는지 검사(picker UI에서 day 단위까지만 필터링 돼서 H 이하 단위는 여기서 검사해야 됨)하고 검사 결과를 저장
        validateReserveTime();

        ui.tvDiaBottomNowOrReserveDatePickBtn.setText(
                isValidReserve ?
                        DateUtil.getDate(pickedTime.getTime(), DateUtil.DATE_FORMAT_yyyy_MM_dd_e_hh_mm) :
                        activity.getString(R.string.sd_out_of_range_reserve)
        );
    }

    private void validateReserveTime() {
        //유효성 검사를 위해 자료형 맞춤
        long picked = pickedTime.getTimeInMillis();

        isValidReserve = (reserveLimitStart <= picked && picked <= reserveLimitEnd);
    }

    private void initOkBtn() {
        ui.btnOk.setOnClickListener(view -> {

            inputConfirmed = validateSelect();

            if (!inputConfirmed) {
                return;
            }

            msg = msgInput.getText().toString();
            dismiss();
        });
    }

    private boolean validateSelect() {
        //즉시 호출이면 ok, 예약이면 선택 날짜 유효성 검사도 통과된 상태여야 함(선택 처리 마지막 단계에서 검사 결과 저장까지 처리함)
        return isNow || isValidReserve;
    }


    public boolean isInputConfirmed() {
        return inputConfirmed;
    }

    public boolean isNow() {
        return isNow;
    }

    public String getReserveDate() {
        if (isNow) {
            return "";
        } else {
            return DateUtil.getDate(pickedTime.getTime(), DateUtil.DATE_FORMAT_yyyyMMddHHmm);
        }
    }

    public String getMsg() {
        return msg;
    }
}
