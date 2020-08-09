//package com.genesis.apps.ui.view;
//
//import android.content.Context;
//import android.view.MotionEvent;
//import android.widget.Toast;
//
//import com.genesis.apps.comm.model.map.MapViewModel;
//import com.genesis.apps.comm.model.map.ReverseGeocodingReqVO;
//import com.genesis.apps.comm.net.NetUIResponse;
//import com.hmns.playmap.PlayMapPoint;
//import com.hmns.playmap.PlayMapView;
//import com.hmns.playmap.extension.PlayMapGeoItem;
//
//import androidx.lifecycle.LifecycleOwner;
//import androidx.lifecycle.Observer;
//
//public class ViewPlayMap {
//
//    private MapViewModel mapViewModel;
//    private Context context;
//
//    public ViewPlayMap(MapViewModel mapViewModel, Context context){
//        this.mapViewModel = mapViewModel;
//        this.context = context;
//    }
//
//    public void setObserver(LifecycleOwner owner){
//        //TODO OBSERVE는 어디에둘지.. 위 뷰 모델은 저렇게 ?
//        this.mapViewModel.getPlayMapGeoItem().observe(owner, new Observer<NetUIResponse<PlayMapGeoItem>>() {
//            @Override
//            public void onChanged(NetUIResponse<PlayMapGeoItem> playMapGeoItemNetUIResponse) {
//                Toast.makeText(context, playMapGeoItemNetUIResponse.data.addrRoad, Toast.LENGTH_LONG).show();
//            }
//        });
//    }
//
//
//    private void init(double lat, double lon, int zoom){
//        initMap(lat, lon, zoom);
//
//        onMapInitListener(new PlayMapView.OnMapInitCallback() {
//            @Override
//            public void onMapInitEvent(int zoom, PlayMapPoint playMapPoint) {
//                Toast.makeText(activity, "Map Loading Success !!!", Toast.LENGTH_LONG).show();
//            }
//        });
//
//        onMapLongPressListener(new PlayMapView.OnMapLongPressCallback() {
//            @Override
//            public void onMapLongPressEvent(PlayMapPoint playMapPoint) {
////                eventTextView.setText("지도 롱탭 : " + String.format("%.5f", playMapPoint.getLongitude()) + ", " + String.format("%.5f", playMapPoint.getLatitude()));
//            }
//        });
//        onMapSingleTapUpListener(new PlayMapView.OnMapSingleTapUpCallback() {
//            @Override
//            public void onMapSingleTapUpEvent(MotionEvent event, PlayMapPoint playMapPoint) {
////                eventTextView.setText("지도 탭 : " + String.format("%.5f", playMapPoint.getLongitude()) + ", " + String.format("%.5f", playMapPoint.getLatitude()));
//            }
//        });
//        onMapDoubleTapUpListener(new PlayMapView.OnMapDoubleTapUpCallback() {
//            @Override
//            public void onMapDoubleTapUpEvent(MotionEvent event, PlayMapPoint playMapPoint) {
////                eventTextView.setText("지도 더블탭 : " + String.format("%.5f", playMapPoint.getLongitude()) + ", " + String.format("%.5f", playMapPoint.getLatitude()));
//            }
//            onZoomLevelChangeListener(new PlayMapView.OnZoomLevelChangeCallback() {
//                @Override
//                public void onZoomLevelChangeEvent(int zoom) {
//
//                }
//
//            });
//            onMapCenterChangeListener(new PlayMapView.OnMapCenterChangeCallback() {
//                @Override
//                public void onMapCenterChangeEvent(PlayMapPoint playMapPoint) {
//
//                    mapViewModel.reqPlayMapGeoItem(new ReverseGeocodingReqVO(playMapPoint.getLatitude(),playMapPoint.getLongitude(), 1));
//
//
////                    mapRestApi.reverseGeocoding(playMapPoint.getLatitude(), playMapPoint.getLongitude(), 1, new PlayMapRestApi.reverseGeocodingListenerCallback() {
////                        @Override
////                        public void onReverseGeocoding(final PlayMapGeoItem geoItem) {
////                            MainActivity.this.runOnUiThread(new Runnable() {
////                                @Override
////                                public void run() {
////                                    if (geoItem == null) {
////
////                                    } else {
////                                        Toast.makeText(MainActivity.this, geoItem.addrRoad, Toast.LENGTH_LONG).show();
////                                    }
////                                }
////                            });
////                        }
////                    });
//        });
////
////                if (m_bCenterPointMode) {
////                    //centerLongitude.setText(Double.toString(playMapPoint.getLongitude()));
////                    //centerLatitude.setText(Double.toString(playMapPoint.getLatitude()));
////                }
//
//                //eventTextView.setText("지도이동됨 : " + String.format("%.5f", playMapPoint.getLongitude()) + ", " + String.format("%.5f", playMapPoint.getLatitude()) + "Zoom(" + mapView.getZoomLevel() + ")");
//            }
//        });
////        mapView.onMapTouchUpListener(new PlayMapView.OnMapTouchUpCallback() {
////            @Override
////            public void onMapTouchUpEvent(MotionEvent event, ArrayList<PlayMapMarker> markerList) {
////                if (markerList.size() > 0) {
////                    eventTextView.setText("마커 탭 :" + String.format("%.5f", markerList.get(0).longitude) + ", " + String.format("%.5f", markerList.get(0).latitude));
////                    //추가 사항
////                    if (poiItemMap != null) {
////                        if (poiItemMap.size() > 0) {
////                            PlayMapPoiItem poiItem = poiItemMap.get(markerList.get(0).getId());
////                            String msg = "";
////                            msg += "\nPOI ID : " + Integer.toString(poiItem.poiId);
////                            if (poiItem.pid != null) {
////                                msg += "\npid : " + poiItem.pid;
////                            }
////                            if (poiItem.title != null) {
////                                msg += "\ntitle : " + poiItem.title;
////                            }
////                            if (poiItem.cname != null) {
////                                msg += "\ncname : " + poiItem.cname;
////                            }
////                            if (poiItem.addr != null) {
////                                msg += "\naddr : " + poiItem.addr;
////                            }
////                            if (poiItem.addrRoad != null) {
////                                msg += "\naddrRoad : " + poiItem.addrRoad;
////                            }
////                            if (poiItem.tele != null) {
////                                msg += "\ntele : " + poiItem.tele;
////                            }
////                            if (poiItem.addrRoad != null) {
////                                msg += "\naddrRoad : " + poiItem.addrRoad;
////                            }
////                            msg += "\ncenter : " + poiItem.centerLat + " , " + poiItem.centerLon;
////                            if (poiItem.guideLat > 0 && poiItem.guideLon > 0) {
////                                msg += "\nguide : " + poiItem.guideLat + " , " + poiItem.guideLon;
////                            }
////                            if (poiItem.openTime1 != null) {
////                                msg += "\nopenTime1 : " + poiItem.openTime1;
////                            }
////                            if (poiItem.closeTime1 != null) {
////                                msg += "\ncloseTime1 : " + poiItem.closeTime1;
////                            }
////                            if (poiItem.openTime2 != null) {
////                                msg += "\nopenTime2 : " + poiItem.openTime2;
////                            }
////                            if (poiItem.closeTime2 != null) {
////                                msg += "\ncloseTime2 : " + poiItem.closeTime2;
////                            }
////                            if (poiItem.holiday != null) {
////                                msg += "\nholiday : " + poiItem.holiday;
////                            }
////                            msg += "\ndetailCode : " + Integer.toString(poiItem.detailCode);
////
////                            if (poiItem.brandType != null) {
////                                msg += "\nbrandType : " + poiItem.brandType;
////                            }
////                            if (poiItem.chargerCnt > 0) {
////                                msg += "\nchargerCnt : " + Integer.toString(poiItem.chargerCnt);
////                            }
////                            if (poiItem.seq > -1) {
////                                msg += "\nseq : " + Integer.toString(poiItem.seq);
////                            }
////                            if (poiItem.id != null) {
////                                msg += "\nid : " + poiItem.id;
////                            }
////                            if (poiItem.name != null) {
////                                msg += "\nname : " + poiItem.name;
////                            }
////                            if (poiItem.type > -1) {
////                                msg += "\ntype : " + Integer.toString(poiItem.type);
////                            }
////                            if (poiItem.speed > -1) {
////                                msg += "\nspeed : " + Integer.toString(poiItem.speed);
////                            }
////                            if (poiItem.speed > -1) {
////                                msg += "\nprice : " + Integer.toString(poiItem.price);
////                            }
////                            if (poiItem.status > -1) {
////                                msg += "\nstatus : " + Integer.toString(poiItem.status);
////                            }
////
////                            if (poiItem.gasoline > -1) {
////                                msg += "\ngasoline : " + Integer.toString(poiItem.gasoline);
////                            }
////                            if (poiItem.diesel > -1) {
////                                msg += "\ndiesel : " + Integer.toString(poiItem.diesel);
////                            }
////                            if (poiItem.lpg > -1) {
////                                msg += "\nlpg : " + Integer.toString(poiItem.lpg);
////                            }
////                            if (poiItem.premiumGasoline > -1) {
////                                msg += "\npremiumGasoline : " + Integer.toString(poiItem.premiumGasoline);
////                            }
////                            if (poiItem.evCharge > -1) {
////                                msg += "\ngasoline : " + Integer.toString(poiItem.evCharge);
////                            }
////                            if (poiItem.update != null) {
////                                msg += "\nupdate : " + poiItem.update;
////                            }
////                            if (poiItem.parkTotalNum > -1) {
////                                msg += "\nparkTotalNum : " + Integer.toString(poiItem.parkTotalNum);
////                            }
////                            if (poiItem.availableCount > -1) {
////                                msg += "\navailableCount : " + poiItem.availableCount;
////                            }
////                            if (poiItem.areaName != null) {
////                                msg += "\nareaName : " + poiItem.areaName;
////                            }
////                            if (poiItem.areaTotalNum > -1) {
////                                msg += "\nareaTotalNum : " + poiItem.areaTotalNum;
////                            }
////                            if (poiItem.AreaAvailableCount > -1) {
////                                msg += "\nAreaAvailableCount : " + poiItem.AreaAvailableCount;
////                            }
////                            if (poiItem.bizHour > -1) {
////                                msg += "\nbizHour : " + poiItem.bizHour;
////                            }
////                            if (poiItem.bizTime != null) {
////                                msg += "\nbizTime : " + poiItem.bizTime;
////                            }
////                            if (poiItem.bizHourSat > -1) {
////                                msg += "\nbizHourSat : " + poiItem.bizHourSat;
////                            }
////                            if (poiItem.bizTimeSat != null) {
////                                msg += "\nbizTimeSat : " + poiItem.bizTimeSat;
////                            }
////                            if (poiItem.bizHourSun > -1) {
////                                msg += "\nbizHourSun : " + poiItem.bizHourSun;
////                            }
////                            if (poiItem.bizTimeSun != null) {
////                                msg += "\nbizTimeSun : " + poiItem.bizTimeSun;
////                            }
////                            if (poiItem.free > -1) {
////                                msg += "\nfree : " + poiItem.free;
////                            }
////                            if (poiItem.availabeCar > -1) {
////                                msg += "\navailabeCar : " + poiItem.availabeCar;
////                            }
////                            if (poiItem.basicMinute > -1) {
////                                msg += "\nbasicMinute : " + poiItem.basicMinute;
////                            }
////                            if (poiItem.basicFee > -1) {
////                                msg += "\nbasicFee : " + poiItem.basicFee;
////                            }
////                            if (poiItem.addMinute > -1) {
////                                msg += "\naddMinute : " + poiItem.addMinute;
////                            }
////                            if (poiItem.addFee > -1) {
////                                msg += "\naddFee : " + poiItem.addFee;
////                            }
////                            if (poiItem.oneDayFee > -1) {
////                                msg += "\noneDayFee : " + poiItem.oneDayFee;
////                            }
////                            if (poiItem.monthlyFee > -1) {
////                                msg += "\nmonthlyFee : " + poiItem.monthlyFee;
////                            }
////                            if (poiItem.echarge > -1) {
////                                msg += "\necharge : " + poiItem.echarge;
////                            }
////                            if (poiItem.echargeCd > -1) {
////                                msg += "\nechargeCd : " + poiItem.echargeCd;
////                            }
////                            if (poiItem.hPay != null) {
////                                msg += "\nhPay : " + poiItem.hPay;
////                            }
////                            if (poiItem.smartCode != null) {
////                                msg += "\nsmartCode : " + poiItem.smartCode;
////                            }
////                            if (poiItem.starPoint > -1) {
////                                msg += "\nstarPoint : " + poiItem.starPoint;
////                            }
////                            if (poiItem.tastyPlaceInfo_day != null) {
////                                msg += "\ntastyPlaceInfo_day : " + poiItem.tastyPlaceInfo_day;
////                            }
////                            if (poiItem.tastyPlaceInfo_opening != null) {
////                                msg += "\ntastyPlaceInfo_opening : " + poiItem.tastyPlaceInfo_opening;
////                            }
////                            if (poiItem.tastyPlaceInfo_menuName != null) {
////                                msg += "\ntastyPlaceInfo_menuName : " + poiItem.tastyPlaceInfo_menuName;
////                            }
////                            if (poiItem.tastyPlaceInfo_menuPrice > -1) {
////                                msg += "\ntastyPlaceInfo_menuPrice : " + poiItem.tastyPlaceInfo_menuPrice;
////                            }
////                            AlertDialog alertDialog = new AlertDialog.Builder(mContext)
////                                    .setTitle("POI 상세 정보")
////                                    .setMessage(msg)
////                                    .setNeutralButton("확인", new DialogInterface.OnClickListener() {
////
////                                        public void onClick(DialogInterface dialog, int which) {
////                                            dialog.dismiss();
////                                        }
////
////                                    }).show();
////                        }
////                    }
////                }
////            }
////        });
////
////        mapView.onInfoWindowClickListner(new PlayMapView.OnInfoWindowClickCallback() {
////            @Override
////            public void onInfoWindowClickEvent(PlayMapMarker item) {
////                Toast.makeText(getBaseContext(), "Info Window Click \nfrom Marker Id : " + item.getId(), Toast.LENGTH_LONG).show();
////            }
////        });
//    }
//
//
//}
//
