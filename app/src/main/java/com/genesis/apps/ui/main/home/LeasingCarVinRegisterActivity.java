package com.genesis.apps.ui.main.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.GNS_1011;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.viewmodel.GNSViewModel;
import com.genesis.apps.databinding.ActivityLeasingCarRegisterBinding;
import com.genesis.apps.ui.common.activity.SubActivity;

import androidx.lifecycle.ViewModelProvider;

import static com.genesis.apps.comm.model.constants.KeyNames.KEY_NAME_VIN;

/**
 * @author hjpark
 * @brief 렌트/리스 실 운행자 등록 (차대번호 입력)
 */
public class LeasingCarVinRegisterActivity extends SubActivity<ActivityLeasingCarRegisterBinding> {
    private GNSViewModel gnsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResizeScreen();

        setContentView(R.layout.activity_leasing_car_register);
        getDataFromIntent();
        setViewModel();
        setObserver();
        initView();
    }

    private void initView() {

        ui.etVin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(!TextUtils.isEmpty(editable.toString().trim())){
                    ui.lVin.setError(null);
                }

            }
        });

    }

    @Override
    public void onClickCommon(View v) {

        switch (v.getId()){
            case R.id.btn_check:
                if(TextUtils.isEmpty(ui.etVin.getText().toString().trim())){
                    ui.lVin.setError(getString(R.string.gm_carlst_01_23));
                }else if(ui.etVin.getText().toString().trim().length()!=17){
                    ui.lVin.setError(getString(R.string.gm_carlst_01_45));
                }else{
                   gnsViewModel.reqGNS1011(new GNS_1011.Request(APPIAInfo.GM_CARLST_01.getId(), ui.etVin.getText().toString().trim()));
                }
                break;
            case R.id.btn_info:
                startActivitySingleTop(new Intent(this, LeasingCarInfoActivity.class), 0, VariableType.ACTIVITY_TRANSITION_ANIMATION_VERTICAL_SLIDE);
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
        gnsViewModel = new ViewModelProvider(this).get(GNSViewModel.class);
    }

    @Override
    public void setObserver() {
        gnsViewModel.getRES_GNS_1011().observe(this, result -> {

            switch (result.status){
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    showProgressDialog(false);
                    if(!TextUtils.isEmpty(result.data.getRgstPsblYn())&&result.data.getRgstPsblYn().equalsIgnoreCase("Y")){
                        startActivitySingleTop(new Intent(this, LeasingCarRegisterInputActivity.class).putExtra(KEY_NAME_VIN, ui.etVin.getText().toString().trim()), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                        finish();
                        break;
                    }else if(!TextUtils.isEmpty(result.data.getRgstPsblYn())&&result.data.getRgstPsblYn().equalsIgnoreCase("N")) {
                        SnackBarUtil.show(this, getString(R.string.gm_carlst_01_snackbar_3));
                        break;
                    }
                default:
                    showProgressDialog(false);
                    String serverMsg="";
                    try {
                        serverMsg = result.data.getRtMsg();
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally{
                        SnackBarUtil.show(this, serverMsg);
                    }
                    break;


            }


        });



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
