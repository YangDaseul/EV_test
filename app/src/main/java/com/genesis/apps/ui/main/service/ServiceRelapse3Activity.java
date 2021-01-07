package com.genesis.apps.ui.main.service;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.genesis.apps.comm.model.api.gra.VOC_1002;
import com.genesis.apps.comm.model.api.gra.VOC_1004;
import com.genesis.apps.comm.model.api.gra.VOC_1005;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.TermVO;
import com.genesis.apps.comm.model.vo.VOCInfoVO;
import com.genesis.apps.comm.util.InteractionUtil;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.util.SoftKeyboardUtil;
import com.genesis.apps.comm.viewmodel.VOCViewModel;
import com.genesis.apps.databinding.ActivityServiceRelapseApply3Binding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.bottom.BottomDialogAskAgreeTerms;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;
import com.genesis.apps.ui.common.fragment.SubFragment;
import com.genesis.apps.ui.main.ServiceTermDetailActivity;
import com.genesis.apps.ui.main.service.ServiceRelapse3Adapter.RepairData;

import java.util.List;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ServiceRelapse3Activity extends SubActivity<ActivityServiceRelapseApply3Binding> {
    private static final String TAG = ServiceRelapse3Activity.class.getSimpleName();

    private static final int STATE_INIT = 0;
    private static final int STATE_OPEN_DEFECT_HISTORY = 1;
    private static final int STATE_ASK_OVER_4 = 2;
    private static final int STATE_ASK_PERIOD = 3;

    private VOCViewModel viewModel;
    private VOCInfoVO vocInfoVO;
    private ServiceRelapse3Adapter adapter;
    private BottomDialogAskAgreeTerms termsDialog;

    public boolean over4;
    private String count;
    private String period;

    private int currentState = STATE_INIT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_relapse_apply_3);
        setResizeScreen();

        getDataFromIntent();
        setViewModel();
        setObserver();
        setAdapter();

        ui.setActivity(this);
    }

    @Override
    public void onClickCommon(View v) {
        Log.d(TAG, "onClickCommon: " + v.getId());

        switch (v.getId()) {
            //수리 횟수 추가
            case R.id.tv_relapse_3_repair_add_btn:
                onClickAddRepairHistory();
                break;

            //4회 이상? 예/아니오 버튼
            case R.id.tv_relapse_3_yes:
                selectOver4(true);
                break;
            case R.id.tv_relapse_3_no:
                selectOver4(false);
                break;

            //약관 내용 보기 버튼
            case R.id.iv_arrow:
                showTerm(v);
                break;

            //다음 버튼
            case R.id.tv_relapse_3_next_btn:
//                //todo 테스트 빨리 진입하려고 앞단계 다 건너뛰고 호출하도록 함
//                showTermsDialog(Arrays.asList(new TermVO[]{
//                        new TermVO(
//                                "",
//                                "2000",
//                                "약관 1호",
//                                "ㅁㄴㅇㄹ",
//                                "Y"
//                        ),
//
//                        new TermVO(
//                                "",
//                                "3000",
//                                "약관 2호",
//                                "ㅁㄴㅇㄹ",
//                                "Y")
//                }));
                onClickNextBtn();
                break;

            default:
                //do nothing
                break;
        }
    }

    @Override
    public void onBackPressed() {
        dialogExit();
    }

    @Override
    public void onBackButton() {
        dialogExit();
    }

    private void dialogExit() {
        List<SubFragment> fragments = getFragments();
        if (fragments != null && fragments.size() > 0) {
            hideFragment(fragments.get(0));
        } else {
            finish();
            closeTransition();
        }
    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        viewModel = new ViewModelProvider(this).get(VOCViewModel.class);
    }

    @Override
    public void setObserver() {
        Log.d(TAG, "setObserver: ");

        //신청
        viewModel.getRES_VOC_1002().observe(this, result -> {
            Log.d(TAG, "VOC 1002 (req) obs: " + result.status);

            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;

                case SUCCESS:
                    if (result.data != null && result.data.getRtCd() != null) {
                        if (result.data.getRtCd().equals(BaseResponse.RETURN_CODE_SUCC)) {
                            exitPage(getString(R.string.relapse_succ), ResultCodes.REQ_CODE_NORMAL.getCode());
                        }

                        showProgressDialog(false);
                        break;
                    }
                    //not break; 데이터 이상하면 default로 진입시킴

                default:
                    showProgressDialog(false);
                    String serverMsg = "";
                    try {
                        serverMsg = result.data.getRtMsg();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (TextUtils.isEmpty(serverMsg)) {
                            serverMsg = getString(R.string.instability_network);
                        }
                        SnackBarUtil.show(this, serverMsg);
                    }
                    break;
            }
        });

        //약관 목록 조회
        viewModel.getRES_VOC_1004().observe(this, result -> {
            Log.d(TAG, "VOC 1004(terms list) obs: " + result.status);

            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;

                case SUCCESS:
                    if (result.data != null && result.data.getTermList() != null) {

                        //약관 동의 대화상자
                        showTermsDialog(result.data.getTermList());

                        showProgressDialog(false);
                        break;
                    }
                    //not break; 데이터 이상하면 default로 진입시킴

                default:
                    showProgressDialog(false);
                    String serverMsg = "";
                    try {
                        serverMsg = result.data.getRtMsg();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (TextUtils.isEmpty(serverMsg)) {
                            serverMsg = getString(R.string.instability_network);
                        }
                        SnackBarUtil.show(this, serverMsg);
                    }
                    break;
            }
        });


        //약관 상세 조회
        viewModel.getRES_VOC_1005().observe(this, result -> {
            Log.d(TAG, "VOC 1005(term detail) obs: " + result.status);

            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;

                case SUCCESS:
                    if (result.data != null && result.data.getTermVO() != null) {
                        Intent intent = new Intent(this, ServiceTermDetailActivity.class)
                                .putExtra(VariableType.KEY_NAME_TERM_VO, result.data.getTermVO())
                                .putExtra(KeyNames.KEY_NAME_MAP_SEARCH_TITLE_ID, result.data.getTermVO().getTermNm());

                        startActivitySingleTop(
                                intent,
                                RequestCodes.REQ_CODE_ACTIVITY.getCode(),
                                VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);

                        showProgressDialog(false);
                        break;
                    }
                    //not break; 데이터 이상하면 default로 진입시킴

                default:
                    showProgressDialog(false);
                    String serverMsg = "";
                    try {
                        serverMsg = result.data.getRtMsg();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (TextUtils.isEmpty(serverMsg)) {
                            serverMsg = getString(R.string.instability_network);
                        }
                        SnackBarUtil.show(this, serverMsg);
                    }
                    break;
            }
        });

    }

    @Override
    public void getDataFromIntent() {
        try {
            vocInfoVO = (VOCInfoVO) getIntent().getSerializableExtra(KeyNames.KEY_NAME_SERVICE_VOC_INFO_VO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setAdapter() {
        adapter = new ServiceRelapse3Adapter(this);
        ui.rvRelapse3RepairHistoryList.setLayoutManager(new LinearLayoutManager(this));
        ui.rvRelapse3RepairHistoryList.setHasFixedSize(true);
        ui.rvRelapse3RepairHistoryList.setAdapter(adapter);
    }

    //수리 내역 추가 버튼
    private void onClickAddRepairHistory() {
        //입력 창 추가
        adapter.addRow();
    }

    private void onClickNextBtn() {
        Log.d(TAG, "onClickNextBtn: ");

        switch (currentState) {
            case STATE_OPEN_DEFECT_HISTORY:
                SoftKeyboardUtil.hideKeyboard(this, getWindow().getDecorView().getWindowToken());
                if (adapter.validateInputData()) {
                    changeStatusToAskOver4();
                }
                break;

            case STATE_ASK_OVER_4:
                if (adapter.validateInputData() &&
                        //↓ "4회이상?" 질문에 아니오 눌렀거나 예 누르고 값을 넣어야 통과
                        // 근데 기껏 값을 받아놓고 api에 송신하는 정보는 Y/N임 ㅡㅡ;; 사용자가 적은 숫자가 몇인지는 무시(2020.11.19)
                        (!over4 || !TextUtils.isEmpty(ui.etRelapse3TotalCount.getText().toString()))) {
                    changeStatusToAskPeriod();
                }
                break;

            case STATE_ASK_PERIOD:
                if (adapter.validateInputData()) {
                    period = ui.etRelapse3Period.getText().toString();
                    if (TextUtils.isEmpty(period)) {
                        return;
                    }

                    SoftKeyboardUtil.hideKeyboard(this, getWindow().getDecorView().getWindowToken());

                    reqTermsOfService();
                }


            default:
                //do nothing
                break;
        }
    }

    //수리내역 입력 창 해금
    public void changeStatusToDefectHistory() {
        //여기로 오는 트리거는 단계 변경 후에도 원래 기능을 유지하므로
        // 상태 변경 기능 실행 전에 한 번 더 검사 (해당 기능 최초 실행이 상태 변경 트리거)
        if (currentState == STATE_INIT) {
            ui.tvRelapse3Desc.setText(R.string.relapse_3_msg_02);
            ui.tvRelapse3NextBtn.setVisibility(View.VISIBLE);

            //단계 변동을 저장
            currentState = STATE_OPEN_DEFECT_HISTORY;
        }
    }

    //4번 넘었나요?
    private void changeStatusToAskOver4() {
        currentState = STATE_ASK_OVER_4;
        ui.tvRelapse3Desc.setText(R.string.relapse_3_msg_03);

        InteractionUtil.expand(ui.lRelapse3YesNoContainer, null);
        ui.ivDummy.setVisibility(View.VISIBLE);
    }

    //4번넘었나요? : y/n 선택함
    private void selectOver4(boolean over) {
        if (over4 == over) {
            return;
        }

        over4 = over;
        ui.setActivity(this);

        if (over) {
            InteractionUtil.expand(ui.lRelapse3TotalCountContainer, null);
        } else {
            InteractionUtil.collapse(ui.lRelapse3TotalCountContainer, null);
        }
    }

    //누적 수리 기간 묻기
    private void changeStatusToAskPeriod() {
        currentState = STATE_ASK_PERIOD;
        ui.tvRelapse3Desc.setText(R.string.relapse_3_msg_04);
        ui.tvRelapse3NextBtn.setText(R.string.relapse_3_req_btn_text);
        ui.etRelapse3Period.setOnFocusChangeListener(focusChangeListener);

        //앞 단계에서 4회 이상이라고 했으면 값 저장
        if (over4) {
            count = ui.etRelapse3TotalCount.getText().toString();
            count = "" + (Integer.parseInt(count) + ServiceRelapse3Adapter.REPAIR_HISTORY_MAX_SIZE - 1);//세부사항 입력한 3건도 더해서 셈 -1은 마지막 게 ui 더미 칸이니까
        }
        //아니면 입력 칸 3개 중에 몇 개나 채웠나 셈
        else {
            count = "" + (adapter.getItemCount() - 1);//-1은 마지막 게 ui 더미 칸이니까
        }

        InteractionUtil.collapse(ui.lRelapse3YesNoContainer, null);
        InteractionUtil.collapse(ui.lRelapse3TotalCountContainer, null);

        InteractionUtil.expand(ui.lRelapse3PeriodContainer, null);

        new Handler().postDelayed(() -> {
                ui.etRelapse3Period.requestFocus();
        }, 200);
    }

    EditText.OnFocusChangeListener focusChangeListener = (view, hasFocus) -> {
        if (hasFocus) {
            SoftKeyboardUtil.showKeyboard(this);
        }
    };

    //필요한 약관 정보 요청
    private void reqTermsOfService() {
        viewModel.reqVOC1004(new VOC_1004.Request(APPIAInfo.SM_FLAW06_P02.getId()));
    }

    //약관 동의 대화상자 호출
    private void showTermsDialog(List<TermVO> termList) {
        Log.d(TAG, "showTermsDialog: " + termsDialog);

        termsDialog = new BottomDialogAskAgreeTerms(
                this,
                R.style.BottomSheetDialogTheme,
                onSingleClickListener);

        termsDialog.setOnDismissListener(dialog -> {
            if (termsDialog.isInputConfirmed()) {
                reqVOC1002();
            }
        });

        termsDialog.init(termList);
        termsDialog.show();
    }

    //약관 보기
    private void showTerm(View v) {
        TermVO termVO = (TermVO) v.getTag(R.id.tag_term_vo);
        Log.d(TAG, "showTerm: " + termVO);

        if (termVO != null) {
            viewModel.reqVOC1005(new VOC_1005.Request(APPIAInfo.SM_FLAW06.getId(), termVO.getTermCd(), termVO.getTermVer()));
        }
    }

    private void reqVOC1002() {
        VOC_1002.Request param = new VOC_1002.Request(APPIAInfo.SM_FLAW06.getId());

        // 앞 단계에서 가져온 거 + 어댑터가 들고있는 거 + over4 + count + period
        param.setCsmrNm(vocInfoVO.getCsmrNm());
        param.setCsmrTymd(vocInfoVO.getCsmrTymd());
        param.setEmlAdr(vocInfoVO.getEmlAdr());
        param.setRdwNmZip(vocInfoVO.getRdwNmZip());
        param.setRdwNmAdr(vocInfoVO.getRdwNmAdr());
        param.setRdwNmDtlAdr(vocInfoVO.getRdwNmDtlAdr());
        param.setRegnTn(vocInfoVO.getRegnTn());
        param.setFrtDgtTn(vocInfoVO.getFrtDgtTn());
        param.setRealDgtTn(vocInfoVO.getRealDgtTn());
        param.setRecvDt(vocInfoVO.getRecvDt());
        param.setCarNm(vocInfoVO.getCarNm());
        param.setCrnVehlCd(vocInfoVO.getCrnVehlCd());
        param.setMdYyyy(vocInfoVO.getMdYyyy());
        param.setTrvgDist(vocInfoVO.getTrvgDist());
        param.setCarNo(vocInfoVO.getCarNo());
        param.setVin(vocInfoVO.getVin());
        param.setWpa(vocInfoVO.getWpa());
        param.setAdmz(vocInfoVO.getAdmz());
        param.setFlawCd(vocInfoVO.getFlawCd());

        List<RepairData> repairData = adapter.getItems();

        for (RepairData data : repairData) {
            if (data != null) {
                setRepairData(param, data);
            }
        }

        param.setWkCntFth(over4 ? VariableType.COMMON_MEANS_YES : VariableType.COMMON_MEANS_NO);
        param.setWkCnt(count);
        param.setWkPeriod("" + period);
        param.setPrnInfoAgreeFlg(VariableType.COMMON_MEANS_YES);
        param.setCarMgmtAgreeFlg(VariableType.COMMON_MEANS_YES);

        viewModel.reqVOC1002(param);
    }

    private void setRepairData(VOC_1002.Request param, RepairData data) {

        switch (data.getTurn()) {
            case 1:
                param.setWkr1Nm(data.getMechanic());
                param.setWk1StrtDt(data.getReqDate());
                param.setWk1Dt(data.getFinishDate());
                param.setWk1Caus(data.getDefectDetail());
                param.setWk1Dtl(data.getRepairDetail());
                break;
            case 2:
                param.setWkr2Nm(data.getMechanic());
                param.setWk2StrtDt(data.getReqDate());
                param.setWk2Dt(data.getFinishDate());
                param.setWk2Caus(data.getDefectDetail());
                param.setWk2Dtl(data.getRepairDetail());
                break;
            case 3:
                param.setWkr3Nm(data.getMechanic());
                param.setWk3StrtDt(data.getReqDate());
                param.setWk3Dt(data.getFinishDate());
                param.setWk3Caus(data.getDefectDetail());
                param.setWk3Dtl(data.getRepairDetail());
                break;

            default:
                //do nothing
                break;
        }
    }
}
