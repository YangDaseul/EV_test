package com.genesis.apps.ui.main.service;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.view.animation.OvershootInterpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
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
    private static final String PRICE_NO_DATA = "";

    private static final int FROM = 0;
    private static final int TO = 1;

    private static final int NEXT_BTN_LACK_INPUT = 0;
    private static final int NEXT_BTN_OPEN_TO_ADDRESS = 1;
    private static final int NEXT_BTN_NEED_TO_ADDRESS = 2;
    private static final int NEXT_BTN_ASK_PRICE = 3;
    private static final int NEXT_BTN_REQ_SERVICE = 4;

    private VehicleVO mainVehicle;
    private AddressVO[] addressVO = new AddressVO[2];
    private String priceMaybe = PRICE_NO_DATA;    //TODO 예상가격 자료형 숫자인지 글자인지 확인
    private boolean buttonActive = true;

    private TextInputLayout fromDetailLayout;
    private TextInputLayout toDetailLayout;
    private TextInputEditText fromDetail;
    private TextInputEditText toDetail;

    //TODO  이거 이제 필요없지않나? 재생 되기 전에 다음 액티비티가 덮어버릴텐데
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

        //TODO  이거 이제 필요없지않나? 재생 되기 전에 다음 액티비티가 덮어버릴텐데
        setContentView(layouts[FROM]);

        init();

        //시작하자마자 출발 주소 검색 화면으로 넘어감
        onClickSearchAddressBtn(FROM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //출발지 주소 얻어옴
        if (resultCode == ResultCodes.REQ_CODE_SERVICE_DRIVE_FROM_MAP.getCode()) {
            setAddressData(
                    FROM,
                    data,
                    ui.tvServiceDriveReqSearchFromAddressBtn,
                    R.string.service_drive_input_02,
                    new View[]{ui.tvServiceDriveReqFromTitle, fromDetailLayout, ui.tvServiceDriveReqNextBtn}
            );

        }
        //도착지 주소 얻어옴
        else if (resultCode == ResultCodes.REQ_CODE_SERVICE_DRIVE_TO_MAP.getCode()) {
            setAddressData(
                    TO,
                    data,
                    ui.tvServiceDriveReqSearchToAddressBtn,
                    R.string.service_drive_input_04,
                    new View[]{ui.tvServiceDriveReqToTitle, toDetailLayout}
            );
        }
        //뭔가 잘못됨?
        else {
            Log.d(TAG, "onActivityResult: unexpected result code");
        }
    }

    /**
     * 검색된 주소를 화면에 출력
     *
     * @param where      : 출발지/도착지 구분
     * @param data       : 지도 액티비티에서 가져온 데이터
     * @param addressBtn : 출발지 버튼/도착지 버튼 구분. 여기에 주소를 출력함.
     * @param topMsgId   : 입력창 위에 있는 안내 메시지
     * @param views      : 다음 단계 입력을 위해 해금해야 할 뷰
     */
    private void setAddressData(int where, Intent data, TextView addressBtn, int topMsgId, View[] views) {
        try {
            addressVO[where] = (AddressVO) data.getSerializableExtra(KeyNames.KEY_NAME_ADDR);
        } catch (Exception exception) {
            Log.d(TAG, "setAddressData: 주소 데이터 추출 실패");
            return;
        }

        addressBtn.setText(addressVO[where].getAddrRoad());
        addressBtn.setTextAppearance(R.style.ServiceDrive_SearchAddress_HasData);
        addressBtn.setBackground(getDrawable(R.drawable.ripple_bg_ffffff_stroke_141414));
        ui.ivServiceDriveReqPleaseInputXxx.setText(topMsgId);

        for (View v : views) {
            v.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClickCommon(View v) {
        Log.d(TAG, "onClickCommon: " + v.getId());

        switch (v.getId()) {
            //이용 내역
            case R.id.tv_titlebar_text_btn:
                startActivitySingleTop(new Intent(this, ServiceDriveHistoryActivity.class), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                break;

            //출발주소 검색버튼
            case R.id.tv_service_drive_req_search_from_address_btn:
                onClickSearchAddressBtn(FROM);
                break;

            //도착주소 검색버튼
            case R.id.tv_service_drive_req_search_to_address_btn:
                onClickSearchAddressBtn(TO);
                break;

            //다음 버튼
            case R.id.tv_service_drive_req_next_btn:
                onClickNextBtn();

            default:
                Log.d(TAG, "onClickCommon: unexpected click event : " + v.getId());
                break;
        }
    }

    private void onClickSearchAddressBtn(int where) {
        Intent intent = new Intent(this, MapSearchMyPositionActivity.class)
                .putExtra(KeyNames.KEY_NAME_ADDR, addressVO[where])
                .putExtra(KeyNames.KEY_NAME_MAP_SEARCH_TITLE_ID, where == FROM ? R.string.service_drive_address_search_from_title : R.string.service_drive_address_search_to_title)
                .putExtra(KeyNames.KEY_NAME_MAP_SEARCH_MSG_ID, where == FROM ? R.string.service_drive_address_search_from_msg : R.string.service_drive_address_search_to_msg);

        if (addressVO[where] == null) {
            intent.putExtra(KeyNames.KEY_NAME_MAP_SEARCH_DIRECT_OPEN, true);//바로 다음 화면 넘기기
        }

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
        Log.d(TAG, "onClickNextBtn active : " + buttonActive);
        if (!buttonActive) {
            return;
        }

        int work = determineWork();
        Log.d(TAG, "onClickNextBtn: " + work);

        switch (work) {
            //입력 미비 : 예상 가격에 저장된 값 삭제.
            // 1) 출발지 주소 입력됐고 세부주소 안 적음(도착지 주소 잠긴 상태).
            // 2) 출발지 및 도착지 주소 입력됐고 세부주소 둘 중에 하나 이상 빈칸.
            // 3) 예상가격 보고 나서 세부주소를 지운 경우도 여기로 떨어짐(  [2)]와 같은 판정  ).
            case NEXT_BTN_LACK_INPUT:
                priceMaybe = PRICE_NO_DATA;
                break;

            //출발지 입력 다 있으니 도착지 검색 버튼 해금. 그리고 즉시 실행(break; 안 함)시킴.
            case NEXT_BTN_OPEN_TO_ADDRESS:
                doTransition();
                //not break
            case NEXT_BTN_NEED_TO_ADDRESS:
                onClickSearchAddressBtn(TO);
                //todo 키보드 숨김처리 명시적으로 필요한지 확인하여 반영
                break;

            case NEXT_BTN_ASK_PRICE:
                //todo [1] 서버에 예상 가격 물어보기

                // 그동안 로딩 뷰스텁 띄우기
                setViewStub(R.layout.layout_service_drive_req_loading, null);

                // 로딩하는 동안 다음버튼 비활성화
                buttonActive = false;

                //todo 옵저버
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
        //도착지 주소 버튼이 기본값으로 보이는 경우
        //출발 세부주소가 빈칸(지웠다는 거)이어도 여기선 그냥 넘어감. 어차피 바로 뒤에서 잡음
        if (ui.tvServiceDriveReqSearchToAddressBtn.getVisibility() == View.VISIBLE &&
                ui.tvServiceDriveReqSearchToAddressBtn.getText().toString().equals(getString(R.string.service_drive_input_09))) {
            return NEXT_BTN_NEED_TO_ADDRESS;
        }

        //-----여기까지 왔다는 건 출발/도착지 검색 버튼에 둘 다 값(=주소)이 있다는 뜻------

        //세부 주소 입력 미비 : 여기서부터는 [[입력이 하나라도 없으면]] 바로 리턴해버림
        if (!hasFromDetail || !hasToDetail) {
            return NEXT_BTN_LACK_INPUT;
        }

        //------여기까지 왔으면 입력은 다 됐음------

        if (priceMaybe == PRICE_NO_DATA) {
            //가격을 모르면 예상 가격 조회
            return NEXT_BTN_ASK_PRICE;
        } else {
            //가격을 알면 신청(또는 예약 신청) 대화상자 호출
            return NEXT_BTN_REQ_SERVICE;
        }
    }

    //세부주소 입력칸이 빈칸이면 에러메시지 표시(입력 창에 손도 안 댄 경우 여기서 처리됨)
    private void checkDetailAddress(boolean hasFromDetail, boolean hasToDetail) {
        //출발지 세부 주소
        setError(fromDetailLayout, hasFromDetail);

        //도착지 세부 주소는 입력 창이 해금된 상태에서만 검사
        if (toDetailLayout.getVisibility() == View.VISIBLE) {
            setError(toDetailLayout, hasToDetail);
        }
    }

    //도착지 주소 검색버튼 트랜지션 : TODO  이거 이제 필요없지않나? 재생 되기 전에 다음 액티비티가 덮어버릴텐데
    private void doTransition() {
        if (ui.tvServiceDriveReqToTitle.getVisibility() == View.GONE) {
            Transition changeBounds = new ChangeBounds();
            changeBounds.setInterpolator(new OvershootInterpolator());
            TransitionManager.beginDelayedTransition(ui.lServiceDriveReqContainer);
            constraintSets[TO].applyTo(ui.lServiceDriveReqContainer);

            ui.ivServiceDriveReqPleaseInputXxx.setText(R.string.service_drive_input_03);
        }
    }

    public void setViewStub(int addLayout, ViewStub.OnInflateListener listener) {
        ViewStub stub = findViewById(R.id.vs_service_req_status);
        stub.setLayoutResource(addLayout);
        stub.setOnInflateListener(listener);
        stub.inflate();
    }
}
