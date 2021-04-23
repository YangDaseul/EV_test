package com.genesis.apps.ui.main.service;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.gra.CHB_1021;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.model.vo.map.FindPathResVO;
import com.genesis.apps.databinding.FragmentMapBinding;
import com.genesis.apps.databinding.LayoutMapOverlayUiBottomInfoBarBinding;
import com.genesis.apps.ui.common.activity.GpsBaseActivity;
import com.genesis.apps.ui.common.dialog.bottom.DialogChargeBtrDriverInfo;
import com.genesis.apps.ui.common.fragment.SubFragment;

public class FragmentServiceChargeBtrMap extends SubFragment<FragmentMapBinding> {


    private VehicleVO mainVehicle;

    private int minute = 0;


    private LayoutMapOverlayUiBottomInfoBarBinding bottomBinding;
    private DialogChargeBtrDriverInfo dialogCHBDriverInfo;

    CHB_1021.Response data;

    public static FragmentServiceChargeBtrMap newInstance(CHB_1021.Response data) {
        FragmentServiceChargeBtrMap fragment = new FragmentServiceChargeBtrMap();

        Bundle args = new Bundle();
        args.putSerializable(KeyNames.KEY_NAME_CHB_CONTENTS_VO, data);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null) {
            data = (CHB_1021.Response) getArguments().getSerializable(KeyNames.KEY_NAME_CHB_CONTENTS_VO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.setContentView(inflater, R.layout.fragment_map);
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
        me.pmvMapView.initMap();
        updateBottomView(null);
    }

    private void initData() {
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onClickCommon(View v) {
        switch (v.getId()) {
            case R.id.btn_driver_info:
                dialogCHBDriverInfo = new DialogChargeBtrDriverInfo(getContext(), R.style.BottomSheetDialogTheme);
                dialogCHBDriverInfo.setData(data);
                dialogCHBDriverInfo.setOnDismissListener(dialogInterface -> {

                });
                dialogCHBDriverInfo.show();
                break;
        }
    }

    @Override
    public void onRefresh() {
    }

    private void setViewModel() {
    }

    private void setObserver() {
    }

    private void updateBottomView(FindPathResVO.Summary summary) {
        try {
            if (summary != null) {
                minute = summary.getTotalTime() / 60;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bottomBinding == null) {
                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) getActivity().findViewById(R.id.vs_map_overlay_bottom_box).getLayoutParams();
                params.setMargins(0, 0, 0, 0);
//                params.height = (int) DeviceUtil.dip2Pixel(this, 40);
                getActivity().findViewById(R.id.vs_map_overlay_bottom_box).setLayoutParams(params);
                ((GpsBaseActivity)getActivity()).setViewStub(R.id.vs_map_overlay_bottom_box, R.layout.layout_map_overlay_ui_bottom_info_bar_2, (viewStub, inflated) -> {
                    bottomBinding = DataBindingUtil.bind(inflated);
                    bottomBinding.btnDriverInfo.setOnClickListener(onSingleClickListener);
                    bottomBinding.setMinute(minute);
                });
            }else{
                bottomBinding.setMinute(minute);
            }

            if(dialogCHBDriverInfo!=null) dialogCHBDriverInfo.setMinute(minute);
        }
    }
}
