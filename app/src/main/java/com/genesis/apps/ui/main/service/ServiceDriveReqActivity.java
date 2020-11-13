package com.genesis.apps.ui.main.service;


import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.AddressVO;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.databinding.ActivityServiceDriveReqBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ServiceDriveReqActivity extends SubActivity<ActivityServiceDriveReqBinding> {
    private static final String TAG = ServiceDriveReqActivity.class.getSimpleName();

    private static final int FROM = 0;
    private static final int TO = 1;

    private static final int STATUS_GONE = -1;
    private static final int STATUS_LOADING = 0;
    private static final int STATUS_CANT_SERVICE = 1;
    private static final int STATUS_PRICE_MAYBE = 2;
    private static final int STATUS_ERROR = 3;

    private static final int NEXT_BTN_LACK_INPUT = 0;
    private static final int NEXT_BTN_OPEN_TO_ADDRESS = 1;
    private static final int NEXT_BTN_NEED_TO_ADDRESS = 2;
    private static final int NEXT_BTN_ASK_PRICE = 3;
    private static final int NEXT_BTN_REQ_SERVICE = 4;

    private VehicleVO mainVehicle;
    private AddressVO[] addressVO = new AddressVO[2];
    private String priceMaybe;    //TODO 예상가격 자료형 숫자인지 글자인지 확인
    private boolean commonButtonEnable = true;
    private boolean nextButtonEnable = true;

    private View[] statusViews;
    private AnimationDrawable loadingAnimation;
    private TextView priceTextView;

    private TextInputLayout fromDetailLayout;
    private TextInputLayout toDetailLayout;
    private TextInputEditText fromDetail;
    private TextInputEditText toDetail;

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
        setContentView(R.layout.activity_service_drive_req);
        init();

        //시작하자마자 출발 주소 검색 화면으로 넘어감
        onClickSearchAddressBtn(FROM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: reqCode : " + requestCode);

        //출발지 주소 얻어옴
        if (requestCode == RequestCodes.REQ_CODE_FROM_ADDRESS.getCode()) {
            setAddressData(
                    FROM,
                    data,
                    ui.tvServiceDriveReqSearchFromAddressBtn,
                    fromDetail,
                    R.string.service_drive_input_02,
                    new View[]{ui.tvServiceDriveReqFromTitle, fromDetailLayout, ui.tvServiceDriveReqNextBtn}
            );

        }
        //도착지 주소 얻어옴
        else if (requestCode == RequestCodes.REQ_CODE_TO_ADDRESS.getCode()) {
            setAddressData(
                    TO,
                    data,
                    ui.tvServiceDriveReqSearchToAddressBtn,
                    toDetail,
                    R.string.service_drive_input_04,
                    new View[]{ui.tvServiceDriveReqToTitle, toDetailLayout}
            );
        }
    }

    @Override
    public void onClickCommon(View v) {
        Log.d(TAG, "onClickCommon: " + v.getId());
        Log.d(TAG, "onClickCommon active : " + commonButtonEnable);

        //예상 가격 응답 기다리는 동안은 버튼 무력화
        if (!commonButtonEnable) {
            return;
        }

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

            //가격 조회 실패하면 뜨는 재시도 버튼
            // (재시도 누르기 전에 입력 값을 바꿀 수 있으니 여기로 보내서 할 일 검사부터 다시 한다)
            case R.id.tv_service_drive_retry:
            case R.id.tv_service_drive_req_next_btn://다음 버튼
                onClickNextBtn();
                break;

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

        startActivitySingleTop(
                intent,
                where == FROM ? RequestCodes.REQ_CODE_FROM_ADDRESS.getCode() : RequestCodes.REQ_CODE_TO_ADDRESS.getCode(),
                VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
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

        //todo  : 키보드 올라와있는 동안 이거 안 보여야됨 -> ui.lServiceDriveReqTopPanel

        initCarInfo();
        initStatusViews();
        initTextInputLayouts();
        ui.setActivity(this);
    }

    private void initCarInfo() {
        //차종, 번호판 정보 표시
        ui.lServiceDriveReqTopPanel.tvServiceReqCarModel.setText(mainVehicle.getMdlNm());
        ui.lServiceDriveReqTopPanel.tvServiceReqCarNumber.setText(mainVehicle.getCarRgstNo());
    }

    private void initStatusViews() {
        //visibility 일괄 초기화를 위해 묶어둠
        statusViews = new View[]{
                ui.lServiceDriveReqTopPanel.lServiceDriveReqLoading.getRoot(),
                ui.lServiceDriveReqTopPanel.lServiceDriveReqCantService.getRoot(),
                ui.lServiceDriveReqTopPanel.lServiceDriveReqPrice.getRoot(),
                ui.lServiceDriveReqTopPanel.lServiceDriveReqRetry.getRoot()
        };

        ImageView loadingView = ui.lServiceDriveReqTopPanel.lServiceDriveReqLoading.ivServiceDriveReqLoadingPrice;
        loadingAnimation = (AnimationDrawable) loadingView.getDrawable();

        priceTextView = ui.lServiceDriveReqTopPanel.lServiceDriveReqPrice.tvServicePriceMaybe;

        //재시도 버튼 리스너 : 이 레이아웃은 딴 데서도 쓰는데 리스너는 여기만 쓰므로 코드에서 처리
        ui.lServiceDriveReqTopPanel.lServiceDriveReqRetry.tvServiceDriveRetry.setOnClickListener(onSingleClickListener);
    }

    private void initTextInputLayouts() {
        fromDetailLayout = ui.lServiceDriveReqInputFromDetail;
        toDetailLayout = ui.lServiceDriveReqInputToDetail;
        fromDetail = ui.tietServiceDriveReqInputFromAddressDetail;
        toDetail = ui.tietServiceDriveReqInputToAddressDetail;

        setTextListener();
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

    //버튼 활성화 여부 변경
    //서비스 불가 뜨면 다음버튼은 막고 주소변경은 가능하게 해야 돼서 다음 버튼은 별도 플래그 하나 더 가짐
    //common을 disable하는 상황은 api 응답 대기중이므로 이 플래그가 false이면 다음 버튼도 비활성화됨
    private void setBtnEnable(boolean commonBtn, boolean nextBtn) {
        commonButtonEnable = commonBtn;
        nextButtonEnable = nextBtn;
    }

    private void onClickNextBtn() {
        Log.d(TAG, "onClickNextBtn active : " + nextButtonEnable);

        //예상 가격 응답 기다리는 동안은 버튼 무력화
        if (!nextButtonEnable) {
            return;
        }

        int work = determineWork();
        Log.d(TAG, "onClickNextBtn: " + work);

        switch (work) {
            //입력 미비 : 예상 가격에 저장된 값 삭제.
            // 1) 출발지 주소 입력됐고 세부주소 안 적음(도착지 주소 잠긴 상태).
            // 2) 출발지 및 도착지 주소 입력됐고 세부주소 둘 중에 하나 이상 빈칸.
            // 3) 예상가격 조회 시도 후에 세부주소를 지운 경우도 여기로 떨어짐(  [2)]와 같은 판정  ).
            case NEXT_BTN_LACK_INPUT:
                priceMaybe = null;
                showStatus(STATUS_GONE);
                break;

            //출발지 입력 다 있으니 도착지 검색 버튼 해금. 그리고 즉시 실행(break; 안 함)시킴.
            case NEXT_BTN_OPEN_TO_ADDRESS:
                openToAddressBtn();
                //not break
            case NEXT_BTN_NEED_TO_ADDRESS:
                onClickSearchAddressBtn(TO);
                break;

            //예상 가격 조회
            case NEXT_BTN_ASK_PRICE:
                askPrice();
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

        if (TextUtils.isEmpty(priceMaybe)) {
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

    //도착지 주소 검색 버튼 해금
    private void openToAddressBtn() {
        ui.tvServiceDriveReqSearchToAddressBtn.setVisibility(View.VISIBLE);
        ui.tvServiceDriveReqPleaseInputXxx.setText(R.string.service_drive_input_03);
    }

    /**
     * 검색된 주소를 화면에 출력
     *
     * @param where         : 출발지/도착지 구분
     * @param data          : 지도 액티비티에서 가져온 데이터
     * @param addressBtn    : 출발지 버튼/도착지 버튼 구분. 여기에 주소를 출력함.
     * @param addressDetail : 출발지 버튼/도착지 세부주소 구분. 여기에 세부 주소를 출력함.
     * @param topMsgId      : 입력창 위에 있는 안내 메시지
     * @param views         : 다음 단계 입력을 위해 해금해야 할 뷰
     */
    private void setAddressData(int where, Intent data, TextView addressBtn, TextInputEditText addressDetail, int topMsgId, View[] views) {
        try {
            addressVO[where] = (AddressVO) data.getSerializableExtra(KeyNames.KEY_NAME_ADDR);
        } catch (Exception exception) {
            Log.d(TAG, "setAddressData: 주소 데이터 인텐트 없음");
            return;
        }

        //주소 꺼내기. 도로명/지번 알아서 처리됨. [메인주소, 세부주소]로 리턴됨.
        String[] address = getAddress(addressVO[where]);

        if (TextUtils.isEmpty(address[0])) {
            Log.d(TAG, "setAddressData: 주소 객체에 해당 데이터 없음");
            return;
        }

        //검색 버튼에 주소 입력하고 '값 있음' 스타일로 변경
        addressBtn.setText(address[0]);
        addressBtn.setTextAppearance(R.style.ServiceDrive_SearchAddress_HasData);
        addressBtn.setBackground(getDrawable(R.drawable.ripple_bg_ffffff_stroke_141414));

        //세부주소도 입력하고 입력 창에 포커스 주기
        addressDetail.setText(address[1]);
        addressDetail.requestFocus();
        addressDetail.setSelection(addressDetail.length());

        //다음 단계 입력에 대한 안내 메시지
        ui.tvServiceDriveReqPleaseInputXxx.setText(topMsgId);

        //다음 단게 뷰 해금
        for (View v : views) {
            v.setVisibility(View.VISIBLE);
        }

        //사용자가 이상한 시나리오로 조작하는 상황에 대한 방어
        refreshState();
    }

    //예상가격 조회 누른 이후에 주소를 바꾸는 경우에 대비한 초기화
    private void refreshState() {
        //가격조회 시도 결과 '서비스 이용불가 지역' 나온 상태[ changeStateToCantService() ]에서
        //주소 변경하면 재조회 가능하도록 다음버튼 해금
        setBtnEnable(true, true);

        //예상가격 조회 결과로 출력된 상태 뷰 초기화
        showStatus(STATUS_GONE);
    }

    //예상 가격 조회
    private void askPrice() {
        Log.d(TAG, "askPrice: ");

        changeStateToLoading();
        //todo 로드윈에 서비스 가능 지역 물어보기 api 호출

        //todo 서비스 가능지역 옵저버
        //  불가능하면   changeStateToCantService();
        //  가능하면    예상 가격 물어보기 api 호출

        //  todo 예상가격 옵저버    {
        //                            changeStateToPriceMaybe(price);
        //                      }

        //todo 위에 두 옵저버 오류 공통 [ changeStateToError() ]
        //TODO TEST 임시로 3초 뒤에 실패상태로 진입하도록 함
        new Handler().postDelayed(this::changeStateToError, 3000);
        //
    }

    private void changeStateToLoading() {
        Log.d(TAG, "changeStateToLoading: ");

        // 로딩하는 동안 버튼들 비활성화
        setBtnEnable(false, false);

        //로딩중 표시
        showStatus(STATUS_LOADING);
    }

    private void changeStateToCantService() {
        Log.d(TAG, "changeStateToCantService: ");

        //주소 변경이 가능하도록 해금. 다음 버튼은 주소 변경 안 하고 누르면 또 여기로 떨어질테니 막아둠.
        setBtnEnable(true, false);

        //서비스 이용 불가지역 안내 뷰 표시
        showStatus(STATUS_CANT_SERVICE);
    }

    private void changeStateToPriceMaybe(String price) {
        Log.d(TAG, "changeStateToPriceMaybe: ");

        //다음 단계 진행 가능해졌으니 버튼 전부 활성화
        setBtnEnable(true, true);

        //예상 가격 저장
        priceMaybe = price;

        showStatus(STATUS_PRICE_MAYBE);

        //여기까지 와놓고 주소 변경해버리면? 그게 서비스 불가 주소면??
        //->주소 변경하면 이거 초기화하는 내용 넣었음
    }

    private void changeStateToError() {
        Log.d(TAG, "changeStateToError: ");

        //로딩 끝났으니 버튼들 활성화
        setBtnEnable(true, true);

        //오류 발생 표시
        showStatus(STATUS_ERROR);
    }

    public void showStatus(int newStatus) {
        Log.d(TAG, "showStatus: " + newStatus);

        //전부 숨기도록 초기화
        loadingAnimation.stop();
        for (View status : statusViews) {
            status.setVisibility(View.GONE);
        }

        switch (newStatus) {
            case STATUS_LOADING:
                loadingAnimation.start();
                break;

            case STATUS_PRICE_MAYBE:
                priceTextView.setText(StringUtil.getPriceString(priceMaybe));
                //not break;
            case STATUS_CANT_SERVICE:
            case STATUS_ERROR:
                //뒤에 리턴으로 떨어지면 안 되니까 이 둘도 지우면 안 됨
                break;

            case STATUS_GONE:
            default:
                //do nothing
                //브레이크 말고 리턴
                return;
        }

        //새 상태 표시
        statusViews[newStatus].setVisibility(View.VISIBLE);
    }
}
