package com.genesis.apps.ui.main.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintSet;
import androidx.lifecycle.ViewModelProvider;
import androidx.transition.ChangeBounds;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.BTR_2001;
import com.genesis.apps.comm.model.vo.CounselCodeVO;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.viewmodel.BTRViewModel;
import com.genesis.apps.databinding.ActivityBtrConsultType1Binding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.bottom.BottomListDialog;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class BtrConsultTypeActivity extends SubActivity<ActivityBtrConsultType1Binding> {

    private final int[] layouts = {R.layout.activity_btr_consult_type_1, R.layout.activity_btr_consult_type_2, R.layout.activity_btr_consult_type_3, R.layout.activity_btr_consult_type_4};
    private TextView[] textViews;
    private TextView[] textTitleViews;
    private ConstraintSet[] constraintSets = new ConstraintSet[layouts.length];
    private String[] selectCdValId = new String[layouts.length];
    private List<CounselCodeVO> listCnsl;
    private List<CounselCodeVO> listLgct;
    private List<CounselCodeVO> listMdct;
    private List<CounselCodeVO> listSmct;
    private String vin;

    private BTRViewModel btrViewModel;
//    private String vin;
//    private BtrVO btrVO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layouts[0]);
        getDataFromIntent();
        setViewModel();
        setObserver();
        initView();
        btrViewModel.reqBTR2001(new BTR_2001.Request(APPIAInfo.GM_BT04.getId(), VariableType.BTR_CNSL_CODE_CNSL,"","",""));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void getDataFromIntent() {
        try {
            vin = getIntent().getStringExtra(KeyNames.KEY_NAME_VIN);
            if (TextUtils.isEmpty(vin)) {
                exitPage("차대번호가 존재하지 않습니다.\n잠시후 다시 시도해 주십시오.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
            exitPage("차대번호가 존재하지 않습니다.\n잠시후 다시 시도해 주십시오.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
        }
    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        ui.setActivity(this);
        btrViewModel = new ViewModelProvider(this).get(BTRViewModel.class);
    }

    @Override
    public void setObserver() {

        btrViewModel.getRES_BTR_2001().observe(this, result -> {

            switch (result.status){
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    showProgressDialog(false);
                    if(result.data!=null){
                        initSelectCdValid(result.data);
                    }
                    break;
                default:
                    showProgressDialog(false);
                    break;
            }
        });

    }


    /**
     * @brief 선택된 아이템 초기화
     */
    private void initSelectCdValid(BTR_2001.Response result) {
        String msg="";
        int nextPos=0;
        switch (result.getCdReqCd()){
            case VariableType.BTR_CNSL_CODE_CNSL:
                nextPos=0;
                selectCdValId[0]="";
                selectCdValId[1]="";
                selectCdValId[2]="";
                selectCdValId[3]="";
                listCnsl = result.getCdList();
                msg = String.format(getString(R.string.gm_bt04_2),"");
                break;
            case VariableType.BTR_CNSL_CODE_LARGE:
                nextPos=1;
                selectCdValId[0]=result.getConslCd();
                selectCdValId[1]="";
                selectCdValId[2]="";
                selectCdValId[3]="";
                listLgct = result.getCdList();
                msg = String.format(getString(R.string.gm_bt04_2),getString(R.string.gm_bt04_7));
                break;
            case VariableType.BTR_CNSL_CODE_MEDIUM:
                nextPos=2;
                selectCdValId[1]=result.getLgrCatCd();
                selectCdValId[2]="";
                selectCdValId[3]="";
                listMdct = result.getCdList();
                msg = String.format(getString(R.string.gm_bt04_2),getString(R.string.gm_bt04_8));
                break;
            case VariableType.BTR_CNSL_CODE_SMALL:
                nextPos=3;
                selectCdValId[2]=result.getMdlCatCd();
                selectCdValId[3]="";
                listSmct = result.getCdList();
                msg = String.format(getString(R.string.gm_bt04_2),getString(R.string.gm_bt04_9));
                break;
        }
        ui.tvMsg.setText(msg);
        setUpdateView();
        doTransition(nextPos);
    }

    /**
     * @brief 뷰 갱신
     * 선택되거나 선택되지 않은 뷰를 갱신
     *
     */
    private void setUpdateView() {
        String name="";
        for(int i=0; i<selectCdValId.length; i++){
            if(selectCdValId[i].equalsIgnoreCase("")){
                //선택되어 있지 않을 경우
                textViews[i].setTextAppearance(R.style.BtrConsultTypeDisable);
                textTitleViews[i].setVisibility(View.GONE);
                switch (i){
                    case 0:
                        name = getString(R.string.gm_bt04_3);
                        break;
                    case 1:
                        name = String.format(getString(R.string.gm_bt04_6), getString(R.string.gm_bt04_7));
                        break;
                    case 2:
                        name = String.format(getString(R.string.gm_bt04_6), getString(R.string.gm_bt04_8));
                        break;
                    case 3:
                        name = String.format(getString(R.string.gm_bt04_6), getString(R.string.gm_bt04_9));
                        break;
                }
            }else{
                textViews[i].setTextAppearance(R.style.BtrConsultTypeEnable);
                textTitleViews[i].setVisibility(View.VISIBLE);
                switch (i){
                    case 0:
                        name = getName(listCnsl, selectCdValId[i]);
                        break;

                    case 1:
                        name = getName(listLgct, selectCdValId[i]);
                        break;

                    case 2:
                        name = getName(listMdct, selectCdValId[i]);
                        break;

                    case 3:
                        name = getName(listSmct, selectCdValId[i]);
                        break;
                }
            }
            textViews[i].setText(name);
        }
    }


    private void initView() {
        initConstraintSets();
    }


    @Override
    public void onClickCommon(View v) {
        switch (v.getId()){
            case R.id.tv_cnsl:
                openBottomDialog(v, listCnsl);
                break;
            case R.id.tv_lgct:
                openBottomDialog(v, listLgct);
                break;
            case R.id.tv_mdct:
                openBottomDialog(v, listMdct);
                break;
            case R.id.tv_smct:
                openBottomDialog(v, listSmct);
                break;
        }
    }

    private void openBottomDialog(final View v, final List<CounselCodeVO> counselList) {

        if(!isValid(v.getId())) {
            SnackBarUtil.show(this, getErrorMsg(v.getId()));
            return;
        }

        List<String> list = new ArrayList<>();

        for(CounselCodeVO counselCodeVO : counselList){
            list.add(counselCodeVO.getCdValNm());
        }

        final BottomListDialog bottomListDialog = new BottomListDialog(this, R.style.BottomSheetDialogTheme);
        bottomListDialog.setOnDismissListener(dialogInterface -> {
            String result = bottomListDialog.getSelectItem();
            if(!TextUtils.isEmpty(result)){
                selectItem(result, v.getId());
            }
        });
        bottomListDialog.setDatas(list);
        bottomListDialog.setTitle(getPopUpTitle(v.getId()));
        bottomListDialog.show();
    }

    private void initConstraintSets() {
        textViews = new TextView[]{ui.tvCnsl, ui.tvLgct, ui.tvMdct, ui.tvSmct};
        textTitleViews = new TextView[]{ui.tvTitleCnsl, ui.tvTitleLgct, ui.tvTitleMdct, ui.tvTitleSmct};
        for (int i = 0; i < layouts.length; i++) {
            constraintSets[i] = new ConstraintSet();

            if (i == 0)
                constraintSets[i].clone(ui.container);
            else
                constraintSets[i].clone(this, layouts[i]);
        }
    }

    private void doTransition(int pos) {
        if (textViews[pos].getVisibility() == View.GONE) {
            Transition changeBounds = new ChangeBounds();
            changeBounds.setInterpolator(new OvershootInterpolator());
            TransitionManager.beginDelayedTransition(ui.container);
            constraintSets[pos].applyTo(ui.container);
        }
    }



    /**
     * @brief 뷰 아이디를 기준으로 팝업용 타이틀 생성
     * @param id
     * @return
     */
    private String getPopUpTitle(int id){

        String title="";

        switch (id){
            case R.id.tv_cnsl:
                title = getString(R.string.gm_bt04_17);
                break;
            case R.id.tv_lgct:
                title = String.format(getString(R.string.gm_bt04_4), getString(R.string.gm_bt04_18));
                break;
            case R.id.tv_mdct:
                title = String.format(getString(R.string.gm_bt04_4), getString(R.string.gm_bt04_19));
                break;
            case R.id.tv_smct:
            default:
                title = String.format(getString(R.string.gm_bt04_4), getString(R.string.gm_bt04_20));
                break;
        }

        return title;
    }


    private String getErrorMsg(int id){

        String title="";

        switch (id){
            case R.id.tv_cnsl:
                break;
            case R.id.tv_lgct:
                title = "상담 구분이 선택되지 않았습니다.\n상담 구분을 먼저 선택해 주세요.";
                break;
            case R.id.tv_mdct:
                title = "상담 유형(대분류)이 선택되지 않았습니다.\n상담 유형을 먼저 선택해 주세요.";
                break;
            case R.id.tv_smct:
            default:
                title = "상담 유형(중분류)이 선택되지 않았습니다.\n상담 유형을 먼저 선택해 주세요.";
                break;
        }

        return title;
    }



    private void selectItem(String cdValNm, int viewId){
        switch (viewId){
            case R.id.tv_cnsl:
                //기 선택되어 있는 항목과 다를 경우
                if(!selectCdValId[0].equalsIgnoreCase(getCode(listCnsl, cdValNm))){
                    btrViewModel.reqBTR2001(new BTR_2001.Request(APPIAInfo.GM_BT04.getId(), VariableType.BTR_CNSL_CODE_LARGE, getCode(listCnsl, cdValNm),"",""));
                }
                break;
            case R.id.tv_lgct:
                //기 선택되어 있는 항목과 다를 경우
                if(!selectCdValId[1].equalsIgnoreCase(getCode(listLgct, cdValNm))){
                    btrViewModel.reqBTR2001(new BTR_2001.Request(APPIAInfo.GM_BT04.getId(), VariableType.BTR_CNSL_CODE_MEDIUM, getCode(listLgct, cdValNm),"",""));
                }
                break;
            case R.id.tv_mdct:
                //기 선택되어 있는 항목과 다를 경우
                if(!selectCdValId[2].equalsIgnoreCase(getCode(listMdct, cdValNm))){
                    btrViewModel.reqBTR2001(new BTR_2001.Request(APPIAInfo.GM_BT04.getId(), VariableType.BTR_CNSL_CODE_SMALL, getCode(listMdct, cdValNm),"",""));
                }
                break;
            case R.id.tv_smct:
            default:
                //기 선택되어 있는 항목과 다를 경우
                String cdValId=getCode(listSmct, cdValNm);
                if(!selectCdValId[3].equalsIgnoreCase(cdValId)){
                    selectCdValId[3] = cdValId;
                    setUpdateView();
                    startActivitySingleTop(new Intent(this, BtrConsultApplyActivity.class).putExtra(KeyNames.KEY_NAME_BTR_CNSL_LIST, new Gson().toJson(selectCdValId)).putExtra(KeyNames.KEY_NAME_VIN,vin).addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT), 0, VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                    finish();
                }
                break;
        }
    }

    /**
     * @brief 코드 값 확인
     * counselcode list에서 cdValNm에 맞는 코드값 확인
     *
     * @param list
     * @param cdValNm
     * @return
     */
    private String getCode(List<CounselCodeVO> list, String cdValNm){

        String cdValId="";

        for(CounselCodeVO counselCodeVO : list){

            if(cdValNm.equalsIgnoreCase(counselCodeVO.getCdValNm())){
                cdValId = counselCodeVO.getCdValId();
                break;
            }
        }

        return cdValId;
    }

    /**
     * @brief 명칭 값 확인
     * counselcode list에서 cdValId 맞는 명칭 값 확인
     *
     * @param list
     * @param cdValId
     * @return
     */
    private String getName(List<CounselCodeVO> list, String cdValId){
        String cdValNm="";

        for(CounselCodeVO counselCodeVO : list){

            if(cdValId.equalsIgnoreCase(counselCodeVO.getCdValId())){
                cdValNm = counselCodeVO.getCdValNm();
                break;
            }
        }
        return cdValNm;
    }

    private boolean isValid(int viewId){
        boolean isValid = false;
        if (viewId == R.id.tv_cnsl || !selectCdValId[getPositionFromViewId(viewId) - 1].equalsIgnoreCase("")) {
            isValid = true;
        }
        return isValid;
    }


    private int getPositionFromViewId(int viewId){
        int pos=0;

        switch (viewId){
            case R.id.tv_cnsl:
                pos=0;
                break;
            case R.id.tv_lgct:
                pos=1;
                break;
            case R.id.tv_mdct:
                pos=2;
                break;
            case R.id.tv_smct:
            default:
                pos=3;
                break;
        }
        return pos;
    }

}
