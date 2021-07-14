package com.genesis.apps.ui.main.service;

import android.content.Intent;
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
 * <p>
 * 기능
 * 1. 결제 카드 삭제.
 * 2. 결제 카드 주사용 등록.
 * 3. 카드 추가.
 *
 * @author Ki-man Kim
 * @since 2021-04-02
 */
public class CardManageTestActivity extends SubActivity<ActivityCardManageBinding> {

    private final int MIN_CARD_COUNT = 1;
    private CHBViewModel chbViewModel;

    private CardManageTestListAdapter adapter;

    /**
     * 주카드 설정 요청한 카드 정보.
     */
    private PaymtCardVO favoritPaymtCard;

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
        setContentView(R.layout.activity_card_manage_test);
        setViewModel();
        getDataFromIntent();
        setObserver();
        initialize();


    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    /****************************************************************************************************
     * Override Method - Event
     ****************************************************************************************************/
    @Override
    public void onClickCommon(View v) {
        Object tag = v.getTag();
        switch (v.getId()) {

        }
    }

    /****************************************************************************************************
     * Override Method
     ****************************************************************************************************/
    @Override
    public void setViewModel() {

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


    }

    /**
     * 카드 정보 조회 함수.
     */
    private void getCardList() {

    }

    /**
     * 카드 목록 업데이트 함수.
     *
     * @param list 목록에 표시할 카드 정보 리스트.
     */
    private void updateCardList(List<PaymtCardVO> list) {


    }

    /**
     * 카드 총 갯수 업데이트 함수.
     *
     * @param count 카드 총 갯수.
     */
    private void updateCardCount(int count) {

    }

    /**
     * 간편결제 가입 필요 레이아웃 표시
     *
     * @param visibility
     */
    private void setMemeberRegUI(boolean visibility) {

    }

    /**
     * 등록된 결제카드 없음 레이아웃 표시
     *
     * @param visibility
     */
    private void setEmptyCardUI(boolean visibility) {

    }

    /**
     * 회원 가입 요청 함수
     */
    private void reqMemberReg() {

    }

    /**
     * 카드 추가 함수.
     */
    private void addCard() {

    }

    /**
     * 카드 삭제 함수.
     *
     * @param vo 삭제할 카드 정보 데이터.
     */
    private void deleteCard(PaymtCardVO vo) {



    }

    /**
     * 주 사용 설정 함수.
     *
     * @param vo 주사용 설정할 카드 정보 데이터.
     */
    private void setFavoritCard(PaymtCardVO vo) {
        
    }
} // end of class CardManageActivity
