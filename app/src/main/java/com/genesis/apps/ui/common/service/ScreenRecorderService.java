package com.genesis.apps.ui.common.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.genesis.apps.R;
import com.genesis.apps.comm.util.ScreenCaptureUtil;
import com.genesis.apps.ui.common.activity.PushDummyActivity;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

import static com.genesis.apps.comm.model.KeyNames.IS_FOREGROUND_SERVICE;

@AndroidEntryPoint
public class ScreenRecorderService extends Service {
    private static final String TAG = ScreenRecorderService.class.getSimpleName();
    private static final int NOTIFICATION = R.string.app_name;


    private static final String BASE = "com.genesis.apps.ui.common.service.ScreenRecorderService.";
    public static final String ACTION_START = BASE + "ACTION_START";
    public static final String ACTION_STOP = BASE + "ACTION_STOP";
    public static final String ACTION_PAUSE = BASE + "ACTION_PAUSE";
    public static final String ACTION_RESUME = BASE + "ACTION_RESUME";
    public static final String ACTION_QUERY_STATUS = BASE + "ACTION_QUERY_STATUS";
    public static final String ACTION_QUERY_STATUS_RESULT = BASE + "ACTION_QUERY_STATUS_RESULT";
    public static final String EXTRA_RESULT_CODE = BASE + "EXTRA_RESULT_CODE";
    public static final String EXTRA_QUERY_RESULT_RECORDING = BASE + "EXTRA_QUERY_RESULT_RECORDING";
    public static final String EXTRA_QUERY_RESULT_PAUSING = BASE + "EXTRA_QUERY_RESULT_PAUSING";

    @Inject
    public ScreenCaptureUtil screenCaptureUtil;

    private NotificationManager mNotificationManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mNotificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        startForegroundService();
    }

    void startForegroundService() {
        Intent notificationIntent = new Intent(this, PushDummyActivity.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        notificationIntent.putExtra(IS_FOREGROUND_SERVICE, true);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        NotificationCompat.Builder builder;
        if (Build.VERSION.SDK_INT >= 26) {
            String CHANNEL_ID = "snwodeer_service_channel";
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "SnowDeer Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT);

            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);
            builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        }else{
            builder = new NotificationCompat.Builder(this);
        }
        Notification notification = builder
                .setSmallIcon(R.drawable.ic_launcher_foreground)  // the status icon
                .setTicker(TAG)  // the status text
                .setWhen(System.currentTimeMillis())  // the time stamp
                .setContentTitle(getText(R.string.app_name))  // the label of the entry
                .setContentText(TAG)  // the contents of the entry
                .setContentIntent(pendingIntent)  // The intent to send when the entry is clicked
                .build();
        startForeground(NOTIFICATION, notification);
        mNotificationManager.notify(NOTIFICATION, notification);
    }


    @Override
    public int onStartCommand(final Intent intent, final int flags, final int startId) {

        int result = START_STICKY;
        final String action = intent != null ? intent.getAction() : null;
        if (ACTION_START.equals(action)) {
            final int resultCode = intent.getIntExtra(EXTRA_RESULT_CODE, 0);
            screenCaptureUtil.screenRecorder(resultCode,intent);
        } else if (ACTION_STOP.equals(action) || TextUtils.isEmpty(action)) {
            screenCaptureUtil.stopRecord();
            final Intent info = new Intent();
            info.setAction(ACTION_QUERY_STATUS_RESULT);
            info.putExtra(EXTRA_QUERY_RESULT_RECORDING, false);
            info.putExtra(EXTRA_QUERY_RESULT_PAUSING, true);
            sendBroadcast(info);
            result = START_NOT_STICKY;


            stopForeground(true/*removeNotification*/);
            if (mNotificationManager != null) {
                mNotificationManager.cancel(NOTIFICATION);
                mNotificationManager = null;
            }
            stopSelf();
        }
        return result;
    }

//    private void startRecord(){
//        ListenableFuture<NetResult> f = executorService.getListeningExecutorService().submit(() -> ga.checkEnroll(tokenCode, scope));
//        f.addListener(() -> {
//            try {
//                excuteResultEnroll(f.get());
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//                Thread.currentThread().interrupt();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//        }, executorService.getUiThreadExecutor());
//    }
}
