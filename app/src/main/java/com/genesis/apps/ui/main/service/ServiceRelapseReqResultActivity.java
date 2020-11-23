package com.genesis.apps.ui.main.service;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.vo.VOCInfoVO;
import com.genesis.apps.comm.util.DateUtil;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.databinding.ActivityRelapseReqResultBinding;
import com.genesis.apps.ui.common.activity.SubActivity;

import java.util.Date;
import java.util.Objects;

public class ServiceRelapseReqResultActivity extends SubActivity<ActivityRelapseReqResultBinding> {
    private static final String TAG = ServiceRelapseReqResultActivity.class.getSimpleName();

    public VOCInfoVO vocInfoVO;
    public String reqDate;
    public String status;
    public String address;
    public String phoneNo;
    public String birthday;
    public String carInfo;
    public String carReceiveDate;
    public String carRegistDate;
    public String carDistance;
    public String defectLevel;
    public String tryCount;
    public String tryPeriod;
    public DefectHistoryData[] history;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relapse_req_result);

        getDataFromIntent();//데이터 제대로 안 들어있으면 액티비티 종료처리까지 함
        extractData();

        ui.setActivity(this);
    }

    //서버에서 이상하게 집어던진 값들 가공
    private void extractData() {
        //접수일
        reqDate = parseDate(vocInfoVO.getInpDate());

        //접수상태 코드
        status = vocInfoVO.getInpSt();
        if (Objects.equals(status, VOCInfoVO.INP_ST_CODE_WAITING)) {
            status = getString(R.string.relapse_req_result_status_waiting);
        } else if (Objects.equals(status, VOCInfoVO.INP_ST_CODE_FINISH)) {
            status = getString(R.string.relapse_req_result_status_req_complete);
        }

        //고객 주소
        address = (String) TextUtils.concat(
                "(", vocInfoVO.getRdwNmZip(), ") ",//우편번호
                vocInfoVO.getRdwNmAdr(), "\n",      //도로명주소
                vocInfoVO.getRdwNmDtlAdr()          //상세주소
        );

        //폰번 제정신인가...
        phoneNo = (String) TextUtils.concat(
                vocInfoVO.getRegnTn(), "-", //010
                vocInfoVO.getFrtDgtTn(), "-",      //1234
                vocInfoVO.getRealDgtTn()           //5678
        );

        //생년월일
        birthday = parseDate(vocInfoVO.getCsmrTymd());

        //차명 : todo 이거 맞나?
        carInfo = vocInfoVO.getCrnVehlCd() + " " + vocInfoVO.getCarNm();

        //인도날짜
        carReceiveDate = parseDate(vocInfoVO.getRecvDt());

        //등록연월일
        carRegistDate = parseDate(vocInfoVO.getMdYyyy());

        //주행거리
        carDistance = StringUtil.getDigitGroupingString(vocInfoVO.getTrvgDist()) + " km";

        //하자 구분
        defectLevel = vocInfoVO.getFlawCd();
        if (Objects.equals(defectLevel, VOCInfoVO.DEFECT_LEVEL_HIGH)) {
            defectLevel = getString(R.string.relapse_req_result_defect_high);
        } else if (Objects.equals(defectLevel, VOCInfoVO.DEFECT_LEVEL_LOW)) {
            defectLevel = getString(R.string.relapse_req_result_defect_low);
        }

        extractHistory();

        //수리 시도 횟수, 누적 수리 기간
        tryCount = vocInfoVO.getWkCnt() + getString(R.string.relapse_3_repair_count);
        tryPeriod = vocInfoVO.getWkPeriod() + getString(R.string.relapse_3_repair_day);

    }

    private void extractHistory() {
        //서버가 배열로 안 줘서 포기함 몰라 그냥 하드코딩 박아OTL
        history = new DefectHistoryData[3];

        history[0] = new DefectHistoryData();
        if (!TextUtils.isEmpty(vocInfoVO.getWkr1Nm())) {
            history[0].visibility = View.VISIBLE;
            history[0].title = "1" + getString(R.string.relapse_req_result_repair_detail_title);
            history[0].mechanic = vocInfoVO.getWkr1Nm();
            history[0].repairReqDate = parseDate(vocInfoVO.getWk1StrtDt());
            history[0].repairFinishDate = parseDate(vocInfoVO.getWk1Dt());
            history[0].defectDetail = vocInfoVO.getWk1Caus();
            history[0].repairDetail = vocInfoVO.getWk1Dtl();
        }

        history[1] = new DefectHistoryData();
        if (!TextUtils.isEmpty(vocInfoVO.getWkr2Nm())) {
            history[1].visibility = View.VISIBLE;
            history[1].mechanic = vocInfoVO.getWkr2Nm();
            history[1].repairReqDate = parseDate(vocInfoVO.getWk2StrtDt());
            history[1].title = "2" + getString(R.string.relapse_req_result_repair_detail_title);
            history[1].repairFinishDate = parseDate(vocInfoVO.getWk2Dt());
            history[1].defectDetail = vocInfoVO.getWk2Caus();
            history[1].repairDetail = vocInfoVO.getWk2Dtl();
        }

        history[2] = new DefectHistoryData();
        if (!TextUtils.isEmpty(vocInfoVO.getWkr3Nm())) {
            history[2].visibility = View.VISIBLE;
            history[2].mechanic = vocInfoVO.getWkr3Nm();
            history[2].repairReqDate = parseDate(vocInfoVO.getWk3StrtDt());
            history[2].title = "3" + getString(R.string.relapse_req_result_repair_detail_title);
            history[2].repairFinishDate = parseDate(vocInfoVO.getWk3Dt());
            history[2].defectDetail = vocInfoVO.getWk3Caus();
            history[2].repairDetail = vocInfoVO.getWk3Dtl();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    public void onClickCommon(View v) {
        switch (v.getId()) {
            //확인버튼
            case R.id.tv_relapse_req_result_ok_btn:
                exitPage("", ResultCodes.REQ_CODE_NORMAL.getCode());
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
            vocInfoVO = (VOCInfoVO) getIntent().getSerializableExtra(KeyNames.KEY_NAME_SERVICE_VOC_INFO_VO);
        } catch (NullPointerException e) {
            e.printStackTrace();
            exitPage("인텐트 데이터 이상", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private String parseDate(String dateOriginal) {
        Date date = DateUtil.getDefaultDateFormat(dateOriginal, DateUtil.DATE_FORMAT_yyyyMMdd);
        return DateUtil.getDate(date, DateUtil.DATE_FORMAT_yyyy_mm_dd_dot);
    }

    public class DefectHistoryData {
        public int visibility = View.GONE;
        public String title = "";
        public String mechanic = "";
        public String repairReqDate = "";
        public String repairFinishDate = "";
        public String defectDetail = "";
        public String repairDetail = "";
    }
}
