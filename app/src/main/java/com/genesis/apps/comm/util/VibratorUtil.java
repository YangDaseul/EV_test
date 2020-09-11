package com.genesis.apps.comm.util;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.RequiresApi;

public class VibratorUtil {
    public static void doVibrator(Application context){
        Vibrator vib = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
        long[] mVibratePattern = new long[]{0, 15};
        int[] mAmplitudes = new int[]{0,1};
        if(Build.VERSION.SDK_INT>28){
            VibrationEffect effect = VibrationEffect.createWaveform(mVibratePattern, mAmplitudes, -1);
            vib.vibrate(effect);
        }else if(Build.VERSION.SDK_INT>25){//vibrationEffect는 26부터 지원을 하지만
            mVibratePattern[1]=50;
            VibrationEffect effect = VibrationEffect.createWaveform(mVibratePattern, mAmplitudes, -1);
            vib.vibrate(effect);
        }else{
            vib.vibrate(50);
        }
    }

    public static void doVibratorLong(Application context){
        Vibrator vib = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {1 , 600, 1};
        vib.vibrate(pattern,-1);
    }





//    @RequiresApi(api = Build.VERSION_CODES.O)
//    public static void createWaveFormVibrationUsingVibrationEffect(Application context) {
//        Vibrator vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
//        long[] mVibratePattern = new long[]{0, 400, 1000, 600, 1000, 800, 1000, 1000};
//        // -1 : Play exactly once
//        VibrationEffect effect = VibrationEffect.createWaveform(mVibratePattern, -1);
//        vibrator.vibrate(effect);
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    public static void createWaveFormVibrationUsingVibrationEffectAndAmplitude(Application context) {
//        Vibrator vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
//        long[] mVibratePattern = new long[]{0, 15};
//        int[] mAmplitudes = new int[]{0,1};
//        // -1 : Play exactly once
//
//            VibrationEffect effect = VibrationEffect.createWaveform(mVibratePattern, mAmplitudes, -1);
//            vibrator.vibrate(effect);
//    }
}
