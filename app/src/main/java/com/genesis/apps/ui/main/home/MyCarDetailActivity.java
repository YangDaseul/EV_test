package com.genesis.apps.ui.main.home;

import android.os.Bundle;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.databinding.ActivityMyCarDetailBinding;
import com.genesis.apps.ui.common.activity.SubActivity;

public class MyCarDetailActivity extends SubActivity<ActivityMyCarDetailBinding> {

    private VehicleVO vehicleVO;
    private int vehicleOwnerCnt=0;//복원시켰을 때 단수일 경우 임시로 주 이용 차량인 것 처럼 보여주기 위해서 현재 소유차량 갯수 필요
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_car_detail);
        getDataFromIntent();
        setViewModel();
        setObserver();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void getDataFromIntent() {
        try {
            vehicleOwnerCnt = getIntent().getIntExtra(KeyNames.KEY_NAME_VEHICLE_OWNER_COUNT,0);
            vehicleVO = (VehicleVO)getIntent().getSerializableExtra(KeyNames.KEY_NAME_VEHICLE);
            if(vehicleOwnerCnt==0||vehicleVO==null){
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
    }

    @Override
    public void setObserver() {

    }




    @Override
    public void onClickCommon(View v) {
    }
}













//package com.genesis.apps.ui.main.home;
//
//import android.content.DialogInterface;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.view.View;
//
//import androidx.lifecycle.ViewModelProvider;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.load.DecodeFormat;
//import com.bumptech.glide.load.engine.DiskCacheStrategy;
//import com.genesis.apps.R;
//import com.genesis.apps.comm.model.constants.KeyNames;
//import com.genesis.apps.comm.model.constants.ResultCodes;
//import com.genesis.apps.comm.model.constants.VariableType;
//import com.genesis.apps.comm.model.api.APPIAInfo;
//import com.genesis.apps.comm.model.api.gra.GNS_1002;
//import com.genesis.apps.comm.model.api.gra.GNS_1003;
//import com.genesis.apps.comm.model.api.gra.GNS_1004;
//import com.genesis.apps.comm.model.api.gra.GNS_1005;
//import com.genesis.apps.comm.model.api.gra.GNS_1010;
//import com.genesis.apps.comm.model.api.gra.MYP_1005;
//import com.genesis.apps.comm.model.vo.CouponVO;
//import com.genesis.apps.comm.model.vo.PrivilegeVO;
//import com.genesis.apps.comm.model.vo.VehicleVO;
//import com.genesis.apps.comm.util.SnackBarUtil;
//import com.genesis.apps.comm.viewmodel.GNSViewModel;
//import com.genesis.apps.comm.viewmodel.MYPViewModel;
//import com.genesis.apps.databinding.ActivityMyCarDetailBinding;
//import com.genesis.apps.room.ResultCallback;
//import com.genesis.apps.ui.common.activity.SubActivity;
//import com.genesis.apps.ui.common.dialog.bottom.BottomListDialog;
//import com.genesis.apps.ui.common.dialog.bottom.DialogCarRgstNo;
//
//import java.util.Arrays;
//import java.util.List;
//
//public class MyCarDetailActivity extends SubActivity<ActivityMyCarDetailBinding> {
//
//    private MYPViewModel mypViewModel;
//    private GNSViewModel gnsViewModel;
//    private VehicleVO vehicleVO;
//    private int vehicleOwnerCnt=0;//복원시켰을 때 단수일 경우 임시로 주 이용 차량인 것 처럼 보여주기 위해서 현재 소유차량 갯수 필요
//    private String tmpCarRgstNo="";
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_my_car_detail);
//        getDataFromIntent();
//        setViewModel();
//        setObserver();
//        initView();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        mypViewModel.reqMYP1005(new MYP_1005.Request(APPIAInfo.GM_CARLST_04.getId(), vehicleVO.getVin()));
//        gnsViewModel.reqGNS1010(new GNS_1010.Request(APPIAInfo.GM_CARLST_04.getId(), vehicleVO.getVin(), vehicleVO.getMdlNm()));
//    }
//
//    @Override
//    public void getDataFromIntent() {
//        try {
//            vehicleOwnerCnt = getIntent().getIntExtra(KeyNames.KEY_NAME_VEHICLE_OWNER_COUNT,0);
//            vehicleVO = (VehicleVO)getIntent().getSerializableExtra(KeyNames.KEY_NAME_VEHICLE);
//            if(vehicleOwnerCnt==0||vehicleVO==null){
//                exitPage("차량 정보가 존재하지 않습니다.\n잠시후 다시 시도해 주십시오.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//            exitPage("차량 정보가 존재하지 않습니다.\n잠시후 다시 시도해 주십시오.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
//        }
//    }
//
//    @Override
//    public void setViewModel() {
//        ui.setLifecycleOwner(this);
//        ui.setActivity(this);
//        gnsViewModel = new ViewModelProvider(this).get(GNSViewModel.class);
//        mypViewModel = new ViewModelProvider(this).get(MYPViewModel.class);
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
//                    showProgressDialog(false);
//
//                    gnsViewModel.setGNS1001ToDB(result.data, new ResultCallback() {
//                        @Override
//                        public void onSuccess(Object object) {
//                            try {
//                                if (Boolean.parseBoolean(object.toString())) {
//                                   vehicleVO = gnsViewModel.getVehicle(vehicleVO.getVin());
//
//                                   if(vehicleVO!=null){
//                                       initView();
//                                   }else{
//                                       //TODO 예외처리필요
//                                   }
//
//                                }
//                            }catch (Exception e){
//                                //TODO 예외처리 필요
//                            }
//                        }
//
//                        @Override
//                        public void onError(Object e) {
//                        //TODO 예외처리 필요
//                        }
//                    });
//                    break;
//                default:
//                    showProgressDialog(false);
//                    //TODO 예외처리 필요
//
//
//                    break;
//            }
//        });
//
//        gnsViewModel.getRES_GNS_1002().observe(this, result -> {
//            switch (result.status){
//                case LOADING:
//                    showProgressDialog(true);
//                    break;
//                case SUCCESS:
//                    showProgressDialog(false);
//                    if(result.data.getRtCd().equalsIgnoreCase("0000")){
//                        vehicleVO.setCarRgstNo(tmpCarRgstNo);
//                        tmpCarRgstNo="";
//                        initView();
//                        SnackBarUtil.show(this, getString(R.string.gm_carlst01_snackbar_2));
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
//
//        gnsViewModel.getRES_GNS_1003().observe(this, result -> {
//
//            switch (result.status){
//                case LOADING:
//                    showProgressDialog(true);
//                    break;
//                case SUCCESS:
//                    showProgressDialog(false);
//                    if(result.data.getRtCd().equalsIgnoreCase("0000")){
//                        //삭제가 완료되면 로컬 데이터의 주 이용 차량 정보를 제거
//                        vehicleVO.setMainVhclYn(VariableType.MAIN_VEHICLE_N);
//                        vehicleVO.setDelExpDay("7");
//                        vehicleVO.setDelExpYn(VariableType.DELETE_EXPIRE_Y);
//                        //TODO UPDATE VEHICLE VO TO DB 진행 필요
//                        initView();
//                        SnackBarUtil.show(this, getString(R.string.gm_carlst01_snackbar_4));
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
////                        gnsViewModel.reqGNS1001(new GNS_1001.Request(APPIAInfo.GM_CARLST_04.getId()));
//                        vehicleVO.setMainVhclYn(VariableType.MAIN_VEHICLE_Y);
//                        //TODO UPDATE VEHICLE VO TO DB 진행 필요
//                        initView();
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
//        });
//
//
//        gnsViewModel.getRES_GNS_1005().observe(this, result -> {
//            switch (result.status){
//                case LOADING:
//                    showProgressDialog(true);
//                    break;
//                case SUCCESS:
//                    showProgressDialog(false);
//                    if(result.data.getRtCd().equalsIgnoreCase("0000")){
////                        gnsViewModel.reqGNS1001(new GNS_1001.Request(APPIAInfo.GM_CARLST_04.getId()));
//                        vehicleVO.setDelExpYn(VariableType.DELETE_EXPIRE_N);
//                        vehicleVO.setDelExpDay("");
//                        if(vehicleOwnerCnt==1){
//                            vehicleVO.setMainVhclYn(VariableType.MAIN_VEHICLE_Y);
//                        }
//                        //TODO UPDATE VEHICLE VO TO DB 진행 필요
//                        initView();
//                        SnackBarUtil.show(this, getString(R.string.gm_carlst01_snackbar_3));
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
//        });
//
//        //프리빌리지 상태 observer
//        mypViewModel.getRES_MYP_1005().observe(this, result -> {
//            switch (result.status) {
//                case SUCCESS:
//                    setPrivilegeLayout(result.data);
//                    break;
//                case LOADING:
//                    break;
//                case ERROR:
//                    break;
//            }
//        });
//
//        gnsViewModel.getRES_GNS_1010().observe(this, result -> {
//            switch (result.status){
//                case LOADING:
//                    showProgressDialog(true);
//                    break;
//                case SUCCESS:
//                    showProgressDialog(false);
//                    if(result.data.getCpnList()!=null&&result.data.getCpnList().size()>0) {
//                        setViewCoupon(result.data.getCpnList());
//                        break;
//                    }
//                default:
//                    showProgressDialog(false);
//                    setViewCoupon(null);
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
//    }
//
//    private void setViewCoupon(List<CouponVO> list) {
//
//        if(list!=null&&list.size()>0) {
//
//            for (CouponVO couponVO : list) {
//                String cnt = (TextUtils.isEmpty(couponVO.getRemCnt()) ? "0" : couponVO.getRemCnt()) +" "+getString(R.string.gm_carlst_04_16);
//                switch (couponVO.getItemDivCd()) {
//                    case VariableType.COUPON_CODE_ENGINE:
//                        ui.tvPartEngineOilCnt.setText(cnt);
//                        ui.tvPartOilFilterCnt.setText(cnt);
//                        ui.tvPartAirCleanerCnt.setText(cnt);
//                        ui.tvTitlePartEngine.setText((TextUtils.isEmpty(couponVO.getItemNm()) ? getString(R.string.gm_carlst_04_7) : couponVO.getItemNm()));
//                        break;
//                    case VariableType.COUPON_CODE_FILTER:
//                        ui.tvPartAirConditionerFilterCnt.setText(cnt);
//                        ui.tvTitlePartAirConditionerFilter.setText((TextUtils.isEmpty(couponVO.getItemNm()) ? getString(R.string.gm_carlst_04_11) : couponVO.getItemNm()));
//                        break;
//                    case VariableType.COUPON_CODE_BREAK_PAD:
//                        ui.tvPartBreakPadCnt.setText(cnt);
//                        ui.tvTitlePartBreakPad.setText((TextUtils.isEmpty(couponVO.getItemNm()) ? getString(R.string.gm_carlst_04_12) : couponVO.getItemNm()));
//                        break;
//                    case VariableType.COUPON_CODE_WIPER:
//                        ui.tvPartWiperCnt.setText(cnt);
//                        ui.tvTitlePartWiper.setText((TextUtils.isEmpty(couponVO.getItemNm()) ? getString(R.string.gm_carlst_04_13) : couponVO.getItemNm()));
//                        break;
//                    case VariableType.COUPON_CODE_BREAK_OIL:
//                        ui.tvPartBreakCnt.setText(cnt);
//                        ui.tvTitlePartBreak.setText((TextUtils.isEmpty(couponVO.getItemNm()) ? getString(R.string.gm_carlst_04_14) : couponVO.getItemNm()));
//                        break;
//                    case VariableType.COUPON_CODE_PICKUP_DELIVERY:
//                        ui.tvPartHomeCnt.setText(cnt);
//                        ui.tvTitlePartHome.setText((TextUtils.isEmpty(couponVO.getItemNm()) ? getString(R.string.gm_carlst_04_15) : couponVO.getItemNm()));
//                        break;
//                    case VariableType.COUPON_CODE_SONAKS:
//                    default:
//                        //처리안함
//                        break;
//                }
//            }
//        }else{
//            String cnt = "0 "+getString(R.string.gm_carlst_04_16);
//            ui.tvPartEngineOilCnt.setText(cnt);
//            ui.tvPartOilFilterCnt.setText(cnt);
//            ui.tvPartAirCleanerCnt.setText(cnt);
//            ui.tvPartAirConditionerFilterCnt.setText(cnt);
//            ui.tvPartBreakPadCnt.setText(cnt);
//            ui.tvPartWiperCnt.setText(cnt);
//            ui.tvPartBreakCnt.setText(cnt);
//            ui.tvPartHomeCnt.setText(cnt);
//        }
//    }
//
//    private void setPrivilegeLayout(MYP_1005.Response data) {
//
//        if (data.getMbrshJoinYn().equalsIgnoreCase("N") || data.getPvilList().size() < 1) {
//            ui.lPrivilege.setVisibility(View.GONE);
//        } else {
//            ui.lPrivilege.setVisibility(View.VISIBLE);
//            if (data.getPvilList().size()>0) {
//                switch (data.getPvilList().get(0).getJoinPsblCd()) {
//                    case PrivilegeVO.JOIN_CODE_APPLY_POSSIBLE:
//                        ui.btnStatus.setVisibility(View.GONE);
//                        ui.btnBenefit.setVisibility(View.GONE);
//                        ui.btnApply.setVisibility(View.VISIBLE);
//                        ui.btnApply.setTag(R.id.url, data.getPvilList().get(0).getServiceUrl());
//                        break;
//                    case PrivilegeVO.JOIN_CODE_APPLYED:
//                        ui.btnStatus.setVisibility(View.VISIBLE);
//                        ui.btnBenefit.setVisibility(View.VISIBLE);
//                        ui.btnApply.setVisibility(View.GONE);
//
//                        ui.btnStatus.setTag(R.id.url, data.getPvilList().get(0).getServiceUrl());
//                        ui.btnBenefit.setTag(R.id.url, data.getPvilList().get(0).getServiceDetailUrl());
//                        break;
//                    default:
//                        //TODO 정의 필요 임시로 프리빌리지 레이아웃이 안보이도록 처리;
//                        ui.lPrivilege.setVisibility(View.GONE);
//                        break;
//                }
//            }
//        }
//    }
//
//    private void initView() {
//
//        ui.lCar.tvModel.setVisibility(View.VISIBLE);
//        ui.lCar.ivCar.setVisibility(View.VISIBLE);
//        ui.lCar.tvCarRgstNo.setVisibility(View.VISIBLE);
//        ui.lCar.ivFavorite.setVisibility(View.GONE);
//        ui.lCar.tvCarStatus.setVisibility(View.GONE);
//        ui.lCar.btnDelete.setVisibility(View.GONE);
//        ui.lCar.btnModify.setVisibility(View.GONE);
//        ui.lCar.btnRecovery.setVisibility(View.GONE);
//        Glide
//                .with(this)
//                .load(vehicleVO.getMygImgUri())
//                .format(DecodeFormat.PREFER_ARGB_8888)
//                .error(R.drawable.img_car_339_2)
//                .placeholder(R.drawable.img_car_339_2)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(ui.lCar.ivCar);
//
//        ui.lCar.tvModel.setText(vehicleVO.getMdlCd() + "\n" + vehicleVO.getMdlNm());
//        ui.lCar.tvCarRgstNo.setText(vehicleVO.getCarRgstNo());
//
//        if (vehicleVO.getDelExpYn().equalsIgnoreCase(VariableType.DELETE_EXPIRE_Y)) {
//            //삭제 예정 차량
//            ui.lCar.tvCarStatus.setVisibility(View.VISIBLE);
//            ui.lCar.tvCarStatus.setText(String.format(getString(R.string.gm_carlst_04_4), (((TextUtils.isEmpty(vehicleVO.getDelExpDay())) ? "0" : vehicleVO.getDelExpDay()))));
//            ui.lCar.btnRecovery.setVisibility(View.VISIBLE);
//            ui.lCar.btnRecovery.setOnClickListener(onSingleClickListener);//TODO
//        } else {
//            ui.lCar.ivFavorite.setVisibility(View.VISIBLE);
//            ui.lCar.ivFavorite.setImageResource(vehicleVO.getMainVhclYn().equalsIgnoreCase(VariableType.MAIN_VEHICLE_N) ? R.drawable.ic_star_s : R.drawable.ic_star_l_s);
//            ui.lCar.ivFavorite.setOnClickListener(onSingleClickListener);
//            ui.lCar.btnModify.setVisibility(View.VISIBLE);
//            ui.lCar.btnDelete.setVisibility(View.VISIBLE);
//            ui.lCar.btnModify.setOnClickListener(onSingleClickListener);
//            ui.lCar.btnDelete.setOnClickListener(onSingleClickListener);
//        }
//    }
//
//    @Override
//    public void onClickCommon(View v) {
//        switch (v.getId()) {
//            case R.id.iv_favorite:
//                if(vehicleVO!=null
//                        &&!vehicleVO.getDelExpYn().equalsIgnoreCase(VariableType.DELETE_EXPIRE_Y) //삭제 예정 차량이 아니고
//                        &&vehicleVO.getCustGbCd().equalsIgnoreCase(VariableType.MAIN_VEHICLE_TYPE_OV) //소유 차량이고
//                        &&!vehicleVO.getMainVhclYn().equalsIgnoreCase(VariableType.MAIN_VEHICLE_Y)){ //현재 주 이용 차량이 아니면
//                    //주 이용 차량 설정 가능
//                    gnsViewModel.reqGNS1004(new GNS_1004.Request(APPIAInfo.GM_CARLST_04.getId(), vehicleVO.getVin()));
//                }
//                break;
//            case R.id.btn_recovery:
//                //TODO 차량 복구
//
//                if(vehicleVO!=null
//                        &&vehicleVO.getDelExpYn().equalsIgnoreCase(VariableType.DELETE_EXPIRE_Y) //삭제 예정 차량이고
//                        &&vehicleVO.getCustGbCd().equalsIgnoreCase(VariableType.MAIN_VEHICLE_TYPE_OV) //소유 차량이고
//                        ){
//                    //복구 요청 가능
//                    gnsViewModel.reqGNS1005(new GNS_1005.Request(APPIAInfo.GM_CARLST_04.getId(), vehicleVO.getVin()));
//                }
//                break;
//            case R.id.btn_modify:
//                //TODO 차량 수정
//                final DialogCarRgstNo dialogCarRgstNo = new DialogCarRgstNo(this, R.style.BottomSheetDialogTheme);
//                dialogCarRgstNo.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                    @Override
//                    public void onDismiss(DialogInterface dialogInterface) {
//                        if(!TextUtils.isEmpty(dialogCarRgstNo.getCarRgstNo())){
//                            tmpCarRgstNo = dialogCarRgstNo.getCarRgstNo();
//                            gnsViewModel.reqGNS1002(new GNS_1002.Request(APPIAInfo.GM_CARLST_04.getId(), vehicleVO.getVin(), dialogCarRgstNo.getCarRgstNo()));
//                        }
//                    }
//                });
//                dialogCarRgstNo.show();
//                //수정 요청 후
//                //성공이면 해당 DB의 및 로컬 데이터의 주이용차량 값 변경 후
//                //UI갱신
//                break;
//            case R.id.btn_delete:
//                //TODO 차량 삭제
//                final List<String> deletionList = Arrays.asList(getResources().getStringArray(R.array.vehicle_deletion_reason));
//                final BottomListDialog bottomListDialog = new BottomListDialog(this, R.style.BottomSheetDialogTheme);
//                bottomListDialog.setOnDismissListener(dialogInterface -> {
//                    String result = bottomListDialog.getSelectItem();
//                    if(!TextUtils.isEmpty(result)){
//                        //TODO 삭제 요청
//                        String delRsnCd = "";
//
//                        if(result.equalsIgnoreCase(deletionList.get(0))){
//                            delRsnCd = VariableType.MY_CAR_DELETION_REASON_SELL;
//                        }else if(result.equalsIgnoreCase(deletionList.get(1))){
//                            delRsnCd = VariableType.MY_CAR_DELETION_REASON_SCRAPPED;
//                        }else if(result.equalsIgnoreCase(deletionList.get(2))){
//                            delRsnCd = VariableType.MY_CAR_DELETION_REASON_TRANSFER;
//                        }else{
//                            delRsnCd = VariableType.MY_CAR_DELETION_REASON_ETC;
//                        }
//                        gnsViewModel.reqGNS1003(new GNS_1003.Request(APPIAInfo.GM_CARLST_04.getId(), vehicleVO.getVin(), delRsnCd));
//                    }
//                });
//                bottomListDialog.setDatas(deletionList);
//                bottomListDialog.setTitle(getString(R.string.gm_carlst_p01_7));
//                bottomListDialog.show();
//                //삭제제요청후
//                //성공이면 gns 001 요청 및 db갱신 후
//                //해당 차대번호의 차량으로 vehicleVO를 덮어쓴 다음에
//                //INIT..호출
//                break;
//            case R.id.btn_benefit:
//                //TODO 프리빌리지 혜택
//            case R.id.btn_status:
//                //TODO 프리빌리지 현황
//            case R.id.btn_apply:
//                //TODO 프리빌리지 신청하기
//
//                String url = v.getTag(R.id.url).toString();
//                if(!TextUtils.isEmpty(url)){
//                    //todo webview 이동
//                }else{
//                    SnackBarUtil.show(this, "페이지 정보가 존재하지 않습니다.\n잠시 후 다시 시도해 주십시오.");
//                }
//
//                break;
//
//        }
//    }
//}
