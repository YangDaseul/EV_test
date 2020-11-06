package com.genesis.apps.ui.main.service;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.gra.APPIAInfo;
import com.genesis.apps.comm.model.gra.api.DDS_1001;
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
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onClickCommon(View v) {
        int id = v.getId();
        Log.d(TAG, "onClickCommon: view id :" + id);

        switch (id) {
            //대리운전 신청 버튼 (이미 신청한 상태이면 그 내용을 보여줌)
            case R.id.tv_service_drive_req_btn:
                onClickReqBtn();
                break;

            //TODO 테스트 끝나고 삭제
            case R.id.test_force_req:
                //신청 내역 있는지 확인 안 하고 그냥 신청 페이지 띄움
                ((BaseActivity) getActivity()).startActivitySingleTop(new Intent(getActivity(), ServiceDriveReqActivity.class), 0, VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
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

            switch (result.status) {
                case LOADING:
                    ((MainActivity) getActivity()).showProgressDialog(true);
                    break;

                case SUCCESS:
                    if (result.data != null && result.data.getSvcStusCd() != null) {
                        ((MainActivity) getActivity()).showProgressDialog(false);

                        switch (result.data.getSvcStusCd()) {
                            //신청 현황 액티비티 호출
                            case DDS_1001.STATUS_DRIVER_MATCH_WAIT:
                            case DDS_1001.STATUS_RESERVED:
                            case DDS_1001.STATUS_DRIVER_MATCHED:
                            case DDS_1001.STATUS_DRIVER_REMATCHED:
                            case DDS_1001.STATUS_DRIVE_NOW:
                            case DDS_1001.STATUS_NO_DRIVER:

                                //result.data 통째로 들고가서 화면에 뿌려야됨.
                                Intent intent = new Intent(getActivity(), ServiceDriveReqResultActivity.class);
                                intent.putExtra(DDS_1001.SERVICE_DRIVE_STATUS, result.data);

                                ((BaseActivity) getActivity()).startActivitySingleTop(intent, 0, VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                                break;

                            //대리운전 신청 액티비티 호출
                            case DDS_1001.STATUS_REQ:
                            case DDS_1001.STATUS_SERVICE_COMPLETE:
                            case DDS_1001.STATUS_CANCEL_BY_USER:
                            case DDS_1001.STATUS_CANCEL_CAUSE_NO_DRIVER:
                                ((BaseActivity) getActivity()).startActivitySingleTop(new Intent(getActivity(), ServiceDriveReqActivity.class), 0, VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                                break;

                            default:
                                Log.d(TAG, "getRES_DDS_1001: unknown svcStusCd ");
                                break;
                        }

                        return;
                    }
                    //not break; 데이터 이상하면 default로 진입시킴

                default:
                    ((MainActivity) getActivity()).showProgressDialog(false);
                    SnackBarUtil.show(getActivity(), getString(result.message));
                    //todo : 구체적인 예외처리
                    break;
            }
        });

    }

    //대리운전 신청 버튼
    //신청 현황을 확인하고 그에 따라 옵저버에서 처리(신청 상태 표시 or 신청하기 액티비티 호출)
    private void onClickReqBtn() {
        try {
            //신청 현황 조회
            ddsViewModel.reqDDS1001(
                    new DDS_1001.Request(
                            APPIAInfo.SM_DRV02.getId(),
                            lgnViewModel.getMainVehicleFromDB().getVin()));
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            //todo 차량 정보 접근 실패에 대한 예외처리
        }
    }
}
