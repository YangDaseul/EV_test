package com.genesis.apps.ui.main.service;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.gra.APPIAInfo;
import com.genesis.apps.comm.model.gra.api.WSH_1004;
import com.genesis.apps.comm.model.vo.WashReserveVO;
import com.genesis.apps.comm.viewmodel.WSHViewModel;
import com.genesis.apps.databinding.ActivityCarWashHistoryBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.main.service.CarWashHistoryAdapter.CarWashHistoryViewHolder;

import java.util.List;

public class CarWashHistoryActivity extends SubActivity<ActivityCarWashHistoryBinding> {
    private static final String TAG = CarWashHistoryActivity.class.getSimpleName();

    private WSHViewModel viewModel;
    private CarWashHistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_wash_history);

        getDataFromIntent();
        setViewModel();
        setObserver();

        setAdapter();

        //TODO 저거 ("SONAX") 하드코딩하는 게 맞나
        viewModel.reqWSH1004(new WSH_1004.Request(APPIAInfo.SM_CW01.getId(), "SONAX"));
    }

    @Override
    public void onClickCommon(View v) {
        Log.d(TAG, "onClickCommon()");
        int id = v.getId();
//        CarWashHistoryViewHolder carWashHistoryItem = (CarWashHistoryViewHolder) v.getParent();

        switch (id) {
            case R.id.tv_car_wash_history_call:
                //todo 전화번호 알아내서 걸기

//                Log.d(TAG, "onClickCommon(): 통화하기 : " + carWashHistoryItem.getPhoneNumber());
//                Toast.makeText(this, carWashHistoryItem.getPhoneNumber(), Toast.LENGTH_LONG).show();

                break;

            case R.id.tv_car_wash_history_confirm:
                // todo 뭐 해야되는지 정의 아직 정의 안 된 듯
                break;

            case R.id.tv_car_wash_history_cancel:
                //todo : 취소할거냐? 팝업 -> yes하면
                // 예약번호 알아내서 취소 api 호출. carWashHistoryItem.getReservationNumber()
                // 취소 성공 응답 받으면 취소됨 상태로 변해야되네
                // 어... visibility 스위칭으로 해놔서 접히기 애니메이션 적용 안 되는데;;;
                break;

            default:
                //do nothing
                break;
        }

    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        viewModel = new ViewModelProvider(this).get(WSHViewModel.class);
    }

    @Override
    public void setObserver() {
        viewModel.getRES_WSH_1004().observe(this, result -> {

            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;

                case SUCCESS:
                    showProgressDialog(false);
                    if (result.data != null && result.data.getRsvtList() != null) {
                        List<WashReserveVO> list = result.data.getRsvtList();
                        adapter.setRows(list);
                        adapter.notifyDataSetChanged();
                        break;
                    }

                default:
                    showProgressDialog(false);
                    break;
            }
        });

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
        adapter = new CarWashHistoryAdapter(onSingleClickListener);
        ui.rvCarWashHistoryList.setLayoutManager(new LinearLayoutManager(this));
        ui.rvCarWashHistoryList.setHasFixedSize(true);
        ui.rvCarWashHistoryList.setAdapter(adapter);
    }

}
