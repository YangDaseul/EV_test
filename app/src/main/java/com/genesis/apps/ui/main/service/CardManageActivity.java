package com.genesis.apps.ui.main.service;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.util.DeviceUtil;
import com.genesis.apps.comm.util.RecyclerViewDecoration;
import com.genesis.apps.databinding.ActivityCardManageBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.view.ItemMoveCallback;

import java.util.ArrayList;

/**
 * Class Name : CardManageActivity
 *
 * @author Ki-man Kim
 * @since 2021-04-02
 */
public class CardManageActivity extends SubActivity<ActivityCardManageBinding> {
    private ArrayList<DummyDataCard> list = new ArrayList<>();

    private CardManageListAdapter adapter;

    /****************************************************************************************************
     * Override Method - LifeCycle
     ****************************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_manage);
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
        Log.d("FID", "test :: onClickCommon");
        Object tag = v.getTag();
        switch (v.getId()) {
            case R.id.iv_btn_delete: {
                Log.d("FID", "test :: delete :: tag=" + tag);
                break;
            }
        }
    }

    /****************************************************************************************************
     * Override Method
     ****************************************************************************************************/
    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
    }

    @Override
    public void setObserver() {

    }

    @Override
    public void getDataFromIntent() {

    }

    /****************************************************************************************************
     * Method - Private
     ****************************************************************************************************/
    private void initialize() {
        adapter = new CardManageListAdapter(this.onSingleClickListener);
        ItemTouchHelper.Callback callback = new ItemMoveCallback(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(ui.rvCardList);


        getCardList();
        adapter.setRows(list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(CardManageActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        ui.rvCardList.setLayoutManager(layoutManager);
        ui.rvCardList.setAdapter(adapter);
        ui.rvCardList.addItemDecoration(new RecyclerViewDecoration((int) DeviceUtil.dip2Pixel(this, 1.0f)));
    }

    /**
     * 카드 목록 조회 함수.
     */
    private void getCardList() {
        list.add(new DummyDataCard("삼성카드", "0012 1332 2351 1111"));
        list.add(new DummyDataCard("신한카드", "0012 1332 2351 1111"));
        list.add(new DummyDataCard("국민카드", "0012 1332 2351 1111"));
        list.add(new DummyDataCard("삼성카드", "0000 0000 2890 1234"));
    }

    /**
     * 카드 추가 함수.
     */
    private void addCard() {

    }

    /**
     * 카드 위치 변경 함수.
     */
    private void moveCard() {

    }

    /**
     * 카드 정렬 저장 함수.
     */
    private void saveCardSetting() {

    }

    /**
     * 간편 결제 회원 탈퇴 안내 팝업
     */
    private void showSecessionDialog() {

    }

    /**
     * 카드 탈퇴 함수.
     */
    private void secession() {

    }

    class DummyDataCard extends BaseData {
        private String name;
        private String number;

        public DummyDataCard(String name, String number) {
            this.name = name;
            this.number = number;
        }

        public String getName() {
            return name;
        }

        public String getNumber() {
            return number;
        }
    }
} // end of class CardManageActivity
