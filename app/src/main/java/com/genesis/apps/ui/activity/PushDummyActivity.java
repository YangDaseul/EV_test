package com.genesis.apps.ui.activity;

import android.os.Bundle;

import com.genesis.apps.comm.util.CommonUtil;
import com.genesis.apps.fcm.FcmCallback;


public class PushDummyActivity extends BaseActivity implements FcmCallback {

    private final static String TAG = PushDummyActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isExcuteApp = CommonUtil.getServiceTaskName(PushDummyActivity.this, MainActivity.class.getName());
        if (!isExcuteApp&&isPushData()) {
            startActivity(moveToPush(IntroActivity.class));
        }else {
            checkPushCode();
        }
        finish();
    }


    @Override
    public void callbackCarWash() {
//        String carWashLink = this.getIntent().getStringExtra("carWashLink");
//        Log.d(TAG, "onCreate() carWashLink: " + carWashLink);
//        if (!TextUtils.isEmpty(carWashLink)) {
//            this.getIntent().getExtras().clear();
//
//            if (isExcuteApp) intent = new Intent(this, CarWashOrderListActivity.class);
//
//            intent.putExtra("carWashLink", carWashLink);
//        } else {
//            intent = null;
//        }
    }

    @Override
    public void callbackGPS() {
//        pushInfo = (PushGpsInfo) getIntent().getSerializableExtra("push_gps_info");
//        vehicleInfo = DatabaseHolder.getInstance().getDatabase().vehicleInfoDao().getVehicleInfo(pushInfo != null && !TextUtils.isEmpty(pushInfo.getVin()) ? pushInfo.getVin() : "");
//        if (pushInfo != null) {
//            //2020-04-28 vin에 해당하는 차량이 없을 경우 / 최승일B
//            //앱이 구동중일 때는 해당 페이지로 이동시키지 않도록 처리
//            //앱이 구동중이 아닐때는 메인화면까지는 이동시켜야함
//            if (isExcuteApp) {//앱이 구동중 일때
//                if(vehicleInfo!=null&&!TextUtils.isEmpty(vehicleInfo.getUid())) { //vin에 해당하는 차량이 있는 경우
//                    if (pushInfo.getType().equals(PushGpsInfo.PUSH_GPS_TYPE.GPS)) //수정버튼 클릭 시
//                        intent = new Intent(this, GpsActivity.class);
//                    else
//                        intent = new Intent(this, GpsRegistActivity.class);//등록버튼 클릭 시
//                }else{
//                    intent = null; //vin에 해당하는 차량이 없는 경우
//                    return;
//                }
//            }
//
//            intent.putExtra("push_gps_info", pushInfo);
//            intent.putExtra("vehicleUid", vehicleInfo != null ? vehicleInfo.getUid() : null);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        } else {
//            intent = null;
//        }
    }

    @Override
    public void callbackInsuranceExpire() {
//        startActivityForResultAddFlag(new Intent(this, ActivityInsuranceJoinGuideList.class), REQUEST_CODE_REFRESH_SHARELIST);
//        finish();
    }

    @Override
    public void callbackETC() {
//        if (isExcuteApp) intent = new Intent(this, PushHistoryActivity.class);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
