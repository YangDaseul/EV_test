package com.genesis.apps.ui.main.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.developers.Detail;
import com.genesis.apps.comm.model.api.developers.Dtc;
import com.genesis.apps.comm.model.api.developers.Replacements;
import com.genesis.apps.comm.model.api.developers.Target;
import com.genesis.apps.comm.model.api.gra.BTR_1001;
import com.genesis.apps.comm.model.api.gra.GNS_1010;
import com.genesis.apps.comm.model.api.gra.LGN_0003;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.BtrVO;
import com.genesis.apps.comm.model.vo.DataMilesVO;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.net.NetUIResponse;
import com.genesis.apps.comm.net.ga.LoginInfoDTO;
import com.genesis.apps.comm.util.DeviceUtil;
import com.genesis.apps.comm.util.PhoneUtil;
import com.genesis.apps.comm.util.RecyclerViewDecoration;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.comm.viewmodel.BTRViewModel;
import com.genesis.apps.comm.viewmodel.DevelopersViewModel;
import com.genesis.apps.comm.viewmodel.GNSViewModel;
import com.genesis.apps.comm.viewmodel.LGNViewModel;
import com.genesis.apps.databinding.FragmentHome2Binding;
import com.genesis.apps.ui.common.activity.GAWebActivity;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;
import com.genesis.apps.ui.common.fragment.SubFragment;
import com.genesis.apps.ui.main.MainActivity;
import com.genesis.apps.ui.main.home.view.Home2BtrAdapter;
import com.genesis.apps.ui.main.home.view.Home2DataMilesAdapter;
import com.genesis.apps.ui.main.home.view.Home2WarrantyAdapter;
import com.genesis.apps.ui.main.service.ServiceRepairReserveHistoryActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

import static com.genesis.apps.comm.viewmodel.DevelopersViewModel.CCSSTAT.STAT_AGREEMENT;

@AndroidEntryPoint
public class FragmentHome2 extends SubFragment<FragmentHome2Binding> {

    @Inject
    public LoginInfoDTO loginInfoDTO;

    private ConcatAdapter concatAdapter;
    private Home2DataMilesAdapter home2DataMilesAdapter;
    //    private Home2AsanAdapter home2AsanAdapter;
    private Home2WarrantyAdapter home2WarrantyAdapter;
    private Home2BtrAdapter home2BtrAdapter;
    private LGNViewModel lgnViewModel;
    private VehicleVO vehicleVO;
    private DevelopersViewModel developersViewModel;
    private GNSViewModel gnsViewModel;
    private BTRViewModel btrViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.setContentView(inflater, R.layout.fragment_home_2);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        setViewModel();
        setObserver();
        try {
            vehicleVO = lgnViewModel.getMainVehicleFromDB();
        }catch (Exception e){

        }
    }

    private void setObserver() {
        LifecycleOwner lifecycleOwner = getViewLifecycleOwner();

//        lgnViewModel.getRES_LGN_0003().observe(lifecycleOwner, result -> {
//            switch (result.status) {
//                case SUCCESS:
////                    setViewAsanList(result);
////                    setViewWarranty();
////                    setViewBtrInfo(result);
//                    break;
//            }
//        });

        developersViewModel.getRES_TARGET().observe(lifecycleOwner, result -> {
            NetUIResponse.Status status = result.status;
            Target.Response data = result.data;

            // TODO ?????? ?????????. ?????? ??????.
//            status = NetUIResponse.Status.SUCCESS;

            switch (status) {
                case SUCCESS: {
                    // TODO ?????? ????????? ?????? ??????.
//                    String dummyData = "{\"targetYn\":\"N\",\"supportedYn\":\"Y\",\"msgId\":\"11e77efa-aff0-4b3c-a5a0-c4cde4674963\"}";
//                    data = new Gson().fromJson(dummyData, Target.Response.class);

                    if(vehicleVO!=null) {
                        String carId = developersViewModel.getCarId(vehicleVO.getVin());
                        DataMilesVO dataMilesVO = new DataMilesVO(carId);
                        if ("Y".equals(data.getTargetYn())) {
                            // UBI ????????? ??????
                            dataMilesVO.setUbiStatus(DataMilesVO.UBI_STATUS.JOIN);
                            // ?????? ?????? ?????? ??????
                            developersViewModel.reqDetail(new Detail.Request(carId));
                        } else {
                            // UBI??? ???????????? ?????? ??????
                            if ("Y".equals(data.getSupportedYn())) {
                                // UBI ?????? ????????? ??????
                                // ?????? ?????? ?????? ????????? ????????? UI ??????.
                                dataMilesVO.setUbiStatus(DataMilesVO.UBI_STATUS.NOT_JOIN);
                            } else {
                                // UBI ?????? ???????????? ??????.
                                dataMilesVO.setUbiStatus(DataMilesVO.UBI_STATUS.NOT_SUPPORTED);
                            }
                            dataMilesVO.setChangedDrivingScore(true);
                        }
                        home2DataMilesAdapter.setRows(Collections.singletonList(dataMilesVO));
                        home2DataMilesAdapter.notifyDataSetChanged();

                        // ????????? ?????? ????????? ??????
                        developersViewModel.reqReplacements(new Replacements.Request(carId));
//                    gnsViewModel.reqGNS1010(new GNS_1010.Request(APPIAInfo.GM_CARLST_04.getId(), vehicleVO.getVin(), vehicleVO.getMdlNm()));

                        // ?????? ?????? ????????? ??????
                        developersViewModel.reqDtc(new Dtc.Request(carId));
                    }
                    break;
                }
            }
        });

        // ????????? ????????? : ???????????? ?????? ????????? ??????
        developersViewModel.getRES_DETAIL().observe(lifecycleOwner, result -> {
            NetUIResponse.Status status = result.status;

            // TODO ?????? ?????????. ?????? ??????.
//            status = NetUIResponse.Status.ERROR;

            switch (status) {
                case ERROR: {
                    if(vehicleVO!=null&&!TextUtils.isEmpty(vehicleVO.getVin())) {
                        String carId = developersViewModel.getCarId(vehicleVO.getVin());
                        DataMilesVO dataMilesVO = home2DataMilesAdapter.findVOByCarId(carId);
                        if (dataMilesVO != null) {
                            dataMilesVO.setDetailStatus(DataMilesVO.STATUS.FAIL);
                            dataMilesVO.setChangedDrivingScore(true);
                        }
                    }
                    break;
                }
                case SUCCESS: {
                    setDatamilesDetail(result);
                    break;
                }
            }
        });

        // ????????? ????????? : ????????? ?????? ????????? ??????
        developersViewModel.getRES_REPLACEMENTS().observe(lifecycleOwner, result -> {
            NetUIResponse.Status status = result.status;

            // TODO ?????? ?????????. ?????? ??????.
//            status = NetUIResponse.Status.SUCCESS;

            switch (status) {
                case ERROR: {
                    if(vehicleVO!=null&&!TextUtils.isEmpty(vehicleVO.getVin())) {
                        String carId = developersViewModel.getCarId(vehicleVO.getVin());
                        DataMilesVO dataMilesVO = home2DataMilesAdapter.findVOByCarId(carId);
                        if (dataMilesVO != null) {
                            dataMilesVO.setReplacementsStatus(DataMilesVO.STATUS.FAIL);
                            dataMilesVO.setChangedReplacements(true);
                        }
                    }
                    break;
                }
                case SUCCESS: {
                    setDatamilesReplacements(result);
                    break;
                }
            }
        });

        // ????????? ????????? : ?????? ?????? ????????? ????????? ??????
        developersViewModel.getRES_DTC().observe(lifecycleOwner, result -> {
            NetUIResponse.Status status = result.status;

            // TODO ?????? ?????????. ?????? ??????.
//            status = NetUIResponse.Status.SUCCESS;

            switch (status) {
                case SUCCESS: {
                    setDatamilesDtc(result);
                    break;
                }
            }
        });

        gnsViewModel.getRES_GNS_1010().observe(lifecycleOwner, result -> {
            NetUIResponse.Status status = result.status;

            // TODO ?????? ?????????. ?????? ??????.
//            status = NetUIResponse.Status.SUCCESS;

            switch (status) {
                case SUCCESS: {
                    setServiceCoupons(result);
                    break;
                }
            }
        });


        btrViewModel.getRES_BTR_1001().observe(lifecycleOwner, result -> {
            switch (result.status) {
                case LOADING:
                    ((MainActivity) getActivity()).showProgressDialog(true);
                    break;
                case SUCCESS:
                    if (result.data != null && result.data.getRtCd().equalsIgnoreCase("0000") && result.data.getBtrVO() != null) {
                        BtrVO btrVO = null;
                        try {
                            btrVO = ((BtrVO) result.data.getBtrVO().clone());
                        } catch (Exception e) {

                        }
                        if (btrVO != null &&
                                (StringUtil.isValidString(btrVO.getBltrChgYn()).equalsIgnoreCase("Y") ||
                                 StringUtil.isValidString(btrVO.getBltrChgYn()).equalsIgnoreCase("N") ||
                                 StringUtil.isValidString(btrVO.getBltrChgYn()).equalsIgnoreCase("C"))
                        ) {
                            ((MainActivity) getActivity()).startActivitySingleTop(new Intent(getActivity(), BtrBluehandsActivity.class)
                                    , RequestCodes.REQ_CODE_ACTIVITY.getCode()
                                    , VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                            ((MainActivity) getActivity()).showProgressDialog(false);
                            break;
                        }
                    } else if (result.data != null && result.data.getRtCd().equalsIgnoreCase("2005")) {
                        MiddleDialog.dialogBtrApply(getActivity(), () -> {
                            PhoneUtil.phoneDial(getActivity(), getString(R.string.word_home_14));
                        }, () -> {

                        });
                        ((MainActivity) getActivity()).showProgressDialog(false);
                        break;
                    }
                default:
                    ((MainActivity) getActivity()).showProgressDialog(false);
                    String serverMsg = "";
                    try {
                        serverMsg = result.data.getRtMsg();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        SnackBarUtil.show(getActivity(), (TextUtils.isEmpty(serverMsg)) ? getString(R.string.r_flaw06_p02_snackbar_1) : serverMsg);
                    }
                    break;
            }
        });

    }

    private void setViewModel() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            lgnViewModel = new ViewModelProvider(activity).get(LGNViewModel.class);
            developersViewModel = new ViewModelProvider(activity).get(DevelopersViewModel.class);
            gnsViewModel = new ViewModelProvider(activity).get(GNSViewModel.class);
            btrViewModel = new ViewModelProvider(activity).get(BTRViewModel.class);
        }
    }

    /**
     * @biref ??? ?????????
     * ??????????????? ??? ??? ????????? ?????????
     */
    private void initView() {
        me.rv.setLayoutManager(new LinearLayoutManager(getContext()));
        me.rv.setHasFixedSize(true);
        me.rv.addItemDecoration(new RecyclerViewDecoration((int) DeviceUtil.dip2Pixel(getContext(), 0.0f)));
        home2DataMilesAdapter = new Home2DataMilesAdapter(onSingleClickListener);
//        home2AsanAdapter = new Home2AsanAdapter(onSingleClickListener);
        home2WarrantyAdapter = new Home2WarrantyAdapter(onSingleClickListener);
        home2BtrAdapter = new Home2BtrAdapter(onSingleClickListener);
        concatAdapter = new ConcatAdapter(home2DataMilesAdapter, home2WarrantyAdapter, home2BtrAdapter);
        me.rv.setAdapter(concatAdapter);
    }

    /**
     * ????????? ????????? ?????? ?????? ?????? ??????
     *
     * @param result ?????? ?????? ?????? ?????????.
     */
    private void setDatamilesDetail(NetUIResponse<Detail.Response> result) {
        Detail.Response data = result.data;
        String carId = developersViewModel.getCarId(vehicleVO.getVin());

        /*
        // TODO : ???????????????????????? ???????????? ?????? ??????.
        String dummyData = "{\"safetyDrvScore\":75,\"prevSafetyDrvScore\":76,\"isDiscountYn\":\"Y\",\"bsrtAccCount\":4,\"bsrtDecCount\":0,\"nightDrvCount\":2,\"rangeDrvDist\":1201,\"distribution\":10,\"modelDistribution\":21,\"insightMsg\":\"????????? ?????? ?????? ??????????????????.\n???????????? ??????????????? ????????? ?????? ????????? ???????????????!\n(?????? 12% ??????)\",\"scoreDate\":20200408223039,\"msgId\":\"11e77efa-aff0-4b3c-a5a0-c4cde4674963\"}";
        home2DataMilesAdapter.setDetail(carId, new Gson().fromJson(dummyData, Detail.Response.class));
        home2DataMilesAdapter.notifyDataSetChanged();
        */

        if (data != null) {
            home2DataMilesAdapter.setDetail(carId, result.data);
            home2DataMilesAdapter.notifyDataSetChanged();
        }
    }

    private void setDatamilesReplacements(NetUIResponse<Replacements.Response> result) {
        Replacements.Response data = result.data;
        String carId = developersViewModel.getCarId(vehicleVO.getVin());

        /*
        // TODO : ???????????????????????? ???????????? ?????? ??????.
        String dummyData = "{\"sests\":[" +
                "{\"sestCode\":1,\"sestName\":\"????????????/??????\",\"stdDistance\":10000,\"lastInfo\":" +
                "{\"replacementDate\":\"20200701101011\",\"updateDate\":\"20200701101011\",\"odometer\":\"2400\"}}," +
                "{\"sestCode\":2,\"sestName\":\"???????????????\",\"stdDistance\":20000,\"lastInfo\":" +
                "{\"replacementDate\":\"20200701101011\",\"updateDate\":\"20200701101011\",\"odometer\":\"25420\"}}" +
                "],\"odometer\":{\"timestamp\":\"20200114152139\",\"value\":12320,\"unit\":1},\"msgId\":\"5db9fc02-1b36-448e-9307-52761fd9ad92\"}";
        home2DataMilesAdapter.setReplacements(carId, new Gson().fromJson(dummyData, Replacements.Response.class));
        home2DataMilesAdapter.notifyDataSetChanged();
        */

        if (data != null) {
            home2DataMilesAdapter.setReplacements(carId, result.data, vehicleVO.isEV());
            home2DataMilesAdapter.notifyDataSetChanged();
        }
    }

    private void setServiceCoupons(NetUIResponse<GNS_1010.Response> result) {
        GNS_1010.Response data = result.data;
        String carId = developersViewModel.getCarId(vehicleVO.getVin());

        /*
        // TODO : ???????????????????????? ???????????? ?????? ??????.
        String dummyData = "{\"rtCd\":\"0000\",\"rtMsg\":\"msg\",\"cpnList\":[" +
                "{\"itemDivCd\":\"11\",\"itemNm\":\"??????????????????\",\"totCnt\":\"0\",\"remCnt\":\"2\",\"useCnt\":\"0\"}," +
                "{\"itemDivCd\":\"13\",\"itemNm\":\"???????????????\",\"totCnt\":\"0\",\"remCnt\":\"11\",\"useCnt\":\"0\"}," +
                "{\"itemDivCd\":\"34\",\"itemNm\":\"???????????? ??????\",\"totCnt\":\"0\",\"remCnt\":\"7\",\"useCnt\":\"0\"}]}";
        data = new Gson().fromJson(dummyData, GNS_1010.Response.class);
        */

        if (data != null) {
            home2DataMilesAdapter.setCoupons(carId, data.getCpnList());
        }
    }

    private void setDatamilesDtc(NetUIResponse<Dtc.Response> result) {
        Dtc.Response data = result.data;
        String carId = developersViewModel.getCarId(vehicleVO.getVin());

        /*
        // TODO : ???????????????????????? ???????????? ?????? ??????.
        String dummyData = "{\"dtcList\":[{\"timestamp\":\"20200114152139\",\"dtcType\":\"????????? ?????? ?????????\",\"description\":\"????????? ?????? 6??? ?????? ???????????? ????????? ?????????, 6??? ????????? ???????????? ????????? ????????? ?????? ?????? ???????????? ????????? ????????? ??? ??????????????? ???????????? ????????? ????????? ?????? ???????????? ????????? ?????? ?????? ?????????????????? ???????????????????????? ????????? ???????????????.\",\"dtcCnt\":2}],\"msgId\":\"11e77efa-aff0-4b3c-a5a0-c4cde4674963\"}";
        home2DataMilesAdapter.setDtc(carId, new Gson().fromJson(dummyData, Dtc.Response.class));
        home2DataMilesAdapter.notifyDataSetChanged();
         */

        if (data != null) {
            home2DataMilesAdapter.setDtc(carId, result.data);
            home2DataMilesAdapter.notifyDataSetChanged();
        }
    }

    /**
     * @param result
     * @brief ????????? ?????? ??????
     */
    private void setViewBtrInfo(NetUIResponse<LGN_0003.Response> result) {
        List<LGN_0003.Response> responseList = new ArrayList<>();
        if (result != null && result.data != null) {
            responseList.add(result.data);
        } else {
            responseList.add(null);
        }
        home2BtrAdapter.setRows(responseList);
        home2BtrAdapter.notifyDataSetChanged();
//        concatAdapter.removeAdapter(home2BtrAdapter);
//        concatAdapter.addAdapter(ADAPTER_ORDER_3, home2BtrAdapter);
    }

    /**
     * @biref ?????????????????? ??????
     */
    private void setViewWarranty() {
        List<VehicleVO> vehicleVOS = new ArrayList<>();
        vehicleVOS.add(vehicleVO);
        home2WarrantyAdapter.setRows(vehicleVOS);
        home2WarrantyAdapter.notifyDataSetChanged();
//        concatAdapter.removeAdapter(home2WarrantyAdapter);
//        concatAdapter.addAdapter(ADAPTER_ORDER_2, home2WarrantyAdapter);
    }

//    /**
//     * @param result
//     * @brief ?????? ?????? ??????
//     */
//    private void setViewAsanList(NetUIResponse<LGN_0003.Response> result) {
//        List<MainHistVO> list = new ArrayList<>();
//        if (result.data != null && result.data.getAsnHistList() != null)
//            list.addAll(result.data.getAsnHistList());
//
//        if (list == null || list.size() == 0) {
//            list = new ArrayList<>();
//            list.add(new MainHistVO("", "", "", "", "", "", "", ""));
//        }
//
//        home2AsanAdapter.setRows(list);
//        home2AsanAdapter.notifyDataSetChanged();
////        concatAdapter.removeAdapter(home2AsanAdapter);
////        concatAdapter.addAdapter(ADAPTER_ORDER_1, home2AsanAdapter);
//    }


    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onClickCommon(View v) {
        try {
            switch (v.getId()) {
                case R.id.tv_btr_apply:
                    MiddleDialog.dialogBtrApply(getActivity(), () -> {
                        PhoneUtil.phoneDial(getActivity(), getString(R.string.word_home_14));
//                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(WebView.SCHEME_TEL + getString(R.string.word_home_14))));
                    }, () -> {

                    });
                    break;
                case R.id.tv_title_btr_term:
                    String annMgmtCd;
                    try {
                        annMgmtCd = "BTR-" + vehicleVO.getMdlCd();
                        ((MainActivity) getActivity()).startActivitySingleTop(new Intent(getActivity(), BtrServiceInfoActivity.class)
                                        .putExtra(KeyNames.KEY_NAME_ADMIN_CODE, annMgmtCd)
                                , RequestCodes.REQ_CODE_ACTIVITY.getCode()
                                , VariableType.ACTIVITY_TRANSITION_ANIMATION_NONE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.l_btr:
                    if (vehicleVO != null && !TextUtils.isEmpty(vehicleVO.getVin())) {
                        btrViewModel.reqBTR1001(new BTR_1001.Request(APPIAInfo.GM_BT02.getId(), vehicleVO.getVin()));
                    }
//                ((MainActivity) getActivity()).startActivitySingleTop(new Intent(getActivity(), BtrBluehandsActivity.class)
//                        , RequestCodes.REQ_CODE_ACTIVITY.getCode()
//                        , VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                    break;
                case R.id.l_warranty:
                    ((MainActivity) getActivity()).startActivitySingleTop(new Intent(getActivity(), WarrantyRepairGuideActivity.class)
                            , RequestCodes.REQ_CODE_ACTIVITY.getCode()
                            , VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                    break;
                case R.id.tv_datamiles_more:
                case R.id.iv_datamiles_service_guide:
//                     ????????? ????????? : ????????? ??????
                    Object tag = v.getTag();
                    if (tag instanceof String) {
                        ((MainActivity) getActivity()).startActivitySingleTop(new Intent(getActivity(), GAWebActivity.class)
                                        .putExtra(KeyNames.KEY_NAME_URL, (String) tag)
                                , RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                    }
                    break;
                case R.id.ll_datamiles_driving_score_error:
                    // ????????? ????????? : ?????? ?????? ?????? ?????? ??????.
                    developersViewModel.reqDetail(new Detail.Request(developersViewModel.getCarId(vehicleVO.getVin())));
                    break;
                case R.id.ll_datamiles_expenables_error:
                    // ????????? ????????? : ????????? ?????? ?????? ??????.
                    if(vehicleVO!=null&&!TextUtils.isEmpty(vehicleVO.getVin())) {
                        developersViewModel.reqReplacements(new Replacements.Request(developersViewModel.getCarId(vehicleVO.getVin())));
                    }
                    break;
                case R.id.btn_asan_detail:
                    ((MainActivity) getActivity()).startActivitySingleTop(new Intent(getActivity(), ServiceRepairReserveHistoryActivity.class).putExtra(KeyNames.KEY_NAME_TAB_TWO, true), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                    break;

            }
        }catch (Exception e){
            //do nothing
        }
    }

    public void initScrollPosition(){
        try {
            me.rv.scrollToPosition(0);
        }catch (Exception e){

        }
    }

    @Override
    public void onRefresh() {
        Log.e("onResume", "onReusme FragmentHome2");

        //????????????????????? ???????????? ????????? ??????
        if(((MainActivity)getActivity()).isMoveHomeBottom()){
            ((MainActivity)getActivity()).setMoveHomeBottom(false);
        }else{
            //??? ???????????? ???????????? ??? ??????
            ((MainActivity)getActivity()).initFragmentHome(1);
            return;
        }

        try {
            vehicleVO = lgnViewModel.getMainVehicleFromDB();
            checkIndicatorCnt();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        lgnViewModel.reqLGN0003(new LGN_0003.Request(APPIAInfo.GM01.getId(), vehicleVO.getVin()));

        setViewWarranty();
        setViewBtrInfo(null);

        String carId = developersViewModel.getCarId(vehicleVO.getVin());
        String userId = "";
        if(loginInfoDTO.getProfile()!=null)
            userId = loginInfoDTO.getProfile().getId();

        // Car ID ?????? ?????? ???????????? ????????? ????????? ????????? ??????.
        if (!TextUtils.isEmpty(carId) && developersViewModel.checkCarInfoToDevelopers(vehicleVO.getVin(), userId) == STAT_AGREEMENT) {
            // CarId??? ????????????, ?????? ?????? ??? ??????.

            // ????????? ????????? ?????? URL ??????.
            home2DataMilesAdapter.setMoreUrl(developersViewModel.getDataMilesDetailUrl(loginInfoDTO.getAccessToken(), userId, carId));
            // ????????? ????????? ?????? ??????.
            developersViewModel.reqTarget(new Target.Request(developersViewModel.getCarId(vehicleVO.getVin())));
            SubActivity.setStatusBarColor(getActivity(), R.color.x_ffffff);
        } else {
            SubActivity.setStatusBarColor(getActivity(), R.color.x_f8f8f8);
            if (home2DataMilesAdapter != null) {
                home2DataMilesAdapter.clear();
                home2DataMilesAdapter.notifyDataSetChanged();
            }
        }
        ((MainActivity) getActivity()).setGNB("", View.GONE);
    }

    private void checkIndicatorCnt() {
        int indicatorCnt = 0;
        try{
            indicatorCnt = Integer.parseInt(lgnViewModel.selectGlobalDataFromDB(KeyNames.KEY_NAME_DB_GLOBAL_DATA_ISINDICATOR));
        }catch (Exception e){
            indicatorCnt = 0;
        }
        if(indicatorCnt<10)
            indicatorCnt++;

        lgnViewModel.updateGlobalDataToDB(KeyNames.KEY_NAME_DB_GLOBAL_DATA_ISINDICATOR, indicatorCnt+"");
    }

}
