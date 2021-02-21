package com.genesis.apps.ui.main.service;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.airbnb.paris.Paris;
import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.REQ_1016;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.RepairReserveVO;
import com.genesis.apps.comm.model.vo.SurveyItemVO;
import com.genesis.apps.comm.model.vo.SurveyVO;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.viewmodel.REQViewModel;
import com.genesis.apps.databinding.ActivityPrecheckBinding;
import com.genesis.apps.databinding.ItemMinorBinding;
import com.genesis.apps.databinding.ItemPrecheckBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.bottom.BottomCheckListDialog;
import com.genesis.apps.ui.common.dialog.bottom.BottomContentDialog;
import com.genesis.apps.ui.common.dialog.bottom.BottomListDialog;
import com.genesis.apps.ui.common.dialog.bottom.BottomTwoButtonDialog;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;

import java.util.ArrayList;
import java.util.List;

public class ServiceRepair2PreCheckActivity extends SubActivity<ActivityPrecheckBinding> {
    private static final String TAG= ServiceRepair2PreCheckActivity.class.getSimpleName();
    private REQViewModel reqViewModel;

    private RepairReserveVO repairReserveVO;
    private SurveyVO surveyVO;

    private List<String> surveyList;
    private List<String> selectedMinorList;
    private List<SurveyItemVO> majorList;
    private List<SurveyItemVO> middleList;
    private List<SurveyItemVO> minorList;

    private String mSvyDivCd; // L : 대분류, M : 중분류, S : 소분류
    private String mSvyMgmtNo;
    private String mSvyPrvsNo;
    private boolean isNext = false;

    private ItemPrecheckBinding mItemBinding;
    private BottomListDialog surveyListDialog;
    private BottomCheckListDialog surveyMinorListDialog;
    private BottomContentDialog contentDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_precheck);
        getDataFromIntent();
        setViewModel();
        setObserver();
        initView();
    }

    @Override
    public void onClickCommon(View v) {
        switch (v.getId()) {
            case R.id.tv_precheck_major:
                mSvyDivCd = "L";

                List<SurveyItemVO> majorItems = (List<SurveyItemVO>) mItemBinding.tvPrecheckMajor.getTag();
                if(majorItems!=null) {
                    surveyList = new ArrayList<>();
                    for (int i = 0; i < majorItems.size(); i++) {
                        surveyList.add(majorItems.get(i).getSvyPrvsNm());
                    }

                    showBottomDialog();
                }
                break;
            case R.id.tv_precheck_middle:
                mSvyDivCd = "M";

                List<SurveyItemVO> middleItems = (List<SurveyItemVO>) mItemBinding.tvPrecheckMiddle.getTag();
                if(middleItems!=null) {
                    surveyList = new ArrayList<>();
                    for (int i = 0; i < middleItems.size(); i++) {
                        surveyList.add(middleItems.get(i).getSvyPrvsNm());
                    }

                    showBottomDialog();
                }
                break;
            case R.id.ll_minor_contain:
            case R.id.tv_precheck_minor:
                mSvyDivCd = "S";

                selectedMinorList = new ArrayList<>();
                // 선택한 소분류
                for(int i=0; i<mItemBinding.llMinorContain.getChildCount(); i++) {
                    TextView tvMinor = (TextView) ((LinearLayout) mItemBinding.llMinorContain.getChildAt(i)).getChildAt(0);
                    selectedMinorList.add(tvMinor.getText().toString());
                }

                List<SurveyItemVO> minorItems = (List<SurveyItemVO>) mItemBinding.tvPrecheckMinor.getTag();
                if(minorItems!=null) {
                    surveyList = new ArrayList<>();
                    for (int i = 0; i < minorItems.size(); i++) {
                        surveyList.add(minorItems.get(i).getSvyPrvsNm());
                    }

                    showCheckBottomDialog();
                }
                break;
            case R.id.ll_delete:
                ui.llContain.removeViewAt(0);

                setSurveyEnable();

                break;
            case R.id.tv_content:
                // 상세 내용 입력 다이얼로그
                if(contentDialog == null) {
                    contentDialog = new BottomContentDialog(this, R.style.BottomSheetDialogTheme);
                    contentDialog.setTitle(getString(R.string.sm01_maintenance_28));
                    contentDialog.setOnDismissListener(
                            dialog -> {
                                if(checkValidRepair()) {
                                    if(!TextUtils.isEmpty(contentDialog.getContent())) {
                                        ui.tvContent.setTextAppearance(R.style.Precheck_Content_Selected);
                                        ui.tvContent.setBackgroundResource(R.drawable.ripple_bg_ffffff_stroke_000000);
                                        ui.tvContent.setText(contentDialog.getContent());
                                    } else {
                                        ui.tvContent.setTextAppearance(R.style.Precheck_Content_DeSelected);
                                        ui.tvContent.setBackgroundResource(R.drawable.ripple_bg_ffffff_stroke_e5e5e5);
                                        ui.tvContent.setText(getString(R.string.sm01_maintenance_27));
                                    }

                                    moveToNextPage();
                                }
                            }
                    );
                }

                contentDialog.show();

                break;
            case R.id.tv_service_precheck_next_btn:
                if(checkValidRepair()) {
                    // isNext = TURE 일때, 데이터 조합
                    if(isNext) {
                        moveToNextPage();

                        return;
                    }

                    if(ui.llContain.getChildCount() > 2) {
                        isNext = true;

                        ui.tvPrecheckContent.setText(getString(R.string.sm01_maintenance_26));
                        ui.tvContent.setVisibility(View.VISIBLE);

                        mItemBinding.llTitle.setVisibility(View.VISIBLE);
                        mItemBinding.tvTitle.setText(getString(R.string.sm01_maintenance_25) + " " + ui.llContain.getChildCount());

                        setSurveyEnable();

                        return;
                    }

                    BottomTwoButtonDialog dialog = new BottomTwoButtonDialog(this, R.style.BottomSheetDialogTheme);
                    dialog.setTitle(getString(R.string.sm01_maintenance_24));
                    dialog.setButtonAction(() -> {
                        // "예" 버튼 선택 시
                        mItemBinding.llTitle.setVisibility(View.VISIBLE);
                        mItemBinding.tvTitle.setText(getString(R.string.sm01_maintenance_25) + " " + ui.llContain.getChildCount());

                        for(int i=0; i<ui.llContain.getChildCount(); i++) {
                            ItemPrecheckBinding binding = DataBindingUtil.bind(ui.llContain.getChildAt(i));
                            binding.llDelete.setVisibility(View.GONE);
                            binding.llMinorContain.setEnabled(false);
                            binding.tvPrecheckMinor.setEnabled(false);
                            binding.tvPrecheckMiddle.setEnabled(false);
                            binding.tvPrecheckMajor.setEnabled(false);
                        }

                        initView();

                        dialog.dismiss();
                    }, () -> {
                        // "아니오" 버튼 선택 시
                        isNext = true;

                        ui.tvPrecheckContent.setText(getString(R.string.sm01_maintenance_26));
                        ui.tvContent.setVisibility(View.VISIBLE);

                        mItemBinding.llTitle.setVisibility(View.VISIBLE);
                        mItemBinding.tvTitle.setText(getString(R.string.sm01_maintenance_25) + " " + ui.llContain.getChildCount());

                        setSurveyEnable();

                        dialog.dismiss();
                    });

                    dialog.show();
                }

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
        MiddleDialog.dialogPrecheckBack(this, () -> {
            finish();
            closeTransition();
        }, () -> {

        });
    }

    private void initView() {
        mSvyDivCd = "L";
        mSvyMgmtNo = "";
        mSvyPrvsNo = "";

        mItemBinding = DataBindingUtil.inflate(LayoutInflater.from(getApplicationContext()), R.layout.item_precheck, null, false);
        mItemBinding.setActivity(this);

        ui.llContain.addView(mItemBinding.getRoot(), 0);

        getSurveyList(mSvyDivCd, mSvyMgmtNo, mSvyPrvsNo);
    }

    private void getSurveyList(String svyDivCd, String svyMgmtNo, String svyPrvsNo) {
        reqViewModel.reqREQ1016(new REQ_1016.Request(APPIAInfo.SM_SNFIND01_P01.getId(), svyDivCd, svyMgmtNo, svyPrvsNo));
    }

    private boolean checkValidRepair() {
        if(mItemBinding.tvPrecheckMajorTitle.getVisibility() != View.VISIBLE) {
            Paris.style(mItemBinding.tvPrecheckMajor).apply(R.style.CommonInputItemError);
            mItemBinding.tvMajorErrorRepair.setVisibility(View.VISIBLE);

            return false;
        } else if(mItemBinding.tvPrecheckMiddleTitle.getVisibility() != View.VISIBLE) {
            Paris.style(mItemBinding.tvPrecheckMiddle).apply(R.style.CommonInputItemError);
            mItemBinding.tvMiddleErrorRepair.setVisibility(View.VISIBLE);

            return false;
        } else if(mItemBinding.tvPrecheckMinorTitle.getVisibility() != View.VISIBLE) {
            Paris.style(mItemBinding.tvPrecheckMinor).apply(R.style.CommonInputItemError);
            mItemBinding.tvMinorErrorRepair.setVisibility(View.VISIBLE);

            return false;
        }

        return true;
    }

    private void moveToNextPage() {
        surveyVO = new SurveyVO();
        surveyVO.setSvyInpSbc(contentDialog == null ? "" : contentDialog.getContent());

        List<SurveyItemVO> tempItems = new ArrayList<>();
        int position = 1;
        for(int i=ui.llContain.getChildCount(); i>0; i--) {
            int index = i - 1;
            ItemPrecheckBinding binding = DataBindingUtil.bind(ui.llContain.getChildAt(index));
            String majorNm = binding.tvPrecheckMajor.getText().toString();
            String middleNm = binding.tvPrecheckMiddle.getText().toString();

            List<SurveyItemVO> majorItems = (List<SurveyItemVO>) binding.tvPrecheckMajor.getTag();
            for(SurveyItemVO item: majorItems) {
                if(majorNm.equals(item.getSvyPrvsNm())) {
                    item.setSvySetNo(String.valueOf(position));
                    tempItems.add(item);
                }
            }

            List<SurveyItemVO> middleItems = (List<SurveyItemVO>) binding.tvPrecheckMiddle.getTag();
            for(SurveyItemVO item: middleItems) {
                if(middleNm.equals(item.getSvyPrvsNm())) {
                    item.setSvySetNo(String.valueOf(position));
                    tempItems.add(item);
                }
            }

            List<SurveyItemVO> minorItems = (List<SurveyItemVO>) binding.tvPrecheckMinor.getTag();
            for(SurveyItemVO item: minorItems) {
                for(int j=0; j<binding.llMinorContain.getChildCount(); j++) {
                    TextView tvMinor = (TextView) ((LinearLayout) binding.llMinorContain.getChildAt(j)).getChildAt(0);
                    if(tvMinor.getText().toString().equals(item.getSvyPrvsNm())) {
                        item.setSvySetNo(String.valueOf(position));
                        tempItems.add(item);
                    }
                }
            }

            if(index == 0) {
                surveyVO.setSvyMgmtNo(majorItems.get(0).getSvyMgmtNo());
            }
            position++;
        }

        surveyVO.setSvyPrvsList(tempItems);
        Log.d(TAG, "Json : " + surveyVO.toString());
        repairReserveVO.setSvyInfo(surveyVO);
        startActivitySingleTop(new Intent(this
                        , ServiceRepair3CheckActivity.class)
                        .putExtra(KeyNames.KEY_NAME_SERVICE_RESERVE_INFO, repairReserveVO)
                , RequestCodes.REQ_CODE_ACTIVITY.getCode()
                , VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
    }

    private void setSurveyEnable() {
        if(ui.llContain.getChildCount() == 1) {
            ItemPrecheckBinding binding = DataBindingUtil.bind(ui.llContain.getChildAt(0));
            mItemBinding = binding;

            binding.llDelete.setVisibility(View.GONE);

            binding.llMinorContain.setEnabled(true);
            binding.tvPrecheckMinor.setEnabled(true);
            binding.tvPrecheckMiddle.setEnabled(true);
            binding.tvPrecheckMajor.setEnabled(true);
        } else {
            for(int i=0; i<ui.llContain.getChildCount(); i++) {
                ItemPrecheckBinding binding = DataBindingUtil.bind(ui.llContain.getChildAt(i));
                if(i == 0) {
                    binding.llDelete.setVisibility(View.VISIBLE);
                    mItemBinding = binding;

                    binding.llMinorContain.setEnabled(true);
                    binding.tvPrecheckMinor.setEnabled(true);
                    binding.tvPrecheckMiddle.setEnabled(true);
                    binding.tvPrecheckMajor.setEnabled(true);
                } else {
                    binding.llDelete.setVisibility(View.GONE);

                    binding.llMinorContain.setEnabled(false);
                    binding.tvPrecheckMinor.setEnabled(false);
                    binding.tvPrecheckMiddle.setEnabled(false);
                    binding.tvPrecheckMajor.setEnabled(false);
                }
            }
        }
    }

    private void showBottomDialog() {
        surveyListDialog = new BottomListDialog(this, R.style.BottomSheetDialogTheme);
        surveyListDialog.setTitle(getString(R.string.sm01_maintenance_23));
        surveyListDialog.setDatas(surveyList);
        surveyListDialog.setOnDismissListener(
                dialog -> {
                    if (surveyListDialog.getSelectItem() != null) {
                        if("L".equals(mSvyDivCd)) {
                            mItemBinding.tvMajorErrorRepair.setVisibility(View.GONE);
                            mItemBinding.tvPrecheckMajorTitle.setVisibility(View.VISIBLE);
                            mItemBinding.tvPrecheckMajor.setText(surveyListDialog.getSelectItem());
                            mItemBinding.tvPrecheckMajor.setTextAppearance(R.style.Precheck_SelectBox_Selected);
                            mItemBinding.tvPrecheckMajor.setBackgroundResource(R.drawable.ripple_bg_ffffff_stroke_000000);

                            mItemBinding.tvPrecheckMiddleTitle.setVisibility(View.GONE);
                            mItemBinding.tvPrecheckMiddle.setVisibility(View.VISIBLE);
                            mItemBinding.tvPrecheckMiddle.setTextAppearance(R.style.Precheck_SelectBox_DeSelected);
                            mItemBinding.tvPrecheckMiddle.setBackgroundResource(R.drawable.ripple_bg_ffffff_stroke_e5e5e5);
                            mItemBinding.tvPrecheckMiddle.setText(getString(R.string.sm01_maintenance_20));

                            mItemBinding.tvPrecheckMinorTitle.setVisibility(View.GONE);
                            mItemBinding.llMinorContain.setVisibility(View.GONE);
                            mItemBinding.tvPrecheckMinor.setVisibility(View.GONE);
                            mItemBinding.tvPrecheckMinor.setTextAppearance(R.style.Precheck_SelectBox_DeSelected);
                            mItemBinding.tvPrecheckMinor.setBackgroundResource(R.drawable.ripple_bg_ffffff_stroke_e5e5e5);
                            mItemBinding.tvPrecheckMinor.setText(getString(R.string.sm01_maintenance_22));

                            mSvyDivCd = "M";
                            for(int i=0; i<majorList.size(); i++) {
                                SurveyItemVO item = majorList.get(i);
                                if(surveyListDialog.getSelectItem().equals(item.getSvyPrvsNm())) {
                                    mSvyMgmtNo = item.getSvyMgmtNo();
                                    mSvyPrvsNo = item.getSvyPrvsNo();

                                    break;
                                }
                            }

                            getSurveyList(mSvyDivCd, mSvyMgmtNo, mSvyPrvsNo);
                        } else if("M".equals(mSvyDivCd)) {
                            mItemBinding.tvMiddleErrorRepair.setVisibility(View.GONE);
                            mItemBinding.tvPrecheckMiddleTitle.setVisibility(View.VISIBLE);
                            mItemBinding.tvPrecheckMiddle.setText(surveyListDialog.getSelectItem());
                            mItemBinding.tvPrecheckMiddle.setTextAppearance(R.style.Precheck_SelectBox_Selected);
                            mItemBinding.tvPrecheckMiddle.setBackgroundResource(R.drawable.ripple_bg_ffffff_stroke_000000);

                            mItemBinding.tvPrecheckMinorTitle.setVisibility(View.GONE);
                            mItemBinding.llMinorContain.setVisibility(View.GONE);
                            mItemBinding.tvPrecheckMinor.setVisibility(View.VISIBLE);
                            mItemBinding.tvPrecheckMinor.setTextAppearance(R.style.Precheck_SelectBox_DeSelected);
                            mItemBinding.tvPrecheckMinor.setBackgroundResource(R.drawable.ripple_bg_ffffff_stroke_e5e5e5);
                            mItemBinding.tvPrecheckMinor.setText(getString(R.string.sm01_maintenance_22));

                            mSvyDivCd = "S";
                            for(int i=0; i<middleList.size(); i++) {
                                SurveyItemVO item = middleList.get(i);
                                if(surveyListDialog.getSelectItem().equals(item.getSvyPrvsNm())) {
                                    mSvyMgmtNo = item.getSvyMgmtNo();
                                    mSvyPrvsNo = item.getSvyPrvsNo();

                                    break;
                                }
                            }

                            getSurveyList(mSvyDivCd, mSvyMgmtNo, mSvyPrvsNo);
                        } else if("S".equals(mSvyDivCd)) {

                        }
                    }
                }
        );

        surveyListDialog.setSelectItem(null);
        surveyListDialog.show();
    }

    private void showCheckBottomDialog() {
        surveyMinorListDialog = new BottomCheckListDialog(this, R.style.BottomSheetDialogTheme);
        surveyMinorListDialog.setTitle(getString(R.string.sm01_maintenance_23));
        surveyMinorListDialog.setDatas(surveyList);
        surveyMinorListDialog.setCheckDatas(selectedMinorList);
        surveyMinorListDialog.setOnDismissListener(
                dialog -> {
                    if(surveyMinorListDialog.getSelectItems() != null) {
                        if(surveyMinorListDialog.getSelectItems().size() > 0) {
                            mItemBinding.tvMinorErrorRepair.setVisibility(View.GONE);
                            mItemBinding.tvPrecheckMinorTitle.setVisibility(View.VISIBLE);
                            mItemBinding.tvPrecheckMinor.setVisibility(View.GONE);
                            mItemBinding.llMinorContain.setVisibility(View.VISIBLE);
                            mItemBinding.llMinorContain.removeAllViews();

                            // 선택한 소분류 레이아웃 그리기
                            for(int i=0; i<surveyMinorListDialog.getSelectItems().size(); i++) {
                                ItemMinorBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getApplicationContext()), R.layout.item_minor, null, false);
                                binding.tvMinor.setText(surveyMinorListDialog.getSelectItems().get(i));

                                mItemBinding.llMinorContain.addView(binding.getRoot());
                            }

                                ui.tvServicePrecheckNextBtn.performClick();
                        }
                    }
                }
        );

        surveyMinorListDialog.setSelectItems(null);
        surveyMinorListDialog.show();
    }

    @Override
    public void setViewModel() {
        ui.setActivity(this);
        reqViewModel = new ViewModelProvider(this).get(REQViewModel.class);
    }

    @Override
    public void setObserver() {
        reqViewModel.getRES_REQ_1016().observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    return;
                case SUCCESS:
                    showProgressDialog(false);

                    if (result.data != null) {
                        if("0000".equals(result.data.getRtCd())) {
                            surveyList = new ArrayList<>();

                            if(result.data.getSvyPrvsList().size() > 0) {
                                if("L".equals(result.data.getSvyDivCd())) {
                                    majorList = result.data.getSvyPrvsList();
                                    mItemBinding.tvPrecheckMajor.setTag(majorList);
                                    mItemBinding.tvPrecheckMajor.performClick();
                                } else if("M".equals(result.data.getSvyDivCd())) {
                                    middleList = result.data.getSvyPrvsList();
                                    mItemBinding.tvPrecheckMiddle.setTag(middleList);
                                    mItemBinding.tvPrecheckMiddle.performClick();
                                } else {
                                    minorList = result.data.getSvyPrvsList();
                                    mItemBinding.tvPrecheckMinor.setTag(minorList);
                                    mItemBinding.tvPrecheckMinor.performClick();
                                }
                            }
                            break;
                        }
                    }
                default:
                    showProgressDialog(false);
                    String serverMsg="";
                    try {
                        serverMsg = result.data.getRtMsg();
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally{
                        if(TextUtils.isEmpty(serverMsg)) serverMsg = getString(R.string.r_flaw06_p02_snackbar_1);
                        SnackBarUtil.show(this, serverMsg);
                    }
                    break;
            }
        });
    }

    @Override
    public void getDataFromIntent() {
        try {
            repairReserveVO = (RepairReserveVO) getIntent().getSerializableExtra(KeyNames.KEY_NAME_SERVICE_RESERVE_INFO);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (repairReserveVO == null) {
                exitPage("신청 정보가 존재하지 않습니다.\n잠시후 다시 시도해 주십시오.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ResultCodes.REQ_CODE_SERVICE_RESERVE_REPAIR.getCode()) {
            exitPage(data, ResultCodes.REQ_CODE_SERVICE_RESERVE_REPAIR.getCode());
        }
    }
}