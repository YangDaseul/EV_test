package com.genesis.apps.fcm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.genesis.apps.R;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import static com.genesis.apps.fcm.PushCode.findCode;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    private static final String TAG = FirebaseMessagingService.class.getSimpleName();
    private static final int NOTIFICATION_ID  = 687987; //TODO 2020-07-16 PUSH 정책에 따라 변경

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        //TODO 2020-07-16 PUSH DB 저장 유무 확인 필요
//        PushEventBus.Message m = new PushEventBus.Message(PushEventBus.Event.RECEIVE_NEW_PUSH);
//        PushEventBus.getInstance().post(m);

        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            JsonObject json = new Gson().fromJson(remoteMessage.getData().toString(), JsonObject.class);


            String id = json.get("id").getAsString();
            String category = json.get("cat").getAsString();

            JsonObject messageObject = json.get("msg") == null ? null : json.get("msg").getAsJsonObject();
            JsonObject dataObject = json.get("data") == null ? null : json.get("data").getAsJsonObject();

            switch (findCode(category)) {
                case CAT_00:
                case CAT_01:
                case CAT_02:
                case CAT_03:
                case CAT_05:
                case CAT_06:
                case CAT_07:
                case CAT_08:
                case CAT_09:
                case CAT_0A:
                case CAT_0B:
                case CAT_0C:
                case CAT_0D:
                case CAT_0E:
                case CAT_0Z:
                case CAT_21:
                case CAT_22:
                case CAT_23:
                case CAT_50:
                case CAT_0X:
                case CAT_0K:
                default:
                    if (messageObject != null) {
                        notifyMessage(messageObject,findCode(category));
                    } else {
                        // 메시지 없음... 오류
                        Log.e(TAG, "category is " + category + " but message object is null!");
                    }
                    break;
            }
        }
    }

    private void notifyMessage(JsonObject messageObject, PushCode code) {
        Log.d(TAG, "push test2: " +code);
        if (messageObject != null) {
            String head = messageObject.get("head").getAsString();
            String body = messageObject.get("body").getAsString();

//            Intent intent = new Intent(this, IntroActivity.class);
//            PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);

            Intent intent = new Intent(this, PushDummyActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("code", code);
            PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);



            NotificationCompat.Builder mBuilder = null;
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel("defaultChannel", "Noti", NotificationManager.IMPORTANCE_DEFAULT);
                notificationManager.createNotificationChannel(channel);
                mBuilder = new NotificationCompat.Builder(this, channel.getId());
            } else {
                mBuilder = new NotificationCompat.Builder(this);
            }

            mBuilder.setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(head)
                    .setContentText(body)
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(body))
//                    .addAction(R.drawable.ic_info_1, "added action", pIntent)
                    .setContentIntent(pIntent);


            notificationManager.notify(NOTIFICATION_ID, mBuilder.build());
            Log.e(TAG, "notify!");
            // TODO

            // 모든 PUSH 메시지는 진동 1회 + 효과음 1회
//            MyApplication.effect(this);

        } else {
            // 메시지 없음...
        }
    }

//    public static void notifyMessageGpsTest(Context context, CodeCategory category) {
//            String head = "NOTI LANDING TEST";
//            String body = "테스트 중 입니다.";
//
////            Intent intent = new Intent(this, IntroActivity.class);
////            PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
//
//            PushGpsInfo info = new PushGpsInfo();
//            info.setVin("vin");
//            info.setDkcCustNo("no");
//            info.setSendDt("sendDt");
//            info.setLatitude("latitude");
//            info.setLongitude("longitude");
//            info.setType(PushGpsInfo.PUSH_GPS_TYPE.GPS);
//
//            Intent intent = new Intent(context, PushDummyActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
//                    | Intent.FLAG_ACTIVITY_CLEAR_TOP
//                    | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//            intent.putExtra("push_gps_info", info);
//            intent.putExtra("category", category);
//            PendingIntent pIntent = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//            PushGpsInfo info2 = new PushGpsInfo();
//            info2.setVin("vin");
//            info2.setDkcCustNo("no");
//            info2.setSendDt("sendDt");
//            info2.setLatitude("latitude");
//            info2.setLongitude("longitude");
//            info2.setType(PushGpsInfo.PUSH_GPS_TYPE.GPS_REGIST);
//
//            Intent intent2 = new Intent(context, PushDummyActivity.class);
//            intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
//                    | Intent.FLAG_ACTIVITY_CLEAR_TOP
//                    | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//            intent2.putExtra("push_gps_info", info2);
//            intent2.putExtra("category", category);
//            PendingIntent pendingIntent2 = PendingIntent.getActivity(context, 2, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
//
//
//
//            NotificationCompat.Builder mBuilder = null;
//            NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                NotificationChannel channel = new NotificationChannel("defaultChannel", "Noti", NotificationManager.IMPORTANCE_DEFAULT);
//                notificationManager.createNotificationChannel(channel);
//                mBuilder = new NotificationCompat.Builder(context, channel.getId());
//            } else {
//                mBuilder = new NotificationCompat.Builder(context);
//            }
//
//
//            mBuilder.setSmallIcon(R.mipmap.app_icon)
//                    .setContentTitle(head)
//                    .setContentText(body)
//                    .addAction(0, "확인", pIntent)
//                    .addAction(0, "수정", pendingIntent2)
//                    .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
//                    .setContentIntent(pIntent)
//                    .setAutoCancel(true);
//
//
//            notificationManager.notify(NOTIFICATION_ID, mBuilder.build());
//            Log.e(TAG, "notify!");
//            // TODO
//
//            // 모든 PUSH 메시지는 진동 1회 + 효과음 1회
////            MyApplication.effect(this);
//
//    }

//        public static void notifyMessageTest(Context context, CodeCategory category) {
////            String head = messageObject.get("head").getAsString();
////            String body = messageObject.get("body").getAsString();
//
//            String head = "NOTI LANDING TEST";
//            String body = "테스트 중 입니다.";
//
//
//            Intent intent = new Intent(context, PushDummyActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
//                | Intent.FLAG_ACTIVITY_CLEAR_TOP
//                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//            intent.putExtra("category", category);
//            PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//            NotificationCompat.Builder mBuilder = null;
//            NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                NotificationChannel channel = new NotificationChannel("defaultChannel", "Noti", NotificationManager.IMPORTANCE_DEFAULT);
//                notificationManager.createNotificationChannel(channel);
//                mBuilder = new NotificationCompat.Builder(context, channel.getId());
//            } else {
//                mBuilder = new NotificationCompat.Builder(context);
//            }
//
//            mBuilder.setSmallIcon(R.mipmap.app_icon)
//                    .setContentTitle(head)
//                    .setContentText(body)
//                    .setStyle(new NotificationCompat.BigTextStyle()
//                            .bigText(body))
////                    .addAction(R.drawable.ic_info_1, "added action", pIntent)
//                    .setContentIntent(pIntent);
//
//
//            notificationManager.notify(NOTIFICATION_ID, mBuilder.build());
//            Log.e(TAG, "notify!");
//            // TODO
//            // 모든 PUSH 메시지는 진동 1회 + 효과음 1회
////            MyApplication.effect(this);
//
//    }
}