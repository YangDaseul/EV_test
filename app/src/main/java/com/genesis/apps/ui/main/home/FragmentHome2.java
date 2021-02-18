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
import com.genesis.apps.comm.model.api.gra.GNS_1010;
import com.genesis.apps.comm.model.api.gra.LGN_0003;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.DataMilesVO;
import com.genesis.apps.comm.model.vo.MainHistVO;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.net.NetUIResponse;
import com.genesis.apps.comm.net.ga.LoginInfoDTO;
import com.genesis.apps.comm.util.DeviceUtil;
import com.genesis.apps.comm.util.PhoneUtil;
import com.genesis.apps.comm.util.RecyclerViewDecoration;
import com.genesis.apps.comm.viewmodel.DevelopersViewModel;
import com.genesis.apps.comm.viewmodel.GNSViewModel;
import com.genesis.apps.comm.viewmodel.LGNViewModel;
import com.genesis.apps.databinding.FragmentHome2Binding;
import com.genesis.apps.ui.common.activity.DataMilesWebViewActivity;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;
import com.genesis.apps.ui.common.fragment.SubFragment;
import com.genesis.apps.ui.main.MainActivity;
import com.genesis.apps.ui.main.home.view.Home2AsanAdapter;
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
    private Home2AsanAdapter home2AsanAdapter;
    private Home2WarrantyAdapter home2WarrantyAdapter;
    private Home2BtrAdapter home2BtrAdapter;
    private LGNViewModel lgnViewModel;
    private VehicleVO vehicleVO;
    private DevelopersViewModel developersViewModel;
    private GNSViewModel gnsViewModel;

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
    }

    private void setObserver() {
        LifecycleOwner lifecycleOwner = getViewLifecycleOwner();

        lgnViewModel.getRES_LGN_0003().observe(lifecycleOwner, result -> {
            switch (result.status) {
                case SUCCESS:
                    setViewAsanList(result);
                    setViewWarranty();
                    setViewBtrInfo(result);
                    break;
            }
        });

        developersViewModel.getRES_TARGET().observe(lifecycleOwner, result -> {
            NetUIResponse.Status status = result.status;
            Target.Response data = result.data;

            // TODO 더미 데이터. 삭제 필요.
//            status = NetUIResponse.Status.SUCCESS;

            switch (status) {
                case SUCCESS: {
                    // TODO 데미 데이터 삭제 필요.
//                    String dummyData = "{\"targetYn\":\"N\",\"supportedYn\":\"Y\",\"msgId\":\"11e77efa-aff0-4b3c-a5a0-c4cde4674963\"}";
//                    data = new Gson().fromJson(dummyData, Target.Response.class);

                    String carId = developersViewModel.getCarId(vehicleVO.getVin());
                    DataMilesVO dataMilesVO = new DataMilesVO(carId);
                    if ("Y".equals(data.getTargetYn())) {
                        // UBI 가입한 상태
                        dataMilesVO.setUbiStatus(DataMilesVO.UBI_STATUS.JOIN);
                        // 안전 운전 정보 조회
                        developersViewModel.reqDetail(new Detail.Request(carId));
                    } else {
                        // UBI를 가입하지 않은 상태
                        if ("Y".equals(data.getSupportedYn())) {
                            // UBI 가입 가능한 상태
                            // 안전 운전 점수 서비스 가이드 UI 표시.
                            dataMilesVO.setUbiStatus(DataMilesVO.UBI_STATUS.NOT_JOIN);
                        } else {
                            // UBI 가입 불가능한 상태.
                            dataMilesVO.setUbiStatus(DataMilesVO.UBI_STATUS.NOT_SUPPORTED);
                        }
                    }
                    home2DataMilesAdapter.setRows(Collections.singletonList(dataMilesVO));
                    home2DataMilesAdapter.notifyDataSetChanged();

                    // 소모품 현황 데이터 조회
                    developersViewModel.reqReplacements(new Replacements.Request(carId));
                    gnsViewModel.reqGNS1010(new GNS_1010.Request(APPIAInfo.GM_CARLST_04.getId(), vehicleVO.getVin(), vehicleVO.getMdlNm()));

                    // 고장 코드 데이터 조회
                    developersViewModel.reqDtc(new Dtc.Request(carId));
                    break;
                }
            }
        });

        // 데이터 마일스 : 안전운전 점수 옵저버 등록
        developersViewModel.getRES_DETAIL().observe(lifecycleOwner, result -> {
            NetUIResponse.Status status = result.status;

            // TODO 더미 데이터. 삭제 필요.
//            status = NetUIResponse.Status.ERROR;

            switch (status) {
                case ERROR: {
                    String carId = developersViewModel.getCarId(vehicleVO.getVin());
                    DataMilesVO dataMilesVO = home2DataMilesAdapter.findVOByCarId(carId);
                    if (dataMilesVO != null) {
                        dataMilesVO.setDetailStatus(DataMilesVO.STATUS.FAIL);
                    }
                    home2DataMilesAdapter.notifyDataSetChanged();
                    break;
                }
                case SUCCESS: {
                    setDatamilesDetail(result);
                    break;
                }
            }
        });

        // 데이터 마일스 : 소모품 현황 옵저버 등록
        developersViewModel.getRES_REPLACEMENTS().observe(lifecycleOwner, result -> {
            NetUIResponse.Status status = result.status;

            // TODO 더미 데이터. 삭제 필요.
//            status = NetUIResponse.Status.SUCCESS;

            switch (status) {
                case ERROR: {
                    String carId = developersViewModel.getCarId(vehicleVO.getVin());
                    DataMilesVO dataMilesVO = home2DataMilesAdapter.findVOByCarId(carId);
                    if (dataMilesVO != null) {
                        dataMilesVO.setReplacementsStatus(DataMilesVO.STATUS.FAIL);
                    }
                    break;
                }
                case SUCCESS: {
                    setDatamilesReplacements(result);
                    break;
                }
            }
        });

        // 데이터 마일스 : 고장 코드 데이터 옵저버 등록
        developersViewModel.getRES_DTC().observe(lifecycleOwner, result -> {
            NetUIResponse.Status status = result.status;

            // TODO 더미 데이터. 삭제 필요.
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

            // TODO 더미 데이터. 삭제 필요.
//            status = NetUIResponse.Status.SUCCESS;

            switch (status) {
                case SUCCESS: {
                    setServiceCoupons(result);
                    break;
                }
            }
        });
    }

    private void setViewModel() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            lgnViewModel = new ViewModelProvider(activity).get(LGNViewModel.class);
            developersViewModel = new ViewModelProvider(activity).get(DevelopersViewModel.class);
            gnsViewModel = new ViewModelProvider(activity).get(GNSViewModel.class);
        }
    }

    /**
     * @biref 뷰 초기화
     * 리사이클러 뷰 및 어댑터 초기화
     */
    private void initView() {
        me.rv.setLayoutManager(new LinearLayoutManager(getContext()));
        me.rv.setHasFixedSize(true);
        me.rv.addItemDecoration(new RecyclerViewDecoration((int) DeviceUtil.dip2Pixel(getContext(), 0.0f)));
        home2DataMilesAdapter = new Home2DataMilesAdapter(onSingleClickListener);
        home2AsanAdapter = new Home2AsanAdapter(onSingleClickListener);
        home2WarrantyAdapter = new Home2WarrantyAdapter(onSingleClickListener);
        home2BtrAdapter = new Home2BtrAdapter(onSingleClickListener);
        concatAdapter = new ConcatAdapter(home2DataMilesAdapter, home2AsanAdapter, home2WarrantyAdapter, home2BtrAdapter);
        me.rv.setAdapter(concatAdapter);
    }

    /**
     * 데이터 마일스 안전 운전 정보 표시
     *
     * @param result 안전 운전 정보 데이터.
     */
    private void setDatamilesDetail(NetUIResponse<Detail.Response> result) {
        Detail.Response data = result.data;
        String carId = developersViewModel.getCarId(vehicleVO.getVin());

        /*
        // TODO : 더미데이터이므로 실제로는 삭제 필요.
        String dummyData = "{\"safetyDrvScore\":75,\"prevSafetyDrvScore\":76,\"isDiscountYn\":\"Y\",\"bsrtAccCount\":4,\"bsrtDecCount\":0,\"nightDrvCount\":2,\"rangeDrvDist\":1201,\"distribution\":10,\"modelDistribution\":21,\"insightMsg\":\"최고의 모범 안전 운전자입니다.\n안전운전 유지하시고 자동차 보험 혜택을 받아보세요!\n(최대 12% 할인)\",\"scoreDate\":20200408223039,\"msgId\":\"11e77efa-aff0-4b3c-a5a0-c4cde4674963\"}";
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
        // TODO : 더미데이터이므로 실제로는 삭제 필요.
        String dummyData = "{\"sests\":[" +
                "{\"sestCode\":1,\"sestName\":\"엔진오일/필터\",\"stdDistance\":10000,\"lastInfo\":" +
                "{\"replacementDate\":\"20200701101011\",\"updateDate\":\"20200701101011\",\"odometer\":\"2400\"}}," +
                "{\"sestCode\":2,\"sestName\":\"에어클리너\",\"stdDistance\":20000,\"lastInfo\":" +
                "{\"replacementDate\":\"20200701101011\",\"updateDate\":\"20200701101011\",\"odometer\":\"25420\"}}" +
                "],\"odometer\":{\"timestamp\":\"20200114152139\",\"value\":12320,\"unit\":1},\"msgId\":\"5db9fc02-1b36-448e-9307-52761fd9ad92\"}";
        home2DataMilesAdapter.setReplacements(carId, new Gson().fromJson(dummyData, Replacements.Response.class));
        home2DataMilesAdapter.notifyDataSetChanged();
        */

        if (data != null) {
            home2DataMilesAdapter.setReplacements(carId, result.data);
            home2DataMilesAdapter.notifyDataSetChanged();
        }
    }

    private void setServiceCoupons(NetUIResponse<GNS_1010.Response> result) {
        GNS_1010.Response data = result.data;
        String carId = developersViewModel.getCarId(vehicleVO.getVin());

        /*
        // TODO : 더미데이터이므로 실제로는 삭제 필요.
        String dummyData = "{\"rtCd\":\"0000\",\"rtMsg\":\"msg\",\"cpnList\":[" +
                "{\"itemDivCd\":\"11\",\"itemNm\":\"엔진오일세트\",\"totCnt\":\"0\",\"remCnt\":\"2\",\"useCnt\":\"0\"}," +
                "{\"itemDivCd\":\"13\",\"itemNm\":\"에어컨필터\",\"totCnt\":\"0\",\"remCnt\":\"11\",\"useCnt\":\"0\"}," +
                "{\"itemDivCd\":\"34\",\"itemNm\":\"브레이크 오일\",\"totCnt\":\"0\",\"remCnt\":\"7\",\"useCnt\":\"0\"}]}";
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
        // TODO : 더미데이터이므로 실제로는 삭제 필요.
        String dummyData = "{\"dtcList\":[{\"timestamp\":\"20200114152139\",\"dtcType\":\"에어백 제어 시스템\",\"description\":\"시동을 키고 6초 동안 경고등이 켜지지 않거나, 6초 후에도 경고등이 꺼지지 않거나 주행 중에 경고등이 켜지면 에어백 및 프리텐셔너 시트벨트 장치에 이상이 있는 것이므로 가까운 자사 직영 서비스센터나 지정정비센터에서 점검을 받으십시오.\",\"dtcCnt\":2}],\"msgId\":\"11e77efa-aff0-4b3c-a5a0-c4cde4674963\"}";
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
     * @brief 버틀러 정보 표시
     */
    private void setViewBtrInfo(NetUIResponse<LGN_0003.Response> result) {
        List<LGN_0003.Response> responseList = new ArrayList<>();
        if (result.data != null) {
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
     * @biref 보증수리안내 표시
     */
    private void setViewWarranty() {
        List<VehicleVO> vehicleVOS = new ArrayList<>();
        vehicleVOS.add(vehicleVO);
        home2WarrantyAdapter.setRows(vehicleVOS);
        home2WarrantyAdapter.notifyDataSetChanged();
//        concatAdapter.removeAdapter(home2WarrantyAdapter);
//        concatAdapter.addAdapter(ADAPTER_ORDER_2, home2WarrantyAdapter);
    }

    /**
     * @param result
     * @brief 정비 내역 표시
     */
    private void setViewAsanList(NetUIResponse<LGN_0003.Response> result) {
        List<MainHistVO> list = new ArrayList<>();
        if (result.data != null && result.data.getAsnHistList() != null)
            list.addAll(result.data.getAsnHistList());

        if (list == null || list.size() == 0) {
            list = new ArrayList<>();
            list.add(new MainHistVO("", "", "", "", "", "", "", ""));
        }

        home2AsanAdapter.setRows(list);
        home2AsanAdapter.notifyDataSetChanged();
//        concatAdapter.removeAdapter(home2AsanAdapter);
//        concatAdapter.addAdapter(ADAPTER_ORDER_1, home2AsanAdapter);
    }


    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onClickCommon(View v) {

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
                    annMgmtCd = "BTR-" + vehicleVO.getMdlNm();
                    ((MainActivity) getActivity()).startActivitySingleTop(new Intent(getActivity(), BtrServiceInfoActivity.class)
                                    .putExtra(KeyNames.KEY_NAME_ADMIN_CODE, annMgmtCd)
                            , RequestCodes.REQ_CODE_ACTIVITY.getCode()
                            , VariableType.ACTIVITY_TRANSITION_ANIMATION_NONE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.l_btr:
                ((MainActivity) getActivity()).startActivitySingleTop(new Intent(getActivity(), BtrBluehandsActivity.class)
                        , RequestCodes.REQ_CODE_ACTIVITY.getCode()
                        , VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                break;
            case R.id.l_warranty:
                ((MainActivity) getActivity()).startActivitySingleTop(new Intent(getActivity(), WarrantyRepairGuideActivity.class)
                        , RequestCodes.REQ_CODE_ACTIVITY.getCode()
                        , VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                break;
            case R.id.tv_datamiles_more:
                // 데이터 마일스 : 더보기 버튼
                Object tag = v.getTag();
                if (tag instanceof String) {
                    ((MainActivity) getActivity()).startActivitySingleTop(new Intent(getActivity(), DataMilesWebViewActivity.class)
                                    .putExtra(KeyNames.KEY_NAME_URL, (String) tag)
                            , RequestCodes.REQ_CODE_ACTIVITY.getCode()
                            , VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                }
                break;
            case R.id.ll_datamiles_driving_score_error:
                // 데이터 마일스 : 안전 운전 점수 새로 고침.
                developersViewModel.reqDetail(new Detail.Request(developersViewModel.getCarId(vehicleVO.getVin())));
                break;
            case R.id.ll_datamiles_expenables_error:
                // 데이터 마일스 : 소모품 현황 새로 고침.
                developersViewModel.reqReplacements(new Replacements.Request(developersViewModel.getCarId(vehicleVO.getVin())));
                break;
            case R.id.btn_asan_detail:
                ((MainActivity) getActivity()).startActivitySingleTop(new Intent(getActivity(), ServiceRepairReserveHistoryActivity.class).putExtra(KeyNames.KEY_NAME_TAB_TWO, true), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                break;

        }
    }


    @Override
    public void onRefresh() {
        Log.e("onResume", "onReusme FragmentHome2");
        try {
            vehicleVO = lgnViewModel.getMainVehicleFromDB();
            lgnViewModel.updateGlobalDataToDB(KeyNames.KEY_NAME_DB_GLOBAL_DATA_ISINDICATOR, VariableType.COMMON_MEANS_NO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        SubActivity.setStatusBarColor(getActivity(), R.color.x_f8f8f8);
        lgnViewModel.reqLGN0003(new LGN_0003.Request(APPIAInfo.GM01.getId(), vehicleVO.getVin()));
        String carId = developersViewModel.getCarId(vehicleVO.getVin());
        String userId = loginInfoDTO.getProfile().getId();
        // Car ID 값이 있는 경우에만 데이터 마일스 정보를 노출.
        if (!TextUtils.isEmpty(carId) && developersViewModel.checkCarInfoToDevelopers(vehicleVO.getVin(), userId) == STAT_AGREEMENT) {
            // CarId가 존재하고, 동의 상태 인 경우.

            // 데이터 마일스 상세 URL 설정.
            home2DataMilesAdapter.setMoreUrl(developersViewModel.getDataMilesDetailUrl(loginInfoDTO.getAccessToken(), userId, carId));
            // 데이터 마일스 연동 시작.
            developersViewModel.reqTarget(new Target.Request(developersViewModel.getCarId(vehicleVO.getVin())));
        }
        ((MainActivity) getActivity()).setGNB("", View.GONE);
    }

}
