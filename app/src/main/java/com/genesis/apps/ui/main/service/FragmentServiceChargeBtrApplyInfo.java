package com.genesis.apps.ui.main.service;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.CHB_1021;
import com.genesis.apps.comm.model.api.gra.CHB_1024;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.model.vo.carlife.OptionVO;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.comm.viewmodel.CHBViewModel;
import com.genesis.apps.databinding.FragmentServiceChargeBtrApplyInfoBinding;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;
import com.genesis.apps.ui.common.fragment.SubFragment;

public class FragmentServiceChargeBtrApplyInfo extends SubFragment<FragmentServiceChargeBtrApplyInfoBinding> {

    private CHBViewModel chbViewModel;

    private CHB_1021.Response resData;
    private VehicleVO mainVehicle;

    public static FragmentServiceChargeBtrApplyInfo newInstance(CHB_1021.Response data) {
        FragmentServiceChargeBtrApplyInfo fragment = new FragmentServiceChargeBtrApplyInfo();

        Bundle args = new Bundle();
        args.putSerializable(KeyNames.KEY_NAME_CHB_CONTENTS_VO, data);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            resData = (CHB_1021.Response) getArguments().getSerializable(KeyNames.KEY_NAME_CHB_CONTENTS_VO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.setContentView(inflater, R.layout.fragment_service_charge_btr_apply_info);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setViewModel();
        setObserver();
        initView();
        initData();
    }

    private void initView() {
        me.setData(resData);
    }

    /**
     * 가격 정보들 표시
     */
    private void setLayoutPriceItem() {
        if(resData == null && resData.getOrderInfo() == null)
            return;

        // 충전 금액 표시
        me.lAdvancePaymt.setMsg(StringUtil.getPriceString(resData.getOrderInfo().getProductPrice()));
        // 충전 크레딧 포인트 정보 표시
//        me.lCreditPoint.setMsg();
        // 탁송금액 표시
        int deliverPrice = chbViewModel.getOptionVO(VariableType.SERVICE_CHARGE_BTR_OPT_TYPE_1, resData.getOrderInfo().getOptionList()).getOptionPrice();
        me.lDeliveryPaymt.setMsg(StringUtil.getPriceString(deliverPrice));
        // 세차금액 표시
        OptionVO carwashVO = chbViewModel.getOptionVO(VariableType.SERVICE_CHARGE_BTR_OPT_TYPE_2, resData.getOrderInfo().getOptionList());
        if(carwashVO != null) {
            me.lCarWashPaymt.lWhole.setVisibility(View.VISIBLE);
            int carwashPrice = carwashVO.getOptionPrice();
            me.lCarWashPaymt.setMsg(StringUtil.getPriceString(carwashPrice));
        } else {
            me.lCarWashPaymt.lWhole.setVisibility(View.GONE);
        }

        me.lPaymtAmt.setMsg(StringUtil.getPriceString(resData.getOrderInfo().getTotalServiceCost()));
    }

    private void initData() {
        try {
            if (mainVehicle == null) mainVehicle = chbViewModel.getMainVehicleFromDB();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onClickCommon(View v) {
        switch (v.getId()) {
            case R.id.btn_cancel:
                dialogCancel();
                break;
            case R.id.btn_call_cs:
                if (!TextUtils.isEmpty(resData.getVendorInfo().getVendorCSTelNo())) {
                    getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(WebView.SCHEME_TEL + resData.getVendorInfo().getVendorCSTelNo())));
                }
                break;
            default:
                break;
        }
    }

    private void dialogCancel() {
        MiddleDialog.dialogServiceChargeBtrCancel(getActivity(), () -> {
            chbViewModel.reqCHB1024(new CHB_1024.Request(APPIAInfo.SM_CGRV04_02.getId(), resData.getOrderId(), mainVehicle.getVin()));
        }, () -> {

        });
    }

    @Override
    public void onRefresh() {

    }

    private void setViewModel() {
        me.setLifecycleOwner(getViewLifecycleOwner());
        me.setFragment(this);

        chbViewModel = new ViewModelProvider(getActivity()).get(CHBViewModel.class);
    }

    private void setObserver() {
    }
}
