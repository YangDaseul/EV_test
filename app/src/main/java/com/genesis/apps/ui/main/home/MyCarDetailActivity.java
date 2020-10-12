package com.genesis.apps.ui.main.home;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.OilCodes;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.gra.APPIAInfo;
import com.genesis.apps.comm.model.gra.api.GNS_1001;
import com.genesis.apps.comm.model.gra.api.GNS_1002;
import com.genesis.apps.comm.model.gra.api.GNS_1004;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.viewmodel.GNSViewModel;
import com.genesis.apps.comm.viewmodel.MYPViewModel;
import com.genesis.apps.databinding.ActivityMyCarBinding;
import com.genesis.apps.databinding.ActivityMyCarDetailBinding;
import com.genesis.apps.room.ResultCallback;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.bottom.BottomListDialog;
import com.genesis.apps.ui.common.dialog.bottom.DialogCarRgstNo;
import com.genesis.apps.ui.common.view.listener.ViewPressEffectHelper;
import com.genesis.apps.ui.main.home.view.CarHorizontalAdapter;

import java.util.Arrays;
import java.util.List;

import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

public class MyCarDetailActivity extends SubActivity<ActivityMyCarDetailBinding> {

    private MYPViewModel mypViewModel;
    private GNSViewModel gnsViewModel;
    private VehicleVO vehicleVO;
    private String tmpCarRgstNo="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_car_detail);
        getDataFromIntent();
        setViewModel();
        setObserver();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        gnsViewModel.reqGNS1001(new GNS_1001.Request(APPIAInfo.GM_CARLST01.getId()));
    }

    @Override
    public void getDataFromIntent() {
        try {
            vehicleVO = (VehicleVO)getIntent().getSerializableExtra(KeyNames.KEY_NAME_VEHICLE);
            if(vehicleVO==null){
                exitPage("차량 정보가 존재하지 않습니다.\n잠시후 다시 시도해 주십시오.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
            }
        }catch (Exception e){
            e.printStackTrace();
            exitPage("차량 정보가 존재하지 않습니다.\n잠시후 다시 시도해 주십시오.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
        }
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
                                   vehicleVO = gnsViewModel.getVehicle(vehicleVO.getVin());

                                   if(vehicleVO!=null){
                                       initView();
                                   }else{
                                       //TODO 예외처리필요
                                   }

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
//
        gnsViewModel.getRES_GNS_1004().observe(this, result -> {

            switch (result.status){
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    showProgressDialog(false);
                    if(result.data.getRtCd().equalsIgnoreCase("0000")){
//                        gnsViewModel.reqGNS1001(new GNS_1001.Request(APPIAInfo.GM_CARLST_04.getId()));
                        vehicleVO.setMainVhclYn(VariableType.MAIN_VEHICLE_Y);
                        initView();
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


        gnsViewModel.getRES_GNS_1002().observe(this, result -> {

            switch (result.status){
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    showProgressDialog(false);
                    if(result.data.getRtCd().equalsIgnoreCase("0000")){
                        vehicleVO.setCarRgstNo(tmpCarRgstNo);
                        tmpCarRgstNo="";
                        initView();
                        SnackBarUtil.show(this, getString(R.string.gm_carlst01_snackbar_2));
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
        ui.lCar.tvModel.setVisibility(View.VISIBLE);
        ui.lCar.ivCar.setVisibility(View.VISIBLE);
        ui.lCar.tvCarRgstNo.setVisibility(View.VISIBLE);
        ui.lCar.ivFavorite.setVisibility(View.GONE);
        ui.lCar.tvCarStatus.setVisibility(View.GONE);
        ui.lCar.btnDelete.setVisibility(View.GONE);
        ui.lCar.btnModify.setVisibility(View.GONE);
        ui.lCar.btnRecovery.setVisibility(View.GONE);
        Glide
                .with(this)
                .load(vehicleVO.getVhclImgUri())
                .format(DecodeFormat.PREFER_ARGB_8888)
                .error(R.drawable.img_car_339_2)
                .placeholder(R.drawable.img_car_339_2)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ui.lCar.ivCar);

        ui.lCar.tvModel.setText(vehicleVO.getMdlCd() + "\n" + vehicleVO.getMdlNm());
        ui.lCar.tvCarRgstNo.setText(vehicleVO.getCarRgstNo());

        if (vehicleVO.getDelExpYn().equalsIgnoreCase(VariableType.DELETE_EXPIRE_Y)) {
            //삭제 예정 차량
            ui.lCar.tvCarStatus.setVisibility(View.VISIBLE);
            ui.lCar.tvCarStatus.setText(String.format(getString(R.string.gm_carlst_04_4), (((TextUtils.isEmpty(vehicleVO.getDelExpDay())) ? "0" : vehicleVO.getDelExpDay()))));
            ui.lCar.btnRecovery.setVisibility(View.VISIBLE);
            ui.lCar.btnRecovery.setOnClickListener(onSingleClickListener);//TODO
        } else {
            ui.lCar.ivFavorite.setVisibility(View.VISIBLE);
            ui.lCar.ivFavorite.setImageResource(vehicleVO.getMainVhclYn().equalsIgnoreCase(VariableType.MAIN_VEHICLE_N) ? R.drawable.ic_star_s : R.drawable.ic_star_l_s);
            ui.lCar.ivFavorite.setOnClickListener(onSingleClickListener);
            ui.lCar.btnModify.setVisibility(View.VISIBLE);
            ui.lCar.btnDelete.setVisibility(View.VISIBLE);
            ui.lCar.btnModify.setOnClickListener(onSingleClickListener);
            ui.lCar.btnDelete.setOnClickListener(onSingleClickListener);
        }
    }

    @Override
    public void onClickCommon(View v) {
        switch (v.getId()) {
            case R.id.iv_favorite:
                if(vehicleVO!=null
                        &&!vehicleVO.getDelExpYn().equalsIgnoreCase(VariableType.DELETE_EXPIRE_Y) //삭제 예정 차량이 아니고
                        &&vehicleVO.getCustGbCd().equalsIgnoreCase(VariableType.MAIN_VEHICLE_TYPE_OV) //소유 차량이고
                        &&!vehicleVO.getMainVhclYn().equalsIgnoreCase(VariableType.MAIN_VEHICLE_Y)){ //현재 주 이용 차량이 아니면
                    //주 이용 차량 설정 가능
                    gnsViewModel.reqGNS1004(new GNS_1004.Request(APPIAInfo.GM_CARLST_04.getId(), vehicleVO.getVin()));
                }
                break;
            case R.id.btn_recovery:
                //TODO 차량 복구
                //복구요청후
                //성공이면 gns 1001 요청 및 db갱신 후
                //해당 차대번호의 차량으로 vehicleVO를 덮어쓴 다음에
                //INIT..호출
                break;
            case R.id.btn_modify:
                //TODO 차량 수정
                final DialogCarRgstNo dialogCarRgstNo = new DialogCarRgstNo(this, R.style.BottomSheetDialogTheme);
                dialogCarRgstNo.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        if(!TextUtils.isEmpty(dialogCarRgstNo.getCarRgstNo())){
                            tmpCarRgstNo = dialogCarRgstNo.getCarRgstNo();
                            gnsViewModel.reqGNS1002(new GNS_1002.Request(APPIAInfo.GM_CARLST_04.getId(), vehicleVO.getVin(), dialogCarRgstNo.getCarRgstNo()));
                        }
                    }
                });
                dialogCarRgstNo.show();
                //수정 요청 후
                //성공이면 해당 DB의 및 로컬 데이터의 주이용차량 값 변경 후
                //UI갱신
                break;
            case R.id.btn_delete:
                //TODO 차량 삭제
                final BottomListDialog bottomListDialog = new BottomListDialog(this, R.style.BottomSheetDialogTheme);
                bottomListDialog.setOnDismissListener(dialogInterface -> {
                    String result = bottomListDialog.getSelectItem();
                    if(!TextUtils.isEmpty(result)){
                        //TODO 삭제 요청
                    }
                });
                bottomListDialog.setDatas(Arrays.asList(getResources().getStringArray(R.array.vehicle_deletion_reason)));
                bottomListDialog.show();
                //삭제제요청후
                //성공이면 gns 001 요청 및 db갱신 후
                //해당 차대번호의 차량으로 vehicleVO를 덮어쓴 다음에
                //INIT..호출
                break;
            case R.id.btn_benefit:
                //TODO 프리빌리지 혜택
                break;
            case R.id.btn_status:
                //TODO 프리빌리지 현황
                break;
            case R.id.btn_apply:
                //TODO 프리빌리지 신청하기
                break;

        }
    }
}
