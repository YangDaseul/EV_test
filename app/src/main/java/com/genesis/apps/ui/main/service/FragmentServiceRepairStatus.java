package com.genesis.apps.ui.main.service;

import android.os.Bundle;
import android.text.TextUtils;
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
import com.genesis.apps.comm.model.api.gra.REQ_1013;
import com.genesis.apps.comm.model.vo.RepairReserveVO;
import com.genesis.apps.comm.model.vo.RepairVO;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.viewmodel.REQViewModel;
import com.genesis.apps.databinding.FragmentServiceRepairStatusBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.fragment.SubFragment;
import com.genesis.apps.ui.main.service.view.ServiceRepairCurrentStatusAdapter;
import com.genesis.apps.ui.main.service.view.ServiceRepairReserveStatusAdapter;

import java.util.ArrayList;
import java.util.List;

public class FragmentServiceRepairStatus extends SubFragment<FragmentServiceRepairStatusBinding> {

    private ServiceRepairCurrentStatusAdapter serviceRepairCurrentStatusAdapter;
    private ServiceRepairReserveStatusAdapter serviceRepairReserveStatusAdapter;

    private ConcatAdapter concatAdapter;
    private REQViewModel reqViewModel;
    private VehicleVO mainVehicle;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.setContentView(inflater, R.layout.fragment_service_repair_status);
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
        reqDataFromServer();
    }

    private void initView() {
        me.rv.setLayoutManager(new LinearLayoutManager(getContext()));
        serviceRepairCurrentStatusAdapter = new ServiceRepairCurrentStatusAdapter(getActivity());
        serviceRepairReserveStatusAdapter = new ServiceRepairReserveStatusAdapter(onSingleClickListener);
        concatAdapter = new ConcatAdapter(serviceRepairCurrentStatusAdapter, serviceRepairReserveStatusAdapter);
        me.rv.setHasFixedSize(true);
        me.rv.setAdapter(concatAdapter);
    }

    private void reqDataFromServer() {
        try {
            if (mainVehicle == null) mainVehicle = reqViewModel.getMainVehicle();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mainVehicle != null)
                reqViewModel.reqREQ1013(new REQ_1013.Request(APPIAInfo.SM_R_RSV05.getId(), mainVehicle.getVin()));
        }
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onClickCommon(View v) {

    }

    @Override
    public void onRefresh() {

    }

    private void setViewModel() {
        reqViewModel = new ViewModelProvider(this).get(REQViewModel.class);
    }

    private void setObserver() {
        reqViewModel.getRES_REQ_1013().observe(getViewLifecycleOwner(), result -> {

            switch (result.status) {
                case LOADING:
                    ((SubActivity) getActivity()).showProgressDialog(true);
                    break;
                case SUCCESS:
                    ((SubActivity) getActivity()).showProgressDialog(false);
                    if (result.data != null) {

                        List<RepairReserveVO> rsvtStatList = new ArrayList<>();
                        rsvtStatList.addAll(result.data.getRsvtStatList());
                        RepairVO currentData = null;
                        try{
                            currentData = (RepairVO)result.data.getRparStatus().clone();
                        }catch (Exception e){

                        }

                        if ((rsvtStatList == null || rsvtStatList.size() == 0) //예약내역리스트가 0 혹은 null이고
                                && (currentData == null || currentData.getAsnCd() == null || TextUtils.isEmpty(currentData.getAsnCd()))) //정비현황이 null 혹은 비어있으면
                        {
                            setViewEmpty();
                        } else {
                            if (rsvtStatList == null || rsvtStatList.size() == 0) {
                                rsvtStatList = new ArrayList<>(); //예약내역리스트가 0 혹은 null이면 empty view를 활성화 시킬 수 있도록 데이터 처리
                                rsvtStatList.add(new RepairReserveVO()); //RepairReserveVO 생성 시 rsvtTypCd가 빈값으로 생성됨
                            }
                            rsvtStatList.add(0, new RepairReserveVO()); //헤더 추가
                            if (currentData == null) {
                                currentData = new RepairVO(); //정비현황 인스턴스 초기화
                            }

                            currentData.setStusCd(TextUtils.isEmpty(result.data.getStusCd()) ? "" : result.data.getStusCd()); //stusCd가 빈값이면 뷰가 자동으로 비활성화 됨
                            List<RepairVO> repairVOList = new ArrayList<>();
                            repairVOList.add(currentData);
                            serviceRepairCurrentStatusAdapter.setRows(repairVOList);
                            serviceRepairReserveStatusAdapter.setRows(rsvtStatList);
                            serviceRepairCurrentStatusAdapter.notifyDataSetChanged();
                            serviceRepairReserveStatusAdapter.notifyDataSetChanged();
                        }
                        break;
                    }
                default:
                    setViewEmpty();

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

    private void setViewEmpty() {
        me.rv.setVisibility(View.GONE);
        me.lEmpty.lWhole.setVisibility(View.VISIBLE); //empty 뷰 활성화
    }

}
