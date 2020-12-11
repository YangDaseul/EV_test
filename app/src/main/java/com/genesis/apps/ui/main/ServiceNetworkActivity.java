package com.genesis.apps.ui.main;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewStub;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.BTR_1008;
import com.genesis.apps.comm.model.api.gra.BTR_1009;
import com.genesis.apps.comm.model.api.gra.REQ_1002;
import com.genesis.apps.comm.model.api.gra.REQ_1003;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.BtrVO;
import com.genesis.apps.comm.model.vo.RepairTypeVO;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.viewmodel.BTRViewModel;
import com.genesis.apps.comm.viewmodel.LGNViewModel;
import com.genesis.apps.comm.viewmodel.PUBViewModel;
import com.genesis.apps.comm.viewmodel.REQViewModel;
import com.genesis.apps.databinding.ActivityMap2Binding;
import com.genesis.apps.databinding.LayoutMapOverlayUiBottomSelectBinding;
import com.genesis.apps.ui.common.activity.GpsBaseActivity;
import com.genesis.apps.ui.common.dialog.bottom.BottomListDialog;
import com.genesis.apps.ui.common.fragment.SubFragment;
import com.genesis.apps.ui.main.home.BluehandsFilterFragment;
import com.genesis.apps.ui.main.home.BtrBluehandsListActivity;
import com.google.gson.Gson;
import com.hmns.playmap.PlayMapPoint;
import com.hmns.playmap.shape.PlayMapMarker;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
/**
 * @brief 서비스 네트워크
 * 버틀러 변경, 렌트리스 차량 등록, 서비스 네트워크 찾기 등에서 이용 가능하며
 * 각 메뉴마다 동작이 약간 상이함
 *
 * @see #PAGE_TYPE_BTR 버틀러 변경
 * 하단 메뉴 이름 : 설정
 * @see #PAGE_TYPE_RENT 렌트리스
 * 하단 메뉴 이름 : 신청
 * @see #PAGE_TYPE_SERVICE 서비스 네트워크 찾기
 * 하단 메뉴 이름 : 예약
 *
 */
public class ServiceNetworkActivity extends GpsBaseActivity<ActivityMap2Binding> {

    private BTRViewModel btrViewModel;
    private LGNViewModel lgnViewModel;
    private PUBViewModel pubViewModel;
    private REQViewModel reqViewModel;
    private BtrVO btrVO = null;
    private LayoutMapOverlayUiBottomSelectBinding bottomSelectBinding;
    private String fillerCd = "";
    private String addr = "";
    private String addrDtl = "";
    private VehicleVO mainVehicle=null;

    public final static int PAGE_TYPE_BTR=0;//버틀러 변경
    public final static int PAGE_TYPE_RENT=1;//렌트리스 등록
    public final static int PAGE_TYPE_SERVICE=2;//서비스 네트워크
    public final static int PAGE_TYPE_REPAIR=3;//정비소예약
    private int pageType=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map2);
        getDataFromIntent();
        setViewModel();
        setObserver();
        initView();
        reqMyLocation();
    }

    private void initView() {
        ui.lMapOverlayTitle.tvMapTitleText.setOnClickListener(onSingleClickListener);
        ui.btnMyPosition.setOnClickListener(onSingleClickListener);
        ui.pmvMapView.onMapTouchUpListener((motionEvent, makerList) -> {

            //선택한 지도위치에 마커가 한개 이상 있을 경우
            if (makerList != null && makerList.size() > 0) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_UP:
                        BtrVO btrVO = btrViewModel.getBtrVO(makerList.get(0).getId());
                        if (btrVO != null) {
                            ui.pmvMapView.removeAllMarkerItem();
                            switch (pageType) {
                                case PAGE_TYPE_BTR:
                                case PAGE_TYPE_RENT:
                                    setPosition(btrViewModel.getRES_BTR_1008().getValue().data.getAsnList(), btrVO);
                                    break;
                                case PAGE_TYPE_SERVICE:
                                case PAGE_TYPE_REPAIR:
                                default:
                                    setPosition(reqViewModel.getRES_REQ_1002().getValue().data.getAsnList(), btrVO);
                                    break;
                            }
                        }
                        break;
                    default:
                        break;
                }

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void getDataFromIntent() {
        try {
            btrVO = (BtrVO) getIntent().getSerializableExtra(KeyNames.KEY_NAME_BTR);
            pageType = getIntent().getIntExtra(KeyNames.KEY_NAME_PAGE_TYPE, PAGE_TYPE_SERVICE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        btrViewModel = new ViewModelProvider(this).get(BTRViewModel.class);
        lgnViewModel = new ViewModelProvider(this).get(LGNViewModel.class);
        pubViewModel = new ViewModelProvider(this).get(PUBViewModel.class);
        reqViewModel = new ViewModelProvider(this).get(REQViewModel.class);
    }

    @Override
    public void setObserver() {

        lgnViewModel.getPosition().observe(this, doubles -> {
            ui.pmvMapView.initMap(doubles.get(0), doubles.get(1), 17);


            //기본 버틀러 정보가 없고 렌트리스 인 경우에는 제네시스 전담으로 기본 필터 전달
            if (btrVO==null&&pageType==PAGE_TYPE_RENT) {
                fillerCd = VariableType.BTR_FILTER_CODE_A;
            } else {
                fillerCd = "";
            }

            switch (pageType) {
                case PAGE_TYPE_BTR:
                case PAGE_TYPE_RENT:
                    btrViewModel.reqBTR1008(new BTR_1008.Request(APPIAInfo.GM_CARLST_01_B01.getId(), String.valueOf(doubles.get(1)), String.valueOf(doubles.get(0)), "", "", fillerCd));
                    break;
                case PAGE_TYPE_REPAIR:
                    if(btrVO!=null) {
                        try {
                            mainVehicle = reqViewModel.getMainVehicle();
                            reqViewModel.reqREQ1002(new REQ_1002.Request(APPIAInfo.SM_SNFIND01.getId(), mainVehicle.getVin(), mainVehicle.getMdlCd(), String.valueOf(doubles.get(1)), String.valueOf(doubles.get(0)),"", "", "", ""));
                        } catch (Exception e) {

                        }
                        break;
                    }
                case PAGE_TYPE_SERVICE:
                default:
                    try {
                        mainVehicle = reqViewModel.getMainVehicle();
                        reqViewModel.reqREQ1002(new REQ_1002.Request(APPIAInfo.SM_SNFIND01.getId(), mainVehicle.getVin(), mainVehicle.getMdlCd(), String.valueOf(doubles.get(1)), String.valueOf(doubles.get(0)), "", "", "", ""));
                    }catch (Exception e){

                    }
                    break;
            }

        });

        btrViewModel.getRES_BTR_1008().observe(this, result -> {

            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    showProgressDialog(false);
                    if (result.data != null && result.data.getAsnList() != null) {
                        setPosition(result.data.getAsnList(), result.data.getAsnList().get(0));
                    }
                    break;
                default:
                    showProgressDialog(false);
                    break;
            }
        });

        btrViewModel.getRES_BTR_1009().observe(this, result -> {
            switch (result.status){
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    showProgressDialog(false);
                    if(result.data!=null&&result.data.getRtCd().equalsIgnoreCase("0000")){
                        setResult(ResultCodes.REQ_CODE_BTR.getCode(), new Intent().putExtra(KeyNames.KEY_NAME_BTR, btrVO));
                        finish();
                        break;
                    }
                default:
                    showProgressDialog(false);
                    String serverMsg="";
                    try {
                        serverMsg = result.data.getRtMsg();
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally{
                        SnackBarUtil.show(this, (TextUtils.isEmpty(serverMsg)) ? getString(R.string.gm_bt06_snackbar_2) : serverMsg);
                    }
                    break;
            }
        });


        pubViewModel.getFilterInfo().observe(this, filterInfo -> {
            try {
                fillerCd = filterInfo.get(0);
            } catch (Exception e) {
                fillerCd = "";
            }
            try {
                addr = filterInfo.get(1);
            } catch (Exception e) {
                addr = "";
            }
            try {
                addrDtl = filterInfo.get(2);
            } catch (Exception e) {
                addrDtl = "";
            }

            if (!TextUtils.isEmpty(fillerCd) || !TextUtils.isEmpty(addr) || !TextUtils.isEmpty(addrDtl)) {
                try {
                    switch (pageType) {
                        case PAGE_TYPE_BTR:
                        case PAGE_TYPE_RENT:
                            btrViewModel.reqBTR1008(new BTR_1008.Request(APPIAInfo.GM_CARLST_01_B01.getId(), String.valueOf(lgnViewModel.getPosition().getValue().get(1)), String.valueOf(lgnViewModel.getPosition().getValue().get(0)), addr, addrDtl, fillerCd));
                            break;
                        case PAGE_TYPE_REPAIR:
                        case PAGE_TYPE_SERVICE:
                        default:
                            reqViewModel.reqREQ1002(new REQ_1002.Request(APPIAInfo.SM_SNFIND01.getId(), reqViewModel.getMainVehicle().getVin(), reqViewModel.getMainVehicle().getMdlCd(), String.valueOf(lgnViewModel.getPosition().getValue().get(1)), String.valueOf(lgnViewModel.getPosition().getValue().get(0)), addr, addrDtl, fillerCd, "" ));
                            break;
                    }
                }catch (Exception e){

                }
            }
        });


        reqViewModel.getRES_REQ_1002().observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    showProgressDialog(false);
                    if (result.data != null && result.data.getAsnList() != null) {
                        setPosition(result.data.getAsnList(), result.data.getAsnList().get(0));
                    }
                    break;
                default:
                    showProgressDialog(false);
                    break;
            }
        });


        reqViewModel.getRES_REQ_1003().observe(this, result -> {
            switch (result.status){
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    showProgressDialog(false);
                    List<RepairTypeVO> list = new ArrayList<>();
                    if(result.data.getPrctYn().equalsIgnoreCase("Y")&&result.data.getRparTypList()!=null&&result.data.getRparTypList().size()>0){
                        list.addAll(result.data.getRparTypList());
                        showDialogRepairType(list);
                        break;
                    }
                default:
                    showProgressDialog(false);
                    SnackBarUtil.show(this, "정비 예약이 불가능합니다.\n다른 지점을 선택해 주세요.");
                    break;
            }
        });
    }

    private void showDialogRepairType(final List<RepairTypeVO> list) {
        try {
            final BottomListDialog bottomListDialog = new BottomListDialog(this, R.style.BottomSheetDialogTheme);
            bottomListDialog.setOnDismissListener(dialogInterface -> {
                String result = bottomListDialog.getSelectItem();
                if (!TextUtils.isEmpty(result)) {
                    RepairTypeVO repairTypeVO = null;
                    try{
                        repairTypeVO = reqViewModel.getRepairTypeCd(result, list);
                        exitPage(new Intent().putExtra(KeyNames.KEY_NAME_SERVICE_REPAIR_TYPE_CODE, repairTypeVO)
                        .putExtra(KeyNames.KEY_NAME_BTR, btrVO), ResultCodes.REQ_CODE_SERVICE_NETWORK_RESERVE.getCode());
                    }catch (Exception e){

                    }
                }
            });
            bottomListDialog.setDatas(reqViewModel.getRepairTypeNm(list));
            bottomListDialog.setTitle(getString(R.string.sm_snfind01_p01_1));
            bottomListDialog.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void onClickCommon(View v) {

        switch (v.getId()) {
            case R.id.tv_map_select_btn1://선택
                switch (pageType){
                    case PAGE_TYPE_BTR:
                        //todo 아래 vin정보와 asncd가 유효한 값인지 확인 필요
                        btrViewModel.reqBTR1009(new BTR_1009.Request(APPIAInfo.GM_BT06.getId(), btrVO.getVin(), btrVO.getAsnCd()));
                        break;
                    case PAGE_TYPE_RENT:
                        //기타 렌트리스.
                        exitPage(new Intent().putExtra(KeyNames.KEY_NAME_BTR, btrVO), ResultCodes.REQ_CODE_BTR.getCode());
                        break;
                    case PAGE_TYPE_REPAIR:
                        //정비소 예약2단계 선택
                        exitPage(new Intent().putExtra(KeyNames.KEY_NAME_BTR, btrVO), ResultCodes.REQ_CODE_BTR.getCode());
                        break;
                    case PAGE_TYPE_SERVICE:
                    default:
                        reqViewModel.reqREQ1003(new REQ_1003.Request(APPIAInfo.SM_SNFIND01.getId(),mainVehicle.getVin(),mainVehicle.getMdlCd(), btrVO.getAsnCd(), btrVO.getAcps1Cd(), btrVO.getFirmScnCd()));
                        //todo 예약 시 이펙트..
                        break;
                }
//
//                switch (btnStrId){
//                    case R.string.bt06_14:
//                        //버틀러 신청 서비스인 경우
//                        //todo 아래 vin정보와 asncd가 유효한 값인지 확인 필요
//                        btrViewModel.reqBTR1009(new BTR_1009.Request(APPIAInfo.GM_BT06.getId(), btrVO.getVin(), btrVO.getAsnCd()));
//                        break;
//                    default:
//                        //기타 렌트리스.
//                        setResult(ResultCodes.REQ_CODE_BTR.getCode(), new Intent().putExtra(KeyNames.KEY_NAME_BTR, btrVO));
//                        finish();
//                        break;
//                }
                break;
            case R.id.tv_map_select_btn2://목록

                List<BtrVO> list = new ArrayList<>();

                switch (pageType){
                    case PAGE_TYPE_BTR:
                    case PAGE_TYPE_RENT:
                        list = btrViewModel.getRES_BTR_1008().getValue().data.getAsnList();
                        break;
                    case PAGE_TYPE_REPAIR:
                    case PAGE_TYPE_SERVICE:
                    default:
                        list = reqViewModel.getRES_REQ_1002().getValue().data.getAsnList();
                        break;
                }
                if (list != null && list.size() > 0) {
                    startActivitySingleTop(new Intent(this, BtrBluehandsListActivity.class).putExtra(KeyNames.KEY_NAME_BTR,  new Gson().toJson(list)), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                }
                break;
            case R.id.btn_my_position:
                ui.pmvMapView.setMapCenterPoint(new PlayMapPoint(lgnViewModel.getMyPosition().get(0), lgnViewModel.getMyPosition().get(1)), 500);
                break;
            case R.id.tv_map_title_text:
                showFragment(new BluehandsFilterFragment());
                break;
        }

    }

    /**
     * drawMarkerItem 지도에 마커를 그린다.
     */
    public void drawMarkerItem(BtrVO btrVO, int iconId) {
        PlayMapMarker markerItem = new PlayMapMarker();
//        PlayMapPoint point = mapView.getMapCenterPoint();
        PlayMapPoint point = new PlayMapPoint(Double.parseDouble(btrVO.getMapYcooNm()),Double.parseDouble(btrVO.getMapXcooNm()) );
        markerItem.setMapPoint(point);
//        markerItem.setCalloutTitle("제목");
//        markerItem.setCalloutSubTitle("내용");
        markerItem.setCanShowCallout(false);
        markerItem.setAutoCalloutVisible(false);
        markerItem.setIcon(((BitmapDrawable) getResources().getDrawable(iconId, null)).getBitmap());


        String strId = btrVO.getAsnCd();
        ui.pmvMapView.addMarkerItem(strId, markerItem);
    }


    private void reqMyLocation() {
        showProgressDialog(true);
        findMyLocation(location -> {
            showProgressDialog(false);
            if (location == null) {
                exitPage("위치 정보를 불러올 수 없습니다. GPS 상태를 확인 후 다시 시도해 주세요.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
                return;
            }

            runOnUiThread(() -> {
                if (btrVO == null) {
                    //버틀러 정보가 없으면 내 위치를 기본값으로 사용
                    lgnViewModel.setPosition(location.getLatitude(), location.getLongitude());
                } else {
                    //버틀러 정보가 잇으면 버틀러 블루핸즈 위치를 기본값으로 사용
                    lgnViewModel.setPosition(Double.parseDouble(btrVO.getMapYcooNm()), Double.parseDouble(btrVO.getMapXcooNm()) );
                }
                //내위치는 항상 저장
                lgnViewModel.setMyPosition(location.getLatitude(), location.getLongitude());
            });

        }, 5000, GpsRetType.GPS_RETURN_HIGH, false);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ResultCodes.REQ_CODE_BTR.getCode()) {
            btrVO = (BtrVO) data.getSerializableExtra(KeyNames.KEY_NAME_BTR);
            List<BtrVO> list = null;

            switch (pageType){
                case PAGE_TYPE_BTR:
                case PAGE_TYPE_RENT:
                    list = btrViewModel.getRES_BTR_1008().getValue().data.getAsnList();
                    break;
                case PAGE_TYPE_REPAIR:
                case PAGE_TYPE_SERVICE:
                default:
                    list = reqViewModel.getRES_REQ_1002().getValue().data.getAsnList();
                    break;
            }


            setPosition(list, btrVO);
        } else if (resultCode == ResultCodes.REQ_CODE_ADDR_FILTER.getCode()) {
//            try {
//                fillerCd = data.getStringExtra(KeyNames.KEY_NAME_MAP_FILTER);
//            } catch (Exception e) {
//                fillerCd = "";
//            }
//            try {
//                addr = data.getStringExtra(KeyNames.KEY_NAME_MAP_CITY);
//            } catch (Exception e) {
//                addr = "";
//            }
//            try {
//                addrDtl = data.getStringExtra(KeyNames.KEY_NAME_MAP_GU);
//            } catch (Exception e) {
//                addrDtl = "";
//            }
//
//            if (!TextUtils.isEmpty(fillerCd) || !TextUtils.isEmpty(addr) || !TextUtils.isEmpty(addrDtl)) {
//                btrViewModel.reqBTR1008(new BTR_1008.Request(APPIAInfo.GM_CARLST_01_B01.getId(), String.valueOf(btrVO.getMapXcooNm()), String.valueOf(btrVO.getMapYcooNm()), "", "", ""));
//            }
        }
    }

    private void setPosition(List<BtrVO> list, BtrVO btrVO) {
        this.btrVO = btrVO;
        if (bottomSelectBinding == null) {
            setViewStub(R.id.vs_map_overlay_bottom_box, R.layout.layout_map_overlay_ui_bottom_select, new ViewStub.OnInflateListener() {
                @Override
                public void onInflate(ViewStub viewStub, View inflated) {
                    bottomSelectBinding = DataBindingUtil.bind(inflated);
                    bottomSelectBinding.setActivity(ServiceNetworkActivity.this);
                    switch (pageType){
                        case PAGE_TYPE_BTR:
                            bottomSelectBinding.tvMapSelectBtn1.setText(R.string.bt06_14);
                            break;
                        case PAGE_TYPE_RENT:
                            bottomSelectBinding.tvMapSelectBtn1.setText(R.string.bt06_15);
                            break;
                        case PAGE_TYPE_REPAIR:
                            bottomSelectBinding.tvMapSelectBtn1.setText(R.string.map_btn_1);
                            break;
                        case PAGE_TYPE_SERVICE:
                        default:
                            String custGbCd = lgnViewModel.getDbUserRepo().getUserVO().getCustGbCd();
                            if(!TextUtils.isEmpty(custGbCd)&&custGbCd.equalsIgnoreCase(VariableType.MAIN_VEHICLE_TYPE_OV)){//서비스네트워크에서는 차량 소유 고객에게만 예약버튼 제공
                                bottomSelectBinding.tvMapSelectBtn1.setVisibility(View.VISIBLE);
                                bottomSelectBinding.tvMapSelectBtn1.setText(R.string.map_btn_3);
                            }else{
                                bottomSelectBinding.tvMapSelectBtn1.setVisibility(View.INVISIBLE);
                            }
                            break;
                    }
                    bottomSelectBinding.setData(btrVO);
                }
            });
        } else {
            bottomSelectBinding.setData(btrVO);
        }

        for (int i = 0; i < list.size(); i++) {
            if (btrVO.getAsnCd().equalsIgnoreCase(list.get(i).getAsnCd())) {
                drawMarkerItem(list.get(i), R.drawable.ic_pin_carcenter);
            } else {
                drawMarkerItem(list.get(i), R.drawable.ic_pin);
            }
        }
        ui.pmvMapView.setMapCenterPoint(new PlayMapPoint( Double.parseDouble(btrVO.getMapYcooNm()), Double.parseDouble(btrVO.getMapXcooNm())), 500);
//        ui.pmvMapView.initMap(Double.parseDouble(btrVO.getMapXcooNm()), Double.parseDouble(btrVO.getMapYcooNm()), 17);
    }


    @Override
    public void onBackPressed() {
        List<SubFragment> fragments = getFragments();
        if(fragments!=null&&fragments.size()>0){
            hideFragment(fragments.get(0));
        }else{
            super.onBackPressed();
        }
    }
}
