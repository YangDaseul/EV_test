package com.genesis.apps.ui.main.service;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.CHB_1008;
import com.genesis.apps.comm.model.api.gra.CHB_1009;
import com.genesis.apps.comm.model.api.gra.CHB_1010;
import com.genesis.apps.comm.model.api.gra.CHB_1015;
import com.genesis.apps.comm.model.api.gra.CHB_1027;
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
import com.genesis.apps.comm.model.vo.carlife.PaymtCardVO;
import com.genesis.apps.comm.model.vo.carlife.ReqInfoVO;
import com.genesis.apps.comm.model.vo.carlife.ReqOrderVO;
import com.genesis.apps.comm.net.ga.LoginInfoDTO;
import com.genesis.apps.comm.util.DateUtil;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.comm.viewmodel.CHBViewModel;
import com.genesis.apps.databinding.ActivityServiceChargeBtrCheckBinding;
import com.genesis.apps.ui.common.activity.BluewalnutWebActivity;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.bottom.BottomListDialog;
import com.genesis.apps.ui.common.dialog.bottom.DialogCalendarChargeBtr;

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
    private boolean selectedOption; // 세차 서비스 옵션 선택 여부
    private OptionVO carwashVO; //  세차 서비스 옵션 정보

    private String userAgentString = null;

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
        setRsvtDateOptionInfo(rsvtDate, selectedOption);

        setLayoutPriceItem();
        setLayoutCardItem();
    }

    /**
     * 가격 정보들 표시
     */
    private void setLayoutPriceItem() {

        if(contentsVO == null)
            return;

        // 충전 금액 표시
        ui.tvChargePrice.setContent(StringUtil.getPriceString(contentsVO.getProductPrice()));
        ui.tvChargePrice.lWhole.setTag(contentsVO.getProductPrice());
        // 충전 크레딧 포인트 정보 표시
        setLayoutCreditPoint();
        // 탁송금액 표시
        int deliverPrice = chbViewModel.getOptionVO(VariableType.SERVICE_CHARGE_BTR_OPT_CD_1, contentsVO.getOptionList()).getOptionPrice();
        ui.tvDeliveryPrice.setContent(StringUtil.getPriceString(deliverPrice));
        ui.tvDeliveryPrice.lWhole.setTag(deliverPrice);

        // 세차금액 표시
        this.carwashVO = chbViewModel.getOptionVO(VariableType.SERVICE_CHARGE_BTR_OPT_CD_2, contentsVO.getOptionList());

        updateSvcPaymt();
    }

    /**
     * 세차 서비스 옵션 표시
     */
    private void updateOptionInfo() {
        if (carwashVO != null && selectedOption) {
            ui.tvCarwashPrice.lWhole.setVisibility(View.VISIBLE);
            ui.tvCarwashPrice.setContent(StringUtil.getPriceString(carwashVO.getOptionPrice()));
        } else {
            ui.tvCarwashPrice.lWhole.setVisibility(View.GONE);
        }

        if (ui.lCreditPointInfo.getVisibility() != View.VISIBLE) {
            if (ui.tvCarwashPrice.lWhole.getVisibility() == View.VISIBLE) {
                ui.tvDeliveryPrice.setHideLine(false);
                ui.tvCarwashPrice.setHideLine(true);
            } else ui.tvDeliveryPrice.setHideLine(true);
        }
    }

    /**
     *  총 결재 금액 업데이트
     */
    private void updateSvcPaymt() {
        updateOptionInfo();

        // 서비스 금액 표시(충전 금액 - 충전 크레딧 포인트 + 탁송 금액 + 세차 금액)
        int chargePrice = (int) ui.tvChargePrice.lWhole.getTag();
        int discountPoint = ui.lCreditPointInfo.getVisibility() == View.VISIBLE ? (int) ui.tvCreditPoint.lWhole.getTag() : 0;
        int opt1Price = (int) ui.tvDeliveryPrice.lWhole.getTag();
        int opt2Price = (carwashVO != null && selectedOption) ? carwashVO.getOptionPrice() : 0;

        int totalPaymt = chargePrice - discountPoint + opt1Price + opt2Price;
        ui.tvSvcPaymt.setText(StringUtil.getPriceString(totalPaymt));
        ui.tvSvcPaymt.setTag(totalPaymt);
    }

    /**
     * 충전 크레딧 정보 표시
     */
    private void setLayoutCreditPoint() {
        if (contentsVO.getStrafficInfo() == null) {
            ui.lCreditPointInfo.setVisibility(View.GONE);
            return;
        }

        int creditBalance = 0;

        if(contentsVO.getStrafficInfo().getAvailableYN().equalsIgnoreCase(VariableType.COMMON_MEANS_YES)){
            creditBalance = contentsVO.getStrafficInfo().getBalance();
        }

        int discountPoint = creditBalance > contentsVO.getProductPrice() ? contentsVO.getProductPrice() : 0;
        ui.tvCreditPoint.setContent(discountPoint > 0 ? StringUtil.getDiscountString(discountPoint) : getString(R.string.service_charge_btr_word_30));
        ui.tvCreditPoint.lWhole.setTag(discountPoint);
        ui.tvCreditPointBalance.setText(String.format(Locale.getDefault(), getString(R.string.service_charge_btr_word_41), StringUtil.getPriceString(creditBalance)));
        ui.tvCreditPointInfo.setText(String.format(getString(R.string.service_charge_btr_msg_04), StringUtil.getPriceString(contentsVO.getProductPrice())));

        ui.lCreditPointInfo.setVisibility(View.VISIBLE);
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
                ui.lSelectedPaymtCard.setCompoundDrawablesWithIntrinsicBounds(null, null, contentsVO.getCardList().size() > 1 ? getDrawable(R.drawable.btn_dropdown) : null, null);
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
                } else {
                    ui.ivArrowImage.setImageResource(R.drawable.btn_arrow_close);
                    ui.lSvcPaymtDetail.setVisibility(View.VISIBLE);
                }
                break;
//            case R.id.cb_carwash_option:
//                ui.tvCarwashPrice.lWhole.setVisibility(selectedOption ? View.VISIBLE : View.GONE);
//                updateSvcPaymt();
//                break;
            case R.id.btn_card_reg:
            case R.id.btn_card_mgmt:
                // 결제수단관리 페이지로 연결(간편결제 가입/카드등록 분기 처리)
                startActivitySingleTop(new Intent(this, CardManageActivity.class), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_VERTICAL_SLIDE);
                break;
            case R.id.l_selected_paymt_card:
                if(contentsVO.getCardList().size() > 1)
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

        hpNo = hpNo.replaceAll("-", "");

        int estimatedPaymentAmount = (int) ui.tvSvcPaymt.getTag(); // 최종 결제 금액
        List<OptionVO> optList = new ArrayList<>();
        optList.add(new OptionVO(chbViewModel.getOptionVO(VariableType.SERVICE_CHARGE_BTR_OPT_CD_1, contentsVO.getOptionList()).getOptionCode(), 1));

        if(selectedOption)
            optList.add(new OptionVO(chbViewModel.getOptionVO(VariableType.SERVICE_CHARGE_BTR_OPT_CD_2, contentsVO.getOptionList()).getOptionCode(), 1));

        int membershipUsePoint = 0;
        List<MembershipVO> membershipVO = new ArrayList<>();
        if(contentsVO.getStrafficInfo() != null) {
//            membershipVO = new ArrayList<>();
            membershipVO.add(new MembershipVO(VariableType.SERVICE_CHARGE_BTR_MEMBERSHIP_CODE_STRFF, contentsVO.getStrafficInfo().getCardNo(), membershipUsePoint));
        }

        List<LotVO> lotVOList = new ArrayList<LotVO>();
        lotVOList.add(lotVO);

        if(selectedCardVo == null){
            SnackBarUtil.show(this, getString(R.string.service_charge_btr_err_15));
            return;
        }

        if(TextUtils.isEmpty(userAgentString))
            userAgentString = new WebView(this).getSettings().getUserAgentString();

        chbViewModel.reqCHB1010(new CHB_1010.Request(APPIAInfo.SM_CGRV02.getId()
                , contentsVO.getTxid()
                , hpNo
                , userAgentString
                , new CarVO(mainVehicle.getVin(), carNo, mainVehicle.getMdlCd(), mainVehicle.getMdlNm())
                , new ReqInfoVO(rsvtDate, keyTransferType)
                , lotVOList
                , new ReqOrderVO(contentsVO.getProductCode(), estimatedPaymentAmount, optList.size(), optList)
                , membershipVO
                , selectedCardVo != null ? new PaymtCardVO(selectedCardVo.getCardId(), selectedCardVo.getCardNo(), selectedCardVo.getCardCoCode()) : null));
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
                        selectCalendar(result.data);
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
                    if (result.data != null && result.data.getRtCd().equalsIgnoreCase("0000") && result.data.getPaymentFormData() != null) {
                        startActivitySingleTop(new Intent(this, BluewalnutWebActivity.class)
                                        .putExtra(KeyNames.KEY_NAME_CONTENTS_VO, result.data.getPaymentFormData())
                                , RequestCodes.REQ_CODE_ACTIVITY.getCode()
                                , VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
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

        chbViewModel.getRES_CHB_1027().observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    showProgressDialog(false);
                    if (result.data != null && result.data.getRtCd().equalsIgnoreCase("0000")) {
                        startActivitySingleTop(new Intent(this, ServiceChargeBtrResultActivity.class).putExtra(KeyNames.KEY_NAME_CONTENTS_VO, result.data), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_VERTICAL_SLIDE);
                        break;
                    }
                default:
                    showProgressDialog(false);
                    startActivitySingleTop(new Intent(this, ServiceChargeBtrFailActivity.class), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_VERTICAL_SLIDE);
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
            selectedOption = getIntent().getBooleanExtra(KeyNames.KEY_NAME_CHB_SELECTED_OPTION,  false);
        } catch (Exception e) {

        }
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
     * 예약 희망일/옵션 선택 팝업 호출
     *
     * @param data
     */
    private void selectCalendar(CHB_1008.Response data) {

        List<BookingDateVO> list = data.getDailyBookingSlotList();

        DialogCalendarChargeBtr dialogCalendar = new DialogCalendarChargeBtr(this, R.style.BottomSheetDialogTheme, onSingleClickListener);
        dialogCalendar.setOnDismissListener(dialogInterface -> {
            Calendar calendar = dialogCalendar.calendar;
            if (calendar != null) {
                Log.d("LJEUN", "rsvtDate : " + StringUtil.isValidString(rsvtDate));
                String tempDate = DateUtil.getDate(calendar.getTime(), DateUtil.DATE_FORMAT_yyyyMMdd);
                tempDate += dialogCalendar.getSelectBookingTime();
                Log.d("LJEUN", "tempDate : " + StringUtil.isValidString(tempDate));
                Log.d("LJEUN", "isEquals date : " + StringUtil.isValidString(rsvtDate).equalsIgnoreCase(tempDate));

                if(!StringUtil.isValidString(rsvtDate).equalsIgnoreCase(tempDate)) {
                    // 픽업앤충전 신청 전문 요청
                    chbViewModel.reqCHB1009(new CHB_1009.Request(APPIAInfo.SM_CGRV01.getId(),
                            mainVehicle.getVin(),
                            mainVehicle.getMdlCd(),
                            tempDate,
                            lotVO
                    ));
                }

                // 예약 희망일/옵션 선택 정보 표시
                setRsvtDateOptionInfo(tempDate, dialogCalendar.getOptionChecked());

                updateSvcPaymt();
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

        // 옵션 정보
        if (data.getOptionList() != null && data.getOptionList().size() > 0) {
            for (OptionVO vo : data.getOptionList()) {
                if (StringUtil.isValidString(vo.getOptionCode()).equalsIgnoreCase(VariableType.SERVICE_CHARGE_BTR_OPT_CD_2)) {
                    dialogCalendar.setOptionVO(vo);
                    break;
                }
            }
        }

        dialogCalendar.setSelectBookingDay(rsvtDate.substring(0, 8));
        dialogCalendar.setOptionChecked(selectedOption);
        dialogCalendar.setRemoveWeekends(true);
        dialogCalendar.show();

    }

    /**
     * 예약 희망일/옵션 정보 표시
     *
     * @param date            일시
     * @param isCheckedOption 옵션 선택 여부
     */
    private void setRsvtDateOptionInfo(String date, boolean isCheckedOption) {

        String resultDtm = DateUtil.getDate(DateUtil.getDefaultDateFormat(StringUtil.isValidString(date), DateUtil.DATE_FORMAT_yyyyMMddHHmmss), DateUtil.DATE_FORMAT_yyyy_MM_dd_E_HH_mm);

        String targetTxt = " / " + getString(R.string.service_charge_btr_word_34);
        if (isCheckedOption) resultDtm += targetTxt;

        ui.tvRsvtHopeDt.setText(resultDtm);


        this.rsvtDate = date;
        this.selectedOption = isCheckedOption;
    }

    @Override
    public void onBackButton() {
        onBackPressed();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == ResultCodes.REQ_CODE_PAYMENT_CARD_CHANGE.getCode()) {
            // 간편결제카드 관리 변동 사항 있음.
            chbViewModel.reqCHB1015(new CHB_1015.Request(APPIAInfo.SM_CGRV02.getId()));
        } else if (resultCode == ResultCodes.REQ_CODE_BLUEWALNUT_PAYMENT_FINISH.getCode()) {
            // 블루월넛 결제 완료 시
            showProgressDialog(true);

            new Handler().postDelayed(() -> {
                chbViewModel.reqCHB1027(new CHB_1027.Request(APPIAInfo.SM_CGRV02.getId(), mainVehicle.getVin()));
            }, 2000);

        } else if (resultCode == ResultCodes.REQ_CODE_SERVICE_CHARGE_BTR_RESERVATION_FINISH.getCode()) {
            // 픽업앤충전 서비스 예약 완료되어 화면 종료 시
            exitPage(new Intent(), ResultCodes.REQ_CODE_SERVICE_CHARGE_BTR_RESERVATION_FINISH.getCode());
        } else if (resultCode == ResultCodes.REQ_CODE_SERVICE_CHARGE_BTR_RESERVATION_FAIL.getCode()) {
            // 픽업앤충전 서비스 예약 실패되어 화면 종료 시
            exitPage(new Intent(), ResultCodes.REQ_CODE_SERVICE_CHARGE_BTR_RESERVATION_FAIL.getCode());
        }
    }

}
