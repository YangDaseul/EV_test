package com.genesis.apps.ui.main.service;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.gra.APPIAInfo;
import com.genesis.apps.comm.model.gra.api.WSH_1002;
import com.genesis.apps.comm.model.gra.api.WSH_1003;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.model.vo.WashBrnVO;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.viewmodel.LGNViewModel;
import com.genesis.apps.comm.viewmodel.WSHViewModel;
import com.genesis.apps.databinding.ActivityMap2Binding;
import com.genesis.apps.databinding.LayoutMapOverlayUiBottomSonaxBranchBinding;
import com.genesis.apps.ui.common.activity.GpsBaseActivity;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;
import com.genesis.apps.ui.common.fragment.SubFragment;
import com.hmns.playmap.PlayMapPoint;
import com.hmns.playmap.shape.PlayMapMarker;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class CarWashSearchActivity extends GpsBaseActivity<ActivityMap2Binding> {
    private static final String TAG = CarWashSearchActivity.class.getSimpleName();
    public static final int X = 0;
    public static final int Y = 1;

    private static final int DEFAULT_ZOOM = 17;

    private WSHViewModel wshViewModel;
    private LGNViewModel lgnViewModel;
    private VehicleVO mainVehicle;

    private LayoutMapOverlayUiBottomSonaxBranchBinding sonaxBranchBinding;
    private List<WashBrnVO> searchedBranchList;
    private WashBrnVO pickedBranch;

    private String godsSeqNo;
    private String godsNm;
    private double[] myPosition = {360., 360.};//경도위도 무효값으로 초기화

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map2);

        //앞 단계에서 선택한 쿠폰 정보를 알 수 없으면 지점 검색을 할 수 없다
        if (!initDataFromIntent()) {
            finish();
            return;
        }

        setViewModel();
        setObserver();
        initView();
        initMainVehicle();
        reqMyLocation();
    }

    private boolean initDataFromIntent() {
        godsSeqNo = getIntent().getStringExtra(WSH_1002.GOODS_SEQ_NUM);
        godsNm = getIntent().getStringExtra(WSH_1002.GOODS_NAME);

        Log.d(TAG, "initDataFromIntent: " + godsSeqNo + ", " + godsNm);
        return godsSeqNo != null && godsNm != null;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void getDataFromIntent() {
    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        wshViewModel = new ViewModelProvider(this).get(WSHViewModel.class);
        lgnViewModel = new ViewModelProvider(this).get(LGNViewModel.class);
    }

    @Override
    public void setObserver() {
        //예약 신청
        wshViewModel.getRES_WSH_1003().observe(this, result -> {
            Log.d(TAG, "setObserver req reserve: " + result.status);

            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;

                case SUCCESS:
                    if (result.data != null && result.data.getRtCd() != null) {
                        showProgressDialog(false);

                        //예약 내역 액티비티 열기
                        startActivitySingleTop(new Intent(this, CarWashHistoryActivity.class), 0, VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                        finish();
                        return;
                    }
                    //not break; 데이터 이상하면 default로 진입시킴

                default:
                    showProgressDialog(false);
                    SnackBarUtil.show(this, getString(result.message));
                    //todo : 구체적인 예외처리
                    break;
            }
        });

    }

    @Override
    public void onBackPressed() {
        List<SubFragment> fragments = getFragments();
        if (fragments != null && fragments.size() > 0) {
            hideFragment(fragments.get(0));
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClickCommon(View v) {
        Log.d(TAG, "onClickCommon: ");

        switch (v.getId()) {

            //내 위치 버튼
            case R.id.btn_my_position:
                moveMapToInitPosition();
                break;

            //지역선택 프래그먼트 호출
            case R.id.tv_map_title_address:
                showFragment(new CarWashFindSonaxBranchFragment());
                break;

            //지점 사진
            case R.id.iv_map_sonax_branch_img:
                showFragment(new FragmentCarWashBranchPreview());
                break;

            //예약
            case R.id.tv_map_sonax_branch_reserve_btn:
                showReserveDialog();
                break;
        }
    }

    private void initView() {
        Log.d(TAG, "initView: ");
        ui.lMapOverlayTitle.tvMapTitleText.setVisibility(View.GONE);

        ui.lMapOverlayTitle.tvMapTitleAddress.setVisibility(View.VISIBLE);
        ui.lMapOverlayTitle.tvMapTitleAddress.setBackground(getDrawable(R.drawable.ripple_bg_ffffff_round_99_stroke_141414));
        ui.lMapOverlayTitle.setTitle(getString(R.string.sm_cw_find_01));

        ui.lMapOverlayTitle.tvMapTitleAddress.setOnClickListener(onSingleClickListener);
        ui.btnMyPosition.setOnClickListener(onSingleClickListener);

        setMarkerClickListener();
    }

    private void initMainVehicle() {
        try {
            mainVehicle = lgnViewModel.getMainVehicleSimplyFromDB();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            //TODO 차량 정보 접근 실패에 대한 예외처리
        }
    }

    //지도 마커 클릭 리스너
    private void setMarkerClickListener() {
        ui.pmvMapView.onMapTouchUpListener((motionEvent, makerList) -> {
            if (makerList != null && makerList.size() > 0) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //do nothing
                        break;

                    case MotionEvent.ACTION_UP:
                        WashBrnVO picked = findBranch(makerList.get(0).getId());
                        if (picked != null) {
                            showBranchInfo(picked, false);
                        }

                        break;

                    default:
                        break;
                }

            }
        });
    }

    //gps로 내 위치 찾아서 지도에 표시
    private void reqMyLocation() {
        Log.d(TAG, "reqMyLocation: ");

        showProgressDialog(true);
        findMyLocation(location -> {
            showProgressDialog(false);
            if (location == null) {
                exitPage("위치 정보를 불러올 수 없습니다. GPS 상태를 확인 후 다시 시도해 주세요.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
                return;
            }

            runOnUiThread(() -> {
                //내 위치 저장
                myPosition[X] = location.getLatitude();
                myPosition[Y] = location.getLongitude();

                //지도 초기화
                ui.pmvMapView.initMap(myPosition[X], myPosition[Y], DEFAULT_ZOOM);
            });

        }, 5000);
    }

    //초기 위치로 지도 이동
    private void moveMapToInitPosition() {
        ui.pmvMapView.setMapCenterPoint(new PlayMapPoint(myPosition[X], myPosition[Y]), 500);
    }

    //ViewStub을 inflate하고 지점 정보 세팅
    public void showBranchInfo(WashBrnVO branchData, boolean moveMapFocus) {
        Log.d(TAG, "setBranchData: ");
        if (searchedBranchList == null) {
            return;
        }

        //선택된 지점 정보를 저장. 예약 및 사진 미리보기에서 이용.
        pickedBranch = branchData;

        if (sonaxBranchBinding == null) {
            setViewStub(
                    R.id.vs_map_overlay_bottom_box,
                    R.layout.layout_map_overlay_ui_bottom_sonax_branch,
                    (viewStub, inflated) -> {
                        sonaxBranchBinding = DataBindingUtil.bind(inflated);
                        sonaxBranchBinding.setActivity(CarWashSearchActivity.this);
                        setBranchData(branchData, moveMapFocus);
                    });
        } else {
            setBranchData(branchData, moveMapFocus);
        }

        drawMarkerItem(branchData);
    }

    // 지점 정보 세팅
    private void setBranchData(WashBrnVO branchData, boolean moveMapFocus) {
        //지도 뷰를 해당 위치로 이동
        if (moveMapFocus) {
            ui.pmvMapView.initMap(Double.parseDouble(branchData.getBrnhX()), Double.parseDouble(branchData.getBrnhY()), ui.pmvMapView.getZoomLevel());
        }

        //지점 정보 뷰에 데이터 바인딩
        sonaxBranchBinding.setData(branchData);

        Glide.with(CarWashSearchActivity.this)
                .load(branchData.getBrnhImgUri1())
                .format(DecodeFormat.PREFER_ARGB_8888)
                .error(R.drawable.img_car_339_2) //todo 대체 이미지 필요
                .placeholder(R.drawable.img_car_339_2) //todo 에러시 대체 이미지 필요
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(sonaxBranchBinding.ivMapSonaxBranchImg);
    }

    // 지도에 마커를 그린다.
    public void drawMarkerItem(WashBrnVO pickedBranch) {
        //마커 초기화
        ui.pmvMapView.removeAllMarkerItem();

        //작은 마커 그리다가 선택된 놈 찾으면 걔만 큰 마커
        for (WashBrnVO branch : searchedBranchList) {
            PlayMapMarker markerItem = new PlayMapMarker();
            PlayMapPoint point = new PlayMapPoint(Double.parseDouble(branch.getBrnhX()), Double.parseDouble(branch.getBrnhY()));
            markerItem.setMapPoint(point);
            markerItem.setCanShowCallout(false);
            markerItem.setAutoCalloutVisible(false);
            markerItem.setIcon(
                    ((BitmapDrawable) getResources().getDrawable(
                            pickedBranch == branch ? R.drawable.ic_pin_wash : R.drawable.ic_pin,
                            null)).getBitmap()
            );

            String name = branch.getBrnhCd();
            ui.pmvMapView.addMarkerItem(name, markerItem);
        }
    }

    //예약 할래? 대화상자
    private void showReserveDialog() {
        if (pickedBranch == null) {
            SnackBarUtil.show(this, getString(R.string.cw_branch_no_data));
            return;
        }

        String msg = pickedBranch.getBrnhNm() + "\n" + godsNm + "\n" + getString(R.string.cw_reserve_msg);

        //예약 할래? 대화상자
        MiddleDialog.dialogCarWashReserve(this, this::reserveCarWash, msg);
    }

    //예약 요청 보냄
    private void reserveCarWash() {
        wshViewModel.reqWSH1003(
                new WSH_1003.Request(
                        APPIAInfo.SM_CW01_A01.getId(),
                        godsSeqNo,
                        WSHViewModel.SONAX,
                        pickedBranch.getBrnhCd(),
                        mainVehicle.getVin(),
                        mainVehicle.getCarRgstNo()
                ));
    }

    private WashBrnVO findBranch(String id) {
        for (WashBrnVO branch : searchedBranchList) {
            if (id.equals(branch.getBrnhCd())) {
                return branch;
            }
        }
        return null;
    }

    public String getGodsSeqNo() {
        return godsSeqNo;
    }

    public double[] getMyPosition() {
        return myPosition;
    }

    public WashBrnVO getPickedBranch() {
        return pickedBranch;
    }

    public void setBranchList(List<WashBrnVO> list) {
        searchedBranchList = list;
    }
}
