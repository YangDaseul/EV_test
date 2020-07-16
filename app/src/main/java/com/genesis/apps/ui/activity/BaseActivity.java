package com.genesis.apps.ui.activity;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.genesis.apps.R;
import com.genesis.apps.comm.util.excutor.ExecutorService;
import com.genesis.apps.fcm.PushCode;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

import static com.genesis.apps.comm.model.KeyNames.NOTIFICATION_ID;
import static com.genesis.apps.comm.model.KeyNames.PUSH_CODE;

@AndroidEntryPoint
public class BaseActivity extends AppCompatActivity {
    @Inject
    public ExecutorService executorService;

    //About PUSH
    public PushCode pushCode;
    public int notificationId;
    public Intent intent = null;
    public boolean isExcuteApp = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutDownExcutor();
    }

    public final void showProgressDialog(final boolean show, final View view) {
        try {
            if (view != null)
                runOnUiThread(() -> view.setVisibility(show ? View.VISIBLE : View.GONE));
        } catch (Exception ignore) {

        }
    }

    public final void showProgressDialog(final boolean show) {
        try {
            if (findViewById(R.id.l_progress) != null)
                runOnUiThread(() -> findViewById(R.id.l_progress).setVisibility(show ? View.VISIBLE : View.GONE));
        } catch (Exception e) {

        }
    }

    public void startActivitySingleTop(Intent intent, int flag) {
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if(flag==0)
            startActivity(intent);
        else
            startActivityForResult(intent, flag);
    }

    public void alert(String title, String msg, DialogInterface.OnClickListener okListener) {
        if(TextUtils.isEmpty(title)) title = getString(R.string.comm_word_3);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton(R.string.comm_word_1, okListener);
        builder.setCancelable(false);
        builder.create();
        builder.show();
    }




    public Intent moveToPush(Class className){
        intent = new Intent(this, className).putExtra(PUSH_CODE, pushCode).putExtra(NOTIFICATION_ID, notificationId);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return intent;
    }


    public boolean isPushData() {
        boolean isPush=false;
        try {
            pushCode = (PushCode) getIntent().getSerializableExtra(PUSH_CODE);
            notificationId = getIntent().getIntExtra(NOTIFICATION_ID, 0);
            if(pushCode!=PushCode.CAT_DEFAULT) isPush=true;
        }catch (Exception e){
            pushCode = PushCode.CAT_DEFAULT;
        }
        return isPush;
    }

    public void checkPushCode() {
        if(isPushData()) {
            switch (pushCode) {
                case CAT_50:
                    //TODO EXCUTE ACTIVITY
                    break;
                case CAT_G1:
                    //TODO EXCUTE ACTIVITY
                    break;
                case CAT_40:
                case CAT_41:
                case CAT_42:
                case CAT_43:
                    //TODO EXCUTE ACTIVITY
                    break;
                default:
                    //TODO EXCUTE ACTIVITY
                    break;
            }

            if (intent != null) {
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
            this.getIntent().removeExtra(PUSH_CODE);
            this.getIntent().removeExtra(NOTIFICATION_ID);
            ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).cancel(notificationId);
        }
    }

}
