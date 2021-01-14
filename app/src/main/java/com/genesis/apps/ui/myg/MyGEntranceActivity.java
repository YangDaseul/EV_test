package com.genesis.apps.ui.myg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.LGN_0001;
import com.genesis.apps.comm.model.api.gra.LGN_0007;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.net.ga.GA;
import com.genesis.apps.comm.util.PackageUtil;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.viewmodel.LGNViewModel;
import com.genesis.apps.databinding.ActivityMygEntranceBinding;
import com.genesis.apps.room.ResultCallback;
import com.genesis.apps.ui.common.activity.LoginActivity;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.view.listener.ViewPressEffectHelper;
import com.genesis.apps.ui.main.ServiceJoinActivity;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MyGEntranceActivity extends SubActivity<ActivityMygEntranceBinding> {
    @Inject
    public GA ga;
    private LGNViewModel lgnViewModel;

    private String tokenCode;
    private String authUuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myg_entrance);
        getDataFromIntent();
        setViewModel();
        setObserver();
        initView();
    }

    private void initView() {
        ViewPressEffectHelper.attach(ui.btnFind);
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
                moveToFind();
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
        lgnViewModel.getRES_LGN_0007().observe(this, result -> {
            restart();
            switch (result.status){
                case LOADING:

                    break;
                case SUCCESS:
                    if(result.data!=null&&result.data.getTopicList()!=null){
                        subscribeTopic(lgnViewModel, result.data.getTopicList());
//                        try {
//                            //기존에 db에 등록된 토픽 확인 및 구독 해제
//                            List<TopicVO> topicList = new ArrayList<>();
//                            topicList.addAll(lgnViewModel.getTopicList());
//                            for (TopicVO oriTopic : topicList) {
//                                FirebaseMessaging.getInstance().unsubscribeFromTopic(oriTopic.getTopicNm());
//                            }
//                        }catch (Exception e){
//
//                        }
//
//                        try {
//                            //db에 신규 토픽 등록
//                            lgnViewModel.insertTopicList(result.data.getTopicList());
//                            //db에 신규 등록된 토픽을 로드
//                            List<TopicVO> newTopicList = new ArrayList<>();
//                            newTopicList.addAll(lgnViewModel.getTopicList());
//                            for (TopicVO newTopic : newTopicList) {
//                                FirebaseMessaging.getInstance().subscribeToTopic(newTopic.getTopicNm());
//                            }
//                        }catch (Exception e){
//
//                        }
                    }
                    restart();
                    break;
                default:
                    restart();
                    break;
            }
        });


        lgnViewModel.getRES_LGN_0001().observe(this, result -> {

            switch (result.status){
                case LOADING:
                    showProgressDialog(true);
                    break;

                case SUCCESS:
                    if(result.data!=null
                            &&(TextUtils.isEmpty(result.data.getCustGbCd())||result.data.getCustGbCd().equalsIgnoreCase("0000"))
//                            &&(result.data.getRtCd().equalsIgnoreCase("2002")||result.data.getRtCd().equalsIgnoreCase("2001"))){
                            &&(result.data.getRtCd().equalsIgnoreCase("0000"))){
                        showProgressDialog(false);
                        startActivitySingleTop(new Intent(this, ServiceJoinActivity.class).putExtra(VariableType.KEY_NAME_LOGIN_TOKEN_CODE, tokenCode).putExtra(VariableType.KEY_NAME_LOGIN_AUTH_UUID, authUuid), RequestCodes.REQ_CODE_JOIN_SERVICE.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                        break;
                    }else if(result.data!=null
                            &&result.data.getRtCd().equalsIgnoreCase("0000")
                            &&!result.data.getCustGbCd().equalsIgnoreCase("0000")){

                        lgnViewModel.setLGN0001ToDB(result.data, new ResultCallback() {
                            @Override
                            public void onSuccess(Object retv) {
                                if (((Boolean) retv)) {
                                    lgnViewModel.reqLGN0007(new LGN_0007.Request(APPIAInfo.INT01.getId()));
                                } else {
                                    ga.clearLoginInfo();
                                    SnackBarUtil.show(MyGEntranceActivity.this, "데이터가 저장되지 않았습니다.\n잠시 후 다시 시도해 주세요.");
                                }
                                showProgressDialog(false);
                            }
                            @Override
                            public void onError(Object e) {
                                showProgressDialog(false);
                                ga.clearLoginInfo();
                                SnackBarUtil.show(MyGEntranceActivity.this, "데이터가 저장되지 않았습니다.\n잠시 후 다시 시도해 주세요.\nErrCode:2");
                            }
                        }, true);

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
                        ga.clearLoginInfo();
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
        startActivitySingleTop(new Intent(this, LoginActivity.class)
                        .putExtra(KeyNames.KEY_NAME_URL,ga.getLoginUrl())
                        .putExtra(KeyNames.KEY_ANME_CCSP_TYPE, LoginActivity.TYPE_LOGIN)
                , RequestCodes.REQ_CODE_LOGIN.getCode()
                , VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
    }
    public void moveToJoin(){
        startActivitySingleTop(new Intent(this, LoginActivity.class)
                        .putExtra(KeyNames.KEY_NAME_URL,ga.getEnrollUrl())
                        .putExtra(KeyNames.KEY_ANME_CCSP_TYPE, LoginActivity.TYPE_JOIN)
                , RequestCodes.REQ_CODE_JOIN.getCode()
                , VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
    }
    public void moveToFind(){
        startActivitySingleTop(new Intent(this, LoginActivity.class)
                        .putExtra(KeyNames.KEY_NAME_URL,ga.getFindUrl())
                        .putExtra(KeyNames.KEY_ANME_CCSP_TYPE, LoginActivity.TYPE_FIND)
                , RequestCodes.REQ_CODE_JOIN.getCode()
                , VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==Activity.RESULT_OK){
            if(requestCode==RequestCodes.REQ_CODE_LOGIN.getCode()||requestCode==RequestCodes.REQ_CODE_JOIN.getCode()&&data!=null){
                try {
                    tokenCode = data.getStringExtra(VariableType.KEY_NAME_LOGIN_TOKEN_CODE);
                    authUuid = data.getStringExtra(VariableType.KEY_NAME_LOGIN_AUTH_UUID);
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if(!TextUtils.isEmpty(tokenCode)) lgnViewModel.reqLGN0001(new LGN_0001.Request(APPIAInfo.INT01.getId(), PackageUtil.getApplicationVersionName(MyGEntranceActivity.this, getPackageName()),""));
                }
            }else if(requestCode==RequestCodes.REQ_CODE_JOIN_SERVICE.getCode()){
                //서비스 가입 완료 시
                lgnViewModel.reqLGN0001(new LGN_0001.Request(APPIAInfo.INT01.getId(), PackageUtil.getApplicationVersionName(MyGEntranceActivity.this, getPackageName()),""));
            }
        }else{
            ga.clearLoginInfo();
        }
    }
}
