package com.genesis.apps.ui.main.service;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

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
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ServiceDriveReqActivity extends SubActivity<ActivityServiceDriveReq1Binding> {
    private static final String TAG = ServiceDriveReqActivity.class.getSimpleName();

    private static final int FROM = 0;
    private static final int TO = 1;

    private static final int NEXT_BTN_LACK_INPUT = 0;
    private static final int NEXT_BTN_OPEN_TO_ADDRESS = 1;
    private static final int NEXT_BTN_NEED_TO_ADDRESS = 2;
    private static final int NEXT_BTN_ASK_PRICE = 3;
    private static final int NEXT_BTN_REQ_SERVICE = 4;

    private VehicleVO mainVehicle;
    private AddressVO addressVO;

    private TextInputLayout fromDetailLayout;
    private TextInputLayout toDetailLayout;
    private TextInputEditText fromDetail;
    private TextInputEditText toDetail;

    private final int[] layouts = {
            R.layout.activity_service_drive_req_1,
            R.layout.activity_service_drive_req_2,
    };

    private ConstraintSet[] constraintSets = new ConstraintSet[layouts.length];

    //키보드로 입력하고 엔터 누르면 밑에 다음 버튼 누른 거랑 같도록 하는 리스너
    private EditText.OnEditorActionListener editorActionListener = (textView, actionId, keyEvent) -> {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            if (!TextUtils.isEmpty(textView.getText().toString())) {
                onClickNextBtn();
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
//        onClickSearchFromAddressBtn();
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
                //todo impl
//                onClickSearchFromAddressBtn();

                //onActivityResult()에서 이 값을 세팅하고 다음 게 이미 펼쳐져있도록 하면 될 듯?
                //아니면 펼쳐지는 장면을 onActivityResult()에서 보여주거나(이미 입력된 값을 수정하고왔을 때는 펼치기 이펙트 재생 안 해야겠지?)
                ui.tvServiceDriveReqSearchFromAddressBtn.setText("지도 액티비티에서 가져온 출발지 주소");
                ui.tvServiceDriveReqSearchFromAddressBtn.setTextAppearance(R.style.ServiceDrive_SearchAddress_HasData);
                ui.tvServiceDriveReqSearchFromAddressBtn.setBackground(getDrawable(R.drawable.ripple_bg_ffffff_stroke_141414));
                ui.ivServiceDriveReqPleaseInputXxx.setText(R.string.service_drive_input_02);
                ui.tvServiceDriveReqFromTitle.setVisibility(View.VISIBLE);
                fromDetailLayout.setVisibility(View.VISIBLE);
                ui.tvServiceDriveReqNextBtn.setVisibility(View.VISIBLE);
                break;

            //도착주소 검색버튼
            case R.id.tv_service_drive_req_search_to_address_btn:
                //onActivityResult()에서 이 값을 세팅하고 다음 게 이미 펼쳐져있도록 하면 될 듯?
                //아니면 펼쳐지는 장면을 onActivityResult()에서 보여주거나(이미 입력된 값을 수정하고왔을 때는 펼치기 이펙트 재생 안 해야겠지?)
                //todo impl
                ui.tvServiceDriveReqSearchToAddressBtn.setText("지도 액티비티에서 가져온 도착지 주소");
                ui.tvServiceDriveReqSearchToAddressBtn.setTextAppearance(R.style.ServiceDrive_SearchAddress_HasData);
                ui.tvServiceDriveReqSearchToAddressBtn.setBackground(getDrawable(R.drawable.ripple_bg_ffffff_stroke_141414));
                ui.ivServiceDriveReqPleaseInputXxx.setText(R.string.service_drive_input_04);
                ui.tvServiceDriveReqToTitle.setVisibility(View.VISIBLE);
                toDetailLayout.setVisibility(View.VISIBLE);
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
                .putExtra(KeyNames.KEY_NAME_MAP_SEARCH_TITLE_ID, R.string.service_drive_address_search_title)
                .putExtra(KeyNames.KEY_NAME_MAP_SEARCH_MSG_ID, R.string.service_drive_address_search_msg);
//                .putExtra(KeyNames.KEY_NAME_MAP_SEARCH_DIRECT_OPEN, true);//바로 다음 화면 넘기기

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
        int msgId = getIntent().getIntExtra(KeyNames.KEY_NAME_SERVICE_DRIVE_REQ_START_MSG, -1);

        //메지지가 있으면 보여준다
        if (msgId > 0) {
            new Handler().postDelayed(
                    () -> SnackBarUtil.show(this, getString(msgId)),
                    100);
        }

        mainVehicle = (VehicleVO) getIntent().getSerializableExtra(KeyNames.KEY_NAME_VEHICLE_VO);
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
        Log.d(TAG, "init: ");
        //차종, 번호판 정보 표시
        //todo  : 키보드 올라와있는 동안 안 보여야됨  ui.lServiceDriveReqTopPanel
        ui.lServiceDriveReqTopPanel.tvServiceReqCarModel.setText(mainVehicle.getMdlNm());
        ui.lServiceDriveReqTopPanel.tvServiceReqCarNumber.setText(mainVehicle.getCarRgstNo());


        fromDetailLayout = ui.lServiceDriveReqInputFromDetail;
        toDetailLayout = ui.lServiceDriveReqInputToDetail;
        fromDetail = ui.tietServiceDriveReqInputFromAddressDetail;
        toDetail = ui.tietServiceDriveReqInputToAddressDetail;

        initConstraintSets();
        setTextListener();
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

    public void setTextListener() {
        fromDetail.setOnEditorActionListener(editorActionListener);
        fromDetail.addTextChangedListener(new TextWatcher() {
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
                setError(fromDetailLayout, hasInput(editable.toString()));
            }
        });

        toDetail.setOnEditorActionListener(editorActionListener);
        toDetail.addTextChangedListener(new TextWatcher() {
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
                setError(toDetailLayout, hasInput(editable.toString()));
            }
        });
    }

    //세부주소 칸에 입력한 값이 있는지 없는지 검사
    private boolean hasInput(String input) {
        return 0 < input.length();
    }

    //에러 메시지 표시하기/끄기
    private void setError(TextInputLayout textInputLayout, boolean hasData) {
        if (hasData) {
            textInputLayout.setError(null);
        } else {
            textInputLayout.setError(getString(R.string.service_drive_input_00));
        }
    }

    private void onClickNextBtn() {
        int work = determineWork();
        Log.d(TAG, "onClickNextBtn: " + work);

        switch (work) {

            case NEXT_BTN_OPEN_TO_ADDRESS:
                doTransition();
                //not break
            case NEXT_BTN_NEED_TO_ADDRESS:
                //todo 도착지 주소검색 버튼 누름
                //todo 키보드 숨김처리 명시적으로 필요한지 확인하여 반영
                break;

            case NEXT_BTN_ASK_PRICE:
                //todo [1] 서버에 예상 가격 물어보기
                // 그동안 로딩 뷰스텁 띄우기(점 세 개짜리).
                // 로딩하는 동안 다음버튼 비활성화

                // 예상가격 받으면 저장. 다음 버튼 재활성화
                // 서비스 불가 지역 : 다음 비활성 및 뷰스텁 갱신
                // 오류 : 다음 비활성 및 재시도 뷰스텁 재시도하면 [1]을 다시 실행
                //


                break;

            case NEXT_BTN_REQ_SERVICE:
                //todo : 지금할래, 예약할래 대화상자 호출
                // 만들어야 됨... 레이아웃만 있음
                // R.layout.dialog_bottom_now_or_reserve 보드 167쪽

                break;

            case NEXT_BTN_LACK_INPUT:   //입력 미비 todo 디폴트랑 분리할지 고민
            default:
                //do nothing
                break;
        }


    }

    //입력 상태를 검사하여 "다음" 버튼이 처리할 내용 결정
    private int determineWork() {
        Log.d(TAG, "determineWork: ");

        boolean hasFromDetail = hasInput(fromDetail.getText().toString());
        boolean hasToDetail = hasInput(toDetail.getText().toString());

        //세부 주소 빈칸인지 검사
        checkDetailAddress(hasFromDetail, hasToDetail);

        //도착지 검색 버튼 해금 :
        //버튼이 안 보이고 출발 상세주소가 입력된 상태
        if (ui.tvServiceDriveReqSearchToAddressBtn.getVisibility() == View.GONE && hasFromDetail) {
            return NEXT_BTN_OPEN_TO_ADDRESS;
        }

        //도착지 주소 안 고르고 뒤로 와버려서
        // 도착지 주소 버튼이 기본값으로 보이는 경우
        if (ui.tvServiceDriveReqSearchToAddressBtn.getVisibility() == View.VISIBLE &&
                ui.tvServiceDriveReqSearchToAddressBtn.getText().toString().equals(getString(R.string.service_drive_input_09))) {
            return NEXT_BTN_NEED_TO_ADDRESS;
        }

        //세부 주소 입력 미비 : 여기서부터는 [[입력이 하나라도 없으면]] 바로 리턴해버림
        if (!hasFromDetail || !hasToDetail) {
            //todo 저장해둔 예상가격이 있으면 삭제 : 예상가격 보고 나서 세부주소를 지웠다는 뜻임. 여기서 해야되나, 리턴 받은 뒤에 해도 되나 고민
            return NEXT_BTN_LACK_INPUT;
        }

        //여기까지 왔으면 입력은 다 됐음

        //if(가격을 모른다)
        return NEXT_BTN_ASK_PRICE;

        //예상 가격 값이 있다
        //todo
//        return NEXT_BTN_REQ_SERVICE;
    }

    private void checkDetailAddress(boolean hasFromDetail, boolean hasToDetail) {
        //출발지 세부 주소가 빈칸인 채로 진행하려고 하는 경우(입력 창에 손도 안 댄 경우) 입력 창에 에러메시지 출력
        setError(fromDetailLayout, hasFromDetail);

        //도착지 세부 주소는 입력 창이 해금된 상태에서만 검사
        if (toDetailLayout.getVisibility() == View.VISIBLE) {
            setError(toDetailLayout, hasToDetail);
        }
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
