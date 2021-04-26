package com.genesis.apps.ui.main.service;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.WSH_1002;
import com.genesis.apps.comm.model.api.gra.WSH_1003;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.constants.VariableType;
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
    public static final int LATITUDE = 0;   //Y 소낙스만 xy 뒤집힌 모양이랬던가...LATITUDE
    public static final int LONGITUDE = 1;  //X LONGITUDE

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
        showNearestBranch();
    }

    private boolean initDataFromIntent() {
        godsSeqNo = getIntent().getStringExtra(KeyNames.KEY_NAME_WASH_GOODS_SEQ_NUM);
        godsNm = getIntent().getStringExtra(KeyNames.KEY_NAME_WASH_GOODS_NAME);

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
                        exitPage(new Intent(), ResultCodes.REQ_CODE_SERVICE_WASH_RESERVATION_FINISH.getCode());
                        //예약 내역 액티비티 열기
                        return;
                    }
                    //not break; 데이터 이상하면 default로 진입시킴

                default:
                    showProgressDialog(false);
                    String serverMsg = "";
                    try {
                        serverMsg = result.data.getRtMsg();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        SnackBarUtil.show(this, (TextUtils.isEmpty(serverMsg)) ? getString(R.string.r_flaw06_p02_snackbar_1) : serverMsg);
                    }
                    break;
            }
        });


        //지점 목록 옵저버
        wshViewModel.getRES_WSH_1002().observe(this, result -> {
            Log.d(TAG, "setObserver branchObs: " + result.status);

            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;

                case SUCCESS:
                    if (result.data != null && result.data.getBrnhList() != null) {
                        List<WashBrnVO> list = result.data.getBrnhList();

                        //최근거리 지점을 찾아서 정보 표시
                        showBranchInfo(findNearest(list), true);

                        //성공 후 데이터 로딩까지 다 되면 로딩 치우고 break;
                        showProgressDialog(false);
                        break;
                    }

                    //not break; 데이터 이상하면 default로 진입시킴

                default:
                    showProgressDialog(false);
                    String serverMsg = "";
                    try {
                        serverMsg = result.data.getRtMsg();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (TextUtils.isEmpty(serverMsg)) {
                            serverMsg = getString(R.string.instability_network);
                        }
                        SnackBarUtil.show(this, serverMsg);
                    }
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
            //전화걸기
            case R.id.tv_map_sonax_branch_phone:
                String tel="";
                try{
                    tel = ((TextView)v).getText().toString().replaceAll("-","");
                }catch (Exception e){
                    tel = "";
                }
                if(!TextUtils.isEmpty(tel)) startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(WebView.SCHEME_TEL + tel)));
                break;

            //내 위치 버튼
            case R.id.btn_my_position:
                //최근거리 지점을 찾아서 정보 표시
//                showBranchInfo(findNearest(searchedBranchList), true);
                reqBranchList();
                break;

            //지역선택 프래그먼트 호출
            case R.id.tv_map_title_address:
            case R.id.tv_map_title_text:
                showFragment(new CarWashFindSonaxBranchFragment());
                break;

            //지점 사진
            case R.id.iv_map_sonax_branch_img:
                if(!TextUtils.isEmpty(pickedBranch.getBrnhImgUri1()) || !TextUtils.isEmpty(pickedBranch.getBrnhImgUri2()) || !TextUtils.isEmpty(pickedBranch.getBrnhImgUri3())) showFragment(new FragmentCarWashBranchPreview());
                break;

            //예약
            case R.id.tv_map_sonax_branch_reserve_btn:
                showReserveDialog();
                break;
        }
    }

    private void initView() {
        Log.d(TAG, "initView: ");
        ui.lMapOverlayTitle.tvMapTitleText.setVisibility(View.VISIBLE);

//        ui.lMapOverlayTitle.tvMapTitleAddress.setVisibility(View.VISIBLE);
//        ui.lMapOverlayTitle.tvMapTitleAddress.setBackground(getDrawable(R.drawable.ripple_bg_ffffff));
//        ui.lMapOverlayTitle.setTitle(getString(R.string.sm_cw_find_01));

        ui.lMapOverlayTitle.tvMapTitleText.setOnClickListener(onSingleClickListener);
//        ui.lMapOverlayTitle.tvMapTitleAddress.setOnClickListener(onSingleClickListener);
        ui.btnMyPosition.setOnClickListener(onSingleClickListener);

        setMarkerClickListener();
    }

    private void initMainVehicle() {
        try {
            mainVehicle = lgnViewModel.getMainVehicleSimplyFromDB();
        } catch (ExecutionException e){
            e.printStackTrace();
        } catch (InterruptedException e) {
            Log.d(TAG, "InterruptedException");
            Thread.currentThread().interrupt();
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
    private void showNearestBranch() {
        Log.d(TAG, "showNearestBranch: ");

        showProgressDialog(true);
        findMyLocation(location -> {
            showProgressDialog(false);
            if (location == null) {
                exitPage("위치 정보를 불러올 수 없습니다. GPS 상태를 확인 후 다시 시도해 주세요.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
                return;
            }

            runOnUiThread(() -> {
                //내 위치 저장
                myPosition[LATITUDE] = location.getLatitude();
                myPosition[LONGITUDE] = location.getLongitude();

                //지도 초기화
                ui.pmvMapView.initMap(myPosition[LATITUDE], myPosition[LONGITUDE], DEFAULT_ZOOM_WIDE);

                //최근거리 지점을 기본값으로 보여주기 위해 지점 목록 정보를 요청
                reqBranchList();
            });

        }, 5000, GpsRetType.GPS_RETURN_FIRST, false);
    }

    //지점 목록 요청
    private void reqBranchList() {
        Log.d(TAG, "reqBranchList: ");

        wshViewModel.reqWSH1002(
                new WSH_1002.Request(
                        APPIAInfo.SM_CW01_A02.getId(),
                        godsSeqNo,
                        WSHViewModel.SONAX,
                        "" + myPosition[LONGITUDE],
                        "" + myPosition[LATITUDE],
                        ""));
    }

    //최근거리 지점 찾기
    private WashBrnVO findNearest(List<WashBrnVO> list) {
        //전체 목록을 저장(선택 안 된 지점도 지도에 표시하기 위함)
        searchedBranchList = list;

        double nearestDistance;
        double tempDistance;

        //목록 첫째를 기준으로 두고
        WashBrnVO nearest = list.get(0);

        //목록 전수조사
        for (WashBrnVO cmp : list) {
            nearestDistance = getDistance(nearest);
            tempDistance = getDistance(cmp);

            //더 가까운 지점이 발견되면 기준을 교체
            if (tempDistance < nearestDistance) {
                nearest = cmp;
            }
        }

        return nearest;
    }

    //대상 지점과 현재 위치의 거리를 계산
    private double getDistance(WashBrnVO cmp) {
        double dx = myPosition[LONGITUDE] - Double.parseDouble(cmp.getBrnhX());
        double dy = myPosition[LATITUDE] - Double.parseDouble(cmp.getBrnhY());

        return Math.sqrt(dx * dx + dy * dy);
    }

    //초기 위치로 지도 이동
    private void moveMapToInitPosition() {
        ui.pmvMapView.setMapCenterPoint(new PlayMapPoint(myPosition[LATITUDE], myPosition[LONGITUDE]), 500);
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
            ui.pmvMapView.initMap(
                    Double.parseDouble(branchData.getBrnhY()),
                    Double.parseDouble(branchData.getBrnhX()),
                    DEFAULT_ZOOM_WIDE);
        }

        //지점 정보 뷰에 데이터 바인딩
        sonaxBranchBinding.setData(branchData);

        //차량 소유자에게만 예약버튼 해금
        sonaxBranchBinding.setReserveEnable(isVehicleOwner());

        Glide.with(CarWashSearchActivity.this)
                .load(branchData.getBrnhImgUri1())
                .format(DecodeFormat.PREFER_RGB_565)
                .error(R.drawable.img_sonax)
                .placeholder(R.drawable.img_sonax)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(sonaxBranchBinding.ivMapSonaxBranchImg);
    }

    private boolean isVehicleOwner() {
        try {
            return lgnViewModel.getUserInfoFromDB()
                    .getCustGbCd()
                    .equals(VariableType.MAIN_VEHICLE_TYPE_OV);
        } catch (ExecutionException e){
            e.printStackTrace();
        } catch (InterruptedException e) {
            Log.d(TAG, "InterruptedException");
            Thread.currentThread().interrupt();
        }

        return false;
    }

    // 지도에 마커를 그린다.
    public void drawMarkerItem(WashBrnVO pickedBranch) {
        //마커 초기화
        ui.pmvMapView.removeAllMarkerItem();

        //작은 마커 그리다가 선택된 놈 찾으면 걔만 큰 마커
        for (WashBrnVO branch : searchedBranchList) {
            PlayMapMarker markerItem = new PlayMapMarker();
            PlayMapPoint point = new PlayMapPoint(Double.parseDouble(branch.getBrnhY()), Double.parseDouble(branch.getBrnhX()));
            markerItem.setMapPoint(point);
            markerItem.setCanShowCallout(false);
            markerItem.setAutoCalloutVisible(false);
            markerItem.setIcon(
                    ((BitmapDrawable) getResources().getDrawable(
                            pickedBranch == branch ? R.drawable.ico_map_pin_active_b : R.drawable.ic_pin,
                            null)).getBitmap()
            );

            String name = branch.getBrnhCd();
            ui.pmvMapView.addMarkerItem(name, markerItem);
        }
    }

    private void showReserveDialog() {
        if (pickedBranch == null) {
            SnackBarUtil.show(this, getString(R.string.cw_branch_no_data));
            return;
        }

        MiddleDialog.dialogCarWashReserve(this, this::reserveCarWash, () -> {
        }, pickedBranch.getBrnhNm(), mainVehicle.getMdlNm());
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
