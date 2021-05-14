package com.genesis.apps.ui.main.service;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.STC_1001;
import com.genesis.apps.comm.model.constants.ChargeSearchCategorytype;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.ChargeSearchCategoryVO;
import com.genesis.apps.comm.model.vo.ReserveVo;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.viewmodel.DevelopersViewModel;
import com.genesis.apps.comm.viewmodel.LGNViewModel;
import com.genesis.apps.comm.viewmodel.REQViewModel;
import com.genesis.apps.comm.viewmodel.STCViewModel;
import com.genesis.apps.databinding.ActivityChargeReserveBinding;
import com.genesis.apps.ui.common.activity.GpsBaseActivity;
import com.genesis.apps.ui.main.service.view.ChargePlaceListAdapter;
import com.genesis.apps.ui.main.service.view.ChargeSTCPlaceListAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.genesis.apps.comm.model.api.BaseResponse.RETURN_CODE_EMPTY;
import static com.genesis.apps.comm.model.api.BaseResponse.RETURN_CODE_SUCC;

/**
 * Class Name : ChargeReserveActivity
 *
 * @author Ki-man Kim
 * @since 2021-05-14
 */
public class ChargeReserveActivity extends GpsBaseActivity<ActivityChargeReserveBinding> implements InputChargePlaceFragment.FilterChangeListener {
    private InputChargePlaceFragment inputChargePlaceFragment;

    private final ArrayList<ChargeSearchCategoryVO> selectedFilterList = new ArrayList<>();
    private ChargeSTCPlaceListAdapter adapter;

    private String reservYn;
    private String chgCd;
    private ArrayList<String> chgSpeedList = new ArrayList<>();
    private ArrayList<String> payTypeList = new ArrayList<>();

    private int pageNo;
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
            // 충전소 구분 코드 초기화.
            chgCd = null;
            // 충전 속도 값 초기화
            chgSpeedList.clear();
            // 결제 방식 값 초기화.
            payTypeList.clear();

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

    }

    @Override
    public void onSearchMap() {

    }

    /****************************************************************************************************
     * Method - Private
     ****************************************************************************************************/
    private void initialize() {
        EvChargeStatusFragment evChargeStatusFragment = EvChargeStatusFragment.newInstance();
        inputChargePlaceFragment = InputChargePlaceFragment.newInstance();
        inputChargePlaceFragment.setOnFilterChangedListener(ChargeReserveActivity.this);
        selectedFilterList.add(inputChargePlaceFragment.getDefaultCategoryReserveYN());
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
//        String chgSpeed = null;
//        String payType = null;
//        if (chgSpeedList.size() > 0) {
//            chgSpeed = chgSpeedList.stream().map(it -> "\"" + it + "\"").collect(Collectors.joining(",", "[", "]"));
//            chgSpeed = chgSpeed.replace("\\","");
//        }
//        if (payTypeList.size() > 0) {
//            payType = payTypeList.stream().map(it -> "\"" + it + "\"").collect(Collectors.joining(",", "[", "]"));
//        }
        if (lat == VariableType.DEFAULT_POSITION[0] && lot == VariableType.DEFAULT_POSITION[1]) {
            inputChargePlaceFragment.setGuideErrorMsg();
        }
        stcViewModel.reqSTC1001(new STC_1001.Request(
                APPIAInfo.SM_EVSB01.getId(),
                mainVehicleVO.getVin(),
                lat,
                lot,
                reservYn,
                chgSpeedList.contains(ChargeSearchCategorytype.SUPER_SPEED.getCode()) ? "Y" : null,
                chgSpeedList.contains(ChargeSearchCategorytype.HIGH_SPEED.getCode()) ? "Y": null,
                chgSpeedList.contains(ChargeSearchCategorytype.SLOW_SPEED.getCode()) ? "Y" : null,
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
            } else if (item.getTitleResId() == R.string.sm_evss01_16) {
                // 충전소 종류 필터
                if (item.getSelectedItem().size() > 0) {
                    ChargeSearchCategorytype chargeStation = item.getSelectedItem().get(0);
                    switch (chargeStation) {
                        case GENESIS: // 제네시스 충전소
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
    }
} // end of class ChargeReserveActivity
