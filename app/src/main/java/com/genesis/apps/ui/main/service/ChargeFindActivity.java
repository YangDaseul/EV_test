package com.genesis.apps.ui.main.service;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.developers.ParkLocation;
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
import com.genesis.apps.ui.common.fragment.SubFragment;
import com.genesis.apps.ui.main.ServiceNetworkActivity;
import com.genesis.apps.ui.main.service.view.ChargePlaceListAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import dagger.hilt.android.AndroidEntryPoint;

import static com.genesis.apps.comm.model.api.BaseResponse.RETURN_CODE_EMPTY;
import static com.genesis.apps.comm.model.api.BaseResponse.RETURN_CODE_SUCC;
import static com.genesis.apps.comm.viewmodel.DevelopersViewModel.CCSSTAT.STAT_AGREEMENT;

/**
 * Class Name : ChargeSearchActivity
 * 충전소 검색 Activity.
 *
 * @author Ki-man Kim
 * @since 2021-03-22
 */
@AndroidEntryPoint
public class ChargeFindActivity extends GpsBaseActivity<ActivityChargeFindBinding> implements InputChargePlaceFragment.FilterChangeListener, SearchAddressHMNFragment.AddressSelectListener {
    private InputChargePlaceFragment inputChargePlaceFragment;

    private ChargePlaceListAdapter adapter;

    private String reservYn=VariableType.COMMON_MEANS_YES;
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
            searchChargeStation(VariableType.DEFAULT_POSITION[0], VariableType.DEFAULT_POSITION[1]);
        }, this::reqMyLocation);
    }

    /****************************************************************************************************
     * Override Method - Event
     ****************************************************************************************************/
    @Override
    public void onClickCommon(View v) {
        Object tag = v.getTag(R.id.item);
        switch (v.getId()) {
            case R.id.tv_btn_route_detail: {
                // 충전소 목록 아이템 - 상세 경로 보기 버튼 > 제네시스 커넥티드 앱 호출
                if (tag instanceof ChargeEptInfoVO) {
                    ChargeEptInfoVO item = (ChargeEptInfoVO) tag;
                    if (item != null && !TextUtils.isEmpty(item.getLat()) && !TextUtils.isEmpty(item.getLot())) {
                        PackageUtil.runAppWithScheme(ChargeFindActivity.this, PackageUtil.PACKAGE_CONNECTED_CAR, VariableType.getGCSScheme(item.getLat(), item.getLot()));
                    }
                }
                break;
            }
            case R.id.l_whole: {
                // 충전소 목록 아이템 - 충전소 상세 버튼 > 충전소 상세 화면 이동.
                if (tag instanceof ChargeEptInfoVO) {
                    ChargeEptInfoVO chargeEptInfoVO = (ChargeEptInfoVO) tag;
                    startActivitySingleTop(new Intent(ChargeFindActivity.this, ChargeStationDetailActivity.class)
                                    .putExtra(KeyNames.KEY_NAME_CHARGE_STATION_SPID, chargeEptInfoVO.getSpid())
                                    .putExtra(KeyNames.KEY_NAME_CHARGE_STATION_CSID, chargeEptInfoVO.getCsid())
                                    .putExtra(KeyNames.KEY_NAME_LAT, lgnViewModel.getPositionValue().get(0)+"")
                                    .putExtra(KeyNames.KEY_NAME_LOT, lgnViewModel.getPositionValue().get(1)+""),
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
        developersViewModel.getRES_PARKLOCATION().observe(ChargeFindActivity.this, result -> {
            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    if (result.data != null && result.data.getLat() != 0 && result.data.getLon() != 0) {
                        showProgressDialog(false);
                        searchChargeStation(result.data.getLat(), result.data.getLon());
                        break;
                    }
                default:
                    showProgressDialog(false);
                    searchChargeStation(VariableType.DEFAULT_POSITION[0], VariableType.DEFAULT_POSITION[1]);
                    break;
            }
        });

        eptViewModel.getRES_EPT_1001().observe(ChargeFindActivity.this, result -> {
            switch (result.status) {
                case LOADING: {
                    showProgressDialog(true);
                    break;
                }
                case SUCCESS: {
                    showProgressDialog(false);
                    if (result.data != null&&(RETURN_CODE_SUCC.equalsIgnoreCase(result.data.getRtCd())||RETURN_CODE_EMPTY.equalsIgnoreCase(result.data.getRtCd()))) {
                        updateChargeList(result.data.getChgList());
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

    @Override
    public void onFilterChanged(InputChargePlaceFragment.SEARCH_TYPE type, List<ChargeSearchCategoryVO> filterList) {
        updateFilterValue(filterList);
        switch (type){
            case ADDRESS:
                reqAddress(type.getAddressVO());
                break;
            case MY_CAR:
                reqParkLocationToDevelopers();
                break;
            case MY_LOCATION:
            default:
                reqMyLocation();
                break;
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
        Intent intent = new Intent(this, ServiceNetworkActivity.class)
                .putExtra(KeyNames.KEY_NAME_ADDR, new AddressVO(0,0,"","","","","", lgnViewModel.getPositionValue().get(0), lgnViewModel.getPositionValue().get(1)))
                .putExtra(KeyNames.KEY_NAME_PAGE_TYPE, ServiceNetworkActivity.PAGE_TYPE_EVCHARGE);
        intent.putParcelableArrayListExtra(KeyNames.KEY_NAME_FILTER_INFO, inputChargePlaceFragment.getSearchCategoryList());
        startActivitySingleTop(intent, RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestCodes.REQ_CODE_GPS.getCode()) {
            if (resultCode == RESULT_OK)
                reqMyLocation();
            else {
                //현대양재사옥위치
                lgnViewModel.setMyPosition(VariableType.DEFAULT_POSITION[0], VariableType.DEFAULT_POSITION[1]);
                searchChargeStation(VariableType.DEFAULT_POSITION[0], VariableType.DEFAULT_POSITION[1]);
            }
        }else if(resultCode== ResultCodes.REQ_CODE_CHARGE_FILTER_APPLY.getCode()){
            if(data!=null){
                try {
                    ArrayList<ChargeSearchCategoryVO> filterList = new ArrayList<>();
                    filterList.addAll(data.getParcelableArrayListExtra(KeyNames.KEY_NAME_FILTER_INFO));
                    if(filterList!=null&&filterList.size()>0){
                        inputChargePlaceFragment.updateFilter(filterList);
                        onFilterChanged(inputChargePlaceFragment.getCurrentType(), filterList);
                    }
                }catch (Exception e){

                }
            }
        }
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
        inputChargePlaceFragment = InputChargePlaceFragment.newInstance(null);
        inputChargePlaceFragment.setOnFilterChangedListener(ChargeFindActivity.this);
        getSupportFragmentManager().beginTransaction()
                .add(ui.vgEvStatusConstainer.getId(), evChargeStatusFragment)
                .add(ui.vgInputChargePlace.getId(), inputChargePlaceFragment)
                .commitAllowingStateLoss();

        LinearLayoutManager layoutManager = new LinearLayoutManager(ChargeFindActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        ui.rvSearchResult.setLayoutManager(layoutManager);

        adapter = new ChargePlaceListAdapter(ChargeFindActivity.this);
        ui.rvSearchResult.setAdapter(adapter);

        try {
            mainVehicleVO = reqViewModel.getMainVehicle();
            setViewBatteryStatus();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void reqMyLocation() {
        showProgressDialog(true);
        findMyLocation(location -> {
            showProgressDialog(false);
            if (location == null) {
                lgnViewModel.setMyPosition(VariableType.DEFAULT_POSITION[0], VariableType.DEFAULT_POSITION[1]);
                searchChargeStation(VariableType.DEFAULT_POSITION[0], VariableType.DEFAULT_POSITION[1]);
                return;
            }
            runOnUiThread(() -> {
                lgnViewModel.setMyPosition(location.getLatitude(), location.getLongitude());
                searchChargeStation(location.getLatitude(), location.getLongitude());
            });

        }, 5000, GpsRetType.GPS_RETURN_FIRST, false);
    }

    private void searchChargeStation(double lat, double lot) {
        lgnViewModel.setPosition(lat, lot);
        if(lat==VariableType.DEFAULT_POSITION[0]&&lot==VariableType.DEFAULT_POSITION[1]){
            inputChargePlaceFragment.setGuideErrorMsg();
        }
        eptViewModel.reqEPT1001(new EPT_1001.Request(
                APPIAInfo.SM_EVSS01.getId(),
                mainVehicleVO.getVin(),
                String.valueOf(lat),
                String.valueOf(lot),
                reservYn,
                chgCd,
                chgSpeedList.size()<1 ? null : chgSpeedList,
                payTypeList.size()<1 ? null : payTypeList
        ));
    }

    private void updateChargeList(List<ChargeEptInfoVO> list) {
        try {
            adapter.setRows(list);
            adapter.notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            setEmptyView();
        }
    }

    private void setEmptyView() {
        if(adapter==null||adapter.getItemCount()<1){
            ui.rvSearchResult.setVisibility(View.GONE);
            ui.tvEmpty.setVisibility(View.VISIBLE);
        }else{
            ui.rvSearchResult.setVisibility(View.VISIBLE);
            ui.tvEmpty.setVisibility(View.GONE);
        }
    }

    private void updateFilterValue(List<ChargeSearchCategoryVO> filterList) {
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
                        case GENESIS: // 제네시스 충전소
                        case E_PIT:
                        case HI_CHARGER: {
                            // 관련 충전소 종류 코드 설정.
                            chgCd = chargeStation.getCode();
                            break;
                        }
                        case ALL:
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

    private void reqParkLocationToDevelopers() {
        if (developersViewModel.checkCarInfoToDevelopers(mainVehicleVO.getVin(), "") == STAT_AGREEMENT) {
            developersViewModel.reqParkLocation(new ParkLocation.Request(developersViewModel.getCarId(mainVehicleVO.getVin())));
        }else{
            searchChargeStation(VariableType.DEFAULT_POSITION[0], VariableType.DEFAULT_POSITION[1]);
        }
    }

    private void reqAddress(AddressVO addressVO) {
        if(addressVO!=null){
            searchChargeStation(addressVO.getCenterLat(), addressVO.getCenterLon());
        }else{
            updateChargeList(new ArrayList<>());
            setEmptyView();
        }
    }

    /**
     * 정보 제공 동의 유무에 따라서 FRAGMENT VISIBLE/GONE 결정
     * (top margin 제거 목적)
     */
    private void setViewBatteryStatus() {
        try {
            if (mainVehicleVO != null) {
                //정보제공동의유무확인
                switch (developersViewModel.checkCarInfoToDevelopers(mainVehicleVO.getVin(), "")) {
                    case STAT_AGREEMENT:
                        //동의한경우
                        ui.vgEvStatusConstainer.setVisibility(View.VISIBLE);
                        break;
                    case STAT_DISAGREEMENT:
                    case STAT_DISABLE:
                    default:
                        ui.vgEvStatusConstainer.setVisibility(View.GONE);
                        break;
                }
            } else {
                ui.vgEvStatusConstainer.setVisibility(View.GONE);
            }
        } catch (Exception e) {

        }
    }

    @Override
    public void onBackPressed() {
        List<SubFragment> fragments = getFragments();
        if (fragments != null) {
            for (SubFragment fragment : fragments) {
                if (fragment instanceof SearchAddressHMNFragment) {
                    hideFragment(fragment);
                    return;
                }
            }
        }
        super.onBackPressed();
    }

} // end of class ChargeSearchActivity
