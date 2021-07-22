package com.genesis.apps.ui.main.service;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.CHB_1015;
import com.genesis.apps.comm.model.api.gra.CHB_1016;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.carlife.PaymtCardVO;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.comm.viewmodel.CHBViewModel;
import com.genesis.apps.databinding.ActivityCardManageBinding;
import com.genesis.apps.databinding.ActivityCardManageTestBinding;
import com.genesis.apps.ui.common.activity.SubActivity;

import java.util.List;

public class CardManageTestActivity extends SubActivity<ActivityCardManageTestBinding> {

    private CHBViewModel chbViewModel;

    private CardManageTestListAdapter adapter;

    private String deleteCardId;

    private PaymtCardVO mainCardID;


    private CardDeleteReqTy currCardDelReqType;  // 현재 카드 삭제 요청 타입


    public enum CardDeleteReqTy {
        SUB_CARD_DELETE,        // 메인 이외 카드 삭제 요청 하는 경우
        ONLY_MAIN_CARD_DELETE,  // 메인 카드 삭제 요청 하는 경우
        MAIN_CARD_DELETE_AND_CHANGE     // 메인 카드를 삭제하고 다른 카드를 메인 카드로 설정 하는 경우
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_manage_test);
        setViewModel();
        getDataFromIntent();
        setObserver();
        initialize();


    }

    @Override
    protected void onResume() {
        super.onResume();

        getCardList();
    }
    @Override
    public void onClickCommon(View v) {
        Object tag = v.getTag();
        switch (v.getId()) {
            // 카드 추가 버튼
            case R.id.tv_add_card: {
                addCard();
                break;
            }
            case R.id.card_manage_btn: {
                if (v instanceof TextView) {
                    String text = ((TextView) v).getText().toString();
                    if (StringUtil.isValidString(text).equalsIgnoreCase(getString(R.string.pay03_5)))
                        addCard();
                    else
                        reqMemberReg();
                }
                break;
            }
            // 카드 목록 이벤트 - 카드 삭제 버튼
            case R.id.btn_delete: {
                if (tag instanceof PaymtCardVO) {
                    deleteCard((PaymtCardVO) tag);
                }
                break;
            }
            // 카드 목록 이벤트 - 카드 주사용 등록/해제 버튼.
            case R.id.tv_main_card: {
                if (tag instanceof PaymtCardVO) {
                    setFavoriteCard((PaymtCardVO) tag);
                }
                break;
            }
        }
    }
    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        chbViewModel = new ViewModelProvider(CardManageTestActivity.this).get(CHBViewModel.class);
    }

    @Override
    public void setObserver() {
        // 결제 수단 조회 Observe 등록.
        chbViewModel.getRES_CHB_1015().observe(this, (result) -> {
            switch (result.status) {
                case LOADING: {
                    showProgressDialog(true);
                    break;
                }
                case SUCCESS: {
                    showProgressDialog(false);
                    if (result.data != null) {
                        if (StringUtil.isValidString(result.data.getSignInYN()).equalsIgnoreCase(VariableType.COMMON_MEANS_YES)) {
                            updateCardList(result.data.getCardList());
                        } else {
                            setMemeberRegUI(true);
                        }
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
            switch (result.status) {
                case LOADING: {
                    showProgressDialog(true);
                    break;
                }
                case SUCCESS: {
                    showProgressDialog(false);
                    if(result.data != null && StringUtil.isValidString(result.data.getRtCd()).equalsIgnoreCase(CHB_1016.Response.RETURN_CODE_SUCC)){
                        // 주 카드만 변
                        SnackBarUtil.show(CardManageTestActivity.this, getString(R.string.pay03_10));
                        mainCardID.setMainCardYN("Y");

                    }




                    updateCardList(chbViewModel.getRES_CHB_1015().getValue().data.getCardList());
                    break;

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
            switch (result.status) {
                case LOADING: {
                    showProgressDialog(true);
                    break;
                }
                case SUCCESS: {
                    showProgressDialog(false);

                        break;

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
    }

    @Override
    public void getDataFromIntent() {


    }
    private void initialize() {
        ui.setActivity(CardManageTestActivity.this);
        adapter = new CardManageTestListAdapter(this.onSingleClickListener);

        ui.rvCardList.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(CardManageTestActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        ui.rvCardList.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(CardManageTestActivity.this, layoutManager.getOrientation());
        dividerItemDecoration.setDrawable(new ColorDrawable(getColor(R.color.x_e5e5e5)));
        ui.rvCardList.addItemDecoration(dividerItemDecoration);

    }

    private void getCardList() {
        chbViewModel.reqCHB1015(new CHB_1015.Request(APPIAInfo.SM_CGRV02_P01.getId()));
    }


    private void updateCardList(List<PaymtCardVO> list) {


        if (list.size() > 0) {
            ui.rvCardList.setVisibility(View.VISIBLE);
            setEmptyCardUI(false);
            adapter.setRows(list);
            adapter.notifyDataSetChanged();
        } else {
            ui.rvCardList.setVisibility(View.GONE);
            setEmptyCardUI(true);

        }
        updateCardCount(adapter.getItemCount());


    }


    private void updateCardCount(int count) {
        ui.tvCardCnt.setText(String.format("총 %d개", count));
    }


    private void setMemeberRegUI(boolean visibility) {
        ui.tvAddCard.setVisibility(visibility ? View.GONE : View.VISIBLE);
        ui.lCardInfo.setVisibility(visibility ? View.VISIBLE : View.GONE);
        ui.cardInfo1.setText(R.string.pay03_6);
        ui.cardInfo2.setText(R.string.pay03_7);
        ui.cardInfo3.setVisibility(View.VISIBLE);
        ui.cardInfo3.setText(R.string.pay03_8);
        ui.cardManageBtn.setText(R.string.pay03_9);
    }


    private void setEmptyCardUI(boolean visibility) {
        ui.tvAddCard.setVisibility(visibility ? View.GONE : View.VISIBLE);
        ui.lCardInfo.setVisibility(visibility ? View.VISIBLE : View.GONE);
        ui.cardInfo1.setText(R.string.pay03_3);
        ui.cardInfo2.setText(R.string.pay03_4);
        ui.cardInfo3.setVisibility(View.GONE);
        ui.cardManageBtn.setText(R.string.pay03_5);
    }

    private void reqMemberReg() {
        // 회원 가입 처리

    }


    private void addCard() {

    }


    private void deleteCard(PaymtCardVO vo) {


    }


    private void setFavoriteCard(PaymtCardVO vo) {
        mainCardID = vo;
        chbViewModel.reqCHB1016(new CHB_1016.Request(APPIAInfo.SM_CGRV02_P01.getId(), mainCardID.getCardId()));

    }
} // end of class CardManageActivity
