package com.genesis.apps.ui.main.service;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.REQ_1014;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.RepairHistVO;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.comm.viewmodel.REQViewModel;
import com.genesis.apps.databinding.FragmentServiceRepairHistoryBinding;
import com.genesis.apps.ui.common.activity.BaseActivity;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.fragment.SubFragment;
import com.genesis.apps.ui.main.service.view.ServiceRepairReserveHistoryAdapter;

import java.util.ArrayList;
import java.util.List;

public class FragmentServiceRepairHistory extends SubFragment<FragmentServiceRepairHistoryBinding> {
    private static final int PAGE_SIZE = 10;
    private ServiceRepairReserveHistoryAdapter adapter;
    private REQViewModel reqViewModel;
    private VehicleVO mainVehicle;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.setContentView(inflater, R.layout.fragment_service_repair_history);
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

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onClickCommon(View v) {
        switch (v.getId()){
            case R.id.btn_repair_image:
                RepairHistVO item = ((RepairHistVO)v.getTag(R.id.item));
                if(item!=null){
                    ((BaseActivity) getActivity()).startActivitySingleTop(new Intent(getActivity(), ServiceRepairImageActivity.class)
                                    .putExtra(KeyNames.KEY_NAME_ASNCD, item.getAsnCd())
                                    .putExtra(KeyNames.KEY_NAME_VEHICLE_IN_OUT_NO, item.getVhclInoutNo())
                                    .putExtra(KeyNames.KEY_NAME_WRHS_NO, item.getWrhsNo())
                            , RequestCodes.REQ_CODE_ACTIVITY.getCode()
                            , VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                }
                break;
        }

    }

    @Override
    public void onRefresh() {
        if (mainVehicle != null)
            requestREQ1014("1");
    }

    private void setViewModel() {
        reqViewModel = new ViewModelProvider(this).get(REQViewModel.class);
    }

    private void setObserver() {

        reqViewModel.getRES_REQ_1014().observe(getViewLifecycleOwner(), result -> {
            switch (result.status) {
                case LOADING:
                    ((SubActivity) getActivity()).showProgressDialog(true);
                    break;
                case SUCCESS:
                    if (result.data != null && result.data.getRsvtStatList() != null) {
                        if (result.data.getRsvtStatList().size() > 0) {
                            int itemSizeBefore = adapter.getItemCount();
                            //서버로 부터 받은 리스트를 adapter에 추가
                            if (adapter.getPageNo() == 0) {
                                adapter.setRows(result.data.getRsvtStatList());
                            } else {
                                adapter.addRows(result.data.getRsvtStatList());
                            }

                            List<RepairHistVO> newList = new ArrayList<>();
                            try {
                                newList.addAll(reqViewModel.getRepairHistList(adapter.getItems()));//adapter에 들어있는 데이터의 날짜 기준으로 다시 sort 진행
                                adapter.setRows(newList);//adpter에 sort된 리스트를 set
                                adapter.setPageNo(adapter.getPageNo() + 1);
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
//                                adapter.notifyItemRangeInserted(itemSizeBefore, adapter.getItemCount());
                                adapter.notifyDataSetChanged(); //날짜로 sort를 전체 다시 했기 때문에.. 전체 아이템을 다 갱신해줘야함.........
                            }
                        }

                        if (adapter != null && adapter.getItemCount() < 1) {
                            setViewEmpty();
                        } else {
                            me.lEmpty.lWhole.setVisibility(View.GONE);
                        }

                        ((SubActivity) getActivity()).showProgressDialog(false);
                        break;
                    }
                default:
                    setViewEmpty();
                    ((SubActivity) getActivity()).showProgressDialog(false);
                    String serverMsg = "";
                    try {
                        serverMsg = result.data.getRtMsg();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (StringUtil.isValidString(result.data.getRtCd()).equalsIgnoreCase("2005"))//조회된 정보가 없을 경우 에러메시지 출력하지 않음
                        return;

                    if (TextUtils.isEmpty(serverMsg)) {
                        serverMsg = getString(R.string.r_flaw06_p02_snackbar_1);
                    }
                    SnackBarUtil.show(getActivity(), serverMsg);
                    break;
            }
        });

    }

    private void initView() {
        me.rv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ServiceRepairReserveHistoryAdapter(onSingleClickListener);
        me.rv.setHasFixedSize(true);
        me.rv.setAdapter(adapter);
        me.rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!me.rv.canScrollVertically(1)&&me.rv.getScrollState()==RecyclerView.SCROLL_STATE_IDLE) {//scroll end
                    if (adapter.getItemCount() >= adapter.getPageNo() * PAGE_SIZE)
                        requestREQ1014((adapter.getPageNo() + 1) + "");
                }
            }
        });
    }

    private void initData() {
        try {
            if (mainVehicle == null) mainVehicle = reqViewModel.getMainVehicle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void requestREQ1014(String pageNo) {
        reqViewModel.reqREQ1014(new REQ_1014.Request(APPIAInfo.SM_R_HISTORY01.getId(), mainVehicle.getVin(), pageNo, PAGE_SIZE + ""));
    }

    private void setViewEmpty() {
        me.rv.setVisibility(View.GONE);
        me.lEmpty.lWhole.setVisibility(View.VISIBLE); //empty 뷰 활성화
    }

}
