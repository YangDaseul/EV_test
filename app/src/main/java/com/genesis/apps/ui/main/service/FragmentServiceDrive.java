package com.genesis.apps.ui.main.service;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.DDS_1001;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.viewmodel.DDSViewModel;
import com.genesis.apps.comm.viewmodel.LGNViewModel;
import com.genesis.apps.databinding.FragmentServiceDriveBinding;
import com.genesis.apps.ui.common.activity.BaseActivity;
import com.genesis.apps.ui.common.fragment.SubFragment;
import com.genesis.apps.ui.main.MainActivity;

import java.util.concurrent.ExecutionException;

public class FragmentServiceDrive extends SubFragment<FragmentServiceDriveBinding> {
    private static final String TAG = FragmentServiceDrive.class.getSimpleName();

    private DDSViewModel ddsViewModel;
    private LGNViewModel lgnViewModel;
    private VehicleVO mainVehicle;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.d(TAG, "onActivityCreated: ");
        super.onActivityCreated(savedInstanceState);

        setViewModel();
        setObserver();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView(): start");
        return super.setContentView(inflater, R.layout.fragment_service_drive);
    }

    @Override
    public void onRefresh() {
        initMainVehicle();
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onClickCommon(View v) {
        int id = v.getId();
        Log.d(TAG, "onClickCommon: view id :" + id);

        try {
            if (!((FragmentService) getParentFragment()).checkCustGbCd(id, lgnViewModel.getUserInfoFromDB().getCustGbCd()))
                return;
        } catch (Exception e) {

        }


        switch (id) {
            //대리운전 신청 버튼 (이미 신청한 상태이면 그 내용을 보여줌)
            case R.id.tv_service_drive_req_btn:
                onClickReqBtn();
                break;

            default:
                //do nothing
                break;
        }
    }

    public void setViewModel() {
        me.setLifecycleOwner(getViewLifecycleOwner());
        me.setFragment(this);
        ddsViewModel = new ViewModelProvider(this).get(DDSViewModel.class);
        lgnViewModel = new ViewModelProvider(this).get(LGNViewModel.class);
    }

    public void setObserver() {
        //신청 현황 확인
        ddsViewModel.getRES_DDS_1001().observe(getViewLifecycleOwner(), result -> {
            Log.d(TAG, "getRES_DDS_1001 check req obs: " + result.status);

            Intent intent;

            switch (result.status) {
                case LOADING:
                    ((MainActivity) getActivity()).showProgressDialog(true);
                    break;

                case SUCCESS:
                    if (result.data != null) {
                        ((MainActivity) getActivity()).showProgressDialog(false);

                        //신청하기 또는 신청 현황 조회 액티비티 호출
                        startServiceDriveActivity(result.data);
                        break;
                    }
                    //not break; 데이터 이상하면 default로 진입시킴

                default:
                    ((MainActivity) getActivity()).showProgressDialog(false);
                    String serverMsg = "";
                    try {
                        serverMsg = result.data.getRtMsg();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (TextUtils.isEmpty(serverMsg)) {
                            serverMsg = getString(R.string.instability_network);
                        }
                        SnackBarUtil.show(getActivity(), serverMsg);
                    }
                    //todo : 구체적인 예외처리
                    break;
            }
        });

    }

    private void initMainVehicle() {
        try {
            mainVehicle = lgnViewModel.getMainVehicleSimplyFromDB();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            //TODO 차량 정보 접근 실패에 대한 예외처리
        }
    }

    //대리운전 신청 버튼
    //신청 현황을 확인하고 그에 따라 옵저버에서 처리(신청 상태 표시 or 신청하기 액티비티 호출)
    private void onClickReqBtn() {
        //신청 현황 조회
        ddsViewModel.reqDDS1001(
                new DDS_1001.Request(
                        APPIAInfo.SM_DRV02.getId(),
                        mainVehicle.getVin()));
    }

    //신청하기 또는 신청 현황 조회 액티비티 호출
    private void startServiceDriveActivity(@NonNull DDS_1001.Response data) {
        Log.d(TAG, "startServiceDriveActivity: ");

        if (data.getSvcStusCd() == null) {
            //대리운전 신청 액티비티 호출
            startReqActivity();
            return;
        }

        switch (data.getSvcStusCd()) {
            //신청 현황 액티비티 호출
            case DDS_1001.STATUS_DRIVER_MATCH_WAIT:
            case DDS_1001.STATUS_RESERVED:
            case DDS_1001.STATUS_DRIVER_MATCHED:
            case DDS_1001.STATUS_DRIVER_REMATCHED:
            case DDS_1001.STATUS_DRIVE_NOW:
            case DDS_1001.STATUS_NO_DRIVER:
                //현황 데이터를 현황 액티비티로 가져감
                startReqResultActivity(data);
                break;

            //대리운전 신청 액티비티 호출
            case DDS_1001.STATUS_REQ:
            case DDS_1001.STATUS_SERVICE_FINISHED:
            case DDS_1001.STATUS_CANCEL_BY_USER:
            case DDS_1001.STATUS_CANCEL_CAUSE_NO_DRIVER:
                startReqActivity();
                break;

            default:
                Log.d(TAG, "getRES_DDS_1001: unknown svcStusCd ");
                break;
        }
    }

    //신청 현황 액티비티 호출
    private void startReqResultActivity(DDS_1001.Response data) {
        //신청 현황 데이터랑 주 차량 정보를 신청현황 액티비티로 가져감
        Intent intent = new Intent(getActivity(), ServiceDriveReqResultActivity.class)
                .putExtra(KeyNames.KEY_NAME_SERVICE_DRIVE_STATUS, data)
                .putExtra(KeyNames.KEY_NAME_VEHICLE_VO, mainVehicle);

        ((BaseActivity) getActivity()).startActivitySingleTop(intent, RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
    }

    //신청 액티비티 호출
    private void startReqActivity() {
        //주 차량 정보를 가져감
        Intent intent = new Intent(getActivity(), ServiceDriveReqActivity.class)
                .putExtra(KeyNames.KEY_NAME_VEHICLE_VO, mainVehicle);

        ((BaseActivity) getActivity()).startActivitySingleTop(intent, RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
    }
}
