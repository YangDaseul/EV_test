package com.genesis.apps.comm.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.text.TextUtils;
import android.util.Log;

import java.util.List;

public class PackageUtil {
    private static final String LOG_TAG = "PackageUtil";

    /**
     * app 버전 코드 조회
     *
     * @param context
     * @param packageName
     * @return 설치되지 않은 경우 -1
     */
    public static int getApplicationVersionCode(Context context,
                                                String packageName) {
        int versionCode = -1;

        if (context == null) {
            throw new RuntimeException("context is null!");
        }

        if (TextUtils.isEmpty(packageName)) {
            throw new RuntimeException("packageName is null!");
        }

        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(
                    packageName, PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                versionCode = pi.versionCode;
            } else {
                versionCode = -1;
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.d(LOG_TAG, "can not found packageName[" + packageName + "]");
            versionCode = -1;
        }

        return versionCode;
    }

    /**
     * app 버전 이름을 조회
     *
     * @param context
     * @param packageName
     * @return 설치되지 않은 경우 null
     */
    public static String getApplicationVersionName(Context context,
                                                   String packageName) {
        if (context == null) {
            throw new RuntimeException("context is null!");
        }

        if (TextUtils.isEmpty(packageName)) {
            throw new RuntimeException("packageName is null!");
        }

        String versionName = null;
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(
                    packageName, PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                versionName = pi.versionName;
            } else {
                versionName = null;
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.d(LOG_TAG, "can not found packageName[" + packageName + "]");
            versionName = null;
        }

        return versionName;
    }

    /**
     * 전달 받은 action 값을 처리할 수 있는 app이 존재하는지 검사.
     *
     * @param context
     * @return 처리 가능 true
     */
    public static boolean isIntentAvailable(Context context, Intent intent) {
        final PackageManager packageManager = context.getPackageManager();
//		final Intent intent = new Intent(action);
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    public static boolean checkPermission(Context context, String permission) {

        PackageManager pm = context.getPackageManager();
        int hasPerm = pm.checkPermission(permission, context.getPackageName());
        return hasPerm == PackageManager.PERMISSION_GRANTED;
    }
}
