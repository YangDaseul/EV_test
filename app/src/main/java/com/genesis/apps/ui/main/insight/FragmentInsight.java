package com.genesis.apps.ui.main.insight;

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
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.IST_1002;
import com.genesis.apps.comm.model.api.gra.IST_1003;
import com.genesis.apps.comm.model.api.gra.IST_1004;
import com.genesis.apps.comm.model.api.gra.IST_1005;
import com.genesis.apps.comm.model.api.gra.SOS_1001;
import com.genesis.apps.comm.model.api.gra.SOS_1006;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.ISTAmtVO;
import com.genesis.apps.comm.model.vo.SOSDriverVO;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.model.vo.map.FindPathReqVO;
import com.genesis.apps.comm.util.DeviceUtil;
import com.genesis.apps.comm.util.RecyclerViewDecoration;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.viewmodel.ISTViewModel;
import com.genesis.apps.comm.viewmodel.LGNViewModel;
import com.genesis.apps.comm.viewmodel.MapViewModel;
import com.genesis.apps.comm.viewmodel.SOSViewModel;
import com.genesis.apps.databinding.FragmentInsightBinding;
import com.genesis.apps.ui.common.activity.BaseActivity;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.fragment.SubFragment;
import com.genesis.apps.ui.main.MainActivity;
import com.genesis.apps.ui.main.insight.view.InsightArea1Adapter;
import com.genesis.apps.ui.main.insight.view.InsightArea2Adapter;
import com.genesis.apps.ui.main.insight.view.InsightArea3Adapter;
import com.genesis.apps.ui.main.insight.view.InsightCarAdapter;
import com.genesis.apps.ui.main.service.ServiceSOSRouteInfoActivity;
import com.hmns.playmap.PlayMapPoint;

import java.util.ArrayList;
import java.util.List;

public class FragmentInsight extends SubFragment<FragmentInsightBinding> {
    private ISTViewModel istViewModel;
    private LGNViewModel lgnViewModel;
    private SOSViewModel sosViewModel;
    private MapViewModel mapViewModel;

    private ConcatAdapter concatAdapter;
    private InsightCarAdapter insightCarAdapter;
    private InsightArea1Adapter insightArea1Adapter;
    private InsightArea2Adapter insightArea2Adapter;
    private InsightArea3Adapter insightArea3Adapter;
    private VehicleVO mainVehicleInfo;
    private String tmpAcptNo;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.setContentView(inflater, R.layout.fragment_insight);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        me.rvInsight.setLayoutManager(new LinearLayoutManager(getContext()));
        me.rvInsight.setHasFixedSize(true);
        me.rvInsight.addItemDecoration(new RecyclerViewDecoration((int) DeviceUtil.dip2Pixel(getContext(), 4.0f)));
        insightCarAdapter = new InsightCarAdapter(onSingleClickListener);
        insightArea1Adapter = new InsightArea1Adapter(onSingleClickListener);
        insightArea2Adapter = new InsightArea2Adapter(onSingleClickListener);
        insightArea3Adapter = new InsightArea3Adapter(onSingleClickListener);

        concatAdapter = new ConcatAdapter(insightCarAdapter, insightArea1Adapter, insightArea2Adapter, insightArea3Adapter);
        me.rvInsight.setAdapter(concatAdapter);

        istViewModel = new ViewModelProvider(getActivity()).get(ISTViewModel.class);
        lgnViewModel = new ViewModelProvider(getActivity()).get(LGNViewModel.class);
        sosViewModel = new ViewModelProvider(this).get(SOSViewModel.class);
        mapViewModel = new ViewModelProvider(this).get(MapViewModel.class);

        setObserve();
    }

    private void setObserve() {
        istViewModel.getRES_IST_1002().observe(getViewLifecycleOwner(), result -> {
            switch (result.status) {
                case LOADING:
                    ((MainActivity) getActivity()).showProgressDialog(true);
                    break;
                case SUCCESS:
                    ((MainActivity) getActivity()).showProgressDialog(false);
                    List<ISTAmtVO> list = new ArrayList<>();
                    if (result.data != null&&result.data.getRtCd().equalsIgnoreCase("0000")) {
                        String preUseAmt = "0";

                        try {
                            preUseAmt = result.data.getPrvsMthAmt().get(0).getTotUseAmt()==null ? "0" : result.data.getPrvsMthAmt().get(0).getTotUseAmt();
                        } catch (Exception e) {
                            preUseAmt = "0";
                        }

                        insightCarAdapter.setPrvsToUseAmt(preUseAmt);
                        if(result.data.getCurrMthAmt()==null||result.data.getCurrMthAmt().size()<1){ //정책으로 데이터가 없을 때도 그래프를 정상적으로 출력
                            list.add(new ISTAmtVO("0", "0", "0", "0", "0"));
                        }else{
                            list.add(result.data.getCurrMthAmt().get(0));
                        }
                        insightCarAdapter.setViewType(InsightCarAdapter.TYPE_CAR);
                    }else{
                        list.add(new ISTAmtVO("0", "0", "0", "0", "0"));
                        insightCarAdapter.setViewType(InsightCarAdapter.TYPE_EMPTY);
                    }
                    insightCarAdapter.setRows(list);
                    insightCarAdapter.notifyDataSetChanged();
                    break;
                default:
                    ((MainActivity) getActivity()).showProgressDialog(false);
                    break;
            }
        });


        istViewModel.getRES_IST_1003().observe(getViewLifecycleOwner(), result -> {
            switch (result.status) {
                case LOADING:
                    ((MainActivity) getActivity()).showProgressDialog(true);
                    break;
                case SUCCESS:
                    ((MainActivity) getActivity()).showProgressDialog(false);
                    if (result.data != null && result.data.getAdmMsgList() != null) {
                        insightArea1Adapter.setRows(result.data.getAdmMsgList());
                        insightArea1Adapter.notifyDataSetChanged();
                    }
                    break;
                default:
                    ((MainActivity) getActivity()).showProgressDialog(false);
                    break;
            }
        });


        istViewModel.getRES_IST_1005().observe(getViewLifecycleOwner(), result -> {
            switch (result.status) {
                case LOADING:
                    ((MainActivity) getActivity()).showProgressDialog(true);
                    break;
                case SUCCESS:
                    ((MainActivity) getActivity()).showProgressDialog(false);
                    if (result.data != null && result.data.getMsgList() != null) {
                        insightArea3Adapter.setRows(result.data.getMsgList());
                        insightArea3Adapter.notifyDataSetChanged();
                    }
                    break;
                default:
                    ((MainActivity) getActivity()).showProgressDialog(false);
                    break;
            }
        });

        istViewModel.getRES_IST_1004().observe(getViewLifecycleOwner(), result -> {
            switch (result.status) {
                case LOADING:
                    ((MainActivity) getActivity()).showProgressDialog(true);
                    break;
                case SUCCESS:
                    ((MainActivity) getActivity()).showProgressDialog(false);
                    if (result.data != null&&result.data.getSosStatus()!=null) {
                        SOSDriverVO sosDriverVO = null;
                        try{
                            sosDriverVO = (SOSDriverVO) result.data.getSosStatus().clone();
                            mapViewModel.reqFindPathResVo(new FindPathReqVO("0","0","0","2","0",new PlayMapPoint(Double.parseDouble(sosDriverVO.getGYpos()),Double.parseDouble(sosDriverVO.getGXpos())),new ArrayList(), new PlayMapPoint(Double.parseDouble(sosDriverVO.getGCustY()),Double.parseDouble(sosDriverVO.getGCustX()))));
                        }catch (Exception e){

                        }
                    }
                    break;
                default:
                    ((MainActivity) getActivity()).showProgressDialog(false);
                    break;
            }
        });

        mapViewModel.getFindPathResVo().observe(getViewLifecycleOwner(), result -> {
            if(result!=null&&result.status!=null) {
                switch (result.status) {
                    case LOADING:
                        ((SubActivity) getActivity()).showProgressDialog(true);
                        break;
                    case SUCCESS:
                        if(result.data!=null&&result.data.getSummary()!=null) {

                            int minute=0;
                            List<SOSDriverVO> list = new ArrayList<>();
                            try {
                                minute = result.data.getSummary().getTotalTime()/60;
                                SOSDriverVO sosDriverVO = (SOSDriverVO) istViewModel.getRES_IST_1004().getValue().data.getSosStatus().clone();
                                if (sosDriverVO != null) {
                                    sosDriverVO.setMinute(minute+"");
                                    list.add(sosDriverVO);
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }finally {
                                insightArea2Adapter.setRows(list);
                                insightArea2Adapter.notifyDataSetChanged();
                                ((SubActivity) getActivity()).showProgressDialog(false);
                            }
                            break;
                        }
                    default:
                        ((SubActivity) getActivity()).showProgressDialog(false);
                        break;
                }
            }

        });

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
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onClickCommon(View v) {

        switch (v.getId()) {
            case R.id.l_car_expn_graph:
            case R.id.chart:
                //차계부 클릭 시
                try {
                    switch (lgnViewModel.getUserInfoFromDB().getCustGbCd()) {
                        case VariableType.MAIN_VEHICLE_TYPE_OV://소유
                            ((MainActivity) getActivity()).startActivitySingleTop(new Intent(getActivity(), InsightExpnMainActivity.class), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                            break;
                        default:
                            SnackBarUtil.show(getActivity(), getString(R.string.sm01_snack_bar));
                            break;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

                break;
            case R.id.btn_driver_position:
                //긴급출동
                sosViewModel.reqSOS1001(new SOS_1001.Request(APPIAInfo.TM01.getId()));
                break;

        }
   }


    @Override
    public void onRefresh() {
        Log.e("onResume", "onReusme FragmentInsight");
        try {
            mainVehicleInfo = lgnViewModel.getMainVehicleSimplyFromDB();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            initView();
            ((MainActivity) getActivity()).setGNB(false, 0, View.VISIBLE);
        }
    }

    private void initView() {
        try {
            switch (lgnViewModel.getUserInfoFromDB().getCustGbCd()) {
                case VariableType.MAIN_VEHICLE_TYPE_OV://소유
                    istViewModel.reqIST1002(new IST_1002.Request(APPIAInfo.TM01.getId(), "INSGT", "CBK", mainVehicleInfo.getVin()));
                    istViewModel.reqIST1004(new IST_1004.Request(APPIAInfo.TM01.getId(), "INSGT", "INS-02", mainVehicleInfo.getVin()));
                case VariableType.MAIN_VEHICLE_TYPE_CV://계약
                case VariableType.MAIN_VEHICLE_TYPE_NV://차량없음
                    istViewModel.reqIST1003(new IST_1003.Request(APPIAInfo.TM01.getId(), "INSGT", "INS-01"));
                    istViewModel.reqIST1005(new IST_1005.Request(APPIAInfo.TM01.getId(), "INSGT", "INS-03", String.valueOf(lgnViewModel.getPositionValue().get(1)), String.valueOf(lgnViewModel.getPositionValue().get(0)), mainVehicleInfo.getVin(), mainVehicleInfo.getMdlNm()));
                    me.ivInfo1.setVisibility(View.GONE);
                    me.ivInfo2.setVisibility(View.GONE);
                    break;
                case VariableType.MAIN_VEHICLE_TYPE_0000: //미로그인
                default:
                    me.ivInfo1.setVisibility(View.VISIBLE);
                    me.ivInfo2.setVisibility(View.VISIBLE);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
