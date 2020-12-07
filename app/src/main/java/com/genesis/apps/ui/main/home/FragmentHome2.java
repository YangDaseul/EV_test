package com.genesis.apps.ui.main.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ReplacementTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.developers.Detail;
import com.genesis.apps.comm.model.api.developers.Dtc;
import com.genesis.apps.comm.model.api.developers.Replacements;
import com.genesis.apps.comm.model.api.developers.Target;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.LGN_0003;
import com.genesis.apps.comm.model.vo.DataMilesVO;
import com.genesis.apps.comm.model.vo.MainHistVO;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.net.NetUIResponse;
import com.genesis.apps.comm.util.DeviceUtil;
import com.genesis.apps.comm.util.RecyclerViewDecoration;
import com.genesis.apps.comm.viewmodel.DevelopersViewModel;
import com.genesis.apps.comm.viewmodel.LGNViewModel;
import com.genesis.apps.databinding.FragmentHome2Binding;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;
import com.genesis.apps.ui.common.fragment.SubFragment;
import com.genesis.apps.ui.main.MainActivity;
import com.genesis.apps.ui.main.home.view.Home2AsanAdapter;
import com.genesis.apps.ui.main.home.view.Home2BtrAdapter;
import com.genesis.apps.ui.main.home.view.Home2DataMilesAdapter;
import com.genesis.apps.ui.main.home.view.Home2WarrantyAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;

public class FragmentHome2 extends SubFragment<FragmentHome2Binding> {

    private final int ADAPTER_ORDER_1 = 0;
    private final int ADAPTER_ORDER_2 = 1;
    private final int ADAPTER_ORDER_3 = 2;
    private final int ADAPTER_ORDER_4 = 3;
    private final int ADAPTER_ORDER_5 = 4;
    private final int ADAPTER_ORDER_6 = 5;

    private ConcatAdapter concatAdapter;
    private Home2DataMilesAdapter home2DataMilesAdapter;
    private Home2AsanAdapter home2AsanAdapter;
    private Home2WarrantyAdapter home2WarrantyAdapter;
    private Home2BtrAdapter home2BtrAdapter;
    private LGNViewModel lgnViewModel;
    private VehicleVO vehicleVO;
    private DevelopersViewModel developersViewModel;

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
        Log.d("FID", "test :: setObserver ::");
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
            Log.d("FID", "test :: RES_TARGET :: result=" + result);
            Log.d("FID", "test :: RES_TARGET :: data=" + result.data);
            String targetYn = "Y";// UBI 가입 여부 더미 값.
            String supportedTargetYn = "Y"; // UBI 가입 가능 여부 더미값.

            String carId = developersViewModel.getCarId(vehicleVO.getVin());
            home2DataMilesAdapter.setRows(Collections.singletonList(new DataMilesVO(carId)));
            if ("Y".equals(targetYn)) {
                // UBI 가입한 상태
                // 안전 운전 정보 조회
                developersViewModel.reqDetail(new Detail.Request(carId));
            } else {
                // UBI를 가입하지 않은 상태
                if ("Y".equals(supportedTargetYn)) {
                    // UBI 가입 가능한 상태
                    // 안전 운전 점수 서비스 가이드 UI 표시.
                }
            }
            // 소모품 현황 데이터 조회
            developersViewModel.reqReplacements(new Replacements.Request(carId));
            // 고장 코드 데이터 조회
//            developersViewModel.reqDtc(new Dtc.Request(carId));
        });

        // 데이터 마일스 : 안전운전 점수 옵저버 등록
        developersViewModel.getRES_DETAIL().observe(lifecycleOwner, result -> {
            Log.d("FID", "test :: RES_DETAIL :: result=" + result);
            setDatamilesDetail(result);
        });

        // 데이터 마일스 : 소모품 현황 옵저버 등록
        developersViewModel.getRES_REPLACEMENTS().observe(lifecycleOwner, result -> {
            Log.d("FID", "test :: getRES_REPLACEMENTS :: result=" + result);
            setDatemilesReplacements(result);
        });

        // 데이터 마일스 : 고장 코드 데이터 옵저버 등록
        developersViewModel.getRES_DTC().observe(lifecycleOwner, result -> {
            Log.d("FID", "test :: getRES_DTC :: result=" + result);
        });
    }

    private void setViewModel() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            lgnViewModel = new ViewModelProvider(activity).get(LGNViewModel.class);
            developersViewModel = new ViewModelProvider(activity).get(DevelopersViewModel.class);
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
        home2DataMilesAdapter = new Home2DataMilesAdapter();
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
//        if (data != null) {
            home2DataMilesAdapter.setDetail(carId, result);
            home2DataMilesAdapter.notifyDataSetChanged();
//        }
    }

    private void setDatemilesReplacements(NetUIResponse<Replacements.Response> result) {
        String carId = developersViewModel.getCarId(vehicleVO.getVin());

        home2DataMilesAdapter.setReplacements(carId);
        home2DataMilesAdapter.notifyDataSetChanged();
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
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(WebView.SCHEME_TEL + getString(R.string.word_home_14))));
                }, () -> {

                });
                break;
            case R.id.tv_title_btr_term:
                String annMgmtCd;
                try {
                    annMgmtCd = "BTR_" + vehicleVO.getMdlNm();
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
                                .putExtra(KeyNames.KEY_NAME_VIN, vehicleVO.getVin())
                        , RequestCodes.REQ_CODE_ACTIVITY.getCode()
                        , VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                break;
            case R.id.l_warranty:
                ((MainActivity) getActivity()).startActivitySingleTop(new Intent(getActivity(), WarrantyRepairGuideActivity.class)
                                .putExtra(KeyNames.KEY_NAME_VIN, vehicleVO.getVin())
                        , RequestCodes.REQ_CODE_ACTIVITY.getCode()
                        , VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                break;
        }
    }


    @Override
    public void onRefresh() {
        Log.e("onResume", "onReusme FragmentHome2");
        try {
            vehicleVO = lgnViewModel.getMainVehicleFromDB();
        } catch (Exception e) {
            e.printStackTrace();
        }

        lgnViewModel.reqLGN0003(new LGN_0003.Request(APPIAInfo.GM01.getId(), vehicleVO.getVin()));
        String carId = developersViewModel.getCarId(vehicleVO.getVin());
        // Car ID 값이 있는 경우에만 데이터 마일스 정보를 노출.
        if (!TextUtils.isEmpty(carId)) {
            developersViewModel.reqTarget(new Target.Request(developersViewModel.getCarId(vehicleVO.getVin())));
        }
        ((MainActivity) getActivity()).setGNB(false, 1, View.GONE);
    }

}
