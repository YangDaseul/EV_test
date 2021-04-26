package com.genesis.apps.ui.main.service;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.CHB_1019;
import com.genesis.apps.comm.model.api.gra.CHB_1020;
import com.genesis.apps.comm.model.api.gra.DDS_1005;
import com.genesis.apps.comm.model.api.gra.EVL_1001;
import com.genesis.apps.comm.model.api.gra.WSH_1007;
import com.genesis.apps.comm.model.api.gra.WSH_1008;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.comm.viewmodel.CHBViewModel;
import com.genesis.apps.comm.viewmodel.DDSViewModel;
import com.genesis.apps.comm.viewmodel.WSHViewModel;
import com.genesis.apps.databinding.ActivityServiceReviewBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;

import java.util.Locale;

import androidx.lifecycle.ViewModelProvider;

import static com.genesis.apps.comm.model.api.BaseResponse.RETURN_CODE_SUCC;

public class ServiceReviewActivity extends SubActivity<ActivityServiceReviewBinding> {
    private static final String TAG = ServiceReviewActivity.class.getSimpleName();

    //서버 측 제한 2048바이트라서 한글 utf-8기준으로 넘치지 않도록
    private static final int REVIEW_MAX_LENGTH = 680;

    private View[] ratingViews;
    private WSHViewModel wshViewModel;
    private DDSViewModel ddsViewModel;
    private CHBViewModel chbViewModel;
    private int mRate = 0;

    private String PI; //평가아이디
    private String ID; //메뉴아이디

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_review);
        ui.setActivity(this);
        ratingViews = new View[] {ui.llRate1, ui.llRate2, ui.llRate3, ui.llRate4, ui.llRate5};
        getDataFromIntent();
        initView();
    }

    private void initView() {
        if(TextUtils.isEmpty(PI)||TextUtils.isEmpty(ID)) {
            exitPage("", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
        } else {
            setViewModel();
            setObserver();
            reqEval();
        }
    }

    private void reqEval() {
        wshViewModel.reqEVL1001(new EVL_1001.Request(ID, String.format(Locale.getDefault(), "genesisapp://menu?id=%s&PI=%s", ID, PI)));
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
            case R.id.ll_rate_1:
            case R.id.ll_rate_2:
            case R.id.ll_rate_3:
            case R.id.ll_rate_4:
            case R.id.ll_rate_5:
                boolean isSelect = false;
                for(int i=0; i<ratingViews.length; i++) {
                    LinearLayout linearLayout = (LinearLayout) ratingViews[i];

                    if(linearLayout.getChildCount() != 0) {
                        if(isSelect) {
                            Glide.with(this)
                                    .load(R.drawable.ic_star_l_n_c)
                                    .format(DecodeFormat.PREFER_RGB_565)
                                    .error(R.drawable.ic_star_l_n_c)
                                    .placeholder(R.drawable.ic_star_l_n_c)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into((ImageView) linearLayout.getChildAt(0));
                        } else {
                            Glide.with(this)
                                    .load(R.drawable.ic_star_l_s_c)
                                    .format(DecodeFormat.PREFER_RGB_565)
                                    .error(R.drawable.ic_star_l_s_c)
                                    .placeholder(R.drawable.ic_star_l_s_c)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into((ImageView) linearLayout.getChildAt(0));
                        }
                    }

                    if(v.getId() == ratingViews[i].getId()) {
                        isSelect = true;
                        mRate = i + 1;
                        Log.d(TAG, "Click : " + mRate);
                    }
                }

                break;
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
    public void getDataFromIntent() {
        Intent intent = getIntent();
        try{
            PI = intent.getStringExtra(KeyNames.KEY_NAME_REVIEW_PI);
            ID = intent.getStringExtra(KeyNames.KEY_NAME_REVIEW_ID);
        }catch (Exception e){

        }
    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);

        switch (APPIAInfo.findCode(ID)) {
            case SM_REVIEW01_P01:
                //세차
                wshViewModel = new ViewModelProvider(this).get(WSHViewModel.class);
                break;
            case SM_REVIEW01_P03:
                //대리운전
                ddsViewModel = new ViewModelProvider(this).get(DDSViewModel.class);
                break;
            case SM_REVIEW01_P04:
                //픽업앤충전
                chbViewModel = new ViewModelProvider(this).get(CHBViewModel.class);
                break;
            default:
                exitPage("서비스 세부사항을 확인할 수 없습니다.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
                break;
        }
    }

    @Override
    public void setObserver() {
        switch (APPIAInfo.findCode(ID)) {
            case SM_REVIEW01_P01:
                wshViewModel.getRES_EVL_1001().observe(this, result -> {
                    switch (result.status){
                        case LOADING:
                            showProgressDialog(true);
                            break;
                        case SUCCESS:
                            showProgressDialog(false);
                            if(result.data!=null){
                                if(StringUtil.isValidString(result.data.getEvlFinYn()).equalsIgnoreCase(VariableType.COMMON_MEANS_NO)){
                                    setTitleMsg();
                                }else{
                                    rejectReview();
                                }
                            }
                            break;
                        default:
                            showProgressDialog(false);
                            String serverMsg = "";
                            try {
                                serverMsg = result.data.getRtMsg();
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                SnackBarUtil.show(this, (TextUtils.isEmpty(serverMsg)) ? getString(R.string.r_flaw06_p02_snackbar_1) : serverMsg);
                            }
                            break;
                    }
                });


                //세차 리뷰 옵저버
                wshViewModel.getRES_WSH_1007().observe(this, result -> {
                    Log.d(TAG, "wash review title obs: " + result.status);
                    switch (result.status) {
                        case LOADING:
                            showProgressDialog(true);
                            break;

                        case SUCCESS:
                            if (result.data != null) {
                                showProgressDialog(false);

                                if("0000".equals(result.data.getRtCd())) {
                                    if(!TextUtils.isEmpty(result.data.getEvalQst())){
                                        ui.tvServiceReviewTitleMsg.setText(result.data.getEvalQst());
                                    }
                                    break;
                                } else if("9030".equals(result.data.getRtCd())) {
                                    rejectReview();
                                    break;
                                }
                            }
                            //not break; 데이터 이상하면 default로 진입시킴

                        default:
                            showProgressDialog(false);
                            String serverMsg = "";
                            try {
                                serverMsg = result.data.getRtMsg();
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                SnackBarUtil.show(this, (TextUtils.isEmpty(serverMsg)) ? getString(R.string.r_flaw06_p02_snackbar_1) : serverMsg);
                            }
                            break;
                    }
                });

                wshViewModel.getRES_WSH_1008().observe(this, result -> {
                    Log.d(TAG, " wash review obs: " + result.status);
                    switch (result.status) {
                        case LOADING:
                            showProgressDialog(true);
                            break;

                        case SUCCESS:
                            if (result.data != null && StringUtil.isValidString(result.data.getRtCd()).equalsIgnoreCase(RETURN_CODE_SUCC)) {
                                showProgressDialog(false);
                                finishReview();
                                break;
                            }
                            //not break; 데이터 이상하면 default로 진입시킴

                        default:
                            showProgressDialog(false);
                            String serverMsg = "";
                            try {
                                serverMsg = result.data.getRtMsg();
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                SnackBarUtil.show(this, (TextUtils.isEmpty(serverMsg)) ? getString(R.string.r_flaw06_p02_snackbar_1) : serverMsg);
                            }
                            break;
                    }
                });
                break;

            case SM_REVIEW01_P03:
                //대리운전 리뷰 옵저버
                ddsViewModel.getRES_EVL_1001().observe(this, result -> {
                    switch (result.status){
                        case LOADING:
                            showProgressDialog(true);
                            break;
                        case SUCCESS:
                            showProgressDialog(false);
                            if(result.data!=null){
                                if(!StringUtil.isValidString(result.data.getEvlFinYn()).equalsIgnoreCase(VariableType.COMMON_MEANS_NO)){
                                    rejectReview();
                                }
                            }
                            break;
                        default:
                            showProgressDialog(false);
                            String serverMsg = "";
                            try {
                                serverMsg = result.data.getRtMsg();
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                SnackBarUtil.show(this, (TextUtils.isEmpty(serverMsg)) ? getString(R.string.r_flaw06_p02_snackbar_1) : serverMsg);
                            }
                            break;
                    }
                });


                ddsViewModel.getRES_DDS_1005().observe(this, result -> {
                    Log.d(TAG, "getRES_DDS_1005 service drive review obs: " + result.status);
                    switch (result.status) {
                        case LOADING:
                            showProgressDialog(true);
                            break;

                        case SUCCESS:
                            if (result.data != null) {
                                showProgressDialog(false);
                                if(StringUtil.isValidString(result.data.getRtCd()).equalsIgnoreCase(RETURN_CODE_SUCC)){
                                    finishReview();
                                }else if(StringUtil.isValidString(result.data.getRtCd()).equalsIgnoreCase("9030")){
                                    rejectReview();
                                }
                                break;
                            }
                        default:
                            showProgressDialog(false);
                            String serverMsg = "";
                            try {
                                serverMsg = result.data.getRtMsg();
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                SnackBarUtil.show(this, (TextUtils.isEmpty(serverMsg)) ? getString(R.string.r_flaw06_p02_snackbar_1) : serverMsg);
                            }
                            break;
                    }
                });
                break;

            case SM_REVIEW01_P04:
                chbViewModel.getRES_EVL_1001().observe(this, result -> {
                    switch (result.status){
                        case LOADING:
                            showProgressDialog(true);
                            break;
                        case SUCCESS:
                            showProgressDialog(false);
                            if(result.data!=null){
                                if(StringUtil.isValidString(result.data.getEvlFinYn()).equalsIgnoreCase(VariableType.COMMON_MEANS_NO)){
                                    setTitleMsg();
                                }else{
                                    rejectReview();
                                }
                            }
                            break;
                        default:
                            showProgressDialog(false);
                            String serverMsg = "";
                            try {
                                serverMsg = result.data.getRtMsg();
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                SnackBarUtil.show(this, (TextUtils.isEmpty(serverMsg)) ? getString(R.string.r_flaw06_p02_snackbar_1) : serverMsg);
                            }
                            break;
                    }
                });


                chbViewModel.getRES_CHB_1019().observe(this, result -> {
                    switch (result.status) {
                        case LOADING:
                            showProgressDialog(true);
                            break;

                        case SUCCESS:
                            if (result.data != null) {
                                showProgressDialog(false);
                                if("0000".equals(StringUtil.isValidString(result.data.getRtCd()))) {
                                    if(!TextUtils.isEmpty(result.data.getRvwStmt())){
                                        ui.tvServiceReviewTitleMsg.setText(result.data.getRvwStmt());
                                    }
                                    break;
                                } else if("9030".equals(result.data.getRtCd())) {
                                    rejectReview();
                                    break;
                                }
                            }
                        default:
                            showProgressDialog(false);
                            String serverMsg = "";
                            try {
                                serverMsg = result.data.getRtMsg();
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                SnackBarUtil.show(this, (TextUtils.isEmpty(serverMsg)) ? getString(R.string.r_flaw06_p02_snackbar_1) : serverMsg);
                            }
                            break;
                    }
                });

                chbViewModel.getRES_CHB_1020().observe(this, result -> {
                    switch (result.status) {
                        case LOADING:
                            showProgressDialog(true);
                            break;
                        case SUCCESS:
                            if (result.data != null && StringUtil.isValidString(result.data.getRtCd()).equalsIgnoreCase(RETURN_CODE_SUCC)) {
                                showProgressDialog(false);
                                finishReview();
                                break;
                            }
                        default:
                            showProgressDialog(false);
                            String serverMsg = "";
                            try {
                                serverMsg = result.data.getRtMsg();
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                SnackBarUtil.show(this, (TextUtils.isEmpty(serverMsg)) ? getString(R.string.r_flaw06_p02_snackbar_1) : serverMsg);
                            }
                            break;
                    }
                });


                break;
            default:
                //do nothing
                break;
        }

    }


    private void setTitleMsg() {
        switch (APPIAInfo.findCode(ID)) {
            case SM_REVIEW01_P01:
                wshViewModel.reqWSH1007(new WSH_1007.Request(ID, PI));
                break;
            case SM_REVIEW01_P04:
                chbViewModel.reqCHB1019(new CHB_1019.Request(ID, "CHRGBTR", PI));
                break;
        }
    }

    //확인버튼 처리
    private void onClickOkBtn() {
        if(mRate == 0) {
            SnackBarUtil.show(this, getString(R.string.rate_error_1));
            return;
        }

        //사용자가 입력한 별점, 기타의견 가져오기
        String starRating = String.valueOf(mRate);
        String reviewInput = ui.etServiceReviewInput.getText().toString();

        //입력이 너무 길면 자르...는 걸로 일단 코드에서 처리
        if (reviewInput.length() > REVIEW_MAX_LENGTH) {
            reviewInput = reviewInput.substring(0, REVIEW_MAX_LENGTH);
        }

        switch (APPIAInfo.findCode(ID)) {
            case SM_REVIEW01_P01:
                //세차 리뷰 전송
                wshViewModel.reqWSH1008(new WSH_1008.Request(ID, PI, starRating, reviewInput));
                break;
            case SM_REVIEW01_P03:
                //대리운전 리뷰 전송
                ddsViewModel.reqDDS1005(new DDS_1005.Request(ID, PI, starRating, reviewInput));
                break;
            case SM_REVIEW01_P04:
                //픽업앤충전 리뷰 전송
                chbViewModel.reqCHB1020(new CHB_1020.Request(ID, PI, starRating, reviewInput));
                break;
            default:
                //do nothing
                break;
        }
    }


    //작성한 리뷰 전달 성공
    private void finishReview() {
        exitPage(getString(R.string.service_review_finish), ResultCodes.REQ_CODE_NORMAL.getCode());
    }

    private void rejectReview() {
        exitPage(getString(R.string.service_review_duplicate), ResultCodes.REQ_CODE_NORMAL.getCode());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}

