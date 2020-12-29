package com.genesis.apps.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.STO_1003;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.viewmodel.LGNViewModel;
import com.genesis.apps.databinding.ActivitySimilarCarContractHistoryBinding;
import com.genesis.apps.ui.common.activity.SubActivity;

import static com.genesis.apps.comm.model.api.BaseResponse.RETURN_CODE_SUCC;

/**
 * @author hjpark
 * @brief 구매 계약 내역
 */
public class SimilarCarContractHistoryActivity extends SubActivity<ActivitySimilarCarContractHistoryBinding> {
    private String ctrctNo;
    private LGNViewModel lgnViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_similar_car_contract_history);
        setViewModel();
        setObserver();
        getDataFromIntent();
    }


    @Override
    public void onClickCommon(View v) {

        switch (v.getId()){
            case R.id.tv_msg: //계약서 상세 조회
                if(lgnViewModel.getRES_STO_1003().getValue().data!=null)
                    startActivitySingleTop(new Intent(this, SimilarCarContractDetailActivity.class).putExtra(KeyNames.KEY_NAME_STO_1003, lgnViewModel.getRES_STO_1003().getValue().data), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_VERTICAL_SLIDE);
                break;
        }

    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        ui.setActivity(this);
        lgnViewModel = new ViewModelProvider(this).get(LGNViewModel.class);
    }

    @Override
    public void setObserver() {
        lgnViewModel.getRES_STO_1003().observe(this, result -> {
            switch (result.status){
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    if(result.data!=null&&result.data.getRtCd().equalsIgnoreCase(RETURN_CODE_SUCC)){
                        showProgressDialog(false);
                        ui.setData(result.data);
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
                        if (TextUtils.isEmpty(serverMsg)) {
                            serverMsg = getString(R.string.r_flaw06_p02_snackbar_1);
                        }
                        exitPage(serverMsg, ResultCodes.RES_CODE_NETWORK.getCode());
                    }
                    break;
            }
        });
    }

    @Override
    public void getDataFromIntent() {
        try {
            ctrctNo = getIntent().getStringExtra(KeyNames.KEY_NAME_CTRCT_NO);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (TextUtils.isEmpty(ctrctNo)) {
                exitPage("게약 번호가 존재하지 않습니다.\n잠시후 다시 시도해 주십시오.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
            }else{
                lgnViewModel.reqSTO1003(new STO_1003.Request(APPIAInfo.GM02_CTR01.getId(), ctrctNo));
            }
        }
    }

    public String getCnttStNm(String cnttStCd){
        String cnttStNm="";

        if(!TextUtils.isEmpty(cnttStCd)) {
            switch (cnttStCd) {
                case "1000"://대기
                    cnttStNm = getString(R.string.gm02_ctr02_14);
                    break;
                case "2000"://생산중
                    cnttStNm = getString(R.string.gm02_ctr02_19);
                    break;
                case "3000"://생산완료
                    cnttStNm = getString(R.string.gm02_ctr02_12);
                    break;
                case "4000"://출고중
                    cnttStNm = getString(R.string.gm02_ctr02_20);
                    break;
                case "5000"://출고완료
                    cnttStNm = getString(R.string.gm02_ctr02_21);
                    break;
                case "6000"://인도중
                    cnttStNm = getString(R.string.gm02_ctr02_22);
                    break;
                case "7000"://인도완료
                    cnttStNm = getString(R.string.gm02_ctr02_23);
                    break;
                default:
                    cnttStNm="상태 없음";
                    break;
            }
        }

        return cnttStNm;
    }

    public boolean isFinish(String cnttStCd){
        boolean isFinsih=false;

        if(!TextUtils.isEmpty(cnttStCd)) {
            switch (cnttStCd) {
                case "3000"://생산완료
                case "5000"://출고완료
                case "7000"://인도완료
                    isFinsih = true;
                    break;
            }
        }

        return isFinsih;
    }

}
