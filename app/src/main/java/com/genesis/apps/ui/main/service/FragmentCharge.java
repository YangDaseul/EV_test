package com.genesis.apps.ui.main.service;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.comm.viewmodel.LGNViewModel;
import com.genesis.apps.databinding.FragmentServiceChargeBinding;
import com.genesis.apps.ui.common.activity.BaseActivity;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;
import com.genesis.apps.ui.common.fragment.SubFragment;
import com.genesis.apps.ui.main.MainActivity;

public class FragmentCharge extends SubFragment<FragmentServiceChargeBinding> {
    private static final String TAG = FragmentCharge.class.getSimpleName();
    private LGNViewModel lgnViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView(): start");
        View view = super.setContentView(inflater, R.layout.fragment_service_charge);
        me.setFragment(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        me.setLifecycleOwner(this);
        lgnViewModel = new ViewModelProvider(getActivity()).get(LGNViewModel.class);
    }

//    private void startSOSActivity() {
//
//        if (!((MainActivity) getActivity()).isGpsEnable()) {
//            MiddleDialog.dialogGPS(getActivity(), () -> ((MainActivity) getActivity()).turnGPSOn(isGPSEnable -> {
//                Log.v("test","value:"+isGPSEnable);
//            }), () -> {
//                //현대양재사옥위치
//            });
//        } else {
//            String pgrsStusCd = "";
//            try {
//                pgrsStusCd = reqViewModel.getRES_REQ_1001().getValue().data.getPgrsStusCd();
//            } catch (Exception e) {
//                pgrsStusCd = "";
//            }
//
//            if (!TextUtils.isEmpty(pgrsStusCd)) {
//                switch (pgrsStusCd) {
//                    case VariableType.SERVICE_SOS_STATUS_CODE_R://신청
//                    case VariableType.SERVICE_SOS_STATUS_CODE_W://접수
//                        ((BaseActivity) getActivity()).startActivitySingleTop(new Intent(getActivity(), ServiceSOSApplyInfoActivity.class), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
//                        break;
//                    case VariableType.SERVICE_SOS_STATUS_CODE_S://출동
//                        sosViewModel.reqSOS1001(new SOS_1001.Request(APPIAInfo.SM01.getId()));
//                        break;
//                    case VariableType.SERVICE_SOS_STATUS_CODE_E://완료
//                    case VariableType.SERVICE_SOS_STATUS_CODE_C://취소
//                    default:
//                        ((BaseActivity) getActivity()).startActivitySingleTop(new Intent(getActivity(), ServiceSOSApplyActivity.class), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
//                        break;
//                }
//            }
//        }
//    }

    @Override
    public void onRefresh() {
//        reqServiceInfoToServer();
    }

//    private void reqServiceInfoToServer() {
//        VehicleVO mainVehicle = null;
//        String custGbCd = "";
//        try {
//            mainVehicle = reqViewModel.getMainVehicle();
//            custGbCd = lgnViewModel.getUserInfoFromDB().getCustGbCd();
//        } catch (Exception e) {
//            mainVehicle = null;
//        } finally {
//            if (mainVehicle != null && !TextUtils.isEmpty(custGbCd) && custGbCd.equalsIgnoreCase(VariableType.MAIN_VEHICLE_TYPE_OV)) {
//                reqViewModel.reqREQ1001(new REQ_1001.Request(APPIAInfo.SM01.getId(), mainVehicle.getVin()));
//            }
//        }
//    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onClickCommon(View v) {
        String title="";
        int id = v.getId();
        Log.d(TAG, "onClickCommon: view id :" + id);
        /*
        TODO 임의로 체크 로직 주석 처리.
        try {
            if (!((FragmentService) getParentFragment()).checkCustGbCd(id, lgnViewModel.getUserInfoFromDB().getCustGbCd()))
                return;
        } catch (Exception e) {

        }
        */

        switch (id) {
            //충전소 검색
            case R.id.btn_service_charge_search:
                ((BaseActivity) getActivity()).startActivitySingleTop(
                        new Intent(getActivity(), ChargeSearchActivity.class),
                        RequestCodes.REQ_CODE_ACTIVITY.getCode(),
                        VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE
                );
                break;
            //충전소 예약 내역
            case R.id.l_service_charge_reservation_list:
                break;
            //여행 경로 추천
            case R.id.l_service_charge_recommend_trip:
                break;
            //충전 버틀러 서비스
            case R.id.l_service_charge_btr_service:

                break;
            //찾아가는 충전 서비스
            case R.id.l_service_charge_service:
                startServiceChargeActivity();
                break;
            case R.id.tv_service_maintenance_btn_black:
                title = v.getTag().toString();
                if(StringUtil.isValidString(title).equalsIgnoreCase(getString(R.string.sm_cg_sm02_11))){
                    startServiceChargeActivity();
                }else if(StringUtil.isValidString(title).equalsIgnoreCase(getString(R.string.sm_cg_sm02_7))){
                    //충전 버틀러 서비스 버틀러 신청
                }
                break;
            case R.id.tv_service_maintenance_btn_white:
                title = v.getTag().toString();
                if(StringUtil.isValidString(title).equalsIgnoreCase(getString(R.string.sm_cg_sm02_11))){
                    //찾아가는 충전 서비스 전화 신청
//                    String tel=mainVehicle.getMdlNm().equalsIgnoreCase("G90")||mainVehicle.getMdlNm().equalsIgnoreCase("EQ900") ? "080-900-6000" : "080-700-6000";
                    String sample = "080-700-6000";
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(WebView.SCHEME_TEL + sample)));
                }else if(StringUtil.isValidString(title).equalsIgnoreCase(getString(R.string.sm_cg_sm02_7))){
                    //충전 버틀러 서비스 신청 내역
                }
                break;
            default:
                //do nothing
                break;
        }
    }


    //TODO 전문 활성화 시 수정 필요
    private void startServiceChargeActivity() {

        if (!((MainActivity) getActivity()).isGpsEnable()) {
            MiddleDialog.dialogGPS(getActivity(), () -> ((MainActivity) getActivity()).turnGPSOn(isGPSEnable -> {
                Log.v("test","value:"+isGPSEnable);
            }), () -> {
                //현대양재사옥위치
            });
        } else {
            String pgrsStusCd = "test";
            try {
//                pgrsStusCd = reqViewModel.getRES_REQ_1001().getValue().data.getPgrsStusCd();
            } catch (Exception e) {
                pgrsStusCd = "";
            }

            if (!TextUtils.isEmpty(pgrsStusCd)) {
                switch (pgrsStusCd) {
                    case VariableType.SERVICE_SOS_STATUS_CODE_R://신청
                    case VariableType.SERVICE_SOS_STATUS_CODE_W://접수
                        ((BaseActivity) getActivity()).startActivitySingleTop(new Intent(getActivity(), ServiceChargeApplyInfoActivity.class), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                        break;
                    case VariableType.SERVICE_SOS_STATUS_CODE_S://출동
//                        sosViewModel.reqSOS1001(new SOS_1001.Request(APPIAInfo.SM01.getId()));
                        break;
                    case VariableType.SERVICE_SOS_STATUS_CODE_E://완료
                    case VariableType.SERVICE_SOS_STATUS_CODE_C://취소
                    default:
                        ((BaseActivity) getActivity()).startActivitySingleTop(new Intent(getActivity(), ServiceChargeApplyActivity.class), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                        break;
                }
            }
        }
    }

}
