package com.genesis.apps.ui.main.service;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.gra.api.WSH_1002;
import com.genesis.apps.comm.model.vo.WashBrnVO;
import com.genesis.apps.databinding.ActivityMap2Binding;
import com.genesis.apps.databinding.LayoutMapOverlayUiBottomSonaxBranchBinding;
import com.genesis.apps.ui.common.activity.GpsBaseActivity;
import com.genesis.apps.ui.common.fragment.SubFragment;
import com.hmns.playmap.PlayMapPoint;
import com.hmns.playmap.shape.PlayMapMarker;

import java.util.List;

public class CarWashSearchActivity extends GpsBaseActivity<ActivityMap2Binding> {
    private static final String TAG = CarWashSearchActivity.class.getSimpleName();
    public static final int X = 0;
    public static final int Y = 1;

    private static final int DEFAULT_ZOOM = 17;

    private LayoutMapOverlayUiBottomSonaxBranchBinding sonaxBranchBinding;
    private List<WashBrnVO> searchedBranchList;
    private WashBrnVO pickedBranch;

    private String godsSeqNo;
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
        reqMyLocation();
    }

    private boolean initDataFromIntent() {
        godsSeqNo = getIntent().getStringExtra(WSH_1002.GOODS_SEQ_NUM);

        Log.d(TAG, "initDataFromIntent: " + godsSeqNo);
        return godsSeqNo != null;
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
    }

    @Override
    public void setObserver() {
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
            //좌상단 백버튼
            case R.id.fab_map_back:
                super.onBackPressed();
                break;

            //내 위치 버튼
            case R.id.btn_my_position:
                moveMapToInitPosition();
                break;

            //지역선택 프래그먼트 호출
            case R.id.tv_map_title_text:
                showFragment(new CarWashFindSonaxBranchFragment());
                break;

            //지점 사진
            case R.id.iv_map_sonax_branch_img:
                showFragment(new FragmentCarWashBranchPreview(pickedBranch));
                break;

            case R.id.tv_map_sonax_branch_reserve_btn://예약
                //todo impl pickedBranch널검사 하고 이거 예약
                break;
        }
    }

    private void initView() {
        Log.d(TAG, "initView: ");

        ui.lMapOverlayTitle.tvMapTitleText.setText(R.string.sm_cw_find_01);
        ui.lMapOverlayTitle.tvMapTitleText.setTextAppearance(R.style.MapOverlayTitleBar_SearchSonax);
        ui.lMapOverlayTitle.tvMapTitleText.setBackground(getDrawable(R.drawable.ripple_bg_ffffff_round_99_stroke_141414));

        ui.lMapOverlayTitle.tvMapTitleText.setOnClickListener(onSingleClickListener);
        ui.lMapOverlayTitle.fabMapBack.setOnClickListener(onSingleClickListener);
        ui.btnMyPosition.setOnClickListener(onSingleClickListener);

        setMarkerClickListener();
    }

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

    /**
     * drawMarkerItem 지도에 마커를 그린다.
     *
     * @param pickedBranch
     */
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

    public String getGodsSeqNo() {
        return godsSeqNo;
    }

    public double[] getMyPosition() {
        return myPosition;
    }

    public void setBranchList(List<WashBrnVO> list) {
        searchedBranchList = list;
    }

    private WashBrnVO findBranch(String id) {
        for (WashBrnVO branch : searchedBranchList) {
            if (id.equals(branch.getBrnhCd())) {
                return branch;
            }
        }
        return null;
    }
}
