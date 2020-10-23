package com.genesis.apps.ui.main.home;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;

import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.gra.APPIAInfo;
import com.genesis.apps.comm.model.gra.api.GNS_1007;
import com.genesis.apps.comm.model.gra.api.STO_1001;
import com.genesis.apps.comm.model.vo.RentStatusVO;
import com.genesis.apps.comm.model.vo.SimilarVehicleVO;
import com.genesis.apps.comm.net.NetUIResponse;
import com.genesis.apps.comm.util.DeviceUtil;
import com.genesis.apps.comm.util.RecyclerViewDecoration;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.viewmodel.GNSViewModel;
import com.genesis.apps.comm.viewmodel.LGNViewModel;
import com.genesis.apps.databinding.ActivityLeasingCarHistBinding;
import com.genesis.apps.databinding.ActivitySimilarCarBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.main.SimilarCarAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hjpark
 * @brief 렌트리스 실 운행자 내역
 */
public class LeasingCarHistActivity extends SubActivity<ActivityLeasingCarHistBinding> {
    private GNSViewModel gnsViewModel;
    private LeasingCarHistAdapter adapter;
    private boolean isApply;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leasing_car_hist);
        getDataFromIntent();
        setViewModel();
        setObserver();
        initView();

        gnsViewModel.reqGNS1007(new GNS_1007.Request(APPIAInfo.GM_CARLST_02.getId()));
    }

    private void initView() {
        adapter = new LeasingCarHistAdapter(onSingleClickListener);
        ui.rv.setLayoutManager(new LinearLayoutManager(this));
        ui.rv.addItemDecoration(new RecyclerViewDecoration((int) DeviceUtil.dip2Pixel(this,4.0f)));
        ui.rv.setHasFixedSize(true);
        ui.rv.setAdapter(adapter);
    }

    @Override
    public void onClickCommon(View v) {

        switch (v.getId()){
            case R.id.btn_status:


                break;

            case R.id.btn_more:

                List<RentStatusVO> list = gnsViewModel.getRES_GNS_1007().getValue().data.getSubspList();

                if(adapter.getItemCount()>=list.size()){
                    adapter.setMore(false);
//                    adapter.notifyItemChanged(list.size()-1);
                }else{
                    if(list.size()-adapter.getItemCount()==1){
                        adapter.setMore(false);
                        adapter.addRow(list.get(list.size()-1));
                    }
//                    else if(list.size()-adapter.getItemCount()==2){
//                        adapter.setMore(false);
//                        adapter.addRow(list.get(list.size()-2));
//                        adapter.addRow(list.get(list.size()-1));
//                    }
                    else{
                        adapter.setMore(true);
                        adapter.addRows(list.subList(adapter.getItemCount(),adapter.getItemCount()+2));
                    }
                }

                adapter.notifyDataSetChanged();
                break;

        }

    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        gnsViewModel = new ViewModelProvider(this).get(GNSViewModel.class);
    }

    @Override
    public void setObserver() {

        gnsViewModel.getRES_GNS_1007().observe(this, result -> {

            switch (result.status){
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    showProgressDialog(false);

                    if(result.data!=null&&result.data.getSubspList()!=null&&result.data.getSubspList().size()>0){
                        List<RentStatusVO> list;

                        if(result.data.getSubspList().size()>2){
                            list = new ArrayList<>( result.data.getSubspList().subList(0,3));
                            adapter.setMore(true);
                        }else{
                            list = result.data.getSubspList();
                            adapter.setMore(false);
                        }
                        adapter.setRows(list);
                        adapter.notifyDataSetChanged();
                        ui.tvEmpty.setVisibility(View.GONE);

                        if (isApply) {
                            SnackBarUtil.show(this, getString(R.string.gm_carlist_01_01_snackbar_1));
                        }

                        break;
                    }

                default:
                    showProgressDialog(false);
                    ui.tvEmpty.setVisibility(View.VISIBLE);
                    break;
            }
        });

    }

    @Override
    public void getDataFromIntent() {
        try {
            isApply = getIntent().getBooleanExtra(KeyNames.KEY_NAME_APPLY_LEASINGCAR, false);
        } catch (Exception e) {
        }

    }
}
