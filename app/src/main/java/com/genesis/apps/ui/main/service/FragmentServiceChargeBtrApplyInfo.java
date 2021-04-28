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
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.viewmodel.CHBViewModel;
import com.genesis.apps.databinding.FragmentServiceChargeBtrApplyInfoBinding;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;
import com.genesis.apps.ui.common.fragment.SubFragment;

public class FragmentServiceChargeBtrApplyInfo extends SubFragment<FragmentServiceChargeBtrApplyInfoBinding> {

    private CHBViewModel chbViewModel;

    private CHB_1021.Response mData;
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
            mData = (CHB_1021.Response) getArguments().getSerializable(KeyNames.KEY_NAME_CHB_CONTENTS_VO);
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
        me.setData(mData);
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
                if (!TextUtils.isEmpty(mData.getVendorInfo().getVendorCSTelNo())) {
                    getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(WebView.SCHEME_TEL + mData.getVendorInfo().getVendorCSTelNo())));
                }
                break;
            default:
                break;
        }
    }

    private void dialogCancel() {
        MiddleDialog.dialogServiceChargeBtrCancel(getActivity(), () -> {
            chbViewModel.reqCHB1024(new CHB_1024.Request(APPIAInfo.SM_CGRV04_02.getId(), mData.getOrderId(), mainVehicle.getVin()));
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
//        chbViewModel.getRES_CHB_1024().observe(getViewLifecycleOwner(), result -> {
//            switch (result.status) {
//                case LOADING:
//                    ((SubActivity) getActivity()).showProgressDialog(true);
//                    break;
//                case SUCCESS:
//                    if (result.data != null && result.data.getRtCd().equals(BaseResponse.RETURN_CODE_SUCC)) {
//                        SnackBarUtil.show(getActivity(), getString(R.string.service_charge_btr_popup_msg_01));
//                        ((ServiceChargeBtrReserveHistoryActivity) getActivity()).moveChargeBtrHistTab(true);
//                        ((SubActivity) getActivity()).showProgressDialog(false);
//                        break;
//                    }
//                default:
//                    String serverMsg = "";
//                    try {
//                        serverMsg = result.data.getRtMsg();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    } finally {
//                        if (TextUtils.isEmpty(serverMsg)) {
//                            serverMsg = getString(R.string.r_flaw06_p02_snackbar_1);
//                        }
//                        SnackBarUtil.show(getActivity(), serverMsg);
//                        ((SubActivity) getActivity()).showProgressDialog(false);
//                    }
//                    break;
//            }
//        });
    }
}
