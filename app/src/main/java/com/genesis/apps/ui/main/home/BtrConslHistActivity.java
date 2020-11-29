package com.genesis.apps.ui.main.home;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.BTR_2003;
import com.genesis.apps.comm.util.DeviceUtil;
import com.genesis.apps.comm.util.RecyclerViewDecoration;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.viewmodel.BTRViewModel;
import com.genesis.apps.databinding.ActivityBtrCnslHistBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.main.home.view.BtrAccodianRecyclerAdapter;

/**
 * @author hjpark
 * @brief 버틀러 상담 이력
 */
public class BtrConslHistActivity extends SubActivity<ActivityBtrCnslHistBinding> {
    private final String TAG = BtrConslHistActivity.class.getSimpleName();
    private BTRViewModel btrViewModel;
    private BtrAccodianRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btr_cnsl_hist);
        getDataFromIntent();
        setViewModel();
        setObserver();
        initView();

        btrViewModel.reqBTR2003(new BTR_2003.Request(APPIAInfo.GM_BT05.getId()));
    }

    private void initView() {
        adapter = new BtrAccodianRecyclerAdapter();
        ui.rvBtr.setLayoutManager(new LinearLayoutManager(this));
        ui.rvBtr.setHasFixedSize(true);
        ui.rvBtr.addItemDecoration(new RecyclerViewDecoration((int)DeviceUtil.dip2Pixel(this,4.0f)));
        ui.rvBtr.setAdapter(adapter);
    }

    @Override
    public void onClickCommon(View v) {

    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        btrViewModel = new ViewModelProvider(this).get(BTRViewModel.class);
    }

    @Override
    public void setObserver() {

        btrViewModel.getRES_BTR_2003().observe(this, result -> {
            switch (result.status){
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    showProgressDialog(false);
                    if(result.data!=null&&result.data.getConslList()!=null&&result.data.getConslList().size()>0){
                        ui.tvEmpty.setVisibility(View.GONE);
                        ui.tvCntValue.setText(result.data.getConslList().size()+"");
                        adapter.setRows(result.data.getConslList());
                        adapter.notifyDataSetChanged();
                    }else{
                        ui.tvEmpty.setVisibility(View.VISIBLE);
                        ui.tvCntValue.setText("0");
                    }
                    break;
                default:
                    showProgressDialog(false);
                    String serverMsg="";
                    try {
                        serverMsg = result.data.getRtMsg();
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally{
                        SnackBarUtil.show(this, serverMsg);
                        ui.tvEmpty.setVisibility(View.VISIBLE);
                        ui.tvCntValue.setText("0");
                    }
                    break;
            }

        });
    }

    @Override
    public void getDataFromIntent() {

    }
}
