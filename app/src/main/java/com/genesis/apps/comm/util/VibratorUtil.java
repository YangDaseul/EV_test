package com.genesis.apps.comm.util;

import android.app.Application;
import android.content.Context;
import android.os.Vibrator;
import android.view.inputmethod.InputMethodManager;

public class VibratorUtil {
    public static void doVibrator(Application context){
        Vibrator vib = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
        vib.vibrate(100);
    }

    public static void doVibratorLong(Application context){
        Vibrator vib = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {100 , 400, 500,100 , 400, 500};
        vib.vibrate(pattern,-1);
    }

}
