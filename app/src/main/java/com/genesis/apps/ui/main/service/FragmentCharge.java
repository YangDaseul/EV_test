package com.genesis.apps.ui.main.service;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.CHB_1006;
import com.genesis.apps.comm.model.api.gra.SOS_3001;
import com.genesis.apps.comm.model.api.gra.SOS_3006;
import com.genesis.apps.comm.model.api.gra.SOS_3013;
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
import com.genesis.apps.ui.common.dialog.bottom.BottomDialogAskAgreeTermCharge;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;
import com.genesis.apps.ui.common.fragment.SubFragment;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;
import com.genesis.apps.ui.main.MainActivity;
import com.genesis.apps.ui.main.ServiceTermDetailActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import dagger.hilt.android.AndroidEntryPoint;

import static com.genesis.apps.comm.model.api.BaseResponse.RETURN_CODE_SUCC;
import static com.genesis.apps.comm.model.constants.VariableType.COMMON_MEANS_YES;

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

    //    private BottomTwoButtonTerm bottomTwoButtonTerm;
    private final int EVENT_TYPE_BTR = 1;
    private final int EVENT_TYPE_SOS = 2;
    private int eventType = 0;
    private boolean isTutorial=false;

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
        setObserver();
        initBatteryLayout();
    }

    private void setObserver() {
        sosViewModel = new ViewModelProvider(this).get(SOSViewModel.class);
        lgnViewModel = new ViewModelProvider(getActivity()).get(LGNViewModel.class);
        chbViewModel = new ViewModelProvider(this).get(CHBViewModel.class);
        developersViewModel = new ViewModelProvider(getActivity()).get(DevelopersViewModel.class);

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

        sosViewModel.getRES_SOS_3001().observe(getViewLifecycleOwner(), result -> {
            switch (result.status) {
                case LOADING:
                    ((SubActivity) getActivity()).showProgressDialog(true);
                    break;
                case SUCCESS:
                    ((SubActivity) getActivity()).showProgressDialog(false);
                    if (result.data != null && StringUtil.isValidString(result.data.getRtCd()).equalsIgnoreCase(RETURN_CODE_SUCC)) {
                        //????????????????????????
                        if (result.data.getSosStus() != null) {
                            switch (StringUtil.isValidString(result.data.getSosStus().getPgrsStusCd())) {
                                case VariableType.SERVICE_SOS_STATUS_CODE_R://??????
                                    me.lServiceChargeService.tvMovingNow.setVisibility(View.VISIBLE);
                                    me.lServiceChargeService.tvMovingNow.setText(R.string.sm_cggo_01_16);
                                    me.lServiceChargeService.tvServiceMaintenanceBtnBlack.setText(R.string.sm01_maintenance_42);
                                    break;
                                case VariableType.SERVICE_SOS_STATUS_CODE_W://??????
                                    me.lServiceChargeService.tvMovingNow.setVisibility(View.VISIBLE);
                                    me.lServiceChargeService.tvMovingNow.setText(R.string.sm_cggo_01_17);
                                    me.lServiceChargeService.tvServiceMaintenanceBtnBlack.setText(R.string.sm01_maintenance_42);
                                    break;
                                case VariableType.SERVICE_SOS_STATUS_CODE_S://??????
                                    me.lServiceChargeService.tvMovingNow.setVisibility(View.VISIBLE);
                                    me.lServiceChargeService.tvMovingNow.setText(R.string.sm_cggo_01_18);
                                    me.lServiceChargeService.tvServiceMaintenanceBtnBlack.setText(R.string.sm01_maintenance_42);
                                    break;
                                case VariableType.SERVICE_SOS_STATUS_CODE_E://??????
                                case VariableType.SERVICE_SOS_STATUS_CODE_C://??????
                                default:
                                    me.lServiceChargeService.tvMovingNow.setVisibility(View.GONE);
                                    me.lServiceChargeService.tvMovingNow.setText("");
                                    me.lServiceChargeService.tvServiceMaintenanceBtnBlack.setText(R.string.sm01_maintenance_40);
                                    break;
                            }
                        }
                        if (result.data.getChbStus() != null) {
                            switch (StringUtil.isValidString(result.data.getChbStus().getStatus())) {
                                case VariableType.SERVICE_CHARGE_BTR_CODE_RESERVATION://????????????
                                case VariableType.SERVICE_CHARGE_BTR_CODE_SVC_BEFORE://????????? ?????????
                                case VariableType.SERVICE_CHARGE_BTR_CODE_PICKUP://?????? ???
                                case VariableType.SERVICE_CHARGE_BTR_CODE_SERVICE://????????? ???
                                case VariableType.SERVICE_CHARGE_BTR_CODE_DELIVERY://???????????? ???
                                    me.lServiceChargeBtrService.tvMovingNow.setVisibility(View.VISIBLE);
                                    me.lServiceChargeBtrService.tvMovingNow.setText(StringUtil.isValidString(result.data.getChbStus().getStatusNm()));
                                    break;
                                case VariableType.SERVICE_CHARGE_BTR_CODE_FINISH:
                                case VariableType.SERVICE_CHARGE_BTR_CODE_CANCEL:
                                default:
                                    me.lServiceChargeBtrService.tvMovingNow.setVisibility(View.GONE);
                                    me.lServiceChargeBtrService.tvMovingNow.setText("");
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
                    if (result.data != null && StringUtil.isValidString(result.data.getRtCd()).equalsIgnoreCase(RETURN_CODE_SUCC)) {
                        try {
                            //?????????????????? ?????? ?????? ??? ???????????? ?????? ????????? ?????? ?????? ??????
                            sosViewModel.getRES_SOS_3001().getValue().data.getEvSvcTerm().setTrmsAgmtYn(COMMON_MEANS_YES);
                            if (eventType == EVENT_TYPE_SOS) startServiceChargeActivity();
                            else startChargeBtrReqActivity();

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
    }

    private void initBatteryLayout() {
        EvChargeStatusFragment evChargeStatusFragment = EvChargeStatusFragment.newInstance();
        getChildFragmentManager().beginTransaction()
                .add(me.vgEvStatusContainer.getId(), evChargeStatusFragment)
                .commitAllowingStateLoss();
    }

    @Override
    public void onRefresh() {
        getVehicleVO();
        setViewBatteryStatus();
        if(vehicleVO!=null&&!TextUtils.isEmpty(vehicleVO.getVin()))
            sosViewModel.reqSOS3001(new SOS_3001.Request(APPIAInfo.SM01.getId(), vehicleVO.getVin()));

        if(!isTutorial){
            isTutorial=true;
            ((SubActivity) getActivity()).hadTutorial(vehicleVO.isEVonlyOV(), lgnViewModel.hadTutorial(VariableType.TUTORIAL_TYPE_CHARGE), VariableType.TUTORIAL_TYPE_CHARGE);
        }
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onClickCommon(final View view) {
        int id = view.getId();
        String title = "";
        Log.d(TAG, "onClickCommon: view id :" + id);
        try {
            if (!((FragmentService) getParentFragment()).checkCustGbCd(id, lgnViewModel.getUserInfoFromDB().getCustGbCd()))
                return;
        } catch (Exception e) {

        }
        try {
            title = view.getTag().toString();
        }catch (Exception e){

        }

//        if(!(id==R.id.tv_service_maintenance_btn_white&&StringUtil.isValidString(title).equalsIgnoreCase(getString(R.string.sm_cg_sm02_11)))//???????????? ?????? ????????? ??????????????? ?????????
//                && id!=R.id.btn_service_charge_search //????????? ?????? ????????? ?????????
//                && checkSimplePayInfo()){ // ???????????? ?????? ????????? ??????

        if(((id==R.id.tv_service_maintenance_btn_black&&StringUtil.isValidString(title).equalsIgnoreCase(getString(R.string.sm_cg_sm02_7)))//??????????????? ??????
                || id==R.id.l_service_charge_btr_service //???????????????
                || (id==R.id.tv_service_maintenance_btn_white&&StringUtil.isValidString(title).equalsIgnoreCase(getString(R.string.sm_cg_sm02_7)))) //??????????????? ????????????
                && checkSimplePayInfo()){ // ???????????? ?????? ????????? ??????
            MiddleDialog.dialogServiceSimplePayInfo(getActivity(), new OnSingleClickListener() {
                @Override
                public void onSingleClick(View v) {
                    setSimplePayInfo((Boolean)v.getTag(R.id.item));
                    // ?????????????????? ???????????? ??????
                    //((BaseActivity) getActivity()).startActivitySingleTop(new Intent(getActivity(), CardManageActivity.class), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_VERTICAL_SLIDE);
                    ((BaseActivity) getActivity()).startActivitySingleTop(new Intent(getActivity(), CardManageTestActivity.class), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_VERTICAL_SLIDE);
                }
            }, new OnSingleClickListener() {
                @Override
                public void onSingleClick(View v) {
                    setSimplePayInfo((Boolean)v.getTag(R.id.item));
                    onEvent(id,view);
                }
            });
            return;
        }


        onEvent(id,view);
    }

    private void onEvent(final int id, final View v) {
        String title = "";
        switch (id) {
            //?????? ?????? ?????? ??????
            case R.id.iv_arrow:
                showTerm(v);
                break;
            //????????? ??????
            case R.id.btn_service_charge_search:
                ((BaseActivity) getActivity()).startActivitySingleTop(
                        new Intent(getActivity(), ChargeFindActivity.class),
                        RequestCodes.REQ_CODE_ACTIVITY.getCode(),
                        VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE
                );
                break;
            //????????? ??????/??????
            case R.id.l_service_charge_reservation_list:
                if(sosViewModel.getRES_SOS_3001().getValue() != null) {
                    SOS_3001.Response data = sosViewModel.getRES_SOS_3001().getValue().data;
                    if (data != null && data.getChbStus() != null) {
                        if ("Y".equalsIgnoreCase(data.getChbStus().getStMbrYn())) {
                            // ??????????????? ????????? ?????? - ????????? ?????? ?????? ???????????? ??????.
                            ((BaseActivity) getActivity()).startActivitySingleTop(
                                    new Intent(getActivity(), ChargeReserveActivity.class),
                                    RequestCodes.REQ_CODE_ACTIVITY.getCode(),
                                    VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE
                            );
                        } else {
                            // ??????????????? ????????? ?????? ?????? - ????????? ?????? ?????? ?????? ??????
                            MiddleDialog.dialogNeedRegistSTC(getActivity(), () -> {});
                        }
                    }else{
                        SnackBarUtil.show(getActivity(), "???????????? ????????? ????????? ??????????????????. ??????????????? ?????? ?????? ????????????.");
                    }
                }
                /* ????????? ????????? ??????
                MiddleDialog.dialogEVServiceInfo(getActivity(), (Runnable) () -> {

                });
                 */
                break;
            //?????? ?????? ??????
            case R.id.l_service_charge_recommend_trip:
                break;
            //??????????????? ?????????
            case R.id.l_service_charge_btr_service:
                startChargeBtrReqActivity();
                break;
            //???????????? ?????? ?????????
            case R.id.l_service_charge_service:
                startServiceChargeActivity();
                break;
            case R.id.tv_service_maintenance_btn_black:
                title = v.getTag().toString();
                if (StringUtil.isValidString(title).equalsIgnoreCase(getString(R.string.sm_cg_sm02_11))) {
                    startServiceChargeActivity();
                } else if (StringUtil.isValidString(title).equalsIgnoreCase(getString(R.string.sm_cg_sm02_7))) {
                    //??????????????? ????????? ??????
                    startChargeBtrReqActivity();
                }
                break;
            case R.id.tv_service_maintenance_btn_white:
                title = v.getTag().toString();
                if (StringUtil.isValidString(title).equalsIgnoreCase(getString(R.string.sm_cg_sm02_11))) {
                    //???????????? ?????? ????????? ?????? ??????
//                    String tel=mainVehicle.getMdlNm().equalsIgnoreCase("G90")||mainVehicle.getMdlNm().equalsIgnoreCase("EQ900") ? "080-900-6000" : "080-700-6000";
                    String sample = "080-700-6000";
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(WebView.SCHEME_TEL + sample)));
                } else if (StringUtil.isValidString(title).equalsIgnoreCase(getString(R.string.sm_cg_sm02_7))) {
                    //??????????????? ????????? ?????? ??????
                    startChargeBtrHistoryActivity(sosViewModel.getChbStusCd());
                }
                break;
            default:
                //do nothing
                break;
        }
    }

    private void setSimplePayInfo(Boolean isCheck){
        if(isCheck){
            lgnViewModel.updateGlobalDataToDB(KeyNames.KEY_NAME_CHARGE_TAB_PAY_SERVICE_POPUP, COMMON_MEANS_YES);
        }
    }

    private boolean checkSimplePayInfo() {
        return !VariableType.COMMON_MEANS_YES.equalsIgnoreCase(lgnViewModel.getDbGlobalDataRepository().select(KeyNames.KEY_NAME_CHARGE_TAB_PAY_SERVICE_POPUP))&&!sosViewModel.isPayInfo();
    }

    private void startChargeBtrReqActivity() {
//        if (!sosViewModel.isTrmsAgmtYn()) {
//            //????????????????????? ????????? ?????? ??????
//            showTermsDialog(sosViewModel.getChargeTermVO(), EVENT_TYPE_BTR);
//        } else
        if (sosViewModel.isChbApplyYn()) {
            //????????? ?????? ????????????
            startChargeBtrHistoryActivity(sosViewModel.getChbStusCd());
        } else {

            //??????????????? ????????? ??????
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
                //????????????????????????
            });
        } else {
//            if (!sosViewModel.isTrmsAgmtYn()) {
//                //????????????????????? ????????? ?????? ??????
//                showTermsDialog(sosViewModel.getChargeTermVO(), EVENT_TYPE_SOS);
//            } else
            if (!sosViewModel.isUseYn()) {
                //????????? ?????? ??????????????? ??????
                SnackBarUtil.show(getActivity(), getString(R.string.sm_cggo_01_14));
            } else if (sosViewModel.isSubspYn()) {
                //????????? ?????? ????????????
                switch (sosViewModel.getPgrsStusCd()) {
                    case VariableType.SERVICE_SOS_STATUS_CODE_R://??????
                    case VariableType.SERVICE_SOS_STATUS_CODE_W://??????
                        ((BaseActivity) getActivity()).startActivitySingleTop(new Intent(getActivity(), ServiceChargeApplyInfoActivity.class)
                                .putExtra(KeyNames.KEY_NAME_SOS_TMP_NO, sosViewModel.getTmpAcptNo()), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                        break;
                    case VariableType.SERVICE_SOS_STATUS_CODE_S://??????
                        sosViewModel.reqSOS3006(new SOS_3006.Request(APPIAInfo.SM01.getId(), sosViewModel.getTmpAcptNo()));
                        break;
                    case VariableType.SERVICE_SOS_STATUS_CODE_E://??????
                    case VariableType.SERVICE_SOS_STATUS_CODE_C://??????
                    default:
                        //do nothing (?????? ???????????? ?????? ????????????????????? ???????????? ???????????? ???????????? ?????? ?????????????????? ???????????? ??????)
                        break;
                }
            } else {
                if (sosViewModel.isCnt()) {
                    //??????????????? ????????????
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
                String userId = loginInfoDTO.getProfile().getId();
                //??????????????????????????????
                switch (developersViewModel.checkCarInfoToDevelopers(vehicleVO.getVin(), userId)) {
                    case STAT_AGREEMENT:
                        //???????????????
                        me.vgEvStatusContainer.setVisibility(View.VISIBLE);
                        break;
                    case STAT_DISAGREEMENT:
                    case STAT_DISABLE:
                    default:
                        me.vgEvStatusContainer.setVisibility(View.GONE);
                        break;
                }
            } else {
                me.vgEvStatusContainer.setVisibility(View.GONE);
            }
        } catch (Exception e) {

        }
    }

    //?????? ?????? ???????????? ??????
    private void showTermsDialog(List<TermVO> termList, int type) {
        if (termList != null && termList.size() > 0) {
            BottomDialogAskAgreeTermCharge termsDialog = new BottomDialogAskAgreeTermCharge(
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
                        agreeTerm2List.add(new AgreeTerm2VO(termVO.getTermCd(), COMMON_MEANS_YES));
                    }
                    if (agreeTerm2List != null && agreeTerm2List.size() > 0) {
                        eventType = type;
                        sosViewModel.reqSOS3013(new SOS_3013.Request(APPIAInfo.SM01.getId(), agreeTerm2List));
                    }
                }
            });
            termsDialog.init(termList);
            termsDialog.setTermEsnAgmtYn(false);
            termsDialog.show();
        } else {
            SnackBarUtil.show(getActivity(), "????????? ?????? ?????? ????????? ???????????? ????????????.\n???????????? ????????? ?????? ??? ?????? ????????? ????????????.");
        }
    }

    //?????? ?????? ??????
    private void showTerm(View v) {
        TermVO termVO = (TermVO) v.getTag(R.id.tag_term_vo);
        if (termVO != null) {
            ((BaseActivity) getActivity()).startActivitySingleTop(new Intent(getActivity(), ServiceTermDetailActivity.class)
                            .putExtra(VariableType.KEY_NAME_TERM_VO, termVO)
//                    .putExtra(KeyNames.KEY_NAME_MAP_SEARCH_TITLE_ID, termVO.getTermNm()) //2021-04-27 ?????????????????? ?????? ??????
                    , RequestCodes.REQ_CODE_ACTIVITY.getCode()
                    , VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
        }
    }
}
