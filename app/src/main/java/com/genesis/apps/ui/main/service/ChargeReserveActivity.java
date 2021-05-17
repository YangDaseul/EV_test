package com.genesis.apps.ui.main.service;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.developers.ParkLocation;
import com.genesis.apps.comm.model.api.gra.STC_1001;
import com.genesis.apps.comm.model.constants.ChargeSearchCategorytype;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.AddressVO;
import com.genesis.apps.comm.model.vo.ChargeSearchCategoryVO;
import com.genesis.apps.comm.model.vo.ReserveVo;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.util.PackageUtil;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.viewmodel.DevelopersViewModel;
import com.genesis.apps.comm.viewmodel.LGNViewModel;
import com.genesis.apps.comm.viewmodel.REQViewModel;
import com.genesis.apps.comm.viewmodel.STCViewModel;
import com.genesis.apps.databinding.ActivityChargeReserveBinding;
import com.genesis.apps.ui.common.activity.GpsBaseActivity;
import com.genesis.apps.ui.main.ServiceNetworkActivity;
import com.genesis.apps.ui.main.service.view.ChargeSTCPlaceListAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.genesis.apps.comm.model.api.BaseResponse.RETURN_CODE_EMPTY;
import static com.genesis.apps.comm.model.api.BaseResponse.RETURN_CODE_SUCC;
import static com.genesis.apps.comm.viewmodel.DevelopersViewModel.CCSSTAT.STAT_AGREEMENT;

/**
 * Class Name : ChargeReserveActivity
 *
 * @author Ki-man Kim
 * @since 2021-05-14
 */
public class ChargeReserveActivity extends GpsBaseActivity<ActivityChargeReserveBinding> implements InputChargePlaceFragment.FilterChangeListener, SearchAddressHMNFragment.AddressSelectListener {
    private InputChargePlaceFragment inputChargePlaceFragment;

    private final ArrayList<ChargeSearchCategoryVO> selectedFilterList = new ArrayList<>();
    private ChargeSTCPlaceListAdapter adapter;

    private String reservYn = "Y";
    private String superSpeedYn;
    private String highSpeedYn;
    private String slowSpeedYn;

    private int pageNo = 1;
    private final String MAX_PAGE_CNT = "10";

    private VehicleVO mainVehicleVO;

    private DevelopersViewModel developersViewModel;
    private REQViewModel reqViewModel;
    private STCViewModel stcViewModel;
    private LGNViewModel lgnViewModel;

    /****************************************************************************************************
     * Override Method - LifeCycle
     ****************************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charge_reserve);
        getDataFromIntent();
        setViewModel();
        setObserver();
        initialize();
        checkEnableGPS(() -> {
            //현대양재사옥위치
            searchChargeStation(VariableType.DEFAULT_POSITION[0], VariableType.DEFAULT_POSITION[1]);
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
                PackageUtil.runApp(ChargeReserveActivity.this, PackageUtil.PACKAGE_CONNECTED_CAR);
                break;
            }
            case R.id.iv_arrow: {
                // 충전소 목록 아이템 - 충전소 상세 버튼 > 충전소 상세 화면 이동.
                if (tag instanceof ReserveVo) {
                    ReserveVo reserveVo = (ReserveVo) tag;
                    startActivitySingleTop(new Intent(ChargeReserveActivity.this, ChargeStationDetailActivity.class)
                                    .putExtra(KeyNames.KEY_NAME_CHARGE_STATION_CSID, reserveVo.getSid())
                                    .putExtra(KeyNames.KEY_NAME_LAT, reserveVo.getLat())
                                    .putExtra(KeyNames.KEY_NAME_LOT, reserveVo.getLot())
                                    .putExtra(KeyNames.KEY_STATION_TYPE, VariableType.CHARGE_STATION_TYPE_STC),
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
        ui.setLifecycleOwner(ChargeReserveActivity.this);
        ui.setActivity(ChargeReserveActivity.this);
        developersViewModel = new ViewModelProvider(ChargeReserveActivity.this).get(DevelopersViewModel.class);
        reqViewModel = new ViewModelProvider(ChargeReserveActivity.this).get(REQViewModel.class);
        stcViewModel = new ViewModelProvider(ChargeReserveActivity.this).get(STCViewModel.class);
        lgnViewModel = new ViewModelProvider(ChargeReserveActivity.this).get(LGNViewModel.class);
    }

    @Override
    public void setObserver() {
        developersViewModel.getRES_PARKLOCATION().observe(ChargeReserveActivity.this, result -> {
            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    if (result.data != null && result.data.getLat() != 0 && result.data.getLon() != 0) {
                        showProgressDialog(false);
                        searchChargeStation(result.data.getLat(), result.data.getLon());
                        reqMyLocation();
                        break;
                    }
                default:
                    showProgressDialog(false);
                    searchChargeStation(VariableType.DEFAULT_POSITION[0], VariableType.DEFAULT_POSITION[1]);
                    break;
            }
        });

        stcViewModel.getRES_STC_1001().observe(ChargeReserveActivity.this, result -> {
            switch (result.status) {
                case LOADING: {
                    showProgressDialog(true);
                    break;
                }
                case SUCCESS: {
                    showProgressDialog(false);
                    if (result.data != null && (RETURN_CODE_SUCC.equalsIgnoreCase(result.data.getRtCd()) || RETURN_CODE_EMPTY.equalsIgnoreCase(result.data.getRtCd()))) {
                        updateChargeList(result.data.getSearchList());
                        break;
                    }
                }
                default: {
                    String serverMsg = "";
                    try {
                        serverMsg = result.data.getRtMsg();
                        //기획 요청으로 검색 결과가 없습니다 로 에러메시지 통일
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        showProgressDialog(false);
                        SnackBarUtil.show(this, (TextUtils.isEmpty(serverMsg)) ? getString(R.string.r_flaw06_p02_snackbar_1) : serverMsg);
                        updateChargeList(new ArrayList<>());
                    }
                }
            }
        });
    }

    @Override
    public void getDataFromIntent() {

    }

    /****************************************************************************************************
     * Override Method - {@link InputChargePlaceFragment.FilterChangeListener}
     ****************************************************************************************************/
    @Override
    public void onFilterChanged(InputChargePlaceFragment.SEARCH_TYPE type, List<ChargeSearchCategoryVO> filterList) {
        if (type == InputChargePlaceFragment.SEARCH_TYPE.ADDRESS) {
            // 주소 검색은 별도 처리 없음.
        } else {
            // 나머지 내 위치, 내 차량 위치 기준 검색.
            selectedFilterList.clear();
            selectedFilterList.addAll(filterList);
            // 예약가능여부 값 초기화.
            reservYn = null;
            // 충전 속도 값 초기화
            superSpeedYn = null;
            highSpeedYn = null;
            slowSpeedYn = null;

            // 페이지 번호 초기화
            pageNo = 1;

            updateFilterValue(selectedFilterList);

            if (type == InputChargePlaceFragment.SEARCH_TYPE.MY_LOCATION) {
                // 내 위치 기준 충전소 검색.
                reqMyLocation();
            } else if (type == InputChargePlaceFragment.SEARCH_TYPE.MY_CAR) {
                reqParkLocationToDevelopers();
            }
        }
    }

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
        Intent intent = new Intent(this, ServiceNetworkActivity.class)
                .putExtra(KeyNames.KEY_NAME_PAGE_TYPE, ServiceNetworkActivity.PAGE_TYPE_EVCHARGE);
        intent.putParcelableArrayListExtra(KeyNames.KEY_NAME_FILTER_INFO, getFilterListBySetting());
        startActivitySingleTop(intent, RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
    }

    /****************************************************************************************************
     * Override Method - {@link SearchAddressHMNFragment.AddressSelectListener}
     ****************************************************************************************************/
    @Override
    public void onAddressSelected(AddressVO selectedAddr) {
        inputChargePlaceFragment.setAddress(selectedAddr);
        searchChargeStation(selectedAddr.getCenterLat(), selectedAddr.getCenterLon());
    }

    /****************************************************************************************************
     * Method - Private
     ****************************************************************************************************/
    private void initialize() {
        EvChargeStatusFragment evChargeStatusFragment = EvChargeStatusFragment.newInstance();
        inputChargePlaceFragment = InputChargePlaceFragment.newInstance(new ArrayList(Arrays.asList(
                new ChargeSearchCategoryVO(R.string.sm_evss01_15, ChargeSearchCategoryVO.COMPONENT_TYPE.ONLY_ONE, null).setSelected("Y".equalsIgnoreCase(reservYn)),
                new ChargeSearchCategoryVO(R.string.sm_evss01_22, ChargeSearchCategoryVO.COMPONENT_TYPE.ONLY_ONE, null).setSelected(true),
                new ChargeSearchCategoryVO(R.string.sm_evss01_23, ChargeSearchCategoryVO.COMPONENT_TYPE.ONLY_ONE, null).setSelected(true),
                new ChargeSearchCategoryVO(R.string.sm_evss01_24, ChargeSearchCategoryVO.COMPONENT_TYPE.ONLY_ONE, null).setSelected(true)
        )));
        inputChargePlaceFragment.setOnFilterChangedListener(ChargeReserveActivity.this);
        getSupportFragmentManager().beginTransaction()
                .add(ui.vgEvStatusConstainer.getId(), evChargeStatusFragment)
                .add(ui.vgInputChargePlace.getId(), inputChargePlaceFragment)
                .commitAllowingStateLoss();

        LinearLayoutManager layoutManager = new LinearLayoutManager(ChargeReserveActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        ui.rvSearchResult.setLayoutManager(layoutManager);

        adapter = new ChargeSTCPlaceListAdapter(ChargeReserveActivity.this);
        ui.rvSearchResult.setAdapter(adapter);

        try {
            mainVehicleVO = reqViewModel.getMainVehicle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void reqMyLocation() {
        showProgressDialog(true);
        findMyLocation(location -> {
            showProgressDialog(false);
            if (location == null) {
                searchChargeStation(VariableType.DEFAULT_POSITION[0], VariableType.DEFAULT_POSITION[1]);
                return;
            }
            runOnUiThread(() -> {
                searchChargeStation(location.getLatitude(), location.getLongitude());
            });

        }, 5000, GpsRetType.GPS_RETURN_FIRST, false);
    }

    private void searchChargeStation(double lat, double lot) {
        lgnViewModel.setMyPosition(lat, lot);

        if (lat == VariableType.DEFAULT_POSITION[0] && lot == VariableType.DEFAULT_POSITION[1]) {
            inputChargePlaceFragment.setGuideErrorMsg();
        }
        stcViewModel.reqSTC1001(new STC_1001.Request(
                APPIAInfo.SM_EVSB01.getId(),
                mainVehicleVO.getVin(),
                lat,
                lot,
                reservYn,
                superSpeedYn,
                highSpeedYn,
                slowSpeedYn,
                String.valueOf(pageNo),
                MAX_PAGE_CNT
        ));
    }

    private void updateChargeList(List<ReserveVo> list) {
        try {
            adapter.setRows(list);
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            setEmptyView();
        }
    }

    private void setEmptyView() {
        if (adapter == null || adapter.getItemCount() < 1) {
            ui.rvSearchResult.setVisibility(View.GONE);
            ui.tvEmpty.setVisibility(View.VISIBLE);
        } else {
            ui.rvSearchResult.setVisibility(View.VISIBLE);
            ui.tvEmpty.setVisibility(View.GONE);
        }
    }

    private void updateFilterValue(List<ChargeSearchCategoryVO> filterList) {
        // 설정된 필터값 적용.
        for (ChargeSearchCategoryVO item : filterList) {
            if (item.getTitleResId() == R.string.sm_evss01_15) {
                // 예약가능 충전소 필터
                reservYn = item.isSelected() ? "Y" : "N";
            } else if (item.getTitleResId() == R.string.sm_evss01_22) {
                // 초고속 충전소 필터
                superSpeedYn = item.isSelected() ? "Y" : "N";
            } else if (item.getTitleResId() == R.string.sm_evss01_23) {
                // 초고속 충전소 필터
                highSpeedYn = item.isSelected() ? "Y" : "N";
            } else if (item.getTitleResId() == R.string.sm_evss01_24) {
                // 초고속 충전소 필터
                slowSpeedYn = item.isSelected() ? "Y" : "N";
            }
        }
    }

    private ArrayList<ChargeSearchCategoryVO> getFilterListBySetting() {
        ArrayList<ChargeSearchCategoryVO> result = new ArrayList<>();
        result.add(new ChargeSearchCategoryVO(R.string.sm_evss01_15, ChargeSearchCategoryVO.COMPONENT_TYPE.ONLY_ONE, null)
                .setSelected("Y".equalsIgnoreCase(reservYn)));
        ChargeSearchCategoryVO speedFilter = new ChargeSearchCategoryVO(R.string.sm_evss01_21, ChargeSearchCategoryVO.COMPONENT_TYPE.CHECK,
                Arrays.asList(ChargeSearchCategorytype.SUPER_SPEED, ChargeSearchCategorytype.HIGH_SPEED, ChargeSearchCategorytype.SLOW_SPEED));
        ArrayList<ChargeSearchCategorytype> selectedSpeedFilterList = new ArrayList<>();
        if ("Y".equalsIgnoreCase(superSpeedYn)) {
            selectedSpeedFilterList.add(ChargeSearchCategorytype.SUPER_SPEED);
        }
        if ("Y".equalsIgnoreCase(highSpeedYn)) {
            selectedSpeedFilterList.add(ChargeSearchCategorytype.HIGH_SPEED);
        }
        if ("Y".equalsIgnoreCase(slowSpeedYn)) {
            selectedSpeedFilterList.add(ChargeSearchCategorytype.SLOW_SPEED);
        }
        if (selectedSpeedFilterList.size() > 0) {
            speedFilter.addSelectedItems(selectedSpeedFilterList);
        }
        result.add(speedFilter);
        return result;
    }

    private void reqParkLocationToDevelopers() {
        if (developersViewModel.checkCarInfoToDevelopers(mainVehicleVO.getVin(), "") == STAT_AGREEMENT) {
            developersViewModel.reqParkLocation(new ParkLocation.Request(developersViewModel.getCarId(mainVehicleVO.getVin())));
        } else {
            searchChargeStation(VariableType.DEFAULT_POSITION[0], VariableType.DEFAULT_POSITION[1]);
        }
    }
} // end of class ChargeReserveActivity
