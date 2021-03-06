package com.genesis.apps.ui.main.service;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.airbnb.paris.Paris;
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
import com.genesis.apps.ui.common.fragment.SubFragment;
import com.genesis.apps.ui.main.ServiceTermDetailActivity;
import com.genesis.apps.ui.main.service.ServiceRelapse3Adapter.RepairData;

import java.util.List;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

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

    public boolean over4 = false;
    private String count;
    private String period;
    private int currentState = STATE_INIT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_relapse_apply_3);
        setResizeScreen();

        initView();
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
            //?????? ?????? ??????
            case R.id.tv_relapse_3_repair_add_btn:
                onClickAddRepairHistory();
                break;

            //4??? ??????? ???/????????? ??????
            case R.id.tv_relapse_3_yes:
                selectOver4(true);
                break;
            case R.id.tv_relapse_3_no:
                selectOver4(false);
                break;

            //?????? ?????? ?????? ??????
            case R.id.iv_arrow:
                showTerm(v);
                break;

            //?????? ??????
            case R.id.tv_relapse_3_next_btn:
//                //todo ????????? ?????? ??????????????? ????????? ??? ???????????? ??????????????? ???
//                showTermsDialog(Arrays.asList(new TermVO[]{
//                        new TermVO(
//                                "",
//                                "2000",
//                                "?????? 1???",
//                                "????????????",
//                                "Y"
//                        ),
//
//                        new TermVO(
//                                "",
//                                "3000",
//                                "?????? 2???",
//                                "????????????",
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

        //??????
        viewModel.getRES_VOC_1002().observe(this, result -> {
            Log.d(TAG, "VOC 1002 (req) obs: " + result.status);

            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;

                case SUCCESS:
                    if (result.data != null && result.data.getRtCd() != null) {
                        if (result.data.getRtCd().equals(BaseResponse.RETURN_CODE_SUCC)) {
                            exitPage(getString(R.string.relapse_succ), ResultCodes.REQ_CODE_APPLY_RELAPSE.getCode());
                        }

                        showProgressDialog(false);
                        break;
                    }
                    //not break; ????????? ???????????? default??? ????????????

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

        //?????? ?????? ??????
        viewModel.getRES_VOC_1004().observe(this, result -> {
            Log.d(TAG, "VOC 1004(terms list) obs: " + result.status);

            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;

                case SUCCESS:
                    if (result.data != null && result.data.getTermList() != null) {

                        //?????? ?????? ????????????
                        showTermsDialog(result.data.getTermList());

                        showProgressDialog(false);
                        break;
                    }
                    //not break; ????????? ???????????? default??? ????????????

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


        //?????? ?????? ??????
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
                    //not break; ????????? ???????????? default??? ????????????

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

    private void initView() {
        ui.etRelapse3TotalCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(TextUtils.isEmpty(editable) || Integer.parseInt(editable.toString()) < 4) {
                    ui.lRelapse3TotalCount.setError(getString(R.string.relapse_0));
                } else {
                    ui.lRelapse3TotalCount.setError(null);
                }
            }
        });
    }

    private void setAdapter() {
        adapter = new ServiceRelapse3Adapter(this);
        ui.rvRelapse3RepairHistoryList.setLayoutManager(new LinearLayoutManager(this));
        ui.rvRelapse3RepairHistoryList.setHasFixedSize(true);
        ui.rvRelapse3RepairHistoryList.setAdapter(adapter);
    }

    //?????? ?????? ?????? ??????
    private void onClickAddRepairHistory() {
        //?????? ??? ??????
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
                if (adapter.validateInputData()) {
                    if(over4) {
                        if(!TextUtils.isEmpty(ui.etRelapse3TotalCount.getText().toString()) && Integer.parseInt(ui.etRelapse3TotalCount.getText().toString()) > 3) {
                            ui.lRelapse3TotalCount.setError(null);
                        } else {
                            ui.lRelapse3TotalCount.setError(getString(R.string.relapse_0));

                            return;
                        }
                    }

                    changeStatusToAskPeriod();
                } else {
                    if(TextUtils.isEmpty(ui.etRelapse3TotalCount.getText().toString()) || Integer.parseInt(ui.etRelapse3TotalCount.getText().toString()) < 4) ui.lRelapse3TotalCount.setError(getString(R.string.relapse_0));
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

    //???????????? ?????? ??? ??????
    public void changeStatusToDefectHistory() {
        //????????? ?????? ???????????? ?????? ?????? ????????? ?????? ????????? ???????????????
        // ?????? ?????? ?????? ?????? ?????? ??? ??? ??? ?????? (?????? ?????? ?????? ????????? ?????? ?????? ?????????)
        if (currentState == STATE_INIT) {
            ui.tvRelapse3Desc.setText(R.string.relapse_3_msg_02);
            ui.tvRelapse3NextBtn.setVisibility(View.VISIBLE);

            //?????? ????????? ??????
            currentState = STATE_OPEN_DEFECT_HISTORY;
        }
    }

    //4??? ?????????????
    private void changeStatusToAskOver4() {
        currentState = STATE_ASK_OVER_4;
        ui.tvRelapse3Desc.setText(R.string.relapse_3_msg_07);
        ui.tvRelapse3TotalCountTitle.setVisibility(View.VISIBLE);
        ui.clRelapseBtnContainer.setVisibility(View.VISIBLE);
//        ui.tvRelapse3Yes.setVisibility(View.VISIBLE);
//        ui.tvRelapse3No.setVisibility(View.VISIBLE);
        ui.ivDummy.setVisibility(View.VISIBLE);
    }

    //4???????????????? : y/n ?????????
    private void selectOver4(boolean over) {
        setStyleOver(over);
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

    public void setStyleOver(boolean over) {
        Paris.style(ui.tvRelapse3Yes).apply(over ? R.style.BtrFilterEnable2 : R.style.BtrFilterDisable2);
        Paris.style(ui.tvRelapse3No).apply(over ? R.style.BtrFilterDisable2 : R.style.BtrFilterEnable2);
    }

    //?????? ?????? ?????? ??????
    private void changeStatusToAskPeriod() {
        currentState = STATE_ASK_PERIOD;
        ui.tvRelapse3Desc.setText(R.string.relapse_3_msg_04);
        ui.tvRelapse3NextBtn.setText(R.string.relapse_3_req_btn_text);
        ui.etRelapse3Period.setOnFocusChangeListener(focusChangeListener);

        //??? ???????????? 4??? ??????????????? ????????? ??? ??????
        if (over4) {
            count = ui.etRelapse3TotalCount.getText().toString();
            //?????? ????????? ????????? ?????????????????? 2021-01-13
//            count = "" + (Integer.parseInt(count) + ServiceRelapse3Adapter.REPAIR_HISTORY_MAX_SIZE - 1);//???????????? ????????? 3?????? ????????? ??? -1??? ????????? ??? ui ?????? ????????????
        }
        //????????? ?????? ??? 3??? ?????? ??? ?????? ????????? ???
        else {
            InteractionUtil.collapse(ui.lRelapse3TotalCountContainer, null);
            count = "" + (adapter.getItemCount() - 1);//-1??? ????????? ??? ui ?????? ????????????
        }
        ui.tvRelapse3TotalCountTitle.setVisibility(View.VISIBLE);
//        ui.tvRelapse3Yes.setVisibility(View.GONE);
//        ui.tvRelapse3No.setVisibility(View.GONE);
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

    //????????? ?????? ?????? ??????
    private void reqTermsOfService() {
        viewModel.reqVOC1004(new VOC_1004.Request(APPIAInfo.SM_FLAW06_P02.getId()));
    }

    //?????? ?????? ???????????? ??????
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

    //?????? ??????
    private void showTerm(View v) {
        TermVO termVO = (TermVO) v.getTag(R.id.tag_term_vo);
        Log.d(TAG, "showTerm: " + termVO);

        if (termVO != null) {
            viewModel.reqVOC1005(new VOC_1005.Request(APPIAInfo.SM_FLAW06.getId(), termVO.getTermCd(), termVO.getTermVer()));
        }
    }

    private void reqVOC1002() {
        VOC_1002.Request param = new VOC_1002.Request(APPIAInfo.SM_FLAW06.getId());

        // ??? ???????????? ????????? ??? + ???????????? ???????????? ??? + over4 + count + period
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
                param.setWk1StrtDt(data.getReqDate().replaceAll("\\.",""));
                param.setWk1Dt(data.getFinishDate().replaceAll("\\.",""));
                param.setWk1Caus(data.getDefectDetail());
                param.setWk1Dtl(data.getRepairDetail());
                break;
            case 2:
                param.setWkr2Nm(data.getMechanic());
                param.setWk2StrtDt(data.getReqDate().replaceAll("\\.",""));
                param.setWk2Dt(data.getFinishDate().replaceAll("\\.",""));
                param.setWk2Caus(data.getDefectDetail());
                param.setWk2Dtl(data.getRepairDetail());
                break;
            case 3:
                param.setWkr3Nm(data.getMechanic());
                param.setWk3StrtDt(data.getReqDate().replaceAll("\\.",""));
                param.setWk3Dt(data.getFinishDate().replaceAll("\\.",""));
                param.setWk3Caus(data.getDefectDetail());
                param.setWk3Dtl(data.getRepairDetail());
                break;

            default:
                //do nothing
                break;
        }
    }

    public void setFlawCd(String flawCd) {
        vocInfoVO.setFlawCd(flawCd);
    }
}
