package com.genesis.apps.ui.main.service;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.vo.VOCInfoVO;
import com.genesis.apps.databinding.ActivityRelapseReqResultBinding;
import com.genesis.apps.ui.common.activity.SubActivity;

import java.util.Objects;

public class ServiceRelapseReqResultActivity extends SubActivity<ActivityRelapseReqResultBinding> {
    private static final String TAG = ServiceRelapseReqResultActivity.class.getSimpleName();

    public VOCInfoVO vocInfoVO;
    public String status;
    public String address;
    public String phoneNo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relapse_req_result);

        getDataFromIntent();//데이터 제대로 안 들어있으면 액티비티 종료처리까지 함
        extractData();

        initView();//getDataFromIntent()가 성공해야 실행가능

        ui.setActivity(this);
    }

    //서버에서 이상하게 집어던진 값들 가공
    private void extractData() {

        //접수상태 코드
        status = vocInfoVO.getInpSt();
        if (Objects.equals(status, VOCInfoVO.INP_ST_CODE_WAITING)) {
            status = getString(R.string.relapse_req_result_status_waiting);
        } else if (Objects.equals(status, VOCInfoVO.INP_ST_CODE_FINISH)) {
            status = getString(R.string.relapse_req_result_status_req_complete);
        }

        //고객 주소
        address = (String) TextUtils.concat(
                vocInfoVO.getRdwNmZip(), " ",//우편번호
                vocInfoVO.getRdwNmAdr(), "\n",      //도로명주소
                vocInfoVO.getRdwNmDtlAdr()          //상세주소
        );

        //폰번 제정신인가...
        phoneNo = (String) TextUtils.concat(
                vocInfoVO.getRegnTn(),   //010
                vocInfoVO.getFrtDgtTn(), "-",   //1234
                vocInfoVO.getRealDgtTn(), "-"   //5678
        );
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    public void onClickCommon(View v) {
        switch (v.getId()) {
            //확인버튼
            case R.id.tv_titlebar_text_btn:
                //tood impl 종료처리
                break;

            default:
                //do nothing
                break;
        }
    }

    @Override
    public void setViewModel() {
        //do nothing
    }

    @Override
    public void setObserver() {
        //do nothing
    }

    //인텐트 까서 데이터를 뷰에 뿌림. 실패하면 액티비티 종료(뷰에 데이터 없어서 화면 다 깨짐)
    @Override
    public void getDataFromIntent() {
        Log.d(TAG, "getDataFromIntent: ");

        try {
            //todo get
//            vocInfoVO =
        } catch (NullPointerException e) {
            e.printStackTrace();
            exitPage("인텐트 데이터 이상", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initView() {

    }

}
