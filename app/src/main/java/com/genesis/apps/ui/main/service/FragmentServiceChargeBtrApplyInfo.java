package com.genesis.apps.ui.main.service;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.CHB_1021;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.viewmodel.CHBViewModel;
import com.genesis.apps.databinding.FragmentServiceChargeBtrApplyInfoBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.fragment.SubFragment;

public class FragmentServiceChargeBtrApplyInfo extends SubFragment<FragmentServiceChargeBtrApplyInfoBinding> {


    private CHBViewModel chbViewModel;
    private VehicleVO mainVehicle;

    private int minute = 0;


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
    }

    private void initData() {
        try {
            if (mainVehicle == null) mainVehicle = chbViewModel.getMainVehicleFromDB();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (mainVehicle != null)
            chbViewModel.reqCHB1021(new CHB_1021.Request(APPIAInfo.SM_CG_SM01.getId(), mainVehicle.getVin()));

    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onClickCommon(View v) {
        switch (v.getId()) {
            default:
                break;
        }
    }

    @Override
    public void onRefresh() {
    }

    private void setViewModel() {
        me.setLifecycleOwner(getViewLifecycleOwner());
        me.setFragment(this);

        chbViewModel = new ViewModelProvider(this).get(CHBViewModel.class);
    }

    private void setObserver() {
        chbViewModel.getRES_CHB_1021().observe(getViewLifecycleOwner(), result -> {

            switch (result.status) {
                case LOADING:
                    ((SubActivity) getActivity()).showProgressDialog(true);
                    break;
                case SUCCESS:
                    ((SubActivity) getActivity()).showProgressDialog(false);
                    if (result.data != null && result.data.getRtCd().equalsIgnoreCase("0000")) {
                        me.setData(result.data);
                        break;
                    }
                default:
                    String serverMsg = "";
                    try {
                        serverMsg = result.data.getRtMsg();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (TextUtils.isEmpty(serverMsg)) {
                            serverMsg = getString(R.string.r_flaw06_p02_snackbar_1);
                        }
                        SnackBarUtil.show(getActivity(), serverMsg);
                        ((SubActivity) getActivity()).showProgressDialog(false);
                    }
                    break;
            }
        });
    }
}
