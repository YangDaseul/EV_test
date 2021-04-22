package com.genesis.apps.ui.main.service;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.STC_1005;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.comm.viewmodel.STCViewModel;
import com.genesis.apps.databinding.ActivityChargeReserveHistoryBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.main.service.view.ChargeReserveHistoryAdapter;

/**
 * @author hjpark
 * @brief 충전소 예약 내역
 */
public class ChargeReserveHistoryActivity extends SubActivity<ActivityChargeReserveHistoryBinding> {

    private ChargeReserveHistoryAdapter adapter;
    private STCViewModel stcViewModel;
    private VehicleVO mainVehicle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charge_reserve_history);
        setViewModel();
        getDataFromIntent();
        setObserver();
        initView();
    }

    private void initView() {
        ui.rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ChargeReserveHistoryAdapter(onSingleClickListener);
        ui.rv.setHasFixedSize(true);
        ui.rv.setAdapter(adapter);
    }

    @Override
    public void onClickCommon(View v) {
        switch (v.getId()) {
            //todo 취소버튼
        }
    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        stcViewModel = new ViewModelProvider(this).get(STCViewModel.class);
    }

    @Override
    public void setObserver() {
        //예약 내역 조회
        stcViewModel.getRES_STC_1005().observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    if (result.data != null && result.data.getReservList() != null) {
                        adapter.setRows(result.data.getReservList());
                        adapter.setPageNo(adapter.getPageNo() + 1);
                        adapter.notifyDataSetChanged();
                        setViewEmpty();
                        showProgressDialog(false);
                        break;
                    }
                default:
                    setViewEmpty();
                    showProgressDialog(false);
                    String serverMsg = "";
                    try {
                        serverMsg = result.data.getRtMsg();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (result.data!=null&& StringUtil.isValidString(result.data.getRtCd()).equalsIgnoreCase("2005"))//조회된 정보가 없을 경우 에러메시지 출력하지 않음
                        return;

                    if (TextUtils.isEmpty(serverMsg)) {
                        serverMsg = getString(R.string.r_flaw06_p02_snackbar_1);
                    }
                    SnackBarUtil.show(this, serverMsg);
                    break;
            }
        });

    }
    @Override
    public void getDataFromIntent() {
        try {
            if (mainVehicle == null) mainVehicle = stcViewModel.getMainVehicle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mainVehicle!=null) stcViewModel.reqSTC1005(new STC_1005.Request(APPIAInfo.SM_EVSB02.getId(), mainVehicle.getVin(), "", ""));
    }

    private void setViewEmpty() {
        if (adapter == null || adapter.getItemCount() < 1) {
            ui.rv.setVisibility(View.GONE);
            ui.lEmpty.lWhole.setVisibility(View.VISIBLE); //empty 뷰
        } else {
            ui.lEmpty.lWhole.setVisibility(View.GONE);
            ui.rv.setVisibility(View.VISIBLE);
        }
    }
}
