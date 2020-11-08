package com.genesis.apps.ui.main.service;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.gra.APPIAInfo;
import com.genesis.apps.comm.model.gra.api.REQ_1004;
import com.genesis.apps.comm.model.vo.RentStatusVO;
import com.genesis.apps.comm.model.vo.RepairTypeVO;
import com.genesis.apps.comm.net.NetUIResponse;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.viewmodel.REQViewModel;
import com.genesis.apps.comm.viewmodel.WSHViewModel;
import com.genesis.apps.databinding.ActivityMaintenanceReserveBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.bottom.BottomListDialog;

import java.util.List;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class MaintenanceReserveActivity extends SubActivity<ActivityMaintenanceReserveBinding> {
    private static final String TAG = MaintenanceReserveActivity.class.getSimpleName();

    private REQViewModel reqViewModel;
    private String avlRsrYn;
    private RepairTypeVO selectRepairTypeVO;
    private List<RepairTypeVO> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_reserve);
        getDataFromIntent();
        setViewModel();
        setObserver();
        reqViewModel.reqREQ1004(new REQ_1004.Request(APPIAInfo.SM_R_RSV01.getId()));
    }

    @Override
    public void onClickCommon(View v) {
        switch (v.getId()){
            case R.id.tv_maintenance_category_select_btn:
                showDialogRepairType(list);
                break;
        }
    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        ui.setActivity(this);
        reqViewModel = new ViewModelProvider(this).get(REQViewModel.class);
    }

    @Override
    public void setObserver() {


        reqViewModel.getRES_REQ_1004().observe(this, result -> {
            switch (result.status){
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    showProgressDialog(false);
                    list = result.data.getRparTypList();
                default:
                    showProgressDialog(false);
                    break;
            }
        });

    }

    @Override
    public void getDataFromIntent() {
        try {
            avlRsrYn = getIntent().getStringExtra(KeyNames.KEY_NAME_MAINTENANCE_AVL_RSR_YN);
            selectRepairTypeVO = (RepairTypeVO) getIntent().getSerializableExtra(KeyNames.KEY_NAME_MAINTENANCE_REPAIR_TYPE);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (TextUtils.isEmpty(avlRsrYn)){
                exitPage("정비 예약 정보가 존재하지 않습니다.\n잠시후 다시 시도해 주십시오.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
            }else if(avlRsrYn.equalsIgnoreCase("N")){
                ui.lMaintenanceRepair.lMaintenanceCategoryItemBtn.setBackgroundResource(R.drawable.bg_11000000_round_10);
                ui.lMaintenanceRepair.lMaintenanceCategoryItemBtn.setOnClickListener(null);
            }else{
                ui.lMaintenanceRepair.lMaintenanceCategoryItemBtn.setBackgroundResource(R.drawable.ripple_bg_ffffff_round_10);
                ui.lMaintenanceRepair.lMaintenanceCategoryItemBtn.setOnClickListener(onSingleClickListener);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private void showDialogRepairType(final List<RepairTypeVO> list) {

        if(list==null||list.size()<1)
            return;

        try {
            final BottomListDialog bottomListDialog = new BottomListDialog(this, R.style.BottomSheetDialogTheme);
            bottomListDialog.setOnDismissListener(dialogInterface -> {
                String result = bottomListDialog.getSelectItem();
                if (!TextUtils.isEmpty(result)) {
                    try{
                        selectRepairTypeVO = reqViewModel.getRepairTypeCd(result, list);
                        ui.tvMaintenanceCategorySelectBtn.setText(result);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
            bottomListDialog.setDatas(reqViewModel.getRepairTypeNm(list));
            bottomListDialog.setTitle(getString(R.string.sm_snfind01_p01_1));
            bottomListDialog.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
