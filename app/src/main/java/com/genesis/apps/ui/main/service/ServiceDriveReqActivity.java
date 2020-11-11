package com.genesis.apps.ui.main.service;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintSet;
import androidx.transition.ChangeBounds;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.AddressVO;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.databinding.ActivityServiceDriveReq1Binding;
import com.genesis.apps.ui.common.activity.SubActivity;

public class ServiceDriveReqActivity extends SubActivity<ActivityServiceDriveReq1Binding> {
    private static final String TAG = ServiceDriveReqActivity.class.getSimpleName();
    public static final String START_MSG = "StartMsg";

    private static final int FROM = 0;
    private static final int TO = 1;

    private VehicleVO mainVehicle;
    private AddressVO addressVO;

    private final int[] layouts = {
            R.layout.activity_service_drive_req_1,
            R.layout.activity_service_drive_req_2,
    };

    private ConstraintSet[] constraintSets = new ConstraintSet[layouts.length];

    private EditText.OnEditorActionListener editorActionListener = (textView, actionId, keyEvent) -> {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            if (!TextUtils.isEmpty(textView.getText().toString())) {
                doTransition();
            }
        }
        return false;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDataFromIntent();//데이터 제대로 안 들어있으면 액티비티 종료처리까지 함

        setResizeScreen();
        setContentView(layouts[0]);

        init();

        //시작하자마자 출발 주소 검색 화면으로 넘어감
        onClickSearchFromAddressBtn();
    }

    @Override
    public void onClickCommon(View v) {
        switch (v.getId()) {
            //이용 내역
            case R.id.tv_titlebar_text_btn:
                startActivitySingleTop(new Intent(this, ServiceDriveHistoryActivity.class), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                break;

            //출발주소 검색버튼
            case R.id.tv_service_drive_req_search_from_address_btn:
                onClickSearchFromAddressBtn();

                //onActivityResult()에서 이 값을 세팅하고 다음 게 이미 펼쳐져있도록 하면 될 듯?
                //아니면 펼쳐지는 장면을 onActivityResult()에서 보여주거나(이미 입력된 값을 수정하고왔을 때는 펼치기 이펙트 재생 안 해야겠지?)
//                ui.tvServiceDriveReqSearchFromAddressBtn.setText("지도 액티비티에서 가져온 출발지 주소");
//                ui.tvServiceDriveReqSearchFromAddressBtn.setTextAppearance(R.style.ServiceDrive_SearchAddress_HasData);
//                ui.tvServiceDriveReqSearchFromAddressBtn.setBackground(getDrawable(R.drawable.ripple_bg_ffffff_stroke_141414));
//                ui.ivServiceDriveReqPleaseInputXxx.setText(R.string.service_drive_input_02);
//                ui.tvServiceDriveReqFromTitle.setVisibility(View.VISIBLE);
//                ui.lServiceDriveReqInputFromDetail.setVisibility(View.VISIBLE);
//                ui.tvServiceDriveReqNextBtn.setVisibility(View.VISIBLE);
                break;

            //도착주소 검색버튼
            case R.id.tv_service_drive_req_search_to_address_btn:
                //onActivityResult()에서 이 값을 세팅하고 다음 게 이미 펼쳐져있도록 하면 될 듯?
                //아니면 펼쳐지는 장면을 onActivityResult()에서 보여주거나(이미 입력된 값을 수정하고왔을 때는 펼치기 이펙트 재생 안 해야겠지?)
                ui.tvServiceDriveReqSearchToAddressBtn.setText("지도 액티비티에서 가져온 도착지 주소");
                ui.tvServiceDriveReqSearchToAddressBtn.setTextAppearance(R.style.ServiceDrive_SearchAddress_HasData);
                ui.tvServiceDriveReqSearchToAddressBtn.setBackground(getDrawable(R.drawable.ripple_bg_ffffff_stroke_141414));
                ui.ivServiceDriveReqPleaseInputXxx.setText(R.string.service_drive_input_04);
                ui.tvServiceDriveReqToTitle.setVisibility(View.VISIBLE);
                ui.lServiceDriveReqInputToDetail.setVisibility(View.VISIBLE);
                break;

            //다음 버튼
            case R.id.tv_service_drive_req_next_btn:
                onClickNextBtn();

            default:
                //do nothing
                break;
        }
    }

    private void onClickSearchFromAddressBtn() {
        Intent intent = new Intent(this, MapSearchMyPositionActivity.class)
                .putExtra(KeyNames.KEY_NAME_ADDR, addressVO)
                .putExtra(KeyNames.KEY_NAME_MAP_SEARCH_TITLE_ID, R.string.service_drive_map_title)
                .putExtra(KeyNames.KEY_NAME_MAP_SEARCH_MSG_ID, R.string.sm_r_rsv02_01_11);

        startActivitySingleTop(intent, RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
    }

    @Override
    public void setViewModel() {

    }

    @Override
    public void setObserver() {

    }

    @Override
    public void getDataFromIntent() {
        //대리운전 기사 못 찾아서 취소 누르고 여기로 떨어지는 경우, 스낵바 메시지를 들고온다. 없으면 무효값(-1)으로 초기화.
        int msgId = getIntent().getIntExtra(START_MSG, -1);

        //메지지가 있으면 보여준다
        if (msgId > 0) {
            new Handler().postDelayed(
                    () -> SnackBarUtil.show(this, getString(msgId)),
                    100);
        }

        mainVehicle = (VehicleVO) getIntent().getSerializableExtra(VehicleVO.VEHICLE_VO);
        if (mainVehicle == null) {
            Log.d(TAG, "getDataFromIntent: 주차량 정보 없음");
            exitPage("", ResultCodes.REQ_CODE_NORMAL.getCode());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void init() {
        //차종, 번호판 정보 표시
        ui.lServiceDriveReqTopPanel.tvServiceReqCarModel.setText(mainVehicle.getMdlNm());
        ui.lServiceDriveReqTopPanel.tvServiceReqCarNumber.setText(mainVehicle.getCarRgstNo());


        initConstraintSets();
        initView();
        ui.setActivity(this);
    }

    private void initConstraintSets() {
        for (int i = 0; i < layouts.length; ++i) {
            constraintSets[i] = new ConstraintSet();

            if (i == 0)
                constraintSets[i].clone(ui.lServiceDriveReqContainer);
            else
                constraintSets[i].clone(this, layouts[i]);
        }
    }

    public void initView() {
        //출발지 상세주소 입력 완료하면 도착지 검색버튼 뜨도록 리스너 붙임(키패드 엔터)
        ui.tietServiceDriveReqInputFromAddressDetail.setOnEditorActionListener(editorActionListener);


        //출발지 상세주소 입력 완료하면 도착지 검색버튼 뜨도록 리스너 붙임(화면 맨 아래 다음 버튼)
        ui.tvServiceDriveReqNextBtn.setOnClickListener(view -> {
            if (getCurrentFocus() instanceof TextView) {
                if (!TextUtils.isEmpty(((TextView) getCurrentFocus()).getText().toString())) {
                    doTransition();

                }
            }
        });
    }

    private void onClickNextBtn() {

    }

    /**
     * 도착지 주소 검색버튼 트랜지션
     */
    private void doTransition() {
        if (ui.tvServiceDriveReqToTitle.getVisibility() == View.GONE) {
            Transition changeBounds = new ChangeBounds();
            changeBounds.setInterpolator(new OvershootInterpolator());
            TransitionManager.beginDelayedTransition(ui.lServiceDriveReqContainer);
            constraintSets[TO].applyTo(ui.lServiceDriveReqContainer);

            ui.ivServiceDriveReqPleaseInputXxx.setText(R.string.service_drive_input_03);
        }
    }
}
