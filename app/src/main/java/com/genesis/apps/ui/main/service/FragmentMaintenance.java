package com.genesis.apps.ui.main.service;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.REQ_1001;
import com.genesis.apps.comm.model.api.gra.SOS_1001;
import com.genesis.apps.comm.model.api.gra.SOS_1006;
import com.genesis.apps.comm.model.vo.AddressVO;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.viewmodel.LGNViewModel;
import com.genesis.apps.comm.viewmodel.REQViewModel;
import com.genesis.apps.comm.viewmodel.SOSViewModel;
import com.genesis.apps.databinding.FragmentServiceMaintenanceBinding;
import com.genesis.apps.ui.common.activity.BaseActivity;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;
import com.genesis.apps.ui.common.fragment.SubFragment;
import com.genesis.apps.ui.main.ServiceNetworkActivity;

public class FragmentMaintenance extends SubFragment<FragmentServiceMaintenanceBinding> {
    private static final String TAG = FragmentMaintenance.class.getSimpleName();
    private REQViewModel reqViewModel;
    private SOSViewModel sosViewModel;
    private LGNViewModel lgnViewModel;
    private String tmpAcptNo;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView(): start");
        View view = super.setContentView(inflater, R.layout.fragment_service_maintenance);
        me.setFragment(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        me.setLifecycleOwner(this);
        sosViewModel = new ViewModelProvider(this).get(SOSViewModel.class);
        reqViewModel = new ViewModelProvider(this).get(REQViewModel.class);
        lgnViewModel = new ViewModelProvider(getActivity()).get(LGNViewModel.class);

        sosViewModel.getRES_SOS_1001().observe(getViewLifecycleOwner(), result -> {
            switch (result.status){
                case LOADING:
                    ((SubActivity)getActivity()).showProgressDialog(true);
                    break;
                case SUCCESS:
                    ((SubActivity)getActivity()).showProgressDialog(false);
                    tmpAcptNo = result.data.getTmpAcptNo();
                    if(result.data!=null&&!TextUtils.isEmpty(tmpAcptNo)){
                        sosViewModel.reqSOS1006(new SOS_1006.Request(APPIAInfo.SM01.getId(),tmpAcptNo));
                        break;
                    }
                default:
                    ((SubActivity)getActivity()).showProgressDialog(false);
                    SnackBarUtil.show(getActivity(), getString(R.string.r_flaw06_p02_snackbar_1)+(" code:1"));
                    break;
            }
        });
        sosViewModel.getRES_SOS_1006().observe(getViewLifecycleOwner(), result -> {
            switch (result.status){
                case LOADING:
                    ((SubActivity)getActivity()).showProgressDialog(true);
                    break;
                case SUCCESS:
                    ((SubActivity)getActivity()).showProgressDialog(false);
                    if(result.data!=null&&result.data.getSosDriverVO()!=null&&!TextUtils.isEmpty(tmpAcptNo)){
                        ((BaseActivity) getActivity()).startActivitySingleTop(new Intent(getActivity(), ServiceSOSRouteInfoActivity.class).putExtra(KeyNames.KEY_NAME_SOS_DRIVER_VO, result.data), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                        break;
                    }
                default:
                    ((SubActivity)getActivity()).showProgressDialog(false);
                    SnackBarUtil.show(getActivity(), getString(R.string.r_flaw06_p02_snackbar_1));
                    break;
            }
        });

        reqViewModel.getRES_REQ_1001().observe(getViewLifecycleOwner(), result -> {

            switch (result.status){
                case LOADING:
                    ((SubActivity)getActivity()).showProgressDialog(true);
                    break;
                case SUCCESS:
                    ((SubActivity)getActivity()).showProgressDialog(false);
                    if(result.data!=null){
                        setViewSOSStatus(result.data.getPgrsStusCd());
                        setViewMaintenanceStatus(result.data.getStusCd());
                    }
                    break;
                default:
                    ((SubActivity)getActivity()).showProgressDialog(false);
                    break;
            }


        });

    }

    private void setViewMaintenanceStatus(String result) {
        try {
            if (!TextUtils.isEmpty(result)) {
                int stusCd = Integer.parseInt(result);
                if (stusCd > 4609 && stusCd < 4851) {
                    //정비중 표시
                    me.lServiceMaintenanceHistoryBtn.tvMovingNow.setVisibility(View.VISIBLE);
                    me.lServiceMaintenanceHistoryBtn.tvMovingNow.setText(R.string.sm01_maintenance_14);
                } else {
                    me.lServiceMaintenanceHistoryBtn.tvMovingNow.setVisibility(View.GONE);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setViewSOSStatus(String pgrsStusCd) {
        if(!TextUtils.isEmpty(pgrsStusCd)){
            switch (pgrsStusCd){
                case VariableType.SERVICE_SOS_STATUS_CODE_W://접수
                    me.lServiceMaintenanceEmergencyBtn.tvMovingNow.setVisibility(View.VISIBLE);
                    me.lServiceMaintenanceEmergencyBtn.tvMovingNow.setText(R.string.sm01_maintenance_13);
                    break;
                case VariableType.SERVICE_SOS_STATUS_CODE_S://출동
                    me.lServiceMaintenanceEmergencyBtn.tvMovingNow.setVisibility(View.VISIBLE);
                    me.lServiceMaintenanceEmergencyBtn.tvMovingNow.setText(R.string.sm01_maintenance_7);
                    break;
                case VariableType.SERVICE_SOS_STATUS_CODE_R://신청
                case VariableType.SERVICE_SOS_STATUS_CODE_E://완료
                case VariableType.SERVICE_SOS_STATUS_CODE_C://취소
                default:
                    me.lServiceMaintenanceEmergencyBtn.tvMovingNow.setVisibility(View.GONE);
                    break;
            }
        }
    }


    private void startSOSActivity() {

        String pgrsStusCd="";
        try{
            pgrsStusCd = reqViewModel.getRES_REQ_1001().getValue().data.getPgrsStusCd();
        }catch (Exception e){
            pgrsStusCd = "";
        }

        if(!TextUtils.isEmpty(pgrsStusCd)){
            switch (pgrsStusCd){
                case VariableType.SERVICE_SOS_STATUS_CODE_W://접수
                    ((BaseActivity) getActivity()).startActivitySingleTop(new Intent(getActivity(), ServiceSOSApplyInfoActivity.class),RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                    break;
                case VariableType.SERVICE_SOS_STATUS_CODE_S://출동
                    sosViewModel.reqSOS1001(new SOS_1001.Request(APPIAInfo.SM01.getId()));
                    break;
                case VariableType.SERVICE_SOS_STATUS_CODE_R://신청
                case VariableType.SERVICE_SOS_STATUS_CODE_E://완료
                case VariableType.SERVICE_SOS_STATUS_CODE_C://취소
                default:
                    ((BaseActivity) getActivity()).startActivitySingleTop(new Intent(getActivity(), ServiceSOSApplyActivity.class), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                    break;
            }
        }
    }

    @Override
    public void onRefresh() {
        reqServiceInfoToServer();
    }

    /**
     * @brief 서비스 관련 정보를 서버에 요청
     */
    private void reqServiceInfoToServer() {
        VehicleVO mainVehicle=null;
        String custGbCd="";
        try{
            mainVehicle = reqViewModel.getMainVehicle();
            custGbCd=lgnViewModel.getUserInfoFromDB().getCustGbCd();
        }catch (Exception e){
            mainVehicle = null;
        }finally{
            if(mainVehicle!=null&&!TextUtils.isEmpty(custGbCd)&&custGbCd.equalsIgnoreCase(VariableType.MAIN_VEHICLE_TYPE_OV)){
                reqViewModel.reqREQ1001(new REQ_1001.Request(APPIAInfo.SM01.getId(), mainVehicle.getVin()));
            }
        }
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
//        if(!checkCustGbCd(id))
//            return;

        switch (id) {
            //서비스 네트워크 찾기
            case R.id.tv_service_maintenance_find_network_btn:
                ((BaseActivity) getActivity()).startActivitySingleTop(new Intent(getActivity(), ServiceNetworkActivity.class).putExtra(KeyNames.KEY_NAME_PAGE_TYPE, ServiceNetworkActivity.PAGE_TYPE_SERVICE), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                break;

            //정비 예약
            case R.id.l_service_maintenance_reservation_btn:
                try{
                    String avlRsrVn = reqViewModel.getRES_REQ_1001().getValue().data.getAvlRsrYn();
                    if(!TextUtils.isEmpty(avlRsrVn)&&avlRsrVn.equalsIgnoreCase(VariableType.COMMON_MEANS_YES))
                         ((BaseActivity) getActivity()).startActivitySingleTop(new Intent(getActivity(), MaintenanceReserveActivity.class), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                    else
                        MiddleDialog.dialogServiceInfo(getActivity(), null);
                }catch (Exception ignore){

                }
                break;

            //정비 현황/예약 내역
            case R.id.l_service_maintenance_history_btn:
                ((BaseActivity) getActivity()).startActivitySingleTop(new Intent(getActivity(), ServiceRepairReserveHistoryActivity.class), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                break;

            //긴급출동
           case R.id.l_service_maintenance_emergency_btn:
                startSOSActivity();
                break;

            //원격진단 신청
            case R.id.l_service_maintenance_customercenter_btn:

//                MiddleDialog.dialogServiceRemoteInfo(getActivity(), () -> {
//                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(WebView.SCHEME_TEL+"080-700-6000"));
//                    startActivity(intent);
                ((BaseActivity) getActivity()).startActivitySingleTop(
                        new Intent(getActivity(), ServiceRemoteRegisterActivity.class),
                        RequestCodes.REQ_CODE_ACTIVITY.getCode(),
                        VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE
                );
//                }, () -> {
//
//                });


                break;
                // 원격 진단 신청 내역
            case R.id.l_service_maintenance_remote_servie_list_btn:
                ((BaseActivity) getActivity()).startActivitySingleTop(
                        new Intent(getActivity(), ServiceRemoteListActivity.class),
                        RequestCodes.REQ_CODE_ACTIVITY.getCode(),
                        VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE
                );
                break;

            //하자재발통보
            case R.id.l_service_maintenance_defect_btn:
                //주소 검색 시 사용하기 위한 내 위치 정보 전달
                AddressVO addressVO = new AddressVO();
                addressVO.setCenterLat(lgnViewModel.getPosition().getValue().get(0));
                addressVO.setCenterLon(lgnViewModel.getPosition().getValue().get(1));
                ((BaseActivity) getActivity()).startActivitySingleTop(new Intent(getActivity(), ServiceRelapseHistoryActivity.class).putExtra(KeyNames.KEY_NAME_ADDR, addressVO), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                break;

            default:
                //do nothing
                break;
        }
    }
}
