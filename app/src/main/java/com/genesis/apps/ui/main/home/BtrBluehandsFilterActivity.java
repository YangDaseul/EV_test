package com.genesis.apps.ui.main.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.gra.APPIAInfo;
import com.genesis.apps.comm.model.gra.api.BTR_2001;
import com.genesis.apps.comm.model.gra.api.PUB_1002;
import com.genesis.apps.comm.model.gra.api.PUB_1003;
import com.genesis.apps.comm.model.vo.AddressCityVO;
import com.genesis.apps.comm.model.vo.AddressGuVO;
import com.genesis.apps.comm.net.NetUIResponse;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.viewmodel.BTRViewModel;
import com.genesis.apps.comm.viewmodel.PUBViewModel;
import com.genesis.apps.databinding.ActivityBtrFilterBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.bottom.BottomListDialog;
import com.google.gson.Gson;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

/**
 * @author hjpark
 * @brief 블루핸즈찾기
 */
public class BtrBluehandsFilterActivity extends SubActivity<ActivityBtrFilterBinding> {
    private BTRViewModel btrViewModel;
    private PUBViewModel pubViewModel;
    private final int[] filterIds = {R.id.tv_category_1, R.id.tv_category_3, R.id.tv_category_4, R.id.tv_category_2};
    private final boolean[] isSelectFilter = {false,false,false,false};
    private String fillerCd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btr_filter);
        getDataFromIntent();
        setViewModel();
        setObserver();
        initView();
        pubViewModel.reqPUB1002(new PUB_1002.Request(APPIAInfo.GM_BT06_01.getId()));
    }

    private void initView() {

    }

    @Override
    public void onClickCommon(View v) {

        switch (v.getId()){
            case R.id.tv_category_1:
            case R.id.tv_category_2:
            case R.id.tv_category_3:
            case R.id.tv_category_4:
                setFilter(v.getId());
                break;
            case R.id.tv_position_1:
                List<String> listSidoNm = pubViewModel.getListAddrSidoNm().getValue();
                showMapDialog(v.getId(), listSidoNm, R.string.gm_carlst_p01_9);

                break;
            case R.id.tv_position_2:
                List<String> listGuNm = pubViewModel.getListAddrGuNm().getValue();
                showMapDialog(v.getId(), listGuNm, R.string.gm_carlst_p01_10);
                break;
        }

    }

    private void showMapDialog(int id, List<String> list, int title) {
        if(list!=null&&list.size()>0){
            final BottomListDialog bottomListDialog = new BottomListDialog(this, R.style.BottomSheetDialogTheme);
            bottomListDialog.setOnDismissListener(dialogInterface -> {
                String selectItem = bottomListDialog.getSelectItem();
                if(!TextUtils.isEmpty(selectItem)){
                    selectItem(selectItem, id);
                }
            });
            bottomListDialog.setDatas(list);
            bottomListDialog.setTitle(getString(title));
            bottomListDialog.show();
        }else{
            SnackBarUtil.show(this,id==R.id.tv_position_2 ? "시/도가 선택되지 않았습니다.\n시/도 정보를 선택 후 다시 시도해 주세요." : "시/도 정보가 없습니다.\n네트워크 상태를 확인 후 다시 시도해 주십시오." );
        }
    }

    private void selectItem(String selectNm, int id) {
        switch (id){
            case R.id.tv_position_1:
                if(!ui.tvPosition1.getText().toString().equalsIgnoreCase(selectNm)){
                    ui.tvPosition1.setText(selectNm);
                    ui.tvPosition1.setTextAppearance(R.style.BtrPositionEnable);
                    ui.tvPosition2.setText(R.string.bt06_5);
                    ui.tvPosition2.setTextAppearance(R.style.BtrPositionDisable);
                    pubViewModel.reqPUB1003(new PUB_1003.Request(APPIAInfo.GM_BT06_01.getId(), pubViewModel.getSidoCode(selectNm)));
                }
                break;
            case R.id.tv_position_2:
                if(!ui.tvPosition2.getText().toString().equalsIgnoreCase(selectNm)){
                    ui.tvPosition2.setText(selectNm);
                    ui.tvPosition2.setTextAppearance(R.style.BtrPositionEnable);
                }
                break;
        }
    }

    private void setFilter(int selectId){
        for(int i=0; i<filterIds.length; i++){

            if(selectId==filterIds[i]){
                isSelectFilter[i]=true;
                ((TextView)findViewById(filterIds[i])).setTextAppearance(R.style.BtrFilterEnable);
            }else{
                isSelectFilter[i]=false;
                ((TextView)findViewById(filterIds[i])).setTextAppearance(R.style.BtrFilterDisable);
            }
        }
    }

    private String getFillerCd(){
        fillerCd="";
        for(int i=0; i<filterIds.length;i++){
            if(isSelectFilter[i]){
                fillerCd+=((TextView)findViewById(filterIds[i])).getTag().toString();
            }
        }
        return fillerCd;
    }


    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        btrViewModel = new ViewModelProvider(this).get(BTRViewModel.class);
    }

    @Override
    public void setObserver() {
        pubViewModel.getRES_PUB_1002().observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    showProgressDialog(false);
                    ui.tvPosition1.setText(R.string.bt06_4);
                    ui.tvPosition1.setTextAppearance(R.style.BtrPositionDisable);
                    ui.tvPosition2.setText(R.string.bt06_5);
                    ui.tvPosition2.setTextAppearance(R.style.BtrPositionDisable);
                    break;
                default:
                    showProgressDialog(false);
                    break;
            }
        });
        pubViewModel.getRES_PUB_1003().observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    showProgressDialog(false);
                    break;
                default:
                    ui.tvPosition1.setText(R.string.bt06_4);
                    ui.tvPosition1.setTextAppearance(R.style.BtrPositionDisable);
                    ui.tvPosition2.setText(R.string.bt06_5);
                    ui.tvPosition2.setTextAppearance(R.style.BtrPositionDisable);
                    pubViewModel.getRES_PUB_1003().setValue(null);
                    showProgressDialog(false);
                    break;
            }
        });
    }

    @Override
    public void getDataFromIntent() {

    }

}
