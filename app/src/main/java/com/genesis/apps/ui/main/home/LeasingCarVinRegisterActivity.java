package com.genesis.apps.ui.main.home;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.gra.APPIAInfo;
import com.genesis.apps.comm.model.gra.api.PUB_1002;
import com.genesis.apps.comm.model.gra.api.PUB_1003;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.viewmodel.BTRViewModel;
import com.genesis.apps.comm.viewmodel.PUBViewModel;
import com.genesis.apps.databinding.ActivityBtrFilterBinding;
import com.genesis.apps.databinding.ActivityLeasingCarRegisterBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.bottom.BottomListDialog;

import java.util.List;

/**
 * @author hjpark
 * @brief 렌트/리스 실 운행자 등록 (차대번호 입력)
 */
public class LeasingCarVinRegisterActivity extends SubActivity<ActivityLeasingCarRegisterBinding> {
    private BTRViewModel btrViewModel;
    private PUBViewModel pubViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leasing_car_register);
        getDataFromIntent();
        setViewModel();
        setObserver();
        initView();
//        pubViewModel.reqPUB1002(new PUB_1002.Request(APPIAInfo.GM_BT06_01.getId()));
    }

    private void initView() {

    }

    @Override
    public void onClickCommon(View v) {

        switch (v.getId()){
            case R.id.btn_check:
                break;
        }

    }

//    private void showMapDialog(int id, List<String> list, int title) {
//        if(list!=null&&list.size()>0){
//            final BottomListDialog bottomListDialog = new BottomListDialog(this, R.style.BottomSheetDialogTheme);
//            bottomListDialog.setOnDismissListener(dialogInterface -> {
//                String selectItem = bottomListDialog.getSelectItem();
//                if(!TextUtils.isEmpty(selectItem)){
//                    selectItem(selectItem, id);
//                }
//            });
//            bottomListDialog.setDatas(list);
//            bottomListDialog.setTitle(getString(title));
//            bottomListDialog.show();
//        }else{
//            SnackBarUtil.show(this,id==R.id.tv_position_2 ? "시/도가 선택되지 않았습니다.\n시/도 정보를 선택 후 다시 시도해 주세요." : "시/도 정보가 없습니다.\n네트워크 상태를 확인 후 다시 시도해 주십시오." );
//        }
//    }

//    private void selectItem(String selectNm, int id) {
//        switch (id){
//            case R.id.tv_position_1:
//                if(!ui.tvPosition1.getText().toString().equalsIgnoreCase(selectNm)){
//                    ui.tvPosition1.setText(selectNm);
//                    ui.tvPosition1.setTextAppearance(R.style.BtrPositionEnable);
//                    ui.tvPosition2.setText(R.string.bt06_5);
//                    ui.tvPosition2.setTextAppearance(R.style.BtrPositionDisable);
//                    pubViewModel.reqPUB1003(new PUB_1003.Request(APPIAInfo.GM_BT06_01.getId(), pubViewModel.getSidoCode(selectNm)));
//                }
//                break;
//            case R.id.tv_position_2:
//                if(!ui.tvPosition2.getText().toString().equalsIgnoreCase(selectNm)){
//                    ui.tvPosition2.setText(selectNm);
//                    ui.tvPosition2.setTextAppearance(R.style.BtrPositionEnable);
//                }
//                break;
//        }
//    }
//
//    private void setFilter(int selectId){
//        for(int i=0; i<filterIds.length; i++){
//
//            if(selectId==filterIds[i]){
//                ((TextView)findViewById(filterIds[i])).setTextAppearance(R.style.BtrFilterEnable);
//            }else{
//                ((TextView)findViewById(filterIds[i])).setTextAppearance(R.style.BtrFilterDisable);
//            }
//        }
//    }


    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        ui.setActivity(this);
        btrViewModel = new ViewModelProvider(this).get(BTRViewModel.class);
    }

    @Override
    public void setObserver() {
//        pubViewModel.getRES_PUB_1002().observe(this, result -> {
//            switch (result.status) {
//                case LOADING:
//                    showProgressDialog(true);
//                    break;
//                case SUCCESS:
//                    showProgressDialog(false);
//                    ui.tvPosition1.setText(R.string.bt06_4);
//                    ui.tvPosition1.setTextAppearance(R.style.BtrPositionDisable);
//                    ui.tvPosition2.setText(R.string.bt06_5);
//                    ui.tvPosition2.setTextAppearance(R.style.BtrPositionDisable);
//                    break;
//                default:
//                    showProgressDialog(false);
//                    break;
//            }
//        });
//        pubViewModel.getRES_PUB_1003().observe(this, result -> {
//            switch (result.status) {
//                case LOADING:
//                    showProgressDialog(true);
//                    break;
//                case SUCCESS:
//                    showProgressDialog(false);
//                    break;
//                default:
//                    ui.tvPosition1.setText(R.string.bt06_4);
//                    ui.tvPosition1.setTextAppearance(R.style.BtrPositionDisable);
//                    ui.tvPosition2.setText(R.string.bt06_5);
//                    ui.tvPosition2.setTextAppearance(R.style.BtrPositionDisable);
//                    pubViewModel.getRES_PUB_1003().setValue(null);
//                    showProgressDialog(false);
//                    break;
//            }
//        });
    }

    @Override
    public void getDataFromIntent() {

    }

}
