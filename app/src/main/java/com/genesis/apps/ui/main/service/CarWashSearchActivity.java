package com.genesis.apps.ui.main.service;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.gra.api.WSH_1002;
import com.genesis.apps.comm.model.vo.WashBrnVO;
import com.genesis.apps.databinding.ActivityMap2Binding;
import com.genesis.apps.databinding.LayoutMapOverlayUiBottomSonaxBranchBinding;
import com.genesis.apps.ui.common.activity.GpsBaseActivity;
import com.hmns.playmap.PlayMapPoint;
import com.hmns.playmap.shape.PlayMapMarker;

import java.util.ArrayList;
import java.util.List;

public class CarWashSearchActivity extends GpsBaseActivity<ActivityMap2Binding> {
    private static final String TAG = CarWashSearchActivity.class.getSimpleName();
    private static final int X = 0;
    private static final int Y = 1;

    private static final int DEFAULT_ZOOM = 17;

    private LayoutMapOverlayUiBottomSonaxBranchBinding sonaxBranchBinding;
    private List<WashBrnVO> searchedBranchList;
    private int focusedBranch;

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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }

        //현재 타 액티비티 호출은 지역별 검색으로 지점 선택해서 가져오는 것 뿐
        //검색된 지점 목록, 선택된 지점의 index를 저장하고 화면에 표시
        searchedBranchList = (ArrayList<WashBrnVO>) data.getSerializableExtra(WSH_1002.BRANCH_LIST);
        focusedBranch = data.getIntExtra(WSH_1002.BRANCH_INDEX, 0);
        showBranchInfoLayout();
    }

    @Override
    public void onClickCommon(View v) {
        Log.d(TAG, "onClickCommon: ");

        switch (v.getId()) {
            //좌상단 백버튼
            case R.id.fab_map_back:
                super.onBackPressed();
                break;

            //내 위치 찾기
            case R.id.btn_my_position:
                reqMyLocation();
                break;

            //지역선택 액티비티 호출
            case R.id.tv_map_title_text:
                //선택한 쿠폰 정보, 현재 고객 위치 가져가기
                Intent intent = new Intent(this, CarWashFindSonaxBranchActivity.class);
                intent.putExtra(WSH_1002.GOODS_SEQ_NUM, godsSeqNo);
                intent.putExtra(WSH_1002.CUST_X, myPosition[X]);
                intent.putExtra(WSH_1002.CUST_Y, myPosition[Y]);

                startActivitySingleTop(intent, RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                break;

            case R.id.iv_map_sonax_branch_img:

                break;

            case R.id.tv_map_sonax_branch_reserve_btn://예약
                //todo impl
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
        ui.pmvMapView.onMapTouchUpListener((motionEvent, makerList) -> {

            if (makerList != null && makerList.size() > 0) {

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_UP:
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

                //지도를 내 위치로 초기화
                initMapWithMyPosition();
            });

        }, 5000);
    }

    //지도 초기화(현재 위치)
    private void initMapWithMyPosition() {
        ui.pmvMapView.initMap(myPosition[X], myPosition[Y], DEFAULT_ZOOM);
    }

    //ViewStub을 inflate하고 지점 정보 세팅
    private void showBranchInfoLayout() {
        Log.d(TAG, "setBranchData: ");
        if (searchedBranchList == null) {
            return;
        }

        if (sonaxBranchBinding == null) {
            setViewStub(
                    R.id.vs_map_overlay_bottom_box,
                    R.layout.layout_map_overlay_ui_bottom_sonax_branch,
                    (viewStub, inflated) -> {
                        sonaxBranchBinding = DataBindingUtil.bind(inflated);
                        sonaxBranchBinding.setActivity(CarWashSearchActivity.this);
                        setBranchData(searchedBranchList.get(focusedBranch));
                    });
        } else {
            setBranchData(searchedBranchList.get(focusedBranch));
        }

        drawMarkerItem();
    }

    // 지점 정보 세팅
    private void setBranchData(WashBrnVO branchData) {
        //지도 뷰를 해당 위치로 이동
        ui.pmvMapView.initMap(Double.parseDouble(branchData.getBrnhX()), Double.parseDouble(branchData.getBrnhY()), DEFAULT_ZOOM);

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
     */
    public void drawMarkerItem() {
        for (int i = 0; i < searchedBranchList.size(); ++i) {
            WashBrnVO brnVO = searchedBranchList.get(i);
            PlayMapMarker markerItem = new PlayMapMarker();
            PlayMapPoint point = new PlayMapPoint(Double.parseDouble(brnVO.getBrnhX()), Double.parseDouble(brnVO.getBrnhY()));
            markerItem.setMapPoint(point);
        markerItem.set
            markerItem.setCanShowCallout(false);
            markerItem.setAutoCalloutVisible(false);
            markerItem.setIcon(
                    ((BitmapDrawable) getResources().getDrawable(
                            i == focusedBranch ? R.drawable.ic_pin_wash : R.drawable.ic_pin,
                            null)).getBitmap()
            );

            String name = brnVO.getBrnhNm();
            ui.pmvMapView.addMarkerItem(name, markerItem);
        }

    }
}
