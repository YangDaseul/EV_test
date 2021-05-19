package com.genesis.apps.ui.main.service;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.STC_1005;
import com.genesis.apps.comm.model.api.gra.STC_1006;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.ReserveHisVO;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.comm.viewmodel.STCViewModel;
import com.genesis.apps.databinding.ActivityChargeReserveHistoryBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;
import com.genesis.apps.ui.main.service.view.ChargeReserveHistoryAdapter;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.genesis.apps.comm.model.api.BaseResponse.RETURN_CODE_SUCC;

/**
 * @author hjpark
 * @brief 충전소 예약 내역
 */
public class ChargeReserveHistoryActivity extends SubActivity<ActivityChargeReserveHistoryBinding> {
    private static final int PAGE_SIZE = 20;
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
        ui.rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!ui.rv.canScrollVertically(1)&&ui.rv.getScrollState()==RecyclerView.SCROLL_STATE_IDLE) {//scroll end
                    if (adapter.getItemCount()>0&&adapter.getItemCount() >= adapter.getPageNo() * PAGE_SIZE)
                        requestSTC1005((adapter.getPageNo() + 1) + "");
                }
            }
        });
    }

    @Override
    public void onClickCommon(View v) {
        ReserveHisVO reserveHisVO = (ReserveHisVO) v.getTag();
        switch (v.getId()) {
            case R.id.btn_cancel:
                try{
                    if(reserveHisVO!=null){
                        MiddleDialog.dialogChargeReserveCancel(this, () -> {
                            stcViewModel.reqSTC1006(new STC_1006.Request(APPIAInfo.SM_EVSB02.getId(), reserveHisVO.getReservNo()));
                        }, () -> {

                        });
                    }
                }catch (Exception e){

                }
                break;
            case R.id.tv_chg_name://충전소 클릭
                if(reserveHisVO!=null){
                    startActivitySingleTop(new Intent(this, ChargeStationDetailActivity.class)
                                    .putExtra(KeyNames.KEY_NAME_CHARGE_STATION_CSID, reserveHisVO.getSid()),
//                                    .putExtra(KeyNames.KEY_NAME_LAT, "lat")
//                                    .putExtra(KeyNames.KEY_NAME_LOT, "lot"),
                            RequestCodes.REQ_CODE_ACTIVITY.getCode(),
                            VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                }
                break;
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
                    if (result.data != null && StringUtil.isValidString(result.data.getRtCd()).equalsIgnoreCase(RETURN_CODE_SUCC)) {

                        if(result.data.getReservList()!=null&&result.data.getReservList().size()>0){
                            if (adapter.getPageNo() == 0) {
                                adapter.setRows(result.data.getReservList());
                            } else {
                                adapter.addRows(result.data.getReservList());
                            }
                            adapter.setPageNo(adapter.getPageNo() + 1);
                            adapter.notifyDataSetChanged();
                        }
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

        //예약 취소
        stcViewModel.getRES_STC_1006().observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    if (result.data != null && StringUtil.isValidString(result.data.getRtCd()).equalsIgnoreCase(RETURN_CODE_SUCC)) {
                        showProgressDialog(false);
                        SnackBarUtil.show(this, getString(R.string.sm_evsb02_p01_3));
                        stcViewModel.reqSTC1005(new STC_1005.Request(APPIAInfo.SM_EVSB02.getId(), mainVehicle.getVin(), "", "","",""));
                        break;
                    }
                default:
                    showProgressDialog(false);
                    String serverMsg = "";
                    try {
                        serverMsg = result.data.getRtMsg();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
        requestSTC1005("1");
    }

    private void requestSTC1005(String pageNo) {
        if(adapter!=null&&pageNo.equalsIgnoreCase("1"))
            adapter.setPageNo(0);

        if(mainVehicle!=null)
            stcViewModel.reqSTC1005(new STC_1005.Request(APPIAInfo.SM_EVSB02.getId(), mainVehicle.getVin(), "", "",pageNo,PAGE_SIZE+""));
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
