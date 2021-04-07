package com.genesis.apps.ui.main.service;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.CHB_1015;
import com.genesis.apps.comm.model.vo.PaymtCardVO;
import com.genesis.apps.comm.viewmodel.CHBViewModel;
import com.genesis.apps.databinding.ActivityCardManageBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.view.ItemMoveCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Name : CardManageActivity
 *
 * @author Ki-man Kim
 * @since 2021-04-02
 */
public class CardManageActivity extends SubActivity<ActivityCardManageBinding> {

    private ArrayList<PaymtCardVO> deleteCardList = new ArrayList<>();
    private CHBViewModel chbViewModel;

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

        chbViewModel.reqCHB1015(new CHB_1015.Request(APPIAInfo.SM_CGRV02_P01.getId()));
    }

    /****************************************************************************************************
     * Override Method - Event
     ****************************************************************************************************/
    @Override
    public void onClickCommon(View v) {
        Log.d("FID", "test :: onClickCommon");
        Object tag = v.getTag();
        switch (v.getId()) {
            // 카드 추가 버튼
            case R.id.tv_add_card: {
                addCard();
                break;
            }
            // 카드 정렬 저장 버튼.
            case R.id.tv_sort_save: {
                // TODO 카드 저장 연동 코드 필요. - 현재까지는 최상단의 카드가 바뀌면 그 카드를 주카드 등록 연동을 진행.
                for (PaymtCardVO item : deleteCardList) {
                    Log.d("FID", "test :: delete card list :: name=" + item.getCardName() + " :: number=" + item.getCardNo());
                }

                if (adapter.getItemCount() > 0) {
                    PaymtCardVO firstCard = adapter.getItems().get(0);
                    Log.d("FID", "test :: first Card :: name=" + firstCard.getCardName() + " :: no=" + firstCard.getCardNo());
                }
                break;
            }
            // 카드 목록 이벤트 - 카드 삭제 버튼
            case R.id.iv_btn_delete: {
                Log.d("FID", "test :: delete :: tag=" + tag);
                if (tag instanceof PaymtCardVO) {
                    PaymtCardVO targetVO = (PaymtCardVO) tag;
                    String cardId = targetVO.getCardId();
                    if (cardId != null) {
                        List<PaymtCardVO> items = adapter.getItems();
                        int i = 0;
                        while (i < items.size()) {
                            if (cardId.equals(items.get(i).getCardId())) {
                                // 해당 카드 정보 삭제.
                                items.remove(i);
                                adapter.notifyDataSetChanged();
                                updateCardCount(adapter.getItemCount());
                                break;
                            }
                            i++;
                        }
                        deleteCardList.add(targetVO);
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
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        chbViewModel = new ViewModelProvider(CardManageActivity.this).get(CHBViewModel.class);
    }

    @Override
    public void setObserver() {
        chbViewModel.getRES_CHB_1015().observe(this, (result) -> {
            Log.d("FID", "test :: RES_CHB_1015 :: result=" + result);
            switch (result.status) {
                case LOADING: {
                    showProgressDialog(true);
                    break;
                }
                case SUCCESS: {
                    showProgressDialog(false);
                    Log.d("FID", "test :: RES_CHB_1015 :: data=" + result.data);
                    if (result.data != null) {
                        updateCardList(result.data.getCardList());
                    } else {
                        // TODO 결제수단 데이터가 없음.
                    }
                    break;
                }
                case ERROR: {
                    showProgressDialog(false);
                    // TODO 통신 오류 코드 추가.
                    break;
                }
            }
        });

    }

    @Override
    public void getDataFromIntent() {

    }

    /****************************************************************************************************
     * Method - Private
     ****************************************************************************************************/
    private void initialize() {
        ui.setActivity(CardManageActivity.this);
        adapter = new CardManageListAdapter(this.onSingleClickListener);
        ItemMoveCallback callback = new ItemMoveCallback(adapter);
        callback.setIsLongPressDragEnabled(false);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(ui.rvCardList);
        adapter.setItemTouchHelper(touchHelper);

//        adapter.setRows(list);
        ui.rvCardList.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(CardManageActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        ui.rvCardList.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(CardManageActivity.this, DividerItemDecoration.VERTICAL);
        ui.rvCardList.addItemDecoration(dividerItemDecoration);
    }


    /**
     * 카드 목록 업데이트 함수.
     */
    private void updateCardList(List<PaymtCardVO> list) {
        adapter.setRows(list);
        adapter.notifyDataSetChanged();
        updateCardCount(adapter.getItemCount());
    }

    /**
     * 카드 추가 함수.
     */
    private void addCard() {

    }

    /**
     * 카드 정렬 저장 함수.
     */
    private void saveCardSetting() {

    }

    private void updateCardCount(int count) {
        ui.tvCardTotalCount.setText(String.format("총 %,3d개", count));
    }
} // end of class CardManageActivity
