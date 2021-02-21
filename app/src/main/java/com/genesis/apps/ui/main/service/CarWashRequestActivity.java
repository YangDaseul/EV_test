package com.genesis.apps.ui.main.service;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.WSH_1001;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.WashGoodsVO;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.viewmodel.LGNViewModel;
import com.genesis.apps.comm.viewmodel.WSHViewModel;
import com.genesis.apps.databinding.ActivityCarWashRequestBinding;
import com.genesis.apps.ui.common.activity.SubActivity;

import java.util.concurrent.ExecutionException;

public class CarWashRequestActivity extends SubActivity<ActivityCarWashRequestBinding> {

    private WSHViewModel viewModel;
    private LGNViewModel lgnViewModel;
    private FragmentCarWashAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_wash_request);

        setViewModel();
        setAdapter();
        setObserver();
    }

    @Override
    public void onClickCommon(View v) {
        switch (v.getId()) {
            //세차 쿠폰 선택(지점 검색(지도) 액티비티 열기)
            case R.id.l_service_car_wash_item:
                //선택한 쿠폰 정보를 새 액티비티에 가지고 가야 한다
                String godsSeqNo = ((WashGoodsVO) v.getTag(R.id.tag_wash_item)).getGodsSeqNo();
                String godsNm = ((WashGoodsVO) v.getTag(R.id.tag_wash_item)).getGodsNm();

                Intent intent = new Intent(this, CarWashSearchActivity.class);
                intent.putExtra(KeyNames.KEY_NAME_WASH_GOODS_SEQ_NUM, godsSeqNo);
                intent.putExtra(KeyNames.KEY_NAME_WASH_GOODS_NAME, godsNm);

                startActivitySingleTop(intent, RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                break;
        }
    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        viewModel = new ViewModelProvider(this).get(WSHViewModel.class);
        lgnViewModel = new ViewModelProvider(this).get(LGNViewModel.class);

        try {
            viewModel.reqWSH1001(new WSH_1001.Request(APPIAInfo.SM_CW01_A01.getId(), WSHViewModel.SONAX, lgnViewModel.getMainVehicleFromDB().getVin()));
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setObserver() {
        //세차 쿠폰 목록
        viewModel.getRES_WSH_1001().observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;

                case SUCCESS:
                    //TODO 2020-12-08 가격표 URI가 추가되어 수정 필요
                    if (result.data != null && result.data.getGodsList() != null) {
                        adapter.setRows(result.data.getGodsList());
                        adapter.notifyDataSetChanged();

                        //성공 후 데이터 로딩까지 다 되면 로딩 치우고 break;
                        showProgressDialog(false);
                        break;
                    }
                    //not break; 데이터 이상하면 default로 진입시킴

                default:
                    showProgressDialog(false);
                    String serverMsg="";
                    try {
                        serverMsg = result.data.getRtMsg();
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally{
                        if (TextUtils.isEmpty(serverMsg)){
                            serverMsg = getString(R.string.instability_network);
                        }
                        SnackBarUtil.show(this, serverMsg);
                    }
                    break;
            }

        });
    }

    @Override
    public void getDataFromIntent() {

    }

    private void setAdapter() {
        //세차 아이템 어댑터 (인스턴스 타입 맞나 확인)
        adapter = new FragmentCarWashAdapter(onSingleClickListener);
        ui.rvServiceCarWashItemList.setLayoutManager(new LinearLayoutManager(this));
        ui.rvServiceCarWashItemList.setHasFixedSize(true);
        ui.rvServiceCarWashItemList.setAdapter(adapter);
    }
}