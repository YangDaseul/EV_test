package com.genesis.apps.ui.myg;

import android.os.Bundle;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.MYP_1005;
import com.genesis.apps.comm.util.DeviceUtil;
import com.genesis.apps.comm.util.RecyclerViewDecoration;
import com.genesis.apps.comm.viewmodel.MYPViewModel;
import com.genesis.apps.databinding.ActivityPrivilegeApplyBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.myg.view.PrivilegeApplyAdapter;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * @author hjpark
 * @brief 프리빌리지 신청
 */
public class MyGPrivilegeApplyActivity extends SubActivity<ActivityPrivilegeApplyBinding> {
    private MYPViewModel mypViewModel;
    private PrivilegeApplyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privilege_apply);
        getDataFromIntent();
        setViewModel();
        setObserver();
        initView();
        mypViewModel.reqMYP1005(new MYP_1005.Request(APPIAInfo.MG01.getId(), ""));
    }

    private void initView() {
        adapter = new PrivilegeApplyAdapter(onSingleClickListener);
        ui.rv.setLayoutManager(new LinearLayoutManager(this));
        ui.rv.setHasFixedSize(true);
        ui.rv.addItemDecoration(new RecyclerViewDecoration((int)DeviceUtil.dip2Pixel(this,4.0f)));
        ui.rv.setAdapter(adapter);
    }

    @Override
    public void onClickCommon(View v) {

    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        mypViewModel = new ViewModelProvider(this).get(MYPViewModel.class);
    }

    @Override
    public void setObserver() {
        mypViewModel.getRES_MYP_1005().observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    showProgressDialog(false);
                    if(result.data!=null&&result.data.getPvilList()!=null){
                        adapter.setRows(result.data.getPvilList());
                        adapter.removeUnableApplyVehicle();
                        adapter.notifyDataSetChanged();
                    }else{
                        //todo 에러처리
                    }
                    setListData();
                    break;
                case ERROR:
                    showProgressDialog(false);
                    setListData();
                    break;
            }
        });
    }

    private void setListData(){
        ui.tvCntValue.setText(adapter.getItemCount()+"");
        ui.tvEmpty.setVisibility(adapter.getItemCount()==0 ? View.VISIBLE: View.GONE);
    }

    @Override
    public void getDataFromIntent() {

    }
}
