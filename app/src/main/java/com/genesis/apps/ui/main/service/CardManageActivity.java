package com.genesis.apps.ui.main.service;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.CHB_1015;
import com.genesis.apps.comm.model.api.gra.CHB_1016;
import com.genesis.apps.comm.model.api.gra.CHB_1017;
import com.genesis.apps.comm.model.vo.PaymtCardVO;
import com.genesis.apps.comm.viewmodel.CHBViewModel;
import com.genesis.apps.databinding.ActivityCardManageBinding;
import com.genesis.apps.ui.common.activity.SubActivity;

import java.util.List;

/**
 * Class Name : CardManageActivity
 * 결제수단관리 화면 Activity.
 *
 * 기능
 * 1. 결제 카드 삭제.
 * 2. 결제 카드 주사용 등록.
 * 3. 카드 추가.
 *
 * @author Ki-man Kim
 * @since 2021-04-02
 */
public class CardManageActivity extends SubActivity<ActivityCardManageBinding> {

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

        getCardList();
    }

    /****************************************************************************************************
     * Override Method - Event
     ****************************************************************************************************/
    @Override
    public void onClickCommon(View v) {
        Object tag = v.getTag();
        switch (v.getId()) {
            // 카드 추가 버튼
            case R.id.tv_add_card: {
                addCard();
                break;
            }
            // 카드 목록 이벤트 - 카드 삭제 버튼
            case R.id.iv_btn_delete: {
                if (tag instanceof PaymtCardVO) {
                    deleteCard((PaymtCardVO) tag);
                    /*
                    카드 정렬 기능 삭제로 해당 코드 주석 처리.
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
                     */
                }
                break;
            }
            // 카드 목록 이벤트 - 카드 주사용 등록/해제 버튼.
            case R.id.iv_btn_favorit: {
                if (tag instanceof PaymtCardVO) {
                    setFavoritCard((PaymtCardVO) tag);
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
        // 결제 수단 조회 Observe 등록.
        chbViewModel.getRES_CHB_1015().observe(this, (result) -> {
            Log.d("FID", "test :: RES_CHB_1015 :: status=" + result.status);
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

        // 주카드 등록 Observe 등록.
        chbViewModel.getRES_CHB_1016().observe(this, (result) -> {
            Log.d("FID", "test :: RES_CHB_1016 :: status=" + result.status);
            switch (result.status) {
                case LOADING: {
                    showProgressDialog(true);
                    break;
                }
                case SUCCESS: {
                    showProgressDialog(false);
                    if (result.data != null && CHB_1016.Response.RETURN_CODE_SUCC.equals(result.data.getRtCd())) {
                        // 주카드 등록 성공 - 카드 목록 갱신
                        getCardList();
                    } else {
                        // 주카드 등록 실패 또는 연동 데이터 없음. TODO 예외 처리
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

        // 결제수단 삭제 Observe 등록.
        chbViewModel.getRES_CHB_1017().observe(this, (result) -> {
            Log.d("FID", "test :: RES_CHB_1017 :: status=" + result.status);
            switch (result.status) {
                case LOADING: {
                    showProgressDialog(true);
                    break;
                }
                case SUCCESS: {
                    showProgressDialog(false);
                    if (result.data != null && CHB_1017.Response.RETURN_CODE_SUCC.equals(result.data.getRtCd())) {
                        // 카드 삭제 성공 - 카드 목록 갱신
                        getCardList();
                    } else {
                        // 카드 삭제 실패 또는 연동 데이터 없음. TODO 예외 처리
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
        /*
        카드 정렬 기능이 삭제 되어 해당 기능 주석 처리.
        ItemMoveCallback callback = new ItemMoveCallback(adapter);
        callback.setIsLongPressDragEnabled(false);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(ui.rvCardList);
        adapter.setItemTouchHelper(touchHelper);
         */

        ui.rvCardList.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(CardManageActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        ui.rvCardList.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(CardManageActivity.this, DividerItemDecoration.VERTICAL);
        ui.rvCardList.addItemDecoration(dividerItemDecoration);
    }

    /**
     * 카드 정보 조회 함수.
     */
    private void getCardList() {
        chbViewModel.reqCHB1015(new CHB_1015.Request(APPIAInfo.SM_CGRV02_P01.getId()));
    }

    /**
     * 카드 목록 업데이트 함수.
     *
     * @param list 목록에 표시할 카드 정보 리스트.
     */
    private void updateCardList(List<PaymtCardVO> list) {
        if (list.size() > 0) {
            ui.rvCardList.setVisibility(View.VISIBLE);
            ui.tvEmptyList.setVisibility(View.GONE);
            adapter.setRows(list);
            adapter.notifyDataSetChanged();
        } else {
            ui.rvCardList.setVisibility(View.GONE);
            ui.tvEmptyList.setVisibility(View.VISIBLE);
        }
        updateCardCount(adapter.getItemCount());
    }

    /**
     * 카드 총 갯수 업데이트 함수.
     *
     * @param count 카드 총 갯수.
     */
    private void updateCardCount(int count) {
        ui.tvCardTotalCount.setText(String.format("총 %,3d개", count));
    }

    /**
     * 카드 추가 함수.
     */
    private void addCard() {
        // TODO 카드 추가 관련 로직이 필요. 카드 선택 Full WebView 화면 추가.
    }

    /**
     * 카드 삭제 함수.
     *
     * @param vo 삭제할 카드 정보 데이터.
     */
    private void deleteCard(PaymtCardVO vo) {
        Log.d("FID", "test :: deleteCard :: vo=" + vo);

        if ("Y".equalsIgnoreCase(vo.getMainCardYN())) {
            // 삭제할 카드가 주 사용 카드로 설정된 경우 - 다음 순서의 카드를 주 카드로 설정하는 코드 추가.

        } else {
            // 삭제할 카드가 주 사용 카드가 아닌 경우 - 카드 삭제만 진행.
            // TODO 팝업 표시 필요할 수 있음.
            chbViewModel.reqCHB1017(new CHB_1017.Request(APPIAInfo.SM_CGRV02_P01.getId(), vo.getCardId()));
        }
    }

    /**
     * 주 사용 설정 함수.
     *
     * @param vo 주사용 설정할 카드 정보 데이터.
     */
    private void setFavoritCard(PaymtCardVO vo) {
        Log.d("FID", "test :: favoritCard :: vo=" + vo);
        chbViewModel.reqCHB1016(new CHB_1016.Request(APPIAInfo.SM_CGRV02_P01.getId(), vo.getCardId()));
    }
} // end of class CardManageActivity
