package com.genesis.apps.comm.util;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.genesis.apps.R;
import com.google.android.material.snackbar.Snackbar;
import com.kishandonga.csbx.CustomSnackbar;

public class SnackBarUtil {

//    public static void show(Activity activity, String snackBarMsg) {
//        if (activity != null) {
//            Snackbar.make(activity.getWindow().getDecorView().findViewById(android.R.id.content), validateString(snackBarMsg), Snackbar.LENGTH_LONG).show();
//
//        }
//    }

    public static void show(Activity activity, String msg) {
        if (activity != null) {
            final CustomSnackbar sb = new CustomSnackbar(activity);
            sb.customView(R.layout.snackbar_msg);
            sb.duration(Snackbar.LENGTH_LONG);
            ((TextView)sb.getView().findViewById(R.id.tv_msg)).setText(msg);
            sb.show();


            //                sb.withCustomView(new Function1<View, Unit>() {
//                    @Override
//                    public Unit invoke(View view) {
//                        view.findViewById(R.id.btnUndo).setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                sb.dismiss();
//                            }
//                        });
//                        return null;
//                    }
//                });
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
                Toast.makeText(activity, validateString(snackBarMsg), Toast.LENGTH_LONG).show();
            }
        }
    }

    // for activity and action
    public static void show(Activity activity, String snackBarMsg, String actionText, View.OnClickListener clickListener) {
        if (activity != null) {
            Snackbar
                    .make(activity.getWindow().getDecorView().findViewById(android.R.id.content), validateString(snackBarMsg), Snackbar.LENGTH_LONG)
                    .setAction(actionText, clickListener).show();
        }

    }

    // for view and action
    public static void show(View view, String snackBarMsg, String actionText, View.OnClickListener clickListener) {
        if (view != null) {
            Snackbar
                    .make(view, validateString(snackBarMsg), Snackbar.LENGTH_LONG)
                    .setAction(actionText, clickListener).show();
        }

    }

    // for styling view and action color action
    public static void show(View view, int viewBgColor, int colorOfMessage, String snackBarMsg, boolean isCapsMesg, int messageSize, int actionTextColor, String actionText, View.OnClickListener clickListener) {
        if (view != null) {
            Snackbar snackbar = Snackbar.make(view, validateString(snackBarMsg), Snackbar.LENGTH_LONG);
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

    private static String validateString(String msg) {
        if (msg == null) {
            return "null";
        }
        return msg;
    }



//    public static Snackbar makeText(Context context, String message, int duration) {
//        Activity activity = (Activity) context;
//        View layout;
//        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content), message, duration);
//        layout = snackbar.getView();
//        //setting background color
//        layout.setBackgroundColor(context.getResources().getColor(R.color.orange));
//        android.widget.TextView text = (android.widget.TextView) layout.findViewById(android.support.design.R.id.snackbar_text);
//        //setting font color
//        text.setTextColor(context.getResources().getColor(R.color.white));
//        Typeface font = null;
//        //Setting font
//        font = Typeface.createFromAsset(context.getAssets(), "DroidSansFallbackanmol256.ttf");
//        text.setTypeface(font);
//        return snackbar;
//
//    }


//
//    /************************************ ShowSnackbar with message, KeepItDisplayedOnScreen for few seconds*****************************/
//    public static void showSnakbarTypeOne(View rootView, String mMessage) {
//        Snackbar.make(rootView, mMessage, Snackbar.LENGTH_LONG)
//                .setAction("Action", null)
//                .show();
//    }
//
//    /************************************ ShowSnackbar with message, KeepItDisplayedOnScreen*****************************/
//    public static void showSnakbarTypeTwo(View rootView, String mMessage) {
//
//        Snackbar.make(rootView, mMessage, Snackbar.LENGTH_LONG)
//                .make(rootView, mMessage, Snackbar.LENGTH_INDEFINITE)
//                .setAction("Action", null)
//                .show();
//
//    }
//
//    /************************************ ShowSnackbar without message, KeepItDisplayedOnScreen, OnClickOfOk restrat the activity*****************************/
//    public static void showSnakbarTypeThree(View rootView, final Activity activity) {
//
//        Snackbar
//                .make(rootView, "NoInternetConnectivity", Snackbar.LENGTH_INDEFINITE)
//                .setAction("TryAgain", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Intent intent = activity.getIntent();
//                        activity.finish();
//                        activity.startActivity(intent);
//                    }
//                })
//                .setActionTextColor(Color.CYAN)
//                .addCallback(new Snackbar.Callback() {
//                    @Override
//                    public void onDismissed(Snackbar snackbar, int event) {
//                        super.onDismissed(snackbar, event);
//                    }
//
//                    @Override
//                    public void onShown(Snackbar snackbar) {
//                        super.onShown(snackbar);
//                    }
//                })
//                .show();
//
//    }
//
//    /************************************ ShowSnackbar with message, KeepItDisplayedOnScreen, OnClickOfOk restrat the activity*****************************/
//    public static void showSnakbarTypeFour(View rootView, final Activity activity, String mMessage) {
//
//        Snackbar
//                .make(rootView, mMessage, Snackbar.LENGTH_INDEFINITE)
//                .setAction("TryAgain", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Intent intent = activity.getIntent();
//                        activity.finish();
//                        activity.startActivity(intent);
//                    }
//                })
//                .setActionTextColor(Color.CYAN)
//                .addCallback(new Snackbar.Callback() {
//                    @Override
//                    public void onDismissed(Snackbar snackbar, int event) {
//                        super.onDismissed(snackbar, event);
//                    }
//
//                    @Override
//                    public void onShown(Snackbar snackbar) {
//                        super.onShown(snackbar);
//                    }
//                })
//                .show();
//
//    }
}
