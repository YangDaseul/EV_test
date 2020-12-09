package com.genesis.apps.ui.common.activity;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.util.excutor.ExecutorService;
import com.genesis.apps.fcm.PushVO;
import com.genesis.apps.ui.intro.IntroActivity;
import com.genesis.apps.ui.main.AlarmCenterActivity;
import com.genesis.apps.ui.main.service.ServiceReviewActivity;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import dagger.hilt.android.AndroidEntryPoint;

import static com.genesis.apps.comm.model.api.APPIAInfo.SM_REVIEW01_P03;
import static com.genesis.apps.comm.model.constants.KeyNames.NOTIFICATION_ID;
import static com.genesis.apps.comm.model.constants.KeyNames.PUSH_VO;

@AndroidEntryPoint
public class BaseActivity extends AppCompatActivity {

    @Inject
    ExecutorService executorService;

    //About PUSH
    public PushVO pushVO;
    public int notificationId;
    public boolean isForeground=false;
    public Intent intent = null;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutDownExcutor();
    }


    public void startActivitySingleTop(Intent intent, int flag, int animationFlag) {

        // intent에 Intent.FLAG_ACTIVITY_FORWARD_RESULT 가 추가됬을 경우 반드시 flag를 0으로 사용해야함

        if(intent.getFlags()==Intent.FLAG_ACTIVITY_FORWARD_RESULT)
            flag=0;

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
                overridePendingTransition(R.anim.slide_up, R.anim.fragment_stay);
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

    public Intent getPushIntent(Class className){
        intent = new Intent(this, className).putExtra(PUSH_VO, pushVO).putExtra(NOTIFICATION_ID, notificationId);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return intent;
    }


    public boolean isPushData() {
        boolean isPush=false;
        try {
            pushVO = (PushVO) getIntent().getSerializableExtra(PUSH_VO);
            notificationId = getIntent().getIntExtra(NOTIFICATION_ID, 0);
            if(pushVO!=null) isPush=true;
        }catch (Exception e){

        }
        return isPush;
    }

    public void checkPushCode() {
        if (isPushData()) {

            try {
                String url = !TextUtils.isEmpty(pushVO.getData().getMsgLnkUri()) ? pushVO.getData().getMsgLnkUri() : pushVO.getData().getDtlLnkUri();
                if (!TextUtils.isEmpty(url))
                    moveToNativePage(url, true);
            } catch (Exception e) {

            }

            this.getIntent().removeExtra(PUSH_VO);
            this.getIntent().removeExtra(NOTIFICATION_ID);
            ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).cancel(notificationId);
        }
    }

    public void moveToNativePage(String lnkUri, boolean isPush) {
        Uri uri = null;
        String id = "";
        String PI = "";
        try {
            uri = Uri.parse(lnkUri);
            id = uri.getQueryParameter(KeyNames.KEY_NAME_URI_PARSER_ID);
            PI = uri.getQueryParameter(KeyNames.KEY_NAME_URI_PARSER_PI);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(TextUtils.isEmpty(id))
                return;
        }

        switch (APPIAInfo.findCode(id)) {
            case SM_REVIEW01_P01:
                if (!TextUtils.isEmpty(PI)) {
                    startActivitySingleTop(new Intent(this, ServiceReviewActivity.class).putExtra(KeyNames.KEY_NAME_REVIEW_RSVT_SEQ_NO, PI), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                }else{
                    SnackBarUtil.show(this, "예약번호가 존재하지 않습니다.");
                }
                break;
            case SM_REVIEW01_P03:
                if (!TextUtils.isEmpty(PI)) {
                    startActivitySingleTop(new Intent(this, ServiceReviewActivity.class).putExtra(KeyNames.KEY_NAME_REVIEW_TRANS_ID, PI), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                }else{
                    SnackBarUtil.show(this, "트랜잭션 ID가 존재하지 않습니다.");
                }
                break;
            default:
                if(!isPush) {
                    APPIAInfo appiaInfo = APPIAInfo.findCode(id);
                    if (appiaInfo != null && appiaInfo.getActivity() != null) {
                        startActivitySingleTop(new Intent(this, appiaInfo.getActivity()), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                    }else{
                        SnackBarUtil.show(this, "화면 ID가 올바르지 않습니다.");
                    }
                }else{
                    startActivitySingleTop(new Intent(this, AlarmCenterActivity.class).putExtra(PUSH_VO, pushVO), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                }
                break;
        }
    }

    public void moveToExternalPage(String lnkUri, String wvYn){

        if (TextUtils.isEmpty(wvYn) || wvYn.equalsIgnoreCase(VariableType.COMMON_MEANS_YES)) {
            startActivitySingleTop(new Intent(this, GAWebActivity.class).putExtra(KeyNames.KEY_NAME_URL, lnkUri), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
        } else {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(lnkUri));
            startActivity(intent); //TODO 테스트 필요 0002
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RequestCodes.REQ_CODE_ACTIVITY.getCode()&&
                (resultCode==ResultCodes.RES_CODE_NETWORK.getCode()||
                        resultCode==ResultCodes.REQ_CODE_EMPTY_INTENT.getCode()||
                        resultCode==ResultCodes.REQ_CODE_NORMAL.getCode())){

            String errorMsg="";
            try {
                errorMsg = data.getStringExtra("msg");
            }catch (Exception e){
                e.printStackTrace();
            }finally{
                if(TextUtils.isEmpty(errorMsg)){
                    if(requestCode==ResultCodes.RES_CODE_NETWORK.getCode()){
                        errorMsg = getString(R.string.r_flaw06_p02_snackbar_1);
                    }else{
                        errorMsg = "오류가 발생했습니다.\n앱 재실행 후 다시 시도해 주세요. \nCODE:1";
                    }
                }
                SnackBarUtil.show(this, errorMsg);
            }
        }
    }

    public void exitPage(String msg, int resultCode){
        setResult(resultCode, new Intent().putExtra("msg",msg));
        finish();
        closeTransition();
    }

    public void exitPage(Intent intent, int resultCode){
        setResult(resultCode, intent);
        finish();
        closeTransition();
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


    /**
     * 앱 재시작
     */
    public void restart() {
        final Intent intent = new Intent(this, IntroActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_USER_ACTION);
        startActivity(intent);
        finish();
    }

}
