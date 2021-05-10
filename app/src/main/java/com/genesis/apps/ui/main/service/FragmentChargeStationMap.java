package com.genesis.apps.ui.main.service;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.databinding.FragmentMapBinding;
import com.genesis.apps.ui.common.fragment.SubFragment;
import com.hmns.playmap.PlayMapPoint;
import com.hmns.playmap.shape.PlayMapMarker;

/**
 * Class Name : FragmentChargeStationMap
 *
 * @author Ki-man Kim
 * @since 2021-04-30
 */
public class FragmentChargeStationMap extends SubFragment<FragmentMapBinding> {
    private final int DEFAULT_ZOOM_WIDE = 12;
    private double lat;
    private double lot;

    public static FragmentChargeStationMap newInstance(double lat, double lot) {
        Bundle args = new Bundle();
        args.putDouble(KeyNames.KEY_NAME_LAT, lat);
        args.putDouble(KeyNames.KEY_NAME_LOT, lot);
        FragmentChargeStationMap fragment = new FragmentChargeStationMap();
        fragment.setArguments(args);
        return fragment;
    }

    private FragmentChargeStationMap() {
    }


    /****************************************************************************************************
     * Override Method
     ****************************************************************************************************/
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            lat = args.getDouble(KeyNames.KEY_NAME_LAT);
            lot = args.getDouble(KeyNames.KEY_NAME_LOT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.setContentView(inflater, R.layout.fragment_map);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        me.setLifecycleOwner(getViewLifecycleOwner());
        initialize();
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onClickCommon(View v) {

    }

    /****************************************************************************************************
     * Private Method
     ****************************************************************************************************/
    private void initialize() {
        me.pmvMapView.initMap();
        me.btnMyPosition.setVisibility(View.GONE);
        me.btnPosRefresh.setVisibility(View.GONE);
        me.pmvMapView.initMap(lat, lot, DEFAULT_ZOOM_WIDE);
        drawMarkerItem("station", lat, lot, R.drawable.ic_pin_carcenter);
    }

    /**
     * 마커를 추가하는 함수.
     *
     * @param markerId 추가할 마커에 지정할 ID
     * @param x        추가할 마커 위도
     * @param y        추가할 마커 경도
     * @param iconId   추가할 마커 이미지 Resource ID
     */
    private void drawMarkerItem(String markerId, double x, double y, int iconId) {
        PlayMapMarker markerItem = new PlayMapMarker();
        PlayMapPoint point = new PlayMapPoint(x, y);
        markerItem.setMapPoint(point);
        markerItem.setCanShowCallout(false);
        markerItem.setAutoCalloutVisible(false);
        markerItem.setIcon(((BitmapDrawable) getResources().getDrawable(iconId, null)).getBitmap());
        me.pmvMapView.addMarkerItem(markerId, markerItem);
    }
} // end of class FragmentChargeStationMap
