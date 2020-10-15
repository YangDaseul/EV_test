package com.genesis.apps.ui.main.home;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.genesis.apps.R;
import com.genesis.apps.comm.viewmodel.BTRViewModel;
import com.genesis.apps.databinding.ActivityBtrFilterBinding;
import com.genesis.apps.ui.common.activity.SubActivity;

import androidx.lifecycle.ViewModelProvider;

/**
 * @author hjpark
 * @brief 블루핸즈찾기
 */
public class BtrBluehandsFilterActivity extends SubActivity<ActivityBtrFilterBinding> {
    private BTRViewModel btrViewModel;
    private int[] filterIds = {R.id.tv_category_1, R.id.tv_category_2, R.id.tv_category_3, R.id.tv_category_4};
    private String firmScnCd;
    private String acps1Cd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btr_filter);
        getDataFromIntent();
        setViewModel();
        setObserver();
        initView();
//        btrViewModel.reqBTR1008(new BTR_1008.Request(APPIAInfo.GM_BT06_02.getId(), "", "", "", "", "", ""));
    }

    private void initView() {
//        adapter = new BtrBluehandsAdapter(onSingleClickListener);
//        ui.rvBtr.setLayoutManager(new LinearLayoutManager(this));
//        ui.rvBtr.setHasFixedSize(true);
//        ui.rvBtr.addItemDecoration(new RecyclerViewDecoration((int)DeviceUtil.dip2Pixel(this,4.0f)));
//        ui.rvBtr.setAdapter(adapter);
    }

    @Override
    public void onClickCommon(View v) {

        switch (v.getId()){
            case R.id.tv_category_1:
                firmScnCd="Y";
                acps1Cd="";
                setFilter(v.getId());
                break;
            case R.id.tv_category_2:
                firmScnCd="N";
                acps1Cd="CD";
                setFilter(v.getId());
                break;
            case R.id.tv_category_3:
                firmScnCd="N";
                acps1Cd="C";
                setFilter(v.getId());
                break;
            case R.id.tv_category_4:
                firmScnCd="N";
                acps1Cd="D";
                setFilter(v.getId());
                break;
        }

    }

    private void setFilter(int selectId){
        for(int i=0; i<filterIds.length; i++){

            if(selectId==filterIds[i]){
                ((TextView)findViewById(filterIds[i])).setTextAppearance(R.style.BtrFilterEnable);
            }else{
                ((TextView)findViewById(filterIds[i])).setTextAppearance(R.style.BtrFilterDisable);
            }
        }
    }


    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        btrViewModel = new ViewModelProvider(this).get(BTRViewModel.class);
    }

    @Override
    public void setObserver() {

//        btrViewModel.getRES_BTR_1008().observe(this, result -> {
//
//            switch (result.status){
//                case LOADING:
//                    showProgressDialog(true);
//                    break;
//
//                case SUCCESS:
//                    showProgressDialog(false);
//
//                    if(result.data!=null&&result.data.getAsnList()!=null&&result.data.getAsnList().size()>0){
//                        ui.tvEmpty.setVisibility(View.GONE);
//                        ui.tvCntValue.setText(result.data.getAsnList().size()+"");
//                        adapter.setRows(result.data.getAsnList());
//                        adapter.notifyDataSetChanged();
//                    }else{
//                        ui.tvEmpty.setVisibility(View.VISIBLE);
//                        ui.tvCntValue.setText("0");
//                    }
//                    break;
//
//                default:
//                    showProgressDialog(false);
//                    break;
//            }
//
//        });
    }

    @Override
    public void getDataFromIntent() {

    }
}
