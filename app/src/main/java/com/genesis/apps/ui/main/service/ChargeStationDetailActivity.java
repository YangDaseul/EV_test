package com.genesis.apps.ui.main.service;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.EPT_1002;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.vo.ChargeEptInfoVO;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.viewmodel.EPTViewModel;
import com.genesis.apps.comm.viewmodel.REQViewModel;
import com.genesis.apps.databinding.ActivityChargeStationDetailBinding;
import com.genesis.apps.ui.common.activity.GpsBaseActivity;

import java.util.ArrayList;

/**
 * Class Name : ChargeStationDetailActivity
 *
 * @author Ki-man Kim
 * @since 2021-04-27
 */
public class ChargeStationDetailActivity extends GpsBaseActivity<ActivityChargeStationDetailBinding> {

    /**
     * 환경부-기관ID
     * 환경부에서 발급한 ID
     */
    private String spid;
    /**
     * 환경부-충전소ID
     * 환경부에서 발급한 ID
     */
    private String csid;
    /**
     * 위도
     * 현재위치/차량위치/지명검색위치
     */
    private String lat;
    /**
     * 경도
     * 현재위치/차량위치/지명검색위치
     */
    private String lot;

    private VehicleVO mainVehicleVO;

    private REQViewModel reqViewModel;
    private EPTViewModel eptViewModel;

    private EvChargeStatusFragment evChargeStatusFragment;

    /****************************************************************************************************
     * Override Method - LifeCycle
     ****************************************************************************************************/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charge_station_detail);
        getDataFromIntent();
        setViewModel();
        setObserver();
        initialize();
//        checkEnableGPS(() -> {
        //현대양재사옥위치
//            searchChargeStation(37.463936, 127.042953); // FIXME 고정된 위치값이 여러 곳에 사용중이어서 이 값은 한 곳에서 관리 할 수 있게 처리가 필요.
//        }, this::reqMyLocation);
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
        ui.setLifecycleOwner(ChargeStationDetailActivity.this);
        reqViewModel = new ViewModelProvider(ChargeStationDetailActivity.this).get(REQViewModel.class);
        eptViewModel = new ViewModelProvider(ChargeStationDetailActivity.this).get(EPTViewModel.class);
    }

    @Override
    public void setObserver() {
        eptViewModel.getRES_EPT_1002().observe(ChargeStationDetailActivity.this, result -> {
            switch (result.status) {
                case LOADING: {
                    showProgressDialog(true);
                    break;
                }
                case SUCCESS: {
                    showProgressDialog(false);
                    EPT_1002.Response data = result.data;
                    if (data == null) {
                        // 데이터가 없음. - TODO 안내 처리 필요.
                    } else {
                        updateStation(data);
                    }
                    break;
                }
                default:
                case ERROR: {
                    showProgressDialog(false);

                }
            }
        });
    }

    @Override
    public void getDataFromIntent() {
        Intent getIntent = getIntent();
        spid = getIntent.getStringExtra(KeyNames.KEY_NAME_CHARGE_STATION_SPID);
        csid = getIntent.getStringExtra(KeyNames.KEY_NAME_CHARGE_STATION_CSID);
        lat = getIntent.getStringExtra(KeyNames.KEY_NAME_LAT);
        lot = getIntent.getStringExtra(KeyNames.KEY_NAME_LOT);

        if (TextUtils.isEmpty(spid) || TextUtils.isEmpty(csid) || TextUtils.isEmpty(lat) || TextUtils.isEmpty(lot)) {
            // 데이터가 전달되지 않아 조회 할수 없음. - TODO 안내 처리 필요.
            return;
        }
    }

    /****************************************************************************************************
     * Method - Private
     ****************************************************************************************************/
    private void initialize() {
        evChargeStatusFragment = EvChargeStatusFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .add(ui.vgEvStatusConstainer.getId(), evChargeStatusFragment)
                .commitAllowingStateLoss();

        LinearLayoutManager layoutManager = new LinearLayoutManager(ChargeStationDetailActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        ui.rvStationDetail.setLayoutManager(layoutManager);

        try {
            mainVehicleVO = reqViewModel.getMainVehicle();
        } catch (Exception e) {
            e.printStackTrace();
        }

        eptViewModel.reqEPT1002(new EPT_1002.Request(
                APPIAInfo.SM_EVSS04.getId(),
                mainVehicleVO.getVin(),
                spid,
                csid,
                lat,
                lot));
    }

    private void updateStation(EPT_1002.Response data) {
        ChargeEptInfoVO chargeEptInfoVO = data.getChgInfo();
        ArrayList<ChargeStationDetailListAdapter.ItemVO> list = new ArrayList<>();
        list.add(new ChargeStationDetailListAdapter.ItemVO(ChargeStationDetailListAdapter.DetailType.ADDRESS, chargeEptInfoVO.getDaddr() + "\n" + chargeEptInfoVO.getAddrDtl()));
        list.add(new ChargeStationDetailListAdapter.ItemVO(ChargeStationDetailListAdapter.DetailType.TIME, chargeEptInfoVO.getUseTime()));
        list.add(new ChargeStationDetailListAdapter.ItemVO(ChargeStationDetailListAdapter.DetailType.SPNM, chargeEptInfoVO.getSpnm()));

        StringBuilder payStringBuilder = new StringBuilder();
        // 신용카드 고정 노출
        payStringBuilder.append(getString(R.string.sm_evss04_06));
        // TODO 충전크래딧 지원 여부 데이터가 필요.
        if ("Y".equalsIgnoreCase(chargeEptInfoVO.getGcpYn())) {
            // 카페이 지원할 경우 항목 추가.
            payStringBuilder.append("\n")
                    .append(getString(R.string.sm_evss04_08));
        }

        list.add(new ChargeStationDetailListAdapter.ItemVO(ChargeStationDetailListAdapter.DetailType.PAY_TYPE, payStringBuilder.toString()));
        ChargeStationDetailListAdapter adapter = new ChargeStationDetailListAdapter();
        adapter.setRows(list);
        ui.rvStationDetail.setAdapter(adapter);
    }
} // end of class ChargeStationDetailActivity
