package com.genesis.apps.ui.main.service;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.EPT_1001;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.ChargeEptInfoVO;
import com.genesis.apps.comm.model.vo.ChargeSearchCategoryVO;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.viewmodel.EPTViewModel;
import com.genesis.apps.comm.viewmodel.REQViewModel;
import com.genesis.apps.databinding.ActivityChargeFindBinding;
import com.genesis.apps.ui.common.activity.GpsBaseActivity;
import com.genesis.apps.ui.main.ServiceNetworkActivity;
import com.genesis.apps.ui.main.service.view.ChargePlaceListAdapter;

import java.util.List;

/**
 * Class Name : ChargeSearchActivity
 * 충전소 검색 Activity.
 *
 * @author Ki-man Kim
 * @since 2021-03-22
 */
public class ChargeFindActivity extends GpsBaseActivity<ActivityChargeFindBinding> implements InputChargePlaceFragment.FilterChangeListener {
    private EvChargeStatusFragment evChargeStatusFragment;
    private InputChargePlaceFragment inputChargePlaceFragment;

    private ChargePlaceListAdapter adapter;

    private VehicleVO mainVehicleVO;

    private REQViewModel reqViewModel;
    private EPTViewModel eptViewModel;

    /****************************************************************************************************
     * Override Method - LifeCycle
     ****************************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charge_find);
        getDataFromIntent();
        setViewModel();
        setObserver();
        initialize();
    }

    /****************************************************************************************************
     * Override Method - Event
     ****************************************************************************************************/
    @Override
    public void onClickCommon(View v) {
        Object tag = v.getTag();
        switch (v.getId()) {
            case R.id.tv_btn_route_detail: {
                // TODO 충전소 목록 아이템 - 상세 경로 보기 버튼 > 제네시스 커넥티드 앱 호출
                Log.d("FID", "test :: onClickCommon :: tv_brn_route_detail :: tag=" + tag);
                break;
            }
            case R.id.iv_arrow: {
                // TODO 충전소 목록 아이템 - 충전소 상세 버튼 > 충전소 상세 화면 이동.
                Log.d("FID", "test :: onClickCommon :: iv_arrow :: tag=" + tag);
                break;
            }
        }
    }

    /****************************************************************************************************
     * Override Method
     ****************************************************************************************************/
    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(ChargeFindActivity.this);
        ui.setActivity(ChargeFindActivity.this);
        reqViewModel = new ViewModelProvider(ChargeFindActivity.this).get(REQViewModel.class);
        eptViewModel = new ViewModelProvider(ChargeFindActivity.this).get(EPTViewModel.class);
    }

    @Override
    public void setObserver() {
        eptViewModel.getRES_EPT_1001().observe(ChargeFindActivity.this, result -> {
            Log.d("FID", "test :: RES_EPT_1001 :: result.status=" + result.status);
            switch (result.status) {
                case LOADING: {
                    showProgressDialog(true);
                    break;
                }
                case SUCCESS: {
                    showProgressDialog(false);
                    Log.d("FID", "test :: 1111=" + result.data);
                    if (result.data != null) {
                        updateChargeList(result.data.getChgList());
                    }
                    break;
                }
                default: {
                    String serverMsg = "";
                    try {
                        serverMsg = getString(R.string.snackbar_etc_3);
                        //기획 요청으로 검색 결과가 없습니다 로 에러메시지 통일
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        showProgressDialog(false);
                        SnackBarUtil.show(this, (TextUtils.isEmpty(serverMsg)) ? getString(R.string.r_flaw06_p02_snackbar_1) : serverMsg);
                    }
                }
            }
        });

    }

    @Override
    public void getDataFromIntent() {
        // TODO 차량 정보를 가져와야 할지 체크 및 코드 추가 필요.

    }

    @Override
    public void onFilterChanged(InputChargePlaceFragment.SEARCH_TYPE type, List<ChargeSearchCategoryVO> filterList) {
        Log.d("FID", "test :: onFilterChanged :: filterList=" + filterList);
    }

    @Override
    public void onSearchAddress() {
        // TODO 주소 검색 이동.
    }

    @Override
    public void onSearchMap() {
        // TODO 지도 이동.
        // 충전소 찾기 지도 표시.
        startActivitySingleTop(new Intent(this, ServiceNetworkActivity.class)
                        .putExtra(KeyNames.KEY_NAME_PAGE_TYPE, ServiceNetworkActivity.PAGE_TYPE_EVCHARGE),
                RequestCodes.REQ_CODE_ACTIVITY.getCode(),
                VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
    }

    /****************************************************************************************************
     * Method - Private
     ****************************************************************************************************/
    private void initialize() {
        evChargeStatusFragment = EvChargeStatusFragment.newInstance();
        inputChargePlaceFragment = InputChargePlaceFragment.newInstance();
        inputChargePlaceFragment.setOnFilterChangedListener(ChargeFindActivity.this);
        getSupportFragmentManager().beginTransaction()
                .add(ui.vgEvStatusConstainer.getId(), evChargeStatusFragment)
                .add(ui.vgInputChargePlace.getId(), inputChargePlaceFragment)
                .commitAllowingStateLoss();

        LinearLayoutManager layoutManager = new LinearLayoutManager(ChargeFindActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        ui.rvSearchResult.setLayoutManager(layoutManager);

//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(ChargeFindActivity.this, layoutManager.getOrientation());
//        dividerItemDecoration.setDrawable(new ColorDrawable(getColor(R.color.x_e5e5e5)));
//        ui.rvSearchResult.addItemDecoration(dividerItemDecoration);

        adapter = new ChargePlaceListAdapter(ChargeFindActivity.this);
        ui.rvSearchResult.setAdapter(adapter);

        try {
            mainVehicleVO = reqViewModel.getMainVehicle();
        } catch (Exception e) {
            e.printStackTrace();
        }
        reqMyLocation();
    }

    private void reqMyLocation() {
        Log.d("FID", "test :: reqMyLocation");
        showProgressDialog(true);
        findMyLocation(location -> {
            showProgressDialog(false);
            if (location == null) {
                exitPage("위치 정보를 불러올 수 없습니다. GPS 상태를 확인 후 다시 시도해 주세요.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
                return;
            }
            Log.d("FID", "test :: findMyLocation :: location=" + location);
            runOnUiThread(() -> {
                searchChargeStation(location.getLatitude(), location.getLongitude());
            });

        }, 5000, GpsRetType.GPS_RETURN_FIRST, false);
    }

    private void searchChargeStation(double lat, double lot) {
        eptViewModel.reqEPT1001(new EPT_1001.Request(
                APPIAInfo.SM_EVSS01.getId(),
                mainVehicleVO.getVin(),
                String.valueOf(lat),
                String.valueOf(lot),
                null,
                null,
                null,
                null,
                null
        ));
    }

    private void updateChargeList(List<ChargeEptInfoVO> list) {
        adapter.setRows(list);
        adapter.notifyDataSetChanged();
    }
} // end of class ChargeSearchActivity
