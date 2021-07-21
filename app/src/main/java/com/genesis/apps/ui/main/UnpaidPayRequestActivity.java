package com.genesis.apps.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.CHB_1015;
import com.genesis.apps.comm.model.api.gra.DTW_1003;
import com.genesis.apps.comm.model.api.gra.DTW_1004;
import com.genesis.apps.comm.model.api.gra.DTW_1007;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.carlife.PaymtCardVO;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.comm.viewmodel.CHBViewModel;
import com.genesis.apps.comm.viewmodel.DTWViewModel;
import com.genesis.apps.databinding.ActivityUnpaidPayRequestBinding;
import com.genesis.apps.ui.common.activity.BluewalnutWebActivity;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.bottom.BottomListDialog;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;
import com.genesis.apps.ui.main.service.CardManageActivity;
import com.genesis.apps.ui.main.service.CardManageTestActivity;

import java.util.List;

public class UnpaidPayRequestActivity extends SubActivity<ActivityUnpaidPayRequestBinding> {

    private DTWViewModel dtwViewModel;
    private CHBViewModel chbViewModel;
    private PaymtCardVO selectedCardVo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unpaid_pay_request);
        getDataFromIntent();
        setViewModel();
        setObserver();
        dtwViewModel.reqDTW1003(new DTW_1003.Request(APPIAInfo.PAY04_PAY01.getId()));
    }

    /**
     * 결제 수단 정보 표시
     */
    private void initLayoutCardItem(List<PaymtCardVO> cardInfo) {

        PaymtCardVO cardVo = null;

        if (cardInfo != null && cardInfo.size() > 0) {

            for (PaymtCardVO vo : cardInfo) {
                if (cardVo == null)
                    cardVo = vo;

                if (StringUtil.isValidString(vo.getMainCardYN()).equalsIgnoreCase(VariableType.COMMON_MEANS_YES)) {
                    cardVo = vo;
                    break;
                }

            }

            ui.lSelectedPaymtCard.setTag(cardInfo);
        }

        updateCardItem(cardVo);
    }

    private void updateCardItem(PaymtCardVO cardVO) {

        selectedCardVo = cardVO;

        if (cardVO != null && !TextUtils.isEmpty(cardVO.getCardName())) {
            ui.btnCardReg.lWhole.setVisibility(View.GONE);
            ui.btnCardMgmt.lWhole.setVisibility(View.VISIBLE);
            ui.lNoregPaymtCard.setVisibility(View.GONE);
            ui.lSelectedPaymtCard.setVisibility(View.VISIBLE);
            ui.lSelectedPaymtCard.setText(cardVO.getCardName());
            return;
        }

        ui.btnCardReg.lWhole.setVisibility(View.VISIBLE);
        ui.btnCardMgmt.lWhole.setVisibility(View.GONE);
        ui.lNoregPaymtCard.setVisibility(View.VISIBLE);
        ui.lSelectedPaymtCard.setVisibility(View.GONE);
    }

    @Override
    public void onClickCommon(View v) {
        switch (v.getId()) {
            case R.id.btn_card_reg:
            case R.id.btn_card_mgmt:
                //startActivitySingleTop(new Intent(this, CardManageActivity.class), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_VERTICAL_SLIDE);
                startActivitySingleTop(new Intent(this, CardManageTestActivity.class), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_VERTICAL_SLIDE);
                break;
            case R.id.l_selected_paymt_card:
                showDialogCardList(dtwViewModel.getPaymtCardList());
                break;
            case R.id.btn_paymt:

                if (selectedCardVo == null) {
                    SnackBarUtil.show(this, getString(R.string.service_charge_btr_err_15));
                    return;
                }

                if (ui.getData() != null) {

                    String userAgentString = new WebView(this).getSettings().getUserAgentString();

                    dtwViewModel.reqDTW1004(new DTW_1004.Request(APPIAInfo.PAY04_PAY01.getId(),
                            ui.getData().getPayTrxId(),
                            selectedCardVo.getCardType(),
                            selectedCardVo.getCardId(),
                            selectedCardVo.getCardCoCode(),
                            selectedCardVo.getCardNo(),
                            userAgentString
                    ));
                }

                break;
        }
    }

    /**
     * 결제 수단 카드 리스트 팝업 호출
     *
     * @param list
     */
    private void showDialogCardList(final List<PaymtCardVO> list) {
        try {
            final BottomListDialog bottomListDialog = new BottomListDialog(this, R.style.BottomSheetDialogTheme);
            bottomListDialog.setOnDismissListener(dialogInterface -> {
                String result = bottomListDialog.getSelectItem();
                if (!TextUtils.isEmpty(result)) {
                    updateCardItem(dtwViewModel.getPaymtCardVO(result));
                }
            });

            bottomListDialog.setDatas(dtwViewModel.getPaymtCardNm(list));
            bottomListDialog.setTitle(getString(R.string.service_charge_btr_07));
            bottomListDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        ui.setActivity(this);

        dtwViewModel = new ViewModelProvider(this).get(DTWViewModel.class);
        chbViewModel = new ViewModelProvider(this).get(CHBViewModel.class);
    }

    @Override
    public void setObserver() {
        dtwViewModel.getRES_DTW_1003().observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    showProgressDialog(false);
                    if (result.data != null && result.data.getRtCd().equalsIgnoreCase("0000")) {

                        // 미수금 정보 표시
                        if (result.data.getUnpayInfo() != null)
                            ui.setData(result.data.getUnpayInfo());


                        // 결제 수단 정보 표시
                        initLayoutCardItem(result.data.getCardList());
                        break;
                    }
                default:
                    showProgressDialog(false);
                    String serverMsg = "";
                    try {
                        serverMsg = result.data.getRtMsg();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        SnackBarUtil.show(this, TextUtils.isEmpty(serverMsg) ? getString(R.string.r_flaw06_p02_snackbar_1) : serverMsg);
                    }
                    break;
            }
        });

        dtwViewModel.getRES_DTW_1004().observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    showProgressDialog(false);
                    if (result.data != null && result.data.getRtCd().equalsIgnoreCase("0000") && result.data.getPaymentFormData() != null) {
                        showProgressDialog(false);
                        startActivitySingleTop(new Intent(this, BluewalnutWebActivity.class)
                                        .putExtra(KeyNames.KEY_NAME_CONTENTS_VO, result.data.getPaymentFormData())
                                , RequestCodes.REQ_CODE_ACTIVITY.getCode()
                                , VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                        break;
                    }
                default:
                    showProgressDialog(false);
                    String serverMsg = "";
                    try {
                        serverMsg = result.data.getRtMsg();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        SnackBarUtil.show(this, TextUtils.isEmpty(serverMsg) ? getString(R.string.r_flaw06_p02_snackbar_1) : serverMsg);
                    }
                    break;
            }
        });

        dtwViewModel.getRES_DTW_1007().observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    showProgressDialog(false);
                    if (result.data != null && StringUtil.isValidString(result.data.getRtCd()).equalsIgnoreCase("0000")) {
                        showProgressDialog(false);
                        startActivitySingleTop(new Intent(this, UnpaidPayResultActivity.class)
                                        .putExtra(KeyNames.KEY_NAME_CONTENTS_VO, result.data)
                                , RequestCodes.REQ_CODE_ACTIVITY.getCode()
                                , VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                        break;
                    }
                default:
                    showProgressDialog(false);
                    String serverMsg = "";
                    try {
                        serverMsg = result.data.getRtMsg();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        SnackBarUtil.show(this, TextUtils.isEmpty(serverMsg) ? getString(R.string.r_flaw06_p02_snackbar_1) : serverMsg);
                    }
                    break;
            }
        });

        chbViewModel.getRES_CHB_1015().observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    showProgressDialog(false);
                    if (result.data != null) {
                        initLayoutCardItem(result.data.getCardList());
                    }
                    break;
                default:
                    showProgressDialog(false);
                    break;
            }
        });
    }

    @Override
    public void getDataFromIntent() {
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ResultCodes.REQ_CODE_PAYMENT_CARD_CHANGE.getCode()) {
            // 간편결제카드 관리 변동 사항 있을 때
            chbViewModel.reqCHB1015(new CHB_1015.Request(APPIAInfo.SM_CGRV02.getId()));
        } else if (resultCode == ResultCodes.REQ_CODE_BLUEWALNUT_PAYMENT_FINISH.getCode()) {
            // 블루월넛 결제 완료 시
            String payTrxId = data.getStringExtra(KeyNames.KEY_NAME_PAY_TRXID);
            if(!TextUtils.isEmpty(payTrxId))
                    dtwViewModel.reqDTW1007(new DTW_1007.Request(APPIAInfo.PAY04_PAY01.getId(), payTrxId));
            else
                SnackBarUtil.show(this, getString(R.string.r_flaw06_p02_snackbar_1));

        } else if(resultCode == ResultCodes.REQ_CODE_UNPAID_PAYMT_FINISH.getCode()) {
            // 미수금 결제 완료되어 화면 종료 시
            exitPage(new Intent(), ResultCodes.REQ_CODE_UNPAID_PAYMT_FINISH.getCode());
        }
    }

    @Override
    public void onBackPressed() {
        dialogExit();
    }

    @Override
    public void onBackButton(){
        dialogExit();
    }

    private void dialogExit(){
        MiddleDialog.dialogServiceRemoteTwoButton(this, R.string.pay04_pay01_p01_1, R.string.pay04_pay01_p01_2,() -> {
            exitPage("", ResultCodes.REQ_CODE_UNPAID_PAYMT_CANCEL.getCode());
        }, () -> {

        });
    }
}
