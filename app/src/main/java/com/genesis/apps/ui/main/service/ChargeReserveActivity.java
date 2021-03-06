package com.genesis.apps.ui.main.service;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.developers.ParkLocation;
import com.genesis.apps.comm.model.api.gra.STC_1001;
import com.genesis.apps.comm.model.constants.ChargeSearchCategorytype;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.AddressVO;
import com.genesis.apps.comm.model.vo.ChargeSearchCategoryVO;
import com.genesis.apps.comm.model.vo.ReserveInfo;
import com.genesis.apps.comm.model.vo.ReserveVo;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.util.DeviceUtil;
import com.genesis.apps.comm.util.PackageUtil;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.viewmodel.DevelopersViewModel;
import com.genesis.apps.comm.viewmodel.LGNViewModel;
import com.genesis.apps.comm.viewmodel.REQViewModel;
import com.genesis.apps.comm.viewmodel.STCViewModel;
import com.genesis.apps.databinding.ActivityChargeReserveBinding;
import com.genesis.apps.ui.common.activity.GpsBaseActivity;
import com.genesis.apps.ui.common.fragment.SubFragment;
import com.genesis.apps.ui.main.ServiceNetworkActivity;
import com.genesis.apps.ui.main.service.view.ChargeReserveHistoryListAdapter;
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
    private ChargeReserveHistoryListAdapter reserveHistoryListAdapter;
    private ChargeSTCPlaceListAdapter adapter;

    private String reservYn = "N";
    private String superSpeedYn = "Y";
    private String highSpeedYn = "Y";
    private String slowSpeedYn = "Y";

    private int pageNo = 1;
    private final String FIXED_PAGE_NO = "1";
    private final String MAX_PAGE_CNT = "50";
    /**
     * ????????? ?????? ?????? ?????? ?????? ????????? ??????.
     */
    private boolean isMoreSearchListReq = false;

    private ArrayList<ReserveVo> searchList = new ArrayList<>();

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
            //????????????????????????
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
            case R.id.tv_btn_reserve_history: {
                // ?????? ?????? ?????? - ?????? ?????? ???????????? ??????.
                startActivitySingleTop(
                        new Intent(ChargeReserveActivity.this, ChargeReserveHistoryActivity.class),
                        RequestCodes.REQ_CODE_ACTIVITY.getCode(),
                        VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE
                );
                break;
            }
            case R.id.tv_btn_reserve_history_title: {
                openReserveHistoryList(!v.isSelected());
                break;
            }
            case R.id.tv_btn_route_detail: {
                // ????????? ?????? ????????? - ?????? ?????? ?????? ?????? > ???????????? ???????????? ??? ??????
                if (tag instanceof ReserveVo) {
                    ReserveVo item = (ReserveVo) tag;
                    if (item != null && !TextUtils.isEmpty(item.getLat()) && !TextUtils.isEmpty(item.getLot())) {
                        PackageUtil.runAppWithScheme(this, PackageUtil.PACKAGE_CONNECTED_CAR, VariableType.getGCSScheme(item.getLat(), item.getLot()));
                    }
                }
                break;
            }
            case R.id.l_whole: {
                // ?????? ?????? ????????? ?????? ????????? - ????????? ?????? ?????? ??????.
                // ????????? ?????? ????????? - ????????? ?????? ?????? ??????.
                if (tag instanceof ReserveVo) {
                    ReserveVo reserveVo = (ReserveVo) tag;
                    List<Double> position = lgnViewModel.getPositionValue();
                    if(position != null && position.size() > 1) {
                        startActivitySingleTop(new Intent(ChargeReserveActivity.this, ChargeStationDetailActivity.class)
                                        .putExtra(KeyNames.KEY_NAME_CHARGE_STATION_CSID, reserveVo.getSid())
                                        .putExtra(KeyNames.KEY_NAME_LAT, String.valueOf(position.get(0)))
                                        .putExtra(KeyNames.KEY_NAME_LOT, String.valueOf(position.get(1)))
                                        .putExtra(KeyNames.KEY_STATION_TYPE, VariableType.CHARGE_STATION_TYPE_STC),
                                RequestCodes.REQ_CODE_ACTIVITY.getCode(),
                                VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                    }
                }
                break;
            }
        }
    }

    /****************************************************************************************************
     * Override Method
     ****************************************************************************************************/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestCodes.REQ_CODE_GPS.getCode()) {
            if (resultCode == RESULT_OK)
                reqMyLocation();
            else {
                //????????????????????????
                lgnViewModel.setMyPosition(VariableType.DEFAULT_POSITION[0], VariableType.DEFAULT_POSITION[1]);
                searchChargeStation(VariableType.DEFAULT_POSITION[0], VariableType.DEFAULT_POSITION[1]);
            }
        } else if (resultCode == ResultCodes.REQ_CODE_CHARGE_FILTER_APPLY.getCode()) {
            if (data != null) {
                ArrayList<ChargeSearchCategoryVO> filterList = new ArrayList<>();
                boolean isChangeAddress=false;
                boolean isChangeFilter=false;

                try {
                    ArrayList<ChargeSearchCategoryVO> receiveFilterList = data.getParcelableArrayListExtra(KeyNames.KEY_NAME_FILTER_INFO);
                    for (ChargeSearchCategoryVO filterItem : receiveFilterList) {
                        if (filterItem.getComponentType() == ChargeSearchCategoryVO.COMPONENT_TYPE.ONLY_ONE) {
                            filterList.add(filterItem);
                        } else if (filterItem.getComponentType() == ChargeSearchCategoryVO.COMPONENT_TYPE.CHECK) {
                            for (ChargeSearchCategorytype type : filterItem.getTypeList()) {
                                int titleResId = 0;
                                if (type == ChargeSearchCategorytype.SUPER_SPEED) {
                                    titleResId = R.string.sm_evss01_22;
                                } else if (type == ChargeSearchCategorytype.HIGH_SPEED) {
                                    titleResId = R.string.sm_evss01_23;
                                } else if (type == ChargeSearchCategorytype.SLOW_SPEED) {
                                    titleResId = R.string.sm_evss01_24;
                                }

                                if (titleResId != 0) {
                                    ChargeSearchCategoryVO categoryVO = new ChargeSearchCategoryVO(titleResId, ChargeSearchCategoryVO.COMPONENT_TYPE.ONLY_ONE, null);
                                    categoryVO.setSelected(filterItem.getSelectedItem().contains(type));
                                    filterList.add(categoryVO);
                                }
                            }
                        }
                    }
                    if (filterList != null && filterList.size() > 0) {
                        inputChargePlaceFragment.updateFilter(filterList);
                        isChangeFilter=true;
                    }
                } catch (Exception e) {

                }

                try{
                    AddressVO addressVO = (AddressVO) data.getSerializableExtra(KeyNames.KEY_NAME_ADDR);
                    if (addressVO != null) {
                        //????????? ???????????? ???????????? ?????????
                        if(addressVO.getCenterLat()!=lgnViewModel.getPositionValue().get(0)||addressVO.getCenterLon()!=lgnViewModel.getPositionValue().get(1)){
                            inputChargePlaceFragment.eventSearchAddress();
                            onAddressSelected(addressVO);
                            isChangeAddress=true;
                        }
                    }
                }catch (Exception e){

                }

                if(!isChangeAddress&&isChangeFilter) {
                    onFilterChanged(inputChargePlaceFragment.getCurrentType(), filterList);
                }
            }
        } else if (resultCode == ResultCodes.REQ_CODE_CHARGE_RESERVATION_FINISH.getCode()) {
            // ????????? ?????? ????????? ?????? ?????? ?????? ??????.
            ReserveInfo reserveInfo = (ReserveInfo) data.getSerializableExtra(KeyNames.KEY_NAME_CHARGE_RESERVE_INFO);
            if (reserveInfo != null) {
                startActivitySingleTop(new Intent(ChargeReserveActivity.this, ChargeResultActivity.class)
                                .putExtra(KeyNames.KEY_NAME_CHARGE_RESERVE_INFO, reserveInfo)
                        , RequestCodes.REQ_CODE_ACTIVITY.getCode()
                        , VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
            }
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
                        if (result.data.getSearchList() != null && result.data.getSearchList().size() > 0) {
//                            if (isMoreSearchListReq) {
//                                // ????????? ????????? ????????? ????????? ??????.
//                                pageNo++;
//                            }
                            searchList.clear();
                            searchList.addAll(result.data.getSearchList());
                        }
                        updateChargeList(searchList);
                        updateReserveHistoryList(result.data.getReservList());
                        isMoreSearchListReq = false;
                        break;
                    }
                }
                default: {
                    String serverMsg = "";
                    try {
                        serverMsg = result.data.getRtMsg();
                        //?????? ???????????? ?????? ????????? ???????????? ??? ??????????????? ??????
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        isMoreSearchListReq = false;
                        showProgressDialog(false);
                        SnackBarUtil.show(this, (TextUtils.isEmpty(serverMsg)) ? getString(R.string.r_flaw06_p02_snackbar_1) : serverMsg);
                        updateReserveHistoryList(null);
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
     * Override Method - {@link NestedScrollView.OnScrollChangeListener}
     ****************************************************************************************************/
    // 2021.06.14 ?????????K ????????????, ????????? ?????? ??? ????????? ?????? ???????????? ??????????????? 50??? ?????? ??????????????? ?????? ??????, ?????? ????????? ?????? ??????!
//    @Override
//    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//        // ?????? ????????? ??????????????? ????????? ?????? ???????????? ?????? ?????? ?????? ?????????
//        if (isMoreSearchListReq) {
//            return;
//        }
//
//        if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
//            List<Double> position = lgnViewModel.getPositionValue();
//            if (position != null && position.size() > 1) {
//                if(position.size() >= Integer.parseInt(MAX_PAGE_CNT))
//                    isMoreSearchListReq = true;
//
//                getChargeStation(position.get(0), position.get(1), pageNo + 1);
//            }
//        }
//    }

    /****************************************************************************************************
     * Override Method - {@link InputChargePlaceFragment.FilterChangeListener}
     ****************************************************************************************************/
    @Override
    public void onFilterChanged(InputChargePlaceFragment.SEARCH_TYPE type, List<ChargeSearchCategoryVO> filterList) {
        updateFilterValue(filterList);
        switch (type) {
            case ADDRESS:
                // ?????? ????????? ?????? ?????? ?????? ????????? ????????? ??? ?????? ??????.
                updateReserveHistoryList(null);
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

    @Override
    public void onSearchAddress() {
        // ?????? ??????
        Bundle bundle = new Bundle();
        bundle.putInt(KeyNames.KEY_NAME_MAP_SEARCH_TITLE_ID, R.string.sm_evss01_34);
        bundle.putInt(KeyNames.KEY_NAME_MAP_SEARCH_MSG_ID, R.string.sm_evss01_35);
        SearchAddressHMNFragment fragment = new SearchAddressHMNFragment();
        fragment.setAddressSelectListener(this);
        showFragment(fragment, bundle);
    }

    @Override
    public void onSearchMap() {
        // ????????? ?????? ?????? ??????.
        Intent intent = new Intent(this, ServiceNetworkActivity.class)
                .putExtra(KeyNames.KEY_NAME_ADDR, new AddressVO(0,0,"","","","","", lgnViewModel.getPositionValue().get(0), lgnViewModel.getPositionValue().get(1)))
                .putExtra(KeyNames.KEY_NAME_PAGE_TYPE, ServiceNetworkActivity.PAGE_TYPE_EVCHARGE_STC);
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
                new ChargeSearchCategoryVO(R.string.sm_evss01_22, ChargeSearchCategoryVO.COMPONENT_TYPE.ONLY_ONE, null).setSelected("Y".equalsIgnoreCase(superSpeedYn)),
                new ChargeSearchCategoryVO(R.string.sm_evss01_23, ChargeSearchCategoryVO.COMPONENT_TYPE.ONLY_ONE, null).setSelected("Y".equalsIgnoreCase(highSpeedYn)),
                new ChargeSearchCategoryVO(R.string.sm_evss01_24, ChargeSearchCategoryVO.COMPONENT_TYPE.ONLY_ONE, null).setSelected("Y".equalsIgnoreCase(slowSpeedYn))
        )));
        inputChargePlaceFragment.setOnFilterChangedListener(ChargeReserveActivity.this);
        getSupportFragmentManager().beginTransaction()
                .add(ui.vgEvStatusConstainer.getId(), evChargeStatusFragment)
                .add(ui.vgInputChargePlace.getId(), inputChargePlaceFragment)
                .commitAllowingStateLoss();

//        ui.vgNsv.setOnScrollChangeListener(ChargeReserveActivity.this);

        LinearLayoutManager reserveHistoryListLayoutManager = new LinearLayoutManager(ChargeReserveActivity.this);
        reserveHistoryListLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        ui.rvReserveHistory.setLayoutManager(reserveHistoryListLayoutManager);

        reserveHistoryListAdapter = new ChargeReserveHistoryListAdapter(ChargeReserveActivity.this);
        ui.rvReserveHistory.setAdapter(reserveHistoryListAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(ChargeReserveActivity.this, DividerItemDecoration.HORIZONTAL);
        dividerItemDecoration.setDrawable(new ColorDrawable(getColor(R.color.x_e5e5e5)));
        ui.rvReserveHistory.addItemDecoration(dividerItemDecoration);

        // ?????? ?????? ????????? ?????? ?????????
        updateReserveHistoryList(null);

        LinearLayoutManager searchListLayoutManager = new LinearLayoutManager(ChargeReserveActivity.this);
        searchListLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        ui.rvSearchResult.setLayoutManager(searchListLayoutManager);

        adapter = new ChargeSTCPlaceListAdapter(ChargeReserveActivity.this);
        ui.rvSearchResult.setAdapter(adapter);

        try {
            mainVehicleVO = reqViewModel.getMainVehicle();
            setViewBatteryStatus();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openReserveHistoryList(boolean isOpen) {
        ui.tvBtnReserveHistoryTitle.setSelected(isOpen);
        ui.rvReserveHistory.setVisibility(isOpen ? View.VISIBLE : View.GONE);
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) ui.tvBtnReserveHistoryTitle.getLayoutParams();
        lp.setMargins(0, 0, 0, isOpen ? 0 : (int) DeviceUtil.dip2Pixel(ChargeReserveActivity.this, 10));
    }

    private void updateReserveHistoryList(List<ReserveVo> list) {
        // ?????? ???????????? ????????? ?????? ????????? ?????? ????????? ????????? ?????? ?????? ????????? UI ?????????.
        if (list == null || list.size() == 0 || inputChargePlaceFragment.getCurrentType() == InputChargePlaceFragment.SEARCH_TYPE.ADDRESS) {
            ui.tvBtnReserveHistoryTitle.setVisibility(View.GONE);
            ui.rvReserveHistory.setVisibility(View.GONE);
            return;
        }
        if (ui.tvBtnReserveHistoryTitle.getVisibility() == View.GONE) {
            // ?????? ?????? ????????? ????????? ???????????? ?????? ???????????? ??????.
            openReserveHistoryList(true);
            ui.tvBtnReserveHistoryTitle.setVisibility(View.VISIBLE);
        }

        reserveHistoryListAdapter.setRows(list);
        reserveHistoryListAdapter.notifyDataSetChanged();
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
        if (lat == VariableType.DEFAULT_POSITION[0] && lot == VariableType.DEFAULT_POSITION[1]) {
            inputChargePlaceFragment.setGuideErrorMsg();
        }
        pageNo=1;
        getChargeStation(lat, lot, pageNo);
    }

    private void getChargeStation(double lat, double lot, int pageNo) {
        stcViewModel.reqSTC1001(new STC_1001.Request(
                APPIAInfo.SM_EVSB01.getId(),
                mainVehicleVO.getVin(),
                lat,
                lot,
                reservYn,
                superSpeedYn,
                highSpeedYn,
                slowSpeedYn,
                FIXED_PAGE_NO,
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
        // ????????? ?????? ?????? ?????????
        searchList.clear();
        // ????????? ??? ??????, ??? ?????? ?????? ?????? ??????.
        selectedFilterList.clear();
        selectedFilterList.addAll(filterList);
        // ?????????????????? ??? ?????????.
        reservYn = null;
        // ?????? ?????? ??? ?????????
        superSpeedYn = null;
        highSpeedYn = null;
        slowSpeedYn = null;

        // ????????? ?????? ?????????
        pageNo = 1;
        // ????????? ????????? ??????.
        for (ChargeSearchCategoryVO item : filterList) {
            if (item.getTitleResId() == R.string.sm_evss01_15) {
                // ???????????? ????????? ??????
                reservYn = item.isSelected() ? "Y" : "N";
            } else if (item.getTitleResId() == R.string.sm_evss01_22) {
                // ????????? ????????? ??????
                superSpeedYn = item.isSelected() ? "Y" : "N";
            } else if (item.getTitleResId() == R.string.sm_evss01_23) {
                // ????????? ????????? ??????
                highSpeedYn = item.isSelected() ? "Y" : "N";
            } else if (item.getTitleResId() == R.string.sm_evss01_24) {
                // ????????? ????????? ??????
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

    private void reqAddress(AddressVO addressVO) {
        if (addressVO != null) {
            searchChargeStation(addressVO.getCenterLat(), addressVO.getCenterLon());
        } else {
            updateChargeList(new ArrayList<>());
            setEmptyView();
        }
    }

    /**
     * ?????? ?????? ?????? ????????? ????????? FRAGMENT VISIBLE/GONE ??????
     * (top margin ?????? ??????)
     */
    private void setViewBatteryStatus() {
        try {
            if (mainVehicleVO != null) {
                //??????????????????????????????
                switch (developersViewModel.checkCarInfoToDevelopers(mainVehicleVO.getVin(), "")) {
                    case STAT_AGREEMENT:
                        //???????????????
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
} // end of class ChargeReserveActivity
