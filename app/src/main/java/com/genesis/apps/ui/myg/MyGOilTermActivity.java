package com.genesis.apps.ui.myg;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.OilCodes;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.OIL_0001;
import com.genesis.apps.comm.viewmodel.OILViewModel;
import com.genesis.apps.comm.model.vo.TermVO;
import com.genesis.apps.databinding.ActivityMygOilTermBinding;
import com.genesis.apps.databinding.ItemTermBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import static com.genesis.apps.comm.model.vo.OilPointVO.OIL_CODE_GSCT;
import static com.genesis.apps.comm.model.vo.TermVO.TERM_ESN_AGMT_N;

public class MyGOilTermActivity extends SubActivity<ActivityMygOilTermBinding> {

    private String oilRfnCd;
    private OILViewModel oilViewModel;
    private List<TermView> checkBoxs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myg_oil_term);
        getDataFromIntent();
        setViewModel();
        setObserver();
        setView();
        oilViewModel.reqOIL0001(new OIL_0001.Request(APPIAInfo.MG_CON02_01.getId(), oilRfnCd));
    }

    private void setView() {
        ui.setActivity(this);
        ui.cbAll.setOnCheckedChangeListener(listenerAll);
        ui.tv1.setText(String.format(Locale.getDefault(), getString(R.string.mg_con02_01_2), getString(OilCodes.findCode(oilRfnCd).getOilNm())));
        ui.tv2.setText(String.format(Locale.getDefault(), getString(R.string.mg_con02_01_3), getString(OilCodes.findCode(oilRfnCd).getOilNm())));
        ui.tv3.setText(String.format(Locale.getDefault(), getString(R.string.mg_con02_01_6), getString(OilCodes.findCode(oilRfnCd).getOilPontNm())));
    }


    @Override
    public void onClickCommon(View v) {
        switch (v.getId()){
            case R.id.btn_next:
                //TODO 약관동의 페이지로 이동 및 데이터 실패에 대한 스낵바 정의를 여기서 해줘야함.
                break;
            case R.id.iv_arrow:
                try{
                    TermVO termVO = (TermVO)v.getTag(R.id.oil_term);
                    Log.v("test","test:"+termVO.getTermCd());
                    startActivitySingleTop(new Intent(this, MyGOilTermDetailActivity.class).putExtra(MyGOilTermDetailActivity.OIL_CODE,oilRfnCd).putExtra(MyGOilTermDetailActivity.TERMS_CODE,termVO), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        oilViewModel = new ViewModelProvider(this).get(OILViewModel.class);
    }

    @Override
    public void setObserver() {
        oilViewModel.getRES_OIL_0001().observe(this, result -> {

            String test="{\n" +
                    "  \"rsltCd\": \"0000\",\n" +
                    "  \"rsltMsg\": \"성공\",\n" +
                    "  \"termList\": [\n" +
//                    "    {\n" +
//                    "      \"termVer\": \"00.00.01\",\n" +
//                    "      \"termCd\": \"1000\",\n" +
//                    "      \"termNm\": \"모바일 멤버십 발급을 위한 제3자 제공\",\n" +
//                    "      \"termEsnAgmtYn\": \"Y\"\n" +
//                    "    },\n" +
//                    "    {\n" +
//                    "      \"termVer\": \"00.00.01\",\n" +
//                    "      \"termCd\": \"1001\",\n" +
//                    "      \"termNm\": \"'제네시스 앱' 서비스 이용을 위한 제3자 제공\",\n" +
//                    "      \"termEsnAgmtYn\": \"Y\"\n" +
//                    "    },\n" +
                    "    {\n" +
                    "      \"termVer\": \"00.00.01\",\n" +
                    "      \"termCd\": \"2000\",\n" +
                    "      \"termNm\": \"GS&POINT 서비스 약관\",\n" +
                    "      \"termEsnAgmtYn\": \"Y\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"termVer\": \"00.00.01\",\n" +
                    "      \"termCd\": \"2001\",\n" +
                    "      \"termNm\": \"GS&POINT 개인정보 수집 및 활용\",\n" +
                    "      \"termEsnAgmtYn\": \"Y\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"termVer\": \"00.00.01\",\n" +
                    "      \"termCd\": \"2002\",\n" +
                    "      \"termNm\": \"마케팅 목적 개인정보 수집 및 활용에 대한 동\",\n" +
                    "      \"termEsnAgmtYn\": \"N\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"termVer\": \"00.00.01\",\n" +
                    "      \"termCd\": \"2003\",\n" +
                    "      \"termNm\": \"GS&POINT 서비스 제공을 위한 제3자 제공\\n(GS리테일, GS홈쇼핑)\",\n" +
                    "      \"termEsnAgmtYn\": \"Y\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"termVer\": \"00.00.01\",\n" +
                    "      \"termCd\": \"2004\",\n" +
                    "      \"termNm\": \"\tGS&POINT 참여사의 상품/서비스 마케팅 및 고객응대를 위한 제3자 제공\n(GS리테일, GS 홈쇼핑) \",\n" +
                    "      \"termEsnAgmtYn\": \"N\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"termVer\": \"00.00.01\",\n" +
                    "      \"termCd\": \"2005\",\n" +
                    "      \"termNm\": \"\tGS&POINT 제휴 상품/서비스 홍보를 위한 제3자 제공\n(GS엠비즈) \",\n" +
                    "      \"termEsnAgmtYn\": \"N\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"termVer\": \"00.00.01\",\n" +
                    "      \"termCd\": \"2006\",\n" +
                    "      \"termNm\": \"\tGS&POINT 개인정보의 처리위탁\",\n" +
                    "      \"termEsnAgmtYn\": \"N\"\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}";

            if(!oilRfnCd.equalsIgnoreCase(OIL_CODE_GSCT)){
                test="{\n" +
                        "  \"rsltCd\": \"0000\",\n" +
                        "  \"rsltMsg\": \"성공\",\n" +
                        "  \"termList\": [\n" +
//                    "    {\n" +
//                    "      \"termVer\": \"00.00.01\",\n" +
//                    "      \"termCd\": \"1000\",\n" +
//                    "      \"termNm\": \"모바일 멤버십 발급을 위한 제3자 제공\",\n" +
//                    "      \"termEsnAgmtYn\": \"Y\"\n" +
//                    "    },\n" +
//                    "    {\n" +
//                    "      \"termVer\": \"00.00.01\",\n" +
//                    "      \"termCd\": \"1001\",\n" +
//                    "      \"termNm\": \"'제네시스 앱' 서비스 이용을 위한 제3자 제공\",\n" +
//                    "      \"termEsnAgmtYn\": \"Y\"\n" +
//                    "    },\n" +
                        "    {\n" +
                        "      \"termVer\": \"00.00.01\",\n" +
                        "      \"termCd\": \"3000\",\n" +
                        "      \"termNm\": \"회원약관\",\n" +
                        "      \"termEsnAgmtYn\": \"Y\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"termVer\": \"00.00.01\",\n" +
                        "      \"termCd\": \"3001\",\n" +
                        "      \"termNm\": \"보너스카드 이용약관\",\n" +
                        "      \"termEsnAgmtYn\": \"Y\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"termVer\": \"00.00.01\",\n" +
                        "      \"termCd\": \"3002\",\n" +
                        "      \"termNm\": \"가입화면 내 개인정보수집이용동의\",\n" +
                        "      \"termEsnAgmtYn\": \"Y\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"termVer\": \"00.00.01\",\n" +
                        "      \"termCd\": \"3003\",\n" +
                        "      \"termNm\": \"가입화면 내 개인정보수집이용동의\",\n" +
                        "      \"termEsnAgmtYn\": \"N\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"termVer\": \"00.00.01\",\n" +
                        "      \"termCd\": \"3004\",\n" +
                        "      \"termNm\": \"\t마케팅정보활용동의\",\n" +
                        "      \"termEsnAgmtYn\": \"N\"\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}";
            }



            OIL_0001.Response sample = new Gson().fromJson(test, OIL_0001.Response.class);
            addTermToLayout(sample.getTermList());
//            //TODO ERROR 및 메시지 처리 필요
//            switch (result.status){
//                case SUCCESS:
//                    showProgressDialog(false);
//                    addTermToLayout(result.data.getTermList());
//                    break;
//                case LOADING:
//                    showProgressDialog(true);
//                    break;
//                case ERROR:
//                    showProgressDialog(false);
//                    break;
//            }
        });
    }

    @Override
    public void getDataFromIntent() {
        try {
            oilRfnCd = getIntent().getStringExtra(OilCodes.KEY_OIL_CODE);
            if(TextUtils.isEmpty(oilRfnCd)){
                exitPage("정유소 정보가 존재하지 않습니다.\n잠시후 다시 시도해 주십시오.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
            }
        }catch (Exception e){
            e.printStackTrace();
            exitPage("정유소 정보가 존재하지 않습니다.\n잠시후 다시 시도해 주십시오.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
        }
    }

    private void checkAgree() {

        for(int i=0; i<checkBoxs.size(); i++){
            if(checkBoxs.get(i).termVO.getTermEsnAgmtYn().equalsIgnoreCase(TermVO.TERM_ESN_AGMT_Y)&&!checkBoxs.get(i).checkBox.isChecked()){
                ui.btnBlock.setVisibility(View.VISIBLE);
                ui.btnNext.setEnabled(false);
                return;
            }
        }
        ui.btnBlock.setVisibility(View.INVISIBLE);
        ui.btnNext.setEnabled(true);
        //TODO 약관동의 요청하는 부분 추가 필요
    }

    private void addTermToLayout(List<TermVO> termList){
        for(TermVO termVO : termList){

            final LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final ItemTermBinding itemTermBinding = DataBindingUtil.inflate(inflater, R.layout.item_term, null, false);
            final View view = itemTermBinding.getRoot();

            itemTermBinding.setListener(onSingleClickListener);
            itemTermBinding.cb.setOnCheckedChangeListener(listener);
            itemTermBinding.cb.setText(termVO.getTermNm() + (termVO.getTermEsnAgmtYn().equalsIgnoreCase(TERM_ESN_AGMT_N) ? getString(R.string.mg_con02_01_13) : getString(R.string.mg_con02_01_14)));
            itemTermBinding.ivArrow.setTag(R.id.oil_term, termVO);
            checkBoxs.add(new TermView(termVO, itemTermBinding.cb));
//            view.setId(Integer.parseInt(termVO.getTermCd()));
            view.setId(Integer.parseInt(termVO.getTermCd()));
            //TODO 수정필요
            switch (termVO.getTermCd()){
                case "1000":
                case "1001":
                    ui.lTermTop.addView(itemTermBinding.getRoot());
                    break;
                default:
                    ui.lTermBottom.addView(itemTermBinding.getRoot());
                    break;
            }
        }
    }

    private void setCheckBoxsAll(boolean check){
        for(int i=0; i<checkBoxs.size();i++){
            checkBoxs.get(i).checkBox.setChecked(check);
        }
    }

    private void setCheckBoxsAll(){
        for(int i=0; i<checkBoxs.size();i++){
            if(!checkBoxs.get(i).checkBox.isChecked()){
                ui.cbAll.setChecked(false);
                return;
            }
        }
        ui.cbAll.setChecked(true);
    }


    CompoundButton.OnCheckedChangeListener listener = (compoundButton, b) -> {
        if(compoundButton.isPressed()) {
            setCheckBoxsAll();
            checkAgree();
        }
    };

    CompoundButton.OnCheckedChangeListener listenerAll = (compoundButton, b) -> {
        if(compoundButton.isPressed()) {
            setCheckBoxsAll(b);
            checkAgree();
        }
    };


    class TermView{
        private TermVO termVO;
        private CheckBox checkBox;

        TermView(TermVO termVO, CheckBox checkBox){
            this.termVO = termVO;
            this.checkBox = checkBox;
        }

        public CheckBox getCheckBox() {
            return checkBox;
        }

        public void setCheckBox(CheckBox checkBox) {
            this.checkBox = checkBox;
        }
    }



}
