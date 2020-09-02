package com.genesis.apps.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.RequestCodes;
import com.genesis.apps.comm.model.vo.DeviceDTO;
import com.genesis.apps.comm.util.PackageUtil;
import com.genesis.apps.databinding.ActivityIntroBinding;
import com.genesis.apps.room.DatabaseHolder;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class IntroActivity extends SubActivity<ActivityIntroBinding> {

    @Inject
    public DeviceDTO deviceDTO;
    @Inject
    public DatabaseHolder databaseHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
//        setForegroundService();
        init();
    }

//    private void setForegroundService() {
//        Intent intent = new Intent(this, ScreenRecorderService.class);
//        if (Build.VERSION.SDK_INT >= 26) {
//            startForegroundService(intent);
//        }
//        else {
//            startService(intent);
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RequestCodes.REQ_CODE_PERMISSIONS.getCode()) {
            if (resultCode == RESULT_OK) {
                init();
            } else {
                finish();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void init(){
        if(isPermissions()){
            deviceDTO.initData();
//            String test = "{\n" +
//                    "  \"rsltCd\": \"0000\",\n" +
//                    "  \"rsltMsg\": \"성공\",\n" +
//                    "  \"pushIdChgYn\": \"0000\",\n" +
//                    "  \"carGbCd\": \"0000\",\n" +
//                    "  \"custMgmtNo\": \"0000\",\n" +
//                    "  \"custNm\": \"0000\",\n" +
//                    "  \"celphNo\": \"0000\",\n" +
//                    "  \"vin\": \"JK1234~\",\n" +
//                    "  \"vrn\": \"0000\",\n" +
//                    "  \"carMdelNm\": \"G70\",\n" +
//                    "  \"carCdNm\": \"2WD 엘리트\",\n" +
//                    "  \"exteriaColr\": \"블레이징 레드\",\n" +
//                    "  \"interiaColor\": \"브라운 투톤\",\n" +
//                    "  \"contractNo\": \"CJK1234~\"\n" +
//                    "}";
//
//            UserVO user = new Gson().fromJson(test, UserVO.class);
//
//            databaseHolder.getDatabase().userDao().insert(user);
//            UserVO user2 = databaseHolder.getDatabase().userDao().select();
//
//
//
//
//
//            String test2 = "{\n" +
//                    "  \"rsltCd\": \"0000\",\n" +
//                    "  \"rsltMsg\": \"성공\",\n" +
//                    "  \"pushIdChgYn\": \"0000\",\n" +
//                    "  \"carGbCd\": \"park\",\n" +
//                    "  \"custMgmtNo\": \"park\",\n" +
//                    "  \"custNm\": \"park\",\n" +
//                    "  \"celphNo\": \"0000\",\n" +
//                    "  \"vin\": \"JK1234~\",\n" +
//                    "  \"vrn\": \"0000\",\n" +
//                    "  \"carMdelNm\": \"G70\",\n" +
//                    "  \"carCdNm\": \"2WD 엘리트\",\n" +
//                    "  \"exteriaColr\": \"블레이징 레드\",\n" +
//                    "  \"interiaColor\": \"브라운 투톤\",\n" +
//                    "  \"contractNo\": \"CJK1234~\"\n" +
//                    "}";
//
//            UserVO user3 = new Gson().fromJson(test2, UserVO.class);
//            databaseHolder.getDatabase().userDao().insert(user3);
//            UserVO user4 = databaseHolder.getDatabase().userDao().select();

            new Handler().postDelayed(() -> {
//                startActivitySingleTop(new Intent(IntroActivity.this, GAWebActivity.class).putExtra("url",GAWebActivity.URL_BTO_MAIN), 0);

                if(isPushData()){
                    startActivity(moveToPush(MainActivity.class));
                }else{
                    startActivitySingleTop(new Intent(IntroActivity.this, MainActivity.class),0);
                }
                finish();
            }, 2000);
        }
    }


    private boolean isPermissions() {
        // 최초에 실행해서 권한 팝업으로 이동
        // 한번 권한 팝업을 받는다.
        // 필수 권한이 없는 경우 다시 권한 동의 팝업으로 이동한다.
        for (String p : PermissionsActivity.requiredPermissions) {
            boolean check = PackageUtil.checkPermission(this, p);
            for (String permission : PermissionsActivity.permissions) {
                if (permission.equals(p)) {
                    check = true;
                }
            }
            if (!check) {
                // 권한동의로 이동
                startActivitySingleTop(new Intent(this, PermissionsActivity.class), RequestCodes.REQ_CODE_PERMISSIONS.getCode());
                return false;
            }
        }

        return true;
    }


}
