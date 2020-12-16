package com.genesis.apps.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.REQ_1017;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.vo.RepCostDetailVO;
import com.genesis.apps.comm.model.vo.RepCostVO;
import com.genesis.apps.comm.model.vo.TermVO;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.viewmodel.REQViewModel;
import com.genesis.apps.databinding.ActivityServiceNetworkPriceBinding;
import com.genesis.apps.databinding.ItemPriceContentsBinding;
import com.genesis.apps.databinding.ItemPriceTitleBinding;
import com.genesis.apps.databinding.ItemTermBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.view.TermView;

import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import static com.genesis.apps.comm.model.constants.VariableType.TERM_SERVICE_JOIN_GRA0005;
import static com.genesis.apps.comm.model.vo.TermVO.TERM_ESN_AGMT_N;

public class ServiceNetworkPriceActivity extends SubActivity<ActivityServiceNetworkPriceBinding> {

    private REQViewModel reqViewModel;
    private String asnCd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_network_price);
        getDataFromIntent();
        setViewModel();
        setObserver();
        reqViewModel.reqREQ1017(new REQ_1017.Request(APPIAInfo.MG_MEMBER01_P01.getId(), asnCd));
    }

    @Override
    public void onClickCommon(View v) {

    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        reqViewModel = new ViewModelProvider(this).get(REQViewModel.class);
    }

    @Override
    public void setObserver() {
        reqViewModel.getRES_REQ_1017().observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    if (result.data != null && result.data.getRepCostList() != null && result.data.getRepCostList().size() > 0) {
                        addTermToLayout(result.data.getRepCostList());
                        showProgressDialog(false);
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
                            serverMsg = getString(R.string.sm_snfind01_snackbar_1);

                        exitPage(serverMsg, ResultCodes.RES_CODE_NETWORK.getCode());
                    }
                    break;
            }
        });


    }

    @Override
    public void getDataFromIntent() {
        try {
            asnCd = getIntent().getStringExtra(KeyNames.KEY_NAME_ASNCD);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (TextUtils.isEmpty(asnCd)) {
                exitPage("정비망코드가 존재하지 않습니다.\n잠시후 다시 시도해 주십시오.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
            }
        }
    }

//    private void setEmptyView(int itemCount){
//        if(itemCount>0){
//            ui.rv.setVisibility(View.VISIBLE);
//            ui.tvEmpty.setText("");
//        }else{
//            ui.rv.setVisibility(View.GONE);
//            ui.tvEmpty.setText(R.string.msg_membership_11);
//        }
//    }


    private void addTermToLayout(List<RepCostVO> repCostList) {
        for (RepCostVO repCostVO : repCostList) {
            final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final ItemPriceTitleBinding itemPriceTitleBinding = DataBindingUtil.inflate(inflater, R.layout.item_price_title, null, false);

            //타이틀 추가
            itemPriceTitleBinding.tvPriceTitle.setText(repCostVO.getMdlNm());
            ui.lPrice.addView(itemPriceTitleBinding.getRoot());

            int cnt = 0;
            //컨텐츠 추가
            if (repCostVO.getCostList() != null) {
                for (RepCostDetailVO repCostDetailVO : repCostVO.getCostList()) {
                    cnt++;
                    final ItemPriceContentsBinding itemPriceContentsBinding = DataBindingUtil.inflate((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), R.layout.item_price_contents, null, false);
                    itemPriceContentsBinding.tvWkNm.setText(repCostDetailVO.getWkNm());
                    itemPriceContentsBinding.tvRprAmt.setText(repCostDetailVO.getRprAmt());
                    //각 항목의 마지막 라인에서 단위 및 라인 활성화
                    if (cnt == repCostVO.getCostList().size()) {
                        itemPriceContentsBinding.ivEndLine.setVisibility(View.VISIBLE);
                        itemPriceContentsBinding.tvUnit.setVisibility(View.VISIBLE);
                    }

                    ui.lPrice.addView(itemPriceContentsBinding.getRoot());
                }
            } else {
                //todo 아이템이 없을 경우 예외처리 필요한지 확인 필요
            }
        }
    }

}
