package com.genesis.apps.ui.common.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewStub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;

import com.genesis.apps.R;
import com.genesis.apps.comm.util.GpsUtils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.Timer;
import java.util.TimerTask;

public abstract class GpsBaseActivity<T extends ViewDataBinding> extends SubActivity<T> {
    private final static String TAG = GpsBaseActivity.class.getSimpleName();
    private GpsUtils gpsUtils;
    public enum GpsRetType {
        GPS_RETURN_ONCE,        // searchTime 까지 기다렸다가 정확도가 가장 높은 1회만 보냄 ex) 경로찾기
        GPS_RETURN_TWICE,       // 최초 1회 먼저 보내고, searchTime 동안 찾고 최적값 보냄 ex) 내 위치 찾기
        GPS_RETURN_EVERYTIME    // 최초부터 searchTime 까지 검색하는데 정확도가 높은 값이 검색될 때마다 보냄
    }

    public interface OnGpsMyLocGetListener {
        void onGpsMyLocGet(Location location);
    }

    public interface OnGpsMyLocAniListener {
        void onGpsMyLocAniGet(String lat, String lon, boolean isFirstFrame, boolean isLastFrame);
    }

    private LocationManager mLocationManager;
    private Location mLocation = null;
    private Location mFirstLocation = null;
    private float mMinAccuracy = 9999;
    private Timer mGpsTimer;

    private OnGpsMyLocGetListener mMyLocGetListener;
    private OnGpsMyLocAniListener mMyLocAniListener;
    private long mSearchTime;
    private GpsRetType mLocRetType;

    private int mAniCount = 8;
    private double[] mAniLatutude = new double[mAniCount];
    private double[] mAniLongitude = new double[mAniCount];
    private int mAniIndex = 0;
    private Timer mLocationAniTimer = null;

    private static String prevLon = null;
    private static String prevLat = null;

    private static boolean isDrawAnimation = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gpsUtils = new GpsUtils(this);
        mLocationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();

        setPrevLat(null);
        setPrevLon(null);
    }

    public void findMyLocation(OnGpsMyLocGetListener locListener) {
        findMyLocation(locListener, null, GpsRetType.GPS_RETURN_TWICE, 5000, false);
    }

    public void findMyLocation(OnGpsMyLocGetListener locListener, boolean dontShowGpsPopup) {
        findMyLocation(locListener, null, GpsRetType.GPS_RETURN_TWICE, 5000, dontShowGpsPopup);
    }

    public void findMyLocation(OnGpsMyLocGetListener locListener, long searchTime) {
        findMyLocation(locListener, null, GpsRetType.GPS_RETURN_ONCE, searchTime, false);
    }

    public void findMyLocation(OnGpsMyLocGetListener locListener, OnGpsMyLocAniListener AniListener) {
        findMyLocation(locListener, AniListener, GpsRetType.GPS_RETURN_TWICE, 5000, false);
    }

    public void findMyLocation(OnGpsMyLocGetListener locListener, OnGpsMyLocAniListener AniListener, long searchTime) {
        findMyLocation(locListener, AniListener, GpsRetType.GPS_RETURN_TWICE, searchTime, false);
    }

    @SuppressLint("MissingPermission")
    public void findMyLocation(OnGpsMyLocGetListener locListener, OnGpsMyLocAniListener AniListener, GpsRetType retType, long searchTime, boolean dontShowGpsPopup) {
        if (mLocationManager != null) {
            mLocationManager.removeUpdates(mLocationListener);
        }

        mMinAccuracy = 9999;

        mMyLocGetListener = locListener;
        mMyLocAniListener = AniListener;
        mSearchTime = searchTime;
        mLocRetType = retType;

        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,100, 1, mLocationListener);
        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,100, 1, mLocationListener);

        FusedLocationProviderClient locationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationProviderClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location == null) {
                            Log.e(TAG, "locationProviderClient location is null");
                            return;
                        }

                        mMinAccuracy = location.getAccuracy();
                        mFirstLocation = location;
                        mLocation = location;

                        Log.d(TAG, "get Location Accuracy:" + mMinAccuracy + ", lat:" + mLocation.getLatitude() + ", lon:" + mLocation.getLongitude());
                        if (mLocRetType != GpsRetType.GPS_RETURN_ONCE) {
                            mMyLocGetListener.onGpsMyLocGet(mLocation);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                    }
                });

        if (mGpsTimer != null) {
            mGpsTimer.cancel();
            mGpsTimer = null;
        }

        if (mLocationAniTimer != null) {
            mLocationAniTimer.cancel();
            mLocationAniTimer = null;
        }

        mGpsTimer = new Timer();
        mGpsTimer.schedule(new CustomTimer(), mSearchTime);
    }

    public void stopGpsSearch() {
        if (mLocationManager != null) {
            mLocationManager.removeUpdates(mLocationListener);
        }

        if (mGpsTimer != null) {
            mGpsTimer.cancel();
            mGpsTimer = null;
        }

        if (mLocationAniTimer != null) {
            mLocationAniTimer.cancel();
            mLocationAniTimer = null;
        }

        mMinAccuracy = 9999;
        setIsDrawAnimation(false);
    }

    private final LocationListener mLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            if (location == null) {
                Log.e(TAG, "onLocationChanged() location is null");
                return;
            }

            if (mMinAccuracy > location.getAccuracy()) {
                mLocation = location;
                mMinAccuracy = location.getAccuracy();

                Log.d(TAG, "get location!! onLocationChanged Accuracy:" + mMinAccuracy + ", lat:" + mLocation.getLatitude() + ", lon:" + mLocation.getLongitude());
                if (mLocRetType == GpsRetType.GPS_RETURN_EVERYTIME) {
                    mMyLocGetListener.onGpsMyLocGet(mLocation);
                }
            }
        }

        public void onProviderDisabled(String provider) {}
        public void onProviderEnabled(String provider) {}
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    };

    private class CustomTimer extends TimerTask {
        @Override
        public void run() {
            if (mLocationManager != null) {
                mLocationManager.removeUpdates(mLocationListener);
            }

            if (mLocRetType == GpsRetType.GPS_RETURN_ONCE) {
                mMyLocGetListener.onGpsMyLocGet(mLocation);
            } else if (mLocRetType == GpsRetType.GPS_RETURN_TWICE) {
                if (mFirstLocation != null && mLocation != null) {
                    if (mFirstLocation.getAccuracy() > mLocation.getAccuracy()) {
                        Log.d(TAG, "CustomTimer() find more good LOC, send LOC");
                        mMyLocGetListener.onGpsMyLocGet(mLocation);
                    }
                }
            }

            if (mGpsTimer != null) {
                mGpsTimer.cancel();
                mGpsTimer = null;
            }
            mMinAccuracy = 9999;

            // 내 위치 찾기에서 searchTime 동안 찾기 이후, 최종으로 찾은 값이 처음 찾은 값과 차이가 클 경우,
            // 바로 위치를 변경하면 어색하다. 카카오맵이나 구글맵을 보니, 내위치가 애니메이션 식으로 사부작(?)
            // 이동하길래 어설프게 따라해 본다. 50ms 간격으로 8개의 중간좌표를 그린다.
            if (mLocRetType != GpsRetType.GPS_RETURN_TWICE) {
                return;
            }

            if (mMyLocAniListener == null) {
                return;
            }

            if (mLocation == null || mFirstLocation == null) {
                return;
            }

            if (mLocation.equals(mFirstLocation)) {
                return;
            }

            if (mFirstLocation.getAccuracy() <= mLocation.getAccuracy()) {
                mFirstLocation = null;
                return;
            }

            mAniLatutude[0] = mFirstLocation.getLatitude();
            mAniLongitude[0] = mFirstLocation.getLongitude();

            mAniLatutude[mAniCount - 1] = mLocation.getLatitude();
            mAniLongitude[mAniCount - 1] = mLocation.getLongitude();

            double diffLatitude = Math.abs(mFirstLocation.getLatitude() - mLocation.getLatitude()) / mAniCount;
            double diffLongitude = Math.abs(mFirstLocation.getLongitude() - mLocation.getLongitude()) / mAniCount;

            for (int i = 1; i < mAniCount - 1; i++) {
                if (mFirstLocation.getLatitude() > mLocation.getLatitude()) {
                    mAniLatutude[i] = mAniLatutude[i - 1] - diffLatitude;
                } else {
                    mAniLatutude[i] = mAniLatutude[i - 1] + diffLatitude;
                }

                if (mFirstLocation.getLongitude() > mLocation.getLongitude()) {
                    mAniLongitude[i] = mAniLongitude[i - 1] - diffLongitude;
                } else {
                    mAniLongitude[i] = mAniLongitude[i - 1] + diffLongitude;
                }
            }

            mAniIndex = 0;
            mLocationAniTimer = new Timer();
            mLocationAniTimer.schedule(new LocationAniTimer(), 0, 50);
        }
    }

    private class LocationAniTimer extends TimerTask {
        @Override
        public void run() {
//            boolean isLastIndex = false;
            if (mAniIndex >= mAniCount) {
                if (mLocationAniTimer != null) {
                    mLocationAniTimer.cancel();
                    mLocationAniTimer = null;
                }
                mAniIndex = 0;
                return;
            }

            if (mAniIndex == 0) {
                setIsDrawAnimation(true);
            } else if (mAniIndex >= (mAniCount - 1)) {
                setIsDrawAnimation(false);
            }

            mMyLocAniListener.onGpsMyLocAniGet(String.valueOf(mAniLatutude[mAniIndex]), String.valueOf(mAniLongitude[mAniIndex]), (mAniIndex == 0 ? true : false), (mAniIndex >= (mAniCount - 1) ? true : false));
            mAniIndex++;
        }
    }

    // 횬다이 Map 에서 넘어오는 좌표는 소수점 이하 6자리로 고정, GPS 나 Network 에서 얻어지는 좌표는 7자리임.
    // 두 좌표를 비교해 보면 소수점 7자리에서 반올림한 좌표로 동일한 좌표인데 다르다고 판단되는 경우가 많음.
    // 동일한 좌표에 대한 getrGeoCoding()/getBuildingName() 다중 호출을 막기 위해 좌표를 비교하는 함수.
    @SuppressLint("DefaultLocale")
    public boolean isSameLocation(String fromLat, String fromLon, String toLat, String toLon) {
        if (TextUtils.isEmpty(fromLat) || TextUtils.isEmpty(fromLon)) {
            return false;
        }

        if (TextUtils.isEmpty(toLat) || TextUtils.isEmpty(toLon)) {
            return false;
        }


        fromLat = String.format("%10.6f", Double.valueOf(fromLat));
        fromLon = String.format("%10.6f", Double.valueOf(fromLon));

        toLat = String.format("%10.6f", Double.valueOf(toLat));
        toLon = String.format("%10.6f", Double.valueOf(toLon));

        if (fromLat.equals(toLat) && fromLon.equals(toLon)) {
            return true;
        }
        return false;
    }


    public String getPrevLon() {
        return prevLon;
    }

    public void setPrevLon(String prevLon) {
        GpsBaseActivity.prevLon = prevLon;
    }

    public String getPrevLat() {
        return prevLat;
    }

    public void setPrevLat(String prevLat) {
        GpsBaseActivity.prevLat = prevLat;
    }

    public boolean isDrawAnimation() {
        return isDrawAnimation;
    }

    public void setIsDrawAnimation(boolean isDrawAnimation) {
        GpsBaseActivity.isDrawAnimation = isDrawAnimation;
    }

    public boolean isGpsEnable(){
        return gpsUtils.isGpsEnabled();
    }

    public void turnGPSOn(GpsUtils.onGpsListener onGpsListener){
        gpsUtils.turnGPSOn(onGpsListener);
    }

    public void setViewStub(int viewStub, int layout){
        ViewStub stub = findViewById(viewStub);
        stub.setLayoutResource(layout);
        stub.inflate();
    }


}
