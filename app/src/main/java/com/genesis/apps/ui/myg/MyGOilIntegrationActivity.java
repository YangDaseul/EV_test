package com.genesis.apps.ui.myg;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.MYP_0001;
import com.genesis.apps.comm.model.api.gra.MYP_0004;
import com.genesis.apps.comm.model.api.gra.OIL_0005;
import com.genesis.apps.comm.model.constants.OilCodes;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.comm.viewmodel.MYPViewModel;
import com.genesis.apps.comm.viewmodel.OILViewModel;
import com.genesis.apps.databinding.ActivityMygOilIntegrationBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.bottom.BottomTwoButtonIndivTerm;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import static com.genesis.apps.comm.model.api.BaseResponse.RETURN_CODE_SUCC;
import static com.genesis.apps.comm.model.constants.VariableType.TERM_SERVICE_JOIN_GRA0013;
import static com.genesis.apps.comm.model.vo.OilPointVO.OIL_JOIN_CODE_R;

public class MyGOilIntegrationActivity extends SubActivity<ActivityMygOilIntegrationBinding> {

    private String oilRfnCd;
    private String rgstYn;
    private OILViewModel oilViewModel;
    private MYPViewModel mypViewModel;
    private BottomTwoButtonIndivTerm bottomTwoButtonIndivTerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myg_oil_integration);
        getDataFromIntent();
        setViewModel();
        setObserver();
        setView();
    }

    private void setView() {
        ui.setActivity(this);
        ui.ivOil1.setImageResource(OilCodes.findCode(oilRfnCd).getBigSrc());
        ui.ivOil2.setImageResource(OilCodes.findCode(oilRfnCd).getBigSrc2());
    }

    @Override
    public void onClickCommon(View v) {
        switch (v.getId()) {
            case R.id.btn_integration:
                mypViewModel.reqMYP0001(new MYP_0001.Request(APPIAInfo.MG_GA01.getId()));
                break;
        }
    }

    private void reqIntregration(){
        if (TextUtils.isEmpty(rgstYn) || !rgstYn.equalsIgnoreCase(OIL_JOIN_CODE_R)) {
            startActivitySingleTop(new Intent(this, MyGOilTermActivity.class).putExtra(OilCodes.KEY_OIL_CODE, oilRfnCd), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
        } else{
            MiddleDialog.dialogOilReConnectInfo(this,
                    () -> oilViewModel.reqOIL0005(new OIL_0005.Request(APPIAInfo.MG01.getId(), StringUtil.isValidString(oilRfnCd))),
                    () -> {}
            );
        }
    }


    @Override
    public void setViewModel() {
        oilViewModel = new ViewModelProvider(this).get(OILViewModel.class);
        mypViewModel = new ViewModelProvider(this).get(MYPViewModel.class);
    }

    @Override
    public void setObserver() {
        mypViewModel.getRES_MYP_0001().observe(this, result -> {
            switch (result.status){
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    showProgressDialog(false);
                    if(result.data!=null&&StringUtil.isValidString(result.data.getRtCd()).equalsIgnoreCase("0000")){
                        //약관 동의가 되어있는 경우
                        if(StringUtil.isValidString(result.data.getPrvcyYn()).equalsIgnoreCase(VariableType.COMMON_MEANS_YES)){
                            reqIntregration();
                        }else{
                        //약관 미동의 상태인 경우(동의팝업활성화)
                            if(bottomTwoButtonIndivTerm==null)
                                bottomTwoButtonIndivTerm = new BottomTwoButtonIndivTerm(this, R.style.BottomSheetDialogTheme);

                            bottomTwoButtonIndivTerm.setButtonAction(() -> {
                                //정보 제공 동의 요청
                                String mrktYn="";
                                String mrktCd="";
                                try{
                                    mrktYn = StringUtil.isValidString(result.data.getMrktYn());
                                    mrktCd = StringUtil.isValidString(result.data.getMrktCd());
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                                mypViewModel.reqMYP0004(new MYP_0004.Request(APPIAInfo.MG_GA01.getId(), mrktYn, mrktCd, VariableType.COMMON_MEANS_YES));
                            }, () -> {
                                //팝업 종료
                            });
                            //약관 내용 보기
                            bottomTwoButtonIndivTerm.setEventTerm(() -> startActivitySingleTop(new Intent(MyGOilIntegrationActivity.this, MyGTermsActivity.class).putExtra(MyGTermsActivity.TERMS_CODE, TERM_SERVICE_JOIN_GRA0013), RequestCodes.REQ_CODE_ACTIVITY.getCode(),VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE));
                            bottomTwoButtonIndivTerm.show();
                        }
                        break;
                    }
                default:
                    showProgressDialog(false);
                    String serverMsg="";
                    try {
                        serverMsg = result.data.getRtMsg();
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally{
                        if(TextUtils.isEmpty(serverMsg)) serverMsg = getString(R.string.r_flaw06_p02_snackbar_1);
                        SnackBarUtil.show(this, serverMsg);
                    }
                    break;
            }
        });

        mypViewModel.getRES_MYP_0004().observe(this, result -> {
            switch (result.status){
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    showProgressDialog(false);
                    if(result.data!=null&&StringUtil.isValidString(result.data.getRtCd()).equalsIgnoreCase("0000")){
                        //동의 완료 응답을 받은 경우
                        reqIntregration();
                        break;
                    }
                default:
                    showProgressDialog(false);
                    String serverMsg="";
                    try {
                        serverMsg = result.data.getRtMsg();
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally{
                        if(TextUtils.isEmpty(serverMsg)) serverMsg = getString(R.string.r_flaw06_p02_snackbar_1);
                        SnackBarUtil.show(this, serverMsg);
                    }
                    break;
            }
        });

        oilViewModel.getRES_OIL_0005().observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    showProgressDialog(false);
                    if (result.data != null && result.data.getRtCd().equalsIgnoreCase(RETURN_CODE_SUCC)) {
                        exitPage(getString(R.string.mg_con02_p01_3), ResultCodes.REQ_CODE_NORMAL.getCode());
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
                        if (TextUtils.isEmpty(serverMsg))
                            serverMsg = getString(R.string.r_flaw06_p02_snackbar_1);
                        SnackBarUtil.show(this, serverMsg);
                    }
                    break;
            }
        });

    }

    @Override
    public void getDataFromIntent() {
        try {
            oilRfnCd = getIntent().getStringExtra(OilCodes.KEY_OIL_CODE);
            rgstYn = getIntent().getStringExtra(OilCodes.KEY_OIL_RGSTYN);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (TextUtils.isEmpty(oilRfnCd)) {
            exitPage("정유소 정보가 존재하지 않습니다.\n잠시후 다시 시도해 주십시오.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ResultCodes.REQ_CODE_OIL_INTEGRATION_SUCCESS.getCode()) {
            exitPage(data, ResultCodes.REQ_CODE_NORMAL.getCode());
        }
    }


}
