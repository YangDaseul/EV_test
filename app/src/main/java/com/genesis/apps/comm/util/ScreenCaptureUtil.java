package com.genesis.apps.comm.util;

import android.app.Application;
import android.content.Intent;
import android.content.res.Resources;
import android.hardware.display.DisplayManager;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Environment;
import android.util.DisplayMetrics;

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
        try {
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
                    1080, 1920, displayMetrics.densityDpi, DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                    screenRecorder.getSurface(), null, null);

            screenRecorder.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private MediaRecorder createRecorder() {
        CamcorderProfile camcorderProfile = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH);
        MediaRecorder mediaRecorder = new MediaRecorder();
//        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
//        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
//        mediaRecorder.setVideoEncodingBitRate(1024 * 1000);
        mediaRecorder.setVideoEncodingBitRate(camcorderProfile.videoBitRate);
//        mediaRecorder.setVideoFrameRate(30);
        mediaRecorder.setVideoFrameRate(camcorderProfile.videoFrameRate);

//        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();

//        mediaRecorder.setVideoSize(displayMetrics.widthPixels, displayMetrics.heightPixels);
        mediaRecorder.setVideoSize(1080, 1920);
//        mediaRecorder.setVideoSize(camcorderProfile.videoFrameWidth, camcorderProfile.videoFrameHeight);
        mediaRecorder.setOutputFile(videoFile);

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
