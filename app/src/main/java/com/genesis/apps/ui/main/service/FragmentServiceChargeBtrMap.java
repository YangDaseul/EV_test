package com.genesis.apps.ui.main.service;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.CHB_1021;
import com.genesis.apps.comm.model.api.gra.CHB_1022;
import com.genesis.apps.comm.model.constants.ChargeBtrStatus;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.util.DateUtil;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.comm.viewmodel.CHBViewModel;
import com.genesis.apps.databinding.FragmentMapBinding;
import com.genesis.apps.databinding.LayoutMapOverlayUiBottomInfoBar2Binding;
import com.genesis.apps.ui.common.activity.GpsBaseActivity;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.bottom.DialogChargeBtrDriverInfo;
import com.genesis.apps.ui.common.fragment.SubFragment;
import com.hmns.playmap.PlayMapPoint;
import com.hmns.playmap.shape.PlayMapMarker;

/**
 * 픽업앤충전 현황/예약_서비스 중 화면(SM_CGRV04_03)
 * @author ljeun
 * @since 2021. 5. 10.
 */
public class FragmentServiceChargeBtrMap extends SubFragment<FragmentMapBinding> {

    private final String LOG_TAG = FragmentServiceChargeBtrMap.class.getSimpleName();

    private CHBViewModel chbViewModel;

    private LayoutMapOverlayUiBottomInfoBar2Binding bottomBinding;
    private DialogChargeBtrDriverInfo dialogCHBDriverInfo;

    private VehicleVO mainVehicle;
    private CHB_1021.Response data;

    private String stusMsg = null;

    public static FragmentServiceChargeBtrMap newInstance(CHB_1021.Response data) {
        FragmentServiceChargeBtrMap fragment = new FragmentServiceChargeBtrMap();

        Bundle args = new Bundle();
        args.putSerializable(KeyNames.KEY_NAME_CHB_CONTENTS_VO, data);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null) {
            data = (CHB_1021.Response) getArguments().getSerializable(KeyNames.KEY_NAME_CHB_CONTENTS_VO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.setContentView(inflater, R.layout.fragment_map);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setViewModel();
        setObserver();
        initView();
        initData();
    }

    private void initView() {
        me.pmvMapView.initMap();

        if(ChargeBtrStatus.STATUS_1500.getStusCd().equalsIgnoreCase(StringUtil.isValidString(data.getStatus())))
            me.btnPosRefresh.setVisibility(View.GONE);
        else
            me.btnPosRefresh.setVisibility(View.VISIBLE);

        me.btnMyPosition.setOnClickListener(onSingleClickListener);
        me.btnPosRefresh.setOnClickListener(onSingleClickListener);

        updateBottomView(data.getStatus());
    }

    private void initData() {
    }

    private void reqDriverPos() {
        if (mainVehicle != null && data != null && !TextUtils.isEmpty(data.getOrderId()))
            chbViewModel.reqCHB1022(new CHB_1022.Request(APPIAInfo.SM_CGRV04_03.getId(), data.getOrderId(), mainVehicle.getVin()));
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onClickCommon(View v) {
        switch (v.getId()) {
            case R.id.btn_my_position:
                reqMyLocation();
                break;
            case R.id.btn_pos_refresh:
                reqDriverPos();
                break;
            case R.id.btn_driver_info:
                dialogCHBDriverInfo = new DialogChargeBtrDriverInfo(getContext(), R.style.BottomSheetDialogTheme);
                dialogCHBDriverInfo.setData(data);
                dialogCHBDriverInfo.setStusMsg(stusMsg);
                dialogCHBDriverInfo.setOnDismissListener(dialogInterface -> {

                });
                dialogCHBDriverInfo.show();
                break;
        }
    }

    /**
     * drawMarkerItem 지도에 마커를 그린다.
     */
    public void drawMarkerItem(double lat, double lon) {

        PlayMapMarker markerItem = new PlayMapMarker();
        PlayMapPoint point = new PlayMapPoint(lat, lon);
        markerItem.setMapPoint(point);
        markerItem.setCanShowCallout(false);
        markerItem.setAutoCalloutVisible(false);
        markerItem.setIcon(((BitmapDrawable) getResources().getDrawable(R.drawable.ico_map_pin_active_b, null)).getBitmap());

        me.pmvMapView.removeAllMarkerItem();
        me.pmvMapView.addMarkerItem("start", markerItem);
    }

    @Override
    public void onRefresh() {
        try {
            mainVehicle = chbViewModel.getMainVehicleFromDB();
        } catch (Exception e) {
            mainVehicle = null;
        } finally {
            //소유차량인 고객
            reqDriverPos();
        }
    }

    private void setViewModel() {
        chbViewModel = new ViewModelProvider(this).get(CHBViewModel.class);
    }

    private void setObserver() {
        chbViewModel.getRES_CHB_1022().observe(getViewLifecycleOwner(), result -> {
            switch (result.status) {
                case LOADING:
                    ((SubActivity) getActivity()).showProgressDialog(true);
                    break;
                case SUCCESS:
                    ((SubActivity) getActivity()).showProgressDialog(false);
                    if (result.data != null && result.data.getRtCd().equalsIgnoreCase("0000")) {
                        // TODO 업데이트 처리
                        if(result.data.getLatitude() > 0 && result.data.getLongitude() > 0) {
                            me.pmvMapView.initMap(result.data.getLatitude(), result.data.getLongitude(), ((GpsBaseActivity)getActivity()).DEFAULT_ZOOM);
                            drawMarkerItem(result.data.getLatitude(), result.data.getLongitude());
                        }
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
                            serverMsg = getString(R.string.service_charge_btr_err_16);
                        }
                        SnackBarUtil.show(getActivity(), serverMsg);
                        ((SubActivity) getActivity()).showProgressDialog(false);
                    }
                    break;
            }
        });
    }

    private void updateBottomView(String stusCd) {

        if (!TextUtils.isEmpty(stusCd)) {
            ChargeBtrStatus status = ChargeBtrStatus.findCode(stusCd);
            if (status != null){
                stusMsg = status.getStusMsg();
                if(status.getStusCd().equalsIgnoreCase(ChargeBtrStatus.STATUS_1500.getStusCd())) {
                    if(!TextUtils.isEmpty(data.getBookingDt()))
                        stusMsg = String.format(stusMsg, DateUtil.getDate(DateUtil.getDefaultDateFormat(data.getBookingDt(), DateUtil.DATE_FORMAT_yyyyMMddHHmmss), DateUtil.DATE_FORMAT_aa_hh_mm));
                }
            }
        }

        if (bottomBinding == null) {
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) getActivity().findViewById(R.id.vs_map_overlay_bottom_box).getLayoutParams();
            params.setMargins(0, 0, 0, 0);
//                params.height = (int) DeviceUtil.dip2Pixel(this, 40);
            getActivity().findViewById(R.id.vs_map_overlay_bottom_box).setLayoutParams(params);
            ((GpsBaseActivity) getActivity()).setViewStub(R.id.vs_map_overlay_bottom_box, R.layout.layout_map_overlay_ui_bottom_info_bar_2, (viewStub, inflated) -> {
                bottomBinding = DataBindingUtil.bind(inflated);
                bottomBinding.btnDriverInfo.setOnClickListener(onSingleClickListener);
                bottomBinding.setStusMsg(stusMsg);
            });
        } else {
            bottomBinding.setStusMsg(stusMsg);
        }
    }

    private void reqMyLocation() {
        Log.d(LOG_TAG, "test :: reqMyLocation");
        ((SubActivity) getActivity()).showProgressDialog(true);
        ((GpsBaseActivity) getActivity()).findMyLocation(location -> {
            ((SubActivity) getActivity()).showProgressDialog(false);
            if (location == null) {
                SnackBarUtil.show(getActivity(), "위치 정보를 불러올 수 없습니다. GPS 상태를 확인 후 다시 시도해 주세요.");
                return;
            }

            Log.d(LOG_TAG, "test :: findMyLocation :: location=" + location);
            this.getActivity().runOnUiThread(() -> {
                me.pmvMapView.initMap(location.getLatitude(), location.getLongitude(), ((GpsBaseActivity)getActivity()).DEFAULT_ZOOM);
            });

        }, 5000, GpsBaseActivity.GpsRetType.GPS_RETURN_FIRST, false);
    }
}
