package com.genesis.apps.comm.util;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import java.util.Iterator;
import java.util.List;

public class CommonUtil {
    private static final String TAG = CommonUtil.class.getSimpleName();
    /**
     * 2020-07-17 hj
     * Activity 구동 중 여부 판단
     * @param context
     * @param activityName 대상 activity
     * @return true:지정한 activity가 task의 top 혹은 base에 존재함
     *         false:지정한 activity가 task의 top 혹은 base에 존재하지 않음
     */
    public static boolean getServiceTaskName(Context context, String activityName) {

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        List<ActivityManager.RunningTaskInfo> info = am.getRunningTasks(30);
        Log.i(TAG, "myTaskName() = " + context.getPackageName());

        for (int i = 0; i < info.size(); i++) {
            Log.i(TAG, "[" + i + "] getServiceTaskName:" + info.get(i).topActivity.getPackageName() + " >> " + info.get(i).topActivity.getClassName());
        }

        for (Iterator iterator = info.iterator(); iterator.hasNext();) {
            ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) iterator.next();
            Log.i(TAG, "getServiceTaskName().topActivity= "+runningTaskInfo.topActivity.getClassName());
            Log.i(TAG, "getServiceTaskName().baseActivity= "+runningTaskInfo.baseActivity.getClassName());
            Log.i(TAG, "getServiceTaskName().numRunning= "+runningTaskInfo.numRunning);

            if (runningTaskInfo.topActivity.getClassName().equals(activityName)) {
                Log.i(TAG, "getServiceTaskName() = true");
                return true;
            }
            if (runningTaskInfo.baseActivity.getClassName().equals(activityName)) {
                Log.i(TAG, "getServiceTaskName() = true");
                return true;
            }
        }
        return false;
    }
}
