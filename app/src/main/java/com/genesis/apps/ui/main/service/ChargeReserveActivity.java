package com.genesis.apps.ui.main.service;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.EPT_1001;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.ChargeSearchCategoryVO;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.viewmodel.DevelopersViewModel;
import com.genesis.apps.comm.viewmodel.LGNViewModel;
import com.genesis.apps.comm.viewmodel.REQViewModel;
import com.genesis.apps.comm.viewmodel.STCViewModel;
import com.genesis.apps.databinding.ActivityChargeReserveBinding;
import com.genesis.apps.ui.common.activity.GpsBaseActivity;
import com.genesis.apps.ui.main.service.view.ChargePlaceListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Name : ChargeReserveActivity
 *
 * @author Ki-man Kim
 * @since 2021-05-14
 */
public class ChargeReserveActivity extends GpsBaseActivity<ActivityChargeReserveBinding> implements InputChargePlaceFragment.FilterChangeListener {
    private InputChargePlaceFragment inputChargePlaceFragment;

    private final ArrayList<ChargeSearchCategoryVO> selectedFilterList = new ArrayList<>();
    private ChargePlaceListAdapter adapter;

    private String reservYn;
    private String chgCd;
    private ArrayList<String> chgSpeedList = new ArrayList<>();
    private ArrayList<String> payTypeList = new ArrayList<>();

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
    }

    @Override
    public void getDataFromIntent() {

    }

    /****************************************************************************************************
     * Override Method - {@link InputChargePlaceFragment.FilterChangeListener}
     ****************************************************************************************************/
    @Override
    public void onFilterChanged(InputChargePlaceFragment.SEARCH_TYPE type, List<ChargeSearchCategoryVO> filterList) {

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
//        eptViewModel.reqEPT1001(new EPT_1001.Request(
//                APPIAInfo.SM_EVSS01.getId(),
//                mainVehicleVO.getVin(),
//                String.valueOf(lat),
//                String.valueOf(lot),
//                reservYn,
//                chgCd,
//                chgSpeedList,
//                payTypeList
//        ));
    }
} // end of class ChargeReserveActivity
