package com.genesis.apps.ui.main.service;


import android.os.Bundle;
import android.text.TextUtils;
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
import com.genesis.apps.databinding.ActivityServiceDriveReq1Binding;
import com.genesis.apps.ui.common.activity.SubActivity;

public class ServiceDriveReqActivity extends SubActivity<ActivityServiceDriveReq1Binding> {
    private static final int FROM = 0;
    private static final int TO = 1;


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
        setResizeScreen();
        setContentView(layouts[0]);
        init();
    }

    @Override
    public void onClickCommon(View v) {

    }

    @Override
    public void setViewModel() {

    }

    @Override
    public void setObserver() {

    }

    @Override
    public void getDataFromIntent() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void init() {
        //TODO : 차종, 번호판 정보 표시
        ui.lServiceDriveReqTopPanel.tvServiceDriveReqCarModel.setText("GV80");
        ui.lServiceDriveReqTopPanel.tvServiceDriveReqCarNumber.setText("12너 3456");

        initConstraintSets();
        initView();
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

        //TODO 출발지 검색 버튼 : 지도 액티비티로 가야되는데 테스트 중이므로 바로 결과 나왔다 치고 다음 거 보이도록
        //TODO : 함수로 독립, onActvityResult()에서 호출
        ui.tvServiceDriveReqSearchFromAddressBtn.setOnClickListener(v -> {
            //onActivityResult()에서 이 값을 세팅하고 다음 게 이미 펼쳐져있도록 하면 될 듯?
            //아니면 펼쳐지는 장면을 onActivityResult()에서 보여주거나(이미 입력된 값을 수정하고왔을 때는 펼치기 이펙트 재생 안 해야겠지?)
            ui.tvServiceDriveReqSearchFromAddressBtn.setText("지도 액티비티에서 가져온 출발지 주소");
            ui.tvServiceDriveReqSearchFromAddressBtn.setTextAppearance(R.style.ServiceDrive_SearchAddress_HasData);
            ui.tvServiceDriveReqSearchFromAddressBtn.setBackground(getDrawable(R.drawable.ripple_bg_ffffff_stroke_141414));
            ui.ivServiceDriveReqPleaseInputXxx.setText(R.string.service_drive_input_02);
            ui.tvServiceDriveReqFromTitle.setVisibility(View.VISIBLE);
            ui.lServiceDriveReqInputFromDetail.setVisibility(View.VISIBLE);
            ui.tvServiceDriveReqNextBtn.setVisibility(View.VISIBLE);
        });

        //TODO 도착지 검색 버튼 : 지도 액티비티로 가야되는데 테스트 중이므로 바로 결과 나왔다 치고 다음 거 보이도록
        //TODO : 함수로 독립, onActvityResult()에서 호출
        ui.tvServiceDriveReqSearchToAddressBtn.setOnClickListener(v -> {
            //onActivityResult()에서 이 값을 세팅하고 다음 게 이미 펼쳐져있도록 하면 될 듯?
            //아니면 펼쳐지는 장면을 onActivityResult()에서 보여주거나(이미 입력된 값을 수정하고왔을 때는 펼치기 이펙트 재생 안 해야겠지?)
            ui.tvServiceDriveReqSearchToAddressBtn.setText("지도 액티비티에서 가져온 도착지 주소");
            ui.tvServiceDriveReqSearchToAddressBtn.setTextAppearance(R.style.ServiceDrive_SearchAddress_HasData);
            ui.tvServiceDriveReqSearchToAddressBtn.setBackground(getDrawable(R.drawable.ripple_bg_ffffff_stroke_141414));
            ui.ivServiceDriveReqPleaseInputXxx.setText(R.string.service_drive_input_04);
            ui.tvServiceDriveReqToTitle.setVisibility(View.VISIBLE);
            ui.lServiceDriveReqInputToDetail.setVisibility(View.VISIBLE);
        });

        //출발지 상세주소 입력 완료하면 도착지 검색버튼 뜨도록 리스너 붙임(화면 맨 아래 다음 버튼)
        ui.tvServiceDriveReqNextBtn.setOnClickListener(view -> {
            if (getCurrentFocus() instanceof TextView) {
                if (!TextUtils.isEmpty(((TextView) getCurrentFocus()).getText().toString())) {
                    doTransition();
                }
            }
        });
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
