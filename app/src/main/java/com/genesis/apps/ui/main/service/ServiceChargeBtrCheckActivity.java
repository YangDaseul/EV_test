package com.genesis.apps.ui.main.service;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.CHB_1008;
import com.genesis.apps.comm.model.api.gra.CHB_1009;
import com.genesis.apps.comm.model.api.gra.CHB_1010;
import com.genesis.apps.comm.model.api.gra.CHB_1015;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.model.vo.carlife.BookingDateVO;
import com.genesis.apps.comm.model.vo.carlife.CarVO;
import com.genesis.apps.comm.model.vo.carlife.LotVO;
import com.genesis.apps.comm.model.vo.carlife.MembershipVO;
import com.genesis.apps.comm.model.vo.carlife.OptionVO;
import com.genesis.apps.comm.model.vo.carlife.OrderVO;
import com.genesis.apps.comm.model.vo.carlife.PaymtCardVO;
import com.genesis.apps.comm.model.vo.carlife.ReqInfoVO;
import com.genesis.apps.comm.net.ga.LoginInfoDTO;
import com.genesis.apps.comm.util.DateUtil;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.comm.viewmodel.CHBViewModel;
import com.genesis.apps.databinding.ActivityServiceChargeBtrCheckBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.bottom.BottomListDialog;
import com.genesis.apps.ui.common.dialog.bottom.DialogCalendarChargeBtr;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ServiceChargeBtrCheckActivity extends SubActivity<ActivityServiceChargeBtrCheckBinding> {

    @Inject
    public LoginInfoDTO loginInfoDTO;

    private CHBViewModel chbViewModel;

    private VehicleVO mainVehicle;
    private CHB_1009.Response contentsVO;   // 픽업앤세차 신청 정보
    private String hpNo;    // 신청 핸드폰 번호
    private String carNo;    // 신청하는 차량 번호
    private String rsvtDate;    // 예약 희망 일시
    private String keyTransferType;    // 차량 키 전달 방식
    private LotVO lotVO;        // 위치 정보
    private PaymtCardVO selectedCardVo; // 결제 카드 정보

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResizeScreen();
        setContentView(R.layout.activity_service_charge_btr_check);
        getDataFromIntent();
        setViewModel();
        setObserver();
        initView();
    }

    private void initView() {
        try {
            //주 이용 차량 정보를 DB에서 GET
            mainVehicle = chbViewModel.getMainVehicleFromDB();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }

        ui.setData(contentsVO);
        ui.setRsvtDt(rsvtDate);

        setLayoutPriceItem();
        setLayoutCardItem();
    }

    /**
     * 가격 정보들 표시
     */
    private void setLayoutPriceItem() {
        if(contentsVO == null)
            return;

        // TODO : 서비스 금액 표시(충전 금액 - 충전 크레딧 포인트 + 탁송 금액 + 세차 금액)
//        ui.tvSvcPaymt.setText();
        // 충전 금액 표시
        ui.tvChargePaymt.setText(StringUtil.getPriceString(contentsVO.getProductPrice()));
        // 충전 크레딧 포인트 정보 표시
        setLayoutCreditPoint();
        // 탁송금액 표시
        int deliverPrice = getOptionVO(VariableType.SERVICE_CHARGE_BTR_OPT_TYPE_1).getOptionPrice();
        ui.tvDeliveryPaymt.setText(StringUtil.getPriceString(deliverPrice));
        // 세차금액 표시
        int carwashPrice = getOptionVO(VariableType.SERVICE_CHARGE_BTR_OPT_TYPE_2).getOptionPrice();
        ui.tvCarwashPaymt.setText(StringUtil.getPriceString(carwashPrice));
        ui.tvCarwashPrice.setText(StringUtil.getPriceString(carwashPrice));

        // 하단 결제 상세 내역 정보 표시
        // TODO 최종 결제 금액 표시
//        ui.tvPaymtAmt.setText();
        ui.lChargePrice.setContent(StringUtil.getPriceString(contentsVO.getProductPrice()));
        ui.lDeliveryPrice.setContent(StringUtil.getPriceString(deliverPrice));
    }

    /**
     * 충전 크레딧 정보 표시
     */
    private void setLayoutCreditPoint() {
        int creditBalance = 0;

        if(contentsVO.getStrafficInfo() != null && contentsVO.getStrafficInfo().getAvailableYN().equalsIgnoreCase(VariableType.COMMON_MEANS_YES)){
            creditBalance = contentsVO.getStrafficInfo().getBalance();
        }

        int discountPoint = creditBalance > contentsVO.getProductPrice() ? contentsVO.getProductPrice() : 0;
        ui.tvCreditPoint.setText(discountPoint > 0 ? StringUtil.getDiscountString(discountPoint) : getString(R.string.service_charge_btr_word_30));
        ui.tvCreditPointBalance.setText(String.format(Locale.getDefault(), getString(R.string.service_charge_btr_word_41), StringUtil.getPriceString(creditBalance)));
        ui.tvCreditPointInfo.setText(String.format(getString(R.string.service_charge_btr_msg_04), StringUtil.getPriceString(contentsVO.getProductPrice())));

        // 하단 결제 상세 내역 정보 표시
        ui.lCreditPoint.setContent(discountPoint > 0 ? StringUtil.getDiscountString(discountPoint) : getString(R.string.service_charge_btr_word_42));
    }

    /**
     * 결제 수단 정보 표시
     */
    private void setLayoutCardItem() {
        if (contentsVO != null && isSingIn()) {
            String selectedCardNm = getPaymtCardNm(contentsVO.getCardList());
            if (!TextUtils.isEmpty(selectedCardNm)) {
                ui.btnCardReg.lWhole.setVisibility(View.GONE);
                ui.btnCardMgmt.lWhole.setVisibility(View.VISIBLE);
                ui.lNoregPaymtCard.setVisibility(View.GONE);
                ui.lSelectedPaymtCard.setVisibility(View.VISIBLE);
                ui.lSelectedPaymtCard.setText(selectedCardNm);
                return;
            }
        }
        ui.btnCardReg.lWhole.setVisibility(View.VISIBLE);
        ui.btnCardMgmt.lWhole.setVisibility(View.GONE);
        ui.lNoregPaymtCard.setVisibility(View.VISIBLE);
        ui.lSelectedPaymtCard.setVisibility(View.GONE);
    }

    @Override
    public void onClickCommon(View v) {
        switch (v.getId()) {
            case R.id.btn_change_reserve_dtm:
                // 예약 희망일 변경 버튼
                requestPossibleTime();
                break;

            case R.id.l_svc_paymt:
                if (ui.lSvcPaymtDetail.getVisibility() == View.VISIBLE) {
                    ui.ivArrowImage.setImageResource(R.drawable.btn_arrow_open);
                    ui.lSvcPaymtDetail.setVisibility(View.GONE);
                    ui.ivSvcPaymtPadding.setVisibility(View.VISIBLE);
                } else {
                    ui.ivArrowImage.setImageResource(R.drawable.btn_arrow_close);
                    ui.lSvcPaymtDetail.setVisibility(View.VISIBLE);
                    ui.ivSvcPaymtPadding.setVisibility(View.GONE);
                }
                break;
            case R.id.l_paymt_amt:
                if (ui.lPaymtAmtDetail.getVisibility() == View.VISIBLE) {
                    ui.ivPaymtAmtArrow.setImageResource(R.drawable.btn_arrow_open);
                    ui.lPaymtAmtDetail.setVisibility(View.GONE);
                    ui.ivPaymtAmtPadding.setVisibility(View.VISIBLE);
                } else {
                    ui.ivPaymtAmtArrow.setImageResource(R.drawable.btn_arrow_close);
                    ui.lPaymtAmtDetail.setVisibility(View.VISIBLE);
                    ui.ivPaymtAmtPadding.setVisibility(View.GONE);
                }
                break;
            case R.id.cb_carwash_option:
                ui.tvCarwashPaymt.setVisibility(ui.cbCarwashOption.isChecked() ? View.VISIBLE : View.GONE);
                if (ui.cbCarwashOption.isChecked())
                    ui.lCarwashPrice.setContent(ui.tvCarwashPrice.getText().toString());
                else
                    ui.lCarwashPrice.setContent(getString(R.string.service_charge_btr_word_42));

                break;
            case R.id.btn_card_reg:
                if (isSingIn()) {
                    startActivitySingleTop(new Intent(this, CardManageActivity.class), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_VERTICAL_SLIDE);
                } else {
                    // 회원 가입 처리

                    MiddleDialog.dialogBlueWalnutSingIn(this, () -> {
                        // TODO : 웹뷰로 이동
                    }, () -> {

                    });
                }
                break;
            case R.id.btn_card_mgmt:
                //CardManageActivity
                startActivitySingleTop(new Intent(this, CardManageActivity.class), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_VERTICAL_SLIDE);
                break;
            case R.id.l_selected_paymt_card:
                showDialogCardList(contentsVO.getCardList());
                break;
            case R.id.btn_confirm:
                reqChargeBtrApply();
                break;
            default:
                break;
        }
    }

    private void reqChargeBtrApply(){

        int estimatedPaymentAmount = 0; // 최종 결제 금액
        List<OptionVO> optList = new ArrayList<>();
        optList.add(new OptionVO(getOptionVO(VariableType.SERVICE_CHARGE_BTR_OPT_TYPE_1).getOptionCode(), 1));

        if(ui.cbCarwashOption.isChecked())
            optList.add(new OptionVO(getOptionVO(VariableType.SERVICE_CHARGE_BTR_OPT_TYPE_2).getOptionCode(), 1));

        int membershipUsePoint = 0;
        List<MembershipVO> membershipVO = new ArrayList<>();
        if(contentsVO.getStrafficInfo() != null)
            membershipVO.add(new MembershipVO(VariableType.SERVICE_CHARGE_BTR_MEMBERSHIP_CODE_STRFF, contentsVO.getStrafficInfo().getCardNo(), membershipUsePoint));

        List<LotVO> lotVOList = new ArrayList<LotVO>();
        lotVOList.add(lotVO);

        chbViewModel.reqCHB1010(new CHB_1010.Request(APPIAInfo.SM_CGRV02.getId()
                , contentsVO.getTxid()
                , hpNo
                , new CarVO(mainVehicle.getVin(), carNo, mainVehicle.getMdlCd(), mainVehicle.getMdlNm(), mainVehicle.getXrclCtyNm())
                , new ReqInfoVO(rsvtDate, keyTransferType)
                , lotVOList
                , new OrderVO(contentsVO.getProductCode(), estimatedPaymentAmount, optList.size(), optList)
                , membershipVO
                , new PaymtCardVO(selectedCardVo.getCardId(), selectedCardVo.getCardNo(), selectedCardVo.getCardCoCode())));
    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        ui.setActivity(this);

        chbViewModel = new ViewModelProvider(this).get(CHBViewModel.class);
    }

    @Override
    public void setObserver() {
        chbViewModel.getRES_CHB_1008().observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    showProgressDialog(false);
                    if (result.data != null && result.data.getDailyBookingSlotList() != null && result.data.getDailyBookingSlotList().size() > 0) {
                        selectCalendar(result.data.getDailyBookingSlotList());
                        break;
                    }
                default:
                    String serverMsg = "";
                    try {
                        serverMsg = result.data.getRtMsg();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (TextUtils.isEmpty(serverMsg)) {
                            serverMsg = getString(R.string.r_flaw06_p02_snackbar_1);
                        }
                        SnackBarUtil.show(this, serverMsg);
                        showProgressDialog(false);
                    }
                    break;
            }
        });

        chbViewModel.getRES_CHB_1009().observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    showProgressDialog(false);
                    if (result.data != null) {
                        CHB_1009.Response temp = result.data;
                        if (result.data.getCardList() != null && result.data.getCardList().size() > 0) {
                            for (PaymtCardVO cardVO : temp.getCardList()) {
                                if (!cardVO.getCardType().equalsIgnoreCase("C"))
                                    temp.getCardList().remove(cardVO);
                            }
                        }
                        contentsVO = temp;
                        ui.setData(contentsVO);

                        setLayoutCardItem();
                        break;
                    }
                default:
                    String serverMsg = "";
                    try {
                        serverMsg = result.data.getRtMsg();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (TextUtils.isEmpty(serverMsg)) {
                            serverMsg = getString(R.string.r_flaw06_p02_snackbar_1);
                        }
                        SnackBarUtil.show(this, serverMsg);
                        showProgressDialog(false);
                    }
                    break;
            }
        });

        chbViewModel.getRES_CHB_1010().observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    showProgressDialog(false);
                    if (result.data != null) {
                        Intent intent = new Intent(this, ServiceChargeBtrResultActivity.class);
                        intent.putExtra(KeyNames.KEY_NAME_CHB_CAR_NO, carNo);
                        intent.putExtra(KeyNames.KEY_NAME_CHB_RSVT_DT, rsvtDate);
                        intent.putExtra(KeyNames.KEY_NAME_CHB_ADDRESS, lotVO.getAddress());

                        if(ui.cbCarwashOption.isChecked())
                            intent.putExtra(KeyNames.KEY_NAME_CHB_OPTION_TY, VariableType.SERVICE_CHARGE_BTR_OPT_TYPE_2);

                        startActivitySingleTop(intent, RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_VERTICAL_SLIDE);
                        break;
                    }
                default:
                    String serverMsg = "";
                    try {
                        serverMsg = result.data.getRtMsg();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (TextUtils.isEmpty(serverMsg)) {
                            serverMsg = getString(R.string.r_flaw06_p02_snackbar_1);
                        }
                        SnackBarUtil.show(this, serverMsg);
                        showProgressDialog(false);
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
                        contentsVO.setSignInYN(result.data.getSignInYN());
                        contentsVO.setCardList(result.data.getCardList());
                        setLayoutCardItem();
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
        try {
            hpNo = getIntent().getStringExtra(KeyNames.KEY_NAME_CHB_HP_NO);
            carNo = getIntent().getStringExtra(KeyNames.KEY_NAME_CHB_CAR_NO);
            rsvtDate = getIntent().getStringExtra(KeyNames.KEY_NAME_CHB_RSVT_DT);
            keyTransferType = getIntent().getStringExtra(KeyNames.KEY_NAME_CHB_KEY_TRANS_TY);
            lotVO = (LotVO) getIntent().getSerializableExtra(KeyNames.KEY_NAME_CHB_LOT_VO);
            contentsVO = (CHB_1009.Response) getIntent().getSerializableExtra(KeyNames.KEY_NAME_CHB_CONTENTS_VO);
        } catch (Exception e) {

        }
    }


    /**
     * 옵션 VO 조회
     * @param optTy
     * @return
     */
    private OptionVO getOptionVO(String optTy) {

        if (!TextUtils.isEmpty(optTy)) {
            for (OptionVO optVo : contentsVO.getOptionList()) {
                if (optVo.getOptionType().equalsIgnoreCase(optTy)) {
                    return optVo;
                }
            }
        }

        return null;
    }

    /**
     * 블루월넛 가입 여부 체크
     *
     * @return
     */
    private boolean isSingIn() {
        if (!TextUtils.isEmpty(contentsVO.getSignInYN())) {
            return contentsVO.getSignInYN().equalsIgnoreCase(VariableType.COMMON_MEANS_YES) ? true : false;
        }
        return false;
    }

    /**
     * 디폴트 결제 수단 카드명 조회
     * @param cardList
     * @return
     */
    private String getPaymtCardNm(List<PaymtCardVO> cardList) {
        String cardNm = null;
        if (cardList != null) {
            for (int i = 0; i < cardList.size(); i++) {
                if (i == 0)
                    selectedCardVo = cardList.get(i);

                if (cardList.get(i).getMainCardYN().equalsIgnoreCase("Y"))
                    selectedCardVo = cardList.get(i);
            }

            cardNm = selectedCardVo.getCardName();
        }
        return cardNm;
    }

    /**
     * 결제 수단 카드 VO 조회
     * @param cardNm
     * @return
     */
    private PaymtCardVO findPaymtCardVo(String cardNm) {
        for (PaymtCardVO cardVO : contentsVO.getCardList()) {
            if (cardVO.getCardName().equalsIgnoreCase(cardNm))
                return cardVO;
        }
        return null;
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
                    selectedCardVo = findPaymtCardVo(result);
                    ui.lSelectedPaymtCard.setText(result);
                }
            });
            bottomListDialog.setDatas(chbViewModel.getPaymtCardNm(list));
            bottomListDialog.setTitle(getString(R.string.service_charge_btr_07));
            bottomListDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 예약 가능 일시 조회
     */
    private void requestPossibleTime() {
        Calendar minCalendar = Calendar.getInstance(Locale.getDefault());
        minCalendar.add(Calendar.DATE, 1);
        Calendar maxCalendar = Calendar.getInstance(Locale.getDefault());
        maxCalendar.add(Calendar.DATE, 5);

        chbViewModel.reqCHB1008(new CHB_1008.Request(APPIAInfo.SM_CGRV02.getId(),
                DateUtil.getDate(minCalendar.getTime(), DateUtil.DATE_FORMAT_yyyyMMdd),
                DateUtil.getDate(maxCalendar.getTime(), DateUtil.DATE_FORMAT_yyyyMMdd)
        ));
    }

    /**
     * 예약 희망일 선택 팝업 호출
     *
     * @param list
     */
    private void selectCalendar(List<BookingDateVO> list) {
        DialogCalendarChargeBtr dialogCalendar = new DialogCalendarChargeBtr(this, R.style.BottomSheetDialogTheme, onSingleClickListener);
        dialogCalendar.setOnDismissListener(dialogInterface -> {
            Calendar calendar = dialogCalendar.calendar;
            if (calendar != null) {
                rsvtDate = DateUtil.getDate(calendar.getTime(), DateUtil.DATE_FORMAT_yyyyMMdd);
                rsvtDate += dialogCalendar.getSelectBookingTime();

                ui.setRsvtDt(rsvtDate);

                // 픽업앤충전 신청 전문 요청
                chbViewModel.reqCHB1009(new CHB_1009.Request(APPIAInfo.SM_CGRV01.getId(),
                        mainVehicle.getVin(),
                        mainVehicle.getMdlCd(),
                        rsvtDate,
                        lotVO
                ));
            }
        });

        dialogCalendar.setCalendarMaximum(Calendar.getInstance(Locale.getDefault()));
        dialogCalendar.setTitle(getString(R.string.service_charge_btr_03));
        Calendar minCalendar = Calendar.getInstance(Locale.getDefault());
        minCalendar.add(Calendar.DATE, 1);
        Calendar maxCalendar = Calendar.getInstance(Locale.getDefault());
        maxCalendar.add(Calendar.DATE, 5);

        dialogCalendar.setCalendarMinimum(minCalendar);
        dialogCalendar.setCalendarMaximum(maxCalendar);
        dialogCalendar.setBookingDateVOList(list);
        dialogCalendar.setSelectBookingDay(rsvtDate.substring(0, 8));
        dialogCalendar.setRemoveWeekends(true);
        dialogCalendar.show();

    }


    @Override
    public void onBackButton() {
        onBackPressed();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == ResultCodes.REQ_CODE_SERVICE_CHARGE_BTR_RESERVATION_FINISH.getCode()) {
            exitPage(new Intent(), ResultCodes.REQ_CODE_SERVICE_CHARGE_BTR_RESERVATION_FINISH.getCode());
        } else if(resultCode == ResultCodes.REQ_CODE_PAYMENT_CARD_CHANGE.getCode()) {
            chbViewModel.reqCHB1015(new CHB_1015.Request(APPIAInfo.SM_CGRV02.getId()));
        }
    }

}
