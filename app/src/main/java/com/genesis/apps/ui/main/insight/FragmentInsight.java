package com.genesis.apps.ui.main.insight;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.gra.APPIAInfo;
import com.genesis.apps.comm.model.gra.api.IST_1002;
import com.genesis.apps.comm.model.gra.api.IST_1003;
import com.genesis.apps.comm.model.gra.api.IST_1004;
import com.genesis.apps.comm.model.gra.api.IST_1005;
import com.genesis.apps.comm.model.gra.api.LGN_0005;
import com.genesis.apps.comm.model.vo.ISTAmtVO;
import com.genesis.apps.comm.model.vo.SOSDriverVO;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.net.NetUIResponse;
import com.genesis.apps.comm.util.DeviceUtil;
import com.genesis.apps.comm.util.RecyclerViewDecoration;
import com.genesis.apps.comm.viewmodel.ISTViewModel;
import com.genesis.apps.comm.viewmodel.LGNViewModel;
import com.genesis.apps.databinding.FragmentInsightBinding;
import com.genesis.apps.ui.common.fragment.SubFragment;
import com.genesis.apps.ui.main.MainActivity;
import com.genesis.apps.ui.main.insight.view.InsightArea1Adapter;
import com.genesis.apps.ui.main.insight.view.InsightArea2Adapter;
import com.genesis.apps.ui.main.insight.view.InsightArea3Adapter;
import com.genesis.apps.ui.main.insight.view.InsightCarAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FragmentInsight extends SubFragment<FragmentInsightBinding> {
    private ISTViewModel istViewModel;
    private LGNViewModel lgnViewModel;

    private ConcatAdapter concatAdapter;
    private InsightCarAdapter insightCarAdapter;
    private InsightArea1Adapter insightArea1Adapter;
    private InsightArea2Adapter insightArea2Adapter;
    private InsightArea3Adapter insightArea3Adapter;
    private VehicleVO mainVehicleInfo;

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
        me.rvInsight.addItemDecoration(new RecyclerViewDecoration((int) DeviceUtil.dip2Pixel(getContext(),4.0f)));
        insightCarAdapter = new InsightCarAdapter(onSingleClickListener);
        insightArea1Adapter = new InsightArea1Adapter(onSingleClickListener);
        insightArea2Adapter = new InsightArea2Adapter(onSingleClickListener);
        insightArea3Adapter = new InsightArea3Adapter(onSingleClickListener);

        concatAdapter = new ConcatAdapter(insightCarAdapter, insightArea1Adapter,insightArea2Adapter,insightArea3Adapter);
        me.rvInsight.setAdapter(concatAdapter);

        istViewModel = new ViewModelProvider(getActivity()).get(ISTViewModel.class);
        lgnViewModel = new ViewModelProvider(getActivity()).get(LGNViewModel.class);
        setObserve();
    }

    private void setObserve() {
        istViewModel.getRES_IST_1002().observe(getViewLifecycleOwner(), result -> {
            switch (result.status){
                case LOADING:
                    ((MainActivity)getActivity()).showProgressDialog(true);
                    break;
                case SUCCESS:
                    ((MainActivity)getActivity()).showProgressDialog(false);
                    if(result.data!=null){


                        String preUseAmt="0";
                        String curUseAmt="0";
                        try{
                            preUseAmt = result.data.getPrvsMthAmt().getTotUseAmt();
                        }catch (Exception e){
                            preUseAmt="0";
                        }

                        try{
                            curUseAmt = result.data.getCurrMthAmt().getTotUseAmt();
                        }catch (Exception e){
                            curUseAmt="0";
                        }
                        List<ISTAmtVO> list = new ArrayList<>();
                        if(!TextUtils.isEmpty(curUseAmt)&&!curUseAmt.equalsIgnoreCase("0")) {
                            insightCarAdapter.setPrvsToUseAmt(preUseAmt);
                            list.add(result.data.getCurrMthAmt());
                            insightCarAdapter.setViewType(InsightCarAdapter.TYPE_CAR);
                        }else{
                            list.add(new ISTAmtVO("0","0","0","0","0"));
                            insightCarAdapter.setViewType(InsightCarAdapter.TYPE_EMPTY);
                        }
                        insightCarAdapter.setRows(list);
                        insightCarAdapter.notifyDataSetChanged();
                    }
                    break;
                default:
                    ((MainActivity)getActivity()).showProgressDialog(false);
                    break;
            }
        });


        istViewModel.getRES_IST_1003().observe(getViewLifecycleOwner(), result -> {
            switch (result.status){
                case LOADING:
                    ((MainActivity)getActivity()).showProgressDialog(true);
                    break;
                case SUCCESS:
                    ((MainActivity)getActivity()).showProgressDialog(false);
                    if(result.data!=null&&result.data.getAdmMsgList()!=null){
                        insightArea1Adapter.setRows(result.data.getAdmMsgList());
                        insightArea1Adapter.notifyDataSetChanged();
                    }
                    break;
                default:
                    ((MainActivity)getActivity()).showProgressDialog(false);
                    break;
            }
        });


        istViewModel.getRES_IST_1005().observe(getViewLifecycleOwner(), result -> {
            switch (result.status){
                case LOADING:
                    ((MainActivity)getActivity()).showProgressDialog(true);
                    break;
                case SUCCESS:
                    ((MainActivity)getActivity()).showProgressDialog(false);
                    if(result.data!=null&&result.data.getMsgList()!=null){
                        insightArea3Adapter.setRows(result.data.getMsgList());
                        insightArea3Adapter.notifyDataSetChanged();
                    }
                    break;
                default:
                    ((MainActivity)getActivity()).showProgressDialog(false);
                    break;
            }
        });

        istViewModel.getRES_IST_1004().observe(getViewLifecycleOwner(), result -> {
            switch (result.status){
                case LOADING:
                    ((MainActivity)getActivity()).showProgressDialog(true);
                    break;
                case SUCCESS:
                    ((MainActivity)getActivity()).showProgressDialog(false);
                    if(result.data!=null){
                        List<SOSDriverVO> list = new ArrayList<>();
                        if(result.data.getSosStatus()!=null){
                            list.add(result.data.getSosStatus());
                        }
                        insightArea2Adapter.setRows(list);
                        insightArea2Adapter.notifyDataSetChanged();
                    }
                    break;
                default:
                    ((MainActivity)getActivity()).showProgressDialog(false);
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

        switch (v.getId()){


        }
    }


    @Override
    public void onRefresh() {
        try {
            mainVehicleInfo = lgnViewModel.getMainVehicleFromDB();
        }catch (Exception e){
            e.printStackTrace();
        }
        initView();
        ((MainActivity)getActivity()).setGNBColor(0);
    }

    //TODO 로그인 되었을 때 상태 처리 전체적으로 필요
    private void initView() {
        try{
            switch (lgnViewModel.getUserInfoFromDB().getCustGbCd()){
                case VariableType.MAIN_VEHICLE_TYPE_OV://소유
                    //TODO 소유차량 표기
                    istViewModel.reqIST1002(new IST_1002.Request(APPIAInfo.TM01.getId(), "INSGT", "CBK", mainVehicleInfo.getVin()));


                    istViewModel.reqIST1003(new IST_1003.Request(APPIAInfo.TM01.getId(), "INSGT", "INS-01"));
                    istViewModel.reqIST1004(new IST_1004.Request(APPIAInfo.TM01.getId(), "INSGT", "INS-02"));
                    Double[] position = lgnViewModel.getPosition();
                    istViewModel.reqIST1005(new IST_1005.Request(APPIAInfo.TM01.getId(), "INSGT", "INS-03",  String.valueOf(position[0]),String.valueOf(position[1]),mainVehicleInfo.getVin(),mainVehicleInfo.getMdlNm()));
                    break;
                case VariableType.MAIN_VEHICLE_TYPE_CV://계약
                case VariableType.MAIN_VEHICLE_TYPE_NV://차량없음
                    //TODO 정의필요
                    break;
                case VariableType.MAIN_VEHICLE_TYPE_0000: //미로그인
                default:
                    //TODO 미로그인 처리
                    break;
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }




}
