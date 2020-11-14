package com.genesis.apps.ui.common.dialog.bottom;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.vo.VehicleVO;
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
    private static final int HOUR3 = 1000 * 60 * 60 * 3;
    private static final int RESERVE_MAX_DAY = 7;

    private SubActivity activity;
    private VehicleVO mainVehicle;
    private String priceMaybe;

    private boolean inputConfirmed = false;
    private boolean isNow = true;
    private Calendar pickedDate;
    private String parsedDate;
    private EditText msgInput;
    private String msg;

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

        ui.btnOk.setOnClickListener(view -> {
            inputConfirmed = true;
            msg = msgInput.getText().toString();
            dismiss();
        });
    }

    private void initCarInfo() {
        ui.lDiaBottomNowOrReserveCarInfoPanel.tvServiceReqCarModel.setText(mainVehicle.getMdlNm());
        ui.lDiaBottomNowOrReserveCarInfoPanel.tvServiceReqCarNumber.setText(mainVehicle.getCarRgstNo());
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
        //지금 버튼 :
        ui.rbDiaBottomNowBtn.setOnClickListener(v -> {
            setNow(true);
        });

        //예약 버튼
        ui.rbDiaBottomReserveBtn.setOnClickListener(v -> {
            setNow(false);
        });

        //예약-날짜 선택 버튼
        ui.tvDiaBottomNowOrReserveDatePickBtn.setOnClickListener(v -> {
            pickDate();
        });
    }

    //선택을 저장하고 예약 날짜 뷰 접기/펴기(펼 때 picker 호출)
    private void setNow(boolean now) {
        isNow = now;
        if (now) {
            InteractionUtil.collapse(ui.lDiaBottomNowOrReserveDate, null);
        } else {
            InteractionUtil.expand(ui.lDiaBottomNowOrReserveDate, null);
            pickDate();
        }
    }

    private void pickDate() {
        //호출된 시점을 기준시간으로 설정
        pickedDate = Calendar.getInstance(Locale.getDefault());

        //todo 실제로 picker 띄우기
        // TEST : 날짜 결정 임시로 72시간 뒤로 설정
        pickedDate.add(Calendar.DAY_OF_MONTH, 3);

        //결정된 날짜/시간을 pickedDate에 저장하고 picker 뷰에 출력
        ui.tvDiaBottomNowOrReserveDatePickBtn.setText(
                DateUtil.getDate(pickedDate.getTime(), DateUtil.DATE_FORMAT_yyyy_MM_dd_e_hh_mm));

        //todo : 기껏 picker 호출해놓고 날짜선택을 취소하면
//        setNow(true);
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
            return DateUtil.getDate(pickedDate.getTime(), DateUtil.DATE_FORMAT_yyyyMMddHHmm);
        }
    }

    public String getMsg() {
        return msg;
    }
}
