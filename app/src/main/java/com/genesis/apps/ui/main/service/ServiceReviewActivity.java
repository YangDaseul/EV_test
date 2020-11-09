package com.genesis.apps.ui.main.service;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.gra.APPIAInfo;
import com.genesis.apps.comm.model.gra.api.DDS_1005;
import com.genesis.apps.comm.model.gra.api.WSH_1008;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.viewmodel.DDSViewModel;
import com.genesis.apps.comm.viewmodel.WSHViewModel;
import com.genesis.apps.databinding.ActivityServiceReviewBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;

public class ServiceReviewActivity extends SubActivity<ActivityServiceReviewBinding> {
    private static final String TAG = ServiceReviewActivity.class.getSimpleName();
    private static final int REVIEW_WASH = 1;
    private static final int REVIEW_DRIVE = 2;

    //서버 측 제한 2048바이트라서 한글 utf-8기준으로 넘치지 않도록
    private static final int REVIEW_MAX_LENGTH = 680;

    private WSHViewModel wshViewModel;
    private DDSViewModel ddsViewModel;
    int reviewType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_review);
        ui.setActivity(this);

        //todo 세차 리뷰인지 대리운전 리뷰인지 알아내기
        // 리뷰 송신할 때, 세차는 예약번호, 대리운전은 transId, vin도 있어야 함
        reviewType = REVIEW_WASH;

        setViewModel();
        setObserver();
    }

    @Override
    public void onBackButton() {
        Log.d(TAG, "onBackButton: ");
        MiddleDialog.dialogCarWashReview(this, super::onBackButton);
    }

    @Override
    public void onBackPressed() {
        onBackButton();
    }

    @Override
    public void onClickCommon(View v) {
        Log.d(TAG, "onClickCommon: " + v.getId());

        switch (v.getId()) {
            //확인버튼
            case R.id.tv_service_review_ok_btn:
                onClickOkBtn();
                break;

            default:
                //do nothing
                break;
        }

    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);

        switch (reviewType) {
            case REVIEW_WASH:
                //세차
                wshViewModel = new ViewModelProvider(this).get(WSHViewModel.class);
                break;

            case REVIEW_DRIVE:
                //대리운전
                ddsViewModel = new ViewModelProvider(this).get(DDSViewModel.class);
                break;

            default:
                //do nothing
                break;
        }
    }

    @Override
    public void setObserver() {
        Log.d(TAG, "setObserver: type:" + reviewType);
        switch (reviewType) {
            case REVIEW_WASH:
                //세차 리뷰 옵저버
                wshViewModel.getRES_WSH_1008().observe(this, result -> {
                    Log.d(TAG, "getRES_WSH_1008 wash review obs: " + result.status);
                    switch (result.status) {
                        case LOADING:
                            showProgressDialog(true);
                            break;

                        case SUCCESS:
                            if (result.data != null && result.data.getRtCd() != null) {
                                showProgressDialog(false);

                                finishReview();
                                break;
                            }
                            //not break; 데이터 이상하면 default로 진입시킴

                        default:
                            showProgressDialog(false);
                            SnackBarUtil.show(this, getString(result.message));
                            //todo : 구체적인 예외처리
                            break;
                    }
                });
                break;

            case REVIEW_DRIVE:
                //대리운전 리뷰 옵저버
                ddsViewModel.getRES_DDS_1005().observe(this, result -> {
                    Log.d(TAG, "getRES_DDS_1005 service drive review obs: " + result.status);
                    switch (result.status) {
                        case LOADING:
                            showProgressDialog(true);
                            break;

                        case SUCCESS:
                            if (result.data != null && result.data.getRtCd() != null) {
                                showProgressDialog(false);

                                finishReview();
                                break;
                            }
                            //not break; 데이터 이상하면 default로 진입시킴

                        default:
                            showProgressDialog(false);
                            SnackBarUtil.show(this, getString(result.message));
                            //todo : 구체적인 예외처리
                            break;
                    }
                });
                break;

            default:
                //do nothing
                break;
        }

    }

    //확인버튼 처리
    private void onClickOkBtn() {
        Log.d(TAG, "onClickOkBtn: " + reviewType);
        //사용자가 입력한 별점, 기타의견 가져오기
        String starRating = String.valueOf(ui.rbServiceReviewRatingbar.getRating());
        String reviewInput = ui.etServiceReviewInput.getText().toString();

        //입력이 너무 길면 자르...는 걸로 일단 코드에서 처리
        if (reviewInput.length() > REVIEW_MAX_LENGTH) {
            reviewInput = reviewInput.substring(0, REVIEW_MAX_LENGTH);
        }

        switch (reviewType) {
            case REVIEW_WASH:
                //세차 리뷰 전송
                reqCarWashReview(starRating, reviewInput);
                break;

            case REVIEW_DRIVE:
                //대리운전 리뷰 전송
                reqServiceDriveReview(starRating, reviewInput);
                break;

            default:
                //do nothing
                break;
        }
    }

    //세차 리뷰 전송 TODO : 예약 번호 알아내기
    private void reqCarWashReview(String starRating, String reviewInput) {
        wshViewModel.reqWSH1008(
                new WSH_1008.Request(APPIAInfo.SM_REVIEW01.getId(),
                        "예약번호",
                        starRating,
                        reviewInput));
    }

    //대리운전 리뷰 전송 TODO :  transId 및 Vin 알아내기
    private void reqServiceDriveReview(String starRating, String reviewInput) {
        ddsViewModel.reqDDS1005(
                new DDS_1005.Request(APPIAInfo.SM_REVIEW01.getId(),
                        "vin",// TODO 이거 현재 메인 차량일 거라는 보장이 없네... 이것도 푸시 알림에 포함되어있나?
                        "transId",
                        starRating,
                        reviewInput));
    }

    //작성한 리뷰 전달 성공시 처리
    //안내 띄우고
    // TODO : [메인 화면으로 보냄]으로 돼 있는데 기획 확인 후 처리 방침 확정하여 반영
    //  임시로 리뷰 액티비티만 종료하도록 해 둠.
    private void finishReview() {
        SnackBarUtil.show(this, getString(R.string.service_review_finish));
        //TODO 스낵 바 안 보임(인식하기도 전에 액티비티랑 같이 없어져버림)
        finish();
//        startActivitySingleTop(new Intent(this, MainActivity.class), 0, VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
    }

    @Override
    public void getDataFromIntent() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}

