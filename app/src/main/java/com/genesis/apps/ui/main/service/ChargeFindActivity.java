package com.genesis.apps.ui.main.service;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.databinding.ActivityChargeFindBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.main.service.view.ChargePlaceListAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.genesis.apps.comm.model.constants.KeyNames.KEY_NAME_CHARGE_TYPE;

/**
 * Class Name : ChargeSearchActivity
 * 충전소 검색 Activity.
 *
 * @author Ki-man Kim
 * @since 2021-03-22
 */
public class ChargeFindActivity extends SubActivity<ActivityChargeFindBinding> {
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

        // TODO 테스트 코드
        reqGetChargeList();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // TODO 테스트 코드
        updateEvChargeStatus();
    }

    /****************************************************************************************************
     * Override Method - Event
     ****************************************************************************************************/
    @Override
    public void onClickCommon(View v) {
        Log.d("FID", "test :: ChargeFindActivity :: onClickCommon ");
        switch (v.getId()) {
            case R.id.tv_more: {
                Object tag = v.getTag();
                if (tag instanceof ChargePlaceListAdapter.DummyData) {
                    startActivitySingleTop(
                            new Intent(ChargeFindActivity.this, ChargePlaceListActivity.class)
                                    .putExtra(KEY_NAME_CHARGE_TYPE, ((ChargePlaceListAdapter.DummyData) tag).getName()),
                            RequestCodes.REQ_CODE_ACTIVITY.getCode(),
                            VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE
                    );
                }
                break;
            }
            default: {
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

    }

    @Override
    public void setObserver() {
        // TODO 전문이 전달되면 해당 전문 코드 적용 필요

    }

    @Override
    public void getDataFromIntent() {
        // TODO 차량 정보를 가져와야 할지 체크 및 코드 추가 필요.

    }

    /****************************************************************************************************
     * Method - Private
     ****************************************************************************************************/
    private void updateEvChargeStatus() {
        getSupportFragmentManager().beginTransaction()
                .add(ui.vgEvStatusConstainer.getId(), EvChargeStatusFragment.newInstance())
                .add(ui.vgInputChargePlace.getId(), InputChargePlaceFragment.newInstance())
                .commitAllowingStateLoss();
    }

    private void updateChargeList(List<ChargePlaceListAdapter.DummyData> list) {
        ChargePlaceListAdapter adapter = new ChargePlaceListAdapter(ChargeFindActivity.this);
        adapter.setRows(list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ChargeFindActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        ui.rvSearchResult.setLayoutManager(layoutManager);
        ui.rvSearchResult.setAdapter(adapter);
    }

    /**
     * 충전소 목록 조회 함수.
     */
    private void reqGetChargeList() {

        /**
         * 더미 데이터를 통해 View 정보를 호출
         */
        ArrayList<ChargePlaceListAdapter.DummyData> list = new ArrayList<>();
        list.add(new ChargePlaceListAdapter.DummyData(ChargePlaceListAdapter.Type.CATEGORY, "가까운 초급속 충전소"));
        list.add(new ChargePlaceListAdapter.DummyData(ChargePlaceListAdapter.Type.PLACE, "현대EV 스테이션 강동", "0.8km", "충전중"));
        list.add(new ChargePlaceListAdapter.DummyData(ChargePlaceListAdapter.Type.CATEGORY, "가까운 급속 충전소"));
        list.add(new ChargePlaceListAdapter.DummyData(ChargePlaceListAdapter.Type.PLACE, "현대EV 스테이션 0", "0.8km", "충전중"));
        list.add(new ChargePlaceListAdapter.DummyData(ChargePlaceListAdapter.Type.PLACE, "현대EV 스테이션 1", "3.8km", "충전중"));
        list.add(new ChargePlaceListAdapter.DummyData(ChargePlaceListAdapter.Type.PLACE, "현대EV 스테이션 2", "2.8km", "충전중"));
        list.add(new ChargePlaceListAdapter.DummyData(ChargePlaceListAdapter.Type.CATEGORY, "가까운 완속 충전소"));
        list.add(new ChargePlaceListAdapter.DummyData(ChargePlaceListAdapter.Type.PLACE, "현대EV 스테이션 3", "0.8km", "충전중"));
        list.add(new ChargePlaceListAdapter.DummyData(ChargePlaceListAdapter.Type.PLACE, "현대EV 스테이션 4", "1.1km", "충전중"));

        updateChargeList(list);
    }
} // end of class ChargeSearchActivity
