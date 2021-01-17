package com.genesis.apps.fcm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.PushCodes;
import com.genesis.apps.ui.common.activity.PushDummyActivity;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

import androidx.core.app.NotificationCompat;


public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    private static final String TAG = FirebaseMessagingService.class.getSimpleName();
    private static final int NOTIFICATION_ID = 687987;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "From:receive push");
        if (remoteMessage != null) {
            Log.e(TAG, "From: " + remoteMessage.getFrom());
            PushVO.PushData data = getPushData(remoteMessage);
            PushVO.Notification notification = getNotification(remoteMessage);
            PushVO pushVO = new PushVO();
            pushVO.setData(data);
            pushVO.setNotification(notification);
            notifyMessage(pushVO, PushCodes.findCodeByCd(pushVO.getData()!=null ? pushVO.getData().getLgrCatCd() : ""));
        }
    }

    private PushVO.PushData getPushData(RemoteMessage remoteMessage) {
        PushVO.PushData data = null;
        if (remoteMessage != null && remoteMessage.getData()!=null && remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Message data payload: " + remoteMessage.getData().toString());
            JSONObject json = new JSONObject(remoteMessage.getData());
            data = new Gson().fromJson(json.toString(), PushVO.PushData.class);
        }
        return data;
    }

    private PushVO.Notification getNotification(RemoteMessage remoteMessage) {
        PushVO.Notification noti = new PushVO.Notification();
        if (remoteMessage != null && remoteMessage.getNotification() != null) {
            noti.setTitle(remoteMessage.getNotification().getTitle());
            noti.setBody(remoteMessage.getNotification().getBody());
        }
        return noti;
    }

    private void notifyMessage(PushVO pushVO, PushCodes code) {
        Log.d(TAG, "push test2: " + code);
        if (pushVO.getNotification() != null && !TextUtils.isEmpty(pushVO.getNotification().getTitle()) && !TextUtils.isEmpty(pushVO.getNotification().getBody())) {
            String head = pushVO.getNotification().getTitle();
            String body = pushVO.getNotification().getBody();
            Intent intent = new Intent(this, PushDummyActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra(KeyNames.PUSH_VO, pushVO);
            intent.putExtra(KeyNames.NOTIFICATION_ID, NOTIFICATION_ID);
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

            if (pushVO.getData() != null && !TextUtils.isEmpty(pushVO.getData().getImgFilUri1())) {
                try {
                    URL url = new URL(pushVO.getData().getImgFilUri1());
                    //이미지 처리
                    Bitmap bigPicture = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    mBuilder.setStyle(
                            new NotificationCompat.BigPictureStyle()
                                    .bigPicture(bigPicture)
                                    .setBigContentTitle(head)
                                    .setSummaryText(body))
                            .setContentIntent(pIntent);

//                                                        .bigPicture(bigPicture)
//                            .setBigContentTitle(remoteMessage.getData().get("title"))
//                            .setSummaryText(remoteMessage.getData().get("body")));
                } catch (IOException e) {

                }


            }


            notificationManager.notify(NOTIFICATION_ID, mBuilder.build());
            Log.e(TAG, "notify!");
            // TODO

            // 모든 PUSH 메시지는 진동 1회 + 효과음 1회
//            MyApplication.effect(this);

        } else {
            // 메시지 없음...
        }
    }


    public static void getToken(FcmListener listener) {
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(task -> {
            String token = null;
            try {
                if (task.isSuccessful()) {
                    token = task.getResult().getToken();
                } else {
                    Log.w(TAG, "getInstanceId failed", task.getException());
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (listener != null) {
                    listener.onToken(token);
                }
            }
        });

    }

    private static NotificationCompat.Builder mBuilder = null;
//    public static void notifyMessageTest(Context context, PushVO pushVO, PushCode code) {
//        String data = "{\n" +
//                "  \"msgLnkUri\": \"\",\n" +
//                "  \"msgLnkCd\": \"\",\n" +
//                "  \"dtlLnkUri\": \"http://www.naver.com\",\n" +
//                "  \"imgIncsYn\": \"N\",\n" +
//                "  \"lgrCatCd\": \"7000\",\n" +
//                "  \"smlCatCd\": \"7001\",\n" +
//                "  \"appCatCd\": \"\",\n" +
//                "  \"dtlLnkCd\": \"O\",\n" +
//                "  \"imgFilUri1\": \"https://s.pstatic.net/static/www/mobile/edit/2016/0705/mobile_212852414260.png\",\n" +
//                "  \"imgFilUri2\": \"\",\n" +
//                "  \"imgFilUri3\": \"\"\n" +
//                "}";
//        PushVO.PushData data1 = new Gson().fromJson(data, PushVO.PushData.class);
//        pushVO.setData(data1);
//        pushVO.setNotification(new PushVO.Notification());
//        pushVO.getNotification().setBody("testestetste");
//        pushVO.getNotification().setTitle("test1");
//
//
//        Log.d(TAG, "push test2: " + code);
//        if (pushVO.getNotification() != null) {
//            String head = pushVO.getNotification().getTitle();
//            String body = pushVO.getNotification().getBody();
//
//            Intent intent = new Intent(context, PushDummyActivity.class);
//
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
//                    | Intent.FLAG_ACTIVITY_CLEAR_TOP
//                    | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//            intent.putExtra(KeyNames.PUSH_VO, pushVO);
//            intent.putExtra(KeyNames.NOTIFICATION_ID, NOTIFICATION_ID);
//            PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//
////            NotificationCompat.Builder mBuilder = null;
//            NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                NotificationChannel channel = new NotificationChannel("defaultChannel", "Noti", NotificationManager.IMPORTANCE_DEFAULT);
//                notificationManager.createNotificationChannel(channel);
//                mBuilder = new NotificationCompat.Builder(context, channel.getId());
//            } else {
//                mBuilder = new NotificationCompat.Builder(context);
//            }
//
//            mBuilder.setSmallIcon(R.mipmap.ic_launcher)
//                    .setContentTitle(head)
//                    .setContentText(body)
//                    .setStyle(new NotificationCompat.BigTextStyle()
//                            .bigText(body))
////                    .addAction(R.drawable.ic_info_1, "added action", pIntent)
//                    .setContentIntent(pIntent);
//
//            if (pushVO.getData() != null && !TextUtils.isEmpty(pushVO.getData().getImgFilUri1())) {
//                try {
//
//
//
//                    Glide.with(context)
//                            .asBitmap().load(pushVO.getData().getImgFilUri1())
//                            .listener(new RequestListener<Bitmap>() {
//                                          @Override
//                                          public boolean onLoadFailed(@Nullable GlideException e, Object o, Target<Bitmap> target, boolean b) {
//                                              return false;
//                                          }
//
//                                          @Override
//                                          public boolean onResourceReady(Bitmap bitmap, Object o, Target<Bitmap> target, DataSource dataSource, boolean b) {
//
//                                              mBuilder.setStyle(
//                                                      new NotificationCompat.BigPictureStyle()
//                                                              .bigPicture(bitmap)
//                                                              .setBigContentTitle(head)
//                                                              .setSummaryText(body));
//
//                                              notificationManager.notify(NOTIFICATION_ID, mBuilder.build());
//                                              return false;
//                                          }
//                                      }
//                            ).submit();
//
//
////                    URL url = new URL(pushVO.getData().getImgFilUri1());
////                    //이미지 처리
////                    Bitmap bigPicture = BitmapFactory.decodeStream(url.openConnection().getInputStream());
////                    mBuilder.setStyle(
////                            new NotificationCompat.BigPictureStyle()
////                                    .bigPicture(bigPicture)
////                                    .setBigContentTitle(head)
////                                    .setSummaryText(body));
//
//                } catch (Exception e) {
//
//                }
//            }else {
//                notificationManager.notify(NOTIFICATION_ID, mBuilder.build());
//                Log.e(TAG, "notify!");
//            }
//            // 모든 PUSH 메시지는 진동 1회 + 효과음 1회
////            MyApplication.effect(this);
//
//        } else {
//            // 메시지 없음...
//        }
//    }
}
