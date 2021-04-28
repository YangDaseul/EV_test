package com.genesis.apps.ui.main.service;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.developers.EvStatus;
import com.genesis.apps.comm.model.api.gra.CHB_1003;
import com.genesis.apps.comm.model.api.gra.CHB_1006;
import com.genesis.apps.comm.model.api.gra.SOS_3006;
import com.genesis.apps.comm.model.api.gra.SOS_3013;
import com.genesis.apps.comm.model.constants.ChargeBtrStatus;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.AgreeTerm2VO;
import com.genesis.apps.comm.model.vo.TermVO;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.net.ga.LoginInfoDTO;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.comm.viewmodel.CHBViewModel;
import com.genesis.apps.comm.viewmodel.DevelopersViewModel;
import com.genesis.apps.comm.viewmodel.LGNViewModel;
import com.genesis.apps.comm.viewmodel.SOSViewModel;
import com.genesis.apps.databinding.FragmentServiceChargeBinding;
import com.genesis.apps.ui.common.activity.BaseActivity;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.bottom.BottomDialogAskAgreeTerms;
import com.genesis.apps.ui.common.dialog.bottom.BottomTwoButtonTerm;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;
import com.genesis.apps.ui.common.fragment.SubFragment;
import com.genesis.apps.ui.main.MainActivity;
import com.genesis.apps.ui.main.ServiceTermDetailActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

import static com.genesis.apps.comm.model.api.BaseResponse.RETURN_CODE_SUCC;

@AndroidEntryPoint
public class FragmentCharge extends SubFragment<FragmentServiceChargeBinding> {
    private static final String TAG = FragmentCharge.class.getSimpleName();
    private LGNViewModel lgnViewModel;
    private CHBViewModel chbViewModel;
    private SOSViewModel sosViewModel;
    private DevelopersViewModel developersViewModel;
    private VehicleVO vehicleVO;
    @Inject
    public LoginInfoDTO loginInfoDTO;

    private BottomTwoButtonTerm bottomTwoButtonTerm;

    public static FragmentCharge newInstance(int position) {
        FragmentCharge fragmentCharge = new FragmentCharge();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        fragmentCharge.setArguments(bundle);
        return fragmentCharge;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView(): start");
        View view = super.setContentView(inflater, R.layout.fragment_service_charge);
        me.setFragment(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        me.setLifecycleOwner(this);
        sosViewModel = new ViewModelProvider(this).get(SOSViewModel.class);
        lgnViewModel = new ViewModelProvider(getActivity()).get(LGNViewModel.class);
        chbViewModel = new ViewModelProvider(this).get(CHBViewModel.class);
        developersViewModel = new ViewModelProvider(getActivity()).get(DevelopersViewModel.class);

        chbViewModel.getRES_CHB_1003().observe(getViewLifecycleOwner(), result -> {
            switch (result.status) {
                case LOADING:
                    ((SubActivity) getActivity()).showProgressDialog(true);
                    break;
                case SUCCESS:
                    ((SubActivity) getActivity()).showProgressDialog(false);
                    if (result.data != null && StringUtil.isValidString(result.data.getRtCd()).equalsIgnoreCase(RETURN_CODE_SUCC)) {
                        try {
                            //정보제공동의 완료 처리 후 픽업앤충전 신청 버튼 클릭 처리
                            sosViewModel.getRES_SOS_3001().getValue().data.getChbStus().setPrvcyInfoAgmtYn(VariableType.COMMON_MEANS_YES);
                            startChargeBtrReqActivity();
                        } catch (Exception e) {

                        }
                        break;
                    }
                default:
                    ((SubActivity) getActivity()).showProgressDialog(false);
                    String serverMsg = "";
                    try {
                        serverMsg = result.data.getRtMsg();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        SnackBarUtil.show(getActivity(), TextUtils.isEmpty(serverMsg) ? getString(R.string.r_flaw06_p02_snackbar_1) : serverMsg);
                    }
                    break;
            }
        });

        chbViewModel.getRES_CHB_1006().observe(getViewLifecycleOwner(), result -> {
            switch (result.status) {
                case LOADING:
                    ((SubActivity) getActivity()).showProgressDialog(true);
                    break;
                case SUCCESS:
                    ((SubActivity) getActivity()).showProgressDialog(false);

                    String dkcKeyAvailableYN = result.data.getDkcKeyAvailableYN();
                    if (result.data != null && !TextUtils.isEmpty(dkcKeyAvailableYN) && dkcKeyAvailableYN.equalsIgnoreCase("Y")) {
                        ((BaseActivity) getActivity()).startActivitySingleTop(new Intent(getActivity(), ServiceChargeBtrReqActivity.class).putExtra(KeyNames.KEY_NAME_IS_DK_AVL, true), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                        break;
                    }
                default:
                    ((SubActivity) getActivity()).showProgressDialog(false);
                    ((BaseActivity) getActivity()).startActivitySingleTop(new Intent(getActivity(), ServiceChargeBtrReqActivity.class).putExtra(KeyNames.KEY_NAME_IS_DK_AVL, false), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                    break;
            }
        });

        developersViewModel.getRES_EV_STATUS().observe(getViewLifecycleOwner(), result -> {
            switch (result.status) {
                case LOADING:
                    ((SubActivity) getActivity()).showProgressDialog(true);
                    break;
                case SUCCESS:
                    if (result.data != null) {
                        ((SubActivity) getActivity()).showProgressDialog(false);
                        Spanned evStatus = null;
                        if (result.data.isBatteryCharge()) {
                            //충전 중
                            if (result.data.getSoc() == 100) {
                                //충전완료
                                evStatus = Html.fromHtml(getContext().getString(R.string.sm_cg_sm02_18), Html.FROM_HTML_MODE_COMPACT);
                            } else if (result.data.getRemainTime() != null && result.data.getRemainTime().getUnit() < 4) {
                                String time = developersViewModel.getBatteryChargeTime();
                                if (!TextUtils.isEmpty(time)) {
                                    evStatus = Html.fromHtml(String.format(Locale.getDefault(), getString(R.string.sm_cg_sm02_17), time), Html.FROM_HTML_MODE_COMPACT);
                                }
                            }
                        } else {
                            //충전 중 아님
                            if (result.data.getDte() != null && result.data.getDte().getDistance() != null) {
                                int distanceValue = (int) result.data.getDte().getDistance().getValue();
                                int unit = (int) result.data.getDte().getDistance().getUnit();
                                String distance = StringUtil.getDigitGrouping(distanceValue) + developersViewModel.getDistanceUnit(unit);
                                int soc = (int) result.data.getSoc();
                                if (soc <= 30) {
                                    evStatus = Html.fromHtml(String.format(Locale.getDefault(), getString(R.string.sm_cg_sm02_16), soc, distance), Html.FROM_HTML_MODE_COMPACT);
                                    //배터리 잔량이 30% 이하일 때
                                } else {
                                    //기본 상태 표시
                                    evStatus = Html.fromHtml(String.format(Locale.getDefault(), getString(R.string.sm_cg_sm02_15), soc, distance), Html.FROM_HTML_MODE_COMPACT);
                                }
                            }
                        }
                        if (evStatus != null) {
                            me.tvChargeStatus.setVisibility(View.VISIBLE);
                            me.tvChargeStatus.setText(evStatus);
                            break;
                        }
                    }
                default:
                    ((SubActivity) getActivity()).showProgressDialog(false);
                    me.tvChargeStatus.setVisibility(View.GONE);
//                    batteryCharge = false;
//                    soc = -1;
//                    setViewEvBattery();
                    break;
            }
        });
        sosViewModel.getRES_SOS_3001().observe(getViewLifecycleOwner(), result -> {
            switch (result.status) {
                case LOADING:
                    ((SubActivity) getActivity()).showProgressDialog(true);
                    break;
                case SUCCESS:
                    ((SubActivity) getActivity()).showProgressDialog(false);
                    if (result.data != null && StringUtil.isValidString(result.data.getRtCd()).equalsIgnoreCase(RETURN_CODE_SUCC)) {
                        //긴급출동상태표시
                        if (result.data.getSosStus() != null) {
                            switch (StringUtil.isValidString(result.data.getSosStus().getPgrsStusCd())) {
                                case VariableType.SERVICE_SOS_STATUS_CODE_R://신청
                                    me.lServiceChargeService.tvMovingNow.setVisibility(View.VISIBLE);
                                    me.lServiceChargeService.tvMovingNow.setText(R.string.sm_cggo_01_16);
                                    me.lServiceChargeService.tvServiceMaintenanceBtnBlack.setText(R.string.sm01_maintenance_42);
                                    break;
                                case VariableType.SERVICE_SOS_STATUS_CODE_W://접수
                                    me.lServiceChargeService.tvMovingNow.setVisibility(View.VISIBLE);
                                    me.lServiceChargeService.tvMovingNow.setText(R.string.sm_cggo_01_17);
                                    me.lServiceChargeService.tvServiceMaintenanceBtnBlack.setText(R.string.sm01_maintenance_42);
                                    break;
                                case VariableType.SERVICE_SOS_STATUS_CODE_S://출동
                                    me.lServiceChargeService.tvMovingNow.setVisibility(View.VISIBLE);
                                    me.lServiceChargeService.tvMovingNow.setText(R.string.sm_cggo_01_18);
                                    me.lServiceChargeService.tvServiceMaintenanceBtnBlack.setText(R.string.sm01_maintenance_42);
                                    break;
                                case VariableType.SERVICE_SOS_STATUS_CODE_E://완료
                                case VariableType.SERVICE_SOS_STATUS_CODE_C://취소
                                default:
                                    me.lServiceChargeService.tvMovingNow.setVisibility(View.GONE);
                                    me.lServiceChargeService.tvMovingNow.setText("");
                                    me.lServiceChargeService.tvServiceMaintenanceBtnBlack.setText(R.string.sm01_maintenance_40);
                                    break;
                            }
                        }
                        if (result.data.getChbStus() != null) {
                            switch (StringUtil.isValidString(result.data.getChbStus().getStatus())) {
                                case VariableType.SERVICE_CHARGE_BTR_CODE_RESERVATION://예약완료
                                case VariableType.SERVICE_CHARGE_BTR_CODE_PICKUP://픽업 중
                                case VariableType.SERVICE_CHARGE_BTR_CODE_SERVICE://서비스 중
                                case VariableType.SERVICE_CHARGE_BTR_CODE_DELIVERY://딜리버리 중
                                    me.lServiceChargeBtrService.tvMovingNow.setVisibility(View.VISIBLE);
                                    me.lServiceChargeBtrService.tvMovingNow.setText(StringUtil.isValidString(result.data.getChbStus().getStatusNm()));
                                    break;
                                case VariableType.SERVICE_CHARGE_BTR_CODE_FINISH:
                                case VariableType.SERVICE_CHARGE_BTR_CODE_CANCEL:
                                default:
                                    me.lServiceChargeService.tvMovingNow.setVisibility(View.GONE);
                                    me.lServiceChargeService.tvMovingNow.setText("");
                                    break;
                            }
                        }
                        break;
                    }
                default:
                    ((SubActivity) getActivity()).showProgressDialog(false);
                    String serverMsg = "";
                    try {
                        serverMsg = result.data.getRtMsg();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        SnackBarUtil.show(getActivity(), TextUtils.isEmpty(serverMsg) ? getString(R.string.r_flaw06_p02_snackbar_1) : serverMsg);
                    }
                    break;
            }
        });
        sosViewModel.getRES_SOS_3006().observe(getViewLifecycleOwner(), result -> {
            switch (result.status) {
                case LOADING:
                    ((SubActivity) getActivity()).showProgressDialog(true);
                    break;
                case SUCCESS:
                    ((SubActivity) getActivity()).showProgressDialog(false);
                    if (result.data != null && result.data.getSosDriverVO() != null) {
                        ((BaseActivity) getActivity()).startActivitySingleTop(new Intent(getActivity(), ServiceChargeRouteInfoActivity.class).putExtra(KeyNames.KEY_NAME_SOS_DRIVER_VO, result.data), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                        break;
                    }
                default:
                    ((SubActivity) getActivity()).showProgressDialog(false);
                    String serverMsg = "";
                    try {
                        serverMsg = result.data.getRtMsg();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        SnackBarUtil.show(getActivity(), TextUtils.isEmpty(serverMsg) ? getString(R.string.r_flaw06_p02_snackbar_1) : serverMsg);
                    }
                    break;
            }
        });

        sosViewModel.getRES_SOS_3013().observe(getViewLifecycleOwner(), result -> {
            switch (result.status) {
                case LOADING:
                    ((SubActivity) getActivity()).showProgressDialog(true);
                    break;
                case SUCCESS:
                    ((SubActivity) getActivity()).showProgressDialog(false);
                    if (result.data != null&& StringUtil.isValidString(result.data.getRtCd()).equalsIgnoreCase(RETURN_CODE_SUCC)) {
                        try {
                            //정보제공동의 완료 처리 후 찾아가는 출동 서비스 버튼 클릭 처리
                            sosViewModel.getRES_SOS_3001().getValue().data.getSosStus().setTrmsAgmtYn(VariableType.COMMON_MEANS_YES);
                            startServiceChargeActivity();
                        }catch (Exception e){

                        }
                        break;
                    }
                default:
                    ((SubActivity) getActivity()).showProgressDialog(false);
                    String serverMsg = "";
                    try {
                        serverMsg = result.data.getRtMsg();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        SnackBarUtil.show(getActivity(), TextUtils.isEmpty(serverMsg) ? getString(R.string.r_flaw06_p02_snackbar_1) : serverMsg);
                    }
                    break;
            }
        });

    }

    @Override
    public void onRefresh() {
        getVehicleVO();
        setViewBatteryStatus();
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onClickCommon(View v) {
        String title = "";
        int id = v.getId();
        Log.d(TAG, "onClickCommon: view id :" + id);
        try {
            if (!((FragmentService) getParentFragment()).checkCustGbCd(id, lgnViewModel.getUserInfoFromDB().getCustGbCd()))
                return;
        } catch (Exception e) {

        }

        switch (id) {
            //약관 내용 보기 버튼
            case R.id.iv_arrow:
                showTerm(v);
                break;
            //충전소 검색
            case R.id.btn_service_charge_search:
                ((BaseActivity) getActivity()).startActivitySingleTop(
                        new Intent(getActivity(), ChargeFindActivity.class),
                        RequestCodes.REQ_CODE_ACTIVITY.getCode(),
                        VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE
                );
                break;
            //충전소 예약/내역
            case R.id.l_service_charge_reservation_list:
                //TODO 2021-04-23 PARK 예약 내역만 추가하였으며 충전소 예약 분기 처리 필요 > 분기 처리 후 해당 페이지를 호출을 다시 정의해 주세요. (김기만 C)
                ((BaseActivity) getActivity()).startActivitySingleTop(
                        new Intent(getActivity(), ChargeReserveHistoryActivity.class),
                        RequestCodes.REQ_CODE_ACTIVITY.getCode(),
                        VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE
                );
                break;
            //여행 경로 추천
            case R.id.l_service_charge_recommend_trip:
                break;
            //픽업앤충전 서비스
            case R.id.l_service_charge_btr_service:

                break;
            //찾아가는 충전 서비스
            case R.id.l_service_charge_service:
                startServiceChargeActivity();
                break;
            case R.id.tv_service_maintenance_btn_black:
                title = v.getTag().toString();
                if (StringUtil.isValidString(title).equalsIgnoreCase(getString(R.string.sm_cg_sm02_11))) {
                    startServiceChargeActivity();
                } else if (StringUtil.isValidString(title).equalsIgnoreCase(getString(R.string.sm_cg_sm02_7))) {
                    //픽업앤충전 서비스 신청
                    startChargeBtrReqActivity();
                }
                break;
            case R.id.tv_service_maintenance_btn_white:
                title = v.getTag().toString();
                if (StringUtil.isValidString(title).equalsIgnoreCase(getString(R.string.sm_cg_sm02_11))) {
                    //찾아가는 충전 서비스 전화 신청
//                    String tel=mainVehicle.getMdlNm().equalsIgnoreCase("G90")||mainVehicle.getMdlNm().equalsIgnoreCase("EQ900") ? "080-900-6000" : "080-700-6000";
                    String sample = "080-700-6000";
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(WebView.SCHEME_TEL + sample)));
                } else if (StringUtil.isValidString(title).equalsIgnoreCase(getString(R.string.sm_cg_sm02_7))) {
                    //픽업앤충전 서비스 신청 내역
                    startChargeBtrHistoryActivity(ChargeBtrStatus.STATUS_1000.getStusCd());
                }
                break;
            default:
                //do nothing
                break;
        }
    }

    private void startChargeBtrReqActivity() {

        if (!sosViewModel.isPrvcyInfoAgmtYn()) {
            //정보제공동의가 안되어 있는 경우
            if (bottomTwoButtonTerm == null)
                bottomTwoButtonTerm = new BottomTwoButtonTerm(getActivity(), R.style.BottomSheetDialogTheme);

            bottomTwoButtonTerm.setTitle(getString(R.string.service_charge_btr_10));
            bottomTwoButtonTerm.setContent(getString(R.string.service_charge_btr_popup_msg_04));
            bottomTwoButtonTerm.setButtonAction(() -> {
                // 픽업앤충전 정보제공동의 설정 요청
                chbViewModel.reqCHB1003(new CHB_1003.Request(APPIAInfo.SM01.getId(), VariableType.SERVICE_CHARGE_BTR_SVC_CD, "Y"));
            }, () -> {
                // 팝업 종료
            });

            bottomTwoButtonTerm.setEventTerm(() -> {
                ((BaseActivity) getActivity()).startActivitySingleTop(new Intent(getActivity(), ServiceTermDetailActivity.class)
                                .putExtra(VariableType.KEY_NAME_TERM_VO, new TermVO("01.03", "2000", "", sosViewModel.getChbTermCont(), ""))
                        , RequestCodes.REQ_CODE_ACTIVITY.getCode()
                        , VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
            });
            bottomTwoButtonTerm.show();

        } else if (sosViewModel.isChbApplyYn()) {
            //서비스 신청 중인경우
            startChargeBtrHistoryActivity(sosViewModel.getChbStusCd());
        } else {

            //픽업앤충전 서비스 신청
            String vin = vehicleVO.getVin();
            if (!TextUtils.isEmpty(vin))
                chbViewModel.reqCHB1006(new CHB_1006.Request(APPIAInfo.SM01.getId(), vehicleVO.getVin()));
            else
                SnackBarUtil.show(getActivity(), getString(R.string.r_flaw06_p02_snackbar_1));
        }

    }

    private void startChargeBtrHistoryActivity(String statusCd) {
        ((BaseActivity) getActivity()).startActivitySingleTop(new Intent(getActivity(), ServiceChargeBtrReserveHistoryActivity.class).putExtra(KeyNames.KEY_NAME_CHB_STUS_CD, statusCd), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
    }

    private void getVehicleVO() {
        try {
            vehicleVO = lgnViewModel.getMainVehicleSimplyFromDB();
        } catch (Exception e) {

        }
    }

    private void startServiceChargeActivity() {
        if (!((MainActivity) getActivity()).isGpsEnable()) {
            MiddleDialog.dialogGPS(getActivity(), () -> ((MainActivity) getActivity()).turnGPSOn(isGPSEnable -> {
                Log.v("test", "value:" + isGPSEnable);
            }), () -> {
                //현대양재사옥위치
            });
        } else {
            if (!sosViewModel.isTrmsAgmtYn()) {
                //정보제공동의가 안되어 있는 경우
                showTermsDialog(sosViewModel.getChargeTermVO());
            } else if (!sosViewModel.isUseYn()) {
                //서비스 사용 불가상태인 경우
                SnackBarUtil.show(getActivity(), getString(R.string.sm_cggo_01_14));
            } else if (sosViewModel.isSubspYn()) {
                //서비스 신청 중인경우
                switch (sosViewModel.getPgrsStusCd()) {
                    case VariableType.SERVICE_SOS_STATUS_CODE_R://신청
                    case VariableType.SERVICE_SOS_STATUS_CODE_W://접수
                        ((BaseActivity) getActivity()).startActivitySingleTop(new Intent(getActivity(), ServiceChargeApplyInfoActivity.class)
                                .putExtra(KeyNames.KEY_NAME_SOS_TMP_NO, sosViewModel.getTmpAcptNo()), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                        break;
                    case VariableType.SERVICE_SOS_STATUS_CODE_S://출동
                        sosViewModel.reqSOS3006(new SOS_3006.Request(APPIAInfo.SM01.getId(), sosViewModel.getTmpAcptNo()));
                        break;
                    case VariableType.SERVICE_SOS_STATUS_CODE_E://완료
                    case VariableType.SERVICE_SOS_STATUS_CODE_C://취소
                    default:
                        //do nothing (해당 케이스는 일반 긴급출동에서는 신청으로 이동되나 찾아가는 출동 서비스에서는 존재하지 않음)
                        break;
                }
            } else {
                if (sosViewModel.isCnt()) {
                    //잔여횟수가 남은경우
                    ((BaseActivity) getActivity()).startActivitySingleTop(new Intent(getActivity(), ServiceChargeApplyActivity.class)
                            .putExtra(KeyNames.KEY_NAME_SOS_STATUS_VO, sosViewModel.getRES_SOS_3001().getValue().data.getSosStus())
                            , RequestCodes.REQ_CODE_ACTIVITY.getCode()
                            , VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                } else {
                    SnackBarUtil.show(getActivity(), getString(R.string.sm_cggo_01_15));
                }
            }
        }
    }

    private void setViewBatteryStatus() {
        try {
            if (vehicleVO != null && vehicleVO.isEV()) {
                String carId = developersViewModel.getCarId(vehicleVO.getVin());
                String userId = loginInfoDTO.getProfile().getId();
                //정보제공동의유무확인
                switch (developersViewModel.checkCarInfoToDevelopers(vehicleVO.getVin(), userId)) {
                    case STAT_AGREEMENT:
                        //동의한경우
                        me.tvChargeStatus.setVisibility(View.VISIBLE);
                        developersViewModel.reqEvStatus(new EvStatus.Request(carId));
                        break;
                    case STAT_DISAGREEMENT:
                        //미동의상태
                    case STAT_DISABLE:
                    default:
                        //ccs 사용불가상태
                        me.tvChargeStatus.setVisibility(View.GONE);
                        break;
                }
            } else {
                me.tvChargeStatus.setVisibility(View.GONE);
            }
        } catch (Exception e) {

        }

    }
    //약관 동의 대화상자 호출
    private void showTermsDialog(List<TermVO> termList) {
        if(termList!=null&&termList.size()>0) {
            BottomDialogAskAgreeTerms termsDialog = new BottomDialogAskAgreeTerms(
                    getActivity(),
                    R.style.BottomSheetDialogTheme,
                    onSingleClickListener);
            termsDialog.setTitle(getString(R.string.sm_cggo_01_19));
            termsDialog.setOnDismissListener(dialog -> {
                if (termsDialog.isInputConfirmed()) {
                    List<AgreeTerm2VO> agreeTerm2List = new ArrayList<>();
                    List<TermVO> termVOList = new ArrayList<>();
                    termVOList.addAll(sosViewModel.getChargeTermVO());
                    for (TermVO termVO : termVOList) {
                        agreeTerm2List.add(new AgreeTerm2VO(termVO.getTermCd(), VariableType.COMMON_MEANS_YES));
                    }
                    if (agreeTerm2List != null && agreeTerm2List.size() > 0)
                        sosViewModel.reqSOS3013(new SOS_3013.Request(APPIAInfo.SM01.getId(), agreeTerm2List));
                }
            });
            termsDialog.init(termList);
            termsDialog.show();
        }else{
            SnackBarUtil.show(getActivity(),"서비스 이용 약관 정보가 존재하지 않습니다.\n네트워크 상태를 확인 후 다시 시도해 주십시오.");
        }
    }

    //약관 상세 보기
    private void showTerm(View v) {
        TermVO termVO = (TermVO) v.getTag(R.id.tag_term_vo);
        if (termVO != null) {
            ((BaseActivity) getActivity()).startActivitySingleTop(new Intent(getActivity(), ServiceTermDetailActivity.class)
                    .putExtra(VariableType.KEY_NAME_TERM_VO, termVO)
//                    .putExtra(KeyNames.KEY_NAME_MAP_SEARCH_TITLE_ID, termVO.getTermNm()) //2021-04-27 자세히보기로 노출 결정
                    , RequestCodes.REQ_CODE_ACTIVITY.getCode()
                    , VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
        }
    }
}
