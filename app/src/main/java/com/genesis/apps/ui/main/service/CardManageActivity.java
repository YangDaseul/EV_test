package com.genesis.apps.ui.main.service;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.carlife.PaymtCardVO;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.comm.viewmodel.CHBViewModel;
import com.genesis.apps.databinding.ActivityCardManageBinding;
import com.genesis.apps.ui.common.activity.BluewalnutWebActivity;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;

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

    private final int MIN_CARD_COUNT = 1;
    private CHBViewModel chbViewModel;

    private CardManageListAdapter adapter;

    private String targetDelCardId = null;
    private CardDeleteReqTy currCardDelReqType;  // 현재 카드 삭제 요청 타입

    boolean init = true;

    public enum CardDeleteReqTy {
        SUB_CARD_DELETE,        // 메인 이외 카드 삭제 요청 하는 경우
        ONLY_MAIN_CARD_DELETE,  // 메인 카드 삭제 요청 하는 경우
        MAIN_CARD_DELETE_AND_CHANGE     // 메인 카드를 삭제하고 다른 카드를 메인 카드로 설정 하는 경우
    }

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
                if(chbViewModel.isSignInYN())
                    addCard();
                else
                    reqMemberReg();
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
                        break;
                    }
                }
                default: {
                    showProgressDialog(false);
                    String serverMsg = "";
                    try {
                        serverMsg = result.data.getRtMsg();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (result.data != null && StringUtil.isValidString(result.data.getRtCd()).equalsIgnoreCase("2005"))//조회된 정보가 없을 경우 에러메시지 출력하지 않음
                        return;

                    if (TextUtils.isEmpty(serverMsg)) {
                        serverMsg = getString(R.string.r_flaw06_p02_snackbar_1);
                    }
                    SnackBarUtil.show(this, serverMsg);
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
                        if (currCardDelReqType != null && currCardDelReqType == CardDeleteReqTy.MAIN_CARD_DELETE_AND_CHANGE && !TextUtils.isEmpty(targetDelCardId))
                            chbViewModel.reqCHB1017(new CHB_1017.Request(APPIAInfo.SM_CGRV02_P01.getId(), targetDelCardId));
                        else
                            getCardList();

                        break;
                    }
                }
                default: {
                    showProgressDialog(false);
                    String serverMsg = "";
                    try {
                        serverMsg = result.data.getRtMsg();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (TextUtils.isEmpty(serverMsg)) {
                        serverMsg = getString(R.string.r_flaw06_p02_snackbar_1);
                    }
                    SnackBarUtil.show(this, serverMsg);
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
                        targetDelCardId = null;
                        // 카드 삭제 성공 - 카드 목록 갱신
                        getCardList();

                        break;
                    }
                }
                default: {
                    targetDelCardId = null;

                    showProgressDialog(false);
                    String serverMsg = "";
                    try {
                        serverMsg = result.data.getRtMsg();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (TextUtils.isEmpty(serverMsg)) {
                        serverMsg = getString(R.string.r_flaw06_p02_snackbar_1);
                    }
                    SnackBarUtil.show(this, serverMsg);
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

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(CardManageActivity.this, layoutManager.getOrientation());
        dividerItemDecoration.setDrawable(new ColorDrawable(getColor(R.color.x_e5e5e5)));
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

        if (!init && adapter.getItemCount() != list.size()) {
            // 카드 목록이 업데이트 되는 경우
            setResult(ResultCodes.REQ_CODE_PAYMENT_CARD_CHANGE.getCode());
        }

        if (list.size() > 0) {
            ui.rvCardList.setVisibility(View.VISIBLE);
            ui.tvEmptyList.setVisibility(View.GONE);
            adapter.setRows(list);
            adapter.notifyDataSetChanged();
        } else {
            ui.rvCardList.setVisibility(View.GONE);
            ui.tvEmptyList.setVisibility(View.VISIBLE);

            // 기존에 있던 목록 삭제
            if (adapter.getItemCount() > 0)
                adapter.clear();

        }
        updateCardCount(adapter.getItemCount());

        if(init) init = false;
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
     * 회원 가입 요청 함수
     */
    private void reqMemberReg() {
        // 회원 가입 처리
        MiddleDialog.dialogBlueWalnutSingIn(this, () -> {
            startActivitySingleTop(new Intent(this, BluewalnutWebActivity.class).putExtra(KeyNames.KEY_NAME_PAGE_TYPE, VariableType.EASY_PAY_WEBVIEW_TYPE_MEMBER_REG), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_VERTICAL_SLIDE);
        }, () -> {

        });
    }

    /**
     * 카드 추가 함수.
     */
    private void addCard() {
        startActivitySingleTop(new Intent(this, BluewalnutWebActivity.class).putExtra(KeyNames.KEY_NAME_PAGE_TYPE, VariableType.EASY_PAY_WEBVIEW_TYPE_CARD_REG), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_VERTICAL_SLIDE);
    }

    /**
     * 카드 삭제 함수.
     *
     * @param vo 삭제할 카드 정보 데이터.
     */
    private void deleteCard(PaymtCardVO vo) {
        Log.d("FID", "test :: deleteCard :: vo=" + vo);

        targetDelCardId = vo.getCardId();

        if ("Y".equalsIgnoreCase(vo.getMainCardYN())) {
            // 삭제할 카드가 주 사용 카드로 설정된 경우 - 다음 순서의 카드를 주 카드로 설정하는 코드 추가.
            List<PaymtCardVO> items = adapter.getItems();
            if (items.size() > MIN_CARD_COUNT && items.get(MIN_CARD_COUNT) != null) {
                // 주 결제 카드 이외 카드 있는 경우, 카드가 2개 이상일 경우
                currCardDelReqType = CardDeleteReqTy.MAIN_CARD_DELETE_AND_CHANGE;

                // 주 결제 카드 삭제 및 변경 안내 팝업 표시
                MiddleDialog.dialogDeletePayCard01(this, () -> {
                    setFavoritCard(items.get(MIN_CARD_COUNT));
                }, () -> {

                });
            } else {
                // 주 결제 카드만 존재하는 경우, 카드가 1개인 경우
                currCardDelReqType = CardDeleteReqTy.ONLY_MAIN_CARD_DELETE;

                // 주 결제 카드 삭제 안내 팝업 표시
                MiddleDialog.dialogDeletePayCard02(this, () -> {
                    chbViewModel.reqCHB1017(new CHB_1017.Request(APPIAInfo.SM_CGRV02_P01.getId(), targetDelCardId));
                }, () -> {

                });
            }
        } else {
            // 삭제할 카드가 주 사용 카드가 아닌 경우 - 카드 삭제만 진행.
            currCardDelReqType = CardDeleteReqTy.SUB_CARD_DELETE;

            chbViewModel.reqCHB1017(new CHB_1017.Request(APPIAInfo.SM_CGRV02_P01.getId(), targetDelCardId));
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
