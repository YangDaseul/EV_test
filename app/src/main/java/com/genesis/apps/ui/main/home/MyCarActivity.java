package com.genesis.apps.ui.main.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.gra.APPIAInfo;
import com.genesis.apps.comm.model.gra.api.GNS_1001;
import com.genesis.apps.comm.model.gra.api.GNS_1004;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.viewmodel.GNSViewModel;
import com.genesis.apps.databinding.ActivityMyCarBinding;
import com.genesis.apps.room.ResultCallback;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.main.home.view.CarHorizontalAdapter;

import java.util.List;

import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

public class MyCarActivity extends SubActivity<ActivityMyCarBinding> {

    private GNSViewModel gnsViewModel;
    private CarHorizontalAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_car);
        getDataFromIntent();
        setViewModel();
        setObserver();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gnsViewModel.reqGNS1001(new GNS_1001.Request(APPIAInfo.GM_CARLST01.getId()));
    }

    @Override
    public void getDataFromIntent() {

    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        ui.setActivity(this);
        gnsViewModel = new ViewModelProvider(this).get(GNSViewModel.class);
    }

    @Override
    public void setObserver() {
        gnsViewModel.getRES_GNS_1001().observe(this, result -> {

            switch (result.status){
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    showProgressDialog(false);

                    gnsViewModel.setGNS1001ToDB(result.data, new ResultCallback() {
                        @Override
                        public void onSuccess(Object object) {
                            try {
                                if (Boolean.parseBoolean(object.toString())) {
                                    List<VehicleVO> list = gnsViewModel.getVehicleList();
                                    ui.vpCar.setOffscreenPageLimit(list.size());
                                    adapter.setRows(list);
                                    adapter.notifyDataSetChanged();
                                    //이동효과를 주는데 노티파이체인지와 딜레이없이 콜하면 효과가 중첩되어 사라저서 100ms 후 처리 진행
                                    new Handler().postDelayed(() -> ui.vpCar.setCurrentItem(0, true), 100);
                                }
                            }catch (Exception e){
                                //TODO 예외처리 필요
                            }
                        }

                        @Override
                        public void onError(Object e) {
                        //TODO 예외처리 필요
                        }
                    });
                    break;
                default:
                    showProgressDialog(false);
                    //TODO 예외처리 필요
                    break;
            }
        });

        gnsViewModel.getRES_GNS_1004().observe(this, result -> {

            switch (result.status){
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    showProgressDialog(false);
                    if(result.data.getRtCd().equalsIgnoreCase("0000")){
                        gnsViewModel.reqGNS1001(new GNS_1001.Request(APPIAInfo.GM_CARLST01.getId()));
                        SnackBarUtil.show(this, getString(R.string.gm_carlst01_snackbar_1));
                    }else{
                        SnackBarUtil.show(this, TextUtils.isEmpty(result.data.getRtMsg()) ? "네트워크 상태 불안정" : result.data.getRtMsg());
                    }

                    break;
                default:
                    showProgressDialog(false);
                    //TODO 예외처리필요
                    break;
            }


        });
    }

    private void initView() {
        adapter = new CarHorizontalAdapter(onSingleClickListener);
        ui.vpCar.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        ui.vpCar.setAdapter(adapter);

        final float pageMargin = getResources().getDimensionPixelOffset(R.dimen.offset2);
        final float pageOffset = getResources().getDimensionPixelOffset(R.dimen.offset2);
        ui.vpCar.setPageTransformer((page, position) -> {
            float myOffset = position * -(2 * pageOffset + pageMargin);
            if (position < -1) {
                page.setTranslationX(-myOffset);
            } else if (position <= 1) {
                float scaleFactor = Math.max(0.8f, 1 - Math.abs(position - 0.14285715f));
                page.setTranslationX(myOffset);
                page.setScaleY(scaleFactor);
                page.setAlpha(scaleFactor);
            } else {
                page.setAlpha(0f);
                page.setTranslationX(myOffset);
            }

        });
    }


    @Override
    public void onClickCommon(View v) {

        int pos = -1;
        try {
            pos = Integer.parseInt(v.getTag(R.id.item_position).toString());
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }

        switch (v.getId()) {
            case R.id.iv_car:
                VehicleVO vehicleVO1 = (VehicleVO)v.getTag(R.id.vehicle);
                if(vehicleVO1!=null
                        &&!vehicleVO1.getDelExpYn().equalsIgnoreCase(VariableType.DELETE_EXPIRE_Y) //삭제 예정 차량이 아니고
                        &&vehicleVO1.getCustGbCd().equalsIgnoreCase(VariableType.MAIN_VEHICLE_TYPE_OV)){//소유 차량이면
                    //TODO 차고 상세페이지로 이동
                    startActivitySingleTop(new Intent(this, MyCarDetailActivity.class).putExtra(KeyNames.KEY_NAME_VEHICLE, vehicleVO1).putExtra(KeyNames.KEY_NAME_VEHICLE_OWNER_COUNT, adapter.getVehicleOwnerCnt()), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_ZOON);
                }

                break;
            case R.id.l_whole:
                break;
            case R.id.iv_favorite:
                try{
                    VehicleVO vehicleVO = (VehicleVO)v.getTag(R.id.vehicle);
                    if(vehicleVO!=null
                            &&!vehicleVO.getDelExpYn().equalsIgnoreCase(VariableType.DELETE_EXPIRE_Y) //삭제 예정 차량이 아니고
                            &&vehicleVO.getCustGbCd().equalsIgnoreCase(VariableType.MAIN_VEHICLE_TYPE_OV) //소유 차량이고
                            &&!vehicleVO.getMainVhclYn().equalsIgnoreCase(VariableType.MAIN_VEHICLE_Y)){ //현재 주 이용 차량이 아니면
                        //주 이용 차량 설정 가능
                        gnsViewModel.reqGNS1004(new GNS_1004.Request(APPIAInfo.GM_CARLST01.getId(), vehicleVO.getVin()));
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    //TODO 에러처리 필요
                }

                break;

            case R.id.btn_ris:

                String actoprRgstYn = "N";
                try{
                    actoprRgstYn = gnsViewModel.getRES_GNS_1001().getValue().data.getActoprRgstYn();
                }catch (Exception e){
                    actoprRgstYn = "N";
                }finally{

                    if(actoprRgstYn.equalsIgnoreCase("N")){
                        //등록확인메뉴로 이동
                        startActivitySingleTop(new Intent(this, LeasingCarVinRegisterActivity.class), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                    }else{
                        //내역으로 이동
                        startActivitySingleTop(new Intent(this, LeasingCarHistActivity.class), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                    }
                }
                break;
        }

    }

}
