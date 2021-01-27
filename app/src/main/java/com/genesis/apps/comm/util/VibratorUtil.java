package com.genesis.apps.comm.util;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

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


    public static View makeMeShake(View view, int duration, int offset) {
        Animation anim = new TranslateAnimation(-offset,offset,0,0);
        anim.setDuration(duration);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(5);
        view.startAnimation(anim);
        return view;
    }

    public static View makeMeShakeY(View view, int duration, int offset) {
        Animation anim = new TranslateAnimation(0,0,-offset,offset);
        anim.setDuration(duration);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(5);
        view.startAnimation(anim);
        return view;
    }
}
