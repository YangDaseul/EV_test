package com.genesis.apps.ui.main.service;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.ChargePlaceStatus;
import com.genesis.apps.databinding.ActivityChargeFindBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.main.service.view.ChargePlaceListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Name : ChargeSearchActivity
 * 충전소 검색 Activity.
 *
 * @author Ki-man Kim
 * @since 2021-03-22
 */
public class ChargeFindActivity extends SubActivity<ActivityChargeFindBinding> {
    private ChargePlaceListAdapter adapter;

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
//            case R.id.tv_more: {
//                Object tag = v.getTag();
//                if (tag instanceof ChargePlaceListAdapter.DummyData) {
//                    startActivitySingleTop(
//                            new Intent(ChargeFindActivity.this, ChargePlaceListActivity.class)
//                                    .putExtra(KEY_NAME_CHARGE_TYPE, ((ChargePlaceListAdapter.DummyData) tag).getName()),
//                            RequestCodes.REQ_CODE_ACTIVITY.getCode(),
//                            VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE
//                    );
//                }
//                break;
//            }
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
    private void initialize() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(ChargeFindActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        ui.rvSearchResult.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(ChargeFindActivity.this, layoutManager.getOrientation());
        dividerItemDecoration.setDrawable(new ColorDrawable(getColor(R.color.x_e5e5e5)));
        ui.rvSearchResult.addItemDecoration(dividerItemDecoration);

        adapter = new ChargePlaceListAdapter(ChargeFindActivity.this);
        ui.rvSearchResult.setAdapter(adapter);
    }

    private void updateEvChargeStatus() {
        getSupportFragmentManager().beginTransaction()
                .add(ui.vgEvStatusConstainer.getId(), EvChargeStatusFragment.newInstance())
                .add(ui.vgInputChargePlace.getId(), InputChargePlaceFragment.newInstance())
                .commitAllowingStateLoss();
    }

    private void updateChargeList(List<ChargePlaceListAdapter.DummyData> list) {
        adapter.setRows(list);
        adapter.notifyDataSetChanged();
    }

    /**
     * 충전소 목록 조회 함수.
     */
    private void reqGetChargeList() {

        /**
         * 더미 데이터를 통해 View 정보를 호출
         */
        ArrayList<ChargePlaceListAdapter.DummyData> list = new ArrayList<>();
        list.add(new ChargePlaceListAdapter.DummyData("현대EV스테이션 강동", "0.4km", ChargePlaceStatus.CHECKING, "점검중"));
        list.add(new ChargePlaceListAdapter.DummyData("가산충전소", "0.8km", ChargePlaceStatus.ABLE_BOOK, "초고속 1대, 완속 3대 사용가능"));
        list.add(new ChargePlaceListAdapter.DummyData("현대EV 현대EV스테이션 현대EV스테이션 강남", "1.1km", ChargePlaceStatus.FINISH_BOOK, "초고속 1대 사용가능"));

        updateChargeList(list);
    }
} // end of class ChargeSearchActivity
