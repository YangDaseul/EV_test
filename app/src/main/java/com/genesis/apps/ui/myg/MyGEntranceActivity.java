package com.genesis.apps.ui.myg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.LGN_0001;
import com.genesis.apps.comm.model.api.gra.LGN_0004;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.net.NetUIResponse;
import com.genesis.apps.comm.net.ga.GA;
import com.genesis.apps.comm.util.PackageUtil;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.viewmodel.DDSViewModel;
import com.genesis.apps.comm.viewmodel.LGNViewModel;
import com.genesis.apps.databinding.ActivityMygEntranceBinding;
import com.genesis.apps.room.ResultCallback;
import com.genesis.apps.ui.common.activity.LoginActivity;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;
import com.genesis.apps.ui.intro.IntroActivity;
import com.genesis.apps.ui.main.ServiceJoinActivity;

import javax.inject.Inject;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MyGEntranceActivity extends SubActivity<ActivityMygEntranceBinding> {
    @Inject
    public GA ga;
    private LGNViewModel lgnViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myg_entrance);
        getDataFromIntent();
        setViewModel();
        setObserver();
    }

    @Override
    public void onClickCommon(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                moveToLogin();
                break;
            case R.id.btn_join:
                moveToJoin();
                break;
            case R.id.btn_find:

                break;
        }
    }

    @Override
    public void setViewModel() {
        ui.setActivity(this);
        lgnViewModel = new ViewModelProvider(this).get(LGNViewModel.class);
    }

    @Override
    public void setObserver() {

        lgnViewModel.getRES_LGN_0001().observe(this, result -> {

            switch (result.status){
                case LOADING:
                    showProgressDialog(true);
                    break;

                case SUCCESS:
                    if(result.data!=null
                            &&(TextUtils.isEmpty(result.data.getCustGbCd())||result.data.getCustGbCd().equalsIgnoreCase("0000"))
                            &&(result.data.getRtCd().equalsIgnoreCase("2002")||result.data.getRtCd().equalsIgnoreCase("2001"))){
                        showProgressDialog(false);
                        startActivitySingleTop(new Intent(this, ServiceJoinActivity.class), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                        break;
                    }else if(result.data!=null
                            &&result.data.getRtCd().equalsIgnoreCase("0000")
                            &&!result.data.getCustGbCd().equalsIgnoreCase("0000")){

                        lgnViewModel.setLGN0001ToDB(result.data, new ResultCallback() {
                            @Override
                            public void onSuccess(Object retv) {
                                if (((Boolean) retv)) {
                                    restart(); //todo 테스트필요
                                } else {
                                    SnackBarUtil.show(MyGEntranceActivity.this, "데이터가 저장되지 않았습니다.\n잠시 후 다시 시도해 주세요.");
                                    //TODO ERROR팝업 추가 필요
                                }
                                showProgressDialog(false);
                            }
                            @Override
                            public void onError(Object e) {
                                showProgressDialog(false);
                                SnackBarUtil.show(MyGEntranceActivity.this, "데이터가 저장되지 않았습니다.\n잠시 후 다시 시도해 주세요.\nErrCode:2");
                            }
                        });

                        break;
                    }
                default:
                    String serverMsg = "";
                    try {
                        serverMsg = result.data.getRtMsg();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (TextUtils.isEmpty(serverMsg)) {
                            serverMsg = getString(R.string.r_flaw06_p02_snackbar_1);
                        }
                        SnackBarUtil.show(this, serverMsg);
                        showProgressDialog(false);
                    }
                    break;
            }

        });

    }

    @Override
    public void getDataFromIntent() {

    }

    public void moveToLogin(){
        startActivitySingleTop(new Intent(this, LoginActivity.class).putExtra("url",ga.getLoginUrl()), RequestCodes.REQ_CODE_LOGIN.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
    }
    public void moveToJoin(){
        startActivitySingleTop(new Intent(this, LoginActivity.class).putExtra("url",ga.getEnrollUrl()), RequestCodes.REQ_CODE_JOIN.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
    }
    public void moveToFind(){

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==Activity.RESULT_OK){
            if(requestCode==RequestCodes.REQ_CODE_LOGIN.getCode()||requestCode==RequestCodes.REQ_CODE_JOIN.getCode()){
                lgnViewModel.reqLGN0001(new LGN_0001.Request(APPIAInfo.INT01.getId(), PackageUtil.getApplicationVersionName(MyGEntranceActivity.this, getPackageName()),""));
            }
        }
    }
}
