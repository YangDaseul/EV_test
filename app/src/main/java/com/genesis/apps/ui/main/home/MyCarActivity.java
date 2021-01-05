package com.genesis.apps.ui.main.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.gra.LGN_0003;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.GNS_1001;
import com.genesis.apps.comm.model.api.gra.GNS_1004;
import com.genesis.apps.comm.model.vo.MessageVO;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.viewmodel.GNSViewModel;
import com.genesis.apps.databinding.ActivityMyCarBinding;
import com.genesis.apps.databinding.ActivityMyCarNewBinding;
import com.genesis.apps.room.ResultCallback;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.main.home.view.CarHorizontalAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

public class MyCarActivity extends SubActivity<ActivityMyCarNewBinding> {

    private GNSViewModel gnsViewModel;
    private CarHorizontalAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_car_new);
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
                    if(result.data!=null){
                        gnsViewModel.setGNS1001ToDB(result.data, new ResultCallback() {
                            @Override
                            public void onSuccess(Object object) {
                                try {
                                    if (Boolean.parseBoolean(object.toString())) {
                                        List<VehicleVO> list = new ArrayList<>();
                                        list.addAll(gnsViewModel.getVehicleList());
                                        ui.vpCar.setOffscreenPageLimit(list.size());
                                        adapter.setRows(list);
                                        adapter.notifyDataSetChanged();
                                        setVehicleInfo(list.get(0));
                                        //이동효과를 주는데 노티파이체인지와 딜레이없이 콜하면 효과가 중첩되어 사라저서 100ms 후 처리 진행
                                        new Handler().postDelayed(() ->{
                                            ui.vpCar.setCurrentItem(0, true);
                                            ui.indicator.createIndicators(list.size(), 0);
                                        }, 100);
                                    }
                                }catch (Exception e){
                                    //TODO 예외처리 필요
                                }
                                showProgressDialog(false);
                                setEmptyView();
                            }

                            @Override
                            public void onError(Object e) {
                                showProgressDialog(false);
                                setEmptyView();
                            }
                        });
                        break;
                    }
                default:
                    showProgressDialog(false);
                    String serverMsg = "";
                    try {
                        serverMsg = result.data.getRtMsg();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (TextUtils.isEmpty(serverMsg)) {
                            serverMsg = getString(R.string.r_flaw06_p02_snackbar_1);
                        }
                        SnackBarUtil.show(this, serverMsg);
                        setEmptyView();
                    }
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

    private void setEmptyView() {
        ui.tvEmpty.setVisibility(adapter==null||adapter.getItemCount()==0 ? View.VISIBLE : View.GONE);
    }

    private int beforePostion=-1;
    private void initView() {
        adapter = new CarHorizontalAdapter(onSingleClickListener);
        ui.vpCar.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        ui.vpCar.setAdapter(adapter);
        ui.indicator.setViewPager(ui.vpCar);
        ui.vpCar.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if (positionOffsetPixels == 0&&beforePostion!=0&&beforePostion!=-1) {
                    VehicleVO vehicleVO = ((VehicleVO)adapter.getItem(position));
                    setVehicleInfo(vehicleVO);
                }
                beforePostion = positionOffsetPixels;
            }
        });
//        final float pageMargin = getResources().getDimensionPixelOffset(R.dimen.offset2);
//        final float pageOffset = getResources().getDimensionPixelOffset(R.dimen.offset2);
//        ui.vpCar.setPageTransformer((page, position) -> {
//            float myOffset = position * -(2 * pageOffset + pageMargin);
//            if (position < -1) {
//                page.setTranslationX(-myOffset);
//
//                Log.v("viewpager bug1","offset:"+myOffset);
//            } else if (position <= 1) {
//                float scaleFactor = Math.max(1f, 1 - Math.abs(position - 0.14285715f));
//                page.setTranslationX(myOffset);
//                page.setScaleY(scaleFactor);
//                page.setScaleX(scaleFactor);
//                page.setAlpha(scaleFactor);
//
//                Log.v("viewpager bug2","offset:"+myOffset+ " Scale factor:"+scaleFactor);
//            } else {
//                page.setAlpha(0f);
//                page.setTranslationX(myOffset);
//                Log.v("viewpager bug3","offset:"+myOffset);
//            }
//
//        });
    }

    private void setVehicleInfo(VehicleVO vehicleVO) {
        ui.tvCarCode.setText(vehicleVO.getMdlNm());
        ui.tvCarModel.setText(vehicleVO.getSaleMdlNm());

        //UI초기화
        ui.tvCarVrn.setVisibility(View.GONE);
        ui.btnRegVrn.lWhole.setVisibility(View.GONE);
        ui.btnRecovery.lWhole.setVisibility(View.GONE);
        ui.btnDelete.lWhole.setVisibility(View.GONE);
        ui.btnSimilar.lWhole.setVisibility(View.GONE);
        ui.btnContract.lWhole.setVisibility(View.GONE);
        ui.lPrivilege.setVisibility(View.GONE);
        ui.ivFavorite.setVisibility(View.GONE);
        ui.ivFavorite.setTag(R.id.vehicle, vehicleVO);

        switch (vehicleVO.getCustGbCd()) {
            case VariableType.MAIN_VEHICLE_TYPE_OV:
                if (vehicleVO.getDelExpYn().equalsIgnoreCase(VariableType.DELETE_EXPIRE_Y)) {
                    //삭제 예정 차량
                    //차량번호 셋팅
                    ui.tvCarVrn.setVisibility(View.VISIBLE);
                    ui.tvCarVrn.setText(vehicleVO.getCarRgstNo());
                    ui.tvCarVrn.setOnClickListener(null);
                    ui.tvCarVrn.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                    //삭졔예정표기
                    ui.tvCarStatus.setText(String.format(getString(R.string.gm_carlst_04_4), (((TextUtils.isEmpty(vehicleVO.getDelExpDay())) ? "0" : vehicleVO.getDelExpDay()))));
                    //복구버튼 활성화
                    ui.btnRecovery.lWhole.setVisibility(View.VISIBLE);

                }else{
                    //정상차량
                    //차량번호 셋팅
                    if(!TextUtils.isEmpty(vehicleVO.getCarRgstNo())){
                        //차량번호가 있을 경우
                        ui.tvCarVrn.setVisibility(View.VISIBLE);
                        ui.tvCarVrn.setText(vehicleVO.getCarRgstNo());
                        ui.tvCarVrn.setOnClickListener(onSingleClickListener);
                        ui.tvCarVrn.setCompoundDrawablesWithIntrinsicBounds(null, null, getDrawable(R.drawable.car_m_edit), null);
                    }else{
                        //차량번호가 없을 경우
                        ui.btnRegVrn.lWhole.setVisibility(View.VISIBLE);
                    }

                    //주 이용 차량 셋팅
                    ui.ivFavorite.setVisibility(View.VISIBLE);
                    ui.ivFavorite.setImageResource(vehicleVO.getMainVhclYn().equalsIgnoreCase(VariableType.MAIN_VEHICLE_N) ? R.drawable.ic_star_l_s_d : R.drawable.ic_star_l_s);
                    //주 차량 표기
                    ui.tvCarStatus.setText(vehicleVO.getMainVhclYn().equalsIgnoreCase(VariableType.MAIN_VEHICLE_N) ? "" : getString(R.string.gm_carlst_01_4));
                    //삭제버튼 활성화
                    ui.btnDelete.lWhole.setVisibility(View.VISIBLE);
                }
                break;

            case VariableType.MAIN_VEHICLE_TYPE_CV://계약차량
                //계약차량 표기
                ui.tvCarStatus.setText(R.string.gm_carlst_04_26);
                //유사재고조회
                ui.btnSimilar.lWhole.setVisibility(View.VISIBLE);
                //계약서조회
                ui.btnContract.lWhole.setVisibility(View.VISIBLE);


                break;
            case VariableType.MAIN_VEHICLE_TYPE_NV:
            default:
                break;
        }
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
            case R.id.btn_pre://이전 버튼
                ui.vpCar.setCurrentItem(ui.vpCar.getCurrentItem()-1, true);
                break;
            case R.id.btn_next://다음 버튼
                ui.vpCar.setCurrentItem(ui.vpCar.getCurrentItem()+1, true);
                break;
            case R.id.iv_favorite:// 주 이용 차량 설정
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
                }
                break;


//            case R.id.iv_car://차량 상세페이지로 이동
//                VehicleVO vehicleVO1 = (VehicleVO)v.getTag(R.id.vehicle);
//                if(vehicleVO1!=null
//                        &&vehicleVO1.getCustGbCd().equalsIgnoreCase(VariableType.MAIN_VEHICLE_TYPE_OV)){//소유 차량이면
//                    startActivitySingleTop(new Intent(this, MyCarDetailActivity.class).putExtra(KeyNames.KEY_NAME_VEHICLE, vehicleVO1).putExtra(KeyNames.KEY_NAME_VEHICLE_OWNER_COUNT, adapter.getVehicleOwnerCnt()), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_ZOON);
//                }
//
//                break;
//            case R.id.l_whole:
//                break;

            case R.id.btn_ris://렌트리스

                String actoprRgstYn = "N";
                try{
                    actoprRgstYn = gnsViewModel.getRES_GNS_1001().getValue().data.getActoprRgstYn();
                }catch (Exception e){
                    actoprRgstYn = "N";
                }finally{

                    if(actoprRgstYn==null||actoprRgstYn.equalsIgnoreCase("N")){
                        //등록확인메뉴로 이동
                        startActivitySingleTop(new Intent(this, LeasingCarVinRegisterActivity.class), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                    }else{
                        //내역으로 이동
                        startActivitySingleTop(new Intent(this, LeasingCarHistActivity.class), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                    }
                }
                break;

            case R.id.btn_rent://중고차
                startActivitySingleTop(new Intent(this, RegisterUsedCarActivity.class), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                break;
        }

    }

}









//package com.genesis.apps.ui.main.home;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.View;
//
//import com.genesis.apps.R;
//import com.genesis.apps.comm.model.constants.KeyNames;
//import com.genesis.apps.comm.model.constants.RequestCodes;
//import com.genesis.apps.comm.model.constants.VariableType;
//import com.genesis.apps.comm.model.api.APPIAInfo;
//import com.genesis.apps.comm.model.api.gra.GNS_1001;
//import com.genesis.apps.comm.model.api.gra.GNS_1004;
//import com.genesis.apps.comm.model.vo.VehicleVO;
//import com.genesis.apps.comm.util.SnackBarUtil;
//import com.genesis.apps.comm.viewmodel.GNSViewModel;
//import com.genesis.apps.databinding.ActivityMyCarBinding;
//import com.genesis.apps.room.ResultCallback;
//import com.genesis.apps.ui.common.activity.SubActivity;
//import com.genesis.apps.ui.main.home.view.CarHorizontalAdapter;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import androidx.lifecycle.ViewModelProvider;
//import androidx.viewpager2.widget.ViewPager2;
//
//public class MyCarActivity extends SubActivity<ActivityMyCarBinding> {
//
//    private GNSViewModel gnsViewModel;
//    private CarHorizontalAdapter adapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_my_car);
//        getDataFromIntent();
//        setViewModel();
//        setObserver();
//        initView();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        gnsViewModel.reqGNS1001(new GNS_1001.Request(APPIAInfo.GM_CARLST01.getId()));
//    }
//
//    @Override
//    public void getDataFromIntent() {
//
//    }
//
//    @Override
//    public void setViewModel() {
//        ui.setLifecycleOwner(this);
//        ui.setActivity(this);
//        gnsViewModel = new ViewModelProvider(this).get(GNSViewModel.class);
//    }
//
//    @Override
//    public void setObserver() {
//        gnsViewModel.getRES_GNS_1001().observe(this, result -> {
//
//            switch (result.status){
//                case LOADING:
//                    showProgressDialog(true);
//                    break;
//                case SUCCESS:
//                    if(result.data!=null){
//                        gnsViewModel.setGNS1001ToDB(result.data, new ResultCallback() {
//                            @Override
//                            public void onSuccess(Object object) {
//                                try {
//                                    if (Boolean.parseBoolean(object.toString())) {
//                                        List<VehicleVO> list = new ArrayList<>();
//                                        list.addAll(gnsViewModel.getVehicleList());
//                                        ui.vpCar.setOffscreenPageLimit(list.size());
//                                        adapter.setRows(list);
//                                        adapter.notifyDataSetChanged();
//                                        //이동효과를 주는데 노티파이체인지와 딜레이없이 콜하면 효과가 중첩되어 사라저서 100ms 후 처리 진행
//                                        new Handler().postDelayed(() -> ui.vpCar.setCurrentItem(0, true), 100);
//                                    }
//                                }catch (Exception e){
//                                    //TODO 예외처리 필요
//                                }
//                                showProgressDialog(false);
//                            }
//
//                            @Override
//                            public void onError(Object e) {
//                                showProgressDialog(false);
//                            }
//                        });
//                        break;
//                    }
//                default:
//                    showProgressDialog(false);
//                    String serverMsg = "";
//                    try {
//                        serverMsg = result.data.getRtMsg();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    } finally {
//                        if (TextUtils.isEmpty(serverMsg)) {
//                            serverMsg = getString(R.string.r_flaw06_p02_snackbar_1);
//                        }
//                        SnackBarUtil.show(this, serverMsg);
//                    }
//                    break;
//            }
//        });
//
//        gnsViewModel.getRES_GNS_1004().observe(this, result -> {
//
//            switch (result.status){
//                case LOADING:
//                    showProgressDialog(true);
//                    break;
//                case SUCCESS:
//                    showProgressDialog(false);
//                    if(result.data.getRtCd().equalsIgnoreCase("0000")){
//                        gnsViewModel.reqGNS1001(new GNS_1001.Request(APPIAInfo.GM_CARLST01.getId()));
//                        SnackBarUtil.show(this, getString(R.string.gm_carlst01_snackbar_1));
//                    }else{
//                        SnackBarUtil.show(this, TextUtils.isEmpty(result.data.getRtMsg()) ? "네트워크 상태 불안정" : result.data.getRtMsg());
//                    }
//
//                    break;
//                default:
//                    showProgressDialog(false);
//                    //TODO 예외처리필요
//                    break;
//            }
//
//
//        });
//    }
//
//    private void initView() {
//        adapter = new CarHorizontalAdapter(onSingleClickListener);
//        ui.vpCar.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
//        ui.vpCar.setAdapter(adapter);
//
//        final float pageMargin = getResources().getDimensionPixelOffset(R.dimen.offset2);
//        final float pageOffset = getResources().getDimensionPixelOffset(R.dimen.offset2);
//        ui.vpCar.setPageTransformer((page, position) -> {
//            float myOffset = position * -(2 * pageOffset + pageMargin);
//            if (position < -1) {
//                page.setTranslationX(-myOffset);
//
//                Log.v("viewpager bug1","offset:"+myOffset);
//            } else if (position <= 1) {
//                float scaleFactor = Math.max(1f, 1 - Math.abs(position - 0.14285715f));
//                page.setTranslationX(myOffset);
//                page.setScaleY(scaleFactor);
//                page.setScaleX(scaleFactor);
//                page.setAlpha(scaleFactor);
//
//                Log.v("viewpager bug2","offset:"+myOffset+ " Scale factor:"+scaleFactor);
//            } else {
//                page.setAlpha(0f);
//                page.setTranslationX(myOffset);
//                Log.v("viewpager bug3","offset:"+myOffset);
//            }
//
//        });
//    }
//
//
//    @Override
//    public void onClickCommon(View v) {
//
//        int pos = -1;
//        try {
//            pos = Integer.parseInt(v.getTag(R.id.item_position).toString());
//        } catch (Exception ignore) {
//            ignore.printStackTrace();
//        }
//
//        switch (v.getId()) {
//            case R.id.iv_car:
//                VehicleVO vehicleVO1 = (VehicleVO)v.getTag(R.id.vehicle);
//                if(vehicleVO1!=null
////                        &&!vehicleVO1.getDelExpYn().equalsIgnoreCase(VariableType.DELETE_EXPIRE_Y) //삭제 예정 차량이 아니고 //todo 삭제 예정차량 클릭못했었나 ?
//                        &&vehicleVO1.getCustGbCd().equalsIgnoreCase(VariableType.MAIN_VEHICLE_TYPE_OV)){//소유 차량이면
//                    startActivitySingleTop(new Intent(this, MyCarDetailActivity.class).putExtra(KeyNames.KEY_NAME_VEHICLE, vehicleVO1).putExtra(KeyNames.KEY_NAME_VEHICLE_OWNER_COUNT, adapter.getVehicleOwnerCnt()), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_ZOON);
//                }
//
//                break;
//            case R.id.l_whole:
//                break;
//            case R.id.iv_favorite:
//                try{
//                    VehicleVO vehicleVO = (VehicleVO)v.getTag(R.id.vehicle);
//                    if(vehicleVO!=null
//                            &&!vehicleVO.getDelExpYn().equalsIgnoreCase(VariableType.DELETE_EXPIRE_Y) //삭제 예정 차량이 아니고
//                            &&vehicleVO.getCustGbCd().equalsIgnoreCase(VariableType.MAIN_VEHICLE_TYPE_OV) //소유 차량이고
//                            &&!vehicleVO.getMainVhclYn().equalsIgnoreCase(VariableType.MAIN_VEHICLE_Y)){ //현재 주 이용 차량이 아니면
//                        //주 이용 차량 설정 가능
//                        gnsViewModel.reqGNS1004(new GNS_1004.Request(APPIAInfo.GM_CARLST01.getId(), vehicleVO.getVin()));
//                    }
//                }catch (Exception e){
//                    e.printStackTrace();
//                    //TODO 에러처리 필요
//                }
//
//                break;
//
//            case R.id.btn_ris:
//
//                String actoprRgstYn = "N";
//                try{
//                    actoprRgstYn = gnsViewModel.getRES_GNS_1001().getValue().data.getActoprRgstYn();
//                }catch (Exception e){
//                    actoprRgstYn = "N";
//                }finally{
//
//                    if(actoprRgstYn==null||actoprRgstYn.equalsIgnoreCase("N")){
//                        //등록확인메뉴로 이동
//                        startActivitySingleTop(new Intent(this, LeasingCarVinRegisterActivity.class), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
//                    }else{
//                        //내역으로 이동
//                        startActivitySingleTop(new Intent(this, LeasingCarHistActivity.class), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
//                    }
//                }
//                break;
//
//            case R.id.btn_rent:
//                startActivitySingleTop(new Intent(this, RegisterUsedCarActivity.class), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
//                break;
//        }
//
//    }
//
//}
