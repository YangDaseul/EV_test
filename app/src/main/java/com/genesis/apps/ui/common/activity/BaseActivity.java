package com.genesis.apps.ui.common.activity;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.util.excutor.ExecutorService;
import com.genesis.apps.fcm.PushCode;

import javax.inject.Inject;

import androidx.core.app.ActivityCompat;
import dagger.hilt.android.AndroidEntryPoint;

import static com.genesis.apps.comm.model.constants.KeyNames.NOTIFICATION_ID;
import static com.genesis.apps.comm.model.constants.KeyNames.PUSH_CODE;

@AndroidEntryPoint
public class BaseActivity extends AppCompatActivity {

    @Inject
    ExecutorService executorService;

    //About PUSH
    public PushCode pushCode;
    public int notificationId;
    public boolean isForeground=false;
    public Intent intent = null;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutDownExcutor();
    }


    public void startActivitySingleTop(Intent intent, int flag, int animationFlag) {
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if(flag==0)
            startActivity(intent);
        else
            startActivityForResult(intent, flag);

        switch (animationFlag){
            case VariableType.ACTIVITY_TRANSITION_ANIMATION_ZOON:
                overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
                break;
            case VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE:
                overridePendingTransition(R.anim.fragment_enter, R.anim.fragment_stay);
                break;
            case VariableType.ACTIVITY_TRANSITION_ANIMATION_VERTICAL_SLIDE:
                overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
                break;
            case VariableType.ACTIVITY_TRANSITION_ANIMATION_NONE:
            default:
                break;


        }


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
            if(pushCode!=null&&pushCode!=PushCode.CAT_DEFAULT) isPush=true;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RequestCodes.REQ_CODE_ACTIVITY.getCode()&&
                (resultCode==ResultCodes.RES_CODE_NETWORK.getCode()||
                        resultCode==ResultCodes.REQ_CODE_EMPTY_INTENT.getCode()||
                        resultCode==ResultCodes.REQ_CODE_NORMAL.getCode())){
            SnackBarUtil.show(this, data.getStringExtra("msg"));
        }
    }

    public void exitPage(String msg, int resultCode){
        setResult(resultCode, new Intent().putExtra("msg",msg));
        finish();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        closeTransition();
    }

    public void closeTransition(){
        if(findViewById(R.id.l_title_bar_popup)!=null){
            //팝업형 액티비티인 경우
        }else{
            //스택형 액티비티인 경우
            overridePendingTransition(0, R.anim.fragment_exit_toright);
        }
    }



}
