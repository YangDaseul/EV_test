package com.genesis.apps.ui.intro;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.databinding.ActivityPermissionsBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PermissionsActivity extends SubActivity<ActivityPermissionsBinding> {

//    @Inject
//    public TwoButtonDialog twoButtonDialog;

    public static final List<String> requiredPermissions = new ArrayList<>();

    static {
        requiredPermissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        requiredPermissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        requiredPermissions.add(Manifest.permission.READ_PHONE_STATE);
        requiredPermissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        requiredPermissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
//        requiredPermissions.add(Manifest.permission.CAMERA);
        requiredPermissions.add(Manifest.permission.RECORD_AUDIO);
//        requiredPermissions.add(Manifest.permission.READ_CONTACTS);

        if (Build.VERSION_CODES.O <= Build.VERSION.SDK_INT) {
            requiredPermissions.add(Manifest.permission.READ_PHONE_NUMBERS);
        }
        if (Build.VERSION_CODES.P <= Build.VERSION.SDK_INT) {
            requiredPermissions.add(Manifest.permission.FOREGROUND_SERVICE);
        }

    }

    // 선택적 권한의 퍼미션 배열
    public static String[] permissions = { /* Manifest.permission.READ_CONTACTS */ };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions);
        ui.setActivity(this);
    }

    @Override
    public void onClickCommon(View v) {
        switch (v.getId()){
            case R.id.btn_check:
                checkPermissions();
                break;
        }
    }

    @Override
    public void setViewModel() {
    }

    @Override
    public void setObserver() {

    }

    @Override
    public void getDataFromIntent() {

    }

    private void checkPermissions() {
        /*
        Dexter 라이브러리 내 DexterInstance 클래스가 싱글톤 클래스이다.
        권한 확인 팝업이 보여지던 중 홈버튼을 누르고 최근 앱 사용 내역을 삭제하는 경우 프로세스가 종료되면서 DexterInstance도 초기화 되어야 한다.
        디지털 키 앱은 포그라운드서비스로 사용 내역을 삭제해도 프로세스가 종료되지 않으므로 DexterInstance가 초기화 되지 않는다.

        앱을 다시 실행해서 권한 팝업을 보여주고자 할 때 DexterInstance가 초기화 되지 않고 이전의 데이터를 유지하고 있어서 권한 팝업이 뜨지 않는다 (추정)
        아래와 같이 private 으로 선언된 instance 필드를 null로 초기화 하는 방법으로 해결하였다.

        만약 proguard와 같이 필드의 이름이 변경되는 경우 똔느 라이브러리 업데이트로 이름이 변경되는 경우 등에 오류가 발생할 가능성이 존재한다.
         */
        try {
            Class cls = Class.forName("com.karumi.dexter.Dexter");
            Field field = cls.getDeclaredField("instance");
            field.setAccessible(true);
            Object o = field.get(null);
            if (o != null) {
                Dexter dexter = (Dexter) Dexter.withContext(this);
                field.set(dexter, null);

            }
            field.setAccessible(false);
        } catch (Throwable ignored) {
        }

        Dexter.withContext(this)
                .withPermissions(requiredPermissions.toArray(new String[]{}))
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            // 모든 권한에 동의할 필요가 없을 수 있음.
                            setResult(RESULT_OK);
                            finish();
                        } else {
                            List<PermissionDeniedResponse> l = report.getDeniedPermissionResponses();
                            for (PermissionDeniedResponse p : new ArrayList<>(l)) {
                                Log.d("TEST", "denied permission : " + p.getPermissionName());
                                for (String permission : permissions) {
                                    if (permission.equals(p.getPermissionName())) {
                                        l.remove(p);
                                        break;
                                    }
                                }
                            }
                            if (l.size() > 0) {

                                MiddleDialog.dialogPermission(PermissionsActivity.this, () -> {
                                    Context context = PermissionsActivity.this;
                                    Intent myAppSettings = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                            Uri.parse("package:" + context.getPackageName()));
                                    myAppSettings.addCategory(Intent.CATEGORY_DEFAULT);
                                    myAppSettings.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(myAppSettings);
                                    setResult(RESULT_FIRST_USER);
                                    finish();
                                }, () -> {
                                    setResult(RESULT_FIRST_USER);
                                    finish();
                                });
                            } else {
                                setResult(RESULT_OK);
                                finish();
                            }
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .check();
    }
}
