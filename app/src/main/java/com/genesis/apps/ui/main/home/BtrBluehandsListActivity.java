package com.genesis.apps.ui.main.home;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.gra.APPIAInfo;
import com.genesis.apps.comm.model.gra.api.BTR_1008;
import com.genesis.apps.comm.model.gra.api.BTR_2003;
import com.genesis.apps.comm.util.DeviceUtil;
import com.genesis.apps.comm.util.RecyclerViewDecoration;
import com.genesis.apps.comm.viewmodel.BTRViewModel;
import com.genesis.apps.databinding.ActivityBtrBluehandsBinding;
import com.genesis.apps.databinding.ActivityBtrBluehandsHistBinding;
import com.genesis.apps.databinding.ActivityBtrCnslHistBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.main.home.view.BtrAccodianRecyclerAdapter;
import com.genesis.apps.ui.main.home.view.BtrBluehandsAdapter;

/**
 * @author hjpark
 * @brief 블루핸즈찾기
 */
public class BtrBluehandsListActivity extends SubActivity<ActivityBtrBluehandsHistBinding> {
    private final String TAG = BtrBluehandsListActivity.class.getSimpleName();
    private BTRViewModel btrViewModel;
    private BtrBluehandsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btr_bluehands_hist);
        getDataFromIntent();
        setViewModel();
        setObserver();
        initView();
        btrViewModel.reqBTR1008(new BTR_1008.Request(APPIAInfo.GM_BT06_02.getId(), "", "", "", "", "", ""));
    }

    private void initView() {
        adapter = new BtrBluehandsAdapter(onSingleClickListener);
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

        btrViewModel.getRES_BTR_1008().observe(this, result -> {

            switch (result.status){
                case LOADING:
                    showProgressDialog(true);
                    break;

                case SUCCESS:
                    showProgressDialog(false);

                    if(result.data!=null&&result.data.getAsnList()!=null&&result.data.getAsnList().size()>0){
                        ui.tvEmpty.setVisibility(View.GONE);
                        ui.tvCntValue.setText(result.data.getAsnList().size()+"");
                        adapter.setRows(result.data.getAsnList());
                        adapter.notifyDataSetChanged();
                    }else{
                        ui.tvEmpty.setVisibility(View.VISIBLE);
                        ui.tvCntValue.setText("0");
                    }
                    break;

                default:
                    showProgressDialog(false);
                    break;
            }

        });
    }

    @Override
    public void getDataFromIntent() {

    }
}
