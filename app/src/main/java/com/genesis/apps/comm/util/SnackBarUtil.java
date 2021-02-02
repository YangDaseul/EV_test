package com.genesis.apps.comm.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.androidadvance.topsnackbar.TSnackbar;
import com.genesis.apps.R;
import com.genesis.apps.comm.MyApplication;
import com.genesis.apps.databinding.ItemPriceTitleBinding;
import com.genesis.apps.databinding.ItemSnackbarBinding;
import com.google.android.material.snackbar.Snackbar;
import com.kishandonga.csbx.CustomSnackbar;

import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;

public class SnackBarUtil {

//    public static void show(Activity activity, String snackBarMsg) {
//        if (activity != null) {
//            Snackbar.make(activity.getWindow().getDecorView().findViewById(android.R.id.content), StringUtil.isValidString(snackBarMsg), Snackbar.LENGTH_LONG).show();
//
//        }
//    }

    public static void show(Activity activity, String msg) {
        if (activity != null) {

            TSnackbar snackbar = TSnackbar
                    .make(activity.findViewById(android.R.id.content), StringUtil.isValidString(msg), TSnackbar.LENGTH_LONG);
            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor(activity.getColor(R.color.x_1a1a1a));
            TextView textView = snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            textView.setTypeface(ResourcesCompat.getFont(activity, R.font.regular_genesissanstextglobal));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,14);
            int padding = (int)(DeviceUtil.dip2Pixel(activity,30));
//            textView.setPadding(padding,padding,padding,padding);
//            textView.setPa
            textView.setMinHeight((int)(DeviceUtil.dip2Pixel(activity,100)));
            textView.setMaxLines(Integer.MAX_VALUE);
            snackbar.show();

//                TSnackbar.make(activity.findViewById(android.R.id.content), StringUtil.isValidString(msg), TSnackbar.LENGTH_LONG).show();





//            final CustomSnackbar sb = new CustomSnackbar(activity);
//            sb.customView(R.layout.snackbar_msg);
//            sb.duration(Snackbar.LENGTH_LONG);
//            ((TextView)sb.getView().findViewById(R.id.tv_msg)).setText(msg);
//            sb.show();
        }
    }

    /*
     * if you are passing context from some where then it will be show toast because snackbar can show only for activit
     * */
    public static void show(Context activity, String snackBarMsg) {
        if (activity != null) {
            if (activity instanceof Activity) {
                show((Activity) activity, snackBarMsg);
            } else {
                Toast.makeText(activity, StringUtil.isValidString(snackBarMsg), Toast.LENGTH_LONG).show();
            }
        }
    }

    // for activity and action
    public static void show(Activity activity, String snackBarMsg, String actionText, View.OnClickListener clickListener) {
        if (activity != null) {
            Snackbar
                    .make(activity.getWindow().getDecorView().findViewById(android.R.id.content), StringUtil.isValidString(snackBarMsg), Snackbar.LENGTH_LONG)
                    .setAction(actionText, clickListener).show();
        }

    }

    // for view and action
    public static void show(View view, String snackBarMsg, String actionText, View.OnClickListener clickListener) {
        if (view != null) {
            Snackbar
                    .make(view, StringUtil.isValidString(snackBarMsg), Snackbar.LENGTH_LONG)
                    .setAction(actionText, clickListener).show();
        }

    }

    // for styling view and action color action
    public static void show(View view, int viewBgColor, int colorOfMessage, String snackBarMsg, boolean isCapsMesg, int messageSize, int actionTextColor, String actionText, View.OnClickListener clickListener) {
        if (view != null) {
            Snackbar snackbar = Snackbar.make(view, StringUtil.isValidString(snackBarMsg), Snackbar.LENGTH_LONG);
            View snackbarView = snackbar.getView();

          /*  // styling for rest of text
            TextView textView = snackbarView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(colorOfMessage);
            textView.setAllCaps(isCapsMesg);
            textView.setTextSize(messageSize<10?20:messageSize);*/


            // styling for background of snackbar

            snackbarView.setBackgroundColor(viewBgColor);

            //styling for action of text
            snackbar.setActionTextColor(actionTextColor);
            snackbar.setAction(actionText, clickListener).show();

        }

    }
}
