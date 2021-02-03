package com.genesis.apps.comm.util;

import android.app.Application;
import android.content.Intent;
import android.content.res.Resources;
import android.hardware.display.DisplayManager;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import java.io.IOException;

import javax.inject.Inject;

import static android.content.Context.MEDIA_PROJECTION_SERVICE;

public class ScreenCaptureUtil {
    public static final String videoFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/MediaProjection.mp4";
    private MediaProjection mediaProjection;
//    private Activity activity;
    private Application context;

    @Inject
    public ScreenCaptureUtil(Application context){
        this.context = context;
    }

    public void screenRecorder(int resultCode, @Nullable Intent data) {
        final MediaRecorder screenRecorder = createRecorder();
        MediaProjectionManager mediaProjectionManager = (MediaProjectionManager) context.getSystemService(MEDIA_PROJECTION_SERVICE);
        mediaProjection = mediaProjectionManager.getMediaProjection(resultCode, data);
        MediaProjection.Callback callback = new MediaProjection.Callback() {
            @Override
            public void onStop() {
                super.onStop();
                if (screenRecorder != null) {
                    screenRecorder.stop();
                    screenRecorder.reset();
                    screenRecorder.release();
                }
                mediaProjection.unregisterCallback(this);
                mediaProjection = null;
            }
        };
        mediaProjection.registerCallback(callback, null);

        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        mediaProjection.createVirtualDisplay(
                "sample",
                displayMetrics.widthPixels, displayMetrics.heightPixels, displayMetrics.densityDpi, DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                screenRecorder.getSurface(), null, null);

        screenRecorder.start();
    }


    private MediaRecorder createRecorder() {
        MediaRecorder mediaRecorder = new MediaRecorder();
//        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
//        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mediaRecorder.setVideoEncodingBitRate(512 * 1000);
        mediaRecorder.setVideoFrameRate(30);

        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        mediaRecorder.setVideoSize(displayMetrics.widthPixels, (int) (displayMetrics.heightPixels * 0.9));
        mediaRecorder.setOutputFile(videoFile);

//        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        Display display = wm.getDefaultDisplay();
//
//        Point size = new Point();
//        display.getSize(size);
//
//        Log.d("JJJJ", "width : " + size.x + ", height : " + size.y);
        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mediaRecorder;
    }

    public void stopRecord() {
        if(mediaProjection!=null) mediaProjection.stop();
    }







}
