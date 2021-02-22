package com.genesis.apps.ui.common.activity;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.ViewStub;

import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;

import com.genesis.apps.comm.util.GpsUtils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public abstract class GpsBaseActivity<T extends ViewDataBinding> extends SubActivity<T> {
    private final static String TAG = GpsBaseActivity.class.getSimpleName();
    public final int DEFAULT_ZOOM = 12;
    private GpsUtils gpsUtils;
    public enum GpsRetType {
        GPS_RETURN_HIGH, // searchTime 까지 기다렸다가 정확도가 가장 높은 1회만 보냄 ex) 경로찾기
        GPS_RETURN_FIRST //처음 확인된 GPS 정보 전달
    }

    public interface OnGpsMyLocGetListener {
        void onGpsMyLocGet(Location location);
    }

    private Location mLocation = null;
    private Timer mGpsTimer;
    private CountDownTimer countDownTimer;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest locationRequest;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gpsUtils = new GpsUtils(this);

        locationRequest = new LocationRequest()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(500)//1초에 한번씩 업데이트 수신받음
                .setFastestInterval(500);//0.5초에 한번씩 업데이트 요청

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @SuppressLint("MissingPermission")
    public void findMyLocation(OnGpsMyLocGetListener locListener, long searchTime, GpsRetType retType, boolean dontShowGpsPopup) {
        stopGpsSearch();
        mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);

        switch (retType){
            case GPS_RETURN_HIGH://searchTime 동안 정확도가 가장 우수한 위치정보를 리스너에 전달
                mGpsTimer = new Timer();
                mGpsTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
//                        Log.d(TAG, "TIMER HIGH lat : " + (mLocation!=null ? mLocation.getLatitude() : "0" ) + ",  lng : " + (mLocation!=null ? mLocation.getLongitude() : "0" ) + " accurate:" + (mLocation!=null ? mLocation.getAccuracy() : "0" ));
                        locListener.onGpsMyLocGet(mLocation);
                        stopGpsSearch();
                    }
                }, searchTime);
                break;
            case GPS_RETURN_FIRST:
            default:
                //searchTime 내에 100ms 간격으로 확인해서 위치정보가 있을 경우 리스너에 바로 전달 및 타이머 종료
                countDownTimer = new CountDownTimer(searchTime, 100) {
                    @Override
                    public void onTick(long l) {
//                        Log.d(TAG, "TIMER COUNT DONW ON TICK:"+l +  "   lat:" + (mLocation!=null ? mLocation.getLatitude() : "0" ) + ",  lng : " + (mLocation!=null ? mLocation.getLongitude() : "0" ) + " accurate:" + (mLocation!=null ? mLocation.getAccuracy() : "0" ));
                        if(mLocation!=null) {
//                            Log.d(TAG, "TIMER COUNT DONW ON TICK finish1:"+l +  "   lat:" + (mLocation!=null ? mLocation.getLatitude() : "0" ) + ",  lng : " + (mLocation!=null ? mLocation.getLongitude() : "0" ) + " accurate:" + (mLocation!=null ? mLocation.getAccuracy() : "0" ));
                            locListener.onGpsMyLocGet(mLocation);
                            stopGpsSearch();
                        }
                    }
                    @Override
                    public void onFinish() {
//                        Log.d(TAG, "TIMER COUNT DONW Finish lat:" + (mLocation!=null ? mLocation.getLatitude() : "0" ) + ",  lng : " + (mLocation!=null ? mLocation.getLongitude() : "0" ) + " accurate:" + (mLocation!=null ? mLocation.getAccuracy() : "0" ));
                        locListener.onGpsMyLocGet(null);
                        stopGpsSearch();
                    }
                };
                countDownTimer.start();
                break;

        }

    }

    public void stopGpsSearch() {
        if(mFusedLocationClient != null)
            mFusedLocationClient.removeLocationUpdates(locationCallback);

        if (mGpsTimer != null) {
            mGpsTimer.cancel();
            mGpsTimer = null;
        }

        if(countDownTimer!=null){
            countDownTimer.cancel();
            countDownTimer = null;
        }

        mLocation = null;
    }


    public boolean isGpsEnable(){
        return gpsUtils.isGpsEnabled();
    }

    public void turnGPSOn(GpsUtils.onGpsListener onGpsListener){
        gpsUtils.turnGPSOn(onGpsListener);
    }

    public void setViewStub(int viewStub, int addLayout, ViewStub.OnInflateListener listener){
        ViewStub stub = findViewById(viewStub);
        stub.setLayoutResource(addLayout);
        stub.setOnInflateListener(listener);
        stub.inflate();
    }

    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);

            List<Location> locationList = locationResult.getLocations();

            for(Location location : locationList){
                Log.d(TAG, "lat : " + location.getLatitude() + ", lng : " + location.getLongitude() + " size:" + locationList.size() + " accurate:" + location.getAccuracy());
                if(mLocation == null || mLocation.getAccuracy() > location.getAccuracy()){
                    mLocation = location;
                }
            }
        }
    };

}
