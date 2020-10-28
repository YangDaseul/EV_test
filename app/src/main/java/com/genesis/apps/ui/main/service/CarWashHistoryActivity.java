package com.genesis.apps.ui.main.service;


import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.vo.SimilarVehicleVO;
import com.genesis.apps.comm.viewmodel.CWHViewModel;
import com.genesis.apps.comm.viewmodel.LGNViewModel;
import com.genesis.apps.databinding.ActivityCarWashHistoryBinding;
import com.genesis.apps.ui.common.activity.SubActivity;

import java.util.List;

public class CarWashHistoryActivity extends SubActivity<ActivityCarWashHistoryBinding> {
    private static final String TAG = CarWashHistoryActivity.class.getSimpleName();

    private CWHViewModel viewModel;
    private CarWashHistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_wash_history);

        getDataFromIntent();
        setViewModel();
        setObserver();

        setAdapter();

        //todo : impl
//        viewModel.reqSTO1001(new STO_1001.Request(APPIAInfo.GM02_INV01.getId()));
    }

    @Override
    public void onClickCommon(View v) {
        //do nothing
    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        viewModel = new ViewModelProvider(this).get(CWHViewModel.class);
    }

    @Override
    public void setObserver() {
      /*
       viewModel.getRES_STO_1001().observe(this, result -> {

            switch (result.status){
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    showProgressDialog(false);
                    if(result.data!=null&&result.data.getEstmVhclList()!=null&&result.data.getSmlrVhclList()!=null){
                        List<SimilarVehicleVO> list = result.data.getSmlrVhclList();
                        list.add(0, result.data.getEstmVhclList());
                        adapter.setRows(list);
                        adapter.notifyDataSetChanged();
                        break;
                    }

                default:
                    showProgressDialog(false);
                    break;
            }
        });

        */
    }

    @Override
    public void getDataFromIntent() {
        //do nothing
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private void setAdapter() {
        //세차 예약 내역 어댑터 (인스턴스 타입 맞나 확인)
        adapter = new CarWashHistoryAdapter();
        ui.rvCarWashHistoryList.setLayoutManager(new LinearLayoutManager(this));
        ui.rvCarWashHistoryList.setHasFixedSize(true);
        ui.rvCarWashHistoryList.setAdapter(adapter);
    }

}
