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
import com.genesis.apps.comm.model.constants.ChargeSearchCategorytype;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.AddressVO;
import com.genesis.apps.comm.model.vo.ChargeEptInfoVO;
import com.genesis.apps.comm.model.vo.ChargeSearchCategoryVO;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.util.PackageUtil;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.viewmodel.DevelopersViewModel;
import com.genesis.apps.comm.viewmodel.EPTViewModel;
import com.genesis.apps.comm.viewmodel.LGNViewModel;
import com.genesis.apps.comm.viewmodel.REQViewModel;
import com.genesis.apps.databinding.ActivityChargeFindBinding;
import com.genesis.apps.ui.common.activity.GpsBaseActivity;
import com.genesis.apps.ui.main.ServiceNetworkActivity;
import com.genesis.apps.ui.main.service.view.ChargePlaceListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * Class Name : ChargeSearchActivity
 * 충전소 검색 Activity.
 *
 * @author Ki-man Kim
 * @since 2021-03-22
 */
@AndroidEntryPoint
public class ChargeFindActivity extends GpsBaseActivity<ActivityChargeFindBinding> implements InputChargePlaceFragment.FilterChangeListener, SearchAddressHMNFragment.AddressSelectListener {
    private EvChargeStatusFragment evChargeStatusFragment;
    private InputChargePlaceFragment inputChargePlaceFragment;

    private ChargePlaceListAdapter adapter;

    private String reservYn;
    private String chgCd;
    private ArrayList<String> chgSpeedList = new ArrayList<>();
    private ArrayList<String> payTypeList = new ArrayList<>();

    private VehicleVO mainVehicleVO;

    private DevelopersViewModel developersViewModel;
    private REQViewModel reqViewModel;
    private EPTViewModel eptViewModel;
    private LGNViewModel lgnViewModel;

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
        checkEnableGPS(() -> {
            //현대양재사옥위치
            searchChargeStation(37.463936, 127.042953); // FIXME 고정된 위치값이 여러 곳에 사용중이어서 이 값은 한 곳에서 관리 할 수 있게 처리가 필요.
        }, this::reqMyLocation);
    }

    /****************************************************************************************************
     * Override Method - Event
     ****************************************************************************************************/
    @Override
    public void onClickCommon(View v) {
        Object tag = v.getTag();
        switch (v.getId()) {
            case R.id.tv_btn_route_detail: {
                // 충전소 목록 아이템 - 상세 경로 보기 버튼 > 제네시스 커넥티드 앱 호출
                PackageUtil.runApp(ChargeFindActivity.this, PackageUtil.PACKAGE_CONNECTED_CAR);
                break;
            }
            case R.id.iv_arrow: {
                // 충전소 목록 아이템 - 충전소 상세 버튼 > 충전소 상세 화면 이동.
                if (tag instanceof ChargeEptInfoVO) {
                    ChargeEptInfoVO chargeEptInfoVO = (ChargeEptInfoVO) tag;
                    startActivitySingleTop(new Intent(ChargeFindActivity.this, ChargeStationDetailActivity.class)
                                    .putExtra(KeyNames.KEY_NAME_CHARGE_STATION_SPID, chargeEptInfoVO.getSpid())
                                    .putExtra(KeyNames.KEY_NAME_CHARGE_STATION_CSID, chargeEptInfoVO.getCsid())
                                    .putExtra(KeyNames.KEY_NAME_LAT, chargeEptInfoVO.getLat())
                                    .putExtra(KeyNames.KEY_NAME_LOT, chargeEptInfoVO.getLot()),
                            RequestCodes.REQ_CODE_ACTIVITY.getCode(),
                            VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                }
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
        developersViewModel = new ViewModelProvider(ChargeFindActivity.this).get(DevelopersViewModel.class);
        reqViewModel = new ViewModelProvider(ChargeFindActivity.this).get(REQViewModel.class);
        eptViewModel = new ViewModelProvider(ChargeFindActivity.this).get(EPTViewModel.class);
        lgnViewModel = new ViewModelProvider(ChargeFindActivity.this).get(LGNViewModel.class);
    }

    @Override
    public void setObserver() {
        eptViewModel.getRES_EPT_1001().observe(ChargeFindActivity.this, result -> {
            switch (result.status) {
                case LOADING: {
                    showProgressDialog(true);
                    break;
                }
                case SUCCESS: {
                    showProgressDialog(false);
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
    }

    @Override
    public void onFilterChanged(InputChargePlaceFragment.SEARCH_TYPE type, List<ChargeSearchCategoryVO> filterList) {
        if (type == InputChargePlaceFragment.SEARCH_TYPE.ADDRESS) {
            // 주소 검색은 별도 처리 없음.
        } else {
            // 나머지 내 위치, 내 차량 위치 기준 검색.

            // 예약가능여부 값 초기화.
            reservYn = null;
            // 충전소 구분 코드 초기화.
            chgCd = null;
            // 충전 속도 값 초기화
            chgSpeedList.clear();
            // 결제 방식 값 초기화.
            payTypeList.clear();

            // 설정된 필터값 적용.
            for (ChargeSearchCategoryVO item : filterList) {
                if (item.getTitleResId() == R.string.sm_evss01_15) {
                    // 예약가능 충전소 필터
                    reservYn = item.isSelected() ? "Y" : "N";
                } else if (item.getTitleResId() == R.string.sm_evss01_16) {
                    // 충전소 종류 필터
                    if (item.getSelectedItem().size() > 0) {
                        ChargeSearchCategorytype chargeStation = item.getSelectedItem().get(0);
                        switch (chargeStation) {
                            case GENESIS: // 제네시스 전용 충전소
                            case E_PIT: {
                                // 관련 충전소 종류 코드 설정.
                                chgCd = chargeStation.getCode();
                                break;
                            }
                            case ALL:
                            case HI_CHARGER:
                            default: {
                                // 관련 코드가 없어 전체 조회하는 것으로 처리.
                                chgCd = null;
                                break;
                            }
                        }
                    }
                } else {
                    // 충전 속도, 결제 방식 필터.
                    // 필터별 필터 코드 적용.
                    for (ChargeSearchCategorytype filterItem : item.getSelectedItem()) {
                        Log.d("FID", "test :: 1111 :: item=" + filterItem);
                        switch (filterItem) {
                            case SUPER_SPEED:
                            case HIGH_SPEED:
                            case SLOW_SPEED: {
                                chgSpeedList.add(filterItem.getCode());
                                break;
                            }
                            case CAR_PAY:
                            case S_TRAFFIC_CRADIT_PAY: {
                                payTypeList.add(filterItem.getCode());
                            }
                            default: {
                                // Nothing
                                break;
                            }
                        }
                    }
                }
            }
            if (type == InputChargePlaceFragment.SEARCH_TYPE.MY_LOCATION) {
                // 내 위치 기준 충전소 검색.
                reqMyLocation();
            } else if (type == InputChargePlaceFragment.SEARCH_TYPE.MY_CAR) {
                // TODO 내 차량 위치 기준 충전소 검색
            }
        }
    }

    /****************************************************************************************************
     * Override Method - {@link InputChargePlaceFragment.FilterChangeListener}
     ****************************************************************************************************/
    @Override
    public void onSearchAddress() {
        // 주소 검색
        Bundle bundle = new Bundle();
        bundle.putInt(KeyNames.KEY_NAME_MAP_SEARCH_TITLE_ID, R.string.sm_evss01_34);
        bundle.putInt(KeyNames.KEY_NAME_MAP_SEARCH_MSG_ID, R.string.sm_evss01_35);
        SearchAddressHMNFragment fragment = new SearchAddressHMNFragment();
        fragment.setAddressSelectListener(this);
        showFragment(fragment, bundle);
    }

    @Override
    public void onSearchMap() {
        // 충전소 찾기 지도 표시.
        startActivitySingleTop(new Intent(this, ServiceNetworkActivity.class)
                        .putExtra(KeyNames.KEY_NAME_PAGE_TYPE, ServiceNetworkActivity.PAGE_TYPE_EVCHARGE),
                RequestCodes.REQ_CODE_ACTIVITY.getCode(),
                VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
    }

    /****************************************************************************************************
     * Override Method - {@link SearchAddressHMNFragment.AddressSelectListener}
     ****************************************************************************************************/
    @Override
    public void onAddressSelected(AddressVO selectedAddr) {
        inputChargePlaceFragment.setAddress(getAddress(selectedAddr));
        searchChargeStation(selectedAddr.getCenterLat(), selectedAddr.getCenterLon());
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
        showProgressDialog(true);
        findMyLocation(location -> {
            showProgressDialog(false);
            if (location == null) {
                exitPage("위치 정보를 불러올 수 없습니다. GPS 상태를 확인 후 다시 시도해 주세요.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
                return;
            }
            runOnUiThread(() -> {
                searchChargeStation(location.getLatitude(), location.getLongitude());
            });

        }, 5000, GpsRetType.GPS_RETURN_FIRST, false);
    }

    private void searchChargeStation(double lat, double lot) {
        lgnViewModel.setMyPosition(lat, lot);
        String chgSpeed = null;
        String payType = null;
        if (chgSpeedList.size() > 0) {
            chgSpeed = chgSpeedList.stream().map(it -> "\"" + it + "\"").collect(Collectors.joining(",", "[", "]"));
        }
        if (payTypeList.size() > 0) {
            payType = payTypeList.stream().map(it -> "\"" + it + "\"").collect(Collectors.joining(",", "[", "]"));
        }

        eptViewModel.reqEPT1001(new EPT_1001.Request(
                APPIAInfo.SM_EVSS01.getId(),
                mainVehicleVO.getVin(),
                String.valueOf(lat),
                String.valueOf(lot),
                reservYn,
                chgCd,
                chgSpeed,
                payType
        ));
    }

    private void updateChargeList(List<ChargeEptInfoVO> list) {
        adapter.setRows(list);
        adapter.notifyDataSetChanged();
    }
} // end of class ChargeSearchActivity
